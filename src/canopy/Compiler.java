package canopy;

import java.util.List;
import java.util.ArrayList;
import java.util.HashSet;

import java.util.function.Consumer;
import java.text.ParseException;

import canopy.MetaGrammar.*;
import static canopy.MetaGrammar.Label.*;

public class Compiler {

    String grammarText;
    Builder builder;

    public Compiler(String grammarText, Builder builder) {
        this.grammarText = grammarText;
        this.builder = builder;
    }

    public String toSource() throws ParseException {
        parseTree().compile(builder,null);
        return builder.serialize();
    }

    ASTreeNode tree;

    ASTreeNode parseTree() throws ParseException {
        if (tree == null) {
            tree = (ASTreeNode) MetaGrammar.parse(grammarText,actions);
        }
        return tree;
    }

    class ASTreeNode extends MetaGrammar.TreeNode {
        void apply(Consumer<ASTreeNode> callback) {} // -> forEach()
        void compile(Builder builder, String address) {compile(builder,address,null);}
        void compile(Builder builder, String address, String name) {}
    }

    Actions actions = new Actions() {

        @Override
        public TreeNode grammar(String input, int start, int end, List<TreeNode> elements) {
            var rules = collect(null,elements.get(2).nodes,rule);
            return new Grammar(elements.get(1).get(id).text, rules);
        }

        @Override
        public TreeNode rule(String input, int start, int end, List<TreeNode> elements) {
            return new Rule(elements.get(0).text, (ASTreeNode)elements.get(2));
        }

        @Override
        public TreeNode paren_expr(String input, int start, int end, List<TreeNode> elements) {
            return elements.get(2);
        }

        @Override
        public TreeNode choice(String input, int start, int end, List<TreeNode> elements) {
            var parts = collect(elements.get(0),elements.get(1).nodes,expr);
            return new Choice(parts);
        }

        @Override
        public TreeNode extension(String input, int start, int end, List<TreeNode> elements) {
            var expression = elements.get(0);
            var typeTag    = elements.get(2);
            return new Extension((ASTreeNode)expression,typeTag.get(id).text);
        }

        @Override
        public TreeNode action(String input, int start, int end, List<TreeNode> elements) {
            return new Action((ASTreeNode)elements.get(0), elements.get(2).get(id).text);
        }

        @Override
        public TreeNode sequence(String input, int start, int end, List<TreeNode> elements) {
            var parts = collect(elements.get(0),elements.get(1).nodes,expr);
            return new Sequence(parts);
        }

        @Override
        public TreeNode sequence_part(String input, int start, int end, List<TreeNode> elements) {
            var muted = elements.get(0).text;
            var label = elements.get(1).get(id);
            return new SequencePart((ASTreeNode)elements.get(2),
                    (label != null ? label.text : null),
                    ! muted.isEmpty() );
        }

        @Override
        public TreeNode predicate(String input, int start, int end, List<TreeNode> elements) {
            var polarities = false;
            switch (elements.get(0).text) {
              case "&": polarities = true; break;
              case "!": polarities = false; break;
            }
            return new Predicate((ASTreeNode)elements.get(2), polarities);
        }

        @Override
        public TreeNode repeat(String input, int start, int end, List<TreeNode> elements) {
            var quantities = -1;
            switch (elements.get(2).text) {
              case "*": quantities = 0; break;
              case "+": quantities = 1; break;
            }
            return new Repeat((ASTreeNode)elements.get(0), quantities);
        }

        @Override
        public TreeNode maybe(String input, int start, int end, List<TreeNode> elements) {
            return new Maybe((ASTreeNode)elements.get(0));
        }

        @Override
        public TreeNode reference(String input, int start, int end, List<TreeNode> elements) {
            return new Reference(elements.get(0).text);
        }

        @Override
        public TreeNode string(String input, int start, int end, List<TreeNode> elements) {
            var text = input.substring(start,end);
            var value = eval(text);
            return new CiString(text,value,false);
        }

        @Override
        public TreeNode ci_string(String input, int start, int end, List<TreeNode> elements) {
            var text = input.substring(start,end);
            var value = eval(elements.get(1).text);
            return new CiString(text,value,true);
        }

        @Override
        public TreeNode char_class(String input, int start, int end, List<TreeNode> elements) {
            var text = input.substring(start,end);
            return new CharClass(text, '^'+text);
        }

        @Override
        public TreeNode any_char(String input, int start, int end, List<TreeNode> elements) {
            return new AnyChar();
        }
    };

    String eval(String o) {
        o = Strings.unquote(o);
        return Strings.unescape(o).toString();
    }

    static List<ASTreeNode> collect(TreeNode head, List<TreeNode> tail, Label key) {
        var list = new ArrayList<ASTreeNode>();
        if (head != null) list.add((ASTreeNode)head);
        tail.forEach(e -> list.add((ASTreeNode)e.get(key)));
        return list;
    }

    static String[] args(String... args) { return args; }

    static class Int { Int(int i) {n=i;} int n; }

    class Action extends ASTreeNode {
        Action(ASTreeNode expression, String actionName) {
            this.expression = expression;
            this.actionName = actionName;
        }
        ASTreeNode expression;
        String actionName;

        @Override
        void apply(Consumer callback) {
            callback.accept(expression);
        }

        @Override
        void compile(Builder builder, String address) {
            expression.compile(builder,address,actionName);
        }
    }

    class AnyChar extends ASTreeNode {

        @Override
        void compile(Builder builder, String address, String action) {
            builder.if_(builder.hasChars_(),
              () -> {
                var of = builder.offset_();
                builder.syntaxNode_(address, of, of + " + 1", null, action, null);
              }, () -> {
                builder.failure_(address, "<any char>");
              }
            );
        }
    }

    class CharClass extends ASTreeNode {
        CharClass(String text, String regex) {
            this.text = text;
            this.regex = regex;
        }
        String regex;
        String constName;

        @Override
        void compile(Builder builder, String address, String action) {
            var re = constName != null ? constName : regex;
            var chunk = builder.chunk_(1);
            builder.if_(builder.regexMatch_(re,chunk),
              () -> {
                var of = builder.offset_();
                builder.syntaxNode_(address, of, of + " + 1", null, action, null);
              }, () -> {
                builder.failure_(address, text);
              }
            );
        }
    }

    class Choice extends ASTreeNode {
        Choice(List<ASTreeNode> options) {
            this.options = options;
        }
        List<ASTreeNode> options;

        @Override
        void apply(Consumer<ASTreeNode> callback) {
            options.forEach(callback);
        }

        @Override
        void compile(Builder builder, String address) {
            var startOffset = builder.localVar_("index", builder.offset_());
            compileChoices(builder,address,0,startOffset);
        }
        void compileChoices(Builder builder, String address, int index, String startOffset) {
            if (index == options.size()) return;
            options.get(index).compile(builder, address);
            builder.unlessNode_(address, () -> {
                builder.assign_(builder.offset_(), startOffset);
                compileChoices(builder, address, index + 1, startOffset);
            },null);
        }
    }

    class Extension extends ASTreeNode {
        Extension(ASTreeNode expression, String typeName) {
            this.expression = expression;
            this.typeName   = typeName;
        }
        ASTreeNode expression;
        String typeName;

        @Override
        void apply(Consumer<ASTreeNode> callback) {
            callback.accept(expression);
        }

        @Override
        void compile(Builder builder, String address) {
            expression.compile(builder,address);
            builder.extendNode_(address,typeName);
        }
    }

    class Grammar extends ASTreeNode {
        Grammar(String name, List<ASTreeNode> rules) {
            this.name  = name;
            this.rules = rules;
        }
        String name;
        List<ASTreeNode> rules;

        @Override
        void apply(Consumer<ASTreeNode> callback) {
            rules.forEach(callback);
        }

        void scan(ASTreeNode node, Consumer<ASTreeNode> callback) {
            callback.accept(node);
            node.apply(child -> scan(child,callback));
        }

        @Override
        void compile(Builder builder, String ignore) {
          builder.package_(name, () -> {
            var set = new HashSet<String>();
            scan(this, node -> {
              if (node instanceof Action) {
                set.add(((Action)node).actionName);
              }
            });
            var actions = set.toArray(new String[set.size()]);

            var nodeClassName = builder.syntaxNodeClass_();
            var subclassIndex = new Int(1);
            scan(this, node -> {
              if (node instanceof Sequence) {
                var subclassName = nodeClassName + subclassIndex.n;
                var labels = ((Sequence)node).collectLabels(subclassName);
                if (labels != null)  {
                  builder.class_(subclassName, nodeClassName, () -> {
                    builder.attributes_(labels); // not implemented
                    builder.constructor_(args("text","offset","elements"), () -> {
                      var i = 0;
                      for (var keys:labels) {
                        for (var key:keys) {
                          builder.attribute_(key, builder.arrayLookup_("elements",i));
                        }
                        i++;
                      }
                    });
                  });
                  subclassIndex.n += 1;
                }
              }
            });

            builder.grammarModule_(actions, () -> {
              var regexName = "REGEX_";
              var regexIndex = new Int(1);
              scan(this, node -> {
                if (node instanceof CharClass) {
                  var charClass = (CharClass)node;
                  charClass.constName = regexName + regexIndex.n;  // was in builder.compileRegex_()
                  builder.compileRegex_(charClass.regex, charClass.constName);
                  regexIndex.n += 1;
                }
              });

              rules.forEach(rule -> rule.compile(builder,null) );
            });

            var root = ((Rule)rules.get(0)).name;
            builder.parserClass_(root);
            builder.exports_();
          });
        }
    }

    class Maybe extends ASTreeNode {
        Maybe(ASTreeNode expression) {
            this.expression = expression;
        }
        ASTreeNode expression;

        @Override
        void apply(Consumer<ASTreeNode> callback) {
            callback.accept(expression);
        }

        @Override
        void compile(Builder builder, String address) {
            var startOffset = builder.localVar_("index", builder.offset_());
            expression.compile(builder,address);
            builder.unlessNode_(address, () -> {
                builder.syntaxNode_(address, startOffset, startOffset, null, null, null);
            },null);
        }
    }

    class Predicate extends ASTreeNode {
        Predicate(ASTreeNode expression, boolean positive) {
            this.expression = expression;
            this.positive   = positive;
        }
        ASTreeNode expression;
        boolean positive;

        @Override
        void apply(Consumer<ASTreeNode> callback) {
            callback.accept(expression);
        }

        @Override
        void compile(Builder builder, String address) {
            var startOffset = builder.localVar_("index", builder.offset_());
            Branch branch = positive ? builder::ifNode_ : builder::unlessNode_ ;
            expression.compile(builder, address);
            builder.assign_(builder.offset_(), startOffset);
            branch.if_(address,
              () -> {
                var of = builder.offset_();
                builder.syntaxNode_(address, of, of, null, null, null);
              }, () -> {
                builder.assign_(address, builder.nullNode_());
              }
            );
        }
    }

    @FunctionalInterface
    interface Branch { void if_(String c, Runnable t, Runnable e); }

    class Reference extends ASTreeNode {
        Reference(String name) {
            this.refName = name;
        }
        String refName;

        @Override
        void compile(Builder builder, String address) {
            builder.jump_(address, refName);
        }

        // referenceName: function() {
        //  return this.refName;
        // }
    }

    class Repeat extends ASTreeNode {
        Repeat (ASTreeNode expression, int count) {
            this.expression = expression;
            this.count      = count;
        }
        ASTreeNode expression;
        int count;

        @Override
        void apply(Consumer<ASTreeNode> callback) {
            callback.accept(expression);
        }

        @Override
        void compile(Builder builder, String address, String action) {
            var remaining   = builder.localVar_("remaining", ""+count);
            var startOffset = builder.localVar_("index", builder.offset_());
            var elements    = builder.localVar_("elements", builder.emptyList_(0));
            var elAddr      = builder.localVar_("address", builder.true_());

            builder.whileNotNull_(elAddr, () -> {
                expression.compile(builder, elAddr);
                builder.ifNode_(elAddr, () -> {
                    builder.append_(elements, elAddr, null);
                    builder.decrement_(remaining);
                },null);
            });
            builder.if_(builder.isZero_(remaining),
              () -> {
                builder.syntaxNode_(address, startOffset, builder.offset_(), elements, action, null);
              }, () -> {
                builder.assign_(address, builder.nullNode_());
              }
            );
        }
    }

    class Rule extends ASTreeNode {
        Rule(String name, ASTreeNode expression) {
            this.name = name;
            this.expression = expression;
        }
        String name;
        ASTreeNode expression;

        @Override
        void apply(Consumer<ASTreeNode> callback) {
            callback.accept(expression);
        }

        @Override
        void compile(Builder builder, String address) {
            builder.method_("_read_" + name, noArgs, () -> {
                var rule = name.equals("_") ? "__" : name;
                builder.cache_(rule, (elAddr) -> {
                    expression.compile(builder,elAddr);
                });
            });
        }
    }

    static final String[] noArgs = new String[0];

    class Sequence extends ASTreeNode {
        Sequence(List<ASTreeNode> parts) {
            this.parts = parts;
        }
        List<ASTreeNode> parts;
        String nodeClassName; // from collectLabels()

        @Override
        void apply(Consumer<ASTreeNode> callback) {
            parts.forEach(callback);
        }

        @Override
        void compile(Builder builder, String address, String action) {
            var startOffset = builder.localVar_("index", builder.offset_());
            var elements    = builder.localVar_("elements", builder.emptyList_(countUnmuted()));
            //  var nodeClass   = nodeClassName;
            // System.err.println("nodeClass "+nodeClass);

            compileExpressions(builder, 0, new Int(0), startOffset, elements);

            builder.ifNull_(elements,
              () -> {
                builder.assign_(address, builder.nullNode_());
              }, () -> {
                builder.syntaxNode_(address, startOffset, builder.offset_(), elements, action, nodeClassName);
              }
            );
        }

        void compileExpressions(Builder builder, int index, Int elIndex, String startOffset, String elements) {
            if (index == parts.size()) return;

            var expAddr = builder.localVar_("address",null);
            var expr    = parts.get(index);

            expr.compile(builder, expAddr);

            builder.ifNode_(expAddr,
              () -> {
                if (!muted(expr)) {
                    builder.append_(elements, expAddr, ""+elIndex.n);
                    elIndex.n += 1;
                }
                compileExpressions(builder, index + 1, elIndex, startOffset, elements);
              }, () -> {
                builder.assign_(elements, builder.null_());
                builder.assign_(builder.offset_(), startOffset);
              }
            );
        }

        boolean muted(ASTreeNode expr) {
            return expr instanceof SequencePart && ((SequencePart)expr).muted;
        }

        int countUnmuted() {
            var i = 0; for (var p:parts) if (!muted(p)) i++;
            return i;
        }

        // return parts.stream().filter(p -> !muted(p)).map(i -> 1).reduce(i, Integer::sum);

        List<String[]> collectLabels(String subclassName) {
            var list = new ArrayList<String[]>();
            for (var p:parts) {
                if (p instanceof SequencePart) {
                    var labels = ((SequencePart)p).labels();
                    if (labels.length > 0) nodeClassName = subclassName;
                    list.add(labels);
                }
            }
            return nodeClassName != null ? list : null;
        }
    }

    class SequencePart extends ASTreeNode {
        SequencePart(ASTreeNode expression, String label, boolean muted) {
            this.expression = expression;
            this.label      = label;
            this.muted      = muted;
        };
        ASTreeNode expression;
        String label;
        boolean muted;

        @Override
        void apply(Consumer<ASTreeNode> callback) {
            callback.accept(expression);
        }

        @Override
        void compile(Builder builder, String address) {
            expression.compile(builder,address);
        }

        String[] labels() {
            var ref = expression instanceof Reference ? ((Reference)expression).refName : null;
            if (label != null) {
                return ref != null ? new String[]{label,ref} : new String[]{label};
            } else {
                return ref != null ? new String[]{ref} : noArgs;
            }
        }
    }

    class CiString extends ASTreeNode {
        CiString(String text, String value, boolean ci) {
            this.text  = text;
            this.value = value;
            this.ci    = ci;
        }
        String value;
        boolean ci;

        @Override
        void compile(Builder builder, String address, String action) {
            var length = value.length();
            var chunk  = builder.chunk_(length);
            var condition = ci
                ? builder.stringMatchCI_(chunk, value)
                : builder.stringMatch_(chunk, value);
            builder.if_(condition,
              () -> {
                var of = builder.offset_();
                builder.syntaxNode_(address, of, of + " + " + length, null, action, null);
              }, () -> {
                builder.failure_(address, text);
              }
            );
        }
    }

}

package canopy.builder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Consumer;

public class Java10 implements canopy.Builder {

  @Override
  public void init(String name, char separator) {
      local = new Scope(null,name);
      separatorChar = separator;
      out = new StringBuilder();
      tab = new char[80];
      Arrays.fill(tab,' ');
  }

  static final String ADDRESS =   "address";
  static final String CHUNK =     "chunk";
  static final String ELEMENTS =  "elements";
  static final String INDEX =     "index";
  static final String REMAINING = "remaining";

  static String typeOf(String name) {
    switch (name) {
      case ADDRESS:   return "TreeNode";
      case CHUNK:     return "String";
      case ELEMENTS:  return "List<TreeNode>";
      case INDEX:     return "int";
      case REMAINING: return "int";
    }
    return null;
  }

  class Scope {
    Scope(Scope p, String n) {
      parent=p; name=n;
    }

    Scope parent;
    String name;

    int address = 0;
    int chunk = 0;
    int elements = 0;
    int index = 0;
    int remaining = 0;

    String varName(String name) {
      switch (name) {
        case ADDRESS:   return ADDRESS+(address++);
        case CHUNK:     return CHUNK+(chunk++);
        case ELEMENTS:  return ELEMENTS+(elements++);
        case INDEX:     return INDEX+(index++);
        case REMAINING: return REMAINING+(remaining++);
      }
      throw new RuntimeException("varName "+name);
    }
  }

  Scope local;

  char separatorChar;
  String grammarName;
  Set<String> labels = new HashSet<>();


  void newBuilder(String name, Runnable block) {
    local = new Scope(local,name); // push
    block.run();
    if (local.parent != null) local = local.parent; // pop;
  }

  void _newBuffer(String name) {}

  char[] tab;
  int indent;
  StringBuilder out;

  void _indent(Runnable block) {
    indent += 2; block.run(); indent -= 2;
  }

  void put(String s) {
    out.append(tab,0,indent).append(s).append('\n');
  }
  void putSc(String s) {
    out.append(tab,0,indent).append(s).append(";\n");
  }
  void nl() {
    out.append('\n');
  }

  String _quote(String string) {
    return "\"" + canopy.Strings.escape(string) + '"';
  }

  @Override
  public String serialize() {
    var body = out;
    out = new StringBuilder();

    var name = grammarName;
    var p = name.lastIndexOf('.');
    if (p > -1) {
      putSc("package "+name.substring(0,p));
      nl();
      name = name.substring(p+1);
    }
    putSc("import java.util.List");
    putSc("import java.util.ArrayList");
    putSc("import java.util.Map");
    putSc("import java.util.EnumMap");
    putSc("import java.util.HashMap");
    putSc("import java.util.Iterator");
    putSc("import java.util.regex.Pattern");
    putSc("import java.text.ParseException");
    nl();
    put("public final class " + name + " {");

    return body.insert(0,out).append("\n}\n").toString();
  }
// package canopy;
//

//
// public final class MetaGrammar {
//   -> insert body here
// }

  @Override
  public void package_(String name, Runnable block) {
    grammarName = name.replace("\\.", "");
    block.run();
  }

  @Override
  public String syntaxNodeClass_() {
    var name = "TreeNode";
    _newBuffer(name);

    nl();
    put("public static class " + name + " implements Iterable<" + name + "> {");
    _indent(() -> {

      putSc("public String text");
      putSc("public int offset");
      putSc("public List<" + name + "> nodes");
      nl();
      putSc("Map<Label, " + name + "> labelled");

      nl();
      put("public " + name + "() {");
      _indent(() ->{
        putSc("this(\"\", -1, new ArrayList<" + name + ">(0))");
      });
      put("}");

      nl();
      put("public " + name + "(String text, int offset) {");
      _indent(() -> {
        putSc("this(text, offset, new ArrayList<" + name + ">(0))");
      });
      put("}");

      nl();
      put("public " + name + "(String text, int offset, List<" + name + "> elements) {");
      _indent(() -> {
        assign_("this.text", "text");
        assign_("this.offset", "offset");
        assign_("this.nodes", "elements");
        assign_("this.labelled", "new EnumMap<Label, " + name + ">(Label.class)");
      });
      put("}");

      nl();
      put("public " + name + " get(Label key) {");
      _indent(() -> {
        return_("labelled.get(key)");
      });
      put("}");

      nl();
      put("public Iterator<" + name + "> iterator() {");
      _indent(() -> {
        return_("nodes.iterator()");
      });
      put("}");

    });
    put("}");

    return name;
  }

  @Override
  public void grammarModule_(String[] actions, Runnable block) {
    _newBuffer("CacheRecord");
    nl();
    put("static class CacheRecord {");
    _indent(() -> {
      putSc("TreeNode node");
      putSc("int tail");
      nl();
      put("CacheRecord(TreeNode node, int tail) {");
      _indent(() -> {
        assign_("this.node", "node");
        assign_("this.tail", "tail");
      });
      put("}");
    });
    put("}");

    _newBuffer("Actions");
    nl();
    put("public interface Actions {");
    _indent(() -> {
      for (int i = 0, n = actions.length ; i < n; i++)
        putSc(" TreeNode " + actions[i] + "(String input, int start, int end, List<TreeNode> elements)");
    });
    put("}");

    _newBuffer("Grammar");
    nl();
    put("static class Parser {");
    _indent(() -> {
      assign_("static TreeNode " + nullNode_(), "new TreeNode()");
      nl();
      putSc("int inputSize, offset, failure");
      putSc("String input");
      putSc("List<String> expected");
      putSc("Map<Label, Map<Integer, CacheRecord>> cache");
      putSc("Actions actions");
      nl();
      block.run();
    });
    // put("}"); // -> parserClass_() will provide this
  }

  @Override
  public void compileRegex_(String regex, String name) {
    var source = regex.charAt(0) == '^' ? "\\A"+regex.substring(1) : regex;
    assign_("static Pattern " + name, "Pattern.compile(" + _quote(source) + ")");
  }

  @Override
  public void parserClass_(String root) {

    nl();
    _indent(() -> {

      put("Parser(String input, Actions actions) {");
      _indent(() -> {
        assign_("this.input", "input");
        assign_("this.inputSize", "input.length()");
        assign_("this.actions", "actions");
        assign_("this.offset", "0");
        assign_("this.cache", "new EnumMap<>(Label.class)");
        assign_("this.failure", "0");
        assign_("this.expected", "new ArrayList<>()");
      });
      put("}");

      nl();
      put("TreeNode parse() throws ParseException {");
      _indent(() -> {
        jump_("TreeNode tree", root);
        if_("tree != " + nullNode_() + " && offset == inputSize", () -> {
          return_("tree");
        },null);
        if_("expected.isEmpty()", () -> {
          assign_("failure", "offset");
          append_("expected", "\"<EOF>\"", null);
        },null);
        putSc("throw new ParseException(formatError(input, failure, expected),offset)");
      });
      put("}");

    });
    put("}"); // -> close for grammarModule()

    nl();
    put("public static TreeNode parse(String input, Actions actions) throws ParseException {");
    _indent(() -> {
      assign_("Parser parser", "new Parser(input, actions)");
      return_("parser.parse()");
    });
    put("}");

    nl();
    put("static String formatError(String input, int offset, List<String> expected) {");
    _indent(() -> {
      assign_("String[] lines", "input.split(\"\\n\")");
      putSc("int lineNo = 0, position = 0");
      put("while (position <= offset) {");
      _indent(() -> {
        putSc("position += lines[lineNo].length() + 1");
        putSc("lineNo += 1");
      });
      put("}");
      assign_("String message", "\"Line \" + lineNo + \": expected \" + expected + \"\\n\"");
      assign_("String line", "lines[lineNo - 1]");
      putSc("message += line + \"\\n\"");
      putSc("position -= line.length() + 1");
      put("while (position < offset) {");
      _indent(() -> {
        putSc("message += \" \"");
        putSc("position += 1");
      });
      put("}");
      return_("message + \"^\"");
    });
    put("}");

  }

  @Override
  public void exports_() {
    var names = labels.toArray();
    Arrays.sort(names);
    _newBuffer("Label");
    nl();
    put("public enum Label {");
    _indent(() -> {
      for (int i = 0, n = names.length; i < n; i++) {
        put(names[i] + (i < n - 1 ? "," : ""));
      }
    });
    put("}");
  }

  @Override
  public void class_(String name, String parent, Runnable block) {
    nl();
    put("static class " + name + " extends " + parent + " {");
    newBuilder(name, () -> _indent(block));
    put("}");
  }

  @Override
  public void constructor_(String[] args, Runnable block) {
    put(local.name + "(String text, int offset, List<TreeNode> elements) {");
    _indent(() -> {
      putSc("super(text, offset, elements)");
      block.run();
    });
    put("}");
  }

  @Override
  public void method_(String name, String[] args, Runnable block) {
    nl();
    put("TreeNode " + name + "() {");
    newBuilder(null, () -> _indent(block));
    put("}");
  }

  @Override
  public void cache_(String name, Consumer<String> block) {
    labels.add(name);

    var address = localVar_("address", nullNode_());
    var offset = localVar_("index", "offset");

    assign_("Map<Integer, CacheRecord> rule", "cache.get(Label." + name + ")");
    if_("rule == null", () -> {
      assign_("rule", "new HashMap<Integer, CacheRecord>()");
      putSc("cache.put(Label." + name + ", rule)");
    },null);
    if_("rule.containsKey(offset)",
      () -> {
        assign_(address, "rule.get(offset).node");
        assign_("offset", "rule.get(offset).tail");
      }, () -> {
        block.accept(address); // block.call(context, builder, address);
        putSc("rule.put(" + offset + ", new CacheRecord(" + address + ", offset))");
      }
    );
    return_(address);
  }

  @Override
  public void attributes_(Object ignore) {
    // not implemented
  }

  @Override
  public void attribute_(String name, String value) {
    labels.add(name);
    putSc("labelled.put(Label." + name + ", " + value + ")");
  }

  @Override
  public String localVar_(String name, String value) {
    var varName = local.varName(name);
    assign_(typeOf(name) + " " + varName, (value == null ? nullNode_() : value) );
    return varName;
  }

  @Override
  public String chunk_(int length) {
    var chunk = localVar_("chunk", null_());
    var input = "input";
    var of = "offset";
    if_(of + " < inputSize", () -> {
      putSc(chunk +
        " = " + input + ".substring(" + of + ", " +
        "Math.min(" + of + " + " + length + ", " +
        input + ".length()" + "))" );
    },null);
    return chunk;
  }

  @Override
  public void syntaxNode_(String address, String start, String end, String elements, String action, String nodeClass) {
    List<String> args;
    if (action != null) {
      action = "actions." + action;
      args   = list( "input", start, end );
      if (elements == null) elements = "null";
    } else {
      action = "new " + (nodeClass != null ? nodeClass : "TreeNode");
      args   = list( "input.substring(" + start + ", " + end + ")", start );
    }
    if (elements != null) args.add(elements);
    assign_(address, action + "(" + join(args,", ") + ")");
    assign_("offset", end);
  }

  @Override
  public void ifNode_(String address, Runnable then_, Runnable else_) {
    if_(address + " != " + nullNode_(), then_, else_);
  }

  @Override
  public void unlessNode_(String address, Runnable then_, Runnable else_) {
    if_(address + " == " + nullNode_(), then_, else_);
  }

  @Override
  public void ifNull_(String elements, Runnable then_, Runnable else_) {
    if_(elements + " == null", then_, else_);
  }

  @Override
  public void extendNode_(String address, String nodeType) {
    // if (!nodeType) return; // TODO
  }

  @Override
  public void failure_(String address, String expected) {
    assign_(address, nullNode_());
    if_("offset > failure", () -> {
      assign_("failure", "offset");
      assign_("expected", "new ArrayList<String>()");
    },null);
    if_("offset == failure", () -> {
      append_("expected", _quote(expected), null);
    },null);
  }

  @Override
  public void assign_(String name, String value) {
    Objects.nonNull(name);
    putSc(name + " = " + value);
  }

  @Override
  public void jump_(String address, String rule) {
    assign_(address, "_read_" + rule + "()");
  }

  @Override
  public void conditional_(String kwd, String condition, Runnable then_, Runnable else_) {
    put(kwd + " (" + condition + ") {");
    _indent(then_);
    if (else_ != null) {
      put("} else {");
      _indent(else_);
    }
    put("}");
  }

  @Override
  public void if_(String condition, Runnable then_, Runnable else_) {
    conditional_("if", condition, then_, else_);
  }

  @Override
  public void whileNotNull_(String expression, Runnable block) {
    conditional_("while", expression + " != " + nullNode_(), block, null);
  }

  @Override
  public String stringMatch_(String expression, String string) {
    return expression + " != null && " + expression + ".equals(" + _quote(string) + ")";
  }

  @Override
  public String stringMatchCI_(String expression, String string) {
     return expression + " != null && " + expression + ".toLowerCase().equals(" + _quote(string) + ".toLowerCase())";
  }

  @Override
  public String regexMatch_(String regex, String string) {
    return string + " != null && " + regex + ".matcher(" + string + ").matches()";
  }

  @Override
  public void return_(String expression) {
    putSc("return " + expression);
  }

  @Override
  public String arrayLookup_(String expression, int offset) {
    return expression + ".get(" + offset + ')';
  }

  @Override
  public void append_(String list, String value, String index) {
    if (index == null) {
      putSc(list + ".add(" + value + ')');
    } else {
      putSc(list + ".add(" + index + ", " + value + ')');
    }
  }

  @Override
  public void decrement_(String variable) {
    putSc("--" + variable);
  }

  @Override
  public String isZero_(String expression) {
    return expression + " <= 0";
  }

  @Override
  public String hasChars_() {
    return "offset < inputSize";
  }

  @Override
  public String nullNode_() {
    return "FAILURE";
  }

  @Override
  public String offset_() {
    return "offset";
  }

  @Override
  public String emptyList_(int size) {
    return "new ArrayList<TreeNode>" + (size > 0 ? "("+size+")" : "()" );
  }

  @Override
  public String emptyString_() {
    return "\"\"";
  }

  @Override
  public String true_() {
    return "new TreeNode(\"\", -1)";
  }

  @Override
  public String null_() {
    return "null";
  }

  static Map<String,String> map(String... pair) {
    var map = new HashMap<String,String>();
    int i = 0;
    while (i < pair.length) map.put(pair[i++],pair[i++]);
    return map;
  }

  static List<String> list(String... items) {
    var list = new ArrayList<String>();
    for (var s:items) list.add(s);
    return list;
  }

  static String join(List<String> items, String plus) {
    var b = new StringBuilder();
    for (var s:items) b.append(plus).append(s);
    return b.substring(plus.length());
  }

}
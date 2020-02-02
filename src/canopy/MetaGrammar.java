package canopy;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Iterator;
import java.util.regex.Pattern;
import java.text.ParseException;

public final class MetaGrammar {

public static class TreeNode implements Iterable<TreeNode> {
  public String text;
  public int offset;
  public List<TreeNode> nodes;

  Map<Label, TreeNode> labelled;

  public TreeNode() {
    this("", -1, new ArrayList<TreeNode>(0));
  }

  public TreeNode(String text, int offset) {
    this(text, offset, new ArrayList<TreeNode>(0));
  }

  public TreeNode(String text, int offset, List<TreeNode> elements) {
    this.text = text;
    this.offset = offset;
    this.nodes = elements;
    this.labelled = new EnumMap<Label, TreeNode>(Label.class);
  }

  public TreeNode get(Label key) {
    return labelled.get(key);
  }

  public Iterator<TreeNode> iterator() {
    return nodes.iterator();
  }
}

static class TreeNode1 extends TreeNode {
  TreeNode1(String text, int offset, List<TreeNode> elements) {
    super(text, offset, elements);
    labelled.put(Label.grammar_name, elements.get(1));
  }
}

static class TreeNode2 extends TreeNode {
  TreeNode2(String text, int offset, List<TreeNode> elements) {
    super(text, offset, elements);
    labelled.put(Label.rule, elements.get(1));
  }
}

static class TreeNode3 extends TreeNode {
  TreeNode3(String text, int offset, List<TreeNode> elements) {
    super(text, offset, elements);
    labelled.put(Label.id, elements.get(3));
    labelled.put(Label.object_identifier, elements.get(3));
  }
}

static class TreeNode4 extends TreeNode {
  TreeNode4(String text, int offset, List<TreeNode> elements) {
    super(text, offset, elements);
    labelled.put(Label.identifier, elements.get(0));
    labelled.put(Label.assignment, elements.get(1));
    labelled.put(Label.parsing_expression, elements.get(2));
  }
}

static class TreeNode5 extends TreeNode {
  TreeNode5(String text, int offset, List<TreeNode> elements) {
    super(text, offset, elements);
    labelled.put(Label.identifier, elements.get(0));
  }
}

static class TreeNode6 extends TreeNode {
  TreeNode6(String text, int offset, List<TreeNode> elements) {
    super(text, offset, elements);
    labelled.put(Label.identifier, elements.get(1));
  }
}

static class TreeNode7 extends TreeNode {
  TreeNode7(String text, int offset, List<TreeNode> elements) {
    super(text, offset, elements);
    labelled.put(Label.choice_part, elements.get(0));
  }
}

static class TreeNode8 extends TreeNode {
  TreeNode8(String text, int offset, List<TreeNode> elements) {
    super(text, offset, elements);
    labelled.put(Label.expr, elements.get(3));
    labelled.put(Label.choice_part, elements.get(3));
  }
}

static class TreeNode9 extends TreeNode {
  TreeNode9(String text, int offset, List<TreeNode> elements) {
    super(text, offset, elements);
    labelled.put(Label.actionable, elements.get(0));
    labelled.put(Label.action_tag, elements.get(2));
  }
}

static class TreeNode10 extends TreeNode {
  TreeNode10(String text, int offset, List<TreeNode> elements) {
    super(text, offset, elements);
    labelled.put(Label.actionable, elements.get(2));
  }
}

static class TreeNode11 extends TreeNode {
  TreeNode11(String text, int offset, List<TreeNode> elements) {
    super(text, offset, elements);
    labelled.put(Label.id, elements.get(1));
    labelled.put(Label.identifier, elements.get(1));
  }
}

static class TreeNode12 extends TreeNode {
  TreeNode12(String text, int offset, List<TreeNode> elements) {
    super(text, offset, elements);
    labelled.put(Label.typable, elements.get(0));
    labelled.put(Label.type_tag, elements.get(2));
  }
}

static class TreeNode13 extends TreeNode {
  TreeNode13(String text, int offset, List<TreeNode> elements) {
    super(text, offset, elements);
    labelled.put(Label.id, elements.get(1));
    labelled.put(Label.object_identifier, elements.get(1));
  }
}

static class TreeNode14 extends TreeNode {
  TreeNode14(String text, int offset, List<TreeNode> elements) {
    super(text, offset, elements);
    labelled.put(Label.sequence_part, elements.get(0));
  }
}

static class TreeNode15 extends TreeNode {
  TreeNode15(String text, int offset, List<TreeNode> elements) {
    super(text, offset, elements);
    labelled.put(Label.expr, elements.get(1));
    labelled.put(Label.sequence_part, elements.get(1));
  }
}

static class TreeNode16 extends TreeNode {
  TreeNode16(String text, int offset, List<TreeNode> elements) {
    super(text, offset, elements);
    labelled.put(Label.sequence_element, elements.get(2));
  }
}

static class TreeNode17 extends TreeNode {
  TreeNode17(String text, int offset, List<TreeNode> elements) {
    super(text, offset, elements);
    labelled.put(Label.id, elements.get(0));
    labelled.put(Label.identifier, elements.get(0));
  }
}

static class TreeNode18 extends TreeNode {
  TreeNode18(String text, int offset, List<TreeNode> elements) {
    super(text, offset, elements);
    labelled.put(Label.parsing_expression, elements.get(2));
  }
}

static class TreeNode19 extends TreeNode {
  TreeNode19(String text, int offset, List<TreeNode> elements) {
    super(text, offset, elements);
    labelled.put(Label.atom, elements.get(2));
  }
}

static class TreeNode20 extends TreeNode {
  TreeNode20(String text, int offset, List<TreeNode> elements) {
    super(text, offset, elements);
    labelled.put(Label.atom, elements.get(0));
  }
}

static class TreeNode21 extends TreeNode {
  TreeNode21(String text, int offset, List<TreeNode> elements) {
    super(text, offset, elements);
    labelled.put(Label.atom, elements.get(0));
  }
}

static class TreeNode22 extends TreeNode {
  TreeNode22(String text, int offset, List<TreeNode> elements) {
    super(text, offset, elements);
    labelled.put(Label.identifier, elements.get(0));
  }
}

static class CacheRecord {
  TreeNode node;
  int tail;

  CacheRecord(TreeNode node, int tail) {
    this.node = node;
    this.tail = tail;
  }
}

public interface Actions {
   TreeNode paren_expr(String input, int start, int end, List<TreeNode> elements);
   TreeNode char_class(String input, int start, int end, List<TreeNode> elements);
   TreeNode extension(String input, int start, int end, List<TreeNode> elements);
   TreeNode string(String input, int start, int end, List<TreeNode> elements);
   TreeNode maybe(String input, int start, int end, List<TreeNode> elements);
   TreeNode rule(String input, int start, int end, List<TreeNode> elements);
   TreeNode any_char(String input, int start, int end, List<TreeNode> elements);
   TreeNode reference(String input, int start, int end, List<TreeNode> elements);
   TreeNode sequence(String input, int start, int end, List<TreeNode> elements);
   TreeNode predicate(String input, int start, int end, List<TreeNode> elements);
   TreeNode grammar(String input, int start, int end, List<TreeNode> elements);
   TreeNode repeat(String input, int start, int end, List<TreeNode> elements);
   TreeNode action(String input, int start, int end, List<TreeNode> elements);
   TreeNode sequence_part(String input, int start, int end, List<TreeNode> elements);
   TreeNode choice(String input, int start, int end, List<TreeNode> elements);
   TreeNode ci_string(String input, int start, int end, List<TreeNode> elements);
}

static class Parser {
  static TreeNode FAILURE = new TreeNode();

  int inputSize, offset, failure;
  String input;
  List<String> expected;
  Map<Label, Map<Integer, CacheRecord>> cache;
  Actions actions;

  static Pattern REGEX_1 = Pattern.compile("\\A[ \\t\\n\\r]");
  static Pattern REGEX_2 = Pattern.compile("\\A[^\\n]");
  static Pattern REGEX_3 = Pattern.compile("\\A[a-zA-Z_]");
  static Pattern REGEX_4 = Pattern.compile("\\A[a-zA-Z0-9_]");
  static Pattern REGEX_5 = Pattern.compile("\\A[^\"]");
  static Pattern REGEX_6 = Pattern.compile("\\A[^']");
  static Pattern REGEX_7 = Pattern.compile("\\A[^`]");
  static Pattern REGEX_8 = Pattern.compile("\\A[^\\]]");

  TreeNode _read_grammar() {
    TreeNode address0 = FAILURE;
    int index0 = offset;
    Map<Integer, CacheRecord> rule = cache.get(Label.grammar);
    if (rule == null) {
      rule = new HashMap<Integer, CacheRecord>();
      cache.put(Label.grammar, rule);
    }
    if (rule.containsKey(offset)) {
      address0 = rule.get(offset).node;
      offset = rule.get(offset).tail;
    } else {
      int index1 = offset;
      List<TreeNode> elements0 = new ArrayList<TreeNode>(4);
      TreeNode address1 = FAILURE;
      int remaining0 = 0;
      int index2 = offset;
      List<TreeNode> elements1 = new ArrayList<TreeNode>();
      TreeNode address2 = new TreeNode("", -1);
      while (address2 != FAILURE) {
        address2 = _read__();
        if (address2 != FAILURE) {
          elements1.add(address2);
          --remaining0;
        }
      }
      if (remaining0 <= 0) {
        address1 = new TreeNode(input.substring(index2, offset), index2, elements1);
        offset = offset;
      } else {
        address1 = FAILURE;
      }
      if (address1 != FAILURE) {
        elements0.add(0, address1);
        TreeNode address3 = FAILURE;
        address3 = _read_grammar_name();
        if (address3 != FAILURE) {
          elements0.add(1, address3);
          TreeNode address4 = FAILURE;
          int remaining1 = 1;
          int index3 = offset;
          List<TreeNode> elements2 = new ArrayList<TreeNode>();
          TreeNode address5 = new TreeNode("", -1);
          while (address5 != FAILURE) {
            int index4 = offset;
            List<TreeNode> elements3 = new ArrayList<TreeNode>(2);
            TreeNode address6 = FAILURE;
            int remaining2 = 0;
            int index5 = offset;
            List<TreeNode> elements4 = new ArrayList<TreeNode>();
            TreeNode address7 = new TreeNode("", -1);
            while (address7 != FAILURE) {
              address7 = _read__();
              if (address7 != FAILURE) {
                elements4.add(address7);
                --remaining2;
              }
            }
            if (remaining2 <= 0) {
              address6 = new TreeNode(input.substring(index5, offset), index5, elements4);
              offset = offset;
            } else {
              address6 = FAILURE;
            }
            if (address6 != FAILURE) {
              elements3.add(0, address6);
              TreeNode address8 = FAILURE;
              address8 = _read_rule();
              if (address8 != FAILURE) {
                elements3.add(1, address8);
              } else {
                elements3 = null;
                offset = index4;
              }
            } else {
              elements3 = null;
              offset = index4;
            }
            if (elements3 == null) {
              address5 = FAILURE;
            } else {
              address5 = new TreeNode2(input.substring(index4, offset), index4, elements3);
              offset = offset;
            }
            if (address5 != FAILURE) {
              elements2.add(address5);
              --remaining1;
            }
          }
          if (remaining1 <= 0) {
            address4 = new TreeNode(input.substring(index3, offset), index3, elements2);
            offset = offset;
          } else {
            address4 = FAILURE;
          }
          if (address4 != FAILURE) {
            elements0.add(2, address4);
            TreeNode address9 = FAILURE;
            int remaining3 = 0;
            int index6 = offset;
            List<TreeNode> elements5 = new ArrayList<TreeNode>();
            TreeNode address10 = new TreeNode("", -1);
            while (address10 != FAILURE) {
              address10 = _read__();
              if (address10 != FAILURE) {
                elements5.add(address10);
                --remaining3;
              }
            }
            if (remaining3 <= 0) {
              address9 = new TreeNode(input.substring(index6, offset), index6, elements5);
              offset = offset;
            } else {
              address9 = FAILURE;
            }
            if (address9 != FAILURE) {
              elements0.add(3, address9);
            } else {
              elements0 = null;
              offset = index1;
            }
          } else {
            elements0 = null;
            offset = index1;
          }
        } else {
          elements0 = null;
          offset = index1;
        }
      } else {
        elements0 = null;
        offset = index1;
      }
      if (elements0 == null) {
        address0 = FAILURE;
      } else {
        address0 = actions.grammar(input, index1, offset, elements0);
        offset = offset;
      }
      rule.put(index0, new CacheRecord(address0, offset));
    }
    return address0;
  }

  TreeNode _read_grammar_name() {
    TreeNode address0 = FAILURE;
    int index0 = offset;
    Map<Integer, CacheRecord> rule = cache.get(Label.grammar_name);
    if (rule == null) {
      rule = new HashMap<Integer, CacheRecord>();
      cache.put(Label.grammar_name, rule);
    }
    if (rule.containsKey(offset)) {
      address0 = rule.get(offset).node;
      offset = rule.get(offset).tail;
    } else {
      int index1 = offset;
      List<TreeNode> elements0 = new ArrayList<TreeNode>(4);
      TreeNode address1 = FAILURE;
      String chunk0 = null;
      if (offset < inputSize) {
        chunk0 = input.substring(offset, Math.min(offset + 7, input.length()));
      }
      if (chunk0 != null && chunk0.toLowerCase().equals("grammar".toLowerCase())) {
        address1 = new TreeNode(input.substring(offset, offset + 7), offset);
        offset = offset + 7;
      } else {
        address1 = FAILURE;
        if (offset > failure) {
          failure = offset;
          expected = new ArrayList<String>();
        }
        if (offset == failure) {
          expected.add("`grammar`");
        }
      }
      if (address1 != FAILURE) {
        elements0.add(0, address1);
        TreeNode address2 = FAILURE;
        int index2 = offset;
        String chunk1 = null;
        if (offset < inputSize) {
          chunk1 = input.substring(offset, Math.min(offset + 1, input.length()));
        }
        if (chunk1 != null && chunk1.equals(":")) {
          address2 = new TreeNode(input.substring(offset, offset + 1), offset);
          offset = offset + 1;
        } else {
          address2 = FAILURE;
          if (offset > failure) {
            failure = offset;
            expected = new ArrayList<String>();
          }
          if (offset == failure) {
            expected.add("\":\"");
          }
        }
        if (address2 == FAILURE) {
          address2 = new TreeNode(input.substring(index2, index2), index2);
          offset = index2;
        }
        if (address2 != FAILURE) {
          elements0.add(1, address2);
          TreeNode address3 = FAILURE;
          int remaining0 = 1;
          int index3 = offset;
          List<TreeNode> elements1 = new ArrayList<TreeNode>();
          TreeNode address4 = new TreeNode("", -1);
          while (address4 != FAILURE) {
            address4 = _read__();
            if (address4 != FAILURE) {
              elements1.add(address4);
              --remaining0;
            }
          }
          if (remaining0 <= 0) {
            address3 = new TreeNode(input.substring(index3, offset), index3, elements1);
            offset = offset;
          } else {
            address3 = FAILURE;
          }
          if (address3 != FAILURE) {
            elements0.add(2, address3);
            TreeNode address5 = FAILURE;
            address5 = _read_object_identifier();
            if (address5 != FAILURE) {
              elements0.add(3, address5);
            } else {
              elements0 = null;
              offset = index1;
            }
          } else {
            elements0 = null;
            offset = index1;
          }
        } else {
          elements0 = null;
          offset = index1;
        }
      } else {
        elements0 = null;
        offset = index1;
      }
      if (elements0 == null) {
        address0 = FAILURE;
      } else {
        address0 = new TreeNode3(input.substring(index1, offset), index1, elements0);
        offset = offset;
      }
      rule.put(index0, new CacheRecord(address0, offset));
    }
    return address0;
  }

  TreeNode _read_rule() {
    TreeNode address0 = FAILURE;
    int index0 = offset;
    Map<Integer, CacheRecord> rule = cache.get(Label.rule);
    if (rule == null) {
      rule = new HashMap<Integer, CacheRecord>();
      cache.put(Label.rule, rule);
    }
    if (rule.containsKey(offset)) {
      address0 = rule.get(offset).node;
      offset = rule.get(offset).tail;
    } else {
      int index1 = offset;
      List<TreeNode> elements0 = new ArrayList<TreeNode>(3);
      TreeNode address1 = FAILURE;
      address1 = _read_identifier();
      if (address1 != FAILURE) {
        elements0.add(0, address1);
        TreeNode address2 = FAILURE;
        address2 = _read_assignment();
        if (address2 != FAILURE) {
          elements0.add(1, address2);
          TreeNode address3 = FAILURE;
          address3 = _read_parsing_expression();
          if (address3 != FAILURE) {
            elements0.add(2, address3);
          } else {
            elements0 = null;
            offset = index1;
          }
        } else {
          elements0 = null;
          offset = index1;
        }
      } else {
        elements0 = null;
        offset = index1;
      }
      if (elements0 == null) {
        address0 = FAILURE;
      } else {
        address0 = actions.rule(input, index1, offset, elements0);
        offset = offset;
      }
      rule.put(index0, new CacheRecord(address0, offset));
    }
    return address0;
  }

  TreeNode _read_assignment() {
    TreeNode address0 = FAILURE;
    int index0 = offset;
    Map<Integer, CacheRecord> rule = cache.get(Label.assignment);
    if (rule == null) {
      rule = new HashMap<Integer, CacheRecord>();
      cache.put(Label.assignment, rule);
    }
    if (rule.containsKey(offset)) {
      address0 = rule.get(offset).node;
      offset = rule.get(offset).tail;
    } else {
      int index1 = offset;
      List<TreeNode> elements0 = new ArrayList<TreeNode>(3);
      TreeNode address1 = FAILURE;
      int remaining0 = 1;
      int index2 = offset;
      List<TreeNode> elements1 = new ArrayList<TreeNode>();
      TreeNode address2 = new TreeNode("", -1);
      while (address2 != FAILURE) {
        address2 = _read__();
        if (address2 != FAILURE) {
          elements1.add(address2);
          --remaining0;
        }
      }
      if (remaining0 <= 0) {
        address1 = new TreeNode(input.substring(index2, offset), index2, elements1);
        offset = offset;
      } else {
        address1 = FAILURE;
      }
      if (address1 != FAILURE) {
        elements0.add(0, address1);
        TreeNode address3 = FAILURE;
        String chunk0 = null;
        if (offset < inputSize) {
          chunk0 = input.substring(offset, Math.min(offset + 2, input.length()));
        }
        if (chunk0 != null && chunk0.equals("<-")) {
          address3 = new TreeNode(input.substring(offset, offset + 2), offset);
          offset = offset + 2;
        } else {
          address3 = FAILURE;
          if (offset > failure) {
            failure = offset;
            expected = new ArrayList<String>();
          }
          if (offset == failure) {
            expected.add("\"<-\"");
          }
        }
        if (address3 != FAILURE) {
          elements0.add(1, address3);
          TreeNode address4 = FAILURE;
          int remaining1 = 1;
          int index3 = offset;
          List<TreeNode> elements2 = new ArrayList<TreeNode>();
          TreeNode address5 = new TreeNode("", -1);
          while (address5 != FAILURE) {
            address5 = _read__();
            if (address5 != FAILURE) {
              elements2.add(address5);
              --remaining1;
            }
          }
          if (remaining1 <= 0) {
            address4 = new TreeNode(input.substring(index3, offset), index3, elements2);
            offset = offset;
          } else {
            address4 = FAILURE;
          }
          if (address4 != FAILURE) {
            elements0.add(2, address4);
          } else {
            elements0 = null;
            offset = index1;
          }
        } else {
          elements0 = null;
          offset = index1;
        }
      } else {
        elements0 = null;
        offset = index1;
      }
      if (elements0 == null) {
        address0 = FAILURE;
      } else {
        address0 = new TreeNode(input.substring(index1, offset), index1, elements0);
        offset = offset;
      }
      rule.put(index0, new CacheRecord(address0, offset));
    }
    return address0;
  }

  TreeNode _read__() {
    TreeNode address0 = FAILURE;
    int index0 = offset;
    Map<Integer, CacheRecord> rule = cache.get(Label.__);
    if (rule == null) {
      rule = new HashMap<Integer, CacheRecord>();
      cache.put(Label.__, rule);
    }
    if (rule.containsKey(offset)) {
      address0 = rule.get(offset).node;
      offset = rule.get(offset).tail;
    } else {
      int index1 = offset;
      String chunk0 = null;
      if (offset < inputSize) {
        chunk0 = input.substring(offset, Math.min(offset + 1, input.length()));
      }
      if (chunk0 != null && REGEX_1.matcher(chunk0).matches()) {
        address0 = new TreeNode(input.substring(offset, offset + 1), offset);
        offset = offset + 1;
      } else {
        address0 = FAILURE;
        if (offset > failure) {
          failure = offset;
          expected = new ArrayList<String>();
        }
        if (offset == failure) {
          expected.add("[ \\t\\n\\r]");
        }
      }
      if (address0 == FAILURE) {
        offset = index1;
        address0 = _read_comment();
        if (address0 == FAILURE) {
          offset = index1;
        }
      }
      rule.put(index0, new CacheRecord(address0, offset));
    }
    return address0;
  }

  TreeNode _read_comment() {
    TreeNode address0 = FAILURE;
    int index0 = offset;
    Map<Integer, CacheRecord> rule = cache.get(Label.comment);
    if (rule == null) {
      rule = new HashMap<Integer, CacheRecord>();
      cache.put(Label.comment, rule);
    }
    if (rule.containsKey(offset)) {
      address0 = rule.get(offset).node;
      offset = rule.get(offset).tail;
    } else {
      int index1 = offset;
      List<TreeNode> elements0 = new ArrayList<TreeNode>(2);
      TreeNode address1 = FAILURE;
      String chunk0 = null;
      if (offset < inputSize) {
        chunk0 = input.substring(offset, Math.min(offset + 1, input.length()));
      }
      if (chunk0 != null && chunk0.equals("#")) {
        address1 = new TreeNode(input.substring(offset, offset + 1), offset);
        offset = offset + 1;
      } else {
        address1 = FAILURE;
        if (offset > failure) {
          failure = offset;
          expected = new ArrayList<String>();
        }
        if (offset == failure) {
          expected.add("\"#\"");
        }
      }
      if (address1 != FAILURE) {
        elements0.add(0, address1);
        TreeNode address2 = FAILURE;
        int remaining0 = 0;
        int index2 = offset;
        List<TreeNode> elements1 = new ArrayList<TreeNode>();
        TreeNode address3 = new TreeNode("", -1);
        while (address3 != FAILURE) {
          String chunk1 = null;
          if (offset < inputSize) {
            chunk1 = input.substring(offset, Math.min(offset + 1, input.length()));
          }
          if (chunk1 != null && REGEX_2.matcher(chunk1).matches()) {
            address3 = new TreeNode(input.substring(offset, offset + 1), offset);
            offset = offset + 1;
          } else {
            address3 = FAILURE;
            if (offset > failure) {
              failure = offset;
              expected = new ArrayList<String>();
            }
            if (offset == failure) {
              expected.add("[^\\n]");
            }
          }
          if (address3 != FAILURE) {
            elements1.add(address3);
            --remaining0;
          }
        }
        if (remaining0 <= 0) {
          address2 = new TreeNode(input.substring(index2, offset), index2, elements1);
          offset = offset;
        } else {
          address2 = FAILURE;
        }
        if (address2 != FAILURE) {
          elements0.add(1, address2);
        } else {
          elements0 = null;
          offset = index1;
        }
      } else {
        elements0 = null;
        offset = index1;
      }
      if (elements0 == null) {
        address0 = FAILURE;
      } else {
        address0 = new TreeNode(input.substring(index1, offset), index1, elements0);
        offset = offset;
      }
      rule.put(index0, new CacheRecord(address0, offset));
    }
    return address0;
  }

  TreeNode _read_object_identifier() {
    TreeNode address0 = FAILURE;
    int index0 = offset;
    Map<Integer, CacheRecord> rule = cache.get(Label.object_identifier);
    if (rule == null) {
      rule = new HashMap<Integer, CacheRecord>();
      cache.put(Label.object_identifier, rule);
    }
    if (rule.containsKey(offset)) {
      address0 = rule.get(offset).node;
      offset = rule.get(offset).tail;
    } else {
      int index1 = offset;
      List<TreeNode> elements0 = new ArrayList<TreeNode>(2);
      TreeNode address1 = FAILURE;
      address1 = _read_identifier();
      if (address1 != FAILURE) {
        elements0.add(0, address1);
        TreeNode address2 = FAILURE;
        int remaining0 = 0;
        int index2 = offset;
        List<TreeNode> elements1 = new ArrayList<TreeNode>();
        TreeNode address3 = new TreeNode("", -1);
        while (address3 != FAILURE) {
          int index3 = offset;
          List<TreeNode> elements2 = new ArrayList<TreeNode>(2);
          TreeNode address4 = FAILURE;
          String chunk0 = null;
          if (offset < inputSize) {
            chunk0 = input.substring(offset, Math.min(offset + 1, input.length()));
          }
          if (chunk0 != null && chunk0.equals(".")) {
            address4 = new TreeNode(input.substring(offset, offset + 1), offset);
            offset = offset + 1;
          } else {
            address4 = FAILURE;
            if (offset > failure) {
              failure = offset;
              expected = new ArrayList<String>();
            }
            if (offset == failure) {
              expected.add("\".\"");
            }
          }
          if (address4 != FAILURE) {
            elements2.add(0, address4);
            TreeNode address5 = FAILURE;
            address5 = _read_identifier();
            if (address5 != FAILURE) {
              elements2.add(1, address5);
            } else {
              elements2 = null;
              offset = index3;
            }
          } else {
            elements2 = null;
            offset = index3;
          }
          if (elements2 == null) {
            address3 = FAILURE;
          } else {
            address3 = new TreeNode6(input.substring(index3, offset), index3, elements2);
            offset = offset;
          }
          if (address3 != FAILURE) {
            elements1.add(address3);
            --remaining0;
          }
        }
        if (remaining0 <= 0) {
          address2 = new TreeNode(input.substring(index2, offset), index2, elements1);
          offset = offset;
        } else {
          address2 = FAILURE;
        }
        if (address2 != FAILURE) {
          elements0.add(1, address2);
        } else {
          elements0 = null;
          offset = index1;
        }
      } else {
        elements0 = null;
        offset = index1;
      }
      if (elements0 == null) {
        address0 = FAILURE;
      } else {
        address0 = new TreeNode5(input.substring(index1, offset), index1, elements0);
        offset = offset;
      }
      rule.put(index0, new CacheRecord(address0, offset));
    }
    return address0;
  }

  TreeNode _read_identifier() {
    TreeNode address0 = FAILURE;
    int index0 = offset;
    Map<Integer, CacheRecord> rule = cache.get(Label.identifier);
    if (rule == null) {
      rule = new HashMap<Integer, CacheRecord>();
      cache.put(Label.identifier, rule);
    }
    if (rule.containsKey(offset)) {
      address0 = rule.get(offset).node;
      offset = rule.get(offset).tail;
    } else {
      int index1 = offset;
      List<TreeNode> elements0 = new ArrayList<TreeNode>(2);
      TreeNode address1 = FAILURE;
      String chunk0 = null;
      if (offset < inputSize) {
        chunk0 = input.substring(offset, Math.min(offset + 1, input.length()));
      }
      if (chunk0 != null && REGEX_3.matcher(chunk0).matches()) {
        address1 = new TreeNode(input.substring(offset, offset + 1), offset);
        offset = offset + 1;
      } else {
        address1 = FAILURE;
        if (offset > failure) {
          failure = offset;
          expected = new ArrayList<String>();
        }
        if (offset == failure) {
          expected.add("[a-zA-Z_]");
        }
      }
      if (address1 != FAILURE) {
        elements0.add(0, address1);
        TreeNode address2 = FAILURE;
        int remaining0 = 0;
        int index2 = offset;
        List<TreeNode> elements1 = new ArrayList<TreeNode>();
        TreeNode address3 = new TreeNode("", -1);
        while (address3 != FAILURE) {
          String chunk1 = null;
          if (offset < inputSize) {
            chunk1 = input.substring(offset, Math.min(offset + 1, input.length()));
          }
          if (chunk1 != null && REGEX_4.matcher(chunk1).matches()) {
            address3 = new TreeNode(input.substring(offset, offset + 1), offset);
            offset = offset + 1;
          } else {
            address3 = FAILURE;
            if (offset > failure) {
              failure = offset;
              expected = new ArrayList<String>();
            }
            if (offset == failure) {
              expected.add("[a-zA-Z0-9_]");
            }
          }
          if (address3 != FAILURE) {
            elements1.add(address3);
            --remaining0;
          }
        }
        if (remaining0 <= 0) {
          address2 = new TreeNode(input.substring(index2, offset), index2, elements1);
          offset = offset;
        } else {
          address2 = FAILURE;
        }
        if (address2 != FAILURE) {
          elements0.add(1, address2);
        } else {
          elements0 = null;
          offset = index1;
        }
      } else {
        elements0 = null;
        offset = index1;
      }
      if (elements0 == null) {
        address0 = FAILURE;
      } else {
        address0 = new TreeNode(input.substring(index1, offset), index1, elements0);
        offset = offset;
      }
      rule.put(index0, new CacheRecord(address0, offset));
    }
    return address0;
  }

  TreeNode _read_parsing_expression() {
    TreeNode address0 = FAILURE;
    int index0 = offset;
    Map<Integer, CacheRecord> rule = cache.get(Label.parsing_expression);
    if (rule == null) {
      rule = new HashMap<Integer, CacheRecord>();
      cache.put(Label.parsing_expression, rule);
    }
    if (rule.containsKey(offset)) {
      address0 = rule.get(offset).node;
      offset = rule.get(offset).tail;
    } else {
      int index1 = offset;
      address0 = _read_choice();
      if (address0 == FAILURE) {
        offset = index1;
        address0 = _read_choice_part();
        if (address0 == FAILURE) {
          offset = index1;
        }
      }
      rule.put(index0, new CacheRecord(address0, offset));
    }
    return address0;
  }

  TreeNode _read_choice_part() {
    TreeNode address0 = FAILURE;
    int index0 = offset;
    Map<Integer, CacheRecord> rule = cache.get(Label.choice_part);
    if (rule == null) {
      rule = new HashMap<Integer, CacheRecord>();
      cache.put(Label.choice_part, rule);
    }
    if (rule.containsKey(offset)) {
      address0 = rule.get(offset).node;
      offset = rule.get(offset).tail;
    } else {
      int index1 = offset;
      address0 = _read_action_expression();
      if (address0 == FAILURE) {
        offset = index1;
        address0 = _read_typed_expression();
        if (address0 == FAILURE) {
          offset = index1;
          address0 = _read_sequence();
          if (address0 == FAILURE) {
            offset = index1;
            address0 = _read_sequence_element();
            if (address0 == FAILURE) {
              offset = index1;
            }
          }
        }
      }
      rule.put(index0, new CacheRecord(address0, offset));
    }
    return address0;
  }

  TreeNode _read_sequence_element() {
    TreeNode address0 = FAILURE;
    int index0 = offset;
    Map<Integer, CacheRecord> rule = cache.get(Label.sequence_element);
    if (rule == null) {
      rule = new HashMap<Integer, CacheRecord>();
      cache.put(Label.sequence_element, rule);
    }
    if (rule.containsKey(offset)) {
      address0 = rule.get(offset).node;
      offset = rule.get(offset).tail;
    } else {
      int index1 = offset;
      address0 = _read_predicated_atom();
      if (address0 == FAILURE) {
        offset = index1;
        address0 = _read_repeated_atom();
        if (address0 == FAILURE) {
          offset = index1;
          address0 = _read_maybe_atom();
          if (address0 == FAILURE) {
            offset = index1;
            address0 = _read_atom();
            if (address0 == FAILURE) {
              offset = index1;
            }
          }
        }
      }
      rule.put(index0, new CacheRecord(address0, offset));
    }
    return address0;
  }

  TreeNode _read_atom() {
    TreeNode address0 = FAILURE;
    int index0 = offset;
    Map<Integer, CacheRecord> rule = cache.get(Label.atom);
    if (rule == null) {
      rule = new HashMap<Integer, CacheRecord>();
      cache.put(Label.atom, rule);
    }
    if (rule.containsKey(offset)) {
      address0 = rule.get(offset).node;
      offset = rule.get(offset).tail;
    } else {
      int index1 = offset;
      address0 = _read_reference();
      if (address0 == FAILURE) {
        offset = index1;
        address0 = _read_terminal();
        if (address0 == FAILURE) {
          offset = index1;
          address0 = _read_paren_expression();
          if (address0 == FAILURE) {
            offset = index1;
          }
        }
      }
      rule.put(index0, new CacheRecord(address0, offset));
    }
    return address0;
  }

  TreeNode _read_terminal() {
    TreeNode address0 = FAILURE;
    int index0 = offset;
    Map<Integer, CacheRecord> rule = cache.get(Label.terminal);
    if (rule == null) {
      rule = new HashMap<Integer, CacheRecord>();
      cache.put(Label.terminal, rule);
    }
    if (rule.containsKey(offset)) {
      address0 = rule.get(offset).node;
      offset = rule.get(offset).tail;
    } else {
      int index1 = offset;
      address0 = _read_literal_string();
      if (address0 == FAILURE) {
        offset = index1;
        address0 = _read_ci_string();
        if (address0 == FAILURE) {
          offset = index1;
          address0 = _read_char_class();
          if (address0 == FAILURE) {
            offset = index1;
            address0 = _read_any_char();
            if (address0 == FAILURE) {
              offset = index1;
            }
          }
        }
      }
      rule.put(index0, new CacheRecord(address0, offset));
    }
    return address0;
  }

  TreeNode _read_choice() {
    TreeNode address0 = FAILURE;
    int index0 = offset;
    Map<Integer, CacheRecord> rule = cache.get(Label.choice);
    if (rule == null) {
      rule = new HashMap<Integer, CacheRecord>();
      cache.put(Label.choice, rule);
    }
    if (rule.containsKey(offset)) {
      address0 = rule.get(offset).node;
      offset = rule.get(offset).tail;
    } else {
      int index1 = offset;
      List<TreeNode> elements0 = new ArrayList<TreeNode>(2);
      TreeNode address1 = FAILURE;
      address1 = _read_choice_part();
      if (address1 != FAILURE) {
        elements0.add(0, address1);
        TreeNode address2 = FAILURE;
        int remaining0 = 1;
        int index2 = offset;
        List<TreeNode> elements1 = new ArrayList<TreeNode>();
        TreeNode address3 = new TreeNode("", -1);
        while (address3 != FAILURE) {
          int index3 = offset;
          List<TreeNode> elements2 = new ArrayList<TreeNode>(4);
          TreeNode address4 = FAILURE;
          int remaining1 = 0;
          int index4 = offset;
          List<TreeNode> elements3 = new ArrayList<TreeNode>();
          TreeNode address5 = new TreeNode("", -1);
          while (address5 != FAILURE) {
            address5 = _read__();
            if (address5 != FAILURE) {
              elements3.add(address5);
              --remaining1;
            }
          }
          if (remaining1 <= 0) {
            address4 = new TreeNode(input.substring(index4, offset), index4, elements3);
            offset = offset;
          } else {
            address4 = FAILURE;
          }
          if (address4 != FAILURE) {
            elements2.add(0, address4);
            TreeNode address6 = FAILURE;
            String chunk0 = null;
            if (offset < inputSize) {
              chunk0 = input.substring(offset, Math.min(offset + 1, input.length()));
            }
            if (chunk0 != null && chunk0.equals("/")) {
              address6 = new TreeNode(input.substring(offset, offset + 1), offset);
              offset = offset + 1;
            } else {
              address6 = FAILURE;
              if (offset > failure) {
                failure = offset;
                expected = new ArrayList<String>();
              }
              if (offset == failure) {
                expected.add("\"/\"");
              }
            }
            if (address6 != FAILURE) {
              elements2.add(1, address6);
              TreeNode address7 = FAILURE;
              int remaining2 = 0;
              int index5 = offset;
              List<TreeNode> elements4 = new ArrayList<TreeNode>();
              TreeNode address8 = new TreeNode("", -1);
              while (address8 != FAILURE) {
                address8 = _read__();
                if (address8 != FAILURE) {
                  elements4.add(address8);
                  --remaining2;
                }
              }
              if (remaining2 <= 0) {
                address7 = new TreeNode(input.substring(index5, offset), index5, elements4);
                offset = offset;
              } else {
                address7 = FAILURE;
              }
              if (address7 != FAILURE) {
                elements2.add(2, address7);
                TreeNode address9 = FAILURE;
                address9 = _read_choice_part();
                if (address9 != FAILURE) {
                  elements2.add(3, address9);
                } else {
                  elements2 = null;
                  offset = index3;
                }
              } else {
                elements2 = null;
                offset = index3;
              }
            } else {
              elements2 = null;
              offset = index3;
            }
          } else {
            elements2 = null;
            offset = index3;
          }
          if (elements2 == null) {
            address3 = FAILURE;
          } else {
            address3 = new TreeNode8(input.substring(index3, offset), index3, elements2);
            offset = offset;
          }
          if (address3 != FAILURE) {
            elements1.add(address3);
            --remaining0;
          }
        }
        if (remaining0 <= 0) {
          address2 = new TreeNode(input.substring(index2, offset), index2, elements1);
          offset = offset;
        } else {
          address2 = FAILURE;
        }
        if (address2 != FAILURE) {
          elements0.add(1, address2);
        } else {
          elements0 = null;
          offset = index1;
        }
      } else {
        elements0 = null;
        offset = index1;
      }
      if (elements0 == null) {
        address0 = FAILURE;
      } else {
        address0 = actions.choice(input, index1, offset, elements0);
        offset = offset;
      }
      rule.put(index0, new CacheRecord(address0, offset));
    }
    return address0;
  }

  TreeNode _read_action_expression() {
    TreeNode address0 = FAILURE;
    int index0 = offset;
    Map<Integer, CacheRecord> rule = cache.get(Label.action_expression);
    if (rule == null) {
      rule = new HashMap<Integer, CacheRecord>();
      cache.put(Label.action_expression, rule);
    }
    if (rule.containsKey(offset)) {
      address0 = rule.get(offset).node;
      offset = rule.get(offset).tail;
    } else {
      int index1 = offset;
      List<TreeNode> elements0 = new ArrayList<TreeNode>(3);
      TreeNode address1 = FAILURE;
      address1 = _read_actionable();
      if (address1 != FAILURE) {
        elements0.add(0, address1);
        TreeNode address2 = FAILURE;
        int remaining0 = 1;
        int index2 = offset;
        List<TreeNode> elements1 = new ArrayList<TreeNode>();
        TreeNode address3 = new TreeNode("", -1);
        while (address3 != FAILURE) {
          address3 = _read__();
          if (address3 != FAILURE) {
            elements1.add(address3);
            --remaining0;
          }
        }
        if (remaining0 <= 0) {
          address2 = new TreeNode(input.substring(index2, offset), index2, elements1);
          offset = offset;
        } else {
          address2 = FAILURE;
        }
        if (address2 != FAILURE) {
          elements0.add(1, address2);
          TreeNode address4 = FAILURE;
          address4 = _read_action_tag();
          if (address4 != FAILURE) {
            elements0.add(2, address4);
          } else {
            elements0 = null;
            offset = index1;
          }
        } else {
          elements0 = null;
          offset = index1;
        }
      } else {
        elements0 = null;
        offset = index1;
      }
      if (elements0 == null) {
        address0 = FAILURE;
      } else {
        address0 = actions.action(input, index1, offset, elements0);
        offset = offset;
      }
      rule.put(index0, new CacheRecord(address0, offset));
    }
    return address0;
  }

  TreeNode _read_actionable() {
    TreeNode address0 = FAILURE;
    int index0 = offset;
    Map<Integer, CacheRecord> rule = cache.get(Label.actionable);
    if (rule == null) {
      rule = new HashMap<Integer, CacheRecord>();
      cache.put(Label.actionable, rule);
    }
    if (rule.containsKey(offset)) {
      address0 = rule.get(offset).node;
      offset = rule.get(offset).tail;
    } else {
      int index1 = offset;
      address0 = _read_sequence();
      if (address0 == FAILURE) {
        offset = index1;
        address0 = _read_repeated_atom();
        if (address0 == FAILURE) {
          offset = index1;
          address0 = _read_terminal();
          if (address0 == FAILURE) {
            offset = index1;
            int index2 = offset;
            List<TreeNode> elements0 = new ArrayList<TreeNode>(5);
            TreeNode address1 = FAILURE;
            String chunk0 = null;
            if (offset < inputSize) {
              chunk0 = input.substring(offset, Math.min(offset + 1, input.length()));
            }
            if (chunk0 != null && chunk0.equals("(")) {
              address1 = new TreeNode(input.substring(offset, offset + 1), offset);
              offset = offset + 1;
            } else {
              address1 = FAILURE;
              if (offset > failure) {
                failure = offset;
                expected = new ArrayList<String>();
              }
              if (offset == failure) {
                expected.add("\"(\"");
              }
            }
            if (address1 != FAILURE) {
              elements0.add(0, address1);
              TreeNode address2 = FAILURE;
              int remaining0 = 0;
              int index3 = offset;
              List<TreeNode> elements1 = new ArrayList<TreeNode>();
              TreeNode address3 = new TreeNode("", -1);
              while (address3 != FAILURE) {
                address3 = _read__();
                if (address3 != FAILURE) {
                  elements1.add(address3);
                  --remaining0;
                }
              }
              if (remaining0 <= 0) {
                address2 = new TreeNode(input.substring(index3, offset), index3, elements1);
                offset = offset;
              } else {
                address2 = FAILURE;
              }
              if (address2 != FAILURE) {
                elements0.add(1, address2);
                TreeNode address4 = FAILURE;
                address4 = _read_actionable();
                if (address4 != FAILURE) {
                  elements0.add(2, address4);
                  TreeNode address5 = FAILURE;
                  int remaining1 = 0;
                  int index4 = offset;
                  List<TreeNode> elements2 = new ArrayList<TreeNode>();
                  TreeNode address6 = new TreeNode("", -1);
                  while (address6 != FAILURE) {
                    address6 = _read__();
                    if (address6 != FAILURE) {
                      elements2.add(address6);
                      --remaining1;
                    }
                  }
                  if (remaining1 <= 0) {
                    address5 = new TreeNode(input.substring(index4, offset), index4, elements2);
                    offset = offset;
                  } else {
                    address5 = FAILURE;
                  }
                  if (address5 != FAILURE) {
                    elements0.add(3, address5);
                    TreeNode address7 = FAILURE;
                    String chunk1 = null;
                    if (offset < inputSize) {
                      chunk1 = input.substring(offset, Math.min(offset + 1, input.length()));
                    }
                    if (chunk1 != null && chunk1.equals(")")) {
                      address7 = new TreeNode(input.substring(offset, offset + 1), offset);
                      offset = offset + 1;
                    } else {
                      address7 = FAILURE;
                      if (offset > failure) {
                        failure = offset;
                        expected = new ArrayList<String>();
                      }
                      if (offset == failure) {
                        expected.add("\")\"");
                      }
                    }
                    if (address7 != FAILURE) {
                      elements0.add(4, address7);
                    } else {
                      elements0 = null;
                      offset = index2;
                    }
                  } else {
                    elements0 = null;
                    offset = index2;
                  }
                } else {
                  elements0 = null;
                  offset = index2;
                }
              } else {
                elements0 = null;
                offset = index2;
              }
            } else {
              elements0 = null;
              offset = index2;
            }
            if (elements0 == null) {
              address0 = FAILURE;
            } else {
              address0 = actions.paren_expr(input, index2, offset, elements0);
              offset = offset;
            }
            if (address0 == FAILURE) {
              offset = index1;
            }
          }
        }
      }
      rule.put(index0, new CacheRecord(address0, offset));
    }
    return address0;
  }

  TreeNode _read_action_tag() {
    TreeNode address0 = FAILURE;
    int index0 = offset;
    Map<Integer, CacheRecord> rule = cache.get(Label.action_tag);
    if (rule == null) {
      rule = new HashMap<Integer, CacheRecord>();
      cache.put(Label.action_tag, rule);
    }
    if (rule.containsKey(offset)) {
      address0 = rule.get(offset).node;
      offset = rule.get(offset).tail;
    } else {
      int index1 = offset;
      List<TreeNode> elements0 = new ArrayList<TreeNode>(2);
      TreeNode address1 = FAILURE;
      String chunk0 = null;
      if (offset < inputSize) {
        chunk0 = input.substring(offset, Math.min(offset + 1, input.length()));
      }
      if (chunk0 != null && chunk0.equals("%")) {
        address1 = new TreeNode(input.substring(offset, offset + 1), offset);
        offset = offset + 1;
      } else {
        address1 = FAILURE;
        if (offset > failure) {
          failure = offset;
          expected = new ArrayList<String>();
        }
        if (offset == failure) {
          expected.add("\"%\"");
        }
      }
      if (address1 != FAILURE) {
        elements0.add(0, address1);
        TreeNode address2 = FAILURE;
        address2 = _read_identifier();
        if (address2 != FAILURE) {
          elements0.add(1, address2);
        } else {
          elements0 = null;
          offset = index1;
        }
      } else {
        elements0 = null;
        offset = index1;
      }
      if (elements0 == null) {
        address0 = FAILURE;
      } else {
        address0 = new TreeNode11(input.substring(index1, offset), index1, elements0);
        offset = offset;
      }
      rule.put(index0, new CacheRecord(address0, offset));
    }
    return address0;
  }

  TreeNode _read_typed_expression() {
    TreeNode address0 = FAILURE;
    int index0 = offset;
    Map<Integer, CacheRecord> rule = cache.get(Label.typed_expression);
    if (rule == null) {
      rule = new HashMap<Integer, CacheRecord>();
      cache.put(Label.typed_expression, rule);
    }
    if (rule.containsKey(offset)) {
      address0 = rule.get(offset).node;
      offset = rule.get(offset).tail;
    } else {
      int index1 = offset;
      List<TreeNode> elements0 = new ArrayList<TreeNode>(3);
      TreeNode address1 = FAILURE;
      address1 = _read_typable();
      if (address1 != FAILURE) {
        elements0.add(0, address1);
        TreeNode address2 = FAILURE;
        int remaining0 = 1;
        int index2 = offset;
        List<TreeNode> elements1 = new ArrayList<TreeNode>();
        TreeNode address3 = new TreeNode("", -1);
        while (address3 != FAILURE) {
          address3 = _read__();
          if (address3 != FAILURE) {
            elements1.add(address3);
            --remaining0;
          }
        }
        if (remaining0 <= 0) {
          address2 = new TreeNode(input.substring(index2, offset), index2, elements1);
          offset = offset;
        } else {
          address2 = FAILURE;
        }
        if (address2 != FAILURE) {
          elements0.add(1, address2);
          TreeNode address4 = FAILURE;
          address4 = _read_type_tag();
          if (address4 != FAILURE) {
            elements0.add(2, address4);
          } else {
            elements0 = null;
            offset = index1;
          }
        } else {
          elements0 = null;
          offset = index1;
        }
      } else {
        elements0 = null;
        offset = index1;
      }
      if (elements0 == null) {
        address0 = FAILURE;
      } else {
        address0 = actions.extension(input, index1, offset, elements0);
        offset = offset;
      }
      rule.put(index0, new CacheRecord(address0, offset));
    }
    return address0;
  }

  TreeNode _read_typable() {
    TreeNode address0 = FAILURE;
    int index0 = offset;
    Map<Integer, CacheRecord> rule = cache.get(Label.typable);
    if (rule == null) {
      rule = new HashMap<Integer, CacheRecord>();
      cache.put(Label.typable, rule);
    }
    if (rule.containsKey(offset)) {
      address0 = rule.get(offset).node;
      offset = rule.get(offset).tail;
    } else {
      int index1 = offset;
      address0 = _read_sequence();
      if (address0 == FAILURE) {
        offset = index1;
        address0 = _read_sequence_element();
        if (address0 == FAILURE) {
          offset = index1;
        }
      }
      rule.put(index0, new CacheRecord(address0, offset));
    }
    return address0;
  }

  TreeNode _read_type_tag() {
    TreeNode address0 = FAILURE;
    int index0 = offset;
    Map<Integer, CacheRecord> rule = cache.get(Label.type_tag);
    if (rule == null) {
      rule = new HashMap<Integer, CacheRecord>();
      cache.put(Label.type_tag, rule);
    }
    if (rule.containsKey(offset)) {
      address0 = rule.get(offset).node;
      offset = rule.get(offset).tail;
    } else {
      int index1 = offset;
      List<TreeNode> elements0 = new ArrayList<TreeNode>(3);
      TreeNode address1 = FAILURE;
      String chunk0 = null;
      if (offset < inputSize) {
        chunk0 = input.substring(offset, Math.min(offset + 1, input.length()));
      }
      if (chunk0 != null && chunk0.equals("<")) {
        address1 = new TreeNode(input.substring(offset, offset + 1), offset);
        offset = offset + 1;
      } else {
        address1 = FAILURE;
        if (offset > failure) {
          failure = offset;
          expected = new ArrayList<String>();
        }
        if (offset == failure) {
          expected.add("\"<\"");
        }
      }
      if (address1 != FAILURE) {
        elements0.add(0, address1);
        TreeNode address2 = FAILURE;
        address2 = _read_object_identifier();
        if (address2 != FAILURE) {
          elements0.add(1, address2);
          TreeNode address3 = FAILURE;
          String chunk1 = null;
          if (offset < inputSize) {
            chunk1 = input.substring(offset, Math.min(offset + 1, input.length()));
          }
          if (chunk1 != null && chunk1.equals(">")) {
            address3 = new TreeNode(input.substring(offset, offset + 1), offset);
            offset = offset + 1;
          } else {
            address3 = FAILURE;
            if (offset > failure) {
              failure = offset;
              expected = new ArrayList<String>();
            }
            if (offset == failure) {
              expected.add("\">\"");
            }
          }
          if (address3 != FAILURE) {
            elements0.add(2, address3);
          } else {
            elements0 = null;
            offset = index1;
          }
        } else {
          elements0 = null;
          offset = index1;
        }
      } else {
        elements0 = null;
        offset = index1;
      }
      if (elements0 == null) {
        address0 = FAILURE;
      } else {
        address0 = new TreeNode13(input.substring(index1, offset), index1, elements0);
        offset = offset;
      }
      rule.put(index0, new CacheRecord(address0, offset));
    }
    return address0;
  }

  TreeNode _read_sequence() {
    TreeNode address0 = FAILURE;
    int index0 = offset;
    Map<Integer, CacheRecord> rule = cache.get(Label.sequence);
    if (rule == null) {
      rule = new HashMap<Integer, CacheRecord>();
      cache.put(Label.sequence, rule);
    }
    if (rule.containsKey(offset)) {
      address0 = rule.get(offset).node;
      offset = rule.get(offset).tail;
    } else {
      int index1 = offset;
      List<TreeNode> elements0 = new ArrayList<TreeNode>(2);
      TreeNode address1 = FAILURE;
      address1 = _read_sequence_part();
      if (address1 != FAILURE) {
        elements0.add(0, address1);
        TreeNode address2 = FAILURE;
        int remaining0 = 1;
        int index2 = offset;
        List<TreeNode> elements1 = new ArrayList<TreeNode>();
        TreeNode address3 = new TreeNode("", -1);
        while (address3 != FAILURE) {
          int index3 = offset;
          List<TreeNode> elements2 = new ArrayList<TreeNode>(2);
          TreeNode address4 = FAILURE;
          int remaining1 = 1;
          int index4 = offset;
          List<TreeNode> elements3 = new ArrayList<TreeNode>();
          TreeNode address5 = new TreeNode("", -1);
          while (address5 != FAILURE) {
            address5 = _read__();
            if (address5 != FAILURE) {
              elements3.add(address5);
              --remaining1;
            }
          }
          if (remaining1 <= 0) {
            address4 = new TreeNode(input.substring(index4, offset), index4, elements3);
            offset = offset;
          } else {
            address4 = FAILURE;
          }
          if (address4 != FAILURE) {
            elements2.add(0, address4);
            TreeNode address6 = FAILURE;
            address6 = _read_sequence_part();
            if (address6 != FAILURE) {
              elements2.add(1, address6);
            } else {
              elements2 = null;
              offset = index3;
            }
          } else {
            elements2 = null;
            offset = index3;
          }
          if (elements2 == null) {
            address3 = FAILURE;
          } else {
            address3 = new TreeNode15(input.substring(index3, offset), index3, elements2);
            offset = offset;
          }
          if (address3 != FAILURE) {
            elements1.add(address3);
            --remaining0;
          }
        }
        if (remaining0 <= 0) {
          address2 = new TreeNode(input.substring(index2, offset), index2, elements1);
          offset = offset;
        } else {
          address2 = FAILURE;
        }
        if (address2 != FAILURE) {
          elements0.add(1, address2);
        } else {
          elements0 = null;
          offset = index1;
        }
      } else {
        elements0 = null;
        offset = index1;
      }
      if (elements0 == null) {
        address0 = FAILURE;
      } else {
        address0 = actions.sequence(input, index1, offset, elements0);
        offset = offset;
      }
      rule.put(index0, new CacheRecord(address0, offset));
    }
    return address0;
  }

  TreeNode _read_sequence_part() {
    TreeNode address0 = FAILURE;
    int index0 = offset;
    Map<Integer, CacheRecord> rule = cache.get(Label.sequence_part);
    if (rule == null) {
      rule = new HashMap<Integer, CacheRecord>();
      cache.put(Label.sequence_part, rule);
    }
    if (rule.containsKey(offset)) {
      address0 = rule.get(offset).node;
      offset = rule.get(offset).tail;
    } else {
      int index1 = offset;
      List<TreeNode> elements0 = new ArrayList<TreeNode>(3);
      TreeNode address1 = FAILURE;
      int index2 = offset;
      address1 = _read_mute();
      if (address1 == FAILURE) {
        address1 = new TreeNode(input.substring(index2, index2), index2);
        offset = index2;
      }
      if (address1 != FAILURE) {
        elements0.add(0, address1);
        TreeNode address2 = FAILURE;
        int index3 = offset;
        address2 = _read_label();
        if (address2 == FAILURE) {
          address2 = new TreeNode(input.substring(index3, index3), index3);
          offset = index3;
        }
        if (address2 != FAILURE) {
          elements0.add(1, address2);
          TreeNode address3 = FAILURE;
          address3 = _read_sequence_element();
          if (address3 != FAILURE) {
            elements0.add(2, address3);
          } else {
            elements0 = null;
            offset = index1;
          }
        } else {
          elements0 = null;
          offset = index1;
        }
      } else {
        elements0 = null;
        offset = index1;
      }
      if (elements0 == null) {
        address0 = FAILURE;
      } else {
        address0 = actions.sequence_part(input, index1, offset, elements0);
        offset = offset;
      }
      rule.put(index0, new CacheRecord(address0, offset));
    }
    return address0;
  }

  TreeNode _read_mute() {
    TreeNode address0 = FAILURE;
    int index0 = offset;
    Map<Integer, CacheRecord> rule = cache.get(Label.mute);
    if (rule == null) {
      rule = new HashMap<Integer, CacheRecord>();
      cache.put(Label.mute, rule);
    }
    if (rule.containsKey(offset)) {
      address0 = rule.get(offset).node;
      offset = rule.get(offset).tail;
    } else {
      String chunk0 = null;
      if (offset < inputSize) {
        chunk0 = input.substring(offset, Math.min(offset + 1, input.length()));
      }
      if (chunk0 != null && chunk0.equals("@")) {
        address0 = new TreeNode(input.substring(offset, offset + 1), offset);
        offset = offset + 1;
      } else {
        address0 = FAILURE;
        if (offset > failure) {
          failure = offset;
          expected = new ArrayList<String>();
        }
        if (offset == failure) {
          expected.add("\"@\"");
        }
      }
      rule.put(index0, new CacheRecord(address0, offset));
    }
    return address0;
  }

  TreeNode _read_label() {
    TreeNode address0 = FAILURE;
    int index0 = offset;
    Map<Integer, CacheRecord> rule = cache.get(Label.label);
    if (rule == null) {
      rule = new HashMap<Integer, CacheRecord>();
      cache.put(Label.label, rule);
    }
    if (rule.containsKey(offset)) {
      address0 = rule.get(offset).node;
      offset = rule.get(offset).tail;
    } else {
      int index1 = offset;
      List<TreeNode> elements0 = new ArrayList<TreeNode>(2);
      TreeNode address1 = FAILURE;
      address1 = _read_identifier();
      if (address1 != FAILURE) {
        elements0.add(0, address1);
        TreeNode address2 = FAILURE;
        String chunk0 = null;
        if (offset < inputSize) {
          chunk0 = input.substring(offset, Math.min(offset + 1, input.length()));
        }
        if (chunk0 != null && chunk0.equals(":")) {
          address2 = new TreeNode(input.substring(offset, offset + 1), offset);
          offset = offset + 1;
        } else {
          address2 = FAILURE;
          if (offset > failure) {
            failure = offset;
            expected = new ArrayList<String>();
          }
          if (offset == failure) {
            expected.add("\":\"");
          }
        }
        if (address2 != FAILURE) {
          elements0.add(1, address2);
        } else {
          elements0 = null;
          offset = index1;
        }
      } else {
        elements0 = null;
        offset = index1;
      }
      if (elements0 == null) {
        address0 = FAILURE;
      } else {
        address0 = new TreeNode17(input.substring(index1, offset), index1, elements0);
        offset = offset;
      }
      rule.put(index0, new CacheRecord(address0, offset));
    }
    return address0;
  }

  TreeNode _read_paren_expression() {
    TreeNode address0 = FAILURE;
    int index0 = offset;
    Map<Integer, CacheRecord> rule = cache.get(Label.paren_expression);
    if (rule == null) {
      rule = new HashMap<Integer, CacheRecord>();
      cache.put(Label.paren_expression, rule);
    }
    if (rule.containsKey(offset)) {
      address0 = rule.get(offset).node;
      offset = rule.get(offset).tail;
    } else {
      int index1 = offset;
      List<TreeNode> elements0 = new ArrayList<TreeNode>(5);
      TreeNode address1 = FAILURE;
      String chunk0 = null;
      if (offset < inputSize) {
        chunk0 = input.substring(offset, Math.min(offset + 1, input.length()));
      }
      if (chunk0 != null && chunk0.equals("(")) {
        address1 = new TreeNode(input.substring(offset, offset + 1), offset);
        offset = offset + 1;
      } else {
        address1 = FAILURE;
        if (offset > failure) {
          failure = offset;
          expected = new ArrayList<String>();
        }
        if (offset == failure) {
          expected.add("\"(\"");
        }
      }
      if (address1 != FAILURE) {
        elements0.add(0, address1);
        TreeNode address2 = FAILURE;
        int remaining0 = 0;
        int index2 = offset;
        List<TreeNode> elements1 = new ArrayList<TreeNode>();
        TreeNode address3 = new TreeNode("", -1);
        while (address3 != FAILURE) {
          address3 = _read__();
          if (address3 != FAILURE) {
            elements1.add(address3);
            --remaining0;
          }
        }
        if (remaining0 <= 0) {
          address2 = new TreeNode(input.substring(index2, offset), index2, elements1);
          offset = offset;
        } else {
          address2 = FAILURE;
        }
        if (address2 != FAILURE) {
          elements0.add(1, address2);
          TreeNode address4 = FAILURE;
          address4 = _read_parsing_expression();
          if (address4 != FAILURE) {
            elements0.add(2, address4);
            TreeNode address5 = FAILURE;
            int remaining1 = 0;
            int index3 = offset;
            List<TreeNode> elements2 = new ArrayList<TreeNode>();
            TreeNode address6 = new TreeNode("", -1);
            while (address6 != FAILURE) {
              address6 = _read__();
              if (address6 != FAILURE) {
                elements2.add(address6);
                --remaining1;
              }
            }
            if (remaining1 <= 0) {
              address5 = new TreeNode(input.substring(index3, offset), index3, elements2);
              offset = offset;
            } else {
              address5 = FAILURE;
            }
            if (address5 != FAILURE) {
              elements0.add(3, address5);
              TreeNode address7 = FAILURE;
              String chunk1 = null;
              if (offset < inputSize) {
                chunk1 = input.substring(offset, Math.min(offset + 1, input.length()));
              }
              if (chunk1 != null && chunk1.equals(")")) {
                address7 = new TreeNode(input.substring(offset, offset + 1), offset);
                offset = offset + 1;
              } else {
                address7 = FAILURE;
                if (offset > failure) {
                  failure = offset;
                  expected = new ArrayList<String>();
                }
                if (offset == failure) {
                  expected.add("\")\"");
                }
              }
              if (address7 != FAILURE) {
                elements0.add(4, address7);
              } else {
                elements0 = null;
                offset = index1;
              }
            } else {
              elements0 = null;
              offset = index1;
            }
          } else {
            elements0 = null;
            offset = index1;
          }
        } else {
          elements0 = null;
          offset = index1;
        }
      } else {
        elements0 = null;
        offset = index1;
      }
      if (elements0 == null) {
        address0 = FAILURE;
      } else {
        address0 = actions.paren_expr(input, index1, offset, elements0);
        offset = offset;
      }
      rule.put(index0, new CacheRecord(address0, offset));
    }
    return address0;
  }

  TreeNode _read_predicated_atom() {
    TreeNode address0 = FAILURE;
    int index0 = offset;
    Map<Integer, CacheRecord> rule = cache.get(Label.predicated_atom);
    if (rule == null) {
      rule = new HashMap<Integer, CacheRecord>();
      cache.put(Label.predicated_atom, rule);
    }
    if (rule.containsKey(offset)) {
      address0 = rule.get(offset).node;
      offset = rule.get(offset).tail;
    } else {
      int index1 = offset;
      List<TreeNode> elements0 = new ArrayList<TreeNode>(3);
      TreeNode address1 = FAILURE;
      int index2 = offset;
      String chunk0 = null;
      if (offset < inputSize) {
        chunk0 = input.substring(offset, Math.min(offset + 1, input.length()));
      }
      if (chunk0 != null && chunk0.equals("&")) {
        address1 = new TreeNode(input.substring(offset, offset + 1), offset);
        offset = offset + 1;
      } else {
        address1 = FAILURE;
        if (offset > failure) {
          failure = offset;
          expected = new ArrayList<String>();
        }
        if (offset == failure) {
          expected.add("\"&\"");
        }
      }
      if (address1 == FAILURE) {
        offset = index2;
        String chunk1 = null;
        if (offset < inputSize) {
          chunk1 = input.substring(offset, Math.min(offset + 1, input.length()));
        }
        if (chunk1 != null && chunk1.equals("!")) {
          address1 = new TreeNode(input.substring(offset, offset + 1), offset);
          offset = offset + 1;
        } else {
          address1 = FAILURE;
          if (offset > failure) {
            failure = offset;
            expected = new ArrayList<String>();
          }
          if (offset == failure) {
            expected.add("\"!\"");
          }
        }
        if (address1 == FAILURE) {
          offset = index2;
        }
      }
      if (address1 != FAILURE) {
        elements0.add(0, address1);
        TreeNode address2 = FAILURE;
        int remaining0 = 0;
        int index3 = offset;
        List<TreeNode> elements1 = new ArrayList<TreeNode>();
        TreeNode address3 = new TreeNode("", -1);
        while (address3 != FAILURE) {
          address3 = _read__();
          if (address3 != FAILURE) {
            elements1.add(address3);
            --remaining0;
          }
        }
        if (remaining0 <= 0) {
          address2 = new TreeNode(input.substring(index3, offset), index3, elements1);
          offset = offset;
        } else {
          address2 = FAILURE;
        }
        if (address2 != FAILURE) {
          elements0.add(1, address2);
          TreeNode address4 = FAILURE;
          address4 = _read_atom();
          if (address4 != FAILURE) {
            elements0.add(2, address4);
          } else {
            elements0 = null;
            offset = index1;
          }
        } else {
          elements0 = null;
          offset = index1;
        }
      } else {
        elements0 = null;
        offset = index1;
      }
      if (elements0 == null) {
        address0 = FAILURE;
      } else {
        address0 = actions.predicate(input, index1, offset, elements0);
        offset = offset;
      }
      rule.put(index0, new CacheRecord(address0, offset));
    }
    return address0;
  }

  TreeNode _read_repeated_atom() {
    TreeNode address0 = FAILURE;
    int index0 = offset;
    Map<Integer, CacheRecord> rule = cache.get(Label.repeated_atom);
    if (rule == null) {
      rule = new HashMap<Integer, CacheRecord>();
      cache.put(Label.repeated_atom, rule);
    }
    if (rule.containsKey(offset)) {
      address0 = rule.get(offset).node;
      offset = rule.get(offset).tail;
    } else {
      int index1 = offset;
      List<TreeNode> elements0 = new ArrayList<TreeNode>(3);
      TreeNode address1 = FAILURE;
      address1 = _read_atom();
      if (address1 != FAILURE) {
        elements0.add(0, address1);
        TreeNode address2 = FAILURE;
        int remaining0 = 0;
        int index2 = offset;
        List<TreeNode> elements1 = new ArrayList<TreeNode>();
        TreeNode address3 = new TreeNode("", -1);
        while (address3 != FAILURE) {
          address3 = _read__();
          if (address3 != FAILURE) {
            elements1.add(address3);
            --remaining0;
          }
        }
        if (remaining0 <= 0) {
          address2 = new TreeNode(input.substring(index2, offset), index2, elements1);
          offset = offset;
        } else {
          address2 = FAILURE;
        }
        if (address2 != FAILURE) {
          elements0.add(1, address2);
          TreeNode address4 = FAILURE;
          int index3 = offset;
          String chunk0 = null;
          if (offset < inputSize) {
            chunk0 = input.substring(offset, Math.min(offset + 1, input.length()));
          }
          if (chunk0 != null && chunk0.equals("*")) {
            address4 = new TreeNode(input.substring(offset, offset + 1), offset);
            offset = offset + 1;
          } else {
            address4 = FAILURE;
            if (offset > failure) {
              failure = offset;
              expected = new ArrayList<String>();
            }
            if (offset == failure) {
              expected.add("\"*\"");
            }
          }
          if (address4 == FAILURE) {
            offset = index3;
            String chunk1 = null;
            if (offset < inputSize) {
              chunk1 = input.substring(offset, Math.min(offset + 1, input.length()));
            }
            if (chunk1 != null && chunk1.equals("+")) {
              address4 = new TreeNode(input.substring(offset, offset + 1), offset);
              offset = offset + 1;
            } else {
              address4 = FAILURE;
              if (offset > failure) {
                failure = offset;
                expected = new ArrayList<String>();
              }
              if (offset == failure) {
                expected.add("\"+\"");
              }
            }
            if (address4 == FAILURE) {
              offset = index3;
            }
          }
          if (address4 != FAILURE) {
            elements0.add(2, address4);
          } else {
            elements0 = null;
            offset = index1;
          }
        } else {
          elements0 = null;
          offset = index1;
        }
      } else {
        elements0 = null;
        offset = index1;
      }
      if (elements0 == null) {
        address0 = FAILURE;
      } else {
        address0 = actions.repeat(input, index1, offset, elements0);
        offset = offset;
      }
      rule.put(index0, new CacheRecord(address0, offset));
    }
    return address0;
  }

  TreeNode _read_maybe_atom() {
    TreeNode address0 = FAILURE;
    int index0 = offset;
    Map<Integer, CacheRecord> rule = cache.get(Label.maybe_atom);
    if (rule == null) {
      rule = new HashMap<Integer, CacheRecord>();
      cache.put(Label.maybe_atom, rule);
    }
    if (rule.containsKey(offset)) {
      address0 = rule.get(offset).node;
      offset = rule.get(offset).tail;
    } else {
      int index1 = offset;
      List<TreeNode> elements0 = new ArrayList<TreeNode>(3);
      TreeNode address1 = FAILURE;
      address1 = _read_atom();
      if (address1 != FAILURE) {
        elements0.add(0, address1);
        TreeNode address2 = FAILURE;
        int remaining0 = 0;
        int index2 = offset;
        List<TreeNode> elements1 = new ArrayList<TreeNode>();
        TreeNode address3 = new TreeNode("", -1);
        while (address3 != FAILURE) {
          address3 = _read__();
          if (address3 != FAILURE) {
            elements1.add(address3);
            --remaining0;
          }
        }
        if (remaining0 <= 0) {
          address2 = new TreeNode(input.substring(index2, offset), index2, elements1);
          offset = offset;
        } else {
          address2 = FAILURE;
        }
        if (address2 != FAILURE) {
          elements0.add(1, address2);
          TreeNode address4 = FAILURE;
          String chunk0 = null;
          if (offset < inputSize) {
            chunk0 = input.substring(offset, Math.min(offset + 1, input.length()));
          }
          if (chunk0 != null && chunk0.equals("?")) {
            address4 = new TreeNode(input.substring(offset, offset + 1), offset);
            offset = offset + 1;
          } else {
            address4 = FAILURE;
            if (offset > failure) {
              failure = offset;
              expected = new ArrayList<String>();
            }
            if (offset == failure) {
              expected.add("\"?\"");
            }
          }
          if (address4 != FAILURE) {
            elements0.add(2, address4);
          } else {
            elements0 = null;
            offset = index1;
          }
        } else {
          elements0 = null;
          offset = index1;
        }
      } else {
        elements0 = null;
        offset = index1;
      }
      if (elements0 == null) {
        address0 = FAILURE;
      } else {
        address0 = actions.maybe(input, index1, offset, elements0);
        offset = offset;
      }
      rule.put(index0, new CacheRecord(address0, offset));
    }
    return address0;
  }

  TreeNode _read_reference() {
    TreeNode address0 = FAILURE;
    int index0 = offset;
    Map<Integer, CacheRecord> rule = cache.get(Label.reference);
    if (rule == null) {
      rule = new HashMap<Integer, CacheRecord>();
      cache.put(Label.reference, rule);
    }
    if (rule.containsKey(offset)) {
      address0 = rule.get(offset).node;
      offset = rule.get(offset).tail;
    } else {
      int index1 = offset;
      List<TreeNode> elements0 = new ArrayList<TreeNode>(2);
      TreeNode address1 = FAILURE;
      address1 = _read_identifier();
      if (address1 != FAILURE) {
        elements0.add(0, address1);
        TreeNode address2 = FAILURE;
        int index2 = offset;
        address2 = _read_assignment();
        offset = index2;
        if (address2 == FAILURE) {
          address2 = new TreeNode(input.substring(offset, offset), offset);
          offset = offset;
        } else {
          address2 = FAILURE;
        }
        if (address2 != FAILURE) {
          elements0.add(1, address2);
        } else {
          elements0 = null;
          offset = index1;
        }
      } else {
        elements0 = null;
        offset = index1;
      }
      if (elements0 == null) {
        address0 = FAILURE;
      } else {
        address0 = actions.reference(input, index1, offset, elements0);
        offset = offset;
      }
      rule.put(index0, new CacheRecord(address0, offset));
    }
    return address0;
  }

  TreeNode _read_literal_string() {
    TreeNode address0 = FAILURE;
    int index0 = offset;
    Map<Integer, CacheRecord> rule = cache.get(Label.literal_string);
    if (rule == null) {
      rule = new HashMap<Integer, CacheRecord>();
      cache.put(Label.literal_string, rule);
    }
    if (rule.containsKey(offset)) {
      address0 = rule.get(offset).node;
      offset = rule.get(offset).tail;
    } else {
      int index1 = offset;
      int index2 = offset;
      List<TreeNode> elements0 = new ArrayList<TreeNode>(3);
      TreeNode address1 = FAILURE;
      String chunk0 = null;
      if (offset < inputSize) {
        chunk0 = input.substring(offset, Math.min(offset + 1, input.length()));
      }
      if (chunk0 != null && chunk0.equals("\"")) {
        address1 = new TreeNode(input.substring(offset, offset + 1), offset);
        offset = offset + 1;
      } else {
        address1 = FAILURE;
        if (offset > failure) {
          failure = offset;
          expected = new ArrayList<String>();
        }
        if (offset == failure) {
          expected.add("'\"'");
        }
      }
      if (address1 != FAILURE) {
        elements0.add(0, address1);
        TreeNode address2 = FAILURE;
        int remaining0 = 0;
        int index3 = offset;
        List<TreeNode> elements1 = new ArrayList<TreeNode>();
        TreeNode address3 = new TreeNode("", -1);
        while (address3 != FAILURE) {
          int index4 = offset;
          int index5 = offset;
          List<TreeNode> elements2 = new ArrayList<TreeNode>(2);
          TreeNode address4 = FAILURE;
          String chunk1 = null;
          if (offset < inputSize) {
            chunk1 = input.substring(offset, Math.min(offset + 1, input.length()));
          }
          if (chunk1 != null && chunk1.equals("\\")) {
            address4 = new TreeNode(input.substring(offset, offset + 1), offset);
            offset = offset + 1;
          } else {
            address4 = FAILURE;
            if (offset > failure) {
              failure = offset;
              expected = new ArrayList<String>();
            }
            if (offset == failure) {
              expected.add("\"\\\\\"");
            }
          }
          if (address4 != FAILURE) {
            elements2.add(0, address4);
            TreeNode address5 = FAILURE;
            if (offset < inputSize) {
              address5 = new TreeNode(input.substring(offset, offset + 1), offset);
              offset = offset + 1;
            } else {
              address5 = FAILURE;
              if (offset > failure) {
                failure = offset;
                expected = new ArrayList<String>();
              }
              if (offset == failure) {
                expected.add("<any char>");
              }
            }
            if (address5 != FAILURE) {
              elements2.add(1, address5);
            } else {
              elements2 = null;
              offset = index5;
            }
          } else {
            elements2 = null;
            offset = index5;
          }
          if (elements2 == null) {
            address3 = FAILURE;
          } else {
            address3 = new TreeNode(input.substring(index5, offset), index5, elements2);
            offset = offset;
          }
          if (address3 == FAILURE) {
            offset = index4;
            String chunk2 = null;
            if (offset < inputSize) {
              chunk2 = input.substring(offset, Math.min(offset + 1, input.length()));
            }
            if (chunk2 != null && REGEX_5.matcher(chunk2).matches()) {
              address3 = new TreeNode(input.substring(offset, offset + 1), offset);
              offset = offset + 1;
            } else {
              address3 = FAILURE;
              if (offset > failure) {
                failure = offset;
                expected = new ArrayList<String>();
              }
              if (offset == failure) {
                expected.add("[^\"]");
              }
            }
            if (address3 == FAILURE) {
              offset = index4;
            }
          }
          if (address3 != FAILURE) {
            elements1.add(address3);
            --remaining0;
          }
        }
        if (remaining0 <= 0) {
          address2 = new TreeNode(input.substring(index3, offset), index3, elements1);
          offset = offset;
        } else {
          address2 = FAILURE;
        }
        if (address2 != FAILURE) {
          elements0.add(1, address2);
          TreeNode address6 = FAILURE;
          String chunk3 = null;
          if (offset < inputSize) {
            chunk3 = input.substring(offset, Math.min(offset + 1, input.length()));
          }
          if (chunk3 != null && chunk3.equals("\"")) {
            address6 = new TreeNode(input.substring(offset, offset + 1), offset);
            offset = offset + 1;
          } else {
            address6 = FAILURE;
            if (offset > failure) {
              failure = offset;
              expected = new ArrayList<String>();
            }
            if (offset == failure) {
              expected.add("'\"'");
            }
          }
          if (address6 != FAILURE) {
            elements0.add(2, address6);
          } else {
            elements0 = null;
            offset = index2;
          }
        } else {
          elements0 = null;
          offset = index2;
        }
      } else {
        elements0 = null;
        offset = index2;
      }
      if (elements0 == null) {
        address0 = FAILURE;
      } else {
        address0 = actions.string(input, index2, offset, elements0);
        offset = offset;
      }
      if (address0 == FAILURE) {
        offset = index1;
        int index6 = offset;
        List<TreeNode> elements3 = new ArrayList<TreeNode>(3);
        TreeNode address7 = FAILURE;
        String chunk4 = null;
        if (offset < inputSize) {
          chunk4 = input.substring(offset, Math.min(offset + 1, input.length()));
        }
        if (chunk4 != null && chunk4.equals("'")) {
          address7 = new TreeNode(input.substring(offset, offset + 1), offset);
          offset = offset + 1;
        } else {
          address7 = FAILURE;
          if (offset > failure) {
            failure = offset;
            expected = new ArrayList<String>();
          }
          if (offset == failure) {
            expected.add("\"'\"");
          }
        }
        if (address7 != FAILURE) {
          elements3.add(0, address7);
          TreeNode address8 = FAILURE;
          int remaining1 = 0;
          int index7 = offset;
          List<TreeNode> elements4 = new ArrayList<TreeNode>();
          TreeNode address9 = new TreeNode("", -1);
          while (address9 != FAILURE) {
            int index8 = offset;
            int index9 = offset;
            List<TreeNode> elements5 = new ArrayList<TreeNode>(2);
            TreeNode address10 = FAILURE;
            String chunk5 = null;
            if (offset < inputSize) {
              chunk5 = input.substring(offset, Math.min(offset + 1, input.length()));
            }
            if (chunk5 != null && chunk5.equals("\\")) {
              address10 = new TreeNode(input.substring(offset, offset + 1), offset);
              offset = offset + 1;
            } else {
              address10 = FAILURE;
              if (offset > failure) {
                failure = offset;
                expected = new ArrayList<String>();
              }
              if (offset == failure) {
                expected.add("\"\\\\\"");
              }
            }
            if (address10 != FAILURE) {
              elements5.add(0, address10);
              TreeNode address11 = FAILURE;
              if (offset < inputSize) {
                address11 = new TreeNode(input.substring(offset, offset + 1), offset);
                offset = offset + 1;
              } else {
                address11 = FAILURE;
                if (offset > failure) {
                  failure = offset;
                  expected = new ArrayList<String>();
                }
                if (offset == failure) {
                  expected.add("<any char>");
                }
              }
              if (address11 != FAILURE) {
                elements5.add(1, address11);
              } else {
                elements5 = null;
                offset = index9;
              }
            } else {
              elements5 = null;
              offset = index9;
            }
            if (elements5 == null) {
              address9 = FAILURE;
            } else {
              address9 = new TreeNode(input.substring(index9, offset), index9, elements5);
              offset = offset;
            }
            if (address9 == FAILURE) {
              offset = index8;
              String chunk6 = null;
              if (offset < inputSize) {
                chunk6 = input.substring(offset, Math.min(offset + 1, input.length()));
              }
              if (chunk6 != null && REGEX_6.matcher(chunk6).matches()) {
                address9 = new TreeNode(input.substring(offset, offset + 1), offset);
                offset = offset + 1;
              } else {
                address9 = FAILURE;
                if (offset > failure) {
                  failure = offset;
                  expected = new ArrayList<String>();
                }
                if (offset == failure) {
                  expected.add("[^']");
                }
              }
              if (address9 == FAILURE) {
                offset = index8;
              }
            }
            if (address9 != FAILURE) {
              elements4.add(address9);
              --remaining1;
            }
          }
          if (remaining1 <= 0) {
            address8 = new TreeNode(input.substring(index7, offset), index7, elements4);
            offset = offset;
          } else {
            address8 = FAILURE;
          }
          if (address8 != FAILURE) {
            elements3.add(1, address8);
            TreeNode address12 = FAILURE;
            String chunk7 = null;
            if (offset < inputSize) {
              chunk7 = input.substring(offset, Math.min(offset + 1, input.length()));
            }
            if (chunk7 != null && chunk7.equals("'")) {
              address12 = new TreeNode(input.substring(offset, offset + 1), offset);
              offset = offset + 1;
            } else {
              address12 = FAILURE;
              if (offset > failure) {
                failure = offset;
                expected = new ArrayList<String>();
              }
              if (offset == failure) {
                expected.add("\"'\"");
              }
            }
            if (address12 != FAILURE) {
              elements3.add(2, address12);
            } else {
              elements3 = null;
              offset = index6;
            }
          } else {
            elements3 = null;
            offset = index6;
          }
        } else {
          elements3 = null;
          offset = index6;
        }
        if (elements3 == null) {
          address0 = FAILURE;
        } else {
          address0 = actions.string(input, index6, offset, elements3);
          offset = offset;
        }
        if (address0 == FAILURE) {
          offset = index1;
        }
      }
      rule.put(index0, new CacheRecord(address0, offset));
    }
    return address0;
  }

  TreeNode _read_ci_string() {
    TreeNode address0 = FAILURE;
    int index0 = offset;
    Map<Integer, CacheRecord> rule = cache.get(Label.ci_string);
    if (rule == null) {
      rule = new HashMap<Integer, CacheRecord>();
      cache.put(Label.ci_string, rule);
    }
    if (rule.containsKey(offset)) {
      address0 = rule.get(offset).node;
      offset = rule.get(offset).tail;
    } else {
      int index1 = offset;
      List<TreeNode> elements0 = new ArrayList<TreeNode>(3);
      TreeNode address1 = FAILURE;
      String chunk0 = null;
      if (offset < inputSize) {
        chunk0 = input.substring(offset, Math.min(offset + 1, input.length()));
      }
      if (chunk0 != null && chunk0.equals("`")) {
        address1 = new TreeNode(input.substring(offset, offset + 1), offset);
        offset = offset + 1;
      } else {
        address1 = FAILURE;
        if (offset > failure) {
          failure = offset;
          expected = new ArrayList<String>();
        }
        if (offset == failure) {
          expected.add("\"`\"");
        }
      }
      if (address1 != FAILURE) {
        elements0.add(0, address1);
        TreeNode address2 = FAILURE;
        int remaining0 = 0;
        int index2 = offset;
        List<TreeNode> elements1 = new ArrayList<TreeNode>();
        TreeNode address3 = new TreeNode("", -1);
        while (address3 != FAILURE) {
          int index3 = offset;
          int index4 = offset;
          List<TreeNode> elements2 = new ArrayList<TreeNode>(2);
          TreeNode address4 = FAILURE;
          String chunk1 = null;
          if (offset < inputSize) {
            chunk1 = input.substring(offset, Math.min(offset + 1, input.length()));
          }
          if (chunk1 != null && chunk1.equals("\\")) {
            address4 = new TreeNode(input.substring(offset, offset + 1), offset);
            offset = offset + 1;
          } else {
            address4 = FAILURE;
            if (offset > failure) {
              failure = offset;
              expected = new ArrayList<String>();
            }
            if (offset == failure) {
              expected.add("\"\\\\\"");
            }
          }
          if (address4 != FAILURE) {
            elements2.add(0, address4);
            TreeNode address5 = FAILURE;
            if (offset < inputSize) {
              address5 = new TreeNode(input.substring(offset, offset + 1), offset);
              offset = offset + 1;
            } else {
              address5 = FAILURE;
              if (offset > failure) {
                failure = offset;
                expected = new ArrayList<String>();
              }
              if (offset == failure) {
                expected.add("<any char>");
              }
            }
            if (address5 != FAILURE) {
              elements2.add(1, address5);
            } else {
              elements2 = null;
              offset = index4;
            }
          } else {
            elements2 = null;
            offset = index4;
          }
          if (elements2 == null) {
            address3 = FAILURE;
          } else {
            address3 = new TreeNode(input.substring(index4, offset), index4, elements2);
            offset = offset;
          }
          if (address3 == FAILURE) {
            offset = index3;
            String chunk2 = null;
            if (offset < inputSize) {
              chunk2 = input.substring(offset, Math.min(offset + 1, input.length()));
            }
            if (chunk2 != null && REGEX_7.matcher(chunk2).matches()) {
              address3 = new TreeNode(input.substring(offset, offset + 1), offset);
              offset = offset + 1;
            } else {
              address3 = FAILURE;
              if (offset > failure) {
                failure = offset;
                expected = new ArrayList<String>();
              }
              if (offset == failure) {
                expected.add("[^`]");
              }
            }
            if (address3 == FAILURE) {
              offset = index3;
            }
          }
          if (address3 != FAILURE) {
            elements1.add(address3);
            --remaining0;
          }
        }
        if (remaining0 <= 0) {
          address2 = new TreeNode(input.substring(index2, offset), index2, elements1);
          offset = offset;
        } else {
          address2 = FAILURE;
        }
        if (address2 != FAILURE) {
          elements0.add(1, address2);
          TreeNode address6 = FAILURE;
          String chunk3 = null;
          if (offset < inputSize) {
            chunk3 = input.substring(offset, Math.min(offset + 1, input.length()));
          }
          if (chunk3 != null && chunk3.equals("`")) {
            address6 = new TreeNode(input.substring(offset, offset + 1), offset);
            offset = offset + 1;
          } else {
            address6 = FAILURE;
            if (offset > failure) {
              failure = offset;
              expected = new ArrayList<String>();
            }
            if (offset == failure) {
              expected.add("\"`\"");
            }
          }
          if (address6 != FAILURE) {
            elements0.add(2, address6);
          } else {
            elements0 = null;
            offset = index1;
          }
        } else {
          elements0 = null;
          offset = index1;
        }
      } else {
        elements0 = null;
        offset = index1;
      }
      if (elements0 == null) {
        address0 = FAILURE;
      } else {
        address0 = actions.ci_string(input, index1, offset, elements0);
        offset = offset;
      }
      rule.put(index0, new CacheRecord(address0, offset));
    }
    return address0;
  }

  TreeNode _read_char_class() {
    TreeNode address0 = FAILURE;
    int index0 = offset;
    Map<Integer, CacheRecord> rule = cache.get(Label.char_class);
    if (rule == null) {
      rule = new HashMap<Integer, CacheRecord>();
      cache.put(Label.char_class, rule);
    }
    if (rule.containsKey(offset)) {
      address0 = rule.get(offset).node;
      offset = rule.get(offset).tail;
    } else {
      int index1 = offset;
      List<TreeNode> elements0 = new ArrayList<TreeNode>(4);
      TreeNode address1 = FAILURE;
      String chunk0 = null;
      if (offset < inputSize) {
        chunk0 = input.substring(offset, Math.min(offset + 1, input.length()));
      }
      if (chunk0 != null && chunk0.equals("[")) {
        address1 = new TreeNode(input.substring(offset, offset + 1), offset);
        offset = offset + 1;
      } else {
        address1 = FAILURE;
        if (offset > failure) {
          failure = offset;
          expected = new ArrayList<String>();
        }
        if (offset == failure) {
          expected.add("\"[\"");
        }
      }
      if (address1 != FAILURE) {
        elements0.add(0, address1);
        TreeNode address2 = FAILURE;
        int index2 = offset;
        String chunk1 = null;
        if (offset < inputSize) {
          chunk1 = input.substring(offset, Math.min(offset + 1, input.length()));
        }
        if (chunk1 != null && chunk1.equals("^")) {
          address2 = new TreeNode(input.substring(offset, offset + 1), offset);
          offset = offset + 1;
        } else {
          address2 = FAILURE;
          if (offset > failure) {
            failure = offset;
            expected = new ArrayList<String>();
          }
          if (offset == failure) {
            expected.add("\"^\"");
          }
        }
        if (address2 == FAILURE) {
          address2 = new TreeNode(input.substring(index2, index2), index2);
          offset = index2;
        }
        if (address2 != FAILURE) {
          elements0.add(1, address2);
          TreeNode address3 = FAILURE;
          int remaining0 = 1;
          int index3 = offset;
          List<TreeNode> elements1 = new ArrayList<TreeNode>();
          TreeNode address4 = new TreeNode("", -1);
          while (address4 != FAILURE) {
            int index4 = offset;
            int index5 = offset;
            List<TreeNode> elements2 = new ArrayList<TreeNode>(2);
            TreeNode address5 = FAILURE;
            String chunk2 = null;
            if (offset < inputSize) {
              chunk2 = input.substring(offset, Math.min(offset + 1, input.length()));
            }
            if (chunk2 != null && chunk2.equals("\\")) {
              address5 = new TreeNode(input.substring(offset, offset + 1), offset);
              offset = offset + 1;
            } else {
              address5 = FAILURE;
              if (offset > failure) {
                failure = offset;
                expected = new ArrayList<String>();
              }
              if (offset == failure) {
                expected.add("\"\\\\\"");
              }
            }
            if (address5 != FAILURE) {
              elements2.add(0, address5);
              TreeNode address6 = FAILURE;
              if (offset < inputSize) {
                address6 = new TreeNode(input.substring(offset, offset + 1), offset);
                offset = offset + 1;
              } else {
                address6 = FAILURE;
                if (offset > failure) {
                  failure = offset;
                  expected = new ArrayList<String>();
                }
                if (offset == failure) {
                  expected.add("<any char>");
                }
              }
              if (address6 != FAILURE) {
                elements2.add(1, address6);
              } else {
                elements2 = null;
                offset = index5;
              }
            } else {
              elements2 = null;
              offset = index5;
            }
            if (elements2 == null) {
              address4 = FAILURE;
            } else {
              address4 = new TreeNode(input.substring(index5, offset), index5, elements2);
              offset = offset;
            }
            if (address4 == FAILURE) {
              offset = index4;
              String chunk3 = null;
              if (offset < inputSize) {
                chunk3 = input.substring(offset, Math.min(offset + 1, input.length()));
              }
              if (chunk3 != null && REGEX_8.matcher(chunk3).matches()) {
                address4 = new TreeNode(input.substring(offset, offset + 1), offset);
                offset = offset + 1;
              } else {
                address4 = FAILURE;
                if (offset > failure) {
                  failure = offset;
                  expected = new ArrayList<String>();
                }
                if (offset == failure) {
                  expected.add("[^\\]]");
                }
              }
              if (address4 == FAILURE) {
                offset = index4;
              }
            }
            if (address4 != FAILURE) {
              elements1.add(address4);
              --remaining0;
            }
          }
          if (remaining0 <= 0) {
            address3 = new TreeNode(input.substring(index3, offset), index3, elements1);
            offset = offset;
          } else {
            address3 = FAILURE;
          }
          if (address3 != FAILURE) {
            elements0.add(2, address3);
            TreeNode address7 = FAILURE;
            String chunk4 = null;
            if (offset < inputSize) {
              chunk4 = input.substring(offset, Math.min(offset + 1, input.length()));
            }
            if (chunk4 != null && chunk4.equals("]")) {
              address7 = new TreeNode(input.substring(offset, offset + 1), offset);
              offset = offset + 1;
            } else {
              address7 = FAILURE;
              if (offset > failure) {
                failure = offset;
                expected = new ArrayList<String>();
              }
              if (offset == failure) {
                expected.add("\"]\"");
              }
            }
            if (address7 != FAILURE) {
              elements0.add(3, address7);
            } else {
              elements0 = null;
              offset = index1;
            }
          } else {
            elements0 = null;
            offset = index1;
          }
        } else {
          elements0 = null;
          offset = index1;
        }
      } else {
        elements0 = null;
        offset = index1;
      }
      if (elements0 == null) {
        address0 = FAILURE;
      } else {
        address0 = actions.char_class(input, index1, offset, elements0);
        offset = offset;
      }
      rule.put(index0, new CacheRecord(address0, offset));
    }
    return address0;
  }

  TreeNode _read_any_char() {
    TreeNode address0 = FAILURE;
    int index0 = offset;
    Map<Integer, CacheRecord> rule = cache.get(Label.any_char);
    if (rule == null) {
      rule = new HashMap<Integer, CacheRecord>();
      cache.put(Label.any_char, rule);
    }
    if (rule.containsKey(offset)) {
      address0 = rule.get(offset).node;
      offset = rule.get(offset).tail;
    } else {
      String chunk0 = null;
      if (offset < inputSize) {
        chunk0 = input.substring(offset, Math.min(offset + 1, input.length()));
      }
      if (chunk0 != null && chunk0.equals(".")) {
        address0 = actions.any_char(input, offset, offset + 1, null);
        offset = offset + 1;
      } else {
        address0 = FAILURE;
        if (offset > failure) {
          failure = offset;
          expected = new ArrayList<String>();
        }
        if (offset == failure) {
          expected.add("\".\"");
        }
      }
      rule.put(index0, new CacheRecord(address0, offset));
    }
    return address0;
  }

  Parser(String input, Actions actions) {
    this.input = input;
    this.inputSize = input.length();
    this.actions = actions;
    this.offset = 0;
    this.cache = new EnumMap<>(Label.class);
    this.failure = 0;
    this.expected = new ArrayList<>();
  }

  TreeNode parse() throws ParseException {
    TreeNode tree = _read_grammar();
    if (tree != FAILURE && offset == inputSize) {
      return tree;
    }
    if (expected.isEmpty()) {
      failure = offset;
      expected.add("<EOF>");
    }
    throw new ParseException(formatError(input, failure, expected),offset);
  }
}

public static TreeNode parse(String input, Actions actions) throws ParseException {
  Parser parser = new Parser(input, actions);
  return parser.parse();
}

static String formatError(String input, int offset, List<String> expected) {
  String[] lines = input.split("\n");
  int lineNo = 0, position = 0;
  while (position <= offset) {
    position += lines[lineNo].length() + 1;
    lineNo += 1;
  }
  String message = "Line " + lineNo + ": expected " + expected + "\n";
  String line = lines[lineNo - 1];
  message += line + "\n";
  position -= line.length() + 1;
  while (position < offset) {
    message += " ";
    position += 1;
  }
  return message + "^";
}

public enum Label {
  __,
  action_expression,
  action_tag,
  actionable,
  any_char,
  assignment,
  atom,
  char_class,
  choice,
  choice_part,
  ci_string,
  comment,
  expr,
  grammar,
  grammar_name,
  id,
  identifier,
  label,
  literal_string,
  maybe_atom,
  mute,
  object_identifier,
  paren_expression,
  parsing_expression,
  predicated_atom,
  reference,
  repeated_atom,
  rule,
  sequence,
  sequence_element,
  sequence_part,
  terminal,
  typable,
  type_tag,
  typed_expression
}

}

package canopy;

import java.util.function.Consumer;

public interface Builder {

    void init(String name, char separator);
    String serialize();

    void package_(String name, Runnable block);
    String syntaxNodeClass_();
    void grammarModule_(String[] actions, Runnable block);
    void compileRegex_(String charClass, String name);
    void parserClass_(String root);
    void exports_();
    void class_(String name, String parent, Runnable block);
    void constructor_(String[] args, Runnable block);
    void method_(String name, String[] args, Runnable block);
    void cache_(String name, Consumer<String> block);
    void attributes_(Object args);
    void attribute_(String name, String value);
    String localVar_(String name, String value);
    String chunk_(int length);
    void syntaxNode_(String address, String start, String end, String elements, String action, String nodeClass);
    void ifNode_(String address, Runnable then_, Runnable else_);
    void unlessNode_(String address, Runnable then_, Runnable else_);
    void ifNull_(String elements, Runnable then_, Runnable else_);
    void extendNode_(String address, String nodeType);
    void failure_(String address, String expected);
    void assign_(String name, String value);
    void jump_(String address, String rule);
    void conditional_(String kwd, String condition, Runnable then_, Runnable else_);
    void if_(String condition, Runnable then_, Runnable else_);
    void whileNotNull_(String expression, Runnable block);
    String stringMatch_(String expression, String string);
    String stringMatchCI_(String expression, String string);
    String regexMatch_(String regex, String string);
    void return_(String expression);
    String arrayLookup_(String expression, int offset);
    void append_(String list, String value, String index);
    void decrement_(String variable);
    String isZero_(String expression);
    String hasChars_();
    String nullNode_();
    String offset_();
    String emptyList_(int size);
    String emptyString_();
    String true_();
    String null_();

}

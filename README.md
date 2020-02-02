# Canopy
A port of the Canopy.js PEG parser to java

This is a port of [jcoglan's javascript PEG parser](http://canopy.jcoglan.com) also on [github](https://github.com/jcoglan/canopy).

The code was developed by first running canopy on its own <code>meta_grammar.peg</code> file with the provided <code>builders/java.js</code> module.
Then the <code>canopy.js, compiler.js, builder/java.js</code> and <code>ast/*.js</code> files were ported to java,
at the same time making adjustments to the java files generated from <code>meta_grammar.peg.</code>

To test parser/compiler by re-generating the MetaGrammar code:
```
  mkdir -p bin`
  javac -d bin -sourcepath src src/canopy/Canopy.java
  java -cp bin canopy.Canopy ./src/canopy/MetaGrammar.peg ./test/meta_grammar.txt
  diff ./src/canopy/MetaGrammar.peg ./test/meta_grammar.txt
```  

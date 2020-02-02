package canopy;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;

public class Canopy {

    public String compile(String grammar, Builder builder) throws ParseException {
        var compiler = new Compiler(grammar,builder);
        return compiler.toSource();
    }
    
    public static void main(String... args) throws Exception {
        if (args.length < 1) {
            System.out.println("usage: Canopy <input.peg> <output.java>");
            System.exit(1);
        }
        var input = inputFile(args[0]);
        var output = args.length > 1 ? outputFile(args[1]) : outputFile(input,"java");

        var builder = newBuilder();
        builder.init(args[0],File.pathSeparatorChar);

        System.out.println("reading from "+input);
        System.out.println("writing to "+output);

        var grammar = Files.readString(input);
        var source = new Canopy().compile(grammar,builder);
        Files.writeString(output,source);
    }

    static Path inputFile(String name) throws Exception {
        var file = Paths.get(name);
        if (Files.exists(file)) return file;
        throw new FileNotFoundException(name);
    }

    static Path outputFile(String name) {
        return Paths.get(name);
    }

    static Path outputFile(Path file, String suffix) {
        var name = file.getFileName().toString();
        var p = name.lastIndexOf('.');
        if (p > -1) name = name.substring(0,p);
        name = name + '.' + suffix;
        return file.getParent().resolve(name);
    }

    static Builder newBuilder() {
        return new canopy.builder.Java10();
    }

}

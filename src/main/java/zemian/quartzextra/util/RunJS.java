package zemian.quartzextra.util;

import javax.script.Bindings;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.io.FileReader;
import java.util.Arrays;

/**
 * Execute a JavaScript file and pass rest arguments into script engine.
 *
 * This is similar to 'jjs' command, but to workaround the broken -classpath option.
 *
 * Created by zemian on 7/4/17.
 */
public class RunJS {
    public static void main(String[] args) throws Exception {
        if (args.length < 1) {
            throw new IllegalArgumentException("Missing script file argument.");
        }
        String fileName = args[0];
        String[] newArgs = Arrays.copyOfRange(args, 1, args.length);

        ScriptEngine engine = new ScriptEngineManager().getEngineByName("JavaScript");
        Bindings bindings = engine.createBindings();
        bindings.put("arguments", newArgs);

        try (FileReader reader = new FileReader(fileName)) {
            Object result = engine.eval(reader, bindings);
            if (result != null) {
                System.out.println(result);
            }
        }
    }
}

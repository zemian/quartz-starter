package zemian.quartzextra;

import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * A main class wrapper that will invoke other java class. This provide a
 * entry to the executable jar, and we have option to execute any other
 * main class.
 *
 * Usage:
 * MainRunner <mainClassName> [mainArguments]
 *
 */
public class MainRunner {
    public static void main(String[] args) throws Exception {
        String className = args[0];
        String[] mainArgs = Arrays.copyOfRange(args, 1, args.length);
        Class<?> mainClass = Class.forName(className);
        Method main = mainClass.getMethod("main", String[].class);
        main.invoke(null, new Object[]{mainArgs});
    }
}

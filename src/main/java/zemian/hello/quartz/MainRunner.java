package zemian.hello.quartz;

import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * A main class wrapper that will invoke other java class. This provide a
 * entry to the executable jar, and we have option to execute any other
 * main class.
 *
 * Usage:
 *
 * MainRunner MainClassName [arguments]
 *
 * For convenience, we default MainClassName to "zemian.hello.quartz.QuartzServer"
 *
 */
public class MainRunner {
    public static void main(String[] args) throws Exception {
        String className = "zemian.hello.quartz.QuartzServer";
        String[] mainArgs = args;
        if (args.length > 0) {
            className = args[0];
            mainArgs = Arrays.copyOfRange(args, 1, args.length);
        }
        Class<?> mainClass = Class.forName(className);
        Method main = mainClass.getMethod("main", String[].class);
        main.invoke(null, new Object[]{mainArgs});
    }
}

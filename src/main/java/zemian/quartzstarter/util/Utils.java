package zemian.quartzstarter.util;

public class Utils {
    /** A task that can handle exception instead of Runnable. */
    public interface Task {
        void run() throws Exception;
    }
    public static void waitOnMainThread(Task shutdownHook) {
        // Register JVM shutdown hook
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                shutdownHook.run();
            } catch (Exception e) {
                throw new RuntimeException("Failed to execute shutdown task", e);
            }
        }));

        // Pause main thread forever.
        while (true) {
            try {
                Thread.sleep(Long.MAX_VALUE);
            } catch (InterruptedException e) {
                // Ignore.
            }
        }
    }
}

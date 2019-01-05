package zemian.quartzstarter;

import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A simple quartz server that bootstrap the scheduler and then sits and wait
 * for jobs to be executed.
 *
 * Press CTRL+C on console to stop the server.
 *
 * Created by zemian on 7/3/17.
 */
public class QuartzServer {
    private static Logger LOG = LoggerFactory.getLogger(QuartzServer.class);
    public static final String DEFAULT_CONFIG = "zemian/quartzextra/quartz.properties";

    public static void main(String[] args) throws Exception {
        String config = DEFAULT_CONFIG;
        if (args.length > 0)
            config = args[0];
        final Scheduler scheduler = new StdSchedulerFactory(config).getScheduler();

        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                try {
                    LOG.info("Shutting down quartz server.");
                    boolean waitForJobToCompleteFirst = true;
                    scheduler.shutdown(waitForJobToCompleteFirst);
                } catch (SchedulerException e) {
                    throw new RuntimeException("Failed to shutdown quartz server.", e);
                }
            }
        });

        LOG.info("Starting quartz server.");
        scheduler.start();
        Thread.currentThread().join();
    }
}

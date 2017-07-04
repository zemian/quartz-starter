package zemian.quartz.examples;

import org.quartz.Scheduler;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by zemian on 7/3/17.
 */
public class QuartzServer {
    private static Logger LOG = LoggerFactory.getLogger(QuartzServer.class);
    public static final String DEFAULT_CONFIG = "zemian/quartz/examples/quartz.properties";
    public static void main(String[] args) throws Exception {
        String config = System.getProperty("quartzConfig",DEFAULT_CONFIG);
        Scheduler scheduler = new StdSchedulerFactory(config).getScheduler();
        try {
            LOG.info("Starting quartz server.");
            scheduler.start();
            Thread.currentThread().join();
        } finally {
            scheduler.shutdown();
            LOG.info("Quartz server stopped.");
        }
    }
}

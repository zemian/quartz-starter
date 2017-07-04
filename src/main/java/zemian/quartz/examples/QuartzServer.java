package zemian.quartz.examples;

import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.impl.StdSchedulerFactory;

/**
 * Created by zemian on 7/3/17.
 */
public class QuartzServer {
    public static final String DEFAULT_CONFIG = "zemian/quartz/examples/quartz.properties";
    public static void main(String[] args) throws Exception {
        String config = System.getProperty("quartzConfig",DEFAULT_CONFIG);
        Scheduler scheduler = new StdSchedulerFactory(config).getScheduler();
        try {
            JobDetail job = JobBuilder.newJob(HelloJob.class).storeDurably().build();
            scheduler.addJob(job, true);
        } finally {
            scheduler.shutdown();
        }
    }
}

package zemian.quartz.examples;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by zemian on 7/3/17.
 */
public class QuartzClientWithJobData {
    private static Logger LOG = LoggerFactory.getLogger(QuartzClientWithJobData.class);
    public static final String DEFAULT_CONFIG = "zemian/quartz/examples/quartz.properties";
    public static void main(String[] args) throws Exception {
        String config = System.getProperty("quartzConfig",DEFAULT_CONFIG);
        Scheduler scheduler = new StdSchedulerFactory(config).getScheduler();
        try {
            String jobName = "HelloJobWithData";
            JobDetail job = JobBuilder.newJob(HelloJob.class).withIdentity(jobName)
                    .usingJobData("foo", "fooValue")
                    .usingJobData("color", "blue")
                    .build();
            Trigger trigger = TriggerBuilder.newTrigger().withIdentity("Every5SecsTrigger", jobName)
                    .withSchedule(CronScheduleBuilder.cronSchedule("0/5 * * * * ?"))
                    .forJob(jobName)
                    .build();
            QuartzClient.safeAdd(scheduler, job, trigger);
            LOG.info("Created {} with {}", job.getKey(), trigger.getKey());
        } finally {
            scheduler.shutdown();
        }
    }
}

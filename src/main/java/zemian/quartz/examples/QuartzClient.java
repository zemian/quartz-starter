package zemian.quartz.examples;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.spi.MutableTrigger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by zemian on 7/3/17.
 */
public class QuartzClient {
    private static Logger LOG = LoggerFactory.getLogger(QuartzClient.class);
    public static final String DEFAULT_CONFIG = "zemian/quartz/examples/quartz.properties";
    public static void main(String[] args) throws Exception {
        String config = System.getProperty("quartzConfig",DEFAULT_CONFIG);
        Scheduler scheduler = new StdSchedulerFactory(config).getScheduler();
        try {
            String jobName = "HelloJob";
            JobDetail job = JobBuilder.newJob(HelloJob.class).withIdentity(jobName).build();
            Trigger trigger = TriggerBuilder.newTrigger().withIdentity("Every5SecsTrigger", jobName)
                    .withSchedule(CronScheduleBuilder.cronSchedule("0/5 * * * * ?"))
                    .forJob(jobName)
                    .build();
            safeAdd(scheduler, job, trigger);
            LOG.info("Created {} with {}", job.getKey(), trigger.getKey());

            // Add another trigger with same existing job!
            trigger = TriggerBuilder.newTrigger().withIdentity("HourlyTrigger", jobName)
                    .withSchedule(CronScheduleBuilder.cronSchedule("0 0 * * * ?"))
                    .forJob(jobName)
                    .build();
            safeAdd(scheduler, job, trigger);
            LOG.info("Created {} with {}", job.getKey(), trigger.getKey());

        } finally {
            scheduler.shutdown();
        }
    }

    public static void safeAdd(Scheduler scheduler, JobDetail job, Trigger trigger) throws SchedulerException {
        if (scheduler.checkExists(job.getKey())) {
            scheduler.scheduleJob(trigger);
        } else {
            scheduler.scheduleJob(job, trigger);
        }
    }
}

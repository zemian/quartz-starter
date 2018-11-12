package zemian.hello.quartz;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static zemian.hello.quartz.QuartzServer.CONFIG_KEY;
import static zemian.hello.quartz.QuartzServer.DEFAULT_CONFIG;

/**
 * Example of Quartz client to add few jobs.
 *
 * Created by zemian on 7/3/17.
 */
public class QuartzHelloClient {
    private static Logger LOG = LoggerFactory.getLogger(QuartzHelloClient.class);

    public static void main(String[] args) throws Exception {
        String config = System.getProperty(CONFIG_KEY, DEFAULT_CONFIG);
        Scheduler scheduler = new StdSchedulerFactory(config).getScheduler();
        try {
            String jobName = "HelloJob";
            JobDetail job = JobBuilder.newJob(HelloJob.class).withIdentity(jobName).build();
            Trigger trigger = TriggerBuilder.newTrigger().withIdentity("Every5SecsTrigger")
                    .withSchedule(CronScheduleBuilder.cronSchedule("0/5 * * * * ?"))
                    .forJob(jobName)
                    .build();
            safeAdd(scheduler, job, trigger);

            // Add another trigger with same existing job!
            trigger = TriggerBuilder.newTrigger().withIdentity("HourlyTrigger")
                    .withSchedule(CronScheduleBuilder.cronSchedule("0 0 * * * ?"))
                    .forJob(jobName)
                    .build();
            safeAdd(scheduler, job, trigger);

            // Add a durable job that does not have any triggers yet
            jobName = "ScriptJob";
            job = JobBuilder.newJob(ScriptJob.class).withIdentity(jobName)
                    .storeDurably()
                    .usingJobData("scriptEngine", "Groovy")
                    .usingJobData("scriptText", "println('Hi, I am Groovy.');")
                    .build();
            scheduler.addJob(job, true); // replace existing if there is any.
            LOG.info("Created/replaced {} with no trigger", job.getKey());
        } finally {
            scheduler.shutdown();
        }
    }

    // Only schedule job if it does not already exist, else do nothing.
    public static void safeAdd(Scheduler scheduler, JobDetail job, Trigger trigger) throws SchedulerException {
        boolean jobExists = scheduler.checkExists(job.getKey());
        boolean triggerExists = scheduler.checkExists(trigger.getKey());

        if (!jobExists && !triggerExists) {
            scheduler.scheduleJob(job, trigger);
            LOG.info("Created trigger {} with new {}", trigger.getKey(), job.getKey());
        } else if (jobExists && !triggerExists){
            scheduler.scheduleJob(trigger);
            LOG.info("Created trigger {} with existing {}", trigger.getKey(), job.getKey());
        } else {
            LOG.info("Already exists trigger {} with existing {}", trigger.getKey(), job.getKey());
        }
    }
}

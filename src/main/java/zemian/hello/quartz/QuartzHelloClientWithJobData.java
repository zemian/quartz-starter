package zemian.hello.quartz;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static zemian.hello.quartz.QuartzServer.DEFAULT_CONFIG;

/**
 * Example of Quartz client to add job that contains data map.
 *
 * Created by zemian on 7/3/17.
 */
public class QuartzHelloClientWithJobData {
    private static Logger LOG = LoggerFactory.getLogger(QuartzHelloClientWithJobData.class);

    public static void main(String[] args) throws Exception {
        String config = DEFAULT_CONFIG;
        if (args.length > 0)
            config = args[0];
        Scheduler scheduler = new StdSchedulerFactory(config).getScheduler();
        try {
            String jobName = "HelloJobWithData";
            JobDetail job = JobBuilder.newJob(HelloJob.class).withIdentity(jobName)
                    .usingJobData("foo", "fooValue")
                    .usingJobData("color", "blue")
                    .build();
            Trigger trigger = TriggerBuilder.newTrigger().withIdentity("Every5SecsTrigger")
                    .withSchedule(CronScheduleBuilder.cronSchedule("0/5 * * * * ?"))
                    .forJob(jobName)
                    .build();
            QuartzHelloClient.safeAdd(scheduler, job, trigger);
        } finally {
            scheduler.shutdown();
        }
    }
}

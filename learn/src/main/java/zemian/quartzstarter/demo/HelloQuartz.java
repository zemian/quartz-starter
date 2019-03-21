package zemian.quartzstarter.demo;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import zemian.quartzstarter.util.Utils;

public class HelloQuartz {
    private static Logger log = LoggerFactory.getLogger(HelloQuartz.class);

    public static void main(String[] args) throws Exception {
        Scheduler scheduler;
        if (args.length > 0)
            scheduler = new StdSchedulerFactory(args[0]).getScheduler();
        else
            scheduler = StdSchedulerFactory.getDefaultScheduler();

        // Setup a hello job
        JobDetail job = JobBuilder.newJob(HelloJob.class).withIdentity("helloJob").build();
        Trigger trigger = TriggerBuilder.newTrigger()
                .withSchedule(CronScheduleBuilder.cronSchedule("0/3 * * * * ?"))
                .build();
        scheduler.scheduleJob(job, trigger);

        // Start the scheduler
        scheduler.start();
        log.info("Scheduler {} started.", scheduler);

        Utils.waitOnMainThread(() -> scheduler.shutdown());
    }
}

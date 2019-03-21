package zemian.quartzstarter;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HelloQuartz {
    private static Logger log = LoggerFactory.getLogger(HelloQuartz.class);

    public static void main(String[] args) throws Exception {
        Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();

        // Setup a hello job
        JobDetail job = JobBuilder.newJob(HelloJob.class).withIdentity("helloJob").build();
        Trigger trigger = TriggerBuilder.newTrigger()
                .withSchedule(CronScheduleBuilder.cronSchedule("0/3 * * * * ?"))
                .build();
        scheduler.scheduleJob(job, trigger);

        // Start the scheduler
        log.info("Scheduler {} is starting.", scheduler);
        scheduler.start();
        log.info("Scheduler {} started.", scheduler);

        // Register JVM shutdown hook
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                log.info("Scheduler {} is shutting down.", scheduler);
                scheduler.shutdown(false);

                synchronized(scheduler) {
                    scheduler.notify(); // Signal main thread to end.
                }
            } catch (Exception e) {
                throw new RuntimeException("Failed to shutdown scheduler " + scheduler, e);
            }
        }));

        // Place main thread into wait mode
        synchronized (scheduler) {
            try {
                scheduler.wait();
            } catch (InterruptedException e) {
                // Ignore and go back to wait.
            }
        }
        log.info("Scheduler {} has shutdown. Will exit now.", scheduler);
    }
}

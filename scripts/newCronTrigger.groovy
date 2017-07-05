// Add a new CronTrigger to existing durable job.
// args: jobName triggerName cronExpression

import org.quartz.CronScheduleBuilder
import org.quartz.TriggerBuilder
import org.quartz.TriggerKey
import org.quartz.JobKey
import org.quartz.impl.StdSchedulerFactory
import zemian.quartz.examples.QuartzServer

config = System.getProperty(QuartzServer.CONFIG_KEY, QuartzServer.DEFAULT_CONFIG)
scheduler = new StdSchedulerFactory(config).getScheduler()
try {
    jobKey = JobKey.jobKey(args[0])
    triggerKey = TriggerKey.triggerKey(args[1])
    cronExpression = args[2]
    trigger = TriggerBuilder.newTrigger().withIdentity(triggerKey)
            .withSchedule(CronScheduleBuilder.cronSchedule(cronExpression))
            .forJob(jobKey)
            .build()
    scheduler.scheduleJob(trigger)
    println("Job $triggerKey.name added")
} finally {
    scheduler.shutdown()
}
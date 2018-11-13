// Add a new Job into the scheduler
// Example: addNewJob.groovy <config> <jobClassName> <jobName> <cronExpression>

import org.quartz.CronScheduleBuilder
import org.quartz.JobBuilder
import org.quartz.TriggerBuilder
import org.quartz.TriggerKey
import org.quartz.impl.StdSchedulerFactory

config = args[0]
jobClassName = args[1]
jobName = args[2]
cronExpression = args[3]

try {
    jobClass = Class.forName(jobClassName)
    scheduler = new StdSchedulerFactory(config).getScheduler()
    job = JobBuilder.newJob(jobClass)
            .withIdentity(jobName)
            .build()
    triggerKey = TriggerKey.triggerKey(jobName)
    trigger = TriggerBuilder.newTrigger().withIdentity(triggerKey)
            .withSchedule(CronScheduleBuilder.cronSchedule(cronExpression))
            .build()
    scheduler.scheduleJob(job, trigger)

    println("Job $jobClass - $triggerKey.name added")
} finally {
    scheduler.shutdown()
}
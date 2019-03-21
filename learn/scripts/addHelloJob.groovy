// Add a new HelloJob into the scheduler
// Example: addHelloJob.groovy <config> <jobName> <cronExpression>

import org.quartz.*
import org.quartz.impl.StdSchedulerFactory
import zemian.quartzstarter.HelloJob

config = args[0]
jobName = args[1]
cronExpression = args[2]

try {
    scheduler = new StdSchedulerFactory(config).getScheduler()
    job = JobBuilder.newJob(HelloJob.class)
            .withIdentity(jobName)
            .build()
    triggerKey = TriggerKey.triggerKey(jobName)
    trigger = TriggerBuilder.newTrigger().withIdentity(triggerKey)
            .withSchedule(CronScheduleBuilder.cronSchedule(cronExpression))
            .build()
    scheduler.scheduleJob(job, trigger)

    println("Job $triggerKey.name added")
} finally {
    scheduler.shutdown()
}
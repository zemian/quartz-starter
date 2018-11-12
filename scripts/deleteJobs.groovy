// Delete one or more jobs
// args: jobName ...
import org.quartz.JobKey
import org.quartz.impl.StdSchedulerFactory

config = args[0]
scheduler = new StdSchedulerFactory(config).getScheduler()
try {
    args.each { name ->
        jobKey = JobKey.jobKey(name)
        scheduler.deleteJob(jobKey)
        println("Job $jobKey.name deleted.")
    }
} finally {
    scheduler.shutdown()
}
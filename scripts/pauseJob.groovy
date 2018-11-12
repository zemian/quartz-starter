// Pause all triggers for a job
// args: jobName
import org.quartz.JobKey
import org.quartz.impl.StdSchedulerFactory

config = args[0]
scheduler = new StdSchedulerFactory(config).getScheduler()
try {
    jobName = args[0]
    jobKey = JobKey.jobKey(jobName)
    scheduler.pauseJob(jobKey)
    println("Job $jobKey.name paused.")
} finally {
    scheduler.shutdown()
}
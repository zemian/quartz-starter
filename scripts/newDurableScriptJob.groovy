// Add a new durable job detail into scheduler
// args: jobName /path/to/scriptFile

import org.quartz.JobBuilder
import org.quartz.impl.StdSchedulerFactory
import zemian.hello.quartz.QuartzServer
import zemian.hello.quartz.ScriptJob

config = System.getProperty(QuartzServer.CONFIG_KEY, QuartzServer.DEFAULT_CONFIG)
scheduler = new StdSchedulerFactory(config).getScheduler()
try {
    jobName = args[0]
    scriptFile = args[1]

    job = JobBuilder.newJob(ScriptJob.class).withIdentity(jobName)
            .usingJobData("scriptEngine", "Groovy")
            .usingJobData("scriptFile", scriptFile)
            .storeDurably()
            .build()

    scheduler.addJob(job, true)
    println("ScriptJob $jobName added with script=$scriptFile")
} finally {
    scheduler.shutdown()
}
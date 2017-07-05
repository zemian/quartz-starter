// List all jobs along with their associated triggers

import org.quartz.impl.StdSchedulerFactory
import org.quartz.impl.matchers.GroupMatcher
import zemian.quartz.examples.QuartzServer

config = System.getProperty(QuartzServer.CONFIG_KEY, QuartzServer.DEFAULT_CONFIG)
scheduler = new StdSchedulerFactory(config).getScheduler()
try {
    keys = scheduler.getJobKeys(GroupMatcher.anyGroup())
    println("= Job List =")
    keys.forEach { jobKey ->
        triggers = scheduler.getTriggersOfJob(jobKey)
        if (triggers.isEmpty())
            println("$jobKey.name - no triggers")
        else
            println("$jobKey.name - ${triggers.collect { it.key.name }}")
    }
    println("Total of ${keys.size()} jobs found.")
} finally {
    scheduler.shutdown()
}
// Pause single trigger
// args: triggerName
import org.quartz.TriggerKey
import org.quartz.impl.StdSchedulerFactory

config = args[0]
scheduler = new StdSchedulerFactory(config).getScheduler()
try {
    triggerKey = TriggerKey.triggerKey(args[0])
    scheduler.pauseTrigger(triggerKey)
    println("Trigger $triggerKey.name paused.")
} finally {
    scheduler.shutdown()
}
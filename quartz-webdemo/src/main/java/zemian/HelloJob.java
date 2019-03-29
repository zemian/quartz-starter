package zemian;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HelloJob implements Job {
  private static Logger LOG = LoggerFactory.getLogger(HelloJob.class);
  
  @Override
  public void execute(JobExecutionContext context) throws JobExecutionException {
    try {
      LOG.info("Job " + this + ", context " + context);
      JobDataMap data = context.getMergedJobDataMap();
      if (data.containsKey("pauseTime")) {
        long pauseTime = data.getLong("pauseTime");
        LOG.info("Pausing job " + this + " " + pauseTime + " ms.");
        Thread.sleep(pauseTime);
      }
    } catch (Exception e) {
      throw new JobExecutionException("Job failed.", e);
    }
  }
}

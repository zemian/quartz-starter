package zemian.quartz.examples;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by zemian on 7/4/17.
 */
public class HelloJob implements Job {
    private static Logger LOG = LoggerFactory.getLogger(HelloJob.class);

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        JobDataMap map = jobExecutionContext.getMergedJobDataMap();
        if (!map.isEmpty()) {
            for (String key : map.keySet()) {
                LOG.debug("Job data map {}={}", key, map.get(key));
            }
        }
        LOG.info("Hello {}", jobExecutionContext);
    }
}

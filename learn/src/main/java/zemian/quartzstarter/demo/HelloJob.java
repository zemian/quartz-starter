package zemian.quartzstarter.demo;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;

public class HelloJob implements Job {
    private static Logger log = LoggerFactory.getLogger(HelloJob.class);

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        log.debug("Job is execution with context={}", context);
        log.info("Hello World");

        JobDataMap jobData = context.getMergedJobDataMap();
        if (jobData.size() > 0) {
            // Rewrap map to give a nice string output
            String mapDumpStr = new HashMap(jobData).toString();
            log.info("We found jobData={}", mapDumpStr);
        }
    }
}

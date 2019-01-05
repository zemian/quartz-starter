package zemian.quartzstarter.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HelloSpringBean {
    private static Logger log = LoggerFactory.getLogger(HelloJob.class);
    public void init() {
        log.info("I am a spring bean being initialized.");
    }
}

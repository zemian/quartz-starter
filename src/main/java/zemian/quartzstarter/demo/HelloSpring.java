package zemian.quartzstarter.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import zemian.quartzstarter.util.Utils;

public class HelloSpring {
    private static Logger log = LoggerFactory.getLogger(HelloSpring.class);
    public static void main(String[] args) {
        String fn;
        if (args.length > 0)
            fn = args[0];
        else
            fn = "config/hello-spring.xml";
        log.info("Starting spring with {}", fn);
        ClassPathXmlApplicationContext spring = new ClassPathXmlApplicationContext(fn);
        log.info("Spring {} started", spring);
        Utils.waitOnMainThread(() -> spring.close());
    }
}

package zemian.quartzextra;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.io.FileReader;

/**
 * A dynamic job to execute script file or block of script text directly.
 *
 * Created by zemian on 7/4/17.
 */
public class ScriptJob implements Job {
    private static Logger LOG = LoggerFactory.getLogger(ScriptJob.class);

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        JobDataMap dataMap = jobExecutionContext.getMergedJobDataMap();
        String engineName = dataMap.getString("scriptEngine");
        if (engineName == null) {
            engineName = "Groovy";
        }
        ScriptEngine scriptEngine = getScriptEngine(engineName);
        String scriptFile = dataMap.getString("scriptFile");
        String scriptText = dataMap.getString("scriptText");

        Object result;
        if (scriptFile != null) {
            result = executeScriptFile(scriptEngine, scriptFile);
        } else if (scriptText != null){
            result = executeScriptText(scriptEngine, scriptText);
        } else {
            throw new JobExecutionException("Neither scriptFile nor scriptText found in dataMap.");
        }

        if (result != null) {
            LOG.info("Script result={}", result);
            jobExecutionContext.setResult(result);
        } else {
            LOG.info("Script completed");
        }
    }

    private ScriptEngine getScriptEngine(String engineName) {
        ScriptEngineManager man = new ScriptEngineManager();
        return man.getEngineByName(engineName);
    }

    private Object executeScriptText(ScriptEngine scriptEngine, String scriptText) throws JobExecutionException {
        LOG.info("Executing script text. Length={}", scriptText.length());
        LOG.trace("ScriptText={}", scriptText);
        try {
            return scriptEngine.eval(scriptText);
        } catch (Exception e) {
            throw new JobExecutionException("Failed with scriptText", e);
        }
    }

    private Object executeScriptFile(ScriptEngine scriptEngine, String scriptFile) throws JobExecutionException {
        LOG.info("Executing script file: {}", scriptFile);
        try {
            FileReader reader = new FileReader(scriptFile);
            return scriptEngine.eval(reader);
        } catch (Exception e) {
            throw new JobExecutionException("Failed with scriptFile=" + scriptFile, e);
        }
    }
}

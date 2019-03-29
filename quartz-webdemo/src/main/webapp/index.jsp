<%@ page import="org.quartz.Scheduler" %>
<%@ page import="org.quartz.impl.StdSchedulerFactory" %>
<%@ page import="org.quartz.impl.matchers.GroupMatcher" %>
<%@ page import="org.quartz.TriggerKey" %>
<%@ page import="java.util.Set" %>
<%@ page import="org.quartz.Trigger" %>
<%@ page import="java.util.Date" %>
<%@ page import="org.quartz.JobKey" %>
<html>
<body>
<h2>Quartz Web Demo</h2>
<pre>
<%
    Scheduler s = StdSchedulerFactory.getDefaultScheduler();
    out.println("Scheduler Name: " + s.getSchedulerName());
    out.println("Scheduler Id: " + s.getSchedulerInstanceId());
    out.println("Current Time: " + new Date());

    Set<TriggerKey> keys = s.getTriggerKeys(GroupMatcher.anyTriggerGroup());
    out.println("== List of Jobs (With Triggers): " + keys.size());
    for(TriggerKey k : keys) {
        Trigger trigger = s.getTrigger(k);
        JobKey jobKey = trigger.getJobKey();
        out.println("Job: " + jobKey +
                ", nextFireTime=" + trigger.getNextFireTime() +
                " <a href='delete-job.jsp?jobName=" +
                jobKey.getName() + "&jobGroup=" + jobKey.getGroup() +
                "'>DELETE</a>");
    }
%>
</pre>
</body>
</html>

<%@ page import="org.quartz.Scheduler" %>
<%@ page import="org.quartz.impl.StdSchedulerFactory" %>
<%@ page import="org.quartz.impl.matchers.GroupMatcher" %>
<%@ page import="org.quartz.TriggerKey" %>
<%@ page import="java.util.Set" %>
<%@ page import="org.quartz.Trigger" %>
<%@ page import="java.util.Date" %>
<%@ page import="org.quartz.ScheduleBuilder" %>
<%@ page import="org.quartz.CronScheduleBuilder" %>
<%@ page import="org.quartz.TriggerBuilder" %>
<%@ page import="org.quartz.CronTrigger" %>
<%@ page import="org.quartz.JobBuilder" %>
<%@ page import="org.quartz.JobDetail" %>
<%@ page import="zemian.HelloJob" %>
<%@ page import="java.util.UUID" %>
<%@ page import="org.quartz.Job" %>
<%@ page import="org.quartz.JobKey" %>
<html>
<body>
<h2>Delete Job</h2>
<pre>
<%
    String jobName = request.getParameter("jobName");
    String jobGroup = request.getParameter("jobGroup");
    JobKey jobKey = JobKey.jobKey(jobName, jobGroup);
    Scheduler s = StdSchedulerFactory.getDefaultScheduler();
    s.deleteJob(jobKey);
    out.println("Job deleted: " + jobKey);
%>
</pre>
</body>
</html>

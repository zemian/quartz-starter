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
<html>
<body>
<h2>Add Job</h2>
<form method="post">
    Job Class: <input name="jobClass" value="zemian.HelloJob"/><p/>
    Cron: <input name="cron" value="0 0/5 * * * ? *"/><p/>
    <input type="submit" value="Create"/>
</form>
<pre>
<%
    if ("POST".equals(request.getMethod())) {
        String jobClass = request.getParameter("jobClass");
        Class<? extends Job> jobClz = (Class<? extends Job>) Class.forName(jobClass);

        String uuid = UUID.randomUUID().toString();
        JobDetail job = JobBuilder.newJob(jobClz).
                withIdentity(uuid).
                build();

        String cron = request.getParameter("cron");
        CronTrigger trigger = TriggerBuilder.newTrigger().
                withSchedule(CronScheduleBuilder.cronSchedule(cron)).
                build();

        Scheduler s = StdSchedulerFactory.getDefaultScheduler();
        s.scheduleJob(job, trigger);
        out.println("Job added: " + job.getKey() + " cron=" + cron);
    }
%>
</pre>
</body>
</html>

<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="org.quartz.*"%>
<%@page import="org.quartz.impl.*"%>
<%@page import="org.springframework.scheduling.quartz.*"%>
<%@page import="com.gtjy.common.*"%>
<%@page import="java.text.*"%>
<%@page import="java.util.*"%>

<%
	String cronExpression = request.getParameter("cronExpression");
	
	CronTriggerBean dataSyncTrigger=(CronTriggerBean)ServiceLocator.getService("dataSyncTrigger");
	try {
		dataSyncTrigger.setCronExpression(cronExpression);
	} catch (ParseException e) {
		e.printStackTrace();
	}	
	
	StdScheduler scheduler = (StdScheduler)ServiceLocator.getService("schedulerFactoryBean");
	try {
		scheduler.rescheduleJob(dataSyncTrigger.getName(), scheduler.DEFAULT_GROUP, dataSyncTrigger);
	} catch (SchedulerException e) {
		e.printStackTrace();
	}
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title></title>
	
	
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">

</head>

<body>
<form action="#">
	<%=dataSyncTrigger.getCronExpression()%><input type="text" value="" name="cronExpression">&nbsp;&nbsp; 
	<input type="submit" value="修改">
</form>
</body>
</html>

<script language="javascript">

</script>



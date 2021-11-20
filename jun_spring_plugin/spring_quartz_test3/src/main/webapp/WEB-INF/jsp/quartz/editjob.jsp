<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>编辑任务</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
  </head>
  
 <body style="background: beige; margin: 300px 600px 200px;" >
  <form action="${pageContext.request.contextPath }/quartz/edit" method="post">
	<input type="hidden" name="oldjobName" value="${pd.jobName}" >
	<input type="hidden" name="oldjobGroup" value="${pd.jobGroup}" >
	<input type="hidden" name="oldtriggerName" value="${pd.triggerName}" >
	<input type="hidden" name="oldtriggerGroup" value="${pd.triggerGroupName}" >
	<span>编辑Trigger</span>
	<hr/>
   <table id="table_report" class="table table-striped table-bordered table-hover">
	   <tr><td>cron</td><td><input type="text" name="cron" value="${pd.cron}"/></td></tr>
	   <tr><td>clazz</td><td><input type="text" name="clazz" value="${pd.clazz}"/></td></tr>
	   <tr><td>jobName</td><td><input type="text" name="jobName" value="${pd.jobName}"/></td></tr>
	   <tr><td>jobGroup</td><td><input type="text" name="jobGroupName" value="${pd.jobGroup}"/></td></tr>
	   <tr><td>triggerName</td><td><input type="text" name="triggerName" value="${pd.triggerName}"/></td></tr>
	   <tr><td>triggerGroupName</td><td><input type="text" name="triggerGroupName" value="${pd.triggerGroupName}"/></td></tr>
	   <tr>
	   <td></td>
	   <td><button type="submit" style="border: 0;background-color: #428bca;">提交</button>
	   <button class="cancel" style="border: 0;background-color: #fff;">返回</button>
	   </td>
	   </tr>
	</table>
  </form>
  </body>
</html>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/jquery.min.js"></script>
<script>
$(document).ready(function() {
	$(".cancel").click(function(){
		history.go(-1) ;
	});
});
</script>

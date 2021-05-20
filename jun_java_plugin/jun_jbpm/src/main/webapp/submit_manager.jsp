<%@page contentType="text/html;charset=UTF-8" %>
<%@page import="java.util.*,org.jbpm.api.*"%>
<%
	ProcessEngine processEngine = Configuration.getProcessEngine();
	TaskService taskService = processEngine.getTaskService();

	String taskId = request.getParameter("taskId");
	String result = request.getParameter("result");
	result = new String(result.getBytes("ISO-8859-1"), "UTF-8");
	taskService.completeTask(taskId, result);
	response.sendRedirect("index.jsp");
%>
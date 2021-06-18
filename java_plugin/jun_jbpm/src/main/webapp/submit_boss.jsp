<%@page import="java.util.*,org.jbpm.api.*"%>
<%
	ProcessEngine processEngine = Configuration.getProcessEngine();
	TaskService taskService = processEngine.getTaskService();

	String taskId = request.getParameter("taskId");
	taskService.completeTask(taskId);
	response.sendRedirect("index.jsp");
%>
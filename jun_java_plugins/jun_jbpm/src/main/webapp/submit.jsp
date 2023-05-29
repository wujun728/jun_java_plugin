<%@page import="java.util.*,org.jbpm.api.*"%>
<%
	ProcessEngine processEngine = Configuration.getProcessEngine();
	TaskService taskService = processEngine.getTaskService();

	String taskId = request.getParameter("taskId");
	String owner = request.getParameter("owner");
	int day = Integer.parseInt(request.getParameter("day"));
	String reason = request.getParameter("reason");

	Map map = new HashMap();
	map.put("day", day);
	map.put("reason", reason);
	taskService.completeTask(taskId, map);
	response.sendRedirect("index.jsp");
%>
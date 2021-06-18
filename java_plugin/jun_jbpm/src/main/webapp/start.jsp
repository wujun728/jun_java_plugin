<%@page import="java.util.*,org.jbpm.api.*,java.util.*"%>
<%
	ProcessEngine processEngine = Configuration.getProcessEngine();
	ExecutionService executionService = processEngine
			.getExecutionService();
	Map map = new HashMap();
	map.put("owner", session.getAttribute("username"));
	executionService.startProcessInstanceById(request
			.getParameter("id"), map);
	response.sendRedirect("index.jsp");
%>
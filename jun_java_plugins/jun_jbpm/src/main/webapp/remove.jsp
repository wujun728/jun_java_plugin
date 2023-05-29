<%@page import="java.util.*,org.jbpm.api.*" %>
<%
	ProcessEngine processEngine = Configuration.getProcessEngine();
	RepositoryService repositoryService = processEngine.getRepositoryService();

	repositoryService.deleteDeploymentCascade(request.getParameter("id"));
	response.sendRedirect("index.jsp");
%>
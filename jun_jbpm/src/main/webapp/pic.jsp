<%@page import="org.jbpm.api.*,java.io.*"%>
<%
	ProcessEngine processEngine = Configuration.getProcessEngine();
	RepositoryService repositoryService = processEngine
			.getRepositoryService();
	ExecutionService executionService = processEngine
			.getExecutionService();
	String id = request.getParameter("id");
	ProcessInstance processInstance = executionService
			.findProcessInstanceById(id);
	String processDefinitionId = processInstance
			.getProcessDefinitionId();
	ProcessDefinition processDefinition = repositoryService
			.createProcessDefinitionQuery().processDefinitionId(
					processDefinitionId).uniqueResult();
	InputStream inputStream = repositoryService.getResourceAsStream(processDefinition.getDeploymentId(),"leave.png");
	byte[] b = new byte[1024];
	int len = -1;
	while ((len = inputStream.read(b, 0, 1024)) != -1) {
		response.getOutputStream().write(b, 0, len);
	}
%>
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@include file="/checkLogin.jsp" %>
<%@page import="java.util.*,org.jbpm.api.*,org.jbpm.api.task.*" %>
<%
ProcessEngine processEngine = Configuration.getProcessEngine();
RepositoryService repositoryService = processEngine.getRepositoryService();
ExecutionService executionService = processEngine.getExecutionService();
TaskService taskService = processEngine.getTaskService();

String username = (String) session.getAttribute("username");

List<ProcessDefinition> pdList = repositoryService.createProcessDefinitionQuery().list();
List<ProcessInstance> piList = executionService.createProcessInstanceQuery().list();
List<Task> taskList = taskService.findPersonalTasks(username);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>index</title>
  </head>
  <body> 
    <a href="deploy.jsp">发布新流程</a>&nbsp;[username: <%=username %>]<a href="login.jsp">登陆</a>

    <table border="1" width="100%">
      <caption>流程定义</caption>
      <thead>
        <tr>
          <td>id</td>
          <td>name</td>
          <td>version</td>
          <td>&nbsp;</td>
        </tr>
      </thead>
      <tbody>
<%
	for (ProcessDefinition pd : pdList) {
%>
	    <tr>
	      <td><%=pd.getId() %></td>
	      <td><%=pd.getName() %></td>
	      <td><%=pd.getVersion() %></td>
	      <td>
	        <a href="remove.jsp?id=<%=pd.getDeploymentId() %>">remove</a>
	        &nbsp;|&nbsp;
	        <a href="start.jsp?id=<%=pd.getId() %>">start</a>
	      </td>
	    </tr>
<%
	}
%>
	  </tbody>
	</table> 

    <table border="1" width="100%">
      <caption>流程实例</caption>
      <thead>
        <tr>
          <td>id</td>
          <td>activity</td>
          <td>state</td>
          <td>&nbsp;</td>
        </tr>
      </thead>
      <tbody>
<%
	for (ProcessInstance pi : piList) {
%>
	    <tr>
	      <td><%=pi.getId() %></td>
	      <td><%=pi.findActiveActivityNames() %></td>
	      <td><%=pi.getState() %></td>
	      <td><a href="view.jsp?id=<%=pi.getId() %>">view</a></td>
	    </tr>
<%
	}
%>
	  </tbody>
	</table> 

    <table border="1" width="100%">
      <caption>待办任务</caption>
      <thead>
        <tr>
          <td>id</td>
          <td>name</td>
          <td>&nbsp;</td>
        </tr>
      </thead>
      <tbody>
<%
	for (Task task : taskList) {
%>
	    <tr>
	      <td><%=task.getId() %></td>
	      <td><%=task.getName() %></td>
	      <td><a href="<%=task.getFormResourceName() %>?id=<%=task.getId() %>">view</a></td>
	    </tr>
<%
	}
%>
	  </tbody>
	</table> 
  </body>
</html>

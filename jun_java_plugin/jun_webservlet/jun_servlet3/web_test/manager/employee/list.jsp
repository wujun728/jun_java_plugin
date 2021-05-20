<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/manager/commons/taglibs.jsp"%>
<html><head>
    <title>用户登录页</title>
</head><body>
   <%@ include file="/manager/commons/top.jsp" %>
<div id="main">     
<table id="gridView" > 
   <tr>
      <td id="gridViewHeader" scope=col>员工ID</td>
      <td id="gridViewHeader" scope=col>员工名称</td>
      <td id="gridViewHeader" scope=col>员工职位</td>
      <td id="gridViewHeader" scope=col>操作</td>
   </tr>
   <c:forEach var="emp" items="${list}" varStatus="status">
     <tr>
      <td id="gridViewItem">${emp.id }</td>
      <td id="gridViewItem">${emp.empName }</td>
      <td id="gridViewItem">${emp.position}</td>
      <td id="gridViewItem">
        <a href="${ctx}/manager/employeeServlet?empId=${emp.id}&action=delete">删除</a>|
        <a href="${ctx}/manager/employeeServlet?empId=${emp.id}&action=assignRole">分配角色</a>
      </td>
     </tr>
    </c:forEach>
    <tr>
      <td colspan="4" align="left"><a href="${ctx}/manager/employeeServlet?action=add">>>添加员工</a></td>
    </tr>
</table>
</div></body></html>

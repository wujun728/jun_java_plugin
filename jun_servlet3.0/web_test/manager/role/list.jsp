<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/manager/commons/taglibs.jsp"%>
<html><head>
    <title>用户登录页</title>
</head><body>
   <%@ include file="/manager/commons/top.jsp" %>
<div id="main">     
<table id="gridView" > 
  <tr>
     <td id="gridViewHeader" scope=col>角色ID</td>
     <td id="gridViewHeader" scope=col>角色名称</td>
     <td id="gridViewHeader" scope=col>操作</td>
  </tr>
  <c:forEach var="role" items="${list}" varStatus="status">
  <tr>
     <td id="gridViewItem">${role.id }</td>
     <td id="gridViewItem">${role.roleName }</td>
     <td id="gridViewItem" >
       <a href="${ctx}/manager/roleServlet?roleId=${role.id}&action=delete">删除</a>|
       <a href="${ctx}/manager/roleServlet?roleId=${role.id}&action=assignAuth">分配权限</a>
     </td>
  </tr>
  </c:forEach>
  <tr>
     <td colspan="3" align="left"><a href="${ctx}/manager/roleServlet?action=add">>>添加角色</a></td>
  </tr>
</table>
</div></body></html>

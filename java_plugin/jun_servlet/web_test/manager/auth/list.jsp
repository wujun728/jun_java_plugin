<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/manager/commons/taglibs.jsp"%>
<html><head>
    <title>权限管理页</title>
</head><body>
   <%@ include file="/manager/commons/top.jsp" %>
<div id="main">     
 <table id="gridView" > 
   <tr>
      <td id="gridViewHeader" scope=col>权限ID</td>
      <td id="gridViewHeader"  scope=col>权限名称</td>
      <td id="gridViewHeader"  scope=col>调用方法</td>
      <td id="gridViewHeader"  scope=col>权限URL</td>
      <td id="gridViewHeader"  scope=col>操作</td>
   </tr>
    <c:forEach var="auth" items="${listAuths}" varStatus="status">
      <tr>
      <td id="gridViewItem">${auth.id }</td>
      <td id="gridViewItem">${auth.authName }</td>
      <td id="gridViewItem">${auth.actionName}</td>
      <td id="gridViewItem">${auth.url}</td>
      <td id="gridViewItem">
        <a href="${ctx}/manager/authServlet?authId=${auth.id}&action=delete">删除</a>
      </td>
      </tr>
   </c:forEach>
   <tr>
      <td colspan="4" align="left"><a href="${ctx}/manager/authServlet?action=add">添加单个权限</a></td>
   </tr>
  </table>
</div></body></html>

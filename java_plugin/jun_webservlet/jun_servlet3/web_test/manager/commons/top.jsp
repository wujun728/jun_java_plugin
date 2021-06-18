<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file="taglibs.jsp" %>
<div id="top"><span>
   欢迎:<font color="red">${sessionScope.emp.empName}</font>
 <a href="${ctx}/manager/employeeServlet?action=list">
   <img align="absmiddle" src="${ctx}/manager/images/1.png"/>员工管理
 </a>
 <a href="${ctx}/manager/roleServlet?action=list">
   <img align="absmiddle" src="${ctx}/manager/images/3.png"/>角色管理
 </a>
 <a href="${ctx}/manager/authServlet?action=list">
  <img align="absmiddle" src="${ctx}/manager/images/4.png"/>权限管理
 </a>
 <a href="${ctx}/manager/employeeServlet?action=logout">
  <img align="absmiddle" src="${ctx}/manager/images/2.png"/>注销员工
 </a>
</span></div>
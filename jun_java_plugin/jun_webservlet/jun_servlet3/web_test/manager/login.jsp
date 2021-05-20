<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/manager/commons/taglibs.jsp"%>
<html><head>
    <title>用户登录页</title>
</head><body>
<form name="form1" method="post" action="${ctx}/manager/employeeServlet">
<center><label style="font-size:12px;color:red;">${requestScope.msg}</label></center>
<input type="hidden" name="action" value="login"/>
<table align="center">
   <tr>
     <td colspan="2" align="center">用户登录 <hr size="1"/></td>
   </tr>
   <tr>
     <td>用户名:</td>
     <td><input type="text" name="empname" size="15"/></td>
   </tr>
   <tr>
      <td>密  码:</td>
      <td><input type="password" name="psd" size="17"/></td>
   </tr>
   <tr>
     <td colspan="2" align="right">
       <hr size="1"/>
       <input type="submit" name="submit" value="提交"/>
       <input type="reset" name="reset" value="重置"/>
     </td>
   </tr>
</table>
</form></body></html>

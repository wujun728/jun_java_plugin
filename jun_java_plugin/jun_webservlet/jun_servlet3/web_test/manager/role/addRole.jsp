<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/manager/commons/taglibs.jsp"%>
<html><head>
  <title>增加角色页</title>
</head><body><div id="main"><fieldset>
<legend>增加角色</legend>
<form name="form2" method="post" action="${ctx}/manager/roleServlet" >
  <input type="hidden" name="action" value="save"/>
  <table  align="center">
	<tr>
	 <td><label id="juli" >角色名称：</label></td>
	  <td><input type="text" name="roleName" size="20"/></td>
	</tr>
	<tr>
	 <td colspan="2"><input type="submit" value="提交" id="input-submit"/></td>
	</tr>
 </table>
</form>
</fieldset></div></body></html>

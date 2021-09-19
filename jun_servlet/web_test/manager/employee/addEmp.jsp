<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/manager/commons/taglibs.jsp"%>
<html><head>
  <title>增加员工页</title>
</head><body><div id="main"><fieldset>
<legend>增加员工</legend>
<form name="form2" method="post" action="${ctx}/manager/employeeServlet" >
  <input type="hidden" name="action" value="save"/>
  <table  align="center">
	 <tr>
	  <td><label id="juli" >员工名称：</label></td>
	   <td><input type="text" name="empName" size="20"/></td>
	 </tr>
	 <tr>
	  <td><label id="juli" >员工密码：</label></td>
	   <td><input type="text" name="psd" size="20"/></td>
	 </tr>
	 <tr>
	   <td><label id="juli" >员工职位:</label></td>
	   <td><input type="text" name="position" size="20"/></td>
	 </tr>
	 <tr>
		<td colspan="2"><input type="submit" value="提交" id="input-submit"/></td>
	</tr>
  </table>
</form>
</fieldset></div>
</body></html>

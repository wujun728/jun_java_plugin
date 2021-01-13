<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/manager/commons/taglibs.jsp"%>
<html><head>
  <title>增加权限页</title>
</head><body><div id="main"><fieldset>
<legend>增加权限</legend>
<form name="form2" method="post" action="${ctx}/manager/authServlet" >
  <input type="hidden" name="action" value="save"/>
  <table  align="center">
	 <tr>
	  <td><label id="juli" >权限名称：</label></td>
	   <td><input type="text" name="authName" size="20"/></td>
	 </tr>
	 <tr>
	   <td><label id="juli" >权限模块：</label></td>
	   <td>
	      <select name="moduleId" >
          <c:forEach var="module" items="${moduleList}" >
             <option value="${module.id}">${module.moduleName}</option> 
          </c:forEach>
         </select>  
       </td>
	 </tr>
	 <tr>
	   <td><label id="juli" >调用方法:</label></td>
	   <td><input type="text" name="actionName" size="20"/></td>
	 </tr>
	 <tr>
		<td colspan="2"><input type="submit" value="提交" id="input-submit"/></td>
	</tr>
  </table>
</form>
</fieldset></div></body></html>

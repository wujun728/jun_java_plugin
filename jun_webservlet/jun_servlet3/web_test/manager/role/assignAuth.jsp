<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/manager/commons/taglibs.jsp"%>
<html><head>
  <title>指定权限页</title>
</head><body><div id="main"><fieldset>
<legend>为角色分配权限</legend>
<form name="form2" method="post" action="${ctx}/manager/roleServlet" onSubmit="return selectAll();">
<input type="hidden" name="action" value="assign"/>
<input type="hidden" name="roleId" value="${role.id}"/>
<table   border="0">
 <tr>
   <td><label id="juli" >角色名称:</label></td>
   <td><label id="juli" >${role.roleName}</label></td>
 </tr>
 <tr><td colspan="2"><hr/></td></tr>
 <tr>
   <td colspan="2">
<!-- 下拉列表框开始 -->
<table align= "center" border="0">
<tr>
 <td colspan="2"><span style="font-size:14px;margin-left:30px;">未分配权限</span></td>
 <td><span style="font-size:14px;margin-left:30px;">已分配权限</span></td>
</tr> 
<tr>
 <td>
 <select name="mae" id="mae" size="20" style="width:120px;height:300px;"   multiple="multiple"> 
   <c:forEach var="leftAuth" items="${notAssignedAuth}">
     <option value="${leftAuth.id}">${leftAuth.authName }</option> 
   </c:forEach>
 </select>  
   
 </td>
<td>
<!-- 中间一组按钮开始 -->
<table>
   <tr> <input   type= "button"   value=">>"   onclick="AllToRight()"/> </tr> <br> 
   <tr> <input   type= "button"   value=">"    onclick="ToRight()"/> </tr> <br> 
   <tr> <input   type= "button"   value="<<" onclick="AllToLeft()"/> </tr> <br> 
   <tr> <input   type= "button"   value="<"  onclick="ToLeft()"/> </tr> <br> 
</table> 
<!-- 中间一组按钮结束 -->
</td> 
<td> 
  <select name="ado" id="ado" size="20" style="width:120px;height:300px"  multiple="multiple"> 
     <c:forEach var="rightAuth" items="${assignedAuth}">
       <option value="${rightAuth.id}">${rightAuth.authName }</option> 
     </c:forEach>
  </select> 
</td> 
</tr> 
</table> 
<!-- 下拉列表框结束 -->
   </td>
 </tr>
 <tr>
	<td colspan="2"><input type="submit"  value="提交更改" id="input-submit"/></td>
 </tr>
</table></form>
</fieldset></div></body></html>

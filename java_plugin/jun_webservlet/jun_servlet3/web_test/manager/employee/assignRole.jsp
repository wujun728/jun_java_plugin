<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/manager/commons/taglibs.jsp"%>
<html><head>
  <title>分配角色页</title>
</head><body><div id="main"><fieldset>
<legend>为员工分配角色</legend>
<form name="form2" method="post" action="${ctx}/manager/employeeServlet" onSubmit="return selectAll();">
<input type="hidden" name="action" value="assign"/>
<input type="hidden" name="empId" value="${emp.id}"/>
<table >
 <tr>
   <td ><label id="juli" >名称:</label></td>
   <td ><label id="juli" >${emp.empName}</label></td>
 </tr>
 <tr>
  <td ><label id="juli" >职位:</label></td>
  <td ><label id="juli" >${emp.position}</label></td>
 </tr>
 <tr><td colspan="2"><hr style="border-bottom: #CCC dashed 1px"/></td></tr>
 <tr>
  <td colspan="2">
  <!-- 下拉列表框开始 -->
  <table   align= "center ">
<tr>
 <td colspan="2"><span style="font-size:14px;margin-left:30px;">未分配角色</span></td>
 <td><span style="font-size:14px;margin-left:30px;">已分配角色</span></td>
</tr> 
<tr>
 <td>
 <select name="mae" id="mae" size="20" style="width:120;height:200px;" multiple="multiple"> 
   <c:forEach var="rightRole" items="${notAssignedRole}">
     <option value="${rightRole.id}">${rightRole.roleName }</option> 
   </c:forEach>
 </select>  
 </td>
 <td>
<!-- 中间一组按钮开始 -->
<table >
   <tr><input   type= "button"   value=">>"   onclick="AllToRight()"/></tr> <br> 
   <tr><input   type= "button"   value=">"    onclick="ToRight()"/></tr> <br> 
   <tr><input   type= "button"   value="<<" onclick="AllToLeft()"/></tr> <br> 
   <tr><input   type= "button"   value="<"  onclick="ToLeft()"/></tr> <br> 
</table> 
<!-- 中间一组按钮结束 -->
 </td> 
 <td> 
  <select name="ado" id="ado" size="20" style="width:120;height:200px" multiple="multiple"> 
     <c:forEach var="leftRole" items="${assignedRole}">
       <option value="${leftRole.id}">${leftRole.roleName }</option> 
     </c:forEach>
  </select> 
 </td> 
</tr> 
</table> 
<!-- 下拉列表框结束 -->
   </td>
 </tr>
 <tr>
   <td colspan="2"><input type="submit"  value="提交" id="input-submit"/></td>
 </tr>
</table></form>
</fieldset></div></body></html>

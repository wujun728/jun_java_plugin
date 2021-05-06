<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>修改客户</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  
  <body>
  	<h1>不做客户端验证了，请把数据填写完全并要正确</h1>
    <form action="${pageContext.request.contextPath}/servlet/ControllerServlet?op=editCustomer" method="post">
    	<input type="hidden" name="id" value="${c.id}"/>
    	<table broder="1" width="438">
    		<tr>
    			<td>姓名：</td>
    			<td>
    				<input type="text" name="name" value="${c.name}"/>
    			</td>
    		</tr>
    		<tr>
    			<td>性别：</td>
    			<td>
    				<input type="radio" name="gender" value="male" ${c.gender=='male'?'checked="checked"':'' } />男性
    				<input type="radio" name="gender" value="female" ${c.gender=='female'?'checked="checked"':'' } />女性
    			</td>
    		</tr>
    		<tr>
    			<td>生日：</td>
    			<td>
    				<input type="text" name="birthday" value="${c.birthday}"/>
    			</td>
    		</tr>
    		<tr>
    			<td>电话：</td>
    			<td>
    				<input type="text" name="phonenum" value="${c.phonenum }"/>
    			</td>
    		</tr>
    		<tr>
    			<td>邮箱：</td>
    			<td>
    				<input type="text" name="email" value="${c.email }"/>
    			</td>
    		</tr>
    		<tr>
    			<td>爱好：</td>
    			<td>
    				<input type="checkbox" name="hobbies" value="吃饭" ${fn:contains(c.hobby,'吃饭')?'checked="checked"':'' } />吃饭
    				<input type="checkbox" name="hobbies" value="睡觉"  ${fn:contains(c.hobby,'睡觉')?'checked="checked"':'' }/>睡觉
    				<input type="checkbox" name="hobbies" value="学java"  ${fn:contains(c.hobby,'学java')?'checked="checked"':'' }/>学java
    			</td>
    		</tr>
    		<tr>
    			<td>类型：</td>
    			<td>
    				<input type="radio" name="type" value="VIP" ${c.type=='VIP'?'checked="checked"':'' } />VIP
    				<input type="radio" name="type" value="VVIP" ${c.type=='VVIP'?'checked="checked"':'' }/>VVIP
    			</td>
    		</tr>
    		<tr>
    			<td>描述：</td>
    			<td>
    				<textarea rows="3" cols="38" name="description">${c.description}</textarea>
    			</td>
    		</tr>
    		<tr>
    			<td colspan="2">
    				<input type="submit" value="保存"/>
    			</td>
    		</tr>
    	</table>
    </form>
  </body>
</html>

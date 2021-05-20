<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>新用户注册</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  <!-- 练习重点 -->
  <body>
  	<div>
  		用户名：3~8个字母组成，不能为空<br/>
  		密码：3~8位数字组成，不能为空<br/>
  		重复密码：必须和密码一致<br/>
  		邮箱：不能为空，且要符合邮箱的格式<br/>
  		生日：不能为空，且要符合yyyy-MM-dd的格式<br/>
  	</div>
  
    <form action="${pageContext.request.contextPath}/servlet/RegistServlet" method="post">
    	<table border="1" width="538">
    		<tr>
    			<td>用户名：</td>
    			<td>
    				<input type="text" name="username" value="${formBean.username}"/>${formBean.errors.username}
    			</td>
    		</tr>
    		<tr>
    			<td>密码：</td>
    			<td>
    				<input type="text" name="password" value="${formBean.password}"/>${formBean.errors.password}
    			</td>
    		</tr>
    		<tr>
    			<td>重复密码：</td>
    			<td>
    				<input type="text" name="repassword" value="${formBean.repassword}"/>${formBean.errors.repassword}
    			</td>
    		</tr>
    		<tr>
    			<td>邮箱：</td>
    			<td>
    				<input type="text" name="email" value="${formBean.email}"/>${formBean.errors.email}
    			</td>
    		</tr>
    		<tr>
    			<td>生日：</td>
    			<td>
    				<input type="text" name="birthday" value="${formBean.birthday}"/>${formBean.errors.birthday}
    			</td>
    		</tr>
    		<tr>
    			<td colspan="2">
    				<input type="submit" value="注册"/>
    			</td>
    		</tr>
    	</table>
    </form>
  </body>
</html>

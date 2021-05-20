<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript">
	function checkUserName(){
		var userName=document.getElementById("userName").value;
		var xmlHttp;
		if(window.XMLHttpRequest){
			xmlHttp=new XMLHttpRequest();
		}else{
			xmlHttp=new ActiveXObject("Microsoft.XMLHTTP");
		}
		xmlHttp.onreadystatechange=function(){
			if(xmlHttp.readyState==4 && xmlHttp.status==200){
				alert(xmlHttp.responseText);
				var dataObj=eval("("+xmlHttp.responseText+")");
				if(dataObj.exist){
					document.getElementById("tip").innerHTML="<img src='no.png'/>&nbsp;该用户名已经存在";
				}else{
					document.getElementById("tip").innerHTML="<img src='ok.png'/>&nbsp;该用户名可用";
				}
			}
		};
		xmlHttp.open("get", "getAjaxInfo?action=checkUserName&userName="+userName, true);
		
	    xmlHttp.send();
	}
</script>
</head>
<body>
<table>
	<tr>
		<th>用户注册</th>
	</tr>
	<tr>
		<td>用户名：</td>
		<td><input type="text" id="userName" name="userName" onblur="checkUserName()"/>&nbsp;&nbsp;<font id="tip"></font></td>
	</tr>
	<tr>
		<td>密码：</td>
		<td><input type="text" id="password" name="password"/></td>
	</tr>
	<tr>
		<td>确认密码：</td>
		<td><input type="text" id="password2" name="password2"/></td>
	</tr>
	<tr>
		<td><input type="submit" value="注册"/></td>
		<td><input type="button" value="重置"/></td>
	</tr>
</table>
</body>
</html>
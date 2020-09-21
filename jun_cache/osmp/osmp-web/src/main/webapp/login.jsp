<%@ page language="java" import="java.util.*" pageEncoding="utf-8" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <title>系统登录</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0"> 
	<meta http-equiv="description" content="login">
	<link rel="shortcut icon" href="favicon.ico"/>  
	<link href="css/yjyxlogin.css" rel="stylesheet" type="text/css" />
	<script type="text/javascript">
		if (self != top) {
	        top.location.href = self.location.href;
	    }
		
		function formValid(){
			var nameVal = trim(document.getElementById("userInput").value);
			var pwdVal = trim(document.getElementById("passInput").value);
			if(nameVal == ""||pwdVal == ""){
				alert("用户名或密码为空!");
				return false;
			}
			return true;
		} 
		function trim(str){
			var reg = /(^\s*)|(\s*$)/g;
			return str.replace(reg,"");
		}
	</script>
  </head>
  
  <body>

  		<div id="contrainer">
  			<div id="content">
  				<div id="topBanner">
  				</div>
  				<div id="bannerWrap1">
  				</div>
  				<div id="loginPanel">
  					<form id="loginForm" method="post" action="panel/login.do"> 
	  					<div id="input">
	  						<div id="username">
	  							<input id="userInput"  name="username" type="text"/> 
	  						</div>
	  						<div id="wrap"></div>
	  						<div id="password">
	  							<input id="passInput"  name="password" type="password"/> 
	  						</div>
	  					</div>
	  					<div  id="loginBtnDiv" >
  							<input id="submitBtn" type="submit"  value=""/>
  						</div>
  					</form>
  				</div>
  				<div id="bannerWrap2">
  				</div>
  				<div id="bottomBanner">
  				</div>
  			</div>
  		</div>
  </body>
</html>

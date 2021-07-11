<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page isELIgnored="false"%>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<html>
<head>
<title>登录</title>
<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
<style type="text/css">
body {
	width: 100%;
	height: 100%;
	margin: 0;
	overflow: hidden;
}
</style>
<script src="${ctx}/scripts/boot.js" type="text/javascript"></script>
</head>
<body>
	<div id="loginWindow" class="mini-window" title="用户登录"
		style="width: 350px; height: 200px;" showModal="true"
		showCloseButton="false">
		<form id="mainForm" method="post" action="${ctx}/sso/login">
			<div id="loginForm" style="padding: 15px; padding-top: 10px;">
				<table>
					<tr>
						<td style="width: 60px;"><label for="name$text">帐号：</label></td>
						<td><input id="name" name="name"
							onvalidation="onUserNameValidation" class="mini-textbox"
							required="true" requiredErrorText="账号不能为空" style="width: 150px;" /></td>
					</tr>
					<tr>
						<td style="width: 60px;"><label for="password$text">密码：</label></td>
						<td><input id="password" name="password" onvalidation="onPwdValidation"
							class="mini-password" requiredErrorText="密码不能为空" required="true"
							style="width: 150px;" onenter="onLoginClick" /> &nbsp;&nbsp;<a
							href="#">忘记密码?</a></td>
					</tr>
					<tr>
						<td></td>
						<td style="padding-top: 5px;"><a onclick="onLoginClick"
							class="mini-button" style="width: 60px;">登录</a> <a
							onclick="onResetClick" class="mini-button" style="width: 60px;">重置</a>
						</td>
					</tr>
				</table>
				<label id="loginMessage"></label>
			</div>
		</form>
	</div>

	<script type="text/javascript">
		if('${error}' == 'true') {
			$('#loginMessage').text('${message}');
		} else {
			var authority = getUrlParam('authority');
			if(authority) $('#loginMessage').text(authority);
		}
	
        mini.parse();

        var loginWindow = mini.get("loginWindow");
        loginWindow.show();

        function onLoginClick(e) {
            var form = new mini.Form("#loginWindow");

            form.validate();
            if (form.isValid() == false) return;
            //从浏览器url中获取到 回调地址，动态添加到form中，然后提交
            $('#mainForm').append($('<input type="hidden" name="callbackURL" />')
            		.val(getUrlParam('callbackURL'))).submit();  
         	//提交数据
            /* $.ajax({
            	var formData = mini.encode(form.getData());
				url: '${ctx}/sso/login',
		        type: 'POST',
		        dataType: 'json',
		        data: formData,
		        success: function (result) {
		        	if(result.code)
		        },
		        error: function (result) {
		        	//网络异常或者后台异常
		        }
	    	}); */
        }
        function onResetClick(e) {
            var form = new mini.Form("#loginWindow");
            form.clear();
        }
        /////////////////////////////////////
        function isEmail(s) {
            if (s.search(/^\w+((-\w+)|(\.\w+))*\@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z0-9]+$/) != -1)
                return true;
            else
                return false;
        }
        function onUserNameValidation(e) {
            if (e.isValid) {
            	if (e.value.length < 3) {
                    e.errorText = "账号不能少于3个字符";
                    e.isValid = false;
                }
            }
        }
        function onPwdValidation(e) {
            if (e.isValid) {
                if (e.value.length < 5) {
                    e.errorText = "密码不能少于5个字符";
                    e.isValid = false;
                }
            }
        }
        
		function getUrlParam(name) {
			var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
			var r = window.location.search.substr(1).match(reg); //匹配目标参数
			if (r != null)
				return unescape(r[2]);
			return null; //返回参数值
		}
	</script>

</body>
</html>
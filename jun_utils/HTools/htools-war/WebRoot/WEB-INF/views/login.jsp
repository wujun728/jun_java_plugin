<%@page import="org.apache.shiro.web.filter.authc.FormAuthenticationFilter"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/static/taglibs.jsp"%>
<html>
<head>
<title>登录之后更精彩</title>
<style>
	@-webkit-keyframes logindiv{    /*透明度由0到1*/
	   50%{
	      opacity:0.5; /*透明度为0*/
	    }
	   100%{
	      opacity:1; /*透明度为1*/
	 
	   }
	 }
	#center{
	   width: 520px;  
	   height: 450px;  
	   position: absolute;  
	   left: 100%;
	   margin-left: -550px;
	   top:50%;
	   margin-top: -180px;
	}
	#bk{
	   width: 100%;  
	   height: 500px;
	   position: absolute;  
	   left: 0px;
	   top:15%;
	   background-color: #FFF;
	   background-image: url('${base}/static/images/login-bk.png');
	   background-repeat: no-repeat;
	}
	#footer{
	   width: 100%;  
	   height: 40px;
	   position: absolute;  
	   left: 0px;
	   bottom: 0px;
	   background-color: #efefef;
	   padding-bottom: 0px;
	}
	body {
		background-color: #fefefe;
	}
</style>
</head>
<body>
<div id="bk" align="left">
	<div id="center">
		<form id="inputForm" name="login" action="${base}/login" method="post" class="form-horizontal" onsubmit="return checkLogin();">
			<fieldset>
				<%
				String error = (String) request.getAttribute(FormAuthenticationFilter.DEFAULT_ERROR_KEY_ATTRIBUTE_NAME);
				if(error != null){
				%>
						<div class="alert alert-error controls input-large">
							<button class="close" data-dismiss="alert">×</button>
				<%
						if(error.contains("DisabledAccountException")){
							out.print("用户已被屏蔽,请登录其他用户.");
						}else if(error.contains("CaptchaException")){
							out.print("验证码填写错误.");
						}else{
							out.print("用户名或密码错误.");
						}
				%>		
						</div>
				<%
					}
				%>
	    		<div class="control-group">
			      <div class="controls">
			        <p><input type="text" id="username" name="username" value="${username}" class="input-large required" placeholder="输入账号"></p>
			        <p><input type="password" id="password" name="password" class="input-large required" placeholder="输入密码"></p>
			      	<p>
			      		<img title="点击更换" id="img_captcha" onclick="javascript:refreshCaptcha();" src="${base}/captcha">
			      		<input type="text" style="width: 60px;" maxlength="4" name="captcha" id="captcha" class="input-large required" placeholder="验证码">
			      	</p>
			      </div>
			      <div class="controls">
			      	<label style="float: left;padding-right: 50px;padding-top: 5px;"><input type="checkbox" id="rememberMe" name="rememberMe"/>记住我</label>
			      	<input type="submit" value="登 录" class="btn btn-primary"/>
			      	&nbsp;
					<input type="reset" value="重 置" class="btn btn-warning"/>
			      </div>
			    </div>
			</fieldset>
		</form>
	</div>
</div>

<div id="footer" align="center">
	<h5 id="foottext" style="color: #8f8f8f">HTools -- This's a very cool HADOOP manage tools!</h5>
</div>

<div id="myModal" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
  <div class="modal-header">
    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
    <h3 id="myModalLabel">Oh My God!</h3>
  </div>
  <div class="modal-body">
  	<img alt="惊呆了" src="${base}/static/images/dai.png" style="float: left;"/>
    <div style="padding-left: 80px;">
	    <h4>您正在使用的浏览器不支持HTML5或CSS3，无法正常使用HTools，请下载最新的谷歌浏览器。</h4>
	    <p>
	    	<a class="btn btn-primary" href="https://www.google.com/intl/zh-CN/chrome/browser/" title="下载最新的google-chrome浏览器">下载谷歌浏览器</a>
	    </p>
 	</div>
  </div>
  <div class="modal-footer">
    <button class="btn" data-dismiss="modal" aria-hidden="true">关闭</button>
  </div>
</div>

<script type="text/javascript">
	var element = document.createElement('div');
	var isCss3 = false;
	if('text-overflow' in element.style){       
	 	element.style['text-overflow'] = 'ellipsis';       
	 	isCss3 = element.style['text-overflow'] === 'ellipsis';       
	}
	$(document).ready(function() {
		$(center).css({"-webkit-animation":"logindiv 4s infinite ease-in-out"});
		if(window.ActiveXObject&&!window.XMLHttpRequest){
			if(confirm('温馨提醒：\n\n\t您的浏览器陈旧不堪，不能正常使用HTools。\t\n\n\t安装最新的谷歌浏览器吧！')){
				window.location.href = 'https://www.google.com/intl/zh-CN/chrome/browser/';
			}
			return false;
		}
		if (typeof(Worker) == "undefined"||!isCss3){
			$('#myModal').modal({
				keyboard: false,
				backdrop: true,
				modal:true
			});
			$('#inputForm').attr('action', '#');
		}
		$("#username").focus();
		$("#inputForm").validate({
			errorContainer: "#messageBox",
			errorPlacement: function(error, element) {
					error.insertAfter(element);
			}
		});
	});
	var _captcha_id = "#img_captcha";
	function refreshCaptcha() {
		$(_captcha_id).attr("src","${base}/captcha?t=" + Math.random());
	}
	function checkLogin(){
		if(window.ActiveXObject&&!window.XMLHttpRequest){
			if(confirm('温馨提醒：\n\n\t您的浏览器陈旧不堪，不能正常使用HTools。\t\n\n\t安装最新的谷歌浏览器吧！')){
				window.location.href = 'https://www.google.com/intl/zh-CN/chrome/browser/';
			}
			return false;
		}
		if (typeof(Worker) == "undefined"||!isCss3){
			$('#myModal').modal({
				keyboard: false,
				backdrop: true,
				modal:true
			});
			return false;
		}
		return true;
	}
</script>

</body>
</html>
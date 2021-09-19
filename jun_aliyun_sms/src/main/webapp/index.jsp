<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'index.jsp' starting page</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<script type="text/javascript" src="js/jquery-3.4.1.js"></script>
	<script type="text/javascript">
		var time=5;//定义变量代表显示的秒数
		var recId=0;
		$(function(){
			var flag=false;//定义变量，表示是否可以提交
			
			//当键盘松开后给出提示
			$("#phone").keyup(function(){
				//取值
				var phone=$(this).val();
				if(/^1[3|5|7|8][0-9]{9}$/.test(phone)){//正确
					$("#showMsg").html("<font color='green'>*电话号码输入正确</font>");
					flag=true;
				}else{//错误
					$("#showMsg").html("<font color='red'>*输入的电话号码不可用</font>");
					flag=false;
				}
			});
			
			//单击按钮
			$("#sendSms").click(function(){
				if(flag){
					//异步请求后台发送验证码
					$.post("servlet/AliyunServlet?phone="+$("#phone").val(),function(){
						alert("发送成功");
					});
					
					$(this).attr("disabled","true");//禁用按钮
					//每隔1秒钟执行一次changeTime()函数
					recId = setInterval("changeTime()",1000);
				}else{
					$("#showMsg").html("<font color='red'>*请输入正确的电话号码</font>");
				}
			});
		});
		
		//改变时间的函数
		function changeTime(){
			if(time==1){//到达时间，重新获取
				time=5;
				$("#sendSms").attr("disabled","");//按钮可用
				$("#sendSms").val("获取短信验证码");
				//停止每隔1秒执行
				clearInterval(recId);
			}else{
				time--;
				$("#sendSms").val("【"+time+"】秒后重新获取验证码");
			}
		}
	</script>
  </head>
  
  <body>
       	请输入电话号码：<input id="phone"/><span id="showMsg"></span><br /><br />
       <input id="sendSms" type="button" value="获取短信验证码"/>
  </body>
</html>

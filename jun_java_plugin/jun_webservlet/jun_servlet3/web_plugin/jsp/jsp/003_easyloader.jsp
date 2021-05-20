<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP '004.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!-- 
	<link rel="stylesheet" type="text/css" href="easyui/js/jquery-easyui-1.2.6/themes/default/easyui.css" />
	<link rel="stylesheet" type="text/css" href="easyui/js/jquery-easyui-1.2.6/themes/icon.css" />
	<script type="text/javascript" src="easyui/js/jquery-easyui-1.2.6/jquery.easyui.min.js"></script>
	 -->
	<script type="text/javascript" src="easyui/js/jquery-easyui-1.3.1/easyloader.js"></script>
	    <%@ include file="/common/common.jsp" %>
	<script type="text/javascript">
			$(function(){
				
				/*
				easyloader.load(['window','messager'],function(){
						$('#mydiv').window({
							title:'我的窗口' ,
							width:400 , 
							height:300
						});
						$.messager.alert('提示信息' , '内容' , 'error');
				});
				*/
				
				
			});
			
			
	
	</script>
  </head>
  
  <body>
 		<div id="mydiv"></div>
 		<div id="mydiv2" class="easyui-window" title="窗口2" style="width:300px;height:300px;" ></div>
  </body>
</html>

<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP '015_combotree.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="stylesheet" type="text/css" href="css/common.css" />
    <%@ include file="/common/common.jsp" %>	
	<script type="text/javascript">
	
			$(function(){
				
				$('#tt').combotree({
						//url:'jsp/013_tree.json' ,
						url:'ResourceServlet?method=loadTree' ,
						width:300 ,
						checkbox:true ,
						multiple:true
				});
				
				
				$('#btn1').click(function(){
						console.info($('#tt').combotree('getValues'));
				});
				
				$('#btn2').click(function(){
						//console.info($('#tt').combotree('tree'));
						var tree = $('#tt').combotree('tree');
						var root = tree.tree('getRoot');
						console.info(root);
				});
			});
	</script>
  </head>
  
  <body>
			<input id="tt"  />
			<a id="btn1" class="easyui-linkbutton">获取tree选中的值</a>
			<a id="btn2" class="easyui-linkbutton">返回tree对象</a>
  </body>
</html>

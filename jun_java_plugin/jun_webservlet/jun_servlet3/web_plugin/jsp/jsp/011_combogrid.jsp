<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP '010_combobox.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="stylesheet" type="text/css" href="css/common.css" />
    <%@ include file="/common/common.jsp" %>
	<script type="text/javascript">
			$(function(){
				
				//自动搜索 
				$('#search').combobox({
						//data:[{id:1 ,val:'男'},{id:2 ,val:'女'}] ,
						mode:'remote' ,
						url:'UserServlet?method=searchName' ,
						valueField:'id' ,
						textField:'username' ,
						delay:1000
				});
				
				
				//combogrid实现 			
				$('#cc').combogrid({
					panelWidth:450,
					idField:'id',
					textField:'salary',
					mode:'remote' ,
					url:'UserServlet?method=searchName',
					columns:[[
						{field:'username',title:'用户名',width:100},
						{field:'age',title:'年龄',width:50},
						{field:'sex',title:'性别',width:50 , formatter:function(value , record , index){
								 if(value==1){
									 return '<span style=color:red;>男</span>';
								 } else if(value ==2){
									 return '<span style=color:green;>女</span>';
								 }
							}
						},
						{field:'salary',title:'薪水',width:100}
					]]
				});

			});
	
	</script>
	
	
	
  </head>
  
  <body>
    		自动搜索:<input id="search" /><br/> 
    		combogrid实现:<select id="cc" name="gridId"  style="width:250px;"></select>
    		
  </body>
</html>

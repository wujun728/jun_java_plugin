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
				$('#sel_1').combobox({
					onSelect:function(){
							var pid = $('#sel_1').combobox('getValue');
							$('#sel_2').combobox('setValue' , '');
							$('#sel_2').combobox('reload' , 'ProvinceServlet?method=getCity&pid='+pid);
					}
				});
				
				
				
				//自动搜索 
				$('#search').combobox({
						//data:[{id:1 ,val:'男'},{id:2 ,val:'女'}] ,
						mode:'remote' ,
						url:'UserServlet?method=searchName' ,
						valueField:'id' ,
						textField:'username' ,
						delay:1000
				});
				

			});
	
	</script>
	
	
	
  </head>
  
  <body>
    		<select id="sel_1" class="easyui-combobox" url="ProvinceServlet?method=getPro" valueField="id" textField="name"  ></select>
    		<select id="sel_2" class="easyui-combobox" valueField="id" textField="name" ></select>
    		<br/>&nbsp;&nbsp;
    		自动搜索:<input id="search" /><br/>
  </body>
</html>

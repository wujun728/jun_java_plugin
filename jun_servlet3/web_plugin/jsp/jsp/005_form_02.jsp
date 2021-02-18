<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP '003.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
    <%@ include file="/common/common.jsp" %>
	<script type="text/javascript">
	
		$(function(){
			// 自定义的校验器
			$.extend($.fn.validatebox.defaults.rules, {   
    				midLength: {   
       			 			validator: function(value, param){   
            								return value.length >= param[0] && value.length <= param[1];    
        					},   
        					message: ''  
					} ,
					equalLength : {
       			 			validator: function(value, param){   
            								return value.length == param[0];    
        					},   
        					message: '密码必须为4个字符!'  
					}
			}); 
			
			
			//数值验证组件 
			$('#age').numberbox({
				min:0 , 
				max:150 ,
				required:true , 
				missingMessage:'年龄必填!' ,
				precision:0
			});
			
			//日期组件
			$('#birthday').datebox({
				required:true , 
				missingMessage:'生日必填!' ,
				editable:false
			});
			
			$('#salary').numberbox({
				min:1000 , 
				max:20000 ,
				required:true , 
				missingMessage:'薪水必填!' ,
				precision:2
			});
			
			//日期时间组件
			$('#startTime,#endTime').datetimebox({
				required:true , 
				missingMessage:'时间必填!' ,
				editable:false
			});
			
			$('#btn').click(function(){
					
					$.ajax({
						type:'post' ,					//请求方式
						url:'UserServlet?method=save' ,	//地址
						cache: false ,					//是否情况缓存
						data:$('#myform').serialize() ,	//向后台发送的数据
						dataType:'text' ,				//服务器返回时接收的数据类型 
						success:function(result){
							//var result = eval('('+result+')');
							var result = $.parseJSON(result);
							$.messager.show({
								title:result.status , 
								msg :result.message
							});
						} ,
						error:function(result){
							var result = $.parseJSON(result);
							$.messager.show({
								title:result.status , 
								msg :result.message
							});
						}
						
					});
				
				
				
			});
			
			
			
		});
	
	
	
	</script>
  </head>
  
  <body>
    		
    	<div id="mydiv" class="easyui-panel" style="width:400px;height:350px" title="用户新增"  >
    		<form id="myform" action="" method="post">
    				<table>
    					<tr>
    						<td>用户名:</td>
    						<td><input type="text" name="username" class="easyui-validatebox" required=true validType="midLength[2,5]" missingMessage="用户名必填!" invalidMessage="用户名必须在2到5个字符之间!"  value="" /></td>
    					</tr>
    					<tr>
    						<td>密码:</td>
    						<td><input type="password" name="password" class="easyui-validatebox" required=true validType="equalLength[4]" missingMessage="密码必填!" value="" /></td>
    					</tr>
    					<tr>
    						<td>性别:</td>
    						<td>
    							男<input type="radio" checked="checked" name="sex" value="1" />
    							女<input type="radio" name="sex" value="2" />
    						</td>
    					</tr>
    					<tr>
    						<td>年龄:</td>
    						<td><input id="age" type="text"  name="age" value="" /></td>
    					</tr>
    					<tr>
    						<td>出生日期:</td>
    						<td><input id="birthday" style="width:160px;"  type="text" name="birthday" value="" /></td>
    					</tr>
    					<tr>
    						<td>所属城市:</td>
    						<td>
    							<input name="city" class="easyui-combobox" url="UserServlet?method=getCity" valueField="id" textField="name"  value="" />
    						</td>
    					</tr>
    					<tr>
    						<td>薪水:</td>
    						<td><input id="salary" type="text" name="salary" value="" /></td>
    					</tr>
    					<tr>
    						<td>开始时间:</td>
    						<td><input id="startTime" style="width:160px;"  type="text" name="startTime"  value="" /></td>
    					</tr>
    					<tr>
    						<td>结束时间:</td>
    						<td><input id="endTime" style="width:160px;"   type="text" name="endTime"  value="" /></td>
    					</tr>   
    					<tr>
    						<td>个人描述:</td>
    						<td><input type="text" name="description" class="easyui-validatebox" required=true validType="midLength[5,50]" missingMessage="个人描述必填!" invalidMessage="描述必须在5到50个字符之间!"  value="" /></td>
    					</tr> 
    					<tr align="center">
    						<td colspan="2">
    							<a id="btn" class="easyui-linkbutton">保存</a>
    						</td>
    					</tr>   					 					    					    					    					    					    					    					    					
    				</table>
    		</form>
    	</div>	
    		
    		
    		
    		
    		
    		
  </body>
</html>

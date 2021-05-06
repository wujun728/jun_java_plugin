<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP '014_tree.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="stylesheet" type="text/css" href="css/common.css" />
    <%@ include file="/common/common.jsp" %>
	<script type="text/javascript">
				
			var flag ; //判断执行保存还是修改的方法
			$(function(){
				$('#t1').tree({
						//发送异步ajax请求, 还会携带一个id的参数 
						url:'ResourceServlet?method=loadTree' ,
						dnd:true ,
						onDrop:function(target , source , point){
								var tar = $('#t1').tree('getNode' , target);
								//console.info(tar);
								//console.info(source);
								//console.info(point);
								$.ajax({
									type:'post',
									url:'ResourceServlet?method=changeLevel' , 
									data:{
										targetId:tar.id , 
										sourceId:source.id , 
										point:point
									} , 
									dataType:'json' , 
									cache:false , 
									success:function(result){
										$.messager.show({
											title:'提示信息' ,
											msg:'操作成功!'
										});
									}
								});
						} , 
						onContextMenu: function(e,node){
								//禁止浏览器的菜单打开
								e.preventDefault();
								$(this).tree('select',node.target);
								$('#mm').menu('show', {
									left: e.pageX,
									top: e.pageY
								});
						}
				});
				
				
				//保存和修改按钮
				$('#savebtn').click(function(){
							if(flag == 'add'){
										//1 做前台更新   
										//	(1)获取所选中的节点,也就是父节点
										var node = $('#t1').tree('getSelected');
										//  (2)调用追加节点的方法
										$('#t1').tree('append' , {
											parent:node.target ,
											data:[{
												text: $('#myform').find('input[name=name]').val() ,
												attributes:{
													url:$('#myform').find('input[name=url]').val()
												}
											}]
										});
										
										//2 后台同步更新 
										$.ajax({
											type:'post' ,
											url:'ResourceServlet?method=save' ,
											cache:false , 
											data:{
												parentId:node.id ,
												name:$('#myform').find('input[name=name]').val() ,
												url:$('#myform').find('input[name=url]').val()
											} ,
											dataType:'json' ,
											success:function(result){
												//刷新节点 
												var parent = $('#t1').tree('getParent' , node.target);
												$('#t1').tree('reload',parent.target);
												
												$.messager.show({
													title:'提示信息',
													msg:'操作成功!'
												});
											}
										});
										//3 关闭dialog
										$('#mydiv').dialog('close'); 
							} else {
										$.ajax({
											type:'post' ,
											url:'ResourceServlet?method=update' ,
											cache:false , 
											data:{
												id:$('#myform').find('input[name=id]').val() ,
												name:$('#myform').find('input[name=name]').val() ,
												url:$('#myform').find('input[name=url]').val()
											} ,
											dataType:'json' ,
											success:function(result){
												//刷新节点 (一定是选中节点的父级节点)
												var node = $('#t1').tree('getSelected');
												var parent = $('#t1').tree('getParent' ,node.target);
												$('#t1').tree('reload',parent.target);
												
												//给出提示信息 
												$.messager.show({
													title:'提示信息',
													msg:'操作成功!'
												});
											}
										});
										//3 关闭dialog
										$('#mydiv').dialog('close'); 
										
							}

							

				});
				
				
				//取消按钮
				$('#cancelbtn').click(function(){
						$('#mydiv').dialog('close'); 
				});
				
				
				
			});
			
			
			function append(){
					flag = 'add';
					$('#mydiv').dialog('open');
			}
			
			function editor(){
					flag = 'edit';
					//清空表单数据 ,重新填充选中的节点里的id ,name , url属性
					$('#myform').form('clear');
					var node = $('#t1').tree('getSelected');
					$('#myform').form('load',{
						id:node.id ,
						name:node.text ,
						url:node.attributes.url
					});
					//打开dialog
					$('#mydiv').dialog('open');
			}
			
			function remove(){
					//前台删除
					var node = $('#t1').tree('getSelected');
					$('#t1').tree('remove' , node.target);
					
					//后台删除
					$.post('ResourceServlet?method=delete' , {id:node.id} , function(reuslt){
								//给出提示信息 
								$.messager.show({
									title:'提示信息',
									msg:'操作成功!'
								});
					});
			}

	</script>
	
  </head>
  
  <body>
  		<ul id="t1"></ul>
  		<div id="mydiv" title="设置权限" class="easyui-dialog" style="width:300px;" closed=true >
  				<form id="myform" method="post">
  							<input type="hidden" name="id" value="" />
  							<table> 
  								<tr>
  									<td>权限名称:</td>
  									<td><input type="text" name="name" value=""/></td>
  								</tr>
  								<tr>
  									<td>url:</td>
  									<td><input type="text" name="url" value=""/></td>
  								</tr>
  								<tr align="center"  >
  									<td colspan="2"  >
  										<a id="savebtn" class="easyui-linkbutton">确定</a>
  										<a id="cancelbtn"  class="easyui-linkbutton">取消</a>
  									</td>
  								</tr>  								  								
  							</table>
  				</form>	
  		</div>
  		
  		
		<div id="mm" class="easyui-menu" style="width:150px;">
			<div onclick="append()">Append</div>
			<div onclick="editor()">editor</div>
			<div onclick="remove()">Remove</div>
		</div>  		
  </body>
</html>

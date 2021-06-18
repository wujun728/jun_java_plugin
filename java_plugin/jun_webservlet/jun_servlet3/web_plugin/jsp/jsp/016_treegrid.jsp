<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP '016_treegrid.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="stylesheet" type="text/css" href="css/common.css" />
    <%@ include file="/common/common.jsp" %>
	<script type="text/javascript">
				
	
			var flag ; //判断走的是保存还是修改方法
			$(function(){
					$('#tree_org').treegrid({
							title:'组织机构列表', 
							iconCls:'icon-ok',
							width:1000,
							height:400,
							nowrap: false,
							rownumbers: true,
							collapsible:true,
							url:'OrgServlet?method=getOrgList',			
							idField:'id',				//数据表格要有主键	
							treeField:'name',			//treegrid 树形结构主键 text
							fitColumns:true ,
							columns:[[
								{field:'name',title:'机构名称',width:200} ,
								{field:'count',title:'机构人数',width:120} ,
								{field:'principal',title:'机构负责人',width:120} ,
								{field:'description',title:'机构描述',width:120} 
							]],
							onContextMenu: function(e,row){
								e.preventDefault();					//屏蔽浏览器的菜单
								$(this).treegrid('unselectAll');	//清除所有选中项
								$(this).treegrid('select', row.id);	//选中状态 
								$('#mm').menu('show', {
									left: e.pageX,
									top: e.pageY
								});
							}
					});
					
					$('#btn1').click(function(){
						
							if(flag == 'add'){
										//保存方法 
										//1 前台保存    注意: 没有保存id
										var node = $('#tree_org').treegrid('getSelected');
										$('#tree_org').treegrid('append',{
												parent:node.id ,
												data:[{
														name:$('#myform').find('input[name=name]').val(),
														count:$('#myform').find('input[name=count]').val(),
														principal:$('#myform').find('input[name=principal]').val(),
														description:$('#myform').find('textarea[name=description]').val()
												}]
										});
										
										//2 后台保存 
										$.ajax({
											type:'post',
											url:'OrgServlet?method=save',
											cache:false , 
											dataType:'json',
											data:{
													parentId:node.id ,
													name:$('#myform').find('input[name=name]').val(),
													count:$('#myform').find('input[name=count]').val(),
													principal:$('#myform').find('input[name=principal]').val(),
													description:$('#myform').find('textarea[name=description]').val()
											} ,
											success:function(r){
													//刷新节点 : 刷新当前选中节点
													$('#tree_org').treegrid('reload',node.id);
													$.messager.show({
														title:'提示信息' , 
														msg:'操作成功!'
													});
											}
										});
										//3关闭窗口
										$('#div1').dialog('close');
							} else {
										$.ajax({
											type:'post',
											url:'OrgServlet?method=update',
											cache:false , 
											dataType:'json',
											data:{
													id:$('#myform').find('input[name=id]').val() ,
													name:$('#myform').find('input[name=name]').val(),
													count:$('#myform').find('input[name=count]').val(),
													principal:$('#myform').find('input[name=principal]').val(),
													description:$('#myform').find('textarea[name=description]').val()
											} ,
											success:function(r){
												//刷新节点  :如果当前选中的节点是叶子节点的话,刷新该节点的父亲 ,如果不是叶子节点,刷新当前选中节点即可
												var node = $('#tree_org').treegrid('getSelected');
												var parent =	$('#tree_org').treegrid('getParent' , node.id);
												$('#tree_org').treegrid('reload' , parent.id);
												$.messager.show({
													title:'提示信息',
													msg:'操作成功!'
												});
											}
										});
										//3关闭窗口
										$('#div1').dialog('close');
							}

								
								
					});
					
					//关闭窗口
					$('#btn2').click(function(){
							$('#div1').dialog('close');
					});

					
			});
			
			
			function append(){
						flag='add';
						//1清空表单数据
						$('#myform').form('clear');
						//2打开窗口
						$('#div1').dialog('open');
			}
			
			function update(){
						flag='edit';
						//1清空表单数据
						$('#myform').form('clear');
						//2填充表单回显数据
						var  node  = $('#tree_org').treegrid('getSelected');
						$('#myform').form('load',{
								id:node.id ,
								name:node.name ,
								principal:node.principal,
								count:node.count ,
								description:node.description
						});
						//3打开窗口
						$('#div1').dialog('open');
			}
			
			function remove(){
						$.messager.confirm("提示信息","确认删除?",function(r){
								if(r){
									// 1前台删除
									var  node  = $('#tree_org').treegrid('getSelected');
									$('#tree_org').treegrid('remove',node.id);
									// 2后台删除 
									$.post('OrgServlet?method=delete' , {id:node.id} , function(result){
												
												$('#tree_org').treegrid('unselectAll');
												$('#tree_org').treegrid('reload');
												$.messager.show({
													title:'提示信息',
													msg:'操作成功!'
												});
									});
								} else {
									return ;
								}
								
						});
			}
			
	</script>
  </head>
  
  <body>
  		<table id="tree_org"></table>
  		<div id="div1" title="组合机构" class="easyui-dialog" style="width:400px;" closed=true modal=true >
  				<form id="myform" method="post">
  							<input type="hidden" name="id" value="" />
  						<table>
  							<tr>
  								<td>新机构名称:</td>
  								<td><input name="name" value=""/></td>
  							</tr>
   							<tr>
  								<td>负责人:</td>
  								<td><input name="principal" value="" /></td>
  							</tr>
  							<tr>
  								<td>机构人数:</td>
  								<td><input name="count" value=""/></td>
  							</tr>
  							<tr>
  								<td>机构描述:</td>
  								<td><textarea name="description" cols="30" rows="5"></textarea></td>
  							</tr>
  							<tr align="center">
  								<td colspan="2">
  									<a id="btn1" class="easyui-linkbutton">确定</a>
  									<a id="btn2" class="easyui-linkbutton">取消</a>
  								</td>
  							</tr>  							  							  							 							
  						</table>
  				</form>
  		</div>
  		
  		
		<div id="mm" class="easyui-menu" style="width:120px;">
			<div onclick="append()">新增组织机构</div>
			<div onclick="update()">修改组织机构</div>
			<div onclick="remove()">删除组织机构</div>
		</div>  
  </body>
</html>

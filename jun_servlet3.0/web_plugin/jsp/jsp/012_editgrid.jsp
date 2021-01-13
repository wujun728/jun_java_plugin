<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP '012_editgrid.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	
	<link rel="stylesheet" type="text/css" href="css/common.css" />
	
    <%@ include file="/common/common.jsp" %>
	<script type="text/javascript" src="easyui/js/commons.js"></script>
	
	<script type="text/javascript">  
	
				$(function(){
					
					
					var editing ; //判断用户是否处于编辑状态 
					var flag  ;	  //判断新增和修改方法
					$('#t_user').datagrid({
								idField:'id' ,
								title:'可编辑的数据表格' , 
								fitColumns: true  ,
								url:'UserServlet?method=getList' ,
								striped: true ,					
								loadMsg: '数据正在加载,请耐心的等待...' ,
								rownumbers:true ,
								frozenColumns:[[
										{field:'ck' , checkbox:true}									
								]],
								columns:[[
									{
										field:'username' ,
										title:'用户名' ,
										width:100 ,
										align:'center' ,
										styler:function(value , record){
											if(value == 'admin'){
												return 'background:blue;';
											}
										} ,
										editor:{
											type:'validatebox' ,
											options:{
												required:true , 
												missingMessage:'用户名必填!'
											}
										}
									},
									{
										field:'password' ,
										title:'密码' ,
										width:100  ,
										editor:{
											type:'validatebox' ,
											options:{
												required:true , 
												missingMessage:'密码必填!' 
											}
										}
									},{
										field:'age' ,
										title:'年龄' ,
										width:100 ,
										sortable : true ,
										editor:{
											type:'numberbox' ,
											options:{
												required:true , 
												missingMessage:'年龄必填!' ,
												min:0 , 
												max:150 ,
												precision:0
											}
										}
									},{
										field:'sex' , 
										title:'性别' ,
										width:50 ,
										formatter:function(value , record , index){
														if(value == 1){
															return '<span style=color:red; >男</span>' ;
														} else if( value == 2){
															return '<span style=color:green; >女</span>' ; 
														}
										} , 
										editor:{
											type:'combobox' ,
											options:{
												data:[{id:1 , val:'男'},{id:2 , val:'女'}] ,
												valueField:'id' , 
												textField:'val' ,
												required:true , 
												missingMessage:'性别必填!'
											}
										}
									},{
										field:'birthday' ,
										title:'生日' ,
										width:100 ,
										sortable : true ,
										editor:{
											type:'datebox' , 
											options:{
												required:true , 
												missingMessage:'生日必填!' ,
												editable:false 
											}
										}
									},{
										field:'startTime' , 
										title:'开始时间' , 
										width:150 ,
										editor:{
											type:'datetimebox' , 
											option:{
													required:true , 
													missingMessage:'时间必填!' ,
													editable:false 
											}
										}
									},{
										field:'endTime' , 
										title:'结束时间' ,  
										width:150 ,
										editor:{
											type:'datetimebox' , 
											option:{
													required:true , 
													missingMessage:'时间必填!' ,
													editable:false 
											}
										}
									},{
										field:'city' ,
										title:'所属城市' ,
										width:100 ,
										formatter:function(value , record , index){
														if(value==1){
															return '北京';
														} else if(value == 2){
															return '上海';
														} else if(value == 3){
															return '深圳';
														} else if(value == 4){
															return '长春';
														}
										},
										editor:{
											type:'combobox' ,
											options:{
												url:'UserServlet?method=getCity',
												valueField:'id' , 
												textField:'name' ,
												required:true , 
												missingMessage:'城市必填!'
											}
										}
									},{
										field:'salary' ,
										title:'薪水' ,
										width:100 ,
										editor:{
											type:'numberbox' , 
											options:{
												required:true , 
												missingMessage:'薪水必填!' ,
												min:1000 , 
												max:20000 ,
												precision:2
											}
										}
									},{
										field:'description' ,
										title:'个人描述' ,
										width:150 , 
										formatter:function(value , record , index){
														return '<span title='+value+'>'+value+'</span>';
										} ,
										editor:{
											type:'validatebox' ,
											options:{
												required:true , 
												missingMessage:'描述必填!'
											}
										}
									}
								]] ,
								pagination: true , 
								pageSize: 10 ,
								pageList:[5,10,15,20,50] ,
								toolbar:[
									{
										text:'新增用户',
										iconCls:'icon-add' , 
										handler:function(){
														if(editing == undefined){
															flag = 'add';
															//1 先取消所有的选中状态
															$('#t_user').datagrid('unselectAll');
															//2追加一行
															$('#t_user').datagrid('appendRow',{description:''});
															//3获取当前页的行号
															editing = $('#t_user').datagrid('getRows').length -1;
															//4开启编辑状态
															$('#t_user').datagrid('beginEdit', editing);
														}
												}
									},{
										text:'修改用户',
										iconCls:'icon-edit' , 
										handler:function(){
												var arr = $('#t_user').datagrid('getSelections');
												if(arr.length != 1){
														$.messager.show({
															title:'提示信息',
															msg:'只能选择一条记录进行修改!' 
														});
												} else {
													if(editing == undefined){
														flag = 'edit';
														
														//根据行记录对象获取该行的索引位置
														editing = $('#t_user').datagrid('getRowIndex' , arr[0]);
														//开启编辑状态
														$('#t_user').datagrid('beginEdit',editing);
													}
												}
												
										}										
									},{
										text:'保存用户',
										iconCls:'icon-save' , 
										handler:function(){
												//保存之前进行数据的校验 , 然后结束编辑并师傅编辑状态字段 
												if($('#t_user').datagrid('validateRow',editing)){
														$('#t_user').datagrid('endEdit', editing);
														editing = undefined;
												}
										}
									},{
										text:'删除用户',
										iconCls:'icon-remove' , 
										handler:function(){
											var arr = $('#t_user').datagrid('getSelections');
											if(arr.length <= 0 ){
												$.messager.show({
													title:'提示信息',
													msg:'请选择进行删除操作!'
												});											
											} else {
												$.messager.confirm('提示信息' , '确认删除?' , function(r){
													if(r){
														var ids = '';
														for(var i = 0 ; i < arr.length ; i++){
															ids += arr[i].id + ',';
														}
														ids = ids.substring(0,ids.length-1);
														$.post('UserServlet?method=delete' , {ids:ids},function(result){
															    $('#t_user').datagrid('reload');
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
										}
									},{
										text:'取消操作',
										iconCls:'icon-cancel' , 
										handler:function(){
											//回滚数据 
											$('#t_user').datagrid('rejectChanges');
											editing = undefined;
										}
									}	
								] ,
								onAfterEdit:function(index , record){
									$.post(flag=='add'?'UserServlet?method=save':'UserServlet?method=update' , record , function(result){
											$.messager.show({
												title:'提示信息',
												msg:'操作成功!'
											});
									});
								}
									
								
						});
					
					
					
					
				});
	
	
	
	</script>
  </head>
  
  <body>
 			<table id="t_user"></table>
  </body>
</html>

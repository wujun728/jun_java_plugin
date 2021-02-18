<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP '008_datagrid.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
    <%@ include file="/common/common.jsp" %>
	<script type="text/javascript" src="easyui/js/commons.js"></script>
	<script type="text/javascript">
			$(function(){

					/**
					 * 	对于form表单的验证 
					 */
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
				
					
					var flag ;		//undefined 判断新增和修改方法 
					/**
					 *	初始化数据表格  
					 */
					$('#t_user').datagrid({
							idField:'id' ,		//只要创建数据表格 就必须要加 ifField
							title:'用户列表' ,
							//width:1300 ,
							fit:true ,
							height:450 ,
							url:'UserServlet?method=getList' ,
							fitColumns:true ,  
							striped: true ,					//隔行变色特性 
							//nowrap: false ,				//折行显示 为true 显示在一会 
							loadMsg: '数据正在加载,请耐心的等待...' ,
							rownumbers:true ,
							//singleSelect:true ,				//单选模式 
							//remoteSort: false ,
							//sortName: 'salary' ,
							//sortOrder: 'desc' ,
							rowStyler: function(index ,record){
								 if(record.age > 25){
									 return "background:red";
								 }
								 // console.info(index);
								 // console.info(record);
							} ,
							frozenColumns:[[				//冻结列特性 ,不要与fitColumns 特性一起使用 
								{
									field:'ck' ,
									width:50 ,
									checkbox: true
								}
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
									}
								},
								{
									field:'password' ,
									title:'密码' ,
									width:100 ,
									hidden: true
								},{
									field:'age' ,
									title:'年龄' ,
									width:100 ,
									sortable : true 
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
											//console.info(value);
											//console.info(record);
											//console.info(index); 
									}
								},{
									field:'birthday' ,
									title:'生日' ,
									width:100 ,
									sortable : true
								},{
									field:'startTime' , 
									title:'开始时间' , 
									width:150
								},{
									field:'endTime' , 
									title:'结束时间' ,  
									width:150
								},{
									field:'city' ,
									title:'所属城市' ,
									width:100 ,
									formatter:function(value , record , index){
										/*
										if(value==1){
											return '北京';
										} else if(value == 2){
											return '上海';
										} else if(value == 3){
											return '深圳';
										} else if(value == 4){
											return '长春';
										}
										*/
										var str = '';
										$.ajax({
											type:'post' , 
											url : 'UserServlet?method=getCityName' ,
											cache:false ,
											async: false ,		//同步请求
											data:{id:value},
											dataType:'json' ,
											success:function(result){
												str = result.name ;
											}
										});
										return str ;
									}
								},{
									field:'salary' ,
									title:'薪水' ,
									width:100
								},{
									field:'description' ,
									title:'个人描述' ,
									width:150 , 
									formatter:function(value , record , index){
										return '<span title='+value+'>'+value+'</span>';
									}
								}
							]] ,
							pagination: true , 
							pageSize: 10 ,
							pageList:[5,10,15,20,50] ,
							toolbar:[
								{
									text:'新增用户' ,
									iconCls:'icon-add' , 
									handler:function(){
										flag = 'add';
										$('#mydialog').dialog({
												title:'新增用户' 
										});
										//$('#myform').find('input[name!=sex]').val("");
										$('#myform').get(0).reset();
										//$('#myform').form('clear');
										$('#mydialog').dialog('open');
									
									}
									
								},{
									text:'修改用户' ,
									iconCls:'icon-edit' , 
									handler:function(){
										flag = 'edit';
										var arr =$('#t_user').datagrid('getSelections');
										if(arr.length != 1){
											$.messager.show({
												title:'提示信息!',
												msg:'只能选择一行记录进行修改!'
											});
										} else {
											$('#mydialog').dialog({
												title:'修改用户'
											});
											$('#mydialog').dialog('open'); //打开窗口
											$('#myform').get(0).reset();   //清空表单数据 
											$('#myform').form('load',{	   //调用load方法把所选中的数据load到表单中,非常方便
												id:arr[0].id ,
												username:arr[0].username ,
												password:arr[0].password ,
												sex:arr[0].sex ,
												age:arr[0].age ,
												birthday:arr[0].birthday ,
												city:arr[0].city ,
												salary:arr[0].salary ,
												startTime:arr[0].startTime,
												endTime:arr[0].endTime ,
												description:arr[0].description
											});
										}
									
									}
								},{
									text:'删除用户' ,
									iconCls:'icon-remove' , 
									handler:function(){
											var arr =$('#t_user').datagrid('getSelections');
											if(arr.length <=0){
												$.messager.show({
													title:'提示信息!',
													msg:'至少选择一行记录进行删除!'
												});
											} else {
												
												$.messager.confirm('提示信息' , '确认删除?' , function(r){
														if(r){
																var ids = '';
																for(var i =0 ;i<arr.length;i++){
																	ids += arr[i].id + ',' ;
																}
																ids = ids.substring(0 , ids.length-1);
																$.post('UserServlet?method=delete' , {ids:ids} , function(result){
																	//1 刷新数据表格 
																	$('#t_user').datagrid('reload');
																	//2 清空idField   
																	$('#t_user').datagrid('unselectAll');
																	//3 给提示信息 
																	$.messager.show({
																		title:'提示信息!' , 
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
									text:'查询用户' , 
									iconCls:'icon-search' , 
									handler:function(){
											$('#lay').layout('expand' , 'north');
									}
								}	
							]
					});
					
					
					/**
					 *  提交表单方法
					 */
					$('#btn1').click(function(){
							if($('#myform').form('validate')){
								$.ajax({
									type: 'post' ,
									url: flag=='add'?'UserServlet?method=save':'UserServlet?method=update' ,
									cache:false ,
									data:$('#myform').serialize() ,
									dataType:'json' ,
									success:function(result){
										//1 关闭窗口
										$('#mydialog').dialog('close');
										//2刷新datagrid 
										$('#t_user').datagrid('reload');
										//3 提示信息
										$.messager.show({
											title:result.status , 
											msg:result.message
										});
									} ,
									error:function(result){
										$.meesager.show({
											title:result.status , 
											msg:result.message
										});
									}
								});
							} else {
								$.messager.show({
									title:'提示信息!' ,
									msg:'数据验证不通过,不能保存!'
								});
							}
					});
					
					/**
					 * 关闭窗口方法
					 */
					$('#btn2').click(function(){
						$('#mydialog').dialog('close');
					});
					
						
					$('#searchbtn').click(function(){
						$('#t_user').datagrid('load' ,serializeForm($('#mysearch')));
					});
					
					
					$('#clearbtn').click(function(){
						$('#mysearch').form('clear');
						$('#t_user').datagrid('load' ,{});
					});
					

			});
			
			
			
		
			//js方法：序列化表单 			
			function serializeForm(form){
				var obj = {};
				$.each(form.serializeArray(),function(index){
					if(obj[this['name']]){
						obj[this['name']] = obj[this['name']] + ','+this['value'];
					} else {
						obj[this['name']] =this['value'];
					}
				});
				return obj;
			}

	</script>
  </head>
  
  <body>
			<div id="lay" class="easyui-layout" style="width: 100%;height:100%" >
				<div region="north" title="用户查询" collapsed=true style="height:100px;" >
					<form id="mysearch" method="post">
							用户名:&nbsp;&nbsp;&nbsp;<input name="username" class="easyui-validatebox"  value="" />
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							开始时间:<input name="startTime"  class="easyui-datetimebox" editable="false" style="width:160px;"  value="" />	
							结束时间:<input name="endTime"  class="easyui-datetimebox" editable="false" style="width:160px;"  value="" />	
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							<a id="searchbtn" class="easyui-linkbutton">查询</a> <a id="clearbtn" class="easyui-linkbutton">清空</a>
					</form>
				
				</div>
				<div region="center" >
					<table id="t_user"></table>
				</div>
			</div>
  			
 			<div id="mydialog" title="新增用户" modal=true  draggable=false class="easyui-dialog" closed=true style="width:300px;">
	    		<form id="myform" action="" method="post">
	    				<input type="hidden" name="id" value="" />
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
	    							<a id="btn1" class="easyui-linkbutton">确定</a>
	    							<a id="btn2" class="easyui-linkbutton">关闭</a>
	    						</td>
	    					</tr>   					 					    					    					    					    					    					    					    					
	    				</table>
	    		</form> 			
 			</div>
  </body>
</html>

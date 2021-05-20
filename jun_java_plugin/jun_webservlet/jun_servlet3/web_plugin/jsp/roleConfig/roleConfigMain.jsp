<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"  "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <base href="<%=basePath%>">
    <title>权限编辑</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<jsp:include page="../../layout/script.jsp"></jsp:include>
	<script type="text/javascript">
		var $role;
		var $user;
		$(function() {
			$("#panel").panel({   
				   width:'auto',   
				   height:$(this).height(),   
				   title: '角色分配',   
			});
			$user = $("#user");
			$user.datagrid({
				url : "userController.do?method=findAllUserList",
				width : 'auto',
				height : $(this).height()-120,
				pagination:true,
				rownumbers:true,
				singleSelect:true,
				striped:true,
				border:false,
				columns : [ [ {field : 'myid',title : '用户编码',width : parseInt($(this).width()*0.1),align : 'left',editor : "text"},
				              {field : 'account',title : '用户账号',width : parseInt($(this).width()*0.1),align : 'left',editor : "text"},
				              {field : 'name',title : '用户名',width : parseInt($(this).width()*0.1),editor : {type:'validatebox',options:{required:true}}},
				             // {field : 'password',title : '用户密码',width : 100,editor : "validatebox"},
				              //{field : 'email',title : '邮箱',width : 150,align : 'left',editor : {type:'validatebox',options:{required:true,validType:'email'}}},
				             // {field : 'tel',title : '电话',width : 100,align : 'left',editor : "text"},
							  {field : 'organizeName',title : '组织部门',width : parseInt($(this).width()*0.1),align : 'left',editor : "text"},
				              {field : 'description',title : '描述',width : parseInt($(this).width()*0.1),align : 'left',editor : "text"}
				              ] ],toolbar:"#tbUser",onDblClickRow:getRoles
			});
			$role = $("#role");
			$role.datagrid({
					url : "permissionController.do?method=findAllRoleListNotPage",
					width : 'auto',
					height : $(this).height()-120,
					pagination:false,
					border:false,
					rownumbers:true,
					singleSelect:false,
					striped:true,
					idField: 'roleId',
					columns : [ [ {field:'ck',checkbox:true},
					              {field : 'name',title : '角色名称',width :  parseInt($(this).width()*0.1),align : 'center',editor : {type:'validatebox',options:{required:true}}},
					              //{field : 'sort',title : '排序',width : parseInt($(this).width()*0.1),align : 'center',editor : "numberbox"},
					              {field : 'description',title : '角色描述',width :  parseInt($(this).width()*0.1),align : 'center',editor : "text"}
					              ] ],toolbar:"#tbRole"
			});
			//搜索框
			$("#searchbox").searchbox({ 
				 menu:"#mm", 
				 prompt :'模糊查询',
			    searcher:function(value,name){   
			    	var str="{\"searchName\":\""+name+"\",\"searchValue\":\""+value+"\"}";
		            var obj = eval('('+str+')');
		            $user.datagrid('reload',obj); 
			    }
			   
			});
			//搜索框
			$("#searchbox2").searchbox({ 
				 menu:"#mm2", 
				 prompt :'模糊查询',
			    searcher:function(value,name){   
			    	var str="{\"searchName\":\""+name+"\",\"searchValue\":\""+value+"\"}";
		            var obj = eval('('+str+')');
		            $role.datagrid('reload',obj); 
			    }
			   
			});
		});
		
		 function saveUserRoles(){
			 var selectRow=$user.datagrid("getSelected");
			 var selectRows=$role.datagrid("getSelections");
			 var isCheckedIds=[];
			 $.each(selectRows,function(i,e){
				 isCheckedIds.push(e.roleId);
			 });
			 if(selectRow){
				 $.ajax({
						url:"userController.do?method=saveUserRoles",
						data: "userId="+selectRow.userId+"&isCheckedIds="+isCheckedIds,
						success: function(rsp){
								parent.$.messager.show({
									title :rsp.title,
									msg :rsp.message,
									timeout : 1000 * 2
								});
						},
						error:function(){
							parent.$.messager.show({
								title :"提示",
								msg :"保存用户角色失败！",
								timeout : 1000 * 2
							});
						}

					});
			 }else{
				 parent.$.messager.show({
						title :"提示",
						msg :"请选择角色！",
						timeout : 1000 * 2
					});
			 }
			 
			 /*$.post("user/userAction!saveUserRoles.action", {userId:selectRow.userId,isCheckedIds:isCheckedIds}, function(rsp) {
				 $.messager.alert(rsp.title, rsp.message);
				}, "JSON").error(function() {
					$.messager.alert("提示", "保存用户角色失败！");
				});*/
		 }
		 function getRoles(rowIndex, rowData){
			 $.post("userController.do?method=findUsersRolesList", {userId:rowData.userId}, function(rsp) {
					 $role.datagrid("unselectAll");
				 if(rsp.length!=0){
					 $.each(rsp,function(i,e){
						 $role.datagrid("selectRecord",e.roleId);
					 });
				 }else{
					 parent.$.messager.show({
							title :"提示",
							msg : "该用户暂无角色！",
							timeout : 1000 * 2
						});
				 }
				}, "JSON").error(function() {
					 parent.$.messager.show({
							title :"提示",
							msg : "获取用户角色失败！",
							timeout : 1000 * 2
						});
				});
		}
	</script>
  </head>
  <body>
  
   <div id="panel"  data-options="border:false">
		<div class="easyui-layout" data-options="fit:true">
		<div data-options="region:'north',border:false" title="" style="height: 82px; overflow: hidden; padding: 5px;">
			<div class="well well-small">
				<span class="badge">提示</span>
				<p>
					为用户分配角色，请<span class="label-info"><strong>双击用户</strong></span>查看所属角色！
					超级管理员默认拥有<span class="label-info"><strong>所有权限！</strong></span>
				</p>
			</div>
		</div>
			<div data-options="region:'west',split:true,border:true" style="width:800px;">
				<div id="tbUser" style="padding:2px 0">
					<table cellpadding="0" cellspacing="0">
						<tr>
							<td style="padding-left:2px">
								<shiro:hasPermission name="userRoleConfig">
									<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-config" plain="true" onclick="saveUserRoles();">保存设置</a>
								</shiro:hasPermission>
							</td>
							<td style="padding-left:2px">
								<input id="searchbox" type="text"/>
							</td>
						</tr>
					</table>
				</div>
				<div id="mm">
						<div name="myid">用户编码</div>
						<div name="account">用户账户</div>
						<div name="name">用户名</div>
						<div name="organizeName">组织</div>
						<div name="description">描述</div>
				</div>
				<table id="user" title="用户"></table>
			</div>
			<div data-options="region:'center',border:true">
				<div id="tbRole" style="padding:2px 0">
					<table cellpadding="0" cellspacing="0">
						<tr>
							<td style="padding-left:4px;padding-bottom:4px;">
								<input id="searchbox2" type="text"/>
							</td>
						</tr>
					</table>
				</div>
				<div id="mm2">
						<div name="name">角色名称</div>
						<div name="description">角色描述</div>
				</div>
				<table id="role" title="角色"></table>
			</div>
		</div>
	</div>
  </body>
  </body>
</html>

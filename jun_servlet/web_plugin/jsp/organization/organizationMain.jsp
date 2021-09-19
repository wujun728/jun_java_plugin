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
    <title>组织管理</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<jsp:include page="../../layout/script.jsp"></jsp:include>
		<script type="text/javascript">
			var $dg;
			var $grid;
			$(function() {
				 $dg = $("#dg");
				 $grid=$dg.treegrid({
					width : 'auto',
					height : $(this).height()-90,
					url : "organizationController.do?method=findOrganizationListTreeGrid",
					rownumbers:true,
					animate: true,
					collapsible: true,
					fitColumns: true,
					striped:true,
					border:true,
					//singleSelect:false,
					idField: 'organizationId',
					treeField: 'fullName',
					 frozenColumns:[[
					                 {title:'组织名称',field:'fullName',width:parseInt($(this).width()*0.2),
					                  formatter:function(value){
					                   return '<span style="color:purple">'+value+'</span>';
					                  }
					                 }
					    ]],
					columns : [ [ 
					              {field : 'myid',title : '组织编码',width : parseInt($(this).width()*0.1)},
					              {field : 'ename',title : '英文名称',width : parseInt($(this).width()*0.1),align : 'left'},
					              {field : 'shortName',title : '简称',width : parseInt($(this).width()*0.1),align : 'left'},
					              //{field : 'empQty',title : '编制数量',width : parseInt($(this).width()*0.1),align : 'left'},
					              {field : 'iconCls',title : '组织图标',align : 'center',width : parseInt($(this).width()*0.1),
					            	  formatter:function(value,row){
					            		  return "<span class='"+row.iconCls+"' style='display:inline-block;vertical-align:middle;width:16px;height:16px;'></span>";
										}},
					              {field : 'tel',title : '电话',width : parseInt($(this).width()*0.1),align : 'left'},
					              {field : 'fax',title : '传真',width : parseInt($(this).width()*0.1),align : 'left'},
					              {field : 'description',title : '程式描述',width : parseInt($(this).width()*0.2),align : 'left'}
					              ] ],toolbar:'#tb'
				});
			});
			function delRows(){
				var node = $dg.treegrid('getSelected');
				if(node){
					parent.$.messager.confirm("提示","确定要删除记录吗?",function(r){  
					    if (r){  
							$.post("organizationController.do?method=delOrganization", {id:node.organizationId}, function(rsp) {
								if(rsp.status){
									$dg.treegrid('remove', node.organizationId);
								}
								parent.$.messager.show({
									title : rsp.title,
									msg : rsp.message,
									timeout : 1000 * 2
								});
							}, "JSON").error(function() {
								parent.$.messager.show({
									title :"提示",
									msg :"提交错误了！",
									timeout : 1000 * 2
								});
							});
					    }  
					});
				}else{
					parent.$.messager.show({
						title :"提示",
						msg :"请选择一行记录!",
						timeout : 1000 * 2
					});
				}
			}
			//弹窗修改
			function updRowsOpenDlg() {
				var row = $dg.treegrid('getSelected');
				if (row) {
					parent.$.modalDialog({
						title : "编辑组织",
						width : 600,
						height : 400,
						href : "jsp/organization/organizationEditDlg.jsp",
						onLoad:function(){
							var f = parent.$.modalDialog.handler.find("#form");
							f.form("load", row);
						},			
						buttons : [ {
							text : '编辑',
							iconCls : 'icon-ok',
							handler : function() {
								parent.$.modalDialog.openner= $grid;//因为添加成功之后，需要刷新这个treegrid，所以先预定义好
								var f = parent.$.modalDialog.handler.find("#form");
								f.submit();
							}
						}, {
							text : '取消',
							iconCls : 'icon-cancel',
							handler : function() {
								parent.$.modalDialog.handler.dialog('destroy');
								parent.$.modalDialog.handler = undefined;
							}
						}
						]
					});
				}else{
					parent.$.messager.show({
						title :"提示",
						msg :"请选择一行记录!",
						timeout : 1000 * 2
					});
				}
			}
			//弹窗增加
			function addRowsOpenDlg() {
				var row = $dg.treegrid('getSelected');
				parent.$.modalDialog({
					title : "添加组织",
					width : 600,
					height : 400,
					href : "jsp/organization/organizationEditDlg.jsp",
					onLoad:function(){
						if(row){
							var f = parent.$.modalDialog.handler.find("#form");
							f.form("load", {"pid":row.organizationId});
						}
					},
					buttons : [ {
						text : '保存',
						iconCls : 'icon-ok',
						handler : function() {
							parent.$.modalDialog.openner= $grid;//因为添加成功之后，需要刷新这个treegrid，所以先预定义好
							var f = parent.$.modalDialog.handler.find("#form");
							f.submit();
						}
					}, {
						text : '取消',
						iconCls : 'icon-cancel',
						handler : function() {
							parent.$.modalDialog.handler.dialog('destroy');
							parent.$.modalDialog.handler = undefined;
						}
					}
					]
				});
			}
		</script>
  </head>
  <body>
  	<div class="well well-small" style="margin-left: 5px;margin-top: 5px">
				<span class="badge">提示</span>
				<p>
					在此你可以对<span class="label-info"><strong>组织功能</strong></span>进行编辑!
				</p>
			</div>
    <div id="tb" style="padding:10px;height:auto">
			<div style="margin-bottom:5px">
			<shiro:hasPermission name="orgzAdd">
				<!--  <a href="javascript:void(0);" class="easyui-splitbutton" data-options="menu:'#mm1',iconCls:'icon-add'">添加</a>
				<div id="mm1" style="width:150px;">
					<div data-options="iconCls:'icon-undo'" onclick="addStandPlaceNode();">增加并列项</div>
					<div data-options="iconCls:'icon-redo'" onclick="addSubitemNode();">增加子项</div>
				</div>-->
				<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="addRowsOpenDlg();">添加</a>
			</shiro:hasPermission>
			<shiro:hasPermission name="orgzEdit">
				<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="updRowsOpenDlg();">编辑</a>
			</shiro:hasPermission>
			<shiro:hasPermission name="orgzDel">
				<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="delRows();">删除</a>
			</shiro:hasPermission>
		<!-- <shiro:hasPermission name="funEndEdit">
				<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-cancel" plain="true" onclick="endEdit();">结束编辑</a>
			</shiro:hasPermission>
			<shiro:hasPermission name="funSave">
				<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-save" plain="true" onclick="saveNodes();">保存</a>
			</shiro:hasPermission> -->	
			</div>
			
		</div>
  		<table id="dg" title="组织管理"></table>
  </body>
</html>

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
    <title>词典管理</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<jsp:include page="../../layout/script.jsp"></jsp:include>
		<script type="text/javascript">
			var $dg;
			var $grid;
			var typedata=[{"type":"F","typeName":"菜单"},{"type":"O","typeName":"操作"}];
			$(function() {
				 $dg = $("#dg");
				 $grid=$dg.treegrid({
					width : 'auto',
					height : $(this).height()-90,
					url : "systemCodeController.do?method=findSystemCodeList",
					rownumbers:true,
					animate: true,
					collapsible: true,
					fitColumns: true,
					striped:true,
					border:true,
					//singleSelect:false,
					idField: 'codeId',
					treeField: 'name',
					 frozenColumns:[[
					                 {title:'词典名称',field:'name',align : 'left',width:parseInt($(this).width()*0.2),
					                  formatter:function(value){
					                   return '<span style="color:red">'+value+'</span>';
					                  }
					                 }
					    ]],
					columns : [ [ {field : 'codeMyid',title : '词典编码',width : parseInt($(this).width()*0.1),align : 'left'},
					              {field : 'sort',title : '排序编码',width : parseInt($(this).width()*0.1)},
					              {field : 'description',title : '词典描述',width : parseInt($(this).width()*0.2),align : 'left'}
					              ] ],toolbar:'#tb'
				});
			});
			function removeNode(){
				var node = $dg.treegrid('getSelected');
				if(node){
					$.post("systemCodeController.do?method=delSystemCode", {codeId:node.codeId}, function(rsp) {
						if(rsp.status){
							$dg.treegrid('remove', node.codeId);
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
					if(typeof(row.parentId)=="undefined"){
						parent.$.messager.show({
							title :"提示",
							msg :"模块不允许修改!",
							timeout : 1000 * 2
						});
					}else{
						parent.$.modalDialog({
							title : "编辑词典",
							width : 600,
							height : 400,
							href : "jsp/systemCode/systemCodeEditDlg.jsp",
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
					}
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
					title : "添加词典",
					width : 600,
					height : 400,
					href : "jsp/systemCode/systemCodeEditDlg.jsp?tempId="+(row?row.permissionId:""),
					onLoad:function(){
						if(row){
						var	flag=typeof(row.parentId)=="undefined";
						var f = parent.$.modalDialog.handler.find("#form");
						f.form("load", {"parentId":flag?null:row.codeId,"permissionId":row.permissionId,"codePid":row.codeId});
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
					在此你可以对<span class="label-info"><strong>数据词典</strong></span>进行编辑! 
				</p>
			</div>
    <div id="tb" style="padding:10px;height:auto">
			<div style="margin-bottom:5px">
			<shiro:hasPermission name="dicAdd">
				<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="addRowsOpenDlg();">添加</a>
			</shiro:hasPermission>
			<shiro:hasPermission name="dicEdit">
				<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="updRowsOpenDlg();">编辑</a>
			</shiro:hasPermission>
			<shiro:hasPermission name="dicDel">
				<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="removeNode();">删除</a>
			</shiro:hasPermission>
			</div>
			
		</div>
  		<table id="dg" title="词典管理"></table>
  </body>
</html>

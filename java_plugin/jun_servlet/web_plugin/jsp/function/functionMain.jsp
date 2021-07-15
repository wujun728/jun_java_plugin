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
    <title>菜单管理</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<jsp:include page="../../layout/script.jsp"></jsp:include>
	<script type="text/javascript" src="jsp/function/js/functionMain.js"></script>
  </head>
  <body>
  	<div class="well well-small" style="margin-left: 5px;margin-top: 5px">
				<span class="badge">提示</span>
				<p>
					在此你可以对<span class="label-info"><strong>菜单功能</strong></span>进行编辑!  &nbsp;<span class="label-info"><strong>注意</strong></span>操作功能是对菜单功能的操作权限！
					请谨慎填写程序编码，权限区分标志，请勿重复!
				</p>
			</div>
    <div id="tb" style="padding:0px;height:auto">
			<div style="margin-bottom:5px">
			<shiro:hasPermission name="funAdd">
				<!--  <a href="javascript:void(0);" class="easyui-splitbutton" data-options="menu:'#mm1',iconCls:'icon-add'">添加</a>
				<div id="mm1" style="width:150px;">
					<div data-options="iconCls:'icon-undo'" onclick="addStandPlaceNode();">增加并列项</div>
					<div data-options="iconCls:'icon-redo'" onclick="addSubitemNode();">增加子项</div>
				</div>-->
				<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="addRowsOpenDlg();">添加</a>
			</shiro:hasPermission>
			<shiro:hasPermission name="funCopy">
				<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="cpdRowsOpenDlg();">复制</a>
			</shiro:hasPermission>
			<shiro:hasPermission name="funEdit">
				<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="updRowsOpenDlg();">编辑</a>
			</shiro:hasPermission>
			<shiro:hasPermission name="funDel">
				<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="removeNode();">删除</a>
			</shiro:hasPermission>
		<!-- <shiro:hasPermission name="funEndEdit">
				<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-cancel" plain="true" onclick="endEdit();">结束编辑</a>
			</shiro:hasPermission>
			<shiro:hasPermission name="funSave">
				<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-save" plain="true" onclick="saveNodes();">保存</a>
			</shiro:hasPermission> -->	
			</div>
			
		</div>
  		<table id="dg" title="菜单管理"></table>
  </body>
</html>

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
    <title>备份日志管理</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<jsp:include page="../../layout/script.jsp"></jsp:include>
	<script type="text/javascript" src="jsp/dbBackUp/js/dbBackUpMain.js"></script>
	<link rel="stylesheet" href="jsp/dbBackUp/css/dbBackUpMain.css" type="text/css"></link>
  </head>
  <body>
      <div data-options="region:'center',border : false">
  		<div class="well well-small" style="margin-left: 5px;margin-top: 5px">
				<span class="badge">提示</span>
				<p>
					在此你可以对数据进行<span class="label-info"><strong>备份!有两种备份方式定时备份和手动备份，手动备份不影响定时备份！</strong></span>
				</p>
		</div>
		<div id="tb" style="padding:2px 0">
			<table cellpadding="0" cellspacing="0">
				<tr>
					<td style="padding-left:2px">
						<shiro:hasPermission name="dbBackUpHand">
							<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-time" plain="true" onclick="addRowsOpenDlg();">定时备份</a>
						</shiro:hasPermission>
						<shiro:hasPermission name="dbBackUpTime">
							<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-auto" plain="true" onclick="updRowsOpenDlg();">手动备份</a>
						</shiro:hasPermission>
					</td>
					<td style="padding-left:2px">
						<input id="searchbox" type="text"/>
					</td>
					<!--  <td style="padding-left:2px">
						<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-search" plain="true" onclick="tbsCompanySearch();">高级查询</a>
					</td>-->
				</tr>
			</table>
		</div>
		<div id="mm">
				<div name="name">日志名称</div>
				<div name="logDate">创建日期</div>
				<div name="type">类型</div>
				<div name="eventName">操作名称</div>
				<div name="eventRecord">操作描述</div>
		</div>
		<table id="dg" title="备份管理"></table>
  	</div>	
  </body>
</html>

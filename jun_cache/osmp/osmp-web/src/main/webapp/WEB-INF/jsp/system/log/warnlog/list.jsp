<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<%@include file="/WEB-INF/jsp/pubtag.jsp"%>
</head>
<body>
<div class="easyui-layout" fit="true">
	<div region="center" style="padding: 1px;">
		<z:dataGrid dataGridType="datagrid" name="warnlog" actionUrl="${BASE_PATH}/warnlog/warnList.do" title="告警日志" showPageList="false">
			<z:toolBar title="新增" jsName="Add('${BASE_PATH}/warnlog/toAdd.do','warnlog','datagrid',500,400,'WarnLogAddForm','${BASE_PATH}/warnlog/addWarnLog.do')" icon="icon-add"></z:toolBar>
			<z:dataGridColumn field="id" title="ID" width="20" ></z:dataGridColumn>
			<z:dataGridColumn field="from" title="来源" width="150" align="left"></z:dataGridColumn>
			<z:dataGridColumn field="message" title="消息" width="30"></z:dataGridColumn>
			<z:dataGridColumn field="exception" title="异常" width="30" replace="正常_1,停止_2,异常_3"></z:dataGridColumn>
			<z:dataGridColumn field="recordTime" title="记录时间" width="30" formatter="timeFormat"></z:dataGridColumn>
			<z:dataGridColumn field="insertTime" title="插入时间" width="30" formatter="timeFormat"></z:dataGridColumn>
			<z:dataGridColumn field="opt" title="操作" width="100"></z:dataGridColumn>
			<z:dataGridOpt name="删除" jsName="Del('${BASE_PATH}/area/deleteArea.do?id=&quot;+rowData.id+&quot;','warnlog','datagrid')"></z:dataGridOpt>
		</z:dataGrid>
	</div>
</div>
</body>
</html>

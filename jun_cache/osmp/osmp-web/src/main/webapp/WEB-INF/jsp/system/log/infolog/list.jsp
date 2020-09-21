<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<%@include file="/WEB-INF/jsp/pubtag.jsp"%>
</head>
<body>
<script type="text/javascript">
	function dateformat(index,row,ss){
		var temp = zznode.util.dateFormatter(new Date(index),'yyyy-MM-dd hh:mm:ss');
		return temp;
	}
	var displayAll = function() {
    	$('#bundle').datagrid({
        	queryParams : {
            	displayAll : 'Y'
           	}
       	});
   	}
	var checkData=function(url){
		$('<div/>').dialog({
			href:url,
			title:'详细信息',
			modal:true,
			width:300,
			height:200
		});
	}

	var check=function(){
		var log=serializeObject($('#log_searchForm'));
		$('#infolog').datagrid({
			queryParams:log
		});
	}
	var date = new Date();
	date.setDate(date.getDate()-1);
	$(function(){
		$('#startTime').val(zznode.util.dateFormatter(date,'yyyy-MM-dd hh:mm:ss'));
		$('#endTime').val(zznode.util.dateFormatter(new Date(),'yyyy-MM-dd hh:mm:ss'));
	});
	
	
</script>
<div class="easyui-layout" fit="true">
	<div region="north" style="padding-top: 6px;padding-left:6px;height:140px;" title="查询条件">
		<form id="log_searchForm">
		<div>
			插件名称：
			<input id="bundle" name="bundle" style="width: 120px;" />
			<span style="padding-left:10px;">服务名：</span>
			<input id="service" name="service" style="width: 210px;" />
			<span style="padding-left:10px;">IP地址：</span>
			<input id="loadIp" name="loadIp" style="width: 210px;" />
		</div>
		<div  style="padding-top:6px;">
			<span style="padding-right:12px;">按发生时间查询：</span>
			<input name="startTime" id="startTime" class="Wdate" style="width:150px;" onFocus="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})" />
			<span style="padding-left:5px;padding-right:5px;">至</span><input name="endTime" id="endTime" class="Wdate" style="width:150px;" onFocus="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})" />
			<span style="padding-left:10px;"></span><a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search'" onclick="check()">查询</a> 
		</div>
		</form>
	</div>
	<div region="center" style="padding: 1px;">
		<z:dataGrid dataGridType="datagrid" name="infolog" actionUrl="${BASE_PATH}/infoLog/infoLogList.do" 
			title="INFO日志" checkbox="false" showPageList="true">
			<z:dataGridColumn field="id" title="ID" width="20" ></z:dataGridColumn>
			<z:dataGridColumn field="bundle" title="插件名" width="40" ></z:dataGridColumn>
			<z:dataGridColumn field="service" title="服务名" width="50"></z:dataGridColumn>
			<z:dataGridColumn field="message" title="日志信息" width="150" align="left"></z:dataGridColumn>
			<z:dataGridColumn field="occurTime" title="发生时间" width="40" formatter="dateformat"></z:dataGridColumn>
			<z:dataGridColumn field="insertTime" title="记录时间" width="40" formatter="dateformat"></z:dataGridColumn>
			<z:dataGridColumn field="loadIp" title="IP地址" width="30"></z:dataGridColumn>
			<z:dataGridColumn field="opt" title="操作" width="50"></z:dataGridColumn>
			<z:dataGridOpt name="删除" jsName="Del('${BASE_PATH}/infoLog/deleteInfoLog.do?id=&quot;+rowData.id+&quot;','infolog','datagrid')"></z:dataGridOpt>
		</z:dataGrid>
	</div>
</div>
</body>
</html>
<script tyoe="text/javascript">
$(function(){
	$('#infolog').datagrid({
		onDblClickRow: function (index, row) {
			OpenPlainWindow(BASE_PATH+'/infoLog/toInfoLogDetail.do?id='+row.id,650,400,'日志详情','infoLogDetail');
        }
	});
});
</script>

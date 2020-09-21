<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="/WEB-INF/jsp/pubtag.jsp"%>
</head>
<body>
<script type="text/javascript">
	var displayAll = function() {
    	$('#bundle').datagrid({
        	queryParams : {
            	displayAll : 'Y'
           	}
       	});
   	}
	
	function fresh(){
		$("#dataService").datagrid("reload");
	}
	
	var showIcon = function(value,rec,index){
		if(value == 1){
			return '<img border="0" src="${BASE_PATH}/images/alarm/green.png"/>'
		}else{
			return '<img border="0" src="${BASE_PATH}/images/alarm/red.png"/>'
		}
	}
</script>

    <div class="easyui-layout" fit="true">
        <div region="center" style="padding: 1px;">
            <z:dataGrid dataGridType="datagrid" name="dataService" actionUrl="${BASE_PATH}/dataService/dataServiceList.do?serverIp=${serverIp}"
                title="服务列表" checkbox="false"  >
                <z:toolBar title="刷新服务" jsName="fresh()" icon="icon-reload"></z:toolBar>
                <z:dataGridColumn field="id" title="ID" width="50"></z:dataGridColumn>
                <z:dataGridColumn field="bundle" title="组件" width="50" align="left"></z:dataGridColumn>
                <z:dataGridColumn field="name" title="服务" width="50"></z:dataGridColumn>
                <z:dataGridColumn field="mark" title="服务名称" width="100" align="left"></z:dataGridColumn>
                <z:dataGridColumn field="state" title="状态" width="50" formatter="showIcon"></z:dataGridColumn>
                <z:dataGridColumn field="loadIP" title="服务器IP" width="50"></z:dataGridColumn>
            </z:dataGrid>
        </div>
    </div>
</body>
</html>

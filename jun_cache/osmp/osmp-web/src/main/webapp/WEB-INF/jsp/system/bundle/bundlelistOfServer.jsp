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
		
		var showIcon = function(value,rec,index){
			if(value == 'Active'){
				return '<img border="0" src="${BASE_PATH}/images/alarm/green.png"/>'
			}else{
				return '<img border="0" src="${BASE_PATH}/images/alarm/red.png"/>'
			}
		}
		
		function fresh() {
			$("#bundle").datagrid("reload");
		}
		
	</script>

	<div class="easyui-layout" fit="true">
        <div region="center" style="padding: 1px;">
            <z:dataGrid dataGridType="datagrid" name="bundle" actionUrl="${BASE_PATH}/bundle/bundleList.do?serverIp=${serverIp}"
                title="BUNDLE列表" checkbox="false" showPageList="false">
                <z:toolBar title="安装/升级"
                    jsName="Add('${BASE_PATH}/bundle/toInstall.do','bundle','datagrid',500,300,'BundleUpdateForm','${BASE_PATH}/bundle/installOrUpdate.do?serverIp=${serverIp}')"
                    icon="icon-add"></z:toolBar>
                <z:toolBar title="显示系统组件" jsName="displayAll()" icon="icon-edit"></z:toolBar>
                <z:toolBar title="刷新组件" jsName="fresh()" icon="icon-reload"></z:toolBar>
                <z:dataGridColumn field="id" title="ID" width="50"></z:dataGridColumn>
                <z:dataGridColumn field="name" title="名称" width="200" align="left"></z:dataGridColumn>
                <z:dataGridColumn field="version" title="版本" width="50"></z:dataGridColumn>
                <z:dataGridColumn field="category" title="类别" width="50"></z:dataGridColumn>
                <z:dataGridColumn field="state" title="状态" width="50" formatter="showIcon" ></z:dataGridColumn>
                <z:dataGridColumn field="opt" title="操作" width="150"></z:dataGridColumn>
                <z:dataGridOpt name="停止" filter="state_Active"
                    jsName="ajaxSubmitConfirm('${BASE_PATH}/bundle/optionBundle.do?id=&quot;+rowData.id+&quot;&flag=stop&serverIp=${serverIp}','bundle','datagrid','停止')"></z:dataGridOpt>
                <z:dataGridOpt name="启动" filter="state_Resolved|Installed"
                    jsName="ajaxSubmitConfirm('${BASE_PATH}/bundle/optionBundle.do?id=&quot;+rowData.id+&quot;&flag=start&serverIp=${serverIp}','bundle','datagrid','启动')"></z:dataGridOpt>
                <z:dataGridOpt name="刷新"
                    jsName="ajaxSubmitConfirm('${BASE_PATH}/bundle/optionBundle.do?id=&quot;+rowData.id+&quot;&flag=refresh&serverIp=${serverIp}','bundle','datagrid','刷新')"></z:dataGridOpt>
                <z:dataGridOpt name="卸载"
                    jsName="ajaxSubmitConfirm('${BASE_PATH}/bundle/optionBundle.do?id=&quot;+rowData.id+&quot;&flag=uninstall&serverIp=${serverIp}','bundle','datagrid','卸载')"></z:dataGridOpt>
                <z:dataGridOpt name="查看详情"
                    jsName="OpenPlainWindow('${BASE_PATH}/bundle/toDetail.do?id=&quot;+rowData.id+&quot;&serverIp=${serverIp}',800,600,'详情','BundleDetail')"></z:dataGridOpt>
            </z:dataGrid>
        </div>
    </div>
</body>
</html>

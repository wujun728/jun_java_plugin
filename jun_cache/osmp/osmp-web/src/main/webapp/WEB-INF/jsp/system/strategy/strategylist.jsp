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
            <z:dataGrid dataGridType="datagrid" name="strategy" actionUrl="${BASE_PATH}/strategy/strategyList.do"
                title="策略列表" checkbox="false">
                <z:toolBar title="新增"
                    jsName="Add('${BASE_PATH}/strategy/toAdd.do','strategy','datagrid',500,300,'strategyAddForm','${BASE_PATH}/strategy/saveOrUpdate.do')"
                    icon="icon-add"></z:toolBar>
                <z:toolBar title="刷新缓存" jsName="ajaxSubmitConfirm('${BASE_PATH}/strategy/refresh.do','dict','datagrid','刷新缓存')" icon="icon-reload"></z:toolBar>
                <z:dataGridColumn field="id" title="ID" width="100"></z:dataGridColumn>
                <z:dataGridColumn field="name" title="策略名称" width="40"></z:dataGridColumn>
                <z:dataGridColumn field="forwardIp" title="转发IP" width="40"></z:dataGridColumn>
                <z:dataGridColumn field="remark" title="备注" width="50"></z:dataGridColumn>
                <z:dataGridColumn field="status" title="状态" width="50" replace="开启_1,关闭_0"></z:dataGridColumn>
                <z:dataGridColumn field="opt" title="操作" width="80"></z:dataGridColumn>
                <z:dataGridOpt name="删除"
                    jsName="Del('${BASE_PATH}/strategy/deleteStrategy.do?id=&quot;+rowData.id+&quot;','strategy','datagrid')"></z:dataGridOpt>
                <z:dataGridOpt name="编辑"
                    jsName="Edit('${BASE_PATH}/strategy/toEdit.do?id=&quot;+rowData.id+&quot;','strategy','datagrid',500,300,'strategyUpdateForm','${BASE_PATH}/strategy/saveOrUpdate.do')"></z:dataGridOpt>
         		<z:dataGridOpt name="查看/增加条件"
                  	jsName="OpenPlainWindow('${BASE_PATH}/strategy/toConditionList.do?id=&quot;+rowData.id+&quot;',700,400,'条件管理')"></z:dataGridOpt>
            </z:dataGrid>
        </div>
    </div>
</body>
</html>

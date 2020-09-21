<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="/WEB-INF/jsp/pubtag.jsp"%>
</head>
<script type="text/javascript">
	var showIcon = function(value,rec,index){
		if(value == 1){
			return '<img border="0" src="${BASE_PATH}/images/alarm/green.png"/>'
		}else{
			return '<img border="0" src="${BASE_PATH}/images/alarm/red.png"/>'
		}
	}
	
	function fresh(){
		$("#servers").datagrid("reload");
	}
</script>
<body>
    <div class="easyui-layout" fit="true">
        <div region="center" style="padding: 1px;">
            <z:dataGrid dataGridType="datagrid" name="servers" actionUrl="${BASE_PATH}/servers/serversList.do"
                title="服务器列表" checkbox="false">
                <z:toolBar title="新增"
                    jsName="Add('${BASE_PATH}/servers/toEdit.do','servers','datagrid',500,300,'ServerUpdateForm','${BASE_PATH}/servers/saveOrUpdate.do')"
                    icon="icon-add"></z:toolBar>
                 <z:toolBar title="刷新服务器" jsName="fresh()" icon="icon-reload"></z:toolBar>
                <%-- <z:dataGridColumn field="id" title="ID" width="100"></z:dataGridColumn> --%>
                <z:dataGridColumn field="serverName" title="服务器名称" width="40"></z:dataGridColumn>
                <z:dataGridColumn field="serverIp" title="服务器IP" width="40"></z:dataGridColumn>
                <z:dataGridColumn field="sshPort" title="SSH端口" width="25"></z:dataGridColumn>
                <z:dataGridColumn field="user" title="登陆用户名" width="40"></z:dataGridColumn>
                <%-- <z:dataGridColumn field="password" title="登陆密码" width="50"></z:dataGridColumn> --%>
                <z:dataGridColumn field="manageUrl" title="管理界面URL" width="90"></z:dataGridColumn>
                <z:dataGridColumn field="commandPath" title="执行脚本路径" width="120"></z:dataGridColumn>
                <z:dataGridColumn field="state" title="服务器状态" width="25" formatter="showIcon" ></z:dataGridColumn>
                <z:dataGridColumn field="remark" title="备注" width="40"></z:dataGridColumn>
                <z:dataGridColumn field="opt" title="操作" width="80"></z:dataGridColumn>
                <z:dataGridOpt name="启动" filter="state_2"
                    jsName="ajaxSubmitConfirm('${BASE_PATH}/servers/optionServers.do?id=&quot;+rowData.id+&quot;+&flag=start','servers','datagrid','启动')"></z:dataGridOpt>
                <z:dataGridOpt name="停止" filter="state_1"
                    jsName="ajaxSubmitConfirm('${BASE_PATH}/servers/optionServers.do?id=&quot;+rowData.id+&quot;+&flag=stop','servers','datagrid','停止')"></z:dataGridOpt>
                <z:dataGridOpt name="删除"
                    jsName="Del('${BASE_PATH}/servers/deleteServers.do?id=&quot;+rowData.id+&quot;','servers','datagrid')"></z:dataGridOpt>
                <z:dataGridOpt name="编辑"
                    jsName="Edit('${BASE_PATH}/servers/toEdit.do?id=&quot;+rowData.id+&quot;','servers','datagrid',500,300,'ServerUpdateForm','${BASE_PATH}/servers/saveOrUpdate.do')"></z:dataGridOpt>
            </z:dataGrid>
        </div>
    </div>
</body>
</html>

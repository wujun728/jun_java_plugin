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
            <z:dataGrid dataGridType="datagrid" name="configManager" actionUrl="${BASE_PATH}/config/systemConfigList.do"
                title="系统配置管理" checkbox="false">
                <z:toolBar title="全部刷新"
                    jsName="fresh('all')"
                    icon="icon-reload"></z:toolBar>
                <z:dataGridColumn field="name" title="配置参数名称" width="100"></z:dataGridColumn>
                <z:dataGridColumn field="key" title="配置参数属性名" width="100"></z:dataGridColumn>
                <z:dataGridColumn field="type" title="配置参数类型" width="50"></z:dataGridColumn>
                <z:dataGridColumn field="size" title="配置参数长度" width="100"></z:dataGridColumn>
                <z:dataGridColumn field="desc" title="配置参数说明" width="100"></z:dataGridColumn>
                <z:dataGridColumn field="opt" title="操作" width="100"></z:dataGridColumn>
                <z:dataGridOpt name="刷新"
                    jsName="fresh('&quot;+rowData.key+&quot;')"></z:dataGridOpt>
            </z:dataGrid>
            
        </div>
    </div>
    <script type="text/javascript">
        function fresh(target){
        	$.ajax({
        		url:"${BASE_PATH}/config/refreshSystemConfig/"+target+".do",
                type:"post",
                success:function(data){
                	$("#configManager").datagrid("reload");
                    alert(data);
                }
        	});
        }
    </script>
</body>
</html>

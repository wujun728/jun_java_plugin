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
            <z:dataGrid dataGridType="datagrid" name="${modal.divName }"
                actionUrl="${BASE_PATH}/cache/itemList.do?id=${modal.cacheId }" checkbox="false" showPageList="false">
                <z:dataGridColumn field="itemKey" title="键" width="120" align="left"></z:dataGridColumn>
                <z:dataGridColumn field="itemVal" title="值" width="120"></z:dataGridColumn>
                <z:dataGridColumn field="createTime" title="创建时间" width="100" formatter="dateformat"></z:dataGridColumn>
                <z:dataGridColumn field="lastAccessTime" title="最后访问时间" width="100" formatter="dateformat"></z:dataGridColumn>
                <z:dataGridColumn field="expirationTime" title="失效时间" width="100" formatter="dateformat"></z:dataGridColumn>
                <z:dataGridColumn field="hitCount" title="命中次数" width="100"></z:dataGridColumn>
                <z:dataGridColumn field="opt" title="操作" width="100"></z:dataGridColumn>
                <z:dataGridOpt name="删除缓存"
                    jsName="Del('${BASE_PATH}/cache/deleteCache.do?id=&quot;+rowData.itemKey+&quot;','${modal.divName }','datagrid')"></z:dataGridOpt>
            </z:dataGrid>
        </div>
    </div>
</body>
</html>

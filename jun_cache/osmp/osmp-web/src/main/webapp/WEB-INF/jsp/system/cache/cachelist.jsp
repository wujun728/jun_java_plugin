<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="/WEB-INF/jsp/pubtag.jsp"%>
<script type="text/javascript">
    function dateformat(index, row, ss) {
        var temp = zznode.util.dateFormatter(new Date(index), 'yyyy-MM-dd hh:mm:ss');
        return temp;
    }
</script>
</head>
<body>
    <div style="text-align: center; position: absolute; z-index: 10; width: 800px; left: 400px; top: 4px;">
        Cache个数：${cacheInfo.size }&nbsp;&nbsp;&nbsp;&nbsp; Cache内存占用：${cacheInfo.memorySize }
        &nbsp;&nbsp;&nbsp;&nbsp;Cache命中次数：${cacheInfo.hitCount }&nbsp;&nbsp;&nbsp;&nbsp; Cache错失数：${cacheInfo.missCount }
        &nbsp;&nbsp;&nbsp;&nbsp;
        <button onclick="ajaxSubmitConfirm('${BASE_PATH}/cache/cacheOption.do?type=open','cache','datagrid','开启缓存')">打开Chache</button>
        <button onclick="ajaxSubmitConfirm('${BASE_PATH}/cache/cacheOption.do?type=close','cache','datagrid','关闭缓存')">关闭Chache</button>
    </div>
    <div id="tt" class="easyui-tabs" fit="true" data-options="tools:'#tab-tools'" style="padding: 2px;">
        <div title="CACHE列表" data-options="tools:'#p-tools'" style="padding: 0px;">
            <div class="easyui-layout" fit="true">
                <div region="center" style="padding: 1px;">
                    <z:dataGrid dataGridType="datagrid" name="cache" actionUrl="${BASE_PATH}/cache/cacheList.do"
                        checkbox="false" showPageList="false">
                        <z:dataGridColumn field="id" title="ID" width="50"></z:dataGridColumn>
                        <z:dataGridColumn field="name" title="名称" width="200" align="left"></z:dataGridColumn>
                        <z:dataGridColumn field="prefix" title="前缀" width="50"></z:dataGridColumn>
                        <z:dataGridColumn field="timeToLive" title="有效时间" width="50"></z:dataGridColumn>
                        <z:dataGridColumn field="timeToIdle" title="空闲时间" width="50"></z:dataGridColumn>
                        <z:dataGridColumn field="state" title="状态" width="50" replace="开启_1,关闭_0"></z:dataGridColumn>
                        <z:dataGridColumn field="opt" title="操作" width="150"></z:dataGridColumn>
                        <z:dataGridOpt name="编辑"
                            jsName="Edit('${BASE_PATH}/cache/toEdit.do?id=&quot;+rowData.id+&quot;&timeToLive=&quot;+rowData.timeToLive+&quot;&timeToIdle=&quot;+rowData.timeToIdle+&quot;&state=&quot;+rowData.state+&quot;','cache','datagrid',500,300,'CacheEditForm','${BASE_PATH}/cache/updateCache.do')"></z:dataGridOpt>
                        <z:dataGridOpt name="查看详情"
                            jsName="OpenPlainWindow('${BASE_PATH}/cache/toItem.do?id=&quot;+rowData.id+&quot;',980,600,'详情','CacheEdit')"></z:dataGridOpt>
                    </z:dataGrid>
                </div>
            </div>
        </div>
        <div title="CACHE详情" data-options="closable:false,cache:false,href:'${BASE_PATH}/cache/toItem.do'"
            style="padding: 0px;"></div>
    </div>
</body>
</html>

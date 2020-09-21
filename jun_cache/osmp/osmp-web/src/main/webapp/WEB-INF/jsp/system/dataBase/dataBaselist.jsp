<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="/WEB-INF/jsp/pubtag.jsp"%>
<script type="text/javascript">
$(function(){
	$('#serverList').tabs({ 
		border:false, 
		onSelect:function(title){ 
			var tab = $('#serverList').tabs('getSelected');
			var addr = tab.panel('options').addr;
			var con = '<iframe frameborder="0" height="100%" width="100%"  frameborder="0" src="'+addr+'"></iframe>';
			$('#serverList').tabs('update', {
				tab : tab,
				options:{
					content:con
				}
			});
		} 
	});
});

</script>
</head>
<body>
	<div id="serverList" class="easyui-tabs" fit="true">
		<c:forEach items="${serverList}" var="item" varStatus="status">
        	<div title="${item.serverIp }" data-options="closable:false,cache:true,addr:'http://${ item.serverIp}:8181/druid/index.html'">
        	</div>
        </c:forEach>
	</div>
</body>
</html>

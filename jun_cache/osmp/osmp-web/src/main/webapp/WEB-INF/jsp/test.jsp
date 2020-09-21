<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="/WEB-INF/jsp/pubtag.jsp"%>
<script type="text/javascript">
$('#main').tabs('add',{
    title:title,
    content:content,
    closable:true,
    tools:[{
        iconCls:'icon-mini-refresh',
        handler:function(){
		var pp = $('#main').tabs('getSelected');//选中的选项卡对象 
		var content = pp.panel('options').content;//获取面板内容  
		//console.log(tab); 
		$("#main").tabs('update',{//更新
		    tab:pp,
		    options:content
		})
        }
    }]
});
</script>
</head>
<body>
<div class="easyui-layout">
	<div id = "main">
	</div>
</div>
</body>
</html>

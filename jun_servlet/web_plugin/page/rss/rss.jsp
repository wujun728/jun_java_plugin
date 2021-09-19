<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"  "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <base href="<%=basePath%>">
    <title>RSS</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<jsp:include page="../../layout/script.jsp"></jsp:include>
	<style type="text/css">
		.rtitle{
			font-size:18px;
			font-weight:bold;
			padding:5px 10px;
			background:#336699;
			color:#fff;
		}
		.icon-channels{
			background:url('images/tree_channels.gif') no-repeat;
		}
		.icon-feed{
			background:url('images/rss.png') no-repeat;
		}
		.icon-rss{
			background:url('images/rss.gif') no-repeat;
		}
	</style>
	<script type="text/javascript">
		$(function(){
			$('#dg').datagrid({
				onSelect: function(index,row){
					$('#cc').attr('src', row.link);
				},
				onLoadSuccess:function(){
					var rows = $(this).datagrid('getRows');
					if (rows.length){
						$(this).datagrid('selectRow',0);
					}
				}
			});
			$('#t-channels').tree({
				onSelect: function(node){
					var url = node.attributes.url;
					$('#dg').datagrid('load',{
						url: url
					});
				},
				onLoadSuccess:function(node,data){
					if (data.length){
						var id = data[0].children[0].children[0].id;
						var n = $(this).tree('find', id);
						$(this).tree('select', n.target);
					}
				}
			});
		});
	</script>
</head>
<body class="easyui-layout">
	<div region="north" border="false" class="rtitle">
		RSS Reader
	</div>
	<div region="west" title="Channels Tree" split="true" border="false" style="width:200px;">
		<ul id="t-channels" url="page/rss/data/channels.json"></ul>
	</div>
	<div region="center" border="false">
		<div class="easyui-layout" fit="true">
			<div region="north" split="true" border="false" style="height:200px">
				<table id="dg" 
						url="rssController.do?method=getFeed" border="false" rownumbers="true"
						fit="true" fitColumns="true" singleSelect="true">
					<thead>
						<tr>
							<th field="title" width="100">Title</th>
							<th field="description" width="200">Description</th>
							<th field="pubdate" width="80">Publish Date</th>
						</tr>
					</thead>
				</table>
			</div>
			<div region="center" border="false" style="overflow:hidden">
				<iframe id="cc" scrolling="auto" frameborder="0" style="width:100%;height:100%"></iframe>
			</div>
		</div>
	</div>
</body>
</html>
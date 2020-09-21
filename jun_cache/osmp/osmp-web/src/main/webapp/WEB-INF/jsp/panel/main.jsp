<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>${PROJECT_NAME}</title>
	<%@include file="../pubtag.jsp"%>
<style type="text/css">
a {
	color: Black;
	text-decoration: none;
}
.navDiv {
	white-space: nowrap;
}
.icon-navItem{
	display:inline-block;
	width:14px;
	height:12px;
	vertical-align:middle;
	margin-top:-5px;
	background:url('${BASE_PATH}/images/neiye/navItem.png') no-repeat;
}
li .navItemDiv{
	-webkit-border-bottom: 1px dotted #C3CAD2;
	-moz-border-bottom: 1px dotted #C3CAD2;
	border-bottom: 1px dotted #C3CAD2;
	white-space: nowrap;
}
li .navItemDiv a{
	margin-left:-10px;
	color:#595757;
}
.panel-header{
	padding:5px;
	line-height:15px;
	color:#FFFFFF;
	font-weight:bold;
	font-size:12px;
	background:url('${BASE_PATH}/images/neiye/menu.png') repeat-x center;
	position:relative;
	border:0;
	-webkit-border-top-left-radius: 0;
	-webkit-border-top-right-radius: 0;
	-moz-border-top-left-radius: 0;
	-moz-border-top-right-radius: 0;
	border-top-left-radius: 0;
	border-top-right-radius: 0;
}
.panel-title{
	color:#FFFFFF;
}
.panel-header-noborder{
	border-bottom:0;
}
#nav .panel-body{
	border:0;
}
.layout-button-left{
	background:url('${BASE_PATH}/images/neiye/left.png') no-repeat;
}
.layout-button-right{
	background:url('${BASE_PATH}/images/neiye/right.png') no-repeat;
}
a:hover {
	color: black;
	text-decoration: none;
}
</style>
  <SCRIPT type="text/javascript">
	$(function() {
		$('#layout_east_calendar').calendar({
			fit : true,
			current : new Date(),
			border : false,
			onSelect : function(date) {
				$(this).calendar('moveTo', new Date());
			}
		});

	});
</SCRIPT>
</head>
<body>
	<div id="main" class="easyui-layout" fit="true">
		<!-- 头部信息 -->
		<div region="north" href="north.do" style="height:80px;overflow:hidden;" border="false"></div>  
		
		<!-- 左侧导航菜单 -->
		<div region="west" href="left.do" split="true" border="false" title="导航菜单" style="width: 170px; padding: 0px;"></div>  
		
		<!-- 中间正文部分 -->
		<div id="mainPanle" region="center" style="overflow: hidden;">
			<div id="maintabs" class="easyui-tabs" fit="true" border="false">
				 <div class="easyui-tab" title="首  页" style="overflow: hidden;">
					<iframe src="welcome.do" scrolling="auto" frameborder="0" style="width:100%;height:100%;"></iframe>			 
				 </div>
			</div>
		</div>

		<!-- 底部版权信息 -->
		<div region="south" border="false" style="height: 25px;background: url('${BASE_PATH}/images/foot.png') repeat-x center; overflow: hidden;">
			<div align="center" style="color: #FFFFFF; padding-top: 2px;font:12px '宋体'">
   				&copy; 版权所有
   				<span class="tip">
   					${PROJECT_NAME}
   				</span>
		</div>  
	</div>
	<div id="mm" class="easyui-menu" style="width:150px;">
        <div id="mm-tabupdate">刷新</div>
        <div id="mm-tabclose">关闭</div>
        <div id="mm-tabcloseall">全部关闭</div>
        <div id="mm-tabcloseother">除此之外全部关闭</div>
	</div>
</body>
</html>
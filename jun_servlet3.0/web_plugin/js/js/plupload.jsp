<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"+ request.getServerName() + ":" + request.getServerPort()+ path + "/";
%>

<html>
<head>
<base href="<%=basePath%>">
<title>Pluplaod插件的上传演示</title>
<link rel="stylesheet" href="plugins/bootstrap/easyui.css" type="text/css"></link>
<script type="text/javascript" src="plugins/jquery/jquery-1.8.0.min.js"></script>
<script type="text/javascript" src="plugins/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript">
/**
 * 创建上传窗口 公共方法
 * @param chunk 是否分割大文件
 * @param callBack 上传成功之后的回调
 */
function Uploader(chunk,callBack){
	var addWin = $('<div style="overflow: hidden;"/>');
	var upladoer = $('<iframe/>');
	upladoer.attr({'src':'<%=basePath%>plugins/plupload_uploader.jsp?chunk='+chunk,width:'100%',height:'100%',frameborder:'0',scrolling:'no'});
	addWin.window({
		title:"上传文件",
		height:350,
		width:550,
		minimizable:false,
		modal:true,
		collapsible:false,
		maximizable:false,
		resizable:false,
		content:upladoer,
		onClose:function(){
			var fw = GetFrameWindow(upladoer[0]);
			var files = fw.files;
			$(this).window('destroy');
			callBack.call(this,files);
		},
		onOpen:function(){
			var target = $(this);
			setTimeout(function(){
				var fw = GetFrameWindow(upladoer[0]);
				fw.target = target;
			},100);
		}
	});
}

/**
 * 根据iframe对象获取iframe的window对象
 * @param frame
 * @returns {Boolean}
 */
function GetFrameWindow(frame){
	return frame && typeof(frame)=='object' && frame.tagName == 'IFRAME' && frame.contentWindow;
}
 
function makerUpload(chunk){
 Uploader(chunk,function(files){
	 if(files && files.length>0){
		 $("#res").text("成功上传："+files.join(","));
	 }
 });
}
</script>
</head>
<body style="width: 100%;height: 100%;overflow:hidden;margin: 0;padding: 0;">
	<h1>Pluplaod插件的上传演示</h1>
	<hr/>
	<a class="easyui-linkbutton" href="javascript:makerUpload(false)">不分割文件上传</a> <a class="easyui-linkbutton" href="javascript:makerUpload(true)">分割文件上传</a>
	<hr/>
	<div id="res"></div>
</body>
</html>
<%@ page language="java"  pageEncoding="GBK" isELIgnored="false"%>
<%@ page import="com.linkage.util.web.RequestUtil" %>
<%@ page import="com.linkage.sys.util.StaffUtil" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	String jspFilePath = request.getRequestURI(); 
	request.setAttribute("ctx",path);
	String perMaxSize = RequestUtil.getParameter(request,"perMaxSize","1");//默认1MB
	String sizeUnit = RequestUtil.getParameter(request,"sizeUnit","MB");//默认1MB
	String fileExt = RequestUtil.getParameter(request,"fileExt","*.*");//默认不限制文件类型
	String uploadLimit = RequestUtil.getParameter(request,"uploadLimit","1");//默认只能上传一个 
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" >
<meta http-equiv="Content-Type" content="text/html; charset=GBK" />
<head>  
<title>SWFUpload</title>  
<link href="${ctx}/js/SWFUpload/css/default.css" rel="stylesheet" type="text/css" />
<style  type="text/css">
	body {
		height:100%;
		width:100%;
		background:FFF;
		border-collapse:collapse;
		border-color:#478CCD;
		PADDING-RIGHT: 0px;
		PADDING-LEFT: 0px;
		PADDING-BOTTOM: 0px;
		MARGIN: 0px;
		FONT-FAMILY: 宋体;
		SCROLLBAR-FACE-COLOR: #EAF2FA;
		FONT-SIZE: 12px;
		SCROLLBAR-HIGHLIGHT-COLOR: #92C4E9;
		SCROLLBAR-SHADOW-COLOR: #92C4E9;
		SCROLLBAR-3DLIGHT-COLOR: #FFFFFF;
		SCROLLBAR-ARROW-COLOR: #778AAC;
		SCROLLBAR-TRACK-COLOR: #EDF2FD;
		SCROLLBAR-DARKSHADOW-COLOR: #ffffff;
		background:url(${ctx}/theme/default/images/bg.gif);
	}
</style>
<script type="text/javascript" src="${ctx}/js/SWFUpload/swfupload/swfupload.js"></script>
<script type="text/javascript" src="${ctx}/js/SWFUpload/js/swfupload.queue.js"></script>
<script type="text/javascript" src="${ctx}/js/SWFUpload/js/fileprogress.js"></script>
<script type="text/javascript" src="${ctx}/js/SWFUpload/js/handlers.js"></script>
<script type="text/javascript">
var swfu;
window.returnValue =  [];
window.onload = function() {
var settings = {   
		flash_url : "${ctx}/js/SWFUpload/swfupload/swfupload.swf",
		upload_url: "${ctx}/mFile/saveMFile.mvc",	// Relative to the SWF file
		use_query_string :true,
		post_params: {"upLoad":"SWFUpload","tableName":"${param.tableName}","fileRelaId":"${param.fileRelaId}","J2EE_SESSID" : "<%=request.getSession().getId()%>"},
		file_size_limit : "<%=perMaxSize%> <%=sizeUnit%>",//传入参数
		file_types : "<%=fileExt%>", //传入参数 
		file_types_description : "<%=fileExt%>", //传入参数
		file_upload_limit : "<%=uploadLimit%>", //传入参数
		file_queue_limit : 0,
		custom_settings : {
			progressTarget : "fsUploadProgress",
			cancelButtonId : "btnCancel"
		},
		debug: false, 
		// Button settings
		button_image_url: "${ctx}/js/SWFUpload/images/TestImageNoText_65x29.png",	// Relative to the Flash file
		button_width: "65",
		button_height: "29",
		button_placeholder_id: "spanButtonPlaceHolder",
		button_text: '<span class="theFont">浏览</span>',
		button_text_style: ".theFont { font-size: 16; }",
		button_text_left_padding: 12,
		button_text_top_padding: 3,

		//上传相关事件的回调函数 
		// The event handler functions are defined in handlers.js
		file_queued_handler : fileQueued,
		file_queue_error_handler : fileQueueError,
		file_dialog_complete_handler : fileDialogComplete,
		upload_start_handler : uploadStart,
		upload_progress_handler : uploadProgress,
		upload_error_handler : uploadError,
		upload_success_handler : uploadSuccess, //上传完成后的回调方法
		upload_complete_handler : uploadComplete,
		queue_complete_handler : queueComplete	// Queue plugin event
	};
	swfu = new SWFUpload(settings);
}

function closeWin(){
	window.close();
}
   
</script>
</head>
<body scroll=no>
<div id="content">
	<p>点击“浏览”按钮，选择您要上传的文档文件后，系统将自动上传并在完成后提示您。</p>
	<p>（支持文件类型<%=fileExt%>；单个文件最大不能超过<%=perMaxSize%> <%=sizeUnit%>；文件个数不得超过<%=uploadLimit%>个）</p>
	<form id="form1" action="index.php" method="post" enctype="multipart/form-data">
		<div class="fieldset flash" id="fsUploadProgress">
			<span class="legend">快速上传</span>
	  	</div>
		<div id="divStatus">0 个文件已上传</div>
		<div>
		<span id="spanButtonPlaceHolder"></span>
		<% if( StaffUtil.isLogin(request)) {%>
			 <input id="btnUpload" type="button" value="上传附件" onclick="swfu.startUpload();" style="margin-left: 2px; font-size: 8pt; height: 29px;" />
			 <input id="btnCancel" type="button" value="取消所有上传" onclick="swfu.cancelQueue();" disabled="disabled" style="margin-left: 2px; font-size: 8pt; height: 29px;" />
			<input id="btnClose" type="button" value="关闭窗口" onclick="closeWin();" style="margin-left: 2px; font-size: 8pt; height: 29px;" />
		<%}else { %>
		     <br/><br/>
		     <div style="color:red;font-size:24px;">请先<a href="${ctx}/login.jsp" target="_blank">登录系统</a>....</div> 
		<%} %>	
		</div>
	</form>
</div>


</body>
</html>

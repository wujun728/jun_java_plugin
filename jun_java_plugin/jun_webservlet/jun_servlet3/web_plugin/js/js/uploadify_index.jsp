<%@ page language="java" contentType="text/html; charset=gbk"%>

<%
	String path = request.getContextPath();

	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>



<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html>
	<head>
		<title>Upload</title>

		<!--装载文件-->
		<link href="uploadify-v3.1/uploadify.css" type="text/css" rel="stylesheet" />
		<script type="text/javascript" src="js/jquery-1.7.1.min.js"></script>
		<script type="text/javascript" src="uploadify-v3.1/jquery.uploadify-3.1.js"></script>
		<script type="text/javascript" src="js/swfobject.js"></script>
		

		<!--ready事件-->
		<script type="text/javascript">
		 	$(document).ready(function () {
				　$("#uploadify").uploadify({
					　'swf': 'uploadify-v3.1/uploadify.swf', 
					  'uploader':'servlet/upload', 
					　//'script':'upload!doUpload.action?name=yangxiang',
					　//'script': 'servlet/Upload?name=yangxiang',  
					　//'cancel' : 'uploadify/uploadify-cancel.png',                  
					　'queueID' : 'fileQueue', 
					 //和存放队列的DIV的id一致  
					　//'fileDataName': 'fileupload', //必须，和以下input的name属性一致                   
					　'auto'  : false, //是否自动开始  
					　'multi': true, //是否支持多文件上传  
					  'buttonText': '选择文件', //按钮上的文字  
					　'simUploadLimit' : 1, //一次同步上传的文件数目  
					　//'sizeLimit': 19871202, //设置单个文件大小限制，单位为byte  
					　'fileSizeLimit' : '6000KB',
					  'queueSizeLimit' : 100, //队列中同时存在的文件个数限制  
					　'fileTypeExts': '*.jpg;*.gif;*.jpeg;*.png;*.bmp',//允许的格式
					  'fileTypeDesc': '支持格式:jpg/gif/jpeg/png/bmp.', //如果配置了以下的'fileExt'属性，那么这个属性是必须的  
					　'onUploadSuccess': function ( fileObj, response, data) {  
					　		alert("文件:" + fileObj.name + "上传成功");
					　},  
					　'onUploadError': function(event, queueID, fileObj) {  
					　		alert("文件:" + fileObj.name + "上传失败");  
					　},  
					　'onCancel': function(event, queueID, fileObj){  
					　		alert("取消了" + fileObj.name);  
					　　　} 
				　});
				
			});

    	</script>
	</head>

	<body>

		<div id="fileQueue" style="width:30%"></div>

		<input type="file" name="uploadify" id="uploadify" />

		<p>

			<a href="javascript:jQuery('#uploadify').uploadify('upload','*')">开始上传</a>&nbsp;

			<a href="javascript:jQuery('#uploadify').uploadifyClearQueue()">取消所有上传</a>

		</p>

	</body>

</html>


<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/static/taglibs.jsp"%>
<html>
<head>
<title>Hadoop集群配置向导 - 节点监控/管理 - Hadoop集群列表</title>
<link rel="stylesheet" href="${base}/static/code/codemirror.css" type="text/css" />
<script src="${base}/static/code/codemirror.js"></script>
<style type="text/css">
	.floatR{ float:right; margin-right:8px;}
	.floatL{ float:left;}
	.clearFloat{ clear:both;}
	a{ cursor:pointer;}
	#fileFrame{border:1px solid #ddd;}
	#fileDesc{ background:url("${base}/static/images/filedescbg.jpg") repeat-x; height:44px; line-height:44px;}
	#fileDesc p{ text-indent:1em; width:100%;}
	#fileDesc p span{ width:70%;}
	#currentPath{ height:40px; line-height:40px; margin-bottom:10px; background:#eee;}
	#currPathTip{ margin-left:5px;}
	
	#appendData{border:1px solid #ddd; margin-top:10px;}
	#toolBar{ background:url("${base}/static/images/filedescbg.jpg") repeat-x; height:44px; line-height:44px;}
	.savebtn{ margin:6px;}
	
	.appendbtn{ margin-top:7px; margin-right:7px;}
</style>

<script type="text/javascript">
	$(document).ready(function(){
		editor = CodeMirror.fromTextArea(document.getElementById('readFileResult'), {
            //mode: mode,
            lineNumbers: true,
            matchBrackets: true,
            readOnly: "nocursor",
            lineWrapping: true
           });
       
       editor1 = CodeMirror.fromTextArea(document.getElementById('data'), {
       			lineNumbers: true,
            	matchBrackets: true,
            	readOnly: false,
                lineWrapping: true
       		});
       		
       	$("#appendData").hide();
       	
       	$(".appendbtn").click(function(){
			$("#appendData").show();
		});
	
		$(".savebtn").click(function(){
			
			var hostname = $("#hostname").val();  //主机名
			var parentpath = $("#parentPath").val();  //文件的上一级路径，如：/user/hadoop/file
			var filename = $("#fileName").val(); //文件名，如：1.txt
			var appendData = $.trim(editor1.getValue()); //要往1.txt中追加的数据
			
			var resultData = $.trim(editor.getValue());
			
			if(data != ""){
						$.ajax({
								url:"${base}/hadoop/hdfs/appenddata",
								type:"post",
								cache: false,
								data: {
					    	   		'filename': filename,
					    	   		'data':appendData,
					    	   		'hostname':hostname,
					    	   		'parentpath':parentpath
								},
								success:function(data){
									if(data == "success"){
										if($.trim(appendData).length > 0){
											var value = resultData;
											if(typeof resultData != "null" && resultData != ''){
												value += "\n";
											}
											var oldSize = parseInt($(".fileSize").text());
											if(oldSize < 5){
												editor.setValue(value + appendData);
											}else{
												editor.setValue(value);
											}
											$(".fileSize").empty().text(oldSize + Math.floor(appendData.length/1024) <= 1 ? 1 : oldSize + Math.floor(appendData.length/1024));
										}
										editor1.setValue("");
										$("#appendData").hide();
									}
								},
								error:function(e){
									$("#ajaxErrorModal").modal();
								}
						});
			}
			
		});
		
		
		
		
	});
	
	function getSubDir(currPath,flag){
		$("#currPath").val(currPath);
		$("#breadCrumbForm").attr("action",$("#setFoldAction").val()).submit();
	}
	
	
	function prePage(){
		var num = parseInt($("#pageNum").val());
		if(num - 1 <= 0){
			$("#pageNum").val(1);
		}else{
			$("#pageNum").val(num - 1);
		}
		$("#contentPaginationForm").submit();
	}
	
	function nextPage(){
		var num = parseInt($("#pageNum").val());
		var count = parseInt($("#pageCount").val());
		if(num + 1 >= count){
			$("#pageNum").val(count);
		}else{
			$("#pageNum").val(num + 1);
		}
		$("#contentPaginationForm").submit();
	}
</script>
</head>
<body>
<ul class="breadcrumb">
  <li><a href="${base}/index">首页</a> <span class="divider">/</span></li>
  <li><a href="${base}/hadoop/hdfs/list">HDFS管理</a><span class="divider">/</span></li>
  <li class="active">文件浏览</li>
</ul>

<div>
	<div><!-- <span id = "currPathTip">当前路径：</span>${currPath} -->
	<form id = "breadCrumbForm" method = "post">
		<input type = "hidden" id = "currPath" name = "currPath"/>
		<input type = "hidden" id = "hostname" name = "hostname" value = "${hostname}"/>
		<input type = "hidden" id = "setFoldAction" value="${base}/hadoop/hdfs/fold"/>
		<input type = "hidden" id = "parentPath" name = "parentPath" value = "${parentPath}"/>
		<input type = "hidden" id = "fileName" name = "fileName" value = "${currName}" />
		
		<ul class = "breadcrumb">
			<c:forEach var="strArr" items="${strArrList}" varStatus="status">
				<c:if test = "${!status.last }"><li><a onclick="getSubDir('${strArr[0]}',true)">${strArr[1]}</a><span class="divider">/</span></li></c:if>
			</c:forEach>
			<li>${currName}</li>
		</ul>
	</form>
	</div>
	<form id = "contentPaginationForm" method = "post" action = "${base}/hadoop/hdfs/file">
		<input type = "hidden" id = "currPath" name = "currPath" value = "${currPath}"/>
		<input type = "hidden" id = "hostname" name = "hostname" value = "${hostname}"/>
		<input type = "hidden" id = "pageNum" name = "pageNum" value = "${pageNum}"/>
		<input type = "hidden" id = "pageCount" name = "pageCount" value = "${pageCount}"/>
	<div id = "fileFrame">
		<div id = "fileDesc">
			<p>预览&nbsp;&nbsp;大小：<span class = "fileSize"><fmt:formatNumber type='number' value='${hdfsFile.len/1024 == 0 ? 0 : hdfsFile.len/1024 + 1}' maxFractionDigits="0"></fmt:formatNumber></span>KB<button type="button" class="btn btn-default active appendbtn pull-right">追加</button></p>
		</div>
		<div id = "fileContent">
			<textarea id = "readFileResult">${content}</textarea>
		</div>
		<div id = "pagination"><button type = "button" class = "btn btn-default active" onclick = "prePage()">上一页</button><button type = "button" class = "btn btn-default active"  onclick = "nextPage()">下一页</button></div>
	</div>
	</form>
	<div id = "appendData">
		<div id = "toolBar">
			<button type="button" class="btn btn-success active savebtn">保存</button>
		</div>
		<div id = "appendContent">
			<textarea id = "data"></textarea>
		</div>
	</div>
</div>

<div id="ajaxErrorModal" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="ajaxErrorModalLabel" aria-hidden="true">
	<div class="modal-header">
		<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
		<h3 id="logoutModalLabel">错误提示</h3>
	</div>
	<div class="modal-body">
		<div class="control-group">
			追加数据时发生了错误。
		</div>
	</div>
	<div class="modal-footer">
		<a class="btn btn-warning" href="#" data-dismiss="modal" aria-hidden="true">关闭</a>
	</div>
</div>

</body>
</html>
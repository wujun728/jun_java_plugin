<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/static/taglibs.jsp"%>
<html>
<head>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0"> 
<title>Hadoop集群配置向导 - 节点监控/管理 - Hadoop集群列表</title>
	
	<link href="${base}/static/scripts/jquery-alert/css/jquery-ui.css" type="text/css" rel="stylesheet" /> 
    
    <link href="${base}/static/scripts/jquery-alert/css/jquery.alerts.css" type="text/css" rel="stylesheet" /> 
    
    
    <link rel="stylesheet" type="text/css" href="${base}/static/scripts/upload/uploadify/uploadify.css"/>
	
	
	
	<script type="text/javascript" src="${base}/static/scripts/jquery-alert/js/jquery-ui.js"></script>
	<script type="text/javascript" src="${base}/static/scripts/jquery-alert/js/jquery.contextmenu.r2.js"></script>
	<script type="text/javascript" src="${base}/static/scripts/jquery-alert/js/jquery.alerts.js"></script>
	<script type="text/javascript" src="${base}/static/scripts/jquery-alert/js/jquery.ui.draggable.js"></script>
	
	
	<script type="text/javascript" src="${base}/static/scripts/upload/jquery.uploadify.js"></script>
	<script type="text/javascript" src="${base}/static/scripts/upload/jquery.uploadify.min.js"></script>


   	<script type="text/javascript" src="${base}/static/scripts/drag/js/jquery.jsmartdrag.js"></script>
	
	
<style type="text/css">
	.name{ width:45%; font-size:14px; font-weight:bold;}
	.modifyTime{ width:20%; font-size:14px; font-weight:bold; text-indent:2em;}
	.replication{ width:15%; font-size:14px; font-weight:bold;}
	.len{ width:15%; font-size:14px; font-weight:bold;}
	a{ cursor:pointer;}
	
	#currentPath{ height:40px; line-height:40px; margin-bottom:10px; background:#eee;}
	#currPathTip{ margin-left:5px;}
	
	/*拖拽效果开始*/
	.source{}
	.target{}
	.jsmartdrag-source{cursor:pointer;}
	.jsmartdrag-source-hover{opacity:0.5;}
	.jsmartdrag-cursor-hover{opacity:0.5;}
	.jsmartdrag-target-hover{opacity:0.5;}
	/*拖拽效果结束*/
	.contextMenu{display:none;}
	
	#contentDiv{ height:480px;}
	
	.contentTbl{ height:400px; overflow-y:scroll;}
</style>

<script type="text/javascript">
	function getSubDir(currPath,flag){
		$("#currPath").val(currPath);
		if(flag){
			$("#currPathForm").attr("action",$("#setFoldAction").val()).submit();
		}else{
			$("#currPathForm").attr("action",$("#setFileAction").val()).submit();
		}
	}
	
	$(document).ready(function(){
		js = $(".source").jsmartdrag({
			target:'.target',
			afterDrag:afterDrag
		});
	});
	
	function afterDrag(selected , currentObj , targetSelected){
		if(selected){
			//currPath记录当前的目录
			var currPath = document.getElementById("parentPath").value;
			//hostname记录主机名
			var hostname = document.getElementById("hostname").value;
			//通过获取图片的src的路径来判断文件类型
			var imgSrc = currentObj.find("img").attr("src");
			//fileName记录拖拽的文件名
			var fileName = currentObj.find(".fileOrFold").text();
			//foldName记录拖拽后存放的目标文件夹名
			var foldName = targetSelected.find(".fileOrFold").text();
			//fileType记录文件类型
			var fileType = "file";
			//通过src判断类型，并设置fileType的值
			if(imgSrc.indexOf("file") > 0){
				fileType = "file";
			}else{
				fileType = "fold";
			}
			var targetpath = currPath + "/" + foldName;
			
			var isexist = "false";
			
				$.ajax({
					url:"${base}/hadoop/hdfs/dragisexist",
					type:"post",
					cache: false,
					data: {
					    'filename': fileName,
					    'hostname':hostname,
					    'targetpath':targetpath
						},
					success:function(data){
						if(data == "true"){
							isexist = "true";
							/* $(".dragFileName").val(fileName);
							$(".dragHostName").val(hostname);
							$(".dragCurrPath").val(currPath);
							$(".dragTargetPath").val(targetpath);
							$(".dragIsExist").val(isexist);
							$(".dragCurrentObj").val(currentObj);
							$(".dragConfirmMsg").text("在" + targetpath + "目录中存在与" + fileName + "同名的文件。确定要替换吗？");
							$("#dragConfirmModal").modal(); */
							if(confirm("确定要替换吗？")){
								dragMoveFile(fileName,hostname,currPath,targetpath,isexist,currentObj);
							}
						}else{
							isexist = "false";
							dragMoveFile(fileName,hostname,currPath,targetpath,isexist,currentObj);
						}
					},
					error:function(e){
						$(".ajaxErrorMsg").text("在实现拖拽文件到文件夹中，判断文件名是否在文件夹中存在出现错误.");
						$("#ajaxErrorMsgModal").modal();
					}
				});	
		}
	}
	
	function dragMoveFile(fileName,hostname,currPath,targetpath,isexist,currentObj){
		$.ajax({
			url:"${base}/hadoop/hdfs/dragfile",
			type:"post",
			cache: false,
			data: {
				'filename': fileName,
				'hostname':hostname,
				'currpath':currPath,
				'targetpath':targetpath,
				'isexist':isexist
				},
			success:function(data){
				if(data == "success"){
					currentObj.hide();
				}
			},
			error:function(e){
				$(".ajaxErrorMsg").text("在实现拖拽文件到文件夹中，将文件数据写入到文件夹时出错.");
				$("#ajaxErrorMsgModal").modal();
			}
		});
					
	}
	
	function dragConfirmSubmit(){
		var fileName = $(".dragFileName").val();
		var hostname = $(".dragHostName").val();
		var currPath = $(".dragCurrPath").val();
		var targetpath = $(".dragTargetPath").val();
		var isexist = $(".dragIsExist").val();
		var currentObj = $(".dragCurrentObj").val();
		dragMoveFile(fileName,hostname,currPath,targetpath,isexist,currentObj);
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
	<ul class = "breadcrumb">
		<c:forEach var="strArr" items="${strArrList}" varStatus="status">
			<c:if test = "${!status.last }"><li><a onclick="getSubDir('${strArr[0]}',true)">${strArr[1]}</a><span class="divider">/</span></li></c:if>
		</c:forEach>
		<li>${currName}</li>
	</ul>
  <div id = "contentDiv">
	<form id="currPathForm" method="post">
		<input type = "hidden" id = "currPath" name = "currPath"/>
		<input type = "hidden" id = "setFoldAction" value="${base}/hadoop/hdfs/fold"/>
		<input type = "hidden" id = "setFileAction" value="${base}/hadoop/hdfs/file"/>
		<input type = "hidden" id = "hostname" name = "hostname" value = "${hostname}"/>
		<input type = "hidden" id = "parentPath" value = "${currPath}" />
		<input type = "hidden" id = "pageNum" name = "pageNum" value = "1"/>	
		<ul class = "unstyled inline"><li class="name">文件名</li><li class = "modifyTime">修改时间</li><li class = "replication">复制份数</li><li class = "len">文件大小</li></ul>
		<div class = "contentTbl">
		<table class="table table-hover">
			<thead>
				<tr><th class="name"></th><th class="modifyTime"></th><th class="replication"></th><th class="len"></th></tr>
			</thead>
			<tbody class = "filelist">
				<c:forEach var="hdfsFile" items="${hdfsFiles}" varStatus="idx">
					<tr class="foldfile <c:if test = '${!hdfsFile.dir}'>source</c:if><c:if test = '${hdfsFile.dir}'>target</c:if>" id = "tr${idx.index}">
						<td>
							<input type = "hidden" class = "filename" value = "${hdfsFile.fileName}"/>
								<c:if test = "${!hdfsFile.dir}">
									<img src="${base}/static/scripts/jquery-alert/images/small_file.png" class="fileicon"/>
								</c:if>
								<c:if test='${hdfsFile.dir}'>
									<img src="${base}/static/scripts/jquery-alert/images/small_fold.png" class="fileicon"/>
								</c:if>
								<a onclick="getSubDir('${currPath}/${hdfsFile.fileName}',${hdfsFile.dir})" class = "fileOrFold">${hdfsFile.fileName}</a>
					   </td>
					   <td>
					   		<fmt:formatDate value="${hdfsFile.modifyTime}" type="both"/>
					   </td>
					   <td>${hdfsFile.replication}</td>
					   <td><c:if test="${!hdfsFile.dir}"><fmt:formatNumber type='number' value='${hdfsFile.len/1024 == 0 ? 0 : hdfsFile.len/1024 + 1}' maxFractionDigits="0"></fmt:formatNumber>KB</c:if></td>
				  </tr>
				</c:forEach>
			</tbody>
		</table>
		</div>
	</form>
  </div>
</div>

<!-- 右键菜单的源 -->
   <div class="contextMenu" id = "myMenu1">
   	<ul>
    	<li id="edit">编辑文件名</li>
        <li id="delete">删除</li>
        <li id="down_file">下载</li>
    </ul>
   </div>
   
   <div class="contextMenu" id = "myMenu3">
   	<ul>
        <!--<li id = "paste">粘贴</li>-->
        <li id="new_file">新建文件</li>
        <li id="new_fold">新建文件夹</li>
        <li id="upload_file">上传</li>
    </ul>
   </div>
   
   
   <script type="text/javascript">
	function func(){
		$('.foldfile').contextMenu('myMenu1',
		{
			bindings:
			{
				'edit':function(t){
					var oldname = $("#" + t.id + " > td > .filename").val();
					
					$(".newName").val(oldname);
					$(".oldName").val(oldname);
					$(".editHideId").val(t.id);
					$(".editNamePromptMsg").text("请输入新文件名:");
					$("#editNamePromptModal").modal();
					
				},
				'delete':function(t){
					
					var delname = $("#" + t.id + " > td > .filename").val();
					
					var hostname = $("#hostname").val();
					
					var parentpath = $("#parentPath").val();
					
					$("#delName").val(delname);
					$("#delHostName").val(hostname);
					$("#delParentPath").val(parentpath);
					
					$("#delHideId").val(t.id);
					
					
					$(".deleteConfirmMsg").text("你确定要删除" + delname + "吗？");
					$("#deleteConfirmModal").modal();
				},
				'down_file':function(t){
					var filename = $("#" + t.id + " > td > .filename").val();
					
					var hostname = $("#hostname").val();
					
					var parentpath = $("#parentPath").val();
					
					var form = $("<form>");   //定义一个form表单
					form.attr("style","display:none");
					form.attr("target","");
					form.attr("method","post");
					form.attr("action","${base}/hadoop/hdfs/downfile");
					
					var fileNameInput = $("<input>");
					fileNameInput.attr("type","hidden");
					fileNameInput.attr("name","filename");
					fileNameInput.attr("value",filename);
					
					var hostNameInput = $("<input>");
					hostNameInput.attr("type","hidden");
					hostNameInput.attr("name","hostname");
					hostNameInput.attr("value",hostname);
					
					var parentPathInput = $("<input>");
					parentPathInput.attr("type","hidden");
					parentPathInput.attr("name","parentpath");
					parentPathInput.attr("value",parentpath);
					
					$("body").append(form);  //将表单放置在web中
					form.append(fileNameInput);
					form.append(hostNameInput);
					form.append(parentPathInput);
					
					form.submit();
					 
				}
			},
			onShowMenu:function(e,menu){
				if($(e.currentTarget).attr('class') == 'foldfile target'){
					$('#down_file',menu).remove();
				}
				return menu;
			}
		});	
	}
	$(function(){
		document.oncontextmenu=function(){return false;}
		//var copyObj;	//被拷贝的副本
		var cutObj;		//被剪切的对象
		var copyNo = 1;
		var newCreateFile = 1;	 //新建文件统计
		var newCreateFold = 1;   //新建文件夹统计
		var idNo = 5;        //用于统计id编号的变大
		//所有class为demo1的span标签都会绑定此右键菜单
		func();
		
		//所有div标签class为demo3的绑定此右键菜单
		$("#contentDiv").contextMenu('myMenu3',{
			//重写onContextMenu和onShowMenu事件
			onContextMenu:function(e){
				if($(e.target).attr('id') == 'dontShow') 
					return false;
				else
					return true;
			},
			//显示菜单项
			onShowMenu:function(e,menu){
				if($(e.target).attr('id') == 'showOne'){
					$('#item_22,#item_33',menu).remove();	
				}else if(window.copyObj == null){
					$('#paste',menu).attr("disabled","disabled");
				}
				return menu;
			},
			bindings:
			{
				/* 'paste':function(t){
					$("#fileList").append("<li id = 't_" + idNo + "' class = 'demo1'><div class = 'fileName'>" + $('#' + window.copyObj.id + ' > .fileName').text() + " 复制" + copyNo + "</div></li>");
					idNo++;
					copyNo++;
					//window.copyObj = null;
					func();
				}, */
				'new_file':function(){
					//用户输入新建文件名
					
					$(".namePromptMsg").text("请输入文件名：");
					$(".nameType").val("0");     //nameType为0时代表文件，为1时代表文件夹
					$("#namePromptModal").modal();	
				},
				'new_fold':function(){
					//用户输入新建文件名
									
					$(".namePromptMsg").text("请输入文件夹名：");
					$(".nameType").val("1");     //nameType为0时代表文件，为1时代表文件夹
					$("#namePromptModal").modal();
				},
				'upload_file':function(){
					initUploadify();
					$('#uploadModal').modal('show');
				}
			}
		});
		
	});
	
	function delSubmit(){
		$("#delForm").attr("action","${base}/hadoop/hdfs/delete");
		$("#delForm").submit();
		$("#delName").val("");
		$("#delHostName").val("");
		$("#delParentPath").val("");		
		$("#delHideId").val("");
	}
	
	
	function promptSubmit(){
		var type = $(".nameType").val();
		var name = $("#tmpName").val();
		var reCat=/[^a-zA-Z\\.0-9\u4E00-\uFA29\uE7C7-\uE7F3]/gi;
		name=name.replace(reCat,"");
		//主机名
		var hostname = $("#hostname").val();
		//上一级目录名
		var parentpath = $("#parentPath").val();
					
		var idx = $(".filelist tr").length;
		if(name != "" && $.trim(name).length > 0){
			$("#mkHostName").val(hostname);
			$("#mkParentPath").val(parentpath);
			$("#resultName").val(name);
			switch(type){
				case "0":
				$("#mkFileOrFold").attr("action","${base}/hadoop/hdfs/mkfile");
				$("#mkFileOrFold").submit();
				break;
				case "1":
					$("#mkFileOrFold").attr("action","${base}/hadoop/hdfs/mkfold");
					$("#mkFileOrFold").submit();
				}
			}
		 $(".nameType").val("");
		 $("#tmpName").val("");
	}
	
	function editPromptSubmit(){
		var oldname = $(".oldName").val();
		var newname = $(".newName").val();
		
		var reCat=/[^a-zA-Z\\.0-9\u4E00-\uFA29\uE7C7-\uE7F3]/gi;
		
		newname=newname.replace(reCat,"");
		
		var tid = $(".editHideId").val();
		
		var hostname = $("#hostname").val();
					
		var parentpath = $("#parentPath").val();
					
		if(newname != null && newname != ""){
			$("#editHostName").val(hostname);
			$("#editParentPath").val(parentpath);
			$("#oldName").val(oldname);
			$("#newName").val(newname);
			$("#editNameForm").attr("action","${base}/hadoop/hdfs/rename");
			$("#editNameForm").submit();
		}
		$(".newName").val("");
		$(".oldName").val("");
		$(".editHideId").val("");
	}
    </script>
        
    <div id="uploadModal" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="uploadModalLabel" aria-hidden="true">
		  <div class="modal-header">
		    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
		    <h3 id="logoutModalLabel">上传文件</h3>
		  </div>
		  	<div class="modal-body">
		  		<div class="control-group">
			     	<input id="myfile" type="file" name="uploadFile"/>
			    </div>
			</div>
			<div class="modal-footer">
				 <a class="btn btn-default" href="#" data-dismiss="modal" aria-hidden="true">关闭</a>
			</div>
	</div>
    
   <input  type = "hidden"  value=""  id="upload_file" />
   <input type = "hidden" id = "fileUrl" />
   
   <!-- 用于上传文件  -->
    <input type="hidden"  value="<%=session.getId()%>"  id="sessionId" />
   
   <script type="text/javascript">
		   var sessionId=$("#sessionId").val();
            function initUploadify(){
            	var hostname = $("#hostname").val();
            	var parentpath = $("#parentPath").val();
            	var idx;
				$("#myfile").uploadify({
	                    buttonText:'上传浏览',  //上传按钮的文字。浏览按钮的文本，默认值：BROWSE
		                fileTypeExts:'*.txt',  //允许上传的文件类型，限制弹出文件选择框里能选择的文件。设置可以选择的文件的类型，格式如：“*。doc；*。pdf；*。rar”
		                fileObjName:'filedata',  //文件对象名称，用于在服务器端获取文件,选择的文件对象，有name、size、creationDate、modificationDate、type5个属性
		                fileTypeDesc:'请选择txt文件',  //允许上传的文件类型的描述，在弹出的文件选择框里会显示   。这个属性值必须设置fileTypeExt属性后才有效，用来设置选择文件对话框中的提示文本，如设置fileTypeDesc为“请选择rar doc pdf文件”
		                swf:'${base}/static/scripts/upload/uploadify.swf',   //[必须设置]swf的路径
		                uploader:'${base}/hadoop/hdfs/uploadfile;jsessionid='+sessionId,  //[必须设置]上传文件触发的uril，请求路径
		                height:'20',  //设置浏览按钮的高度，默认值：30.
		                width:'70',  //设置浏览按钮的宽度，默认值：110.
		                formData:{'hostname':hostname,
		                	'parentpath':parentpath
		                },   //和后台交互时，附加的参数
		                method:'post',  //和后台交互的方式：post/get
		                multi:true,   //是否能选择多个文件
		                queueSizeLimit:10, 	//当允许多文件生成时，设置选择文件的个数，默认值：999. 
		                uploadLimit:10,  //最多上传文件数量
		                progressData:'all',  //'percentage''speed''all' 队列中显示文件上传进度的方式：all-上传速度+百分比，percentage-百分比，speed-上传速度 
		                preventCaching:true,  //一个随机数将被加载swf文件URL的后面，防止浏览器缓存
		                onUploadSuccess:function(file, data, response){
		                	if(response){
		                		$("#uploadHostName").val(hostname);
		                		$("#uploadParentPath").val(parentpath);
		                		$("#uploadFlushForm").attr("action","${base}/hadoop/hdfs/fold");
		                		$("#uploadFlushForm").submit();
		                	}
		                }
	                 });
	         }
		</script>
		
		
<!-- 所有alert、confirm、prompt处理的隐藏的div -->
	<!-- ajax返回错误时弹出的页面 -->
	<div id="ajaxErrorMsgModal" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="ajaxErrorMsgModalLabel" aria-hidden="true">
		  <div class="modal-header">
		    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
		    <h3 id="ajaxErrorMsgModalLabel">错误提示</h3>
		  </div>
		  	<div class="modal-body">
		  		<div class="control-group">
			     	<p class = "ajaxErrorMsg"></p>
			    </div>
			</div>
			<div class="modal-footer">
				 <a class="btn btn-default" href="#" data-dismiss="modal" aria-hidden="true">关闭</a>
			</div>
	</div>
	<!-- 删除确认提示框 -->
	<div id="deleteConfirmModal" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="deleteConfirmModalLabel" aria-hidden="true">
		  <div class="modal-header">
		    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
		    <h3 id="deleteConfirmModalLabel">删除确认提示</h3>
		  </div>
		  	<div class="modal-body">
		  		<div class="control-group">
			     	<p class = "deleteConfirmMsg"></p>
			     	<form id = "delForm" method = "post">
			     		<input type = "hidden" id = "delName" name = "delname"/>
			     		<input type = "hidden" id = "delHostName" name = "hostname"/>
			     		<input type = "hidden" id = "delParentPath" name = "parentpath"/>
			     		<input type = "hidden" id = "delHideId"/> 
			     	</form>
			    </div>
			</div>
			<div class="modal-footer">
				 <a class="btn btn-default" href="#" data-dismiss="modal" aria-hidden="true">关闭</a>&nbsp;&nbsp;<a class = "btn btn-primary" data-dismiss = "modal" aria-hidden = "true" onclick = "delSubmit()">确定</a>
			</div>
	</div>
	<!-- 文件或者文件夹名输入提示框 -->
	<div id="namePromptModal" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="namePromptModalLabel" aria-hidden="true">
		  <div class="modal-header">
		    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
		    <h3 id="namePromptModalLabel">输入提示</h3>
		  </div>
		  	<div class="modal-body">
		  		<div class="control-group">
			     	<p class = "namePromptMsg"></p>
			     		<form id = "mkFileOrFold" method = "post">
			     			<input type = "text" id = "tmpName" maxlength = "16"/>
			     			<input type = "hidden" class = "nameType" />
			     			<input type = "hidden" id = "resultName" name = "name"/>
			     			<input type = "hidden" id = "mkHostName" name = "hostname"/>
			     			<input type = "hidden" id = "mkParentPath" name = "parentpath"/>
			     		</form>
			    </div>
			</div>
			<div class="modal-footer">
				 <a class="btn btn-default" href="#" data-dismiss="modal" aria-hidden="true">关闭</a>&nbsp;&nbsp;<a class = "btn btn-primary" data-dismiss = "modal" aria-hidden = "true" onclick = "promptSubmit()">确定</a>
			</div>
	</div>
	
	<!-- 文件或者文件夹名编辑提示框 -->
	<div id="editNamePromptModal" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="editNamePromptModalLabel" aria-hidden="true">
		  <div class="modal-header">
		    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
		    <h3 id="editNamePromptModalLabel">输入提示</h3>
		  </div>
		  	<div class="modal-body">
		  		<div class="control-group">
			     	<p class = "editNamePromptMsg"></p>
			     	<form id = "editNameForm" method = "post">
			     		<input type = "text" class = "newName" maxlength = "16"/>
			     		<input type = "hidden" id = "newName" name = "newname"/>
			     		<input type = "hidden" class = "oldName" id = "oldName" name = "oldname"/>
			     		<input type = "hidden" class = "editHideId"/>
			     		<input type = "hidden" name = "hostname" id = "editHostName"/>
			     		<input type = "hidden" name = "parentpath" id = "editParentPath"/>
			    	</form>
			    </div>
			</div>
			<div class="modal-footer">
				 <a class="btn btn-default" href="#" data-dismiss="modal" aria-hidden="true">关闭</a>&nbsp;&nbsp;<a class = "btn btn-primary" data-dismiss = "modal" aria-hidden = "true" onclick = "editPromptSubmit()">确定</a>
			</div>
	</div>
	
	<!-- 在拖拽文件到文件夹中时，文件夹中有和文件名相同的文件存在，提示是否覆盖 -->
	<div id="dragConfirmModal" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="dragConfirmModalLabel" aria-hidden="true">
		  <div class="modal-header">
		    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
		    <h3 id="dragConfirmModalLabel">复制文件</h3>
		  </div>
		  	<div class="modal-body">
		  		<div class="control-group">
			     	<p class = "dragConfirmMsg"></p>
			     	<input type = "hidden" class = "dragFileName" />
			     	<input type = "hidden" class = "dragHostName" />
			     	<input type = "hidden" class = "dragCurrPath"/>
			     	<input type = "hidden" class = "dragTargetPath"/>
			     	<input type = "hidden" class = "dragIsExist"/>
			     	<input type = "hidden" class = "dragCurrentObj"/>
			    </div>
			</div>
			<div class="modal-footer">
				 <a class="btn btn-default" href="#" data-dismiss="modal" aria-hidden="true">取消</a>&nbsp;&nbsp;<a class = "btn btn-primary" data-dismiss = "modal" aria-hidden = "true" onclick = "dragConfirmSubmit()">确定</a>
			</div>
	</div>
	
	<form id = "uploadFlushForm" method = "post">
		<input type = "hidden" id = "uploadHostName" name = "hostname"/>
		<input type = "hidden" id = "uploadParentPath" name = "currPath"/>
	</form>
	
	<!-- 在上传的时候对于重复的文件进行提示,是否要覆盖 -->
	<div id="replaceConfirmModal" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="replaceConfirmModalLabel" aria-hidden="true">
		  <div class="modal-header">
		    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
		    <h3 id="replaceConfirmModalLabel">复制文件</h3>
		  </div>
		  	<div class="modal-body">
		  		<div class="control-group">
			     	<p class = "replaceConfirmMsg"></p>
			    </div>
			</div>
			<div class="modal-footer">
				 <a class="btn btn-default" href="javascript:$('#myfile').uploadify('cancel', '*')" data-dismiss="modal" aria-hidden="true">取消</a>&nbsp;&nbsp;<a class = "btn btn-primary" data-dismiss = "modal" aria-hidden = "true" href = "javascript:$('#myfile').uploadify('upload','*')">确定</a>
			</div>
	</div>
</body>
</html>
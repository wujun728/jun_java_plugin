<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/static/taglibs.jsp"%>
<html>
<head>
<title>Hadoop集群配置向导 - 步骤【2】</title>
<link rel="stylesheet" href="${base}/static/code/codemirror.css" type="text/css" />
<script src="${base}/static/code/codemirror.js"></script>
<script src="${base}/static/code/xml.js"></script>
<script src="${base}/static/code/shell.js"></script>
</head>
<body>
<ul class="breadcrumb">
  <li><a href="${base}/index">首页</a> <span class="divider">/</span></li>
  <li class="active">Hadoop集群配置向导<span class="divider">/</span></li>
  <li class="active">步骤【2】</li>
</ul>
		<form  id="inputForm" name="master" action="${base}/hadoop/guide/step3" method="post" class="well">
			<fieldset>
	    		<div class="pagination">
		    		<legend>
			    		<ul>
							<li><a href="#">步骤【1】</a></li>
							<li><a href="#">步骤【2】</a></li>
							<li class="disabled"><a href="#">步骤【3】</a></li>
							<li class="disabled"><a href="#">步骤【4】</a></li>
							<li class="disabled"><a href="#">步骤【5】</a></li>
							<li class="disabled"><a href="#">完成</a></li>
						</ul>
					</legend>
				</div>
				<div id="messageBox" class="alert alert-error input-large controls" style="display:none">输入有误，请先更正。</div>
				<label class="control-label">
					请您确认信息的正确性，如果确认点击【下一步】，否则点击【上一步】重新配置。
					<hr>
				</label>
	    		<div class="control-group">
			      <label class="control-label" for="hdfsPort" title="默认端口为${master.hdfsPort}&#13; 例如:'9000'&#13; 及基本目录配置,&#13; 例如:'/user/hadoop'">HDFS端口及基本目录
			      </label>
			      <div class="controls">
				        hdfs://${master.ip}:<input type="text" id="hdfsPort" name="hdfsPort" value="${master.hdfsPort}" class="input-large required" style="width: 50px;" minlength='2' maxlength='5'>
				        <input type="text" id="hdfsBasePath" name="hdfsBasePath" value="${master.hdfsBasePath}" class="input-large" style="width: 175px;" maxlength='200'>
				      </div>
			    </div>
			    <div class="control-group">
			      <label class="control-label" title="为了确认系统给您的配置的准确性，请您参考系统为您获取的Hadoop配置文件">Hadoop配置文件
			      		<div class="controls">
				      		<a href="#testModal" role="button" class="btn dropdown-toggle" data-toggle="modal" onclick="getConfig('hadoop-env.sh');" data-placement="bottom" title="点击打开hadoop-env.sh查看内容"><i class="icon-file"></i>hadoop-env.sh</a>
					      	<a href="#testModal" role="button" class="btn dropdown-toggle" data-toggle="modal" onclick="getConfig('core-site.xml');" data-placement="bottom" title="点击打开core-site.xml查看内容"><i class="icon-file"></i>core-site.xml</a>
					      	<a href="#testModal" role="button" class="btn dropdown-toggle" data-toggle="modal" onclick="getConfig('hdfs-site.xml');" data-placement="bottom" title="点击打开hdfs-site.xml查看内容"><i class="icon-file"></i>hdfs-site.xml</a>
					      	<a href="#testModal" role="button" class="btn dropdown-toggle" data-toggle="modal" onclick="getConfig('mapred-site.xml');" data-placement="bottom" title="点击打开mapred-site.xml查看内容"><i class="icon-file"></i>mapred-site.xml</a>
				  		 </div>
			      </label>
			    </div>
			    <div class="control-group">
			      <label class="control-label" for="jvmSetupPath" title="系统默认为您检测到相关设置，如果要自行填写，&#13;请参考${master.hadoopSetupPath}/conf/hadoop-env.sh的export JAVA_HOME对应值或通过java -version查看">JVM虚拟机安装目录
				      <div class="controls">
				        <input type="text" id="jvmSetupPath" name="jvmSetupPath" value="${master.jvmSetupPath}" class="input-large required" style="width: 350px;" minlength='2' maxlength='200'>
				      </div>
			      </label>
			    </div>
			    <div class="control-group">
			      <label class="control-label" title="系统默认为您检测到相关设置，如果要自行填写，&#13;请参考${master.hadoopSetupPath}/conf/core-site.xml文件的hadoop.tmp.dir属性" for="hadoopTmpPath">hadoop.tmp.dir
				      <div class="controls">
				        <input type="text" id="hadoopTmpPath" name="hadoopTmpPath" value="${master.hadoopTmpPath}" class="input-large required" style="width: 350px;" minlength='2' maxlength='200'>
				      </div>
			      </label>
			    </div>
			     <div class="control-group">
			      <label class="control-label" title="系统默认为您检测到相关设置，如果要自行填写，&#13;请参考${master.hadoopSetupPath}/conf/hdfs-site.xml文件的dfs.name.dir属性" for="hadoopNamePath">dfs.name.dir
				      <div class="controls">
				        <input type="text" id="hadoopNamePath" name="hadoopNamePath" value="${master.hadoopNamePath}" class="input-large required" style="width: 350px;" minlength='2' maxlength='200'>
				      </div>
			      </label>
			    </div>
			     <div class="control-group">
			      <label class="control-label" title="系统默认为您检测到相关设置，如果要自行填写，&#13;请参考${master.hadoopSetupPath}/conf/core-site.xml文件的fs.checkpoint.dir属性" for="hadoopNamesecondaryPath">fs.checkpoint.dir
				      <div class="controls">
				        <input type="text" id="hadoopNamesecondaryPath" name="hadoopNamesecondaryPath" value="${master.hadoopNamesecondaryPath}" style="width: 350px;" class="input-large required" minlength='2' maxlength='200'>
				      </div>
			      </label>
			    </div>
			     <div class="control-group">
			      <label class="control-label">
			      	<hr>
			      	<a class="btn btn-large" href="${base}/hadoop/guide/step1" title="点击上一步不会保存当前配置">上一步</a>&nbsp;&nbsp;
					<input type="submit" value="下一步" class="btn btn-primary btn-large" data-loading-text="提交中..."/>
			      </label>
			    </div>
			</fieldset>
		</form>
		
<script>
	$(document).ready(function() {
		$("#inputForm").validate({
			errorContainer: "#messageBox",
			errorPlacement: function(error, element) {
				error.insertAfter(element);
			}
		});
	});
	var editor = null;
	function getConfig(configFile) {
		if(editor!=null){
			editor.toTextArea();
		}
		$("#resultTest").text('正在读取'+configFile+'文件...');
		$("#testModalLabel").text('读取Hadoop集群配置文件'+configFile);
        $.ajax({  
            url: "${base}/hadoop/guide/config?t=" + Math.random(),
            data: {
		    	   'config': configFile
		    	   },
            cache: false,
            type: "post",
            timeout: 25000,  
            success: function (msg) {
            	if(msg!=null){
            		$("#resultTest").remove();
            		$("#createTest").append("<textarea id='resultTest'></textarea>"); 
            		$("#resultTest").text(msg);
            		var mode = 'xml';
            		if(configFile=='hadoop-env.sh'){
            			mode = 'shell';
            		}
            		editor = CodeMirror.fromTextArea(document.getElementById('resultTest'), {
            		    mode: mode,
            		    lineNumbers: true,
            		    matchBrackets: true,
            		    readOnly: true
            		});
            	}
            },
            error: function (msg) {
            	if(msg!=null){
            		$("#resultTest").text('无法连接远程服务器，请暂停操作!');
            	}
            }
        })
    }
</script>
	<div id="testModal" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="testModalLabel" aria-hidden="true">
		  <div class="modal-header">
		    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
		    <h3 id="testModalLabel"></h3>
		  </div>
		  <div class="modal-body">
		  		<div class="control-group">
				      <div id="createTest" class="controls">
				      		<textarea id="resultTest"></textarea>
				      </div>
			    </div>
		 </div>
		<div class="modal-footer">
			<a class="btn" href="#" data-dismiss="modal" aria-hidden="true">关闭</a>
		</div>	  	
	</div>	
</body>
</html>
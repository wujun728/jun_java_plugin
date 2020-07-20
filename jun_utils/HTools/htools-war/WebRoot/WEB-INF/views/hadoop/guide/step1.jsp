<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/static/taglibs.jsp"%>
<html>
<head>
<title>Hadoop集群配置向导 - 步骤【1】</title>
</head>
<body>

<ul class="breadcrumb">
  <li><a href="${base}/index">首页</a> <span class="divider">/</span></li>
  <li class="active">Hadoop集群配置向导<span class="divider">/</span></li>
  <li class="active">步骤【1】</li>
</ul>

		<form id="inputForm" name="master" action="${base}/hadoop/guide/step2" method="post" class="well">
			<fieldset>
	    		<div class="pagination">
		    		<legend>
			    		<ul>
							<li><a href="#">步骤【1】</a></li>
							<li class="disabled"><a href="#">步骤【2】</a></li>
							<li class="disabled"><a href="#">步骤【3】</a></li>
							<li class="disabled"><a href="#">步骤【4】</a></li>
							<li class="disabled"><a href="#">步骤【5】</a></li>
							<li class="disabled"><a href="#">完成</a></li>
						</ul>
					</legend>
				</div>
	    		<div id="messageBox" class="alert alert-error input-large controls" style="display:none">输入有误，请先更正。</div>
	    		<div class="control-group">
			      <label class="control-label" title="Hadoop的NameNode节点的IP地址" for="ip">主节点IP地址
				      <div class="controls">
				        <input type="text" id="ip" name="ip" value="${master.ip}" class="input-large required" minlength='7' maxlength='17'>
				      	<a href="#syncModal" onclick="return isV();" data-toggle="modal" role="button" class="btn dropdown-toggle" data-placement="bottom" title="要使用HTools管理Hadoop集群，必须同步Hadoop主节点的Hosts文件到HTools所部署的服务器;如果确认已经同步过，可以略过此步骤；"><i class="icon-refresh"></i>同步Hosts文件</a>
				      </div>
			      </label>
			    </div>
	    		<div class="control-group">
			      <label class="control-label" for="hostname" title="Hadoop的NameNode节点的主机名">主节点主机名
				      <div class="controls">
				        <input type="text" id="hostname" name="hostname" value="${master.hostname}" class="input-large required" minlength='2' maxlength='50'>
				        <a href="#" role="button" class="btn dropdown-toggle" onclick="syncHn();" data-placement="bottom" title="如果无法自动获取，请您先点击‘同步Hosts文件’按钮，成功后即可自动获取主机名!"><i class="icon-arrow-left"></i>自动获取</a>
				      </div>
			      </label>
			    </div>
			    <div class="control-group">
			      <label class="control-label" title="安装和运行Hadoop时，Hadoop所使用的SSH账号." for="sshUsername">SSH账号(Hadoop专用)
				      <div class="controls">
				        <input type="text" id="sshUsername" name="sshUsername" value="${master.sshUsername}" class="input-large required" minlength='2' maxlength='30'>
				      </div>
			      </label>
			    </div>
			    <div class="control-group">
			      <label class="control-label" title="对应SSH账号所使用的密码" for="sshPassword">SSH密码
				      <div class="controls">
				        <input type="password" id="sshPassword" name="sshPassword" class="input-large required" minlength='1' maxlength='30'>
				      </div>
			      </label>
			    </div>
			    <div class="control-group">
			      <label class="control-label" title="SSH端口，默认是'${master.sshPort}'.&#13; 如果您的配置也是'${master.sshPort}'，此项无需修改." for="sshPort">SSH端口
				      <div class="controls">
				        <input type="text" id="sshPort" name="sshPort" value="${master.sshPort}" class="input-large required" style="width: 100px;" minlength='2' maxlength='5'>
				      </div>
			      </label>
			    </div>
			    <div class="control-group">
			      <label class="control-label" title="Hadoop的操作系统字符集，默认是'${master.encode}'.&#13; 如果您的配置也是'${master.encode}'，此项无需修改." for="encode">字符集
				      <div class="controls">
				        <input type="text" id="encode" name="encode" value="${master.encode}" class="input-large required" style="width: 100px;" minlength='2' maxlength='20'>
				      </div>
			      </label>
			    </div>
			    <div class="control-group">
			      <label class="control-label" title="Hadoop的安装的位置&#13;例如：'/home/user/hadoop'&#13;注意：不要使用'~'等LINUX系统字符,要填写完整路径" for="hadoopSetupPath">Hadoop安装目录
				      <div class="controls">
				           <input type="text" id="hadoopSetupPath" name="hadoopSetupPath" value="${master.hadoopSetupPath}" style="width: 350px;" class="input-large required" minlength='2' maxlength='250'>
				      </div>
			      </label>
			    </div>
				<p>
					<hr>
					<a href="#testModal" role="button" class="btn btn-success btn-large" data-toggle="modal" data-placement="bottom"  title="测试您所填写的Hadoop主机信息是否能够连接成功" onclick="testConnection()">连接测试</a>&nbsp;&nbsp;
					<input type="submit" value="下一步" class="btn btn-primary btn-large" data-loading-text="提交中..."/>
				</p>
			</fieldset>
		</form>
		
<script>
	$(document).ready(function() {
		$("#hostname").focus();
		$("#inputForm").validate({
			errorContainer: "#messageBox",
			errorPlacement: function(error, element) {
					error.insertAfter(element);
			}
		});
	});
	function isV(){
		if($.trim($("#ip").val())==''||$.trim($("#ip").val())=='127.0.0.1'){
			$("#syncResult").text('IP地址不能为空或为127.0.0.1!');
			return false;
		}else{
			$("#syncResult").text('如果HTools部署在Windows平台，请将hosts文件设为“非只读”权限；如果部署在Linux平台，请将hosts文件权限对当前用户开放；');
			$("#syncResult").append('<p><input placeholder="请输入root密码" title="请放心输入，仅此一次，系统不会记录您的root信息!" type="password" id="rootPassword" name="rootPassword"></p>');
			return true;
		}
	}
	function syncHosts() {
		if($.trim($("#rootPassword").val())==''){
			return false;
		}
		var rootpsw = $.trim($("#rootPassword").val());
		$("#syncResult").text('正在同步Hosts文件...请不要关闭该窗口!');
		$.ajax({  
            url: "${base}/hadoop/guide/synchosts",
            data: {
            		'ip': $.trim($('#ip').val()),
            		'rootpsw': rootpsw,
            		'sshPort': $.trim($('#sshPort').val())
		    	   },
            cache: false,
            type: "post",
            timeout: 25000,  
            success: function (msg) {
            	if(msg!=null){
            		if(msg=='1'){
            			$("#syncResult").text('Hosts文件同步成功!');
            		}else{
            			$("#syncResult").text('Hosts文件同步失败!');
            		}
            	}
            },
            error: function (msg) {
            	if(msg!=null){
            		$("#syncResult").text('由于网络原因Hosts文件同步失败!');
            	}
            }
        })
	}
	function syncHn() {
		$.ajax({  
            url: "${base}/hadoop/guide/synchn",
            data: {
		    	   'ip': $.trim($('#ip').val())
		    	   },
            cache: false,
            type: "post",
            timeout: 5000,  
            success: function (msg) {
            	if(msg!=null){
            		$("#hostname").val(msg);
            		$("#sshUsername").focus();
            		$("#hostname").focus();
            		$("#sshUsername").focus();
            	}
            },
            error: function (msg) {
            	if(msg!=null){
            		$("#hostname").val('不能获取,请点击"同步Hosts文件"');
            	}
            }
        })
	}
	function testConnection() {
		$("#resultTest").text('正在测试连接中...');
		$("#btnDIV").text('');
        $.ajax({  
            url: "${base}/hadoop/guide/test?t=" + Math.random(),
            data: {
		    	   'hostname': $.trim($('#hostname').val()),
		    	   'ip': $.trim($("#ip").val()),
		    	   'sshUsername': $.trim($("#sshUsername").val()),
		    	   'sshPassword': $.trim($("#sshPassword").val()),
		    	   'sshPort': $.trim($("#sshPort").val()),
		    	   'encode': $.trim($("#encode").val()),
		    	   'hadoopSetupPath': $.trim($("#hadoopSetupPath").val())
		    	   },
            cache: false,
            type: "post",
            timeout: 25000,  
            success: function (msg) {
            	if(msg!=null){
            		if(msg=='1'){
            			$("#resultTest").text('连接成功!');
            		}else if(msg=='3'){
            			$("#resultTest").text('虽然连接成功!但是:主机名填写错误!');
            		}else if(msg=='5'){
            			$("#resultTest").text('虽然连接成功!但是:Hadoop安装路径填写错误!');
            		}else if(msg=='7'){
            			$("#resultTest").text('连接失败!您填写的SSH连接信息有错误!');
            		}else if(msg=='9'){
            			$("#resultTest").text('您填写的信息不完整，请查看是否有遗漏!');
            		}else if(msg=='11'){
            			$("#resultTest").text('您配置的Hadoop集群信息已经存在，请重新填写该信息!');
            		}else if(msg=='13'){
            			$("#resultTest").text('虽然连接成功!但是:Hadoop服务没有启动!');
            			$("#btnDIV").append("<a href='#' class='btn btn-primary' onClick='return startup();'>启动Hadoop服务</a>");
            		}
            	}
            },
            error: function (msg) {
            	if(msg!=null){
            		$("#resultTest").text('无法连接远程服务器，请暂停操作!');
            	}
            }
        });
    }
	function startup(){
		$("#btnDIV").text('');
		$("#resultTest").text('正在启动Hadoop服务...');
		$.ajax({  
            url: "${base}/hadoop/guide/startup?t=" + Math.random(),
            data: {
		    	   'hostname': $.trim($('#hostname').val()),
		    	   'ip': $.trim($("#ip").val()),
		    	   'sshUsername': $.trim($("#sshUsername").val()),
		    	   'sshPassword': $.trim($("#sshPassword").val()),
		    	   'sshPort': $.trim($("#sshPort").val()),
		    	   'encode': $.trim($("#encode").val()),
		    	   'hadoopSetupPath': $.trim($("#hadoopSetupPath").val())
		    	   },
            cache: false,
            type: "post",
            timeout: 60000,  
            success: function (msg) {
            	if(msg!=null){
            		if(msg=='1'){
            			$("#resultTest").text('Hadoop服务启动成功!');
            		}else{
            			$("#resultTest").text('Hadoop服务启动失败!');
            		}
            	}
            },
            error: function (msg) {
            	if(msg!=null){
            		$("#resultTest").text('无法连接远程服务器，请暂停操作!');
            	}
            }
		});
	}
</script>
	<div id="testModal" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="testModalLabel" aria-hidden="true">
		  <div class="modal-header">
		    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
		    <h3 id="testModalLabel">Hadoop集群配置连接测试</h3>
		  </div>
		  <div class="modal-body">
		  		<div class="control-group">
				      <div class="controls">
				        	<div id="resultTest" style="font-size: 20px;">
				        	</div>
				        	<p id="btnDIV"></p>
				      </div>
			    </div>
		 </div>
		<div class="modal-footer">
			<a class="btn" href="#" data-dismiss="modal" aria-hidden="true">关闭</a>
		</div>	  	
	</div>	
	<div id="syncModal" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="syncModalLabel" aria-hidden="true">
		  <div class="modal-header">
		    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
		    <h3 id="testModalLabel">同步Hosts文件</h3>
		  </div>
		  <div class="modal-body">
		  		<div class="control-group">
				      <div class="controls">
				        	<div id="syncResult" style="font-size: 20px;">
				        		
				        	</div>
				      </div>
			    </div>
		 </div>
		<div class="modal-footer">
			<a class="btn" href="#" data-dismiss="modal" aria-hidden="true">关闭</a>
			<a class="btn btn-primary" href="#" onclick="syncHosts();">同步</a>
		</div>	  	
	</div>	
</body>
</html>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/static/taglibs.jsp"%>
<html>
<head>
<title>自选式优化  - 优化向导</title>
</head>
<body>
<ul class="breadcrumb">
  <li><a href="${base}/index">首页</a> <span class="divider">/</span></li>
  <li><a href="${base}/hadoop/optimization/list">自选式优化</a><span class="divider">/</span></li>
  <li class="active"> 完成</li>
</ul>
			
<div class="well">
	<h3>恭喜您，优化完成，请点击下面按钮重新启动hadoop集群才能生效。如果还不想立即重启集群，请稍后在“一键式服务”中操作。</h3>
	<input type="hidden" id="hostname" name="hostname" value="${optimization.hostname}">
	<p>
		<div align="center">
			<a href="#restartAllModal" role="button" class="btn btn-large btn-warning" title="重启hadoop服务" data-toggle="modal">重启hadoop服务</a>
		</div>
	</p>
	<div id="restartAllModal" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="restartAllLabel" aria-hidden="true">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
			<h3 id="restartAllLabel">是否确认重启Hadoop服务</h3>
		</div>
	<div class="modal-body">
		<div class="control-group">
			<div class="controls">
				<div id="restartAllInfo" style="font-size: 16px;">将重新启动namenode,secondarynamenode,datanode,jobtracker,tasktracker进程</div>
      		</div>
   		</div>
	</div>
  	<div class="modal-footer">
	    <button class="btn" data-dismiss="modal" aria-hidden="true">关闭</button>
	    <button id="restartAllBtn" class="btn btn-primary" onclick="restartAll();">确认</button>
  	</div>
</div>
<script type="text/javascript">
function restartAll() {
			$("#restartAllInfo").text('Hadoop服务正在重启中,请不要关闭此窗口,稍等片刻...');
			$.ajax({  
	            url: "${base}/hadoop/service/restart_all",
	            data: {
	            	'hostname': $.trim($('#hostname').val())
			    	   },
	            cache: false,
	            type: "post",
	            timeout: 120000,
	            success: function (msg) {
	            	if(msg!=null && msg == true){
	            		$("#restartAllInfo").text('重启成功，请稍后在节点监控模块中查看各节点状态');
	            	}else{
	            		$("#restartAllInfo").text('重启失败，请确认主节点是否状态异常');
	            	}
	            },
	            error: function (msg) {
	            	if(msg!=null){
	            		$("#restartAllInfo").text('重启失败，请确认主节点是否状态异常');
	            	}
	            }
	        })
	        $("#restartAllBtn").hide();
		}
</script>
</body>
</html>
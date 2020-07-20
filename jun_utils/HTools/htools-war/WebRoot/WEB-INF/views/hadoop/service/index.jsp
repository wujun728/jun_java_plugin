<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/static/taglibs.jsp"%>
<html>
<head>
<title>一键式服务  - Hadoop集群主机:${hostname}</title>
<style>
.main{
    position:absolute;
    top:50%;
    left:50%;
    width:600px;
    height:150px;
    margin-top:-150px;
    margin-left:-205px;

    border:1px solid red;
    font-size:16px;
    text-align:center;
    }
</style>

</head>
<body>
<ul class="breadcrumb">
  <li><a href="${base}/index">首页</a> <span class="divider">/</span></li>
  <li><a href="${base}/hadoop/service/list">一键式服务</a><span class="divider">/</span></li>
  <li class="active"> Hadoop集群主机:${hostname}</li>
</ul>

<div style="padding-left: 30px;padding-top: 10px;height: 400px;" class="well">

	<input type="hidden" id="hostname" value="${hostname}">
	
	<div style="margin-top:15px;margin-bottom: 30px;">
		<div class="control-group">
			<label class="control-label" title="启动Hadoop默认服务"><h4>启动Hadoop服务</h4></label>
			<div class="controls" style="padding-left: 30px;">
				<span><a href="#startAllModal" role="button" class="btn btn-large btn-primary" title="启动Hadoop默认服务(包含dfs和task)" data-toggle="modal" >&nbsp;启&nbsp;动&nbsp;</a></span>
				<span style="padding-left: 25px;"><a href="#startDFSModal" role="button" class="btn btn-large btn-primary" title="启动主节点的namenode，启动secondarynamenode，以及各从节点的datanode进程" data-toggle="modal" >&nbsp;只启动DFS&nbsp;</a></span>
				<span style="padding-left: 25px;"><a href="#restartAllModal" role="button" class="btn btn-large btn-warning" title="重启hadoop服务" data-toggle="modal">&nbsp;重&nbsp;启&nbsp;</a></span>
				<span style="padding-left: 25px;"><a href="#stopAllModal" role="button" class="btn btn-large btn-danger" title="关闭hadoop服务" data-toggle="modal" >&nbsp;关&nbsp;闭&nbsp;</a></span>
		 	</div>
		</div>
	</div>
	
	<hr>
	
	<div style="margin-top:15px;margin-bottom: 30px;">
		<div class="control-group">
			<label class="control-label" title="通知所有节点"><h4>通知所有节点</h4></label>
			<div class="controls" style="padding-left: 30px;">
				<span><a href="#cleanAllLogsModal" role="button" class="btn btn-large btn-primary" title="通知所有节点清空hadoop日志信息" data-toggle="modal">&nbsp;清空日志&nbsp;</a></span>
				<span style="padding-left: 25px;"><a href="#rebootAllNodesModal" role="button" class="btn btn-large btn-warning" title="通知所有节点重启" data-toggle="modal">&nbsp;重&nbsp;启&nbsp;</a></span>
				<span style="padding-left: 25px;"><a href="#shutdownAllNodesModal" role="button" class="btn btn-large btn-danger" title="通知所有节点关机" data-toggle="modal">&nbsp;关&nbsp;机&nbsp;</a></span>
		 	</div>
		</div>
	</div>
	
	<hr>
	
	<div style="margin-top:15px;margin-bottom: 30px;">
		<div class="control-group">
		 	<span style="float: left;">
			 	<label class="control-label" title="备份现有主节点信息，从secondary namenode恢复"><h4>恢复备份主节点</h4></label>
				<div class="controls" align="center">
					<a href="#backupNameNodeModal" role="button" class="btn btn-large btn-primary" title="恢复备份主节点" data-toggle="modal">&nbsp;恢&nbsp;复&nbsp;</a>
			 	</div>
		 	</span>
		 	<span style="float: left;padding-left: 50px;">
			 	<label class="control-label" title="负载均衡策略"><h4>负载均衡策略</h4></label>
				<div class="controls" align="center">
					<a href="#startBalanceModal" role="button" class="btn btn-large btn-primary" title="开启主节点负载均衡" data-toggle="modal" >&nbsp;开&nbsp;启&nbsp;</a>
				</div>
			</span>
			<span style="float: left;padding-left: 50px;">
				<label class="control-label" title="强制进入/退出安全模式"><h4>强制进入/退出安全模式</h4></label>
				<div class="controls" align="center">
					<div id="safeSwitch" class="make-switch switch-large" data-on="primary" data-off="danger">
						<c:if test="${isSafe == true}">
							<input type="checkbox" checked="checked">
						</c:if>
						<c:if test="${isSafe == false}">
							<input type="checkbox">
						</c:if>
					</div>
			 	</div>
			</span>
			<!-- 
			<span style="float: left;padding-left: 50px;">
				<label class="control-label" title="格式化NameNode节点，慎用！"><h4>格式化NameNode节点</h4></label>
				<div class="controls" align="center">
					<a href="#formatModal" role="button" class="btn btn-danger btn-large" data-toggle="modal" title="格式化NameNode节点">&nbsp;格式化&nbsp;</a>
			 	</div>
		 	</span>
		 	 -->
		</div>
	</div>

</div>	
	
	
	<!-- 以下为弹出窗体 -->
	
	
	<div id="startAllModal" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="startAllModalLabel" aria-hidden="true">
	  <div class="modal-header">
	    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
	    <h3 id="startAllModalLabel">是否启动全部Hadoop服务</h3>
	  </div>
	  <div class="modal-body">
	  	<div class="control-group">
	      <div class="controls">
	        	<div id="startAllInfo" style="font-size: 16px;">启动namenode,secondarynamenode,datanode,jobtracker,tasktracker进程</div>
	      </div>
    	</div>
	  </div>
	  <div class="modal-footer">
	    <button class="btn" data-dismiss="modal" aria-hidden="true">关闭</button>
	    <button id="startAllBtn" class="btn btn-primary" onclick="startAll();">确认</button>
	  </div>
	</div>
	
	<div id="startDFSModal" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="startDFSModalLabel" aria-hidden="true">
	  <div class="modal-header">
	    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
	    <h3 id="startDFSModalLabel">是否只启动DFS服务</h3>
	  </div>
	  <div class="modal-body">
	  	<div class="control-group">
	      <div class="controls">
	        	<div id="startDFSInfo" style="font-size: 16px;">启动主节点的namenode，启动secondarynamenode，以及各从节点的datanode进程</div>
	      </div>
    	</div>
	  </div>
	  <div class="modal-footer">
	    <button class="btn" data-dismiss="modal" aria-hidden="true">关闭</button>
	    <button id="startDFSBtn" class="btn btn-primary" onclick="startDFS();">确认</button>
	  </div>
	</div>
	
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
	
	<div id="stopAllModal" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="stopAllLabel" aria-hidden="true">
	  <div class="modal-header">
	    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
	    <h3 id="stopAllLabel">是否关闭Hadoop服务</h3>
	  </div>
	  <div class="modal-body">
	  	<div class="control-group">
	      <div class="controls">
	        	<div id="stopAllInfo" style="font-size: 16px;">将关闭namenode,secondarynamenode,datanode,jobtracker,tasktracker进程</div>
	      </div>
    	</div>
	  </div>
	  <div class="modal-footer">
	    <button class="btn" data-dismiss="modal" aria-hidden="true">关闭</button>
	    <button id="stopAllBtn" class="btn btn-primary" onclick="stopAll();">确认</button>
	  </div>
	</div>
	
	<div id="safeSwitchModal" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="safeSwitchLabel" aria-hidden="true">
		  <div class="modal-header">
		    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
		    <h3 id="safeSwitchLabel">是否强制进入/退出安全模式</h3>
		  </div>
		  <div class="modal-body">
		  	<div class="control-group">
		      <div class="controls">
		        	<div id="safeSwitchInfo" style="font-size: 16px;"></div>
		      </div>
	    	</div>
		  </div>
		  <div class="modal-footer">
		    <button class="btn" data-dismiss="modal" aria-hidden="true">关闭</button>
		  </div>
	</div>
	
	<div id="startBalanceModal" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="startBalanceLabel" aria-hidden="true">
		  <div class="modal-header">
		    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
		    <h3 id="startBalanceLabel">是否开启主节点负载均衡</h3>
		  </div>
		  <div class="modal-body">
		  	<div class="control-group">
		      <div class="controls">
		        	<div id="startBalanceInfo" style="font-size: 16px;">开启主节点负载均衡，默认平衡值域10%</div>
		      </div>
	    	</div>
		  </div>
		  <div class="modal-footer">
		    <button class="btn" data-dismiss="modal" aria-hidden="true">关闭</button>
		    <button id="startBalanceBtn" class="btn btn-primary" onclick="startBalance();">确认</button>
		  </div>
	</div>
	
	<div id="rebootAllNodesModal" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="rebootAllNodesLabel" aria-hidden="true">
	  <div class="modal-header">
	    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
	    <h3 id="rebootAllNodesLabel">是否确认通知所有节点重启</h3>
	  </div>
	  <div class="modal-body">
	  	<div class="control-group">
	      <div class="controls">
	        	<div id="rebootAllNodesInfo" style="font-size: 16px;">将重启所有datanode,tasktracker进程</div>
	      </div>
    	</div>
	  </div>
	  <div class="modal-footer">
	    <button class="btn" data-dismiss="modal" aria-hidden="true">关闭</button>
	    <button id="rebootAllNodesBtn" class="btn btn-primary" onclick="rebootAllNodes();">确认</button>
	  </div>
	</div>
	
	<div id="shutdownAllNodesModal" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="shutdownAllNodesLabel" aria-hidden="true">
	  <div class="modal-header">
	    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
	    <h3 id="shutdownAllNodesLabel">是否确认通知所有节点关机</h3>
	  </div>
	  <div class="modal-body">
	  	<div class="control-group">
	      <div class="controls">
	        	<div id="shutdownAllNodesInfo" style="font-size: 16px;">将所有节点关机</div>
	      </div>
    	</div>
	  </div>
	  <div class="modal-footer">
	    <button class="btn" data-dismiss="modal" aria-hidden="true">关闭</button>
	    <button id="shutdownAllNodesBtn" class="btn btn-primary" onclick="shutdownAllNodes();">确认</button>
	  </div>
 	</div>
 	
	<div id="cleanAllLogsModal" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="cleanAllLogsLabel" aria-hidden="true">
	  <div class="modal-header">
	    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
	    <h3 id="cleanAllLogsLabel">是否确认通知所有节点清空Hadoop日志</h3>
	  </div>
	  <div class="modal-body">
	  	<div class="control-group">
	      <div class="controls">
	        	<div id="cleanAllLogsInfo" style="font-size: 16px;">将清空所有节点的Hadoop日志</div>
	      </div>
    	</div>
	  </div>
	  <div class="modal-footer">
	    <button class="btn" data-dismiss="modal" aria-hidden="true">关闭</button>
	    <button id="cleanAllLogsBtn" class="btn btn-primary" onclick="cleanAllLogs();">确认</button>
	  </div>
 	</div>
	
	<div id="backupNameNodeModal" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="backupNameNodeLabel" aria-hidden="true">
	  <div class="modal-header">
	    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
	    <h3 id="backupNameNodeLabel">是否备份主节点</h3>
	  </div>
	  <div class="modal-body">
	  	<div class="control-group">
	      <div class="controls">
	        	<div id="backupNameNodeInfo" style="font-size: 16px;">将备份并删除现有主节点信息,并从secondary namenode上恢复主节点的信息</div>
	      </div>
    	</div>
	  </div>
	  <div class="modal-footer">
	    <button class="btn" data-dismiss="modal" aria-hidden="true">关闭</button>
	    <button id="backupNameNodeBtn" class="btn btn-primary" onclick="backupNameNode();">确认</button>
	  </div>
	</div>
	
	<div id="cleanAllLogsModal" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="cleanAllLogsLabel" aria-hidden="true">
	  <div class="modal-header">
	    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
	    <h3 id="cleanAllLogsLabel">是否确认通知所有节点清空Hadoop日志</h3>
	  </div>
	  <div class="modal-body">
	  	<div class="control-group">
	      <div class="controls">
	        	<div id="cleanAllLogsInfo" style="font-size: 16px;">将清空所有节点的Hadoop日志</div>
	      </div>
    	</div>
	  </div>
	  <div class="modal-footer">
	    <button class="btn" data-dismiss="modal" aria-hidden="true">关闭</button>
	    <button id="cleanAllLogsBtn" class="btn btn-primary" onclick="cleanAllLogs();">确认</button>
	  </div>
 	</div>
	
	<div id="formatModal" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="formatModalLabel" aria-hidden="true">
	  <div class="modal-header">
	    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
	    <h3 id="formatModalLabel">格式化NameNode节点</h4>
	  </div>
	  <div class="modal-body">
		<div class="control-group">
	      <div class="controls">
	        	<div id="formatModalInfo" style="font-size: 16px;">格式化NameNode时，会清空dfs.name.dir和dfs.name.edits.dir两个目录下的所有文件，并重新生成。请谨慎操作！</div>
	      </div>
    	</div>
	  </div>
	  <div class="modal-footer">
	    <button class="btn" data-dismiss="modal" aria-hidden="true">关闭</button>
	    <button id="formatBtn" class="btn btn-primary" onclick="formatNameNode();">确认格式化</button>
	  </div>
	</div>
	
<script type="text/javascript">
	function startAll() {
		$("#startAllInfo").text('正在启动中,请不要关闭此窗口,稍等片刻...');
		$.ajax({
            url: "${base}/hadoop/service/start_all",
            data: {
            	'hostname': $.trim($('#hostname').val())
		    	   },
            cache: false,
            type: "post",
            timeout: 600000, 
            success: function (msg) {
            	if(msg!=null && msg == true){
            		$("#startAllInfo").text('Hadoop服务启动成功，请稍后在节点监控中查看节点状态');
            	}else{
            		$("#startAllInfo").text('Hadoop服务启动失败，请检查主节点是否有异常');
            	}
            },
            error: function (msg) {
            	if(msg!=null){
            		$("#startAllInfo").text('Hadoop服务启动失败，请检查主节点是否有异常');
            	}
            }
        })
        $("#startAllBtn").hide();
	}
	
	function startDFS() {
		$("#startDFSInfo").text('正在启动中,请不要关闭此窗口,稍等片刻...');
		$.ajax({  
            url: "${base}/hadoop/service/start_dfs",
            data: {
            	'hostname': $.trim($('#hostname').val())
		    	   },
            cache: false,
            type: "post",
            timeout: 600000,
            success: function (msg) {
            	if(msg!=null && msg == true){
            		$("#startDFSInfo").text('Hadoop服务启动成功，请稍后在节点监控中查看节点状态');
            	}else{
            		$("#startDFSInfo").text('Hadoop服务启动失败，请检查主节点是否有异常');
            	}
            },
            error: function (msg) {
            	if(msg!=null){
            		$("#startDFSInfo").text('Hadoop服务启动失败，请检查主节点是否有异常');
            	}
            }
        })
        $("#startDFSBtn").hide();
	}
	
	function restartAll() {
		$("#restartAllInfo").text('Hadoop服务正在重启中,请不要关闭此窗口,稍等片刻...');
		$.ajax({  
            url: "${base}/hadoop/service/restart_all",
            data: {
            	'hostname': $.trim($('#hostname').val())
		    	   },
            cache: false,
            type: "post",
            timeout: 600000,
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
	
	function stopAll() {
		$("#stopAllInfo").text('Hadoop服务正在关闭,请不要关闭此窗口,稍等片刻...');
		$.ajax({  
            url: "${base}/hadoop/service/stop_all",
            data: {
            	'hostname': $.trim($('#hostname').val())
		    	   },
            cache: false,
            type: "post",
            timeout: 600000,
            success: function (msg) {
            	if(msg!=null && msg == false){
            		$("#stopAllInfo").text('Hadoop服务已关闭，请稍后在节点监控模块中查看');
            	}else{
            		$("#stopAllInfo").text('Hadoop服务关闭失败，请确认主节点是否状态异常');
            	}
            },
            error: function (msg) {
            	if(msg!=null){
            		$("#stopAllInfo").text('Hadoop服务关闭失败，请确认主节点是否状态异常');
            	}
            }
        })
        $("#stopAllBtn").hide();
	}
	
	function enterSafemode(){
		$('#safeSwitchModal').modal({
			backdrop: true,
			modal: true
		});
		$("#safeSwitchInfo").text('Hadoop正在强制进入安全模式,请不要关闭此窗口,稍等片刻...');
		$.ajax({  
            url: "${base}/hadoop/service/enter_safemode",
            data: {
            	'hostname': $.trim($('#hostname').val())
		    	   },
            cache: false,
            type: "post",
            timeout: 600000,
            success: function (msg) {
            	if(msg!=null && msg == true){
            		$("#safeSwitchInfo").text('Hadoop已强制进入安全模式');
            	}else{
            		$("#safeSwitchInfo").text('强制进入安全模式失败，请检查主节点是否存在异常');
            	}
            },
            error: function (msg) {
            	if(msg!=null){
            		$("#safeSwitchInfo").text('强制进入安全模式失败，请检查主节点是否存在异常');
            	}
            }
        })
	}
	
	function leaveSafemode(){
		$('#safeSwitchModal').modal({
			backdrop: true,
			modal: true
		});
		$("#safeSwitchInfo").text('Hadoop正在强制退出安全模式,请不要关闭此窗口,稍等片刻...');
		$.ajax({  
            url: "${base}/hadoop/service/leave_safemode",
            data: {
            	'hostname': $.trim($('#hostname').val())
		    	   },
            cache: false,
            type: "post",
            timeout: 600000,
            success: function (msg) {
            	if(msg!=null && msg == true){
            		$("#safeSwitchInfo").text('Hadoop已强制退出安全模式');
            	}else{
            		$("#safeSwitchInfo").text('强制退出安全模式失败，请检查主节点是否存在异常');
            	}
            },
            error: function (msg) {
            	if(msg!=null){
            		$("#safeSwitchInfo").text('强制退出安全模式失败，请检查主节点是否存在异常');
            	}
            }
        })
	}
	
	 $('#safeSwitch').on('switch-change', function (e, data) {
		 if(data.value == true){
			 enterSafemode();
		 }else{
			 leaveSafemode();
		 }
	 });
	 
	 function startBalance() {
		$("#startBalanceInfo").text('正在开启负载均衡,请不要关闭此窗口,稍等片刻...');
		$.ajax({  
            url: "${base}/hadoop/service/start_name_node_balancer",
            data: {
            	'hostname': $.trim($('#hostname').val())
		    	   },
            cache: false,
            type: "post",
            timeout: 600000,
            success: function (msg) {
            	if(msg!=null && msg == true){
            		$("#startBalanceInfo").text('负载均衡已成功开启!');
            	}else{
            		$("#startBalanceInfo").text('启动负载均衡失败，请检查主节点是否有异常');
            	}
            },
            error: function (msg) {
            	if(msg!=null){
            		$("#startBalanceInfo").text('启动负载均衡失败，请检查主节点是否有异常');
            	}
            }
        })
        $("#startBalanceBtn").hide();
	}
	 
	function rebootAllNodes() {
		$("#rebootAllNodesInfo").text('正在通知所有节点重启,请不要关闭此窗口,稍等片刻...');
		$.ajax({  
            url: "${base}/hadoop/service/reboot_all_nodes",
            data: {
            	'hostname': $.trim($('#hostname').val())
		    	   },
            cache: false,
            type: "post",
            timeout: 600000,
            success: function (msg) {
            	if(msg!=null && msg == true){
            		$("#rebootAllNodesInfo").text('通知所有节点重启完毕，请稍后在节点监控中查看各个节点状态');
            	}else{
            		$("#rebootAllNodesInfo").text('通知所有节点重启失败，请检查主节点是否有异常');
            	}
            },
            error: function (msg) {
            	if(msg!=null){
            		$("#rebootAllNodesInfo").text('通知所有节点重启失败，请检查主节点是否有异常');
            	}
            }
        })
        $("#rebootAllNodesBtn").hide();
	}
	 
	function shutdownAllNodes() {
		$("#shutdownAllNodesInfo").text('正在通知所有节点关机,请不要关闭此窗口,稍等片刻...');
		$.ajax({  
            url: "${base}/hadoop/service/shutdown_all_nodes",
            data: {
            	'hostname': $.trim($('#hostname').val())
		    	   },
            cache: false,
            type: "post",
            timeout: 600000,
            success: function (msg) {
            	if(msg!=null && msg == true){
            		$("#shutdownAllNodesInfo").text('通知所有节点关机成功，请稍后在节点监控中查看各个节点状态');
            	}else{
            		$("#shutdownAllNodesInfo").text('通知所有节点关机失败，请检查主节点是否有异常');
            	}
            },
            error: function (msg) {
            	if(msg!=null){
            		$("#shutdownAllNodesInfo").text('通知所有节点关机失败，请检查主节点是否有异常');
            	}
            }
        })
        $("#shutdownAllNodesBtn").hide();
	}
	 
	function cleanAllLogs() {
		$("#cleanAllLogsInfo").text('正在通知所有节点清空Hadoop日志信息,请不要关闭此窗口,稍等片刻...');
		$.ajax({  
            url: "${base}/hadoop/service/clean_all_logs",
            data: {
            	'hostname': $.trim($('#hostname').val())
		    	   },
            cache: false,
            type: "post",
            timeout: 600000,
            success: function (msg) {
            	if(msg!=null && msg == true){
            		$("#cleanAllLogsInfo").text('已成功通知所有节点清空Hadoop日志信息');
            	}else{
            		$("#cleanAllLogsInfo").text("通知所有节点清空Hadoop日志信息失败，请检查主节点是否状态异常");
            	}
            },
            error: function (msg) {
            	if(msg!=null){
            		$("#cleanAllLogsInfo").text("通知所有节点清空Hadoop日志信息失败，请检查主节点是否状态异常");
            	}
            }
        })
        $("#cleanAllLogsBtn").hide();
	}
	
	function backupNameNode() {
		$("#backupNameNodeInfo").text('NameNode正在备份恢复中,请不要关闭此窗口,稍等片刻...');
		$.ajax({  
            url: "${base}/hadoop/service/backup_namenode",
            data: {
            	'hostname': $.trim($('#hostname').val())
		    	   },
            cache: false,
            type: "post",
            timeout: 600000,
            success: function (msg) {
            	if(msg!=null && msg == true){
            		$("#backupNameNodeInfo").text('恭喜您，恢复成功!');
            	}else{
            		$("#backupNameNodeInfo").text('备份还原失败，请确认主节点是否状态异常');
            	}
            },
            error: function (msg) {
            	if(msg!=null){
            		$("#backupNameNodeInfo").text('备份还原失败，请确认主节点是否状态异常');
            	}
            }
        })
        $("#backupNameNodeBtn").hide();
	}
	 
	function formatNameNode(){
		$("#formatModalInfo").text('正在格式化NameNode主节点,请不要关闭此窗口,稍等片刻...');
		$.ajax({  
            url: "${base}/hadoop/service/format_name_node",
            data: {
            	'hostname': $.trim($('#hostname').val())
		    	   },
            cache: false,
            type: "post",
            timeout: 600000,
            success: function (msg) {
            	if(msg!=null && msg == true){
            		$("#formatModalInfo").text('格式化NameNode主节点成功');
            	}else{
            		$("#formatModalInfo").text("格式化NameNode主节点失败，请检查主节点状态是否异常");
            	}
            },
            error: function (msg) {
            	if(msg!=null){
            		$("#formatModalInfo").text("格式化NameNode主节点失败，请检查主节点状态是否异常");
            	}
            }
        })
        $("#formatBtn").hide();
	}
	 
</script>
	

</body>
</html>
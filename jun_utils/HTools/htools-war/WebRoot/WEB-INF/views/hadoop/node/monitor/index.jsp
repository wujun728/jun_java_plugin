<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/static/taglibs.jsp"%>
<html>
<head>
<title>Hadoop集群配置向导 - 节点监控维护 - ${hostname}</title>
<style>
	@-webkit-keyframes ledSpan{
	   50%{
	      opacity:0.5;
	    }
	   100%{
	      opacity:1;
	   }
	 }
	 .nodeText{
	 	font-size: 15px;
	 	font-style: italic;
	 	color: white;
	 	float: right;
	 	padding-right: 10px;
	 	padding-top: 3px;
	 }
	 .nodeText2{
	 	font-size: 18px;
	 	font-style: italic;
	 	color: white;
	 	float: right;
	 	padding-right: 5px;
	 	padding-top: 3px;
	 }
	 .nodeArea{
	 	float: left;
	 	padding-right: 20px;
	 	padding-bottom: 20px;
	 }
</style>
<script type="text/javascript">
setInterval(function() {
    $("#content").load(location.href+" #content>*","");
}, 15000);
</script>
</head>
<body>
<ul class="breadcrumb">
  <li><a href="${base}/index">首页</a> <span class="divider">/</span></li>
   <li><a href="${base}/hadoop/node/monitor/list">节点监控维护</a><span class="divider">/</span></li>
  <li class="active"> Hadoop集群主机:${hostname}</li>
</ul>

	<div id="content">
		<div>
			<input type="hidden" id="hostname" value="${hostname}">
			<span class="nodeArea"
				title="【元数据节点】&#13;主机名：${nameNode.hostname}&#13;IP地址：${nameNode.ip}&#13;状态：${nameNode.status}">
				<div class="dropdown">
					<a class="dropdown-toggle" data-toggle="dropdown" href="#">
						<div
							style="background-image: url('${base}/static/images/node.png');width: 180px;height: 50px;">
							<c:if test="${nameNode.status=='LIVE'}">
								<span id="led" class="label label-info">活动中</span>
							</c:if>
							<c:if test="${nameNode.status=='FAULT'}">
								<span id="led" class="label label-important">故障</span>
							</c:if>
							<b style="color: #000000">${nameNode.ip}</b>
							<p class="nodeText2">
								<b>NameNode</b>
							</p>
						</div>
					</a>
					<c:if test="${nameNode.hostname != null}">
						<input id="nameNode" name="nameNode" value="${nameNode.hostname}"
							type="hidden" />
						<ul class="dropdown-menu">
							<li><a href="#" data-toggle="modal"
								data-target="#firsetnode" onclick="firestNode()">添加一个数据节点</a></li>
							<li><a
								href="${base}/hadoop/node/add/datanode/hostname/${nameNode.hostname}">添加多个数据节点</a></li>
							<li><a href="#" data-toggle="modal" data-target="#dev"
								onclick="showdev('${nameNode.hostname}','${nameNode.ip}')">查看硬件状态</a></li>
							<li><a href="#" data-toggle="modal" data-target="#logs"
								onclick="showlog('${nameNode.hostname}','${nameNode.ip}','namenode')">查看节点日志</a></li>
						</ul>
					</c:if>
				</div>
			</span> <span class="nodeArea"
				title="【作业调度节点】&#13;主机名：${nameNode.jobTracker.hostname}&#13;IP地址：${nameNode.jobTracker.ip}&#13;状态：${nameNode.jobTracker.status}">
				<div class="dropdown">
					<a class="dropdown-toggle" data-toggle="dropdown" href="#">
						<div
							style="background-image: url('${base}/static/images/node.png');width: 180px;height: 50px;">
							<c:if test="${nameNode.jobTracker.status=='LIVE'}">
								<span id="led" class="label label-info">活动中</span>
							</c:if>
							<c:if test="${nameNode.jobTracker.status=='FAULT'}">
								<span id="led" class="label label-important">故障</span>
							</c:if>
							<b style="color: #000000">${nameNode.jobTracker.ip}</b>
							<p class="nodeText2">
								<b>JobTracker</b>
							</p>
						</div>
					</a>
					<c:if test="${nameNode.jobTracker.hostname != null}">
						<ul class="dropdown-menu">
							<li><a href="#" data-toggle="modal" data-target="#dev"
								onclick="showdev('${nameNode.jobTracker.hostname}','${nameNode.jobTracker.ip}')">查看硬件状态</a></li>
							<li><a href="#" data-toggle="modal" data-target="#logs"
								onclick="showlog('${nameNode.jobTracker.hostname}','${nameNode.jobTracker.ip}','jobtracker')">查看节点日志</a></li>
						</ul>
					</c:if>
				</div>
			</span> <span class="nodeArea"
				title="【次(备份)元数据节点】&#13;主机名：${nameNode.secondaryNameNode.hostname}&#13;IP地址：${nameNode.secondaryNameNode.ip}&#13;状态：${nameNode.secondaryNameNode.status}">
				<div class="dropdown">
					<a class="dropdown-toggle" data-toggle="dropdown" href="#">
						<div
							style="background-image: url('${base}/static/images/node.png');width: 180px;height: 50px;">
							<c:if test="${nameNode.secondaryNameNode.status=='LIVE'}">
								<span id="led" class="label label-info">活动中</span>
							</c:if>
							<c:if test="${nameNode.secondaryNameNode.status=='FAULT'}">
								<span id="led" class="label label-important">故障</span>
							</c:if>
							<b style="color: #000000">${nameNode.secondaryNameNode.ip}</b>
							<p class="nodeText2">
								<b>SecondaryNode</b>
							</p>
						</div>
					</a>
					<c:if test="${nameNode.secondaryNameNode.hostname != null}">
						<ul class="dropdown-menu">
							<li><a href="#" data-toggle="modal" data-target="#dev"
								onclick="showdev('${nameNode.secondaryNameNode.hostname}','${nameNode.secondaryNameNode.ip}')">查看硬件状态</a></li>
							<li><a href="#" data-toggle="modal" data-target="#logs"
								onclick="showlog('${nameNode.secondaryNameNode.hostname}','${nameNode.secondaryNameNode.ip}','secondaryname')">查看节点日志</a></li>
						</ul>
					</c:if>
				</div>
			</span>
		</div>

		<div>
			<c:forEach var="dataNode" items="${nameNode.dataNodes}"
				varStatus="index">
				<span class="nodeArea"
					title="【数据节点】&#13;主机名：${dataNode.hostname}&#13;IP地址：${dataNode.ip}&#13;状态：${dataNode.status}">
					<div class="dropdown">
						<a class="dropdown-toggle" data-toggle="dropdown" href="#">
							<div
								style="background-image: url('${base}/static/images/node.png');width: 180px;height: 50px;">
								<c:if test="${dataNode.status=='LIVE'}">
									<span id="led" class="label label-info">活动中</span>
								</c:if>
								<c:if test="${dataNode.status=='TEMP'}">
									<span id="led" class="label label-success">新节点</span>
								</c:if>
								<c:if test="${dataNode.status=='FAULT'}">
									<span id="led" class="label label-important">故障</span>
								</c:if>
								<c:if test="${dataNode.status=='DECOMMIS'}">
									<span id="led" class="label">退役</span>
								</c:if>
								<c:if test="${dataNode.status=='DEAD'}">
									<span id="led" class="label label-inverse">死亡</span>
								</c:if>
								<b style="color: #000000">${dataNode.ip}</b>
								<p class="nodeText">
									<b>DataNode</b>
								</p>
							</div>
						</a>
						<ul class="dropdown-menu">
							<c:if test="${dataNode.status=='TEMP'}">
								<li><a href="#" data-toggle="modal"
									data-target="#firsetnode"
									onclick="newadddatanode('${dataNode.ip}')">重新载入集群</a></li>
							</c:if>
							<c:if test="${dataNode.status=='FAULT'}">
								<li><a href="#" data-toggle="modal" data-target="＃verify"
									onclick="decommis('${dataNode.hostname}','${dataNode.ip}')">退役该节点</a></li>
							</c:if>
							<c:if test="${dataNode.status=='LIVE'}">
								<li><a href="#" data-toggle="modal" data-target="#verify"
									onclick="decommis('${dataNode.hostname}','${dataNode.ip}')">退役该节点</a></li>
							</c:if>
							<li><a href="#" data-toggle="modal" data-target="#dev"
								onclick="showdev('${dataNode.hostname}','${dataNode.ip}')">查看硬件状态</a></li>
							<li><a href="#" data-toggle="modal" data-target="#logs"
								onclick="showlog('${dataNode.hostname}','${dataNode.ip}','datanode')">查看节点日志</a></li>
						</ul>
					</div>
				</span>
			</c:forEach>
		</div>
	</div>
	<script>
	$(document).ready(function() {
		$(led).css({"-webkit-animation":"ledSpan 1s infinite ease-in-out"});
	});
	function showInfo(data){
		$("#modalTitle").text(data);
		$("#modalBody").text(data);
	}
	function showlog(_hname,hip,htype){
		 $("#modalBodylogs").text("");
		 $("#modalBodylogs").css({
			 "overflow-y":'scroll',
			 "height":"260px",
			 "width":"100%"
		 }); 
		 $("#modalBodylogs").append("<h4>加载中...</h4>");
		 $("#logs").css({left:"50%"});
		try{
			$.ajax({  
			     url: "${base}/hadoop/node/monitor/showlog?t=" + Math.random(),
			     data: {
			    	 type:htype,
			    	 hname:_hname,
			    	 namenode:$("#nameNode").val(),
			    	 ip:hip
				 },
			     cache: false,
			     type: "post",
			     dataType:"json",
			     success: function (msg) {
			    	 $("#modalBodylogs").text("");
			    	 for(var i=0;i<msg.logs.length;i++)
			    		$("#modalBodylogs").append("<label>"+msg.logs[i]+"</label>");
			     },
			     error: function (msg) {
			    	// alert(msg);
			     }
			   });
			}catch(ex){
				alert(ex);
			}
// 			 $("#logsTitle").text(htype+" "+_hanme+"日志");
// 		        $("#logss").text("<label><h4>日志加载中...</h4></label>");
	}
	//动态节点退役
	function decommis(hname,hip){
	  //  <input type="hidden" id="hname" name="hname"/>
       // <input type="hidden" id="hid" name="hid"/>
		$("input[id=hname]").val(hname);
		$("input[id=hip]").val(hip);
		$("#verify").css({left:"50%"});
	}
	function verifyDecommis(){
		try{
			$.ajax({  
			     url: "${base}/hadoop/node/monitor/adddecommis?t=" + Math.random(),
			     data: {
			    	 hostname:$("input[id=hname]").val(),
			    	 namenode:$("#nameNode").val(),
			    	 ip:$("input[id=hip]").val()
				 },
			     cache: false,
			     type: "post",
			     dataType:"json",
			     success: function (msg) {
			    	if(msg.status == true){
						$("#modalTitle").text(name+"退役成功");
			    	}else{
			    		$("#modalTitle").text(name+"退役失败");
			    	}
			     },
			     error: function (msg) {
			    	// alert(msg);
			     }
			   });
			}catch(ex){
				alert(ex);
			}
	}
	//动态节点添加
	function newaddddatanode(){
			$("#titleBody").text("添加中请稍后");	
			$("#addDeploy").css({left:"50%"});
			$("#addDeploy").modal("show");
	try{
			$.ajax({  
			     url: "${base}/hadoop/node/add/deployonenode?t=" + Math.random(),
			     data: {
			    	 hostname:$("#datanodehostname").val(),
			    	 namenode:$("#nameNode").val(),
			    	 sshRootPassword:$("#dataROOTPassword").val(),
			    	 sshPassword:$("#dataSSHPassword").val(),
			    	 sshUsername:$("#dataSSHUsername").val(),
			    	 ip:$("#datanodeip").val(),
			    	 sshPort:$("#sshPort").val(),
			    	 encode:$("#encode").val(),
			    	 //选择
			    	 changeProfile:$("#changeProfile").is(":checked"),
			    	 addHosts:$("#addHosts").is(":checked"),
			    	 balancer:$("#balancer").is(":checked"),
			    	 firewell:$("#firewell").is(":checked"),
			    	 taskTracker:$("#taskTracker").is(":checked"),
			    	 syncAllNodeConfig:$("#syncAllNodeConfig").is(":checked"),
			    	 addSlaves:$("#addSlaves").is(":checked"),
			    	 removeLogs:$("#removeLogs").is(":checked"),
			    	 autoStartupDataNode:$("#autoStartupDataNode").is(":checked")
				 },
			     cache: false,
			     type: "post",
			     dataType:"json",
			     success: function (msg) {
			    	if(msg.status == true){
						$("#titleBody").text("添加成功");
			    	}else{
			    		$("#titleBody").text("添加失败");	
			    	}
					$("#addDeploy").css({left:"50%"});
			    	$("#addDeploy").modal("show");
			     },
			     error: function (msg) {
			    	 alert(msg);
			     }
			   });
			}catch(ex){
				alert(ex);
			} 
	}


	
	var jobTime = null;
	var _hostname = null;
	var _hostip = null;
	var _job = true;
	
	function showdev(hname,hip){
		 $("#fdisk").append("<tr><td colspan='6'>加载中...</td></tr>");
    	 $("#cpu").text("加载中...");
    	 $("#cpuname").text("加载中...");
    	 $("#memtotal").text("加载中...");
    	 $("#memused").text("加载中...");
    	 $("#memfree").text("加载中...");
    	 dev(hname,hip);
	}
	
	function dev(hname,hip){
		_hostname = hname;
		_hostip = hip;
		try{
		$.ajax({  
		     url: "${base}/hadoop/node/hardware/show?t=" + Math.random(),
		     data: {
		    	 hostname:hname,
		    	 namenode:$("#nameNode").val(),
		    	 ip:hip
			 },
		     cache: false,
		     type: "post",
		     dataType:"json",
		     success: function (msg) {
		    	 $("#fdisk").text("");
		    	 $("#cpu").text(msg.cpu.load+"%");
		    	 $("#cpuname").text(msg.cpu.name);
		    	 $("#memtotal").text(msg.memory.total+"M");
		    	 $("#memused").text(msg.memory.use+"M");
		    	 $("#memfree").text(msg.memory.free+"M");
		    	 var disks = msg.disks;
		    	 for(var n in disks){
 		    		$("#fdisk").append("<tr><td>"+n+"</td><td  style='text-align:right'>"+disks[n][1]+"</td><td style='text-align:right'>"+disks[n][2]+"</td><td  style='text-align:right'>"+disks[n][3]+"</td><td style='text-align:right'>"+disks[n][4]+"</td><td >"+disks[n][5]+"</td></tr>");
		    	 }
		     },
		     error: function (msg) {
		    	// alert(msg);
		     }
		   });
		}catch(ex){
			alert(ex);
		}
		$("#dev").css({left:"50%"});
		jobTime = window.setTimeout("dev('"+_hostname+"','"+_hostip+"')",2000);
	}
	function tJop(){
		window.clearTimeout(jobTime);
	}
	function firestNode(){
		$("#ip").val("");
		$("#firsetnode").css({left:"50%"});
		$("#myModal").css({left:"50%"});
	}
	function jiaru(){
		
		if($("#ip").val() == ''){
			$('#ipNotNull').modal('show');
		}else{
			$('#myModal').modal('show');
			$.ajax({  
	            url: "${base}/hadoop/node/hardware/check",
	            data: {
	            		'ip': $.trim($('#ip').val()),
	            		'hostname': $.trim($('#hostname').val())
			    	   },
	            cache: false,
	            type: "post",
	            timeout: 25000,  
	            success: function (msg) {
	            	if(msg!=null){
	            		if(msg=='1'){
		            		$("#subBtn").hide();
	            			$("#syncResult").text('系统检测到部署节点为NameNode主节点,是否继续？');
	            		}else if(msg=='2'){
	            			$("#subBtn").hide();
	            			$("#nodeInfo").hide();
	            			$("#continueBtn").hide();
	            			$("#syncResult").text('部署节点已处于集群中正常运行，请不要重复添加!');
	            		}else{
	            			$("#continueBtn").hide();
	            		}
	            	}
	            },
	            error: function (msg) {
	            	if(msg!=null){
	            		$("#nodeInfo").hide();
	            		$("#subBtn").hide();
	            		$("#syncResult").text('填写错误，请重新检查后填写!');
	            	}
	            }
	        })
			$("#datanodeip").val($("#ip").val());
			$("#dataROOTPassword").val($("#sshRootPassword").val());
			$("#dataSSHUsername").val($("#sshUsername").val());
			$("#dataSSHPassword").val($("#sshPassword").val());
			
		}
		
	}
	function newadddatanode(hip){
		$("#myModal").css({left:"50%"});
		$("#firsetnode").css({left:"50%"});
 		$("#ip").val(hip);
	}
</script>
	<div class="modal fade" id="myModal" aria-labelledby="myModalLabel" aria-hidden="true" style="left: 10000px">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
					<h4 class="modal-title" id="modalTitle"></h4>
				</div>
				<div id="modalBody">
					<div id="nodeInfo" class="navbar">
						<div class="navbar-inner">
							<input type="hidden" id="datanodeip" name="datanodeip" />
							<input type="hidden" id="datanodehostname" name="datanodehostname" />
							<input type="hidden" id="dataROOTPassword" name="dataROOTPassword" />
							<input type="hidden" id="dataSSHPassword" name="dataSSHPassword" />
							<input type="hidden" id="dataSSHUsername" name="dataSSHUsername" />
							<input type="hidden" id="datanewnode" name="datanewnode" />
							<input type="hidden" id="sshPort" name="sshPort" value="${master.sshPort}" />
							<input type="hidden" id="encode" name="encode" value="${master.encode}" />
							<ul class="nav">
								<li><label>&nbsp;</label><label>防火墙</label>
										<div class="make-switch switch-mini" data-on="primary" data-off="info">
											<input type="checkbox" id="firewell" name="firewell" />
										</div>
									<lable> &nbsp; </lable> 
									<label><input type="checkbox" id="changeProfile" name="changeProfile" checked />修改环境变量 </label> 
									<label><input type="checkbox" id="addSlaves" name="addSlaves" checked />加入到Slaves文件</label></li>
								<li>&nbsp;</li>
								<li>&nbsp;</li>
								<li>&nbsp;</li>
								<li>&nbsp;</li>
								<li>&nbsp;</li>
								<li><label>&nbsp;</label><label>负载均衡</label>
										<div class="make-switch switch-mini" data-on="primary" data-off="info">
											<input type="checkbox" id="balancer" name="balancer" checked />
										</div>
									<lable> &nbsp; </lable> 
									<label><input type="checkbox" id="addHosts" name="addHosts" checked="" /> 添加到主机配置映射</label> 
									<label><input type="checkbox" id="removeLogs" name="removeLogs" checked />移除安装后无用日志</label>
								</li>
								<li>&nbsp;</li>
								<li>&nbsp;</li>
								<li>&nbsp;</li>
								<li>&nbsp;</li>
								<li><label>&nbsp;</label><label>taskTracker守护进程</label>
										<div class="make-switch switch-mini" data-on="primary" data-off="info">
											<input type="checkbox" id="taskTracker" name="taskTracker" checked />
										</div>
									<lable> &nbsp; </lable> 
									<label title="同步所有的配置文件（Slaves）"><input type="checkbox" id="syncAllNodeConfig" name="syncAllNodeConfig" /> 同步所有的配置文件</label> 
									<label><input type="checkbox" id="autoStartupDataNode" name="autoStartupDataNode" checked /> 启动安装后的数据节点</label>
								</li>
							</ul>
						</div>
					</div>
					<div id="nodeError" >
						<div class="control-group">
					      <div class="controls">
					        	<div id="syncResult" style="font-size: 20px;">
					        	</div>
					      </div>
			    		</div>
					</div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					<button id="subBtn" type="button" class="btn btn-primary" data-dismiss="modal" onclick="newaddddatanode()">提交</button>
					<button id="continueBtn" type="button" class="btn btn-primary" data-dismiss="modal" onclick="newaddddatanode()">仍然继续</button>
				</div>
			</div>
		</div>
	</div>
	<div class="modal fade" id="ipNotNull" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
         			<h3 class="modal-title" id="modalTitle">提示</h3>
           
      			</div>
	       		<div  id="modalBody"> 
	        		<center><h4 id="testModalLabel">部署节点IP地址不能为空</h4></center>
	       		</div> 
				<div class="modal-footer">
		        	<button type="button" class="btn" data-dismiss="modal">关闭</button>
		      	</div>
			</div>
		</div>
	</div>

	<!-- Modal 部署一个数据节点-->
	<div class="modal fade" id="firsetnode" aria-labelledby="myModalLabel" aria-hidden="true" style="left: 10000px">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
					<h4 class="modal-title" id="modalTitledeploy">部署一个数据节点</h4>
				</div>
				<div id="modalBody">
				<form id="formId">
					<table style="margin: 0px auto;">
						<tr>
							<td>
								<div class="control-group">
									<label class="control-label" title="Hadoop的DataNode节点的IP地址" for="ip">要部署节点IP地址<i style="color: red">*</i>
										<div class="controls">
											<input type="text" id="ip" name="ip" onkeyup="clearNoNum(this)"/>
										</div>
									</label>
								</div>
								<div class="control-group">
									<label class="control-label"
										title="安装Hadoop节点时，Hadoop所使用的root权限密码." for="sshRootPassword">Root密码(安装Hadoop时使用)<i style="color: red">*</i>
										<div class="controls">
											<input type="password" id="sshRootPassword"
												name="sshRootPassword" class="input-large required" />
										</div>
									</label>
								</div>
								<div class="control-group">
									<label class="control-label" title="安装和运行Hadoop时，Hadoop所使用的SSH账号." for="sshUsername">SSH账号(Hadoop专用)<i style="color: red">*</i>
										<div class="controls">
											<input type="text" id="sshUsername" name="sshUsername" value="${master.sshUsername}" class="input-large required" minlength="2" maxlength="30" />
										</div>
									</label>
								</div>
								<div class="control-group">
									<label class="control-label" title="对应SSH账号所使用的密码" for="sshPassword">SSH密码<i style="color: red">*</i>
										<div class="controls">
											<input type="password" id="sshPassword" name="sshPassword" class="input-large required" minlength="1" maxlength="30" value="${master.sshPassword}" />
										</div>
									</label>
								</div>
							</td>
						</tr>
					</table>
					</form>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					<button type="submit" data-toggle="modal" class="btn btn-primary" data-dismiss="modal" onclick="jiaru()">加入节点</button>
				</div>
			</div>
		</div>
	</div>

	<div class="modal fade" id="dev" aria-labelledby="myModalLabel" aria-hidden="true" style="left: 10000px">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
					<h4 class="modal-title" id="logsTitle">硬件信息</h4>
				</div>
				<div id="modalBodydev" aria-hidden="true">
					<table style="margin: 0px auto;">
						<tr>
							<td valign="middle">
								<table border="1" width="100%">
									<tr>
										<td width="60px" style="background-color: #EFEFEF">CPU名称</td>
										<td id="cpuname">加载中...</td>
									</tr>
									<tr>
										<td style="background-color: #EFEFEF">使用率</td>
										<td id="cpu" style='text-align: center'>加载中...</td>
									</tr>
								</table>
							</td>
						</tr>
						<tr>
							<td>
								<table border="1" width="100%">
									<tr>
										<td colspan="2" style="background-color: #EFEFEF">内存使用率&nbsp;</td>
										<td style="background-color: #EFEFEF">&nbsp;总&nbsp;数&nbsp;</td>
										<td id="memtotal">加载中...</td>
										<td style="background-color: #EFEFEF">&nbsp;使&nbsp;用&nbsp;</td>
										<td id="memused">加载中...</td>
										<td style="background-color: #EFEFEF">&nbsp;空&nbsp;闲&nbsp;</td>
										<td id="memfree">加载中...</td>
									</tr>
								</table>
							</td>
						</tr>
						<tr>
							<td valign="middle">
								<table border="1" width="100%">
									<thead>
										<tr>
											<td colspan="6" style="background-color: #EFEFEF">硬盘使用率</td>
										</tr>
										<tr>
											<td style="background-color: #EFEFEF">文件系统</td>
											<td style='text-align: right; background-color: #EFEFEF'>大小</td>
											<td style='text-align: right; background-color: #EFEFEF'>使用大小</td>
											<td style='text-align: right; background-color: #EFEFEF'>可用大小</td>
											<td style='text-align: right; background-color: #EFEFEF'>使用率</td>
											<td style='text-align: right; background-color: #EFEFEF'>挂载信息</td>
										</tr>
									</thead>
									<tbody id="fdisk">
									</tbody>
								</table>
							</td>
						</tr>
					</table>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal" onclick="tJop()">关闭</button>
				</div>
			</div>
		</div>
	</div>

	<div class="modal fade" id="logs" aria-labelledby="myModalLabel" aria-hidden="true" style="left: 10000px">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
					<h4 class="modal-title" id="logsTitle">日志查看</h4>
				</div>
				<div id="modalBodylogs" aria-hidden="true"></div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
				</div>
			</div>
		</div>
	</div>

	<div class="modal fade" id="verify" aria-labelledby="myModalLabel" aria-hidden="true" style="left: 10000px">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
					<h4 class="modal-title" id="verifyTitle">退役确认</h4>
				</div>
				<div id="modalBodyverify" aria-hidden="true">
					<input type="hidden" id="hname" name="hname" /> <input type="hidden" id="hip" name="hip" />
					<h4>你真的要退役吗？</h4>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					<button type="button" class="btn btn-primary" data-dismiss="modal" onclick="verifyDecommis()">提交</button>
				</div>
			</div>
		</div>
	</div>

	<div class="modal fade" id="addDeploy" aria-labelledby="myModalLabel" aria-hidden="true" style="left: 10000px">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
					<h4 class="modal-title" id="addTitle">提示信息</h4>
				</div>
				<div id="addDeployBody" aria-hidden="true" style="text-align: center;">
					<h4 id="titleBody">添加中请稍后</h4>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
				</div>
			</div>
		</div>
	</div>

	<script>
	
	function clearNoNum(obj){
        //先把非数字的都替换掉，除了数字和.
        obj.value = obj.value.replace(/[^\d.]/g,"");
        //必须保证第一个为数字而不是.
        obj.value = obj.value.replace(/^\./g,"");
    }
	
	$(document).ready(function() {
		
		jQuery.validator.addMethod("ip", function(value, element) {    
		      return this.optional(element) || /^(([1-9]|([1-9]\d)|(1\d\d)|(2([0-4]\d|5[0-5])))\.)(([1-9]|([1-9]\d)|(1\d\d)|(2([0-4]\d|5[0-5])))\.){2}([1-9]|([1-9]\d)|(1\d\d)|(2([0-4]\d|5[0-5])))$/.test(value);    
		    }, "请填写正确的IP地址。");
		
		var validate = $("#formId").validate({
            debug: true, //调试模式取消submit的默认提交功能    
            submitHandler: function(form){   //表单提交句柄,为一回调函数，带一个参数：form   
            },   
            
            rules:{
                ip:{
                    ip:true
                }                
            },
            messages:{
                ip:{
                }
            }
        });
	});
	</script>

</body>
</html>
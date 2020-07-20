<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/static/taglibs.jsp"%>
<html>
 <head> 
  <title>Hadoop集群数据节点配置</title> 


  <style type="text/css">
.controls {
	padding-left: 0;
}

@-webkit-keyframes ledSpan{
	   50%{
	      opacity:0.5;
	    }
	   100%{
	      opacity:1;
	   }
	 }

</style> 
 </head> 
 <body> 
  <ul class="breadcrumb"> 
   <li><a href="${base}/index">首页</a> <span class="divider">/</span></li> 
   <li><a href="${base}/hadoop/node/monitor/list">节点监控维护</a><span class="divider">/</span></li> 
   <li><a href="${base}/hadoop/node/monitor/hostname/${master.hostname}">Hadoop集群主机:${master.hostname}</a><span class="divider">/</span></li> 
   <li class="active">Hadoop集群数据节点部署<span class="divider"></span></li> 
  </ul> 
  <form id="inputForm" name="master" action="${base}/hadoop/node/add/deploy" method="post" class="well"> 
   <fieldset> 
    <div> 
     <div id="messageBox" class="alert alert-error input-large controls" style="display: none">
      输入有误，请先更正。
     </div> 
     <!--  --> 
     <label style="float: left">NameNode主机IP：${master.ip} &nbsp;&nbsp;</label>
     <label>NameNode主机名：${master.hostname}</label> 
     <div class="control-group" style="float: left"> 
      <label class="control-label" title="Hadoop的DataNode节点的IP地址" for="ip">要部署节点IP地址<i style="color:red">*</i>
       <div class="controls"> 
        <input type="text" id="ip" name="ip" value="${master.ip}/255" class="input-large required" minlength="7" maxlength="19" /> 
       </div> </label> 
<!--        <lable  title="如果目标主机没有hadoop用户,就直接创建"> -->
<!--        <input type="checkbox" checked/>自动创建Hadoop用户 -->
<!--        </lable> -->
<%--         	<input type="hidden" name="nodes" id="nodes" value="${nameNode.ip}">  --%>
<%--         	<input type="hidden" name="nodes" id="nodes" value="${nameNode.jobTracker.hostname}">  --%>
<%--         	<input type="hidden" name="nodes" id="nodes" value="${nameNode.secondaryNameNode.hostname}">  --%>
<%-- 			<c:forEach var="dataNode" items="${nameNode.dataNodes}" varStatus="index"> --%>
<%-- 				<input type="hidden" name="nodes" id="nodes" value="${dataNode.ip}"/> --%>
<%-- 			</c:forEach> --%>
     </div> 
     <div class="control-group" style="float: left"> 
      <label class="control-label" title="安装Hadoop节点时，Hadoop所使用的root权限密码." for="sshRootPassword">Root密码(安装Hadoop时使用)<i style="color:red">*</i> 
       <div class="controls"> 
        <input type="password" id="sshRootPassword" name="sshRootPassword" class="input-large required" /> 
       </div> </label> 
<!--        <input type="checkbox"/>跳过节点检测直接部署  -->
     </div> 
     <div class="control-group" style="float: left"> 
      <label class="control-label" title="安装和运行Hadoop时，Hadoop所使用的SSH账号." for="sshUsername">SSH账号(Hadoop专用)<i style="color:red">*</i> 
       <div class="controls"> 
        <input type="text" id="sshUsername" name="sshUsername" value="${master.sshUsername}" class="input-large required" minlength="2" maxlength="30" /> 
       </div> </label> 
     </div> 
     <div class="control-group" style="float: left"> 
      <label class="control-label" title="对应SSH账号所使用的密码" for="sshPassword">SSH密码<i style="color:red">*</i> 
       <div class="controls"> 
        <input type="password" id="sshPassword" name="sshPassword" class="input-large required" minlength="1" maxlength="30" value="${master.sshPassword}" /> 
       </div> </label> 
     </div> 
     <div class="control-group"> 
      <label class="control-label">&nbsp; 
        <div class="controls"> 
         <button type="button" id="test" role="button" class="btn btn-success" onclick="submitInfo()">检测网段中的主机</button>
<!--         <a id="test" href="#dd" role="button" class="btn btn-success" data-toggle="modal" data-placement="bottom" title="" onclick="submitInfo()" >检测网段中的主机</a>  -->
<!--         <a href="#testModal" role="button" class="btn btn-success" data-toggle="modal" data-placement="bottom" title="">部署节点</a>  -->
<!-- 	    <input type="submit" role="button" class="btn btn-success" value="部署节点" onclick="return deploy()"/> -->
         </div> 
       </label> 
     </div> 
     <div>
     <label>
    &nbsp;
    </label>
<!--     <label> -->
<!--     &nbsp; -->
<!--     </label> -->
    </div> 

<div class="accordion" id="accordion2">
<div class="accordion-group" title="部署节点选项，用来对要部署的节点进行一些基本配置">
<div class="accordion-heading" onclick="if($('i[id=xq]').attr('class')=='icon-chevron-up'){$('i[id=xq]').attr('class','icon-chevron-down'); $('#collapseTwo').collapse('show');}else{$('i[id=xq]').attr('class','icon-chevron-up');}">
 <a class="accordion-toggle" data-toggle="collapse" data-parent="#accordion2" href="#collapseOne"> 数据节点部署选项<i style="color:red">＊</i>&nbsp;&nbsp;<i class="icon-chevron-up" id="xq"></i> </a> 
</div>
<div id="collapseOne" class="accordion-body collapse in">
<div class="accordion-inner">
        <div class="navbar"> 
         <div class="navbar-inner"> 
          <ul class="nav"> 
           <li><label>&nbsp;</label> <label> <label>防火墙</label> 
             <div class="make-switch switch-mini" data-on="primary" data-off="info"> 
              <input type="checkbox" id="firewell" name="firewell" /> 
             </div> </label> 
            <lable>
             &nbsp;
            </lable> <label><input type="checkbox" id="changeProfile" name="changeProfile" checked="" />修改环境变量 </label> <label><input type="checkbox" id="addSlaves" name="addSlaves" checked="" /> 加入到Slaves文件</label></li> 
           <li>&nbsp;</li> 
           <li>&nbsp;</li> 
           <li>&nbsp;</li> 
           <li>&nbsp;</li> 
           <li>&nbsp;</li> 
           <li><label>&nbsp;</label> <label> <label>负载均衡</label> 
             <div class="make-switch switch-mini" data-on="primary" data-off="info"> 
              <input type="checkbox" id="balancer" name="balancer" checked="" /> 
             </div> </label> 
            <lable>
             &nbsp;
            </lable> <label><input type="checkbox" id="addHosts" name="addHosts" checked="" /> 添加到主机配置映射</label> <label><input type="checkbox" id="removeLogs" name="removeLogs" checked="" />移除安装后无用日志</label> </li> 
           <li>&nbsp;</li> 
           <li>&nbsp;</li> 
           <li>&nbsp;</li> 
           <li>&nbsp;</li> 
           <li><label>&nbsp;</label> <label> <label>taskTracker守护进程</label> 
             <div class="make-switch switch-mini" data-on="primary" data-off="info"> 
              <input type="checkbox" id="taskTracker" name="taskTracker" checked="" /> 
             </div> </label> 
            <lable>
             &nbsp;
            </lable> <label title="同步所有的配置文件（Slaves）"><input type="checkbox" id="syncAllNodeConfig" name="syncAllNodeConfig" /> 同步所有的配置文件</label> <label><input type="checkbox" id="autoStartupDataNode" name="autoStartupDataNode" checked="" /> 启动安装后的数据节点</label></li> 
          </ul> 
         </div> 
        </div> 
</div>
</div>
</div>
<!-- context  -->
<div class="accordion-group" >
<div id="progress" class="accordion-heading" style="position:relative;height:20px;display:none">
	<div class="progress progress-striped active" style="height:20px;margin-bottom:0;position:absolute;z-index:1;width:100%">
	   <div class="bar" id="listbar" name="listbar" ></div>
	</div>
	<div style="position:absolute;z-index:2;width:100%">
	  <a class="accordion-toggle" data-toggle="collapse" data-parent="#accordion2" href="#collapseTwo">
<!-- 		<span id="led" class="label label-info" style="display:none">加载中...</span>  -->
	  </a>
	</div>
</div>
<div id="collapseTwo" class="accordion-body collapse">
<div class="accordion-inner">
    <ul class="nav nav-tabs" id="myTab" style="padding:0"> 
      <li class="activate" title="显示全部可以连接的主机"><a class="text-warning" href="#home" data-toggle="tab" onclick="show(this)">显示全部的</a></li> 
      <li title="显示可以配置为数据节点的主机"><a class="text-success" href="#profile" data-toggle="tab" onclick="showConfigHost(this)">查看可配的<i style="color:red">*</i></a></li> 
      <li title="显示已经配置过,如果确定一下节点不在其他集群中可以添加为数据节点"><a class="text-info" href="#messages" data-toggle="tab" onclick="existsHost(this)">查看存在的</a></li> 
      <li title="显示网络不通或ip错误的地址"><a class="text-error" href="#settings" data-toggle="tab" onclick="notExistsHost(this)">查看不存在的</a></li> 
      <li title="部署已选服务器主机作为数据节点" ">
         <ul style="list-style:none;"><li>
           <button type="button" id="test" role="button" class="btn btn-success" onclick="deploy()">部署</button>
         </li></ul>
      </li>
     </ul> 
     <div style="height:50px;"> 
      <!-- head --> 
      <ul id="tablehead" class="breadcrumb"> 
       <li id="ipchbox" style="width:20px;"><input type="checkbox" class="checkbox" id="allnodeips" name="allnodeips" onclick="allck()"/></li> 
       <li id="ipaddr" style="width:120px;">主机ip</li> 
       <li id="hostname" style="width:110px;">主机名</li> 
       <li id="hostexists" style="width:100px;">主机是否存在</li> 
       <li id="rootpwd" style="width:80px;">ROOT密码</li> 
       <li id="isHadoop" style="width:150px;">hadoop用户是否存在</li> 
       <li id="ish" style="width:170px;">是否使用hadoop用户登录</li> 
       <li id="hadoophome" style="width:100px;">hadoop程序</li> 
       <li id="javahome" style="width:80px;">java环境</li> 
      </ul> 

     </div>
     <div id="hostInfo"  style="overflow-y: scroll; width: 100%; height: 260px;">

     </div>
</div>
</div>
</div>
     
     
     <div style="display: none"> 
      <input type="hidden" id="sshPort" name="sshPort" value="${master.sshPort}" /> 
      <input type="hidden" id="encode" name="encode" value="${master.encode}" /> 
<%--       <input type="hidden" id="hadoopSetupPath" name="hadoopSetupPath" value="${master.hadoopSetupPath}" />  --%>
     </div> 
    </div> 
    <div> 
     
     </div> 
    </div>  
   </fieldset> 
  </form> 
  
<!-- Modal -->
<div class="modal fade" id="myModal" aria-labelledby="myModalLabel" aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
         <h3 class="modal-title" id="modalTitle">提示</h3>
      </div>
       <div  id="modalBody"> 
        <h4 id="nodeModalLabel">请至少选择一个节点进行部署 </h4> 
       </div> 
      <div class="modal-footer">
        <button type="button" class="btn" data-dismiss="modal">关闭</button>
      </div>
    </div><!-- /.modal-content -->
  </div><!-- /.modal-dialog -->
</div><!-- /.modal -->



<div class="modal fade" id="deployModal2" aria-labelledby="myModalLabel" aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
         <h3 class="modal-title" id="modalTitle">确认提示</h3>
           
      </div>
       <div  id="deployBody"> 
        <h4>您要将以下IP所在的主机部署成数据节点吗？</h4>
        <h4 id="deployH4">
       
		</h4>
       </div> 
      <div class="modal-footer">
        <button type="button" class="btn" data-dismiss="modal">关闭</button>
        <button type="button" class="btn btn-primary" data-dismiss="modal" onclick="deploy2()">确定</button>
      </div>
    </div><!-- /.modal-content -->
  </div><!-- /.modal-dialog -->
</div><!-- /.modal -->

<!-- Modal -->
<div class="modal fade" id="rootNotNull" aria-labelledby="myModalLabel" aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
         <h3 class="modal-title" id="modalTitle">提示</h3>
           
      </div>
       <div  id="modalBody"> 
        <h4 id="testModalLabel">ROOT密码不能为空</h4>
       </div> 
      <div class="modal-footer">
        <button type="button" class="btn" data-dismiss="modal">关闭</button>
      </div>
    </div><!-- /.modal-content -->
  </div><!-- /.modal-dialog -->
</div><!-- /.modal -->  

  <script src="/static/extern/mywebdb2.js" type="text/javascript"></script> 
  <script type="text/javascript" src="/static/extern/monitor.js"></script>
  <script type="text/javascript">
  $(document).ready(function() {
		$(led).css({"-webkit-animation":"ledSpan 1s infinite ease-in-out"});
	});
	$(document).ready(function() {
		$("#inputForm").validate({
			errorContainer: "#messageBox",
			errorPlacement: function(error, element) {
					error.insertAfter(element);
			}
		});
	});
	$(function () {
		 $('#myTab a:first').tab('show');
    });
	var hidjob;
	function deploy(){
		
		if($("#sshRootPassword").val().trim()==''){
			$('#rootNotNull').modal('show');
			return;
		}
	
		$("#deployH4").text("");
		var ips = new Array();
		$("input[name=nodeips]:checked").each(function(){
			ips.push($(this).val());	
		});
		infoLength = ips.length;
		infoIndex = 0;
		if(ips.length>0){
			for(var i=0;i<ips.length;i++){
				$("#deployH4").append(ips[i].split(",")[0]+"<br/>");
			}
		    $('#deployModal2').modal("show");
			window.clearTimeout(hidjob);
			$("#listbar").css("width","0%");
			$("#progress").css({display:'block'});
	
		}else{
			 $("#nodeModalLabel").text("请至少选择一个节点进行部署");
			$('#myModal').modal('show');
		}
	}
	
	function deployInfo(){
		job = window.setTimeout("deployInfo()",3000);
		$.ajax({  
		     url: "${base}/hadoop/node/add/getnodeinfo?t=" + Math.random(),
		     cache: false,
		     type: "post",
		     dataType:"json",
		     success: function (msg) {
		    	 console.log("msg",msg.length); 
		    	 if(infoLength == msg.length){
		    		 $("#listbar").css("width","100%");
		    		 console.log("set time stop");
		    		 window.clearTimeout(job);
		    		 $("#led").css({display:"none"});
		    		 $("#myModal").modal("show");
		    		 $("#nodeModalLabel").append("<lable>部署完成</label><br/>");
		    		 $("button[id=test]").attr({disabled:false});
		    		 hidjob = window.setTimeout("progresshidden()",5000);
		    	 }//end if
		    	 for(;infoIndex<msg.length;infoIndex++){
		    		console.log(infoLength); 
		    		$("#listbar").css("width",100/infoLength*(infoIndex+1)+"%");
//		    		$("#nodeModalLabel").append("<lable>"+msg.ip+"部署成功</label><br/>");
		    	 }//end if
		     },
		     error: function (msg) {
		     }
		   });
	}

	function deploy2(){
		$("#nodeModalLabel").text("");
		job = window.setTimeout("deployInfo()",3000);
		var ips = new Array();
		$("input[name=nodeips]:checked").each(function(){
				ips.push($(this).val());
		});
		$("button[id=test]").attr("disabled",true);
	 $.ajax({  
	     url: "${base}/hadoop/node/add/deploy?t=" + Math.random(),
	     data:{
	     nodeips:ips.join("|"),
	   	 changeProfile:$("#changeProfile").is(":checked"),
		 addHosts:$("#addHosts").is(":checked"),
		 balancer:$("#balancer").is(":checked"),
		 firewell:$("#firewell").is(":checked"),
		 taskTracker:$("#taskTracker").is(":checked"),
		 syncAllNodeConfig:$("#syncAllNodeConfig").is(":checked"),
		 addSlaves:$("#addSlaves").is(":checked"),
		 removeLogs:$("#removeLogs").is(":checked"),
		 autoStartupDataNode:$("#autoStartupDataNode").is(":checked"),
	     },
	     cache: false,
	     type: "post",
	     dataType:"json",
	     success: function (msg) {
	    	
	     },
	     error: function (msg) {
	     }
	   });
	}
	function getNodeInfo(){
		$('#home a:first').tab('show');	
		console.log("getNodeInfo");
	    job = window.setTimeout("getNodeInfo()",3000);
		$('#collapseTwo').collapse('show');
		$.ajax({  
		     url: "${base}/hadoop/node/add/getnodeinfo?t=" + Math.random(),
		     cache: false,
		     type: "post",
		     dataType:"json",
		     success: function (msg) {
		    	 console.log("msg",msg.length); 
		    	 if(infoLength == msg.length){
		    		 $("#listbar").css("width","100%");
		    		 console.log("set time stop");
		    		 window.clearTimeout(job);
		    		 $("#led").css({display:"none"});
		    		 $("#testModalLabel").text("请选择要部署的节点进行部署");
		    		 $("#myModal").modal("show");
		    		 $("button[id=test]").attr("disabled",false);
		    		 hidjob = window.setTimeout("progresshidden()",5000);
		    	 }//end if
		    	 for(;infoIndex<msg.length;infoIndex++){
		    		console.log(infoLength);
		    		$("#listbar").css("width",100/infoLength*(infoIndex+1)+"%");
		    	 	try{
				  		mywebdb.put({
					  		name:"nodeList",
					  		value:msg[infoIndex]
		    	 		});
				  	showInfo(msg[infoIndex]);
		    	 	}catch(ex){
		    	 	}
		    	 }//end if
		     },
		     error: function (msg) {
		     }
		   });
	}
	//disabled:true
	function submitInfo(){
		if($("#sshRootPassword").val().trim()==''){
			$('#rootNotNull').modal('show');
			return;
		}
		window.clearTimeout(hidjob);
		$("#hostInfo").text("");
		$("button[id=test]").attr("disabled",true);
		$("#listbar").css("width","0%");
		$("#progress").css({display:'block'});
		$('#home a:first').tab('show');
		$("#myModalload").modal('show');
		$("span[id=led]").css({display:'inline'});
	 	$('#collapseOne').collapse('hide');
	 	
	 	delDB();
	 	init();
	 	infoLength = ipNumber($.trim($("#ip").val()));
	 	infoIndex=0;

		$.ajax({  
		     url: "${base}/hadoop/node/add/testroot?t=" + Math.random(),
		     data: {
			   'ip':  $.trim($("#ip").val()),
			   'sshRootPassword': $.trim($("#sshRootPassword").val()),
			   'sshPassword': $.trim($("#sshPassword").val()),
			   'sshUsername': $.trim($("#sshUsername").val()),
			   'sshPort': $.trim($("#sshPort").val()),
			   'encode': $.trim($("#encode").val()),
			 },
		     cache: false,
		     type: "post",
		     dataType:"json",
		     success: function (msg) {
		     },
		     error: function (msg) {
		     }
		   });
	       getNodeInfo();
	}
	
</script>  
 </body>
</html>
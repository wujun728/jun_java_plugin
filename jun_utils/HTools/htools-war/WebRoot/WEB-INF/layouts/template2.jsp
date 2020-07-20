<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/static/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta name="viewport" content="width=device-width, initial-scale=1.0"/>
	<link rel="shortcut icon" type="image/x-icon"  href="${base}/static/images/favicon.ico"/>
	<title>HTools - <decorator:title></decorator:title></title>
	<link rel="stylesheet" href="${base}/static/bootstrap/css/bootstrap.css" type="text/css" />
	<link rel="stylesheet" href="${base}/static/bootstrap/css/bootstrap-responsive.css" type="text/css" />
	<link rel="stylesheet" href="${base}/static/bootstrap/css/bootstrap-switch.css" type="text/css" />
	<link rel="stylesheet" href="${base}/static/bootstrap/css/flat-ui-fonts.css" type="text/css" />
	<link href="${base}/static/scripts/jquery-validation/1.10.0/validate.css" type="text/css" rel="stylesheet" />
	<script src="${base}/static/bootstrap/js/jquery.min.js"></script>
   	<script src="${base}/static/bootstrap/js/bootstrap.js"></script>
   	<script src="${base}/static/bootstrap/js/bootstrap-switch.js"></script>
   	<script src="${base}/static/scripts/jquery-validation/1.10.0/jquery.validate.min.js" type="text/javascript"></script>
	<script src="${base}/static/scripts/jquery-validation/1.10.0/messages_bs_zh.js" type="text/javascript"></script>
	<script src="${base}/static/extern/scroll.js" type="text/javascript"></script>

	<style type="text/css">
      body {
        padding-top: 60px;
        padding-bottom: 40px;
      }
      .sidebar-nav {
        padding: 9px 0;
      }

      @media (max-width: 980px) {
        /* Enable use of floated navbar text */
        .navbar-text.pull-right {
          float: none;
          padding-left: 5px;
          padding-right: 5px;
        }
      }
      .r2 {
		-moz-transform:rotate(0deg);
		-webkit-transform:rotate(0deg);
		-o-transform:rotate(0deg);
	  }
	  .r1 {
		-moz-transform:rotate(5deg);
		-webkit-transform:rotate(5deg);
		-o-transform:rotate(5deg);
	  }
	  .leftMenu{
	  	background-color: #EFEFEF;
	  	padding-top: 10px;
	  	padding-bottom: 10px;
	  	-moz-border-radius: 5px;
		-webkit-border-radius: 5px;
		border-radius: 5px;
	  }
    </style>
    <!-- HTML5 shim, for IE6-8 support of HTML5 elements -->
    <!--[if lt IE 9]>
      <script src="${base}/bootstrap/js/html5shiv.js"></script>
    <![endif]-->
	<decorator:head></decorator:head>
</head>
<body>
	<div class="navbar navbar-fixed-top">
	  <div class="navbar-inner">
    	<img src="${base}/static/images/logo.png" class="rotate" alt="logo" style="float: left;padding-top: 3px;padding-left: 40px;"/>
		<shiro:user>
			<span class="pull-right" style="padding-right: 30px;padding-top: 5px;">
				<a href="#logoutModal" role="button" class="btn btn-inverse dropdown-toggle" data-toggle="modal" data-placement="bottom" title="退出登录"><i class="icon-off icon-white"></i></a>
			</span>
			<span class="pull-right" style="padding-right: 5px;padding-top: 5px;">
				<a href="#pswModal" role="button" class="btn btn-danger dropdown-toggle" data-toggle="modal" data-placement="bottom" title="修改个人密码"><i class="icon-edit icon-white"></i></a>
			</span>
		    <div class="container">
		    	<div class="btn-toolbar" style="margin: 0;padding-top: 5px;">
		    		<div class="btn-group">
		    			<a href="${base}/index" class="btn dropdown-toggle"><i class="icon-home"></i> 首页</a>
		            </div>
		            <div class="btn-group">
		                <button class="btn dropdown-toggle" data-toggle="dropdown"><i class="icon-wrench"></i> 系统及安全 <span class="caret"></span></button>
		                <ul class="dropdown-menu">
			                <li><a href="${base}/security/user/page/0"><i class="icon-user"></i> 账户管理</a></li>
			                <li><a href="#"><i class="icon-star"></i> 在线用户</a></li>
							<li><a href="${base}/security/learn/index"><i class="icon-book"></i> 知识库维护</a></li>
							<li><a href="#"><i class="icon-align-left"></i> 监控线程</a></li>
			            </ul>
		            </div>
		             <div class="btn-group">
		                <button class="btn dropdown-toggle" data-toggle="dropdown"><i class="icon-th-large"></i> 常用操作 <span class="caret"></span></button>
		                <ul class="dropdown-menu">
							<li><a href="${base}/hadoop/service/index"><i class="icon-hand-right"></i> 一键式服务</a></li>
				       		<li><a href="${base}/hadoop/hdfs/index"><i class="icon-folder-open"></i> HDFS管理</a></li>
				        	<li><a href="${base}/hadoop/node/monitor"><i class="icon-eye-open"></i> 节点监控维护</a></li>
				       		<li><a href="${base}/hadoop/report/index"><i class="icon-picture"></i> 图形报表</a></li>
			            </ul>
		            </div>
		            <div class="btn-group">
		                <button class="btn dropdown-toggle" data-toggle="dropdown"><i class="icon-th"></i> 高级应用 <span class="caret"></span></button>
		                <ul class="dropdown-menu">
							<li><a href="${base}/hadoop/optimization/index"><i class="icon-shopping-cart"></i> 自选式优化</a></li>
							<li><a href="#"><i class="icon-random"></i> 性能评测</a></li>
							<li><a href="#"><i class="icon-list-alt"></i> 配置模板</a></li>
							<li><a href="#"><i class="icon-briefcase"></i> 远程控制台</a></li>
							<li><a href="#"><i class="icon-download-alt"></i> 数据迁移</a></li>
							<li><a href="#"><i class="icon-edit"></i> 自定义MR任务</a></li>
			            </ul>
		            </div>
		            <div class="btn-group">
		                <button class="btn dropdown-toggle" data-toggle="dropdown"><i class="icon-map-marker"></i> 周边生态 <span class="caret"></span></button>
		                <ul class="dropdown-menu">
							<li><a href="#"><i class="icon-hdd"></i> HBase</a></li>
							<li><a href="#"><i class="icon-leaf"></i> Hive</a></li>
							<li><a href="#"><i class="icon-bullhorn"></i> Pig</a></li>
							<li><a href="#"><i class="icon-comment"></i> Zookeeper</a></li>
							<li><a href="#"><i class="icon-magnet"></i> Sqoop</a></li>
			            </ul>
		            </div>
			    </div>
		    </div>
	    </shiro:user>
	  </div>
	</div>
    
    <div class="container-fluid">
	  <div class="row-fluid">
	  	<shiro:user>
		    <div class="span2">
		    	<div class="leftMenu">
			    	<ul class="nav nav-list">
			          <li class="nav-header">日常维护</li>
				          <li><a href="${base}/hadoop/service/index"><i class="icon-hand-right"></i> 一键式服务</a></li>
				          <li><a href="${base}/hadoop/hdfs/index"><i class="icon-folder-open"></i> HDFS管理</a></li>
				          <li><a href="${base}/hadoop/node/monitor"><i class="icon-eye-open"></i> 节点监控维护</a></li>
				          <li><a href="${base}/hadoop/report/index"><i class="icon-picture"></i> 图形报表</a></li>
			          <li class="divider"></li>
			          	<li class="nav-header">高级应用</li>
				          <li><a href="${base}/hadoop/optimization/index"><i class="icon-shopping-cart"></i> 自选式优化</a></li>
				          <li><a href="#"><i class="icon-random"></i> 性能评测</a></li>
				          <li><a href="#"><i class="icon-list-alt"></i> 配置模板</a></li>
				          <li><a href="#"><i class="icon-briefcase"></i> 远程控制台</a></li>
				          <li><a href="#"><i class="icon-download-alt"></i> 数据迁移</a></li>
				          <li><a href="#"><i class="icon-edit"></i> 自定义MR任务</a></li>
			          <li class="divider"></li>
			          	<li class="nav-header">周边生态</li>
				          <li><a href="#"><i class="icon-hdd"></i> HBase</a></li>
				          <li><a href="#"><i class="icon-leaf"></i> Hive</a></li>
				          <li><a href="#"><i class="icon-bullhorn"></i> Pig</a></li>
				          <li><a href="#"><i class="icon-comment"></i> Zookeeper</a></li>
				          <li><a href="#"><i class="icon-magnet"></i> Sqoop</a></li>
			          <li class="divider"></li>
			          	<li><a href="#learnModal" data-toggle="modal" onclick="return getLearnValue();" ><i class="icon-book"></i> 学习知识</a></li>
			        </ul>
			    </div>
		    </div>
	    </shiro:user>
	    <div class="span10">
	    <c:if test="${not empty success_message}">
			<div id="message" class="alert alert-success"><button data-dismiss="alert" class="close">×</button>${success_message}</div>
		</c:if>
		<c:if test="${not empty error_message}">
			<div id="message" class="alert alert-error"><button data-dismiss="alert" class="close">×</button>${error_message}</div>
		</c:if>
	      <decorator:body/>
	    </div>
	  </div>
	</div>
	
	<div id="pswModal" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="pswModalLabel" aria-hidden="true">
		  <div class="modal-header">
		    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
		    <h3 id="pswModalLabel">修改个人密码</h3>
		  </div>
		  	<div class="modal-body">
		  			<div id="updatePasswordResult"></div>
			  		<div class="control-group">
				      <label class="control-label" for="oldPassword">旧的密码</label>
				      <div class="controls">
				        <input type="password" id="oldPassword" name="oldPassword">
				      </div>
				    </div>
				    <fieldset>
					    <legend></legend>
					    <div class="control-group">
					      <label class="control-label" for="newPassword">新的密码</label>
					      <div class="controls">
					        <input type="password" id="newPassword" name="newPassword">
					      </div>
					    </div>
					    <div class="control-group">
					      <label class="control-label" for="newPassword2">确认新密码</label>
					      <div class="controls">
					        <input type="password" id="newPassword2">
					      </div>
					    </div>
			    	</fieldset>
			</div>
			  	<div class="modal-footer">
				    <a class="btn" href="#" data-dismiss="modal" aria-hidden="true">关闭</a>
				    <input type="button" value="确认提交" class="btn btn-primary" data-loading-text="提交中..." onclick="return updatePassword();"/>
				</div>
			<script>
				function initUpdatePasswrd(){
					$("#updatePasswordResult").text('');
					$("#updatePasswordResult").removeClass('alert alert-error');
					$("#updatePasswordResult").removeClass('alert alert-success');
					$('#oldPassword').val('');
	            	$('#newPassword').val('');
	            	$('#newPassword2').val('');
				}
				function updatePassword() {
					$("#updatePasswordResult").text('');
					$("#updatePasswordResult").removeClass('alert alert-error');
					$("#updatePasswordResult").removeClass('alert alert-success');
					var oldPassword = $.trim($('#oldPassword').val());
					var newPassword = $.trim($('#newPassword').val());
					var newPassword2 = $.trim($('#newPassword2').val());
					if(oldPassword==''){
						$("#updatePasswordResult").addClass('alert alert-error');
						$("#updatePasswordResult").text('旧的密码不能为空！');
						$('#oldPassword').focus();
						return false;
					}
					if(oldPassword.length<5||oldPassword.length>15){
						$("#updatePasswordResult").addClass('alert alert-error');
						$("#updatePasswordResult").text('旧的密码的长度应该在5~15位之间！');
						$('#oldPassword').focus();
						return false;
					}
					if(newPassword==''){
						$("#updatePasswordResult").addClass('alert alert-error');
						$("#updatePasswordResult").text('新的密码不能为空！');
						$('#newPassword').focus();
						return false;
					}
					if(newPassword.length<5||newPassword.length>15){
						$("#updatePasswordResult").addClass('alert alert-error');
						$("#updatePasswordResult").text('新的密码的长度应该在5~15位之间！');
						$('#newPassword').focus();
						return false;
					}
					if(newPassword==oldPassword){
						$("#updatePasswordResult").addClass('alert alert-error');
						$("#updatePasswordResult").text('新的密码与旧密码完全相同！');
						$('#newPassword').focus();
						return false;
					}
					if(newPassword!=newPassword2){
						$("#updatePasswordResult").addClass('alert alert-error');
						$("#updatePasswordResult").text('确认密码与新的密码不同！');
						$('#newPassword2').focus();
						return false;
					}
			        $.ajax({  
			            url: "${base}/security/user/update/password?t=" + Math.random(),
			            data: {
		            		'newPassword': newPassword,
		            		'oldPassword': oldPassword
				    	   },
			            cache: false,
			            type: "post",
			            timeout: 10000,  
			            success: function (msg) {
			            	$('#oldPassword').val('');
			            	$('#newPassword').val('');
			            	$('#newPassword2').val('');
			            	if(msg=='-1'){
			            		$("#updatePasswordResult").addClass('alert alert-error');
								$("#updatePasswordResult").text('当前用户为系统固化，无法修改密码！');
			            	}else if(msg=='0'){
			            		$("#updatePasswordResult").addClass('alert alert-error');
								$("#updatePasswordResult").text('密码修改失败，您输入的旧密码不正确！');
			            	}else{
			            		$("#updatePasswordResult").addClass('alert alert-success');
								$("#updatePasswordResult").text('密码修改成功！');
			            	}
			            },
			            error: function (msg) {
			            	$("#updatePasswordResult").addClass('alert alert-error');
			            	$("#updatePasswordResult").text('无法连接远程服务器，请暂停操作!');
			            }
			        });
			    }
			</script>
	</div>	
	
	<div id="logoutModal" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="logoutModalLabel" aria-hidden="true">
		  <div class="modal-header">
		    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
		    <h3 id="logoutModalLabel">确认提示</h3>
		  </div>
		  	<div class="modal-body">
			  		<div class="control-group">
				     	<h3>您是否确认退出HTools？</h3>
				    </div>
			</div>
			<div class="modal-footer">
			 <form id="logoutForm" name="logout" action="${base}/logout" method="get">
				    <a class="btn" href="#" data-dismiss="modal" aria-hidden="true">关闭</a>
				    <input type="submit" value="确认退出" class="btn btn-primary" data-loading-text="提交中..."/>
			</form>
			</div>
	</div>
	
	<div id="learnModal" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="learnModalLabel" aria-hidden="true">
		  <div class="modal-header">
		    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
		    <h3 id="logoutModalLabel">学习知识</h3>
		  </div>
		  	<div class="modal-body" style="background-image: url('${base}/static/images/learn.jpg');">
		  		<div class="control-group">
			     	<div class="carousel slide" style="height: 380px;width: 540px;">
			     			<div id="learnValue" class="carousel-inner">
							</div>
			     	</div>
			    </div>
			</div>
			<div class="modal-footer">
				 <a href="#learnValue" class="btn" data-slide="prev">上一页</a>
				 <a href="#learnValue" class="btn" data-slide="next">下一页</a>
			</div>
	</div>
	<script>
		function getLearnValue() {
			$("#learnValue").text('正在读取知识库...');
	        $.ajax({  
	            url: "${base}/security/learn/preview?t=" + Math.random(),
	            cache: false,
	            type: "post",
	            timeout: 25000,  
	            success: function (datas) {
	            	var str = "";
	            	if(datas==''||datas==null){
	            		str+="<div class='active'><h3>目前还没有内容</h3><a href='${base}/security/learn/index' class='btn btn-primary'>去添加一些吧!</a></div>";
	            	}else{
	            		for(var data in datas){
		            		var active = '';
		            		if(data==1){
		            			active = 'active';
		            		}
		            		str+="<div class='"+active+" item'><h4><p>[第"+data+"页]</p>"+datas[data]+"</h4></div>";
		            	}
		            	str+="<div class='item'><h3>本次学习结束</h3><a href='#' class='btn btn-primary' onClick='return getLearnValue();'>随便换一下</a></div>";
	            	}
	            	if(datas!=null){
	            		$("#learnValue").text('');
	            		$("#learnValue").append(str);
	            	}
	            },
	            error: function (msg) {
	            	$("#learnValue").text('无法连接远程服务器，请暂停操作!');
	            }
	        });
	    }
		//写cookies
		function setCookie(name,value){
			var exp = new Date(); 
			exp.setTime(exp.getTime() + 60*60*1000);
			document.cookie = name + "="+ escape (value) + ";expires=" + exp.toGMTString();
		}
		//读取cookies
		function getCookie(name){
			var arr,reg=new RegExp("(^| )"+name+"=([^;]*)(;|$)");
			if(arr=document.cookie.match(reg)){
				return unescape(arr[2]);
			}else{
				return null;
			}
		}
		$(function(){
			 function state1(){
				 $(".rotate").removeClass("r2"); 
				 $(".rotate").addClass("r1");
				 setTimeout(state2,110);
			 }
			function state2(){	   
			     $(".rotate").removeClass("r1");
			     $(".rotate").addClass("r2"); 
				 setTimeout(state1,Math.floor(Math.random()*10+1)*1000);
			}
			state1();
		});
	</script>
</body>
</html>

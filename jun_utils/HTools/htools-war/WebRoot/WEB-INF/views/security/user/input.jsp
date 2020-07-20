<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/static/taglibs.jsp"%>
<html>
<head>
<title>账户录入与维护</title>
</head>
<body>
<ul class="breadcrumb">
  <li><a href="${base}/index">首页</a> <span class="divider">/</span></li>
  <li><a href="${base}/security/user/page/0">登录账户维护列表</a> <span class="divider">/</span></li>
  <li class="active">账户录入与维护</li>
</ul>
		<form id="inputForm" name="user" action="${base}/security/user/<c:if test="${user.id!=null}">update</c:if><c:if test="${user.id==null}">save</c:if>" method="post" class="well">
			<fieldset>
	    		<legend>登录账户信息</legend>
	    		<c:if test="${user.id!=null}">
		    		<input type="hidden" name="id" value="${user.id}">
		    		<input type="hidden" name="status" value="${user.status}">
	    		</c:if>
	    		<c:if test="${user.id==null}">
		    		<input type="hidden" name="status" value="enabled">
	    		</c:if>
	    		<div id="messageBox" class="alert alert-error input-large controls" style="display:none">输入有误，请先更正。</div>
	    		<div class="control-group">
			      <label class="control-label" for="loginName">登录名</label>
			      <div class="controls">
			        <input type="text" id="loginName" <c:if test="${user.id!=null}">readonly="readonly"</c:if> name="loginName" value="${user.loginName}" class="input-large required" minlength='5' maxlength='15'>
			      </div>
			    </div>
			    <div class="control-group">
			      <label class="control-label" for="password">密码</label>
			      <div class="controls">
			        <input type="password" id="password" name="password" class="input-large required" minlength='5' maxlength='15'>
			      </div>
			    </div>
			    <div class="control-group">
			      <label class="control-label" for="name">姓名</label>
			      <div class="controls">
			        <input type="text" id="name" name="name" value="${user.name}" class="input-large required" minlength='2' maxlength='10'>
			      </div>
			    </div>
			    <div class="control-group">
			      <label class="control-label" for="email">电子邮箱</label>
			      <div class="controls">
			        <input type="text" id="email" name="email" value="${user.email}" class="input-large email" minlength='8' maxlength='30'>
			      </div>
			    </div>
			    <div class="control-group">
			      <label class="control-label" for="email">手机号码</label>
			      <div class="controls">
			        <input type="text" id="mobile" name="mobile" value="${user.mobile}" class="input-large number" minlength='11' maxlength='11'>
			      </div>
			    </div>
			    <div class="control-group">
			      <label class="control-label">权限选定</label>
			      <div class="controls">
			      		<c:forEach items="${roles}" var="role">
							<c:if test="${user==null || userRoles==null || userRoles==''}">
								<input type="checkbox" name="roles" value="${role.key}">${role.value}&nbsp;
							</c:if>
							<c:set var="sign" value="0"></c:set>
							<c:if test="${userRoles!=null && userRoles!=''}">
								<c:forEach items="${userRoles}" var="checkRole">
									<c:if test="${role.key==checkRole}">
										<input type="checkbox" name="roles" value="${role.key}" checked="checked">${role.value}&nbsp;
										<c:set var="sign" value="1"></c:set>
									</c:if>
						 		</c:forEach>
						 		<c:if test="${sign==0}">
						 			<input type="checkbox" name="roles" value="${role.key}">${role.value}&nbsp;
						 		</c:if>
							</c:if>
						</c:forEach>
			      </div>
			    </div>
				<p>
					<input type="submit" value="确认提交" class="btn btn-primary btn-large" data-loading-text="提交中..."/>&nbsp;&nbsp;
					<a class="btn btn-warning btn-large" href="${base}/security/user/page/0">返回列表</a>
				</p>
			</fieldset>
		</form>
		
<script>
	$(document).ready(function() {
		$("#inputForm").validate({
			errorContainer: "#messageBox",
			errorPlacement: function(error, element) {
				if ( element.is(":checkbox") )
					error.appendTo(element.parent().next());
				else
					error.insertAfter(element);
			}
		});
	});
</script>

</body>
</html>
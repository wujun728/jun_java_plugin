<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<div class="navbar navbar-inverse navbar-fixed-top" role="navigation">
	<div class="container">
		<div class="navbar-header">
			<button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
				<span class="sr-only"></span> <span class="icon-bar"></span> <span class="icon-bar"></span> <span class="icon-bar"></span>
			</button>
			<a class="navbar-brand" href="#">系统</a>
		</div>
		<div class="navbar-collapse collapse">
			<ul class="nav navbar-nav">
				<li class="active"><a href="${ctx }/index">首页</a></li>

				<li class="dropdown"><a href="#" class="dropdown-toggle" data-toggle="dropdown">IM <b class="caret"></b></a>
					<ul class="dropdown-menu">
						<li id="${c.id }"><a href="${ctx }/index">IM聊天</a></li>
						<li id="${c.id }"><a href="${ctx }/message">查看聊天记录</a></li>
					</ul></li>
			</ul>

			<ul class="nav navbar-nav navbar-right">
				<li class="dropdown"><a href="#" class="dropdown-toggle" data-toggle="dropdown">个人中心 <b class="caret"></b></a>
					<ul class="dropdown-menu">
						<li><a href="${ctx }/user/updatepasswordPage">修改密码</a></li>
						<li class="divider"></li>
						<li><a href="${ctx }/logout">退出</a></li>
					</ul></li>
			</ul>
		</div>
		<!--/.nav-collapse -->
	</div>
</div>


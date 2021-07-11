<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
<title>请假管理界面</title>
<link rel="stylesheet" href="//cdn.bootcss.com/bootstrap/3.3.5/css/bootstrap.min.css" />
</head>
<body>
 <ol class="breadcrumb" id="nav_activities">
         <li class="active">申请</li>
		  <li><a href="javascript:void(0)">手机号验证</a></li>
		  <li><a href="javascript:void(0)">身份证验证</a></li>
		</ol>
	<table class="table table-striped table-bordered table-hover">
		<thead>
			<tr>
				<th>ID</th>
				<th>Name</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="item" items="${taskList}">
				<tr>
					<td>${item.id}</td>
					<td>${item.name}</td>
					<td><a href="<%=request.getContextPath()%>/${item.formResourceName}?id=${item.id}">处理</a></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>



<script src="//cdn.bootcss.com/jquery/2.1.4/jquery.min.js"></script>
<script src="//cdn.bootcss.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
</body>
</html>
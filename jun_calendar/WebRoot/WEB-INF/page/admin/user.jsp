<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<form id="pagerForm" method="post" action="/user/getPageList">
	<input type="hidden" name="pageNum" value="1" />
	<input type="hidden" name="numPerPage" value="${page.pageSize}" />
</form>

<div class="pageContent">
	<div class="panelBar">
		<ul class="toolBar">
			<li><a class="add" href="/user/goAdd" target="dialog" mask=true minable=false ref="dlg_user_add" title="新增" width="550" height="300"><span>新增</span></a></li>
			<li><a class="delete" href="user/del/{user_id}" target="ajaxTodo" title="确定要删除吗?"><span>删除</span></a></li>
		</ul>
	</div>
	<table class="table" width="100%" layoutH="75">
		<thead>
			<tr>
				<th style="display: none;"></th>
				<th width="100">真实姓名</th>
				<th width="150">登录名</th>
				<th width="80">TEL</th>
				<th width="80">e-mail</th>
				<th width="80">创建日期</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.list}" var="item">
				<tr target="user_id" rel="${item.id}">
					<td style="display: none;">${item.id}</td>
					<td>${item.name}</td>
					<td>${item.loginName}</td>
					<td>${item.tel}</td>
					<td>${item.email}</td>
					<td>${item.create_date}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<jsp:include page="paginationBar.jsp"></jsp:include>
</div>

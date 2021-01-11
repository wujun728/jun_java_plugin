<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<form id="pagerForm" method="post" action="/menu/getPageList">
	<input type="hidden" name="pageNum" value="1" />
	<input type="hidden" name="numPerPage" value="${page.pageSize}" />
</form>


<div class="pageContent">
	<div class="panelBar">
		<ul class="toolBar">
			<li><a class="add" href="menu/goAdd" target="dialog" mask=true minable=false ref="dlg_menu_add" title="新增" width="550" height="300"><span>新增</span></a></li>
			<li><a class="delete" href="menu/del/{menu_id}" target="ajaxTodo" title="确定要删除吗?"><span>删除</span></a></li>
		</ul>
	</div>
	<table class="table" width="100%" layoutH="75">
		<thead>
			<tr>
				<th width="150">菜品名称</th>
				<th width="80">价格</th>
				<th width="80">餐别</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.list}" var="item">
				<tr target="menu_id" rel="${item.id}">
					<td>${item.name}</td>
					<td>${item.price}</td>
					<td>
						<c:choose>
							<c:when test="${item.state==1 }">午餐</c:when>
							<c:when test="${item.state==2 }">晚餐</c:when>
							<c:when test="${item.state==3 }">午餐，晚餐</c:when>
							<c:otherwise>ERROR</c:otherwise>
						</c:choose>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<jsp:include page="paginationBar.jsp"></jsp:include>
</div>

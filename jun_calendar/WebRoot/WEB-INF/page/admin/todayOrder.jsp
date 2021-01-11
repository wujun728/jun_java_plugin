<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<h1>今日点餐</h1>
<div>
	<table class="table" width="100%">
		<thead>
			<tr>
				<th width="80">菜品名称</th>
				<th width="80">价格</th>
				<th width="80">餐别</th>
				<th width="80">姓名</th>
				<th width="80">TEL</th>
				<th width="80">e-mail</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${orderList}" var="item">
				<tr <c:if test="${item.state==2 }">bgcolor="#CCCC66"</c:if>>
					<td>${item.menu_name}</td>
					<td>${item.price}</td>
					<td>
						<c:choose>
							<c:when test="${item.state==1 }">午餐</c:when>
							<c:when test="${item.state==2 }">晚餐</c:when>
							<c:otherwise>ERROR</c:otherwise>
						</c:choose>
					</td>
					<td>${item.user_name}</td>
					<td>${item.tel}</td>
					<td>${item.email}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</div>
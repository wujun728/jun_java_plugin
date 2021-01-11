<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<h1>今日会议</h1>
<div>
	<table class="table" width="100%">
		<thead>
			<tr>
				<th width="80">主题</th>
				<th width="80">开始时间</th>
				<th width="80">结束时间</th>
				<th width="80">会议室</th>
				<th width="80">发起人</th>
				<th width="80">e-mail</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${meetingList}" var="item">
				<tr>
					<td>${item.subject}</td>
					<td>${item.start}</td>
					<td>${item.end}</td>
					<td>${item.roomname}</td>
					<td>${item.username}</td>
					<td>${item.email}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</div>
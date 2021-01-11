<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="panelBar">
	<div class="pages">
		<span>显示</span>
		<select class="combox" name="numPerPage" onchange="navTabPageBreak({numPerPage:this.value})" >
			<option value="10" <c:if test="${page.pageSize == 10}">selected="selected"</c:if>>10</option>
			<option value="50" <c:if test="${page.pageSize == 50}">selected="selected"</c:if>>50</option>
			<option value="100" <c:if test="${page.pageSize == 100}">selected="selected"</c:if>>100</option>
		</select>
		<span>条，共${page.totalRow}条</span>
	</div>
	
	<div class="pagination" targetType="navTab" totalCount="${page.totalRow}" numPerPage="${page.pageSize}" pageNumShown="10" currentPage="${page.pageNumber}"></div>
</div>
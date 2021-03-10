<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<c:set var="ctx" value="${pageContext.request.contextPath}" />
<div class="ibox-content">
	<div class="table-responsive ">
		<table class="table table-centerbody table-striped table-condensed text-nowrap" id="editable-sample">
			<thead>
				<tr>
					<th>标题</th>
					<th>内容</th>
					<th>地址</th>
					<th>发生时间</th>
					<th>创建时间</th>
					<th class="text-right">操作</th>
				</tr>
			</thead>
			<tbody>
				<c:if test="${page!=null && (page.list)!= null && fn:length(page.list) > 0 }">
					<c:forEach var="n" items="${page.list }">
						<tr>
							<td>${n.title }</td>
							<td>${n.description }</td>
							<td>${n.address }</td>
							<td><fmt:formatDate value="${n.newsTime}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
							<td><fmt:formatDate value="${n.createTime}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
							<td class="text-right text-nowrap">
								<div class="btn-group ">
									<button class="btn btn-white btn-sm edit" data-id="${n.id }" data-toggle="modal" data-target="#edit">
										<i class="fa fa-pencil"></i> 编辑
									</button>
								</div>
							</td>
						</tr>
					</c:forEach>
				</c:if>
			</tbody>
		</table>
	</div>
	
	<!-- 分页表单 -->
	<form action="${ctx }/news/list_page" id="newsPageForm">
		<!-- 查询条件，用隐藏表单域 -->
		<input type="hidden" value="${keywords }" name="keywords" />

		<!-- 分页控键 -->
		<!-- formId: 分页控件表单ID -->
		<!-- showPageId: ajax异步分页获取的数据需要加载到指定的位置 -->
		<jsp:include page="/WEB-INF/views/common/page.jsp" flush="true">
			<jsp:param name="formId" value="newsPageForm" />
			<jsp:param name="showPageId" value="ibox" />
		</jsp:include>
	</form>

</div>
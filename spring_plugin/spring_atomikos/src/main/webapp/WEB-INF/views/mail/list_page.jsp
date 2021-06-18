<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<div class="ibox-content">
	<div class="table-responsive ">
		<table class="table table-centerbody table-condensed text-nowrap table-hover table-bordered" id="editable-sample">
			<thead>
				<tr>
					<th>邮件类型</th>
					<th>收件人</th>
					<th>抄送</th>
					<th>秘送</th>
					<th>状态</th>
					<th>创建时间</th>
					<th class="text-right">操作</th>
				</tr>
			</thead>
			<tbody>
				<c:if test="${!empty page && !empty page.list && fn:length(page.list) > 0 }">
					<c:forEach var="ma" items="${page.list }">
						<tr>
							<td>${ma.mailType}</td>
							<td>${ma.toAddress }</td>
							<td>${ma.toCc }</td>
							<td>${ma.toBcc }</td>
							<td>${ma.delString }</td>
							<td><fmt:formatDate value="${ma.createTime}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
							<td class="text-right text-nowrap">
								<div class="btn-group ">
									<button class="btn btn-white btn-sm edit" data-id="${ma.id }" data-toggle="modal" data-target="#edit">
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
	<form action="${ctx }/mail/list_page" id="mailAddressPageForm">
		<!-- 查询条件，用隐藏表单域 -->
		<input type="hidden" value="${mailType }" name="mailType" />

		<!-- 分页控键 -->
		<!-- formId: 分页控件表单ID -->
		<!-- showPageId: ajax异步分页获取的数据需要加载到指定的位置 -->
		<jsp:include page="/WEB-INF/views/common/page.jsp" flush="true">
			<jsp:param name="formId" value="mailAddressPageForm" />
			<jsp:param name="showPageId" value="ibox" />
		</jsp:include>
	</form>

</div>
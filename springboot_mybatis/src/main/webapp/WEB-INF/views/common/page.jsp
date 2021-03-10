<%@ page contentType="text/html; charset=UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div class="pages border-top">
	<div class="row">
		<div class="col-md-4">
			<div class="m-t-md">当前显示 ${page.startRow } 到 ${page.endRow } 条，共 ${page.pages } 页 ${page.total } 条</div>
		</div>
		<div class="col-md-8 footable-visible">
			<ul class="pagination pull-right">
				<li class="footable-page-arrow disabled"><a data-page="1" href="javascript:void(0);" onclick="goPage(this,'${param.formId }','${param.showPageId }');">«</a></li>
				<li class="footable-page-arrow disabled"><a data-page="${page.prePage }" href="javascript:void(0);" onclick="goPage(this,'${param.formId }','${param.showPageId }');">‹</a></li>
				<c:forEach var="pgnum" items="${page.navigatepageNums }">
					<c:choose>
						<c:when test="${pgnum eq page.pageNum }">
							<li class="footable-page active"><a data-page="${pgnum }" href="javascript:void(0);" onclick="goPage(this,'${param.formId }','${param.showPageId }');">${pgnum }</a></li>
						</c:when>
						<c:otherwise>
							<li class="footable-page"><a data-page="${pgnum }" href="javascript:void(0);" onclick="goPage(this,'${param.formId }','${param.showPageId }');">${pgnum }</a></li>
						</c:otherwise>
					</c:choose>
				</c:forEach>
				<li class="footable-page-arrow"><a data-page="${page.nextPage }" href="javascript:void(0);" onclick="goPage(this,'${param.formId }','${param.showPageId }');">›</a></li>
				<li class="footable-page-arrow"><a data-page="${page.pages }" href="javascript:void(0);" onclick="goPage(this,'${param.formId }','${param.showPageId }');">»</a></li>
			</ul>
		</div>
		<input type="hidden" name="pageNum" />
	</div>
</div>
<script type="text/javascript">
  function goPage(objA, formId, showPageId) {
    $('#' + formId+" input[name='pageNum']").val($(objA).attr("data-page"));
    $.ajax({
      cache: true,
      type: "POST",
      url: $("#" + formId).attr("action"),
      data: $('#' + formId).serialize(),// 序列化的form
      async: false,
      error: function(data) {
        toastr.error('', '分页查询失败');
      },
      success: function(data) {
        $("#" + showPageId).html(data);
      }
    });
  }
</script>

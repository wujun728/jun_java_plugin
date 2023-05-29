<%@ page language="java" pageEncoding="UTF-8"%>
<html>
<head>
<% 
	String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()  + path; 
%>
<jsp:include page="${request.getContextPath()}/layout/meta.jsp"></jsp:include>
<jsp:include page="${request.getContextPath()}/layout/easyui.jsp"></jsp:include>
<script type="text/javascript" charset="UTF-8">
	$(function() {
		$.ajax({
			url : 'repairController.do?repair',
			dataType : 'json',
			cache : false,
			success : function(r) {
				if (r.success) {
					location.replace('index.jsp');
				}
			},
			type : "POST",
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				alert(XMLHttpRequest.responseText);
			}
		});
	});
</script>
</head>
<body>init database...
</body>
</html>

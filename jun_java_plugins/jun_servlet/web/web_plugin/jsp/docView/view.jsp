<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    <title>My JSP 'view.jsp' starting page</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<jsp:include page="../../layout/script.jsp"></jsp:include>
  </head>
  
  <body>
   	<DIV id=showdiv
			style="Z-INDEX: 0; LEFT: 10px; WIDTH: 990px; POSITION: absolute; TOP: -8px; HEIGHT: 10px">
			<object classid="clsid:CA8A9780-280D-11CF-A24D-444553540000"
				width="990" height="700" border="0" top="-10" name="pdf">
				<param name="toolbar" value="false">
				<param name="_Version" value="65539">
				<param name="_ExtentX" value="20108">
				<param name="_ExtentY" value="10866">
				<param name="_StockProps" value="0">
				<!-- 下面是指明你的PDF文档所在地，相对于发布web目录 -->
				<param name="SRC" value="jsp/docView/a.pdf">
				</object>
				</DIV>
				
  </body>
</html>

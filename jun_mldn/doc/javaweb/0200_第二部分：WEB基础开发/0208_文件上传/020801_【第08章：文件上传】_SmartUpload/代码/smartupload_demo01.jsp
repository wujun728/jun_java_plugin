<%@ page contentType="text/html" pageEncoding="GBK"%>
<%@ page import="org.lxh.smart.*"%>
<html>
<head><title>www.mldnjava.cn，MLDN高端Java培训</title></head>
<body>
<%
	SmartUpload smart = new SmartUpload() ;
	smart.initialize(pageContext) ;	// 初始化上传操作
	smart.upload() ;			// 上传准备
	smart.save("upload") ;	// 文件保存
%>
</body>
</html>
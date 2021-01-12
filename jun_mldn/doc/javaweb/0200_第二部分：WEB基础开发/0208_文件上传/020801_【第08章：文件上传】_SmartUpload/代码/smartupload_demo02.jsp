<%@ page contentType="text/html" pageEncoding="GBK"%>
<%@ page import="org.lxh.smart.*"%>
<%@ page import="cn.mldn.lxh.util.*"%>
<html>
<head><title>www.mldnjava.cn，MLDN高端Java培训</title></head>
<body>
<%
	request.setCharacterEncoding("GBK") ;
%>
<%
	SmartUpload smart = new SmartUpload() ;
	smart.initialize(pageContext) ;	// 初始化上传操作
	smart.upload() ;			// 上传准备
	String name = smart.getRequest().getParameter("uname") ;
	IPTimeStamp its = new IPTimeStamp(request.getRemoteAddr()) ;	// 取得客户端的IP地址
	String ext = smart.getFiles().getFile(0).getFileExt() ;	// 扩展名称
	String fileName = its.getIPTimeRand() + "." + ext ;
	smart.getFiles().getFile(0).saveAs(this.getServletContext().getRealPath("/")+"upload"+java.io.File.separator + fileName) ;
%>
<%=smart.getFiles().getFile(0).getFileName().matches("^\\w+.(jpg|gif)$")%>
<h2>姓名：<%=name%></h2>
<img src="../upload/<%=fileName%>">
</body>
</html>
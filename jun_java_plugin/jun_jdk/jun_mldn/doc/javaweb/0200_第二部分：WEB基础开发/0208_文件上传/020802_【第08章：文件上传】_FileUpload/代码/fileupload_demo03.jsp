<%@ page contentType="text/html" pageEncoding="GBK"%>
<%@ page import="java.util.*,java.io.*"%>
<%@ page import="org.apache.commons.fileupload.*"%>
<%@ page import="org.apache.commons.fileupload.disk.*"%>
<%@ page import="org.apache.commons.fileupload.servlet.*"%>
<%@ page import="cn.mldn.lxh.util.*"%>
<html>
<head><title>www.mldnjava.cn£¨MLDN∏ﬂ∂ÀJava≈‡—µ</title></head>
<body>
<%
	FileUploadTools fut = new FileUploadTools(request,3*1024*1024,this.getServletContext().getRealPath(".")+"uploadtemp") ;
	String name = fut.getParameter("uname") ;
	String inst[] = fut.getParameterValues("inst") ;
	List<String> all = fut.saveAll(this.getServletContext().getRealPath("/")+"upload"+java.io.File.separator) ;
%>
<h3>–’√˚£∫<%=name%><h3>
<h3>–À»§£∫
	<%
		for(int x=0;x<inst.length;x++){
%>
			<%=inst[x]%>°¢
<%
		}
	%>
</h3>
<%
	Iterator<String> iter = all.iterator() ;
	while(iter.hasNext()){
%>
		<img src="../upload/<%=iter.next()%>">
<%
	}
%>
</body>
</html>
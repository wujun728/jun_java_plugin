<%@ page contentType="text/html" pageEncoding="GBK"%>
<html>
<head><title>www.mldnjava.cn，MLDN高端Java培训</title></head>
<body>
<%
	request.setCharacterEncoding("GBK") ;// 设置的是统一编码
	String id = request.getParameter("id") ;
	String name = request.getParameter("uname") ;
	String inst[] = request.getParameterValues("inst") ;
%>
<h3>编号：<%=id%></h3>
<h3>姓名：<%=name%></h3>
<h3>兴趣：
<%
if(inst != null) {
	for(int x=0;x<inst.length;x++){
%>
		<%=inst[x]%>、
<%
	}
}
%>
</h3>
</body>
</html>
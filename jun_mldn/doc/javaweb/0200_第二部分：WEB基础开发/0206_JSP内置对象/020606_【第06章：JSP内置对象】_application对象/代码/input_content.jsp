<%@ page contentType="text/html" pageEncoding="GBK"%>
<%@ page import="java.io.*"%>
<%@ page import="java.util.*"%>
<html>
<head><title>www.mldnjava.cn，MLDN高端Java培训</title></head>
<body>
<%	
	request.setCharacterEncoding("GBK") ;	// 解决乱码问题
	String name = request.getParameter("filename") ;
	String content = request.getParameter("filecontent") ;
	// 要想操作文件必须有绝对路径，那么这个时候getRealPath()
	String fileName = this.getServletContext().getRealPath("/") + "note" + File.separator + name ;	// 保存在note文件夹之中
	File file = new File(fileName) ;	// 实例化File类对象
	if(!file.getParentFile().exists()){
		file.getParentFile().mkdir() ;	// 建立一个文件夹
	}
	PrintStream ps = null ;
	ps = new PrintStream(new FileOutputStream(file)) ;
	ps.println(content) ;
	ps.close() ;
%>
<%
	Scanner scan = new Scanner(new FileInputStream(file)) ;
	scan.useDelimiter("\n") ;
	StringBuffer buf = new StringBuffer() ;
	while(scan.hasNext()){
		buf.append(scan.next()).append("<br>") ;
	}
	scan.close() ;
%>
<%=buf%>
</body>
</html>
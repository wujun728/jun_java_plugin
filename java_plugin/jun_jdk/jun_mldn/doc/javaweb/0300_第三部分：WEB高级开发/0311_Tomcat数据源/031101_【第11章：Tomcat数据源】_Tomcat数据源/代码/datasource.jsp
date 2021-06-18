<%@ page contentType="text/html" pageEncoding="GBK"%>
<%@ page import="javax.naming.*"%>
<%@ page import="javax.sql.*"%>
<%@ page import="java.sql.*"%>
<html>
<head><title>www.mldnjava.cn，MLDN高端Java培训</title></head>
<body>
<%
	String DSNAME = "java:comp/env/jdbc/mldn" ;	// 名称
	Context ctx = new InitialContext() ;
	DataSource ds = (DataSource) ctx.lookup(DSNAME) ;
	Connection conn = ds.getConnection() ;	// 从连接池中取连接
%>
<%=conn%>
<%
	conn.close() ;	// 表示将连接放回到池之中
%>
</body>
</html>
<%@page import="com.itheima.util.JndiDsUtil"%>
<%@page import="java.sql.Connection"%>
<%@page import="javax.sql.DataSource"%>
<%@page import="javax.naming.InitialContext"%>
<%@page import="javax.naming.Context"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'index.jsp' starting page</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
  </head>
  
  <body>
  oyeah:
    <%
    Context initContext = new InitialContext();
	//Context envContext  = (Context)initContext.lookup("java:/comp/env");//目录
	//DataSource ds = (DataSource)envContext.lookup("jdbc/test");
	
	DataSource ds = (DataSource)initContext.lookup("java:/comp/env/jdbc/test");
	Connection conn = ds.getConnection();
    out.write(conn.toString());
    %>
    <hr/>
    <%out.write(JndiDsUtil.getConnection().toString()); %>
  </body>
</html>

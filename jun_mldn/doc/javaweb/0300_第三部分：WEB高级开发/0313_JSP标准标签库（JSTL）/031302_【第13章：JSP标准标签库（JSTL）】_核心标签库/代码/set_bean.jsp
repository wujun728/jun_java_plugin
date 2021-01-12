<%@ page contentType="text/html" pageEncoding="GBK"%>
<%@ page import="org.lxh.jstldemo.vo.SimpleInfo"%>
<%@ taglib prefix="c" uri="http://www.mldn.cn/jst/core"%>
<html>
<head><title>www.mldnjava.cn£¬MLDN¸ß¶ËJavaÅàÑµ</title></head>
<body>
	<%
		SimpleInfo sim = new SimpleInfo() ;
		request.setAttribute("simple",sim) ;
	%>
	<c:set value="Hello MLDN!" target="${simple}" property="content"/>
	<h3>ÊôĞÔÄÚÈİ£º${simple.content}</h2>
</body>
</html>
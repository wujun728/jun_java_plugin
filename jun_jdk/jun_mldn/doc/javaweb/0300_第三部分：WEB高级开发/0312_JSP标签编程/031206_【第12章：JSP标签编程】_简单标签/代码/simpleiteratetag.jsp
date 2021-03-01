<%@ page contentType="text/html" pageEncoding="GBK"%>
<%@ page import="java.util.*"%>
<%@ taglib prefix="mytag" uri="mldn"%>
<html>
<head><title>www.mldnjava.cn£¬MLDN¸ß¶ËJavaÅàÑµ</title></head>
<body>
	<%
		List<String> all = new ArrayList<String>() ;
		all.add("www.MLDN.cn") ;
		all.add("www.MLDNJAVA.cn") ;
		all.add("www.JIANGKER.com") ;
		request.setAttribute("all",all) ;
	%>
	<h1>
		<mytag:simpleiterate id="url" name="all" scope="request">
			<h2>ÍøÕ¾£º${url}</h2>
		</mytag:simpleiterate>
	</h1>
</body>
</html>
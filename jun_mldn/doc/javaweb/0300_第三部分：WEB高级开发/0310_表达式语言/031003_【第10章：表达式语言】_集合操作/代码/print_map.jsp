<%@ page contentType="text/html" pageEncoding="GBK"%>
<%@ page import="java.util.*"%>
<html>
<head><title>www.mldnjava.cn，MLDN高端Java培训</title></head>
<body>
<%
	Map map = new HashMap() ;
	map.put("lxh","李兴华") ;
	map.put("mldn","www.MLDNJAVA.cn") ;
	map.put("email","mldnqa@163.com") ;
	request.setAttribute("info",map) ;	// 集合保存在request范围
%>
<h3>KEY为lxh的内容：${info["lxh"]}</h3>
<h3>KEY为mldn的内容：${info["mldn"]}</h3>
<h3>KEY为email的内容：${info["email"]}</h3>
</body>
</html>
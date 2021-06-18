<%@ page contentType="text/html" pageEncoding="GBK"%>
<%@ page import="java.util.*"%>
<html>
<head><title>www.mldnjava.cn，MLDN高端Java培训</title></head>
<body>
<%
	List all = new ArrayList() ;
	all.add("李兴华") ;
	all.add("www.MLDNJAVA.cn") ;
	all.add("mldnqa@163.com") ;
	request.setAttribute("allinfo",all) ;	// 集合保存在request范围
%>
<h3>第一个元素：${allinfo[0]}</h3>
<h3>第二个元素：${allinfo[1]}</h3>
<h3>第三个元素：${allinfo[2]}</h3>
</body>
</html>
<%@ page contentType="text/html" pageEncoding="GBK"%>
<html>
<head><title>www.mldnjava.cn，MLDN高端Java培训</title></head>
<body>
<%	// 正经开发中，此代码要通过过滤器实现
	request.setCharacterEncoding("GBK") ;
%>
<h3>第一个参数：${paramValues.inst[0]}</h3>
<h3>第二个参数：${paramValues.inst[1]}</h3>
<h3>第三个参数：${paramValues.inst[2]}</h3>
</body>
</html>
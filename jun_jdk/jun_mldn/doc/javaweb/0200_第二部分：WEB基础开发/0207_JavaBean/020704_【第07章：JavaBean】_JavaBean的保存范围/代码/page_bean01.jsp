<%@ page contentType="text/html" pageEncoding="GBK"%>
<html>
<head><title>www.mldnjava.cn，MLDN高端Java培训</title></head>
<jsp:useBean id="cou" scope="page" class="cn.mldn.lxh.demo.Count"/>
<body>
<h3>第<jsp:getProperty name="cou" property="count"/>次访问！</h3>
<jsp:forward page="page_bean02.jsp"/>
</body>
</html>
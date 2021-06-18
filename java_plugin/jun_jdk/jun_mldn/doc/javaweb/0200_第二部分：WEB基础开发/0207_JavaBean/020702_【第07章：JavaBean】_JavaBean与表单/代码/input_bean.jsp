<%@ page contentType="text/html" pageEncoding="GBK"%>
<html>
<head><title>www.mldnjava.cn£¬MLDN¸ß¶ËJavaÅàÑµ</title></head>
<body>
<%	request.setCharacterEncoding("GBK") ;	%>
<jsp:useBean id="simple" scope="page" class="cn.mldn.lxh.demo.SimpleBean"/>
<jsp:setProperty name="simple" property="*"/>
<h3>ĞÕÃû£º<%=simple.getName()%></h3>
<h3>ÄêÁä£º<%=simple.getAge()%></h3>
</body>
</html>
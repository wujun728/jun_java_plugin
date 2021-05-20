<%@ page contentType="text/html" pageEncoding="GBK"%>
<html>
<head><title>www.mldnjava.cn，MLDN高端Java培训</title></head>
<%
	request.setCharacterEncoding("GBK") ;
%>
<jsp:useBean id="reg" scope="request" class="cn.mldn.lxh.demo.Register"/>
<body>
<form action="check.jsp" method="post">
	用户名：<input type="text" name="name" value="<jsp:getProperty name="reg" property="name"/>"/><%=reg.getErrorMsg("errname")%><br>
	年&nbsp;&nbsp;龄：<input type="text" name="age" value="<jsp:getProperty name="reg" property="age"/>"/><%=reg.getErrorMsg("errage")%><br>
	E-MAIL：<input type="text" name="email" value="<jsp:getProperty name="reg" property="email"/>"/><%=reg.getErrorMsg("erremail")%><br>
	<input type="submit" value="注册">
	<input type="reset" value="重置">
</form>
</body>
</html>
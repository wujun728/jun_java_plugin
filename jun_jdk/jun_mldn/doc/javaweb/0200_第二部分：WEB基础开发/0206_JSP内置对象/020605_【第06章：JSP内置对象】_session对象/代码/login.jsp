<%@ page contentType="text/html" pageEncoding="GBK"%>
<html>
<head><title>www.mldnjava.cn，MLDN高端Java培训</title></head>
<body>
<form action="login.jsp" method="post">
	用户名：<input type="text" name="uname"><br>
	密&nbsp;&nbsp;码：<input type="password" name="upass"><br>
	<input type="submit" value="登陆">
	<input type="reset" value="重置">
</form>
<%	// 直接通过一个固定的用户名和密码
	String name = request.getParameter("uname") ;
	String password = request.getParameter("upass") ;
	if(!(name==null || "".equals(name) || password==null || "".equals(password))){
		if("lixinghua".equals(name) && "mldn".equals(password)){
			// 如果登陆成功，则设置session属性范围。
			session.setAttribute("userid",name) ;
			response.setHeader("refresh","2;URL=welcome.jsp") ;
%>
			<h3>用户登陆成功，两秒后跳转到欢迎页！</h3>
			<h3>如果没有跳转，请按<a href="welcome.jsp">这里</a>！</h3>
<%
		} else {
%>
			<h3>错误的用户名或密码！</h3>
<%
		}
	}
%>
</body>
</html>
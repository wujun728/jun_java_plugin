<%
	if (session.getAttribute("username") == null) {
		response.sendRedirect("login.jsp");
	}
%>
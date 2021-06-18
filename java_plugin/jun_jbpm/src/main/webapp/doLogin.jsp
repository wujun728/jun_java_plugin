
<%
	session.setAttribute("username", request.getParameter("username"));
	response.sendRedirect("index.jsp");
%>
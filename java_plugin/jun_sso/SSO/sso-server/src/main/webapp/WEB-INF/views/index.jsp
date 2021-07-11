<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%
	String callbackURL = request.getParameter("callbackURL");
	String token = (String)session.getAttribute("token");
	response.sendRedirect(callbackURL + "?token=" + token);
%>

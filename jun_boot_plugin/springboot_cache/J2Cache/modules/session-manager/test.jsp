<%@ page language="java" import="javax.servlet.http.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

NAME: <%=session.getAttribute("name")%>

<ul>
<li>Session ID: <%=session.getId()%> (<%=session.isNew()%>)</li>
<li>Session IP: <%=request.getServerName()%></li>
<li>Session Port: <%=request.getServerPort()%></li>
</ul>


<%
session.setAttribute("name","Winter Lau");
%>
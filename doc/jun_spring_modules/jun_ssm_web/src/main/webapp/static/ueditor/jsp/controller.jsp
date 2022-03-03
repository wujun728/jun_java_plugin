<%@ page language="java" contentType="text/html; charset=gbk"
	import="com.jun.plugin.teamplate.ActionEnter"
    pageEncoding="gbk"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%

    request.setCharacterEncoding( "gbk" );
	response.setHeader("Content-Type" , "text/html");
	
	String rootPath = application.getRealPath( "/" );
	
	out.write( new ActionEnter( request, rootPath ).exec() );
	
%>
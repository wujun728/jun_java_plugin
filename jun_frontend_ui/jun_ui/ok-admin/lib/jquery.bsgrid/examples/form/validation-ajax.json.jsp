<%@ page language="java" pageEncoding="UTF-8" %>
<%
	String formType = request.getParameter("formType");
	String fieldId = request.getParameter("fieldId");
	String fieldValue = request.getParameter("fieldValue");
	
	if (fieldValue == null) {
		fieldValue = request.getParameter("account");
	}
	
	System.out.println("formType=" + formType + ", fieldId=" + fieldId + ", fieldValue=" + fieldValue);
	
	if (fieldValue.equals("Account")) {
		out.print("[\"account\", true]"); // true表示唯一
	} else {
		out.print("[\"account\", false]"); // false非唯一
	}
%>
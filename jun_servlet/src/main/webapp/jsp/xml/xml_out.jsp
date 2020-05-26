<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/xml" prefix="x"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<c:import var="usersInfo" url="usersInfo.xml" charEncoding="UTF-8"/>
<x:parse var="usersInfoXml" doc="${usersInfo }"/>
<h2>姓名：<x:out select="$usersInfoXml/users/user/name"/>
(ID:<x:out select="$usersInfoXml/users/user/name/@id"/>)</h2>
<h2>出生日期：<x:out select="$usersInfoXml/users/user/birthday"/></h2>
</body>
</html>
<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
</head>
<body>
	<%
		double d = Math.random() * 10 + 1;
		int i = (int) d;
		String n = String.valueOf(i);
		if (i < 10) {
			n = "0" + n;
		}
	%>
	<img alt="" src="${pageContext.request.contextPath}/data/dogs/puppy_dogs_<%=n%>.png">
</body>
</html>

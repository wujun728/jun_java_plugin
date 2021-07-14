<%@ page contentType="text/html; charset=gb2312" language="java" 
pageEncoding="GB2312"%>
<html>
<head>
<title></title>
</head>

<body>
<form action="/book/chatroom/method.jsp?action=sendMsg" method="post" name="form1">
<%=session.getAttribute("username")%>:
<input name="msg" type="text" id="msg" size="60">
  <input type="submit" name="Submit" value="·¢ ÑÔ">
</form>
</body>
</html>

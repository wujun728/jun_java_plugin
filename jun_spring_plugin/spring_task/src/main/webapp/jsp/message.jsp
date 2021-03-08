<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>发送消息页面 [spring-websocket demo]</title>
<style type="text/css">
table tr td {font-size:12px;}
input, textarea {font-size: 12px; }
</style>
</head>
<body>
    <form action="${pageContext.request.contextPath}/message/send" method="post">
        <table>
            <tr>
                <td align="right">用户名:</td>
                <td><input name="username" type="text" style="width: 300px;"></td>
            </tr>
            <tr>
                <td align="right">内容:</td>
                <td><textarea name="message" style="width: 300px;"></textarea></td>
            </tr>
            <tr>
                <td colspan="2" align="center" ><input type="submit" /></td>
            </tr>
        </table>
    </form>
</body>
</html>
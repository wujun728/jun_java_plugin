<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>图书修改</title>
</head>
<body>
<form action="/book/update" method="post">
	<input type="hidden" name="id" value="${book.id}"/>
	图书名称：<input type="text" name="name" value="${book.name}"/><br/>
	图书作者：<input type="text" name="author" value="${book.author}"/><br/>
	<input type="submit" value="提交"/>
</form>
</body>
</html>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>My JSP 'createuser-success.jsp' starting page</title>

  </head>
  
  <body>
   <form action="saveMessage.action" method="post" enctype="multipart/form-data">
   	标题: <input type="text" name="message.title"/><br/>
   	内容：<textarea rows="5" cols="20" name="message.content"></textarea><br/>
   	附件：<input type="file" name="file">
   		 <input type="submit" value="Save"/>
   </form>
  </body>
</html>

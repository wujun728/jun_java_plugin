<%
If session("admin")="admin" Then 
%>
<!-- #include file="include/conn.asp" -->
<%
  set rs=server.CreateObject("adodb.recordset")
  sql="select * from contents where id="&request("id")
  rem request("id")是传递过来的参数
  rs.Open sql,conn,1,1
  name = rs("name")
  content = rs("content")
  id = rs("id")
%>

  <form action="modify_save.asp" method="post" name="name1">
    ID  :<%=id%><input type="hidden" name="id" value=<%=id%> > 
    姓名:<%=name%><br> 
    留言:<textarea name="post_contents" rows="10" cols="50"><%=content%></textarea> 
    <input type="submit" value="提交修改"> 
   </form>
<%
  conn.close
  Set conn = Nothing
  else 
  response.write ("<script language='javascript'>alert('非法进入可耻！');location='index.asp';</script>")
  End If 
%>


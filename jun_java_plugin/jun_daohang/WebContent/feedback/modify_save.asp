<%
  If session("admin")="admin" Then 
     content = request("post_contents")
     id = request("id")
%>
<!-- #include file="include/conn.asp" -->
<%
  set rs=server.CreateObject("adodb.recordset")
  sql="select * from contents where id="&id&""
  rs.open sql,conn,1,3
  rs("content")=content
  rs.update
  rs.close
  set rs=nothing
  set conn=nothing
  response.redirect("admin_index.asp")
  }
 
 else
 response.write ("<script language='javascript'>alert('非法进入可耻！');location='index.php';</script>")
 End If 
%>

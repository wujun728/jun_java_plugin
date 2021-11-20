<%
If session("admin")="admin" Then 
amain=session("admin")
%>
<!-- #include file="include/conn.asp" -->
<title>修改密码</title>
<%
 aname=request.Form("mname")
 apwd=request.Form("xpwd")
  set rs=server.CreateObject("adodb.recordset")
  sql="update admin set admin_password='"&apwd&"' where admin_name='"&amain&"'"
  conn.Execute(sql)
  conn.close
  Set conn = nothing
  response.write ("<script language='javascript'>alert('修改成功！');location='admin_index.asp';</script>")
 }
 else 
 response.write ("<script language='javascript'>alert('非法进入可耻！');location='index.asp';</script>")
 End If 
%>

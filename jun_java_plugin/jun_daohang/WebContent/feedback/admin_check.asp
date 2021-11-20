<!-- #include file="include/conn.asp" -->
<% 
  admin_name = request.form("admin_name")
  admin_password = request.Form("admin_password")
  sql="select * from admin where admin_name='"&admin_name&"'and admin_password='"&admin_password&"'"
  set rs=server.CreateObject("adodb.recordset")
  rs.Open sql,conn,1,1
  If  not(rs.bof and rs.eof) Then  
      session("admin")=rs("admin_name")
	  session.Timeout=600
	  rs.Close
      set rs=nothing
      response.redirect("admin_index.asp")       
  else  
	  response.write ("<script language='javascript'>alert('用户名或者密码不正确！');location='index.asp';</script>")
  End If
%>

 <!-- #include file="include/conn.asp" -->
<% 
  ip=Replace(Request.ServerVariables("HTTP_X_FORWARDED_FOR"),"'","")
  If ip=Empty Then ip=Replace(Request.ServerVariables("REMOTE_ADDR"),"'","")
  name=Request.Form("user_name")
  content=Request.Form("user_post")
  if name<>"" and content<>"" then
    set rs=server.CreateObject("adodb.recordset")
        sql="insert into contents (name,content,ip) values ('"&name&"','"&content&"','"&ip&"')" 
        conn.Execute(sql)
		conn.close
		Response.Write "<script language='javascript'>alert('留言成功！');location='index.asp';</script>"
        'response.redirect("index.asp")
 else 
   Response.Write "<script language='javascript'>alert('内容不能有空！');location='index.asp';</script>"
 End If 
%>

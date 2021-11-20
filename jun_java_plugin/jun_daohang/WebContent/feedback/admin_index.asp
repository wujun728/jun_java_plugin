<%
If session("admin")="admin" Then 
amain=session("admin")
%>
<!-- #include file="include/conn.asp" -->
<script language=javascript>
function checkform() {
 if(form_pwd.xpwd.value==""){
 alert("\密码不能为空！");
 return false;
 }
 return true;
}
</script>
<title>后台管理中心</title>
<div style="background:#ccc; padding:10px">
<p>后台管理中心  <a href="login_out.asp">退出</a><br />
<form name="form_pwd" action="adm_mima.asp" method="post" onsubmit="return checkform();">
（修改密码）原账号:<%=amain%>
现密码：<input type="password" name="xpwd" />
<input type="submit" value=" 修 改 " />
</form><hr /></p>
<%
  set rs = server.CreateObject("adodb.recordset")
  sql = "select * from contents order by id desc"
  rs.Open sql,conn,1,1
  Do While Not rs.eof
  %>
	  	姓名：<span style="color:blue"><%=rs("name")%></span>&nbsp;&nbsp;&nbsp;<span><br />留言：<%=rs("content")%></span><br />
		时间：<span><%=rs("time")%></span>  <span>留言人IP：<%=rs("ip")%></span> 
		<a href="modify.asp?id=<%=rs("id")%>">修改</a>   <a href=delete.asp?id=<%=rs("id")%>>删除</a> <hr />
	  
 <%
	  rs.movenext
	  Loop
	  rs.close
	  Set rs = Nothing
  else
      response.redirect("index.asp")
  End If 
%>
</div>
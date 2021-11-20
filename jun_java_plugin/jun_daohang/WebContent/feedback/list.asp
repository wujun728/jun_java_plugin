 <!-- #include file="include/conn.asp" -->
 <title>全部留言</title>
   <%   Set rs = server.CreateObject("ADODB.Recordset")
     sql="select * from contents order by id desc"
	 rs.Open sql,conn,1,1
     Do While Not rs.eof  
             
              response.write ("<table><tr>")
              response.write ("<td>&nbsp;&nbsp;姓名:"&rs("name")&"</td>")
			  response.write ("<td>&nbsp;&nbsp;留言时间:"&rs("time")&"</td>")
			  response.write ("</tr>")
              response.write ("<tr><td colspan='4'>留言:"&rs("content")&"</td></tr></table><br/>")
              response.write ("<hr/>")
              
	  rs.movenext
	  Loop
	  rem 释放资源
	  rs.close
	  Set rs = Nothing
	  
 %>
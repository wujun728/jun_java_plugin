<%
Set conn = Server.CreateObject("ADODB.Connection")
conn.open("driver={Microsoft Access Driver (*.mdb)};dbq="&Server.MapPath("include/#gbmdb.asp"))
%>
<%
rem 清除session
session("admin")=""
Session.Abandon()
response.redirect("index.asp")
rem 返回admin_index.php是判断session是否清除成功，成功则返回index.php
%>
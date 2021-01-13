<%@ page language="java" contentType="text/html" pageEncoding="GBK"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>Spring MVC通讯录</title>
<style>
body {
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
}
.td1 {
	font-size: 9pt;
	line-height: 24px;
	font-weight: bold;
	color: #000000;
	background-color: #dae8f1;
}
.td2 {
	font-size: 9pt;
	font-style: normal;
	line-height: 24px;
	background-color: #f9fdff;
	font-weight: normal;
}
a:link {
	color: #0000FF;
	text-decoration: none;
}
a:visited {
	text-decoration: none;
	color: #0000FF;
}
a:hover {
	text-decoration: none;
	color: #0000FF;
}
a:active {
	text-decoration: none;
	color: #0000FF;
}
  </style>
  </head>
  <body>
      <table width="778" align="center" cellspacing="0" bgcolor="#f9fdff">
		<tr>
			<td height="147" background="images/top.gif" colspan="9"></td>
		</tr>
		<tr>
			<td height="33" background="images/title.gif" colspan="9"></td>
		</tr>

		
		<tr><td align="center" colspan="9">
    <form name="f1" method="post" action="addrBook.html?method=insertAndUpdate">
    	<table align="center" cellspacing="1" bgcolor="#CCCCCC" width="450">
    		<tr>
    			<td height="24" align="right" class="td1">姓名：</td>
    			<td height="24" class="td2">&nbsp;
    				<input type="text" name="name" value="${addrBook.name==null?'':addrBook.name}">
    				<input type="hidden" name="id" value="${addrBook.id==null?0:addrBook.id}">
   			  </td>
    		</tr>
    		<tr>
    			<td height="24" align="right" class="td1">工作单位：</td>
   			  <td height="24" class="td2">&nbsp;&nbsp;<input type="text" name="company" value="${addrBook.company==null?'':addrBook.company}"></td>
    		</tr>
    		<tr>
    			<td height="24" align="right" class="td1">职位：</td>
   			  <td height="24" class="td2">&nbsp;&nbsp;<input type="text" name="job" value="${addrBook.job==null?'':addrBook.job}"></td>
    		</tr>
    		<tr>
    			<td height="24" align="right" class="td1">办公电话：</td>
   			  <td height="24" class="td2">&nbsp;&nbsp;<input type="text" name="tel" value="${addrBook.tel==null?'':addrBook.tel}"></td>
    		</tr>
    		<tr>
    			<td height="24" align="right" class="td1">移动电话：</td>
   			  <td height="24" class="td2">&nbsp;&nbsp;<input type="text" name="mobile" value="${addrBook.mobile==null?'':addrBook.mobile}"></td>
    		</tr>
    		<tr>
    			<td height="24" align="right" class="td1">传真：</td>
   			  <td height="24" class="td2">&nbsp;&nbsp;<input type="text" name="fax" value="${addrBook.fax==null?'':addrBook.fax}"></td>
    		</tr>
    		<tr>
    			<td height="24" align="right" class="td1">电子邮箱：</td>
   			  <td height="24" class="td2">&nbsp;&nbsp;<input type="text" name="mail" value="${addrBook.mail==null?'':addrBook.mail}"></td>
    		</tr>
    		<tr>
    			<td height="24" colspan="2" class="td2">
    				<div align="center">
    				  <input type="submit" value="确定">&nbsp;&nbsp;
    				  <input type="reset"  value="重置">
	          </div></td>
    		</tr>
   	  </table>
    </form>
		</td>
		</tr>
		<tr>
			<td height="111" background="images/bottom.gif" colspan="9"></td>
		</tr>
  </table>
  
 
  </body>
</html>

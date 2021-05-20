<%@ page contentType="text/html" pageEncoding="GBK"%>
<%@ page import="cn.mldn.lxh.factory.*,cn.mldn.lxh.vo.*"%>
<%@ page import="java.util.*"%>
<html>
<head><title>www.mldnjava.cn，MLDN高端Java培训</title></head>
<%	request.setCharacterEncoding("GBK") ;	%>
<body>
<%
	String keyWord = request.getParameter("kw") ;
	if(keyWord == null){
		keyWord = "" ;	// 如果没有查询关键字，则查询全部
	}
	List<Emp> all = DAOFactory.getIEmpDAOInstance().findAll(keyWord) ;
	Iterator<Emp> iter = all.iterator() ;
%>
<center>
<form action="emp_list.jsp" method="post">
	请输入查询关键字：<input type="text" name="kw">
	<input type="submit" value="查询">
</form>
<table border="1" width="80%"> 
	<tr>
		<td>雇员编号</td>
		<td>雇员姓名</td>
		<td>雇员工作</td>
		<td>雇佣日期</td>
		<td>基本工资</td>
	</tr>
<%
	while(iter.hasNext()){
		Emp emp = iter.next() ;
%>
	<tr>
		<td><%=emp.getEmpno()%></td>
		<td><%=emp.getEname()%></td>
		<td><%=emp.getJob()%></td>
		<td><%=emp.getHiredate()%></td>
		<td><%=emp.getSal()%></td>
	</tr>
<%
	}
%>
</table>
</center>
</body>
</html>
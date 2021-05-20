<%@ page contentType="text/html" pageEncoding="GBK"%>
<%@ taglib prefix="c" uri="http://www.mldn.cn/jst/core"%>
<%@ taglib prefix="sql" uri="http://www.mldn.cn/jst/sql"%>
<html>
<head><title>www.mldnjava.cn，MLDN高端Java培训</title></head>
<body>
<sql:setDataSource dataSource="jdbc/mldn" var="mldnds"/>
<sql:query var="result" dataSource="${mldnds}" maxRows="2" startRow="2">
	SELECT empno,ename,job,hiredate,sal FROM emp ;
</sql:query>
<h3>一共有${result.rowCount}条记录！</h3>
<table border="1" width="100%">
	<tr>
		<td>雇员编号</td>
		<td>雇员姓名</td>
		<td>雇员工作</td>
		<td>雇员工资</td>
		<td>雇佣日期</td>
	</tr>
	<c:forEach items="${result.rows}" var="row">
		<tr>
			<td>${row.empno}</td>
			<td>${row.ename}</td>
			<td>${row.job}</td>
			<td>${row.sal}</td>
			<td>${row.hiredate}</td>
		</tr>
	</c:forEach>
</table>
</body>
</html>
<%@ page contentType="text/html" pageEncoding="GBK"%>
<%@ taglib prefix="c" uri="http://www.mldn.cn/jst/core"%>
<%@ taglib prefix="sql" uri="http://www.mldn.cn/jst/sql"%>
<html>
<head><title>www.mldnjava.cn，MLDN高端Java培训</title></head>
<body>
<%
	pageContext.setAttribute("empno",6879) ;
	pageContext.setAttribute("ename","李军") ;
	pageContext.setAttribute("job","分析员") ;
	pageContext.setAttribute("date",new java.util.Date()) ;
%>
<sql:setDataSource dataSource="jdbc/mldn" var="mldnds"/>
<sql:update var="result" dataSource="${mldnds}">
	UPDATE emp SET ename=?,job=?,hiredate=? WHERE empno=? ;
	<sql:param value="${ename}"/>
	<sql:param value="${job}"/>
	<sql:dateParam value="${date}" type="date"/>
	<sql:param value="${empno}"/>
</sql:update>
</table>
</body>
</html>
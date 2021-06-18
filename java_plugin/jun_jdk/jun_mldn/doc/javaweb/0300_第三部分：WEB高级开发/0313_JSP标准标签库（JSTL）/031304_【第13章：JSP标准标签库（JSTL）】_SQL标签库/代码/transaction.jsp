<%@ page contentType="text/html" pageEncoding="GBK"%>
<%@ taglib prefix="c" uri="http://www.mldn.cn/jst/core"%>
<%@ taglib prefix="sql" uri="http://www.mldn.cn/jst/sql"%>
<html>
<head><title>www.mldnjava.cn，MLDN高端Java培训</title></head>
<body>
<sql:setDataSource dataSource="jdbc/mldn" var="mldnds"/>
<sql:transaction isolation="serializable" dataSource="${mldnds}">
	<sql:update var="result">
		INSERT INTO emp(empno,ename,job,hiredate,sal) VALUES ('6889','李彦','经理','2003-03-14',3000) ;
	</sql:update>
</sql:transaction>
</table>
</body>
</html>
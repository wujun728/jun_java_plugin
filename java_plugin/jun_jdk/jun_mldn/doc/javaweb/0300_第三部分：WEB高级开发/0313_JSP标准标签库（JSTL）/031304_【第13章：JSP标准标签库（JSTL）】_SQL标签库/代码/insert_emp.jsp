<%@ page contentType="text/html" pageEncoding="GBK"%>
<%@ taglib prefix="c" uri="http://www.mldn.cn/jst/core"%>
<%@ taglib prefix="sql" uri="http://www.mldn.cn/jst/sql"%>
<html>
<head><title>www.mldnjava.cn，MLDN高端Java培训</title></head>
<body>
<sql:setDataSource dataSource="jdbc/mldn" var="mldnds"/>
<sql:update var="result" dataSource="${mldnds}">
	INSERT INTO emp(empno,ename,job,hiredate,sal) VALUES ('6879','周军','经理','2003-03-14',9000) ;
</sql:update>
</table>
</body>
</html>
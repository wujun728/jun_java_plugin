<%@ page contentType="text/html" pageEncoding="GBK"%>
<%@ taglib prefix="c" uri="http://www.mldn.cn/jst/core"%>
<%@ taglib prefix="sql" uri="http://www.mldn.cn/jst/sql"%>
<html>
<head><title>www.mldnjava.cn£¨MLDN∏ﬂ∂ÀJava≈‡—µ</title></head>
<body>
<sql:setDataSource dataSource="jdbc/mldn" var="mldnds"/>
<sql:update var="result" dataSource="${mldnds}">
	DELETE FROM emp WHERE empno=6878 ;
</sql:update>
</table>
</body>
</html>
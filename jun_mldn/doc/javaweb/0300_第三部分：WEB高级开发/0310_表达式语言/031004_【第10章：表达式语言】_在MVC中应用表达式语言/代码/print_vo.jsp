<%@ page contentType="text/html" pageEncoding="GBK"%>
<%@ page import="org.lxh.eldemo.vo.*"%>
<html>
<head><title>www.mldnjava.cn，MLDN高端Java培训</title></head>
<body>
<%	// 现在假设这些代码是由Servlet完成
	Dept dept = new Dept() ;
	dept.setDeptno(10) ;
	dept.setDname("MLDN教学部") ;
	dept.setLoc("北京西城区") ;
	request.setAttribute("deptinfo",dept) ;
%>
<h3>部门编号：${deptinfo.deptno}</h3>
<h3>部门名称：${deptinfo.dname}</h3>
<h3>部门位置：${deptinfo.loc}</h3>
</body>
</html>
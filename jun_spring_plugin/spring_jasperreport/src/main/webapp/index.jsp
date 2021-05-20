<%--
  Created by IntelliJ IDEA.
  User: liuburu
  Date: 2018/1/8
  Time: 17:45
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    // springmvc
    String contextPath = request.getContextPath();
    // http://localhost:8080/springmvc/
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + contextPath;
    request.setAttribute("ctx", basePath);
%>
<html>
<head>
    <title>欢迎页面</title>
</head>
<body>
This World Is Beautiful.  ---KiWiPeach
<h2><a href="${ctx}/demo/report/base">00.基础报表组件</a></h2>
<hr>
<h2><a href="${ctx}/demo/report/javabean">01.JavaBean数据源方式报表</a></h2>
<hr>
<h2><a href="${ctx}/demo/report/jdbc/测试文件?format=pdf&empNo=4444">02.数据库数据源方式报表</a></h2>
<hr>
<hr>
<h2><a href="${ctx}/demo/report/chinese-font/测试文件?format=pdf">03.中文字体-数据源报表(华文楷体、宋体等..)</a></h2>
<hr>
</body>
</html>

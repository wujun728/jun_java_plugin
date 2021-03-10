<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<html>
<head>
    <base href="<%=basePath%>"/>
    <title>添加部门</title>
    <link rel="stylesheet" href="resources/layui/css/layui.css">
    <style>
        .sys-add-edit{ padding-top: 25px;text-align: center; }
        .sys-input-size{width: 380px}
    </style>
</head>
<body class="layui-container sys-add-edit">
    <form class="layui-form">
        <c:if test="${!empty dept.deptno}">
            <div class="layui-form-item">
                <div class="layui-form-label">部门编号:</div>
                <div class="layui-input-block">
                    <input class="layui-input layui-disabled sys-input-size" name="deptno" readonly value="${dept.deptno}" />
                </div>
            </div>
        </c:if>
        <div class="layui-form-item">
            <div class="layui-form-label">部门名称:</div>
            <div class="layui-input-block">
                 <input class="layui-input sys-input-size" placeholder="请输入部门名称" required lay-verify="required" name="dname" value="${dept.dname}" />
            </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-form-label">部门地址:</div>
            <div class="layui-input-block">
                <input class="layui-input sys-input-size" placeholder="请输入部门地址" required lay-verify="required" name="loc" value="${dept.loc}" />
            </div>
        </div>
        <div class="layui-form-item">
            <c:choose>
                <c:when test="${!empty dept.deptno}">
                    <button class="layui-btn" lay-submit lay-filter="edit-submit">修改</button>
                </c:when>
                <c:otherwise>
                    <button class="layui-btn" lay-submit lay-filter="add-submit">添加</button>
                </c:otherwise>
            </c:choose>
            <input type="reset" class="layui-btn layui-btn-normal" value="重置" />
        </div>
    </form>

    <script type="text/javascript" src="resources/layui/layui.js"></script>
    <script type="text/javascript" src="resources/js/dept.js" ></script>
</body>
</html>

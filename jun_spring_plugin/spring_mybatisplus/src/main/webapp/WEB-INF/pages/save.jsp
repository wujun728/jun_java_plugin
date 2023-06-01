<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <title>编辑</title>
    <%@include file="common/include.jsp" %>
</head>
<body>
<div class="container">
    <div class="row">
        <div class="col-lg-8">
            <form id="form" role="form">
                <input id="id" name="id" value="${user.id}" hidden>
                <div class="form-group">
                    <label for="name">姓名</label>
                    <input type="text" class="form-control" id="name" name="name" value="${user.name}" placeholder="请输入姓名">
                </div>
                <div class="form-group">
                    <label for="age">年龄</label>
                    <input type="text" class="form-control" id="age" name="age" value="${user.age}" placeholder="请输入年龄">
                </div>
                <button type="button" onclick="save()" class="btn btn-default">保存</button>
                <button type="reset" class="btn btn-default">取消</button>
            </form>
        </div>
    </div>
</div>
<script>
    function save() {
        $.ajax({
            type: 'POST',
            url: '${ctx}/save',
            data: $('#form').serialize(),
            dataType: 'json',
            success: function (data) {
                alert(data.msg);
                if (data.success) {
                    setTimeout('toIndex()', 100);
                }
            },
            error: function () {
                alert("出错了,请重试!");
            }
        });
    }
</script>
</body>
</html>

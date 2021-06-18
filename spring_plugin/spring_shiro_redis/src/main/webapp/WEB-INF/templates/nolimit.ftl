<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>
        Spring+Shiro+Redis集成
    </title>
    <script>
        var cpath = '${cpath}';
    </script>
    <!-- 新 Bootstrap 核心 CSS 文件 -->
    <link rel="stylesheet" href="//cdn.bootcss.com/bootstrap/3.3.5/css/bootstrap.min.css">
    <!-- 可选的Bootstrap主题文件（一般不用引入） -->
    <link rel="stylesheet" href="//cdn.bootcss.com/bootstrap/3.3.5/css/bootstrap-theme.min.css">
    <!-- jQuery文件。务必在bootstrap.min.js 之前引入 -->
    <script src="//cdn.bootcss.com/jquery/1.11.3/jquery.min.js"></script>
    <!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
    <script src="//cdn.bootcss.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
    <script src="${cpath}/lib/login.js" type="text/javascript"></script>
    <script>
        $(function(){
            $('#btn-logout').click(function(){
                LOGIN.logout();
            });
        });
    </script>
</head>
<body role="document">
<div class="container">
    <div class="header clearfix">
        <nav>
            <ul class="nav nav-pills pull-right">
                <li role="presentation" class="active"><a href="${cpath}/home/main.html">主页</a></li>
                <li role="presentation"><a id="btn-logout" href="javascript:void(0)">退出登录</a></li>
            </ul>
        </nav>
        <h3 class="text-muted"><#if currUser?exists>${currUser.username}<#else>${username?default("")}</#if>,欢迎您登录。</h3>
    </div>
    <h2><#if currUser?exists>${currUser.username}<#else>${username?default("")}</#if>,无权访问。</h2>
</div>
</body>
</html>
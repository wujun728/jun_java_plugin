<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <title>后台登陆</title>
    <meta content="IE=edge,chrome=1" http-equiv="X-UA-Compatible">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">
    <!-- 新 Bootstrap 核心 CSS 文件 -->
    <link rel="stylesheet" href="//cdn.bootcss.com/bootstrap/3.3.5/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="${cpath}/css/theme.css">
    <link rel="stylesheet" href="${cpath}/lib/font-awesome/css/font-awesome.css">
    <script src="//cdn.bootcss.com/jquery/1.11.3/jquery.min.js"></script>
    <script src="${cpath}/lib/login.js" type="text/javascript"></script>
    <script>
        $(function(){
            $('#btn-login').click(function(){
                LOGIN.dologin();
            });
        });
    </script>

    <style type="text/css">
        #line-chart {
            height:300px;
            width:800px;
            margin: 0px auto;
            margin-top: 1em;
        }
        .brand { font-family: georgia, serif; }
        .brand .first {
            color: #ccc;
            font-style: italic;
        }
        .brand .second {
            color: #fff;
            font-weight: bold;
        }
    </style>

    <!-- Le HTML5 shim, for IE6-8 support of HTML5 elements -->
    <!--[if lt IE 9]>
    <script src="${cpath}/js/html5.js"></script>
    <![endif]-->
</head>

<!--[if lt IE 7 ]> <body class="ie ie6"> <![endif]-->
<!--[if IE 7 ]> <body class="ie ie7"> <![endif]-->
<!--[if IE 8 ]> <body class="ie ie8"> <![endif]-->
<!--[if IE 9 ]> <body class="ie ie9"> <![endif]-->
<!--[if (gt IE 9)|!(IE)]><!-->
<body>
<!--<![endif]-->

<div class="container">
    <div id="loginbox" class="mainbox col-md-6 col-md-offset-3 col-sm-8 col-sm-offset-2 loginbox">
        <div class="panel panel-info" >
            <div class="panel-heading">
                <div class="panel-title">Spring+Shiro+Redis集成测试</div>
                <div class="forgot-password"> <a href="#">忘记密码?</a> </div>
            </div>
            <div class="panel-body panel-pad">
                <div id="login-alert" class="alert alert-danger col-sm-12 login-alert"></div>
                <form id="login_form" action="${cpath}/user/doLogin.html" method="post" class="form-horizontal" role="form">
                    <div class="input-group margT25">
							<span class="input-group-addon">
								<i class="glyphicon glyphicon-user"></i>
							</span>
                        <input id="username" type="text" class="form-control" name="username" value="111111" placeholder="手机号/邮箱">
                    </div>
                    <div class="input-group margT25">
                        <span class="input-group-addon"><i class="glyphicon glyphicon-lock"></i></span>
                        <input id="password" type="password" class="form-control" name="password" value="123456" placeholder="密码">
                    </div>
                    <div class="input-group">
                        <div class="checkbox">
                            <label>
                                <input id="login-remember" type="checkbox" name="rememberMe" value="1">记住我
                            </label>
                        </div>
                    </div>
                    <div class="form-group margT10">
                        <!-- Button -->
                        <div class="col-sm-12 controls">
                            <a id="btn-login" href="#" class="btn btn-block btn-success">登录</a>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="col-md-12 control">
                            <div class="no-acc">
                                还没有账号！
                                <a href="#">点击注册</a>
                            </div>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
    <script src="//cdn.bootcss.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
</body>
</html>



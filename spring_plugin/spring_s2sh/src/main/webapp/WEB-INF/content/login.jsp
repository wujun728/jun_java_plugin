<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>login</title>
    <script src="./js/jquery-1.12.3.min.js"></script>
<link rel="stylesheet" href="//cdn.bootcss.com/bootstrap/3.3.5/css/bootstrap.min.css">
<script src="//cdn.bootcss.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
  </head>
  <body>
    <div class="container">
	<div class="col-md-6 col-md-offset-3">
      <form  action="login" method="post">
        <h2 >Please login</h2>
        <label for="inputEmail" class="sr-only">userid</label>
        <input type="text" name="username" id="inputEmail" class="form-control" placeholder="userid" required autofocus>
        <label for="inputPassword" class="sr-only">Password</label>
        <input type="password" name="password" id="inputPassword" class="form-control" placeholder="Password" required>
        <label for="inputEmail" class="sr-only">userid</label>
        <input type="text" name="pic" id="pic" class="form-control" placeholder="验证码" required>              
                        验证码：<img src="authImg" width="120" height="40">  
        <button class="btn btn-lg btn-primary btn-block" type="submit">login</button>
      </form>
        	
	</div>
    </div> <!-- /container -->
  </body>
</html>

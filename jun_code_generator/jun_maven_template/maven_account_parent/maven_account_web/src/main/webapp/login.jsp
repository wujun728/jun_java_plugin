<%@ page contentType="text/html; charset=gb2312" language="java" %>
<html>
<head>
<style type="text/css">

.text-field {position: absolute; left: 40%; background-color:rgb(255,230,220);}
label {display: inline-table; width: 90px; margin: 0px 0px 10px 20px; }
input {display: inline-table; width: 150px; margin: 0px 20px 10px 0px;}
h2 {margin: 20px 20px 20px 40px;} 
button {margin: 20px 20px 10px 110px}      
</style>
</head>
<body>

<div class="text-field">

<h2>账户登录</h2>
<form name="login" action="login" method="post">
<label>账户ID：</label><input type="text" name="id"></input><br/>
<label>密码：</label><input type="password" name="password"></input><br/>
<button>确认并提交</button>
</form>
</div>

</body>
</html>
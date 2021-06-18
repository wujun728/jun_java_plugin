<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="css/bootstrap.min.css">
<script src="js/vue/vue.min.js"></script>
<script src="js/jquery/jquery.min.js"></script>
<script src="js/laypage/laypage.js" charset="utf-8"></script>
<script src="js/layer/layer.js" charset="utf-8"></script>
</head>
<body>
	${Session["user"].userName}£¬»¶Ó­Äã
	
	<div id="app" class="container">
		<div class="table-responsive">	
			<table border="1px" class="table">
					<tr class="success">
						<td>ÐòºÅ</td>
						<td>ÐÕÃû</td>
						<td>²Ù×÷</td>
					</tr>
				<#list list as u>
					<tr>
						<td><a href="/user/get/${u.id }">${u.userName}</a></td>
						<td>${u.age }</td>
						<td><a href="/user/del/${u.id }">É¾³ý</a></td>				
					</tr>
				</#list>		
			</table>
		</div>
	</div>	
 	
</body>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
	<script src="//cdn.bootcss.com/jquery/2.2.3/jquery.js"></script>
	<script src="/common/js/login.js"></script>
	<script src="/common/js/jquery.js"></script>
</head>
<body>
	<form action="/login" method="post">
		username:<input type="text" name="username"/>
				 <span class="username_inf"></span><br>
		password:<input type="text" name="password"/>
		<input type="button" value="提交">
	</form>
	<hr/>
		<a href="/getexecl">导出Execl表格</a>
	<table>
		<caption align="top">我的表</caption>
		<thead>
			<tr>
				<th>
					userid
				</th>
				<th>
					username
				</th>
				<th>
					money
				</th>
				<th>
					birthday
				</th>
				<th>
					operation
				</th>
			</tr>
		</thead>
		<tbody>
		</tbody>
	</table>
	<script type="text/javascript">
		
		NUC.load({
			//参数
			/*
				username:kangli
				password:123
			*/
		})
	</script>
</body>
</html>
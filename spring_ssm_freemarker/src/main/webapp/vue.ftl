<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<script type="text/javascript" src="js/vue.js"/>
<script>
	var app=new Vue(
		el:'#app',
		data:{
			message:'Hello vue';
		}
	)

</script>

<body>
	<div id="app">
		{{message}}
	</div>
</body>
</html>
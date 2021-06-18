<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
<script src="//cdn.bootcss.com/jquery/2.2.3/jquery.js"></script>
<script type="text/javascript">
	var submit=function(){
		var name=document.getElementById("username").value;
		var age=document.getElementById("userage").value;
		var sex=document.getElementById("usersex").value;
		var data={"name":name,"age":age,"sex":sex}
		$.ajax({
			type:"post",
			url:"/tojson",
			contentType:"application/json",
			data:"{\"sex\":\""+sex+"\",\"age\":\""+age+"\",\"name\":\""+name+"\",\"birthday\":\"1990-01-02\"}"
		})
	}
	var submit2=function(){
		var name=document.getElementById("xmlname").value;
		var age=document.getElementById("xmlage").value;
		var sex=document.getElementById("xmlsex").value;
		alert(xml);
		$.ajax({
			type:"post",
			url:"/toxml",
			contentType:"application/xml",
			dataType:'xml',
			data:"\<?xml version=\"1.0\" encoding=\"UTF-8\"?\>\<user\>\<name\>"+name+"\<\/name\>\<age\>"+age+"\<\/age\>\<sex\>"+sex+"\<\/sex\>\<birthday\>1995-09-20\<\/birthday\>\<\/user\>"		
		   })
	}
</script>
</head>
<body>
<form action="/user" method="post">
	name:<input type="text" name="user.name" /><br/>
	age:<input type="text" name="user.age" /><br/>
	sex:<input type="text" name="user.sex" /><br/>
	street:<input type="text" name="user.address.street"><br/>
	No:<input type="text" name="user.address.No"><br/>
	tel:<input type="text" name="user.address.tel"><br/>
	<input type="submit" value="提交">
</form>
<hr>
<form action="/toarray" method="post">
	book1:<input type="text" name="books"><br/>
	book2:<input type="text" name="books"><br/>
	book3:<input type="text" name="books"><br/>
	book4:<input type="text" name="books"><br/>
	book5:<input type="text" name="books"><br/>
	book6:<input type="text" name="books"><br/>
	<input type="submit" value="提交">
</form>
<hr>
<h1>list</h1>
<form action="/tolist" method="post">
	user.tel:<input type="text" name="addresss[0].tel"><br/>
	user.street:<input type="text" name="addresss[3].street"><br/>
	<input name="list[0]"/>
	<input name="list[1]"/>
	<input name="list[2]"/>
	<input type="submit" value="提交">
</form>
<hr>
<form action="/tomap" method="post">
	user.tel:<input type="text" name="map[0].tel"><br/>
	user.street:<input type="text" name="map[a].street"><br/>
	<input type="submit" value="提交">
</form>
<hr>
<form action="/todate" method="post">
	date1:<input type="text" name="date">
	<input type="submit" value="提交">
</form>
<hr>
<form action="/todate2" method="post">
	date2:<input type="text" name="user.birthday">
	<input type="submit" value="提交">
</form>
<hr>
	name:<input type="text" id="username" /><br/>
	age:<input type="text" id="userage" /><br/>
	sex:<input type="text" id="usersex" /><br/>
	<button  onclick="submit()">提交</button>
<hr>
	<div style="display:inline-block">
		name:<input type="text" id="xmlname" /><br/>
		age:<input type="text" id="xmlage" /><br/>
		sex:<input type="text" id="xmlsex" /><br/>
		<button  onclick="submit2()">提交</button>
	</div>
	<div style="display:inline-block;" >
		xml:<textarea cols="10" rows="5" id="xmltxt" style="width:929px"></textarea>
		<button  onclick="submit2()">提交</button>
	</div>
<hr>
<form action="/request" method="POST">
	<input type="hidden" name="_method" value="delete">
	nick:<input type="text" name="nick">
	<input type="submit" value="提交">
</form>
<hr>
<h1>Session请求</h1>
<form action="/session" method="post">
	<input type="submit" value="提交">
</form>
</body>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript">
	function loadName(){
		var xmlHttp;
		if(window.XMLHttpRequest){
			xmlHttp=new XMLHttpRequest();
		}else{
			xmlHttp=new ActiveXObject("Microsoft.XMLHTTP");
		}
		alert("readState状态："+xmlHttp.readyState+";status状态："+xmlHttp.status);
		xmlHttp.onreadystatechange=function(){
			alert("readState状态："+xmlHttp.readyState+";status状态："+xmlHttp.status);
			if(xmlHttp.readyState==4 && xmlHttp.status==200){
				alert(xmlHttp.responseText);
				document.getElementById("name").value=xmlHttp.responseText;
			}
		};
		// xmlHttp.open("get", "getAjaxName?name=jack&age=11", true);
		// xmlHttp.open("post", "getAjaxName?name=jack&age=11", true);
		// xmlHttp.send();
		
	    xmlHttp.open("post", "getAjaxName", true);
	    xmlHttp.setRequestHeader("Content-type","application/x-www-form-urlencoded");
	    xmlHttp.send("name=jack&age=11");
	}
</script>
</head>
<body>
<div style="text-align: center;">
	<div><input type="button" onclick="loadName()" value="Ajax获取数据"/>&nbsp;&nbsp;<input type="text" id="name" name="name" /></div>
</div>
</body>
</html>
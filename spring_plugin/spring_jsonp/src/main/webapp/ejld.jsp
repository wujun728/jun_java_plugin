<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript">
	function loadInfo(){
		var shengId=document.getElementById("sheng").value;
		shi.options.length=0;  // 删除所有市下拉框的选项
		var xmlHttp;
		if(window.XMLHttpRequest){
			xmlHttp=new XMLHttpRequest();
		}else{
			xmlHttp=new ActiveXObject("Microsoft.XMLHTTP");
		}
		xmlHttp.onreadystatechange=function(){
			if(xmlHttp.readyState==4 && xmlHttp.status==200){
				alert(xmlHttp.responseText);
				var dataObj=eval("("+xmlHttp.responseText+")");
				for(var i=0;i<dataObj.rows.length;i++){
					var o=dataObj.rows[i];
					shi.options.add(new Option(o.text,o.id));
				}
			}
		};
		xmlHttp.open("get", "getAjaxInfo?action=ejld&shengId="+shengId, true);
		
	    xmlHttp.send();
	}
</script>
</head>
<body>
省：
<select id="sheng" onchange="loadInfo()">
	<option value="1">江苏省</option>
	<option value="2">山东省</option>
	<option value="3">浙江省</option>
</select>
&nbsp;&nbsp;
市
<select id="shi">
</select>
</body>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript">
	function loadInfo(){
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
				alert(dataObj.name);
				alert(dataObj.age);
				document.getElementById("name").value=dataObj.name;
				document.getElementById("age").value=dataObj.age;
			}
		};
		xmlHttp.open("get", "getAjaxInfo?action=jsonObject", true);
		
	    xmlHttp.send();
	}
	
	function loadInfo2(){
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
				var st=document.getElementById("studentTable");
				alert(dataObj.students.length);
				var newTr; // 行
				var newTd0; // 第一列
				var newTd1; // 第二列
				var newTd2; // 第三列
				for(var i=0;i<dataObj.students.length;i++){
					var student=dataObj.students[i];
					newTr=st.insertRow();
					newTd0=newTr.insertCell();
					newTd1=newTr.insertCell();
					newTd2=newTr.insertCell();
					newTd0.innerHTML=student.name;
					newTd1.innerHTML=student.age;
					newTd2.innerHTML="语文:"+student.score.chinese+",数学:"+student.score.math+",英语:"+student.score.english;
				}
			}
		};
		// xmlHttp.open("get", "getAjaxInfo?action=jsonArray", true);
		xmlHttp.open("get", "getAjaxInfo?action=jsonNested", true);
	    xmlHttp.send();
	}
</script>
</head>
<body>
<div style="text-align: center;">
	<div><input type="button" onclick="loadInfo()" value="Ajax获取信息"/>&nbsp;&nbsp;姓名：<input type="text" id="name" name="name" />&nbsp;&nbsp;年龄：<input type="text" id="age" name="age" /></div>
	<div style="margin-top: 20px;">
		<input type="button" onclick="loadInfo2()" value="Ajax获取信息2"><br/>
		<table id="studentTable" align="center" border="1px;" cellpadding="0px;">
			<tr>
				<th>姓名</th><th>年龄</th><th>得分</th>
			</tr>
		</table>
	</div>
</div>
</body>
</html>
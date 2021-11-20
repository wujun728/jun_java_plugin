//当页面加载完毕之后，执行以下代码
window.onload = function(){
	document.getElementById("ok").onclick = function(){
		 var xhr = ajaxFunction();
		
		 xhr.onreadystatechange = function(){
			if(xhr.readyState==1){
				document.getElementById("divcheck").innerHTML = "<img src='loading33.gif'></img> 正在连接";
				alert("xxx");
			}else if(xhr.readyState==2){
				document.getElementById("divcheck").innerHTML = "<img src='loading33.gif'></img> 正在加载";
				alert("xxx");
			}else if(xhr.readyState==3){
				document.getElementById("divcheck").innerHTML = "<img src='loading33.gif'></img> 正在处理";
				alert("xxx");
			}else if(xhr.readyState==4){
				document.getElementById("divcheck").innerHTML = "显示视频页面";
			}else{
				document.getElementById("divcheck").innerHTML = "视频页面加载失败";
			}
		 }
		
		 xhr.open("POST","../loadServlet?timeStamp="+new Date().getTime(),true);
		 
		 xhr.setRequestHeader("Content-type","application/x-www-form-urlencoded");
		
		 xhr.send(null);
	}
}

function ajaxFunction(){
   var xmlHttp;
   try{ // Firefox, Opera 8.0+, Safari
        xmlHttp=new XMLHttpRequest();
    }
    catch (e){
	   try{// Internet Explorer
	         xmlHttp=new ActiveXObject("Msxml2.XMLHTTP");
	      }
	    catch (e){
	      try{
	         xmlHttp=new ActiveXObject("Microsoft.XMLHTTP");
	      }
	      catch (e){}
	      }
    }

	return xmlHttp;
 }

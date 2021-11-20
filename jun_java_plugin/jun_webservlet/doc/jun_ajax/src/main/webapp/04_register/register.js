window.onload = function(){
	document.getElementById("checkusername").onclick = function(){
		var username = document.getElementById("username").value;
		
		var xhr = ajaxFunction();
		
		xhr.onreadystatechange = function(){
			if(xhr.readyState==4){
				if(xhr.status==200){
					var data = xhr.responseText;		//获取文本
					document.getElementById("divcheck").innerHTML = data;
				}
			}
		}
		
		xhr.open("post","../registerServlet?timeStamp="+new Date().getTime(),true);
		
		xhr.setRequestHeader("Content-type","application/x-www-form-urlencoded");
		
		xhr.send("username="+username);
		
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
//当页面加载完毕之后，执行以下代码
window.onload = function(){
	document.getElementById("ok").onclick = function(){
		/*
		 * 1	创建XMLHttpRequest对象
		 */ 
		 var xhr = ajaxFunction();
		
		/*
		 * 2	服务器向浏览器响应请求
		 * 
		 * readyState 属性表示Ajax请求的当前状态。它的值用数字代表。
				0 代表未初始化。 还没有调用 open 方法
				1 代表正在加载。 open 方法已被调用，但 send 方法还没有被调用
				2 代表已加载完毕。send 已被调用。请求已经开始
				3 代表交互中。服务器正在发送响应
				4 代表完成。响应发送完毕
				
			常用状态码及其含义：
				404 没找到页面(not found)
				403 禁止访问(forbidden)
				500 内部服务器出错(internal service error)
				200 一切正常(ok)
				304 没有被修改(not modified)(服务器返回304状态，表示源文件没有被修改 )
		 */ 
		 xhr.onreadystatechange = function(){
		 	alert(xhr.readyState);
			//alert(xhr.status);
			if(xhr.readyState==4){
				if(xhr.status==200||xhr.status==304){
					var data = xhr.responseText;
					alert(data);
				}
			}
		 }
		
		/*
		 * 3	浏览器与服务器建立连接
		 * 
		 * xhr.open(method, url, asynch);
		 * 		* 与服务器建立连接使用
		 * 		* method：请求类型，类似 “GET”或”POST”的字符串。
		 * 		* url：路径字符串，指向你所请求的服务器上的那个文件。请求路径
		 * 		* asynch：表示请求是否要异步传输，默认值为true(异步)。
		 */ 
		 xhr.open("POST","../testServlet?timeStamp="+new Date().getTime()+"&c=18",true);
		 
		 //如果是POST请求方式，设置请求首部信息
		 xhr.setRequestHeader("Content-type","application/x-www-form-urlencoded");
		 
		
		/*
		 * 4	浏览器向服务器发送请求
		 * 
		 * 	send()方法：
		 * 		* 如果浏览器请求的类型为GET类型时，通过send()方法发送请求数据，服务器接收不到	
		 * 		* 如果浏览器请求的类型为POST类型时，通过send()方法发送请求数据，服务器可以接收
		 */ 
		 xhr.send("a=6&b=9");		//xhr.send(null);
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

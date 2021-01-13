# jun_activemq，基于Apache ActiveMQ的JMS的Topic和Queue+Ajax实现

> 本 demo 主要是说明原生ActiveMQ的实现，，基于Apache ActiveMQ的JMS的Topic和即Queue实现

## 使用说明
> main方法直接跑就可以了

## pom.xml
> 无特殊依赖  activemq-all-5.11.1.jar
```xml
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
  xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
  <modelVersion>4.0.0</modelVersion>
  <parent>
	  <groupId>com.jun.plugin</groupId>
	  <artifactId>jun_plugin</artifactId>
	  <version>1.0</version>
  </parent>
  <artifactId>jun_ajax</artifactId>

  <properties>
    <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
  </properties>

  <dependencies>
 
    <dependency>
        <groupId>mysql</groupId>
        <artifactId>mysql-connector-java</artifactId>
        <version>5.1.38</version>
    </dependency>
	
    <dependency>
	    <groupId>junit</groupId>
	    <artifactId>junit</artifactId>
	    <version>4.11</version>
        <scope>test</scope>
    </dependency>
    
  </dependencies>
</project>
```

## web.xml
> 无特殊依赖
```xml
 <?xml version="1.0" encoding="UTF-8"?>
<web-app version="3.0" 
	xmlns="http://java.sun.com/xml/ns/javaee" 
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" 
	xsi:schemaLocation="http://java.sun.com/xml/ns/javaee 
	http://java.sun.com/xml/ns/javaee/web-app_3_0.xsd">
  <display-name></display-name>
  <servlet>
    <description>This is the description of my J2EE component</description>
    <display-name>This is the display name of my J2EE component</display-name>
    <servlet-name>TestServlet</servlet-name>
    <servlet-class>com.jun.plugin.ajax.servlet.TestServlet</servlet-class>
  </servlet>
  <servlet>
    <description>This is the description of my J2EE component</description>
    <display-name>This is the display name of my J2EE component</display-name>
    <servlet-name>LoadServlet</servlet-name>
    <servlet-class>com.jun.plugin.ajax.servlet.LoadServlet</servlet-class>
  </servlet>
  <servlet>
    <description>This is the description of my J2EE component</description>
    <display-name>This is the display name of my J2EE component</display-name>
    <servlet-name>RegisterServlet</servlet-name>
    <servlet-class>com.jun.plugin.ajax.servlet.RegisterServlet</servlet-class>
  </servlet>
  <servlet>
    <description>This is the description of my J2EE component</description>
    <display-name>This is the display name of my J2EE component</display-name>
    <servlet-name>XmlFileServlet</servlet-name>
    <servlet-class>com.jun.plugin.ajax.servlet.XmlFileServlet</servlet-class>
  </servlet> 
  <servlet-mapping>
    <servlet-name>TestServlet</servlet-name>
    <url-pattern>/testServlet</url-pattern>
  </servlet-mapping>
  <servlet-mapping>
    <servlet-name>LoadServlet</servlet-name>
    <url-pattern>/loadServlet</url-pattern>
  </servlet-mapping>
  <servlet-mapping>
    <servlet-name>RegisterServlet</servlet-name>
    <url-pattern>/registerServlet</url-pattern>
  </servlet-mapping>
  <servlet-mapping>
    <servlet-name>XmlFileServlet</servlet-name>
    <url-pattern>/xmlFileServlet</url-pattern>
  </servlet-mapping>	
  <welcome-file-list>
    <welcome-file>index.jsp</welcome-file>
  </welcome-file-list>
</web-app>
```

## javascript.js
> 注册Dom树事件，创建XMLHttpRequest对象，建立服务器链接，请求发送数据，接受服务器数据
```javascript
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
		 xhr.open("GET","../testServlet?timeStamp="+new Date().getTime()+"&c=18",true);
		
		/*
		 * 4	浏览器向服务器发送请求
		 * 
		 * 	send()方法：
		 * 		* 如果浏览器请求的类型为GET类型时，通过send()方法发送请求数据，服务器接收不到
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
```

## 运行&地址

将项目运行起来之后，会在**控制台**里查看所有可以访问的端口信息
1. 打开浏览器，访问：http://localhost:8090/jun_ajax ，输入用户名(admin)密码(123456)即可看到所有的信息
2. 打开webapp目录下面的文件，根据文件请求的路径访问
3. 其余可访问的路径，参见文档

## 参考
- Ajax文档：https://www.w3school.com.cn/jquery/ajax_ajax.asp

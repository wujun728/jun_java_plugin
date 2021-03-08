#spring-boot wm-accesstoken
该项目使用 Spring-boot 1.3 来搭建   
是一个简单的应用，可以打包直接运行在有java环境的系统中   
需要工具  git,maven,java-sdk1.7+,打包完成后可以在 javase1.7+环境中运行   

项目说明    
这是一个微服务的简单模型，这个微服务的主要作用是用来统一管理微信的accesstoken。    
在微信开发中，我们都知道`access_token`是个很重要的东西，微信的大部分接口都需要`access_token`来做认证。    
管理`access_token`的方式有很多种，这里就是实现了用微服务来统一托管。该服务提供了几个接口进行`access_token`的操作    
使用get/getstr/flush接口后，服务器都会缓存`access_token`避免重复获取，并且会在设置好的过期时间后自动重新获取`access_token`    
所有接口需要提供appid,secret(微信公众号提供的参数)来保证安全性，和确定`access_token`
目前提供了 微信基本access_token 接口，和 js_token 接口，分别对应token和jstoken请求   使用rest风格api  调用 token/get/appid参数/secret参数 即可 

	(token|jstoken)/get/appid/secret	获取token并返回JSON对象
	(token|jstoken)/getstr/appid/secret	获取token并返回字符串：  wechat_access_token
	(token|jstoken)/flush/appid/secret	刷新token并返回JSON对象
	(token|jstoken)/remove/appid/secret	删除服务器正在托管的token

	
	
返回的json格式对象如下:
	
	{
    "accessToken": "access_token..",
    "type": "WECHAT_ACCESS_TOKEN",  //目前有wechat_access_token 和 JS_ACCESS_TOKEN
    "status": "NEW|CACHE",    //NEW标示是服务器新托管的access_token,cache代表服务器缓存的access_token
    "expires": 1453433924966  //access_token在微信服务器的有效期时间戳
	}	
    
###    

运行方式：    
1: clone代码
*    `git clone https://git.oschina.net/diamond/spring-boot-wm-accesstoken.git`    

2: mvn 打包
*	`cd spring-boot-wm-accesstoken/ `
*	`mvn clean package`
	
3: 运行    

*       `java -jar target\wm-accesstoken-1.0.jar`  or `java -Dserver.port=9999 -Drate=100000 -DflushTime=7150000 -jar target\wm-accesstoken-1.0.jar`  
*		`server.port`是服务器端口号  
*		`rate` 是多久检测一次 accesstoken是否过期  
*		`flushTime Accesstoken` 过期前多长时间刷新，单位毫秒，200000标示过期前200秒进行刷新  

浏览器打开 `localhost:8999/`，后面的端口号如果没有设置，默认是8999，打开后看见    

	Wechat accesstoken manager center; Accesstoken size: 1
	Date:Fri Jan 22 09:44:35 CST 2016
类似这样的信息，表示服务运行成功啦！    
直接用get或者post请求对应的接口，就可以获得对应的参数了，微服务的优点在于，运行方便，参数可以灵活配置，只要一行代码即可部署在任意服务器上！    

源码的src/main/resource下有默认配置，如果命令行没有添加参数，则使用 application.yml 文件中的配置，优先级  命令行参数>application.yml配置文件，大家也可以根据需要，修改默认配置并打包运行
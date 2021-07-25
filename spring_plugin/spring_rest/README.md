# Spring-REST
    如果说Web Service是一种特殊的RPC，那么REST Service又是一种特殊的Web Service，目前已成为分布式通信的重要手段。本项目基于Spring框架，完成REST（表现层状态转移）风格的接口的发布(Server)与接收(Client)。特性如下：
    
    
Server端：
    
    1.基于SpringMVC的@RequestMapping的属性method配置GET,POST,DELETE,PUT四种HTTP请求，对应于CRUD的四种操作；
    
    2.前端页面发送四种HTTP请求，可通过Jquery的ajax方法完成；
    
    3.前后台通过json通信，对于POST和PUT请求，ajax需要提交json字符串作为数据，后台用@RequestBody接收json转为java对象；
    
    4.已配置阿里巴巴的Fastjson工具作为消息转换器，controller返回的对象自动转json字符串到前端；
    
    5.Server端使用SSM框架，需要用mysql建立表格actor持久化数据：
    
      CREATE TABLE `actor` (
        `id` int(11) NOT NULL AUTO_INCREMENT,
        `name` varchar(45) DEFAULT NULL,
        `age` int(11) DEFAULT NULL,
        PRIMARY KEY (`id`)
      ) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8;
      
     6.Server端入口：
     
       http://localhost:8080/Spring-REST-Server/rest
       
  Cilent端:
    
    1.客户端使用Spring的RestTemplate的API发送GET,POST,DELETE,PUT请求，底层是通过HttpClient实现远程调用；
    
    2.注意POST和PUT方法，发送的请求包含了HTTP头设置，否则容易出415的错误；
    
    3.前端页面提交json数据到后台的模板为：
    
     $.ajax({  
            url : "actors",  
            type : "POST/DELETE/PUT",  
            data : JSON.stringify(json), //转JSON字符串  
            dataType: 'json',  
            contentType:'application/json;charset=UTF-8', //contentType很重要
            success : function(result) { });
  

如果是GET请求，直接$.get(...)即可;

4.Client端入口：
      http://localhost:8080/Spring-REST-Client/rest

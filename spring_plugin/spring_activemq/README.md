# spring_activemq

jun_activemq集成了ActiveMQ的Topic及Queue及JMS消费者及生产者的使用订阅，包括docker环境安装等

安装docker，推荐

1、查询activemq
docker search activemq

2、拉取镜像
docker pull docker.io/webcenter/activemq

3、查询本地的镜像
docker images

4、创建activemq容器：61616是activemq的容器使用端口（映射为61617），8161是web页面管理端口（对外映射为8162）.
docker run -d --name activemq_node1 -p 61617:61616 -p 8162:8161 docker.io/webcenter/activemq:latest

5、查看创建的容器：
docker ps

6、页面查看web管理页面：
activemq的web管理界面，http://127.0.0.1:8161/admin
点击manage activemq broker就可以进入管理页面（需要输入账号密码），默认账号密码都是admin

  在业务逻辑的异步处理，系统解耦，分布式通信以及控制高并发的场景下，消息队列有着广泛的应用。
  本项目基于Spring这一平台，整合流行的开源消息队列中间件ActiveMQ,实现一个向ActiveMQ添加和读取消息的功能。
  并比较了两种模式：生产者-消费者模式和发布-订阅模式的区别。
  
包含的特性如下：  
  
1.使用时，将war文件放入tomcat的webapps目录下，启动服务器，开启activeMQ，访问http://localhost:8080/Spring-activeMQ/demo  ，我们可以在页面顶端看到一个黑色的控制台，用于监控消息的内容，如下图：
![输入图片说明](http://git.oschina.net/uploads/images/2016/1116/081248_7013fad4_1110335.jpeg "在这里输入图片标题")

2 在项目中，我们为消息的生产者和发布者分别注册了两个消费者和订阅者，当有消息到达activeMQ时，消费者和订阅者会自动获取对应的消息，可以在前端控制台看到结果（前端页面控制台是基于websocket全双工通信协议实现的，可用于将服务器端的信息主动推送到浏览器，在本项目中不做重点介绍）；

 
3.填入要发送的消息，点击生产消息可以向消息队列添加一条消息，我们可以试着添加了四条消息，并观察控制台结果，可以发现每个消息只被某一个消费者接收； 

 
4.重复以上操作发布四条消息，可以看到订阅者的输出结果，表明每个发布的消息可以被两个订阅者全部接收；
 
   
5.以上结果表明，向队列生产的每条消息，只能被某一个消费者读取，而发布的消息，可以被每个订阅者重复读取，这是两种模式最大的区别，实际应用中要根据情况来选择。

#hystrix-dashborad使用
###官方提供的jar包地址：

https://github.com/kennedyoliveira/standalone-hystrix-dashboard

###启动命令：

java -jar -DserverPort=8080 -DbindAddress=localhost standalone-hystrix-dashboard-1.5.6-all.jar

###打开页面

http://127.0.0.1:8080/hystrix-dashboard/

###添加服务监听

http://127.0.0.1:8081/hystrix.stream

add stream
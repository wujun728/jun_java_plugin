
------MySQL 

1.拉取镜像。

docker pull mysql:8

2.运行镜像，name、端口和密码可自行修改。

docker run -p 3307:3306 --name mysql8 -e MYSQL_ROOT_PASSWORD=mysqladmin -d mysql:8

3.进入容器终端。

docker exec -it mysql8 bash

4.客户端连接mysql。

mysql -uroot -pmysqladmin 
  

------kkfileview
  
docker pull keking/kkfileview
docker run -it -p 8012:8012 keking/kkfileview  

docker run -p 8012:8012 --name kkfileview -d keking/kkfileview  





------manager

ps -ef |grep java

nohup java -jar manager.jar --server.port=8081  > log8081.log &  | tail -f log8081.log &

nohup java -jar manager.jar --server.port=8081  > log8081.log &  
tail -f log8081.log &


nohup java -jar manager.jar --server.port=8082  > log8081.log & 
nohup java -jar manager.jar --server.port=8083  > log8081.log & 
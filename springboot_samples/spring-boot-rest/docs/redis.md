### 安装redis
安装环境 centos 6.8  

##### 1.安装必要包  
	yum install gcc  
	yum -y install wget

##### 2.下载解压
	wget http://download.redis.io/releases/redis-3.2.3.tar.gz  
	tar zxvf redis-3.2.3.tar.gz
	cd redis-3.2.3
##### 3.编译安装
##### 如果不加参数,linux会报错
	make MALLOC=libc
	make install
安装好之后,启动redis

##### 4.启动与测试

###### 启动redis
	src/redis-server &  
	或者  
	redis-server /etc/redis.conf &

设置启动密码  
	vim /etc/redis.conf
	加上  
	requirepass 123456  

###### 关闭redis
	src/redis-cli shutdown

###### 测试redis
	src/redis-cli  
	127.0.0.1:6379> set name cary  
	OK  
	127.0.0.1:6379> get name  
	"cary"
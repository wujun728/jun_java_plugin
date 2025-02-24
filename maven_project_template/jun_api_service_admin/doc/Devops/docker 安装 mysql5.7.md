docker 安装 mysql5.7


1.安装mysql5.7 docker镜像
拉取官方mysql5.7镜像 

docker pull mysql:5.7


查看镜像库

docker images


2.创建mysql容器
在本地创建mysql的映射目录

mkdir -p /root/mysql/data /root/mysql/logs /root/mysql/conf
在/root/mysql/conf中创建 *.cnf 文件(叫什么都行)

touch my.cnf
创建容器,将数据,日志,配置文件映射到本机

docker run -p 3306:3306 --name mysql5.7 -v /root/mysql/conf:/etc/mysql/conf.d -v /root/mysql/logs:/logs -v /root/mysql/data:/var/lib/mysql -e MYSQL_ROOT_PASSWORD=mysqladmin -d mysql:5.7

-d: 后台运行容器

-p 将容器的端口映射到本机的端口

-v 将主机目录挂载到容器的目录

-e 设置参数



启动mysql容器

docker start mysql5.7


 查看/root/mysql/data目录是否有数据文件



使用工具连接测试

 

测试成功
————————————————
版权声明：本文为CSDN博主「疯狂的狮子Li」的原创文章，遵循CC 4.0 BY-SA版权协议，转载请附上原文出处链接及本声明。
原文链接：https://blog.csdn.net/weixin_40461281/article/details/92610876
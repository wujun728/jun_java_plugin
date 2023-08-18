Docker cp 命令
 Docker 命令大全Docker 命令大全

docker cp :用于容器与主机之间的数据拷贝。

语法
docker cp [OPTIONS] CONTAINER:SRC_PATH DEST_PATH|-
docker cp [OPTIONS] SRC_PATH|- CONTAINER:DEST_PATH
OPTIONS说明：

-L :保持源目标中的链接

实例
将主机/www/runoob目录拷贝到容器96f7f14e99ab的/www目录下。

docker cp /www/runoob 96f7f14e99ab:/www/
将主机/www/runoob目录拷贝到容器96f7f14e99ab中，目录重命名为www。

docker cp /www/runoob 96f7f14e99ab:/www
将容器96f7f14e99ab的/www目录拷贝到主机的/tmp目录中。

docker cp  96f7f14e99ab:/www /tmp/
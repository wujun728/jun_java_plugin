# Spring-fastdfs
本项目基于FastDFS的客户端与SpringMVC进行整合实现文件的上传、删除、下载的功能。
- 先去fastdfs官网下载Java客户端https://github.com/happyfish100/fastdfs-client-java
```
mvn clean install
```
- 得到的jar文件导入Spring项目即可使用。
- fastDFS的配置文件放在classpath:conf/fdfs_client.conf下，根据实际进行调整。
- 前端页面访问入口：localhost:8080/Spring-fastdfs/fileupload
- 文件删除接口：localhost:8080/Spring-fastdfs/delete?fileid=xxxxxx
- 若要下载文件，直接根据FastDFS服务器Nginx模块的配置进行访问即可
- FastDFSClient类对原生的API进行了再次封装，使用起来更方便。


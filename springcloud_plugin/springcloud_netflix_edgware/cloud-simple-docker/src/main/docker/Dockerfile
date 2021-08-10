# 基于那个镜像
FROM daocloud.io/java:8
# 将本地文件夹挂载到当前容器（tomcat使用）
VOLUME /tmp
# 拷贝文件到容器
ADD cloud-simple-docker-1.0.0.jar /app.jar
# 打开服务端口
EXPOSE 8080
# 配置容器启动后执行的命令
RUN bash -c 'touch /app.jar'
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
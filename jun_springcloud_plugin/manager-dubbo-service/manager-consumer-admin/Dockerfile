FROM openjdk:8-jdk-alpine
ADD manager-consumer-admin.jar manager-consumer-admin.jar
RUN ln -sf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime
RUN echo 'Asia/Shanghai' >/etc/timezone

ENTRYPOINT ["java","-jar","/manager-consumer-admin.jar"]
EXPOSE 8080

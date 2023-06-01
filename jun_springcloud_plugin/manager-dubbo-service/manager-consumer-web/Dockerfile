FROM openjdk:8-jdk-alpine
ADD manager-consumer-web.jar manager-consumer-web.jar
RUN ln -sf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime
RUN echo 'Asia/Shanghai' >/etc/timezone

ENTRYPOINT ["java","-jar","/manager-consumer-web.jar"]
EXPOSE 8082
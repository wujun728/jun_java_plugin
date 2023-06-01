FROM openjdk:8-jdk-alpine
ADD manager-provider-sys.jar manager-provider-sys.jar
RUN ln -sf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime
RUN echo 'Asia/Shanghai' >/etc/timezone

ENTRYPOINT ["java","-jar","/manager-provider-sys.jar"]
EXPOSE 20880
FROM java:8
VOLUME /tmp

ENV TZ=Asia/Shanghai
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

ADD ./target/demo-0.0.1-SNAPSHOT.jar /app.jar
RUN bash -c 'touch /app.jar'

EXPOSE 8090
ENTRYPOINT ["java", "-jar", "/app.jar", "-Xms", "556m", "-Xmx", "556m"]
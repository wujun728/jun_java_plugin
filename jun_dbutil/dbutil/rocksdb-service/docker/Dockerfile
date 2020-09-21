#FROM supermy/ap-jre
#alpine 不兼容  Error relocating /tmp/librocksdbjni6508399900215018389.so: __strtod_internal: symbol not found
FROM java:8-jre-alpine
#libc6-compat
RUN apk add --update gcc g++ libc6-compat   libstdc++  rocksdb --no-cache  --repository http://nl.alpinelinux.org/alpine/edge/testing
#RUN wget --no-check-certificate "https://circle-artifacts.com/gh/andyshinn/alpine-pkg-glibc/6/artifacts/0/home/ubuntu/alpine-pkg-glibc/packages/x86_64/glibc-2.21-r2.apk" && \
#    apk add --allow-untrusted glibc-2.21-r2.apk
VOLUME /tmp
ADD rocksdb-service-0.1.0.jar app.jar
RUN sh -c 'touch /app.jar'
ENV JAVA_OPTS=""
RUN find /  -name "*.so"
#ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /app.jar" ]
ENTRYPOINT [ "sh", "-c", "java -jar /app.jar" ]
#CMD ["/bin/bash"]
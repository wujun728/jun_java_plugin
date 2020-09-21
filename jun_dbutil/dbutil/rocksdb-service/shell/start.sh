#!/usr/bin/env bash
nohup java -Xmx1024m -Xss1024m -jar ../target/rocksdb-service-0.1.0.jar \
        --spring.config.location=../src/main/resources/application.yml > mydb.log 2>&1 &
tail -f mydb.log



#!/bin/bash
cd manqian-weixin-service/
mvn clean package -P $env.pom.bulid  -DskipTests=true

#!/bin/sh

## 自定义
SERVICE_DIR=/home/roncoo/rc-os/roncoo-jui-springboot/
SERVICE_NAME=roncoo-jui-springboot
SPRING_PROFILES_ACTIVE=prod

## java env
export JAVA_HOME=/opt/jdk1.8
export JRE_HOME=$JAVA_HOME/jre

case "$1" in 
	start)
		procedure=`ps -ef | grep -w "${SERVICE_DIR}/${SERVICE_NAME}" |grep -w "java"| grep -v "grep" | awk '{print $2}'`
		if [ "${procedure}" = "" ];
		then
			echo "start ..."
			if [ "$2" != "" ];
			then
				SPRING_PROFILES_ACTIVE=$2
			fi
			echo "spring.profiles.active=${SPRING_PROFILES_ACTIVE}"
			exec nohup ${JRE_HOME}/bin/java -Xms256m -Xmx256m -jar ${SERVICE_DIR}/${SERVICE_NAME}\.jar --spring.profiles.active=${SPRING_PROFILES_ACTIVE} >/dev/null 2>&1 &
			echo "start success"
		else
			echo "${SERVICE_NAME} is start"
		fi
		;;
		
	stop)
		procedure=`ps -ef | grep -w "${SERVICE_DIR}/${SERVICE_NAME}" |grep -w "java"| grep -v "grep" | awk '{print $2}'`
		if [ "${procedure}" = "" ];
		then
			echo "${SERVICE_NAME} is stop"
		else
			kill ${procedure}
			sleep 1
			argprocedure=`ps -ef | grep -w "${SERVICE_DIR}/${SERVICE_NAME}" |grep -w "java"| grep -v "grep" | awk '{print $2}'`
			if [ "${argprocedure}" = "" ];
			then
				echo "${SERVICE_NAME} stop success"
			else
				kill -9 ${argprocedure}
				echo "${SERVICE_NAME} stop error"
			fi
		fi
		;;
		
	restart)
		$0 stop
		sleep 1
		$0 start $2
		;;  
		
	*)
		$0 stop
		sleep 1
		$0 start $2
		;; 
esac


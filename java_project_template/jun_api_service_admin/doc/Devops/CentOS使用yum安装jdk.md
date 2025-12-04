# [CentOS使用yum安装jdk](https://www.cnblogs.com/zqyanywn/p/10812870.html)

1、查看系统版本命令

```
cat /etc/issue
2、查看yum包含的jdk版本
yum search java 或者 yum list java*
```

　

| 版本 | jre                       | jdk                             |
| ---- | ------------------------- | ------------------------------- |
| 1.8  | java-1.8.0-openjdk.x86_64 | java-1.8.0-openjdk-devel.x86_64 |
| 1.7  | java-1.7.0-openjdk.x86_64 | java-1.7.0-openjdk-devel.x86_64 |
| 1.6  | java-1.6.0-openjdk.x86_64 | java-1.6.0-openjdk-devel.x86_64 |

3、安装jdk
此次选择java-1.8.0-openjdk-devel.x86_64 : OpenJDK Development Environment

```
yum install java-1.8.0-openjdk-devel.x86_64
4、配置全局变量
```

打开配置文件,按insert进入编辑模式

```
vi /etc/profile
```

　　

```
复制以下三行到文件中，按esc退出编辑模式，输入:wq保存退出（这里的JAVA_HOME以自己实际的目录为准）

export JAVA_HOME=/usr/lib/jvm/java-1.8.0-openjdk-1.8.0.312.b07-1.el7_9.x86_64
export CLASSPATH=.:$JAVA_HOME/jre/lib/rt.jar:$JAVA_HOME/lib/dt.jar:$JAVA_HOME/lib/tools.jar
export PATH=$PATH:$JAVA_HOME/bin

```

　　

全局变量立即生效

```
source /etc/profile
5、查看安装jdk是否成功
java -version
```
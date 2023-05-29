# AntResources

#### 项目介绍
读取配置文件的工具，yaml，properties，xml，db，git中读取配置。

[![Maven metadata URI](https://img.shields.io/maven-metadata/v/http/central.maven.org/maven2/cn/antcore/AntResources/maven-metadata.xml.svg)](https://mvnrepository.com/artifact/cn.antcore/AntResources) 
[![Hex.pm](https://img.shields.io/hexpm/l/plug.svg)](https://mvnrepository.com/artifact/cn.antcore/AntResource)
[![jdk](https://img.shields.io/badge/JDK-1.7+-green.svg)](https://mvnrepository.com/artifact/cn.antcore/AntResource)

#### 软件架构
后期补充


#### 安装教程

Maven仓库坐标：
```xml
<dependency>
    <groupId>cn.antcore</groupId>
    <artifactId>AntResources</artifactId>
    <version>${Maven仓库最新版本}</version>
    <scope>compile</scope>
</dependency>
```

#### 使用说明

最新使用方法参见Test.java

```java
PropertiesResources resources = new PropertiesResources();
resources.loadByClassPath("application.properties");
System.err.println(resources.getResources());
```
```java
PropertiesResources resources = new PropertiesResources();
resources.loadByFilePath("D:\\config\\common.properties");
System.err.println(resources.getResources());
```
```java
XmlResources resources = new XmlResources();
resources.loadByClassPath("application.xml");
System.err.println(resources.getResources());
```
```java
XmlResources resources = new XmlResources();
resources.loadByFilePath("D:\\config\\application.xml");
System.err.println(resources.getResources());
```
```java
YamlResources resources = new YamlResources();
resources.loadByClassPath("application.yml");
for (Object key : resources.getResources().keySet()) {
    System.err.println(key + "：" + resources.getResources().get(key));
}
```
```java
DbResources resources = new DbResources();
resources.load();
for (Object key : resources.getResources().keySet()) {
    System.err.println(key + "：" + resources.getResources().get(key));
}
```
```java
DbResources resources = new DbResources();
resources.load("tb_config");
for (Object key : resources.getResources().keySet()) {
    System.err.println(key + "：" + resources.getResources().get(key));
}
```
```java
GitResources resources = new GitResources();
resources.load("client");
for (Object key : resources.getResources().keySet()) {
    System.err.println(key + "：" + resources.getResources().get(key));
}
```
```java
Resources resources = new AutoResources("db:tb_config");
//Resources resources = new AutoResources("classpath:application.yml");
//Resources resources = new AutoResources("file:D:\application.yml");
//Resources resources = new AutoResources("git:test");
System.err.println(resources.getResources());
```
* 默认启动配置文件：application.yml|application.properties|application.xml

* profile，设置配置文件环境
    ```java
    System.setProperty("ant.core.resources.profile", "release");
    ```
    ```yaml
    ant:
      core:
        resources:
          profile: dev
    ```
    以上两种方式均可，建议使用第一种方式

* DbResources，从数据库中读取资源；
    ```yaml
    ant:
      core:
        resources:
          db:
            dataSource: cn.antcore.resources.db.datasource.DefaultDataSource #数据库连接池，使用默认连接池需要导入com.alibaba.druid连接池；如需自定义连接池，请自定义继承AbstractDataSource抽象类，并在此处指定它。
            driveClassName: com.mysql.jdbc.Driver
            url: jdbc:mysql://127.0.0.1:3306/db_config?characterEncoding=utf-8&useSSL=false
            username: root
            password: 123123
            tableName: tb_config
    ```

    > 从指定表读取配置: DbResources.load(var1)，指定一张数据库表名;

    > 从数据库中读取的配置，表中必须包含字段【key,value】两个字段，其它字段不限制；

* GitResources，从Git中读取最新配置；
    ```yaml
    ant:
      core:
        resources:
          git:
            uri: #仓库地址
            username: #仓库访问用户名
            password: #仓库访问密码
            branch: master #仓库分支
            localDir: #仓库保存地址，默认${java.io.tmpdir}地址
    ```
    > 读取指定配置: GitResources.load(var1)，指定一个资源名；
    
* 资源值加密解密配置：
    * 加密
    > 执行cn.antcore.resources.ValueEncrypt类，按提示输入密钥和加密字符串，然后得到一段加密密文
        
        请输入密钥：Hong
        请输入命令：e MyName
        加密成功：7E42A0FE299083AAF3E8BE75D5A17C65
        
    * 如何使用？
    > 先配置GlobalConfig的全局密钥，如下
    ```java
    GlobalConfig.useKey("Hong");
    ```
    > 将加密值配置进属性值中 **（加密属性值前面一定要加一个'E|'开头，用来标识这个是一个加密属性值）**
    ```yml
    my:
        name: E|7E42A0FE299083AAF3E8BE75D5A17C65
    ```

#### 交流群

1. QQ群：499033245

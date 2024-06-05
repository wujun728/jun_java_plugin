
<br/>
<br/>

<div align="center">
    <img src="https://min.io/resources/img/logo.svg" width="30%" style="margin-top:30px;"/>
</div>

<h1 align="center">
    minio-spring-boot-starter
</h1>

<h4 align="center">
    基 于 Minio 对 象 存 储 的 Spring Boot 快 速 启 动 器，开 箱 即 用
</h4> 


<p align="center">
	<a target="_blank" href="https://gitee.com/pear-admin/minio-spring-boot-starter/blob/master/LICENSE">
	    <img src="https://img.shields.io/badge/license-Apache--2.0-blue" />
	</a>
	<a target="_blank">
	    <img src="https://img.shields.io/badge/minio-7.1.0-blue" />
	</a>
	<a target="_blank">
	    <img src="https://img.shields.io/badge/spring--boot-2.3.7.RELEASE-blue" />
	</a>
        <br/>
	<a target="_blank" href="https://www.oracle.com/java/technologies/javase/javase-jdk8-downloads.html">
	    <img src="https://img.shields.io/badge/JDK-8+-green.svg" />
	</a>
	<a target="_blank" href="https://jitpack.io/#com.gitee.pear-admin/minio-spring-boot-starter">
	    <img src="https://jitpack.io/v/com.gitee.pear-admin/minio-spring-boot-starter.svg" />
	</a>
</p>



### 项目介绍

基 于 Minio 对 象 存 储 的 Spring Boot 快 速 启 动 器，开 箱 即 用

<p>
    <a target="_blank" href="https://apidoc.gitee.com/pear-admin/minio-spring-boot-starter"> 
        参考API
    </a>
</p>



### 依赖关系

|  项目名称   |    版本号     | 官网地址                                              |
| :---------: | :-----------: | ----------------------------------------------------- |
|    minio    |     7.1.0     | https://docs.min.io/docs/java-client-quickstart-guide |
| spring-boot | 2.3.7.RELEASE | https://spring.io/projects/spring-boot                |
| hutool-core |     5.7.5     | https://www.hutool.cn                                 |



### 如何使用

Maven

在项目的pom.xml中添加

```xml
    <repositories>
        <repository>
            <id>jitpack.io</id>
            <url>https://jitpack.io</url>
        </repository>
    </repositories>
```

```xml
	<dependency>
	    <groupId>com.gitee.pear-admin</groupId>
	    <artifactId>minio-spring-boot-starter</artifactId>
	    <version>${last.version}</version>
	</dependency>
```


Gradle

```groovy
	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```

```groovy
	dependencies {
	        implementation 'com.gitee.pear-admin:minio-spring-boot-starter:${last.version}'
	}
```

### 快速上手

配置

```
minio:
    ## minio 服务地址
    url: 127.0.0.1:5000
    ## 账户
    accessKey: pear-admin
    ## 密码
    secretKey: pear-admin
    ## 桶
    bucket: default
    ## 当桶不存在，是否创建
    createBucket: true
    ## 启动检测桶，是否存在
    checkBucket: true
    ## 连接超时
    connectTimeout: 6000
    ## 写入超时
    writeTimeout: 2000
    ## 读取超时
    readTimeout: 2000
```

使用

```

@RestController
@RequestMapping("/api/bucket")
public class BucketController {
    
    @Autowired
    private MinioTemplate minioTemplate;
    
    @RequestMapping("/create")
    public Result create(String bucketName) {
        if(!minioTemplate.bucketExists(bucketName)) {
            minioTemplate.createBucket(bucketName);
            return Result.success("创建成功"); 
        }
        return Result.failure("桶，已存在")；
    }   
        
}

```

### 项目贡献者
[![Giteye chart](https://chart.giteye.net/gitee/pear-admin/minio-spring-boot-starter/DDPB5C75.png)](https://giteye.net/chart/DDPB5C75)

### 趋势图
[![Giteye chart](https://chart.giteye.net/gitee/pear-admin/minio-spring-boot-starter/SJRQAG4F.png)](https://giteye.net/chart/SJRQAG4F)
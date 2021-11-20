# Spring Cloud构建微服务架构：分布式配置中心【Dalston版】

**原创**

 [2017-06-28](https://blog.didispace.com/spring-cloud-starter-dalston-3/)

 翟永超

 [Spring Cloud](https://blog.didispace.com/categories/Spring-Cloud/)

[【推荐】从业15年的架构师告诉你：如何落地微服务和云原生架构？](https://blog.didispace.com/how-to-implement-microservice-and-cloud-native-architecture/)

Spring Cloud Config是Spring Cloud团队创建的一个全新项目，用来为分布式系统中的基础设施和微服务应用提供集中化的外部配置支持，它分为服务端与客户端两个部分。其中服务端也称为分布式配置中心，它是一个独立的微服务应用，用来连接配置仓库并为客户端提供获取配置信息、加密/解密信息等访问接口；而客户端则是微服务架构中的各个微服务应用或基础设施，它们通过指定的配置中心来管理应用资源与业务相关的配置内容，并在启动的时候从配置中心获取和加载配置信息。Spring Cloud Config实现了对服务端和客户端中环境变量和属性配置的抽象映射，所以它除了适用于Spring构建的应用程序之外，也可以在任何其他语言运行的应用程序中使用。由于Spring Cloud Config实现的配置中心默认采用Git来存储配置信息，所以使用Spring Cloud Config构建的配置服务器，天然就支持对微服务应用配置信息的版本管理，并且可以通过Git客户端工具来方便的管理和访问配置内容。当然它也提供了对其他存储方式的支持，比如：SVN仓库、本地化文件系统。

在本文中，我们将学习如何构建一个基于Git存储的分布式配置中心，并对客户端进行改造，并让其能够从配置中心获取配置信息并绑定到代码中的整个过程。

## 准备配置仓库

- 准备一个git仓库，可以在码云或Github上创建都可以。比如本文准备的仓库示例：http://git.oschina.net/didispace/config-repo-demo
- 假设我们读取配置中心的应用名为`config-client`，那么我们可以在git仓库中该项目的默认配置文件`config-client.yml`：

```
info:
  profile: default
```

- 为了演示加载不同环境的配置，我们可以在git仓库中再创建一个针对dev环境的配置文件`config-client-dev.yml`：

```
info:
  profile: dev
```

## 构建配置中心

通过Spring Cloud Config来构建一个分布式配置中心非常简单，只需要三步：

- 创建一个基础的Spring Boot工程，命名为：`config-server-git`，并在`pom.xml`中引入下面的依赖（省略了parent和dependencyManagement部分）：

```
<dependencies>
	<dependency>
		<groupId>org.springframework.cloud</groupId>
		<artifactId>spring-cloud-config-server</artifactId>
	</dependency>
</dependencies>
```

- 创建Spring Boot的程序主类，并添加`@EnableConfigServer`注解，开启Spring Cloud Config的服务端功能。

```
@EnableConfigServer
@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		new SpringApplicationBuilder(Application.class).web(true).run(args);
	}

}
```

- 在`application.yml`中添加配置服务的基本信息以及Git仓库的相关信息，例如：

```
spring
  application:
    name: config-server
  cloud:
    config:
      server:
        git:
          uri: http://git.oschina.net/didispace/config-repo-demo/
server:
  port: 1201
```

到这里，使用一个通过Spring Cloud Config实现，并使用Git管理配置内容的分布式配置中心就已经完成了。我们可以将该应用先启动起来，确保没有错误产生，然后再尝试下面的内容。

> 如果我们的Git仓库需要权限访问，那么可以通过配置下面的两个属性来实现；
> spring.cloud.config.server.git.username：访问Git仓库的用户名
> spring.cloud.config.server.git.password：访问Git仓库的用户密码

完成了这些准备工作之后，我们就可以通过浏览器、POSTMAN或CURL等工具直接来访问到我们的配置内容了。访问配置信息的URL与配置文件的映射关系如下：

- /{application}/{profile}[/{label}]
- /{application}-{profile}.yml
- /{label}/{application}-{profile}.yml
- /{application}-{profile}.properties
- /{label}/{application}-{profile}.properties

上面的url会映射`{application}-{profile}.properties`对应的配置文件，其中`{label}`对应Git上不同的分支，默认为master。我们可以尝试构造不同的url来访问不同的配置内容，比如，要访问master分支，config-client应用的dev环境，就可以访问这个url：`http://localhost:1201/config-client/dev/master`，并获得如下返回：

```
{
    "name": "config-client",
    "profiles": [
        "dev"
    ],
    "label": "master",
    "version": null,
    "state": null,
    "propertySources": [
        {
            "name": "http://git.oschina.net/didispace/config-repo-demo/config-client-dev.yml",
            "source": {
                "info.profile": "dev"
            }
        },
        {
            "name": "http://git.oschina.net/didispace/config-repo-demo/config-client.yml",
            "source": {
                "info.profile": "default"
            }
        }
    ]
}
```

我们可以看到该Json中返回了应用名：config-client，环境名：dev，分支名：master，以及default环境和dev环境的配置内容。

## 构建客户端

在完成了上述验证之后，确定配置服务中心已经正常运作，下面我们尝试如何在微服务应用中获取上述的配置信息。

- 创建一个Spring Boot应用，命名为`config-client`，并在`pom.xml`中引入下述依赖：

```
<dependencies>
	<dependency>
		<groupId>org.springframework.boot</groupId>
		<artifactId>spring-boot-starter-web</artifactId>
	</dependency>
	<dependency>
		<groupId>org.springframework.cloud</groupId>
		<artifactId>spring-cloud-starter-config</artifactId>
	</dependency>
</dependencies>
```

- 创建Spring Boot的应用主类，具体如下：

```
@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		new SpringApplicationBuilder(Application.class).web(true).run(args);
	}

}
```

- 创建`bootstrap.yml`配置，来指定获取配置文件的`config-server-git`位置，例如：

```
spring:
  application:
    name: config-client
  cloud:
    config:
      uri: http://localhost:1201/
      profile: default
      label: master

server:
  port: 2001
```

上述配置参数与Git中存储的配置文件中各个部分的对应关系如下：

- spring.application.name：对应配置文件规则中的`{application}`部分
- spring.cloud.config.profile：对应配置文件规则中的`{profile}`部分
- spring.cloud.config.label：对应配置文件规则中的`{label}`部分
- spring.cloud.config.uri：配置中心`config-server`的地址

**这里需要格外注意：上面这些属性必须配置在bootstrap.properties中，这样config-server中的配置信息才能被正确加载。**

在完成了上面你的代码编写之后，读者可以将config-server-git、config-client都启动起来，然后访问http://localhost:2001/info ，我们可以看到该端点将会返回从git仓库中获取的配置信息：

```
{
    "profile": "default"
}
```

另外，我们也可以修改config-client的profile为dev来观察加载配置的变化。

**更多Spring Cloud内容请持续关注我的博客更新或在《Spring Cloud微服务实战》中获取。**

## 代码示例

样例工程将沿用之前在码云和GitHub上创建的SpringCloud-Learning项目，重新做了一下整理。通过不同目录来区分Brixton和Dalston的示例。

- 码云：[点击查看](http://git.oschina.net/didispace/SpringCloud-Learning/tree/master/2-Dalston版教程示例)
- GitHub：[点击查看](https://github.com/dyc87112/SpringCloud-Learning/tree/master/2-Dalston版教程示例)

具体工程说明如下：

- 基于Git仓库的配置中心：config-server-git
- 使用配置中心的客户端：config-client
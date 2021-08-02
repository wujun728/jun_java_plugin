# [Spring Boot：快速入门教程](https://www.cnblogs.com/xifengxiaoma/p/11019240.html)

## 什么是Spring Boot?

Spring Boot是由Pivotal团队提供的全新框架，其设计目的是用来简化新Spring应用的初始搭建以及开发过程。该框架使用了特定的方式来进行配置，从而使开发人员不再需要定义样板化的配置。简而言之，Spring Boot通过提供默认配置的方式整合了所有的框架，让我们可以更加简单、快速、方便地构建应用程序。

## 为什么要用Spring Boot?

Spring Boot包含以下几个特性:

1. 默认提供了大部分框架的使用方式，方便进行快速集成
2. Spring Boot应用可以独立运行，符合微服务的开发理念
3. Spring Boot内置WEB容器，无需部署WAR包即可运行
4. 提供了各种生产就绪型功能，如指标，健康检查和外部配置
5. Spring Boot通过网站提供了项目模板，方便项目的初始化

通过以上这些非常优秀的特性，Spring Boot可以帮助我们非常简单、快速的构建起我们的项目，并能够非常方便进行后续开发、测试和部署。

## 第一个Spring Boot项目

多说无益，实践为上。接下来，我就来建立起我们的第一个Spring Boot项目。

### 生成项目模板

为方便我们初始化项目，Spring Boot给我们提供一个项目模板生成网站。

\1. 打开浏览器，访问：https://start.spring.io/

\2. 根据页面提示，选择构建工具，开发语言，项目信息等。

 ![img](https://img2018.cnblogs.com/blog/616891/201906/616891-20190613195957870-847367543.png)

\3. 点击 Generate the project，生成项目模板，生成之后会将压缩包下载到本地。

\4. 使用IDE导入项目，我这里使用Eclipse，通过导入Maven项目的方式导入。

### 项目结构说明

如下图所示，Spring Boot的项目结构比较简单，只包含三个文件夹。

- src/main/java 放置程序开发代码
- src/main/resources  放置配置文件
- src/test/java 放置测试程序代码

而在其下，包含以下主要文件。

- DemoApplication.java 应用的启动类，包含MAIN方法，是程序的入口
- application.properties 一个空的配置文件，后续可以配置数据源等信息
- DemoApplicationTests.java 一个简单的单元测试类
- pom.xml mave的配置文件，这个应该不用多作介绍了吧

项目结构图

 ![img](https://img2018.cnblogs.com/blog/616891/201906/616891-20190613200041904-342317842.png)

### 添加WEB模块

其实不用添加WEB模块，Spring Boot已经可以启动了。但是为了方便查看，我们引入WEB模块，并添加一个REST接口进行测试。

\1. 引入Maven依赖

在 pom.xml中添加web依赖。

pom.xml

```
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>
```

\2. 编写REST接口

新建一个com.louis.springboot.demo.controller包，并创建一个HelloController。

HelloController.java

[![复制代码](https://common.cnblogs.com/images/copycode.gif)](javascript:void(0);)

```
package com.louis.springboot.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
 
@RestController
public class HelloController {

    @GetMapping("/hello")
    public String hello() {
        return "Hello Spring Boot!";
    }  
}
```

[![复制代码](https://common.cnblogs.com/images/copycode.gif)](javascript:void(0);)

### 项目运行测试

\1. 右键项目 -> Run as -> Maven install，开始执行Maven构建，第一次会下载Maven依赖，可能需要点时间，如果出现如下信息，就说明项目编译打包成功了。

 ![img](https://img2018.cnblogs.com/blog/616891/201906/616891-20190613200419481-1744258835.png)

\2. 右键文件 DemoApplication.java -> Run as -> Java Application，开始启动应用，当出现如下信息的时候，就说明应用启动成功了，默认启动端口是8080。

 ![img](https://img2018.cnblogs.com/blog/616891/201906/616891-20190613200456131-1021376564.png)

\3. 打开浏览器，访问：http://localhost:8080/hello，返回“Hello Spring Boot!”说明我们添加的REST接口已经测试通过了。

![img](https://img2018.cnblogs.com/blog/616891/201906/616891-20190613200534233-1787520821.png)

### 代码单元测试

如果要编写单元测试也比较容易，打开的src/test/下的DemoApplicationTests.java文件，我们编写一个测试hello接口的测试方法，对于http请求的测试，我们可以使用mockmvc来模拟，详情参见具体代码。

DemoApplicationTests.java

[![复制代码](https://common.cnblogs.com/images/copycode.gif)](javascript:void(0);)

```
@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoApplicationTests {

    private MockMvc mvc;

    @Before
    public void setUp() throws Exception {
        mvc = MockMvcBuilders.standaloneSetup(new HelloController()).build();
    }

    @Test
    public void testHello() throws Exception {
       mvc.perform(MockMvcRequestBuilders.get("/hello").accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

}
```

[![复制代码](https://common.cnblogs.com/images/copycode.gif)](javascript:void(0);)

右键DemoApplicationTests.java文件 -> Run as -> Junit Test，运行测试代码，可以从单元测试视图看到单元测试案例运行成功。

 ![img](https://img2018.cnblogs.com/blog/616891/201906/616891-20190613200702242-788204479.png)

### 开发环境调试

如果每次修改代码之后都需要重新启动WEB应用，还是有点麻烦的，Spring Boot支持热启动，修改之后可以实时生效，开发的时候打开还是可以提供一些便利性的。

打开POM文件，在dependencies标签下添加spring-boot-devtools依赖，并修改build标签下的spring-boot-maven-plugin的fork属性为true即可。

[![复制代码](https://common.cnblogs.com/images/copycode.gif)](javascript:void(0);)

```
<dependencies>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-devtools</artifactId>
        <optional>true</optional>
   </dependency>
</dependencies> 

<build>
    <plugins>
         <plugin>
              <groupId>org.springframework.boot</groupId>
              <artifactId>spring-boot-maven-plugin</artifactId>
              <configuration>
                <fork>true</fork>
              </configuration>
          </plugin>
     </plugins>
</build>
```

[![复制代码](https://common.cnblogs.com/images/copycode.gif)](javascript:void(0);)

### 启动端口修改

Spring Boot默认的启动端口是8080，如果需要修改，需要修改配置文件。

打开application.properties文件，在其中添加如下内容，配置启动端口号。

application.properties

```
server.port=8000
```

重新启动应用，查看控制台启动信息，我们发现启动端口已经变成8000了。

 ![img](https://img2018.cnblogs.com/blog/616891/201906/616891-20190613200908754-852185943.png)

Yaml格式文件使目前比较流行的配置文件，我们可以使用Yaml格式配置来代替属性文件配置，将application.properties的文件名修改为application.yml，并将文件内容替换为如下格式内容即可。

application.yml

```
server:
  port: 8000
```

### 启动Banner定制

我们在应用启动的时候，可以看到控制台显示了Spring的Banner信息，我们可以通过定制这个功能，来放置我们自己的应用信息。

 ![img](https://img2018.cnblogs.com/blog/616891/201906/616891-20190613200953912-1985307391.png)

如果要定制自己的Banner, 只需要在 resources 下放置一个 baner.txt 文件，输入自己的banner字符即可。

 ![img](https://img2018.cnblogs.com/blog/616891/201906/616891-20190613201005385-1453228498.png)

Banner字符可以通过类似以下网站生成：

http://patorjk.com/software/taag
http://www.network-science.de/ascii/

![img](https://img2018.cnblogs.com/blog/616891/201906/616891-20190613201015741-559489991.png)

 

生成后复制内容到banner.txt并编辑成想要的样式，完成后重启应用，效果如下。

![img](https://img2018.cnblogs.com/blog/616891/201906/616891-20190613201041768-1953916499.png)

## 胡言乱语

SpringBoot就是行，整啥啥都灵。

配置很容易，运行超简单。

开发难度小，资源也不少。

前途无限好，入坑要趁早。
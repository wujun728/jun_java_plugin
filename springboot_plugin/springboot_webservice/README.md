# SpringBootWebservice


## 1. WebService是什么

准确的来说，webservice 不是一种技术，而是一种规范。是一种跨平台，跨语言的规范，用于不同平台，不同语言开发的应用之间的交互。 举个例子，比如在 Windows Server 服务器上有个 C# .Net 开发的应用 A，在 Linux 上有个 Java 语言开发的应用 B，现在 B 应用要调用 A 应用，或者是互相调用，用于查看对方的业务数据，就可以使用 webservice 的规范。   

再举个例子，天气预报接口。无数的应用需要获取天气预报信息，这些应用可能是各种平台，各种技术实现，而气象局的项目，估计也就一两种，要对外提供天气预报信息，这个时候，如何解决呢？webservice 就是出于以上类似需求而定义出来的规范。   

我们一般是在具体平台开发 webservice 接口，以及调用 webservice 接口，每种开发语言都有自己的 webservice 实现框架。比如 Java 就有 Apache Axis1、Apache Axis2、Codehaus XFire、Apache CXF、Apache Wink、Jboss RESTEasyd 等等。其中 Apache CXF 用的比较多，它也可以和 Spring Boot 整合。

## 2. 项目介绍

本项目是 Spring Boot 和 Webservice 的整合案例，可以直接下载到本地运行，实际项目可以由本案例进行扩展。本项目的相关环境和配置如下：

* JDK版本：1.8
* Spring Boot 版本：1.5.6.RELEASE
* Apache CXF：3.1.12
* Hutool：4.0.9

## 3. 项目运行

* 1、通过git或者svn下载本项目。  
* 2、通过eclipse或者idea打开本项目，下载相关的mvn依赖，配置字符集utf-8。  
* 3、运行项目，打开浏览器输入地址：`http://localhost:9999/services`，可以看到暴露出来的 webservice 服务。  
* 4、点击其中的链接，进入 wsdl 文件，可以看到其中的方法协议。  
* 5、打开项目的 test 目录，找到 com.lli 中的 CxfClient 测试类。我们可以看到其中有两个调用方法，`cl1()` 和 `cl2()`，`cl1()` 方法是静态调用方式调用 webservice 接口，`cl2()` 是动态调用。 


## 4. Spring Boot 和 webservice 的整合过程

### 4.1 Spring Boot cxf 依赖

Spring Boot 和 CXF 整合非常简单，只需要一个依赖即可，如下：
```xml
<dependency>
    <groupId>org.apache.cxf</groupId>
    <artifactId>cxf-spring-boot-starter-jaxws</artifactId>
    <version>3.1.11</version>
</dependency>
```
可以看出，Spring Boot 已经集成了 cxf 所需要的相关依赖，我们不用再分别引入 cxf 对应的依赖了。可以点开 `cxf-spring-boot-starter-jaxws` 看一下：

```xml
<dependency>
    <groupId>org.apache.cxf</groupId>
    <artifactId>cxf-spring-boot-autoconfigure</artifactId>
    <version>${project.version}</version>
</dependency>
<dependency>
    <groupId>org.apache.cxf</groupId>
    <artifactId>cxf-rt-transports-http</artifactId>
    <version>${project.version}</version>
</dependency>
<dependency>
    <groupId>org.apache.cxf</groupId>
    <artifactId>cxf-rt-frontend-jaxws</artifactId>
    <version>${project.version}</version>
</dependency>
```
可以看出，已经包含了 cxf 所需要的相关依赖了。

### 4.2 服务端接口定义

```java
@WebService(name = "CommonService", // 暴露服务名称
        targetNamespace = "http://webservice.lli.com/"// 命名空间,一般是接口的包名倒序
)
public interface CommonService {
    @WebMethod
    String sayHello(@WebParam(name = "param") String param);

    @WebMethod
    String getUser(@WebParam(name = "param") String param);
}
```
创建一个接口，定义两个接口方法，分别用来返回不同的信息，实际中根据具体需求来定义接口方法以及参数。这里有几个注解解释一下：
> `@WebService` 注解：表明该接口是个 WebService 服务，name 属性用来定义该 webservice 服务名称； targetNamespace 属性用来定义命名空间。  
> `@WebMethod` 注解：表明接口中的方法是用来提供具体的服务的。

### 4.3 服务端接口的实现

```java
@WebService(serviceName = "CommonService", // 与接口中指定的name一致
        targetNamespace = "http://webservice.lli.com/", // 与接口中的命名空间一致,一般是接口的包名倒
        endpointInterface = "com.lli.webservice.CommonService"// 接口地址
)
@Component
public class CommonServiceImpl implements CommonService {
@Override
    public String getUser(String param) {
        User user = new User("1", "吹比龙", "18");
        return user.toString();
    }
}
```
其中 `@WebService` 注解中的 `endpointInterface` 属性用来定义服务的地址，这个地址和下面的 cxf 配置中要对应，下面再说明。

### 4.4 cxf 服务配置

```java
@Configuration
public class CxfConfig {
    @Autowired
    private Bus bus;

    @Autowired
    CommonService commonService;

    /** JAX-WS **/
    @Bean
    public Endpoint endpoint2() {
        EndpointImpl endpoint = new EndpointImpl(bus, commonService);
        endpoint.publish("/CommonService");
        return endpoint;
    }
}
```
这里的配置是将上面刚刚定义的 `CommonService` 接口发布到了 `/services/CommonService` 路径下，wsdl 文档路径为 `http://localhost:9999/services/CommonService?wsdl`。

### 4.5 cxf client客户端

这里使用动态调用为例，代理的方式请看源码。
```java
public class CxfClient {
    public static void main(String[] args) throws Exception {
        cl2();
    }
    /**
     * 动态调用方式
     * @throws Exception
     */
    public static void cl2() throws Exception {
        // 创建动态客户端
        JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
        Client client = dcf
                .createClient("http://localhost:9999/services/CommonService?wsdl");
        // getUser 为接口中定义的方法名称 张三为传递的参数 返回一个Object数组
        Object[] objects = client.invoke("getUser", "张三");
        System.out.println(objects[0]);
    }
}
```
注意事项：如果在同一个工程中，上面的 localhost 需要修改成本机的 ip 地址，否则会创建 cxf client 失败。运行一下 client，如果控制台打印出如下信息表示 webservice 服务启动成功：
```
User{userId='1', username='吹比龙', age='18'}
```
至此，Spring Boot 集成 Werbservice 完成。如果你觉得对你有帮助，不妨请作者喝杯咖啡~










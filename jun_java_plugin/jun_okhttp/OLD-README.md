# easy-okhttp

强烈推荐使用最新版，下面是升级到最新版的一些指导。

** 升级指导**

前面我刚刚也说了下，新版和老板不完全兼容，所以升级到新版可能你的项目会报错，可以按照下面列的几个点参考修正。

+ 更新实例名`HttpClient.INSTANCE`为`HttpClient.Instance`
+ 将原方法`HttpClient.formDataPost()`更名为`HttpClient.post()`方法
+ 将原先类`CommonPostRequest`更名为`PostRequest`
+ 将方法名由`formParam`更名为`param`
+ HTTPS证书的方法由`setCertificates`更名为`https`
+ 将`HttpResponse.getRawResponse()`的改为`HttpResponse.custom()`
+ 将`HttpResponse.transferToFile()`的改为`HttpResponse.asFile()`


## 简介

项目`easy-okhttp`是对okhttp网络框架(https://github.com/square/okhttp)上层封装，
支持文件上传和下载，表单(含文件)提交，链式调用，支持HTTPS和自定义签名证书等特性。

`okhttp`网络框架的流行始于Android，但是在Java后端仍然是`Apache HttpClient`网络框架，这个框架的缺点在于设计非常的复杂，而且Jar的比较大。
故此我封装`okhttp`主要目的希望弃用`Apache HttpClient`；其次也是为了帮助`okhttp`的推广^_^

PS:本人Java技术水平中等，还在不断的学习中，所以项目难免存在bug，所以当出现问题的时候，请大家把问题共享出来，我会尽快处理，抑或我们一起讨论也可以的。
共享自己碰到的问题，方便你我他，也使得这个项目就会更加的富有生命力，谢谢！

## 参考与依赖

 + 依赖关系说明
    * `okhttp`，本项目核心所在，底层依赖了`okio`框架，框架的大小在500Kb之内
    * `mzlion-core`，项目依赖的工具类，底层依赖了`slf4j-api`和`gson`两个框架，框架的大小在400Kb之内

## 联系方式

 * Gmail [and.mz.yq@gmail.com](and.mz.yq@gmail.com)
 * QQ群 喜大奔普，QQ群建立啦：<a href="http://shang.qq.com/wpa/qunwpa?idkey=2ae719abbd23107f4ecac0185b31213a1ed5387dd067b40034dd2228093675c1" target="_blank">QQ群加入</a>

## 用法

### 框架引入

项目基于Gradle管理，并且项目要已经上传到JCenter。

MAVEN
```xml
<dependency>
    <groupId>com.mzlion</groupId>
    <artifactId>easy-okhttp</artifactId>
    <version>1.0.4-beta</version>
</dependency>
```

Gradle
```
compile 'com.mzlion:easy-okhttp:1.0.4-beta'
```

下载

[V1.0.4-beta Jar](http://jcenter.bintray.com/com/mzlion/easy-okhttp/1.0.3-beta/easy-okhttp-1.0.4-beta.jar)

### 支持功能说明
 * GET和POST请求
 * 基于POST的大文本数据、二进制文件上传，即通过Http Body提交
 * 普通的表单提交
 * 带有文件表单提交
 * 表单提交支持参数名重复，在后台接收到的是数组或集合
 * 支持session保持
 * 支持链式调用
 * 支持可信任证书和自定义签名证书的https访问
 * 更多特性增加中

### 全局配置（可选）
 * 框架会自动读取classpath下的`easy-okhttp.properties`配置文件，该配置文件包含如下配置信息
    + okhttp.connectionTimeout  连接超时时间，默认设置10秒
    + okhttp.readTimeout 内容读取超时时间，默认是30秒
 * 如果需要更改这些默认配置，有两种方式
    + 覆盖框架提供的配置`easy-okhttp.properties`，框架优先加载项目中的配置文件
    + 通过代码设置全局配置，下面会讲解到
 * 通过代码也可以设置全局参数，该配置操作只需要操作一次，因而可以放在项目启动时配置。
    + 设置连接超时时间    `HttpClient.INSTANCE.setConnectionTimeout(int)`
    + 设置读取超时时间    `HttpClient.INSTANCE.setReadTimeout(int)`
    + 设置自定义签名证书  `HttpClient.INSTANCE.setCertificates(?)`
    + 设置默认Header      `HttpClient.INSTANCE.setDefaultHeader(String,String)`

### 示例

#### 1.普通的GET请求无参数

```java
    String responseData = HttpClient
                .get("http://localhost:8080/okhttp-server-test/userInfo/pageSelect") // 请求方式和请求url
                .execute()
                .asString();
```

#### 2.普通的GET请求带参数

```java
    String responseData = HttpClient
                .get("http://localhost:8080/okhttp-server-test/userInfo/pageSelect")                        // 请求方式和请求url
                .queryString("username","mzlion")   //设置请求参数
                .execute()
                .asString();
```

#### 3.POST普通表单提交

```java
    String responseData = HttpClient
                .post("http://localhost:8080/okhttp-server-test/userInfo/create")                               // 请求方式和请求url
                .formParam("username","mzlion")  // 表单参数
                .formParam("userPwd", "123")     // 表单参数
                //queryString("queryTime","20160530") //url参数
                .execute()
                .asString();
//formParam()重载方法还支持`Map<String,String>`
```

#### 4.POST提交String

```java
    String responseData = HttpClient
                .textBody("http://localhost:8080/okhttp-server-test/userInfo/create")                               // 请求方式和请求url
                .json("{\"username\":\"mzlion\",\"userPwd\":\"123\"}")
                // post提交json
                //.xml("<?xml version=\"1.0\" encoding=\"utf-8\" ?>") 
                //post提交xml
                //.html("function fun(){}")
                //post提交html
                //.charset("utf-8")
                //设置编码
                .execute()
                .asString();
```

#### 5.POST提交二进制文件

```java
    String responseData = HttpClient
                .binaryBody("http://localhost:8080/okhttp-server-test/userInfo/avatar")                               // 请求方式和请求url
                .stream(this.getClass().getClassLoader().getResourceAsStream("andy-bao.jpg"))
                // post提交流
                //.file(new File("d:/andy-bao.jpg")) //post提交文件
                .contentType(ContentType.IMAGE_JPG)
                //设置请求内容类型
                .execute()
                .asString();
//ContentType内置常见的MIME类型，基本上不用自己创建了
```

#### 6.POST表单提交含文件上传

```java
    String responseData = HttpClient
                .formDataPost("http://localhost:8080/okhttp-server-test/userInfo/createWithFile")                               // 请求方式和请求url
                .formParam("userName", "test")
                .formParam("userPwd", "123456")
                .formParam("nickName", "Test")
                .formParam("realName", "测试")
                .formParam("hobby", "测试，就爱测试")
                .formParam("avatarFile", this.getClass().getClassLoader().getResourceAsStream("andy-bao.jpg"), "andy-bao.jpg")
                //.formParam("avatarFile", this.getClass().getClassLoader().getResourceAsStream("andy-bao.jpg"), "andy-bao.jpg")
                .execute()
                .asString();
//formParam()重载方法还支持`Map<String,String>`
```

#### 7.HttpResponse对象介绍

1. `HttpResponse`类是对请求服务端的结果封装，包含了3个属性
    * `isSuccess` 表示请求是否成功
    * `errorMessage` 请求失败时错误消息
    * `rawResponse` 原始的`Response`，当框架提供的功能无法满足时候，可以调用该属性处理。
**当请求执行完成之后一定需要判断请求成功还是失败。**

2. `HttpResponse`类提供对请求服务端的结果转换方法
默认的请求响应结果并不能直接参与编程，一般都需要经过一些转换，所以`HttpResponse`对象提供了几种常见的转换，下面直接通过示例解释各个方法的作用和应用。
**注意：当使用转换方法之前，最好调用方法`isSuccess()`判断请求响应是否成功，否则直接调用转换方法的话，如果响应失败，则会抛出'HttpClientException'异常**

```java
    //将响应结果转为字符串输出
    String responseData = httpResponse.asString();
    
    //将响应结果转为文件保存
    File frc = new File("d:\\web\\save.txt");
    httpResponse.asFile(frc);

    //json转换器
    List<Person> personList = httpResponse.asBean(new TypeToken<List<Person>>(){});
    //重载方法
    //Person person = httpResponse.asBean(Person.class);

    //将响应结果转为输出流中
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    httpResponse.asStream(baos);
```

### 高级配置

#### 为单个请求设置超时

当我们需要对单个请求设置连接超时时间、读取超时时间等属性时，可以在执行`execute`方法之前调用。主要有如下几个方法可以进行调用。
 + `connectionTimeout(int)`   连接超时时间
 + `readTimeout(int)`         读取超时时间
 + `certificates([])`       自定义签名证书设置
以上这些方法的调用会使得框架创建一个新的`OkHttpClient`对象。下面给出一个snippet加以说明。

```java
    //字符串转换器
    String responseData = HttpClient.get()    //设置请求方式
                .url("http://localhost:8080/okhttp-server-test/userInfo/avatar2")   //设置请求地址
                .queryString("id","2")  //设置请求参数
                .readTimeout(30000)     //覆盖设置，读取超时时间
                .execute()            //执行
                .asString();

```

#### 自定义签名网站https访问
 1. 信任指定的自签名证书网站
    自签名网站htts访问处理方式非常简单，首先拿到客户端的签名证书，一般通过浏览器就可以导出xxx.cer证书了。然后将拿到的证书文件拷贝到自己的项目中，然后按照如下方式调用或设置。

    ```java
        //字符串转换器
        String responseData = HttpClient.get()    //设置请求方式
                    .url("http://localhost:8080/okhttp-server-test/userInfo/avatar2")   //设置请求地址
                    .queryString("id","2")  //设置请求参数
                    .readTimeout(30000)     //覆盖设置，读取超时时间
                    .certificates(this.getClass().getClassLoader().getResourceAsStream("SRCA.cer"))
                    .execute()           //执行
                    .asString();

    ```
 2. 信任所有网站
该方法不推荐在生产环境使用，否则https的作用意义就失效了。信任所有网站的https访问设置非常简单，`HttpClient.INSTANCE.setCertificates()`即可。

## 关于demo

目前Demo还没完成，完成之后也会上传，方便大家直接把项目clone下来即可运行。

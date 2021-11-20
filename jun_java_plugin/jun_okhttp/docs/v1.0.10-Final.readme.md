# easy-okhttp
==============

[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.mzlion/easy-okhttp/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.mzlion/easy-okhttp/)[![License](https://img.shields.io/badge/license-Apache%202-4EB1BA.svg)](https://www.apache.org/licenses/LICENSE-2.0.html)

Java网络框架的另一种选择

==============
`easy-okhttp`是Java网络框架，简化网络操作，专注于业务处理

## 文档目录（点击快速导航）

- [新版介绍](#新版介绍)
- [简介](#简介)
- [依赖说明](#依赖说明)
- [框架引入](#框架引入)
- [框架特性说明](#框架特性说明)
- [全局配置(可选)](#全局配置)
- [基本示例](#基本示例)
 + [1.普通的GET请求无参数](#1.普通的GET请求无参数)
 + [2.普通的GET请求带参数](#2.普通的GET请求带参数)
 + [3.POST普通表单提交](#3.POST普通表单提交)
- [上传大文本数据、JSON类型的文本、大文件等](#上传大文本数据、JSON类型的文本、大文件等)
 + [1.POST提交String](#1.POST提交String)
 + [2.POST提交JSON格式的文本](#2.POST提交JSON格式的文本)
 + [3.POST提交XML等其他格式的文本](#3.POST提交XML等其他格式的文本)
 + [4.POST提交二进制文件](4.POST提交二进制文件)
- [表单提交、一参数多值](#表单提交、一参数多值)
 + [1.POST表单提交含文件上传](#1.POST表单提交含文件上传)
 + [2.POST提交支持一个参数设置多个值或替换](#2.POST提交支持一个参数设置多个值或替换)
- [HttpResponse对象介绍](#HttpResponse对象介绍)
- [数据处理器DataHandler<T>](#数据处理器DataHandler<T>)
- [高级配置](#高级配置)
 + [异步请求](#异步请求)
 + [Callback回调接口](#Callback回调接口)
 + [为单个请求设置超时](#为单个请求设置超时)
+ [关于demo](#关于demo) 

** 在GITOSC下不生效 **

## 新版介绍

`easy-okhttp`框架逐渐走向成熟，`1.0.10-Final`是框架的第一个Release包，当然后面还会继续更新。下面简单介绍`1.0.10-Final`的特性。

+ 大部分代码进行了重构;
+ 对`okhttp3`、`gson`,`mzlion-core`依赖包更新至最新版;
+ 简化POST提交的使用(合并了CommonPostRequest和FormDataPostRequest);
+ 对`OkHttpClient`实现全局单例，节省创建`OkHttpClient`耗时;
+ 对HTTPS支持单向认证和双向认证;
+ 增加数据转换接口，统一数据处理;
+ 支持同步请求和异步请求;
+ 对于请求失败的信息更为详细;
+ 代码注释更为详细;

## 简介

`easy-okhttp`是对[okhttp3](https://github.com/square/okhttp)网络框架封装，提供文件上传和下载，表单(含文件)提交，链式调用，支持HTTPS和自定义签名证书等特性。
`okhttp3`网络框架的流行始于`Android`，但是在`Java`后端仍然是`Apache HttpClient`网络框架，这个框架的缺点在于设计非常的复杂，而且jar的比较大。
所以才有`easy-okhttp`项目，主要目的希望弃用`Apache HttpClient`，其次也是为了帮助`okhttp`的推广^_^。

PS:本人Java技术不断成长中，所以项目难免存在bug。当出现问题时，请大家把问题共享出来，我会尽快处理，抑或我们一起讨论也可以的。
共享自己碰到的问题，方便你我他，也使得这个项目就会更加的富有生命力，谢谢！
当然也希望大家star和fork，同时非常欢迎大家参与项目的开发。

## 依赖说明

* `okhttp`，本项目核心所在，底层依赖了`okio`框架，框架的大小在500Kb之内
* `mzlion-core`，项目依赖的工具类，底层依赖了`slf4j-api`和`gson`两个框架，框架的大小在400Kb之内

也就是说`easy-okhttp`整个包的大小在1M左右，相对于`Apache HttpClient`体积非常的小，而且使用也更为方便。

## 联系方式

* 邮箱地址     [mzllon@qq.com](mailto:mzllon@qq.com)
* QQ群        喜大奔普，QQ群建立啦：<a href="http://shang.qq.com/wpa/qunwpa?idkey=2ae719abbd23107f4ecac0185b31213a1ed5387dd067b40034dd2228093675c1" target="_blank">QQ群加入</a>
* 建议使用QQ群，邮箱使用较少，可能看的不及时
* 本QQ群的目前范围不仅仅讨论该项目，同时也支持Java、Intellij IDEA等问题交流，欢迎大家入群。
 
## 框架引入

maven

```xml
<dependency>
    <groupId>com.mzlion</groupId>
    <artifactId>easy-okhttp</artifactId>
    <version>1.0.10-Final</version>
</dependency>
```

Gradle

```
compile 'com.mzlion:easy-okhttp:1.0.10-Final'
```

下载jar


## 框架特性说明

* 支持GET和POST请求
* 基于POST的大文本数据、二进制文件上传，即通过Http Body提交
* 普通的表单提交
* 带有文件表单提交
* 表单提交支持参数名重复，即在后台接收到的是数组或集合
* 支持session保持
* 支持链式调用
* 支持可信任证书和自定义签名证书的https访问
* 注释详细，并且提供了demo
* 增加异步请求的支持
* 提供数据处理器，轻松将数据转为自己想要的结果
* 更多特性增加中

## 全局配置

框架会自动读取classpath下的`easy-okhttp.properties`配置文件，如果没有特别的要求话，一般不需要重载该配置文件。

 * `easy-okhttp.properties`配置文件的配置信息
    + connectTimeout  连接超时时间，默认设置10秒
    + readTimeout 内容读取超时时间，默认是30秒
    + writeTimeout  内容写入超时时间，默认30秒
    + 支持设置公共header，规则如下
        以header开头的表示设置公共请求头,header.之后就是键名,等号之后的键对应的值
        \#header.partner = MZ2017 #则表示往header塞一对键值 partner=MZ2017
 * 如果需要更改这些默认配置，有两种方式
    + 覆盖框架提供的配置`easy-okhttp.properties`，框架优先加载项目中的配置文件，超时时间的单位都是毫秒
    + 通过代码设置全局配置
 * 通过代码也可以设置全局参数，该配置操作只需要操作一次，因而可以放在项目启动时配置。
    + 设置连接超时时间    `HttpClient.Instance.setConnectTimeout(int)`
    + 设置读取超时时间    `HttpClient.Instance.setReadTimeout(int)`
    + 设置写入超时时间    `HttpClient.Instance.writeTimeout(int)`
    + 设置自定义签名证书  `HttpClient.Instance.https(?)`
    + 设置默认Header      `HttpClient.Instance.setDefaultHeader(?)`
* SSL证书配置
    2017年马上就要到来，SSL在2017使用率更加高，所以这边跟紧步骤，增加了HTTPS单向认证和双向认证。HTTPS全局配置需要通过代码设置。
    + 网站启用了HTTPS，但SSL由信任的Root CA发布的，那么框架自动信信任，不需要你做任何配置
    + 信任任何一个网站，这个时候HTTPS形同虚设，客户端根本不鸟这些  `HttpClient.Instance.https()`
    + 假如使用的自签证书（经典的12306）或系统不能自动信任的SSL证书（Let's Encrypt） `HttpClient.Instance.https(HttpClient.class.getClassLoader().getResourceAsStream("mzlion_com.cer"))`
    + 最严格就是双向认证     `HttpClient.Instance.https(InputStream pfxStream, char[] pfxPwd, InputStream... certificates)`

## 基本示例

### 1.普通的GET请求无参数

```java
    String responseData = HttpClient
                // 请求方式和请求url
                .get("http://localhost:8080/user-sys/user/list") 
                .execute()
                .asString();
```

### 2.普通的GET请求带参数

```java
    String responseData = HttpClient
                // 请求方式和请求url
                .get("http://localhost:8080/user-sys/user/list")
                //设置请求参数
                .queryString("mobile","18018110018")   
                .execute()
                .asString();
```

### 3.POST普通表单提交

```java
    String responseData = HttpClient
                // 请求方式和请求url
                .post("http://localhost:8080/user-sys/user/add")                               
                // 表单参数
                .param("name","张三")  
                .param("mobile", "13023614020")
                .param("langs", "Java")
                .param("langs", "Python")
                //url参数
                //queryString("queryTime","20160530") 
                .execute()
                .asString();
```

## 上传大文本数据、JSON类型的文本、大文件等

一般针对POST提交数据内容较为复杂、接口约定需要POST上传时调用本方法非常有效。这种提交方式也是POST，但是数据内容和格式直接写入请求流中。比如微信接口就是这种模式。

### 1.POST提交String

```java
    String responseData = HttpClient
                // 请求方式和请求url
                .textBody("http://localhost:8080/user-sys/user/body1")                               
                .text("设施一串和服务端约定好的数据格式")
                //设置编码
                //.charset("utf-8")
                .execute()
                .asString();
```

### 2.POST提交JSON格式的文本

```java
    String responseData = HttpClient
                // 请求方式和请求url
                .textBody("http://localhost:8080/user-sys/user/import")                               
                // post提交json
                .json("[{\"name\": \"test-13\",\"mobile\": \"18321001200\",\"programLangs\": \"Java,Pyhton\",\"remark\": \"0\"}]")
                //post提交xml
                //.xml("<?xml version=\"1.0\" encoding=\"utf-8\" ?>") 
                //post提交html
                //.html("function fun(){}")
                //.charset("utf-8")
                //设置编码
                .execute()
                .asString();
```

### 3.POST提交XML等其他格式的文本

```java
    String responseData = HttpClient
                // 请求方式和请求url
                .textBody("http://localhost:8080/user-sys/user/body2")                               
                //post提交xml
                .xml("<?xml version=\"1.0\" encoding=\"utf-8\" ?>") 
                //post提交html
                //.html("function fun(){}")
                //post提交一段javascript
                //.javascript("function fn(){}")
                //设置编码
                //.charset("utf-8")
                .execute()
                .asString();
```

### 4.POST提交二进制文件

```java
    String responseData = HttpClient
                // 请求方式和请求url
                .binaryBody("http://localhost:8080/user-sys/user/body3")                               
                // post提交流
                .stream(this.getClass().getClassLoader().getResourceAsStream("avatar.png"))
                //设置请求内容类型
                .contentType(ContentType.IMAGE_JPG)
                //post提交文件
                //.file(new File("d:/avatar.png")) 
                .execute()
                .asString();
//ContentType内置常见的MIME类型，基本上不用自己创建
```

## 表单提交、一参数多值

### 1.POST表单提交含文件上传

```java
    String responseData = HttpClient
                // 请求方式和请求url
                .post("http://localhost:8080/user-sys/user/add")                               
                .param("name", "李四")
                .param("mobile", "13023614021")
                .param("avatarFile", this.getClassLoader().getResourceAsStream("avatar.png"), "avatar.png")
                //.param("avatarFile", new File("D:/avatar.png")
                .execute()
                .asString();
```

### 2.POST提交支持一个参数设置多个值或替换

```java
    String responseData = HttpClient
                // 请求方式和请求url
                .post("http://localhost:8080/user-sys/user/add")                               
                // 表单参数
                .param("name","张三")  
                .param("mobile", "13023614020")
                .param("langs", "Java")
                .param("langs", "Python")//会多种语言
                .execute()
                .asString();
```

## HttpResponse对象介绍

以上展示的代码都是基于同步请求的，并且你会发现示例代码中总是出现`asString()`方法，这个方法来自`HttpResponse`类，该类是对服务端相应数据的封装，
下面就详细介绍下该类的功能特色。

1. `HttpResponse.isSuccess()`表示请求是否成功，只有请求成功才会有后面的功能。
2. `HttpResponse.getErrorMessage` 请求失败时错误消息。
3. 当请求执行完成之后推荐调用`HttpResponse.isSuccess()`判断请求成功还是失败，如果没有进行判断且请求失败则会抛出异常`HttpStatusCodeException`。

**下面就是非常好用的方法**

1. `public String asString()` 将响应结果直接转为字符串
2. `public <E> E asBean(Class<E> targetClass)` 将响应结果转为JavaBean
3. `public <E> E asBean(TypeRef<E> typeRef)` 也是将响应结果转为JavaBean，和上面的区别在于该方法能够提取到泛型信息
4. `public byte[] asByteData()` 将响应结果转为二进制内容，这是数据在网络请求的原始数据
5. `public void asFile(File saveFile)` 将响应结果转为文件存储，当远程是文件时该方法非常有用
6. `public void asStream(OutputStream out)` 直接将响应结果输出到另外一个流中
7. `public <T> T custom(DataHandler<T> dataHandler)` 当以上这些方法都满足不了你的话，这个方法可以自己DIY，随你怎么蹂躏

下面给出一些简单的代码，这些方法已经非常简单了。

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

## HTTPS

前面已经提到HTTPS的配置，下面给出一些简单的示例更直观了解用法。

```java
    //由信任CA机构发布的，比如GitHub的https，框架不需要你做什么事情，正常使用即可
    String githubContent = HttpClient
        .get("https://www.github.com")
        .execute()
        .asString();

    //不管三七二十几，直接信任了所有请求
    String mzlionIndexContent = HttpClient
        .get("https://kyfw.12306.cn/otn/")
        .https()
        .execute()
        .asString();
    
    //自签SSL或程序不认可实际安全的，可以指定客户端证书
    String mzlionIndexContent = HttpClient
        .get("https://kyfw.12306.cn/otn/")
        .https(this.getClass().getClassLoader().getResourceAsStream("SRCA.cer"))
        .execute()
        .asString();
    
```

## 数据处理器DataHandler<T>

这是新版本的新增的一个功能，数据处理器定义接口返回数据处理能力，该接口就只有一个函数
```java
    T handle(final okhttp3.Response response) throws IOException;
```
该函数提供了将原始数据格式转为业务所需数据格式，框架提供了常用接口处理器实现，若以下的处理器无法满足需求可以自己实现一个处理器。

* `StringDataHandler` 字符串处理器，该处理器比较常用，可以直接调用`StringDataHandler.create()`得到处理器的实例
* `JsonDataHandler<T>` JSON处理器，该处理器也比较常用，即将JSON字符串转为Javabean
* `FileDataHandler`     文件处理器，将结果存储到文件中，该处理器有两个构造参数，其中`dirPath`保存目录必填，`filename`保存的文件名可选，如果为空时框架自动从header、url中获取，如果获取不到则随机生成一个文件名。

## 高级配置

### 异步请求

异步请求不会阻塞当前线程（特别是网络慢的时候），适用于对返回结果不关心或不需要立即知晓的情况下，比如推送、通知等。
异步请求只有在执行网络请求的时候有一点区别，其他地方和同步请求配置和操作都是一样的。

```java
    String githubContent = HttpClient
            .get("https://www.github.com")
            .execute(new CallbackAdaptor<String>(){
                
                @Override
                public DataHandler<T> getDataHandler() {
                    return StringDataHandler.create();
                }
            
                @Override
                public void onSuccess(T data) {
                    //data就是经过处理后的数据，直接在这里写自己的业务逻辑
                }
            });
```

### Callback回调接口

`Callback`是回调定义接口，里面总共定义了6个函数，每个函数被调用的顺序不一样。

* `onBefore()` 第一被调用，主要在请求网络之前，这个函数有返回值，如果返回`false`则阻止此次请求了；
* `postProgress()`  第二被调用，上传进度回调函数
* `onError()` 第三被调用，当只有请求失败时才会触发；
* `onComplete()` 第四被调用，当请求接口完成后触发该函数；
* `onSuccess()` 第五被调用，当请求接口成功（HTTP状态码为200）则会触发该函数，
该函数会依赖另外一个函数`getDataHandler()`，返回一个指定的数据处理器，处理原始数据。对于数据处理器前面已经了解过了。

异步回调接口`Callback`总共定义了6个函数，但是一般不会关心所有函数处理情况，所以提供了`CallbackAdaptor`空实现类，想要关注哪个函数的执行结果，重载那个函数即可。

### 为单个请求设置超时

当我们需要对单个请求设置连接超时时间、读取超时时间等属性时，可以在执行`execute`方法之前调用。主要有如下几个方法可以进行调用。

+ `connectTimeout(int)`    连接超时时间
+ `readTimeout(int)`       读取超时时间
+ `writeTimeout(int)`      写入超时时间
+ `https()`                设置https证书

一般一个项目只请求一个远程服务，所以这些配置可以在全局配置，共享一个`OkHttpClient`对象(相当于共享一个浏览器)，以上这些方法的调用会使得框架创建一个新的`OkHttpClient`对象(相当于又打开了一个浏览器)。下面给出一个snippet加以说明。

```java
    //字符串转换器
    String responseData = HttpClient
                //设置请求方式
                .get()    
                //设置请求地址
                .url("http://localhost:8080/user-sys/user/find_by_mobile")   
                //设置请求参数
                .queryString("mobile","18118181234")  
                //覆盖设置，读取超时时间
                .readTimeout(30000)     
                //执行
                .execute()            
                .asString();

```

## 关于demo

目前Demo已完成，使用SpringBoot开发，数据库用的是H2。目前仅仅实现了用户的增删改查功能，可以用来学习SpringBoot和easy-okhttp。
项目地址：[easy-okhttp-test-server](https://git.oschina.net/mzllon/easy-okhttp-test-server)

[老版本V1.0.4的文档在这里](./OLD-README.md)

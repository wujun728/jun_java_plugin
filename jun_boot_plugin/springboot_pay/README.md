# spring-boot-pay
支付服务：支付宝，微信，银联详细 **代码案例** (除银联支付可以测试以外，支付宝和微信支付测试均需要企业认证，个人无法完成测试)，项目启动前请仔细阅读  **[注意事项](https://git.oschina.net/52itstyle/spring-boot-pay#注意事项)** :fa-hand-o-left:   。

Spring-Boot2.0 + Nacos注册中心版：https://gitee.com/52itstyle/spring-boot-pay/tree/spring-boot-nacos-pay

 **墙裂推荐，一个能够让程序猿快速开发的极简工具箱：https://gitee.com/52itstyle/spring-boot-tools** 

## 你问我答

1）为什么会有这个一个项目？

因为平台有多个项目，每个项目都有支付模块，所以就单独出来了一个服务，这样就可以复用呗。

2）服务通过什么方式调用？

当然是 RPC 了，通过注册中心调用服务，技术栈 Zookeeper + Dubbo，这两个玩意都可以做集群。

3）使用 RPC 有什么好处？

一是安全啊，我们项目部署在私有云，注册中心一般不会对外开放，那就不存在 HTTP 接口所谓的鉴权了；
二是高效啊，毕竟 RPC 是基于四层协议的，相对来说的确会高那么一点点，这个大家可以自行测试，但是我觉得对于大部分公司，这个不重要。

4）这个项目可以拿来即用吗？

当然可以，只要只配置好相关参数，把接口类打个包，扔给消费者就是了，当然了，一些业务逻辑还是需要自己去实现的。

5）如何保证高可用？

那就部署多个服务，Dubbo 默认负载均衡策略是轮询，你也可以配置成其他策略，比如根据机器配置设置加权之类的。Zookeeper 也可以啊，保证 2N+1 台就是了。


## 欢迎关注

一个有温度的微信公众号，期待与你共同进步，分享美文，分享各种Java学习资源

![输入图片说明](https://images.gitee.com/uploads/images/2018/0809/181043_76e4d5b8_87650.png "1234.png")


以下所有支付Demo，测试通过，真实有效。

### 支付宝
扫码支付、电脑支付、WAP支付、APP支付服务端
### 微信
扫码支付(模式一二)、公众号H5支付、WAP支付
### 银联
电脑支付、WAP支付


[SpringMvc-Dubbox-pay版本(废弃不再维护)](https://git.oschina.net/52itstyle/springMvc-dubbo-pay)

## 开发环境

JDK1.8、Maven、IDEA、SpringBoot2.2.6、Dubbo2.7.3、zookeeper3.5.3

## 启动说明

- 配置Dubbo需要安装注册中心zookeeper(不过撸主已经在配置文件中为大家准备了公益注册中心): http://www.52itstyle.top/thread-19791-1-1.html

- 基础配置初始化类：com.itstyle.common.cinfig.InitPay

- 最后想测试相关支付效果，请自行配置支付宝、微信以及银联相关账号以及证书

- 启动并访问项目：http://localhost:8080/spring-boot-pay

- 此案例只是实现了部分功能，其它功能大家按需根据自己的业务逻辑自行实现，最重要的下单和回调已经实现

## 友情提示

由于工作原因，项目正在完善中（仅供参考），随时更新日志，有疑问请留言或者加群

- JAVA爱好者:<a target="_blank" href="//shang.qq.com/wpa/qunwpa?idkey=eba7a5d6f672c67cf942e08486e5102f0a0a6268206f873fef48a9d74f248de8"><img border="0" src="//pub.idqqimg.com/wpa/images/group.png" alt="JAVA爱好者" title="JAVA爱好者"></a>


## 支付文档

地址：http://localhost:8080/spring-boot-pay/swagger-ui.html

配置说明：https://blog.52itstyle.vip/archives/1473/

![支付文档](https://git.oschina.net/uploads/images/2017/0828/172331_6537f916_87650.png "zhifuAPI.png")

## 演示界面

部分功能完善中！！！

![模拟登陆](https://git.oschina.net/uploads/images/2017/0802/191105_d59172ca_87650.png "0.png")

![模拟首页](https://git.oschina.net/uploads/images/2017/0802/191116_04d62422_87650.png "1.png")

![模拟支付](https://git.oschina.net/uploads/images/2017/0802/191125_6958b9b3_87650.png "2.png")

## 支付宝

签约功能列表：

![输入图片说明](https://images.gitee.com/uploads/images/2018/1022/174516_1f8bc13b_87650.png "二维码支付.png")

- 电脑支付：https://docs.open.alipay.com/270
- 扫码支付：https://docs.open.alipay.com/194
- 手机支付：https://docs.open.alipay.com/203
- APP支付 : https://docs.open.alipay.com/54/106370/
- 沙箱环境：https://docs.open.alipay.com/200/105311/
- 支付宝公钥参数：https://openclub.alipay.com/read.php?tid=2190&fid=69
- RSA(SHA1)升级为RSA(SHA256)：https://opensupport.alipay.com/support/knowledge/20069/201602242782
- 参数zfbinfo.properties

```
支付宝网关名、partnerId和appId
open_api_domain = https://openapi.alipay.com/gateway.do
mcloud_api_domain = http://mcloudmonitor.com/gateway.do
此处请填写你的PID
pid =XXXXXXXXXXXXXX
此处请填写你当面付的APPID 
appid =XXXXXXXXXXXXXX

RSA私钥、公钥和支付宝公钥
private_key = XXXXXXXXXXXXXX
public_key = XXXXXXXXXXXXXX
alipay_public_key = XXXXXXXXXXXXXX

当面付最大查询次数和查询间隔（毫秒）
max_query_retry = 5
query_duration = 5000

当面付最大撤销次数和撤销间隔（毫秒）
max_cancel_retry = 3
cancel_duration = 2000

交易保障线程第一次调度延迟和调度间隔（秒）
heartbeat_delay = 5
heartbeat_duration = 900

```

支付宝的SDK-alipay-sdk-java这里下载： https://docs.open.alipay.com/54/103419/

大家比较好奇的alipay-trade-sdk从这里下载的TradePayDemo项目中的额lib下面，不过是16年的，目前来说还是可以使用的： https://docs.open.alipay.com/54/104506/

## 微信

- H5支付：https://pay.weixin.qq.com/wiki/doc/api/H5.php?chapter=15_1
- 公众号支付：https://pay.weixin.qq.com/wiki/doc/api/jsapi.php?chapter=7_1
- 扫码支付模式一：https://pay.weixin.qq.com/wiki/doc/api/native.php?chapter=6_4
- 扫码支付模式二：https://pay.weixin.qq.com/wiki/doc/api/native.php?chapter=6_5
- 微信退款说明：https://pay.weixin.qq.com/wiki/doc/api/native.php?chapter=4_3
- 网络设置指引：https://pay.weixin.qq.com/wiki/doc/api/native.php?chapter=23_2
- HTTPS服务器配置:https://pay.weixin.qq.com/wiki/doc/api/wxa/wxa_api.php?chapter=10_4
- 参数wxinfo.properties
- 微信网页授权部分，向微信申请测试号：http://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421137522

```
服务号的应用ID
APP_ID = XXXXXXXXXXXXXX
服务号的应用密钥
APP_SECRET = XXXXXXXXXXXXXX
服务号的配置token
TOKEN = XXXXXXXXXXXXXX
商户号
MCH_ID = XXXXXXXXXXXXXX
API密钥
API_KEY = XXXXXXXXXXXXXX
签名加密方式
SIGN_TYPE = MD5
微信支付证书名称
CERT_PATH = apiclient_cert.p12
```

## 银联
- 开放平台：https://open.unionpay.com/
- 商家中心：https://merchant.unionpay.com/join/
- 测试账号：https://blog.52itstyle.vip/archives/326/
- 证书问题(QA)：https://open.unionpay.com/ajweb/help/faq/list?id=174&level=0&from=0

## 注意事项
- 除银联支付可以测试以外，支付宝和微信支付测试均需要企业认证，个人无法完成测试
- 项目中的支付宝SDk需要自行去官网下载打入本地仓库或者私服，提供下载地址：http://pan.baidu.com/s/1mi5LfhI
- 微信退款证书，微信商户平台(pay.weixin.qq.com)-->账户中心-->账户设置-->API安全-->证书下载，使用apiclient_cert.p12即可
- 支付宝支付相关参数zfbinfo.properties，需要自行去阅读支付宝文档自行生成
- 微信支付相关参数wxinfo.properties，需要自行去阅读微信支付文档自行生成
- 公众平台微信支付公众号支付授权目录、扫码支付回调URL配置入口已于8月1日迁移至商户平台（pay.weixin.qq.com）。迁移后，原有配置数据不会受影
响，你可在商户平台查看和配置。带来的不便敬请谅解。
- 2018年1月8日更新：公众号开发信息、微信H5支付获取access_token接口时，必须设置IP白名单。

![支付模式一回调](https://git.oschina.net/uploads/images/2017/0803/184711_75c8374c_87650.png "模式一支付.png")
- 微信或者支付宝下单调用网关失败，请检查网络 ping api.mch.weixin.qq.com -c 100 或者 ping openapi.alipay.com/gateway.do -c 100
- 支付宝中的初始化配置Configs 不要随便变更，支付相关JAR调用的是Configs中的配置
- 由于项目配置了SSL，访问地址： https://ip:port/springboot_pay/ 见：[SpringBoot开发案例之集成SSL证书](https://blog.52itstyle.com/archives/1403/)
- 2018/01/26 以后新建应用只支持RSA2签名方式，目前已使用RSA签名方式的应用仍然可以正常调用接口，注意下自己生成密钥的签名算法，见AliPayController类。

## 功能日志
- 支付宝生成支付二维码Demo已经测试完成
- 支付宝手机端H5支付Demo已经测试完成
- 支付宝电脑支付Demo已经测试完成

- 微信二维码支付模式二Demo测试完成
- 微信公众号支付(需要添加认证网址)


- 银联支付电脑支付Demo测试完成
- 银联支付H5支付Demo测试完成

- 微信二维码支付模式一Demo测试完成
- 集成Dubbo服务，全注解提供RPC服务
- 集成logback日志组间
- 集成HTTPS证书安全服务 
- 集成微信H5(WAP)支付

## 升级说明

#### 2018-10-10 更新说明：

- 原当当 Dubbox 2.8.4 替换为 Dubbo 2.6.2
- 原spring-context-dubbo.xml 配置 替换为 dubbo-spring-boot-starter 2.0.0
- 原 zkclient 0.6 替换为 curator-recipes 4.0.1
- 原 zookeeper 3.4.6 升级为 zookeeper 3.5.3

#### 2018-10-17 更新说明：

```
{"alipay_trade_precreate_response":{"code":"40003","msg":"Insufficient Conditions","sub_code":"isv.missing-signature-config","sub_msg":"应用未配置对应签名算法的公钥或者证书"}}
```

二维码支付报错：应用未配置对应签名算法的公钥或者证书。记得17年申请的时候貌似不需要门店，如果是18年申请二维码支付需要门店并申请签约才可以使用。

#### 2018-10-24 更新说明：

- [支付宝支付密钥RSA1升级到RSA2](http://https://blog.52itstyle.com/archives/3453/)

- [微信支付SDK漏洞xxe漏洞修复。](https://gitee.com/52itstyle/spring-boot-pay/blob/master/src/main/java/com/itstyle/modules/weixinpay/util/XMLUtil.java)

#### 2018-11-19 更新说明：

- [升级阿里官方SDk](https://mvnrepository.com/artifact/com.alipay.sdk/alipay-sdk-java)

- 支付密钥sign_type升级为RSA2注意事项：

1）当面付(扫码支付)

pom.xml中下载最新的alipay-trade-sdk，并在配置zfbinfo.properties参数中增加以下参数：

```
# 签名类型: RSA->SHA1withRsa,RSA2->SHA256withRsa
sign_type = RSA2
```

2）电脑支付或者手机支付

需要在创建AlipayClient传入RSA2即可：

```
AlipayClient alipayClient = new DefaultAlipayClient(
                                               Configs.getOpenApiDomain(), Configs.getAppid(),
                                               Configs.getPrivateKey(), "json", "UTF-8",
                                               Configs.getAlipayPublicKey(),"RSA2");
```

#### 2020-05-08 更新说明：

- SpringBoot 1.5.10 升级为2.2.6
- Dubbo 2.6.2 升级为 2.7.3
- dubbo-spring-boot-starter 2.0.0 升级为 2.7.3


## 推荐算法

[第一篇、MD5算法和SHA-1算法](https://www.jianshu.com/p/38c93c677124)

[第二篇：对称加密及AES加密算法](https://www.jianshu.com/p/3840b344b27c)

[第三篇：从支付宝SDK的支付流程理解什么是公钥和私钥，什么是加密和数字签名](https://www.jianshu.com/p/6f5f0695d8fa)

[第四篇：非对称加密及RSA加密算法](https://www.jianshu.com/p/9785bd492a6e)

## 推荐阅读


[开发银联支付之前要做的那些事儿](https://blog.52itstyle.vip/archives/3060/)

[开发微信支付之前要做的那些事儿](https://blog.52itstyle.vip/archives/3053/)

[内网穿透神器：Ngrok在支付中的正确使用姿势](https://blog.52itstyle.vip/archives/2972/)

[那些年支付宝微信银联支付遇到的坑](https://blog.52itstyle.vip/archives/1364/)

[微信扫码支付模式以及使用场景 ](http://https://blog.52itstyle.vip/archives/1367/)

[JAVAWEB如何集成银联网关支付(模拟环境测试)](https://blog.52itstyle.vip/archives/331/)

[2017年最新javaweb整合银联在线支付DEMO](https://blog.52itstyle.vip/archives/326/)

[微信支付linux下java.net.UnknownHostException: api.mch.weixin.qq.com](https://blog.52itstyle.vip/archives/162/)

[JAVA实现微信退款报错unexpected end of file from server](https://blog.52itstyle.vip/archives/159/)

[支付宝扫码支付和微信扫码支付业务场景及问题记录](https://blog.52itstyle.vip/archives/263/)

[微信扫码支付(模式一)遇到的那些坑](https://blog.52itstyle.vip/archives/1372/)

[微信公众号H5支付遇到的那些坑 ](https://blog.52itstyle.vip/archives/1440/) 

[阿里云HTTPS证书服务](https://blog.52itstyle.vip/archives/969/)

[SpringBoot开发案例之整合Swagger篇](https://blog.52itstyle.vip/archives/1473/)

[SpringBoot开发案例之整合Dubbo分布式服务](https://blog.52itstyle.vip/archives/3411/)



## 推荐

秒杀案例：https://gitee.com/52itstyle/spring-boot-seckill

任务调度：https://gitee.com/52itstyle/spring-boot-quartz

邮件服务：https://gitee.com/52itstyle/spring-boot-mail

搜索服务：https://gitee.com/52itstyle/spring-boot-elasticsearch

私人网盘：https://gitee.com/52itstyle/spring-boot-CloudDisk


作者： 小柒2012

欢迎关注： https://blog.52itstyle.vip

## 云服务推荐

[![输入图片说明](https://images.gitee.com/uploads/images/2019/0718/130816_45e06f95_87650.png "屏幕截图.png")](https://promotion.aliyun.com/ntms/yunparter/invite.html?userCode=kutpfdo3)

## 小程序推荐

这可能是东半球最好的妹子图小程序，点她、点她、点她！！！

![输入图片说明](https://images.gitee.com/uploads/images/2020/0224/192652_8e043ef1_87650.jpeg "meizi.jpg")


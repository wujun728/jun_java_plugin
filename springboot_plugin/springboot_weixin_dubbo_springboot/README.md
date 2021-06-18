# weixin-dubbo-springboot
QQ网页授权、微信公众号相关接口、企业微信相关接口，如消息推送等功能。基于17年6月最新api开发。使用 dubbo, spring boot, redis 实现的通用项目。

# 环境
  jdk1.8,maven,redis等。
  注意：
```
        <!-- qqSDK -->
        <dependency>
            <groupId>qq</groupId>
            <artifactId>sdk4j</artifactId>
            <version>2.0</version>
        </dependency>
        <!-- dubbo 2.5.4 -->
        <dependency>
            <groupId>com.alibaba</groupId>
            <artifactId>dubbo</artifactId>
            <version>2.5.4</version>
        </dependency>
```
 **qq的sdk** 和  **dubbo-2.5.4**  需要自己下载，然后加到自己本地仓库或者私服。
这两个jar已打包到项目目录中，[dubbo和sdk4j下载地址](http://git.oschina.net/blueriver/weixin-dubbo-springboot/raw/master/dubbo-2.5.4+sdk4j.zip)

[qqSDK官方下载地址](http://qzonestyle.gtimg.cn/qzone/vas/opensns/res/doc/qqConnect_Server_SDK_java_v2.0.zip)
    


# 项目结构
![项目结构](https://git.oschina.net/uploads/images/2017/0817/122249_33e85f50_1069272.png "QQ20170817-122217.png")

# 需要改的配置
## 1.redis服务地址、公众号信息配置
![redis服务地址和公众号信息](https://git.oschina.net/uploads/images/2017/0817/131800_3d2bc2f6_1069272.png "QQ20170817-131711.png")

## 2.QQ授权配置
![QQ授权配置](https://git.oschina.net/uploads/images/2017/0817/131923_99087e75_1069272.png "QQ20170817-131406.png")

## 3.企业号信息配置
![企业号信息配置](https://git.oschina.net/uploads/images/2017/0817/132009_a302c7cc_1069272.png "QQ20170817-131025.png")

# 时序图
  以微信登录授权为例：
![微信授权时序图](https://git.oschina.net/uploads/images/2017/0818/193202_e1f76126_1069272.png "WX20170818-193151.png")

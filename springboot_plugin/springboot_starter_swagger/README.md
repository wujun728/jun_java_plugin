# spring-boot-starter-swagger
该项目是spring-boot与`swagger`的整合，[swagger](https://swagger.io/)是一款高效易用的嵌入式文档插件。引入该项目，可以让你的api发布更容易，修改发布更快捷，团队交流更高效。

## 本项目特点
1.通过spring-boot方式配置的swagger实现，完美并且完整的支持swagger-spring的配置项

2.配置及其简单，容易上手。

2.支持api分组配置，通过正则表达式方式分组

3.支持分环境配置，你可以很容易让你的项目api文档在开发环境，测试环境，预发布环境查看，而在生产环境不可查看

## 快速入门
快速入门[点击这里](http://blog.csdn.net/hulei19900322/article/details/78107516)

### 本项目将在 [开源中国](https://gitee.com/reger/spring-boot-starter-swagger),和[github](https://github.com/halober/spring-boot-starter-swagger)进行同步更新，欢迎大家使用，欢迎大家提出意见建议。
## 生成客户端代码
生成客户端代码[点击这里](http://blog.csdn.net/hulei19900322/article/details/78107874)

### 项目推荐
小项目写多了，你或许需要开始考虑分布式式，考虑rpc框架了，dubbo一定是你最好的选择，这个项目则是你使用dubbo最优的入口 [spring-boot-starter-dubbo](https://gitee.com/reger/spring-boot-starter-dubbo)

### 更新记录
```
1.1.0
  发布时间： 2018年4月11日
  更新内容： 
     1.升级spring-boot版本到2.0.1

1.0.4
  发布时间：2018年3月4日
  更新内容：
     1.优化部分代码
     2.升级spring-boot版本至1.5.10
     3.升级swagger依赖至2.8.0

1.0.3
  发布时间：2017年10月25日
  更新内容：
    1.降低jdk依赖至1.7
    2.升级spring-boot版本至1.5.8
1.0.2
  发布时间：2017年10月15日
  更新内容：
    1.调整配置 spring.swagger-group到spring.swagger.group
    2.增加显示启用swagger配置的参数spring.swagger.enabled
  注： 本次升级完全兼容1.0.1
1.0.1
  发布时间：2017年9月17日 
  更新内容：
    1. 解决1.0.0发版中的bug
1.0.0
  发布时间：2017年9月16日
  更新内容：
    1. 完成基础功能

```
## 示例项目
#### 1.克隆[示例代码](https://gitee.com/lei0719/spring-boot-starter-swagger-example)
```cmd
git clone git@gitee.com:lei0719/spring-boot-starter-swagger-example.git
```
#### 2.启动服务
进入示例代码的目录执行命令
```cmd
mvn spring-boot:run -Dspring.profiles.active=dev  -Dserver.port=8080
```
#### 3.查看文档
访问地址[点击这里](http://127.0.0.1:8080/swagger-ui.html)

## 可用的配置项
```yml
spring:
  swagger:
    enabled: false                                  # 是否启用swagger
    group:
      user-api:                                     # 用户组api，可以配置多个组
        group-name: 01.user-api                     # api组的名字，会在swagger-ui的api下拉列表中显示；组名前的序号，多个组可以排序；最好不要写中文
        title: 用户相关的操作                        # api组的标题，会在swagger-ui的标题处显示
        description: 用户相关的操作，包括用户登录登出  # api组的描述，会在swagger-ui的描述中显示
        path-regex: /api/user/.*                    # 匹配到本组的api接口，匹配uri，可以用用正则表达式
        path-mapping: /                             # 匹配到的url在swagger中测试请求时加的url前缀
        version: 0.0.0                              # api版本
        license: 该文档仅限公司内部传阅               # 授权协议
        license-url: '#'                            # 授权协议地址
        terms-of-service-url:                       # 服务条款地址
        contact:                                    # 文档联系人
          name: 张三                                # 联系人名字
          email: zhangsan@team.com                  # 联系人邮箱
          url: http://www.team.com                  # 联系地址
```

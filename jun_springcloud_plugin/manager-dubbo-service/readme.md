# Dubbo通用后台框架使用

## 使用本框架之前， 请务必认真阅读

#### 项目主要依赖版本

* java:1.8+
* springboot:2.2.6.RELEASE
* dubbo:2.7.8
* nacos-config:0.2.6
* mybatis-plus:3.4.0
* maven

#### 项目目录：
```
parent
	- manager-cosumer-api：接口consumer,不涉及角色权限
	- manager-cosumer-admin：后台管理consumer，shiro管理权限
	- manager-service：interface
	- manager-provider-sys:  后台管理provider, 实现manager-service中的接口
	- manager-entity: 实体类
	- manager-base: 可以放一些公用类方法等
	- manager-generator：代码生成（mybatis-plus）
```

![](https://images.gitee.com/uploads/images/2020/1110/160709_16ca687b_997722.png)

#### 项目配置启动：

* 数据库先执行初始化脚本/doc/mysql.sql
* 本地启动需安装lombok插件
* 默认采用nacos配置中心；nacos需要创建dev、test、prod对应的命名空间，同时添加application-admin.properties、application-sys.properties等配置文件
* 如果不使用nacos配置中心， 本地需要解开所有application.properties中注释部分， 同时注释掉nacos.config部分；
* 本地开发启动， 需修改consumer跟provider的版本号，改为自己的； 因都使用一个nacos注册中心，需要避免跟线上冲突
* maven模块化开发，父模块统一管理主要依赖版本；本地、测试、正式环境切换采用mvn -Ptest方式
* 启动provider，启动consumer即可

#### 本框架开发规范及使用注意事项

* 开发需遵循alibaba开发规范；注释不可缺少，代码美观， 尽量避免黄线
* 开发前请先掌握基础框架springboot、dubbo、nacos、maven、mybatisplus等
* controller请求方式统一：新增/修改/删除post请求，获取get请求； 接口返回JSON统一风格：DataResult.success()/fail()
* 前端发送一个请求所经过的模块：consumer（controller->service->serviceImpl）->service(providerService)   ->provider(providerServiceImpl->mapper)； 注意各个模块之间的传递对象需要序列化；
* consumer方法日志注解：@LogAnnotation(title = "用户管理", action = "更新用户信息")
* 接口请求header需要传token： authorization=j1VcFg0GBRo8AIyc99t820jc18D0xs4a#1
* 调用mybatisplus service自己的的list或get方法， 会自动加上where delete=1； 如果手动写xml， 需要在sql中手动加上where delete=1；
  save方法，自动set delete=1（未删除），创建时间， 需手动set创建人；update方法，自动set更新时间， 需手动set修改人
* 新建实体类继承BaseEntity(page=1,limit=10)，便于前后端分页
* 按道理，可以任意throw new BusinessException("Error Message")
* 后端校验字段是否空等，用校验框架validation即可，无须再额外捕获字段错误异常，已统一处理。校验list也可以  
* java调用第三方接口详见 OkHttpUtils 工具类  

#### 基础功能
* 菜单管理：添加目录、菜单、按钮，控制权限
* 角色管理：添加角色， 绑定菜单
* 用户管理：添加管理端的用户， 分配角色
* 字典管理

#### 项目打包发布
* 本地在根目录下执行 mvn clean package -Dmaven.test.skip=true -Ptest  可打包测试环境的包
* java -jar consumer.jar 等jar包即可启动
* 打包镜像：需先掌握Docker基础，每个模块下应有Dockerfile，build成镜像，push到pass

* 建议直接jenkins一键发布


#### 数据库设计基础字段应统一
id          long        主键  

order_num   int         排序（正序）  

remark	    varchar		备注描述  

unable_flag tinyint     是否启用禁用(1启用；0禁用)      

create_id	varchar		创建者  

create_time	datetime	创建时间  

update_id	varchar		更新者  

update_time	datetime	更新时间	  

deleted	    tinyint	    是否删除(1未删除；0已删除)  

attr1       varchar     冗余字段  

attr2       varchar     冗余字段  

attr2       varchar     冗余字段  


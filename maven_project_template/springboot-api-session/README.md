## 简介
Spring Boot API 是一个基于Spring Boot & MyBatis plus的种子项目，用于快速构建中小型API项目，特点稳定、简单、快速，摆脱那些重复劳动

## 特征&提供
- 统一响应结果封装及生成工具
- 统一异常处理
- 采用redis token认证，支持单登陆端/多登陆端登陆
- 使用Druid Spring Boot Starter 集成Druid数据库连接池与监控
- 集成MyBatis-Plus，实现单表业务零SQL
- 支持多数据源，自由切换，只需方法或类上用 @DS 切换数据源
- 集成国人风格的knife4j，自动生成接口文档
- 提供代码生成器，生成controller,service,serviceImpl,dao,mapper.xml

## 快速开始
1. 克隆项目
2. 导入```test```包里的mysql脚本user.sql
3. 对```test```包内的代码生成器```CodeGenerator```进行配置，主要是JDBC，因为要根据表名来生成代码
4. 输入表名，运行```CodeGenerator.main()```方法，生成基础代码（可能需要刷新项目目录才会出来）
5. 根据业务在基础代码上进行扩展
6. 对开发环境配置文件```application-dev.yml```进行配置，启动项目，Have Fun！

## 开发建议
- post调用接口ip:8080/api/user/login,参数json: {"username":"admin","password":"123456"},调用成功后, 返回token。以后调用api接口，header中传token
- 已写好注册、登陆、登出、修改密码接口， 支持单登陆端/多登陆端登陆， 具体看UserController.java 类。用户登陆之后获取session信息 JSONObject sessionInfo = httpSession.getCurrentSession();
- 正式环境已禁用接口文档的查看，配置文件添加knife4j:production: true 即可
- 是否允许多个登陆端，修改配置文件 redis:allowMultipleLogin:true 
- Model内成员变量建议与表字段数量对应，如需扩展成员变量（比如连表查询）建议创建DTO，否则需在扩展的成员变量上加@TableField(exist = false)，详见[MyBatis-Plus](https://mp.baomidou.com/guide/)文档说明
- 建议业务失败直接使用ServiceException("ErrorMessage")抛出，由统一异常处理器来封装业务失败的响应结果，会直接被封装为{"code":400,"message":"ErrorMessage"}返回，尽情抛出；body方式传参，@Valid校验Model，更无需自己处理；

## 接口文档效果图
![image-20200313084433855](http://tuchuang.aitangbao.com.cn/image-20200313084433855.png)

## 相关文档
- Spring Boot（[springboot官方](https://spring.io/projects/spring-boot/)）
- MyBatis-Plus ([查看官方中文文档](https://mp.baomidou.com/guide/))
- MyBatis-Plus分页插件（[查看官方中文文档](https://mp.baomidou.com/guide/page.html)）
- Druid Spring Boot Starter（[查看官方中文文档](https://github.com/alibaba/druid/tree/master/druid-spring-boot-starter/)）
- Fastjson（[查看官方中文文档](https://github.com/Alibaba/fastjson/wiki/%E9%A6%96%E9%A1%B5)）
- 阿里巴巴Java开发手册[最新版下载](https://github.com/alibaba/p3c)
其他

## License
纯粹开源分享，感谢大家 [Star](https://github.com/aitangbao/springboot-api-v2) 的支持。

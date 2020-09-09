### `jun_plugin` 项目

#### 项目说明
jun_plugin 整合Java企业级各种开发组件、开箱即用、不写重复代码

#### `笔者其他项目` 功能实现&使用
- jun_springboot：[点击查看](https://github.com/wujun728/jun_springboot) 
- jun_springcloud：[点击查看](https://github.com/wujun728/jun_springcloud) 
- jedp：[点击查看](https://github.com/wujun728/jedp) 

#### 组件功能列表
- 【fastdfs-client-java】fastdfs的源码，编辑jar，引入maven本地，maven仓库没这个jar包
- 【jun_activiti】原生集成activity工作流，完成工作流常用操作
- 【jun_ajax】完成ajax操作，前端及后端的ajax
- 【jun_aliyun_sms】发送阿里云短信及验证码等
- 【jun_cron】 cron表达式的java的实现及调度
- 【jun_dao_hibernate】原生集成hibernate及使用
- 【jun_datasource】原生集成各种JDBC DataSource数据源
- 【jun_dbutil】原生集成Apache 的Dbutils完成单表及多表的增删改查
- 【jun_designpattern】23种涉及模式的Java实现
- 【jun_distributed_session】分布式Session实现及配置持久化等
- 【jun_email】原生邮件发送、纯文本、HTML邮件、带附件的邮件
- 【jun_executor】原生的并发多线程demo操作
- 【jun_fileupload】原生的文件上传及下载操作的实现基于common fileupload
- 【jun_freemarker】原生集成freemarker模板引擎，数据+模板=输出，可输出代码生成器
- 【jun_httpclient】原生集成httpclient，发送http请求、下载文件等
- 【jun_image】原生图片操作包、包括图片上传、下载、展示、缩略图等
- 【jun_jdbc】原生JDBC操作，简单封装，需要的可以看下
- 【jun_jdk】JDK原生demo代码，了解的越多才会了解的越深
- 【jun_json】集成jackjson及fastjson及jons-lib及Gson等实现JSON生成及序列化&反序列化
- 【jun_jsoup】HTM标记语言解析包，完成HTML解析、主要爬虫使用，解析HTML渲染数据
- 【jun_leetcode】算法刷题大全
- 【jun_lucene】老牌搜索引擎、可以看下
- 【jun_poi】原生POI完成Excel文件的导入、解析、导出及持久化等
- 【jun_qrcode】二维码生成器
- 【jun_quartz】job任务调度
- 【jun_rpc】原生RPC调用、客户端及服务端
- 【jun_servlet】原生Servlet、WEB开发的基础
- 【jun_sso】原生SSO的实现单点登录
- 【jun_test】JUNIT框架及TestNG框架
- 【jun_utils】常用开发工具集、非常重要！！！
- 【jun_webservice】原生的webservice调用、基于apache cxf实现服务调用及发布
- 【jun_webservlet】原生Servlet 3.0的实现
- 【jun_websocket】原生的WebSocket的实现长链接
- 【jun_xml】原生的XML解析及生产XML、提供SAX、DOM、DOM4J解析方式
- 【jun_zxing】Google二维码生成器

#### 开发环境

- **JDK 1.8 +**
- **Maven 3.5 +**
- **IDEA 2018.2 + or  STS 4.5 +** (*注意：务必使用 IDEA 开发，同时保证安装 `lombok` 插件*)
- **Mysql 5.7 +** (*尽量保证使用 5.7 版本以上，因为 5.7 版本加了一些新特性，同时不向下兼容。本 demo 里会尽量避免这种不兼容的地方，但还是建议尽量保证 5.7 版本以上*)

#### 运行方式

> 提示：如果是 fork 的朋友，同步代码的请参考：
1. `git clone https://github.com/wujun728/jun_xxx.git`
2. 使用 IDEA 打开 clone 下来的项目
3. 在 IDEA 中 Maven Projects 的面板导入项目根目录下 的 `pom.xml` 文件
4. Maven Projects 找不到的童鞋，可以勾上 IDEA 顶部工具栏的 View -> Tool Buttons ，然后 Maven Projects 的面板就会出现在 IDEA 的右侧
5. 找到各个 Module 的 Application 类就可以运行各个 demo 了
6. 注意：每个 demo 均有详细的 README 配套，用 demo 前记得先看看
7. 注意：运行各个 demo 之前，有些是需要事先初始化数据库数据的

#### 已办&待办列表
1. 完善Spring集成各种组件
1. 梳理当前的代码及功能

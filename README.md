### `jun_plugin` 项目

#### 项目说明
jun_plugin 整合Java企业级各种开发组件、开箱即用、不写重复代码

#### `笔者其他项目` 功能实现&使用
- jun_springboot：[点击查看](https://github.com/wujun728/jun_springboot) 
- jun_springcloud：[点击查看](https://github.com/wujun728/jun_springcloud) 
- jedp：[点击查看](https://github.com/wujun728/jedp) 

#### 基础篇：企业级开发组件功能列表（jun_plugin）
- 【fastdfs-client-java】[fastdfs的源码，编辑jar，引入maven本地，maven仓库没这个jar包](https://github.com/wujun728/jun_plugin)
- 【jun_activiti】[原生集成activity工作流，完成工作流常用操作](https://github.com/wujun728/jun_plugin)
- 【jun_ajax】[完成ajax操作，前端及后端的ajax](https://github.com/wujun728/jun_plugin)
- 【jun_aliyun_sms】[发送阿里云短信及验证码等](https://github.com/wujun728/jun_plugin)
- 【jun_cron】 [cron表达式的java的实现及调度](https://github.com/wujun728/jun_plugin)
- 【jun_dao_hibernate】[原生集成hibernate及使用](https://github.com/wujun728/jun_plugin)
- 【jun_datasource】[原生集成各种JDBC DataSource数据源](https://github.com/wujun728/jun_plugin)
- 【jun_dbutil】[原生集成Apache 的Dbutils完成单表及多表的增删改查](https://github.com/wujun728/jun_plugin)
- 【jun_designpattern】[23种涉及模式的Java实现](https://github.com/wujun728/jun_plugin)
- 【jun_distributed_session】[分布式Session实现及配置持久化等](https://github.com/wujun728/jun_plugin)
- 【jun_email】[原生邮件发送、纯文本、HTML邮件、带附件的邮件](https://github.com/wujun728/jun_plugin)
- 【jun_executor】[原生的并发多线程demo操作](https://github.com/wujun728/jun_plugin)
- 【jun_fileupload】[原生的文件上传及下载操作的实现基于common fileupload](https://github.com/wujun728/jun_plugin)
- 【jun_freemarker】[原生集成freemarker模板引擎，数据+模板=输出，可输出代码生成器](https://github.com/wujun728/jun_plugin)
- 【jun_httpclient】[原生集成httpclient，发送http请求、下载文件等](https://github.com/wujun728/jun_plugin)
- 【jun_image】[原生图片操作包、包括图片上传、下载、展示、缩略图等](https://github.com/wujun728/jun_plugin)
- 【jun_jdbc】[原生JDBC操作，简单封装，需要的可以看下](https://github.com/wujun728/jun_plugin)
- 【jun_jdk】[JDK原生demo代码，了解的越多才会了解的越深](https://github.com/wujun728/jun_plugin)
- 【jun_json】[集成jackjson及fastjson及jons-lib及Gson等实现JSON生成及序列化&反序列化](https://github.com/wujun728/jun_plugin)
- 【jun_jsoup】[HTM标记语言解析包，完成HTML解析、主要爬虫使用，解析HTML渲染数据](https://github.com/wujun728/jun_plugin)
- 【jun_leetcode】[算法刷题大全](https://github.com/wujun728/jun_plugin)
- 【jun_lucene】[老牌搜索引擎、可以看下](https://github.com/wujun728/jun_plugin)
- 【jun_poi】[原生POI完成Excel文件的导入、解析、导出及持久化等](https://github.com/wujun728/jun_plugin)
- 【jun_qrcode】[二维码生成器](https://github.com/wujun728/jun_plugin)
- 【jun_quartz】[job任务调度](https://github.com/wujun728/jun_plugin)
- 【jun_rpc】[原生RPC调用、客户端及服务端](https://github.com/wujun728/jun_plugin)
- 【jun_servlet】[原生Servlet、WEB开发的基础](https://github.com/wujun728/jun_plugin)
- 【jun_sso】[原生SSO的实现单点登录](https://github.com/wujun728/jun_plugin)
- 【jun_test】[JUNIT框架及TestNG框架](https://github.com/wujun728/jun_plugin)
- 【jun_utils】[常用开发工具集、非常重要！！！](https://github.com/wujun728/jun_plugin)
- 【jun_webservice】[原生的webservice调用、基于apache cxf实现服务调用及发布](https://github.com/wujun728/jun_plugin)
- 【jun_webservlet】[原生Servlet 3.0的实现](https://github.com/wujun728/jun_plugin)
- 【jun_websocket】[原生的WebSocket的实现长链接](https://github.com/wujun728/jun_plugin)
- 【jun_xml】[原生的XML解析及生产XML、提供SAX、DOM、DOM4J解析方式](https://github.com/wujun728/jun_plugin)
- 【jun_zxing】[Google二维码生成器](https://github.com/wujun728/jun_plugin)


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


content: 7135a7ef01b1ed0a84484d07f7881b1081eb24eaeb420856f519de3aeb158f258f4e335951de71c66658314393007c83dd8b7c16377423c7431f13f4e550ab8bf63f57a4319dc8624a16f1bd8cddc7c3a1404eada665cc09cfa564c1119024dcb33f8a104a461ff0424220656af6662c4ff19b5341d1847ed234da6f8c4d714512f6b979ed76813ddfeb6303c022ddee4c5c61c16baf8ea1f27f77a09f50ba75a868e6b8ab8cbff8ea33131306c8f86ec0db6d7070636ec5ae7b4ae5a5a2e780017c541349f26b36c29d015d77e41ab095e5aa605b8440a0c35bc9162e2695a72a0c885c4a45ad2a1d805a1251e79378833212b4ff073db486b7e962ead9902a2bcb97a89e5bf50b06ff9236ea4417a1273c075264036e9cb231adab521e55ca3cf42c7cdb01c71aa2823c5ae4243172642f55b04cf573d508242c55dd7b606428e4b391533f41fc35f9b2e6650cd62644feae266e587f810f88395558670f41f7c9c145b67647e6ace87c440702b105a1b020ba45f4e823ceed2e0e71955939ff16e2896efb5f8514e256cfadfdd62923079b24644f340d7209113ff2f261036cb3ff7ed81b865c95068fc77bea4d948e6a4a6217b581646760d3a80a76ae60f5fcbe516301aeb8671ea0f3ace4ca22297f06699f2c0140af3d4e41c3324fc36ad2377289c62091973c8441cc306f67fbd739722a5075a2dc818d34ede1bdca268d79c4241a8f04e7cf8f0b6420ac18bcf6bcad51c40080423c52811552c241ffdfc113e3087f53fe9aedf1c8cf3c1b2c57e2bf8036eeea452e9da625a4f908fe43b624a2b9dd3f5c0dff63404cf83ec8d1618ef119fb8536cb5f5744ee340823aaaa04d410a69e58492dbaa7403d5eaf48b968924a97af3399ade6638191d5277d31d03599a65698e6671d9d5e6f506ff60c4344d7cffe57f1a528383caf4dca9098669093eaabf5d2b698804ccba8e3d8f93f53e246171926a76070d96444f43f8d1002a3435f7f7713bebf209ded6c0f9c1b5b1f0fc8daa626acf04591f32c6e19a63c5f3e7b743df8508d9cd3e5be6f948c02be926fa1a5b0486b4cbe61d6c1fa5d49ccfc4b2c9b9c9c331a0374325e0b4c8bbf5d9b934599f99619c39d706314dd28e8eeabca5ccf6518332695ac088384f56ae2f36acd31d38606fd71b0ca8eb55c6df8088aff6601a03a086d427fed53ab1bfb2549126463bba8433b86ad1ebabfad5179d6754512ff38c212d6009e08a79bafd9c126f0bb4a41851fda162c1e43d430cc8ea6958089fecd1c68b098f507af93fa43d0975eea23925544e956878fd27c39ed7d05fa73ce2123c11fa22b8dd39ec2f6f4a0c9581b49a192b83104cb215f149547d2235d455fcaeba8938aa8d15b0366a1bba7de368aa248e964e494d1094066304a528ef0e87043a36f901bde83f609c4e7f95775cd3e6dad6ff23a8051786a1cd590b599300a0723502a347d9c04b80792c3c3e95e7959bfd29f07ac7ef23b30443d1f0e8855e30421cbc48c9cfb10a848440503a5b46e0e947b51a4287ba9e74b6072752d64874a80cbf03a0c6b868139155ca6b492

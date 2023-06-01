# Spring-solr
本项目基于Apache Solr提供一个根据需求配置好的搜索引擎并导入一些初始数据，再基于Spring框架连接solr实现企业级搜索的功能。主要内容如下：

1.搜索引擎基于Solr5.3.1，官网：http://lucene.apache.org/solr/

2.Solr中已经配置好三个内核，用于连接三种不同的数据源：

  -mongoDB内核用于同步mongoDB的数据进行搜索；
  
  -sakila内核用于搜索导入的Mysql中的默认数据库sakila；
  
  -tika内核用于将本地指定文件夹下的文档（Office或PDF等）导入，进行全文检索，本样例仅提供26个文档供体验。
  
3.已完成Solr和Tomcat服务器的整合，整个文件夹下载后，进入apache-tomcat-8.0.36中bin目录，双击startup.bat即可开始运行；

4.已整合中文分词器IKAnalyzer，官网https://github.com/EugenePig/ik-analyzer-solr5 。在apache-tomcat-8.0.36\webapps\solr\WEB-INF\classes路径下添加了用户自定义的字典my.dic用于扩展分词；

5.mongoDB与solr同步工具使用mongo-connector完成，需在Linux下运行，官网https://github.com/mongodb-labs/mongo-connector

6.Spring后台使用solrJ与solr进行交互；

7.把Tomcat拷贝到D盘根目录下，启动Tomcat后，访问http://localhost:8080/solr/  可进入solr的管理控制台页面，搜索入口访问http://localhost:8080/Spring-solr/search

8.前端暂不提供对sakila和mongoDB的搜索，仅提供了文档的全文检索，要使用数据库的搜索，请进入solr控制台查看效果。

效果图：
 ![输入图片说明](http://git.oschina.net/uploads/images/2016/1116/080927_551753d6_1110335.png "在这里输入图片标题")
![输入图片说明](http://git.oschina.net/uploads/images/2016/1116/080940_39753846_1110335.jpeg "在这里输入图片标题")
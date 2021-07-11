<a href="https://www.cnblogs.com/haixiang/p/11078875.html"><img src="https://img.shields.io/badge/博客-Haixiang-important" alt="博客"></a>
<a href="#"><img src="https://img.shields.io/badge/版本-1.0-brightgreen.svg" alt="版本"></a>
<a href="#"><img src="https://img.shields.io/badge/公众号-Java后端架构充电宝-blue.svg" alt="公众号"></a>

## 简介
+ 基于ElasticSearch7.3.2版本的通用搜索系统
+ 使用rest-high-level-client操作ES
+ 封装常用api，包含索引的CRUD、文档的CRUD、索引定制化、模糊搜索、精准匹配搜索
+ 是一套操作简单的搜索系统脚手架，稍加修改即可在项目中快速搭建起搜索系统

## 使用rest-high-level-client整合Es的原因
+ TransportClient 存在并发瓶颈
+ rest-client 版本较低无法支持新特性
+ SpringBoot的Es模板ElasticsearchRepository更新较慢，不支持高版本的ES

## 使用方式
`git clone`按需复制到自己的项目里即可

## 组件版本
| 组件                     | Version       |
| ------------------------ | ------------- |
| Elasticsearch            | 7.3.2         |
| Elasticsearch-rest-high-level-client | 7.3.2         |
| Fastjson                 | 1.2.60        |
| SpringBoot               | 2.1.0.RELEASE |



## 目录
```
├── src
│   ├── main
│      ├── java
│      │   └── com
│      │       └── anqi
│      │           └── es
│      │               ├── DemoEsApplication.java
│      │               ├── Main.java
│      │               ├── client
│      │               │   └── ESClientConfig.java     老版本RestClient封装，这里不会使用
│      │               ├── controller
│      │               │   └── EsController.java       搜索测试接口
│      │               ├── highclient
│      │               │   ├── RestHighLevelClientConfig.java      Client配置
│      │               │   └── RestHighLevelClientService.java     搜索API
│      │               └── util
│      │                   └── SnowflakeIdWorker.java      Twitter的雪花算法用来生成文档id
│      └── resources
│          ├── application.properties
│          ├── static
│          └── templates
├── pom.xml
```

## maven说明
因为elasticsearch-rest-high-level-client7.3.2 依赖 elasticsearch 6.4.2 和 elasticsearch-rest-client 6.4.2 ，而目前这已经是中央仓库中最高版本的jar包了（8.0.0）还未迁入中央仓库，所以我们手动引入7.3.2的新版本elasticsearch和elasticsearch-rest-client

```xml
        <!-- high client-->
        <dependency>
            <groupId>org.elasticsearch.client</groupId>
            <artifactId>elasticsearch-rest-high-level-client</artifactId>
            <version>7.3.2</version>
            <exclusions>
                <exclusion>
                    <groupId>org.elasticsearch.client</groupId>
                    <artifactId>elasticsearch-rest-client</artifactId>
                </exclusion>
                <exclusion>
                    <groupId>org.elasticsearch</groupId>
                    <artifactId>elasticsearch</artifactId>
                </exclusion>
            </exclusions>
        </dependency>
        <!-- https://mvnrepository.com/artifact/org.elasticsearch/elasticsearch -->
        <dependency>
            <groupId>org.elasticsearch</groupId>
            <artifactId>elasticsearch</artifactId>
            <version>7.3.2</version>
        </dependency>

        <!--rest low client-->
        <dependency>
            <groupId>org.elasticsearch.client</groupId>
            <artifactId>elasticsearch-rest-client</artifactId>
            <version>7.3.2</version>
        </dependency>

        <!-- springboot-->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
```


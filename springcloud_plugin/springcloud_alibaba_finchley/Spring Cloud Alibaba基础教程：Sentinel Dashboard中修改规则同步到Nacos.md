# Spring Cloud Alibaba基础教程：Sentinel Dashboard中修改规则同步到Nacos

**原创**

 [2019-05-21](https://blog.didispace.com/spring-cloud-alibaba-sentinel-2-4/)

 翟永超

 [Spring Cloud](https://blog.didispace.com/categories/Spring-Cloud/)

[【推荐】从业15年的架构师告诉你：如何落地微服务和云原生架构？](https://blog.didispace.com/how-to-implement-microservice-and-cloud-native-architecture/)

上一篇我们介绍了如何通过改造Sentinel Dashboard来实现修改规则之后自动同步到Apollo。下面通过这篇，详细介绍当使用Nacos作为配置中心之后，如何实现Sentinel Dashboard中修改规则同步到Nacos。关于下面改造的原理和分析可以见上一篇[《Sentinel Dashboard中修改规则同步到Apollo》](http://blog.didispace.com/spring-cloud-alibaba-sentinel-2-3/)的头两节内容，这里不重复介绍了。

### 代码实现

下面直接来看看如何实现的具体改造步骤，这里参考了`Sentinel Dashboard`源码中关于Nacos实现的测试用例。但是由于考虑到与Spring Cloud Alibaba的结合使用，略作修改。

**第一步**：修改`pom.xml`中的sentinel-datasource-nacos的依赖，将`test`注释掉，这样才能在主程序中使用。

```
<dependency>
    <groupId>com.alibaba.csp</groupId>
    <artifactId>sentinel-datasource-nacos</artifactId>
    <!--<scope>test</scope>-->
</dependency>
```

**第二步**：找到`resources/app/scripts/directives/sidebar/sidebar.html`中的这段代码：

```
<li ui-sref-active="active">
    <a ui-sref="dashboard.flowV1({app: entry.app})">
        <i class="glyphicon glyphicon-filter"></i>&nbsp;&nbsp;流控规则
    </a>
</li>
```

修改为：

```
<li ui-sref-active="active">
    <a ui-sref="dashboard.flow({app: entry.app})">
        <i class="glyphicon glyphicon-filter"></i>&nbsp;&nbsp;流控规则
    </a>
</li>
```

**第三步**：在`com.alibaba.csp.sentinel.dashboard.rule`包下新建一个nacos包，用来编写针对Nacos的扩展实现。

**第四步**：创建Nacos的配置类，具体代码如下：

```
@Configuration
public class NacosConfig {

    @Bean
    public Converter<List<FlowRuleEntity>, String> flowRuleEntityEncoder() {
        return JSON::toJSONString;
    }

    @Bean
    public Converter<String, List<FlowRuleEntity>> flowRuleEntityDecoder() {
        return s -> JSON.parseArray(s, FlowRuleEntity.class);
    }

    @Bean
    public ConfigService nacosConfigService() throws Exception {
        Properties properties = new Properties();
        properties.put(PropertyKeyConst.SERVER_ADDR, "localhost");
        return ConfigFactory.createConfigService(properties);
    }
}
```

如果用到了namespace隔离环境，可以在`nacosConfigService`方法中再加入配置，比如：`properties.put(PropertyKeyConst.NAMESPACE, "130e71fa-97fe-467d-ad77-967456f2c16d");`

**第五步**：实现Nacos的配置拉取。

```
@Component("flowRuleNacosProvider")
public class FlowRuleNacosProvider implements DynamicRuleProvider<List<FlowRuleEntity>> {

    @Autowired
    private ConfigService configService;
    @Autowired
    private Converter<String, List<FlowRuleEntity>> converter;

    public static final String FLOW_DATA_ID_POSTFIX = "-sentinel";
    public static final String GROUP_ID = "DEFAULT_GROUP";

    @Override
    public List<FlowRuleEntity> getRules(String appName) throws Exception {
        String rules = configService.getConfig(appName + FLOW_DATA_ID_POSTFIX, GROUP_ID, 3000);
        if (StringUtil.isEmpty(rules)) {
            return new ArrayList<>();
        }
        return converter.convert(rules);
    }
}
```

- `getRules`方法中的`appName`参数是Sentinel中的服务名称。
- `configService.getConfig`方法是从Nacos中获取配置信息的具体操作。其中，DataId和GroupId分别对应客户端使用时候的对应配置。比如这里的例子对应了之前我们在[《Sentinel使用Nacos存储规则》](http://blog.didispace.com/spring-cloud-alibaba-sentinel-2-1/)一文中的配置，具体如下：

```
spring.cloud.sentinel.datasource.ds.nacos.groupId=DEFAULT_GROUP
spring.cloud.sentinel.datasource.ds.nacos.dataId=${spring.application.name}-sentinel
```

注意：两边的DataId和GroupId必须对应上。

**第六步**：实现Nacos的配置推送。

```
@Component("flowRuleNacosPublisher")
public class FlowRuleNacosPublisher implements DynamicRulePublisher<List<FlowRuleEntity>> {

    @Autowired
    private ConfigService configService;
    @Autowired
    private Converter<List<FlowRuleEntity>, String> converter;

    public static final String FLOW_DATA_ID_POSTFIX = "-sentinel";
    public static final String GROUP_ID = "DEFAULT_GROUP";

    @Override
    public void publish(String app, List<FlowRuleEntity> rules) throws Exception {
        AssertUtil.notEmpty(app, "app name cannot be empty");
        if (rules == null) {
            return;
        }
        configService.publishConfig(app + FLOW_DATA_ID_POSTFIX, GROUP_ID, converter.convert(rules));
    }
}
```

- 这里的大部分内容与上一步中的实现一致。主要就是Nacos中存储配置的DataId和GroupId不要弄错。

**第七步**：修改`com.alibaba.csp.sentinel.dashboard.controller.v2.FlowControllerV2`中`DynamicRuleProvider`和`DynamicRulePublisher`注入的Bean，改为上面我们编写的针对Apollo的实现：

```
@Autowired
@Qualifier("flowRuleNacosProvider")
private DynamicRuleProvider<List<FlowRuleEntity>> ruleProvider;
@Autowired
@Qualifier("flowRuleNacosPublisher")
private DynamicRulePublisher<List<FlowRuleEntity>> rulePublisher;
```

最后，读者可以使用本文改造后的sentinel-dashboard联合之前[《Sentinel使用Nacos存储规则》](http://blog.didispace.com/spring-cloud-alibaba-sentinel-2-1/)一文的例子来验证本文内容。

## 代码示例

本文介绍内容的客户端代码，示例读者可以通过查看下面仓库中的`alibaba-sentinel-dashboard-nacos`项目：

- *Github：*[https://github.com/dyc87112/SpringCloud-Learning/](https://github.com/dyc87112/SpringCloud-Learning/tree/master/4-Finchley)
- *Gitee：*[https://gitee.com/didispace/SpringCloud-Learning/](https://gitee.com/didispace/SpringCloud-Learning/tree/master/4-Finchley)

**如果您对这些感兴趣，欢迎star、follow、收藏、转发给予支持！**

## 系列回顾

- [《Spring Cloud Alibaba基础教程：使用Nacos实现服务注册与发现》](http://blog.didispace.com/spring-cloud-alibaba-1/)
- [《Spring Cloud Alibaba基础教程：支持的几种服务消费方式》](http://blog.didispace.com/spring-cloud-alibaba-2/)
- [《Spring Cloud Alibaba基础教程：使用Nacos作为配置中心》](http://blog.didispace.com/spring-cloud-alibaba-3/)
- [《Spring Cloud Alibaba基础教程：Nacos配置的加载规则详解》](http://blog.didispace.com/spring-cloud-alibaba-nacos-config-1/)
- [《Spring Cloud Alibaba基础教程：Nacos配置的多环境管理》](http://blog.didispace.com/spring-cloud-alibaba-nacos-config-2/)
- [《Spring Cloud Alibaba基础教程：Nacos配置的多文件加载与共享配置》](http://blog.didispace.com/spring-cloud-alibaba-nacos-config-3/)
- [《Spring Cloud Alibaba基础教程：Nacos的数据持久化》](http://blog.didispace.com/spring-cloud-alibaba-4/)
- [《Spring Cloud Alibaba基础教程：Nacos的集群部署》](http://blog.didispace.com/spring-cloud-alibaba-5/)
- [《Spring Cloud Alibaba基础教程：使用Sentinel实现接口限流》](http://blog.didispace.com/spring-cloud-alibaba-sentinel-1/)
- [《Spring Cloud Alibaba基础教程：Sentinel使用Nacos存储规则》](http://blog.didispace.com/spring-cloud-alibaba-sentinel-2-1/)
- [《Spring Cloud Alibaba基础教程：Sentinel使用Apollo存储规则》](http://blog.didispace.com/spring-cloud-alibaba-sentinel-2-2/)
- [《Spring Cloud Alibaba基础教程：Sentinel Dashboard中修改规则同步到Apollo》](http://blog.didispace.com/spring-cloud-alibaba-sentinel-2-3/)
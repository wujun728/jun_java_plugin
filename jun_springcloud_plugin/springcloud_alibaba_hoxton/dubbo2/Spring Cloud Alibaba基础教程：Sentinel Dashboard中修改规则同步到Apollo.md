# Spring Cloud Alibaba基础教程：Sentinel Dashboard中修改规则同步到Apollo

**原创**

 [2019-05-05](https://blog.didispace.com/spring-cloud-alibaba-sentinel-2-3/)

 翟永超

 [Spring Cloud](https://blog.didispace.com/categories/Spring-Cloud/)

[【推荐】从业15年的架构师告诉你：如何落地微服务和云原生架构？](https://blog.didispace.com/how-to-implement-microservice-and-cloud-native-architecture/)

在之前的两篇教程中我们分别介绍了如何将Sentinel的限流规则存储到Nacos和Apollo中。同时，在文末的思考中，我都指出了这两套整合方案都存在一个不足之处：不论采用什么配置中心，限流规则都只能通过Nacos界面或Apollo界面来完成修改才能得到持久化存储，而在Sentinel Dashboard中修改限流规则虽然可以生效，但是不会被持久化到配置中心。而在这两个配置中心里存储的数据是一个Json格式，当存储的规则越来越多，对该Json配置的可读性与可维护性会变的越来越差。所以，下面我们就来继续探讨这个不足之处，并给出相应的解决方案。本文以Apollo存储为例，下一篇介绍Nacos的改在示例。

## 问题分析

在实际操作之前，我们先通过下图了解一下之前我们所实现的限流规则持久化方案的配置数据流向图：

[![upload successful](https://blog.didispace.com/images/pasted-224.png)](https://blog.didispace.com/images/pasted-224.png)upload successful

- `蓝色箭头`代表了限流规则由`配置中心`发起修改的更新路径
- `橙色箭头`代表了限流规则由`Sentinel Dashboard`发起修改的更新路径

从图中可以很明显的看到，`Sentinel Dashboard`与业务服务之间本身是可以互通获取最新限流规则的，这在没有整合配置中心来存储限流规则的时候就已经存在这样的机制。最主要的区别是：配置中心的修改都可以实时的刷新到业务服务，从而被`Sentinel Dashboard`读取到，但是对于这些规则的更新到达各个业务服务之后，并没有一个机制去同步到配置中心，作为配置中心的客户端也不会提供这样的逆向更新方法。

## 改造方案

关于如何改造，现来解读一下官方文档中关于这部分的说明：

> 要通过 Sentinel 控制台配置集群流控规则，需要对控制台进行改造。我们提供了相应的接口进行适配。
>
> 从 Sentinel 1.4.0 开始，我们抽取出了接口用于向远程配置中心推送规则以及拉取规则：
>
> - DynamicRuleProvider: 拉取规则
> - DynamicRulePublisher: 推送规则
>
> 对于集群限流的场景，由于每个集群限流规则都需要唯一的 flowId，因此我们建议所有的规则配置都通过动态规则源进行管理，并在统一的地方生成集群限流规则。
>
> 我们提供了新版的流控规则页面，可以针对应用维度推送规则，对于集群限流规则可以自动生成 flowId。用户只需实现 DynamicRuleProvider 和 DynamicRulePublisher 接口，即可实现应用维度推送（URL: /v2/flow）。

这段内容什么意思呢？简单的说就是`Sentinel Dashboard`通过`DynamicRuleProvider`和`DynamicRulePublisher`两个接口来获取和更新应用的动态规则。默认情况下，就如上一节中`Sentinel Dashboard`与各业务服务之间的两个箭头，一个接口负责获取规则，一个接口负责更新规则。

所以，只需要通过这两个接口，实现对配置中心中存储规则的读写，就能实现`Sentinel Dashboard`中修改规则与配置中心存储同步的效果。

具体的配置数据流向图如下：

[![upload successful](https://blog.didispace.com/images/pasted-225.png)](https://blog.didispace.com/images/pasted-225.png)upload successful

其中，绿色箭头为公共公共部分，即：不论从培中心修改，还是从`Sentinel Dashboard`修改都会触发的操作。这样的话，从上图的两处修改起点看，所有涉及的部分都能获取到一致的限流规则了。

### 代码实现

下面继续说说具体的代码实现，这里参考了`Sentinel Dashboard`源码中关于Apollo实现的测试用例。但是由于考虑到与Spring Cloud Alibaba的结合使用，略作修改。

**第一步**：修改`pom.xml`中的Apollo OpenAPi的依赖，将`test`注释掉，这样才能在主程序中使用。

```
<dependency>
    <groupId>com.ctrip.framework.apollo</groupId>
    <artifactId>apollo-openapi</artifactId>
    <version>1.2.0</version>
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

**第三步**：在`com.alibaba.csp.sentinel.dashboard.rule`包下新建一个apollo包，用来编写针对Apollo的扩展实现。

**第四步**：创建Apollo的配置类，定义Apollo的portal访问地址以及第三方应用访问的授权Token（通过Apollo管理员账户登录，在“开放平台授权管理”功能中创建），具体代码如下：

```
@Configuration
public class ApolloConfig {

    @Bean
    public Converter<List<FlowRuleEntity>, String> flowRuleEntityEncoder() {
        return JSON::toJSONString;
    }

    @Bean
    public Converter<String, List<FlowRuleEntity>> flowRuleEntityDecoder() {
        return s -> JSON.parseArray(s, FlowRuleEntity.class);
    }

    @Bean
    public ApolloOpenApiClient apolloOpenApiClient() {
        ApolloOpenApiClient client = ApolloOpenApiClient.newBuilder()
            .withPortalUrl("https://apollo.xxx.com")  // TODO 根据实际情况修改
            .withToken("open api token") // TODO 根据实际情况修改
            .build();
        return client;
    }

}
```

**第五步**：实现Apollo的配置拉取实现。

```
@Component("flowRuleApolloProvider")
public class FlowRuleApolloProvider implements DynamicRuleProvider<List<FlowRuleEntity>> {

    @Autowired
    private ApolloOpenApiClient apolloOpenApiClient;
    @Autowired
    private Converter<String, List<FlowRuleEntity>> converter;

    @Value("${env:DEV}")
    private String env;

    @Override
    public List<FlowRuleEntity> getRules(String appName) throws Exception {
        // flowDataId对应
        String flowDataId = "sentinel.flowRules";
        OpenNamespaceDTO openNamespaceDTO = apolloOpenApiClient.getNamespace(appName, env, "default", "application");
        String rules = openNamespaceDTO
            .getItems()
            .stream()
            .filter(p -> p.getKey().equals(flowDataId))
            .map(OpenItemDTO::getValue)
            .findFirst()
            .orElse("");

        if (StringUtil.isEmpty(rules)) {
            return new ArrayList<>();
        }
        return converter.convert(rules);
    }
}
```

- `getRules`方法中的`appName`参数是Sentinel中的服务名称，这里直接通过这个名字获取Apollo配置是由于Apollo中的项目AppId与之一致，如果存在不一致的情况，则需要自己做转换。
- 这里注入了一个`env`属性，主要由于我们在使用Apollo的时候，通过启动参数来控制不同环境。所以这样就能在不同环境区分不同的限流配置了。
- 这里的`flowDataId`对应各个微服务应用中定义的`spring.cloud.sentinel.datasource.ds.apollo.flowRulesKey`配置，即：Apollo中使用了什么key来存储限流配置。
- 其他如Cluster、Namepsace都采用了默认值：default和application，这个读者有特殊需求可以做对应的修改。

**第六步**：实现Apollo的配置推送实现。

```
@Component("flowRuleApolloPublisher")
public class FlowRuleApolloPublisher implements DynamicRulePublisher<List<FlowRuleEntity>> {

    @Autowired
    private ApolloOpenApiClient apolloOpenApiClient;
    @Autowired
    private Converter<List<FlowRuleEntity>, String> converter;

    @Value("${env:DEV}")
    private String env;

    @Override
    public void publish(String app, List<FlowRuleEntity> rules) throws Exception {
        String flowDataId = "sentinel.flowRules";

        AssertUtil.notEmpty(app, "app name cannot be empty");
        if (rules == null) {
            return;
        }

        OpenItemDTO openItemDTO = new OpenItemDTO();
        openItemDTO.setKey(flowDataId);
        openItemDTO.setValue(converter.convert(rules));
        openItemDTO.setComment("modify by sentinel-dashboard");
        openItemDTO.setDataChangeCreatedBy("apollo");
        apolloOpenApiClient.createOrUpdateItem(app, env, "default", "application", openItemDTO);

        // Release configuration
        NamespaceReleaseDTO namespaceReleaseDTO = new NamespaceReleaseDTO();
        namespaceReleaseDTO.setEmergencyPublish(true);
        namespaceReleaseDTO.setReleaseComment("release by sentinel-dashboard");
        namespaceReleaseDTO.setReleasedBy("apollo");
        namespaceReleaseDTO.setReleaseTitle("release by sentinel-dashboard");
        apolloOpenApiClient.publishNamespace(app, env, "default", "application", namespaceReleaseDTO);
    }
}
```

- 这里的大部分内容，如：env、flowDataId、app说明与上一步中的实现一致
- `openItemDTO.setDataChangeCreatedBy("apollo");`和`namespaceReleaseDTO.setReleasedBy("apollo");`这两句需要注意一下，必须设置存在并且有权限的用户，不然会更新失败。

**第七步**：修改`com.alibaba.csp.sentinel.dashboard.controller.v2.FlowControllerV2`中`DynamicRuleProvider`和`DynamicRulePublisher`注入的Bean，改为上面我们编写的针对Apollo的实现：

```
@Autowired
@Qualifier("flowRuleApolloProvider")
private DynamicRuleProvider<List<FlowRuleEntity>> ruleProvider;
@Autowired
@Qualifier("flowRuleApolloPublisher")
private DynamicRulePublisher<List<FlowRuleEntity>> rulePublisher;
```

## 代码示例

本文介绍内容的客户端代码，示例读者可以通过查看下面仓库中的`alibaba-sentinel-dashboard-apollo`项目：

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
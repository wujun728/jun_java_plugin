# Spring Cloud实战小贴士：turbine如何聚合设置了context-path的hystrix数据

**原创**

 [2017-07-25](https://blog.didispace.com/spring-cloud-tips-4/)

 翟永超

 [Spring Cloud](https://blog.didispace.com/categories/Spring-Cloud/)

[【推荐】从业15年的架构师告诉你：如何落地微服务和云原生架构？](https://blog.didispace.com/how-to-implement-microservice-and-cloud-native-architecture/)

之前在[spring for all社区](http://spring4all.com/)看到这样一个问题：当actuator端点设置了context-path之后，turbine如何聚合数据？首先，我们要知道actuator端点设置了context-path是什么意思？也就是说，此时spring boot actuator的端点都有了一个前缀，比如：

```
management.context-path=/xxx
```

如果设置了上面的参数，那个对于收集hystrix数据的端点将变为：`/xxx/hystrix.stream`，如果我们还是拿上一篇[Spring Cloud构建微服务架构：Hystrix监控数据聚合【Dalston版】](http://blog.didispace.com/spring-cloud-starter-dalston-5-2/)中构建你的turbine应用，那么将会看到如下错误：

```
INFO 7812 --- [        Timer-0] c.n.t.monitor.instance.InstanceMonitor   : Url for host: http://172.15.0.18:9020/hystrix.stream default
ERROR 7812 --- [InstanceMonitor] c.n.t.monitor.instance.InstanceMonitor   : Could not initiate connection to host, giving up: [{"timestamp":1499941336284,"status":404,"error":"Not Found","message":"No message available","path":"/hystrix.stream"}]
WARN 7812 --- [InstanceMonitor] c.n.t.monitor.instance.InstanceMonitor   : Stopping InstanceMonitor for: 172.15.0.18:9020 default

com.netflix.turbine.monitor.instance.InstanceMonitor$MisconfiguredHostException: [{"timestamp":1499941336284,"status":404,"error":"Not Found","message":"No message available","path":"/hystrix.stream"}]
	at com.netflix.turbine.monitor.instance.InstanceMonitor.init(InstanceMonitor.java:318) ~[turbine-core-1.0.0.jar:na]
	at com.netflix.turbine.monitor.instance.InstanceMonitor.access$100(InstanceMonitor.java:103) ~[turbine-core-1.0.0.jar:na]
	at com.netflix.turbine.monitor.instance.InstanceMonitor$2.call(InstanceMonitor.java:235) [turbine-core-1.0.0.jar:na]
	at com.netflix.turbine.monitor.instance.InstanceMonitor$2.call(InstanceMonitor.java:229) [turbine-core-1.0.0.jar:na]
	at java.util.concurrent.FutureTask.run(FutureTask.java:266) [na:1.8.0_91]
	at java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1142) [na:1.8.0_91]
	at java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:617) [na:1.8.0_91]
	at java.lang.Thread.run(Thread.java:745) [na:1.8.0_91]
```

从上述错误中我们可以知道，turbine在收集的时候由于访问的是`/hystrix.stream`，而此时收集端点却是`/xxx/hystrix.stream`，所以报了404错误。

那么我们要如何解决呢？通过之前的配置内容，我们可能找不到相关的配置信息，所以只能遍历一下源码，最后找到这个类：`org.springframework.cloud.netflix.turbine.SpringClusterMonitor`。它的具体内容如下：

```
public static InstanceUrlClosure ClusterConfigBasedUrlClosure = new InstanceUrlClosure() {

	private final DynamicStringProperty defaultUrlClosureConfig = DynamicPropertyFactory
			    .getInstance().getStringProperty("turbine.instanceUrlSuffix",
				    	"hystrix.stream");
	private final DynamicBooleanProperty instanceInsertPort = DynamicPropertyFactory
				.getInstance().getBooleanProperty("turbine.instanceInsertPort", true);

	@Override
	public String getUrlPath(Instance host) {
		if (host.getCluster() == null) {
			throw new RuntimeException(
					"Host must have cluster name in order to use ClusterConfigBasedUrlClosure");
		}

		// find url
		String key = "turbine.instanceUrlSuffix." + host.getCluster();
		DynamicStringProperty urlClosureConfig = DynamicPropertyFactory.getInstance()
					.getStringProperty(key, null);
		String url = urlClosureConfig.get();
		if (url == null) {
			url = this.defaultUrlClosureConfig.get();
		}
		if (url == null) {
			throw new RuntimeException("Config property: "
					+ urlClosureConfig.getName() + " or "
					+ this.defaultUrlClosureConfig.getName() + " must be set");
		}

		// find port and scheme
		String port;
		String scheme;
		if (host.getAttributes().containsKey("securePort")) {
			port = host.getAttributes().get("securePort");
			scheme = "https";
		} else {
			port = host.getAttributes().get("port");
			scheme = "http";
		}
		if (host.getAttributes().containsKey("fusedHostPort")) {
			return String.format("%s://%s/%s", scheme, host.getAttributes().get("fusedHostPort"), url);
		}

		// determine if to insert port
		String insertPortKey = "turbine.instanceInsertPort." + host.getCluster();
		DynamicStringProperty insertPortProp = DynamicPropertyFactory.getInstance()
					.getStringProperty(insertPortKey, null);
		boolean insertPort;
		if (insertPortProp.get() == null) {
			insertPort = this.instanceInsertPort.get();
		}
		else {
			insertPort = Boolean.parseBoolean(insertPortProp.get());
		}

		// format url with port
		if (insertPort) {
			if (url.startsWith("/")) {
				url = url.substring(1);
			}
			if (port == null) {
				throw new RuntimeException(
						"Configured to use port, but port or securePort is not in host attributes");
			}

			return String.format("%s://%s:%s/%s", scheme, host.getHostname(), port, url);
		}

		//format url without port
		return scheme + "://" + host.getHostname() + url;
	}
};
```

从上述源码中，我们可以找到这个参数`turbine.instanceUrlSuffix`，由此该问题就迎刃而解了，我们只需要在turbine应用的配置文件中增加如下配置信息，就能正确的收集之前配置了`management.context-path=/xxx`的微服务的hystrix数据了。

```
turbine.instanceUrlSuffix=/xxx/hystrix.stream
```
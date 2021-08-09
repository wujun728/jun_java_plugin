# 使用Spring Boot Actuator、Jolokia和Grafana实现准实时监控

**原创**

 [2017-02-26](https://blog.didispace.com/spring-boot-jolokia-grafana-monitor/)

 翟永超

 [Spring Boot](https://blog.didispace.com/categories/Spring-Boot/)

[【推荐】从业15年的架构师告诉你：如何落地微服务和云原生架构？](https://blog.didispace.com/how-to-implement-microservice-and-cloud-native-architecture/)

> 由于最近在做监控方面的工作，因此也读了不少相关的经验分享。其中有这样一篇文章总结了一些基于Spring Boot的监控方案，因此翻译了一下，希望可以对大家有所帮助。
>
> 原文：[Near real-time monitoring charts with Spring Boot Actuator, Jolokia and Grafana](https://medium.com/@brunosimioni/near-real-time-monitoring-charts-with-spring-boot-actuator-jolokia-and-grafana-1ce267c50bcc#.il5xmlnv7)

Spring Boot Actuator通过`/metrics`端点，以开箱即用的方式为应用程序的性能指标与响应统计提供了一个非常友好的监控方式。

由于在集群化的弹性环境中，应用程序的节点可以增长、扩展，并由非常大量的应用实例所组成。对于孤立节点的监控可能即费力又没有什么实际效果。所以，使用基于时间序列的数据聚合工具将获得更好的效果。

本文的目标在于找出一种仅需要通过工具和配置的方式就能实现的解决方案，来对Spring Boot Metrics实现基于时间序列的监控。

像NewRelic, AppDynamics或DataDog这些APM系统都能很好地完成这样的任务，它们通过使用JVM和字节码工具来生成自己的指标、分析工具和相关事务。也可以通过使用`@Timed`注释方法来实现。但是，这些方法将忽略所有Spring Boot Actuator库所提供的可用资源。另外，使用这些方法还有一个与保留数据相关的问题，它们对于短时间窗口内的监控是相对模糊的。

[![NewRelic在1分钟时间窗口内被发现和检测的事务](https://blog.didispace.com/assets/1-W_O7bNYmgarjHNasow-PsQ.png)](https://blog.didispace.com/assets/1-W_O7bNYmgarjHNasow-PsQ.png)NewRelic在1分钟时间窗口内被发现和检测的事务

`spring-boot-admin` 可以作为另外一个备选方案，因为它可以连接到Spring Boot的实例、并且可以聚合节点等。但是， `/metrics` 端点并不是根据时间轴来进行监控的，同时在不同节点上的相同应用模块（水平扩展）也没有得到聚合。这意味着您将面对这两种情况：没有时间序列的监控数据、只有对孤立节点的监控数据快照。

[![Spring Boot Admin with metrics from Actuator: a snapshot of metrics data of a given application node](https://blog.didispace.com/assets/1-KK_x2uD66NIIIfmyFIraEg.png)](https://blog.didispace.com/assets/1-KK_x2uD66NIIIfmyFIraEg.png)Spring Boot Admin with metrics from Actuator: a snapshot of metrics data of a given application node

[![Spring Boot Admin with JMX and MBeans read data of a give application node](https://blog.didispace.com/assets/1-MztwgrZsF2wtXL_OMZCfdQ.png)](https://blog.didispace.com/assets/1-MztwgrZsF2wtXL_OMZCfdQ.png)Spring Boot Admin with JMX and MBeans read data of a give application node

`jconsole`和`visualvm`可能是另外一种选择，它们通过RMI直接连接到JMX节点。Actuator存储来自JMX的MBean内的Metrics数据。另外，通过使用 [Jolokia](https://jolokia.org/)，MBeans以RESTful HTTP端点的方式暴露，`/jolokia`。所以，相同的信息可以通过两个端点来获取：JMX MBean Metrics和Rest HTTP Jolokia端点。然而，这种方式存在同样的问题，它们直接连接到集群环境中的单个节点，另外还伴随着痛苦的老式RMI协议。

[![JConsole old-school JMX Metrics of a given application node](https://blog.didispace.com/assets/1-NOkLUyGMydiFYYotF2rKQ.png)](https://blog.didispace.com/assets/1-NOkLUyGMydiFYYotF2rKQ.png)JConsole old-school JMX Metrics of a given application node

[![VisualVM JMX Metrics of a give application node](https://blog.didispace.com/assets/1-mH5Wey1GDlCmDRdZPlHYtg.png)](https://blog.didispace.com/assets/1-mH5Wey1GDlCmDRdZPlHYtg.png)VisualVM JMX Metrics of a give application node

继续前进，我尝试了一些可能可以解决这些问题的现代化运维工具：

- [**Prometheus**](https://prometheus.io/): 由SoundCloud编写，它存储一系列的监控数据并赋予漂亮的图标展现。Prometheus Gauges和Actuator Metrics并不完全兼容，所以人们写了 [一个数据转换器](https://github.com/prometheus/client_java/pull/114)。你也可以配置Prometheus来收集JMX数据。
- [**Sensu**](https://sensuapp.org/): 作为Nagios和Zabbix的现代化替代品，它有一个插件可以直接连接到Spring Boot，[但是这个仓库最近已经不太更新了](https://github.com/sensu-plugins/sensu-plugins-springboot)，所以我决定放弃它。
- [**StatsD**](https://github.com/etsy/statsd): Spring Boot有一篇文章是[关于自定义导出数据给StatsD](http://docs.spring.io/spring-boot/docs/current-SNAPSHOT/reference/htmlsingle/#production-ready-metric-writers-export-to-statsd)。然而，你除了要为Spring Boot应用安装StatsD实例之外，还不得不实现一些存根来让它工作起来。
- [**Graphite**](http://graphiteapp.org/)**:** You got to be [a hero to install and get Graphite](https://graphite.readthedocs.io/en/latest/install.html) running. If you get there, [you can configure it along StatsD to get metrics working in a chart](https://matt.aimonetti.net/posts/2013/06/26/practical-guide-to-graphite-monitoring/).
- [**OpenTSDB**](http://opentsdb.net/)**:** Spring Boot有一篇文章[关于连接数据到OpenTSBD](http://docs.spring.io/spring-boot/docs/current-SNAPSHOT/reference/htmlsingle/#production-ready-metric-writers-export-to-open-tsdb). 然而，这种方式与StatsD类似，你必须实现和维护自定义的代码来让它工作起来。另外，OpenTSDB没有开箱即用的图形可视化工具。
- [**JMXTrans**](https://github.com/jmxtrans/jmxtrans): 可以用来提取数据并发送到其他的监控工具，它也需要具体的实现。
- [**Ganglia**](http://ganglia.info/): 也是基于JVM上的工具，记录所有Actuator资源。与之前所说的APM有相同问题。

经过一番研究，我发现了一个更好的解决方案：通过InfluxDB 和Telegraf实现，零编码，只需要通过一些正确的配置。

- [**Jolokia**](https://jolokia.org/): Spring Boot [认可使用Jolokia来通过HTTP导出export JMX数据](http://docs.spring.io/spring-boot/docs/current-SNAPSHOT/reference/htmlsingle/#production-ready-jolokia)。你只需要在工程类路径中增加一些依赖项，一切都是开箱即用的。不需要任何额外的实现。
- [**Telegraf**](https://www.influxdata.com/time-series-platform/telegraf/)**:** Telegraf支持通过整合Jolokia来集成JMX数据的收集。[它有一个预制的输入插件](https://github.com/influxdata/telegraf/tree/master/plugins/inputs/jolokia)，它是开箱即用的。不需要任何额外的实现。只需要做一些配置即可。
- [**InfluxDB**](https://www.influxdata.com/time-series-platform/influxdb/)**:** InfluxDB通过 [输出插件](https://github.com/influxdata/telegraf/tree/master/plugins/outputs/influxdb)从Telegraf接收指标数据，它是开箱即用的，不需要任何额外的实现。
- [**Grafana**](http://grafana.org/): Grafana通过[连接InfluxDB作为数据源](http://docs.grafana.org/datasources/influxdb/)来渲染图标。它是开箱即用的，不需要额外的实现。

简而言之，配置所有这些东西都非常的简单。

[![Spring Boot Actuator Raw Metrics](https://blog.didispace.com/assets/1-r252t1MBNRc3thU3DMRTcQ.png)](https://blog.didispace.com/assets/1-r252t1MBNRc3thU3DMRTcQ.png)Spring Boot Actuator Raw Metrics

[![Metrics sent by Telegraf to InfluxDB, collected by Jolokia and JMX over HTTP](https://blog.didispace.com/assets/1-XdrZlKh1Q2G0yd6xNkSrgQ.png)](https://blog.didispace.com/assets/1-XdrZlKh1Q2G0yd6xNkSrgQ.png)Metrics sent by Telegraf to InfluxDB, collected by Jolokia and JMX over HTTP

[![Grafana InfluxDB data source configuration](https://blog.didispace.com/assets/1-K8NTfF4Z2ms1YM_4cCjyvQ.png)](https://blog.didispace.com/assets/1-K8NTfF4Z2ms1YM_4cCjyvQ.png)Grafana InfluxDB data source configuration

[![Grafana Metric chart query and configuration: gauges of an API](https://blog.didispace.com/assets/1-ZoQcStClNQf7iz_cQNZHxA.png)](https://blog.didispace.com/assets/1-ZoQcStClNQf7iz_cQNZHxA.png)Grafana Metric chart query and configuration: gauges of an API
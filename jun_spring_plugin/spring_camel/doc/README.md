# jun_apache_camel集成了ApacheCamel的各种传送数据类型的Demo，稳稳的EIP
Apache Camel Demo (学习样例Demo)



Apache Camel 是一个基于Enterprise Integration Pattern(企业整合模式，简称EIP)的开源框架。EIP定义了一些不同应用系统之间的消息传输模型，包括常见的Point-to-Point，Pub/Sub模型。更多关于EIP的信息，可以参见 这里
 
Apache Camel主要提供了以下功能：
1，实现了EIP的大部分模式，如果你要在不同的应用系统之间以不同的方式传递消息，那么你可以从Apache Camel中找到解决反感。
2，提供了大量Component(组件)，每个组件都是一种消息中间件(或消息服务)的具体实现，每个消息中间件所用的协议都是不同的，因此，你可以通过多种不同的协议来完成消息传输。
3，允许用户定义灵活的路由规则，从这个角度来说，Apache Camel时一个规则引擎。
 
那么Apache Camel的应用场景有那些呢，这里列举一些：
 
1，消息汇聚，比如你有来自不同服务器的消息，有ActiveMQ，RabbitMQ，WebService等，你想把它们都存储到日志文件中，那么可以定义如下规则。
 

1 new RouteBuilder() {
2     @Override
3     public void configure() throws Exception {
4         from("amqp:queue:incoming").to("log:com.mycompany.log?level=DEBUG");
5         from("rabbitmq://localhost/A/routingKey=B").to("log:com.mycompany.log?level=DEBUG");
6         from("jetty:http://localhost:8080/myapp/myservice").to("log:com.mycompany.log?level=DEBUG");
7     }
8 }

 

from表示从这个endpoing取消息，to表示将消息发往这个endpoint，endpoint是消息地址，包含协议类型以及url。 

 
2，消息分发，分为两种，顺序分发和并行分发。顺序分发时，消息会先到到第一个endpoing，第一个endpoint处理完成后，再分发到下下个endpoint。如果第一个endpoing处理出现故障，那么消息不会被传到第二个endpoint。比如有如下规则:
from(“amqp:queue:order”).to(“uri:validateBean”, “uri:handleBean”, “uri:emailBean”);
这个规则是从order队列中取订单信息，然后依次验证订单，处理订单，并发送邮件通知用户。任何一个步骤出错，下一个步骤将不回执行。
 
并行分发是将得到的消息同时发送到不同的endpoint，没有先后顺序之分，各个endpoint处理消息也是独立的。如果将以上路由改成
from(“amqp:queue:order”).multicast().to(“uri:validateBean”, “uri:handleBean”, “uri:emailBean”);
那么消息就会同时发到to所对应的endpoint。




3，消息转换，比如想将xml数据转换成json数据，可以使用如下规则。
 
from("amqp:queue:order").process(new XmlToJsonProcessor()).to("bean:orderHandler");
其中XmlToJsonProcessor是自定义的类，继承org.apache.camel.Processor，用于将xml数据转换成json。

4，规则引擎，你可以使用Spring Xml, Groovy这类DSL来定义route，这样无需修改代码，就能达到修改业务逻辑的目的。
例如上边的规则，from(“amqp:queue:order”).to(“uri:validateBean”, “uri:handleBean”, “uri:emailBean”);使用Spring Xml定义如下：
 

<route>
        <from uri="amqp:queue:order"/>
        <multicast>
            <to uri="uri:validateBean"/>
            <to uri="uri:handleBean"/>
            <to uri="uri:emailBean"/>
        </multicast>
</route>

 
如果需要在处理完订单后添加日志，可以改称如下规则
 

<route>
        <from uri="amqp:queue:order"/>
        <multicast>
            <to uri="uri:validateBean"/>
            <to uri="uri:handleBean"/>
            <to uri="log:com.mycompany.log?level=INFO"/>
            <to uri="uri:emailBean"/>
        </multicast>
</route>

 

另外camel提供了大量的内置Processor，用于逻辑运算，过滤等，这样更加容易定移除灵活的route，例如：
 
from("amqp:queue:order").filter(header("foo").isEqualTo("bar")).choice()
    .when(xpath("/person/city = &#39;London&#39;"))
        .to("file:target/messages/uk")
    .otherwise()
        .to("file:target/messages/others");
 

这条规则先对订单进行过滤，只处理header中foo的值为bar的订单，然后根据用户的城市进行将订单传给不同的endpoint。
 
 
Apache Camel的应用场景有很多，这里只是大致列举了几种。如果还不是很理解Camel的应用场景，你可以先到 Enterprise Integration Pattern(企业整合模式，简称EIP)中去找对应的scenario，点击每种scenario下的链接，就能找到Camel对应的实现。

Apache Camel 是一个非常强大的基于规则的路由以及媒介引擎，该引擎提供了一个基于POJO的 企业应用模式(Enterprise Integration Patterns)的实现，你可以采用其异常强大且十分易用的API (可以说是一种Java的领域定义语言 Domain Specific Language)来配置其路由或者中介的规则。 通过这种领域定义语言，你可以在你的IDE中用简单的Java Code就可以写出一个类型安全并具有一定智能的规则描述文件。这与那种复杂的XML配置相比极大简化了规则定义开发。 当然Apache Camel也提供了一个对Spring 配置文件的支持。

Apache Camel 采用URI来描述各种组件，这样你可以很方便地与各种传输或者消息模块进行交互，其中包含的模块有  HTTP, ActiveMQ, JMS, JBI, SCA, MINA or CXF Bus API。 这些模块是采用可插拔的方式进行工作的。Apache Camel的核心十分小巧你可以很容易地将其集成在各种Java应用中。


该项目中包含
Apache Camel - FTP组件<br>
Apache Camel - CXF组件<br>
Apache Camel - JMS/ActiveMQ组件<br>
Apache Camel - Jetty组件<br>
Apache Camel - Timer组件<br>
Apache Camel - JDBC组件<br>
Camel 各种路由...

参考的资料请自取：<br>
[Red_Hat_Fuse-7.0-Apache_Camel_Development_Guide-en-US](https://download.csdn.net/download/simba_cheng/10575845)<br>
[Apache Camel Developer's Cookbook](https://download.csdn.net/download/simba_cheng/10574302)<br>


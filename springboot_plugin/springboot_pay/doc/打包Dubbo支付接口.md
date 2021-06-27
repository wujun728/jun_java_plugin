如何调用支付服务，当然是通过 RPC 了。

首先我们把接口先打个包，比如 itstyle_pay.jar，然后把包打入本地私服：


```
mvn install:install-file -Dfile=itstyle_pay.jar -DgroupId=vip.52itstyle -DartifactId=itstyle_pay -Dversion=1.0.0 -Dpackaging=jar
```

消费者引用支付服务：


```
<dependency>
	<groupId>vip.itstyle</groupId>
	<artifactId>itstyle_pay</artifactId>
	<version>1.0.0</version>
</dependency>
```

代码注入：




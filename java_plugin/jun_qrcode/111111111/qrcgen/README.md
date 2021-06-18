![输入图片说明](https://git.oschina.net/uploads/images/2017/0808/221921_8d812650_752554.png "codegen_logo.png")

### QrcGen

一个简单易用的二维码生成器。

 **运行环境：**

JDK_1.8及以上，由于此生成器引用了部分JDK_1.8的特性， 如需兼容JDK_1.7，可以通过修改少许代码实现。 :smile: 

 **Maven坐标：** 

```
<dependency>
    <groupId>org.iherus</groupId>
    <artifactId>qrcgen</artifactId>
    <version>1.2.1</version>
</dependency>
```
 **QrcGen接口及使用说明：** 

eg_1：默认配置

-->writeToFile:

```
String content = "https://baike.baidu.com/item/%E5%97%B7%E5%A4%A7%E5%96%B5/19817560?fr=aladdin";

new SimpleQrcodeGenerator().generate(content).toFile("F:\\AodaCat_default.png");

```
-->writeToStream:

```
OutputStream out = null;

try {
    out = new FileOutputStream("F:\\AodaCat_default.png");
    new SimpleQrcodeGenerator().generate(content).toStream(out);
    
} finally {
    IOUtils.closeQuietly(out);
}

```
效果如下：

![输入图片说明](https://git.oschina.net/uploads/images/2017/0808/233329_4f7ffb09_752554.png "AodaCat_default.png")


eg_2：本地 Logo

```

String content = "https://baike.baidu.com/item/%E5%97%B7%E5%A4%A7%E5%96%B5/19817560?fr=aladdin";

new SimpleQrcodeGenerator().setLogo("F:\\AodaCat-1.png").generate(content).toFile("F:\\AodaCat_local_logo.png");

```
效果如下：

![输入图片说明](https://git.oschina.net/uploads/images/2017/0808/233641_4ef20bed_752554.png "AodaCat_local_logo.png")

eg_3：在线 Logo

```
String content = "https://www.apple.com/cn/";
		
String logoUrl = "http://www.demlution.com/site_media/media/photos/2014/11/06/3JmYoueyyxS4q4FcxcavgJ.jpg";
		
new SimpleQrcodeGenerator().setRemoteLogo(logoUrl).generate(content).toFile("F:\\Apple_remote_logo.png");

```

效果如下：

![输入图片说明](https://git.oschina.net/uploads/images/2017/0808/234031_e27eac0f_752554.png "Apple_remote_logo.png")

eg_4：自定义配置

```
QrcodeConfig config = new QrcodeConfig()
		.setBorderSize(2)
		.setPadding(10)
		.setMasterColor("#00BFFF")
		.setLogoBorderColor("#B0C4DE");

String content = "https://baike.baidu.com/item/%E5%97%B7%E5%A4%A7%E5%96%B5/19817560?fr=aladdin";

new SimpleQrcodeGenerator(config).setLogo("F:\\AodaCat-1.png").generate(content).toFile("F:\\AodaCat_custom.png");

```

效果如下：

![输入图片说明](https://git.oschina.net/uploads/images/2017/0808/234624_1c870de9_752554.png "AodaCat_custom.png")


 **更多例子请看：** 

 https://git.oschina.net/iherus/qrcgen/blob/master/qrcgen/src/test/java/org/iherus/example/TestQrGen.java

 **Features** 

欢迎提出更好的意见，帮助完善 QrcGen。

 **Copyright** 

Apache License, Version 2.0
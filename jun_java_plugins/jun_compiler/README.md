# 第一部分 简介
`JCompiler`是用`Java` 编写的用来将`Java源代码`动态编译为`字节码`的辅助工具，开发者可以动态生成源代码并在内存中将其进行编译并获得编译后的类对象。

# 第二部分 开始使用
使用`JCompiler`可以直接下载源代码编译或者下载已经编译的`jar`文件，如果您是使用`maven`来构建项目，也可以直接在`pom.xml`中添加`JCompiler`的坐标：

[![Maven central](https://maven-badges.herokuapp.com/maven-central/com.jianggujin/JCompiler/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.jianggujin/JCompiler)

```xml
<!-- http://mvnrepository.com/artifact/com.jianggujin/JCompiler -->
<dependency>
    <groupId>com.jianggujin</groupId>
    <artifactId>JCompiler</artifactId>
    <version>最新版本</version>
</dependency>
```

最新的版本可以从[Maven仓库](http://mvnrepository.com/artifact/com.jianggujin/JCompiler)或者[码云](https://gitee.com/jianggujin/JCompiler)获取。

如果使用快照`SNAPSHOT`版本需要添加仓库，且版本号为快照版本 [点击查看最新快照版本号](https://oss.sonatype.org/content/repositories/snapshots/com/jianggujin/JCompiler/)

```xml
<repository>
    <id>snapshots</id>
    <url>https://oss.sonatype.org/content/repositories/snapshots/</url>
</repository>
```

一个例子：

假设我们有一个接口`Parent`，其代码如下：

```java
public interface Parent {
    void say();
}
```

> 定义接口是为了动态生成的类对象方便调用，也可以通过其他方式，此处仅仅是为了演示

我们可以准备一个代码片段如下：

```java
package com.jianggujin.compiler.test;
public class Test implements com.jianggujin.compiler.test.CompileTest.Parent {
    public void say() {
        System.out.println("this is dynamic compile class.");
    }
}
```

> 此处的代码片段为字符串变量`content`的值，并非直接编写的源代码

然后我们仅需要简单的几行代码即可实现对上面的代码片段进行编译执行：

```java
JMemoryJavaCompiler compiler = new JMemoryJavaCompiler();
JCompileResult result = compiler.compile(content);
System.out.println(result);
if (result.isSuccess()) {
    ((Parent) result.newInstance()).say();
}
compiler.close();
```

> 完整代码参见单元测试代码：`src/test/java/com/jianggujin/compiler/test/CompileTest.java`
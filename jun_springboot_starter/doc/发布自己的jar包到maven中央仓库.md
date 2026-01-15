# 发布自己的jar包到maven中央仓库

 

**简介：** 手把手教你发布自己的jar包到maven中央仓库

## 背景

兄弟们，最近在搞一个Tile38空间数据库的相关的项目，为了在java端更好地使用Tile38空间数据库，我自己干了两周用java语言撸了一个jtile38客户端（ps：主要是目前没有一个完整好用的Tile38客户端），今天正式发布1.0.0版本。

为了能够更好地让这个小工具服务于更多的开发人员，我花了点时间把它上传到maven中央仓库了，大家有需要的可以自取。

下面讲解一下如何把自己的jar包发布到maven中央仓库。

## 注册Sonatype账号

> 注意：这一步只需要做一次，以后发布别的jar包都不需要，除非更换groupId

注册地址：[issues.sonatype.org/secure/Sign…](https://link.juejin.cn/?spm=a2c6h.12873639.article-detail.5.ccb6ba99Yt6LAI&target=https%3A%2F%2Fissues.sonatype.org%2Fsecure%2FSignup!default.jspa)

注册完毕后，需要提交工单由工作人员审核



![1cb22c9cdb0c4211b74c6a5b7457c089_tplv-k3u1fbpfcp-zoom-in-crop-mark_4536_0_0_0.png](https://ucc.alicdn.com/pic/developer-ecology/dc73hre37f4my_9e544910357f403e85f22ddfefff6a23.png)

![730de71e6cc64500898ca95e08185ba9_tplv-k3u1fbpfcp-zoom-in-crop-mark_4536_0_0_0.png](https://ucc.alicdn.com/pic/developer-ecology/dc73hre37f4my_b4daa886b92249f0afdab61618467a57.png)

按照以上两步完成后，就可以等待工作人员审批了。

> 【注意】项目要提前上传到【Project URL】上面，因为工作人员会去检查。

如果是个人域名或公司域名的项目，那么需要验证你对指定域名的所有权；比如`group id`是`cn.baidu.xxx`，那么在工单的后续回复中会需要你去该域名对应的服务器上添加一条指定的`TXT`解析；

## GPG签名

> 注意：这一步也只要做一次，除非你的GPG签名过期了，或者被你删掉了，要么就是你换电脑了

兄弟们，废话不多说，直接上链接：[www.gnupg.org/download/](https://link.juejin.cn/?spm=a2c6h.12873639.article-detail.6.ccb6ba99Yt6LAI&target=https%3A%2F%2Fwww.gnupg.org%2Fdownload%2F)

咱们先下载一个gpg签名工具，大家根据自己的操作系统来；安装完毕后，直接在控制台输入命令：

```
gpg --generate-key
复制代码
```

后续就根据自己情况输入，最后回车会生成公钥和私钥，我们接下来需要把生成的公钥上传到公共服务器供 sonatype 验证。

可以通过以下命令把公钥发送给公共服务器：

```
gpg --keyserver pgp.mit.edu --send-keys [公钥]
复制代码
```

或

```
gpg --keyserver keyserver.ubuntu.com --send-keys [公钥]
复制代码
```

或

```
gpg --keyserver keys.openpgp.org --send-keys [公钥]
复制代码
```

以上分别为向三个公共服务器发生公钥，只要其中一个成功即可。

## settings.xml配置

接下来，我们需要在`~/.m2/settings.xml`文件中添加以下配置：

```
<profiles>
    <profile>
      <id>oss</id>
      <properties>
        <gpg.executable>gpg</gpg.executable>
        <gpg.passphrase>签名密码</gpg.passphrase>
      </properties>
    </profile>
  </profiles>
<servers>
    <server>
      <id>oss</id>
      <username>ossrh注册的账号</username>
      <password>ossrh注册的密码</password>
    </server>
</servers>
复制代码
```

## pom.xml配置

> 这一步开始，每次发布相同group id的jar包都需要操作一遍。

`settings.xml`文件修改完毕，接下来我们还需要在项目中的`pom.xml`文件中也添加一些配置。

```
<groupId>com.github.zw201913</groupId>
    <artifactId>jtile38</artifactId>
    <version>1.0.0</version>
    <name>jtile38</name>
    <description>java client for tile38</description>
    <url>https://github.com/zw201913/jtile38</url>
<!--   license信息         -->
    <licenses>
        <license>
            <name>The Apache License, Version 2.0</name>
            <url>http://www.apache.org/licenses/LICENSE-2.0.txt</url>
        </license>
    </licenses>
<!--   开发人员信息         -->
    <developers>
        <developer>
            <name>姓名</name>
            <email>邮箱</email>
            <roles>
                <role>developer</role>
            </roles>
            <timezone>+8</timezone>
        </developer>
    </developers>
<!--   项目仓库信息         -->
    <scm>
        <connection>scm:git:https://github.com/zw201913/jtile38.git</connection>
       <developerConnection>scm:git:https://github.com/zw201913/jtile38.git</developerConnection>
        <url>https://github.com/zw201913/jtile38</url>
        <tag>v${project.version}</tag>
    </scm>
<!--   指定打包上传的目标url         -->
    <distributionManagement>
        <snapshotRepository>
            <!--   这个id需要和settings.xml里面的id一致         -->
            <id>oss</id>
            <url>https://oss.sonatype.org/content/repositories/snapshots/</url>
        </snapshotRepository>
        <repository>
          <!--   这个id需要和settings.xml里面的id一致         -->
            <id>oss</id>
            <url>https://oss.sonatype.org/service/local/staging/deploy/maven2/</url>
        </repository>
    </distributionManagement>
<!-- 编译工具 -->
    <build>
        <plugins>
            <!-- Source -->
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-source-plugin</artifactId>
                <version>2.2.1</version>
                <executions>
                    <execution>
                        <phase>package</phase>
                        <goals>
                            <goal>jar-no-fork</goal>
                        </goals>
                    </execution>
                </executions>
            </plugin>
            <!-- Javadoc工具 -->
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-javadoc-plugin</artifactId>
                <version>2.10.4</version>
                <configuration>
                    <additionalJOptions>
                        <additionalJOption>-Xdoclint:none</additionalJOption>
                    </additionalJOptions>
                </configuration>
                <executions>
                    <execution>
                        <phase>package</phase>
                        <goals>
                            <goal>jar</goal>
                        </goals>
                    </execution>
                </executions>
            </plugin>
            <!-- GPG -->
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-gpg-plugin</artifactId>
                <version>1.6</version>
                <configuration>
                    <gpgArguments>
                        <arg>--pinentry-mode</arg>
                        <arg>loopback</arg>
                    </gpgArguments>
                </configuration>
                <executions>
                    <execution>
                        <id>sign-artifacts</id>
                        <phase>verify</phase>
                        <goals>
                            <goal>sign</goal>
                        </goals>
                    </execution>
                </executions>
            </plugin>
        </plugins>
    </build>
复制代码
```

## 打包发布

> 注意：每次发布相同group id的jar包都需要操作的步骤。

在所有的配置都编写完毕后，我们需要在项目路径下开启终端执行以下命令：

```
mvn clean deploy
复制代码
```

该命令执行成功后，我们的jar包就上传到`Staging Repositories`([oss.sonatype.org/#](https://link.juejin.cn/?target=https%3A%2F%2Foss.sonatype.org%2F%23))；如果该命令执行失败，想要查看是什么原因导致失败的，可以使用`mvn clean deploy +X`查看打包发布的详细过程，并显示报错信息；

命令执行完毕后，我们需要登陆[oss.sonatype.org/](https://link.juejin.cn/?spm=a2c6h.12873639.article-detail.8.ccb6ba99Yt6LAI&target=https%3A%2F%2Foss.sonatype.org%2F) 上面查看我们上传到`Staging Repositories`中的jar包：



![cd47b802919744bba3a79986db530221_tplv-k3u1fbpfcp-zoom-in-crop-mark_4536_0_0_0.png](https://ucc.alicdn.com/pic/developer-ecology/dc73hre37f4my_ca3ba53294d04491936b96875a537d0c.png)

- Close目标jar包

![f63b55be9dce4cc2a15842485f448113_tplv-k3u1fbpfcp-zoom-in-crop-mark_4536_0_0_0.png](https://ucc.alicdn.com/pic/developer-ecology/dc73hre37f4my_aefb8fa376ad4400b687990a5e65fb04.png)

点击Close需要填写描述信息，这个自己根据情况写

- 查看进度

咱们刚刚的Close操作，会触发一个工作流来校验上传的jar包，咱们也可以看看校验的进度，时间一般就一两分钟：



![54d4a75b02174cc08bd4afb328280267_tplv-k3u1fbpfcp-zoom-in-crop-mark_4536_0_0_0.png](https://ucc.alicdn.com/pic/developer-ecology/dc73hre37f4my_871bbb0a2ec6489f9072639ad183f84c.png)

- 正式发布jar包

![12c55436b1094135a89188ada834d6e6_tplv-k3u1fbpfcp-zoom-in-crop-mark_4536_0_0_0.png](https://ucc.alicdn.com/pic/developer-ecology/dc73hre37f4my_a8c185a2f14c40209ad063bbda883b20.png)

至此，打包发布的全部流程就完毕了。想要在[maven中央仓库](https://link.juejin.cn/?target=https%3A%2F%2Fmvnrepository.com%2F)中搜索到刚刚发布的jar包还需要30分钟到4个小时不等。
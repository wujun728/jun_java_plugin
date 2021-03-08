##创建smart-boot工程
如果您想将smartboot用于自己的项目中,直接下载smartboot源码并不可取，因为还得将包名以及pom.xml文件中的groupId、artifactId根据您的实际情况做修改。

为了提供更好的使用体验，我们特地制作了[smartboot-archetype](http://mvnrepository.com/artifact/org.smartboot.maven.archetype/smartboot-archetype)（如果您不知道什么是archetype,推荐先去[Maven](http://maven.apache.org/guides/introduction/introduction-to-archetypes.html)官网了解一下）

言归正传，构建一个smartboot工程只需执行一个maven命令即可。

	mvn -X archetype:generate -DarchetypeGroupId=org.smartboot.maven.archetype -DarchetypeArtifactId=smartboot-archetype -DarchetypeVersion=1.0.1 -DarchetypeCatalog=remote -DgroupId=com.test -DartifactId=hello-world -Dversion=1.0.0 -Dpackage=com.test

参数说明:

- -DarchetypeGroupId：脚手架groupId,固定为`org.smartboot.maven.archetype` 
- -DarchetypeArtifactId：脚手架artifactID，固定为`smartboot-archetype` 
- -DarchetypeVersion：脚手架版本号，设置为`1.0.1` 
- -DarchetypeCatalog：默认值:remote、local，标识脚手架来源为加载远程中央仓库或者调用本地仓库		
- -DgroupId：即将创建的工程groupId，例如：`com.test`
- -DartifactId：即将创建的工程artifactId，例如：`hello-world`
- -Dversion：即将创建的工程版本号，例如：`1.0.0`
- -Dpackage：即将创建的工程package,通常与groupId一致
	
>######上述所列参数皆为必填项

>######工程创建完成后，请打开facade模块下的pom.xml文件，并手动删除\<parent>标签的配置。
#spring-demo(在演示平台运行java spring的maven示例项目)
####
##响应同事提议，写了一个spring框架的maven示例项目，基于spring4.1，采用spring-jdbc处理数据库调用，采用spring web mvc应对数据展现和支持restful接口。演示在mopass平台上运行典型java maven项目，并连接mopass平台数据库
##1.配置数据库：
###点击演示平台-->服务管理-->添加服务-->选择mysql并命名你的数据库服务-->服务管理-->绑定该服务-->查看服务参数找到数据库名-->url/用户名/密码-->配置到spring datasourceconfig配置类-->完成（绑定你的mysql服务后还可以点击管理，即可运维管理该mopass数据库）
##2.pom.xml
###您只需要把依赖写入根目录下的pom.xml，并指定打包方式为war，然后push你的源码。点击mopass部署，部署过程中，mopass就会解析pom中的maven依赖并自动获取依赖，编译打包你的源码为war包，放到mopass的web服务器tomcat或jetty下。
##3.启动应用
###这时你再点击运行或重新运行，mopass将为你启动web服务器，运行war包，数十秒后，你就可以访问mopass为你定制的服务地址啦:http://springblog.oschina.mopaas.com

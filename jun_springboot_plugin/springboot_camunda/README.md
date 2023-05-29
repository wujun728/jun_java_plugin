# Camunda BPM 与 Spring Boot 集成示例

本示例是根据 [camunda.org](http://camunda.org/get-started/spring-boot.html)官网Spring Boot集成入门指引创建的。

##本示例组件

1、Spring-Boot:  (v2.1.1.RELEASE)
 
2、Camunda BPM: (v7.10.0)

3、Camunda BPM Spring Boot Starter: (v3.2.1)

4、Spring Clound： (Greenwich.RELEASE)

5、JDK: (v1.8)

6、Mysql: (v5.7)

7、其他见pom.xml

##使用步骤

1、通过 git clone https://github.com/skayliu/CamundaSpringBootDemo.git 或https://gitee.com/skay463/CamundaSpringBootDemo.git下载本代码，然后导入Eclipse或IDEA中；

2、创建camunda_demo数据库，修改application.yml中的mysql连接配置或应用服务的端口，默认为8080；

3、通过mvn spring-boot:run 启动应用，应用启动时会自动创建数据库表并进行初始化

4、打开浏览器并访问 http://localhost:8080，使用kermit/superSecret登录Camunda Webapp，里边有Admin、Cockpit、TaskList应用，在Admin中添加用户

5、打开浏览器并访问 http://localhost:8080/swagger-ui.html，测试流程使用接口示例;

6、打开浏览器并访问 http://localhost:8080/cmaunda-swagger-ui.html，查看原生Camunda REST API;

##流程建模

1、打开浏览器并访问 http://localhost:8080/modeler.html，是WEB版流程模型编辑器示例，编辑好流程放到/src/main/resources/下，待自动重启好后，流程就可以使用了

2、PC端的请使用[Camunda Modeler](https://docs.camunda.org/manual/latest/modeler/camunda-modeler/)，创建好模型后使用工具栏最右边的按钮打开部署窗口，URL中输入：http://localhost:8080/engine-rest/engine/default/deployment/create，填写Name流程名称，和Authentication使用http basic并输入kermit/superSecret，然后执行Deploy部署流程

3、登录Cockpit查看已部署的流程

##测试接口

1、SimpleProcessHandlerImpl 是使用Java API 实现的流程操作功能

2、SimpleRestProcessHandlerImpl 使用REST API 实现的流程操作功能

##引擎配置

在/src/main/resources/META-INF/的processes.xml，为空时,默认内容如下，详细请查看[Configure Process Engine in the processes.xml](https://docs.camunda.org/manual/latest/user-guide/process-engine/process-engine-bootstrapping/#configure-process-engine-in-the-processes-xml)


```
<process-application
  xmlns="http://www.camunda.org/schema/1.0/ProcessApplication"
  xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">

  <process-archive>
    <process-engine>default</process-engine>
    <properties>
      <property name="isDeleteUponUndeploy">false</property>
      <property name="isScanForProcessDefinitions">true</property>
    </properties>
  </process-archive>
  
</process-application>

```

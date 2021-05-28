
# springboot-plus

一个基于SpringBoot 2 的管理后台系统,有数十个基于此的商业应用，包含了用户管理，组织机构管理，角色管理，功能点管理，菜单管理，权限分配，数据权限分配，代码生成等功能
相比其他开源的后台开发平台脚手架，SpringBoot-Plus 使用简单，可以轻易完成中型，大型系统开发。同时技术栈较为简单

如何判断一个开源开发平台适合自己

* 要明白单体系统，系统拆分，微服务三个不同构建开发平台方式，plus支持单体和系统拆分，一般而言，后台管理系统适合单体和系统拆分。微服务并不适合系统管理，以我知道的互联网大厂，央企后台管理系统，还是以前俩个为多
* 你需要的是技术框架还是开发平台，技术框架就是技术堆砌，开发平台必须具备一定复杂基础业务功能
* 看权限模型，支持功能权限和数据权限。plus具备强大的功能权限和数据权限，且可以扩展n种数据权限
* 看用户是否能属于多个部门，用户兼职情况很常见
* 看数据字典是否支持级联，数据字典级联太常见了，平台需要提供数据和前端的支持。puls系统支持
* 看代码生成是否支持预览，为什么要预览，因为生成会覆盖，预览可以修改已经生成的代码 

Plus系统是一个使用简单，功能较为复杂的开源系统，已经数十家商业公司采用

系统基于Spring Boot2.1技术，前端采用了Layui2.4。数据库以MySQL/Oracle/Postgres/SQLServer为实例，理论上是跨数据库平台.

基本技术栈来源于我为电子工业出版社编写的的[<<Spring Boot 2 精髓 >>](https://item.jd.com/12214143.html) (这本书每一章也有各种例子，但Springboot-plus 更偏向于应用而不是教学)
该书的第二版电子版可以可以在[看云广场购买](https://www.kancloud.cn/xiandafu/springboot2-in-practice/), 包含基础篇，分布式篇和微服务篇，第二版也包含一章说明Plus系统

> 我的新书，程序员性能优化，程序装B宝典[《Java系统性能优化》](https://item.jd.com/12742086.html)，可以在京东上购买了


当前版本:1.3.0

技术交流群：252010126

免费视频介绍：https://pan.baidu.com/s/1dFPoaT7



![](https://oscimg.oschina.net/oscnet/ed05b8bb68eb40a3c55e19557e408fb07fb.jpg)

开源地址：https://gitee.com/xiandafu/springboot-plus

视频介绍：https://pan.baidu.com/s/1dFPoaT7



![doc/readme/user.png](doc/readme/user.png)
![doc/readme/user.png](doc/readme/role.png)
![doc/readme/user.png](doc/readme/data.png)
![doc/readme/user.png](doc/readme/codePorject.png)
![doc/readme/user.png](doc/readme/codeconfig.png)
![doc/readme/user.png](doc/readme/codegen.png)
![doc/readme/user.png](doc/readme/codegen2.png)
![doc/readme/user.png](doc/readme/excelExport.png)

# 1 使用说明

## 1.1 安装说明

> 建议在彻底熟悉plus系统之前，先暂时不要修改其他配置选项，免得系统无法访问
>
> 本系统基于Spring Boot 2 ，因此请务必使用JDK8，且打开编译选项[parameters(点击了解parameters)](http://www.mamicode.com/info-detail-2162647.html),<u> 并重新编译工程，如果你没有使用Java8的 parameters 特性，系统不能正常使用</u>



从Git上获取代码后，通过IDE导入此Maven工程，包含俩个子工程

* admin-core  ，核心包，包含了缓存，数据权限，公用的JS和HTML页面。
* admin-console, 系统管理功能，包含了用户，组织机构，角色，权限，数据权限，代码生成等管理功能

com.ibeetl.admin.CosonleApplication 是系统启动类，在admin-console包下,在运行这个之前，还需要初始化数据库，位于doc/starter-mysql.sql,目前只提供mysql, oracle, postgresql脚本。理论上支持所有数据库

还需要修改SpringBoot配置文件application.properties,修改你的数据库地址和访问用户

~~~properties
spring.datasource.baseDataSource.url=jdbc:mysql://127.0.0.1:3306/starter?useUnicode=true&characterEncoding=UTF-8&serverTimezone=GMT%2B8&useSSL=false&useInformationSchema=true
spring.datasource.baseDataSource.username=root
spring.datasource.baseDataSource.password=123456
spring.datasource.baseDataSource.driver-class-name=com.mysql.cj.jdbc.Driver

~~~

运行CosonleApplication，然后访问http://127.0.0.1:8080/  输入admin/123456 则可以直接登录进入管理系统

如果成功启动后运行报错：变量userId未定义，位于第6行，那是因为你没有启用[parameters](http://www.mamicode.com/info-detail-2162647.html)，启用后，需要clean&build整个工程



> 微信扫描付费查看安装和子系统生成视频（约25分钟）
>
> ![doc/readme/user.png](doc/readme/pay-install.png)



## 1.2 创建子系统

SpringBoot-plus 是一个适合大系统拆分成小系统的架构，或者是一个微服务系统，因此，如果你需要创建自己的业务系统，比如，一个CMS子系统，建议你不要在SpringBoot-Plus 添加代码，应该是新建立一个maven工程，依赖admin-core，或者依赖admin-console（如果你有后台管理需求，通常都有，但不是必须的）

创建子系统，可以进入代码生成>子系统生成， 输入maven项目路径，还有包名，就可以直接生成一个可运行的基于SpringBoot-Plus 的子系统,所有代码可以在个项目里些完成，直接运行MainApplication，

~~~java
@SpringBootApplication
@EnableCaching
@ComponentScan(basePackages= {"com.corp.xxx","com.ibeetl.admin"})
public class MainApplication  extends SpringBootServletInitializer implements WebApplicationInitializer {
	
    public static void main(String[] args) {
    	
    	SpringApplication.run(MainApplication.class, args);
    }


}	
~~~

子系统包含了admin-core和admin-console, 因此你可以直接在子系统里使用core和console提供的所有功能，通过子系统的console功能的代码生成来完成进一步开发

子系统可以单独运行和维护，也可以集成到nginx后构成一个庞大的企业应用系统

### 1.2.1 配置子系统

子系统不需要做任何配置即可在IDE里直接运行，如果你想打包jar方式运行，则需要添加

~~~xml
<build>
    <plugins>
        <plugin>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-maven-plugin</artifactId>
        </plugin>
    </plugins>
</build>
~~~

如果你想打包成war放到tomcat下运行，需要修改maven打包为war

~~~xmml
<packaging>war</packaging>
~~~

### 1.2.2 菜单系统

系统默认提供三种类型菜单
* 系统级菜单，出现在页面顶部，表示一个子系统
* 导航菜单，出现在页面左边，点击导航将打开其下所有菜单
* 菜单，点开菜单将定位到页面，菜单必须关联到一个功能点。

建议新建立一个子系统来放置新功能

SpringPlus-Boot 并非以菜单或者按钮来组织整个系统，而是以功能点来组织整个系统提供的功能。如果要使得菜单生效，你必须要先常见一个功能点并且功能点有一个访问地址，然后将此菜单关联到这个功能点

> SpringBoot-Plus 先建立功能点是个好习惯，功能点被组织成一颗树，代表了系统应该提供功能的功能，我们看代码就会看到，功能点跟菜单，跟权限，和数据权限都有密切关系


###  1.2.2 添加代码

可以参考1.3业务代码生成生成初始化的代码，业务代码生成了14个文件，包含前后端所有代码，可以通过生成来了解代码习作规范






## 1.3 业务代码生成

在介绍如何利用Plus开发系统之前，先介绍代码生成功能，此功能可以生成前后端代码总计14个文件，你可以通过预览功能了解如何开发这个系统

![doc/readme/user.png](doc/readme/codeconfig.png)



代码生成针对表进行代码生成，包括JS，JAVA，SQL和HTML，可以通过预览功能直接预览。在生成代码到本地前，有些参数需要修改，否则，代码生成后显示的都是英文

* 显示字段 ： 当此实体显示在任何地方的时候，能代表此实体的名称，比如用户名，组织机构名
* 变量名：可以自己设定一个较短的名字，此变量名会用于前后端的变量
* urlBase：你规划的子系统，最后访问路径是urlBase+变量名字
* system: 存放sql目录的的名称

其他修改的地方有

是否包含导入导出，如果选择，则会生成导入导出的代码，导入导出模板则需要参考已有功能(比如数据字典)来完成

是否包含附件管理，如果选择，则业务对象可以关联一组附件，比如客户关联一组附件，或者申请信息关联一组附件。

字段信息的显示名字，这个用于前端列表，表单的显示，应当输入中文名字

作为搜索，可以勾选几个搜索条件，系统自动生成一个搜索配置类

如果字段关联数据字典，那么设置一个数据字典，这样，生成的界面将会变成一个下拉列表



### 1.3.1 前端代码

前端代码采用了layui的JS框架，使用了按需加载的方式，文档参考 http://www.layui.com/doc/base/infrastructure.html.

* index.js: 系统入口JS，包含了查询和表格
* add.js : 新增操作的所有JS
* edit.js: 编辑操作的所有JS
* del.js: 删除操作的所有JS

基础JS

* Common.js: 封装了通常JS功能，如jquery的post方法，layui的窗口方法
* Lib.js  封装了业务相关方法，如submitForm，loadOrgPanel等方法

### 1.3.2  HTML代码

页面采用layui，文档参考 http://www.layui.com/demo/

模板语言了使用Beetl，文档参考ibeetl.com

* index.html: 功能首页
* add.html: 新增首页
* edit.html: 编辑操作首页

> 采用layui的好处是自带了页面和组件还有JS的管理，能完成大多数业务需求


基础UI组件：

* orgInput.tag.html 组织机构输入框
* simpleDictSelect.tag.html 字典下拉列表
* simpleDataSelect.tag   包含key-value的下拉列表
* searchForm.tag.html  通用搜索表单
* submitButtons.tag.html 提交按钮
* accessButton.tag.html  普通按钮（含权限）
* attachment.tag.html   附件管理组件
* ....



# 2 单体系统，系统拆分和微服务

plus是一个适合单体系统，系统拆分的java快速开发平台，也可以经过改造成微服务平台(以前做一个版本，但觉得plus应该聚焦系统核心，而不是简单堆砌功能，所以放弃了)

以下是单体系统，小系统，和微服务的区别



单体系统是一种常见系统设计方式，也是这十几年年来最主要的设计方式。单体系统的所有功能都在一个工程里，打成一个war包，部署。这样有如下明显好处

- 单体系统开发方式简单，我们从刚开始学习编程，就是完成的单体系统，开发人员只要集中精力开发当前工程
- 容易修改，如果需要修改任何功能，都非常方便，只需要修改一个工程范围的代码
- 测试简单，单体系统测试不需要考虑别的系统，避免本书下册要提到的各种REST，MQ调用
- 部署也很容易：不需要考虑跟别的系统关系，直接打war包部署到Web服务器即可
- 性能容易扩展，可以通过Nginx，把一个应用部署到多个服务器上。



随着业务发展，重构，单体系统越来越多，在开发一个庞大的单体系统的时候，就会有如下弊病

- 单体系统庞大，越来越难理解单体系统，微小的改动牵涉面广泛导致开发小组小心谨慎，开发速度会越来越慢。另外，启动一个庞大的单体系统，可能需要3分钟，或者更多时间
- 多个功能在同一个单体系统上开发，导致测试越来越慢，比如，测试必须排期，串行测试
- 单体系统如果想对技术进行更新换代，那代价非常大，如果是个小系统构成，则可以选取一个小系统先做尝试。单体大系统是几乎不可能做技术升级的
- 单体系统的所有功能运行在同一个JVM里，功能会互相影响，比如一个统计上传word文档的页码的功能由于非常消耗CPU，因此，会因为调用统计功能，导致整个系统短暂都不可用，出现假死的现象



因此，越来越多的架构师在设计系统的时候，会考虑系统拆分成多个单体小系统甚至是微服务。对于传统企业应用，拆成小系统更合适，对互联网系统，使用微服务个更合适，这是因为



- 传统IT系统本质上还是会用一个数据库，而微服务提倡的是一个服务一个数据库
- 传统IT系统很少需要调用其他模块服务。传统IT系统通过工作流来串联其他子系统。而电商类的微服务则是通过RPC等方式进行交互，是一个轻量级协议。传统IT系统也可以通过SOA，JMS跟其他系统(非子系统)交互，采用重量级协议
- 微服务对系统的基础设施要求很高，比如微服务治理，弹性库等等，只要电商系统才有人力物力去做这种事情，而传统IT系统，及时财大气粗，也暂时不具备微服务那样的IT基础设置



因此，对于大多数传统IT应用来说，单体拆分小系统在技术上没有风险，是一个可以立即实施的架构。如下是一个单体系统拆分后的物理架构



![design](doc/readme/design.png)

对于用户来说，访问不同的菜单功能，讲定位到不同得子系统，提供服务。

> plus支持多数据库




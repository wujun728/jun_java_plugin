### `jun_misp` 项目

管理信息系统门户 Management Information System Portal fro Wujun

#### 介绍
管理信息系统门户

#### 软件架构
现状
系统中的功能模块众多，缺少统一个用户信息门户系统以方便用户使用。

1、各应用子系统相对独立，自成体系；

2、信息更新不及时，各级用户查找信息困难；

目标
1、构建完整全面的安全体系，实现统一用户信息生命周期管理、统一权限管理、统一认证管理及单点登录；

2、构建随需应变的工作场所基础，基本实现一个集成的、基于用户和角色可配置的，个性化可定制的、随时随地可由不同种类和级别的用户使用的工作环境。

3、构建随需应变的整合框架基础，实现对现有应用子系统的无缝、灵活的整合，并为新业务系统的建设提供组织级的接口和标准，使用户门户成为企业信息化的基础标准；

    4、构建随需应变的组织运维模型基础，各子系统的数据采集、资料提交等工作流程的整合，实现各个子系统数据快速方便的展现，提高工作效率；


意义
以用户为中心、功能板块化定制、页面风格可定制、功能聚合。根据用户类型和使用习惯生成个性化门户页面，与改用户无关的信息、功能菜单将屏蔽，紧密和相关内容优先显示。

1、信息聚合到统一门户中展示；

2、大大提高获取信息及信息处理的效率；

3、统一的展现方式、风格；

门户系统提供统一的主题和皮肤设置

4、个性化定制；

用户可灵活定制门口中的内容个显示风格

5、不同系统整合；

可以将现有的子系统，资源通过门户来进行封装，提供给用户使用。比如：有些子系统都是彼此分离的，使用和界面并不统一，通过Portal可以很容易地将这些系统提供的服务封装并呈给用户使用。

个性化访问
个人门户系统设计

个性化门户定制
1、导航和菜单定制

不同专业系统用户可根据权限选择定制个人门户的导航和菜单

2、容器页面布局

提供多种布局供用户选择

3、Themes主题和Skin皮肤；

4、统一的展现方式、风格

5、个性化定制。

个人门户系统设计

设计原则
1、安全性原则

建立权限管理和安全机制，便于各级用户行使不同的职能和权限，强化个性化门户的安全管理。

2、稳定

支持一定规模的并发用户访问请求

防止单点故障

门户系统不得对其他子系统的正常运行造成不利影响

3、可扩展原则

满足门户持续性发展的要求，可以灵活方便的扩展。

门户的整体规划及框架设计需要具备可扩充性，前台页面设计能保证在增加widget容器后不会破坏网站的整体结构。后台设计也需要方便灵活修改。

核心功能模块
个人门户系统设计

功能

描述

内容聚合

能够把各种不同应用的内容聚合到一个统一的页面呈现给用户。

基于角色的视图定制

能够基于组织机构中不同的用户的角色生成不同的视图内容。例如，人力资源总监和财务经理登录后所看到的页面也是不同的。

个性化

用户能够根据个人喜好定制符合自己风格的页面和内容。例如，小王喜欢淡蓝色的格调，并且投资股票，则他可以选择一个淡蓝色风格的主题，并且使用一个已经定制好的股票portlet，允许小王设定此portlet的自动刷新时间和自选股等。

单点登录

只需登录Portal服务器一次就可以访问所有其它的应用，这意味着你无需再分别登录每一个应用。

协作功能

一些Portal框架可能会提供复杂的portlets用于聊天，应用程序共享，白板，在线会议，论坛等。

国际化

根据locale的不同呈现不同国家的文字。

工作流

这里主要指支持跨越不同数据源和应用的工作流。

支持不同的客户端

包括主流web浏览器，PDA等。

1、用户应用

用户单点登录，更加用户身份显示用户自定义的门户。

主题皮肤布局设置

业务功能快捷方式：通过有效的用户行为，对用户的的行为属性进行分析归纳，动态 生成用户常用业务的快捷菜单和个性化业务导航。 

个性化工作台：为了增加用户对个性化门户门户的依赖性和便捷性，实现我的工作台功能。用户可将经常访问的功能菜单地址添加到我的工作台。

内容定制功能：用户可以根据自己工作内容、常用习惯，通过鼠标拖拽的方式定制个性化门户页面。可以灵活设定页面展示风格，避免审美疲劳。可根据实际情况，个性化设置资源的显示属性，例如信息内容的列表条数，标题显示长度，显示字段，自动刷新时间间隔等。

统一搜索功能：用户可集成搜索引擎，实现整个门户资源统一搜索服务。

2、后台管理

内容模块管理

个性化属性管理

安全管理

系统管理

Widget开发工具

模块分类和存储管理

3、服务支撑

模块容器开发接口

页面布局管理服务

     模块页面聚合引擎等

技术对策-方案选型
Portal是一个基于web的应用程序，它主要提供个性化、单点登录、不同来源的内容整合以及存放信息系统的表示层。为规范Portal，SUN于2003年底制定了JSR168，它定义了Portlet标准，并给出了一个实现接口。

Portlet是基于java技术的web组件，它由Portlet容器管理、并处理请求，并动态生成输出内容。Portlet是基于java的web组件，由Portlet容器管理，并由容器处理请求，生产动态内容。

1、传统的基于JSR（Java Specification Request ）168或JSR286标准的Java Portlet 门户方案。Portal是一个基于web的应用程序，它主要提供个性化、单点登录、不同来源的内容整合以及存放信息系统的表示层。为规范Portal，SUN于2003年底制定了JSR168，它定义了Portlet标准，并给出了一个实现接口。Portlet是基于java技术的web组件，它由Portlet容器管理、并处理请求，并动态生成输出内容。Portlet是基于java的web组件，由Portlet容器管理，并由容器处理请求，生产动态内容。

常用开源系统框架 ： 

       在这份标准中，被选中来作评价和测试的框架一般都是在某个行业使用比较广泛或当前比较流行的开源框架，下面列出被选中的框架及其被选中的简短理由：

         Sakai 1.5（广泛的用于Virtual Research Environment(VRE)领域）

        uPortal（广泛的用于Academic Institutes work领域）

        GridSphere（第一个支持JSR168规范的开源portal框架）

        eXo平台（当前非常流行）

         Liferay（当前非常流行，良好的用户界面以及丰富的内建portlets）

        StringBeans（非常易用）

个人门户系统设计

2、基于于JQuery技术开发的纯前端轻量级的门户框架

由于web widget技术的迅速发展，widget概念是将Portlet从服务器端复杂配置管理转移到浏览器中用JS脚本配置实现，使用Js这样面向界面的DSL语言极大提高系统松耦合设计，结合使用Ajax技术，使易于扩展和定制功能带来了几乎无限的可能性，使用REST风格API可以很好的与服务器集成。纯前端JS代码跨平台支持集成Java、Net、Php等主流web应用系统。

此类技术开源产品有，jpolite2、jQueryUI Portlet等。

主要特点：

更小的核心只有3K的最小化。

jQuery UI集成控制+主题。

RESTful资源表示。

更好的用户体验–基于网格系统的布局主题和持久性支持。

更好的开发者支持-以及有组织的代码结构和行为的抽象，分离的关注，定制的易用性。

无限的可扩展性-插件和小部件从各方面。

关注点分离内容但HTML +内容+独立的CSS框架的JavaScript。

事件和消息处理

各种模块类型和模板

布局持久性和主题支持

技术方案-基于JQuery轻量级的门户框架


个人门户系统设计

Portal作为前端门户集成系统，需要集成后端业务子系统，将后端各个业务子系统的内容和业务整合在统一的门户页面上，供用户在统一的界面上获取各种来源的信息，而不会意识到信息的真正来源。

Widget是门户中提供特定服务或信息（例如：提供日历、天气预报、公司新闻、即时消息通知等）的窗口，可通过Portal提供的Widget容器处理请求、加载并生成动态内容。一个门户主页可以有多个Widget，通过不同的Widget可以在一个界面上分别显示来自不同来源的信息。

Portal提供了页面集成的柔性框架，通过加载Widget支持内容集成，并通过Widget对外进行数据提供、发出事件、接收外部应用的数据、响应外部事件，实现交互需要。

技术方案- Widget容器

主要包括以下内容：

1、容器布局Layout；

2、聚合机制；

3、持久化；

4、缓存机制；

5、底层AJAX机制






#### 安装教程
1. clone此项目到本地
2. 修改pom.xml文件，把groupId, artifactId,name 修改掉
3. 修改pom.xml文件，把本项目所需要的依赖添加进去

#### 基础篇(工具)：项目模板（mvn_template）
- 【mvn_javaproject】[maven单体Java项目](https://github.com/wujun728/mvn_template)
- 【mvn_webproject】[maven单体JavaWeb项目](https://github.com/wujun728/mvn_template)
- 【mvn_spring4_multi_modules】[maven模块化Spring4的JavaWeb项目](https://github.com/wujun728/mvn_template)
- 【mvn_spring5_multi_modules】[maven模块化Spring5的JavaWeb项目](https://github.com/wujun728/mvn_template)
- 【mvn_spring5template】[maven单体Spring5的JavaWeb项目](https://github.com/wujun728/mvn_template)


https://blog.csdn.net/u010938610/article/details/79282624
spring cloud 实战项目搭建
Eurreke
https://github.com/izerui/tomcat-redis-session-manager
https://blog.csdn.net/qq_38555490/article/details/105297271
https://blog.csdn.net/antma/article/details/79629584
https://gitee.com/antma/SpringBootGetOpenId.git
https://github.com/Thinkingcao/silence-boot


一、名词解释
1. 整理
MIS 管理信息系统 Management Information System

ERP 企业资源计划 Enterprise Resource Planning

CRM 客户关系管理 Customer Relationship Management

DSS 决策支持系统 Decision Support System

BPM 业务流程管理 Business Process Management

OA 办公自动化系统 office Automation

TPS 交易处理系统 Transaction Processing System

ES 专家系统 Expert System

BPR 企业流程再造 Business Process Reengineering

BSP 企业系统规划 Business System Planning

CSF 关键成功因素法 Critical success factors

MRP 物料需求计划 Material Requirement Planning

MRPII 制造资源计划 Manufacturing Resource Planning

SCM 供应链管理 Supply Chain Management

DBS 数据库系统 Database System

DBMS 数据库管理系统 Database Management System

EDI 电子数据交换 Electronic data interchange

EDP 电子数据处理 electronic data processing

EDPS 电子数据处理系统 electronic data processing System

CMMI 能力成熟度模型集成 Capability Maturity Model Integration

SDLC 系统开发的生命周期法 System Development Life Cycle

SADT 结构化分析与设计技术 Structured Analysis and - Design Technologies

CAD 计算机辅助设计 Computer Aided Design

CAM 计算机辅助制造 computer Aided Manufacturing

CIMS 计算机/现代集成制造系统 Computer/contemporary Integrated Manufacturing Systems

OO 面向对象 Object Oriented

OOA 面向对象分析 Object Oriented Analysis

OOD 面向对象设计 Object Oriented Design

OOP 面相对象的编程实现 Object Oriented Programming

CASE 计算机辅助软件工程方法 Computer Aided Software Engineering,

DFD 数据流程图 Data Flow Diagram

UML 统一建模语言 Unified Modeling Language

IDSS 智能化决策支持系统

GDSS 群体化决策支持系统

MPS 主生产计划

XML 可扩展标记语言 eXtensible Markup Language

BI 商业智能 Business Intelligence

ECP 电子商务平台 Electronic Commerce Platform

IS 基础设施系统 Infrastructure System

KMS 知识管理系统 Knowledge Management System

WMS 仓库管理系统 Warehouse Management System

HRMS 人力资源管理系统 Human Resources Management System

HCM 人力资本管理 Human Capital Management

SRM 供应商关系管理 Supplier Relationship Management

PLM 产品生命周期管理 Product Lifecycle Management

DM 需求管理 Demand Management

RFID 无线射频识别

SQL 结构化查询语言

LAN 局域网

WAN 广域网

VPN 虚拟专用网络

ISO9000 国际标准化组织

6 σ 六西格玛，旨在生产过程中降低产品及流程的缺陷次数,防止产品变异，提升品质。

E-R方法 实体-联系方法

1NF 2NF 3NF 第一、二、三范式

2、补充整理（来源于互联网）
信息：指加工以后对人们的活动产生影响的数据。

数据：是对客观事物的性质、状态以及相互关系等进行记载的符号。

信息化：是指国民经济各部门和社会活动各领域普遍采用信息技术，利用信息资源，使得人们能在任何时间、任何地点，通过各种媒体，使用和传递所需信息，以提高工作效率、促进现代化的发展、提高人民生活质量、增强国力的过程。

企业信息化：是指企业利用现代的信息技术，通过对信息资源的深度开发和广泛利用，不断提高生产、经营、管理、决策的效率和水平，提高企业经济效益和企业竞争力的过程。

系统：是由相互作用和相互依赖的若干组成部分，为了某些目标结合而成的有机整体。

信息系统：是以计算机、网络及其它信息技术为核心，为实现某些系统目标，对信息资源进行处理的信息。

管理：管理者或管理机构，在一定范围内，通过计划、组织、控制、领导等工作，对组织所拥有的资源进行合理配置和有效使用，以实现组织预定目标的过程。

虚拟组织：是由若干独立实在的企业组成的临时性、动态的“虚拟”的企业。

组织扁平化：是指通过组织结构的调整，消减中间管理层数量的工作过程。

企业流程再造（BPR）：是对企业流程所进行的根本性的再思考和彻底的再设计，以使企业的速度、质量、服务和成本等关键业绩指标获得根本性的改善。

企业流程：是指生产或服务过程中的一连串活动的工作流程。

企业资源规划（ERP）：是一个集合企业内部的所有资源，进行有效的计划和控制，以期达到最有效的计划和控制，达到最大效益的集成系统。

数据库管理系统（DBMS）：是对计算机中所存放的大量数据进行组织、管理、查询并提供一定处理功能的大型系统软件。

关键成功因素法（CSF）：是分析出企业成功的关键因素，围绕关键因素识别企业的主要信息需求和相关工作的规划方法。

企业系统规划（BSP）：是根据企业目标制定MIS规划的方法。

生命周期：一个信息系统从它的提出、开发、应用到系统的更新，经历了一个发生、发展和灭亡的循环过程。

生命周期法：生命周期法要求系统开发按照步骤逐步完成开发任务，对每一个开发阶段规定了各自的任务、流程、目标等内容，从而使开发工作规范统一，易于管理和控制。

原型法：根据用户提出的基本要求，采用快速技术，在短时间内，开发出一个简单的带有实践性的、可执行的系统原型，交给用户试用，开发人员根据用户反馈的信息，对系统原型进行修改、完善，再交给用户试用，反复这个过程，直至产生用户满意的系统原型为止。

面向对象：按人们认识客观世界的系统思维方法，采用基于对象的概念建立模型，模拟客观世界分析、设计、实现软件的办法。

对象：是客观世界里的任何实体的抽象，是客观世界实体的软件模型，由数据和方法两部分组成。

封装：将对象的数据和操作（方法）合并在一起，称为封装。

类：是对一类相似对象的描述，这些对象具有相同的属性和行为、相同的变量和方法实现。

继承：把若干个对象类组成一个层次结构的系统，下层的系统具有和上层父类相同的特性，称为继承。

IT外包：是将组织中与信息相关的活动，部分或全部交给组织外的信息服务提供者来完成。

供应链：指具有供求关系的上下游企业所构成的组织链条。涉及产品设计、生产以及将产品传递到市场上的所有企业构成供应链。

供应链管理：是指协调供应链中生产、存货、选址以及运输活动，从而在市场达到响应速度和效率的最佳组合。

电子商务：狭义上讲，电子商务是指网上进行交易活动，包括通过Internet买卖产品和提供服务。广义上讲，电子商务是指利用网络来解决商业交易问题，降低产、供、销成本问题，开拓新市场，创造新商机，通过新的网络技术手段，从而增加企业利润的所有商业活动。

数据流程图：是一种用户便于用户理解、分析系统数据流程的图形工具。它摆脱了系统的物理内容，精确地在逻辑上描述系统的功能、输入、输出和数据存储等。

外部实体：是指系统以外与系统有联系的人或事物。

数据流：表示流动的数据，它可以由一项或一组确定的数据组成。

数据处理：实际表示的是一种处理功能。

数据存储：表示数据保存的地方。

业务流程优化：根据系统调查阶段了解到的情况，从业务全过程的角度摸清现状、找出问题的关键点，对业务流程进行彻底的分析和改进。其实质是对业务流程进行重组。

数据字典：对于数据流程图中的每个符号，通过表格和文字的描述定义它们的细节，而这些描述和定义所组成的集合就是数据字典。

数据元素：是最小的数据组成单位，即不可再分的数据单位。

数据结构：数据结构的描述重点是数据之间的组合关系，即说明这个数据结构包括哪些成分。

模块：是指独立命名并且拥有明确定义的实体。

模块化：即把系统功能自顶向下地、由抽象到具体地划分为多层次的独立功能模块，每个模块完成一个特定的功能，一直分解到能简单地用程序实现为止。

模块结构图：又称系统结构图、控制结构图，是用一组特殊的图形符号按照一定的规则描述系统整体结构。

耦合性：指多个模块间相互联系、相互依赖的程度，主要是从模块外部考察模块独立性。

内聚性：指一个模块内部各项处理相互联系的密切程度，主要是从模块内部考察模块独立性。

模块的大小：是实现模块所需编写程序的行数。

扇出系数：就是一个模块直接调用其他模块的个数。

扇入系数：就是直接调用该模块的模块个数。

代码：是用数或字符代表事物的名称、属性和状态等的符号，它以简短的符号形式代替具体的问题说明，惟一地标识系统中的某一事物。

视图：是面向用户呈现出的虚表。在数据库中并没有视图的数据存储，视图是由数据库操作处理出来的一部分数据库存储文件。

数据库的安全性保护：是防止机密数据被泄露，防止无权者使用、改变或有意破坏数据。

数据库的完整性保护：是保护数据结构不受损害，保证数据的正确性、有效性和一致性。

系统的吞吐量：是指每秒钟执行的作业数。

系统的响应时间：指的是从用户向系统发出一个作用请求开始，经系统处理后，给出应答结果的时间。

系统的可靠性：指系统连续无差错工作时间。

集中式系统：是集设备、软件和数据于一体的工作模式。

分布式系统：是将整个系统分成若干个在地理上分散的配置，业务可以独立处理，但系统在统一的工作规范和技术要求下运行。

程序的可维护性：一个软件系统或组件可以被修改的容易程度。

程序的可靠性：是指程序和系统的安全可靠，如数据存取的安全可靠、通信的安全可靠、操作权限的安全可靠等。

程序的可阅读性：程序不仅要求逻辑正确，是计算机能够执行，而且应该层次清楚，便于人们阅读。

程序效率：是指计算机资源能否被有效地使用。

个人复查：指某个编程人员检查自己编写的程序。

走查：是指测试人员通过人工测试方法检查程序中的错误。

会审：将编程人员的讲解与走查结合在一起。

黑盒测试：也称功能测试，将被测程序看作黑盒子，只检查程序功能是否按照需求规定正常使用，程序是否能适当地接受输入数据并产生正确的输出信息。

白盒测试：也称结构测试，它将被测程序看作透明的白盒子。该方法按照程序的内部结构和处理逻辑来选定测试用例，对程序的逻辑路径及过程进行测试。

水波效应：指一个模块的修改而导致隐含缺陷、错误的放大以及一连串的新错误的出现。



jun_product
123\
	BlogHtTemplate\  迁移到jun_frontend/jun_layui
	blogv20180113\  干掉 ，代码
	inspinia_admin_java_ssm\    干掉，代码
	layuiAdmin\   迁移到jun_frontend/jun_layui
	LuGenerate\   干掉，代码搞spring_plugin
	manager-system\  迁移到jun_boot rename jun_boot_layadmin
	noteblogv5\   干掉，源码迁移，没有脚本
	simple-spring-jdbc\  迁移到spring_jdbc
	snaker-springmvc\  干掉，代码
	spring-Boot_templates_layui-Admin\   直接干掉
	spring-shiro-training\  迁移到easyui
	sypro\  迁移到ssh里面
	zb-shiro\   合并到ruoyi
	
jun_administrative\
jun_ask_discuss\
jun_blog\
jun_bos\
jun_crm\
jun_edu\  外网调试
jun_erp\
jun_finance\
jun_flybbs\
jun_hr\
jun_itselfservice\
jun_mis\
jun_music\
jun_oa\
jun_op\
jun_portal\
jun_prj\
jun_resume_java\
jun_resume_pm\
jun_spring\
jun_wms\
项目1111111\
	Jar包下载网视频教程\
	百度云爬虫视频教程\
	百度云搜索视频教程\
	博客采集系统视频教程\
	博客系统视频教程\
	客户关系视频教程\
	请假系统视频教程\
	设备系统视频教程\
	实用cms系统视频教程\
	支付系统视频教程\
	mindskip-uexam-master.zip
	project.zip
	project(1).zip
	project(1)(1).zip
	
pom.xml


1、父pom.xml jun_system --> jun_product
https://github.com/xuxueli/xxl-deep
https://github.com/oneboat/ssm-lay
https://github.com/search?q=lay++ssm&type=Repositories
https://github.com/lcw2004/one
https://github.com/thinkgem/jeesite
https://github.com/thinkgem/jeesite4
https://github.com/thinkgem/jeesite4-cloud


https://github.com/febsteam/FEBS-Cloud
https://github.com/febsteam/FEBS-Shiro

ssm
https://github.com/Mandelo/ssm_shiro_blog


boot
https://github.com/yzcheng90/X-SpringBoot
https://github.com/Heeexy/SpringBoot-Shiro-Vue

cloud
https://github.com/yzcheng90/ms
https://github.com/yzcheng90/ms-ui


CMS
https://github.com/xujeff/tianti

*****
https://github.com/hope-for/hope-boot  

https://github.com/moshowgame/SpringBootCMS

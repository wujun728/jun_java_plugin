Spring Boot 集成SnakerFlow流程引擎，简介、功能列表、详细解读、扩展点分析


功能列表
流程定义
任务参与者
参与者设置
动态添加、删除参与者
组支持
详细解读
Spring Boot集成
表定义
表详细说明：
字段详细说明：
常见操作
常规API
综合查询
模型操作
流程模型
Start节点
name获取节点
类型获取所有节点
所有任务节点
后续一级节点集合
活动任务
转派
撤回
提取
驳回、跳转
唤醒
更新
创建自由任务
决策表达式
decision的expr
transition的expr
自定义类
子流程
子流程模型
父子流程的关联
时限控制
依赖包
配置
超时提醒
超时自动完成
实例抄送
表结构
创建抄送
更新状态
会签任务
会签任务节点
动态加签
会签百分比
串行会签
Fork/Join
级联删除（历史数据清理）
扩展点
自定义实例编号orderNo
任务、实例完成时触发的回调
节点拦截器
全局拦截器
局部拦截器
源码分析
自定义处理节点
基于用户或组的访问策略
☎️ ☎️ ☎️ 已开源基于SnakerFlow轻量级工作流引擎的脚手架项目 easy-admin

详情参见：终于写了个开源项目，easy-admin 为打造一款简单、轻量级的后台管理系统脚手架

简介
SnakerFlow是一个基于Java的轻量级工作流引擎，适用于企业应用中常见的业务流程。本着轻量、简单、灵巧理念设计，定位于简单集成，多环境支持。

轻量: snaker-core.jar大小208K，代码行数约7000行，强大的扩展支持，不依赖于具体的ORM框架，默认支持以下框架：

Spring、Jfinal、Nutz
Jdbc、SpringJdbc、Hibernate3、Hibernate4、Mybatis
简单: 表设计简单，七张核心表

表设计简单[核心表7张]
流程组件简单[start/end/task/custom/subprocess/decision/fork/join]
灵巧: 预留大量扩展接口，支持web流程设计器

暴露大量可扩展接口
支持流程设计器、流程引擎的组件模型自定义[节点自定义、属性自定义、表单自定义]
相关网站如下：

https://github.com/snakerflow-starter

https://github.com/snakerflow-starter/snakerflow-spring-boot-starter

https://yunmel.gitbooks.io/snakerflow/content/

https://github.com/snakerflow-starter/snaker-web

功能列表
流程定义

待办

已办

任务到达某节点发出sms email 创建任务、完成实例

催办

转办

会签

snaker的会签目前相对比较简单，仅仅是根据任务节点的performType属性值确定是否产生多个相同任务。

performType的值有两种，分别是ANY、ALL。

ANY多个参与者时，任何一个完成任务即继续流转

ALL多个参与者时，所有都需要完成任务才能继续流转

performType为0 ANY，则仅仅向wf_task_actor表中增加一条参与者信息performType为1ALL，则会在wf_task表中增加一条任务数据。

加签

engine.task().addTaskActor(String taskId, 1, String… actorIds)
审批环节 同意 拒绝

驳回到任意节点

流程图

撤回

任务参与者

流程定义
支持Eclipse插件、web设计器。

已集成web设计器，具体详见我的开源项目Easy-Admin: https://gitee.com/lakernote/easy-admin

任务参与者
snaker的任务支持静态配置、动态传递、自定义类处理、动态设置、组等方式。

参与者设置
动态添加、删除参与者
组支持
参与者设置
直接设置静态参与者，即assignee值为用户、部门或角色的标识符

通过运行时动态传递，即assignee值为变量名称，在调用流程引擎的api时，通过map参数传递这个变量值

通过自定义类[继承Assignment类]，设置assignmentHandler属性，assign方法返回值就是参与者

常用的动态添加、删除参与者示例

Order dm = snakerEngineFacets.startInstanceById(process.getId(), "张三", Dict.create()
        .set("user1", "张三排")
        .set("user2", "组长")
        .set("approveBoss.operator", "部门经理"));
1
2
3
4
  <task assignee="user1" displayName="请假申请" form="/flow/leave/apply" layout="117,122,-1,-1" name="apply"
          performType="ANY">
        <transition g="" name="transition2" offset="0,0" to="approveDept"/>
    </task>
    <task assignee="user2" displayName="部门经理审批" form="/flow/leave/approveDept" layout="272,122,-1,-1"
          name="approveDept" performType="ANY">
        <transition g="" name="transition3" offset="0,0" to="decision1"/>
    </task>
    <decision displayName="decision1" expr="#day &gt; 2 ? 'transition5' : 'transition4'" layout="426,124,-1,-1"
              name="decision1">
        <transition displayName="&lt;=2天" g="" name="transition4" offset="0,0" to="end1"/>
        <transition displayName="&gt;2天" g="" name="transition5" offset="0,0" to="approveBoss"/>
    </decision>
    <task assignee="approveBoss.operator" displayName="总经理审批" form="/flow/leave/approveBoss" layout="404,231,-1,-1"
          name="approveBoss" performType="ANY">
        <transition g="" name="transition6" offset="0,0" to="end1"/>
    </task>
1
2
3
4
5
6
7
8
9
10
11
12
13
14
15
16
17
源码：org.snaker.engine.core.TaskService

/**
 * 根据Task模型的assignee、assignmentHandler属性以及运行时数据，确定参与者
 * @param model 模型
 * @param execution 执行对象
 * @return 参与者数组
 */
private String[] getTaskActors(TaskModel model, Execution execution) {
   Object assigneeObject = null;
       AssignmentHandler handler = model.getAssignmentHandlerObject();
   if(StringHelper.isNotEmpty(model.getAssignee())) {
      // Args{user1:张三,user2:李四}，model.getAssignee() user1
      assigneeObject = execution.getArgs().get(model.getAssignee());
   } else if(handler != null) {
           if(handler instanceof Assignment) {
               assigneeObject = ((Assignment)handler).assign(model, execution);
           } else {
               assigneeObject = handler.assign(execution);
           }
   }
   return getTaskActors(assigneeObject == null ? model.getAssignee() : assigneeObject);
}
1
2
3
4
5
6
7
8
9
10
11
12
13
14
15
16
17
18
19
20
21
动态添加、删除参与者
向指定任务动态添加参与者，同时支持设置参与类型

performType为0，则仅仅向wf_task_actor表中增加一条参与者信息performType为1，则会在wf_task表中增加一条任务数据。

engine.task().addTaskActor(String taskId, String... actors)
engine.task().addTaskActor(String taskId, Integer performType, String... actors)
1
2
对指定任务动态删除其中的参与者

engine.task().removeTaskActor(String taskId, String... actors)
1
组支持
由于snaker引擎与用户权限完全解耦的，所以对于组的支持，仅仅是你设置组作为参与者，你就要自定义一个任务的访问策略，能够根据操作人得到所有的组集合，这样流程引擎才能允许该操作人处理任务。

自定义任务访问策略类
public class CustomAccessStrategy extends GeneralAccessStrategy {
    protected List<String> ensureGroup(String operator) {
        //此处根据实际项目获取操作人对应的组列表
        return ShiroUtils.getGroups();
    }
}
1
2
3
4
5
6
配置
在snaker.xml中增加下面的配置

<bean class="com.snakerflow.framework.flow.CustomAccessStrategy"/>
1
详细解读
Spring Boot集成
1.pom.xml依赖

<dependency>
  <groupId>com.github.snakerflow-starter</groupId>
  <artifactId>snakerflow-spring-boot-starter</artifactId>
  <version>1.0.7</version>
</dependency>
1
2
3
4
5
2.新增配置项

mybatis-plus:
  type-aliases-package: org.snaker.engine.entity
1
2
3.初始化sql脚本

具体详见我的开源项目Easy-Admin: https://gitee.com/lakernote/easy-admin

表定义
Snaker流程引擎核心共7张表，关系图如下所示:

[外链图片转存失败,源站可能有防盗链机制,建议将图片保存下来直接上传(img-BIBnQoBD-1628575405791)(https://yunmel.gitbooks.io/snakerflow/content/assets/table_relations.png)]

表详细说明：
表名称	描述	备注
wf_process	流程定义表	
wf_order	活动实例表	
wf_task	活动任务表	
wf_task_actor	活动任务参与者表	
wf_hist_order	历史实例表	
wf_hist_task	历史任务表	
wf_hist_task_actor	历史任务参与者表	
wf_surrogate	委托代理管理表	一般业务用不到
wf_cc_order	抄送实例表	一般业务用不到
字段详细说明：
wf_process

字段名称	字段描述
id	主键ID
name	流程名称
display_Name	流程显示名称
type	流程类型
instance_Url	实例url
state	流程是否可用
content	流程模型定义
version	版本
create_Time	创建时间
creator	创建人
wf_order

字段名称	字段描述
id	主键ID
parent_Id	父流程ID
process_Id	流程定义ID
creator	发起人
create_Time	发起时间
expire_Time	期望完成时间
last_Update_Time	上次更新时间
last_Updator	上次更新人
priority	优先级
parent_Node_Name	父流程依赖的节点名称
order_No	流程实例编号
variable	附属变量json存储
version	版本
wf_hist_order

字段名称	字段描述
id	主键ID
parent_Id	父流程ID
process_Id	流程定义ID
creator	发起人
create_Time	发起时间
expire_Time	期望完成时间
end_Time	完成时间
priority	优先级
order_No	流程实例编号
variable	附属变量json存储
order_State	状态
wf_task

字段名称	字段描述
id	主键ID
order_Id	流程实例ID
task_Name	任务名称
display_Name	任务显示名称
task_Type	任务类型
perform_Type	参与类型
operator	任务处理人
create_Time	任务创建时间
finish_Time	任务完成时间
expire_Time	任务期望完成时间
action_Url	任务处理的url
parent_Task_Id	父任务ID
variable	附属变量json存储
version	版本
wf_hist_task

字段名称	字段描述
id	主键ID
order_Id	流程实例ID
task_Name	任务名称
display_Name	任务显示名称
task_Type	任务类型
perform_Type	参与类型
operator	任务处理人
create_Time	任务创建时间
finish_Time	任务完成时间
expire_Time	任务期望完成时间
action_Url	任务处理的url
parent_Task_Id	父任务ID
variable	附属变量json存储
task_State	任务状态
wf_task_actor

字段名称	字段描述
task_Id	任务ID
actor_Id	参与者ID
wf_hist_task_actor

字段名称	字段描述
task_Id	任务ID
actor_Id	参与者ID
wf_surrogate

字段名称	字段描述
id	主键ID
process_Name	流程名称
operator	授权人
surrogate	代理人
odate	操作时间
sdate	开始时间
edate	结束时间
state	状态
wf_cc_order

字段名称	字段描述
order_Id	流程实例ID
actor_Id	参与者ID
create_Time	抄送时间
finish_Time	完成时间
creator	发起人
status	状态
常见操作
常规API
参见SnakerEngineFacets.java

综合查询
综合查询服务不仅提供流程实例、活动/历史任务、待办任务的查询，同时支持原生SQL语句的查询服务。

org.snaker.engine.IQueryService
1
根据流程实例ID获取流程实例对象

Order getOrder(String orderId);
1
根据流程实例ID获取历史流程实例对象

HistoryOrder getHistOrder(String orderId);
1
根据任务ID获取任务对象

Task getTask(String taskId);
1
根据任务ID获取历史任务对象

HistoryTask getHistTask(String taskId);
1
根据任务ID获取活动任务参与者数组

String[] getTaskActorsByTaskId(String taskId);
1
根据任务ID获取历史任务参与者数组

String[] getHistoryTaskActorsByTaskId(String taskId);
1
根据filter查询活动任务

List<Task> getActiveTasks(QueryFilter filter);
1
根据filter分页查询活动任务

List<Task> getActiveTasks(Page<Task> page, QueryFilter filter);
1
根据filter查询流程实例列表

List<Order> getActiveOrders(QueryFilter filter);
1
根据filter分页查询流程实例列表

List<Order> getActiveOrders(Page<Order> page, QueryFilter filter);
1
根据filter查询历史流程实例

List<HistoryOrder> getHistoryOrders(QueryFilter filter);
1
根据filter分页查询历史流程实例

List<HistoryOrder> getHistoryOrders(Page<HistoryOrder> page, QueryFilter filter);
1
根据filter查询所有已完成的任务

List<HistoryTask> getHistoryTasks(QueryFilter filter);
1
根据filter分页查询已完成的历史任务

List<HistoryTask> getHistoryTasks(Page<HistoryTask> page, QueryFilter filter);
1
根据filter分页查询工作项（包含process、order、task三个实体的字段集合）

List<WorkItem> getWorkItems(Page<WorkItem> page, QueryFilter filter);
1
根据filter分页查询抄送工作项（包含process、order）

List<HistoryOrder> getCCWorks(Page<HistoryOrder> page, QueryFilter filter);
1
根据filter分页查询已完成的历史任务项

List<WorkItem> getHistoryWorkItems(Page<WorkItem> page, QueryFilter filter);
1
根据类型T、Sql语句、参数查询单个对象

public <T> T nativeQueryObject(Class<T> T, String sql, Object... args);
1
根据类型T、Sql语句、参数查询列表对象

public <T> List<T> nativeQueryList(Class<T> T, String sql, Object... args);
1
根据类型T、Sql语句、参数分页查询列表对象

public <T> List<T> nativeQueryList(Page<T> page, Class<T> T, String sql, Object... args);
1
模型操作
通过流程定义章节的查询接口可以轻松获取到Process实体对象，该实体中的model属性就是流程图的对象表达形式了，可通过getModel方法获取ProcessModel。

process.getModel()
1
流程模型
Start节点
name获取节点
类型获取所有节点
所有任务节点
后续一级节点集合
流程模型
org.snaker.engine.model.ProcessModel
1
流程模型、流程定义的XML文件、流程图三种表现形式可互相转换，流程模型对象不仅包含了自身的属性（如：namedisplayNameinstanceUrlexpireTimeinstanceNoClass），同时也包含了所有节点模型对象以及它们的关系。

Start节点
org.snaker.engine.model.StartModel
1
Start节点作为流程启动的入口，只有输出路由，其输入路由为空，可通过getInputs方法进行验证

name获取节点
NodeModel getNode(String nodeName)
1
根据节点的name属性获取到节点模型对象

类型获取所有节点
List<T> getModels(Class<T> clazz)
1
根据节点类型获取所有该类型的模型对象集合。常用于如下方式：

List<TaskModel> taskModels = processModel.getModels(TaskModel.class)
1
所有任务节点
List<TaskModel> getTaskModels()
1
该方法获取有序的所有任务模型集合

后续一级节点集合
List<T> getNextModels(Class<T> clazz)
1
获取某个节点的后续一级节点集合，getNextModels是NodeModel的方法

活动任务
任务服务主要配合流程引擎在调度过程中任务数据的操作。

org.snaker.engine.ITaskService
1
转派
撤回
提取
驳回跳转
唤醒
更新
转派
任务转派是向指定人创建新的任务。转派api支持主办、协办两种任务类型

createNewTask(String taskId, int taskType, String... actors)
1
taskType：0 主办任务类型

taskType：1 协办任务类型

撤回
根据历史任务id，撤回由该历史任务派发的所有活动任务，如果无活动任务，则不允许撤回，抛出unchecked异常：SnakerException

withdrawTask(String taskId, String operator)
1
提取
任务提取一般发生在参与者为部门、角色等组的情况下，该组的某位成员提取任务后，不允许其它成员处理该任务。

take(String taskId, String operator)
1
驳回、跳转
任务驳回有两种场景：驳回至上一步；驳回至任意节点。

engine.executeAndJumpTask(String taskId, String operator, Map<String, Object> args, String nodeName);
1
方法的参数nodeName决定驳回的方式：

nodeName为空时，则驳回至上一步，不需要传递参与者数据
nodeName不为空，则表示任意跳转，此时需要传递参与者数据。
唤醒
如果一个已经结束的任务，希望重新激活为活动状态，该如何操作呢。那么调用resume方法即可实现此功能。

Task resume(String taskId, String operator)
1
更新
如果一个活动任务，需要更新部分字段，则可以使用更新方法完成。

updateTask(Task task)
1
可更新任务对象的finish_Time、operator、expire_Time、variable

创建自由任务
engine.createFreeTask(String orderId, String operator, Map<String, Object> args, TaskModel model);
1
创建自由任务时，需要新建任务模型对象TaskModel，再根据此模型创建对应的任务数据。

决策表达式
决策表达式主要用于decision（选择分支）节点，该节点支持三种路由选择方式

decision的expr
transition的expr
自定义类
decision的expr
decision节点的expr有两种方式来设置

1.表达式中增加判断逻辑，如：${day >3 ? 't1' : 't2'}，此时根据day的值决定走t1、或t2的路由，用于设置范围值的情况

2.表达式仅仅是一个变量，如：${tname}，此时，传递tname的值为路由name即可，用于设置具体值的情况（如：同意、不同意）
1
2
3
transition的expr
如果decision节点的expr为空，可设置输出路由的expr，必须返回boolean类型

路由1设置expr的值为：${content==1}，
路由2设置expr的值为：${content > 1}。
1
2
自定义类
自定义类需要实现DecisionHandler接口的decide方法

org.snaker.engine.DecisionHandler
1
该方法返回字符串即表示输出路由的name

Snaker默认支持Juel、SringEL两种表达式引擎，需要在snaker.xml中配置。配置如下：

<bean class="org.snaker.engine.impl.JuelExpression"/>
1
并且支持自定义表达式，实现Expression接口，同样修改一下配合即可。

Juel表达式引擎常用语法
String expr1 = "${content}";
Map<String, Object> args1 = new HashMap<String, Object>();
args1.put("content", 1);
System.out.println(eval(String.class, expr1, args1));

String expr2 = "${content == 1 ? 'v1' : 'v2'}";
Map<String, Object> args2 = new HashMap<String, Object>();
//此处不仅支持数字类型，也支持字符串类型，但是值必须可转换为数字
args2.put("content", "2");
System.out.println(eval(String.class, expr2, args2));

String expr3 = "${content > 1 ? 'v1' : 'v2'}";
Map<String, Object> args3 = new HashMap<String, Object>();
//此处只要是数字类型即可，如1或0.5等
args3.put("content", 4.2);
System.out.println(eval(String.class, expr3, args3));

//juel支持表达式为字符串类型而参数为数字类型
String expr4 = "${content == '1' ? 'v1' : 'v2'}";
Map<String, Object> args4 = new HashMap<String, Object>();
//此处不仅可以写字符串"1"，也可以写char类型'1'，写数字类型1，浮点类型1.00
args4.put("content", 1);
System.out.println(eval(String.class, expr4, args4));

//逻辑表达式与，使用&&表示
String expr5 = "${content > 1 && content <= 10 ? 'v1' : 'v2'}";
Map<String, Object> args5 = new HashMap<String, Object>();
//此处不仅可以写字符串"1"，也可以写char类型'1'，写数字类型1，浮点类型1.00
args5.put("content", "11");
System.out.println(eval(String.class, expr5, args5));

//逻辑表达式或，使用||表示
String expr6 = "${content < 1 || content > 10 ? 'v1' : 'v2'}";
Map<String, Object> args6 = new HashMap<String, Object>();
//此处不仅可以写字符串"1"，也可以写char类型'1'，写数字类型1，浮点类型1.00
args6.put("content", "1");
System.out.println(eval(String.class, expr6, args6));

//逻辑表达式
String expr7 = "${content < 1 || content > 10 ? 'v1' : (content == 8 ? 'v2' : 'v3')}";
Map<String, Object> args7 = new HashMap<String, Object>();
//此处不仅可以写字符串"1"，也可以写char类型'1'，写数字类型1，浮点类型1.00
args7.put("content", "8");
System.out.println(eval(String.class, expr7, args7));
1
2
3
4
5
6
7
8
9
10
11
12
13
14
15
16
17
18
19
20
21
22
23
24
25
26
27
28
29
30
31
32
33
34
35
36
37
38
39
40
41
42
43
44
Spring EL表达式引擎常用语法
String expr1 = "#content";
Map<String, Object> args1 = new HashMap<String, Object>();
args1.put("content", "v2");
System.out.println(expr1 + "====>" + eval(String.class, expr1, args1));

String expr2 = "#content == 1 ? 'v1' : 'v2'";
Map<String, Object> args2 = new HashMap<String, Object>();
//此处仅支持数字类型
args2.put("content", 2.1);
System.out.println(expr2 + "====>" + eval(String.class, expr2, args2));

String expr3 = "#content > 1 ? 'v1' : 'v2'";
Map<String, Object> args3 = new HashMap<String, Object>();
//此处只要是数字类型即可，如1或0.5等
args3.put("content", 4.2);
System.out.println(expr3 + "====>" + eval(String.class, expr3, args3));

//spel支持表达式为字符串类型而参数为数字类型
String expr4 = "#content == '1' ? 'v1' : 'v2'";
Map<String, Object> args4 = new HashMap<String, Object>();
//此处仅支持字符串"1"
args4.put("content", "1");
System.out.println(expr4 + "====>" + eval(String.class, expr4, args4));

//逻辑表达式与，使用and表示
String expr5 = "#content > 1 and #content <= 10 ? 'v1' : 'v2'";
Map<String, Object> args5 = new HashMap<String, Object>();
//此处仅支持数字类型
args5.put("content", 11);
System.out.println(expr5 + "====>" + eval(String.class, expr5, args5));

//逻辑表达式或，使用||表示
String expr6 = "#content < 1 or #content > 10 ? 'v1' : 'v2'";
Map<String, Object> args6 = new HashMap<String, Object>();
//此处不仅可以写字符串"1"，也可以写char类型'1'，写数字类型1，浮点类型1.00
args6.put("content", 1);
System.out.println(expr6 + "====>" + eval(String.class, expr6, args6));

//逻辑表达式
String expr7 = "(#content < 1 or #content > 10) ? 'v1' : (#content == 8 ? 'v2' : 'v3')";
Map<String, Object> args7 = new HashMap<String, Object>();
//此处不仅可以写字符串"1"，也可以写char类型'1'，写数字类型1，浮点类型1.00
args7.put("content", 8);
System.out.println(expr7 + "====>" + eval(String.class, expr7, args7));
1
2
3
4
5
6
7
8
9
10
11
12
13
14
15
16
17
18
19
20
21
22
23
24
25
26
27
28
29
30
31
32
33
34
35
36
37
38
39
40
41
42
43
44
子流程
子流程的作用是将一个复杂的业务流程进行细化拆分，提高流程的复用性。

子流程模型
父子流程的关联
子流程模型
子流程模型类型为SubProcessModel，其主要属性为processName，根据流程的name进行关联，由于流程定义支持一个name多个版本同时运行，那么子流程关联只设置name，即表示与最新版本的子流程关联。

父子流程的关联
对于表结构的设计中，父子流程的关联字段为

wf_order[parent_Id、parent_Node_Name]
wf_hist_order[parent_Id]
时限控制
时限控制常用于流程平台中的超时处理（提醒、自动执行等）、以及任务监控的查询统计等功能。

依赖包
配置
实现提醒接口
依赖包
snaker默认支持quartz定时器调度框架，只需要依赖snaker-quartz的包即可。

配置
在snaker.xml中配置时限控制的拦截器、定时调度的实现类

<bean class="org.snaker.engine.impl.SchedulerInterceptor"/>
<bean class="org.snaker.engine.scheduling.quartz.QuartzScheduler"/>
1
2
如果使用其它定时调度框架，需要实现IScheduler接口，并替换QuartzScheduler类配置

以上两步已经完成了时限的配置工作，下面就可以针对提醒、超时自动执行做自定义扩展了。

超时提醒
编写自定义的提醒类，实现IReminder接口。并配置到snaker.xml中即可。
任务节点配置超时提醒属性：reminderTime、reminderRepeat。
reminderTime是一个变量，表示提醒开始时间，当你调用api时需要传递此变量值，值的类型为date。
reminderRepeat是一个数字，表示重复提醒间隔时间，以分钟为单位
1
2
默认提醒一次就结束，如果希望提醒多次，可通过 snaker.properties 中配置 scheduler.repeat 属性，该值是个数字，表示提醒次数。
节假日配置
#是否启用节假日，默认为false
scheduler.useCalendar=true/false
#节日配置，格式为yyyy-MM-dd,...
scheduler.holidays=2014-12-26,2014-12-27
#工作日设置，格式为1,2,3...7，表示周一至周日
scheduler.weeks=1,2,3,4,5
#工作时间设置，格式为8:00-18:00
scheduler.workTime=8:00-18:00
1
2
3
4
5
6
7
8
超时自动完成
任务节点配置超时处理属性： expireTime、autoExecute、callback
expireTime是一个变量，表示期望完成时间，当你调用api时需要传递此变量值，值的类型为date。

autoExecute的值为Y/N,表示超时是否自动执行

callback是一个字符串，表示自动执行的回调类路径配置

编写回调类
通过实现JobCallback接口

org.snaker.engine.scheduling.JobCallback
1
实例抄送
实例的抄送类似于邮箱里面的抄送功能，一般用于将该流程实例抄送给领导查阅。

表结构
创建抄送
更新状态
表结构
抄送记录表主要保存抄送的记录信息

wf_cc_order
1
创建抄送
根据实例id、创建人、抄送人创建抄送记录

engine.order().createCCOrder(String orderId, String creator, String... actorIds)
1
更新状态
更新状态用于更新抄送记录为已经阅读

engine.order().updateCCStatus(String orderId, String... actorIds)
1
会签任务
snaker的会签目前相对比较简单，仅仅是根据任务节点的performType属性值确定是否产生多个相同任务。

performType的值有两种，分别是ANY、ALL。

ANY多个参与者时，任何一个完成任务即继续流转

ALL多个参与者时，所有都需要完成任务才能继续流转

会签任务节点
动态加签
会签百分比
串行会签
会签任务节点
只需要在流程定义时，将任务节点的属性performType值设置为ALL即可，当调用api时传递多个参与者时，则自动派发与参与者数量相同的任务。会签任务必须等待所有参与者完成后，才继续流转

动态加签
可调用任务服务的addTaskActor方法实现动态加签。

engine.task().addTaskActor(String taskId, 1, String... actorIds)
1
会签百分比
暂未实现
1
串行会签
暂未实现
1
Fork/Join
snaker流程引擎中的所有节点模型都支持fork/join的并行流转。

建议fork/join配对使用，否则会造成流程数据不一致。

测试用例中的forkjoin流程图如下所示：



级联删除（历史数据清理）
级联删除主要用于流程定义、流程实例的数据。一般情况下，不建议在正式环境里使用此功能，顾名思义，会删除所有的关联数据，谨慎使用。

流程定义
engine.process().cascadeRemove(String processId)
1
会删除流程定义以及该定义启动的所有流程实例、任务，谨慎使用。

流程实例
engine.order().cascadeRemove(String orderId)
1
会删除流程实例以及该实例创建的所有任务，谨慎使用。

扩展点
自定义实例编号orderNo
源码：org.snaker.engine.core.OrderService

	/**
	 * 创建活动实例
	 */
	public Order createOrder(Process process, String operator, Map<String, Object> args, 
			String parentId, String parentNodeName) {
	    ...
		String orderNo = (String)args.get(SnakerEngine.ID);
		if(StringHelper.isNotEmpty(orderNo)) {
   		 order.setOrderNo(orderNo);
		} else {
   		 order.setOrderNo(model.getGenerator().generate(model));
           ...
	}
1
2
3
4
5
6
7
8
9
10
11
12
13
有2种方式进行自定义

开启实例时，参数中加入SnakerEngine.ID
实现INoGenerator 接口，并在流程定义的xml中配置
任务、实例完成时触发的回调
Completion：所有任务和实例在完成时会回到Completion的实现类。

源码：org.snaker.engine.core.TaskService

/**
 * 完成指定任务
 * 该方法仅仅结束活动任务，并不能驱动流程继续执行
 */
public Task complete(String taskId, String operator, Map<String, Object> args) {
       Completion completion = getCompletion(); //completion = ServiceContext.find(Completion.class);
       if(completion != null) {
           completion.complete(history);
       }
   return task;
}
1
2
3
4
5
6
7
8
9
10
11
从completion = ServiceContext.find(Completion.class);这里看出，仅能使用一个Completion实现类。

默认的任务、实例完成时触发的动作

public class GeneralCompletion implements Completion {
    private static final Logger log = LoggerFactory.getLogger(GeneralCompletion.class);

    public void complete(HistoryTask task) {
        log.info("The task[{}] has been user[{}] has completed", task.getId(), task.getOperator());
    }
    
    public void complete(HistoryOrder order) {
        log.info("The order[{}] has completed", order.getId());
    }
}
1
2
3
4
5
6
7
8
9
10
11
扩展示例如下：

@Component
public class EasyAdminCompletion implements Completion {
1
2
节点拦截器
snaker的拦截器支持所有的节点前后拦截，并且支持全局、局部拦截器。拦截器统一实现SnakerInterceptor接口

org.snaker.engine.SnakerInterceptor
1
全局拦截器
局部拦截器
全局拦截器
全局拦截器会拦截所有新产生的任务对象。自定义的全局拦截器需要配置到snaker.xml中。如默认支持的日志拦截器

<bean class="org.snaker.engine.impl.LogInterceptor"/>
1
例如：

@Component
public class EasyGlobalCreateTaskInterceptor implements SnakerInterceptor {
        @Override
    public void intercept(Execution execution) {
        // ...
    }
}    
1
2
3
4
5
6
7
局部拦截器
局部拦截器支持节点执行的前置、后置拦截。需要配置到task节点模型的preInterceptors[前置拦截]、postInterceptors[后置拦截]属性

源码分析
这里仅看全局拦截器实现原理。

源码：org.snaker.engine.handlers.impl.CreateTaskHandler

/**
	 * 根据任务模型、执行对象，创建下一个任务，并添加到execution对象的tasks集合中
	 */
	public void handle(Execution execution) {
		List<Task> tasks = execution.getEngine().task().createTask(model, execution);
		execution.addTasks(tasks);
		/**
		 * 从服务上下文中查找任务拦截器列表，依次对task集合进行拦截处理
		 */
		List<SnakerInterceptor> interceptors = ServiceContext.getContext().findList(SnakerInterceptor.class);
		try {
			for(SnakerInterceptor interceptor : interceptors) {
				interceptor.intercept(execution);
			}
		} catch(Exception e) {
			log.error("拦截器执行失败=" + e.getMessage());
			throw new SnakerException(e);
		}
	}
1
2
3
4
5
6
7
8
9
10
11
12
13
14
15
16
17
18
19
从List<SnakerInterceptor> interceptors = ServiceContext.getContext().findList(SnakerInterceptor.class);这里看出可以存在多个全局拦截器。

自定义处理节点
snaker的自定义节点可完成流程的全自动编排。只需要在自定义节点模型中配置处理类即可。

自定义节点的处理类有两种方式：

实现IHandler接口

只需要配置clazz属性即可

普通java类

需要设置clazzmethodNameargsvar

自定义节点的执行不需要外部触发，只要抵达节点立即执行绑定的类进行处理。并记录历史任务，处理类返回值保存在历史任务的变量字段中。

基于用户或组的访问策略
策略类用于判断当前操作人operator是否允许执行taskId指定的任务

源码：org.snaker.engine.core.TaskService

/**
	 * 判断当前操作人operator是否允许执行taskId指定的任务
	 */
	public boolean isAllowed(Task task, String operator) {
		if(StringHelper.isNotEmpty(operator)) {
			if(SnakerEngine.ADMIN.equalsIgnoreCase(operator)
					|| SnakerEngine.AUTO.equalsIgnoreCase(operator)) {
				return true;
			}
			if(StringHelper.isNotEmpty(task.getOperator())) {
				return operator.equals(task.getOperator());
			}
		}
		List<TaskActor> actors = access().getTaskActorsByTaskId(task.getId());
		if(actors == null || actors.isEmpty()) return true;
		return !StringHelper.isEmpty(operator)
				&& getStrategy().isAllowed(operator, actors); // 		strategy = ServiceContext.find(TaskAccessStrategy.class);
	}
====
    调用方法：complete、take
1
2
3
4
5
6
7
8
9
10
11
12
13
14
15
16
17
18
19
20
从strategy = ServiceContext.find(TaskAccessStrategy.class);可得出，仅可存在一个实现类。

扩展示例

@Component
public class GeneralAccessStrategy implements TaskAccessStrategy {
————————————————
版权声明：本文为CSDN博主「lakernote」的原创文章，遵循CC 4.0 BY-SA版权协议，转载请附上原文出处链接及本声明。
原文链接：https://blog.csdn.net/abu935009066/article/details/119568513
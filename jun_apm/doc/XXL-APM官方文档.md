# xxl-apm
A distributed APM(application-performance-management) platform.


### todo-apm
- core model
    - admin rpc discovery: xxl-reigtry [done]
    - transfer log: xxl-rpc, async + mult send [done]
    - log storage: file(client + server) + es(admin)
        - client file store [done]
        - server file store [done]
        - es store [ing]
    - analysize log
    - view: ui  
- Msg: msg type [done]
    - [Trace]: ApmMsg all support [done]
        - parentMsgId: null as root
        - msgId: appname-uuid
    - Transaction: type + status + time, qps/99line [done]
    - Event: type + status, qps/suc rate; [done]
    - Heartbeat: app and machine info, time line chart  [done]
- Probleam: 
    - [Trace]: by msg impl;
    - Event: threshold for 'suc rate'
    - Transaction: threshold for 'suc rate'、'99line long-rpc.service/long-rpc.client'
    - Heartbeat: gc
- Alarm: bind with appname
    - Email: email
    - WebHook: http://.....{error_msg}...

---
- Sample
    - springboot [done]
    - frameless [done]
    
- Support
    - XxlApmWebFilter: support trance [done]
    - Restful
---

- other
    - api：tcp + http
    - mvc、rpc、db、cache
    - trace, 拓扑

```

筛选：
     appname：required
     machine：all | ip
     time：自定义时间跨度 hour ~ 24hour
1、Transaction
     Type列表：
         字段：Type、总数、失败次数、失败率、QPS、LogView-最近3条；+ Max、Avg、90line、95line、99line、99.9line
         列表报表：
             全量-时间柱状图；
             失败-时间柱状图；
             耗时折线图-Max、Avg、90line、95line、99line、99.9line
         分布统计：
             IP分布列表：Ip、Hostname、总数、失败次数、失败率、QPS；+ Max、Avg、90line、95line、99line、99.9line
             IP分布比例饼图：
                 全量-比例图；
                 失败-比例图；
     Name列表：
         字段：Name、总数、失败次数、失败率、QPS、LogView-最近3条、占比；+ Max、Avg、90line、95line、99line、99.9line
         整体报表：占比饼图；
         单个Name报表：属性同Type；
2、Event：
     Type列表：
         列表字段：Type、总数、失败次数、失败率、QPS、LogView-最近3条；
         列表报表：
             全量-时间柱状图；
             失败-时间柱状图；
         分布统计：
             IP分布列表：Ip、Hostname、总数、失败次数、失败率、QPS
             IP分布比例饼图：
                 全量-比例图；
                 失败-比例图；
     Name列表：
         字段：Name、总数、失败次数、失败率、QPS、LogView-最近3条、占比
         整体报表：占比饼图；
         单个Name报表：属性同Type；
3、Heartbeat：时间折线图；
     筛选 time：限制 hour 范围内；
     报表列表：
         时间-指标柱状图，如内存、CPU等；
     线程堆栈：IP + 分钟维度；
         线程堆栈列表：线程数 + 状态分布 + 线程列表-详情；
     数据结构：
        Mysql 表：

---
数据：
    报表（ Mysql 存储）：汇聚到 min 维度上，数据量可控；
    LogView（ES）：全量 LogView，数据量不可用，依赖ES;
全量采集；
单机器集群：appname + address, heartbeat 重复数据抛弃； 

---
- transaction time: map >> db | es >> client real-time compluting, for min;
- xxlapm，全异步：批量压缩，批量发送。
- 日报，小时报；预先生成报表数据；
- 控制日志量，原则：核心日志仅输出变更，常规日志改为 debug 级别；
- qps计算规则调整，total除时间，60或低于60分母不同；
- fullgc 问题排查套路：
    - 确定出发原因，典型四种。
    - dump 内存分析：dump，jmap，分析工具
- 死锁线程检测：ing
- apm，内存简要分析
- event消息，新增count属性，借助map累加降低消息量；

- nest:
    - trans 平均耗时，tp数据-分钟维度报表。
    - 调用链：es关联，logview展示；
    - problem：appname维度查询，实时展示异常；
    - 告警配置：appname邮箱，fullgc、event、trans等阈值；
    - 文档：sample示例，接入步骤；

---                
大盘：分钟维度；
     报错大盘：Transaction、Event 报错，统计 name 汇总次数；
告警：阈值，应用负责人邮箱；
     报错报警：针对Type设置报警阈值，百分比或次数，超过告警；
     Heartbeat 告警：针对 Heartbeat 设置报警阈值，百分比或次数，超过告警；
     
------


```
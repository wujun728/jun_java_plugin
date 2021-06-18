#spring-dynamic-job

项目用Maven管理


使用的技术与版本号
<ol>
 <li>Java (1.7)</li>
 <li>Spring (3.2.2.RELEASE)</li>
 <li>Quartz (2.2.3)</li>
 <li>mysql-connector-java (5.1.35)</li>
</ol>
<hr/>
<strong>使用说明</strong>
<ol>
    <li>
        Check-out代码, 使用IDE(如IDEA) 打开, 配置应用服务器(如TOMCAT)
    </li>
    <li>
        修改<code>spring-dynamic-job.properties</code>文件,并创建数据库(默认数据为名sdj),并运行<code>others/quartz_mysql_innodb.sql</code>文件初始化数据库
    </li>
    <li>
        启用TOMCAT, 访问
    </li>
</ol>

<p>
    项目的核心对象为: <code>DynamicSchedulerFactory</code>
    <br/>
    该项目已在 <a href="http://git.oschina.net/mkk/HeartBeat">HeartBeat</a> 项目中实际使用,
    更多运用案例可查看该项目.
</p>

<p>
    动态的Job定义类: <code>DynamicJob</code>, 在动态操作Job时的类必须是该类的子类(参考<code>TestDynamicJob</code>)
    <br/>
    固定执行的Job示例类: <code>TestFixedJobDetailBean</code>, 配置在XML中,启动时加载,固定执行.
    <br/>
    更多的操作请参考类: <code>TestServiceImpl</code>
</p>

<p>如果不使用数据库(内存中存储job信息), 取消 dataSource 的配置与引用项</p>

<hr/>

<strong>详细使用</strong>

定时任务分两类, 固定的与动态的

一.固定的    - 即指固定时间执行(如每天凌晨2点, 或每隔30秒执行)

1.参考TestFixedJobDetailBean类写具体的实现

2.在idp2.xml中配置 job (参照注释部分)<br/>
  1). 配置 JobDetailFactoryBean<br/>
  2).配置 CronTriggerFactoryBean 注意 cronExpression 的值配置在job.properties<br/>
      对于 cronExpression 的写法, 请参考 http://blog.csdn.net/caiwenfeng_for_23/article/details/17004213<br/>
  3).将CronTriggerFactoryBean 添加到 schedulerFactory 的<list>中<br/>

提示: 对于 cronExpression 可使用 CronExpression.java 类 进行测试


二.动态的    - 即在系统运行中动态添加的job(如 用户添加的 用户同步定时任务)

1.参考 TestDynamicJob 类写具体的实现

2.在需要使用时 创建对应的 DynamicJob 对象,参考如下:

    //创建 一个动态的JOB, 测试用
    private DynamicJob createDynamicJob() {
        return new DynamicJob("test-")
                //动态定时任务的 cron,  每20秒执行一次
                .cronExpression("0/20 * * * * ?")
                .target(TestDynamicJob.class);
    }

提示: Job name 要具体, cronExpression 一般根据时间对象(Date)来生成, target对第1步新建的对象

3.如果在Job执行时有参数, 加上对应的参数名与参数值(可多个), 如下:<br/>

        DynamicJob dynamicJob = createDynamicJob();
        dynamicJob.addJobData("mailGuid", UUID.randomUUID().toString());//transfer parameter

这样在Job实现类中可通过 context 来获取,如下:<br/>
<pre>
final Object mailGuid = context.getMergedJobDataMap().get("mailGuid");
</pre>
4.向 DynamicSchedulerFactory 中注册job, 如下:<br/>
<pre>
 DynamicSchedulerFactory.registerJob(dynamicJob);
</pre>

<hr/>
<strong>帮助与改进</strong>
<ol>
<li>
<p>
 与该项目相关的博客请访问 <a target="_blank" href="http://blog.csdn.net/monkeyking1987/article/details/42173277">http://blog.csdn.net/monkeyking1987/article/details/42173277</a>
</p>
</li>
<li>
<p>
 若没有找到解决办法的,
 欢迎发邮件到<a href="mailto:shengzhao@shengzhaoli.com">shengzhao@shengzhaoli.com</a>一起讨论.
</p>
</li>

<li>
<p>
 如果在使用项目的过程中发现任何的BUG或者更好的提议, 建议将其提交到项目的 <a href="http://git.oschina.net/mkk/spring-dynamic-job/issues">Issues</a> 中,
 我会一直关注并不断改进项目.
</p>
</li>
</ol>

<hr/>
<p>
 关注更多我的开源项目请访问 <a href="http://andaily.com/my_projects.html">http://andaily.com/my_projects.html</a>
</p>

### [springmvc-jpa](https://github.com/wangxinforme/springmvc-jpa)
<code>它是一个典型的MVC三层框架示例工程，快速简单的上手。</code>


+ 介绍：
	+ 集成框架有：SpringMVC、Hibernate jpa2、Bootstrap3、Apache Shiro、Sitemesh3、memcached、solr、redis、log4j2；
	+ 集成示例有：用户登录、文件上传下载、文件压缩、JQuery联想搜索；
	+ 数据库支持：Oracle、MySQL、SQLite；</br></br>
	
+ 启动工程：
	+ <code>web.xml</code>文件当中的<code>spring.profiles.default</code>可切换系统环境，选development是以开发环境启动，选production则是生产环境启动；
	+ 数据库选择，默认开启SQLite数据库，若改用Oracle或SQLite，可在工程的<code>config-datasource.properties</code>数据源配置文件当中设置，按启动的是开发环境还是生产环境来选择数据源配置文件；
	+ 若采用的是SQLite数据库，本步骤可跳过，初始化数据可查看<code>InitServiceTest</code>类中的Junit方法，注意下单元测试类当中<code>@ActiveProfiles("")</code>，请指定相应的环境；
	+ 如若启动<code>memcached、solr、redis</code>，修改<code>web.xml</code>文件当中spring配置文件加载项；
		+ 如果启用<code>Memcached</code>，打开<code>MemcachedFactory</code>类中的<code>@Resource(name = "memcachedClient")</code>注释
		+ 如果启用<code>Solr</code>，打开<code>BaseSolrRepositoryImpl</code>接口实现类中的<code>@Resource(name = "solrcloud_server")</code>注释
		+ 如果启用<code>Redis</code>，打开<code>RedisBaseService</code>抽象类中的<code>@Resource(name = "redisTemplate")</code>注释
	+ 启动工程；
	+ 浏览器访问工程查看示例效果；</br></br>



欢迎[交流讨论](https://github.com/wangxinforme/springmvc-jpa/issues)

<b>[胡桃夹子GitHub](https://github.com/wangxinforme "Vincent Git@OSC主页")</b>


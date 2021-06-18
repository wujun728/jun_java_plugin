一、数据库工具类
	1、com.baijob.commonTools.db.ds
		
		C3p0Ds 和 DruidDs分别是两种连接池的实现，依赖于数据库配置文件，配置文件的样例参考config/db-example.setting
		使用时将db-example.setting复制于${classpath}/config/db.setting，按照配置文件中的说明替换相应值
		如果使用Druid，则需参考druid-example.setting创建${classpath}/config/druid.setting文件，详情请参考官方文档
		使用C3P0则需要参考c3p0-config-example.xml创建${classpath}/c3p0-config.xml来调节C3P0参数
		此时即可调用C3p0Ds.getDataSource()或DruidDs.getDataSource()方法获得默认的数据源
		
		如果要自定义数据库配置文件的参数，请调用相应的init()，传入相关参数
		注：Setting对象请参考与之对应的章节
		
	2、com.baijob.commonTools.db.DbUtil
		
		数据库工具类，提供了关闭方法：关闭可以传入多个参数，关闭的顺序是按照参数的顺序来的，用于一次性关闭Connnection、Statement、ResultSet等
		newSqlRunner方法用于快速新建一个SqlRunner（此类介绍参考下问）
		
	3、com.baijob.commonTools.db.DsSetting，用于读取db.setting文件辅助类，内部使用
	
	4、com.baijob.commonTools.db.SqlRunner类参考Apache的DbUtils工具包，封装了常用的增删改查方法，与com.baijob.commonTools.db.RsHandler配合使用
		com.baijob.commonTools.db.RsHandler接口与Apache的DbUtils的ResultSetHandler等价，抽象结果集处理。
		
二、邮件工具类
	
	1、com.baijob.commonTools.mail.MailAccount 邮件账户类。
		可以调用MailAccount(String accountSettingFileBaseClassLoader)读取相对路径的Setting文件，配置参考mailAccount-example.setting
		
	2、com.baijob.commonTools.mail.MailUtil邮件发送工具类，方法请参考注释
	
	此工具类依赖javax.mail，请参考pom.xml添加依赖或手动下载
	
三、网络相关工具类
	
	1、com.baijob.commonTools.net.AccessControl访问控制，基于配置文件，可以设定IP白名单或黑名单，可以通过配置文件实现简单的账户验证。
		配置文件请参考access-example.xml
	
	2、com.baijob.commonTools.net.Connector 连接对象实体类，有host、端口、用户名、密码等属性
	
	3、com.baijob.commonTools.net.HtmlUtil HTML工具类，暂时只提供特殊字符转义
	
	4、com.baijob.commonTools.net.SocketUtil socket工具类。
		isUsableLocalPort() 检测本地某个端口是否可用（可用是指没有被其他程序占用）
		isValidPort()是否是符合规范的端口号
		longToIpv4()将long转换为ipv4地址，反方法是ipv4ToLong()
		netCat()简易的数据发送方法
		
	5、com.baijob.commonTools.net.SSHUtil SSH相关工具类
		getSession()获得一个SSH会话
		bindPort()将远程主机的端口映射到本地某个端口
		
	6、com.baijob.commonTools.net.URLUtil 将相对、绝对路径转换为URL对象，用于网络或文件流的读写，Setting的配置依赖此工具包
	
四、线程相关工具类
	1、com.baijob.commonTools.thread.BaseRunnable 此类实现了Runnable接口，扩展了功能。
		增加名称、ID，调用次数和时间统计、线程停止接口等，并且在线程运行时，不允许此线程第二次启动。
	
	2、com.baijob.commonTools.thread.Executor 线程池工具类
		调用静态方法execute()启动线程，此线程在公共的线程池中执行
		若想自定义线程池大小或独立控制，可调用newExecutor()实例化一个线程池
		excAsync()执行一个异步方法
	
	3、com.baijob.commonTools.thread.SyncQueue 阻塞队列，简化了JDK的BlockingQueue
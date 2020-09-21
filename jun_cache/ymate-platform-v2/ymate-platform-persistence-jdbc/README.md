### JDBC

JDBC持久化模块针对关系型数据库(RDBMS)数据存取的一套简单解决方案，主要关注数据存取的效率、易用性和透明，其具备以下功能特征：

- 基于JDBC框架API进行轻量封装，结构简单、便于开发、调试和维护；
- 优化批量数据更新、标准化结果集、预编译SQL语句处理；
- 支持单实体ORM操作，无需编写SQL语句；
- 提供脚手架工具，快速生成数据实体类，支持链式调用；
- 支持通过存储器注解自定义SQL语句或从配置文件中加载SQL并自动执行；
- 支持结果集与值对象的自动装配，支持自定义装配规则；
- 支持多数据源，默认支持C3P0、DBCP、JNDI连接池配置，支持数据源扩展；
- 支持多种数据库(如:Oracle、MySQL、SQLServer等)；
- 支持面向对象的数据库查询封装，有助于减少或降低程序编译期错误；
- 支持数据库事务嵌套；
- 支持数据库视图和存储过程；

#### Maven包依赖

    <dependency>
        <groupId>net.ymate.platform</groupId>
        <artifactId>ymate-platform-persistence-jdbc</artifactId>
        <version>2.0.2</version>
    </dependency>

> **注**：在项目的pom.xml中添加上述配置，该模块已经默认引入核心包及持久化基础包依赖，无需重复配置。

####模块初始化配置

    #-------------------------------------
    # JDBC持久化模块初始化参数
    #-------------------------------------

    # 默认数据源名称，默认值为default
    ymp.configs.persistence.jdbc.ds_default_name=

    # 数据源列表，多个数据源名称间用'|'分隔，默认为default
    ymp.configs.persistence.jdbc.ds_name_list=

    # 是否显示执行的SQL语句，默认为false
    ymp.configs.persistence.jdbc.ds.default.show_sql=

    # 是否开启堆栈跟踪，默认为false
    ymp.configs.persistence.jdbc.ds.default.stack_traces=

    # 堆栈跟踪层级深度，默认为0(即全部)
    ymp.configs.persistence.jdbc.ds.default.stack_trace_depth=

    # 堆栈跟踪包名前缀过滤，默认为空
    ymp.configs.persistence.jdbc.ds.default.stack_trace_package=

    # 数据库表前缀名称，默认为空
    ymp.configs.persistence.jdbc.ds.default.table_prefix=

    # 数据源适配器，可选值为已知适配器名称或自定义适配置类名称，默认为default，目前支持已知适配器[default|dbcp|c3p0|jndi|...]
    ymp.configs.persistence.jdbc.ds.default.adapter_class=

    # 数据库类型，可选参数，默认值将通过连接字符串分析获得，目前支持[mysql|oracle|sqlserver|db2|sqlite|postgresql|hsqldb|h2]
    ymp.configs.persistence.jdbc.ds.default.type=

    # 数据库方言，可选参数，自定义方言将覆盖默认配置
    ymp.configs.persistence.jdbc.ds.default.dialect_class=

    # 数据库连接驱动，可选参数，框架默认将根据数据库类型进行自动匹配
    ymp.configs.persistence.jdbc.ds.default.driver_class=

    # 数据库连接字符串，必填参数
    ymp.configs.persistence.jdbc.ds.default.connection_url=

    # 数据库访问用户名称，必填参数
    ymp.configs.persistence.jdbc.ds.default.username=

    # 数据库访问密码，可选参数，经过默认密码处理器加密后的admin字符串为wRI2rASW58E
    ymp.configs.persistence.jdbc.ds.default.password=

    # 数据库访问密码是否已加密，默认为false
    ymp.configs.persistence.jdbc.ds.default.password_encrypted=

    # 数据库密码处理器，可选参数，用于对已加密数据库访问密码进行解密，默认为空
    ymp.configs.persistence.jdbc.ds.default.password_class=

配置参数补充说明：
> 数据源的数据库连接字符串和用户名是必填项，其它均为可选参数，最简配置如下：
>
>   >ymp.configs.persistence.jdbc.ds.default.connection_url=jdbc:mysql://localhost:3306/mydb
>   >
>   >ymp.configs.persistence.jdbc.ds.default.username=root
>
> 为了避免明文密码出现在配置文件中，YMP框架提供了默认的数据库密码处理器，或者通过IPasswordProcessor接口自行实现；
>
>   >net.ymate.platform.core.support.impl.DefaultPasswordProcessor

#### 数据源（DataSource）

##### 多数据源连接

JDBC持久化模块默认支持多数据源配置，下面通过简单的配置来展示如何连接多个数据库：

	# 定义两个数据源分别用于连接MySQL和Oracle数据库，同时指定默认数据源为default(即MySQL数据库)
    ymp.configs.persistence.jdbc.ds_default_name=default
    ymp.configs.persistence.jdbc.ds_name_list=default|oracledb
    
    # 连接到MySQL数据库的数据源配置
    ymp.configs.persistence.jdbc.ds.default.connection_url=jdbc:mysql://localhost:3306/mydb
    ymp.configs.persistence.jdbc.ds.default.username=root
    ymp.configs.persistence.jdbc.ds.default.password=123456

	# 连接到Oracle数据库的数据源配置
    ymp.configs.persistence.jdbc.ds.oracledb.connection_url=jdbc:oracle:thin:@localhost:1521:ORCL
    ymp.configs.persistence.jdbc.ds.oracledb.username=ORCL
    ymp.configs.persistence.jdbc.ds.oracledb.password=123456

从上述配置中可以看出，配置不同的数据源时只需要定义数据源名称列表，再根据列表逐一配置即可；

##### 连接池配置

JDBC持久化模块提供的数据源类型如下：

- default：默认数据源适配器，通过DriverManager直接连接数据库，建议仅用于测试；
- c3p0：基于C3P0连接池的数据源适配器；
- dbcp：基于DBCP连接池的数据源适配器；
- jndi：基于JNDI的数据源适配器；

只需根据实际情况调整对应数据源名称的配置，如：

    ymp.configs.persistence.jdbc.ds.default.adapter_class=dbcp

针对于dbcp和c3p0连接池的配置文件及内容，请将对应的dbcp.properties或c3p0.properties文件放置在工程的classpath根路径下，配置内容请参看JDBC持久化模块开源工程中的示例文件；

当然，也可以通过IDataSourceAdapter接口自行实现，框架针对IDataSourceAdapter接口提供了一个抽象封装AbstractDataSourceAdapter类，直接继承即可；

##### 数据库连接持有者（IConnectionHolder）

用于记录真正的数据库连接对象（Connection）原始的状态及与数据源对应关系；

#### 数据实体（Entity）

##### 数据实体注解

- @Entity：声明一个类为数据实体对象；

    > value：实体名称(数据库表名称)，默认采用当前类名称；

        @Entity("tb_demo")
        public class Demo {
            //...
        }

- @Id：声明一个类成员为主键；

    > 无参数，配合@Property注解使用；

        @Entity("tb_demo")
        public class Demo {

            @Id
            @Property
            private String id;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }
        }

- @Property：声明一个类成员为数据实体属性；

    > name：实现属性名称，默认采用当前成员名称；
    >
    > autoincrement：是否为自动增长，默认为false；
    >
    > sequenceName：序列名称，适用于类似Oracle等数据库，配合autoincrement参数一同使用；
    > 
    > nullable：允许为空，默认为true；
    > 
    > unsigned：是否为无符号，默认为false；
    > 
    > length：数据长度，默认0为不限制；
    > 
    > decimals：小数位数，默认0为无小数；
    > 
    > type：数据类型，默认为Type.FIELD.VARCHAR；

        @Entity("tb_user")
        public class User {

            @Id
            @Property
            private String id;

            @Property(name = "user_name",
                      nullable = false,
                      length = 32)
            private String username;

            @Property(name = "age",
                      unsigned = true,
                      type = Type.FIELD.INT)
            private Integer age;

            // 省略Get/Set方法...
        }

- @PK：声明一个类为某数据实体的复合主键对象；

    > 无参数；
    
        @PK
        public class UserExtPK {

            @Property
            private String uid;

            @Property(name = "wx_id")
            private String wxId;

            // 省略Get/Set方法...
        }
    
        @Entity("tb_user_ext")
        public class UserExt {

            @Id
            private UserExtPK id;

            @Property(name = "open_id",
                      nullable = false,
                      length = 32)
            private String openId;

            // 省略Get/Set方法...
        }

- @Readonly：声明一个成员为只读属性，数据实体更新时其将被忽略；

    > 无参数，配合@Property注解使用；

        @Entity("tb_demo")
        public class Demo {

            @Id
            @Property
            private String id;

            @Property(name = "create_time")
            @Readonly
            private Date createTime;

            // 省略Get/Set方法...
        }

- @Indexes：声明一组数据实体的索引；
 
- @Index：声明一个数据实体的索引；
    
- @Comment：注释内容；

- @Default：为一个成员属性或方法参数指定默认值；
    
看着这么多的注解，是不是觉得编写实体很麻烦呢，不要急，框架提供了自动生成实体的方法，往下看:)
    
**注**：上面注解或注解参数中有一些是用于未来能通过实体对象直接创建数据库表结构（以及SQL脚本文件）的，可以暂时忽略；

##### 自动生成实体类

YMP框架自v1.0开始就支持通过数据库表结构自动生成实体类代码，所以v2.0版本不但重构了实体代码生成器，而且更简单好用！

    #-------------------------------------
    # JDBC数据实体代码生成器配置参数
    #-------------------------------------

    # 是否生成新的BaseEntity类，默认为false(即表示使用框架提供的BaseEntity类)
    ymp.params.jdbc.use_base_entity=

    # 是否使用类名后缀，不使用和使用的区别如: User-->UserModel，默认为false
    ymp.params.jdbc.use_class_suffix=

	# 是否采用链式调用模式，默认为false
	ymp.params.jdbc.use_chain_mode=

    # 是否添加类成员属性值状态变化注解，默认为false
    ymp.params.jdbc.use_state_support=

    # 实体及属性命名过滤器接口实现类，默认为空
    ymp.params.jdbc.named_filter_class=

    # 数据库名称(仅针对特定的数据库使用，如Oracle)，默认为空
    ymp.params.jdbc.db_name=

    # 数据库用户名称(仅针对特定的数据库使用，如Oracle)，默认为空
    ymp.params.jdbc.db_username=

    # 数据库表名称前缀，多个用'|'分隔，默认为空
    ymp.params.jdbc.table_prefix=

    # 否剔除生成的实体映射表名前缀，默认为false
    ymp.params.jdbc.remove_table_prefix=

    # 预生成实体的数据表名称列表，多个用'|'分隔，默认为空表示全部生成
    ymp.params.jdbc.table_list=

    # 排除的数据表名称列表，在此列表内的数据表将不被生成实体，多个用'|'分隔，默认为空
    ymp.params.jdbc.table_exclude_list=

    # 需要添加@Readonly注解声明的字段名称列表，多个用'|'分隔，默认为空
    ymp.params.jdbc.readonly_field_list=

    # 生成的代码文件输出路径，默认为${root}
    ymp.params.jdbc.output_path=

    # 生成的代码所属包名称，默认为: packages
    ymp.params.jdbc.package_name=

实际上你可以什么都不用配置（请参看以上配置项说明，根据实际情况进行配置），但使用过程中需要注意以下几点：

> - 代码生成器依赖JDBC持久化模块才能完成与数据库连接等操作；
> 
> - 在多数据源模式下，代码生成器使用的是默认数据源；
> 
> - 代码生成器依赖`freemarker`模板引擎，所以请检查依赖关系是否正确；
>
> - 在WEB工程中运行代码生成器时请确认`servlet-api`和`jsp-api`包依赖关系是否正确；
>
> - 如果你的工程中引用了很多的模块，在运行代码生成器时可以暂时通过ymp.excluded_modules参数排除掉；
>
> - 如果使用的JDBC驱动是`mysql-connector-java-6.x`及以上版本时，则必须配置`db_name`和`db_username`参数；
>
> - 实体及属性命名过滤器参数`named_filter_class`指定的类需要实现`IEntityNamedFilter`接口；

了解了以上的配置后，直接运行代码生成器：

    net.ymate.platform.persistence.jdbc.scaffold.EntityGenerator

找到并运行它，如果是Maven项目，可以通过以下命令执执行：
    
	mvn compile exec:java -Dexec.mainClass="net.ymate.platform.persistence.jdbc.scaffold.EntityGenerator"

OK！就这么简单，一切都结束了！

当然，上面介绍的实体生成方法还是有些麻烦，所以我们提供了另外一种更方便的方式 —— 通过YMP框架提供的Maven扩展工具生成实体：

- 步骤1：编译并安装`ymate-maven-extension`扩展工具

    - 下载YMP框架Maven扩展工具源码（[点击这里查看此项目](https://git.oschina.net/suninformation/ymate-maven-extension)）
    
        执行命令：

            git clone https://git.oschina.net/suninformation/ymate-maven-extension.git

    - 编译并安装到本地Maven仓库

        执行命令: 

            cd ymate-maven-extension
            mvn clean install

- 步骤2：将pom.xml中添加`ymate-maven-plugin`插件

        <plugin>
            <groupId>net.ymate.maven.plugins</groupId>
            <artifactId>ymate-maven-plugin</artifactId>
            <version>1.0-SNAPSHOT</version>
        </plugin>

- 步骤3：执行插件生成实体

    在工程根路径下执行命令：

        mvn ymate:entity

    输出内容：
    
        Picked up JAVA_TOOL_OPTIONS: -Dfile.encoding=UTF-8
        [INFO] Scanning for projects...
        [INFO] ......（此处省略若干字）
        [INFO] --- ymate-maven-plugin:1.0-SNAPSHOT:entity (default-cli) @ ymp-examples-webapp ---
        三月 25, 2016 12:25:07 上午 net.ymate.platform.core.YMP init
        信息: 
        __   ____  __ ____          ____  
        \ \ / /  \/  |  _ \  __   _|___ \ 
         \ V /| |\/| | |_) | \ \ / / __) |
          | | | |  | |  __/   \ V / / __/ 
          |_| |_|  |_|_|       \_/ |_____|  Website: http://www.ymate.net/
        三月 25, 2016 12:25:07 上午 net.ymate.platform.core.YMP init
        信息: Initializing ymate-platform-core-2.0.0-GA build-20160324-2339 - debug:true
        ......（此处省略若干字）
        信息: [show tables][][1][13ms]
        Output file "/Users/suninformation/IdeaProjects/ymate-platform-examples/ymp-examples-webapp/src/main/java/net/ymate/platform/examples/model/User.java".
        [INFO] ------------------------------------------------------------------------
        [INFO] BUILD SUCCESS
        [INFO] ------------------------------------------------------------------------
        [INFO] Total time: 1.577s
        [INFO] Finished at: Fri Mar 25 00:25:08 CST 2016
        [INFO] Final Memory: 10M/163M
        [INFO] ------------------------------------------------------------------------

    通过插件生成的代码默认放置在`src/main/java`路径，当数据库表发生变化时，直接执行插件命令就可以快速更新数据实体对象，**是不是很更方便呢，大家可以动手尝试一下！**:p

#### 事务（Transaction）

基于YMPv2.0的新特性，JDBC模块对数据库事务的处理更加灵活，任何被类对象管理器管理的对象都可以通过@Transaction注解支持事务；

- @Transaction注解：
	+ 参数说明：

		> value：事务类型（参考JDBC事务类型），默认为JDBC.TRANSACTION.READ_COMMITTED；
	
	+ 使用方式：
	
		> 首先，需要数据库事务支持的类对象必须声明@Transaction注解；
		>
		> 然后，在具体需要开启事务处理的类方法上添加@Transaction注解；

- 事务示例代码：

		public interface IUserService {
		
			User doGetUser(String username, String pwd);
			
			boolean doLogin(String username, String pwd);
		}
		
		@Bean
		@Transaction
		public class UserService implements IUserService {
		
			public User doGetUser(final String username, final String pwd) {
				return JDBC.get().openSession(new ISessionExecutor<User>() {
	                public User execute(ISession session) throws Exception {
	                    Cond _cond = Cond.create().eq("username").param(username).eq("pwd").param(pwd);
	                    return session.findFirst(EntitySQL.create(User.class), Where.create(_cond));
	                }
	            });
			}
		
			@Transaction
			public boolean doLogin(String username, String pwd) {
				User _user = doGetUser(username, pwd);
				if (_user != null) {
					_user.setLastLoginTime(System.currentTimeMillis());
					_user.update();
					//
					return true;
				}
				return false;
			}
		}
		
		@Bean
		public class TransDemo {
			
			@Inject
			private IUserService __userService;
			
			public boolean testTrans() {
				return __userService.doLogin("suninformation", "123456");
			}
			
			public static void main(String[] args) throws Exception {
				YMP.get().init();
				try {
					TransDemo _demo = YMP.get().getBean(TransDemo.class);
					_demo.testTrans();
				} finally {
					YMP.get().destroy();
				}
			}
		}

#### 会话（Session）

会话是对应用中具体业务操作触发的一系列与数据库之间的交互过程的封装，通过建立一个临时通道，负责与数据库之间连接资源的创建及回收，同时提供更为高级的抽象指令接口调用，基于会话的优点：

> 开发人员不需要担心连接资源是否正确释放；
> 
> 严格的编码规范更利于维护和理解；
> 
> 更好的业务封装性；

- 会话对象参数：

	+ 数据库连接持有者（IConnectionHolder）：
	
		指定本次会话使用的数据源连接；

	+ 会话执行器（ISessionExecutor）：
	
		以内部类的形式定义本次会话返回结果对象并提供Session实例对象的引用；
	

- 开启会话示例代码：

		// 使用默认数据源开启会话
		User _result = JDBC.get().openSession(new ISessionExecutor<User>() {
			public User execute(ISession session) throws Exception {
				// TODO 此处填写业务逻辑代码
				return session.findFirst(EntitySQL.create(User.class));
			}
		});
		
		// 使用指定的数据源开启会话
		IConnectionHolder _conn = JDBC.get().getConnectionHolder("oracledb");
		// 不需要关心_conn对象的资源释放
		IResultSet<User> _results = JDBC.get().openSession(_conn, new ISessionExecutor<IResultSet<User>>() {
			public IResultSet<User> execute(ISession session) throws Exception {
				// TODO 此处填写业务逻辑代码
				return session.find(EntitySQL.create(User.class));
			}
		});

- 基于ISession接口的数据库操作：

	示例代码是围绕用户(User)数据实体完成的CRUD(新增、查询、修改、删除)操作来展示如何使用ISession对象，数据实体如下：
	
			@Entity("user")
	    	public static class User extends BaseEntity<User, String> {
	
		        @Id
		        @Property
		        private String id;
		
		        @Property(name = "user_name")
		        private String username;
		
		        @Property(name = "pwd")
		        private String pwd;
		
		        @Property(name = "sex")
		        private String sex;
		
		        @Property(name = "age")
		        private Integer age;
		
		        // 忽略Getter和Setter方法
		
		        public static class FIELDS {
		            public static final String ID = "id";
		            public static final String USER_NAME = "username";
		            public static final String PWD = "pwd";
		            public static final String SEX = "sex";
		            public static final String AGE = "age";
		        }
		        public static final String TABLE_NAME = "user";
		    }

	+ 插入（Insert）：
	
			User _user = new User();
            _user.setId(UUIDUtils.UUID());
            _user.setUsername("suninformation");
            _user.setPwd(DigestUtils.md5Hex("123456"));
            _user.setAge(20);
            _user.setSex("F");
            // 执行数据插入
            session.insert(_user);
            
            // 或者在插入时也可以指定/排除某些字段
            session.insert(_user, Fields.create(User.FIELDS.SEX, User.FIELDS.AGE).excluded(true));
	
	+ 更新（Update）：

			User _user = new User();
            _user.setId("bc19f5645aa9438089c5e9954e5f1ac5");
            _user.setPwd(DigestUtils.md5Hex("654321"));
            // 更新指定的字段
            session.update(_user, Fields.create(User.FIELDS.PWD));
	
	+ 查询（Find）：

		- 方式一：通过数据实体设置条件（非空属性之间将使用and条件连接），查询所有符合条件的记录；
		
				User _user = new User();
	            _user.setUsername("suninformation");
	            _user.setPwd(DigestUtils.md5Hex("123456"));
	            // 返回所有字段
	            IResultSet<User> _users = session.find(_user);
	            // 或者返回指定的字段
	            _users = session.find(_user, Fields.create(User.FIELDS.ID, User.FIELDS.AGE));
	    
		- 方式二：通过自定义条件，查询所有符合条件的记录；
		
				IResultSet<User> _users = session.find(
                        EntitySQL.create(User.class)
                                .field(User.FIELDS.ID)
                                .field(User.FIELDS.SEX), 
                        // 设置Order By条件
                        Where.create()
                                .orderDesc(User.FIELDS.USER_NAME));
	
		- 方式三：分页查询；

				IResultSet<User> _users = session.find(
                        EntitySQL.create(User.class)
                                .field(User.FIELDS.ID)
                                .field(User.FIELDS.SEX),
                        Where.create()
                                .orderDesc(User.FIELDS.USER_NAME),
                        // 查询第1页，每页10条记录，统计总记录数
                        Page.create(1).pageSize(10).count(true));
		
		- 方式四：仅返回符合条件的第一条记录(FindFirst)；

				// 查询用户名称和密码都匹配的第一条记录
                User _user = session.findFirst(EntitySQL.create(User.class), 
                        Where.create(
                                Cond.create()
                                        .eq(User.FIELDS.USER_NAME).param("suninformation")
                                        .and()
                                        .eq(User.FIELDS.PWD).param(DigestUtils.md5Hex("123456"))));

		**注**：更多的查询方式将在后面的 “查询（Query）” 章节中详细阐述；
	
	+ 删除（Delete）：

		- 根据实体主键删除记录：

				User _user = new User();
                _user.setId("bc19f5645aa9438089c5e9954e5f1ac5");
                //
                session.delete(_user);
                
                //
                session.delete(User.class, "bc19f5645aa9438089c5e9954e5f1ac5");
        
        - 根据条件删除记录：

        		// 删除年龄小于20岁的用户记录
        		session.executeForUpdate(
                        SQL.create(
                                Delete.create(User.class).where(
                                        Where.create(
                                                Cond.create()
                                                        .lt(User.FIELDS.AGE).param(20)))));
	
	+ 统计（Count）：

				// 统计年龄小于20岁的用户记录总数
				
				// 方式一：
				long _count = session.count(User.class, 
                        Where.create(
                                Cond.create()
                                        .lt(User.FIELDS.AGE).param(20)));

				// 方式二：
                _count = session.count(
                        SQL.create(
                                Delete.create(User.class).where(
                                        Where.create(
                                                Cond.create()
                                                        .lt(User.FIELDS.AGE).param(20)))));
	
	
	+ 执行更新类操作（ExecuteForUpdate）：

		该方法用于执行ISession接口中并未提供对应的方法封装且执行操作会对数据库产生变化的SQL语句，执行该方法后将返回受影响记录行数，如上面执行的删除年龄小于20岁的用户记录：
		
				int _effectCount =session.executeForUpdate(
                        SQL.create(
                                Delete.create(User.class).where(
                                        Where.create(
                                                Cond.create()
                                                        .lt(User.FIELDS.AGE).param(20)))));
	
	**注**：以上操作均支持批量操作，具体使用请阅读API接口文档和相关源码；

#### 数据实体操作

上面阐述的是基于ISession会话对象完成一系列数据库操作，接下来介绍的操作过程更加简单直接，完全基于数据实体对象；
	
> 注意：本小节所指的数据实体对象必须通过继承框架提供BaseEntity抽象类；
	
##### 插入（Insert）

	User _user = new User();
    _user.setId(UUIDUtils.UUID());
    _user.setUsername("suninformation");
    _user.setPwd(DigestUtils.md5Hex("123456"));
    _user.setAge(20);
    _user.setSex("F");
    // 执行数据插入
    _user.save();
    
    // 或者在插入时也可以指定/排除某些字段
    _user.save(Fields.create(User.FIELDS.SEX, User.FIELDS.AGE).excluded(true));
    
    // 或者插入前判断记录是否已存在，若已存在则执行记录更新操作
    _user.saveOrUpdate();
    
    // 或者执行记录更新操作时仅更新指定的字段
    _user.saveOrUpdate(Fields.create(User.FIELDS.SEX, User.FIELDS.AGE));

##### 更新（Update）

	User _user = new User();
    _user.setId("bc19f5645aa9438089c5e9954e5f1ac5");
    _user.setPwd(DigestUtils.md5Hex("654321"));
    _user.setAge(20);
    _user.setSex("F");
    // 执行记录更新
    _user.update();
    
    // 或者仅更新指定的字段
    _user.update(Fields.create(User.FIELDS.SEX, User.FIELDS.AGE));

##### 查询（Find）

+ 根据记录ID加载：
	
		User _user = new User();
		_user.setId("bc19f5645aa9438089c5e9954e5f1ac5");
		// 根据记录ID加载全部字段
		_user = _user.load();
		
		// 或者根据记录ID加载指定的字段
		_user = _user.load(Fields.create(User.FIELDS.USER_NAME, User.FIELDS.SEX, User.FIELDS.AGE));
	
+ 通过数据实体设置条件（非空属性之间将使用and条件连接），查询所有符合条件的记录；
	
		User _user = new User();
        _user.setUsername("suninformation");
        _user.setPwd(DigestUtils.md5Hex("123456"));
        // 返回所有字段
        IResultSet<User> _users = _user.find();
        
        // 或者返回指定的字段
        _users = _user.find(Fields.create(User.FIELDS.ID, User.FIELDS.AGE));
        
        // 或者分页查询
        _users = _user.find(Page.create(1).pageSize(10));
	
+ 分页查询：

		User _user = new User();
		_user.setSex("F");
		
		// 分页查询，返回全部字段
		IResultSet<User> _users = _user.find(Page.create(1).pageSize(10));
	
		// 或者分页查询，返回指定的字段
        _users = _user.find(Fields.create(User.FIELDS.ID, User.FIELDS.AGE), Page.create(1).pageSize(10));

+ 仅返回符合条件的第一条记录(FindFirst)：

		User _user = new User();
        _user.setUsername("suninformation");
        _user.setPwd(DigestUtils.md5Hex("123456"));
        
        // 返回与用户名称和密码匹配的第一条记录
        _user = _user.findFirst();
        
        // 或者返回与用户名称和密码匹配的第一条记录的ID和AGE字段
        _user = _user.findFirst(Fields.create(User.FIELDS.ID, User.FIELDS.AGE));
	
##### 删除（Delete）

	User _user = new User();
	_user.setId("bc19f5645aa9438089c5e9954e5f1ac5");
	
	// 根据实体主键删除记录
	_user.delete();

**注**：以上介绍的两种数据库操作方式各有特点，请根据实际情况选择更适合的方式，亦可混合使用；


#### 结果集（ResultSet）

JDBC模块将数据查询的结果集合统一使用IResultSet接口进行封装并集成分页参数，下面通过一段代码介绍如何使用IResultSet对象：

	IResultSet<User> _results = JDBC.get().openSession(new ISessionExecutor<IResultSet<User>>() {
        public IResultSet<User> execute(ISession session) throws Exception {
            return session.find(EntitySQL.create(User.class), Page.create(1).pageSize(10));
        }
    });
    
    // 返回当前是否分页查询
    boolean _isPaginated = _results.isPaginated();
    
    // 当前结果集是否可用，即是否为空或元素数量为0
    boolean _isAvailable = _results.isResultsAvailable();
    
    // 返回当前页号
    int _pNumber = _results.getPageNumber();
    
    // 返回每页记录数
    int _pSize = _results.getPageSize();
    
    // 返回总页数
    int _pCount = _results.getPageCount();
    
    // 返回总记录数
    long _rCount = _results.getRecordCount();
    
    // 返回结果集数据
    List<User> _users = _results.getResultData();

> **注意**：
> 
> - Page分页参数将影响总页数和总记录数的返回值是否为0；
> 
>  > 当执行Page.create(1).pageSize(10).count(false)时，将不进行总记录数的count计算；
> 
> - 非分页查询时返回的分页参数值均为0；


#### 查询（Query）

本节主要介绍YMP框架v2版本中新增的特性，辅助开发人员像写Java代码一样编写SQL语句，在一定程度上替代传统字符串拼接的模式，再配合数据实体的字段常量一起使用，这样做的好处就是降低字符串拼接过程中出错的机率，一些特定问题编译期间就能发现，因为Java代码就是SQL语句！

##### 基础参数对象
	
- Fields：字段名称集合对象，用于辅助拼接数据表字段名称，支持前缀、别名等；

	示例代码：

		// 创建Fields对象
        Fields _fields = Fields.create("username", "pwd", "age");
        // 带前缀和别名
        _fields.add("u", "sex", "s");
        // 带前缀
        _fields = Fields.create().add("u", "id").add(_fields);
        // 标记集合中的字段为排除的
        _fields.excluded(true);
        // 判断是否存在排除标记
        _fields.isExcluded();
        // 输出
        System.out.println(_fields.fields());

	执行结果：
	
		[u.id, username, pwd, age, u.sex s]


- Params：参数集合对象，主要用于存储替换SQL语句中?号占位符；

	示例代码：
	
		// 创建Params对象，任何类型参数
		Params _params = Params.create("p1", 2, false, 0.1).add("param");
        // 
        _params = Params.create().add("paramN").add(_params);
        // 输出
        System.out.println(_params.params());
    
    执行结果：
    
    	[paramN, p1, 2, false, 0.1, param]
	
- Pages：分页参数对象；

	示例代码：

		// 查询每1页, 默认每页20条记录
        Page.create(1);
        // 查询第1页, 每页10条记录
        Page.create(1).pageSize(10);
        // 查询第1页, 每页10条记录, 不统计总记录数
        Page.create(1).pageSize(10).count(false);
	
- Cond：条件参数对象，用于生成SQL条件和存储条件参数；

	示例代码：
	
	> 生成如下SQL条件：
	>
	> - (username like ? and age >= ?) or (sex = ? and age < ?)

        Cond _cond = Cond.create()
            .bracketBegin().like("username").param("%ymp%").and().gtEq("age").param(20).bracketEnd()
            .or()
            .bracketBegin().eq("sex").param("F").and().lt("age").param(18).bracketEnd();
	
        System.out.println("SQL: " + _cond.toString());
        System.out.println("参数: " + _cond.params().params());

	执行结果：
	
		SQL: ( username LIKE ? AND age >= ? )  OR  ( sex = ? AND age < ? ) 
		参数: [%ymp%, 20, F, 18]

- OrderBy：排序对象，用于生成SQL条件中的Order By语句；

	示例代码：
	
		OrderBy _orderBy = OrderBy.create().asc("age").desc("u", "birthday");
		//
		System.out.println(_orderBy.toSQL());

	执行结果：
	
		ORDER BY age, u.birthday DESC

- GroupBy：分组对象，用于生成SQL条件中的Group By语句；

	示例代码：
	
		GroupBy _groupBy = GroupBy.create(Fields.create().add("u", "sex").add("dept"))
            .having(Cond.create().lt("age").param(18));

        System.out.println("SQL: " + _groupBy.toString());
        System.out.println("参数: " + _groupBy.having().params().params());

	执行结果：
	
		SQL: GROUP BY u.sex, dept HAVING age < ?
		参数: [18]

- Where：Where语句对象，用于生成SQL语句中的Where子句；

	示例代码：
	
		Cond _cond = Cond.create()
                .like("username").param("%ymp%")
                .and().gtEq("age").param(20);

        OrderBy _orderBy = OrderBy.create().asc("age").desc("u", "birthday");

        GroupBy _groupBy = GroupBy.create(Fields.create().add("u", "sex").add("dept"));

        Where _where = Where.create(_cond).groupBy(_groupBy).orderDesc("username");

        _where.orderBy().orderBy(_orderBy);
		//
        System.out.println("SQL: " + _where.toString());
        System.out.println("参数: " + _where.getParams().params());

	执行结果：（为方便阅读，此处美化了SQL的输出格式:P）
	
		SQL: WHERE
				 username LIKE ?
			 AND age >= ?
			 GROUP BY
				 u.sex,
				 dept
			 ORDER BY
				 username DESC,
				 age,
				 u.birthday DESC
		参数: [%ymp%, 20]

- Join：连接语句对象，用于生成SQL语句中的Join子句，支持left、right和inner连接；

	示例代码：
	
		Join _join = Join.inner("user_ext").alias("ue")
            .on(Cond.create().opt("ue", "uid", Cond.OPT.EQ, "u", "id"));

        System.out.println(_join);

	执行结果：
	
		INNER JOIN user_ext ue ON ue.uid = u.id

- Union：联合语句对象，用于将多个Select查询结果合并；

	示例代码：
	
		Select _select = Select.create("user").where(Where.create(Cond.create().eq("dept").param("IT")))
                .union(Union.create(
                        Select.create("user").where(Where.create(Cond.create().lt("age").param(18)))));
        //
        System.out.println("SQL: " + _select.toString());
        System.out.println("参数: " + _select.getParams().params());

	执行结果：
	
		SQL: SELECT  *  FROM user WHERE dept = ?  UNION SELECT  *  FROM user WHERE age < ?   
		参数: [IT, 18]

##### Select：查询语句对象

	示例代码：
	
		Cond _cond = Cond.create()
	            .like("u", "username").param("%ymp%")
	            .and().gtEq("u", "age").param(20);
	    //
	    GroupBy _groupBy = GroupBy.create(Fields.create().add("u", "sex").add("u", "dept"));
	    //
	    Where _where = Where.create(_cond).groupBy(_groupBy).orderDesc("u", "username");
	    //
        Join _join = Join.inner("user_ext").alias("ue")
                .on(Cond.create().opt("ue", "uid", Cond.OPT.EQ, "u", "id"));
		//
        Select _select = Select.create(User.class, "u")
                .field("u", "username").field("ue", "money")
                .where(_where)
                .join(_join)
                .distinct();
	    //
	    System.out.println("SQL: " + _select.toString());
	    System.out.println("参数: " + _select.getParams().params());

	执行结果：（为方便阅读，此处美化了SQL的输出格式:P）
	
		SQL: SELECT DISTINCT
					u.username,
					ue.money
				FROM
					USER u
				INNER JOIN user_ext ue ON ue.uid = u.id
				WHERE
					u.username LIKE ?
				AND u.age >= ?
				GROUP BY
					u.sex,
					u.dept
				ORDER BY
					u.username DESC 
		参数: [%ymp%, 20]

##### Insert：插入语句对象

	示例代码：
	
		Insert _insert = Insert.create(User.class)
                .field(User.FIELDS.ID).param("123456")
                .field(User.FIELDS.AGE).param(18)
                .field(User.FIELDS.USER_NAME).param("suninformation");
        //
        System.out.println("SQL: " + _insert.toString());
        System.out.println("参数: " + _insert.params().params());

	执行结果：
	
		SQL: INSERT INTO user (id, age, username) VALUES (?, ?, ?)
		参数: [123456, 18, suninformation]

##### Update：更新语句对象

	示例代码：
	
		Update _update = Update.create(User.class)
                .field(User.FIELDS.PWD).param("xxxx")
                .field(User.FIELDS.AGE).param(20)
                .where(Where.create(
                        Cond.create().eq(User.FIELDS.ID).param("123456")));
        //
        System.out.println("SQL: " + _update.toString());
        System.out.println("参数: " + _update.getParams().params());

	执行结果：
	
		SQL: UPDATE user SET pwd = ?, age = ? WHERE id = ? 
		参数: [xxxx, 20, 123456]


##### Delete：删除语句对象

	示例代码：
	
		Delete _delete = Delete.create(User.class)
                .where(Where.create(
                        Cond.create().eq(User.FIELDS.ID).param("123456")));
        //
        System.out.println("SQL: " + _delete.toString());
        System.out.println("参数: " + _delete.getParams().params());

	执行结果：
	
		SQL: DELETE  FROM user WHERE id = ? 
		参数: [123456]

##### SQL：自定义SQL语句

同时也用于ISession会话接口参数封装；

示例代码：

	// 自定义SQL语句
	SQL _sql = SQL.create("select * from user where age > ? and username like ?").param(18).param("%ymp%");
	// 执行
    session.find(_sql, IResultSetHandler.ARRAY);

    // 或封装语句对象
    SQL.create(_select);
    SQL.create(_insert);
    SQL.create(_update);
    SQL.create(_delete);

##### BatchSQL：批量SQL语句对象

主要用于ISession会话对批量操作的参数封装；

示例代码：
	
	// 定义批操作
	BatchSQL _sqls = BatchSQL.create("INSERT INTO user (id, age, username) VALUES (?, ?, ?)")
            .addParameter(Params.create("xxxx", 18, "user0"))
            .addParameter(Params.create("xxx1", 20, "user1"))
            .addParameter(Params.create("xxxN", 20, "userN"))
            .addSQL("DELETE  FROM user WHERE age > 30")
            .addSQL("DELETE  FROM user WHERE age < 18");
    // 执行
    session.executeForUpdate(_sqls);

##### EntitySQL：实体参数封装对象

主要用于ISession会话的参数封装；

示例代码：
	
	session.find(EntitySQL.create(User.class)
                .field(Fields.create(User.FIELDS.ID, User.FIELDS.USER_NAME)
                        .excluded(true)));

#### 存储器（Repository）

为了能够更方便的维护和执行SQL语句，JDBC模块提供了存储器的支持，可以通过`@Repository`注解自定义SQL或自定义SQL语句或从配置文件中加载SQL并自动执行。

- @Repository注解：
	+ 参数说明：

        > dsName：数据源名称，默认为空则采用默认数据源；
        >
        > item：从资源文件中加载item指定的配置项，默认为空；
        >
		> value：自定义SQL配置(value的优先级高于item)；
		>
		> type：操作类型，默认为查询，可选值：Type.OPT.QUERY或Type.OPT.UPDATE


- 存储器示例代码：

    + 存储器：

            @Repository
            public class DemoRepository implements IRepository {

                /**
                 * 注入SQL配置文件(任意配置对象均可)
                 */
                @Inject
                private DefaultRepoConfig _repoCfg;

                /**
                 * 返回SQL配置文件对象, 若不需要配置文件请不要实现IRepository接口
                 */
                public IConfiguration getConfig() {
                    return _repoCfg;
                }

                /**
                 * 自定义SQL
                 */
                @Repository("select * from ymcms_attachment where hash = ${hash}")
                public IResultSet<Object[]> getSQLResults(String hash, IResultSet<Object[]> results) throws Exception {
                    return results;
                }

                /**
                 * 读取配置文件中的SQL
                 */
                @Repository(item = "demo_query")
                public List<Attachment> getAttachments(String hash, IResultSet<Object[]>... results) throws Exception {
                    final List<Attachment> _returnValues = new ArrayList<Attachment>();
                    if (results != null && results.length > 0) {
                        ResultSetHelper _help = ResultSetHelper.bind(results[0]);
                        if (_help != null)
                            _help.forEach(new ResultSetHelper.ItemHandler() {
                                @Override
                                public boolean handle(ResultSetHelper.ItemWrapper wrapper, int row) throws Exception {
                                    _returnValues.add(wrapper.toEntity(new Attachment()));
                                    return true;
                                }
                            });
                    }
                    return _returnValues;
                }
            }

    + SQL配置文件对象：

            @Configuration("cfgs/default.repo.xml")
            public class DefaultRepoConfig extends DefaultConfiguration {
            }

    + SQL配置文件`default.repo.xml`内容：

            <?xml version="1.0" encoding="UTF-8"?>
            <properties>
                <category name="default">
                    <property name="demo_query">
                        <value><![CDATA[select * from ymcms_attachment where hash = ${hash}]]></value>
                    </property>
                </category>
            </properties>

    + 在控制器中调用：在浏览器中访问`http://localhost:8080/hello`查看执行结果

            @Controller
            @RequestMapping("/hello")
            public class HelloController {

                /**
                 * 注入存储器
                 */
                @Inject
                private DemoRepository _repo;

                @RequestMapping("/")
                public IView hello() throws Exception {
                    // 调用存储器方法
                    return View.jsonView(_repo.getAttachments("44f5b005c7a94a0d42f53946f16b6bb2"));
                }
            }

- 说明：

    > - 存储器类通过声明`@Repository`注解被框架自动扫描并加载；
    > - 与其它被容器管理的`@Bean`一样支持拦截器、事务、缓存等注解；
    > - 存储器类方法的参数至少有一个参数(方法有多个参数时，采用最后一个参数)用于接收SQL执行结果；
    > - 查询类型SQL的执行结果数据类型为`IResultSet<Object[]>`，更新类型SQL的执行结果数据类型为`int`；
    > - 用于接收SQL执行结果的方法参数支持变长类型，如：`IResultSet<Object[]> results`和`IResultSet<Object[]>... results`是一样的；

#### 高级特性

##### 多表查询及自定义结果集数据处理

JDBC模块提供的ORM主要是针对单实体操作，实际业务中往往会涉及到多表关联查询以及返回多个表字段，在单实体ORM中是无法将JDBC结果集记录自动转换为实体对象的，这时就需要对结果集数据自定义处理来满足业务需求。
	
若想实现结果集数据的自定义处理，需要了解以下相关接口和类：
	
+ IResultSetHandler接口：结果集数据处理接口，用于完成将JDBC结果集原始数据的每一行记录进行转换为目标对象，JDBC模块默认提供了该接口的三种实现：

	> EntityResultSetHandler：采用实体类存储结果集数据的接口实现，此类已经集成在ISession会话接口业务逻辑中，仅用于处理单实体的数据转换；
	>
	> BeanResultSetHandler：将数据直接映射到类成员属性的结果集处理接口实现；
	>
	> MapResultSetHandler：采用Map存储结果集数据的接口实现；
	>
	> ArrayResultSetHandler：采用Object[]数组存储结果集数据的接口实现；

+ ResultSetHelper类：数据结果集辅助处理工具，用于帮助开发人员便捷的读取和遍历结果集中数据内容，仅支持由 ArrayResultSetHandler 和 MapResultSetHandler 产生的结果集数据类型；

下面通过简单的多表关联查询来介绍IResultSetHandler接口和ResultSetHelper类如何配合使用：

示例代码一：使用ArrayResultSetHandler或MapResultSetHandler处理结果集数据；
	
		IResultSet<Object[]> _results = JDBC.get().openSession(new ISessionExecutor<IResultSet<Object[]>>() {
            public IResultSet<Object[]> execute(ISession session) throws Exception {
                // 通过查询对象创建SQL语句:
                //
                // SELECT u.id id, u.username username, ue.money money 
                // 			FROM user u LEFT JOIN user_ext ue ON u.id = ue.uid
                //
                Select _uSelect = Select.create(User.class, "u")
                        .join(Join.left(UserExt.TABLE_NAME).alias("ue")
                                .on(Cond.create()
                                        .opt("u", User.FIELDS.ID, Cond.OPT.EQ, "ue", UserExt.FIELDS.UID)))
                        .field(Fields.create()
                                .add("u", User.FIELDS.ID, "id")
                                .add("u", User.FIELDS.USER_NAME, "username")
                                .add("ue", UserExt.FIELDS.MONEY, "money"));

                // 执行查询并指定采用Object[]数组存储结果集数据，若采用Map存储请使用：IResultSetHandler.MAP
                return session.find(SQL.create(_uSelect), IResultSetHandler.ARRAY);
            }
        });

        // 采用默认步长(step=1)逐行遍历
        ResultSetHelper.bind(_results).forEach(new ResultSetHelper.ItemHandler() {
            public boolean handle(ResultSetHelper.ItemWrapper wrapper, int row) throws Exception {
                System.out.println("当前记录行数: " + row);

                // 通过返回的结果集字段名取值
                String _id = wrapper.getAsString("id");
                String _uname = wrapper.getAsString("username");

                // 也可以通过索引下标取值
                Double _money = wrapper.getAsDouble(2);

                // 也可以直接将当前行数据赋值给实体对象或自定义JavaBean对象
                wrapper.toEntity(new User());

                // 当赋值给自定义的JavaBean对象时需要注意返回的字段名称与对象成员属性名称要一一对应并且要符合命名规范
                // 例如：对象成员名称为"userName"，将与名称为"user_name"的字段对应
                wrapper.toObject(new User());

                // 返回值将决定遍历是否继续执行
                return true;
            }
        });

        // 采用指定的步长进行数据遍历，此处step=2
        ResultSetHelper.bind(_results).forEach(2, new ResultSetHelper.ItemHandler() {
            public boolean handle(ResultSetHelper.ItemWrapper wrapper, int row) throws Exception {
                // 代码略......
                return true;
            }
        });
	
示例代码二：使用自定义IResultSetHandler处理结果集数据；
	
		// 自定义JavaBean对象，用于封装多表关联的结果集的记录
		public class CustomUser {

			private String id;

			private String username;

			private Double money;

			// 忽略Getter和Setter方法
		}

		// 修改示例一的代码，将结果集中的每一条记录转换成自定义的CustomUser对象
		IResultSet<CustomUser> _results = JDBC.get().openSession(new ISessionExecutor<IResultSet<CustomUser>>() {
            public IResultSet<CustomUser> execute(ISession session) throws Exception {
                Select _uSelect = Select.create(User.class, "u")
                        .join(Join.left(UserExt.TABLE_NAME).alias("ue")
                                .on(Cond.create()
                                        .opt("u", User.FIELDS.ID, Cond.OPT.EQ, "ue", UserExt.FIELDS.UID)))
                        .field(Fields.create()
                                .add("u", User.FIELDS.ID, "id")
                                .add("u", User.FIELDS.USER_NAME, "username")
                                .add("ue", UserExt.FIELDS.MONEY, "money"));

                // 通过实现IResultSetHandler接口实现结果集的自定义处理
                return session.find(SQL.create(_uSelect), new IResultSetHandler<CustomUser>() {
                    public List<CustomUser> handle(ResultSet resultSet) throws Exception {
                        List<CustomUser> _results = new ArrayList<CustomUser>();
                        while (resultSet.next()) {
                            CustomUser _cUser = new CustomUser();
                            _cUser.setId(resultSet.getString("id"));
                            _cUser.setUsername(resultSet.getString("username"));
                            _cUser.setMoney(resultSet.getDouble("money"));
                            //
                            _results.add(_cUser);
                        }
                        return _results;
                    }
                });
            }
        });

##### 存储过程调用与结果集数据处理

针对于存储过程，JDBC模块提供了`IProcedureOperator`操作器接口及其默认接口实现类`DefaultProcedureOperator`来帮助你完成，存储过程有以下几种调用方式，举例说明：

+ 有输入参数无输出参数：

        IConnectionHolder _conn = JDBC.get().getDefaultConnectionHolder();
        try {
            // 执行名称为`procedure_name`的存储过程，并向该存储过程转入两个字符串参数
            IProcedureOperator<Object[]> _opt = new DefaultProcedureOperator<Object[]>("procedure_name", _conn)
                    .addParameter("param1")
                    .addParameter("param2")
                    .execute(IResultSetHandler.ARRAY);
            // 遍历结果集集合
            for (List<Object[]> _item : _opt.getResultSets()) {
                ResultSetHelper.bind(_item).forEach(new ResultSetHelper.ItemHandler() {
                    public boolean handle(ResultSetHelper.ItemWrapper wrapper, int row) throws Exception {
                        System.out.println(wrapper.toObject(new ArchiveVObject()).toJSON());
                        return true;
                    }
                });
            }
        } finally {
            _conn.release();
        }

+ 有输入输出参数：

        IConnectionHolder _conn = JDBC.get().getDefaultConnectionHolder();
        try {
            // 通过addOutParameter方法按存储过程输出参数顺序指定JDBC参数类型
            new DefaultProcedureOperator("procedure_name", _conn)
                    .addParameter("param1")
                    .addParameter("param2")
                    .addOutParameter(Types.VARCHAR)
                    .execute(new IProcedureOperator.IOutResultProcessor() {
                        public void process(int idx, int paramType, Object result) throws Exception {
                            System.out.println(result);
                        }
                    });
        } finally {
            _conn.release();
        }

+ 另一种写法：

        JDBC.get().openSession(new ISessionExecutor<List<List<Object[]>>>() {
            public List<List<Object[]>> execute(ISession session) throws Exception {
                // 创建存储过程操作器对象
                IProcedureOperator<Object[]> _opt = new DefaultProcedureOperator<Object[]>("procedure_name", session.getConnectionHolder())
                        .addParameter("param1")
                        .addParameter("param2")
                        .addOutParameter(Types.VARCHAR)
                        .addOutParameter(Types.INTEGER)
                        .setOutResultProcessor(new IProcedureOperator.IOutResultProcessor() {
                            public void process(int idx, int paramType, Object result) throws Exception {
                                System.out.println(result);
                            }
                        }).setResultSetHandler(IResultSetHandler.ARRAY);
                // 执行
                _opt.execute();
                return _opt.getResultSets();
            }
        });

##### 数据库锁操作

数据库是一个多用户使用的共享资源，当多个用户并发地存取数据时，在数据库中就会产生多个事务同时存取同一数据的情况，若对并发操作不加以控制就可能会造成数据的错误读取和存储，破坏数据库的数据一致性，所以说，加锁是实现数据库并发控制的一个非常重要的技术；
	
> 数据库加锁的流程是：当事务在对某个数据对象进行操作前，先向系统发出请求对其加锁，加锁后的事务就对该数据对象有了一定的控制，在该事务释放锁之前，其他的事务不能对此数据对象进行更新操作；
	
因此，JDBC模块在数据库查询操作中集成了针对数据库记录锁的控制能力，称之为IDBLocker，以参数的方式使用起来同样的简单！
	
首先了解一下IDBLocker提供的锁的类型：
	
+ MySQL：

	> IDBLocker.MYSQL：行级锁，只有符合条件的数据被加锁，其它进程等待资源解锁后再进行操作；

+ Oracle：

	> IDBLocker.ORACLE：行级锁，只有符合条件的数据被加锁，其它进程等待资源解锁后再进行操作；
	>
	> IDBLocker.ORACLE_NOWAIT：行级锁，不进行资源等待，只要发现结果集中有些数据被加锁，立刻返回“ORA-00054错误”；

+ SQL Server：

	> IDBLocker.SQLSERVER_NOLOCK：不加锁，在读取或修改数据时不加任何锁；
	>
	> IDBLocker.SQLSERVER_HOLDLOCK：保持锁，将此共享锁保持至整个事务结束，而不会在途中释放；
	>
	> IDBLocker.SQLSERVER_UPDLOCK：修改锁，能够保证多个进程能同时读取数据但只有该进程能修改数据；
	>
	> IDBLocker.SQLSERVER_TABLOCK：表锁，整个表设置共享锁直至该命令结束，保证其他进程只能读取而不能修改数据；
	>
	> IDBLocker.SQLSERVER_PAGLOCK：页锁；
	>
	> IDBLocker.SQLSERVER_TABLOCKX：排它表锁，将在整个表设置排它锁，能够防止其他进程读取或修改表中的数据；
	
+ 其它数据库：

	> 可以通过IDBLocker接口自行实现；
	
下面通过示例代码展示如何使用锁：
	
示例代码一：通过EntitySQL对象传递锁参数；
	
	session.find(EntitySQL.create(User.class)
            .field(Fields.create(User.FIELDS.ID, User.FIELDS.USER_NAME).excluded(true))
            .forUpdate(IDBLocker.MYSQL));
    
示例代码二：通过Select查询对象传递锁参数；
    
	Select _select = Select.create(User.class, "u")
            .field("u", "username").field("ue", "money")
            .where(Where.create(
                    Cond.create().eq(User.FIELDS.ID).param("bc19f5645aa9438089c5e9954e5f1ac5")))
            .forUpdate(IDBLocker.MYSQL);

    session.find(SQL.create(_select), IResultSetHandler.ARRAY);
    
示例代码三：基于数据实体对象传递锁参数
    
	//
	User _user = new User();
    _user.setId("bc19f5645aa9438089c5e9954e5f1ac5");
    //
    _user.load(IDBLocker.MYSQL);

	//
    User _user = new User();
    _user.setUsername("suninformation");
    _user.setPwd(DigestUtils.md5Hex("123456"));
    //
    IResultSet<User> _users = _user.find(IDBLocker.MYSQL);
    
> **注意**：
>
> 请谨慎使用数据库锁机制，尽量避免产生锁表，以免发生死锁情况！
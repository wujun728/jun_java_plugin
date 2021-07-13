![app-hibernate-persist-Logo](http://git.oschina.net/uploads/images/2016/1121/010747_31fb5e63_620321.jpeg "logo")

> 为简化开发工作、提高生产率而生

# 简介 | Intro

Hibernate 增强工具包 - 只做增强不做改变，更加精简持久层`CRUD`操作

> 技术讨论 QQ 群 492238239 如满，加群 121472998 [（有钱的捧个钱场【点击捐赠】, 没钱的捧个人场）](http://git.oschina.net/uploads/images/2015/1222/211207_0acab44e_12260.png)

# 优点 | Advantages

- **纯正血统**：完全继承原生 `Hibernate` 的所有特性
- **最少依赖**：仅仅依赖 `Hibernate`
- **自动生成代码**：简化操作，使其专注于业务
- **自定义操作**：提供大量API，使开发更加顺畅
- **简化操作**：只需专注于业务，数据库操作请交给 `Hibernate-Plus`
- **无缝分页**：基于`Hibernate`分页，无需具体实现
- **数据库友好**：基于`Hibernate`，支持目前大多数主流数据库
- **避免Sql注入**：内置对特殊字符转义，从根本上预防Sql注入攻击
- **无配置文件**：无需编写SQL配置文件(例如：`Mybatis`的XML)，从而简化操作
- **主从分离**：简单配置即可实现主从分离

# 应用实例 | Demo

[Spring-MVC](http://git.oschina.net/baomidou/springmvc-hibernate-plus)
[Spring-Boot](http://git.oschina.net/cancerGit/springboot-hibernateplus-gradle)

# 下载地址 | Download

[点此去下载](http://search.maven.org/#artifactdetails%7Ccom.baomidou%7Chibernate-plus%7C1.0.0.Final%7Cjar)

```xml
	<dependency>
	    <groupId>com.baomidou</groupId>
	    <artifactId>hibernate-plus</artifactId>
	    <version>1.0.0.Final</version>
	</dependency>
```

# 通用方法 | API

![API说明](http://git.oschina.net/uploads/images/2016/1203/225540_e00cd750_620321.png "API说明")

#优点?

> Hibernate-Plus都可以极大的方便开发人员。可以随意的按照自己的需要选择通用方法。
>
> 让你感觉使用Hibernate感觉跟Mybatis-Plus一样，极大简化开发。

##Hibernate-Plus - Spring集成
	
 	 <!-- 配置数据源 -->
    <bean name="dataSource" class="com.alibaba.druid.pool.DruidDataSource" init-method="init" destroy-method="close">
        <property name="url" value="${master.url}"/>
        <property name="driverClassName" value="${master.driverClassName}"/>
        <property name="username" value="${master.username}"/>
        <property name="password" value="${master.password}"/>
    </bean>

    <!-- 配置hibernate session工厂 master -->
    <bean id="masterSessionFactory" class="com.baomidou.hibernateplus.HibernateSpringSessionFactoryBean">
		<!--主从数据库设置 Master主-->
        <property name="type" value="master"/>
        <property name="dataSource" ref="dataSource"/>
        <property name="hibernateProperties">
            <props>
                <!--<prop key="hibernate.hbm2ddl.auto">${hibernate.hbm2ddl.auto}</prop>-->
                <prop key="hibernate.dialect">${hibernate.dialect}</prop>
                <prop key="hibernate.show_sql">${hibernate.show_sql}</prop>
                <prop key="hibernate.format_sql">${hibernate.format_sql}</prop>
                <prop key="hibernate.use_sql_comments">${hibernate.use_sql_comments}</prop>
            </props>
        </property>

        <!-- 自动扫描注解方式配置的hibernate类文件 -->
        <property name="packagesToScan">
            <list>
                <value>com.baomidou.hibernate.model.po</value>
            </list>
        </property>

    </bean>

    <!-- 配置事务管理器 -->
    <bean name="masterTransactionManager" class="org.springframework.orm.hibernate5.HibernateTransactionManager">
        <property name="sessionFactory" ref="masterSessionFactory"></property>
    </bean>
    <!-- 拦截器方式配置事物 -->
    <tx:advice id="masterTransactionAdvice" transaction-manager="masterTransactionManager">
        <tx:attributes>
            <tx:method name="save*" propagation="REQUIRED"/>
            <tx:method name="update*" propagation="REQUIRED"/>
            <tx:method name="delete*" propagation="REQUIRED"/>
            <tx:method name="get*" propagation="REQUIRED" read-only="true"/>
            <tx:method name="select*" propagation="REQUIRED" read-only="true"/>
            <tx:method name="query*" propagation="REQUIRED" read-only="true"/>
            <tx:method name="*" propagation="REQUIRED"/>
        </tx:attributes>
    </tx:advice>
    <aop:config>
        <aop:pointcut id="masterTransactionPointcut"
                      expression="execution(* com.baomidou.hibernate.service..*Impl.*(..)) or execution(* com.baomidou.hibernateplus.service..*Impl.*(..))"/>
        <aop:advisor pointcut-ref="masterTransactionPointcut" advice-ref="masterTransactionAdvice"/>
    </aop:config>
	

	<!--如果不需要从数据库以下可以不配置-->
	
    <!-- slave数据源 -->
    <bean name="slaveDataSource" class="com.alibaba.druid.pool.DruidDataSource" init-method="init"
          destroy-method="close">
        <property name="url" value="${slave.url}"/>
        <property name="driverClassName" value="${slave.driverClassName}"/>
        <property name="username" value="${slave.username}"/>
        <property name="password" value="${slave.password}"/>
    </bean>
    <!-- 配置hibernate session工厂 slave -->
    <bean id="slaveSessionFactory" class="com.baomidou.hibernateplus.HibernateSpringSessionFactoryBean">
		<!--主从数据库设置 Slave从-->        
		<property name="type" value="slave"/>
        <property name="dataSource" ref="slaveDataSource"/>
        <property name="hibernateProperties">
            <props>
                <!--<prop key="hibernate.hbm2ddl.auto">${hibernate.hbm2ddl.auto}</prop>-->
                <prop key="hibernate.dialect">${hibernate.dialect}</prop>
                <prop key="hibernate.show_sql">${hibernate.show_sql}</prop>
                <prop key="hibernate.format_sql">${hibernate.format_sql}</prop>
                <prop key="hibernate.use_sql_comments">${hibernate.use_sql_comments}</prop>
            </props>
        </property>

        <!-- 自动扫描注解方式配置的hibernate类文件 -->
        <property name="packagesToScan">
            <list>
                <value>com.baomidou.hibernate.model.po</value>
            </list>
        </property>

    </bean>
    <!-- 配置事务管理器 -->
    <bean name="slaveTransactionManager" class="org.springframework.orm.hibernate5.HibernateTransactionManager">
        <property name="sessionFactory" ref="slaveSessionFactory"></property>
    </bean>
    <!-- 拦截器方式配置事物 -->
    <tx:advice id="slaveTransactionAdvice" transaction-manager="slaveTransactionManager">
        <tx:attributes>
            <tx:method name="*" propagation="REQUIRED" read-only="true"/>
        </tx:attributes>
    </tx:advice>
    <aop:config>
        <aop:pointcut id="slaveTransactionPointcut"
                      expression="execution(* com.baomidou.hibernate.service..*Impl.*(..)) or execution(* com.baomidou.hibernateplus.service..*Impl.*(..))"/>
        <aop:advisor pointcut-ref="slaveTransactionPointcut" advice-ref="slaveTransactionAdvice"/>
    </aop:config>

##Hibernate-Plus - SpringBoot集成

查看[Spring-Boot](http://git.oschina.net/cancerGit/springboot-hibernateplus-gradle)

###DAO层

	/*Dao接口类*/
	public interface DemoDao extends IDao<Tdemo> {

	}

	/*Dao接口实现类*/
	public class DemoDaoImpl extends DaoImpl<Tdemo> implements DemoDao {

	}

###Service层
	
	/*Service接口类*/
	public interface DemoService extends IService<Vdemo> {

	}

	/*Service接口实现类*/
	public class DemoServiceImpl extends ServiceImpl<Tdemo, Vdemo> implements DemoService {
	
	}

###实体类注解
	    @Entity
		@Table(name = "demo")
		@DynamicInsert(true)
		@DynamicUpdate(true)
		public class Tdemo extends AutoPrimaryKey {
	
			private String demo1;
			private String demo2;
			private String demo3;
		
			@Column(name = "demo1")
			public String getDemo1() {
				return demo1;
			}
		
			public void setDemo1(String demo1) {
				this.demo1 = demo1;
			}
		
			@Column(name = "demo2")
			public String getDemo2() {
				return demo2;
			}
		
			public void setDemo2(String demo2) {
				this.demo2 = demo2;
			}
		
			@Column(name = "demo3")
			public String getDemo3() {
				return demo3;
			}
		
			public void setDemo3(String demo3) {
				this.demo3 = demo3;
			}
		}

####注解支持

>完全支持 `JPA` 注解，原生 `Hibernate` 用法

####强调

>不是表中字段的属性需要添加`@Transient`注解  

####示例项目
>http://git.oschina.net/baomidou/springmvc-hibernate-plus

##Hibernate-Plus - 简单用法示例

全部针对单表操作，每个实体类都需要继承Convert来PO、TO之间的装换

示例代码：

		List<Vdemo> lists = new ArrayList<Vdemo>();
		for (int i = 0; i <= 100; i++) {
			Vdemo vdemo = new Vdemo();
			vdemo.setDemo1(i + "");
			vdemo.setDemo2(i + "");
			vdemo.setDemo3(i + "");
			lists.add(vdemo);
		}
		// 批量插入
		boolean insertBatch = demoService.insertBatch(lists);
		System.out.println(insertBatch);
		// Condition 链式查询列表
		List<Vdemo> vdemoList = demoService.selectList(SelectWrapper.instance().le("id", 10));
		System.out.println(vdemoList);
		Map map = new HashMap<>();
		map.put("id", 99L);
		// 根据Condition 查询单条记录
		Vdemo vdemo = demoService.selectOne(SelectWrapper.instance().eq("id", 10));
		System.out.println(vdemo);
		List<Map<String, Object>> mapList = demoService.selectMaps(SelectWrapper.instance().ge("id", 80));
		System.out.println(mapList);
		// 根据属性查询单条记录
		Vdemo vdemo1 = demoService.get("1");
		if (vdemo1 != null) {
			vdemo1.setDemo1("999");
			vdemo1.setDemo2("999");
			vdemo1.setDemo3("999");
			// 修改或保存
			demoService.saveOrUpdate(vdemo1);
		}

		Vdemo vdemo2 = demoService.get("1");
		if (vdemo2 != null) {
			vdemo2.setId(null);
			demoService.saveOrUpdate(vdemo2);
		}

		int selectCount2 = demoService.selectCount(SelectWrapper.instance().ge("id", 80));
		System.out.println(selectCount2);
		Page page = new Page(1, 20);
		page.setOrderByField("id");
		page.setAsc(false);
		// 查询分页
		Page selectPage = demoService.selectPage(SelectWrapper.DEFAULT, page);
		System.out.println(selectPage);
		// Condition链式查询分页返回Map
		Page selectMapPage = demoService.selectMapPage(SelectWrapper.instance().ge("id", 50), page);
		System.out.println(selectMapPage);
		// Condition链式查询分页返回VO
		Page selectPage2 = demoService.selectPage(SelectWrapper.instance().ge("id", 50), page);
		System.out.println(selectPage2);
		// Condition链式 删除单条记录
		demoService.delete(DeleteWrapper.instance().eq("id", 1));
		List<Vdemo> vdemos = demoService.selectList(SelectWrapper.instance());
		Iterator<Vdemo> iterator = vdemos.iterator();
		while (iterator.hasNext()) {
			Vdemo vdemo3 = iterator.next();
			vdemo3.setDemo1(vdemo3.getDemo1() + "Caratacus Plus 1");
			vdemo3.setDemo2(vdemo3.getDemo2() + "Caratacus Plus 2");
			vdemo3.setDemo3(vdemo3.getDemo3() + "Caratacus Plus 3");
		}
		// 批量修改
		demoService.updateBatch(vdemos);
		// Condition链式 删除所有记录
		demoService.delete(DeleteWrapper.DEFAULT);

		...

其他 具体请查看 

`示例` 项目1  [Spring-MVC](http://git.oschina.net/baomidou/springmvc-hibernate-plus)
`示例` 项目2  [Spring-Boot](http://git.oschina.net/cancerGit/springboot-hibernateplus-gradle)
	

# 其他开源项目 | Other Project

     `如果你喜欢Mybatis，可以尝试使用`
- [Mybatis-Plus](http://git.oschina.net/baomidou/mybatis-plus)

# 期望 | Futures

> 欢迎提出更好的意见，帮助完善 

# 捐赠 | Donate

> [捐赠记录,感谢你们的支持！](http://git.oschina.net/baomidou/kisso/wikis/%E6%8D%90%E8%B5%A0%E8%AE%B0%E5%BD%95)

![捐赠 mybatis-plus](http://git.oschina.net/uploads/images/2015/1222/211207_0acab44e_12260.png "支持一下mybatis-plus")

# 关注我 | About Me

![程序员日记](http://git.oschina.net/uploads/images/2016/0121/093728_1bc1658f_12260.png "程序员日记")

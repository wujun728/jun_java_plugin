#dbutil 

## 说明
基于Spring的 *AbstractRoutingDataSource* 进行简单的封装,方便进行数据源的切换，目前主要用于主从数据库的读写切换上。


## 使用 

### 添加依赖
	<dependency>
		<groupId>com.tanghd.spring</groupId>
		<artifactId>dbutil</artifactId>
		<version>0.2</version>
	</dependency>
		
### 配置xml (spring + mybatis)

	<bean id="dataSource" ...></bean>
	<bean id="slaveDataSource1" ...></bean>
	<bean id="slaveDataSource2" ...></bean>
		
	<bean id="dynamicDataSource" class="com.tanghd.spring.dbutil.datasource.DynamicDataSource">
		<property name="master" ref="dataSource"/>
		<property name="slaves">
			<list>
				<value ref="slaveDataSource1"/>
				<value ref="slaveDataSource2"/>
			</list>
		</property>
	</bean>
		
	<bean id="sqlSessionFactory" class="org.mybatis.spring.SqlSessionFactoryBean">
			<property name="dataSource" ref="dynamicDataSource" />
			...
	</bean>
		
### 代码里使用

	public void queryXXX(){
		DynamicDataSource.useSlave();
		try{
			...
		}finally{
			DynamicDataSource.reset();
		}
	}
		
### 扩展项
可以使用Spring-AOP进行扩展，减少对代码的入侵。目前支持Aspect和Spring-AOP方式。

#### Aspect
*	需要依赖spring-aspects、aspectjrt、aspectjweaver
*	spring的xml配置：

		<?xml version="1.0" encoding="UTF-8"?>
		<beans xmlns="http://www.springframework.org/schema/beans"
			xmlns:aop="http://www.springframework.org/schema/aop"
			xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
			xsi:schemaLocation="
		    http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context.xsd
		    http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd
		    http://www.springframework.org/schema/aop http://www.springframework.org/schema/aop/spring-aop.xsd">
		    <bean id="DsChangeAspect" class="com.tanghd.spring.dbutil.aop.DataSourceAspect"/>
		    <!--proxy-target-class = true 使用cglib代理，否则使用Java动态代理-->
		    <aop:aspectj-autoproxy proxy-target-class="true" />
		</beans>
		
*	代码示例：

		@DataSourceChange(slave=true)
		public void queryXXX(){
			...
		}
		
#### SpringAOP
不使用aspect，这种方式提供了支持@See DataSourceAdvisor.java，目前还没用到，示例略，只是配置上和Aspect不同，使用方式同样是通过注解来进行改变当前使用的数据源
以下是参考例子:

		<bean id="advisor" class="com.tanghd.spring.dbutil.aop.DataSourceAdvisor" />
		<aop:config proxy-target-class="true">
			<aop:advisor advice-ref="advisor"
				pointcut="@annotation(com.tanghd.spring.dbutil.aop.DataSourceChange)" />
		</aop:config>		
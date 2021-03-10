构建dal层代码:mvn mydalgen:mybatis

#MyBatisDalgen
##什么是MyBatisDalgen?
MyBatis 是支持定制化 SQL、存储过程以及高级映射的优秀的持久层框架。MyBatis 避免了几乎所有的 JDBC 代码和手动设置参数以及获取结果集。MyBatis 可以对配置和原生Map使用简单的 XML 或注解，将接口和 Java 的 POJOs(Plain Old Java Objects,普通的 Java对象)映射成数据库中的记录。

MyBatis的优异特性同时暴露出一个问题，那就是实际开发中需要大量的配置以及相同风格POJO或者DAO接口。为了提升开发效率，这些代码以及配置完全可以通过一个工具来自动生成。开发人员只需关注实际业务所对应的SQL语句，通过一个工具自动生成MyBatis的Mapper配置、POJO类、DAO接口，就此MyBatisDalgen诞生了。

**注：以下MyBatisDalgen将简称为dalgen**

##dalgen目录结构
    -project-dir  
      +-src/main/resource/META-INF  
      |   +-tables  
	  |   | +-*.xml  
	  |   +-tempates  
	  |   | +-mergedir  
	  |   | +-*.vm
	  |   +-tables-config.xml
      +-pom.xml 

##基本配置文件

###pom.xml
	<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
		xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
		....
		<!-- maven-mybatisdalgen-plugin  配置 -->
		<properties>
			<middlegen.destination>${basedir}/src/main</middlegen.destination>
			<middlegen.source>${middlegen.destination}/resources/META-INF</middlegen.source>
			<middlegen.config.file>${middlegen.source}/tables-config.xml</middlegen.config.file>
			<middlegen.package>org.smartboot.dal.middlegen</middlegen.package>
			<middlegen.package.dir>net/vinote/smartboot/dal/middlegen</middlegen.package.dir>
			<middlegen.templates>file://${middlegen.source}/templates</middlegen.templates>
		</properties>
		<build>
			<plugins>
				<plugin>
					<groupId>org.smartboot.maven.plugin.mydalgen</groupId>
					<artifactId>maven-mydalgen-plugin</artifactId>
					<version>0.0.1</version>
					<configuration>
						<config>
							<configFile>${middlegen.config.file}</configFile>
							<tables>*</tables>
							<mergedir>${middlegen.source}/templates/mergedir</mergedir>
							<middlegenPackage>${middlegen.package}</middlegenPackage>
							<jdbcDriver>com.mysql.jdbc.Driver</jdbcDriver>
							<jdbcUrl>jdbc:mysql://localhost:3306/demo</jdbcUrl>
							<jdbcUser>root</jdbcUser>
							<jdbcPassword>root</jdbcPassword>
						</config>
	
						<fileProducers>
							<fileProducer>
								<!-- 为每一个表生成一个DTO -->
								<destination>${middlegen.destination}/java/${middlegen.package.dir}</destination>
								<template>${middlegen.templates}/do.vm</template>
								<filename>dataobject/{0}DO.java</filename>
							</fileProducer>
	
							<fileProducer>
								<!-- 为每一个表生成一个DAO -->
								<destination>${middlegen.destination}/java/${middlegen.package.dir}</destination>
								<template>${middlegen.templates}/dao.vm</template>
								<filename>dao/{0}DAO.java</filename>
							</fileProducer>
	
							<fileProducer>
								<!-- 生成dal层组件bean -->
								<destination>${middlegen.destination}/java/${middlegen.package.dir}</destination>
								<template>${middlegen.templates}/middlegen-dal-bean.vm</template>
								<filename>MiddlegenDalBean.java</filename>
							</fileProducer>
	
							<fileProducer>
								<!-- 生成sqlMap-mapping -->
								<destination>${middlegen.destination}/resources</destination>
								<template>${middlegen.templates}/table-sqlmap-mapping.vm</template>
								<filename>mybatis/mapping/{0}-sqlmap-mapping.xml</filename>
							</fileProducer>
						</fileProducers>
					</configuration>
				</plugin>
			</plugins>
		</build>
	</project>

###tables-config.xml
dalgen读取该文件用于加载生成Mapper配置、POJO、DAO代码所依赖的源文件。源文件的路径配置与include标签的table属性值上。
例如：

    <?xml version="1.0" encoding="UTF-8"?>
    <tables>
        <typemap from="java.sql.Date" to="java.util.Date" />
        <typemap from="java.sql.Timestamp" to="java.util.Date" />
        <typemap from="java.math.BigDecimal" to="java.lang.Long" />
        <typemap from="java.lang.Byte" to="java.lang.Integer" />
        <typemap from="java.lang.Short" to="java.lang.Integer" />
    
        <include table="tables/table1.xml" />
        <include table="tables/table2.xml" />
    </tables>

###源文件
dalgen的核心在于配置源文件，dalgen也是以源文件为模板生成MyBatis的Mapper文件，POJO类以及DAO接口。源文件通常放置于tables目录下。关于源文件的详细配置，在下一章节讲解。

##dalgen源文件
###table
table标签为dalgen源文件的根目录标签
####table属性
1. sqlname
必填，源文件对应的数据库表名称

####table子标签
1. resultMap
2. operation

#####示例
    <?xml version="1.0" encoding="UTF-8"?>
    <table sqlname="user_info">
		<resultMap **>
			***
		</resultMap>
		<opertation **>
        	***
		</operation>
    </table>

###resultMap

####resultMap属性
1. **name**：resultMap的唯一标识
2. **type**：resultMap对应的Java实体类

####resultMap子标签
1. column  
resultMap对象包含的字段集合

###column
resultMap的子标签，用于定义返回结果集中的字段
####column属性
1. **name**：查询语句中的表字段名称
2. **javatype**：字段名对应的Java数据类型

####column子标签
无
####示例
	<resultMap name="USER-INFO"
		type="org.smartboot.smartweb.dal.dataobject.UserInfoDO">
		<column name="id" javatype="int" />
		<column name="password" javatype="java.lang.String" />
	</resultMap>

###operation
operation为table的子标签，MyBatis的一条SQL对应于一个opertation标签。dalgen会根据每一个operation配置生成对应的Mapper配置以及DAO接口。
####operation属性	
1. **name**：在命名空间中唯一的标识符，可以被用来引用这条语句
2. **multiplicity**：返回结果个数,select:one/many 其余皆为one
3. **paramType**：参数类型,object/primitive,若包含extraparams标签,则该属性被忽视
4. **resultType**：从这条语句中返回的期望类型的类的完全限定名或别名。注意如果是集合情形，那应该是集合可以包含的类型，而不能是集合本身。使用 resultType 或 resultMap，但不能同时使用。
5. **resultMap**：外部 resultMap 的命名引用。结果集的映射是 MybatisDalgen 最强大的特性，对其有一个很好的理解的话，许多复杂映射的情形都能迎刃而解。使用 resultMap 或 resultType，但不能同时使用。

####operation子标签
自定义DAO接口入参,参数数量、类型、顺序由其子标签param定义。
1. extraparams
2. sql
3. sqlmap

###extraparams
####extraparams属性
无
####extraparams子标签
1. param

####param属性
1. **name**：自定义DAO接口参数名称
2. **javatype**:自定义DAO接口参数类型

####示例一
	<operation name="selectById" multiplicity="one">
		<sql>
			select * from user_info where id =?
		</sql>
		<sqlmap>
			select * from user_info where id =#{id}
		</sqlmap>
	</operation>
####示例二
	<operation name="selectCount" multiplicity="one" resultType="int" >
		<extraparams>
			<param name="id" javatype="int" />
			<param name="password" javatype="java.lang.String" />
		</extraparams>
		<sql>
			select count(*) from user_info where password=? and id =?
		</sql>
		<sqlmap>
			select count(1) from user_info where password =#{password} and id=#{id}
		</sqlmap>
	</operation>
	
##关于作者
Edit By [Seer](http://zhengjunweimail.blog.163.com/)  
E-mail:zhengjunweimail@163.com  
QQ:504166636

Update Date: 2016-03-15	
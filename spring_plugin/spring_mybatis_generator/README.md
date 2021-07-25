
#### 重点：在使用之前，请认真阅读，请不要在自己写的地方添加 WARNING - @mbg.generated 这个注释，也不要删除自动生成的 WARNING - @mbg.generated 注释，因为合并是根据这个注释进行做操作的，如果不注意，重复生成时会覆盖自行添加的代码，谨记！！！


#### 使用条件

　　mybatis代码生成工具，基于mybatis-generator-core 1.3.5版本进行扩展，在使用该代码生成器前假设你已经有使用mybatis-generator-core生成工具的经验，已经熟知各项配置，如不熟悉请自行在网络中搜索官方版本的使用方法和配置，扩展都是以插件的形式提供，未对官方源码进行修改，如不需要扩展后的版本，只需要在xml中去掉相关的扩展插件即可



#### 启动入口

```
org.mybatis.generator.MyBatisGeneratorTest
```
注意：该类放在测试（test）目录下


#### 自定义插件列表

##### 1.重命名Example名称插件

```
<plugin type="org.mybatis.generator.plugins.RenameExampleClassPlugin">
        <property name="searchString" value="Example"></property>
        <property name="replaceString" value="Criteria"></property>
</plugin>
```
参数说明：  
　searchString： 替换前的名称  
　replaceString：替换后的名称  
　
##### 2.重命名Mapper.java和mapper.xml文件中的Example名称插件

```
<plugin type="org.mybatis.generator.plugins.MyRenameExampleMethodPlugin">
        <property name="searchString" value="Example"></property>
        <property name="replaceString" value="Criteria"></property>
</plugin>
```
参数说明：  
　searchString： 替换前的名称  
　replaceString：替换后的名称  

##### 3.分页插件

```
<plugin type="org.mybatis.generator.plugins.MyPaginationPlugin">
        <property name="limitStartName" value="start"></property>
        <property name="limitSizeName" value="end"></property>
</plugin>
```

参数说明：  
　limitStartName：分页参数开始名称  
　limitSizeName：分页参数结束名称  
　
##### 4.去掉表前缀、修改Mapper文件的后缀插件  


```
<plugin type="org.mybatis.generator.plugins.MyReplaceTablePrefix">
        <property name="tablePrefix" value="TC"></property>
        <property name="mapperSuffix" value="Dao"></property>
</plugin>
```
参数说明：  
　tablePrefix：表的前缀，不包括分隔符；如：tc_user,只需要填写tc就可以，区分大小写  
　mapperSuffix：Mapper文件的后缀名称；如UserMapp >>>> UserDao  
　注意：如果在table标签中设置了domainObjectName、mapperName后以table中设置的为准  
　
##### 5.BaseDao插件


```
<plugin type="org.mybatis.generator.plugins.MyBaseDaoPlugin" >
        <property name="name" value="BaseDao"></property>
        <property name="targetPackage" value="com.xxx.xxx"></property>
        <property name="targetProject" value="project\src\main\java"></property>
</plugin>
```
参数说明：  
　name：BaseDao的名称，可以自己定义；默认建议为BaseDao  
　targetProject：BaseDao所在的工程  
　targetPackage：BaseDao所在的包  
　
##### 6.通用查询ExampleClass包名更改插件


```
<plugin type="org.mybatis.generator.plugins.MyPackageExampleClassPlugin">
        <property name="targetPackage" value="com.xxx.xxx"></property>
        <property name="isProject" value="true"></property>
</plugin>
```
参数说明：  
　targetPackage：修改后ExampleClass所在的包名称  
　isProject：true:表示和数据访问层接口在同一工程；false:表示和Model在同一工程  
  
  
##### 7.单个对象通用查询方法插件


```
<plugin type="org.mybatis.generator.plugins.MySelectSingleByExamplePlugin"/>
```
参数说明：  
　示例方法：
　
```
Xxxxxx selectSingleByCriteria(XxxxxCriteria criteria);
```

##### 8.批量修改方法插件


```
<plugin type="org.mybatis.generator.plugins.MyUpdateBatchPlugin"/>
```
参数说明：  
　示例方法：
　
```
int updateBatchByPrimaryKeySelective(List<Xxxx> records);
```

##### 9.BaseService插件


```
<plugin type="org.mybatis.generator.plugins.MyBaseServicePlugin">
        <property name="name" value="BaseService"></property>
			
	    <property name="targetBaseServiceProject" value="Project\src\test\java"></property>
        <property name="targetPackageBaseService" value="ibatisData.base"></property>
	    <property name="targetBaseServiceImplProject" value="Project\src\test\java"></property>
        <property name="targetPackageBaseServiceImpl" value="ibatisData.base.impl"></property>
            
         
	    <property name="targetBusinessServiceProject" value="Project\src\test\java"></property>
        <property name="targetPackageBusinessService" value="ibatisData.bes"></property>
	    <property name="targetBusinessServiceImplProject" value="Project\src\test\java"></property>
        <property name="targetPackageBusinessServiceImpl" value="ibatisData.bes.impl"></property>

        <property name="searchString" value="Example"></property>
        <property name="replaceString" value="Criteria"></property>

        <property name="baseDaoPackage" value="ibatisData.ff.BaseMapper"></property>
</plugin>
```
参数说明：  
　name：BaseService的名称，可以自定义  

---

　targetBaseServiceProject：BaseService接口所在的工程  
　targetPackageBaseService:BaseService接口所在的包  
　targetBaseServiceImplProject：BaseServiceImpl接口实现所在的工程  
　targetPackageBaseServiceImpl：aseServiceImpl接口实现所在的包    
　

---  


　targetBusinessServiceProject：具体业务接口所在的工程  
　targetPackageBusinessService：具体业务接口所在的包  
　targetBusinessServiceImplProject：具体业务接口实现所在的工程  
　targetPackageBusinessServiceImpl：具体业务接口实现所在的包    
　

---
　searchString：Example替换前的名称，全局替换的地方必须保持一致  
　replaceString：Example替换后的名称，全局替换的地方必须保持一致  
　baseDaoPackage：BaseDao所在的包，必须配置

##### 10.数据类型转换插件


```
<javaTypeResolver type="org.mybatis.generator.plugins.MyJavaTypeResolverDefaultImpl">
        <property name="forceBigDecimals" value="true" />
</javaTypeResolver>
```

参数说明：  
　forceBigDecimals：true:当数据类型为decimal时，始终生成BigDecimal；false：当数据库类型为decimal时，但是没有小数点的时候，则生成Long类型；建议设置为true  
　注意：MyJavaTypeResolverDefaultImpl中当数据库类型为BIT和TINYINT时，都映射为为java中的Integer类型
　


注意：源码中默认提供了配置模板xml文件，可根据实际需求进行修改（generatorConfigTemplate.xml）；配置文件放在test下的resources目录
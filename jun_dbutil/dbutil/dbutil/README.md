# dbutil
在java 中我们有多种方式操作数据库，但是如果只是为了简单处理使用一些框架，在使用中还是显得麻烦。这里就整理一个简单快速操作数据库的一种方案

并且也上maven 库了：[https://mvnrepository.com/artifact/cn.jiangzeyin/dbutil](https://mvnrepository.com/artifact/cn.jiangzeyin/dbutil)

博客专栏：[http://blog.csdn.net/column/details/17021.html](http://blog.csdn.net/column/details/17021.html)

项目特点：

1.支持多数据源

2.多数据源支持读写分离 和 随机落取

3.接口形式记录日志

4.接口形式获取当前操作用户

5.增 删 改 可以使用异步执行

6.对外提供 增 删 改 执行过程中的接口调用

7.自动记录当前最后修改数据人 创建数据人和时间

8.自动记录数据最后修改数据时间（和第5点不冲突）

欢迎━(*｀∀´*)ノ亻!大家测评

cn.jiangzeyin.database.config.DataSourceConfig  工具使用配置类

cn.jiangzeyin.system.SystemDbLog  工具日志为了各个项目适配，使用了接口形式来提供记录日志

cn.jiangzeyin.database.run.read.IsExists 判断是否存在

cn.jiangzeyin.database.run.read.Select 多种方式查询

cn.jiangzeyin.database.run.read.SelectFunction 查询函数

cn.jiangzeyin.database.run.read.SelectPage 分页查询

cn.jiangzeyin.database.run.write.Insert 添加数据

cn.jiangzeyin.database.run.write.Remove 删除数据

cn.jiangzeyin.database.run.write.Update 修改数据

示例配置：

```
sourceTag=core,user,roles,tongji,news
configPath=file:/ztoutiao/read.properties
systemKey=com.yoke
systemKeyColumn=url,username,password
#
lastModify.time=UNIX_TIMESTAMP(NOW())
lastModify.class=com.yoke.entity.EditOptBaseEntity,com.yoke.entity.AdminOptBaseEntity
lastModify.column.user=lastModifyUser
lastModify.column.time=lastModifyTime
#
create.class=com.yoke.entity.AdminOptBaseEntity
create.column.user=createUser
#
systemColumn.pwd=pwd
systemColumn.active=isDelete
systemColumn.active.value=0
systemColumn.inActive.value=1
#
systemColumn.modify.status=true
systemColumn.modify.column=modifyTime
systemColumn.modify.time=UNIX_TIMESTAMP(NOW())
#
systemColumn.notPutUpdate=modifyTime,isDelete,createTime,createUser,lastModifyUser,lastModifyTime,id
systemColumn.columnDefaultValue=modifyTime:UNIX_TIMESTAMP(NOW()),createTime:UNIX_TIMESTAMP(NOW())
systemColumn.writeDefaultRemove=createUser,lastModifyUser,lastModifyTime,id,isDelete
systemColumn.readDefaultRemove=pwd
systemColumn.selectDefaultColumns=*
#
systemColumn.defaultRefKeyName=id
systemColumn.defaultKeyName=id
```


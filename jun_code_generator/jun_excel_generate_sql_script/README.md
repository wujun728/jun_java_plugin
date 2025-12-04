# GenerateSqlScript

#### 介绍
根据SQL文档 （Excel）表格 自动生成SQL创建脚本 一键执行创建数据库。

#### 特点：
1. 可根据Excel表格生成SQL文件导出到本地
2. 自动创建数据表
3. 根据数据表自动创建Mybatis Plus项目代码 包含（实体类，mapper文件与XML，service层文件，controller文件）


模板Excel.xlsx
![输入图片说明](https://images.gitee.com/uploads/images/2020/0312/011312_2b1eb642_1635778.png "屏幕截图.png")


### 编译Jar后的配置文件内容：


config.properties
```
#配置Excel导入和导出的配置
excelPath=/Users/chenzedeng/Downloads/基于内容推荐的阅读APP.xlsx
#导出的文件路径
exportSqlDir=/Users/chenzedeng/Downloads/
#是否导出脚本到文件
outputScriptFile=false

#是否自动构建Java项目
buildJava=true
#	Java构建配置
#Java的构建包名
buildConf.packageName=cn.yustart.book.app
#注释作者
buildConf.author=Yuchen
#要忽略生成的表名 逗号分隔
buildConf.excludeTable=

# 配置数据源 
# 是否开启自动创建数据库
autoRunScript=true
#数据源配置
dataSource.host=127.0.0.1
dataSource.port=3306
dataSource.database=book_recommed_app
dataSource.user=root
dataSource.password=chenzedeng
```


### 下载直接用
https://gitee.com/Jack-chendeng/GenerateSqlScript/releases

使用说明：
解压后 配置config.properties后
直接java -jar xxxx.jar 即可



# code-generator

#### 介绍
代码生成工具
抽取自[若依管理系统](https://gitee.com/y_project/RuoYi)代码生成模块，可在本地运行，无需依赖服务器

#### 项目说明
1. 代码参考若依管理系统
2. 数据库采用sqlite，位置在src\main\resources\static\sqlite\code-generator.db，发布后请将db文件放在jar包的同级sqlite目录中
3. 目前目标数据库只支持MySQL、Oracle、PostgreSQL、SQLServer其他的可自行扩展
4. 数据源可多条配置，导入表结构时进行选择数据源
5. 工具目录中tools文件是jar文件，只是改了个名字，双击.bat启动

#### 注意事项
1. 代码生成后的sql文件在PostgreSQL下没有生成，需要自己在菜单中添加
2. 目前导入表功能在SQLServer还没有测试

#### 工具下载
https://gitee.com/lpf_project/code-generator/releases/v4.6.0

#### 运行说明

1. 双击“启动工具.bat”进行打开
2. 工具内置tomcat，默认端口为：5064
3. 如果需要更改端口号，打开“启动工具.bat”找到“--server.port=5064”更改
4. tomcat启动后会自动打开浏览器，优先级为：谷歌→火狐→-系统默认浏览器；如果无法自动打开，可以手动打开浏览器输入：http://127.0.0.1:5064

#### 效果图
![输入图片说明](https://images.gitee.com/uploads/images/2020/1015/182721_30e89f74_389553.png "数据源.png")
![输入图片说明](https://images.gitee.com/uploads/images/2020/1015/182737_95d1dff4_389553.png "系统配置.png")
![输入图片说明](https://images.gitee.com/uploads/images/2020/1015/182759_29fb2ce6_389553.png "选择数据源.png")
![输入图片说明](https://images.gitee.com/uploads/images/2020/1015/182816_261070dc_389553.png "选择数据源后导入表.png")
![输入图片说明](https://images.gitee.com/uploads/images/2020/1015/182826_b7859006_389553.png "导入成功.png")
![输入图片说明](https://images.gitee.com/uploads/images/2020/1015/182836_c079c269_389553.png "预览.png")
![输入图片说明](https://images.gitee.com/uploads/images/2020/1015/182844_ada9208c_389553.png "字典管理.png")
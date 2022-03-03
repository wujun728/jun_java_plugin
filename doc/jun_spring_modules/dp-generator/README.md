# dp-GEN
基于velocity框架的代码生成工具，根据数据库表结构生成sql、dao、manager、service、controller、html、js基础代码，能快速生成基本代码或模块脚手架。生成代码能直接用于dp-LTE、dp-PRO、dp-PLUS开源项目。
### 传送门
- dp-LTE：[https://gitee.com/dp_group/dp-security/](https://gitee.com/dp_group/dp-security/)
- dp-PRO：[https://gitee.com/dp_group/dp-pro](https://gitee.com/dp_group/dp-pro)
- dp-GEN：[https://gitee.com/dp_group/dp-generator](https://gitee.com/dp_group/dp-generator)
- dp-BOOT：[https://gitee.com/dp_group/dp-boot](https://gitee.com/dp_group/dp-BOOT)
- 项目文档：[http://dp-dev.mydoc.io/](http://dp-dev.mydoc.io/)
- 最新进展详情，请关注 dp-PRO 项目
### 项目介绍
- 一个能快速生成代码的开发脚手架
- 命名规范和工程分层规约参考阿里巴巴JAVA开发规范
- 自动生成sql、dao、manager、service、controller、html、js，更快的开发方式
- 生成代码可直接用于dp-LTE和dp-PRO开源项目
- 支持多模块配置，可快速生成个性化业务模块基础代码和结构
### 项目拓展
- [基于系统参数管理实现动态select控件](https://my.oschina.net/zhouchenglin/blog/1615653)
- [基于ajaxfileupload.js实现文件上传](https://my.oschina.net/zhouchenglin/blog/1615214)
### 项目结构
- dp-gen：父级（聚合）模块
- dp-common：公共通用模块
- dp-orm：数据持久模块
- dp-generator：代码生成模块
- dp-web：前端界面
### 交流反馈
- 项目文档：[http://dp-dev.mydoc.io/](http://dp-dev.mydoc.io/)
- 作者主页：[http://www.chenlintech.com/](http://www.chenlintech.com/)
- 交流QQ群：553461392【已满】，钉钉群号：23119937
- 如果对项目感兴趣，请Watch、Star项目，后期会不定时发布更新
### 命名规范（参考阿里巴巴Java开发手册）
-  获取单个对象的方法用 get 做前缀
-  获取多个对象的方法用 list 做前缀
-  获取统计值的方法用 count 做前缀
-  插入的方法用 save(推荐) 或 insert 做前缀
-  删除的方法用 remove(推荐) 或 delete 做前缀
-  修改的方法用 update 做前缀
### 应用分层（参考阿里巴巴Java开发手册）
![image](https://images.gitee.com/uploads/images/2020/0104/002419_4bec85b6_562480.png)
### 项目演示
- 演示地址：请下载源代码本地启服
- 账号密码：admin / 1
### 运行效果
![image](https://images.gitee.com/uploads/images/2020/0104/002411_1394d26e_562480.png)
![image](https://images.gitee.com/uploads/images/2020/0104/002414_4a9c410a_562480.png)
### 编辑器记得安装lombok插件

### FEBS-Shiro 2.0
![https://img.shields.io/badge/license-Apache%202.0-blue.svg?longCache=true&style=flat-square](https://img.shields.io/badge/license-Apache%202.0-blue.svg?longCache=true&style=flat-square)
![https://img.shields.io/badge/springboot-2.4.2-yellow.svg?style=flat-square](https://img.shields.io/badge/springboot-2.4.2-yellow.svg?style=flat-square)
![https://img.shields.io/badge/shiro-1.6.0-orange.svg?longCache=true&style=flat-square](https://img.shields.io/badge/shiro-1.6.0-orange.svg?longCache=true&style=flat-square)
![https://img.shields.io/badge/layui-2.5.7-brightgreen.svg?longCache=true&style=flat-square](https://img.shields.io/badge/layui-2.5.7-brightgreen.svg?longCache=true&style=flat-square)

FEBS-Shiro是一款简单高效的后台权限管理系统，使用Spring Boot，Shiro和Layui构建。FEBS意指：**F**ast，**E**asy use，**B**eautiful和**S**afe。相信无论作为企业级应用，私活开发脚手架或者权限系统构建学习，FEBS-Shiro都会是一个不错的选择。

### 演示地址

[http://47.104.70.138:8080](http://47.104.70.138:8080/login)

演示环境账号密码：

账号 | 密码| 权限
---|---|---
scott | 1234qwer | 注册账户，拥有查看，新增权限（新增用户除外）和导出Excel权限


本地部署账号密码：

账号 | 密码| 权限
---|---|---
mrbird | 1234qwer |超级管理员，拥有所有增删改查权限
scott | 1234qwer | 注册账户，拥有查看，新增权限（新增用户除外）和导出Excel权限
micaela | 1234qwer |系统监测员，负责整个系统监控模块
Jana   | 1234qwer  |跑批人员，负责任务调度跑批模块

### 更多版本
当前分支为2.0版本，页面采用Layui全新构建，FEBS的其他版本：

名称 | 描述| 地址
---|---|---
FEBS-Cloud | 基于Spring Cloud的微服务权限系统 | [https://github.com/wuyouzhuguli/FEBS-Cloud](https://github.com/wuyouzhuguli/FEBS-Cloud)

### 系统模块
系统功能模块组成如下所示：
```
├─系统管理
│  ├─用户管理
│  ├─角色管理
│  ├─菜单管理
│  └─部门管理
├─系统监控
│  ├─在线用户
│  ├─系统日志
│  ├─登录日志
│  ├─请求追踪
│  ├─系统信息
│  │  ├─JVM信息
│  │  ├─TOMCAT信息
│  │  └─服务器信息
├─任务调度
│  ├─定时任务
│  └─调度日志
├─代码生成
│  ├─生成配置
│  ├─代码生成
└─其他模块
   ├─FEBS组件
   │  ├─表单组件
   │  ├─表单组合
   │  ├─FEBS工具
   │  ├─系统图标
   │  └─其他组件
   ├─APEX图表
   ├─高德地图
   └─导入导出
```
### 系统特点

1. 前后端请求参数校验

2. 支持Excel导入导出

3. 前端页面布局多样化，主题多样化

4. 支持多数据源，代码生成

5. 多Tab页面，适合企业应用

6. 用户权限动态刷新

7. 浏览器兼容性好，页面支持PC，Pad和移动端。

8. 代码简单，结构清晰

### 技术选型

#### 后端
- [Spring Boot 2.4.2](http://spring.io/projects/spring-boot/)
- [Mybatis-Plus](https://mp.baomidou.com/guide/)
- [MySQL 5.7.x](https://dev.mysql.com/downloads/mysql/5.7.html#downloads)
- [Hikari](https://brettwooldridge.github.io/HikariCP/)
- [Redis](https://redis.io/)
- [Shiro 1.6.0](http://shiro.apache.org/)

#### 前端
- [Layui 2.5.7](https://www.layui.com/)
- [Nepadmin](https://gitee.com/june000/nep-admin)
- [eleTree 树组件](https://layuiextend.hsianglee.cn/eletree/)
- [xm-select](https://gitee.com/maplemei/xm-select)
- [Apexcharts图表](https://apexcharts.com/)

### 系统截图

#### PC端
![screenshot](screenshot/pc_screenshot_1.jpg)
![screenshot](screenshot/pc_screenshot_2.jpg)
![screenshot](screenshot/pc_screenshot_3.jpg)
![screenshot](screenshot/pc_screenshot_4.jpg)
![screenshot](screenshot/pc_screenshot_5.jpg)
![screenshot](screenshot/pc_screenshot_6.jpg)

#### 手机
![screenshot](screenshot/mobile_screenshot_1.jpg)
![screenshot](screenshot/mobile_screenshot_2.jpg)
#### Pad
![screenshot](screenshot/pad_screenshot_1.jpg)
![screenshot](screenshot/pad_screenshot_2.jpg)
![screenshot](screenshot/pad_screenshot_3.jpg)
### 浏览器兼容
|[<img src="https://raw.github.com/alrra/browser-logos/master/src/archive/internet-explorer_9-11/internet-explorer_9-11_48x48.png" alt="Edge" width="24px" height="24px" />](http://godban.github.io/browsers-support-badges/)</br>IE| [<img src="https://raw.githubusercontent.com/alrra/browser-logos/master/src/edge/edge_48x48.png" alt="Edge" width="24px" height="24px" />](http://godban.github.io/browsers-support-badges/)</br>Edge | [<img src="https://raw.githubusercontent.com/alrra/browser-logos/master/src/firefox/firefox_48x48.png" alt="Firefox" width="24px" height="24px" />](http://godban.github.io/browsers-support-badges/)</br>Firefox | [<img src="https://raw.githubusercontent.com/alrra/browser-logos/master/src/chrome/chrome_48x48.png" alt="Chrome" width="24px" height="24px" />](http://godban.github.io/browsers-support-badges/)</br>Chrome | [<img src="https://raw.githubusercontent.com/alrra/browser-logos/master/src/safari/safari_48x48.png" alt="Safari" width="24px" height="24px" />](http://godban.github.io/browsers-support-badges/)</br>Safari |[<img src="https://raw.github.com/alrra/browser-logos/master/src/opera/opera_48x48.png" alt="Edge" width="24px" height="24px" />](http://godban.github.io/browsers-support-badges/)</br>Opera
| --------- | --------- | --------- | --------- | --------- |--------- |
|IE 10+| Edge| last 15 versions| last 15 versions| last 10 versions| last 15 versions
### 参与贡献
欢迎提交PR一起完善项目，以下为提PR并合并的小伙伴（排名不分先后）：

<a href="https://github.com/everhopingandwaiting">
    <img src="https://avatars3.githubusercontent.com/u/6021724?s=400&v=4" width="45px"></a>
<a href="https://github.com/mgzu">
    <img src="https://avatars1.githubusercontent.com/u/29629221?s=400&v=4" width="45px"></a>
<a href="https://github.com/yuuki80code">
    <img src="https://avatars0.githubusercontent.com/u/17798853?s=400&v=4" width="45px"></a>
<a href="https://github.com/cinsin">
    <img src="https://avatars1.githubusercontent.com/u/12856067?s=400&v=4" width="45px"></a>
<a href="https://github.com/Minnull">
    <img src="https://avatars2.githubusercontent.com/u/19608781?s=400&v=4" width="45px"></a>
<a href="https://github.com/Harrison0x80">
    <img src="https://avatars2.githubusercontent.com/u/8622915?s=400&v=4" width="45px"></a>
<a href="https://github.com/notlcry">
    <img src="https://avatars2.githubusercontent.com/u/1989218?s=400&v=4" width="45px"></a>
<a href="https://github.com/gelibo">
    <img src="https://avatars1.githubusercontent.com/u/14520562?s=400&v=4" width="45px"></a>
<a href="https://github.com/FiseTch">
    <img src="https://avatars0.githubusercontent.com/u/29654322?s=400&v=4" width="45px"></a>
<a href="https://github.com/pangPython">
    <img src="https://avatars3.githubusercontent.com/u/12680677?s=400&v=4" width="45px"></a>
<a href="https://github.com/atsushinee">
    <img src="https://avatars2.githubusercontent.com/u/26084189?s=400&v=4" width="45px"></a>
<a href="https://github.com/liuzhuoming23">
    <img src="https://avatars1.githubusercontent.com/u/41719224?s=400&v=4" width="45px"></a>
<a href="https://github.com/yqmmm">
    <img src="https://avatars2.githubusercontent.com/u/38187913?s=400&v=4" width="45px"></a>
<a href="https://github.com/wtune">
    <img src="https://avatars1.githubusercontent.com/u/59522871?s=400&v=4" width="45px"></a>                    
<a href="https://github.com/robinliu2827">
    <img src="https://avatars2.githubusercontent.com/u/46709292?s=400&v=4" width="45px"></a> 

### 反馈交流

加入QQ群和大家一起~~交流~~吹水：

![qq](screenshot/QQ.jpg)

### 特别赞助

<a href="https://www.jetbrains.com/?from=FEBS Shiro">
    <img src="screenshot/jetbrains.png" width="100px"></a>


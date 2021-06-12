# X-admin

简介
X-admin基于layui的轻量级前端后台管理框架，简单免费，兼容性好，面向所有层次的前后端程序。创立于2017年初，为了敏捷WEB应用开发和简化企业应用开发而诞生的。#X-admin从诞生以来一直秉承简洁实用的设计原则，在保持出色的性能和至简的代码的同时，也注重易用性。并且拥有众多的原创功能和特性，在社区团队的积极参与下，在易用性、扩展性和性能方面不断优化和改进，已经成长为国内最领先和最具影响力的WEB应用开发前端后台框架，众多的典型案例确保可以稳定用于商业以及门户级的开发。testadmin

商业友好的开源协议
X-admin遵循Apache2开源协议发布。Apache Licence是著名的非盈利开源组织Apache采用的协议。该协议和BSD类似，鼓励代码共享和尊重原作者的著作权，同样允许代码修改，再作为开源或商业软件发布。

## (ps:喜欢记得点赞，开源免费，大家拿去用，如果过意不去就捐点款，我不介意的)

## 官网

http://x.xuebingsi.com

交流QQ群：519492808

## 2019-05-06更新

修改静态表格展现问题，设置td 最小宽度为80px,table宽度溢出自动出现滚动。
升级方式，下载复制最新的 xadmin.css 在table外盒子增加 两个类。
修改如下：class增加"layui-table-body layui-table-main"
 ```
<div class="layui-card-body layui-table-body layui-table-main">
    <table class="layui-table layui-form">
    </table>
</div
```

## 2019-04-26更新

* 完善tab 打开的记忆功能，之后关闭后，之前写入的tab 还可以读取出来
* 增加弹出层父窗口刷新功能 xadmin.father_reload()
* 增加提交后 关闭弹出 层  xadmin.close();
* 静态表格全选  案例在会员列表
* 增加右侧记忆功能 
* v2.2升级只需要覆盖 xadmin.js

## 2019-04-13更新

* 对2.1版本整个逻辑重新设计，增加不同的主题，在xadmin.css后面引入主题文件就可以，主题样式文件放在css目录，喜欢记得点赞

## 2019-03-30更新
* 修改 x_admin_show 弹出窗口实现最大化，默认窗口非最大化，如果需要弹出窗口最大化，在最后一个参数传 true

修复x_admin_add_to_tab 函数bug，在多个页面切换过程的报错。

x_admin_show(title,url,w,h,full=false)

## 2019-03-19更新
* 增加非菜单打开tab函数 x_admin_add_to_tab(title,url,is_refresh) titel为tab标题,url为打开页面地址,is_refresh 可选参数，重复点击是否刷新，默认为false不刷新,true为重复点击刷新页面
用法:
index.html 中直接 x_admin_add_to_tab('在tab打开','http://www.baidu.com',true)
iframe页面中  用 parent.x_admin_add_to_tab('在tab打开','http://www.163.com',true)
我的桌面有demo

## 2019-03-07更新
* 是否开启刷新记忆tab功能, 通过index 页面增加 var is_remember = false;不增加默认开启

## 2019-03-06更新

* 增加tab双击关闭当前tab
* 增加tab右键菜单，可实现 关闭当前/关闭其它/关闭全部

## 2019-02-28更新

* 开发升级为2.1, 引入layui 2.4.5 
* 增加 html 根标签增加 class="x-admin-sm" 可以实现整体方格字体的细化 原字体14px 变成12px 16px变为14px，对于有些需要显示更多信息的页面比较友好 去除x-admin-sm 恢复原来样式
* 增加动态表格事例
* 增加左侧点击刷新tab 可根据实际需要选择  在 sub-menu li 标签中加上属性 date-refresh="1" ,该链接点击会进行刷新
* 增加弹出层函数 x_admin_father_reload() 该函数可以实现对父窗口的刷新
* 刷新保留左侧展开 （利用cookie 保存点击过的 索引，刷新读取cookie对应的索引 进行展开左侧）
* 增加404错误页面
* tab记忆功能，刷新保留之前打开的tab （利用cookie 保存点击过的 索引，刷新读取cookie对应的索引 进行打开)


## 2018-04-30更新

* 登录页面加上动画效果
* 首页欢迎页面也加动画效果

## 2018-04-25更新

* 针对首页欢迎页面调整
* 修改tab窗口首页为能关闭
* 对角色增加页面重新设计
* 增加图标字体对应的编码
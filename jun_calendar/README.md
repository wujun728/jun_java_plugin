#MyCalendar

##应用说明

###场景
    以前家公司，一百多号人，前台每天中午和晚上负责为不想下楼的同学统一叫外卖，快到了饭点，前台就会在群里面喊一声“点餐啦！”，然后饿狼们就会叽叽喳喳一片，跟妹纸扯上十来分钟才能消停，还有些同学因为开会或者其他事情错过了统一点餐，只能去楼下吃了。还有就是公司有四五个会议室，开会需要用会议室时要提前到前台那报备，前台会把每天的每个会议室的安排与会议主题写到公司白板上，甚是麻烦。
    本着为前台妹妹减轻工作压力并方便大家的原则，于是本人就做了这么个小应用，当然公司也没有用，哈哈~，只是自己敲敲代码练练手，问题很多，勿鄙视。

###演示
- 演示地址：http://fcdemo.sinaapp.com/     
- 后台地址：http://fcdemo.sinaapp.com/admin
- 用户名：jin   密码：123
- ~~部署SinaApp上，数据库可以读取，但是不能做update或insert操作，不知道为什么~~。
- 原来SinaApp数据库分主从库，主库域名:w.rdc.sae.sina.com.cn/从库域名:r.rdc.sae.sina.com.cn,主库可写，从库只能读。

###运行说明
1. 执行WebRoot/DBInit下的数据库初始脚本；
2. 修改WebRoot/WEB-INF/jdbcConfig.properties中的数据库连接；
3. 运行com.jin.calendar.SysConfig即可启动服务；
4. 打开浏览器，直接输入[localhost](http://127.0.0.1)查看效果。

##开发环境及技术

* window7，Eclipse4.3，JDK7，MySql5.6
* JFinal，FullCalendar，Jquery,qTip2

##截图
![订餐](http://git.oschina.net/jin/MyCalendar/raw/master/Screenshot/1.png)

![会议室预定](http://git.oschina.net/jin/MyCalendar/raw/master/Screenshot/2.png)
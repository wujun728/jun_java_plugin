# Change log

## Version(1.1.0) 2017-03-21

* 更新默认超时时间单位为秒;
* 增加超时设置方法，支持自定义时间单位;
* 增加Debug调试模式;
* 更新了同步方法方法下可以省略调用`execute`方法;
* 更新了根据不同域名设置默认的请求头(Header);
* 增加了清除和获取默认请求头(Header);
* 很任性的又修改了`https`方法名，新方法名为`customSSL`;
* 解决异步调用接口出现服务器500错误时onError方法未正确响应错误；
* 升级依赖OkHttp版本为3.6.0

## Version(1.0.10-Final)2017-01-02

* 仅仅将版本号从1.0.0-Final提升为1.0.10-Final，其它没有什么变化
* 提升版本号为了适配从maven中央仓库能够自动抓取到最新的版本信息

## Version(1.0.0-Final)2016-12-30
* Change:针对版本1.0.x的最终版本，后面的版本启用1.1.x
* Change:完善文档，代码注释
* Add:增加了demo
* Add:新增文档[http://www.w3cschool.cn/easyokhttp/](http://www.w3cschool.cn/easyokhttp/)

## Version(1.0.7-beta)2016-12-24
* Update:重构整个项目
* Update:okhttp3版本更新
* Update:gson版本更新
* Fix:修正一些问题

## Version(1.0.4-beta)216-10-20
* Update：更新核心依赖包`mzlion-core`

## Version(1.0.3-beta)2016-10-02 
* Update:修正一些bug

## Version(1.0.2-beta)2016-08-25
* Update:更新了核心依赖包`mzlion-core`
* Update:更新了json字符串转JavaBean方法`asBean(TypeRef<T> typeRef)`统一使用泛型处理类

## Version(1.0.1-beta)2016-07-19
* Fix:修正将响应结果进行转换时的bug
* Update:结果转换划分，目前新的以`as`开头表示文本处理,`transferTo`则处理二进制处理
* Update:将`HttpClient.stringBody()`方法名更改为`HttpClient.textBody()`
* Add:完善文档

## Version(1.0.0-beta)2016-07-15
* Initial beta
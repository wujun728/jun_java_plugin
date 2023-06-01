# log4web websocket
## 简介
 结合log4web、websocket的埋点记录，能够对操作记录监控等功能。
## 使用
  具体在webapp下的test目录，详细看代码吧~

## 其他
* log4web使用单例:(https://git.oschina.net/lwb11/log4web)

* websocket使用单例：(https://git.oschina.net/lwb11/spring-mvc-websocket)


## demo

* 页面引入jquery.js跟web4.js
```javascript
<script src="http://code.jquery.com/jquery-1.4.1.min.js"></script>
<script src="log4web.js"></script>
<script>
    log4web.info("log message");
</script>
```
* 访问监控页面:[监控页面](http://120.25.80.99:8081/test/websocketApiLog.html)

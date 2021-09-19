# Ajax详解参考文档

Asynchronous Javascript And XML（可扩展标记语言），AJAX 不是一门的新的语言，而是对现有持术的综合利用。客户端渲染；

本质:是在HTTP协议的基础上以异步的方式通过XMLHttpRequest对象与服务器进行通信。

作用：可以在页面不刷新（局部刷新）的情况下，请求服务器，局部更新页面的数据；

异步（Asynchronous）：指某段程序执行时不会阻塞其它程序执行，其表现形式为程序的执行顺序不依赖程序本身的书写顺序，相反则为同步。

同步：同一时刻只能做一件事，上一步完成才能开始下一步
异步：同时做多件事，效率更高。

发送：
XMLHttpRequest可以以异步方式的处理程序。

1，获取用户数据；

2，让异步对象发送请求；
2.1 创建异步对象；var xhr = new XMLHttpRequest();

```
2.2 设置 请求行 open(请求方式，请求url):
	get请求如果有参数就需要在url后面拼接参数；
       	post不用写参数，就在请求体中传递；

2.3 设置 请求头 setRequestHeader('key':'value')：
          		get方式不需要设置请求头；
          		post需要设置 ("Content-Type","application/x-www-form-urlencoded")；
		如果没有设置，参数无法正确的传递到服务器(本质上说，如果没有参数，也不一定需要设置，不会影响请求的发送)；

       	2.4 设置 请求体:发送请求  send(参数：key=value&key=value)：
          		如果有参数，post应该在这个位置来传递参数；
		xhr.send("username="+name);
            	对于 get请求不需要在这个位置来传递参数；
		xhr.send(null);
1234567891011121314
```

接收：
响应报文：
报文行：响应状态码 响应状态信息 200 ok；
报文头：服务器返回给客户端的一些额外信息 ；
报文体：服务器返回给客户端的数据； responseText:普通字符串 responseXML：符合xml格式的字符串；

```
一个真正成功的响应应该两个方面：1.服务器成功响应  2.数据已经回到客户端并且可以使用了；
	监听异步对象的响应状态：：xhr.onreadystatechange = function(){}
	xhr.status:可以获取当前服务器的响应状态 xhr.status== 200（成功）；
	解析完毕（判断异步对象的响应状态）：xhr.readystate == 4；    
 
（解析步骤：        	
	0:创建了异步对象，但是还没有真正的去发送请求
        		1.调用了send方法，正在发送请求
       		2.send方法执行完毕了，已经收到服务器的响应内容--原始内容，还不可以使用
       		3.正在解析数据
       		4.响应内容解析完毕，可以使用了；readystate == 4；）
1234567891011
```

GET和POST请求方式的差异（面试题）

1、GET没有请求主体，使用xhr.send(null)；
2、GET可以通过在请求URL上添加请求参数；
3、POST可以通过xhr.send(‘name=itcast&age=10’)；
4、POST需要设置：
Content-type:application/x-www-form-urlencoded
5、GET大小限制约4K，POST则没有限制；

json（对象）:
语法规则
1、数据在名称/值对中
2、数据由逗号分隔(最后一个健/值对不能带逗号)
3、花括号保存对象方括号保存数组
4、使用双引号

```
[]:数组；{}：对象；

在js中通过JSON.pars()方法将json格式的字符串转换为js数组或者对象( 如果文件以[]来描述数据，那么就转换为数组，如果文件以{}来描述数据，那么就转换为对象)
        	JSON.stringify():把对象转为字符串；
1234
```

XML：是一种标记语言，很类似HTML，其宗旨是用来传输数据，具有自我描述性（固定的格式的数据）。
语法规则：
1、必须有一个根元素 ；
2、标签名称不可有空格、不可以数字或.开头、大小写敏感；
3、不可交叉嵌套；
4、属性双引号（浏览器自动修正成双引号了）；
5、特殊符号要使用实体；
6、注释和HTML一样；

sleep（4）：线程暂停；

jQUery中的Ajax：
$.ajax({}) 可配置方式发起Ajax请求；

```
$.get() 以GET方式发起Ajax请求：
	$.get(url,data,success,datatype):本质上只能发送get请求；

$.post() 以POST方式发起Ajax请求：
	 $.post(url,data,success,datatype):本质上只能发送post请求；

$('form').serialize()序列化表单（即格式化key=val&key=val）

url 接口地址

type 请求方式

timeout 请求超时；单位为毫秒，如果服务器的响应时间超过指定时间，那么请求失败；

dataType 服务器返回格式 xml，josn，text，html，script，jsonp；

data 发送请求数据

beforeSend:function () {} ：请求发起前调用，如验证；在这个回调函数中，如果return false,那么本次请求会中止；

success（）： 成功响应后调用的函数；

error （）：错误响应时调用的函数；

   ！！	async: false,  重设为同步执行代码！

complete 响应完成时调用（无论成功和失败都会执行的回调）：
123456789101112131415161718192021222324252627
```

.serialize()：可以通过将表单序列化的方式来收集用户数据：
1.这个方法是jq的方式，所以需要使用jq对象来调用；
2.这个方法可以将表单中所有name属性的表单元素的值收集，生成 key=value&key=value…这种格式；
3.在ajax中，支持两种格式的参数(1.对象 2.参数格式字符串)；

模板引擎：http://aui.github.io/art-template/zh-cn/
// 调用模板引擎动态生成页面结构
// 如果参数是对象是直接传入对象
// 如果参数是数组，就包装为对象
var html = template(“musicTemp”,{“items”:result});

同源策略是浏览器的一种安全策略，所谓同源是指，域名，协议，端口完全相同。

跨域：不同源；ajax默认不支持跨域；

JSONP：
dataType:‘jsonp’ 设置dataType值为jsonp即开启跨域访问；
开启jsonp会自动生成callback函数；
jsonpCallback 可以指定相应的回调函数，默认自动生成；

```
原理：
	1. 主要是利用了script标签的天然的跨域特性来发送请求；
	2. 它的实现方式：在发送请求的时候传递一个函数名称给后台，后台返回数据的时候会返回这个函数的调用形式，并且在()中拼接参数；
	3. ajax和jsonp的本质不一样。ajax的核心是通过XMLHttpRequest来发送请求，而jsonp是通过script标签来实现请求的发送；
1234
```

FormData：

```
        // 1.获取表单
        var myform = document.querySelector("#form1");
        // 2.将表单做为参数传递，在创建formData对象的时候
        var formdata = new FormData(myform);
        // 特点之一：可以自由的追加参数
        formdata.append("address","传智播客");
        // 3.生成的formData对象就可以直接做为异步对象的参数传递
        xhr.send(formdata);
12345678
```

$(’#test’)[0].reset(); 表单重置；

动态添加的点击按钮，注册点击事件时，需要使用事件委托；

mysqli_insert_id；

解决获取地址栏汉字乱码：decodeURI（）；
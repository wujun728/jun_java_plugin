# XhrRequest
一个封装了XMLHttpRequest请求的小工具<br>
背景:因为现在不用jQuery了,但是怀念$.ajax(),特别在不支持fetch的浏览器下超想引入ajax(但是jQuery又不模块化不想引入一个100k的东西),所以就封装了这个小东西

# 使用方式
```html
1.引入 XhrRequest.js
<script src="XhrRequest.js"></script>
2.执行请求的方式
  XhrRequest.execute({
      'url': url,
      'successHandler': function (res) {
        //成功处理器,res为结果
        console.log(res);
      },
      'errorHandler': function (err) {
        //失败处理器,err为XHR对象
        console.log(err);
      }
    });
```
# 请求配置说明
 * url=请求连接
 * method或type=请求方法,默认GET
 * async=是否异步,默认true
 * contentType=请求类型,post时默认为:urlencoded
 * dataType=响应的数据类型,默认为json
 * headers=请求头Header参数,Json格式
 * data=请求的数据,接收字符串或json对象
 * timeout=请求的数据,接收字符串或json对象
 * successHandler=成功处理器,接收参数(result)
 * errorHandler=异常处理器,接收参数(XHR)
 * timeoutHandler=超时处理器,没有参数();
 * requestFormatHandler=请求内容格式化处理器,接收参数(data),需要返回处理结果;
 * resultFormatHandler=返回结果内容格式化处理器,接收参数(dataType,XHR.responseText)

# 快捷请求方式
```html
 //get请求方式
 //url = 请求路径
 //successHandler = 成功处理器参数:返回结果
 //errorHandler = 异常处理器参数:XMLHttpRequest
 //options = 请求配置
 XhrRequest.get(url,successHandler, errorHandler, options);

 //post或put或delete
 //data = 请求数据
 XhrRequest.post(url,data, successHandler, errorHandler, options);

 使用示例:
 function testGet() {
     XhrRequest.get('http://localhost:8080/getJson',function (res) {
       console.log(res);
     },function (xhr) {
       console.log(xhr)
     });
   }  
   
```

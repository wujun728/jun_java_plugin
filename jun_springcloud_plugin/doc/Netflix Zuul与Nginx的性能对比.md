# Netflix Zuul与Nginx的性能对比

**原创**

 [2017-04-03](https://blog.didispace.com/zuul-vs-nginx-performance/)

 翟永超

 [Spring Cloud](https://blog.didispace.com/categories/Spring-Cloud/)

[【推荐】从业15年的架构师告诉你：如何落地微服务和云原生架构？](https://blog.didispace.com/how-to-implement-microservice-and-cloud-native-architecture/)

> 这是一篇翻译，关于大家经常质疑的一个问题：API网关Zuul的性能。
> 原文：[NETFLIX ZUUL VS NGINX PERFORMANCE](http://instea.sk/2015/04/netflix-zuul-vs-nginx-performance/)
> 作者：[STANISLAV MIKLIK](http://instea.sk/author/miklik/)

如今你可以听到很多关于“微服务”的信息。Spring Boot是一个用来构建单个微服务应用的理想选择，但是你还需要以某种方式将它们互相联系起来。这就是Spring Cloud试图解决的问题，尤其是Spring Cloud Netflix。它提供了各种组件，比如：Eureka服务发现与Ribbon客户端负载均衡的结合，为内部“微服务”提供通信支持。但是，如果你想要与外界通信时（你提供外部API，或只是从你的页面使用AJAX），将各种服务隐藏在一个代理之后是一个明智的选择。

常规的选择我们会使用Nginx作为代理。但是Netflix带来了它自己的解决方案——智能路由Zuul。它带有许多有趣的功能，它可以用于身份验证、服务迁移、分级卸载以及各种动态路由选项。同时，它是使用Java编写的。如果Netflix使用它，那么它与本地反向代理相比是否足够快呢？或者当我们对灵活性（或其他功能）要求更高时，它是否适合与Nginx联合使用。

免责声明：不要认为这是一个严肃的基准。我只是想感受Nginx和Zuul的差异，因为我在互联网上并没有找到任何基准（也可能是我没有搜索足够长的时间）。它不遵循任何推荐的基准测试方法（预热时间、测试次数……），我只是使用3个在不同可用区域的EC2实例（这不是最佳的）。

## 测试

那我做了什么呢？测试是比较两种解决方案的原始性能，没有任何其他特殊的功能。我只是同时发起单个HTTP请求来获取一个HTML页面（大小约为26KB）。我使用ApacheBench来发起200个并发线程的测试（我也尝试了httpperf，但是它需要更高的CPU要求，所以还是选择了要求更低的ab）。

#### 直接连接

首先，我感兴趣的是不通过任何反向代理直接访问HTTP服务器的性能。Ab在一台机器上运行，直接访问目标服务器。

```
$ ab -n 10000 -c 200 http://target/sample.html

....

Document Path: /sample.html
Document Length: 26650 bytes

Total transferred: 268940000 bytes
HTML transferred: 266500000 bytes
Requests per second: 2928.45 [#/sec] (mean)
Time per request: 68.295 [ms] (mean)
Time per request: 0.341 [ms] (mean, across all concurrent requests)
Transfer rate: 76911.96 [Kbytes/sec] received

Connection Times (ms)
 min mean[+/-sd] median max
Connect: 4 33 6.0 32 66
Processing: 20 35 7.5 35 392
Waiting: 20 35 6.4 34 266
Total: 24 68 7.8 66 423

Percentage of the requests served within a certain time (ms)
 50% 66
 66% 67
 75% 69
 80% 70
 90% 74
 95% 81
 98% 91
 99% 92
 100% 423 (longest request)
```

很好，几次测试都显示了类似的值：2928、2725、2834、2648 req/s。有一些偏差，但这些数字现在还不重要。

#### 通过Nginx

现在我可以使用Nginx的代理服务。只需要将Nginx配置更新为代理到目标服务器，比如：

```
server {
   listen 80 default_server;
   listen [::]:80 default_server ipv6only=on;

   # Make site accessible from http://localhost/
   server_name localhost;

   # allow file upload
   client_max_body_size 10M;

   location / {
      proxy_set_header X-Real-IP $remote_addr;
      proxy_set_header X-Forwarded-For $remote_addr;
      proxy_set_header Host $host;
      proxy_pass http://target:80;
   }
}
```

像之前一样运行类型的测试：

```
$ ab -n 50000 -c 200 http://proxy/sample.html
...
Server Software: nginx/1.4.6
Server Hostname: proxy
Server Port: 80

Document Path: /sample.html
Document Length: 26650 bytes

Concurrency Level: 200
Time taken for tests: 52.366 seconds
Complete requests: 50000
Failed requests: 0
Total transferred: 1344700000 bytes
HTML transferred: 1332500000 bytes
Requests per second: 954.81 [#/sec] (mean)
Time per request: 209.465 [ms] (mean)
Time per request: 1.047 [ms] (mean, across all concurrent requests)
Transfer rate: 25076.93 [Kbytes/sec] received

Connection Times (ms)
 min mean[+/-sd] median max
Connect: 3 50 11.7 48 114
Processing: 37 159 11.9 160 208
Waiting: 36 159 11.9 160 207
Total: 40 209 10.4 209 256

Percentage of the requests served within a certain time (ms)
 50% 209
 66% 212
 75% 214
 80% 216
 90% 220
 95% 224
 98% 232
 99% 238
 100% 256 (longest request)
```

测试结果为954、954、941 req/s。性能与延迟（如预期）变差了。

#### 通过Zuul

现在我们在同一台机器上安装Zuul。它的应用本身很简单：

```
@SpringBootApplication
@Controller
@EnableZuulProxy
public class DemoApplication {
    public static void main(String[] args) {
        new SpringApplicationBuilder(DemoApplication.class)
            .web(true).run(args);
    }
}
```

我们还需要在 `application.yml`中定义固定的路由规则：

```
zuul:
  routes:
    sodik:
      path: /sodik/**
      url: http://target
```

现在我们试试运行测试：

```
$ ab -n 50000 -c 200 http://proxy:8080/sodik/sample.html

Server Software: Apache-Coyote/1.1
Server Hostname: proxy
Server Port: 8080

Document Path: /sodik/sample.html
Document Length: 26650 bytes

Concurrency Level: 200
Time taken for tests: 136.164 seconds
Complete requests: 50000
Failed requests: 2
(Connect: 0, Receive: 0, Length: 2, Exceptions: 0)
Non-2xx responses: 2
Total transferred: 1343497042 bytes
HTML transferred: 1332447082 bytes
Requests per second: 367.20 [#/sec] (mean)
Time per request: 544.657 [ms] (mean)
Time per request: 2.723 [ms] (mean, across all concurrent requests)
Transfer rate: 9635.48 [Kbytes/sec] received

Connection Times (ms)
min mean[+/-sd] median max
Connect: 2 12 92.3 2 1010
Processing: 15 532 321.6 461 10250
Waiting: 10 505 297.2 441 9851
Total: 17 544 333.1 467 10270

Percentage of the requests served within a certain time (ms)
50% 467
66% 553
75% 626
80% 684
90% 896
95% 1163
98% 1531
99% 1864
100% 10270 (longest request)
```

结果比我（乐观的）猜测更差。此外，我们还能看到两次请求失败（我们可以在Zuul的日志中看到有两个相应的异常，这些异常引发了HTTP连接池超时）。显然默认情况下超时时间为10秒。

我们再进一步测试，得到了更多的结果：

```
Document Path: /sodik/sample.html
Document Length: 26650 bytes

Concurrency Level: 200
Time taken for tests: 50.080 seconds
Complete requests: 50000
Failed requests: 0
Total transferred: 1343550000 bytes
HTML transferred: 1332500000 bytes
Requests per second: 998.39 [#/sec] (mean)
Time per request: 200.322 [ms] (mean)
Time per request: 1.002 [ms] (mean, across all concurrent requests)
Transfer rate: 26199.09 [Kbytes/sec] received

Connection Times (ms)
min mean[+/-sd] median max
Connect: 2 16 7.9 16 126
Processing: 15 184 108.1 203 1943
Waiting: 13 183 105.9 202 1934
Total: 18 200 107.8 218 1983

Percentage of the requests served within a certain time (ms)
50% 218
66% 228
75% 235
80% 239
90% 254
95% 287
98% 405
99% 450
100% 1983 (longest request)
```

哇，不错的改善。我认为Java JIT编译对于性能有一定的帮助，但是要验证这是否只是一个巧合，再尝试一次：1010 req / sec。最终结果对我来说是一个惊喜。

## 结论

Zuul的原始性能非常接近于Nginx。事实上，在启动预热之后，我的测试结果甚至略好一些（重申免责声明-这并非一个严肃的基准性能测试）。Nginx显示出更多的可预测性能（变化较小），可悲的是在Zuul预热期间，我们经历了一些小故障（150000个请求中的2个，但是您的微服务应该是容错机制的，对吧？）。

所以，如果您考虑使用一些Zuul的额外功能，或者希望通过它与其他Netflix服务集成（比如Eureka）获得更多的服务能力，Zuul看起来非常有希望作为简单反向代理的替代产品。也许这也是Netflix使用的原因，所以您也可以尝试一下。
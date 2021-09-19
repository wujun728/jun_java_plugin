# Guice Servlet  

Guice Servlet 为使用web应用程序和Servlet容器提供了一个完整的模式。. Guice's servlet 扩展允许从你的servlet应用中完全淘汰web.xml，并且具有类型安全（type-safe）的优势。 符合[Java](http://lib.csdn.net/base/17)方式的配置你的servlet和filter组件。

 这不仅在于可以使用更好的API来配置你的web应用程序，而且也在于在web应用组件中加入依赖注入，意味着你的servlet和filter得益于以下几个方面：

- 构造方法注入（Constructor injection）
- 类型安全，更符合习惯的配置方式（Type-safe, idiomatic configuration）
- 模块化(打包和发布个性化的Guice Servlet类库
- Guice 面向切面编程

在标准的servlet生命周期都将受益。

 

guice servlet简化了传统servlet的开发。

 

具体如下:

[![复制代码](https://common.cnblogs.com/images/copycode.gif)](javascript:void(0);)

```
 1   <filter>
 2     <filter-name>guiceFilter</filter-name>
 3     <filter-class>com.google.inject.servlet.GuiceFilter</filter-class><!--这个是guice servlet的过滤器-->
 4   </filter>
 5   <filter-mapping>
 6     <filter-name>guiceFilter</filter-name>
 7     <url-pattern>/*</url-pattern>
 8   </filter-mapping>
 9   <listener>
10     <listener-class>com.ming.core.web.listener.GoogleGuiceServletContextListener</listener-class><!--这个是用于注册module及servlet的-->
11   </listener>
```

[![复制代码](https://common.cnblogs.com/images/copycode.gif)](javascript:void(0);)

 

 

[![复制代码](https://common.cnblogs.com/images/copycode.gif)](javascript:void(0);)

```
 1 package com.ming.core.web.listener;
 2 
 3 import com.google.inject.Guice;
 4 import com.google.inject.Injector;
 5 import com.google.inject.servlet.GuiceServletContextListener;
 6 import com.ming.user.UserModule;
 7 
 8 public class GoogleGuiceServletContextListener extends GuiceServletContextListener {
 9 
10     @Override
11     protected Injector getInjector() {
12         
13         return Guice.createInjector(new UserModule());
14         //如果绑定多个module，需要像下面这样就可以了
15         //return Guice.createInjector(new UserModule(),new UserModule());
16     }
17 
18 }
```

[![复制代码](https://common.cnblogs.com/images/copycode.gif)](javascript:void(0);)

 

[![复制代码](https://common.cnblogs.com/images/copycode.gif)](javascript:void(0);)

```
 1 package com.ming.user;
 2 
 3 import com.google.inject.AbstractModule;
 4 import com.google.inject.servlet.ServletModule;
 5 import com.ming.core.web.filter.EncodeFilter;
 6 import com.ming.user.action.UserServlet;
 7 public class UserModule extends AbstractModule {
 8 
 9 
10     @Override
11     protected void configure() {
12         install(new ServletModule(){
13             @Override
14             protected void configureServlets() {
15                 
16                 //配置你访问的servlet
17                 //serve("/UserServlet").with(UserServlet.class);
18                 
19                 //如果你一个servlet拥有多个访问地址，这样配置就可以了
20                 serve("/UserServlet","/UserController").with(UserServlet.class);
21                 
22                 //如果你想你的url支持正则匹配，可以像下面这样写
23                 //serveRegex("^user").with(UserServlet.class);
24                 
25                 //同理filter配置如下
26                 //filter("/encodeFilter").through(EncodeFilter.class);
27                 
28                 //多个地址
29                 //filter("/encodeFilter","/haha").through(EncodeFilter.class);
30                 
31                 //支持正则
32                 //filterRegex("^aaa").through(EncodeFilter.class);
33                 
34             }
35         });
36         
37     }
38 
39     
40 }
```

[![复制代码](https://common.cnblogs.com/images/copycode.gif)](javascript:void(0);)

[![复制代码](https://common.cnblogs.com/images/copycode.gif)](javascript:void(0);)

```
 1 package com.ming.user.action;
 2 
 3 import java.io.IOException;
 4 import java.util.ArrayList;
 5 import java.util.List;
 6 
 7 import javax.servlet.ServletException;
 8 import javax.servlet.http.HttpServlet;
 9 import javax.servlet.http.HttpServletRequest;
10 import javax.servlet.http.HttpServletResponse;
11 
12 import com.google.inject.Inject;
13 import com.google.inject.Singleton;
14 import com.ming.user.entity.User;
15 import com.ming.user.service.UserService;
16 
17 /**
18  * 
19  * @author mingge
20  *
21  */
22 @Singleton
23 public class UserServlet extends HttpServlet {
24     
25     
26     private static final long serialVersionUID = 1L;
27 
28     @Inject
29     private UserService userService;
30 
31     protected void doGet(HttpServletRequest request, HttpServletResponse response)
32             throws ServletException, IOException {
33 
34         String account = request.getParameter("account");
35         int userId = Integer.valueOf(request.getParameter("userid"));
36         User u = new User();
37         u.setAccount(account);
38         u.setUser_id(userId);
39         List<User> ulist=new ArrayList<>();
40         ulist.add(u);
41         try {
42             userService.add(u);
43             System.out.println("ok");
44         } catch (Exception e) {
45             System.out.println("error");
46             e.printStackTrace();
47             // 注意：调用service层的方法出异常之后，继续将异常抛出，这样在TransactionFilter就能捕获到抛出的异常，继而执行事务回滚操作
48             throw new RuntimeException(e);
49         }
50 
51     }
52 
53     protected void doPost(HttpServletRequest request, HttpServletResponse response)
54             throws ServletException, IOException {
55 
56         doGet(request, response);
57     }
58 
59 }
```

[![复制代码](https://common.cnblogs.com/images/copycode.gif)](javascript:void(0);)

 

看起简单吧。具体例子下载:http://pan.baidu.com/s/1geMXE1t
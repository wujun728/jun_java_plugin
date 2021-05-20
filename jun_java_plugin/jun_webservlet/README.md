# jun_webservlet

> 此 demo 主要演示了 Servlet 3.0 注解使用，异步回调、GET及POST请求返回、文件上传等。

#Servlet3.0 记得servlet3的特性有以下几点：

1、异步处理支持：有了该特性，Servlet 线程不再需要一直阻塞，直到业务处理完毕才能再输出响应，最后才结束该 Servlet 线程。在接收到请求之后，Servlet 线程可以将耗时的操作委派给另一个线程来完成，自己在不生成响应的情况下返回至容器。针对业务处理较耗时的情况，这将大大减少服务器资源的占用，并且提高并发处理速度。
2、新增的注解支持：该版本新增了若干注解，用于简化 Servlet、过滤器（Filter）和监听器（Listener）的声明，这使得 web.xml 部署描述文件从该版本开始不再是必选的了。
3、可插性支持：熟悉 Struts2 的开发者一定会对其通过插件的方式与包括 Spring 在内的各种常用框架的整合特性记忆犹新。将相应的插件封装成 JAR 包并放在类路径下，Struts2 运行时便能自动加载这些插件。现在 Servlet 3.0 提供了类似的特性，开发者可以通过插件的方式很方便的扩充已有 Web 应用的功能，而不需要修改原有的应用。

以前的servlet的流程：

首先，Servlet接收到请求之后，可能需要对请求携带的数据进行一些处理；接着，调用业务接口的某些方法，已完成业务处理；最后根据处理的结果提交响应，Servlet线程结束。其中第二步的业务处理通常是最耗时的，这主要体现数据库操作，以及其它的跨网络调用等、在此过程中、Servlet线程一直处于阻塞状态，直到业务方法执行完毕。在处理业务过程中、Servlet资源一直被占用而得不到释放，对于并发较大的应用，这有可能造成性能瓶颈，对此，在以前通常采用私有解决方案，来提前结束Servlet线程，并及时释放资源。

Servlet3.0流程：
首先，Servlet接收到请求之后，可能首先需要对请求携带的数据进行一些预处理；接着，Servlet线程将请求转交给一个异步线程来执行业务处理，线程本身返回至容器、此时Servlet还没有生成响应数据，异步线程处理完业务以后，可以直接生成响应数据（异步线程拥有ServletRequest和ServletResponse对象的引用），或者将请求继续转发给其它的Servlet，如此一来，Servlet线程不再是一直处于阻塞状态以等待业务逻辑的处理，而是启动异步线程之后可以立即返回。

注意：

Servlet 3.0 还为异步处理提供了一个监听器，使用 AsyncListener 接口表示。它可以监控如下四种事件：

异步线程开始时，调用 AsyncListener 的 onStartAsync(AsyncEvent event) 方法；
异步线程出错时，调用 AsyncListener 的 onError(AsyncEvent event) 方法；
异步线程执行超时，则调用 AsyncListener 的 onTimeout(AsyncEvent event) 方法；
异步执行完毕时，调用 AsyncListener 的 onComplete(AsyncEvent event) 方法；

要注册一个 AsyncListener，只需将准备好的 AsyncListener 对象传递给 AsyncContext 对象的 addListener() 方法即可。

在这里插入图片描述
代码实现：
##Http2Servlet3.java
```java
import javax.servlet.annotation.WebServlet;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Queue;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import javax.servlet.AsyncContext;
import javax.servlet.AsyncEvent;
import javax.servlet.AsyncListener;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(value = {"/http23"}, asyncSupported = true)
public class Http2Servlet3 extends HttpServlet {

    private Queue<String> messages = new ConcurrentLinkedQueue<String>();
    private final Executor executor = Executors.newFixedThreadPool( 10 );
    private List<AsyncContext> ctxs = new ArrayList<AsyncContext>();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        res.setContentType( "text/plain" );
        res.setCharacterEncoding( "utf-8" );
        res.setHeader( "Access-Control-Allow-Origin", "*" );
        PrintWriter writer = res.getWriter();
        writer.print( "2;Hi;" );
        writer.flush();

        final AsyncContext ctx = req.startAsync();
        ctx.addListener( new AsyncListener() {
            @Override
            public void onStartAsync(AsyncEvent event) throws IOException {
            }
            @Override
            public void onTimeout(AsyncEvent event) throws IOException {
                ctxs.remove( ctx );
            }
            @Override
            public void onError(AsyncEvent event) throws IOException {
                ctxs.remove( ctx );
            }
            @Override
            public void onComplete(AsyncEvent event) throws IOException {
                ctxs.remove( ctx );
            }
        } );
        ctxs.add( ctx );
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        res.setContentType( "text/plain" );
        res.setCharacterEncoding( "utf-8" );
        messages.add( createRandomMessage() );
    }

    @Override
    public void init() throws ServletException {
        super.init();
        // produce random messages
        //生成随机消息
        new Thread( new Runnable() {
            @Override
            public void run() {
                while (true) {
                    messages.add( createRandomMessage() );
                    try {
                        Thread.sleep( new Random().nextInt( 5 ) * 1000 );
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        } ).start();

        // print messages to all users
        //向所有用户打印message

        new Thread( new Runnable() {
            @Override
            public void run() {
                while (true) {
                    if (!messages.isEmpty()) {
                        final String message = messages.poll();
                        executor.execute( new Runnable() {
                            @Override
                            public void run() {
                                for (AsyncContext ctx : ctxs) {
                                    try {
                                        PrintWriter writer = ctx.getResponse().getWriter();
                                        writer.print( message.length() );
                                        writer.print( ';' );
                                        writer.print( message );
                                        writer.print( ';' );
                                        writer.flush();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                            };
                        } );
                    }
                }
            }
        } ).start();
    }

    protected String createRandomMessage() {
        return DateFormat.getTimeInstance().format( Calendar.getInstance().getTime() ) + ' ' + UUID.randomUUID().toString();
    }
}
```

##AsyncDemoServlet.java
```java
import javax.servlet.AsyncContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

@WebServlet(urlPatterns = "/servlet3", asyncSupported = true)
public class AsyncDemoServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        resp.setContentType( "text/html;charset=UTF-8" );
        PrintWriter out = resp.getWriter();
        out.println( "进入Servlet的时间：" + new Date() + "." );
        out.flush();
        //在子线程中执行业务调用，并由其负责输出响应，主线程退出
        AsyncContext ctx = req.startAsync();
        new Thread( new Executor( ctx ) ).start();
        out.println( "结束Servlet的时间：" + new Date() + "." );
        out.flush();
    }
}

/*public class Executor implements Runnable {
    private AsyncContext ctx = null;
    public Executor(AsyncContext ctx) {
        this.ctx = ctx;
    }*/
class Executor implements Runnable {
    private AsyncContext ctx = null;
    public Executor(AsyncContext ctx) {
    this.ctx = ctx;
    }

    @Override
    public void run() {
        try {
            //等待十秒钟，以模拟业务方法的执行
            Thread.sleep( 10000 );
            PrintWriter out = ctx.getResponse().getWriter();
            out.println( "业务处理完毕的时间：" + new Date() + "." );
            out.flush();
            ctx.complete();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
```
##pom.xml
```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>Servlet4Push</groupId>
    <artifactId>Servlet4Push</artifactId>
    <version>1.0-SNAPSHOT</version>

    <packaging>war</packaging>

    <properties>
        <endorsed.dir>${project.build.directory}/endorsed</endorsed.dir>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
    </properties>

    <dependencies>

        <!--servlet4-->
       <dependency>
            <groupId>javax.servlet</groupId>
            <artifactId>javax.servlet-api</artifactId>
            <version>4.0.1</version>
            <scope>provided</scope>
        </dependency>

       <!-- servlet3-->
      <!-- <dependency>
            <groupId>javax.servlet</groupId>
            <artifactId>javax.servlet-api</artifactId>
            <version>3.0.1</version>
            <scope>provided</scope>
        </dependency>-->

        <!--servlet3.1-->
        <!-- <dependency>
            <groupId>javax.servlet</groupId>
            <artifactId>javax.servlet-api</artifactId>
            <version>3.1.0</version>
            <scope>provided</scope>
        </dependency>-->
    </dependencies>

    <build>
        <plugins>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-compiler-plugin</artifactId>
                <version>3.1</version>
                <configuration>
                    <source>1.8</source>
                    <target>1.8</target>
                    <compilerArguments>
                        <endorseddirs>${endorsed.dir}</endorseddirs>
                    </compilerArguments>
                </configuration>
            </plugin>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-war-plugin</artifactId>
                <version>2.6</version>
                <configuration>
                    <warName>Servlet4Push</warName>
                    <failOnMissingWebXml>false</failOnMissingWebXml>
                </configuration>
            </plugin>
        </plugins>
    </build>

</project>
```
记得servlet4新功能：

**1、服务器推送：**是最直观的 HTTP/2 强化功能，通过 PushBuilder 接口在 servlet 中公开。服务器推送功能还在 JavaServer Faces API 中实现，并在 RenderResponsePhase 生命周期内调用，以便 JSF 页面可以利用其增强性能。
2、全新 servlet 映射发现接口： HttpServletMapping 使框架能够获取有关激活给定 servlet 请求的 URL 信息。这可能对框架尤为有用，这些框架需要这一信息来运行内部工作。

Servlet4.0优点：

服务器推送使服务器能预测客户端请求的资源需求。然后，在完成请求处理之前，它可以将这些资源发送到客户端。

要了解服务器推送的好处，可以考虑一个包含图像和其他依赖项（比如 CSS 和 JavaScript 文件）的网页。客户端发出一个针对该网页的请求。服务器然后分析所请求的页面，确定呈现它所需的资源，并主动将这些资源发送到客户端的缓存。

在执行所有这些操作的同时，服务器仍在处理原始网页请求。客户端收到响应时，它需要的资源已经位于缓存中。Servlet 4.0 通过 PushBuilder 接口公开服务器推送、也可以推送静态资源等。

代码实现：

##Http2Servlet4.java
```java
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;

//@WebServlet({"/path/*", "*.ext"})
@WebServlet(value = {"/http24"})
public class Http2Servlet4 extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        PushBuilder pushBuilder = req.newPushBuilder();
        if (pushBuilder != null) {
            pushBuilder
                    .path("images/kodedu-logo.png")
                    .addHeader("content-type", "image/png")
                    .push();
        }

        /*PushBuilder pushBuilder = request.newPushBuilder();

        if (pushBuilder != null) {
            pushBuilder.path("images/hero-banner.jpg").push();
            pushBuilder.path("css/menu.css").push();
            pushBuilder.path("js/marquee.js").push();
        }*/


        HttpServletMapping mappings = req.getHttpServletMapping();
        String mapping = mappings.getMappingMatch().name();
        String value = mappings.getMatchValue();
        String pattern = mappings.getPattern();
        String servletName = mappings.getServletName();


        try (PrintWriter respWriter = resp.getWriter();) {
            respWriter.write("<html>" +
                    "<img src='images/kodedu-logo.png'>" +
                    "</html>");
        }

    }
}
```

## pom.xml

```xml
 
 <plugin> 
	<groupId>org.apache.tomcat.maven</groupId>
	<artifactId>tomcat7-maven-plugin</artifactId>
	<version>2.1</version>
	<configuration>
		<port>8080</port>
		<path>/jun_webservlet</path>
		<uriEncoding>UTF-8</uriEncoding>
		<finalName>jun_webservlet</finalName>
		<server>tomcat7</server>
	</configuration>
</plugin>
```

## Servlet3Demo.java

```java
/**
  * 注解WebServlet用来描述一个Servlet
  * 属性name描述Servlet的名字,可选
  * 属性urlPatterns定义访问的URL,或者使用属性value定义访问的URL.(定义访问的URL是必选属性)
  */
 @WebServlet(name="Servlet3Demo",urlPatterns="/Servlet3Demo")
 @WebInitParam(name="a", value="valuea")  
 public class Servlet3Demo extends HttpServlet {
	 /*
	  * 完成了一个使用注解描述的Servlet程序开发。
	 　　使用@WebServlet将一个继承于javax.servlet.http.HttpServlet的类定义为Servlet组件。
	 　　@WebServlet有很多的属性：
	     　　1、asyncSupported：    声明Servlet是否支持异步操作模式。
	     　　2、description：　　    Servlet的描述。
	     　　3、displayName：       Servlet的显示名称。
	     　　4、initParams：        Servlet的init参数。
	     　　5、name：　　　　       Servlet的名称。
	     　　6、urlPatterns：　　   Servlet的访问URL。
	     　　7、value：　　　        Servlet的访问URL。
	 　　Servlet的访问URL是Servlet的必选属性，可以选择使用urlPatterns或者value定义。
	 　　像上面的Servlet3Demo可以描述成@WebServlet(name="Servlet3Demo",value="/Servlet3Demo")。
	 　　也定义多个URL访问：
	 　　如@WebServlet(name="Servlet3Demo",urlPatterns={"/Servlet3Demo","/Servlet3Demo2"})
	 　　或者@WebServlet(name="AnnotationServlet",value={"/Servlet3Demo","/Servlet3Demo2"})
	  *
	  */
     public void doGet(HttpServletRequest request, HttpServletResponse response)
             throws ServletException, IOException {
         response.getWriter().write("Hello Servlet3.0");
     }
 
     public void doPost(HttpServletRequest request, HttpServletResponse response)
             throws ServletException, IOException {
         this.doGet(request, response);
     }
 }
```

## UploadServlet.java

```java
 //使用@WebServlet配置UploadServlet的访问路径
//使用注解@MultipartConfig将一个Servlet标识为支持文件上传
//标识Servlet支持文件上传
@WebServlet(name="UploadServlet",urlPatterns="/UploadServlet")
@MultipartConfig
public class UploadServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
             request.setCharacterEncoding("utf-8");
            response.setCharacterEncoding("utf-8");
            response.setContentType("text/html;charset=utf-8");
            //存储路径
            String savePath = request.getServletContext().getRealPath("/WEB-INF/uploadFile");
            //获取上传的文件集合
            Collection<Part> parts = request.getParts();
            //上传单个文件
            if (parts.size()==1) {
                 //Servlet3.0将multipart/form-data的POST请求封装成Part，通过Part对上传的文件进行操作。
                //Part part = parts[0];//从上传的文件集合中获取Part对象
                Part part = request.getPart("file");//通过表单file控件(<input type="file" name="file">)的名字直接获取Part对象
                //Servlet3没有提供直接获取文件名的方法,需要从请求头中解析出来
                //获取请求头，请求头的格式：form-data; name="file"; filename="snmp4j--api.zip"
                String header = part.getHeader("content-disposition");
                //获取文件名
                String fileName = getFileName(header);
                //把文件写到指定路径
                part.write(savePath+File.separator+fileName);
            }else {
                //一次性上传多个文件
                for (Part part : parts) {//循环处理上传的文件
                    //获取请求头，请求头的格式：form-data; name="file"; filename="snmp4j--api.zip"
                    String header = part.getHeader("content-disposition");
                    //获取文件名
                    String fileName = getFileName(header);
                    //把文件写到指定路径
                    part.write(savePath+File.separator+fileName);
                }
            }
            PrintWriter out = response.getWriter();
            out.println("上传成功");
            out.flush();
            out.close();
    }
```

参考：https://www.ibm.com/developerworks/cn/java/j-lo-servlet30/index.html
参考：https://www.ibm.com/developerworks/cn/java/j-javaee8-servlet4/index.html
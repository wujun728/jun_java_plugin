package com.jun.webservlet2;

 import java.io.IOException;
 import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
 import javax.servlet.http.HttpServlet;
 import javax.servlet.http.HttpServletRequest;
 import javax.servlet.http.HttpServletResponse;
 
 /**
  * 注解WebServlet用来描述一个Servlet
  * 属性name描述Servlet的名字,可选
  * 属性urlPatterns定义访问的URL,或者使用属性value定义访问的URL.(定义访问的URL是必选属性)
  */
 @WebServlet(name="Servlet3Demo",urlPatterns="/Servlet3Demo")
 @WebInitParam(name="a", value="valuea")  
 public class Servlet3Demo extends HttpServlet {
     
     public void doGet(HttpServletRequest request, HttpServletResponse response)
             throws ServletException, IOException {
         response.getWriter().write("Hello Servlet3.0");
     }
 
     public void doPost(HttpServletRequest request, HttpServletResponse response)
             throws ServletException, IOException {
         this.doGet(request, response);
     }
 }
 
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
package com.jun.plugin.webservlet;
import java.io.IOException;
import java.lang.reflect.Method;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
 
public class BaseServlet extends HttpServlet{
     
	private static final long serialVersionUID = -1320797119073546791L;
	public HttpSession session = null;   //创建一个session对象，让BaseServlet的子类可以直接拿来用
    public String rootPath = null;  //同理创建一个项目名变量
  
    protected void service(HttpServletRequest request , HttpServletResponse response)
        throws ServletException,IOException{
        session = request.getSession();  //得到session
        rootPath = request.getContextPath();   //得到项目名
 
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
 
        String methodName = request.getParameter("method");
        if(methodName == null){    //如果method参数为空
            throw new RuntimeException("请传递method参数以确定您要调用的方法！");
        }
        Method method = null;
        
        try{
            // 2. 通过方法名称methodName来获取Method对象
        	/*
             * this.getClass()  :   得到当前类
             * this.getClass.getMethod()  : 调用当前类的getMethod()来获取当前类的方法
             * methodName : 要获取的方法名称
             * HttpServletRequest.class和HttpServletResponse.class : 要获取的方法的参数的类型
             * 由下面的参数我们不难看出，我们以后写的继承了BaseServlet的子类Servlet中：
             *   1）形参必须是(HttpServletRequest request,HttpServletResponse response)
             *   2）必须抛出与service()方法抛出的相同的异常
             */
            method = this.getClass().getMethod(methodName , HttpServletRequest.class , HttpServletResponse.class);
            
        }catch(NoSuchMethodException e){
            //如果没有找到就说明你写的Servlet中没有此方法
            throw new RuntimeException("您调用的"+methodName+ "(HttpServletRequest request , HttpServletResponse response)方法不存在",e);
        }
 
        //如果程序能够继续执行到这里，说明要调用的方法存在
        // 3. 通过Method对象来调用它
        try{
        	/*
             * 通过反射来调用当前类的方法：
             * method : 你要调用的方法的Method实例对象
             * this : 谁来调用(当前类就是this)
             * request,response : method方法的参数
             * 本来是 this.method(request,response);
             *     现在method是一个变量，不能确定，所以用反射来调用。
             */
            method.invoke(this,request,response);
            
        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }
    
    
    
    public void test(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            // 获取请求标识
            String methodName = request.getParameter("method");
            // 获取指定类的字节码对象
            Class<? extends BaseServlet> clazz = this.getClass();//这里的this指的是继承BaseServlet对象
            // 通过类的字节码对象获取方法的字节码对象
            Method method = clazz.getMethod(methodName, HttpServletRequest.class, HttpServletResponse.class);
            // 让方法执行
            method.invoke(this, request, response);

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
 
    


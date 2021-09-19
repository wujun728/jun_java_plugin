package com.lf.servlet;
import com.lf.bean.userBean;
import com.lf.service.serviceImpl.userServiceImpl;
import com.lf.service.userService1;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

//@WebServlet(name = "zhuceServlet")
public class zhuceServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request,response);

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
            //使用request获取前端页面的数据
        String UserName = request.getParameter("name");
        String UserSex = request.getParameter("sex");
        String UserAddress = request.getParameter("address");
        String date = request.getParameter("date");
        //然后把String类型的时间转换为Date类型
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date2=null;
        try {
            date2 = simpleDateFormat.parse(date);
            System.out.print(date2);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        //把数据封装在对象里面
        userBean user= new userBean(UserName,UserSex,UserAddress,date2);
        //调用service把数据写入到数据库中
        int a=0;
        userService1 userService=new userServiceImpl();
        try {
            a=userService.addUserService(user);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
//		响应数据的时候乱码
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
        if (a!=0) {
            response.getWriter().print("<script>alert('用户注册成功！！！！')</script>");
        }

    }

}

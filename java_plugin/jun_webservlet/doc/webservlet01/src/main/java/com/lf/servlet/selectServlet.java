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
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "selectServlet")
public class selectServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request,response);

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //查询数据库的数据,在页面进行显示
        userService1 userService1= new userServiceImpl();
        List<userBean> list= new ArrayList<userBean>();
        try {
            list=userService1.selectAll();
        } catch (Exception e) {
            e.printStackTrace();
        }
        //响应数据的时候乱码
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
         //在页面制表
        response.getWriter().print("<center><table border='1'>");
        response.getWriter().print("<tr>");
        response.getWriter().print("<th>用户</th>");
        response.getWriter().print("<th>用户名称</th>");
        response.getWriter().print("<th>用户性别</th>");
        response.getWriter().print("<th>用户地址</th>");
        response.getWriter().print("<th>操作</th>");
        for (userBean userBean : list) {
            response.getWriter().print("<tr>");
            //获取数据
            response.getWriter().print(userBean.getId());
            response.getWriter().print(userBean.getNaem());
            response.getWriter().print(userBean.getAddress());
            response.getWriter().print(userBean.getSex());
            response.getWriter().print(userBean.getDate());
            //定义操作
            response.getWriter().print("<td><a href='DeleteServlet?id="+userBean.getId()+"'>"
                    + "删除/</a><a href='UpdateServlet?id="+userBean.getId()+"'>修改</a></td>");
            response.getWriter().print("</tr>");
        }

    }
}

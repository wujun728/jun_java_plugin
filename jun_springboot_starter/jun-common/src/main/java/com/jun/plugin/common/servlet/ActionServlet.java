package com.jun.plugin.common.servlet;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "action",urlPatterns = {"/action"})
public class ActionServlet extends BaseServlet {

    public void add(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //执行service层代码
    }
    public void del(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //执行service层代码
    }
    public void update(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //执行service层代码
    }
    public void find(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //执行service层代码
    }
}


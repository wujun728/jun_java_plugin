package com.jun.plugin.common.service;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/hello")
public class HelloServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 设置响应类型为 HTML
        response.setContentType("text/html");

        // 获取输出流
        PrintWriter out = response.getWriter();

        // 生成 HTML 页面
        out.println("<html>");
        out.println("<head>");
        out.println("<title>Hello World Title</title>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h1>Hello, World! 6666 666 </h1>");
        out.println("</body>");
        out.println("</html>");

        // 关闭输出流
        out.close();
    }
}
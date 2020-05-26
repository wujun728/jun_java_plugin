package com.jun.webservlet;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;


@WebServlet("/UploadServlet")
@MultipartConfig
public class UploadServlet2 extends HttpServlet {

 private static final long serialVersionUID = 5661013723204858883L;

 protected void doGet(HttpServletRequest request, HttpServletResponse response)
     throws ServletException, IOException {
   // 获取文件上传组件
   Part part = request.getPart("file");

   // 获取文件的路径
   String header = part.getHeader("content-disposition");
   String path = header.substring(header.indexOf("filename=") + 10, header.length() - 1);

   // 获取文件名
   String name = UploadUtils.getRealName(path);

   // 获取文件的存放目录
   String dir = UploadUtils.getDir(name);

   String realPath = this.getServletContext().getRealPath("/upload" + dir);
   File file = new File(realPath);
   if (!file.exists()) {
     file.mkdirs();
   }

   // 对拷流
   InputStream inputStream = part.getInputStream();
   FileOutputStream outputStream = new FileOutputStream(new File(file, name));
   int len = -1;
   byte[] bytes = new byte[1024];
   while ((len = inputStream.read(bytes)) != -1) {
     outputStream.write(bytes, 0, len);
   }

   // 关闭资源
   outputStream.close();
   inputStream.close();

   // 删除临时文件
   part.delete();

   response.setContentType("text/html;charset=utf-8");
   response.getWriter().print("文件" + name + "上传成功！");
 }

 protected void doPost(HttpServletRequest request, HttpServletResponse response)
     throws ServletException, IOException {
   doGet(request, response);
 }

}
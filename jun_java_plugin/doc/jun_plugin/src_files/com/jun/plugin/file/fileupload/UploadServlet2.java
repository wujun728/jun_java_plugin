package com.jun.plugin.file.fileupload;


import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.DefaultFileItemFactory;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

public class UploadServlet2 extends HttpServlet {
	PrintWriter out=null;
	/**
	 * 处理请求 
	 */
	public void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//设置中文 
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		out= response.getWriter();
		
		
		//确定本次请求是否是文件上传请�?
		boolean flag=ServletFileUpload.isMultipartContent(request);
		if(flag){
			//创建文件上传的工厂对�?
			FileItemFactory factory=new DiskFileItemFactory();
			//创建ServletFileUpload对象，并将工厂对象作为参数传�?
			ServletFileUpload upload=new ServletFileUpload(factory);
			//设置上传文件的大�?
			upload.setSizeMax(1024*1024*3); //3m
			//使用上传对象从请求对象中解析出提交的�?有表单元�?
			try {
				List<FileItem> lstForms=upload.parseRequest(request);
				//遍历�?有表单元�?
				for (FileItem fileItem : lstForms) {
					//判断每一个表单元素是否是普�?�表�?
					if(fileItem.isFormField()){
						System.out.println(fileItem.getString("UTF-8"));
					}else{
						//上传前准备工�?
						//a、上传后的路径this.getServletContext().getRealPath("/")会出去当前项目在 部署的服务器上的绝对路径
					
						String path=this.getServletContext().getRealPath("/")+"files/";
						//b、找出要上传的文件的名字
						String fileName=fileItem.getName();
						fileName=fileName.substring(fileName.lastIndexOf("\\")+1);
						//c、上�?
						fileItem.write(new File(path+fileName));
						out.println("<h2>"+fileName+"上传成功�?</h2>");
					}
				}
			} catch (Exception e) {
				System.out.println("错误�?"+e.getMessage());
			}
		}else{
			System.out.println("没有上传");
		}
		
		
		out.flush();
		out.close();
	}
	
}

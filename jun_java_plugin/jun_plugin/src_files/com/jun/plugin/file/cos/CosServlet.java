package com.jun.plugin.file.cos;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jun.plugin.base.BaseServlet;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;
import com.oreilly.servlet.multipart.FilePart;
import com.oreilly.servlet.multipart.FileRenamePolicy;
import com.oreilly.servlet.multipart.MultipartParser;
import com.oreilly.servlet.multipart.ParamPart;
import com.oreilly.servlet.multipart.Part;
/**
 * 在Cos中就一个类，
 * MultipartRequest它是request的包装类。
 */

@WebServlet(name="CosServlet",value="/CosServlet")
public class CosServlet extends BaseServlet {
	public void doPost(HttpServletRequest request, HttpServletResponse resp)
			throws ServletException, IOException {
		//第一步：声明文件的保存目录
		String path = request.getSession().getServletContext().getRealPath("/up");
		//第二步：文件传
		//声明文件重新取名的策略
		FileRenamePolicy rename = new DefaultFileRenamePolicy();
		MultipartRequest req = 
				new MultipartRequest(request,path,1024*1024*100,"UTF-8",new MyRenameCos());
		
//		//第三步：显示信息，
		resp.setContentType("text/html;charset=UTf-8");
		PrintWriter out = resp.getWriter();
		
		out.print("文件名称1:"+req.getOriginalFileName("img1"));
		out.print("<br/>新名称:"+req.getFilesystemName("img1"));
		out.print("<br/>类型1:"+req.getContentType("img1"));
		out.print("<br/>大小1："+req.getFile("img1").length());
		out.print("<br/>说明:"+req.getParameter("desc1"));
		if(req.getContentType("img1").contains("image/")){
			out.print("<img src='"+request.getContextPath()+"/up/"+req.getFilesystemName("img1")+"'></img>");
		}
		
//		out.print("<hr/>");
//		out.print("文件名称2:"+req.getOriginalFileName("img2"));
//		out.print("<br/>类型2:"+req.getContentType("img2"));
//		out.print("<br/>大小2："+req.getFile("img2").length());
//		out.print("<br/>说明2:"+req.getParameter("desc2"));
//		
//		
//		out.print("<hr/>");
//		out.print("文件名称3:"+req.getOriginalFileName("img3"));
//		out.print("<br/>类型3:"+req.getContentType("img3"));
//		out.print("<br/>大小3："+req.getFile("img3").length());
//		out.print("<br/>说明3:"+req.getParameter("desc3"));
	}
	
	
	

	public void MultipartTestServlet3(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		MultipartParser mp = new MultipartParser(request, 2 * 1024 * 1024, false, false, "gbk");
		Part part;
		while ((part = mp.readNextPart()) != null) {
			String name = part.getName();
			if (part.isParam()) {
				ParamPart paramPart = (ParamPart) part;
				String value = paramPart.getStringValue();
				System.out.println("param: name=" + name + "; value=" + value);
			} else if (part.isFile()) {
				// it's a file part
				FilePart filePart = (FilePart) part;
				String fileName = filePart.getFileName();
				if (fileName != null) {
					long size = filePart.writeTo(new File("c:/tmp/"));
					System.out.println("file: name=" + name + "; fileName=" + fileName + ", filePath="
							+ filePart.getFilePath() + ", contentType=" + filePart.getContentType() + ", size=" + size);
				} else {
					System.out.println("file: name=" + name + "; EMPTY");
				}
				System.out.flush();
			}
		}
	}
	
	
	

	public void MultipartTestServlet2(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// request.setCharacterEncoding("gbk"); 不起作用
		System.out.println("start ");
		MultipartRequest multi = new MultipartRequest(request, "c:/tmp/", 2 * 1024 * 1024, "gbk",
				new DefaultFileRenamePolicy());
		System.out.println("start ");
		Enumeration filesName = multi.getFileNames();
		Enumeration paramsName = multi.getParameterNames();
		while (paramsName.hasMoreElements()) {
			String paramName = (String) paramsName.nextElement();
			System.out.println(multi.getParameter(paramName));
		}
		while (filesName.hasMoreElements()) {
			String fileName = (String) filesName.nextElement();
			System.out.println(multi.getFilesystemName(fileName) + "  " + multi.getOriginalFileName(fileName) + "  "
					+ multi.getContentType(fileName) + "  ");
			if (multi.getFilesystemName(fileName) != null && !multi.getFilesystemName(fileName).equals(""))
				System.out.println(multi.getFile(fileName).toURI());
		}
	}
	
	
	
	
	

	public void doPost51432(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		MultipartParser mp = new MultipartParser(request, 2 * 1024 * 1024, false, false, "gbk");
		Part part;
		while ((part = mp.readNextPart()) != null) {
			String name = part.getName();
			if (part.isParam()) {
				ParamPart paramPart = (ParamPart) part;
				String value = paramPart.getStringValue();
				System.out.println("param: name=" + name + "; value=" + value);
			} else if (part.isFile()) {
				// it's a file part
				FilePart filePart = (FilePart) part;
				String fileName = filePart.getFileName();
				if (fileName != null) {
					long size = filePart.writeTo(new File("c:/tmp/"));
					System.out.println("file: name=" + name + "; fileName=" + fileName + ", filePath="
							+ filePart.getFilePath() + ", contentType=" + filePart.getContentType() + ", size=" + size);
				} else {
					System.out.println("file: name=" + name + "; EMPTY");
				}
				System.out.flush();
			}
		}
	}
	public void MultipartTestServlet25432(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// request.setCharacterEncoding("gbk"); 不起作用
		System.out.println("start ");
		MultipartRequest multi = new MultipartRequest(request, "c:/tmp/", 2 * 1024 * 1024, "gbk",
				new DefaultFileRenamePolicy());
		System.out.println("start ");
		Enumeration filesName = multi.getFileNames();
		Enumeration paramsName = multi.getParameterNames();
		while (paramsName.hasMoreElements()) {
			String paramName = (String) paramsName.nextElement();
			System.out.println(multi.getParameter(paramName));
		}
		while (filesName.hasMoreElements()) {
			String fileName = (String) filesName.nextElement();
			System.out.println(multi.getFilesystemName(fileName) + "  " + multi.getOriginalFileName(fileName) + "  "
					+ multi.getContentType(fileName) + "  ");
			if (multi.getFilesystemName(fileName) != null && !multi.getFilesystemName(fileName).equals(""))
				System.out.println(multi.getFile(fileName).toURI());
		}
	}
	

    public void UploadServlet32143(HttpServletRequest request, HttpServletResponse response)  
            throws ServletException, IOException {  
        // 存绝对路径  
        // String filePath = "C://upload";  
        // 存相对路径  
        String filePath = request.getSession().getServletContext().getRealPath("/") + "upload";  
        System.out.println(filePath);//输出存放上传文件所到的路径  
        File uploadPath = new File(filePath);  
        // 检查文件夹是否存在 不存在 创建一个  
        if (!uploadPath.exists()) {  
            uploadPath.mkdir();  
        }  
        // 文件最大容量 3M  
        int fileMaxSize = 10 * 1024 * 1024;  
        // 存放文件描述  
        @SuppressWarnings("unused")  
        String[] fileDiscription = { null, null };  
        // 文件名  
        String fileName = null;  
        // 上传文件数  
        int fileCount = 0;  
        // 重命名策略  
//        RandomFileRenamePolicy rfrp = new RandomFileRenamePolicy();  
        // 上传文件  
        MultipartRequest mulit = new MultipartRequest(request, filePath,  
                fileMaxSize, "UTF-8", new FileRenameUtil());//取得上传文件  
  
        String userName = mulit.getParameter("userName");  
        System.out.println(userName);  
  
        Enumeration filesname = mulit.getFileNames();//取得上传的所有文件(相当于标识)   
        while (filesname.hasMoreElements()) {  
            String name = (String) filesname.nextElement();//标识  
            System.out.println(name);  
            fileName = mulit.getFilesystemName(name); //取得文件名  
            String contentType = mulit.getContentType(name);//工具标识取得的文件类型  
            if (fileName != null) {  
                fileCount++;  
            }  
            System.out.println("文件名：" + fileName);  
            System.out.println("文件类型： " + contentType);   
        }  
        System.out.println("共上传" + fileCount + "个文件！");   
    }  
	@SuppressWarnings("unchecked")
	public void UploadServlet11(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 存绝对路径
		// String filePath = "C://upload";
		// 存相对路径
		String filePath =request.getSession().getServletContext().getRealPath("/") + "upload";
		System.out.println(filePath);// 输出存放上传文件所到的路径
		File uploadPath = new File(filePath);
		// 检查文件夹是否存在 不存在 创建一个
		if (!uploadPath.exists()) {
			uploadPath.mkdir();
		}
		// 文件最大容量 3M
		int fileMaxSize = 10 * 1024 * 1024;
		// 存放文件描述
		@SuppressWarnings("unused")
		String[] fileDiscription = { null, null };
		// 文件名
		String fileName = null;
		// 上传文件数
		int fileCount = 0;
		// 重命名策略
		// RandomFileRenamePolicy rfrp = new RandomFileRenamePolicy();
		// 上传文件
		MultipartRequest mulit = new MultipartRequest(request, filePath, fileMaxSize, "UTF-8", new CosFileRenameUtil());// 取得上传文件

		String userName = mulit.getParameter("userName");
		System.out.println(userName);

		Enumeration filesname = mulit.getFileNames();// 取得上传的所有文件(相当于标识)
		while (filesname.hasMoreElements()) {
			String name = (String) filesname.nextElement();// 标识
			System.out.println(name);
			fileName = mulit.getFilesystemName(name); // 取得文件名
			String contentType = mulit.getContentType(name);// 工具标识取得的文件类型
			if (fileName != null) {
				fileCount++;
			}
			System.out.println("文件名：" + fileName);
			System.out.println("文件类型： " + contentType);
		}
		System.out.println("共上传" + fileCount + "个文件！");
	}
	
	
	
	
	
	
}
class MyRenameCos implements FileRenamePolicy{
	public File rename(File file) {
		String fileName = file.getName();
		String extName = fileName.substring(fileName.lastIndexOf("."));
		String uuid = UUID.randomUUID().toString().replace("-","");
		String newName = uuid+extName;//abc.jpg
		file = new File(file.getParent(),newName);
		return file;
	}
	
}

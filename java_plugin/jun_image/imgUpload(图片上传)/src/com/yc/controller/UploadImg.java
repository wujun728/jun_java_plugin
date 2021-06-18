package com.yc.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.yc.model.Menu;
import com.yc.utils.IOUtils;

/**
 * Servlet implementation class UploadImg
 */
@WebServlet("/UploadImg")
public class UploadImg extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UploadImg() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("-------");
		add(request,response);
	}

	private void add(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		Map<String,String> paramMap=new HashMap<String,String>();
		DiskFileItemFactory factory=new DiskFileItemFactory();
		factory.setSizeThreshold(1024*100);
		ServletFileUpload fileUpload=new ServletFileUpload(factory);
		fileUpload.setHeaderEncoding("UTF-8");
		fileUpload.setFileSizeMax(1024*1024*1);
		fileUpload.setSizeMax(1024*1024*100);
		if(!fileUpload.isMultipartContent(request)){
			new RuntimeException("表单提交错误");
		}
		List<FileItem> list=fileUpload.parseRequest(request);
		for(FileItem item:list){
			if(item.isFormField()){
				//普通字段
				String name=item.getFieldName();
				try {
					String value=item.getString("UTF-8");
					paramMap.put(name,value);
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}else{
				String realname=item.getFieldName();
				String fname=new SimpleDateFormat("yyyyMMddHHmmss").format(new Date())+"_"+realname;
				String upload=this.getServletContext().getRealPath("/upload");
				String imgurl="/upload/"+fname;
				paramMap.put("menuPic", imgurl);
				File uploadFile=new File(upload);
				try {
					InputStream in = item.getInputStream();
					OutputStream out = new FileOutputStream(new File(upload,fname));
					
					IOUtils.In2Out(in, out);
					IOUtils.close(in, out);
					
					item.delete();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		Menu m=new Menu();
		try {
			BeanUtils.populate(m, paramMap);
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(m);
		sc.add(m);
		
		
		
	}

}

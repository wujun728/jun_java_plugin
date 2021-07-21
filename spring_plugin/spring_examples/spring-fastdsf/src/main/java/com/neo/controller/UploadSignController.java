package com.neo.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.neo.fastdfs.FastDFSFile;
import com.neo.fastdfs.FileManager;

@Controller
public class UploadSignController {

	protected static Logger logger4J = Logger.getLogger(UploadSignController.class.getSimpleName());

	@ResponseBody
	@RequestMapping(value = "/uploadSign")
	public String uploadSign(HttpServletRequest request,HttpServletResponse response) throws Exception {
		String path="";
		try {
			File filePath = new File("temp");
			if(!filePath.exists()){
				filePath.mkdirs();
			}
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
			try {
				Set set = multipartRequest.getFileMap().entrySet();
				Iterator i = set.iterator();
				while(i.hasNext()) {
					Map.Entry me = (Map.Entry)i.next();
					String fileName = (String)me.getKey();
					MultipartFile multipartFile = (MultipartFile)me.getValue();
					InputStream inputStream = multipartFile.getInputStream();
					path=saveSign(inputStream);
					inputStream.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("出现异常"+e.getMessage());
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("出现异常"+e.getMessage());
		}
		System.out.println("path=="+path);
		return path;
	}
	

	public String saveSign(InputStream inputStream){
		String path="";
		String[] fileAbsolutePath={};
		try {
		byte[] file_buff = null;
		if(inputStream!=null){
			int len1 = inputStream.available();
			file_buff = new byte[len1];
			inputStream.read(file_buff);
		}
		inputStream.close();
		FastDFSFile file = new FastDFSFile("520", file_buff, "jpg");
		try {
			 fileAbsolutePath = FileManager.upload(file);  //上传到分布式文件系统
			System.out.println(fileAbsolutePath);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		if (fileAbsolutePath==null) {
			System.out.println("上传失败,图片未保存成功,请重新上传");
		}
		path="http://192.168.0.1:8080/"+fileAbsolutePath[0]+"/"+fileAbsolutePath[1];
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return path;
	}

	@ResponseBody
	@RequestMapping(value = "/hello")
	public String hello() {
		return "hi neo";
	}
}

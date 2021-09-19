package com.jun.plugin.webservlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;


@WebServlet(asyncSupported=true,name="upload", urlPatterns="/upload")
@MultipartConfig(fileSizeThreshold = 10000, maxFileSize = 1000000, maxRequestSize = 1000000, location="E:/logs")
public class MultiPartServlet3 extends HttpServlet {

	private static final long serialVersionUID = 7306582588845300635L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		Part part = req.getPart("file");
		String value = part.getHeader("content-disposition");
		System.out.println(value);
		String filename = value.substring(value.lastIndexOf("=") + 2,value.length() - 1);
		System.out.println(filename);
		System.out.println(part.getInputStream().toString());
	}

}

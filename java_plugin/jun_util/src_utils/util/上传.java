/**
 * Program  : UploadServlet.java
 * Author   : liangda
 * Create   : 2010-4-9 上午09:19:00
 *
 * Copyright 2009 by Embedded Internet Solutions Inc.,
 * All rights reserved.
 *
 * This software is the confidential and proprietary information
 * of Embedded Internet Solutions Inc.("Confidential Information").  
 * You shall not disclose such Confidential Information and shall 
 * use it only in accordance with the terms of the license agreement 
 * you entered into with Embedded Internet Solutions Inc.
 *
 */

package cn.ipanel.app.newspapers.web.servlet;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import cn.ipanel.app.newspapers.define.Defines;

/**
 * 
 * @author liangda
 * @version 1.0.0
 * @2010-4-9 上午09:19:00
 */
public class UploadServlet extends HttpServlet {

	private static final long serialVersionUID = 51944239803133660L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding(Defines.SERVLET_ENCODING);

		// 使用fileUpload组件将保存文件到服务器中
		DiskFileItemFactory diskFileItemFactory = new DiskFileItemFactory();
		// 设置上传组建的缓冲大小
		diskFileItemFactory.setSizeThreshold(4096);
		ServletFileUpload uploader = new ServletFileUpload(diskFileItemFactory);
		uploader.setSizeMax(Defines.MAX_UPLOAD_FILE_SIZE);

		try {
			@SuppressWarnings("unchecked")
			List<FileItem> fileItems = uploader.parseRequest(req);
			for (FileItem fileItem : fileItems) {
				if (!fileItem.isFormField()) {
					String fileName = fileItem.getName();
					File uploadFolder = new File(Defines.ABS_PATH_OF_PROJECT + Defines.FLEX_UPLOAD_TEMPDIR);
					if (!uploadFolder.exists()) {
						uploadFolder.mkdirs();
					}
					fileItem.write(new File(uploadFolder, fileName));
				}
			}
		} catch (FileUploadException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("Access!");
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		this.doPost(req, resp);
	}
}

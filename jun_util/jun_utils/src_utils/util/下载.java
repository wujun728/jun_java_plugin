/**
 * Program  : DownServlet.java
 * Author   : lihuan
 * Create   : 2010-10-19 下午02:26:12
 *
 * Copyright 2010 by Embedded Internet Solutions Inc.,
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

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.ipanel.app.newspapers.define.NewspaperException;
import cn.ipanel.app.newspapers.define.Defines;
import cn.ipanel.app.newspapers.util.FileUtil;
import cn.ipanel.app.newspapers.util.ZipUtil;

/**
 * 
 * @author lihuan
 * @version 1.0.0
 * @2010-10-19 下午02:26:12
 */
public class DownServlet extends HttpServlet {

	private static final long serialVersionUID = -8429092442180048726L;

	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		downloadModule(request, response);

	}

	private void downloadModule(HttpServletRequest request, HttpServletResponse response) {
		String downType = request.getParameter("downType");
		if (downType.equals("template")) {
			long templateId = Long.parseLong(request.getParameter("id"));
			// 模板存放路径
			String templateFileFolder = Defines.ABS_PATH_OF_PROJECT + Defines.TEMPLATE_FOLDER_PATH + templateId;
			String zipFilePath = Defines.ABS_PATH_OF_PROJECT + Defines.DOWM_FILE_PATH;

			String zipFileName = "template_" + templateId;

			// 压缩文件文件名，例如：template_1.zip
			File zipFile = ZipUtil.getCompressToZip(templateFileFolder, zipFilePath, zipFileName);

			// 下载压缩文件
			byte buf[] = FileUtil.readFileByte(zipFile);
			writeByteToFile(response, zipFileName + ".zip", buf);

			zipFile.delete();

		} else if (downType.equals("news")) {

			long columnId = Long.parseLong(request.getParameter("columnId"));
			long newsId = Long.parseLong(request.getParameter("newsId"));
			long pageClassifyId = Long.parseLong(request.getParameter("pageClassifyId"));
			long newspaperId = Long.parseLong(request.getParameter("newspaperId"));

			boolean isHdNews = Boolean.parseBoolean(request.getParameter("isHdNews"));

			// 找到应用文件所在的文件夹
			String newsFilesFolder = Defines.ABS_PATH_OF_PROJECT;
			if (isHdNews)
				newsFilesFolder = newsFilesFolder + Defines.NEWSPAPERS_FOLDER_HD;
			else
				newsFilesFolder = newsFilesFolder + Defines.NEWSPAPERS_FOLDER_BD;
			newsFilesFolder = newsFilesFolder + columnId + File.separator + newspaperId + File.separator + pageClassifyId + File.separator + newsId;
			String zipFilePath = Defines.ABS_PATH_OF_PROJECT + Defines.DOWM_FILE_PATH;

			String zipFileName = "news_" + newspaperId + "_" + newsId;

			// 压缩文件名"news_"+newspaperId + "_" + newsId + ".zip"如news_1_1.zip
			File zipFile = ZipUtil.getCompressToZip(newsFilesFolder, zipFilePath, zipFileName);

			// 下载压缩文件
			byte buf[] = FileUtil.readFileByte(zipFile);
			writeByteToFile(response, zipFileName + ".zip", buf);

			zipFile.delete();
		}
	}

	private void writeByteToFile(HttpServletResponse resp, String fileName, byte[] blobs) {
		ServletOutputStream sos = null;
		try {
			resp.setContentType("application/octet-stream;charset=gbk");
			resp.setHeader("Content-Disposition", "attachment;filename=" + new String(fileName.getBytes("GBK"), "ISO-8859-1"));
			sos = resp.getOutputStream();

			InputStream in = new ByteArrayInputStream(blobs);
			byte[] b = new byte[1024];
			int length = 0;
			while ((length = in.read(b)) != -1) {
				sos.write(b, 0, length);
			}
		} catch (Exception e) {
			throw new NewspaperException("下载新闻出错！");
		} finally {
			if (sos != null) {
				try {
					sos.flush();
					sos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

}

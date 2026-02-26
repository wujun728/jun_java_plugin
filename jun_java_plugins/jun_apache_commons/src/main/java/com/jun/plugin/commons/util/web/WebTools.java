// This file is commented out — uses POI + JExcelApi (jxl) which are now removed.
// For POI-based Excel export, see jun_poi module.
/*
package com.jun.plugin.commons.util.web;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.format.Colour;
import jxl.format.UnderlineStyle;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang.ArrayUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jun.plugin.commons.util.Conf;
import com.jun.plugin.commons.util.exception.ExceptAll;
import com.jun.plugin.commons.util.exception.ProjectException;

public abstract class WebTools {
	private static Logger logger = LoggerFactory.getLogger(WebTools.class);
	public final static File tempDir=new File(Conf.utilProperties.getProperty("file.tempDir"));

	public static void returnExcelStreamResponse(HttpServletResponse response,
			Workbook workbook, String fileName) throws ProjectException {
		ByteArrayOutputStream file = new ByteArrayOutputStream();
		try {
			workbook.write(file);
			final byte[] dataFile = file.toByteArray();
			response.setHeader("Content-Disposition", "attachment; filename=\""
					+ new String(fileName.getBytes("GBK"), "iso8859-1")
					+ ".xls\"");
			response.setContentLength(dataFile.length);
			ServletOutputStream resStream = response.getOutputStream();
			resStream.write(dataFile);
			resStream.close();
		} catch (Exception e) {
			throw new ProjectException(ExceptAll.default_Project,
					"返回excle输出流错误");
		}
	}

	public static OperateResult exportExcel(HttpServletResponse response,
			String context, String... exshow) {
		OperateResult retObj = new OperateResult(0);
		try {
			String fileName=ArrayUtils.isEmpty(exshow)?"fileName":exshow[0];
			String sheetTitle = ArrayUtils.isEmpty(exshow)||exshow.length<2?"sheet1":exshow[1];
			OutputStream os = response.getOutputStream();
			response.reset();
			response.setHeader("Content-disposition", "attachment; filename="
					+ fileName + ".xls");
			response.setContentType("application/msexcel");
			WritableWorkbook wbook = jxl.Workbook.createWorkbook(os);
			WritableSheet wsheet = wbook.createSheet(sheetTitle, 0);
			WritableFont wfont = new WritableFont(WritableFont.ARIAL, 16,
					WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE,
					Colour.BLACK);
			WritableCellFormat wcfFC = new WritableCellFormat(wfont);
			wcfFC.setBackground(Colour.AQUA);
			wsheet.addCell(new Label(1, 0, sheetTitle, wcfFC));
			wfont = new jxl.write.WritableFont(WritableFont.ARIAL, 14,
					WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE,
					Colour.BLACK);
			wcfFC = new WritableCellFormat(wfont);
			String[] rows = context.split("\r\n");
			for (int i = 0; i < rows.length; i++) {
				String rowString = rows[i];
				String[] rowAry = rowString.split(",");
				for (int j = 0; j < rowAry.length; j++) {
					wsheet.addCell(new Label(j, i, rowAry[j]));
				}
			}
			wbook.write();
			wbook.close();
			os.close();
			retObj.setResult(1);
			retObj.setMessage("success");
		} catch (Exception e) {
			retObj.setMessage(e.getMessage());
		}
		return retObj;
	}

	public static void returnJsonResponse(HttpServletResponse response,
			String jsonMsg) {
		response.setContentType("text/html");
		try {
			PrintWriter out = response.getWriter();
			out.print(jsonMsg);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void returnJsonResponse(HttpServletResponse response,
			OperateResult operateResult) {
		returnJsonResponse(response, operateResult.getJsonMsg(null));
	}

	public static Map<String, String> paseReqestParam(HttpServletRequest request) {
		Map<String, String> retMap = new HashMap<String, String>();
		boolean isMultipart = ServletFileUpload.isMultipartContent(request);
		if (isMultipart) {
			DiskFileItemFactory factory = new DiskFileItemFactory();
			ServletFileUpload upload = new ServletFileUpload(factory);
			upload.setSizeMax(Long.parseLong(Conf.utilProperties.getProperty("file.sizeMax")));
			factory.setSizeThreshold(Integer.parseInt(Conf.utilProperties.getProperty("file.sizeThreshold")));
			factory.setRepository(tempDir);
			try {
				List<FileItem> items = upload.parseRequest(request);
				for (FileItem fileItem : items) {
					String name = fileItem.getFieldName();
					String value = fileItem.getString(Conf.utilProperties.getProperty("connect.encode"));
					retMap.put(name, value);
				}
			} catch (Exception e) {
				logger.error("解析文件类型的参数错误",e);
			}
		} else {
			Map<String, String[]> requestParam = request.getParameterMap();
			for (String key : requestParam.keySet()) {
				retMap.put(key, ArrayUtils.toString(requestParam.get(key)));
			}
		}
		return retMap;
	}

}
*/

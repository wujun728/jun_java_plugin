package com.kvn.poi.exp;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.expression.spel.standard.SpelExpressionParser;

import com.kvn.poi.exception.PoiElErrorCode;
import com.kvn.poi.exp.context.PoiExporterContext;
import com.kvn.poi.exp.function.FunctionRegister;
import com.kvn.poi.exp.processor.RowProcessorStrategy;
import com.kvn.poi.log.Log;

/**
 * excel导出类
 * @author wzy
 * @date 2017年7月5日 上午9:41:52
 */
public class PoiExporter {
	private static final Logger logger = LoggerFactory.getLogger(PoiExporter.class);
	
	/**
	 * 向StandardEvaluationContext中注册内部函数
	 */
	static {
		FunctionRegister.registerInternalFunction();
	}
	
	public static void export(XSSFWorkbook wb, Map<String, Object> rootObjectMap) {
		Long start = System.currentTimeMillis();
		
		PoiExporterContext peContext = new PoiExporterContext(new SpelExpressionParser(), rootObjectMap);
		
		// 分sheet进行处理
		for (int i = 0; i < wb.getNumberOfSheets(); i++) {
			XSSFSheet sheet = wb.getSheetAt(i);
			// 开始行结束行
			int j = sheet.getFirstRowNum();
			// 每行
			while (j <= sheet.getLastRowNum()) {
				XSSFRow row = sheet.getRow(j);
				if (row == null) {
					continue;
				}
				int dealRows = RowProcessorStrategy.getRowProcessor(row).dealRow(row, peContext);
				j = j + dealRows;
			}
		}
		
		long end = System.currentTimeMillis();
		
		logger.info(Log.op("PoiEl#parse").msg("PoiEl解析模板耗时[{0}]ms", (end - start)).toString());
	}
	
	/**
	 * 导出到指定地方 des
	 * @param templateFile
	 * @param rootObjectMap
	 * @param des
	 * @return
	 */
	public static XSSFWorkbook export2Destination(File templateFile, Map<String, Object> rootObjectMap, OutputStream des){
		InputStream in = null;
		try {
			in = new FileInputStream(templateFile);
		} catch (FileNotFoundException e) {
			throw PoiElErrorCode.TEMPLATE_FILE_NOT_FOUND.exp(e, templateFile.getName());
		}
		return export2Destination(in, rootObjectMap, des);
	}
	
	/**
	 * 导出到指定地方 des
	 * @param templateInputStream 模板
	 * @param rootObjectMap 数据
	 * @param des 导出的位置
	 * @return
	 */
	public static XSSFWorkbook export2Destination(InputStream templateInputStream, Map<String, Object> rootObjectMap, OutputStream des){
		XSSFWorkbook wb = null;
		try {
			wb = new XSSFWorkbook(templateInputStream);
		} catch (IOException e) {
			throw PoiElErrorCode.SYSTEM_ERROR.exp(e);
		}
		PoiExporter.export(wb, rootObjectMap);
		
		// 关闭资源
		try {
			wb.write(des);
			des.flush();
			des.close();
		} catch (IOException e) {
			throw PoiElErrorCode.SYSTEM_ERROR.exp(e);
		}
		
		return wb;
	}
}

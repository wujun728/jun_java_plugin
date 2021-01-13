package com.kvn.poi.exp.processor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.expression.EvaluationException;
import org.springframework.expression.ParseException;
import org.springframework.expression.common.TemplateParserContext;

import com.kvn.poi.common.Constants;
import com.kvn.poi.exp.context.PoiExporterContext;
import com.kvn.poi.log.Log;

/**
 * 默认的行处理器。单元格内容中含有${key}的进行替换，否则不处理
 * 
 * @author wzy
 */
public class DefaultRowProcessor implements RowProcessor {
	private static final Logger logger = LoggerFactory.getLogger(DefaultRowProcessor.class);

	public static class SINGLE {
		public static DefaultRowProcessor INSTANCE = new DefaultRowProcessor();
	}

	@Override
	public int support(XSSFRow row) {
		return 0;
	}

	@Override
	public int dealRow(XSSFRow currentRow, PoiExporterContext peContext) {
		for (int i = 0; i < currentRow.getLastCellNum(); i++) {
			XSSFCell cell = currentRow.getCell(i);
			if (null != cell && cell.getCellType() == XSSFCell.CELL_TYPE_STRING) {
				String cellContent = cell.getStringCellValue();
				String resolvedContent = resolve(cellContent, peContext);
				cell.setCellValue(resolvedContent);
			}

		}
		return 1;
	}

	/**
	 * 将带有${key}和${vo.key}的内容进行替换
	 * 
	 * @param cellContent
	 * @param rootObjectMap
	 * @param parser
	 * @return
	 */
	public static String resolve(String cellContent, PoiExporterContext peContext) {
		String resolvedContent = cellContent;
		Pattern pattern = Pattern.compile(Constants.POI_KEY_REGEXP);
		// 处理${key}
		for (Matcher matcher = pattern.matcher(resolvedContent); matcher.find(); matcher = pattern.matcher(resolvedContent)) {
			String key = matcher.group(1);
			String value = (String) peContext.getRootObjectMap().get(key);
			resolvedContent = matcher.replaceFirst(value);
		}

		// 处理${vo.key}
		pattern = Pattern.compile(Constants.POI_VO_DOT_KEY_REGEXP);
		try {
			for (Matcher matcher = pattern.matcher(resolvedContent); matcher.find(); matcher = pattern.matcher(resolvedContent)) {
				String vo = matcher.group(2);
				vo = vo.substring(0, vo.length()-1);
				Object rootObjectItem = peContext.getRootObjectMap().get(vo);
				String expression = matcher.replaceFirst("#$1$3"); // 转换成EL
				String resolvedKey = peContext.getSpelExpParser().parseExpression(expression, new TemplateParserContext()).getValue(peContext.EVAL_CONTEXT, rootObjectItem, String.class);
				resolvedContent = resolvedKey; // 替换内容
			}
		} catch (EvaluationException | ParseException e) {
			logger.error(Log.op("DefaultRowProcessor#resolve").msg("EL解析出错").toString(), e);
		}
		return resolvedContent;
	}

}

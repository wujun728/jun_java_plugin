package com.jun.plugin.poi.test.excel;

import java.io.File;
import java.lang.reflect.Type;
import java.net.URISyntaxException;
import java.net.URL;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import com.google.common.base.MoreObjects;
import com.jun.plugin.poi.test.excel.annotation.BingConvertor;
import com.jun.plugin.poi.test.excel.annotation.CellConfig;
import com.jun.plugin.poi.test.excel.converter.AbstractFieldConvertor;
import com.jun.plugin.poi.test.excel.converter.FieldValueConverter;
import com.jun.plugin.poi.test.excel.converter.base.BooleanFieldConverter;
import com.jun.plugin.poi.test.excel.core.BingExcel;
import com.jun.plugin.poi.test.excel.core.BingExcelBuilder;
import com.jun.plugin.poi.test.excel.core.BingExcelEvent;
import com.jun.plugin.poi.test.excel.core.BingExcelEventBuilder;
import com.jun.plugin.poi.test.excel.core.BingReadListener;
import com.jun.plugin.poi.test.excel.core.handler.ConverterHandler;
import com.jun.plugin.poi.test.excel.core.impl.BingExcelEventImpl.ModelInfo;
import com.jun.plugin.poi.test.excel.core.impl.BingExcelImpl.SheetVo;
import com.jun.plugin.poi.test.excel.vo.OutValue;
import com.jun.plugin.poi.test.utils.StringParseUtil;

/**
 * @author Wujun
 *
 * @date 2016-3-23
 * Description:  
 */
public class ReadTestEventModel5 {

	@Test
	public void readExcelTest() throws URISyntaxException {
		// InputStream in = Person.class.getResourceAsStream("/person.xlsx");
		URL url = Salary.class.getResource("/salaryEvent.xlsx");
		File f = new File(url.toURI());
		//event模式读取数据
		BingExcelEvent bing = BingExcelEventBuilder.toBuilder().builder();
		try {
			long st = System.currentTimeMillis();
			 bing.readFile(f, Salary.class, 1,new tempListener());
			 System.out.println((System.currentTimeMillis()-st)/1000);
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	enum Department {
		develop, personnel, product;
	}

	public static class Salary {

		@CellConfig(index = 0)
		private String employID;

		
		
		@CellConfig(index = 13)
		@BingConvertor(DateTestConverter.class)
		// 自定义转换器
		private Date atypiaDate;
		@CellConfig(index = 15)
		@BingConvertor(DateTestConverter.class)
		// 自定义转换器
		private Date entryTime;

		

		public String toString() {
			return MoreObjects.toStringHelper(this.getClass()).omitNullValues()
					.add("employID", employID)
					.add("atypiaDate", atypiaDate)
					.add("entryTime", entryTime).toString();
		}
	}

	public static class DateTestConverter  extends AbstractFieldConvertor {

		

		@Override
		public boolean canConvert(Class<?> clz) {
			return clz.equals(Date.class);
		}

		@Override
		public Object fromString(String cell, ConverterHandler converterHandler,Type type) {

			if (StringUtils.isBlank(cell)) {
				return null;
			}
			try {
				return StringParseUtil.convertYMDT2Date(cell);
			} catch (ParseException e) {

				throw new RuntimeException(e);
			}
		}

		

	}
	
	public static class tempListener implements BingReadListener{

		@Override
		public void readModel(Object object, ModelInfo modelInfo) {
			
			System.out.println(modelInfo+":"+object);
		}
		
	}
}

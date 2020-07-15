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
import com.jun.plugin.poi.test.excel.ReadTestGlobalConverter6.EmploryAttribute;
import com.jun.plugin.poi.test.excel.annotation.BingConvertor;
import com.jun.plugin.poi.test.excel.annotation.CellConfig;
import com.jun.plugin.poi.test.excel.annotation.OutAlias;
import com.jun.plugin.poi.test.excel.converter.AbstractFieldConvertor;
import com.jun.plugin.poi.test.excel.converter.FieldValueConverter;
import com.jun.plugin.poi.test.excel.converter.base.BooleanFieldConverter;
import com.jun.plugin.poi.test.excel.core.BingExcel;
import com.jun.plugin.poi.test.excel.core.BingExcelBuilder;
import com.jun.plugin.poi.test.excel.core.ReaderCondition;
import com.jun.plugin.poi.test.excel.core.handler.ConverterHandler;
import com.jun.plugin.poi.test.excel.core.impl.BingExcelImpl.SheetVo;
import com.jun.plugin.poi.test.utils.StringParseUtil;

/**
 * @author shizhongtao
 * 
 * @date 2016-3-23 Description:
 */
public class ReadTest7 {

	@Test
	public void readExcelTest() throws URISyntaxException {
		// InputStream in = Person.class.getResourceAsStream("/person.xlsx");
		URL url = Salary.class.getResource("/salary7.xlsx");
		File f = new File(url.toURI());

		BingExcel bing = BingExcelBuilder.toBuilder().registerFieldConverter(Date.class, new MyDateConverter()).builder();
		try {
			ReaderCondition<Salary> condition = new ReaderCondition<>(0, 1,
					Salary.class);
			SheetVo vo = bing.readFile(f, condition);
			List objectList = vo.getObjectList();
			for (Object object : objectList) {
				System.out.println(object);
				for (Date item : ((Salary)object).workingTime) {
					System.out.println(item);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@OutAlias("hghg")
	public static class Salary {

		@CellConfig(index = 1)
		private String employNum;
		@CellConfig(index = 10)
		private Date[] workingTime;
		@CellConfig(index = 11)
		private String[] team;

		public String toString() {
			return MoreObjects.toStringHelper(this.getClass()).omitNullValues()
					.add("employNum", employNum)
					.add("workingTime", workingTime).add("team", team)
					.toString();
		}
	}
	public static class MyDateConverter extends AbstractFieldConvertor{

		@Override
		public boolean canConvert(Class<?> clz) {
			
			return Date.class.isAssignableFrom(clz);
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
}

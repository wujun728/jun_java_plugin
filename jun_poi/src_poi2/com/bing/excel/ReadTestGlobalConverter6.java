package com.bing.excel;

import java.io.File;
import java.lang.reflect.Type;
import java.net.URISyntaxException;
import java.net.URL;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import com.bing.common.Builder;
import com.bing.excel.annotation.BingConvertor;
import com.bing.excel.annotation.CellConfig;
import com.bing.excel.converter.AbstractFieldConvertor;
import com.bing.excel.converter.FieldValueConverter;
import com.bing.excel.converter.base.BooleanFieldConverter;
import com.bing.excel.core.BingExcel;
import com.bing.excel.core.BingExcelBuilder;
import com.bing.excel.core.ReaderCondition;
import com.bing.excel.core.handler.ConverterHandler;
import com.bing.excel.core.impl.BingExcelImpl.SheetVo;
import com.bing.excel.reader.ExcelReadListener;
import com.bing.excel.reader.ExcelReaderFactory;
import com.bing.excel.reader.ReadHandler;
import com.bing.excel.vo.ListRow;
import com.bing.utils.StringParseUtil;
import com.google.common.base.MoreObjects;

/**
 * @author shizhongtao
 *
 * @date 2016-3-23
 * Description:  
 */
public class ReadTestGlobalConverter6 {
	

	@Test
	public void readExcelTest() throws URISyntaxException {
		// InputStream in = Person.class.getResourceAsStream("/person.xlsx");
		URL url = Salary.class.getResource("/salary6.xlsx");
		File f = new File(url.toURI());

		 BingExcel bing = BingExcelBuilder.toBuilder().registerFieldConverter(EmploryAttribute.class, new MyDateConverter()).builder();
		 ReaderCondition<Salary> condition=new ReaderCondition<>(1,Salary.class);
		 condition.setStartRow(2);
		try {
			SheetVo<Salary> vo = bing.readFile(f, condition);
			System.out.println(vo.getSheetIndex());
			System.out.println(vo.getSheetName());
			List<Salary> objectList = vo.getObjectList();
			for (Salary salary : objectList) {
				System.out.println(salary);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static class Salary {

		@CellConfig(index = 1)
		private String employNum;

		@CellConfig(index = 0)
		private String id;

		@CellConfig(index = 3)
		private EmploryAttribute attribute;// 枚举类型

		public String toString() {
			return MoreObjects.toStringHelper(this.getClass()).omitNullValues()
					.add("id", id).add("employNum", employNum)
					.add("attribute", attribute).toString();
		}
	}

	public static class EmploryAttribute {
		private String key;
		private String value;

		public String toString() {
			return MoreObjects.toStringHelper(getClass()).add("key", key)
					.add("value", value).toString();
		}

	}
	
	public static class MyDateConverter extends AbstractFieldConvertor{

		@Override
		public boolean canConvert(Class<?> clz) {
			
			return EmploryAttribute.class.equals(clz);
		}

		@Override
		public Object fromString(String cell, ConverterHandler converterHandler,Type type) {
			if(StringUtils.isBlank(cell)){
				return null;
			}else{
				String[] split = cell.split(":");
				EmploryAttribute attribute=new EmploryAttribute();
				attribute.key=split[0];
				attribute.value=split[1];
				return attribute;
			}
		}
		
	}
}

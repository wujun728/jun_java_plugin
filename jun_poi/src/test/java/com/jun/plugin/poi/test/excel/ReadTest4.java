package com.jun.plugin.poi.test.excel;

import java.io.File;
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
import com.jun.plugin.poi.test.excel.annotation.OutAlias;
import com.jun.plugin.poi.test.excel.converter.FieldValueConverter;
import com.jun.plugin.poi.test.excel.converter.base.BooleanFieldConverter;
import com.jun.plugin.poi.test.excel.core.BingExcel;
import com.jun.plugin.poi.test.excel.core.BingExcelBuilder;
import com.jun.plugin.poi.test.excel.core.ReaderCondition;
import com.jun.plugin.poi.test.excel.core.impl.BingExcelImpl.SheetVo;
import com.jun.plugin.poi.test.utils.StringParseUtil;

/**
 * @author Wujun
 *
 * @date 2016-3-23
 * Description:  
 */
public class ReadTest4 {

	@Test
	public void readExcelTest() throws URISyntaxException {
		// InputStream in = Person.class.getResourceAsStream("/person.xlsx");
		URL url = Salary.class.getResource("/salary4.xlsx");
		File f = new File(url.toURI());

		BingExcel bing = BingExcelBuilder.toBuilder().builder();
		try {
			ReaderCondition<Salary> condition1=new ReaderCondition<>(0,1,Salary.class);
			ReaderCondition<Person> condition2=new ReaderCondition<>(1,1,Person.class);
			ReaderCondition[] arr=new ReaderCondition[]{condition1,condition2};
			List<SheetVo> list = bing.readFileToList(f, arr);
			for (SheetVo sheetVo : list) {
				System.out.println(sheetVo.getSheetIndex()+":"+sheetVo.getSheetName());
				List list2 = sheetVo.getObjectList();
				for (Object object : list2) {
					System.out.println(object);
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	enum Department {
		develop, personnel, product;
	}
	public static class Salary {

		@CellConfig(index = 1)
		private String employNum;

		@CellConfig(index = 0)
		private String id;

		@CellConfig(index = 7)
		private Department department;// 枚举类型


		public String toString() {
			return MoreObjects.toStringHelper(this.getClass()).omitNullValues()
					.add("id", id).add("employNum", employNum)
					.add("department", department)
					.toString();
		}
	}

	public static class Person {
		@CellConfig(index = 0)
		private String id;
		@CellConfig(index = 2)
		private String name;
		@CellConfig(index = 1)
		private String num;

		public String toString() {
			return MoreObjects.toStringHelper(getClass()).add("name", name)
					.add("id", id).add("num", num).toString();
		}
	}

}

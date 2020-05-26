package com.bing.excel;

import java.beans.ConstructorProperties;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.net.URISyntaxException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import com.bing.excel.annotation.BingConvertor;
import com.bing.excel.annotation.CellConfig;
import com.bing.excel.converter.AbstractFieldConvertor;
import com.bing.excel.converter.FieldValueConverter;
import com.bing.excel.core.BingExcel;
import com.bing.excel.core.BingExcelBuilder;
import com.bing.excel.core.handler.ConverterHandler;
import com.bing.excel.core.impl.BingExcelImpl.SheetVo;
import com.bing.excel.vo.OutValue;
import com.bing.utils.StringParseUtil;
import com.google.common.base.MoreObjects;

public class ReadTest2 {

	@Test
	public void readExcelTest() throws URISyntaxException {
		// InputStream in = Person.class.getResourceAsStream("/person.xlsx");
		URL url = Salary.class.getResource("/salary.xlsx");
		File f = new File(url.toURI());

		BingExcel bing = BingExcelBuilder.toBuilder().builder();
		try {
			SheetVo<Salary> vo = bing.readFile(f, Salary.class, 1);
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
		
		@CellConfig(index = 12)
		private Double salary;
		
		@CellConfig(index = 11)
		private double trueSalary;
		
		@CellConfig(index = 14)
		private Date trueDate;
		
		@CellConfig(index = 13)
		@BingConvertor(DateTestConverter.class)//自定义转换器
		private Date atypiaDate;
		@CellConfig(index = 15)
		@BingConvertor(DateTestConverter.class)//自定义转换器
		private Date entryTime;
		
		
		
		// 其他不对应的属性可以这样定义
		private transient String test;

		public String toString() {
			return MoreObjects.toStringHelper(this.getClass()).omitNullValues()
					.add("id", id).add("employNum", employNum)
					.add("salary", salary).add("trueSalary", trueSalary)
					.add("trueDate", trueDate)
					 .add("atypiaDate", atypiaDate)
					 .add("entryTime", entryTime)
					.toString();
		}
	}

	public static class DateTestConverter extends AbstractFieldConvertor {


		


	

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
}

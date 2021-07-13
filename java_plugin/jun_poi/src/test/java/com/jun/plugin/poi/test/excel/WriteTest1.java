package com.jun.plugin.poi.test.excel;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.google.common.base.MoreObjects;
import com.google.common.collect.Lists;
import com.jun.plugin.poi.test.excel.annotation.CellConfig;
import com.jun.plugin.poi.test.excel.annotation.OutAlias;
import com.jun.plugin.poi.test.excel.core.BingExcel;
import com.jun.plugin.poi.test.excel.core.BingExcelBuilder;

public class WriteTest1 {
	BingExcel bing;
	@Before
	public void before(){
		bing = BingExcelBuilder.toBuilder().builder();
	}
	@Test
	public void testWrite() {
		List<Person> list = Lists.newArrayList();
		list.add(new Person(12, "nihoa", 23434.9));
		list.add(new Person(23, "nihoa", 234.9));
		list.add(new Person(122, "nihoa", 23434.9));
		list.add(new Person(12, "nihoa", 23434.9));

		
		bing.writeExcel("E:/test/adb.xlsx", list,list);
	}

	@OutAlias("xiaoshou")
	public static class Person {

		public Person(int age, String name, Double salary) {
			super();
			this.age = age;
			this.name = name;
			this.salary = salary;
		}

		public Person() {
			super();
		}

		@CellConfig(index = 1, aliasName = "年龄")
		private int age;
		@CellConfig(index = 0)
		private String name;
		@CellConfig(index = 3)
		private Double salary;
		private transient boolean testProperty = false;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public int getAge() {
			return age;
		}

		public Double getSalary() {
			return salary;
		}

		public String toString() {
			return MoreObjects.toStringHelper(this.getClass()).omitNullValues().add("name", name).add("age", age)
					.add("salary", salary).toString();
		}
	}
}

package com.jun.plugin.poi.test.excel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;

import com.google.common.base.MoreObjects;
import com.google.common.collect.Lists;
import com.jun.plugin.poi.test.excel.annotation.CellConfig;
import com.jun.plugin.poi.test.excel.annotation.OutAlias;
import com.jun.plugin.poi.test.excel.core.BingExcel;
import com.jun.plugin.poi.test.excel.core.BingExcelBuilder;

public class WriteTest2 {
	BingExcel bing;
	List<Person> list = new ArrayList<>();

	@Before
	public void before() {
		bing = BingExcelBuilder.toBuilder().builder();
		for (int i = 0; i < 10000; i++) {
			Person p = new Person((int) (Math.random() * 100),
					RandomStringUtils.randomAlphanumeric(4),
					Math.random() * 1000);
			list.add(p);
		}
	}

	@Test
	public void testWrite() {

		WriteThread thread1 = new WriteThread(bing, list);
		WriteThread thread2 = new WriteThread(bing, list);
		WriteThread thread3 = new WriteThread(bing, list);
		WriteThread thread4 = new WriteThread(bing, list);
		WriteThread thread5 = new WriteThread(bing, list);

		thread1.start();
		thread2.start();
		thread3.start();
		thread4.start();
		thread5.start();
		try {
			Thread.sleep(30000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static class WriteThread extends Thread {
		BingExcel bing;
		List<?> list;

		public WriteThread(BingExcel bing, List<?> list) {
			this.bing = bing;
			this.list = list;
		}

		@Override
		public void run() {

			if (Math.random() > 0.4) {
				bing.writeExcel("E:/test/adb"
						+ Thread.currentThread().getName() + ".xlsx", list,
						list);
			} else {
				// bing.writeExcel("E:/test/adb"+Thread.currentThread().getName()+".xlsx",
				// list);
				bing.writeOldExcel("E:/test/adb"
						+ Thread.currentThread().getName() + ".xls", list);
			}
			System.out.println("end:" + Thread.currentThread().getName());
		}

	}

	@OutAlias("xiaoshou")
	public static class Person {

		public Person(int age, String name, Double salary) {
			super();
			this.age = age;
			this.name = name;
			this.salary = salary;
			this.date = new Date();
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

		@CellConfig(index = 2, aliasName = "日期")
		private Date date;
		private transient boolean testProperty = false;
		private transient String test = "test";

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
			return MoreObjects.toStringHelper(this.getClass()).omitNullValues()
					.add("name", name).add("age", age).add("salary", salary)
					.toString();
		}
	}
}

package com.jun.plugin.poi.test.other;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.Test;

import com.google.common.base.MoreObjects;
import com.jun.plugin.poi.test.excel.annotation.CellConfig;
import com.jun.plugin.poi.test.excel.core.BingExcelEvent;
import com.jun.plugin.poi.test.excel.core.BingExcelEventBuilder;
import com.jun.plugin.poi.test.excel.core.BingReadListener;
import com.jun.plugin.poi.test.excel.core.impl.BingExcelEventImpl.ModelInfo;
import com.jun.plugin.poi.test.excel.reader.ExcelReadListener;
import com.jun.plugin.poi.test.excel.reader.ExcelReaderFactory;
import com.jun.plugin.poi.test.excel.reader.ReadHandler;
import com.jun.plugin.poi.test.excel.vo.ListRow;

public class ReadTestThreadLocal {

	@Test
	public void readExcelTest() {
		System.out.println("start: " + System.currentTimeMillis());
		new ThreadTest().start();
		new ThreadTest().start();
		new ThreadTest().start();
		new ThreadTest().start();
		new ThreadTest().start();

		try {
			Thread.sleep(25000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static class ThreadTest extends Thread {

		@Override
		public void run() {
			/*BingExcelEvent builder = BingExcelEventBuilder.toBuilder()
					.builder();
			try {
				File f = new File("E:/test/bc.xlsx");

				builder.readFile(f, Person.class, 1, new BingReadListener() {
					

					@Override
					public void readModel(Object object, ModelInfo modelInfo) {

						
					}
				});
				System.out.println(System.currentTimeMillis());
			} catch (Exception e) {
				e.printStackTrace();
			}*/
			File f = new File("E:/test/bc.xlsx");
			try {
				ReadHandler handler = ExcelReaderFactory.create(f, new ExcelReadListener() {
					
					@Override
					public void startSheet(int sheetIndex, String name) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void optRow(int curRow, ListRow rowList) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void endWorkBook() {
						// TODO Auto-generated method stub
						System.out.println(System.currentTimeMillis());
					}
					
					@Override
					public void endSheet(int sheetIndex, String name) {
						// TODO Auto-generated method stub
						
					}
				});
				handler.readSheets();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	public static class Person {

		@CellConfig(index = 0)
		private Date date;

		public Date getDate() {
			return date;
		}

		public void setDate(Date date) {
			this.date = date;
		}

		public String toString() {
			return MoreObjects.toStringHelper(this.getClass()).omitNullValues()
					.add("date", date).toString();
		}
	}
}

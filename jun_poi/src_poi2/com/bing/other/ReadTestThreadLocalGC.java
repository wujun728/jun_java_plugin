package com.bing.other;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.Test;
import org.junit.internal.runners.statements.RunAfters;

import com.bing.excel.annotation.CellConfig;
import com.bing.excel.core.BingExcelEvent;
import com.bing.excel.core.BingExcelEventBuilder;
import com.bing.excel.core.BingReadListener;
import com.bing.excel.core.impl.BingExcelEventImpl.ModelInfo;
import com.google.common.base.MoreObjects;

public class ReadTestThreadLocalGC {
	

	@Test
	public void readExcelTest() throws InterruptedException {

		Handler h=new Handler();
		h.printSome();
		//注意此处
		h.local=null;
		Thread.sleep(1000);
		System.out.println("gc1");
		System.gc();
		//System.out.println(h.local.get());
		h=new Handler();
		h.printSome();
		
		Thread.sleep(1000);
		System.out.println("gc2");
		System.gc();
		;
	}

	

	@Test
	public void readExcelTest2() throws InterruptedException {

		//System.out.println(Handler.local);
		Thread t1= new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Handler h=new Handler();
				h.printSome();
				//h.local=null;
				System.out.println(h.local);
			}
		});
		Thread t2= new Thread(new Runnable() {
			 
			 @Override
			 public void run() {
				 try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				 Handler h=new Handler();
				 h.printSome();
				 //h.local=null;
				 System.out.println(h.local);
			 }
		 });
		Thread t3= new Thread(new Runnable() {
			 
			 @Override
			 public void run() {
				 try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				 Handler h=new Handler();
				 h.printSome();
				 //h.local=null;
				 System.out.println(h.local);
			 }
		 });
		t1.start();
		t2.start();
		t3.start();
		 System.gc();
		System.out.println("gc1");
		System.gc();
		Thread.sleep(3000);
		System.out.println("gc2");
		System.gc();
		Thread.sleep(1000);
		System.gc();
		//System.out.println(Handler.local);
	
	}

	public static class Handler{
		 static ThreadMyLocal<Person> local;

		private Person getPerson() {
			if(local==null){
				local=new ThreadMyLocal<>();
			}
			if(local.get()==null){
				Person person = new Person();
				person.setAge(((Double)(Math.random()*100)).intValue());
				local.set(person);
			}
			return local.get();
		}
		
		
		public void printSome(){
			
			Person person = getPerson();
			System.out.println(person.getAge()+":Person对象age属性");
		}


		@Override
		protected void finalize() throws Throwable {
			System.out.println("Handler GC");
			super.finalize();
		}
		
	}
	
	
	
	
	public static class ThreadMyLocal<T> extends ThreadLocal<T> {

		
		
		@Override
		protected void finalize() throws Throwable {
			System.out.println("threadLocal  gc:");
			super.finalize();
		}

	}

	public static class Person {

		private int age;

		public int getAge() {
			return age;
		}
		public void setAge(int age) {
			this.age = age;
		}

		@Override
		protected void finalize() {
			System.out.println("Person  gc");
		}

	}
}

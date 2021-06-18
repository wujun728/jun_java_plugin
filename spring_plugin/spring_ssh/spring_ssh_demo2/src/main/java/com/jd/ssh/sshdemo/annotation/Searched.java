package com.jd.ssh.sshdemo.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD) 
@Retention(RetentionPolicy.RUNTIME) 
@Documented 
public @interface Searched {
	SearchedEnum type(); 
	
	
	public static enum SearchedEnum {
		/**
		 * 存储
		 */
		store("存储", 1),
		
		/**
		 * 可分词
		 */
		index("可分词", 2);
		
		private final String value;
		private final int key;
		
		private SearchedEnum(final String value, final int key) {
			this.value = value;
			this.key = key;
		}
		public String value() {
			return this.value;
		}
		public int key() {
			return this.key;
		}
	}
}


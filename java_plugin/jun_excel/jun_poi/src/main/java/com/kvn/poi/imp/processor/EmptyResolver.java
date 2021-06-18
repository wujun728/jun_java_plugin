package com.kvn.poi.imp.processor;

import java.lang.reflect.Field;

/**
* @author wzy
* @date 2017年7月12日 下午2:40:20
*/
@SuppressWarnings("rawtypes")
public class EmptyResolver extends AbstractResolver {
	
	private EmptyResolver() {
		super();
	}

	public static class SINGLE {
		public static final EmptyResolver INSTANCE = new EmptyResolver();
	}

	/**
	 * 永远为true
	 */
	@Override
	public boolean support(Field field) {
		return true;
	}

	@Override
	public Object process() {
		return null;
	}

}

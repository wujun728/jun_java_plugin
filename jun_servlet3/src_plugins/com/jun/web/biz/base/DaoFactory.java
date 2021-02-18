package com.jun.web.biz.base;

import java.util.ResourceBundle;

public class DaoFactory {

	private DaoFactory(){};
	
	private static DaoFactory instance = new DaoFactory();
	
	public static DaoFactory getInstance() {
		return instance;
	}

	
	//����dao ��ʵ��
	public <T> T createDao(Class<T> clazz){
		
		// ��� simpleName ,Ȼ�� ȥ�� properties �����ļ�
		String simpleName = clazz.getSimpleName();
		
		// ��dao.properties
		String className = ResourceBundle.getBundle("dao").getString(simpleName);
		
		try {
			return (T)Class.forName(className).newInstance();
		} catch (Exception e) {
			
			return null;
		}
		
		
		
	}
}

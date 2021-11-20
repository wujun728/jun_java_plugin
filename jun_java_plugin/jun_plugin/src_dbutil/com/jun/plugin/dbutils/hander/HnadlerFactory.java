package com.jun.plugin.dbutils.hander;

import java.util.Map;

import org.apache.commons.dbutils.handlers.BeanListHandler;

public class HnadlerFactory {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	public static BeanListHandler BeanListHandler(Object obj) {
		BeanListHandler handler = new BeanListHandler(obj.getClass());
		return handler;
	}
	
	public static BeanListHandler BeanListHandlerForObject() {
		BeanListHandler handler = new BeanListHandler(Object.class);
		return handler;
	}
	
	public static BeanListHandler BeanListHandlerForMap() {
		BeanListHandler handler = new BeanListHandler(Map.class);
		return handler;
	}

}

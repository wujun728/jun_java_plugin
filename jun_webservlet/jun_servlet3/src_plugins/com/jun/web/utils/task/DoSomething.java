package com.jun.web.utils.task;

import java.util.Map;

public class DoSomething {
	/**
	 * ��ָ���ֻ��ŷ��Ͷ���
	 * @param name
	 * @param content
	 * @return 
	 */
	public boolean testMethod(Map param){
		String name = String.valueOf(param.get("name"));
		String content = String.valueOf(param.get("content"));
		Map map = (Map)param.get("map");
		System.out.println("1111111111:" + name + ",2222222222:" + content+",33333="+map.get("key1"));
		return true;
	}
}

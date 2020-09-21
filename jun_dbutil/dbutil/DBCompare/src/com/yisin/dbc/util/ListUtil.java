package com.yisin.dbc.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * @简述：List工具类
 * @详述：对List数据进行处理，转换等操作，可扩展
 * 
 * @author yisin
 */
public class ListUtil {

	public static void main(String[] args) {
		
	}

	/**
	 * List截取，取[x, y]之间的数据
	 * @param list
	 * @param index
	 * @param count
	 * @return
	 */
	public static List<Object> pickList(List<Object> list, int index, int count) {
		List<Object> newList = new ArrayList<Object>();
		if (list != null) {
			int k = list.size();
			int fromIndex = (index - 1) * count;
			int toIndex = index * count;
			toIndex = toIndex > k ? k : toIndex;
			newList = list.subList(fromIndex, toIndex);
		}
		return newList;
	}

	/**
	 * 将list元素按照chats分隔符分割组合成字符串
	 * 
	 * @param list
	 * @param chars
	 * @return
	 */
	public static String listJoin(List<?> list, String chars) {
		String result = "";
		if (list != null) {
			StringBuffer sb = new StringBuffer();
			for (Object obj : list) {
				if(obj instanceof Integer || obj instanceof Double || 
						obj instanceof Float || obj instanceof Short || 
						obj instanceof Long){
					sb.append(obj).append(chars);
				} else {
					sb.append("'").append(obj).append("'").append(chars);
				}
			}
			result = sb.toString();
			if (result.length() > 0) {
				result = result.substring(0, result.length() - 1);
			}
		}
		return result;
	}

	/**
	 * list<对象>去重
	 * @param list
	 * @return
	 */
	public static List<Serializable> listDelDuplicated(List<Serializable> list) {
		if (list != null) {
			Set<Serializable> ts = new HashSet<Serializable>();
			ts.addAll(list);
			list.clear();
			Iterator<Serializable> ito = ts.iterator();
			while (ito.hasNext()) {
				list.add(ito.next());
			}
		}
		return list;
	}
	
	/**
	 * 将List转换为数组
	 * @param list
	 * @return
	 */
	public static Object[] list2Array(List<Object> list){
	    Object[] objArr = null;
	    if(list != null){
	        objArr = new Object[list.size()];
	        for (int i = 0, k = objArr.length; i < k; i++) {
	            objArr[i] = list.get(i);
            }
	    }
	    return objArr;
	}
}

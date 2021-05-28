package com.ibeetl.admin.core.web.query;

import java.lang.reflect.Field;
import java.util.concurrent.ConcurrentHashMap;

import com.ibeetl.admin.core.annotation.Query;
import com.ibeetl.admin.core.util.ClassLoaderUtil;

/**
 * 页面查询条件
 * @author Wujun
 *
 */
public class QueryParser {
	ConcurrentHashMap<String, QueryData> cache = new ConcurrentHashMap<> ();
	public QueryData getData(String querClass){
		if(cache.containsKey(querClass)){
			return cache.get(querClass);
		}
		
		Class cls = ClassLoaderUtil.loadClass(querClass);
		Field[] fs =cls.getDeclaredFields();
		QueryData data = new QueryData();
		for(Field f:fs){
			Query query = f.getAnnotation(Query.class);
			
			if(query==null){
				continue ;
			}
			QueryItem item = new QueryItem();
			item.setFieldName(f.getName());
			item.setName(query.name());
			item.setShow(query.display());
			item.setType(query.type());
			item.setDictName(query.dict());
			item.setFuzzy(query.fuzzy());
			item.setGroup(query.group());
			data.addQueryItem(item);
		}
		
		cache.put(querClass, data);
		return data;
		
	}
}

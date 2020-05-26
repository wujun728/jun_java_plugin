package com.bing.other;


import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.junit.Test;

import com.bing.excel.annotation.OutAlias;
import com.bing.excel.vo.OutValue.OutType;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

public class AnotationTest {

	@Test
	public void testOut() throws  IllegalAccessException{
		Field[] fields = Person.class.getDeclaredFields();
		for (Field field : fields) {
			System.out.println(getFieldClass(field));
			 System.out.println("____________");
			//System.out.println(createCollection(field.getType()));
		}

	}
	
	
	
	private static Class getFieldClass(Field field) {
	    Class fieldClazz = field.getType();
	 
	    if (Collection.class.isAssignableFrom(fieldClazz)) {
	        Type fc = field.getGenericType(); // 关键的地方，如果是List类型，得到其Generic的类型
	        System.out.println(fc);
	       
	        if (fc instanceof ParameterizedType) // 如果是泛型参数的类型
	        {
	            ParameterizedType pt = (ParameterizedType) fc;
	 
	            fieldClazz = (Class) pt.getActualTypeArguments()[0]; //得到泛型里的class类型对象。
	        }
	    }
	 
	    return fieldClazz;
	}
	private Collection createCollection(Class type) {
		if (type == null) {
			return null;
		}
		if(type.equals(ArrayList.class)||type.equals(List.class)){
			return Lists.newArrayList();
		}else if(type.equals(HashSet.class)||type.equals(Set.class)){
			return Sets.newHashSet();
		}else if(type.equals(LinkedList.class)){
			return Lists.newArrayList();
		}else{
			return null;
		}
		
	}
}
@OutAlias("nihao")
class Person{
	private String name;
	private Integer age;
	private List<String> li;
	private HashSet<Integer> set;
	private ArrayList al;
}
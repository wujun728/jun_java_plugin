package com.ky26.collectionn;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class MapTest {
	public static void main(String[] args) {
		Map<String,String>map=new HashMap<String,String>();
		Emp emp=new Emp("351","张三");
		Emp emp2=new Emp("350","李四");
		Emp emp3=new Emp("315","王毅");
		Emp emp4=new Emp("355","赵六");
		Emp emp5=new Emp("305","张二");
		
		map.put(emp4.getE_id(), emp4.getE_name());
		map.put(emp2.getE_id(), emp2.getE_name());
		map.put(emp3.getE_id(), emp3.getE_name());
		map.put(emp.getE_id(), emp.getE_name());
		map.put(emp5.getE_id(), emp5.getE_name());
		
		Set <String>set=map.keySet();
		Iterator <String>it=set.iterator();
		while(it.hasNext()){
			String str=(String)it.next();
			String name=(String)map.get(str);
			System.out.println(str+" "+name);
		}
		System.out.println("---------");
		TreeMap<String,String>treemap=new TreeMap<String, String>();
		treemap.putAll(map);
//		Set <String>sett=treemap.keySet();
//		Iterator <String>iter=sett.iterator();
		Iterator<String>iter=treemap.keySet().iterator();
		while(iter.hasNext()){
			String str=(String)iter.next();
			String name=(String)treemap.get(str);
			System.out.println(str+" "+name);
		}
	}
}

class Emp{
	private String e_id;
	private String e_name;
	Emp(String e_id,String e_name){
		this.e_id=e_id;
		this.e_name=e_name;
	}
	public String getE_id() {
		return e_id;
	}
	public void setE_id(String e_id) {
		this.e_id = e_id;
	}
	public String getE_name() {
		return e_name;
	}
	public void setE_name(String e_name) {
		this.e_name = e_name;
	}
	
}

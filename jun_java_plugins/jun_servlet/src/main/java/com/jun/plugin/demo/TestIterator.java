package com.jun.plugin.demo;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class TestIterator {

	public static void main(String[] args) {
		List<String> list = new ArrayList<String>();
		list.add("fuck1");
		list.add("fuck2");
		list.add("fuck3");
		list.add("fuck4");
		list.add("fuck5");
		list.add("fuck6");
		
		for(Iterator<String> iter = list.iterator();iter.hasNext();){
			String i  = (String)iter.next();
			if(i.equals("fuck3")){
				iter.remove();
			}
		}
		
		for(int i =0;i<list.size();i++){
			System.out.println(list.get(i));
		}
		
	}
}

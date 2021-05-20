package com.ky26.collectionn;

import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

public class StringTest2 {
	public static void main(String[] args) {
		String str="ababdcdacbfxsrvfgr";
		System.out.println(getCharCount(str));
	}
	public static String getCharCount(String str){
		char[] ch=str.toCharArray();
		Map <Character,Integer> map=new TreeMap<Character,Integer>();
		for(int i=0;i<ch.length;i++){
			if(!(Character.toLowerCase(ch[i])>='a'&&Character.toLowerCase(ch[i])<='z')){
				continue;
			}
			Integer value=map.get(ch[i]);
			int count=1;
			if(value!=null){
				count=value+1;
			}
			map.put(ch[i], count);
		}
		return mapToString(map);
	}
	
	public static String mapToString(Map<Character,Integer>map){
		StringBuilder sb=new StringBuilder();
		Iterator<Character>it=map.keySet().iterator();
		while(it.hasNext()){
			Character key=it.next();
			Integer value=map.get(key);
			sb.append(key+"("+value+")"+",");
		}
		return sb.toString();
	}
	
}

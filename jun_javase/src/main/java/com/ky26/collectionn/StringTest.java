package com.ky26.collectionn;

import java.util.HashMap;

public class StringTest {
	public static void main(String[] args) {
		HashMap<Character,Integer> hashmap=new HashMap();
		String str="ababdcdacbfxsrvfgr";
		for(int i=0;i<str.length();i++){
			if(!hashmap.containsKey(str.charAt(i))){
				hashmap.put(str.charAt(i), new Integer(1));
			}else{
				hashmap.put(str.charAt(i),hashmap.get(str.charAt(i))+1);
			}
		}
		System.out.println(hashmap);
		System.out.println("-------·Ö¸îÏß-------");
		char[] arr=str.toCharArray();
		int countt=1;
		for(int i=0;i<arr.length;i++){
			for(int j=i+1;j<arr.length;j++){
				if(arr[i]==arr[j]&&arr[j]!=0){
					countt++;
					arr[j]='0';
				}
			}
			if(arr[i]!='0'){
				System.out.print(arr[i]+"("+countt+")"+",");
			}
			countt=1;
		}
	}
}

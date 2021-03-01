package com.ky26.string;

public class Test1 {
	public static void main(String[] args) {
		/*String[] strArr={"nba","abc","cba","zz","qq","haha","zq"};
		compareTo(strArr);*/
		String str="nba";
		String str2="nbaernbanatypenbnbapnaonanba";
		showCount3(str2,str);
		
		/*String str3="nbaernbanatypenbnbapnaonanba";
		System.out.println(str3.indexOf(str,0));*/
	}
	
	static void compareTo(String[] arr){
		for(int i=0;i<arr.length-1;i++){
			for(int j=i+1;j<arr.length;j++){
				if(arr[i].compareTo(arr[j])>0){
					String temp=arr[i];
					arr[i]=arr[j];
					arr[j]=temp;
				}
			}
		}
		for(int i=0;i<arr.length;i++){
			System.out.println(arr[i]);
		}
	}
	
	static void showCount(String str1,String str2){
		int n=str1.length();
		int count=0;
		for(int i=0;i<str2.length()-(n-1);i++){
			if(str1.equals(str2.substring(i,i+n))){
				count++;
			}
		}
		System.out.println(count);
	}
	
	static void showCount2(String str,String key){
		int count=0;
		int index=0;
		while((index=str.indexOf(key))!=-1){
			count++;
			str=str.substring(index+key.length());
		}
		System.out.println(count);
	}
	static void showCount3(String str,String key){
		int count=0;
		int index=0;
		while((index=str.indexOf(key,0))!=-1){
			count++;
			str=str.substring(index+key.length());
		}
		System.out.println(count);
	}
}

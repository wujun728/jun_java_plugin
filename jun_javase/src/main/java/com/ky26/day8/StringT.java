package com.ky26.day8;
import java.util.Scanner;
public class StringT {
	public static void main(String[] args) {
		System.out.println("======关键字搜索======");
		
		String[] arr={"无锡，天气3-5度，晴，北风","南京，天气-3-5度，雨，南风","重庆，天气5-10度，晴，东北风","成都，天气3-8度，小雨，西南风","北京，天气3-5度，雪，偏北风","上海，天气5-9度，晴，偏南风"};
		
		Scanner scan=new Scanner(System.in);
		System.out.println("请输入关键字搜索：");
		String key=scan.next();
		boolean find=true;
		for(int i=0;i<arr.length;i++){
			if(arr[i].contains(key)){
				System.out.println(arr[i]);
				find=false;
			}
			else{
				continue;
			}
			
		}
		
		if(find==true){
			System.out.println("无结果");
		}

		
		
	}
	
	
}

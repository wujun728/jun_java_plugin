package com.ky26.SevDay;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTimeFormatter {
	public static void main(String[] args) throws ParseException {
		String idNumber="500102199510153465";
		String burnDate=idNumber.substring(6,14);
		String sex=idNumber.substring(16,17);
		int sex1=Integer.parseInt(sex);
		
		if(sex1%2==0){
			System.out.println("性别"+"女");
		}
		else{
			System.out.println("性别"+"男");
		}
		
		DateFormat dateFormat1=new SimpleDateFormat("yyyyMMdd");
		Date myDate1=dateFormat1.parse(burnDate);
		String yearN=String.format("%tF",myDate1);
		System.out.println("出生日期："+yearN);
		
		
		String str="1271826981@163.com";
		String regex="\\d{8,13}@\\w+(\\.com)";
		
		
		if(str.matches(regex)){
			System.out.println("合法的");
		}
		else{
			System.out.println("不合法");
		}
		
		int[][] arr=new int[3][2];
		arr[0]=new int[]{1,2,3};
		arr[1]=new int[]{4,5,6};
		arr[2]=new int[]{7,8,9};
		
		System.out.print(arr+",");
		System.out.println(arr[0]+","+arr[1]+","+arr[2]);
		
		int num=28728;
		String str2=Integer.toString(num);
		System.out.println(str2);
		
		StringBuilder bf=new StringBuilder("hello");
		
		bf.insert(5,"world");
		
		System.out.println(bf);
		
	}
}

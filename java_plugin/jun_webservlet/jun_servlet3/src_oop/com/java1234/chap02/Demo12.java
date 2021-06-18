package com.java1234.chap02;

public class Demo12 {

	public static void main(String[] args) {
		int a=1;
		
		// if语句
		// 多行注释快捷方式 ctrl+shift+/
		/*if(a>0){
			System.out.println(a+"是正数");
		}*/
		
		// if ..else语句
		/*if(a>0){
			System.out.println(a+"是正数");
		}else{
			System.out.println(a+"不是正数");
		}*/
		
		// if ...else if ...else
		if(a>0){
			System.out.println(a+"是正数");
		}else if(a<0){
			System.out.println(a+"是负数");
		}else{
			System.out.println(a+"是0");
		}
	}
}

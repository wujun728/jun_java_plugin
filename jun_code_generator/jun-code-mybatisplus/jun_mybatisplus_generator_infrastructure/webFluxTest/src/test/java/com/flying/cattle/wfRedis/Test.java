package com.flying.cattle.wfRedis;

public class Test {
	private static int count_down_sec=68;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		double i=100000;
//		
//		for (int j = 0; j < 12; j++) {
//			i=i+(i*0.05);
//		}
//		System.out.println("本钱加盈利："+i);
		int h = count_down_sec/(60*60);
		int m = (count_down_sec%(60*60))/60;
		int s = (count_down_sec%(60*60))%60;
		
		String countDown="倒计时："+h+"小时"+m+"分钟"+s+"秒";
		System.out.println(countDown);
	}

}

package com.jun.admin.util;

import java.math.BigDecimal;
import java.security.MessageDigest;
import java.text.DecimalFormat;

import org.aspectj.internal.lang.annotation.ajcDeclareAnnotation;

public class MD5Util3 {

	// 十六进制下数字到字符的映射数组

	@SuppressWarnings("unused")
	private final static String[] hexDigits = { "0", "1", "2", "3", "4", "5",
			"6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };



	public static String encodeByMD5(String originString) {
		if (originString != null) {
			try {
				// 创建具有指定算法名称的信息摘要
				MessageDigest md = MessageDigest.getInstance("MD5");
				// 使用指定的字节数组对摘要进行最后更新，然后完成摘要计算
				byte[] results = md.digest(originString.getBytes());
				// 将得到的字节数组变成字符串返回
				String resultString = MsgGenUtil.buf2hex(results);
				return resultString.toUpperCase();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		return null;
	}
	
	/**
	 * 
	 *TODOGBK编码
	 *@param originString
	 *@return   
	 *String
	 */
	public static String encodeByMD5GBK(String originString) {
		if (originString != null) {
			try {
				// 创建具有指定算法名称的信息摘要
				MessageDigest md = MessageDigest.getInstance("MD5");
				// 使用指定的字节数组对摘要进行最后更新，然后完成摘要计算
				byte[] results = md.digest(originString.getBytes("GBK"));
				// 将得到的字节数组变成字符串返回
				String resultString = MsgGenUtil.buf2hex(results);
				return resultString.toUpperCase();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		return null;
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public static void main(String[] s){
//		for(int i=0;i<100;i++){
//			Test1(16);
//		}
//		System.out.println(Integer.MAX_VALUE);
//		System.out.println(String.valueOf(Integer.MAX_VALUE).length());
		System.out.println(Long.MAX_VALUE);
		System.out.println(String.valueOf(Long.MAX_VALUE).length());
		DECODE2(0);
//		DecimalFormat df=new DecimalFormat("00000000");
//		System.out.println(df.format(99));
//		Test2(32);
//		Threads(5);
//		DECODE1(1);
	}
	
	public static String Test1(int n) {
		String s1=RandomUtil.generateNumStr(n);
		System.out.println(s1);
		System.out.println(MD5Util3.encodeByMD5GBK(s1));
		return null;
	}
	
	
	
	
	public static String DECODE1(int n) {
		String md51="A092670C9E82001C72B1881DD7CF5EDB";
		int j=0;
		 for(Long i=1000004093333056l;i<=Long.MAX_VALUE;i++){
			 
			 if(String.valueOf(i).contains("000")||String.valueOf(i).contains("111")||String.valueOf(i).contains("222")||String.valueOf(i).contains("333")||String.valueOf(i).contains("444")){
				 j++;
//				 System.out.println("i1="+i);
				 continue;
			 }
			 if(String.valueOf(i).contains("555")||String.valueOf(i).contains("666")||String.valueOf(i).contains("777")||String.valueOf(i).contains("888")||String.valueOf(i).contains("999")){
				 j++;
//				 System.out.println("i2="+i);
				 continue;
			 }
			 if(i%29999998==0){
				 System.out.println("位数："+String.valueOf(i).length());
				 System.out.println("当前值="+i);
				 System.out.println("百分比="+i/Double.valueOf(10000000000000000L));
			 }
			 if(i<999999999999999L){
//				 j++;
				 continue;
			 }
			 if(String.valueOf(i).length()!=16){
				 j++;
				 continue;
			 }
			 if(i>9999999999999999L){
//				 j++;
				 continue;
			 }
			 
			 StringBuffer s=new StringBuffer();
			 
			 if(i%9998==0){
				 System.out.println("跳过个数="+j);
				 System.out.println("位数："+String.valueOf(i).length());
				 System.out.println("当前值="+i);
				 System.out.println("百分比值="+i+"/"+10000000000000000L);
				 System.out.println("百分比="+i/Double.valueOf(10000000000000000L));
				 System.out.println("原值="+s);
			 }
			 if(i%9999==0){
				 System.gc();
			 }
			 String md6=MD5Util3.encodeByMD5GBK(String.valueOf(i));
			 if(md51.equals(md6)){
				 for(int m=0;m<10;m++){
					 System.err.println(s);
				 }
				 break;
			 }
		 }
		 System.err.println("End ..........................................");
		 System.err.println("End ..........................................");
		 System.err.println("End ..........................................");
		 System.err.println("End ..........................................");
		 System.err.println("End ..........................................");
//		System.out.println(MD5Util.encodeByMD5GBK(""));
		return null;
	}
	
	
	public static String DECODE2(int n) {
		String md51="A092670C9E82001C72B1881DD7CF5EDB";
		DecimalFormat df=new DecimalFormat("00000000");
		String dfi="";
		String dfj="";
		int i=0;
		int j=0;
		int m=0;
		for(i=0;i<=99999999;i++){
			dfi=df.format(i);
			if(dfi.contains("00")||dfi.contains("11")||dfi.contains("22")||dfi.contains("33")||dfi.contains("44")){
				 continue;
			 }
			 if(dfi.contains("55")||dfi.contains("66")||dfi.contains("77")||dfi.contains("88")||dfi.contains("99")){
				 continue;
			 }
			 m++;
//			 if(m%100==0){
//				 System.out.println("m="+m);
//				 System.out.println("m="+m);
//			 }
//			for(j=0;j<=99999999;j++){
//				dfj=df.format(j);
//				if(md51.equals(MD5Util3.encodeByMD5GBK(dfi.concat(dfj)))){
//					for(int n1=0;n1<1000;n1++){
//						System.err.println(dfi.concat(dfj));
//					}
//					break;
//				}
//			}
//			 System.out.println("位数："+dfi.concat(dfj).length());
//			 System.out.println("当前值="+dfi.concat(dfj));
//			 System.out.println("百分比值="+dfi.concat(dfj)+"/"+10000000000000000L);
//			 System.out.println("百分比="+Double.valueOf(dfi.concat(dfj))/Double.valueOf(10000000000000000L));
//			 System.out.println("原值="+dfi.concat(dfj));
//			System.gc();
		}
		System.out.println(m);
		return null;
	}
	
	
	
	public static String Threads(int n) {
		Threads mt1 = new Threads("线程A","2",1000004093333056l) ;
		Threads mt2 = new Threads("线程B","4",2000004093333056l) ;
		Threads mt3 = new Threads("线程C","6",3000004093333056l) ;
		Threads mt4 = new Threads("线程D","8",4000004093333056l) ;
		Threads mt5 = new Threads("线程E","8",5000004093333056l) ;
		new Thread(mt1).start() ;
		new Thread(mt2).start() ;
		new Thread(mt3).start() ;
		new Thread(mt4).start() ;
		new Thread(mt5).start() ;
		return null;
	}
 

}

class Threads implements Runnable { // 线程主体类
	private String title ;
	private String ss ;
	private Long num ;
	public Threads(String title,String ss,Long num) {
		this.title = title ;
		this.ss = ss ;
		this.num = num ;
	}
	@Override
	public void run() { // 线程的开始方法
 
		String md51="A092670C9E82001C72B1881DD7CF5EDB";
		int j=0;
		 for(Long i=num;i<=Long.MAX_VALUE;i++){
			 
			 if(String.valueOf(i).contains("000")||String.valueOf(i).contains("111")||String.valueOf(i).contains("222")||String.valueOf(i).contains("333")||String.valueOf(i).contains("444")){
				 j++;
//				 System.out.println("i1="+i);
				 continue;
			 }
			 if(String.valueOf(i).contains("555")||String.valueOf(i).contains("666")||String.valueOf(i).contains("777")||String.valueOf(i).contains("888")||String.valueOf(i).contains("999")){
				 j++;
//				 System.out.println("i2="+i);
				 continue;
			 }
			 if(i%29999998==0){
				 System.out.println("位数："+String.valueOf(i).length());
				 System.err.println("当前值="+i);
				 System.out.println("百分比="+i/Double.valueOf(10000000000000000L));
			 }
			 if(String.valueOf(i).length()!=16){
				 j++;
				 System.err.println("length err ..........................................");
				 System.err.println(Thread.currentThread().getName());
				 continue;
			 }
			 StringBuffer s=new StringBuffer();
			 
			 if(i%999998==0){
				 System.out.println("线程："+title);
				 System.err.println("Thread.currentThread().getName()："+Thread.currentThread().getName());
				 System.out.println("跳过个数="+j);
				 System.err.println("位数："+String.valueOf(i).length());
				 System.err.println("当前值="+i);
				 System.err.println("百分比值="+i+"/"+10000000000000000L);
				 System.out.println("百分比="+i/Double.valueOf(10000000000000000L));
				 System.out.println("原值="+s);
			 }
			 if(i%999998==0){
				 System.gc();
			 }
			 String md6=MD5Util3.encodeByMD5GBK(String.valueOf(i));
			 if(md51.equals(md6)){
				 for(int m=0;m<10;m++){
					 System.err.println(s);
				 }
				 break;
			 }
		 }
		 for(int i =0;i<99999999;i++){
			 System.err.println("End ..........................................");
			 System.out.println(Thread.currentThread().getName());
		 }
	} 
}


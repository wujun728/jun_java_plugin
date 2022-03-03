package com.jun.admin.util;

import java.security.MessageDigest;
import java.text.DecimalFormat;

public class MD5Util2 {

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
//		Test2(32);
		
		DECODE1(1);
	}
	
	public static String Test1(int n) {
		String s1=RandomUtil.generateNumStr(n);
		System.out.println(s1);
		System.out.println(MD5Util2.encodeByMD5GBK(s1));
		return null;
	}
	
	public static String Test2(int n) {
		String md51="A092670C9E82001C72B1881DD7CF5EDB";
		int j=0;
		 for(int i=239000000;i<=Integer.MAX_VALUE;i++){
			 if(Integer.toBinaryString(i).toString().toString().contains("00000000")){
				 j++;
				 continue;
			 }
			 if(Integer.toBinaryString(i).toString().toString().contains("11111111")){
				 j++;
				 continue;
			 }
			 
			 StringBuffer s=new StringBuffer();
			 if(String.valueOf(i).length()<=32){
				 for(int k=0;k<32-Integer.toBinaryString(i).toString().length();k++){
					 s.append("0");
				 }
			 }
			 s.append(Integer.toBinaryString(i).toString());
			 
			 if(i%100000==0){
				 System.out.println("跳过个数="+j);
				 System.out.println("位数："+Integer.toBinaryString(i).toString().length());
				 System.out.println("当前值="+i);
				 System.out.println("百分比值="+i+"/"+Integer.MAX_VALUE);
				 System.out.println("百分比="+i/Double.valueOf(Integer.MAX_VALUE));
				 System.out.println("原值="+s);
			 }
			 if(i%100000000==0){
				 System.gc();
			 }
			 String md6=MD5Util2.encodeByMD5GBK(s.toString());
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
			 String md6=MD5Util2.encodeByMD5GBK(String.valueOf(i));
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
	
	
	
	public static String Test3(int n) {
		MyThread mt1 = new MyThread("线程A","2") ;
//		MyThread mt2 = new MyThread("线程B","4") ;
//		MyThread mt3 = new MyThread("线程C","6") ;
//		MyThread mt4 = new MyThread("线程D","8") ;
//		MyThread mt5 = new MyThread("线程E","10") ;
		new Thread(mt1).start() ;
//		new Thread(mt2).start() ;
//		new Thread(mt3).start() ;
//		new Thread(mt4).start() ;
//		new Thread(mt5).start() ;
		return null;
	}
 

}

class MyThread2 implements Runnable { // 线程主体类
	private String title ;
	private String ss ;
	public MyThread2(String title,String ss) {
		this.title = title ;
		this.ss = ss ;
	}
	
	@Override
	public void run() { // 线程的开始方法
 
		String md51="4AF94FFEEDD67727ADEFA7FB40785D9E";
		int j=0;
		//0009900000000101000000
		 for(int i=5950000;i<=99999999999L;i++){
//			 if(Integer.toString(i).contains("000")){
//				 j++;
////				 System.out.println("j="+j++);
//				 continue;
//			 }
//			 if(Integer.toString(i).contains("111")){
//				 j++;
////				 System.out.println("j="+j++);
//				 continue;
//			 }
			 
//			 StringBuffer s=new StringBuffer();
//			 s.append("0");
//			 s.append(Integer.toString(i));
			 DecimalFormat df = new DecimalFormat("00000000000"); 
			 String s=String.valueOf(df.format(i));
			 if(i%1000000==0){
				 System.out.println("线程："+title);
				 System.out.println("Thread.currentThread().getName()："+Thread.currentThread().getName());
				 System.out.println("跳过个数："+j);
				 System.out.println("位数："+s.length());
				 System.out.println("当前值："+s);
				 System.out.println("百分比值："+s+"/"+2000000000);
				 System.out.println("百分比："+i/Integer.valueOf(2000000000));
				 System.out.println("原值："+s);
			 }
			 if(i%10000==0){
				 System.gc();
			 }
			 String md6=MD5Util2.encodeByMD5GBK(s.toString());
			 if(md51.equals(md6)){
				 for(int m=0;m<10;m++){
					 System.err.println(s);
				 }
				 break;
			 }
//			 if(md52.equals(md6)){
//				 for(int m=0;m<10;m++){
//					 System.err.println(s);
//				 }
//				 break;
//			 }
			 
		 }
		 System.err.println("End ..........................................");
		 System.err.println("End ..........................................");
		 System.err.println("End ..........................................");
		 System.err.println("End ..........................................");
		 System.err.println("End ..........................................");
		//**************************************************************************************************
		//**************************************************************************************************
		//**************************************************************************************************
//		System.out.println(Thread.currentThread().getName());
	} 
}


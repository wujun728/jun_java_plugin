package test;

import java.security.MessageDigest;
import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public abstract class MD5Tools
{
    public final static String MD5(String pwd) {
        //用于加密的字符
        char md5String[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'A', 'B', 'C', 'D', 'E', 'F' };
        try {
            //使用平台的默认字符集将此 String 编码为 byte序列，并将结果存储到一个新的 byte数组中
            byte[] btInput = pwd.getBytes();
             
            //信息摘要是安全的单向哈希函数，它接收任意大小的数据，并输出固定长度的哈希值。
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
             
            //MessageDigest对象通过使用 update方法处理数据， 使用指定的byte数组更新摘要
            mdInst.update(btInput);
             
            // 摘要更新之后，通过调用digest（）执行哈希计算，获得密文
            byte[] md = mdInst.digest();
             
            // 把密文转换成十六进制的字符串形式
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {   //  i = 0
                byte byte0 = md[i];  //95
                str[k++] = md5String[byte0 >>> 4 & 0xf];    //    5 
                str[k++] = md5String[byte0 & 0xf];   //   F
            }
             
            //返回经过加密后的字符串
            return new String(str);
             
        } catch (Exception e) {
            return null;
        }
    }
    
    public static void main(String[] args) {
    	String s = "";
    	int i =0;
    	while(i<32) {
    		s+=new Random().nextInt(2);
    		i++;
    	}
    	System.err.println(s.length());
    	System.err.println(s);
    	System.err.println(MD5Tools.MD5(s));
    	System.err.println();
 
    	
    	/*ThreadClazz ticket  = new ThreadClazz();
        Thread thread1 = new Thread(ticket ,"线程1    ");//创建了一个线程；
        Thread thread2 = new Thread(ticket ,"线程2    ");//创建了一个线程；
        Thread thread3 = new Thread(ticket ,"线程3    ");//创建了一个线程；
        Thread thread4 = new Thread(ticket ,"线程4    ");//创建了一个线程；
        thread1.start();
        thread2.start();
        thread3.start();
        thread4.start();*/ 
    	System.out.println("---------------------------->"+Long.valueOf("11111111111111111111111111111111", 2));
    	System.out.println("---------------------------->"+"11111111111111111111111111111111".length());
    	System.out.println("---------------------------->"+Integer.MAX_VALUE*16L);
    	getDecode();
	}
    /*

* 
*/
    
    public static void getDecode() {
//     	Integer num=Integer.MAX_VALUE;
     	//Long num=56860000000L;//Integer.MAX_VALUE*16L;
//     	Long num=1099511627775L;//Integer.MAX_VALUE*16L;
//     	Long num=1080597000000L;//Integer.MAX_VALUE*16L;
     	Long num=4294967295L;//Integer.MAX_VALUE*16L;
    	//System.out.println(Thread.currentThread().getName());53533000000
    	for(Long val = num ;val >= 0L;val--) {
    		String str1 = Long.toBinaryString((val));
    		/*if(val%1000000==0) {
    			System.out.println("--1--------------------------A  -- >"+str1.toString().length());
    			System.out.println("--2--------------------------A  -- >"+str1);
    		}*/
    		if(str1.length()!=32) {
    			int i = 32 - str1.length();
    			while(i > 0)  {
    				str1="0"+str1;
    				i--;
    			}
    		}
    		//String str = str1 ;
			if(MD5Tools.MD5(str1.toString()).equals("0DB02B4029930C2DFD5D66813A6D91B0")) {
				System.err.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
				System.err.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! "+str1.toString());
				System.err.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!"+val);
				System.err.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
				System.err.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
				System.err.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
				System.err.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
				System.err.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
				break;
			}else {
				if(val%1000000==0) {
					System.out.println("1---------------------------->"+str1.toString());
					System.out.println("2---------------------------->"+str1.length());
					//System.out.println("3---------------------------->"+Long.valueOf(str.toString(), 2));
					System.out.println("4---------------------------->"+(val));
					System.out.println("4---------------------------->"+(val*10000/4294967295L));
				}
			}
    	}
    } 
    
   /* !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
  
    !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
*/
    
}


class ThreadClazz implements Runnable   //extends Thread
{
     private int val = 785000000-1;
     public void run(){
           while(true){
        	   Lock lock = new ReentrantLock();
           	   lock.lock();
               if(val>0){
	           		String str1 = Integer.toBinaryString((val));
	           		if(str1.length()!=31) {
	           			int i = 31 - str1.length();
	           			do {
	           				str1 = "0"+str1;
	           				i--;
	           			}while(i > 0);
	           		}
	           		String str ="1" + str1 ;
	       			if(MD5Tools.MD5(str.toString()).equals("77206656523BD98992CCB228CB8A44EA")) {
	       				System.err.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
	       				System.err.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!"+str.toString());
	       				System.err.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!"+val);
	       				System.err.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
	       				System.err.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
	       				System.err.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
	       				System.err.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
	       				System.err.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
	       				break;
	       			}else {
	       				if(val%1000000==0) {
	       					System.out.println(Thread.currentThread().getName()+"---------------------------->"+(val)+"---------------------------->"+str.toString());
	       				}
	       			}
	       			System.out.println(Thread.currentThread().getName()+"---------------------------->"+(val)+"---------------------------->"+str.toString());
//	                System.out.println(Thread.currentThread().getName()+ );
	                val--;
	                lock.unlock();;
               }
          } 
    	 
    	 MD5Tools.getDecode();
     }
}


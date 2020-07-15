package com.jun.plugin.utils.codec;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Map.Entry;

import com.jun.plugin.utils.NumUtil;
import com.jun.plugin.utils.StringUtil;
import com.jun.plugin.utils.time.DateUtil;

import sun.misc.BASE64Encoder;

/**
 * URLEncoding is the alternate base64 encoding defined in RFC 4648. It is
 * typically used in URLs and file names.
 * 
 */
public class EncodeUtils {
	
	


	/**
	 * 测试
	 * 
	 * @param args
	 */
	public static void Testmain(String[] args) {
		// md5加密测试
		String md5_1 = md5("123456");
		String md5_2 = md5("孙宇");
		System.out.println(md5_1 + "\n" + md5_2);
		// sha加密测试
		String sha_1 = sha("123456");
		String sha_2 = sha("孙宇");
		System.out.println(sha_1 + "\n" + sha_2);

	}

	/**
	 * 加密
	 * 
	 * @param inputText
	 * @return
	 */
	public static String e(String inputText) {
		return md5(inputText);
	}

	/**
	 * 二次加密，应该破解不了了吧？
	 * 
	 * @param inputText
	 * @return
	 */
	public static String md5AndSha(String inputText) {
		return sha(md5(inputText));
	}

	/**
	 * md5加密
	 * 
	 * @param inputText
	 * @return
	 */
	public static String md5(String inputText) {
		return encrypt(inputText, "md5");
	}

	/**
	 * sha加密
	 * 
	 * @param inputText
	 * @return
	 */
	public static String sha(String inputText) {
		return encrypt(inputText, "sha-1");
	}

	/**
	 * md5或者sha-1加密
	 * 
	 * @param inputText
	 *            要加密的内容
	 * @param algorithmName
	 *            加密算法名称：md5或者sha-1，不区分大小写
	 * @return
	 */
	private static String encrypt(String inputText, String algorithmName) {
		if (inputText == null || "".equals(inputText.trim())) {
			throw new IllegalArgumentException("请输入要加密的内容");
		}
		if (algorithmName == null || "".equals(algorithmName.trim())) {
			algorithmName = "md5";
		}
		String encryptText = null;
		try {
			MessageDigest m = MessageDigest.getInstance(algorithmName);
			m.update(inputText.getBytes("UTF8"));
			byte s[] = m.digest();
			// m.digest(inputText.getBytes("UTF8"));
			return hex(s);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return encryptText;
	}

	/**
	 * 返回十六进制字符串
	 * 
	 * @param arr
	 * @return
	 */
	private static String hex(byte[] arr) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < arr.length; ++i) {
			sb.append(Integer.toHexString((arr[i] & 0xFF) | 0x100).substring(1, 3));
		}
		return sb.toString();
	}
	
	
	
	/**
	 * @param args add by zxx ,Dec 30, 2008
	 * @throws IOException 
	 */
	public static void main44(String[] args) throws IOException {
		// TODO Auto-generated method stub
		BASE64Encoder encoder = new BASE64Encoder();
		System.out.println("please input user name:");
		String username = new BufferedReader(
					new InputStreamReader(System.in))
					.readLine();
		System.out.println(encoder.encode(username.getBytes()));
		System.out.println("please input password:");
		String password = new BufferedReader(
				new InputStreamReader(System.in))
				.readLine();		
		System.out.println(encoder.encode(password.getBytes()));
	}

	/**
	 * 测试
	 * 
	 * @param args
	 */
	public static void main22(String[] args) {
		// md5加密测试
		String md5_1 = md5("123456");
		String md5_2 = md5("zhangdaihao");
		//System.out.println(md5_1 + "\n" + md5_2);
		// sha加密测试
		String sha_1 = sha("123456");
		String sha_2 = sha("zhangdaihao");
		//System.out.println(sha_1 + "\n" + sha_2);
		
		System.out.println(e("admin"));

	}

 

	public static final String allChar = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
	public static final String letterChar = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
	public static final String numberChar = "0123456789";
	public static final String numberChar_1 = "123456789";

	/**
	 * 返回一个定长的随机字符串(只包含大小写字母、数字)
	 * 
	 * @param length
	 *            随机字符串长度
	 * @return 随机字符串
	 */
	public static String generateNumAndLetterStr(int length) {
		StringBuffer sb = new StringBuffer();
		Random random = new Random();
		for (int i = 0; i < length; i++) {
			sb.append(allChar.charAt(random.nextInt(allChar.length())));
		}
		return sb.toString();
	}

	/**
	 * 返回一个定长的随机字符串(只包含数字)
	 * 
	 * @param length
	 *            随机字符串长度
	 * @return 随机字符串
	 */
	public static String generateNumStr(int length) {
		StringBuffer sb = new StringBuffer();
		Random random = new Random();
		for (int i = 0; i < length; i++) {
			sb.append(numberChar.charAt(random.nextInt(numberChar.length())));
		}
		return sb.toString();
	}
	
	/**
	 * 返回一个定长的随机字符串(只包含数字)
	 * 
	 * @param length
	 *            随机字符串长度
	 * @return 随机字符串
	 */
	public static String generateNumStr01(int length) {
		StringBuffer sb = new StringBuffer();
		Random random = new Random();
		for (int i = 0; i < length; i++) {
			int r=random.nextInt(2);
			sb.append(r);
		}
		return sb.toString();
	}

	/**
	 * 返回一个定长的随机纯字母字符串(只包含大小写字母)
	 * 
	 * @param length
	 *            随机字符串长度
	 * @return 随机字符串
	 */
	public static String generateLetterStr(int length) {
		StringBuffer sb = new StringBuffer();
		Random random = new Random();
		for (int i = 0; i < length; i++) {
			sb.append(letterChar.charAt(random.nextInt(letterChar.length())));
		}
		return sb.toString();
	}

	/**
	 * 返回一个定长的随机纯大写字母字符串(只包含大小写字母)
	 * 
	 * @param length
	 *            随机字符串长度
	 * @return 随机字符串
	 */
	public static String generateLowerLetterStr(int length) {
		return generateLetterStr(length).toLowerCase();
	}

	/**
	 * 返回一个定长的随机纯小写字母字符串(只包含大小写字母)
	 * 
	 * @param length
	 *            随机字符串长度
	 * @return 随机字符串
	 */
	public static String generateUpperLetterStr(int length) {
		return generateLetterStr(length).toUpperCase();
	}

	/**
	 * 生成一个定长的纯0字符串
	 * 
	 * @param length
	 *            字符串长度
	 * @return 纯0字符串
	 */
	public static String generateZeroStr(int length) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < length; i++) {
			sb.append('0');
		}
		return sb.toString();
	}

	/**
	 * 根据数字生成一个定长的字符串，长度不够前面补0
	 * 
	 * @param num
	 *            数字
	 * @param fixdlenth
	 *            字符串长度
	 * @return 定长的字符串
	 */
	public static String toFixdLengthStr(long num, int fixdlenth) {
		StringBuffer sb = new StringBuffer();
		String strNum = String.valueOf(num);
		if (fixdlenth - strNum.length() >= 0) {
			sb.append(generateZeroStr(fixdlenth - strNum.length()));
		}
		sb.append(strNum);
		return sb.toString();
	}

	/**
	 * 根据数字生成一个定长的字符串，长度不够前面补0
	 * 
	 * @param num
	 *            数字
	 * @param fixdlenth
	 *            字符串长度
	 * @return 定长的字符串
	 */
	public static String toFixdLengthStr(int num, int fixdlenth) {
		StringBuffer sb = new StringBuffer();
		String strNum = String.valueOf(num);
		if (fixdlenth - strNum.length() >= 0) {
			sb.append(generateZeroStr(fixdlenth - strNum.length()));
		}
		sb.append(strNum);
		return sb.toString();
	}

	/**
	 * 
	 * TODO生成不含0的随机数
	 * 
	 * @param length
	 * @return
	 * 
	 *         String
	 */
	public static String generateNumStrNotContain_0(int length) {
		StringBuffer sb = new StringBuffer();
		Random random = new Random();
		for (int i = 0; i < length; i++) {
			sb.append(numberChar_1.charAt(random.nextInt(numberChar_1.length())));
		}
		return sb.toString();
	}

	/**
	 * 
	 * 获取时间和随机数的字符串
	 * 
	 * @return
	 * 
	 *         String
	 */
	public static String getRandomDateAndNumber() {
		return DateUtil.getTimeStamp() + generateNumStr(5);
	}
	
	
	


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
				String resultString = NumUtil.buf2hex(results);
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
				String resultString = NumUtil.buf2hex(results);
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
//		System.out.println(Long.MAX_VALUE);
//		System.out.println(String.valueOf(Long.MAX_VALUE).length());
//		Test2(32);
		
		Test3(32);
	}
	
	public static String Test1(int n) {
		String s1=StringUtil.generateNumStr(n);
		System.out.println(s1);
		System.out.println(EncodeUtils.encodeByMD5GBK(s1));
		return null;
	}
	
	public static String Test2(int n) {
//		System.out.println(Integer.toBinaryString(Integer.MAX_VALUE-2));
//		System.out.println(Integer.toBinaryString(Integer.MAX_VALUE-2).toString().length());
		
		String md51="441D8BFB7163DEA2530078A108D61FF4";
		String md52="95F73ADEC5AE5D3EF30C8DAD20B84A4E";
		int j=0;
		 for(int i=239000000;i<=Integer.MAX_VALUE;i++){
			 if(Integer.toBinaryString(i).toString().toString().contains("00000000")){
				 j++;
//				 System.out.println("j="+j++);
				 continue;
			 }
			 if(Integer.toBinaryString(i).toString().toString().contains("11111111")){
				 j++;
//				 System.out.println("j="+j++);
				 continue;
			 }
			 
			 StringBuffer s=new StringBuffer();
			 if(String.valueOf(i).length()<=32){
				 for(int k=0;k<32-Integer.toBinaryString(i).toString().length();k++){
					 s.append("0");
				 }
			 }
			 s.append(Integer.toBinaryString(i).toString());
			 
			 if(i%1000000==0){
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
			 String md6=EncodeUtils.encodeByMD5GBK(s.toString());
			 if(md51.equals(md6)){
				 for(int m=0;m<10;m++){
					 System.err.println(s);
				 }
				 break;
			 }
			 if(md52.equals(md6)){
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

class MyThread implements Runnable { // 线程主体类
	private String title ;
	private String ss ;
	public MyThread(String title,String ss) {
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
			 String md6=EncodeUtils.encodeByMD5GBK(s.toString());
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
	//		System.out.println(Thread.currentThread().getName());
		//**************************************************************************************************
		//**************************************************************************************************
		/**************************************************************************************************/
		 /**************************************************************************************************/

	} 
	/**************************************************************************************************/
	/**************************************************************************************************/
	/**************************************************************************************************/
	/**************************************************************************************************/

	 static MessageDigest md = null;

	    static {
	        try {
	            md = MessageDigest.getInstance("MD5");
	        } catch (NoSuchAlgorithmException ne) {
	            //log.error("NoSuchAlgorithmException: md5", ne);
	        }
	    }

	    /**
	     * 对一个文件求他的md5值
	     * @param f 要求md5值的文件
	     * @return md5串
	     */
	    public static String md5(File f) {
	        FileInputStream fis = null;
	        try {
	            fis = new FileInputStream(f);
	            byte[] buffer = new byte[8192];
	            int length;
	            while ((length = fis.read(buffer)) != -1) {
	                md.update(buffer, 0, length);
	            }

	            //return new String(Hex.encodeHex(md.digest()));
	            return null;
	        } catch (FileNotFoundException e) {
	           // log.error("md5 file " + f.getAbsolutePath() + " failed:" + e.getMessage());
	            return null;
	        } catch (IOException e) {
	           // log.error("md5 file " + f.getAbsolutePath() + " failed:" + e.getMessage());
	            return null;
	        } finally {
	            try {
	                if (fis != null) {
	                    fis.close();
	                }
	            } catch (IOException ex) {
	            // log.error("文件操作失败",ex);
	            }
	        }
	    }

	    /**
	     * 求一个字符串的md5值
	     * @param target 字符串
	     * @return md5 value
	     */
	    public static String EncodeUtilsmd5(String target) {
	    	//DigestUtils.md5Hex(target);
	        return  null;
	    }
	    /**
	     * 可以比较两个文件是否内容相等
	     * @param args 
	     */
	    public static void EncodeUtilsmain(String[] args){
	        File newFile=new File("D:/files/paoding-analysis.jar.new");
	        File oldFile=new File("D:/files/paoding-analysis.jar.old");
	        String s1=md5(newFile);
	        String s2=md5(oldFile);
	        System.out.println(s1.equals(s2));
	        System.out.println(s1);
	        System.out.println(s2);
	    }
	/**************************************************************************************************/
	/**************************************************************************************************/
	/**************************************************************************************************/
	/**************************************************************************************************/
	    

		/**
		 * ʹ��md5���㷨���м���
		 */
		public static String md52(String plainText) {
			byte[] secretBytes = null;
			try {
				
							// ��ϢժҪ 
				secretBytes = MessageDigest.getInstance("md5").digest(
						plainText.getBytes());
			} catch (NoSuchAlgorithmException e) {
				throw new RuntimeException("û��md5����㷨��");
			}
			
			String md5code = new BigInteger(1, secretBytes).toString(16);// 16��������
			// �����������δ��32λ����Ҫǰ�油0
			for (int i = 0; i < 32 - md5code.length(); i++) {
				md5code = "0" + md5code;
			}
			return md5code;
		}

		//mysql: 202cb962ac59075b964b07152d234b70
		//java:  202cb962ac59075b964b07152d234b70
		public static void main(String[] args) {
			System.out.println(md5("123"));
		}

		
 

		/**
		 * 加密
		 * 
		 * @param inputText
		 * @return
		 */
		public static String e(String inputText) {
			return md5(inputText);
		}

		/**
		 * 二次加密，应该破解不了了吧？
		 * 
		 * @param inputText
		 * @return
		 */
		public static String md5AndSha(String inputText) {
			return sha(md5(inputText));
		}

		/**
		 * md5加密
		 * 
		 * @param inputText
		 * @return
		 */
		public static String md5(String inputText) {
			return encrypt(inputText, "md5");
		}

		/**
		 * sha加密
		 * 
		 * @param inputText
		 * @return
		 */
		public static String sha(String inputText) {
			return encrypt(inputText, "sha-1");
		}

		/**
		 * md5或者sha-1加密
		 * 
		 * @param inputText
		 *            要加密的内容
		 * @param algorithmName
		 *            加密算法名称：md5或者sha-1，不区分大小写
		 * @return
		 */
		private static String encrypt(String inputText, String algorithmName) {
			if (inputText == null || "".equals(inputText.trim())) {
				throw new IllegalArgumentException("请输入要加密的内容");
			}
			if (algorithmName == null || "".equals(algorithmName.trim())) {
				algorithmName = "md5";
			}
			String encryptText = null;
			try {
				MessageDigest m = MessageDigest.getInstance(algorithmName);
				m.update(inputText.getBytes("UTF8"));
				byte s[] = m.digest();
				// m.digest(inputText.getBytes("UTF8"));
				return hex(s);
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			return encryptText;
		}

		/**
		 * 返回十六进制字符串
		 * 
		 * @param arr
		 * @return
		 */
		private static String hex(byte[] arr) {
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < arr.length; ++i) {
				sb.append(Integer.toHexString((arr[i] & 0xFF) | 0x100).substring(1, 3));
			}
			return sb.toString();
		}

		
		 ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		

		private final static String[] hex = { "00", "01", "02", "03", "04", "05",
				"06", "07", "08", "09", "0A", "0B", "0C", "0D", "0E", "0F", "10",
				"11", "12", "13", "14", "15", "16", "17", "18", "19", "1A", "1B",
				"1C", "1D", "1E", "1F", "20", "21", "22", "23", "24", "25", "26",
				"27", "28", "29", "2A", "2B", "2C", "2D", "2E", "2F", "30", "31",
				"32", "33", "34", "35", "36", "37", "38", "39", "3A", "3B", "3C",
				"3D", "3E", "3F", "40", "41", "42", "43", "44", "45", "46", "47",
				"48", "49", "4A", "4B", "4C", "4D", "4E", "4F", "50", "51", "52",
				"53", "54", "55", "56", "57", "58", "59", "5A", "5B", "5C", "5D",
				"5E", "5F", "60", "61", "62", "63", "64", "65", "66", "67", "68",
				"69", "6A", "6B", "6C", "6D", "6E", "6F", "70", "71", "72", "73",
				"74", "75", "76", "77", "78", "79", "7A", "7B", "7C", "7D", "7E",
				"7F", "80", "81", "82", "83", "84", "85", "86", "87", "88", "89",
				"8A", "8B", "8C", "8D", "8E", "8F", "90", "91", "92", "93", "94",
				"95", "96", "97", "98", "99", "9A", "9B", "9C", "9D", "9E", "9F",
				"A0", "A1", "A2", "A3", "A4", "A5", "A6", "A7", "A8", "A9", "AA",
				"AB", "AC", "AD", "AE", "AF", "B0", "B1", "B2", "B3", "B4", "B5",
				"B6", "B7", "B8", "B9", "BA", "BB", "BC", "BD", "BE", "BF", "C0",
				"C1", "C2", "C3", "C4", "C5", "C6", "C7", "C8", "C9", "CA", "CB",
				"CC", "CD", "CE", "CF", "D0", "D1", "D2", "D3", "D4", "D5", "D6",
				"D7", "D8", "D9", "DA", "DB", "DC", "DD", "DE", "DF", "E0", "E1",
				"E2", "E3", "E4", "E5", "E6", "E7", "E8", "E9", "EA", "EB", "EC",
				"ED", "EE", "EF", "F0", "F1", "F2", "F3", "F4", "F5", "F6", "F7",
				"F8", "F9", "FA", "FB", "FC", "FD", "FE", "FF" };

		private final static byte[] val = { 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
				0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
				0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
				0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
				0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x00, 0x01,
				0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08, 0x09, 0x3F, 0x3F, 0x3F,
				0x3F, 0x3F, 0x3F, 0x3F, 0x0A, 0x0B, 0x0C, 0x0D, 0x0E, 0x0F, 0x3F,
				0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
				0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
				0x3F, 0x3F, 0x3F, 0x0A, 0x0B, 0x0C, 0x0D, 0x0E, 0x0F, 0x3F, 0x3F,
				0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
				0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
				0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
				0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
				0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
				0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
				0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
				0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
				0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
				0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
				0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
				0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
				0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
				0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F };

		/** */
		/**   
		 * ���� 
		 *    
		 * @param s   
		 * @return   
		 */
		public static String escape(String s) {
			StringBuffer sbuf = new StringBuffer();
			int len = s.length();
			for (int i = 0; i < len; i++) {
				int ch = s.charAt(i);
				if ('A' <= ch && ch <= 'Z') {
					sbuf.append((char) ch);
				} else if ('a' <= ch && ch <= 'z') {
					sbuf.append((char) ch);
				} else if ('0' <= ch && ch <= '9') {
					sbuf.append((char) ch);
				} else if (ch == '-' || ch == '_' || ch == '.' || ch == '!'
						|| ch == '~' || ch == '*' || ch == '\'' || ch == '('
						|| ch == ')') {
					sbuf.append((char) ch);
				} else if (ch <= 0x007F) {
					sbuf.append('%');
					sbuf.append(hex[ch]);
				} else {
					sbuf.append('%');
					sbuf.append('u');
					sbuf.append(hex[(ch >>> 8)]);
					sbuf.append(hex[(0x00FF & ch)]);
				}
			}
			return sbuf.toString();
		}

		/** */
		/**   
		 * ���� ˵������������֤ ���۲���s�Ƿ񾭹�escape()���룬���ܵõ���ȷ�ġ����롱��� 
		 *    
		 * @param s   
		 * @return   
		 */
		public static String unescape(String s) {
			StringBuffer sbuf = new StringBuffer();
			int i = 0;
			int len = s.length();
			while (i < len) {
				int ch = s.charAt(i);
				if ('A' <= ch && ch <= 'Z') {
					sbuf.append((char) ch);
				} else if ('a' <= ch && ch <= 'z') {
					sbuf.append((char) ch);
				} else if ('0' <= ch && ch <= '9') {
					sbuf.append((char) ch);
				} else if (ch == '-' || ch == '_' || ch == '.' || ch == '!'
						|| ch == '~' || ch == '*' || ch == '\'' || ch == '('
						|| ch == ')') {
					sbuf.append((char) ch);
				} else if (ch == '%') {
					int cint = 0;
					if ('u' != s.charAt(i + 1)) {
						cint = (cint << 4) | val[s.charAt(i + 1)];
						cint = (cint << 4) | val[s.charAt(i + 2)];
						i += 2;
					} else {
						cint = (cint << 4) | val[s.charAt(i + 2)];
						cint = (cint << 4) | val[s.charAt(i + 3)];
						cint = (cint << 4) | val[s.charAt(i + 4)];
						cint = (cint << 4) | val[s.charAt(i + 5)];
						i += 5;
					}
					sbuf.append((char) cint);
				} else {
					sbuf.append((char) ch);
				}
				i++;
			}
			return sbuf.toString();
		}

		public static void Escape(String[] args) {
			String stest = "测试234bcd[]()<+>,.~";
			System.out.println(stest);
			System.out.println(escape(stest));
			System.out.println(unescape(escape(stest)));
		}


}

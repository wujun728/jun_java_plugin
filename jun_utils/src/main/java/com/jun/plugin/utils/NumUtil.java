package com.jun.plugin.utils;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.regex.Pattern;

import com.jun.plugin.utils.StringUtil;
import com.jun.plugin.utils.time.DateUtil;



/**
 * 数组转换
 *
 */
public class NumUtil {

	/**
	 * 从二进制转换成int
	 * @param bt
	 * @param begin
	 * @param end
	 * @return
	 * @throws Exception
	 */
	public static int bToInt(byte[] bt, int begin, int end) throws Exception {
		int mask = 0xff;
		int temp = 0;
		int res = 0;
		for (int i = end; i >= begin; i--) {
			res <<= 8;
			temp = bt[i] & mask;
			res |= temp;
		}
		return res;
	}

	/**
	 * 16进制字符串转换成byte数组
	 * @param hexString
	 * @return
	 */
	public static byte[] hexStringToBytes(String hexString) {
		if (hexString == null || hexString.equals("")) {
			return null;
		}
		hexString = hexString.toUpperCase();
		int length = hexString.length() / 2;
		char[] hexChars = hexString.toCharArray();
		byte[] d = new byte[length];
		for (int i = 0; i < length; i++) {
			int pos = i * 2;
			d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
		}
		return d;
	}

	private static byte charToByte(char c) {
		return (byte) "0123456789ABCDEF".indexOf(c);
	}

	public static int getPaddingLengthByte(int dataLenth) throws Exception {
		return (dataLenth & 0xFFFFFFF0) + 0x10;
	}

	public static short bToShort(byte[] bt, int begin, int end)
			throws Exception {
		int mask = 0xff;
		int temp = 0;
		short res = 0;
		for (int i = end; i <= begin; i--) {
			res <<= 8;
			temp = bt[i] & mask;
			res |= temp;
		}
		return res;
	}

	// 从int转换成二进制
	public static byte[] intToB(int num, int len) throws Exception {
		byte[] b = new byte[len];
		int mask = 0xff;
		// int move=(len-1)*8;
		for (int i = 0; i < len; i++) {
			b[i] = (byte) (num >> (i << 3) & mask);
		}
		return b;
	}

	// 从long转换成byte[]
	public static byte[] longToB(long num, int len) throws Exception {
		byte[] b = new byte[len];
		int mask = 0xff;
		// int move=(len-1)*8;
		for (int i = 0; i < len; i++) {
			b[i] = (byte) (num >> (i << 3) & mask);
		}
		return b;
	}

	public static long bToLong(byte[] bt, int begin, int end) throws Exception {
		int mask = 0xff;
		int temp = 0;
		long res = 0;
		for (int i = end; i <= begin; i--) {
			res <<= 8;
			temp = bt[i] & mask;
			res |= temp;
		}
		return res;
	}

	/**
	 * @函数功能: 10进制串转为BCD码
	 * @输入参数: 10进制串
	 * @输出结果: BCD码
	 */
	public static byte[] str2Bcd(String asc) {
		int len = asc.length();
		int mod = len % 2;

		if (mod != 0) {
			asc = "0 " + asc;
			len = asc.length();
		}

		byte abt[] = new byte[len];
		if (len >= 2) {
			len = len / 2;
		}

		byte bbt[] = new byte[len];
		abt = asc.getBytes();
		int j, k;

		for (int p = 0; p < asc.length() / 2; p++) {
			if ((abt[2 * p] >= '0') && (abt[2 * p] <= '9')) {
				j = abt[2 * p] - '0';
			} else if ((abt[2 * p] >= 'a') && (abt[2 * p] <= 'z')) {
				j = abt[2 * p] - 'a' + 0x0a;
			} else {
				j = abt[2 * p] - 'A' + 0x0a;
			}

			if ((abt[2 * p + 1] >= '0') && (abt[2 * p + 1] <= '9')) {
				k = abt[2 * p + 1] - '0';
			} else if ((abt[2 * p + 1] >= 'a') && (abt[2 * p + 1] <= 'z')) {
				k = abt[2 * p + 1] - 'a' + 0x0a;
			} else {
				k = abt[2 * p + 1] - 'A' + 0x0a;
			}

			int a = (j << 4) + k;
			byte b = (byte) a;
			bbt[p] = b;
		}
		return bbt;
	}

	/**
	 * @函数功能: BCD码转为10进制串(阿拉伯数据)
	 * @输入参数: BCD码
	 * @输出结果: 10进制串
	 */
	public static String bcd2Str(byte[] bytes) {
		StringBuffer temp = new StringBuffer(bytes.length * 2);

		for (int i = 0; i < bytes.length; i++) {
			temp.append((byte) ((bytes[i] & 0xf0) >>> 4));
			temp.append((byte) (bytes[i] & 0x0f));
		}
		return temp.toString();// .substring(0,1).equalsIgnoreCase("0")?temp.toString().substring(1):temp.toString();
	}

	// 分割byte数组
	public static byte[] byteToByte(byte[] bt, int begin, int length)
			throws Exception {
		byte[] result = new byte[length];
		System.arraycopy(bt, begin, result, 0, length);
		return result;
	}

	public static String buf2hex(byte abyte0[]) {
		String s = new String();
		for (int i = 0; i < abyte0.length; i++)
			s = s.concat(b2hex(abyte0[i]));

		// s = s.toUpperCase();
		s = s.toLowerCase();
		return s;
	}

	public static String b2hex(byte byte0) {
		String s = Integer.toHexString(byte0 & 0xff);
		String s1;
		if (s.length() == 1)
			s1 = "0".concat(s);
		else
			s1 = s;
		return s1.toUpperCase();
	}

	public static void main(String[] args) {
		byte[] aa = str2Bcd(DateUtil
				.getCurDateTime(DateUtil.DATE_FORMAT_COMPACTFULL));
		byte[] bb = str2Bcd("00000011");
		System.out.println(bb.length + "**" + bcd2Str(bb));
		try {
			byte[] cc = new byte[] { (byte) 25, (byte) 00 };
			System.out.println(bToInt(cc, 0, 1));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// System.out.println(bb[0]+"###"+bb[1]);
		// System.out.println(bcd2Str(bb));
	}
	/**
	 * 默认除法运算精度
	 */
	private static final int DEF_DIV_SCALE = 10;

	/**
	 * 这个类不能实例化
	 */
	 

	/**
	 * 
	 * 提供精确的加法运算。
	 * 
	 * @param v1
	 *            被加数
	 * 
	 * @param v2
	 *            加数
	 * 
	 * @return 两个参数的和
	 */
	public static double add(double v1, double v2) {
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.add(b2).doubleValue();
	}

	/**
	 * 
	 * 提供精确的减法运算。
	 * 
	 * @param v1
	 *            被减数
	 * 
	 * @param v2
	 *            减数
	 * 
	 * @return 两个参数的差
	 */
	public static double sub(double v1, double v2) {
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.subtract(b2).doubleValue();
	}

	/**
	 * 
	 * 提供精确的乘法运算。
	 * 
	 * @param v1
	 *            被乘数
	 * 
	 * @param v2
	 *            乘数
	 * 
	 * @return 两个参数的积
	 */

	public static double mul(double v1, double v2) {
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.multiply(b2).doubleValue();
	}

	/**
	 * 
	 * 提供（相对）精确的除法运算，当发生除不尽的情况时，精确到
	 * 
	 * 小数点以后10位，以后的数字四舍五入。
	 * 
	 * @param v1
	 *            被除数
	 * 
	 * @param v2
	 *            除数
	 * 
	 * @return 两个参数的商
	 */

	public static double div(double v1, double v2) {
		return div(v1, v2, DEF_DIV_SCALE);
	}

	/**
	 * 
	 * 提供（相对）精确的除法运算。当发生除不尽的情况时，由scale参数指
	 * 
	 * 定精度，以后的数字四舍五入。
	 * 
	 * @param v1
	 *            被除数
	 * 
	 * @param v2
	 *            除数
	 * 
	 * @param scale
	 *            表示表示需要精确到小数点以后几位。
	 * 
	 * @return 两个参数的商
	 */
	public static double div(double v1, double v2, int scale) {
		if (scale < 0) {
			throw new IllegalArgumentException("The scale must be a positive integer or zero");
		}
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	/**
	 * 
	 * 提供精确的小数位四舍五入处理。
	 * 
	 * @param v
	 *            需要四舍五入的数字
	 * 
	 * @param scale
	 *            小数点后保留几位
	 * 
	 * @return 四舍五入后的结果
	 */
	public static double round(double v, int scale) {
		if (scale < 0) {
			throw new IllegalArgumentException("The scale must be a positive integer or zero");
		}
		BigDecimal b = new BigDecimal(Double.toString(v));
		BigDecimal one = new BigDecimal("1");
		return b.divide(one, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
	}
	

	/* (non-Javadoc)
	 * <p>Title: isPattern</p> 
	 * <p>Description: </p> 
	 * @param pattern
	 * @return 
	 * @see com.sysmanage.common.tools.macher.Matcher#isPattern(java.lang.String) 
	 */
	public boolean isPattern(String pattern) {
		return (pattern.indexOf('*') != -1 || pattern.indexOf('?') != -1 || pattern.indexOf('-') != -1);
	}

	/* 通过给定的模板判断对象值是否与该模板相匹配
	 * <p>Title: match</p> 
	 * <p>Description: </p> 
	 * @param pattern 模板
	 * @param value 待匹配的对象,其类型必须是<code>Number</code>的子类
	 * @return 如果对象与模板匹配返回true,否则返回false
	 * @see com.sysmanage.common.tools.macher.Matcher#match(java.lang.String, java.lang.Object) 
	 */
	public boolean match(String pattern, Object value) {
		if(!(value instanceof Number))
			return false;
		
		String str = ((Number)value).toString();

		if(pattern == null || pattern.trim().equals("") || str == null || str.trim().equals(""))
			return false;
		
		if(!Pattern.matches("[\\d|\\.]*",str))
			return false;
		
		pattern = pattern.replaceAll("[.]", "\\\\.");
		StringBuffer sb = new StringBuffer("");
		for (int i = 0; i < pattern.length(); i++) {
			char subChar = pattern.charAt(i);
			switch(subChar){
				case '*':
					sb.append("\\d*");
					break;
				case '?':
					sb.append("\\d{1}");
					break;
				default:
					sb.append(subChar);
			}
		}
		
		String[] patterns = sb.toString().split("-");
		if(patterns.length <= 1)
			pattern = patterns[0];
		else{
			sb = new StringBuffer("");
			for (int i = 0; i < patterns.length; i++) {
				String element = patterns[i];
				if(i != 0)
					sb.append(element.charAt(0) + "]{1}" + element.substring(1));
				if(i != patterns.length - 1){
					sb.append(element.substring(0, element.length() - 1) + "[" + element.charAt(element.length()-1) + "-");
				}
			}
		}
		pattern = sb.toString();
		return Pattern.matches(pattern,str);
	}
	
	public static void main1(String[] args) {
		NumUtil matcher = new NumUtil();
		System.out.println(matcher.match("34-5.5*", new Double(34.5345)));
		
//		System.out.println(Pattern.matches("34\\d*5","34345"));
	}
	
  
	/*******************************************************************************************/
	/*******************************************************************************************/

	/** 金额为分的格式 */
	public static final String CURRENCY_FEN_REGEX = "\\-?[0-9]+";

	/**
	 * 将分为单位的转换为元并返回金额格式的字符串 （除100）
	 * 
	 * @param amount
	 * @return
	 * @throws Exception
	 */
	public static String changeF2Y(Long amount) throws Exception {

		if (!amount.toString().matches(CURRENCY_FEN_REGEX)) {
			throw new Exception("金额格式有误");
		}

		int flag = 0;
		String amString = amount.toString();
		if (amString.charAt(0) == '-') {
			flag = 1;
			amString = amString.substring(1);
		}
		StringBuffer result = new StringBuffer();
		if (amString.length() == 1) {
			result.append("0.0").append(amString);
		} else if (amString.length() == 2) {
			result.append("0.").append(amString);
		} else {
			String intString = amString.substring(0, amString.length() - 2);
			for (int i = 1; i <= intString.length(); i++) {
				if ((i - 1) % 3 == 0 && i != 1) {
					result.append(",");
				}
				result.append(intString.substring(intString.length() - i, intString.length() - i + 1));
			}
			result.reverse().append(".").append(amString.substring(amString.length() - 2));
		}
		if (flag == 1) {
			return "-" + result.toString();
		} else {
			return result.toString();
		}
	}

	/**
	 * 将分为单位的转换为元 （除100）
	 * 
	 * @param amount
	 * @return
	 * @throws Exception
	 */
	public static String changeF2Y(String amount) throws Exception {

		if (StringUtil.isEmpty(amount)) {
			return "0.00";
		}

		if (!amount.matches(CURRENCY_FEN_REGEX)) {
			throw new Exception("金额格式有误");
		}
		return BigDecimal.valueOf(Long.valueOf(amount)).divide(new BigDecimal(100)).toString();
	}

	/**
	 * 将元为单位的转换为分 （乘100）
	 * 
	 * @param amount
	 * @return
	 */
	public static String changeY2F(Long amount) {
		return BigDecimal.valueOf(amount).multiply(new BigDecimal(100)).toString();
	}

	/**
	 * 将元为单位的转换为分 替换小数点，支持以逗号区分的金额
	 * 
	 * @param amount
	 * @return
	 */
	public static String changeY2F(String amount) {

		if (StringUtil.isEmpty(amount)) {
			return "0.00";
		}

		String currency = amount.replaceAll("\\$|\\￥|\\,", ""); // 处理包含, ￥
																// 或者$的金额
		int index = currency.indexOf(".");
		int length = currency.length();
		Long amLong = 0l;
		if (index == -1) {
			amLong = Long.valueOf(currency + "00");
		} else if (length - index >= 3) {
			amLong = Long.valueOf((currency.substring(0, index + 3)).replace(".", ""));
		} else if (length - index == 2) {
			amLong = Long.valueOf((currency.substring(0, index + 2)).replace(".", "") + 0);
		} else {
			amLong = Long.valueOf((currency.substring(0, index + 1)).replace(".", "") + "00");
		}
		return amLong.toString();
	}

	public static void main11(String[] args) {
		try {
			System.out.println("结果：" + changeF2Y("-000a00"));
		} catch (Exception e) {
			System.out.println("----------->>>" + e.getMessage());
			// return e.getErrorCode();
		}
		// System.out.println("结果："+changeY2F("1.00000000001E10"));

		System.out.println(NumUtil.changeY2F("1000000000000000"));
		System.out.println(Long.parseLong(NumUtil.changeY2F("1000000000000000")));
		System.out.println(Integer.parseInt(NumUtil.changeY2F("10000000")));
		System.out.println(Integer.MIN_VALUE);
		long a = 0;
		System.out.println(a);

	}
	/*******************************************************************************************/
	/*******************************************************************************************/
	/*******************************************************************************************/

	public static void main222(String[] args) {
		byte[] aa = str2Bcd(DateUtil
				.getCurDateTime(DateUtil.DATE_FORMAT_COMPACTFULL));
		byte[] bb = str2Bcd("00000011");
		System.out.println(bb.length + "**" + bcd2Str(bb));
		try {
			byte[] cc = new byte[] { (byte) 25, (byte) 00 };
			System.out.println(bToInt(cc, 0, 1));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// System.out.println(bb[0]+"###"+bb[1]);
		// System.out.println(bcd2Str(bb));
	}
	
	/*******************************************************************************************/
	/*******************************************************************************************/
	/*******************************************************************************************/
	
	


	private static String encodingCharset = "UTF-8";
	
	/**
	 * 生成hmac方法
	 * 
	 * @param p0_Cmd 业务类型
	 * @param p1_MerId 商户编号
	 * @param p2_Order 商户订单号
	 * @param p3_Amt 支付金额
	 * @param p4_Cur 交易币种
	 * @param p5_Pid 商品名称
	 * @param p6_Pcat 商品种类
	 * @param p7_Pdesc 商品描述
	 * @param p8_Url 商户接收支付成功数据的地址
	 * @param p9_SAF 送货地址
	 * @param pa_MP 商户扩展信息
	 * @param pd_FrpId 银行编码
	 * @param pr_NeedResponse 应答机制
	 * @param keyValue 商户密钥
	 * @return
	 */
	public static String buildHmac(String p0_Cmd,String p1_MerId,
			String p2_Order, String p3_Amt, String p4_Cur,String p5_Pid, String p6_Pcat,
			String p7_Pdesc,String p8_Url, String p9_SAF,String pa_MP,String pd_FrpId,
			String pr_NeedResponse,String keyValue) {
		StringBuilder sValue = new StringBuilder();
		// 业务类型
		sValue.append(p0_Cmd);
		// 商户编号
		sValue.append(p1_MerId);
		// 商户订单号
		sValue.append(p2_Order);
		// 支付金额
		sValue.append(p3_Amt);
		// 交易币种
		sValue.append(p4_Cur);
		// 商品名称
		sValue.append(p5_Pid);
		// 商品种类
		sValue.append(p6_Pcat);
		// 商品描述
		sValue.append(p7_Pdesc);
		// 商户接收支付成功数据的地址
		sValue.append(p8_Url);
		// 送货地址
		sValue.append(p9_SAF);
		// 商户扩展信息
		sValue.append(pa_MP);
		// 银行编码
		sValue.append(pd_FrpId);
		// 应答机制
		sValue.append(pr_NeedResponse);
		
		return NumUtil.hmacSign(sValue.toString(), keyValue);
	}
	
	/**
	 * 返回校验hmac方法
	 * 
	 * @param hmac 支付网关发来的加密验证码
	 * @param p1_MerId 商户编号
	 * @param r0_Cmd 业务类型
	 * @param r1_Code 支付结果
	 * @param r2_TrxId 易宝支付交易流水号
	 * @param r3_Amt 支付金额
	 * @param r4_Cur 交易币种
	 * @param r5_Pid 商品名称
	 * @param r6_Order 商户订单号
	 * @param r7_Uid 易宝支付会员ID
	 * @param r8_MP 商户扩展信息
	 * @param r9_BType 交易结果返回类型
	 * @param keyValue 密钥
	 * @return
	 */
	public static boolean verifyCallback(String hmac, String p1_MerId,
			String r0_Cmd, String r1_Code, String r2_TrxId, String r3_Amt,
			String r4_Cur, String r5_Pid, String r6_Order, String r7_Uid,
			String r8_MP, String r9_BType, String keyValue) {
		StringBuilder sValue = new StringBuilder();
		// 商户编号
		sValue.append(p1_MerId);
		// 业务类型
		sValue.append(r0_Cmd);
		// 支付结果
		sValue.append(r1_Code);
		// 易宝支付交易流水号
		sValue.append(r2_TrxId);
		// 支付金额
		sValue.append(r3_Amt);
		// 交易币种
		sValue.append(r4_Cur);
		// 商品名称
		sValue.append(r5_Pid);
		// 商户订单号
		sValue.append(r6_Order);
		// 易宝支付会员ID
		sValue.append(r7_Uid);
		// 商户扩展信息
		sValue.append(r8_MP);
		// 交易结果返回类型
		sValue.append(r9_BType);
		String sNewString = NumUtil.hmacSign(sValue.toString(), keyValue);
		return sNewString.equals(hmac);
	}
	
	/**
	 * @param aValue
	 * @param aKey
	 * @return
	 */
	public static String hmacSign(String aValue, String aKey) {
		byte k_ipad[] = new byte[64];
		byte k_opad[] = new byte[64];
		byte keyb[];
		byte value[];
		try {
			keyb = aKey.getBytes(encodingCharset);
			value = aValue.getBytes(encodingCharset);
		} catch (UnsupportedEncodingException e) {
			keyb = aKey.getBytes();
			value = aValue.getBytes();
		}

		Arrays.fill(k_ipad, keyb.length, 64, (byte) 54);
		Arrays.fill(k_opad, keyb.length, 64, (byte) 92);
		for (int i = 0; i < keyb.length; i++) {
			k_ipad[i] = (byte) (keyb[i] ^ 0x36);
			k_opad[i] = (byte) (keyb[i] ^ 0x5c);
		}

		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {

			return null;
		}
		md.update(k_ipad);
		md.update(value);
		byte dg[] = md.digest();
		md.reset();
		md.update(k_opad);
		md.update(dg, 0, 16);
		dg = md.digest();
		return toHex(dg);
	}

	public static String toHex(byte input[]) {
		if (input == null)
			return null;
		StringBuffer output = new StringBuffer(input.length * 2);
		for (int i = 0; i < input.length; i++) {
			int current = input[i] & 0xff;
			if (current < 16)
				output.append("0");
			output.append(Integer.toString(current, 16));
		}

		return output.toString();
	}

	/**
	 * 
	 * @param args
	 * @param key
	 * @return
	 */
	public static String getHmac(String[] args, String key) {
		if (args == null || args.length == 0) {
			return (null);
		}
		StringBuffer str = new StringBuffer();
		for (int i = 0; i < args.length; i++) {
			str.append(args[i]);
		}
		return (hmacSign(str.toString(), key));
	}

	/**
	 * @param aValue
	 * @return
	 */
	public static String digest(String aValue) {
		aValue = aValue.trim();
		byte value[];
		try {
			value = aValue.getBytes(encodingCharset);
		} catch (UnsupportedEncodingException e) {
			value = aValue.getBytes();
		}
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("SHA");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		}
		return toHex(md.digest(value));

	}
	
	
	/*******************************************************************************************/
	/*******************************************************************************************/
	/*******************************************************************************************/
	
}

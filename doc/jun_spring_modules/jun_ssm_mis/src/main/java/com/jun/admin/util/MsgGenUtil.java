package com.jun.admin.util;


/**
 * 数组转换
 *
 */
public class MsgGenUtil {

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
}

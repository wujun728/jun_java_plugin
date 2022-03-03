package com.jun.admin.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * 字符串的编码方式转换辅助类
 */
public abstract class CharsetUtil {
	/** 7位ASCII字符，也叫作ISO646-US、Unicode字符集的基本拉丁块 */
	public static final String US_ASCII = "US-ASCII";

	/** ISO拉丁字母表 No.1，也叫做ISO-LATIN-1 */
	public static final String ISO_8859_1 = "ISO-8859-1";

	/** 8 位 UCS 转换格式 */
	public static final String UTF_8 = "UTF-8";

	/** 16 位 UCS 转换格式，Big Endian(最低地址存放高位字节）字节顺序 */
	public static final String UTF_16BE = "UTF-16BE";

	/** 16 位 UCS 转换格式，Litter Endian（最高地址存放地位字节）字节顺序 */
	public static final String UTF_16LE = "UTF-16LE";

	/** 16 位 UCS 转换格式，字节顺序由可选的字节顺序标记来标识 */
	public static final String UTF_16 = "UTF-16";

	/** 中文超大字符集 **/
	public static final String GBK = "GBK";

	public static final String GB2312 = "GB2312";

	/** 将字符编码转换成US-ASCII码 */
	public static String toASCII(String str) throws UnsupportedEncodingException {
		return changeCharset(str, US_ASCII);
	}

	/** 将字符编码转换成ISO-8859-1 */
	public static String toISO_8859_1(String str) throws UnsupportedEncodingException {
		return changeCharset(str, ISO_8859_1);
	}

	/** 将字符编码转换成UTF-8 */
	public static String toUTF_8(String str) throws UnsupportedEncodingException {
		return changeCharset(str, UTF_8);
	}

	/** 将字符编码转换成UTF-16BE */
	public static String toUTF_16BE(String str) throws UnsupportedEncodingException {
		return changeCharset(str, UTF_16BE);
	}

	/** 将字符编码转换成UTF-16LE */
	public static String toUTF_16LE(String str) throws UnsupportedEncodingException {
		return changeCharset(str, UTF_16LE);
	}

	/** 将字符编码转换成UTF-16 */
	public static String toUTF_16(String str) throws UnsupportedEncodingException {
		return changeCharset(str, UTF_16);
	}

	/** 将字符编码转换成GBK */
	public static String toGBK(String str) throws UnsupportedEncodingException {
		return changeCharset(str, GBK);
	}

	/** 将字符编码转换成GB2312 */
	public static String toGB2312(String str) throws UnsupportedEncodingException {
		return changeCharset(str, GB2312);
	}

	/**
	 * 字符串编码转换的实现方法
	 * 
	 * @param str
	 *            待转换的字符串
	 * @param newCharset
	 *            目标编码
	 */
	private static String changeCharset(String str, String newCharset) throws UnsupportedEncodingException {
		if (str != null) {
			// 用默认字符编码解码字符串。与系统相关，中文windows默认为GB2312
			byte[] bs = str.getBytes();
			return new String(bs, newCharset); // 用新的字符编码生成字符串
		}
		return null;
	}

	/**
	 * 字符串编码转换的实现方法
	 * 
	 * @param str
	 *            待转换的字符串
	 * @param oldCharset
	 *            源字符集
	 * @param newCharset
	 *            目标字符集
	 */
	public static String changeCharset(String str, String oldCharset, String newCharset) throws UnsupportedEncodingException {
		if (str != null) {
			// 用源字符编码解码字符串
			byte[] bs = str.getBytes(oldCharset);
			return new String(bs, newCharset);
		}
		return null;
	}

	/**
	 * GBK编码到Unicode编码转换
	 * 
	 * @param dataStr
	 * @return
	 */
	public static String GBK2Unicode(String dataStr) {
		StringBuffer buffer = new StringBuffer();
		if (null == dataStr)
			return "";
		for (int i = 0; i < dataStr.length(); i++) {
			buffer.append("&#x");
			char c = dataStr.charAt(i);
			int t = (int) c;

			String last = Integer.toHexString(t / (16 * 16)).toUpperCase();
			if (last.length() < 2) {
				buffer.append("0" + last);
			} else {
				buffer.append(last);
			}
			String frist = Integer.toHexString(t % (16 * 16)).toUpperCase();
			if (frist.length() < 2) {
				buffer.append("0" + frist);
			} else {
				buffer.append(frist);
			}
			buffer.append(";");
		}
		return buffer.toString();
	}

	/**
	 * <p>
	 * 判断文件的编码格式。
	 * <p>
	 * <list>
	 * <li>前两字节为0xefbb: "UTF-8";
	 * <li>前两字节为EFBB 0xfffe: "Unicode";
	 * <li>前两字节为FFFE 0xfeff: "Unicode   big   endian";
	 * <li>前两字节为FEFF ANSI：无格式定义 <list>
	 * 
	 * @param file
	 *            - 文件
	 * @return
	 * @throws IOException
	 */
	public static String getFileCharset(File file) throws IOException {

		// 默认字符集编码格式
		String charset = "GBK";

		byte[] first3Bytes = new byte[3];

		boolean checked = false;
		BufferedInputStream bis = null;
		try {
			bis = new BufferedInputStream(new FileInputStream(file));
			bis.mark(0);

			// 获取前3个字符 BOM
			int read = bis.read(first3Bytes, 0, 3);
			if (read == -1) {
				return charset;
			}
			if (first3Bytes[0] == (byte) 0xFF && first3Bytes[1] == (byte) 0xFE) {
				charset = "UTF-16LE";
				checked = true;
			} else if (first3Bytes[0] == (byte) 0xFE && first3Bytes[1] == (byte) 0xFF) {
				charset = "UTF-16BE";
				checked = true;
			} else if (first3Bytes[0] == (byte) 0xEF && first3Bytes[1] == (byte) 0xBB && first3Bytes[2] == (byte) 0xBF) {
				charset = "UTF-8";
				checked = true;
			}

			bis.reset();
			if (!checked) {
				int loc = 0;
				while ((read = bis.read()) != -1) {
					loc++;
					if (read >= 0xF0)
						break;
					if (0x80 <= read && read <= 0xBF) // 单独出现BF以下的，也算是GBK
						break;
					if (0xC0 <= read && read <= 0xDF) {
						read = bis.read();
						if (0x80 <= read && read <= 0xBF)// 双字节 (0xC0 - 0xDF)
							// (0x80 - 0xBF),也可能在GB编码内
							continue;
						else
							break;
					} else if (0xE0 <= read && read <= 0xEF) {// 也有可能出错，但是几率较小
						read = bis.read();
						if (0x80 <= read && read <= 0xBF) {
							read = bis.read();
							if (0x80 <= read && read <= 0xBF) {
								charset = "UTF-8";
								break;
							} else
								break;
						} else
							break;
					}
				}
				// BOM信息
				// System.out.println(loc + " " + Integer.toHexString(read));
			}

			return charset;
		} finally {
			bis.close();
		}
	}

	/**
	 * 将字符编码转换成UTF-8
	 * 
	 * @throws UnsupportedEncodingException
	 */
	public static String decodeToUTF8(String str) throws UnsupportedEncodingException {
		return java.net.URLDecoder.decode(str, UTF_8);
	}

	/**
	 * 将字符编码转换成UTF-8
	 * 
	 * @throws UnsupportedEncodingException
	 */
	public static String encodeToUTF8(String str) throws UnsupportedEncodingException {
		return java.net.URLEncoder.encode(str, UTF_8);
	}

	public static void main(String[] args) throws UnsupportedEncodingException {
		String str = "This is a 中文的 String!";
		System.out.println("str：" + str);

		String gbk = CharsetUtil.toGBK(str);
		System.out.println("转换成GBK码：" + gbk);
		System.out.println();

		String ascii = CharsetUtil.toASCII(str);
		System.out.println("转换成US-ASCII：" + ascii);
		System.out.println();

		String iso88591 = CharsetUtil.toISO_8859_1(str);
		System.out.println("转换成ISO-8859-1码：" + iso88591);
		System.out.println();

		gbk = CharsetUtil.changeCharset(iso88591, ISO_8859_1, GBK);
		System.out.println("再把ISO-8859-1码的字符串转换成GBK码：" + gbk);
		System.out.println();

		String utf8 = CharsetUtil.toUTF_8(str);
		System.out.println();
		System.out.println("转换成UTF-8码：" + utf8);

		String utf16be = CharsetUtil.toUTF_16BE(str);
		System.out.println("转换成UTF-16BE码：" + utf16be);

		gbk = CharsetUtil.changeCharset(utf16be, UTF_16BE, GBK);
		System.out.println("再把UTF-16BE编码的字符转换成GBK码：" + gbk);
		System.out.println();

		String utf16le = CharsetUtil.toUTF_16LE(str);
		System.out.println("转换成UTF-16LE码：" + utf16le);

		gbk = CharsetUtil.changeCharset(utf16le, UTF_16LE, GBK);
		System.out.println("再把UTF-16LE编码的字符串转换成GBK码：" + gbk);
		System.out.println();

		String utf16 = CharsetUtil.toUTF_16(str);
		System.out.println("转换成UTF-16码：" + utf16);

		String gb2312 = CharsetUtil.changeCharset(utf16, UTF_16, GB2312);
		System.out.println("再把UTF-16编码的字符串转换成GB2312码：" + gb2312);
	}

}

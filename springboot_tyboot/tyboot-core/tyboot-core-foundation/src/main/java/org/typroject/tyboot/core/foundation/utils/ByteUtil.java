package org.typroject.tyboot.core.foundation.utils;

/**
 * 
 * @author Wujun
 * 字节工具类，提供字节的基本转换
 */
public class ByteUtil {

	private final static char[] digits = {
			'0' , '1' , '2' , '3' , '4' , '5' ,
	        '6' , '7' , '8' , '9' , 'a' , 'b' ,
	        'c' , 'd' , 'e' , 'f' , 'g' , 'h' ,
	        'i' , 'j' , 'k' , 'l' , 'm' , 'n' ,
	        'o' , 'p' , 'q' , 'r' , 's' , 't' ,
	        'u' , 'v' , 'w' , 'x' , 'y' , 'z'
	};
	
	public static String toBinaryString(byte b) {
		char[] buf = new char[8];
		int charPos = 8;
		do{
			buf[--charPos] = digits[b & 0x01];
			b >>>= 1;
		}while(charPos > 0);
		return new String(buf);
	}
	
	public static String toHexString(byte b){
		return new String(new char[]{
				digits[b >>> 4 & 0x0f],
				digits[b & 0x0f]
		});
	}
	/**
	 * convert byte array to literal represented by the specified radix,hex or binary
	 * @param source byte array to convert
	 * @param radix convert radix
	 * @return literal string ,if the byte array is empty, return null;
	 * @throws IllegalArgumentException
	 */
	public static String toString(byte[] source,int radix){
		if(source == null || source.length == 0) return null;
		StringBuilder builder = new StringBuilder();
		switch (radix) {
			case 16:
				for(byte b : source){
					builder.append(toHexString(b));
				}
				break;
			case 2:
				for(byte b : source){
					builder.append(toBinaryString(b));
				}
				break;
			default:
				throw new IllegalArgumentException("radix is not valid,expected 2 or 16");
		}
		return builder.toString();
	}
	
	public static int toInt(byte[] source) {
		if (source == null || source.length == 0)
			return 0;
		if (source.length > 4)
			throw new IllegalArgumentException("int value must be less than 32 bits");
		int result = 0;
		for (int i = source.length - 1, shift = 0; i >= 0; i--, shift++) {
			result |= ((source[i] & 0xff) << (shift * 8));
		}
		return result;
	}

	public static long toLong(byte[] source) {
		if (source == null || source.length == 0)
			return 0;
		if (source.length > 8)
			throw new IllegalArgumentException("long value must be less than 64 bits");
		long result = 0;
		for (int i = source.length - 1, shift = 0; i >= 0; i--, shift++) {
			result |= (long) (source[i] & 0xff) << (shift * 8);
		}
		return result;
	}

	public static byte[] longToBytes(long num) {
		byte[] b = new byte[8];
		for (int i = 0; i < 8; i++) {
			b[i] = (byte) (num >>> (56 - (i * 8)));
		}
		return b;
	}

	public static byte[] intToBytes(int num) {
		byte[] b = new byte[4];
		for (int i = 0; i < 4; i++) {
			b[i] = (byte) (num >>> (24 - (i * 8)));
		}
		return b;
	}
	
	public static void main(String[] args) {
		System.out.println(toString(new byte[]{-128,127,32,64},16));
		System.out.println(toInt(new byte[]{127,-1,-1,-1}));
		System.out.println(toLong(new byte[]{127,-1,-1,-1,-1,-1,-1,-1}));
		
		System.out.println(toLong(longToBytes(Long.MAX_VALUE)));
		System.out.println(toInt(intToBytes(Integer.MAX_VALUE)));
		
		System.out.println(toString(intToBytes(2), 2));
	}
}

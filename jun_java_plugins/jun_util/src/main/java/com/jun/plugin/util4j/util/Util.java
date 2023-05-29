package com.jun.plugin.util4j.util;

public class Util {

	/**
	 * 取整数某二进制位的值
	 * @param number
	 * @param pos 0开始
	 * @return
	 */
	public static int getPosValue(int number,int pos)
	{
		return (number & (0x1<<pos))>>>pos;
	}
}

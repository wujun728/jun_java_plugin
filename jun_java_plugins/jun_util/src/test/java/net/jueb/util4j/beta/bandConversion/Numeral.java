package net.jueb.util4j.beta.bandConversion;

import java.util.Arrays;

/**
 * 数学符号
 * @author Administrator
 *
 */
public class Numeral {
	/**
	 * 符号对应的持久化字节数据
	 */
	private final byte[] data;
	private  String viewStr="null";
	
	public Numeral(byte[] data,String viewStr) {
		this(data);
		this.viewStr=viewStr;
	}
	public Numeral(byte[] data) {
		if(data.length<=0)
		{
			throw new RuntimeException("一个符号必须有一个唯一对应的字节表示");
		}else
		{
			this.data=data;
		}
	}

	public byte[] getData()
	{
		return this.data;
	}
	
	/**
	 * 获取该符号的字符串形式
	 * @return
	 */
	public String getViewStr()
	{
		return this.viewStr;
	}
	
	@Override
	public String toString() {
		return "Radix [data=" + Arrays.toString(data) + ", viewStr=" + viewStr
				+ "]";
	}
	
}

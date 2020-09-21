package cn.skynethome.redisx.common;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

/**
  * 项目名称:[redisx]
  * 包:[cn.skynethome.redisx.common]    
  * 文件名称:[ByteUtils]  
  * 描述:[一句话描述该文件的作用]
  * 创建人:[陆文斌]
  * 创建时间:[2017年1月3日 上午11:36:12]   
  * 修改人:[陆文斌]   
  * 修改时间:[2017年1月3日 上午11:36:12]   
  * 修改备注:[说明本次修改内容]  
  * 版权所有:luwenbin006@163.com
  * 版本:[v1.0]
 */
public class ByteUtils
{
	public static byte[] longToByte(long number)
	{
		long temp = number;
		byte[] b = new byte[8];
		for (int i = 0; i < b.length; i++)
		{
			b[i] = new Long(temp & 0xff).byteValue(); // 将最低位保存在最低位 
			temp = temp >> 8; // 向右移8位 
		}
		return b;
	}

	//byte数组转成long 
	public static long byteToLong(byte[] b)
	{
		if (b == null)
		{
			return 0;
		}
		long s = 0;
		long s0 = b[0] & 0xff; // 最低位 
		long s1 = b[1] & 0xff;
		long s2 = b[2] & 0xff;
		long s3 = b[3] & 0xff;
		long s4 = b[4] & 0xff; // 最低位 
		long s5 = b[5] & 0xff;
		long s6 = b[6] & 0xff;
		long s7 = b[7] & 0xff;

		// s0不变 
		s1 <<= 8;
		s2 <<= 16;
		s3 <<= 24;
		s4 <<= 8 * 4;
		s5 <<= 8 * 5;
		s6 <<= 8 * 6;
		s7 <<= 8 * 7;
		s = s0 | s1 | s2 | s3 | s4 | s5 | s6 | s7;
		return s;
	}

	public static byte[] intToByte(int number)
	{
		int temp = number;
		byte[] b = new byte[4];
		for (int i = 0; i < b.length; i++)
		{
			b[i] = new Integer(temp & 0xff).byteValue(); // 将最低位保存在最低位 
			temp = temp >> 8; // 向右移8位 
		}
		return b;
	}

	public static int byteToInt(byte[] b)
	{
		if (b == null)
		{
			return 0;
		}
		int s = 0;
		int s0 = b[0] & 0xff; // 最低位 
		int s1 = b[1] & 0xff;
		int s2 = b[2] & 0xff;
		int s3 = b[3] & 0xff;
		s3 <<= 24;
		s2 <<= 16;
		s1 <<= 8;
		s = s0 | s1 | s2 | s3;
		return s;
	}

	public static byte[] shortToByte(short number)
	{
		int temp = number;
		byte[] b = new byte[2];
		for (int i = 0; i < b.length; i++)
		{
			b[i] = new Integer(temp & 0xff).byteValue(); //将最低位保存在最低位 
			temp = temp >> 8; // 向右移8位 
		}
		return b;
	}

	public static short byteToShort(byte[] b)
	{
		if (b == null)
		{
			return 0;
		}
		short s = 0;
		short s0 = (short) (b[0] & 0xff); // 最低位 
		short s1 = (short) (b[1] & 0xff);
		s1 <<= 8;
		s = (short) (s0 | s1);
		return s;
	}

	public static byte[] stringToByte(String str)
	{
		if (null==str || "".equals(str))
		{
			return null;
		}
		return str.getBytes(Charset.forName("UTF-8"));
	}

	public static String byteToString(byte[] b)
	{
		if (b == null)
		{
			return null;
		}
		try
		{
			return new String(b, "UTF-8");
		} catch (UnsupportedEncodingException e)
		{
		}
		return null;
	}

	public static byte[] append(byte[] src, byte[] dest)
	{
		if (src == null)
		{
			return null;
		}
		if (dest == null)
		{
			return src;
		}
		byte[] _byte = new byte[src.length + dest.length];
		System.arraycopy(src, 0, _byte, 0, src.length);
		System.arraycopy(dest, 0, _byte, src.length, dest.length);
		return _byte;
	}

}
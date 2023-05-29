package net.jueb.util4j.beta.serializable.bytesMap;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import org.apache.commons.lang.StringUtils;


/**
 * 获取返回数据的参数
 *
 */
public class DataTools {
	
	/**
	 * 根据指定范围，读取字节数组
	 * 从0开始,[start,end]
	 * @param start
	 * @param end
	 * @return
	 */
	public byte[] readByteArray(byte[] data,int start,int end)
	{
		if(start>data.length||start>end)
		{
			System.out.println("参数错误!");
			return null;
		}
		byte[] array=new byte[end-start+1];//1~3=3-1+1=3
		System.arraycopy(data, start, array, 0, array.length);
		return array;
	}
	/**
	 * 根据指定范围，读取字节数组的内容，并返回指定编码格式的文本
	 * 从0开始,[start,end]
	 * @param data byte数据
	 * @param start 起始位置
	 * @param end   结束位置
	 * @param encode 返回文本编码
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	public String readByteArray(byte[] data,int start,int end,String encode) throws UnsupportedEncodingException
	{
		if(start>data.length||start>end)
		{
			System.out.println("参数错误!");
			return null;
		}
		byte[] array=new byte[end-start+1];//1~3=3-1+1=3
		System.arraycopy(data, start, array, 0, array.length);
		return new String(array,encode);
	}
	/**
	 * 使用指定编码解码字节数组
	 * @param data
	 * @param encode
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public String readByteArray(byte[] data,String encode) throws UnsupportedEncodingException
	{
		return new String(data,encode);
	}
	/**
	 * 根据起始和结束16进制字符串取byte数据中间数据
	 * @param data  类型：[0, 7, -63, -108]
	 * @param startHexStr 类型：[0, 7, -63, -108]或者[0x0C, 0xB6, 0x05, 0x00]
	 * @param endHexStr   类型：[0x0C, 0xB6, 0x05, 0x00]
	 * @return 类型：[0C, B6, 05, 00]
	 */
	public byte[] subByteArrays(byte[] data,byte[] startByte,byte[] endByte)
	{
		if(data==null||data.length<=0)
		{
			System.out.println("data参数错误!");
			return null;
		}
		String[] s=byteArrayToHexArray(startByte);
		String[] e=byteArrayToHexArray(endByte);
		String[] hexData=SubHexArrays(data, s, e);
		return hexArrayToBtyeArray(hexData);
	}
	public byte[] subByteArrays(byte[] data,byte[] startArray)
	{
		if(data==null||data.length<=0)
		{
			System.out.println("data参数错误!");
			return null;
		}
		String[] dataHex=byteArrayToHexArray(data);//转换为hex字符数组
		String dataHexStr=Arrays.toString(dataHex);//转换为hex字符串
		dataHexStr=StringUtils.substringBetween(dataHexStr, "[", "]").replaceAll("\\s", "");//去括号空格
		
		String[] startHex=byteArrayToHexArray(startArray);//转换为hex字符数组
		String startHexStr=Arrays.toString(startHex);//转换为hex字符串
		startHexStr=StringUtils.substringBetween(startHexStr, "[", "]").replaceAll("\\s", "");//去括号空格
		String resultHex=StringUtils.substringAfter(dataHexStr, startHexStr);//截取并转换为hex字符串
		if(resultHex==null)
		{
			//System.out.println("注意：截取内容为空,无数据!");
			return null;
		}
		String[] result=StringUtils.split(resultHex, ',');//重组为hexstr数组
//		System.out.println(Arrays.toString(result));
		return hexArrayToBtyeArray(result);
	}
	/**
	 * 根据起始和结束16进制字符串取byte数据中间数据
	 * @param data  类型：[0, 7, -63, -108]
	 * @param startHexStr 类型：["0C", "B6", "05", "00"]
	 * @param endHexStr   类型：["0C", "B6", "05", "00"]
	 * @return 类型：[0C, B6, 05, 00]
	 */
	public byte[] subByteArrays(byte[] data,String[] startHexStr,String[] endHexStr)
	{
		if(data==null||data.length<=0)
		{
			System.out.println("data参数错误!");
			return null;
		}
		String[] hexData=SubHexArrays(data, startHexStr, endHexStr);
		return hexArrayToBtyeArray(hexData);
	}
	/**
	 * 失败返回null
	 * 根据字符串直接截取数据
	 * @param data
	 * @param startHexStr
	 * @param endHexStr
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public byte[] subByteArraysByStr(byte[] data,String startStr,String endStr) throws UnsupportedEncodingException
	{
		if(data==null||data.length<=0)
		{
			System.out.println("data参数无效!");
			return null;
		}
		byte[] s=startStr.getBytes("utf-8");
		byte[] e=endStr.getBytes("utf-8");
		return subByteArrays(data,s, e);
	}
	public byte[] subByteArraysByStr(byte[] data,String startStr) throws UnsupportedEncodingException
	{
		if(data==null||data.length<=0)
		{
			System.out.println("startStr:"+startStr+"data参数无效!");
			return null;
		}
		String[] result=SubHexArraysByStr(data, startStr);
		return hexArrayToBtyeArray(result);
	}
	
	/**
	 * 根据byte数组起始和结束数据，截取中间数据
	 * @param data  类型：[0, 7, -63, -108]
	 * @param startByte 类型：[0, 7, -63, -108]或者[0x0C, 0xB6, 0x05, 0x00]
	 * @param endByte   类型：[0x0C, 0xB6, 0x05, 0x00]
	 * @return
	 */
	public String[] SubHexArrays(byte[] data,byte[] startByte,byte[] endByte)
	{
		if(data==null||data.length<=0)
		{
			System.out.println("data参数错误!");
			return null;
		}
		byte[] result=subByteArrays(data, startByte, endByte);
		return byteArrayToHexArray(result);
	}
	/**
	 * 失败返回null
	 * 根据hex字符数组截取数据，返回截获数据的hex数组
	 * @param data 类型：[0, 7, -63, -108]
	 * @param startHexStr 类型：["0C", "B6", "05", "00"]
	 * @param endHexStr 类型：["0C", "B6", "05", "00"]
	 * @return 类型：["0C", "B6", "05", "00"]
	 */
	public String[] SubHexArrays(byte[] data,String[] startHexStr,String[] endHexStr)
	{
		if(data==null||data.length<=0)
		{
			System.out.println("data数据无效!");
			return null;
		}
		String[] result=null;
		String[] hexarray=byteArrayToHexArray(data);
		String hexstr=Arrays.toString(hexarray);
		hexstr=StringUtils.substringBetween(hexstr, "[", "]").replaceAll("\\s", "");//原数据字符串去括号空格
		String start=Arrays.toString(startHexStr);//转换为字符串
		start=StringUtils.substringBetween(start, "[", "]").replaceAll("\\s", "");//去括号空格
		String end=Arrays.toString(endHexStr);
		end=StringUtils.substringBetween(end, "[", "]").replaceAll("\\s", "");//去括号空格
		String resultHex=StringUtils.substringBetween(hexstr, start, end);//取中间数据
		if(resultHex==null)
		{
			//System.out.println("注意：截取内容为空,无数据!");
			return null;
		}
		result=StringUtils.split(resultHex, ',');//重组为hexstr数组
		return result;
	}
	/**
	 * 使用文本截取数据的中间hex数据，文本默认使用utf-8编码
	 * @param data
	 * @param startHexStr "username"
	 * @param endHexStr	 "id"
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	public String[] SubHexArrays(byte[] data,String startStr,String endStr) throws UnsupportedEncodingException
	{
		if(data==null||data.length<=0)
		{
			System.out.println("data参数无效!");
			return null;
		}
		byte[] s=startStr.getBytes("utf-8");
		byte[] e=endStr.getBytes("utf-8");
		return SubHexArrays(data, s, e);
	}
	/**
	 * 截取以什么开头的数据
	 * @param data
	 * @param startHexStr
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public String[] SubHexArraysByStr(byte[] data,String startStr) throws UnsupportedEncodingException
	{
		if(data==null||data.length<=0)
		{
			System.out.println("data数据无效!");
			return null;
		}
		String[] result=null;
		//转换原数据
		String[] hexarray=byteArrayToHexArray(data);
		String hexstr=Arrays.toString(hexarray);
		hexstr=StringUtils.substringBetween(hexstr, "[", "]").replaceAll("\\s", "");//原数据字符串去括号空格
		////转换匹配参数数据
		byte[] startArray=startStr.getBytes("utf-8");//转换为字节
		String[] startHex=byteArrayToHexArray(startArray);//转换为hex字符数组
		String startHexStr=Arrays.toString(startHex);//转换为hex字符串
		startHexStr=StringUtils.substringBetween(startHexStr, "[", "]").replaceAll("\\s", "");//去括号空格
		String resultHex=StringUtils.substringAfter(hexstr, startHexStr);
		if(resultHex==null)
		{
			//System.out.println("注意：截取内容为空,无数据!");
			return null;
		}
		result=StringUtils.split(resultHex, ',');//重组为hexstr数组
		return result;
	}
	/**
	 * 根据字节数组拆分若干个字字节数组
	 * @param data
	 * @param separator
	 * @return
	 */
	public byte[][] getsonArrays(byte[] data,byte[] separator)
	{
		if(data==null||data.length<=0||separator==null||separator.length<=0)
		{
			System.out.println("data||separator数据无效!");
			return null;
		}
		String[] dataHexArray=byteArrayToHexArray(data);
		String dataHexStr=StringUtils.substringBetween(Arrays.toString(dataHexArray), "[", "]").replaceAll("\\s","");
		//System.out.println("待拆分字符串:"+dataHexStr);
		String[] separatorHexhArray=byteArrayToHexArray(separator);
		String separatorHexStr=StringUtils.substringBetween(Arrays.toString(separatorHexhArray), "[", "]").replaceAll("\\s","");
		//System.out.println("字符串拆分符:"+separatorHexStr);
		//得到拆分后的数组
		String[] arrays=StringUtils.splitByWholeSeparator(dataHexStr, separatorHexStr);
		//System.out.println("拆分后的数组:"+Arrays.toString(arrays));
		if(arrays==null||arrays.length<=0)
		{
			System.out.println("注意：数组拆分为0");
			return null;
		}
		byte[][] result=new byte[arrays.length][];
		//对子数组进行重组
		for(int i=0;i<arrays.length;i++)
		{
			String arrayStr=arrays[i];
			arrayStr=StringUtils.removeStart(arrayStr, ",");//去掉两端的逗号
			arrayStr=StringUtils.removeEnd(arrayStr, ",");//去掉两端的逗号
			String[] array=arrayStr.split(",");//根据子字符串中间剩余的逗号重组为hex字符串
			result[i]=hexArrayToBtyeArray(array);
		}
		return result;
	}
	/**
	 * 将文本转换为Hex文本数组
	 * @param str 文本
	 * @param encode 编码格式
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	public String[] strToHexArrays(String str,String encode) throws UnsupportedEncodingException
	{
		byte[] data=str.getBytes(encode);
		return byteArrayToHexArray(data);
	}
	/**
	 * int整数转换到内存字节数组byte[]
	 * 374284=[0C, B6, 05, 00]
	 * @param i
	 * @return
	 */
	public byte[] intToByteArray(int i)
	{
		byte[] data=new byte[4];
		data[3]=(byte)((i>>24)&0xff);//右移24位，高8位
		data[2]=(byte)((i>>16)&0xff);
		data[1]=(byte)((i>>8)&0xff);
		data[0]=(byte)(i&0xff);
		return data;
	}
	/**
	 * 将小于FF(255)的int值转换为byte类型
	 * @param i
	 * @return
	 */
	public byte intToByte(int i)
	{
		return (byte)(i&0xff);
	}
	/**
	 * 将4个字节转换为long数值在付给int类，int值最大21亿
	 * 将字节数组byte[]转int
	 * [0C, B6, 05, 00]=0005b60c=374284
	 * @param bytes
	 * @return
	 */
	public int byteArrayToInt(byte[] array)
	{
		int value=1;
		if(array==null||array.length>4||array.length==0)
		{
			System.out.println("byteArrayToInt:array不合法");
			return value-1;
		}
		String[] hexStrArray=byteArrayToHexArray(array);
		if(hexStrArray.toString().equals("[FF, FF, FF, FF]"))
		{
			return Integer.MAX_VALUE;
		}
		String str="";
		boolean flag=true;//去掉后面的00
		for(int i=hexStrArray.length-1;i>=0;i--)
		{
			if(flag)
			{
				if("00".equals(hexStrArray[i]))
				{
					flag=false;
					continue;
				}
			}
			str+=hexStrArray[i];
		}
		if(str.equals(""))
		{
			str="00";
		}
		//value=Integer.parseInt(str,16);
		value=(int) Long.parseLong(str, 16);
		return value;
	}
	public int hexStringToInt(String hexStr)
	{
		return Integer.parseInt(hexStr, 16);
	}
	/**
	 * 将数组形式的16进制文本替换为普通文本
	 * @param str
	 * @return
	 */
	public String hexStringArrayToHexStr(String[] str)
	{
		String hexStr=Arrays.toString(str);
		hexStr=StringUtils.substringBetween(hexStr, "[", "]");
		hexStr=hexStr.replaceAll("\\s", "");//去空格
		hexStr=hexStr.replaceAll(",", "");
		return hexStr;
	}
	/**
	 * 将16进制的字符数组转换为byte[]数组
	 * [0C, B6, 05, 00]=[,,,]
	 * @param hexArrays
	 * @return
	 */
	public byte[] hexArrayToBtyeArray(String[] hexArrays)
	{
		if(hexArrays==null||hexArrays.length<=0)
		{
			//System.out.println("参数错误!");
			return null;
		}
		byte[] data=new byte[hexArrays.length];
		for(int i=0;i<hexArrays.length;i++)
		{
			if(hexArrays[i].length()!=2)
			{
				System.out.println("16进制文本错误:"+hexArrays[i]);
				return null;
			}
//			Byte.decode("0x"+hexArrays[i]).p;
//			data[i]=(byte)(0xff);
			int v=Integer.parseInt(hexArrays[i], 16);//转换为int值,
			data[i]=(byte)(v&0xff);//取int的低8位
		}
		return data;
	}
	/**
	 * OK
	 * 将字节数组转换为16进制字符串文本
	 * @param data
	 * @return
	 */
	public String[] byteArrayToHexArray(byte[] data)
	{
		if(data==null||data.length<=0)
		{
			System.out.println("参数错误!");
			return null;
		}
		String[] hex=new String[data.length];
		int value=0;
		for(int i=0;i<data.length;i++)
		{
			value=data[i]&0xff;//防止出现负数，前面补0;
			if(value<=0xf)
			{//如果是F以内的数，则前面补零
				hex[i]="0"+Integer.toHexString(value).toUpperCase();
			}
			else
			{
				hex[i]=Integer.toHexString(value).toUpperCase();
			}
		}
 		return hex;
	}
	/**
	 * 根据参数取数据
	 * @param data 原数据
	 * @param str  开始字符串
	 * @param end  结束字符串 如果为null,则表示不限制结束
	 * @param s    二次截取数据,如果为null,则不截取
	 * @param e    二次截取数据,如果为null,则表示不限制结束
	 * @return 返回-1表示数据不正常
	 * @throws UnsupportedEncodingException
	 */
	public int getInt(byte[] data,String str,String end,byte[] s,byte[] e) throws UnsupportedEncodingException
	{
		byte[] array=null;
		if(end==null)
		{//不限制后面
			array=subByteArraysByStr(data, str);
			//System.out.println(Arrays.toString(byteArrayToHexArray(array)));
		}
		else
		{
			array=subByteArraysByStr(data, str, end);
		}
		if(array==null||array.length<=0)
		{
			//System.out.println(str+"一次截取数据为空!");
			return -1;
		}
		if(s!=null)//如果开始截获的不等于null
		{
			//二次截取
			if(e==null)
			{
				array=subByteArrays(array, s);
			}
			else
			{
				array=subByteArrays(array, s, e);
			}
			if(array==null||array.length<=0)
			{
				//System.out.println(str+"二次截取数据为空!");
				return -1;
			}
		}
		int value=byteArrayToInt(array);
		return value;
	}
	/**
	 * 根据参数取数据
	 * @param data 原数据
	 * @param str  开始字符串
	 * @param end  结束字符串
	 * @param s    二次截取数据,如果为null,则不截取
	 * @param e    二次截取数据,如果为null,则表示不限制结束
	 * @return 返回""表示数据不正常
	 * @throws UnsupportedEncodingException
	 */
	public String getStringUTF16LE(byte[] data,String str,String end,byte[] s,byte[] e) throws UnsupportedEncodingException
	{
		byte[] array=null;
		if(end==null)
		{//不限制后面
			array=subByteArraysByStr(data, str);
		}
		else
		{
			array=subByteArraysByStr(data, str, end);
		}
		if(array==null||array.length<=0)
		{
			//System.out.println(str+"一次截取数据为空!");
			return "";
		}
		if(s!=null)//如果开始截获的不等于null
		{
			//二次截取
			if(e==null)
			{
				array=subByteArrays(array, s);
			}
			else
			{
				array=subByteArrays(array, s, e);
			}
			if(array==null||array.length<=0)
			{
				//System.out.println(str+"二次截取数据为空!");
				return "";
			}
		}
		String value=new String(array,"UTF-16LE");
		return value;
	}
	public String getStringUTF8(byte[] data,String str,String end,byte[] s,byte[] e) throws UnsupportedEncodingException
	{
		byte[] array=null;
		if(end==null)
		{//不限制后面
			array=subByteArraysByStr(data, str);
		}
		else
		{
			array=subByteArraysByStr(data, str, end);
		}
		if(array==null||array.length<=0)
		{
			System.out.println(str+"一次截取数据为空!");
			return "";
		}
		if(s!=null)//如果开始截获的不等于null
		{
			//二次截取
			if(e==null)
			{
				array=subByteArrays(array, s);
			}
			else
			{
				array=subByteArrays(array, s, e);
			}
			if(array==null||array.length<=0)
			{
				System.out.println(str+"二次截取数据为空!");
				return "";
			}
		}
		String value=new String(array,"UTF8");
		return value;
	}
	/**
	 * 将10 30 33 00 1F 66 4A这样的hex文本转换为文本内容
	 * @param str
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public String hexStrToStrUTF16LE(String str) throws UnsupportedEncodingException
	{
		String[] s=str.split("\\s");
		byte[] ss=hexArrayToBtyeArray(s);
		return new String(ss,"utf-16le");
	}
	/**
	 * 将10 30 33 00 1F 66 4A这样的hex文本转换为文本内容
	 * @param str
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public String hexStrToStrUTF8(String str) throws UnsupportedEncodingException
	{
		String[] s=str.split("\\s");
		byte[] ss=hexArrayToBtyeArray(s);
		return new String(ss,"utf8");
	}
}


package net.jueb.util4j.beta.tools.convert;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import org.apache.commons.lang.StringUtils;

/**
 * 字节数组,16进制字符串数组文本之间的转换
 * hexArray:[01, 16, 1F, 0E] String数组,存放大写的Hex字节
 * byteArray:[1, 22, 31, 14] byte数组
 * hexStr:01161F0E  String文本,
 * str:普通文本
 * @author juebanlin
 *
 */
public class HexStrBytes {

	/**
	 * 根据字节数组拆分若干个字字节数组
	 * @param data    待拆分数组
	 * @param separator 分割数组
	 * @return 
	 */
	public byte[][] getSonArrays(byte[] data,byte[] separator)
	{
		if(data==null||data.length<=0||separator==null||separator.length<=0)
		{
			System.out.println("data||separator数据无效!");
			return null;
		}
		String[] dataHexArray=toHexArray(data);
		String dataHexStr=StringUtils.substringBetween(Arrays.toString(dataHexArray), "[", "]").replaceAll("\\s","");
		//System.out.println("待拆分字符串:"+dataHexStr);
		String[] separatorHexhArray=toHexArray(separator);
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
			result[i]=toBtyeArray(array);
		}
		return result;
	}
	/**
	 * 使用指定编码解码字节数组
	 * @param data   待解码字节
	 * @param encode 编码格式
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public String encodeByteArray(byte[] data,String encode) throws UnsupportedEncodingException
	{
		return new String(data,encode);
	}
	/**
	 * 截取目标字节数组指定索引范围的字节数组
	 * 从0开始,[start,end]
	 * @param start  
	 * @param end
	 * @return
	 */
	public byte[] subByteArray(byte[] data,int start,int end)
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
	 * 根据指定范围,截取字节数组,并将该数组以指定编码文本返回
	 * 被截取的数组内容不丢失
	 * 从0开始,[start,end]
	 * @param data byte数据
	 * @param start 起始位置
	 * @param end   结束位置
	 * @param encode 文本编码
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	public String subByteArray(byte[] data,int start,int end,String encode) throws UnsupportedEncodingException
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
	 * 根据起始和结束16进制字符串取byte数据中间数据
	 * 	byte[] a=new byte[]{1,2,3,4,5,6,7,8,9}; 
		byte[] b=new byte[]{2,3};
		byte[] c=new byte[]{7,9};
		byte[] d=hsb.subByteArray(a,b,c);=[4,5,6]
	 * @param data  
	 * @param startByte 
	 * @param endByte   
	 * @return 类型：[0C, B6, 05, 00]
	 */
	public byte[] subByteArray(byte[] data,byte[] startByte,byte[] endByte)
	{
		if(data==null||data.length<=0)
		{
			System.out.println("data参数错误!");
			return null;
		}
		String[] s=toHexArray(startByte);
		String[] e=toHexArray(endByte);
		String[] hexData=subHexArray(data, s, e);
		return toBtyeArray(hexData);
	}
	/**
	 * 截取目标字节数组中startArray后面的字节数组
	 * @param byteArray
	 * @param startArray
	 * @return
	 */
	public byte[] subByteArray(byte[] byteArray,byte[] startArray)
	{
		if(byteArray==null||byteArray.length<=0)
		{
			System.out.println("data参数错误!");
			return null;
		}
		String[] dataHex=toHexArray(byteArray);//转换为hex字符数组
		String dataHexStr=Arrays.toString(dataHex);//转换为hex字符串
		dataHexStr=StringUtils.substringBetween(dataHexStr, "[", "]").replaceAll("\\s", "");//去括号空格
		
		String[] startHex=toHexArray(startArray);//转换为hex字符数组
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
		return toBtyeArray(result);
	}
	/**
	 * 根据起始和结束16进制字符串取byte数据中间数据
	 * @param data  类型：[0, 7, -63, -108]
	 * @param startHexStr 类型：["0C", "B6", "05", "00"]
	 * @param endHexStr   类型：["0C", "B6", "05", "00"]
	 * @return 类型：[0C, B6, 05, 00]
	 */
	public byte[] subByteArray(byte[] data,String[] startHexArray,String[] endHexArray)
	{
		if(data==null||data.length<=0)
		{
			System.out.println("data参数错误!");
			return null;
		}
		String[] hexData=subHexArray(data, startHexArray, endHexArray);
		return toBtyeArray(hexData);
	}
	/**
	 * 失败返回null
	 * 根据字符串的utf字节数据截取目标数据的中间数据，返回符合的字节数组
	 * @param byteArray
	 * @param startStr 普通文本
	 * @param startStr 普通文本
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public byte[] subByteArray(byte[] byteArray,String startStr,String endStr) throws UnsupportedEncodingException
	{
		if(byteArray==null||byteArray.length<=0)
		{
			System.out.println("data参数无效!");
			return null;
		}
		byte[] s=startStr.getBytes("utf-8");
		byte[] e=endStr.getBytes("utf-8");
		return subByteArray(byteArray,s, e);
	}
	/**
	 * 以文本对应的字节数据为头，截取后面的字节数组
	 * @param byteArray
	 * @param startStr
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public byte[] subByteArray(byte[] byteArray,String startStr) throws UnsupportedEncodingException
	{
		if(byteArray==null||byteArray.length<=0)
		{
			System.out.println("startStr:"+startStr+"byteArray参数无效!");
			return null;
		}
		String[] result=subHexArray(byteArray, startStr);
		return toBtyeArray(result);
	}
	
	/**
	 * 根据byte数组起始和结束数据，截取中间数据，以hsxStringArray返回
	 * @param data  类型：[0, 7, -63, -108]
	 * @param startByte 类型：[0, 7, -63, -108]或者[0x0C, 0xB6, 0x05, 0x00]
	 * @param endByte   类型：[0x0C, 0xB6, 0x05, 0x00]
	 * @return
	 */
	public String[] subHexArray(byte[] byteArray,byte[] startByteArray,byte[] endByteArray)
	{
		if(byteArray==null||byteArray.length<=0)
		{
			System.out.println("data参数错误!");
			return null;
		}
		byte[] result=subByteArray(byteArray, startByteArray, endByteArray);
		return toHexArray(result);
	}
	/**
	 * 根据hex文本数组截取数据，返回截获的hex数组
	 * @param data 类型：[0, 7, -63, -108]
	 * @param startHexStr 类型：["0C", "B6", "05", "00"]
	 * @param endHexStr 类型：["0C", "B6", "05", "00"]
	 * @return 类型：["0C", "B6", "05", "00"] 失败返回null
	 */
	public String[] subHexArray(byte[] data,String[] startHexStr,String[] endHexStr)
	{
		if(data==null||data.length<=0)
		{
			System.out.println("data数据无效!");
			return null;
		}
		String[] result=null;
		String[] hexarray=toHexArray(data);
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
	public String[] subHexArray(byte[] data,String startStr,String endStr) throws UnsupportedEncodingException
	{
		if(data==null||data.length<=0)
		{
			System.out.println("data参数无效!");
			return null;
		}
		byte[] s=startStr.getBytes("utf-8");
		byte[] e=endStr.getBytes("utf-8");
		return subHexArray(data, s, e);
	}
	/**
	 * 截取以文本开头的字节数据并以hexStringArray数组返回
	 * @param data 目标字节数组
	 * @param startHexStr  普通文本,默认以utf8形式
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public String[] subHexArray(byte[] data,String startStr) throws UnsupportedEncodingException
	{
		if(data==null||data.length<=0)
		{
			System.out.println("data数据无效!");
			return null;
		}
		String[] result=null;
		//转换原数据
		String[] hexarray=toHexArray(data);
		String hexstr=Arrays.toString(hexarray);
		hexstr=StringUtils.substringBetween(hexstr, "[", "]").replaceAll("\\s", "");//原数据字符串去括号空格
		////转换匹配参数数据
		byte[] startArray=startStr.getBytes("utf-8");//转换为字节
		String[] startHex=toHexArray(startArray);//转换为hex字符数组
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
	 * 拼接2个数组，以第一个数组为头
	 * 返回拼接后的数组
	 * @param bytes1
	 * @param bs
	 * @return
	 */
	public byte[] addByteArray(byte[] bytes1,byte[] bs)
	{
		if(bytes1!=null&&bytes1.length>=1)
		{//如果第一个不为null且有元素
			if(bs!=null&&bs.length>=1)
			{
				byte[] bytes=new byte[bytes1.length+bs.length];
				for(int i=0;i<bytes1.length;i++)
				{
					bytes[i]=bytes1[i];
				}
				for(int i=0;i<bs.length;i++)
				{
					bytes[bytes1.length+i]=bs[i];
				}
				return bytes;
			}else
			{
				byte[] bytes=new byte[bytes1.length];
				for(int i=0;i<bytes1.length;i++)
				{
					bytes[i]=bytes1[i];
				}
				return bytes;
			}
		}else
		{
			if(bs!=null&&bs.length>=1)
			{
				byte[] bytes=new byte[bs.length];
				for(int i=0;i<bs.length;i++)
				{
					bytes[i]=bs[i];
				}
				return bytes;
			}else
			{
				return null;
			}
			
		}
	}
	/**
		 * 将16进制的字符数组转换为byte[]数组
		 * [01, 16, 1F, 0E] ==>[1, 22, 31, 14]
		 * @param hexArrays
		 * @return
		 */
		public byte[] toBtyeArray(String[] hexArrays)
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
	 * byte[] a={1,22,31,14};==>"[01, 16, 1F, 0E]"
	 * @param data
	 * @return
	 */
	public String[] toHexArray(byte[] data)
	{
		if(data==null||data.length<=0)
		{
			System.out.println("toHexArray：参数错误!");
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
	 * 将文本转换为Hex文本数组
	 * @param str 文本
	 * @param encode 编码格式
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	public String[] toHexArray(String str,String encode) throws UnsupportedEncodingException
	{
		byte[] data=str.getBytes(encode);
		return toHexArray(data);
	}
	
	/**
	 * 将一个装有16进制的string数组变成一个16进制字符串
	 * "[01, 16, 1F, 0E]" ==> 01161F0E
	 * @param hexStringArray
	 * @return
	 */
	public String toHexStr(String[] hexStringArray)
	{
		String hexStr=Arrays.toString(hexStringArray);
		hexStr=StringUtils.substringBetween(hexStr, "[", "]");
		hexStr=hexStr.replaceAll("\\s", "");//去空格
		hexStr=hexStr.replaceAll(",", "");
		return hexStr;
	}
	/**
	 * 将一个16进制文本数组字符串转为16进制文本数组
	 * "[01, 16, 1F, 0E]" ==> [01, 16, 1F, 0E]
	 * @param hexArrayStr
	 * @return
	 */
	public String[] toHexStrArray(String hexArrayStr)
	{
		String hexStr=StringUtils.substringBetween(hexArrayStr, "[", "]");
		hexStr=hexStr.replaceAll("\\s", "");//去空格
		String[] hexStrArray=hexStr.split(",");
		return hexStrArray;
	}
	/**
	 * 将10 30 33 00 1F 66 4A这样的hex文本转换为utf16le文本内容
	 * @param hexStr
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public String toUTF16LEStr(String hexStr) throws UnsupportedEncodingException
	{
		String[] s=hexStr.split("\\s");
		byte[] ss=toBtyeArray(s);
		return new String(ss,"utf-16le");
	}
	/**
	 * 将10 30 33 00 1F 66 4A这样的hex文本转换为utf8文本内容
	 * @param hexStr
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public String toStrUTF8Str(String hexStr) throws UnsupportedEncodingException
	{
		String[] s=hexStr.split("\\s");
		byte[] ss=toBtyeArray(s);
		return new String(ss,"utf8");
	}
	
	/**
	* unicode 转换成 utf-8
	* @param theString
	* @return
	*/
	public String toUtf8ByUnicodeStr(String theString) {
		char aChar;
		int len = theString.length();
		StringBuffer outBuffer = new StringBuffer(len);
		for (int x = 0; x < len;) {
			aChar = theString.charAt(x++);
			if (aChar == '\\') {
				aChar = theString.charAt(x++);
				if (aChar == 'u') {
					// Read the xxxx
					int value = 0;
					for (int i = 0; i < 4; i++) {
						aChar = theString.charAt(x++);
						switch (aChar) {
						case '0':
						case '1':
						case '2':
						case '3':
						case '4':
						case '5':
						case '6':
						case '7':
						case '8':
						case '9':
							value = (value << 4) + aChar - '0';
							break;
						case 'a':
						case 'b':
						case 'c':
						case 'd':
						case 'e':
						case 'f':
							value = (value << 4) + 10 + aChar - 'a';
							break;
						case 'A':
						case 'B':
						case 'C':
						case 'D':
						case 'E':
						case 'F':
							value = (value << 4) + 10 + aChar - 'A';
							break;
						default:
							throw new IllegalArgumentException(
									"Malformed \\uxxxx encoding.");
						}
					}
					outBuffer.append((char) value);
				} else {
					if (aChar == 't')
						aChar = '\t';
					else if (aChar == 'r')
						aChar = '\r';
					else if (aChar == 'n')
						aChar = '\n';
					else if (aChar == 'f')
						aChar = '\f';
					outBuffer.append(aChar);
				}
			} else
				outBuffer.append(aChar);
		}
		return outBuffer.toString();
	}
	public static void main(String[] args) {
		HexStrBytes hb=new HexStrBytes();
		String s="[FA, 16, C4, 7B]";
		byte[] data=hb.toBtyeArray(hb.toHexStrArray(s));
		System.out.println(new String(data));
	}
}

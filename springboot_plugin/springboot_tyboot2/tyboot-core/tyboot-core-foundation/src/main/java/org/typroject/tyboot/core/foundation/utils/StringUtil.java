package org.typroject.tyboot.core.foundation.utils;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import java.io.IOException;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public final class StringUtil
{

	/**
	 * 汉字转换位汉语拼音首字母，英文字符不变
	 * 
	 * @param chines 汉字
	 * @return 拼音
	 */
	public static String converterToFirstSpell(String chines)
	{
		String pinyinName = "";
		char[] nameChar = chines.toCharArray();
		HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
		defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
		defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
		for (int i = 0; i < nameChar.length; i++)
		{
			if (nameChar[i] > 128)
			{
				try
				{
					pinyinName += PinyinHelper.toHanyuPinyinStringArray(nameChar[i], defaultFormat)[0].charAt(0);
				}
				catch (BadHanyuPinyinOutputFormatCombination e)
				{
					e.printStackTrace();
				}
			}
			else
			{
				pinyinName += nameChar[i];
			}
		}
		return pinyinName;
	}

	/**
	 * 汉字转换位汉语拼音，英文字符不变
	 * @author Wujun
	 * @param chines 汉字
	 * @return 拼音
	 */
	public static String converterToSpell(String chines)
	{
		String pinyinName = "";
		char[] nameChar = chines.toCharArray();
		HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
		defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
		defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
		for (int i = 0; i < nameChar.length; i++)
		{
			if (nameChar[i] > 128)
			{
				try
				{
					pinyinName += PinyinHelper.toHanyuPinyinStringArray(nameChar[i], defaultFormat)[0];
				}
				catch (BadHanyuPinyinOutputFormatCombination e)
				{
					e.printStackTrace();
				}
			}
			else
			{
				pinyinName += nameChar[i];
			}
		}
		return pinyinName;
	}

	public static String iso2UTF8(String str) throws Exception
	{
		if(ValidationUtil.isEmpty(str))
		{
			return str;
		}
		return new String(str.getBytes("iso-8859-1"), "utf-8");
	}

	
	/**
	 * 将文件名中的汉字转为UTF8编码的串,以便下载时能正确显示另存的文件名.
	 * @author Wujun
	 * @param s 原文件名
	 * @return 重新编码后的文件名
	 */
	public static String toUtf8String(String s)
	{
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < s.length(); i++)
		{
			char c = s.charAt(i);
			if (c >= 0 && c <= 255)
			{
				sb.append(c);
			}
			else
			{
				byte[] b;
				try
				{
					b = Character.toString(c).getBytes("utf-8");
				}
				catch (Exception ex)
				{
					//System.out.println(ex);
					b = new byte[0];
				}
				for (int j = 0; j < b.length; j++)
				{
					int k = b[j];
					if (k < 0)
						k += 256;
					sb.append("%" + Integer.toHexString(k).toUpperCase());
				}
			}
		}
		return sb.toString();
	}

	/**
	 * 将utf-8编码的汉字转为中文
	 * @author Wujun
	 * @param str
	 * @return
	 */
	public static String utf8Decoding(String str)
	{
		String result = str;
		try
		{
			result = URLDecoder.decode(str, "UTF-8");
		}
		catch (UnsupportedEncodingException e)
		{
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 将数字类型的字符串转换成大写字母形式的字符串
	 * 转换规则按照0-A; 1-B;依次类推
	 * TODO.
	 * @author Wujun
	 * 2015-06-10 
	 * @param str
	 * @return
	 */
	public static String intStr2UpperStr(String str)
	{
		char[] charArr = str.toCharArray();
		StringBuilder tempStr = new StringBuilder();
		for (int i = 0; i < charArr.length; i++)
		{
			char tempChar = charArr[i];
			if(tempChar > 47 && tempChar < 58 ){
				charArr[i] = (char) (tempChar + 17);
				tempStr.append(charArr[i]);
			}
			str = tempStr.toString();
		}
		return str;
	}
	
	/**
	 * 将A-J的字符串转换成数字形式的字符串
	 * 转换规则按照A-0; B-1;依次类推
	 * TODO.
	 * @author Wujun
	 * 2015-06-10 
	 * @param str
	 * @return
	 */
	public static String upperStr2IntStr(String str)
	{
		char[] charArr = str.toCharArray();
		StringBuilder tempStr = new StringBuilder();
		for (int i = 0; i < charArr.length; i++)
		{
			char tempChar = charArr[i];
			if(tempChar > 64 && tempChar < 75 ){
				charArr[i] = (char) (tempChar - 17);
				tempStr.append(charArr[i]);
			}
			str = tempStr.toString();
		}
		return str;
	}
	
	public static void main(String[] as)
	{
		/*String inputString = "ADBC-d/..d-\\123-.+.0|1+";

		for (int i = 0; i < inputString.length(); i++)
		{
			char c = inputString.charAt(i);
			System.out.print(c);
		}

		System.out.println();

		String escaped = encodePK(inputString);
		System.out.println(escaped);

		String unescaped = decodePK(escaped);
		System.out.println(unescaped);*/
		
	/*	String str = "0123456789";
		System.out.println(intStr2UpperStr(str));
		
		String string = "ABCDEFGHIJ";
		System.out.println(upperStr2IntStr(string));
		*/
	}
	
	
	 /***
     * 提供精确的小数位四舍五入处理。
     *
     * @param v 需要四舍五入的数字
     * @return 四舍五入后的结果 去掉了多余的.与0
     */
    public static String round(double v) {
        int scale = 2;//scale 小数点后保留几位
        BigDecimal b = new BigDecimal(Double.toString(v));
        BigDecimal one = new BigDecimal("1");
        double tmp = b.divide(one, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
        return subZeroAndDot(tmp + "");
    }
    
    /***
     * 向上进位
     */
    public static String givenUpDecimal(double v) {
    	return subZeroAndDot(Math.ceil(v)+"");
    }
    
    /***
     * 向上进位
     */
    public static Double givenUpDecimal2Double(double v) {
    	return Math.ceil(v);
    }
    
    
	 /***
     * 提供精确的小数位四舍五入处理。
     *
     * @param v 需要四舍五入的数字
     * @return 四舍五入后的结果 去掉了多余的.与0
     */
    public static Double round2Double(double v) {
    	String returnStr = StringUtil.round(v);
    	return Double.valueOf(returnStr);
    }


    /**
     * 使用java正则表达式去掉多余的.与0
     *
     * @param s
     * @return
     */
    public static String subZeroAndDot(String s) {
        if (s.indexOf(".") > 0) {
            s = s.replaceAll("0+?$", "");//去掉多余的0
            s = s.replaceAll("[.]$", "");//如最后一位是.则去掉
        }
        return s;
    }
	/**
	 * 加带逗号的字符串分割为数组
	 */
    public static String[] string2Array(String s){
		String[] strings = new String[]{};
		if(ValidationUtil.isEmpty(s))
			return strings;
		strings = s.split(",");
    	return strings;
    }



	public static Long[] String2LongArray(String str)
	{
		Long [] longSeqs    = new Long[]{};
		if(ValidationUtil.isEmpty(str))
		{
			String [] seqs      = StringUtil.string2Array(str);
			longSeqs            = new Long[seqs.length];
			for(int i=0;i<seqs.length;i++)
				longSeqs[i]     =Long.parseLong(seqs[i]);
		}
		return longSeqs;
	}

	public static List<Long> String2LongList(String str)
	{
		List<Long> longSeqs     = new ArrayList<>();
		if(!ValidationUtil.isEmpty(str))
		{
			String [] seqs          = StringUtil.string2Array(str);
			for(String id:seqs)longSeqs.add(Long.parseLong(id));
		}
		return longSeqs;
	}



	public static List<String> String2List(String str)
	{
		List<String> stringList     = new ArrayList<>();
		if(!ValidationUtil.isEmpty(str))
		{
			String [] seqs          = StringUtil.string2Array(str);
			for(String id:seqs)stringList.add(id);
		}
		return stringList;
	}



	public static String  string2SqlCase(String s){
		String[] strings = s.split(",");
		String returnStr = "";
		if(!ValidationUtil.isEmpty(strings))
		{
			for(String str : strings)
			{
				returnStr += "'"+str+"',";
			}
			returnStr = returnStr.substring(0, returnStr.length()-1);
		}
    	return returnStr;
    }
}

/*
 * $Log: av-env.bat,v $
 */

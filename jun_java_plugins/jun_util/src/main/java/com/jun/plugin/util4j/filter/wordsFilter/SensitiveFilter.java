package com.jun.plugin.util4j.filter.wordsFilter;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 敏感字符过滤器
 * @author Administrator
 */
public class SensitiveFilter
{
	protected Logger log=LoggerFactory.getLogger(getClass());
	@SuppressWarnings("rawtypes")
	private Map pool = new HashMap<>();
	
	/**
	 * 包含敏感字符的文件,以行隔开
	 * @param filePath
	 */
	public SensitiveFilter(InputStream in,Charset charset)
	{
		try {
			createTree(readSensitiveWord(in, charset));
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
	
	private Set<String> readSensitiveWord(InputStream in,Charset charset) throws Exception{
		Set<String> set = new HashSet<String>();
		InputStreamReader read = new InputStreamReader(in,charset);
		try {
			set = new HashSet<String>();
			BufferedReader bufferedReader = new BufferedReader(read);
			String txt = null;
			while((txt = bufferedReader.readLine()) != null){    //读取文件，将文件内容放入到set中
				set.add(txt);
		    }
		} catch (Exception e) {
			throw e;
		}finally{
			read.close();     //关闭文件流
		}
		return set;
	}
	
	public SensitiveFilter(Set<String> words)
	{
		createTree(words);
	}
	
	private void createTree(Set<String> lines)
	{
		for (String line : lines)
		{
			processLine(line);
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void processLine(String line)
	{
		Map nowMap = pool;
		char[] chars = line.toCharArray();
		for (int i = 0; i < chars.length; i++)
		{
			char ch = Character.toUpperCase(chars[i]);
			Object wordMap = nowMap.get(ch);
			if (wordMap != null)
			{
				nowMap = (Map) wordMap;
			}
			else
			{
				Map newWordMap = new HashMap();
				newWordMap.put("isEnd", "0");
				nowMap.put(ch, newWordMap);
				nowMap = newWordMap;
			}
			if (i == chars.length - 1)
			{
				nowMap.put("isEnd", "1");
			}
		}
	}
	
	/**
	 * 替换敏感字
	 * @param source
	 * @return 返回替换后的字符串
	 */
	@SuppressWarnings("rawtypes")
	public String replace(String source,char replaceChar)
	{
		StringBuilder sb = new StringBuilder(source);
		char[] chars = source.toCharArray();
		int start = -1, end = -1;
		Map nowMap = pool;
		for (int i = 0; i < chars.length; i++)
		{
			char ch = Character.toUpperCase(chars[i]);
			nowMap = (Map) nowMap.get(ch);
			if (nowMap != null)
			{
				if (start == -1)
				{
					start = i;
				}
				if ("1".equals(nowMap.get("isEnd")))
				{
					end = i;
					for (int j = start; j <= end; j++)
					{
						sb.setCharAt(j, replaceChar);
					}
					start = -1; end = -1;
				}
			}
			else
			{
				start = -1;
				nowMap = pool;
				nowMap = (Map) nowMap.get(ch);
				if (nowMap != null)
				{
					if (start == -1)
					{
						start = i;
					}
					if ("1".equals(nowMap.get("isEnd")))
					{
						end = i;
						for (int j = start; j <= end; j++)
						{
							sb.setCharAt(j, replaceChar);
						}
						start = -1; end = -1;
					}
				}
				else
				{
					nowMap = pool;
				}
			}
		}
		return sb.toString();
	}
	
	/**
	 * 是否有敏感字符
	 * @param source
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public boolean hasSensitiveWord(String source)
	{
		char[] chars = source.toCharArray();
		Map nowMap = pool;
		for (int i = 0; i < chars.length; i++)
		{
			char ch = Character.toUpperCase(chars[i]);
			nowMap = (Map) nowMap.get(ch);
			if (nowMap != null)
			{
				if ("1".equals(nowMap.get("isEnd")))
				{
					return true;
				}
			}
			else
			{
				nowMap = pool;
				nowMap = (Map) nowMap.get(ch);
				if (nowMap != null)
				{
					if ("1".equals(nowMap.get("isEnd")))
					{
						return true;
					}
				}
				else
				{
					nowMap = pool;
				}
			}
		}
		return false;
	}
	public static void main(String[] args) {
		Set<String> set=new HashSet<String>();
		set.add("法轮功");
		set.add("杀人");
		SensitiveFilter filter = new SensitiveFilter(set);
		System.out.println("敏感词的数量：" + set.size());
		String string = "太多的伤感情怀也许只局限于饲养基地 荧幕中的情节，主人公尝试着去用某种方式渐渐的很潇洒地释自杀指南怀那些自己经历的伤感。"
						+ "然后法轮功 我们的扮演的角色就是跟随着主人公的喜红客联盟 怒哀乐而过于牵强的把自己的情感也附加于银幕情节中，然后感动就流泪，"
						+ "难过就躺在某一个人的怀里尽情的阐述心扉或者手机卡复制器一个人一杯红酒一部电影在夜三级片 深人静的晚上，关上电话静静的发呆着。";
		System.out.println("待检测语句字数：" + string.length());
		long beginTime = System.currentTimeMillis();
		String result=filter.replace(string,'*');
		long endTime = System.currentTimeMillis();
		System.out.println("总共消耗时间为：" + (endTime - beginTime));
		System.out.println("替换后:"+result);
	}
}

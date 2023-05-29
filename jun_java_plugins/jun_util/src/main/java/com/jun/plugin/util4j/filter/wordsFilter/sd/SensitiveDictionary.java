package com.jun.plugin.util4j.filter.wordsFilter.sd;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *敏感词库,将敏感词加入到HashMap中，构建DFA算法模型
 */
public class SensitiveDictionary {
	protected Logger log=LoggerFactory.getLogger(getClass());
	@SuppressWarnings("rawtypes")
	public Map sensitiveWordMap;
	
	public SensitiveDictionary(InputStream in,Charset charset){
		initKeyWord(in,charset);
	}
	
	public SensitiveDictionary(File file,Charset charset){
		initKeyWord(file,charset);
	}
	
	public SensitiveDictionary(byte[] data,Charset charset){
		initKeyWord(new ByteArrayInputStream(data),charset);
	}
	
	public SensitiveDictionary(Set<String> keyWordSet){
		initKeyWord(keyWordSet);
	}
	
	private void initKeyWord(InputStream in,Charset charset){
		try {
			//读取敏感词库
			Set<String> keyWordSet = readSensitiveWord(in,charset);
			//将敏感词库加入到HashMap中
			sensitiveWordMap=sensitiveWordToHashMap(keyWordSet);
			//spring获取application，然后application.setAttribute("sensitiveWordMap",sensitiveWordMap);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
	
	private void initKeyWord(File file,Charset charset){
		try {
			//读取敏感词库
			Set<String> keyWordSet = readSensitiveWord(new FileInputStream(file),charset);
			//将敏感词库加入到HashMap中
			sensitiveWordMap=sensitiveWordToHashMap(keyWordSet);
			//spring获取application，然后application.setAttribute("sensitiveWordMap",sensitiveWordMap);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}

	private void initKeyWord(Set<String> keyWordSet){
		try {
			//将敏感词库加入到HashMap中
			sensitiveWordMap=sensitiveWordToHashMap(keyWordSet);
			//spring获取application，然后application.setAttribute("sensitiveWordMap",sensitiveWordMap);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}

	@SuppressWarnings("rawtypes")
	protected Map getSensitiveWordMap() {
		return sensitiveWordMap;
	}

	/**
	 * 读取敏感词库，将敏感词放入HashSet中，构建一个DFA算法模型：<br>
	 * 中 = {
	 *      isEnd = 0
	 *      国 = {<br>
	 *      	 isEnd = 1
	 *           人 = {isEnd = 0
	 *                民 = {isEnd = 1}
	 *                }
	 *           男  = {
	 *           	   isEnd = 0
	 *           		人 = {
	 *           			 isEnd = 1
	 *           			}
	 *           	}
	 *           }
	 *      }
	 *  五 = {
	 *      isEnd = 0
	 *      星 = {
	 *      	isEnd = 0
	 *      	红 = {
	 *              isEnd = 0
	 *              旗 = {
	 *                   isEnd = 1
	 *                  }
	 *              }
	 *      	}
	 *      }
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private Map sensitiveWordToHashMap(Set<String> keyWordSet) {
		HashMap sensitiveWordMap = new HashMap(keyWordSet.size());     //初始化敏感词容器，减少扩容操作
		String key = null;  
		Map nowMap = null;
		Map<String, String> newWorMap = null;
		//迭代keyWordSet
		Iterator<String> iterator = keyWordSet.iterator();
		while(iterator.hasNext()){
			key = iterator.next();    //关键字
			nowMap = sensitiveWordMap;
			for(int i = 0 ; i < key.length() ; i++){
				char keyChar = key.charAt(i);       //转换成char型
				Object wordMap = nowMap.get(keyChar);       //获取
				
				if(wordMap != null){        //如果存在该key，直接赋值
					nowMap = (Map) wordMap;
				}
				else{     //不存在则，则构建一个map，同时将isEnd设置为0，因为他不是最后一个
					newWorMap = new HashMap<String,String>();
					newWorMap.put("isEnd", "0");     //不是最后一个
					nowMap.put(keyChar, newWorMap);
					nowMap = newWorMap;
				}
				
				if(i == key.length() - 1){
					nowMap.put("isEnd", "1");    //最后一个
				}
			}
		}
		return sensitiveWordMap;
	}

	private Set<String> readSensitiveWord(InputStream in,Charset charset) throws Exception{
		Set<String> set = new HashSet<String>();
		InputStreamReader read = new InputStreamReader(in,charset);
		try {
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
}

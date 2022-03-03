package org.springrain.cms.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @ClassName: KeyWordUtils
 * @Description:敏感词过滤
 * @author win7
 * @date 2016年2月27日 下午9:01:34
 */
public class SensitiveWordUtils{
	
	private static final Logger logger = LoggerFactory.getLogger(SensitiveWordUtils.class);
	
	
	// 刷新敏感詞(0:已經刷新,1:未刷新)
	private static int FALUSH = 1;
	private static final int WORDS_MAX_LENGTH = 100;
	// 敏感词列表
	private static Map<String, String>[] banWordsList = null;
	// 敏感词索引
	private static Map<String, Integer> wordIndex = new HashMap<String, Integer>();

	/**
	 * 初始化敏感词库
	 * 
	 * @throws IOException
	 */
	@SuppressWarnings({ "unchecked" })
	private static void init(String path) throws IOException {
		banWordsList = new Map[WORDS_MAX_LENGTH];
		for (int i = 0; i < banWordsList.length; i++) {
			banWordsList[i] = new HashMap<String, String>();
		}
		// 敏感词词库所在目录，这里为txt文本，一个敏感词一行
		//String path = System.getProperty("user.dir");
		path = path.replaceAll("\\\\", "/");
		//path = path + PATH;
		File f = FileUtils.getFile(path);
		FileInputStream fis = null;
		UnicodeReader ur = null;
		BufferedReader br = null;
		if (f.exists()) {
			try {
				fis = new FileInputStream(f);
				//ur = new UnicodeReader(fis, Charset.defaultCharset().name());
				ur = new UnicodeReader(fis, "UTF-8");
				br = new BufferedReader(ur);
				//List<String> words = FileUtils.readLines(f, ENCODING);
				for (String w = br.readLine(); w != null; w = br
						.readLine()) {
					if (StringUtils.isNotBlank(w)) {
						// 将敏感词按长度存入map
						banWordsList[w.length()].put(w.toLowerCase(), "");
						Integer index = wordIndex.get(w.substring(0, 1));
						// 生成敏感词索引，存入map
						if (index == null) {
							index = 0;
						}
	
						int x = (int) Math.pow(2, w.length());
						index = (index | x);
						wordIndex.put(w.substring(0, 1), index);
					}
				}
			}catch(Exception e){
				logger.error(e.getMessage(), e);
			}finally{
				if (fis != null) {
					fis.close();
				}
				if (ur != null) {
					ur.close();
				}
				if (br != null) {
					try {
						br.close();
					} catch (IOException e) {
						logger.error(e.getMessage(), e);
					}
				}
			}
		}
		// 初始化完成
		FALUSH = 0;
	}
	
	/**
	 * @author win7
	 * @Description: 校验敏感词，并将敏感词标记-加"<span style='color:red;'></span>"
	 * @param 参数
	 * @return String 返回值
	 */
	public static String validateWordsForColor(String content,String path) {
		if (FALUSH == 1) {
			try {
				init(path);
			} catch (IOException e) {
				logger.error(e.getMessage(), e);
			}
		}
		List<String> word_list = new ArrayList<>(); 
		for (int i = 0; i < content.length(); i++) {
			Integer index = wordIndex.get(content.substring(i, i + 1));
			int p = 0;
			while ((index != null) && (index > 0)) {
				p++;
				index = index >> 1;
				String sub = "";
				if ((i + p) <= (content.length() - 1)) {
					sub = content.substring(i, i + p);
				} else {
					sub = content.substring(i);
				}
				if (((index % 2) == 1) && banWordsList[p].containsKey(sub)) {
					//System.out.println("找到敏感词："+content.substring(i,i+p));
					word_list.add(content.substring(i,i+p));
				}
			}
		}
		for(String str : word_list){
			if (isLetter(str)==false && (!"<".equalsIgnoreCase(str) || !">".equalsIgnoreCase(str) || !"=".equalsIgnoreCase(str)
					|| !"+".equalsIgnoreCase(str) || !"/".equalsIgnoreCase(str) || !"'".equalsIgnoreCase(str)
					|| !":".equalsIgnoreCase(str) || !";".equalsIgnoreCase(str))) {
				content = content.replace(str, "<span style='color:red;'>" + str + "</span>");
				
			}
		}
		return content;
	}
	/**
	 * @author li
	 * @Description:判断字符串是否为字符，是字母返回true,不是字符返回false;
	 * @param 参数说明
	 * @return boolean 返回类型
	 */
	public static boolean isLetter(String s) {
		char c = s.charAt(0);
		int i = (int) c;
		if ((i >= 65 && i <= 90) || (i >= 97 && i <= 122)) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * @author li
	 * @Description: 查询出敏感词并将其屏蔽为*
	 * @param 参数说明
	 * @return String 返回类型
	 */
	public static String validateBanWords(String content,String path) {
		if (FALUSH == 1) {
			try {
				init(path);
			} catch (IOException e) {
				logger.error(e.getMessage(), e);
			}
		}
		for (int i = 0; i < content.length(); i++) {
			Integer index = wordIndex.get(content.substring(i, i + 1));
			int p = 0;
			while ((index != null) && (index > 0)) {
				p++;
				index = index >> 1;
				String sub = "";
				if ((i + p) <= (content.length() - 1)) {
					sub = content.substring(i, i + p);
				} else {
					sub = content.substring(i);
				}
				if (((index % 2) == 1) && banWordsList[p].containsKey(sub)) {
					//System.out.println("找到敏感词："+content.substring(i,i+p));
					String rep = "";
					for(int j=0; j<content.substring(i,i+p).length();j++){
						rep+="*";
					}
					content = content.replace(content.substring(i,i+p), rep);//替换敏感词
				}
			}
		}
		return content;
	}

	public static void main(String[] argc) {
		String path = "E:\\workspace\\hysd\\src\\main\\webapp\\upload\\test01.txt";
		String content = "胡锦涛哈哈胡锦涛毛泽东";
		String t = SensitiveWordUtils.validateBanWords(content,path);
		System.out.println(t);
	}

}

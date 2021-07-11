package com.cl.search.utils;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

public class PinyinUtil {
	
	private static HanyuPinyinOutputFormat hypof;

	static {
		hypof = new HanyuPinyinOutputFormat();
		hypof.setCaseType(HanyuPinyinCaseType.LOWERCASE);
		hypof.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
		hypof.setVCharType(HanyuPinyinVCharType.WITH_V);
	}
	
	public static String getPingYin(String src) {
		char[] t1 = src.toCharArray();
		String[] t2 = new String[t1.length];
		String t4 = "";
		int t0 = t1.length;
		try {
			for (int i = 0; i < t0; i++) {
				// 判断是否为汉字字符
				if (Character.toString(t1[i]).matches("[\\u4E00-\\u9FA5]+")) {
					t2 = PinyinHelper.toHanyuPinyinStringArray(t1[i],
							hypof);
					if(t2!=null){
						t4 += t2[0];
					}
				} else
					t4 += Character.toString(t1[i]);
			}
			t1 = null;
			t2 = null;
		} catch (BadHanyuPinyinOutputFormatCombination e1) {
			e1.printStackTrace();
		}
		return t4;
	}

	public static String getPinYinHeadChar(String str) {
		String convert = "";
		char word;
		String[] pinyinArray = null;
		for (int j = 0; j < str.length(); j++) {
			word = str.charAt(j);
			pinyinArray = PinyinHelper.toHanyuPinyinStringArray(word);
			if (pinyinArray != null) {
				convert += pinyinArray[0].charAt(0);
			} else {
				convert += word;
			}
		}
		return convert;
	}
}

package com.kevin.imageuploadserver;

import java.math.BigInteger;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
/**
 * 版权所有：新时代门业
 * 
 * IdGenertor
 * 
 * @author zhou.wenkai ,Created on 2016-2-16 13:36:25
 * 		   Major Function：<b>ID生成 工具类</b>
 * 
 *         注:如果您修改了本类请填写以下内容作为记录，如非本人操作劳烦通知，谢谢！！！
 * @author mender，Modified Date Modify Content:
 */
public class IdGenertor {
	
	/**
	 * 生成UUID
	 * 
	 * @return UUID
	 */
	public static String generateGUID() {
        return new BigInteger(165, new Random()).toString(36).toUpperCase();
    }
	
	public static String generateOrdersNum() {
		//YYYYMMDD+当前时间（纳秒）  ：   1秒=1000毫秒=1000*1000纳秒
		Date now = new Date();
		DateFormat df = new SimpleDateFormat("yyyyMMdd");
		String str = df.format(now);
		return str+System.nanoTime();
	}
}

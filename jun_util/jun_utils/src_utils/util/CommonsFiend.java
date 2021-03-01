/**
 * Program  : CommonsFiend.java
 * Author   : niehai
 * Create   : 2008-5-14 下午05:05:54
 *
 * Copyright 2007 by Embedded Internet Solutions Inc.,
 * All rights reserved.
 *
 * This software is the confidential and proprietary information
 * of Embedded Internet Solutions Inc.("Confidential Information").
 * You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement
 * you entered into with Embedded Internet Solutions Inc.
 *
 */

package cn.ipanel.apps.portalBackOffice.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.apache.commons.beanutils.BeanComparator;
import org.apache.commons.lang.time.DateFormatUtils;
import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.Tag;
import org.htmlparser.tags.ImageTag;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import cn.ipanel.apps.portalBackOffice.define.*;

/**
 * @since
 * @author niehai
 * @2008-5-14 下午05:05:54
 */
public class CommonsFiend {

	/**
	 * @since 获取随机数
	 * @author niehai
	 * @create 2008-5-14 下午05:06:31
	 * @param len
	 * @return
	 */
	public static String getUniqueId(int len) {
		if (len < 10)
			len = 10;
		return getUniqueId(len, 999999999);
	}

	private static String getUniqueId(int length, int maxrandom) {
		String tmpstr = "";
		String thread = (new SimpleDateFormat("yyyyMMddhhmmssSSS"))
				.format(new Date())
				+ Integer.toString(getRandom(maxrandom));
		thread = Integer.toString(thread.hashCode());
		if (thread.indexOf("-") >= 0)
			thread = thread.substring(thread.indexOf("-") + 1);
		if (thread.length() < length) {
			for (int i = thread.length(); i < length; i++)
				tmpstr = tmpstr + "0";

			thread = tmpstr + thread;
		}
		return thread;
	}

	public static int getRandom(int max) {
		return (int) (Math.random() * (double) max);
	}

	public static Date stringToDate(String timeStr) {
		Date date = new Date();
		SimpleDateFormat apf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			date = apf.parse(timeStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;

	}

	public static String getCurrentDate() {
		return DateFormatUtils.format(Calendar.getInstance().getTime(),
				Defines.FORMAT_DATE_STRING);
	}
	
	
	public static String getCurrentDateTime() {
		return DateFormatUtils.format(Calendar.getInstance().getTime(),
				Defines.FORMAT_DATE_TIME_STRING);
	}

	public static Date stringToTime(String timeStr) {
		Date date = new Date();
		SimpleDateFormat apf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			date = apf.parse(timeStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	public static Calendar stringToCalendar(String timeStr) {
		Date date = stringToTime(timeStr);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar;
	}

	/**
	 * @author liquan_apps
	 * @createTime 2007-1-28 19:32:51
	 * @param
	 * @return
	 * @since 得到明天的日期 ****-**-**
	 */
	public static String nextDay(String today) {
		Calendar calendar = Calendar.getInstance();
		long todayLong = stringToDate(today).getTime();
		long lastDayLong = todayLong + 3600000 * 24;
		Date lastDay = new Date(lastDayLong);
		return DateFormatUtils.format(lastDay, Defines.FORMAT_DATE_STRING);
	}

	/**
	 * 将日期分解获得连串的日期
	 * 
	 * @param startDate
	 * @param endDate
	 * @return
	 * @author lixiang
	 * @create 2008-8-28 下午09:22:46
	 * @since
	 */
	public static List getDays(String startDate, String endDate) {
		List list = new ArrayList();
		if (startDate.equals(endDate))
			list.add(startDate);
		else {
			for (String date = startDate; !date.equals(endDate); date = CommonsFiend
					.nextDay(date)) {
				list.add(date);
			}
			list.add(endDate);
		}
		return list;
	}

	public static String[] getUpdateTime() {
		Date date = Calendar.getInstance().getTime();
		date.setMinutes(date.getMinutes() + 1);
		String[] returnTime = new String[2];
		returnTime[0] = DateFormatUtils.format(date,
				Defines.FORMAT_DATE_TIME_STRING);
		date.setHours(23);
		date.setMinutes(59);
		date.setSeconds(59);
		returnTime[1] = DateFormatUtils.format(date,
				Defines.FORMAT_DATE_TIME_STRING);
		return returnTime;
	}

	/**
	 * 讲时间分解成小时
	 * 
	 * @param beginTime
	 * @param endTime
	 * @return
	 * @author lixiang
	 * @create 2008-11-20 上午02:17:57
	 * @since
	 */
	public static int[] getTimesString(String beginTime, String endTime) {
		int s1 = Integer.parseInt(beginTime.split(":")[0]);
		int s2 = Integer.parseInt(endTime.split(":")[0]);
		int[] back = new int[s2 - s1 + 1];
		for (int i = 0; i < back.length; i++) {
			back[i] = s1 + i;
		}
		return back;
	}

	/**
	 * 根据某年的某一月，获取该月的所有日期
	 * 
	 * @param date
	 *            格式yyyy-MM
	 * @return
	 * @author lixiang
	 * @throws ParseException
	 * @create 2008-9-22 下午01:59:33
	 * @since
	 */
	public static List getDatesList(String date) throws ParseException {
		DateFormat format = new SimpleDateFormat("yyyy-MM");
		Calendar time = Calendar.getInstance();
		time.clear();
		Date d1 = format.parse(date);
		time.setTime(d1);
		int day = time.getActualMaximum(Calendar.DAY_OF_MONTH);
		DateFormat formats = new SimpleDateFormat(Defines.FORMAT_DATE_STRING);
		List list = new ArrayList();
		for (int i = 1; i <= day; i++) {
			String s = format.format(d1) + "-" + i;
			Date sss = formats.parse(s);
			String dd = formats.format(sss);
			list.add(dd);
		}
		return list;
	}

	/**
	 * 按照HH:mm:ss的格式截断字符串获取广告播放时间长度
	 * 
	 * @param beginTime
	 * @param endTime
	 * @return
	 * @author lixiang
	 * @create 2008-9-24 下午03:31:28
	 * @since
	 */
	public static String getPlayTime(String beginTime, String endTime) {
		int begin = Integer.parseInt(beginTime.split(":")[0]);
		int end = Integer.parseInt(endTime.split(":")[0]);
		return String.valueOf(end - begin);
	}

	public static Date stringToTime(String timeStr, String formatString) {
		Date date = new Date();
		SimpleDateFormat apf = new SimpleDateFormat(formatString);
		try {
			date = apf.parse(timeStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	public static List sortList(List list, String name) {
		Comparator namCompare = new BeanComparator(name);
		Collections.sort(list, namCompare);
		return list;
	}

	/**
	 * @since int --> byte
	 * @author niehai
	 * @create 2008-10-31 上午11:15:05
	 * @param number
	 * @param byteSum
	 * @return
	 */
	public static byte[] convertBytes(int number, int byteSum) {
		byte[] b = new byte[byteSum];
		int len = b.length;
		for (int i = 0; i < len; i++) {
			b[len - i - 1] = (byte) ((number >> ((i) * 8)) & 0xFF);
		}
		return b;
	}

	public static String getCurrentTime() {
		return DateFormatUtils.format(Calendar.getInstance().getTime(),
				Defines.FORMAT_TIME_STRING);
	}

	public static String next59Secends(String beginTime) {
		Date begin = new Date();
		try {
			begin = new SimpleDateFormat(Defines.FORMAT_TIME_STRING)
					.parse(beginTime);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		long nextMinute = begin.getTime() + 59000;
		return DateFormatUtils.format(nextMinute, Defines.FORMAT_TIME_STRING);
	}
	
	/**
	 * Task:比较日期1是否在日期2之前(包括)
	 * @author laihm
	 * @create 2009-5-20 上午10:20:03
	 * @since 
	 * @param date1日期字符串1
	 * @param date2日期字符串2
	 * @return 日期1在日期2之前返回true,否则返回false
	 */
	public static boolean isNotAfter(String date1,String date2){
		return stringToDate(date1).before(stringToDate(date2))||stringToDate(date1).equals(stringToDate(date2));
	}
	
	/**
	 * Task:比较时间1是否在时间2之前(包括)
	 * @author laihm
	 * @create 2009-5-20 上午10:23:08
	 * @since 
	 * @param time1时间字符串1
	 * @param time2时间字符串2
	 * @param formatString时间格式
	 * @return 时间1在时间2之前返回true,否则返回false
	 */
	public static boolean isNotAfter(String time1,String time2,String formatString){
		return stringToTime(time1, formatString).before(stringToTime(time2, formatString))||stringToTime(time1, formatString).equals(stringToTime(time2, formatString));
	}
	
	/**
	 * Task:比较两个时间字符串的天数间隔
	 * @author laihm
	 * @create 2009-5-20 下午03:18:29
	 * @since 
	 * @param s1时间字符串1
	 * @param s2时间字符串2
	 * @return 间隔天数，负数表示s1在s2之前，正数表示s1在s2之后
	 */
	public int compareDate(String s1,String s2){
		Date date1=stringToDate(s1);
		Date date2=stringToDate(s2);
		return (int)(date1.getTime()-date2.getTime())/(24*3600*1000);
	}

	public static boolean validateImgFormat(String fileName) {
		String imgType = Defines.IMAGETYPE_REGEXP;// 不区分大小写
		Pattern pattern = Pattern.compile(imgType, Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(fileName);
		boolean result = matcher.find();
		return result;
	}
	
	/**
	 * 获得项目的物理地址路径(最后一个字符是分隔符).
	 * 
	 * @return
	 * @author sunny
	 * @create 2007-10-25 下午03:44:40
	 */
	public static String getAbsPathOfProject() {
		String url = CommonsFiend.class.getClassLoader().getResource("").toString(); 
		String reg = "file:(.+)WEB-INF";
		Matcher mat = Pattern.compile(reg, Pattern.CASE_INSENSITIVE).matcher(
				url);
		if (mat.find()) {
			String path = mat.group(1);
			path = path.replaceAll("/", "\\" + File.separator);
			if (File.separator.equals("\\"))// windows
				return path.substring(1);
			return path;
		}
		return null;
	}
	
	
	/**
	 * 将字节转为Sring字符串
	 * 
	 * @param data
	 * @return
	 */
	public static String base64CodeByteTo64String(byte[] data) {
		BASE64Encoder encoder = new BASE64Encoder();
		if (data == null) {
			System.out.println("not get the img!");
			return null;
		}
		return encoder.encode(data).replaceAll("\\s*","");
	}
	
	
	/**
	 * 将string类型的数据转码为byte类型.
	 * 
	 * @param fileData
	 *            String 类型的数据.
	 * @return 转码后的数据byte类型,发生异常或者filedate为null时返回null.
	 */
	public static byte[] base64CodeDecode(String fileData) {
		if (fileData == null) {
			return null;
		}
		BASE64Decoder decoder = new BASE64Decoder();
		try {
			return decoder.decodeBuffer(fileData);
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println(e.toString());
		}
		return null;
	}
		
	
	/**
	 * 将zip文件转成Base64编码字符串
	 * @param filePath
	 * @return
	 * @author pengcc
	 */
	public static String getBase64StringOfZipFile(String filePath){		
		byte[] data=FileFiend.readFileByte(filePath);
		return CommonsFiend.base64CodeByteTo64String(data);
	}
	
	
	/**
	 * 解析出html代码里的图片
	 * @param htmlCode
	 * @return
	 * @author pengcc
	 */
	public static List getImageNamesInHtmlCode(String htmlCode){
		if(htmlCode==null){
			return new ArrayList();
		}
		NodeFilter linkAndImagFilter = new NodeFilter() {		
			 static final long serialVersionUID = -3600416039172283494L;
			public boolean accept(Node node) {
				if (node instanceof ImageTag)
					return true; 
				return false; 
			}
		};		
		Set imageSet=new HashSet(); //去掉重复的图片
		Parser parser = new Parser();
		try {
			parser.setEncoding("gb2312");
			parser.setInputHTML(htmlCode);
			NodeList nlist = parser.extractAllNodesThatMatch(linkAndImagFilter);
			for (int j = 0; j < nlist.size(); j++) {
				Tag node = (Tag) nlist.elementAt(j);
				if (node instanceof ImageTag) {
					ImageTag img = (ImageTag) node;
					String imageName = img.getAttribute("src");
					if(imageName!=null){
					   imageName = imageName.substring(imageName.lastIndexOf('/') + 1);
					   imageSet.add(imageName);
					}
				}
			}
		} catch (ParserException e) {
			e.printStackTrace();
		}
		List imageList=new ArrayList(imageSet); //去掉重复的图片
		return imageList;
	}
	
	
	/**
	 * 验证marquee的内容和图片zip包
	 * @param imageList
	 * @param zipfilePath
	 * @return
	 */
	public static boolean validateMarqueeZipPic(List imageList,String zipfilePath){
		if(imageList.size()==0){
			return true;  //无图片，不需要验证zip
		}
		if(FileFiend.judgeFileZip(zipfilePath)==false){  //上传的不是zip
			return false;
		}
		boolean result=true;
		InputStream in=null;
		ZipInputStream zipInput=null;
		List zipPicList=new ArrayList();
		try{
			File file=new File(zipfilePath);
			in= new FileInputStream(file);
			zipInput=new ZipInputStream(in);
			ZipEntry zipEntry = null;				
		   while ((zipEntry = zipInput.getNextEntry()) != null) {
				String fileName = zipEntry.getName();
				if(CommonsFiend.validateImgFormat(fileName)==false){  //验证marqueeContent中图片的格式
					return false;
				}
				zipPicList.add(fileName); 			   
			}
		}
		 catch (FileNotFoundException e) {			
			e.printStackTrace();			
		} catch (IOException e) {			
			e.printStackTrace();			
		}
		finally{			
				try {					
					if(zipInput!=null){
						zipInput.closeEntry();
						zipInput.close();
					}
					if(in!=null){
						in.close();
					}
				} catch (IOException e) {					
					e.printStackTrace();					
				}			
		}
		
		for(int i=0;i<imageList.size();i++){
			String imageName=(String)imageList.get(i);
			boolean contain=false;
			for(int j=0;j<zipPicList.size();j++){
				String fileName=(String)zipPicList.get(j);
				 if(imageName.equals(fileName)){
				    	contain=true;    //找到了对应的图片文件
				    	break;
				 }
			}
		    if(contain==false){  //有一个图片没找到
	    	  result=false;
			  break;
		    }
	    }

		return result;
	}
	
	/**
	 * 将放在List里所有String用","连接起来
	 * @author huangfei
	 * @create 2009-6-24 下午03:02:00
	 * @since 
	 * @param StringList
	 * @return
	 */
	public static String splitListWithComma(List StringList){
		StringBuffer strOfList = new StringBuffer("");
		for(int i=0;i<StringList.size();i++){
			if(i!=0){
				strOfList.append(",");
			}
			strOfList.append(StringList.get(i).toString());
		}
		return strOfList.toString();
	}

}

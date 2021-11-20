package com.gootrip.util;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: </p>
 * @author <a href="mailto:royiwu@hotmail.com">advance.wu</a>
 * @version 1.0
 */
import java.util.*;
import java.text.*;

public class DateHandler {

  public DateHandler() {
  }
  public static int openDay=5;
  private String iDate="";
  private int iYear;
  private int iMonth;
  private int iDay;
//  iDateTime = 2002-01-01 23:23:23
  public void setDate(String iDateTime){
    this.iDate=iDateTime.substring(0,10);
  }
  public String getDate(){
    return this.iDate;
  }
  public int getYear(){
    iYear=Integer.parseInt(iDate.substring(0,4));
    return iYear;
  }
  public int getMonth(){
    iMonth=Integer.parseInt(iDate.substring(5,7));
    return iMonth;
  }
  public int getDay(){
    iDay=Integer.parseInt(iDate.substring(8,10));
    return iDay;
  }

  public static String subDate(String date){
    return date.substring(0,10);
  }
  
  /**
   * 计算是否是季度末
   * @param date
   * @return
   */
  public static boolean isSeason(String date){
    int getMonth = Integer.parseInt(date.substring(5,7));
    boolean sign = false;
    if (getMonth==3)
      sign = true;
    if (getMonth==6)
      sign = true;
    if (getMonth==9)
      sign = true;
    if (getMonth==12)
      sign = true;
    return sign;
  }
  
  /**
   * 计算从现在开始几天后的时间
   * @param afterDay
   * @return
   */
  public static String getDateFromNow(int afterDay){
     GregorianCalendar calendar = new GregorianCalendar();
     Date date = calendar.getTime();

     SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

     calendar.set(Calendar.DATE,calendar.get(Calendar.DATE)+afterDay);
     date = calendar.getTime();

     return df.format(date);
  }

  /**
   * 带格式
   * @param afterDay
   * @param format_string
   * @return
   */
  public static String getDateFromNow(int afterDay, String format_string)
  {
      Calendar calendar = Calendar.getInstance();
      Date date = null;

      DateFormat df = new SimpleDateFormat(format_string);

      calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) + afterDay);
      date = calendar.getTime();

      return df.format(date);
  }

  /**
   * 得到当前时间，用于文件名，没有特殊字符，使用yyyyMMddHHmmss格式
   * @param afterDay
   * @return
   * by tim
   */
  public static String getNowForFileName(int afterDay){
    GregorianCalendar calendar = new GregorianCalendar();
//    Date date = calendar.getTime();

    SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");

    calendar.set(Calendar.DATE,calendar.get(Calendar.DATE)+afterDay);
    Date date = calendar.getTime();

    return df.format(date);
 }

//==============================================================================
//比较日期，与现在-N天的日期对比  -1 0 1
//==============================================================================
  public int getDateCompare(String limitDate,int afterDay){
     GregorianCalendar calendar = new GregorianCalendar();
     Date date = calendar.getTime();
     calendar.set(Calendar.DATE,calendar.get(Calendar.DATE)+afterDay);
     date = calendar.getTime();//date是新来的天数，跟今天相比的天数

     iDate=limitDate;
     calendar.set(getYear(),getMonth()-1,getDay());
     Date dateLimit = calendar.getTime();
     return dateLimit.compareTo(date);
  }
//==============================================================================
//比较日期，与现在的日期对比     
//==============================================================================
  public int getDateCompare(String limitDate){
     iDate=limitDate;
     GregorianCalendar calendar = new GregorianCalendar();
     calendar.set(getYear(),getMonth()-1,getDay());
     Date date = calendar.getTime();

     SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
     Date nowDate = new Date();
     return date.compareTo(nowDate);
  }
//==============================================================================
//比较日期，与现在的日期对比  得到天数
//==============================================================================
  public long getLongCompare(String limitDate){

     iDate=limitDate;
     GregorianCalendar calendar = new GregorianCalendar();
     calendar.set(getYear(),getMonth()-1,getDay());
     Date date = calendar.getTime();
     long datePP=date.getTime();
     Date nowDate = new Date();
     long dateNow = nowDate.getTime();
     return ((dateNow-datePP)/(24*60*60*1000));

  }
//==============================================================================
//比较日期，与现在的日期对比  得到信息
//==============================================================================
  public String getStringCompare(String limitDate,int openDay){
         iDate=limitDate;
         GregorianCalendar calendar = new GregorianCalendar();
         calendar.set(getYear(),getMonth()-1,getDay());
         Date date = calendar.getTime();
         long datePP=date.getTime();
         Date nowDate = new Date();
         long dateNow = nowDate.getTime();
         long overDay = ((dateNow-datePP)/(24*60*60*1000));
         String info="";
         return info;

  }
//==============================================================================
//比较日期，与现在的日期对比  得到信息
//==============================================================================
  public String getPicCompare(String limitDate,int openDay){

       iDate=limitDate;
       GregorianCalendar calendar = new GregorianCalendar();
       calendar.set(getYear(),getMonth()-1,getDay());
       Date date = calendar.getTime();
       long datePP=date.getTime();
       Date nowDate = new Date();
       long dateNow = nowDate.getTime();
       long overDay = ((dateNow-datePP)/(24*60*60*1000));
       String info="";
       if (overDay>0){
           info="plaint1.gif";
       }
       if (overDay==0){
           info="plaint2.gif";
       }
       if (overDay<0&&overDay>=-openDay){
           info="plaint2.gif";
       }
       if (overDay<-openDay){
           info="plaint3.gif";
       }
       if (overDay<-150){
           info="plaint3.gif";
       }
       return info;

  }
  /**
   * method diffdate 计算两个日期间相隔的日子
   * @param beforDate 格式:2005-06-20
   * @param afterDate 格式:2005-06-21
   * @return
   */
  public static int diffDate(String beforeDate,String afterDate){
	  String[] tt = beforeDate.split("-");
	  Date firstDate = new Date(Integer.parseInt(tt[0]),Integer.parseInt(tt[1])-1,Integer.parseInt(tt[2]));
	  
	  tt = afterDate.split("-");
	  Date nextDate = new Date(Integer.parseInt(tt[0]),Integer.parseInt(tt[1])-1,Integer.parseInt(tt[2]));
	  return (int)(nextDate.getTime()-firstDate.getTime())/(24*60*60*1000);  
  }
  
  /**
   * 获取今天的日期的字符串
   * @return
   */
  public static String getToday(){
    Calendar cld=Calendar.getInstance();
    java.util.Date date=new Date();
    cld.setTime(date);
    int intMon=cld.get(Calendar.MONTH)+1;
    int intDay=cld.get(Calendar.DAY_OF_MONTH);
    String mons=String.valueOf(intMon);
    String days=String.valueOf(intDay);
    if(intMon<10)
      mons="0"+String.valueOf(intMon);
    if(intDay<10)
      days="0"+String.valueOf(intDay);
    return String.valueOf(cld.get(Calendar.YEAR))+"-"+mons+"-"+days;
  }

	/**
	 * 获取当前月份
	 * @return 返回字符串 格式：两位数
	 */
	public static String getCurrentMonth(){
		String strmonth = null;
	    Calendar cld = Calendar.getInstance();
	    java.util.Date date = new Date();
	    cld.setTime(date);
	    int intMon=cld.get(Calendar.MONTH) + 1;
		if(intMon<10)
			strmonth = "0" + String.valueOf(intMon);
		else
			strmonth = String.valueOf(intMon);
		date = null;
		return strmonth;
	}
  
//  public static String getCurrMonth()
//  {
//    Calendar cld=Calendar.getInstance();
//    java.util.Date date=new Date();
//    cld.setTime(date);
//    int intMon=cld.get(Calendar.MONTH)+1;
//
//    return String.valueOf(intMon).toString();
//  }

  /**
   * 获取昨天的日期的字符串
   */
  public static String getYestoday(){
    Calendar cld = Calendar.getInstance();
    java.util.Date date = new Date();
    cld.setTime(date);
    cld.add(Calendar.DATE,-1);
    int intMon = cld.get(Calendar.MONTH)+1;
    int intDay = cld.get(Calendar.DAY_OF_MONTH);
    String mons = String.valueOf(intMon);
    String days = String.valueOf(intDay);
    if(intMon < 10)
    	mons="0" + String.valueOf(intMon);
    if(intDay < 10)
    	days = "0" + String.valueOf(intDay);
    return String.valueOf(cld.get(Calendar.YEAR)) + "-" + mons + "-" + days;
  }

  /**
   * 此函数用来计算员工的工作天数，如在使用期和离职期该月份的工作日
   * 输入（离职日期，-1）可得该月工作天数  时间以2002-12-14为准
   * 输入（入司时间，1）可的该月工作天数
   */
  public static int getWorkDay(String date , int sign){
    int month=0;
    int week=0;
    int workDay=0;
    Calendar rightNow = Calendar.getInstance();

    DateHandler dateOver=new DateHandler();
    dateOver.setDate(date);

    rightNow.set(rightNow.YEAR,dateOver.getYear());
    rightNow.set(rightNow.MONTH,dateOver.getMonth()-1);
    rightNow.set(rightNow.DATE,dateOver.getDay());

    month = rightNow.get(rightNow.MONTH);

    while(rightNow.get(rightNow.MONTH)==month){
        week=rightNow.get(Calendar.DAY_OF_WEEK);
        if (week==1||week==7){
        }else{
            workDay++;
            System.out.println(rightNow.get(rightNow.DATE));
        }
        rightNow.add(rightNow.DATE,sign);
        }
    return workDay;
  }

  public static void main(String args[]){
	  System.out.println(DateHandler.isSeason("2002-03-02"));
//    String cc ="100.123.342";
//    System.out.println(cc.indexOf(".",3));
//
//    StringTokenizer st=new StringTokenizer(cc,".");
//
//    if (st.countTokens()!=2) {
//
//    String state = st.nextToken();
//    String event = st.nextToken();
//    System.out.println(""+event);
	  String strDate = DateHandler.getDateFromNow(0,"yyyy-MM-dd HH:mm:ss");
      System.out.println("date:" + strDate);
      System.out.println("15:" + strDate.substring(0,16));
      
      Date firstDate = new Date(2006,11,14,18,3,0);
      Date nextDate = new Date(2006,11,15,18,2,0);
      System.out.println("date's num: " + (int)(nextDate.getTime()-firstDate.getTime())/(24*60*60*1000));
//    }
  //System.out.println(DateHandler.getWorkDay("2002-11-14",-1));
  }
}

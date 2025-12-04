/**
 * @filename:Const 2018年06月01日
 * @project OnlineGame    边鹏  V1.0
 * Copyright(c) 2018 BianP Co. Ltd. 
 * All right reserved. 
 */
package com.item.util;

import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

/**
 * 前置系统专用工具类
 * @create Jun 1, 2018 12:16:49 PM
 * @update Jun 6, 2018 3:07:09 PM
 * @author 边鹏
 * @version v1.1.0
 * @history v1.0.1-->新增<code>checkEmail()</code>邮箱验证
 * @history v1.0.2-->新增<code>checkMobileNumber()</code>手机号码验证
 * @history v1.0.3-->新增<code>getSysJournalNo()</code>获取流水号，实现细节为<code>java.util.UUID.randomUUID()</code>
 * @history v1.0.4-->新增<code>moneyYuanToFen()</code>元转分
 * @history v1.0.4-->新增<code>moneyYuanToFenByRound()</code>元转分
 * @history v1.0.4-->新增<code>moneyFenToYuan()</code>分转元
 * @history v1.1.0-->新增<code>getStringSimple()</code>获取一个字符串的简明效果,返回的字符串格式类似于"abcd***hijk"
 */
public class Tools {
	private Tools(){};
	/**
	 * 验证邮箱
	 * @param email
	 * @return
	 */
	public static boolean checkEmail(String email) {
		boolean flag = false;
		try {
			String check = "^([a-z0-9A-Z]+[-|_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
			Pattern regex = Pattern.compile(check);
			Matcher matcher = regex.matcher(email);
			flag = matcher.matches();
		} catch (Exception e) {
			flag = false;
		}
		return flag;
	}

	/**
	 * 验证手机号码
	 * @param mobiles
	 * @return
	 */
	public static boolean checkMobileNumber(String mobileNumber) {
		boolean flag = false;
		try {
			Pattern regex = Pattern
					.compile("^(((13[0-9])|(15([0-3]|[5-9]))|(18[0,5-9]))\\d{8})|(0\\d{2}-\\d{8})|(0\\d{3}-\\d{7})$");
			Matcher matcher = regex.matcher(mobileNumber);
			flag = matcher.matches();
		} catch (Exception e) {
			flag = false;
		}
		return flag;
	}

	/**
     * 获取系统流水号
     * @param length   指定流水号长度
     * @param isNumber 指定流水号是否全由数字组成
     */
    public static String getSysJournalNo(int length, boolean isNumber){
        //replaceAll()之后返回的是一个由十六进制形式组成的且长度为32的字符串
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        if(uuid.length() > length){
            uuid = uuid.substring(0, length);
        }else if(uuid.length() < length){
            for(int i=0; i<length-uuid.length(); i++){
                uuid = uuid + Math.round(Math.random()*9);
            }
        }
        if(isNumber){
            return uuid.replaceAll("a", "1").replaceAll("b", "2").replaceAll("c", "3").replaceAll("d", "4").replaceAll("e", "5").replaceAll("f", "6");
        }else{
            return uuid;
        }
    }
    
    /**
     * 金额元转分
     * @see 注意:该方法可处理贰仟万以内的金额,且若有小数位,则不限小数位的长度
     * @see 注意:如果你的金额达到了贰仟万以上,则不推荐使用该方法,否则计算出来的结果会令人大吃一惊
     * @param amount  金额的元进制字符串
     * @return String 金额的分进制字符串
     */
    public static String moneyYuanToFen(String amount){
        if(StringUtils.isBlank(amount)){
            return null;
        }
        //传入的金额字符串代表的是一个整数
        if(-1 == amount.indexOf(".")){
            return Integer.parseInt(amount) * 100 + "";
        }
        //传入的金额字符串里面含小数点-->取小数点前面的字符串,并将之转换成单位为分的整数表示
        int money_fen = Integer.parseInt(amount.substring(0, amount.indexOf("."))) * 100;
        //取到小数点后面的字符串
        String pointBehind = (amount.substring(amount.indexOf(".") + 1));
        //amount=12.3
        if(pointBehind.length() == 1){
            return money_fen + Integer.parseInt(pointBehind)*10 + "";
        }
        //小数点后面的第一位字符串的整数表示
        int pointString_1 = Integer.parseInt(pointBehind.substring(0, 1));
        //小数点后面的第二位字符串的整数表示
        int pointString_2 = Integer.parseInt(pointBehind.substring(1, 2));
        //amount==12.03,amount=12.00,amount=12.30
        if(pointString_1 == 0){
            return money_fen + pointString_2 + "";
        }else{
            return money_fen + pointString_1*10 + pointString_2 + "";
        }
    }
    /**
     * 金额元转分
     * @see 该方法会将金额中小数点后面的数值,四舍五入后只保留两位....如12.345-->12.35
     * @see 注意:该方法可处理贰仟万以内的金额
     * @see 注意:如果你的金额达到了贰仟万以上,则非常不建议使用该方法,否则计算出来的结果会令人大吃一惊
     * @param amount  金额的元进制字符串
     * @return String 金额的分进制字符串
     */
    public static String moneyYuanToFenByRound(String amount){
        if(StringUtils.isBlank(amount)){
        	return null;
        }
        if(-1 == amount.indexOf(".")){
            return Integer.parseInt(amount) * 100 + "";
        }
        int money_fen = Integer.parseInt(amount.substring(0, amount.indexOf("."))) * 100;
        String pointBehind = (amount.substring(amount.indexOf(".") + 1));
        if(pointBehind.length() == 1){
            return money_fen + Integer.parseInt(pointBehind)*10 + "";
        }
        int pointString_1 = Integer.parseInt(pointBehind.substring(0, 1));
        int pointString_2 = Integer.parseInt(pointBehind.substring(1, 2));
        //下面这种方式用于处理pointBehind=245,286,295,298,995,998等需要四舍五入的情况
        if(pointBehind.length() > 2){
            int pointString_3 = Integer.parseInt(pointBehind.substring(2, 3));
            if(pointString_3 >= 5){
                if(pointString_2 == 9){
                    if(pointString_1 == 9){
                        money_fen = money_fen + 100;
                        pointString_1 = 0;
                        pointString_2 = 0;
                    }else{
                        pointString_1 = pointString_1 + 1;
                        pointString_2 = 0;
                    }
                }else{
                    pointString_2 = pointString_2 + 1;
                }
            }
        }
        if(pointString_1 == 0){
            return money_fen + pointString_2 + "";
        }else{
            return money_fen + pointString_1*10 + pointString_2 + "";
        }
    }
    
    
    /**
     * 金额分转元
     * @see 注意:如果传入的参数中含小数点,则直接原样返回
     * @see 该方法返回的金额字符串格式为<code>00.00</code>,其整数位有且至少有一个,小数位有且长度固定为2
     * @param amount  金额的分进制字符串
     * @return String 金额的元进制字符串
     */
    public static String moneyFenToYuan(String amount){
        if(StringUtils.isBlank(amount)){
        	return null;
        }
        if(amount.indexOf(".") > -1){
            return amount;
        }
        if(amount.length() == 1){
            return "0.0" + amount;
        }else if(amount.length() == 2){
            return "0." + amount;
        }else{
            return amount.substring(0, amount.length()-2) + "." + amount.substring(amount.length()-2);
        }
    }
    
    /**
     * 获取一个字符串的简明效果
     * @return String 返回的字符串格式类似于"abcd***hijk"
     */
    public static String getStringSimple(String data){
        return data.substring(0,4) + "***" + data.substring(data.length()-4);
    }
}

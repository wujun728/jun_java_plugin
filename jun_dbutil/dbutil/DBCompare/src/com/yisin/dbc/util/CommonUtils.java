package com.yisin.dbc.util;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <p>
 * 简述:该类是工具类，提供对字符串，数字等的操作 详述:主要提供了以下几个特性(可以继续扩展):
 * <li>判断字符是否为数字</li>
 * <li>字符串是否是数字串</li>
 * <li>字符是否是小写</li>
 * <li>字符串是否是小写字符</li>
 * <li>字符是否是大写字符</li>
 * <li>字符串是否是大写字符串</li>
 * <li>int值转换为十六进制</li>
 * <li>long值转换为十六进制</li>
 * <li>int值转换为八进制</li>
 * <li>long值转换为八进制</li>
 * <li>int值转换为二进制</li>
 * <li>long值转换为二进制</li>
 * <li>字符串为空对象</li>
 * <li>字符串不为空对象</li>
 * <li>字符串为空字符串</li>
 * <li>字符串为空对象</li>
 * <li>字符串不为空字符串</li>
 * <li>处理为空字符串对象</li>
 * <li>处理为空对象</li>
 * <li>汉字字符编码转换</li>
 * <li>将参数转换为UTF-8编码</li>
 * <li>将参数转换为GBK编码</li>
 * <li>将Object类型转换为String类型</li>
 * <li>将Object类型转换为int类型</li>
 * <li>将Object类型转换为long类型</li>
 * <li>将Object类型转换为float类型</li>
 * <li>将Object类型转换为double类型</li>
 * <li>将String类型转换为int类型</li>
 * <li>将String类型转换为long类型</li>
 * <li>将String类型转换为float类型</li>
 * <li>将String类型转换为double类型</li>
 * </p>
 * 
 * @author yanxf
 * @since 1.0
 * @version 1.0
 */
public final class CommonUtils {
	
	private final static String regxpForHtml = "<([^>]*)>"; // 过滤所有以<开头以>结尾的标签

    /**
     * 数字正则表达式
     */
    private static final Pattern REG_INTEGER = Pattern.compile("(\\+|\\-)?\\d+");
    /**
     * 小写字符表达式
     */
    private static final Pattern IS_LOWCASE = Pattern.compile("[a-z]+");
    /**
     * 大写字符表达式
     */
    private static final Pattern IS_UPPERCASE = Pattern.compile("[A-Z]+");
    /**
     * 十六进制字符
     */
    private static final char[] CHAR_ARRAY = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D',
            'E', 'F' };
    /**
     * 手机号表达式
     */
    private static final Pattern MOBILE_PATTERN = Pattern
            .compile("^(13[0-9]|14[5|7]|15[0-3|5-9]|18[0|2|3|5-9])\\d{8}$");
    /**
     * 邮件地址正则表达式
     */
    private static final Pattern MAIL_PATTERN = Pattern.compile("^\\b[A-Z0-9._%-]+@[A-Z0-9.-]+\\.[A-Z]{2,4}\\b$");

    private CommonUtils() {
    }

    /**
     * <p>
     * 判断字符是否是数字
     * </p>
     * 
     * @author yanxf
     * @since 1.0
     * @param ch
     *            字符
     * @return boolean true:字符是数字 false:字符不是数字
     */
    public static boolean isDigit(final char sch) {
        final int temp = sch & Constants.NUM_INT255;
        return temp >= Constants.NUM_INT48 && temp <= Constants.NUM_INT57;
    }

    /**
     * <p>
     * 字符串是否是数字串
     * </p>
     * 
     * @author yanxf
     * @since 1.0
     * @param string
     *            字符串
     * @return boolean true:数字 false：非数字
     */
    public static boolean isDigit(final String string) {
        boolean digit = false;
        if (CommonUtils.isNotBlank(string)) {
            digit = REG_INTEGER.matcher(string).matches();
        }
        return digit;
    }

    /**
     * <p 字符是否是小写,只能包含a-z字符，不能包含特殊字符
     * </p>
     * 
     * @author yanxf
     * @since 1.0
     * @param ch
     *            字符
     * @return boolean true:小写字符
     */
    public static boolean isLowerCase(final char sch) {
        final int temp = sch & Constants.NUM_INT255;
        return temp >= Constants.NUM_INT97 && temp <= Constants.NUM_INT122;
    }

    /**
     * <p>
     * 字符串是否是小写字符
     * </p>
     * 
     * @author yanxf
     * @since 1.0
     * @param string
     *            字符串
     * @return boolean true:字符串为小写字符串
     */
    public static boolean isLowerCase(final String string) {
        return isNotBlank(string) ? IS_LOWCASE.matcher(string).matches() : false;
    }

    /**
     * <p>
     * 字符是否是大写字符
     * </p>
     * 
     * @author yanxf
     * @since 1.0
     * @param ch
     *            字符
     * @return boolean true:大写字符
     */
    public static boolean isUppercase(final char sch) {
        final int temp = sch & Constants.NUM_INT255;
        return temp >= Constants.NUM_INT65 && temp <= Constants.NUM_INT90;
    }

    /**
     * <p>
     * 字符串是否是大写字符串
     * </p>
     * 
     * @author yanxf
     * @since 1.0
     * @param string
     *            字符串
     * @return boolean true:字符串为大写字符串
     */
    public static boolean isUppercases(final String string) {
        return isNotBlank(string) ? IS_UPPERCASE.matcher(string).matches() : false;
    }

    /**
     * <p>
     * <li>将字符串转换成hexstring</li>
     * </p>
     * 
     * @author yanxf
     * @since 1.0
     * @param sourceStr
     *            需要转换的字符串
     * @return String 转换后的字符串
     */
    public static String toHexString(final String sourceStr) {
        final StringBuffer strBuff = new StringBuffer("");
        if (CommonUtils.isNotBlank(sourceStr)) {
            for (char ch : sourceStr.toCharArray()) {
                strBuff.append(toHexString(ch));
            }
        }
        return strBuff.toString();
    }

    /**
     * <p>
     * <li>简述：将将字节数组转换成十六进制数字</li>
     * <li>详述：字节转换，循环转换字节数组，将单个字节数组转换成十六进制字符</li>
     * </p>
     * 
     * @author yanxf
     * @param bytes
     *            字节数组
     * @return String 返回转换后的字符串
     */
    public static String bytesToHexString(final byte[] bytes) {
        final StringBuilder sBuilder = new StringBuilder("");
        if (bytes != null && bytes.length > Constants.NUM_INT0) {
            for (byte byitem : bytes) {
                final int tmpv = byitem & 0XFF;
                final String thv = Integer.toHexString(tmpv);
                if (thv.length() < Constants.NUM_INT2) {
                    sBuilder.append(Constants.NUM_INT0);
                }
                sBuilder.append(thv);
            }
        }
        return sBuilder.toString();
    }

    private static String toUnsignedString(final long nums, final int shifts) {
        long tmpNums = nums;
        char[] chs = new char[Constants.NUM_INT64];
        int charPosition = Constants.NUM_INT64;
        final int radix = Constants.NUM_INT1 << shifts;
        final int value = radix - Constants.NUM_INT1;
        do {
            chs[--charPosition] = CHAR_ARRAY[(int) (tmpNums & value)];
            tmpNums >>>= shifts;
        } while (Constants.NUM_INT0 < tmpNums);
        return new String(chs, charPosition, Constants.NUM_INT64 - charPosition);
    }

    /**
     * <p>
     * int值转换为十六进制
     * </p>
     * 
     * @author yanxf
     * @since 1.0
     * @param i
     *            int值
     * @return String 十六进制字符串
     */
    public static String toHexString(final int nums) {
        return toUnsignedString(nums, Constants.NUM_INT4);
    }

    /**
     * <p>
     * long值转换为十六进制
     * </p>
     * 
     * @author yanxf
     * @since 1.0
     * @param i
     *            long值
     * @return String 十六进制字符串
     */
    public static String toHexString(final long nums) {
        return toUnsignedString(nums, Constants.NUM_INT4);
    }

    /**
     * <p>
     * int值转换为八进制
     * </p>
     * 
     * @author yanxf
     * @since 1.0
     * @param i
     *            int值
     * @return String 八进制字符串
     */
    public static String toOctalString(final int nums) {
        return toUnsignedString(nums, Constants.NUM_INT3);
    }

    /**
     * <p>
     * long值转换为八进制
     * </p>
     * 
     * @author yanxf
     * @since 1.0
     * @param i
     *            long值
     * @return String 八进制字符串
     */
    public static String toOctalString(final long nums) {
        return toUnsignedString(nums, Constants.NUM_INT3);
    }

    /**
     * <p>
     * int值转换为二进制
     * </p>
     * 
     * @author yanxf
     * @since 1.0
     * @param i
     *            int值
     * @return String 二进制字符串
     */
    public static String toBinaryString(final int nums) {
        return toUnsignedString(nums, Constants.NUM_INT1);
    }

    /**
     * <p>
     * long值转换为二进制
     * </p>
     * 
     * @author yanxf
     * @since 1.0
     * @param i
     *            long值
     * @return String 二进制字符串
     */
    public static String toBinaryString(final long nums) {
        return toUnsignedString(nums, Constants.NUM_INT1);
    }

    /**
     * <p>
     * 判断对象是否为空
     * </p>
     * 
     * @author yanxf
     * @since 1.0
     * @param object
     *            源对象
     * @return boolean true:空对象 false:非空对象
     */
    public static boolean isEmpty(final Object object) {
        return null == object;
    }

    /**
     * <p>
     * 判断空对象不为空
     * </p>
     * 
     * @author yanxf
     * @since 1.0
     * @param object
     *            源对象
     * @return boolean true:非空对象 false:空对象
     */
    public static boolean isNotEmpty(final Object object) {
        return !isEmpty(object);
    }

    /**
     * <p>
     * 字符串为空字符串
     * </p>
     * 
     * @author yanxf
     * @since 1.0
     * @param string
     *            源字符串
     * @return boolean true:空字符串 false:非空字符串
     */
    public static boolean isBlank(final String string) {
        return isEmpty(string) || "".equals(string.trim());
    }

    /**
     * <p>
     * 字符串不为空字符串
     * </p>
     * 
     * @author yanxf
     * @since 1.0
     * @param string
     *            源字符串
     * @return boolean true:非空字符串 false:空字符串
     */
    public static boolean isNotBlank(final String string) {
        return isNotEmpty(string) && !"".equals(string.trim());
    }

    /**
     * <p>
     * 字符串转换成UTF-8字符串
     * </p>
     * 
     * @param string
     *            源字符串
     * @return
     * @throws CommonException
     */
    public static String toUTF8(final String string) {
        if (CommonUtils.isBlank(string)) {
            throw new IllegalArgumentException("The param can not be null.");
        }
        try {
            return new String(string.getBytes(Constants.CHARSET_UTF8), Constants.CHARSET_ISO88591);
        } catch (UnsupportedEncodingException e) {
        	e.printStackTrace();
        }
        return string;
    }

    /**
     * <p>
     * 字符串转换成GBK字符串
     * </p>
     * 
     * @param string
     *            源字符串
     * @return
     * @throws CommonException
     */
    public static String toGBK(final String string) {
        if (CommonUtils.isBlank(string)) {
            throw new IllegalArgumentException("The param can not be null.");
        }
        try {
            return new String(string.getBytes(Constants.CHARSET_GBK), Constants.CHARSET_ISO88591);
        } catch (UnsupportedEncodingException e) {
        	e.printStackTrace();
        }
        return string;
    }

    /**
     * <p>
     * <li>验证手机号是否正确</li>
     * </p>
     * 
     * @author yanxf
     * @param mobile
     *            手机号
     * @return boolean true:正确的手机号 false:错误的手机号
     */
    public static boolean isLegalMobile(final String mobile) {
        return CommonUtils.isNotBlank(mobile) ? MOBILE_PATTERN.matcher(mobile).find() : false;
    }

    /**
     * <p>
     * <li>验证邮箱是否非法</li>
     * </p>
     * 
     * @author yanxf
     * @param mail
     *            邮件地址
     * @return boolean true:是否是正确的邮件地址 false:非法邮件地址
     */
    public static boolean isLegalMail(final String mail) {
        return isNotBlank(mail) ? MAIL_PATTERN.matcher(mail.toUpperCase(Locale.getDefault())).find() : false;
    }

    /**
     * <p>
     * <li>获取ByteArrayOutputStream字节，并转换成字符串,默认使用UTF-8字符集</li>
     * </p>
     * 
     * @param baos
     *            字节数组输出流
     * @return String 将内容转换成字符串
     * @author yanxf
     * @throws CommonException
     * @since 1.0
     */
    public static String byteArrayStreamToString(ByteArrayOutputStream baos) {
        return byteArrayStreamToString(baos, Constants.CHARSET_UTF8);
    }

    /**
     * <p>
     * <li>获取ByteArrayOutputStream字节，并转换成字符串</li>
     * </p>
     * 
     * @param baos
     *            字节数组输出流
     * @param charset
     *            字符集名称
     * @return String 将内容转换成字符串
     * @author yanxf
     * @throws CommonException
     * @since 1.0
     */
    public static String byteArrayStreamToString(ByteArrayOutputStream baos, String charset) {
        if (null == baos) {
            throw new IllegalArgumentException("The param baos can not be null.");
        }
        try {
            return baos.toString(Constants.CHARSET_UTF8);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
		return null;
    }

    /**
     * 处理Null或空字符串
     * 
     * @author yisin
     * @date 2012-11-30 上午10:45:17
     * @param string
     * @return String
     */
    public static String excNullToString(String string) {
        return excNullToString(string, "");
    }

    /**
     * 处理Null或空字符串
     * 
     * @author yisin
     * @date 2012-11-30 上午10:45:55
     * @param string
     * @param added
     * @return String
     */
    public static String excNullToString(String string, String added) {
        if (isEmpty(string)) {
            string = added;
        }
        return string;
    }

    /**
     * 处理Null或空对象
     * 
     * @author yisin
     * @date 2012-11-30 上午10:45:17
     * @param Object
     * @return Object
     */
    public static Object excNullToObject(Object obj) {
        return excNullToObject(obj, new Object());
    }

    /**
     * 处理Null或空对象
     * 
     * @author yisin
     * @date 2012-11-30 上午10:45:55
     * @param Object
     *            obj
     * @param Object
     *            added
     * @return Object
     */
    public static Object excNullToObject(Object obj, Object added) {
        if (isEmpty(obj)) {
            obj = added;
        }
        return obj;
    }

    /**
     * 将字Object转换为字符串
     * 
     * @author yisin
     * @date 2012-11-30 上午11:29:11
     * @param Object
     * @return
     */
    public static String objectToString(Object obj) {
        return objectToString(obj, null);
    }

    /**
     * 将字Object转换为字符串
     * 
     * @author yisin
     * @date 2012-11-30 上午11:29:11
     * @param Object
     * @param string
     * @return
     */
    public static String objectToString(Object obj, String added) {
        String result = added;
        if (obj != null) {
            result = String.valueOf(obj);
        }
        return result;
    }

    /**
     * 将字符串转换成整数类型，如果为空则转换成0
     * 
     * @author yisin
     * @date 2012-11-30 上午11:29:11
     * @param string
     * @return
     */
    public static int stringToInt(String string) {
        return stringToInt(string, 0);
    }

    /**
     * 将字符串转换成整数类型，如果为空则转换成指定值
     * 
     * @author yisin
     * @date 2012-11-30 上午11:29:11
     * @param string
     * @return
     */
    public static int stringToInt(String string, int added) {
        int result = 0;
        try {
            result = Integer.parseInt(string);
        } catch (Exception e) {
            result = added;
        }
        return result;
    }

    /**
     * 将object转换成整数类型，如果为空则转换成0
     * 
     * @author yisin
     * @date 2012-11-30 上午11:29:11
     * @param Object
     * @return
     */
    public static int objectToInt(Object obj) {
        return objectToInt(obj, 0);
    }

    /**
     * 将object转换成整数类型，如果为空则转换成指定值
     * 
     * @author yisin
     * @date Object-11-30 上午11:29:11
     * @param string
     * @return
     */
    public static int objectToInt(Object obj, int added) {
        if (obj instanceof Integer) {
            return (Integer) obj;
        } else if (obj instanceof Float) {
            return ((Float) obj).intValue();
        } else if (obj instanceof Double) {
            return ((Double) obj).intValue();
        }
        return stringToInt(objectToString(obj), added);
    }

    /**
     * 将字符串转换成长整数类型，如果为空则转换成0
     * 
     * @author yisin
     * @date 2012-11-30 上午11:29:11
     * @param string
     * @return
     */
    public static long stringToLong(String string) {
        return stringToLong(string, 0);
    }

    /**
     * 将字符串转换成长整数类型，如果为空则转换成指定值
     * 
     * @author yisin
     * @date 2012-11-30 上午11:29:11
     * @param string
     * @return
     */
    public static long stringToLong(String string, long added) {
        long result = 0;
        try {
            result = Long.parseLong(string);
        } catch (Exception e) {
            result = added;
        }
        return result;
    }

    /**
     * 将字符串转换成长整数类型，如果为空则转换成0
     * 
     * @author yisin
     * @date 2012-11-30 上午11:29:11
     * @param string
     * @return
     */
    public static long objectToLong(Object obj) {
        return objectToLong(obj, 0);
    }

    /**
     * 将字符串转换成长整数类型，如果为空则转换成指定值
     * 
     * @author yisin
     * @date 2012-11-30 上午11:29:11
     * @param string
     * @return
     */
    public static long objectToLong(Object obj, long added) {
        long result = 0;
        try {
            result = Long.parseLong(obj.toString());
        } catch (Exception e) {
            result = added;
        }
        return result;
    }

    /**
     * 将字符串转换成float类型
     * 
     * @author yisin
     * @date 2012-12-11 上午11:58:44
     * @param string
     * @return float
     */
    public static float stringToFloat(String string) {
        return stringToFloat(string, 0.0f);
    }

    /**
     * 将字符串转换成float类型,如果为空则转为指定的值
     * 
     * @author yisin
     * @date 2012-12-11 上午11:58:44
     * @param string
     * @return float
     */
    public static float stringToFloat(String string, float added) {
        float result = 0.0f;
        try {
            result = Float.parseFloat(string);
        } catch (Exception e) {
            result = added;
        }
        return result;
    }

    /**
     * 将字符串转换成float类型，如果为空则转换成0
     * 
     * @author yisin
     * @date 2012-11-30 上午11:29:11
     * @param string
     * @return
     */
    public static float objectToFloat(Object obj) {
        return objectToFloat(obj, 0f);
    }

    /**
     * 将字符串转换成float类型，如果为空则转换成指定值
     * 
     * @author yisin
     * @date 2012-11-30 上午11:29:11
     * @param string
     * @return
     */
    public static float objectToFloat(Object obj, float added) {
        float result = 0;
        try {
            result = Float.parseFloat(obj.toString());
        } catch (Exception e) {
            result = added;
        }
        return result;
    }

    /**
     * 将字符串转换成double类型,如果为空则转为指定的值
     * 
     * @author yisin
     * @date 2012-12-11 上午11:58:44
     * @param string
     * @return double
     */
    public static double stringToDouble(String string) {
        return stringToDouble(string, 0.0d);
    }

    /**
     * 将字符串转换成double类型,如果为空则转为指定的值
     * 
     * @author yisin
     * @date 2012-12-11 上午11:58:44
     * @param string
     * @return double
     */
    public static double stringToDouble(String string, double added) {
        double result = 0.0d;
        try {
            result = Double.parseDouble(string);
        } catch (Exception e) {
            result = added;
        }
        return result;
    }

    /**
     * 将字符串转换成double类型，如果为空则转换成0
     * 
     * @author yisin
     * @date 2012-11-30 上午11:29:11
     * @param string
     * @return
     */
    public static double objectToDouble(Object obj) {
        return objectToDouble(obj, 0f);
    }

    /**
     * 将字符串转换成double类型，如果为空则转换成指定值
     * 
     * @author yisin
     * @date 2012-11-30 上午11:29:11
     * @param string
     * @return
     */
    public static double objectToDouble(Object obj, double added) {
        double result = 0;
        try {
            result = Double.parseDouble(obj.toString());
        } catch (Exception e) {
            result = added;
        }
        return result;
    }

    public static String firstCharToUpperCase(String content) {
        if (!isEmpty(content)) {
            String tou = content.substring(0, 1);
            String wei = content.substring(1);
            content = tou.toUpperCase() + wei;
        }
        return content;
    }

    public static String firstCharToLowerCase(String content) {
        if (!isEmpty(content)) {
            String tou = content.substring(0, 1);
            String wei = content.substring(1);
            content = tou.toLowerCase() + wei;
        }
        return content;
    }

    /**
     * 将Unicode编码转换为正常字符
     * 
     * @author yisin
     * @date 2012-12-11 下午12:03:57
     * @param param
     * @return
     */
    public static String stringUncode(String param) {
        if (param != null && !param.trim().equals("")) {
            try {
                param = URLDecoder.decode(param, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return param;
    }

    /**
     * 将字符转换为Unicode编码
     * 
     * @author yisin
     * @date 2012-12-11 下午12:04:05
     * @param param
     * @return
     */
    public static String stringEncode(String param) {
        if (param != null && !param.trim().equals("")) {
            try {
                param = URLEncoder.encode(param, "utf-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return param;
    }
    
    /**
     * 字符串格式化
     * @param resource
     * @param target
     * @return
     */
    public static String format(String resource, String... target) {
    	if(resource == null){
    		throw new IllegalArgumentException("parameter is null");
    	}
    	if(target != null && target.length > 0){
    		for (int i = 0, k = target.length; i < k; i++) {
    			resource = resource.replace("{" + i + "}", target[i]);
			}
    	}
    	return resource;
    }
    
    public static long getTimeUnix(){
    	return new Date().getTime();
    }
    
    /**  
     *   
     * 基本功能：过滤所有以"<"开头以">"结尾的标签  
     * <p>  
     *   
     * @param str  
     * @return String  
     */  
    public static String filterHtml(String str) {   
        Pattern pattern = Pattern.compile(regxpForHtml);   
        Matcher matcher = pattern.matcher(str);   
        StringBuffer sb = new StringBuffer();   
        boolean result1 = matcher.find();   
        while (result1) {   
            matcher.appendReplacement(sb, "");   
            result1 = matcher.find();   
        }   
        matcher.appendTail(sb);   
        return sb.toString();   
    }

}

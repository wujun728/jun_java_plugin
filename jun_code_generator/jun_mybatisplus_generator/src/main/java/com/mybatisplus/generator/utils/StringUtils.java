package com.mybatisplus.generator.utils;



import java.io.UnsupportedEncodingException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Administrator
 * @date 2018年10月24日 上午11:48:32
 */
public class StringUtils {

    public static boolean chkPass(String str){
        //定义一个boolean值，用来表示是否包含数字
        boolean isDigit = false;
        //定义一个boolean值，用来表示是否包含字母
        boolean isLetter = false;
        //定义一个boolean值，用来表示是否包含特殊字符
        boolean isSpecial = false;
        for (int i = 0; i < str.length(); i++) {
            //用char包装类中的判断数字的方法判断每一个字符
            if (Character.isDigit(str.charAt(i))) {
                isDigit = true;
                //用char包装类中的判断字母的方法判断每一个字符
            } else if (Character.isLetter(str.charAt(i))) {
                isLetter = true;
            } else if (isSpecialChar(str)){
                isSpecial=true;
            }
        }
        String regex = "(.*){6,20}$";
        boolean isRight = isDigit && isLetter && isSpecial && str.matches(regex);
        return isRight;
    }
    /**
     * 判断是否含有特殊字符
     *
     * @param str
     * @return true为包含，false为不包含
     */
    public static boolean isSpecialChar(String str) {
        String regEx = "[ _`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]|\n|\r|\t";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.find();
    }
    public static String toUtf8String(String s) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c >= 0 && c <= 255) {
                sb.append(c);
            } else {
                byte[] b;
                try {
                    b = Character.toString(c).getBytes("utf-8");
                } catch (Exception ex) {
                    System.out.println(ex);
                    b = new byte[0];
                }
                for (int j = 0; j < b.length; j++) {
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
     * 去掉String首尾空格
     */
    public static String trim(String str) {
        if (isEmpty(str)) {
            return str.trim();
        } else {
            return null;
        }
    }

    /**
     * 判断字符串是否为空
     */
    public static boolean isEmpty(String str) {
        if (null != str && !"".equals(str) && !"Null".equalsIgnoreCase(str)) {
            return false;
        } else {
            return true;
        }
    }

    public static boolean isNotEmpty_alex(String str) {
        if (null != str && !"".equals(str.trim())
                && !"Null".equalsIgnoreCase(str.trim())) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 判断String非空,且不为"",返回true
     *
     * @param param
     * @return
     */
    public static boolean isNotEmpty(String param) {
        if (null == param || param.length() == 0) {
            return false;
        }
        return true;
    }

    /**
     * 判断String非空,且去除空格不为"",返回true
     *
     * @param param
     * @return
     */
    public static boolean isTrimNotEmpty(String param) {
        if (null == param || param.length() == 0 || param.trim().length() == 0) {
            return false;
        }
        return true;
    }

    public static boolean isTrimObjNotEmpty(Object param) {
        if (null == param || param.toString().length() == 0 || param.toString().trim().length() == 0) {
            return false;
        }
        return true;
    }

    /**
     * 判断String非空,且去除空格不为"",返回true
     *
     * @param param
     * @return
     */
    public static boolean isTrimEmpty(String param) {
        return !isTrimNotEmpty(param);
    }

    /**
     * 处理 mySQL 查询结果为空，输出"Null"的情况。
     *
     * @param param
     * @return
     */
    public static String transNullValue(String param) {

        if (param == null || "null".equalsIgnoreCase(param)) {
            return "";
        }
        return param;
    }

    /**
     * 判断字符串是否为空或-1 不为空则返回true 为空则返回false
     */
    public static boolean isCorrect(String str) {
        if (null != str && !"".equals(str) && !"null".equalsIgnoreCase(str)
                && !"-1".equals(str)) {
            return true;
        } else {
            return false;
        }
    }

    /***************************************************************************
     * 界面字符转换，如：'测' 转成 '%E6%B5%8B' 这样方面在url中传中文
     *
     * @param str
     * @return
     */
    public static String encodeURL(String str) {
        if (isEmpty(str)) {
            try {
                return java.net.URLEncoder.encode(str.trim(), "UTF-8");
            } catch (UnsupportedEncodingException e) {
                return "";
            }
        } else {
            return "";
        }
    }

    /***************************************************************************
     * 界面字符解码，如： '%E6%B5%8B' 转成 '测'
     *
     * @param str
     * @return
     */
    public static String decodeURL(String str) {
        if (isEmpty(str)) {
            try {
                return java.net.URLDecoder.decode(str.trim(), "UTF-8");
            } catch (UnsupportedEncodingException e) {
                return "";
            }
        } else {
            return "";
        }
    }

    // 文本格式函数，去除中英文标点符号，空格
    public static String textFormat(String str) {
        String text = null;
        String stext = null;
        Pattern pa = Pattern.compile("[.,\"\\?!:']");
        Matcher ma = pa.matcher(str);
        text = ma.replaceAll("");// 除去英文标点符号
        pa = Pattern.compile(" {2,}");// 去除多余空格
        ma = pa.matcher(text);
        stext = ma.replaceAll("");
        // stext =stext.replaceAll("[^\\u4e00-\\u9fa5]",
        // "");//汉字所在的utf-8编码范围，此句仅输出汉字，且输出中不包含标点符号，英文字母
        return stext;
    }

    // 字符串切割函数，将格式化后的字符串切割为不同长度的短字符串
    public static List<String> cutText(String text) {
        List<String> list = new ArrayList<String>();
        String temp = textFormat(text);
        int i = 0, j = 0;
        // 将字符串切割为长度为4,3,2位的长度
        for (i = 0; i < temp.length() - 4; i++) {
            j = i + 4;
            list.add(temp.substring(i, j));
        }
        for (i = 0; i < temp.length() - 3; i++) {
            j = i + 3;
            list.add(temp.substring(i, j));
        }
        for (i = 0; i < temp.length() - 2; i++) {
            j = i + 2;
            list.add(temp.substring(i, j));
        }
        return list;
    }

    // 检查字符串中是否含有非法字符,有则返回 包含关键字
    public static String auditText(String text, List<String> keylist) {
        StringBuffer sb = new StringBuffer();
        if (null != text) {
            for (String keyword : keylist) {
                if (text.indexOf(keyword) > 0) {
                    sb.append(keyword);
                    sb.append(",");
                }
            }
        }
        return sb.toString();
    }

    // 检查字符串中是否含有非法字符,有则返回 包含关键字
    public static String checkText(List<String> textlist, List<String> keylist) {
        StringBuffer sb = new StringBuffer();
        List<String> text = new ArrayList<String>();
        text = textlist;
        for (String check : text) {
            for (String keyword : keylist) {
                if (check.contains(keyword) || keyword.contains(check)) {
                    sb.append(keyword);
                    sb.append(",");
                }
            }
        }
        return sb.toString();
    }

    /**
     * 截取邮件中的邮件名
     *
     * @param mailName
     * @return
     */
    public static String spliteEmail(String mailName) {
        String result = "";
        if (StringUtils.isEmpty(mailName)) {
            int index = mailName.indexOf("@");
            if (index > -1) {
                result = mailName.substring(0, index);
            } else {
                result = mailName.replace("@", "");
            }

        }
        return result;
    }

    public static String[] spliteString(String regex, String param) {

        String[] result = {};
        if (isNotEmpty(param)) {
            result = param.split(regex);
        }
        return result;
    }

    public static String replaceStr(String param, int regex, char value) {
        StringBuffer buffer = new StringBuffer();
        if (isTrimNotEmpty(param)) {
            if (regex <= 1)
                regex = 1;
            if (regex >= 12)
                regex = 12;
            char[] paramArray = param.toCharArray();
            paramArray[regex - 1] = value;
            for (int index = 0; index < paramArray.length; index++) {
                buffer.append(paramArray[index]);
            }
        }
        return buffer.toString();
    }

    public static String splitFileName(String fileUrl) {

        String result = "";
        if (isTrimEmpty(fileUrl)) {
            return result;
        }
        String temp[] = fileUrl.replaceAll("\\\\", "/").split("/");
        if (temp.length > 1) {
            result = temp[temp.length - 1];
        }

        return result;
    }

    // 去掉字符串空格
    public static String replaceBlank(String str) {
        String dest = "";
        if (str != null) {
            Pattern p = Pattern.compile("\\s*|\t|\r|\n");
            Matcher m = p.matcher(str);
            dest = m.replaceAll("");
        }
        return dest;
    }

    public static boolean isBlank(String str) {
        int strLen;
        if ((str == null) || ((strLen = str.length()) == 0)) {
            return true;
        }
        for (int i = 0; i < strLen; ++i) {
            if (!(Character.isWhitespace(str.charAt(i)))) {
                return false;
            }
        }
        return true;
    }

    public static boolean isNotBlank(String str) {
        return (!(isBlank(str)));
    }

    public static String defaultString(String str) {
        return ((str == null) ? "" : str);
    }

    public static String defaultString(String str, String defaultStr) {
        return ((str == null) ? defaultStr : str);
    }

    /**
     * 生成随即密码
     *
     * @param len 生成的密码的总长度
     * @return 密码的字符串
     */
    public static String genRandomNum(int len) {
        // 35是因为数组是从0开始的，26个字母+10个数字
        final int maxNum = 36;
        int i; // 生成的随机数
        int count = 0; // 生成的密码的长度
        char[] str = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k',
                'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w',
                'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};

        StringBuffer pwd = new StringBuffer("");
        Random r = new Random();
        while (count < len) {
            // 生成随机数，取绝对值，防止生成负数，
            i = Math.abs(r.nextInt(maxNum)); // 生成的数最大为36-1
            if (i >= 0 && i < str.length) {
                pwd.append(str[i]);
                count++;
            }
        }

        return pwd.toString();
    }

    public static String genRandomInt(int len) {
        // 35是因为数组是从0开始的，26个字母+10个数字
        final int maxNum = 36;
        int i; // 生成的随机数
        int count = 0; // 生成的密码的长度
        char[] str = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};

        StringBuffer pwd = new StringBuffer("");
        Random r = new Random();
        while (count < len) {
            // 生成随机数，取绝对值，防止生成负数，
            i = Math.abs(r.nextInt(maxNum)); // 生成的数最大为36-1
            if (i >= 0 && i < str.length) {
                pwd.append(str[i]);
                count++;
            }
        }

        return pwd.toString();
    }

    public static boolean regex(String regex, String content) {
        Pattern pattern = Pattern.compile(regex);
        Matcher match = pattern.matcher(content);
        return match.matches();
    }

    public static List removeDuplicate(List list) {
        HashSet h = new HashSet(list);
        list.clear();
        list.addAll(h);
        return list;
    }
    public static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        Map<Object,Boolean> seen = new ConcurrentHashMap<>();
        return t -> seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }

    /***
     * 首字母大写
     * @param str
     * @return
     */
    public static String firstUpper(String str){
        return str.substring(0,1).toUpperCase()+str.substring(1);
    }

    /**
     * 首字母小写
     * @param str
     * @return
     */
    public static String firstLower(String str){
        return str.substring(0,1).toLowerCase()+str.substring(1);
    }

    /***
     * 移除tab_,tb_
     * @return
     */
    public static String replaceTab(String str){
        return str.replaceFirst("tab_","").replaceFirst("tb_","");
    }

    /***
     * 将下划线替换掉
     * @param str
     * @return
     */
    public static String replace_(String str){
        //根据下划线分割
        String[] split = str.split("_");
        //循环组装
        String result = split[0];
        if(split.length>1){
            for (int i = 1; i < split.length; i++) {
                result+=firstUpper(split[i]);
            }
        }
        return result;
    }


    public static void main(String[] args) {
        System.out.println(StringUtils.chkPass("aaa@888"));
    }
}

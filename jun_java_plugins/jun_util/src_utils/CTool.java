package com.gootrip.util;

/**
 * 此类中收集Java编程中WEB开发常用到的一些工具。
 * 为避免生成此类的实例，构造方法被申明为private类型的。
 * @author 
 */
import java.io.IOException;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.util.Date;


public class CTool {
    /**
     * 私有构造方法，防止类的实例化，因为工具类不需要实例化。
     */
    private CTool() {
    }

    /**
      <pre>
     * 例：
     * String strVal="This is a dog";
     * String strResult=CTools.replace(strVal,"dog","cat");
     * 结果：
     * strResult equals "This is cat"
     *
     * @param strSrc 要进行替换操作的字符串
     * @param strOld 要查找的字符串
     * @param strNew 要替换的字符串
     * @return 替换后的字符串
      <pre>
     */
    public static final String replace(String strSrc, String strOld,
                                       String strNew) {
        if (strSrc == null || strOld == null || strNew == null)
            return "";

        int i = 0;
        
        if (strOld.equals(strNew)) //避免新旧字符一样产生死循环
        	return strSrc;
        
        if ((i = strSrc.indexOf(strOld, i)) >= 0) {
            char[] arr_cSrc = strSrc.toCharArray();
            char[] arr_cNew = strNew.toCharArray();

            int intOldLen = strOld.length();
            StringBuffer buf = new StringBuffer(arr_cSrc.length);
            buf.append(arr_cSrc, 0, i).append(arr_cNew);

            i += intOldLen;
            int j = i;

            while ((i = strSrc.indexOf(strOld, i)) > 0) {
                buf.append(arr_cSrc, j, i - j).append(arr_cNew);
                i += intOldLen;
                j = i;
            }

            buf.append(arr_cSrc, j, arr_cSrc.length - j);

            return buf.toString();
        }

        return strSrc;
    }

    /**
     * 用于将字符串中的特殊字符转换成Web页中可以安全显示的字符串
     * 可对表单数据据进行处理对一些页面特殊字符进行处理如'<','>','"',''','&'
     * @param strSrc 要进行替换操作的字符串
     * @return 替换特殊字符后的字符串
     * @since  1.0
     */

    public static String htmlEncode(String strSrc) {
        if (strSrc == null)
            return "";

        char[] arr_cSrc = strSrc.toCharArray();
        StringBuffer buf = new StringBuffer(arr_cSrc.length);
        char ch;

        for (int i = 0; i < arr_cSrc.length; i++) {
            ch = arr_cSrc[i];

            if (ch == '<')
                buf.append("&lt;");
            else if (ch == '>')
                buf.append("&gt;");
            else if (ch == '"')
                buf.append("&quot;");
            else if (ch == '\'')
                buf.append("&#039;");
            else if (ch == '&')
                buf.append("&amp;");
            else
                buf.append(ch);
        }

        return buf.toString();
    }

    /**
     * 用于将字符串中的特殊字符转换成Web页中可以安全显示的字符串
     * 可对表单数据据进行处理对一些页面特殊字符进行处理如'<','>','"',''','&'
     * @param strSrc 要进行替换操作的字符串
     * @param quotes 为0时单引号和双引号都替换，为1时不替换单引号，为2时不替换双引号，为3时单引号和双引号都不替换
     * @return 替换特殊字符后的字符串
     * @since  1.0
     */
    public static String htmlEncode(String strSrc, int quotes) {

        if (strSrc == null)
            return "";
        if (quotes == 0) {
            return htmlEncode(strSrc);
        }

        char[] arr_cSrc = strSrc.toCharArray();
        StringBuffer buf = new StringBuffer(arr_cSrc.length);
        char ch;

        for (int i = 0; i < arr_cSrc.length; i++) {
            ch = arr_cSrc[i];
            if (ch == '<')
                buf.append("&lt;");
            else if (ch == '>')
                buf.append("&gt;");
            else if (ch == '"' && quotes == 1)
                buf.append("&quot;");
            else if (ch == '\'' && quotes == 2)
                buf.append("&#039;");
            else if (ch == '&')
                buf.append("&amp;");
            else
                buf.append(ch);
        }

        return buf.toString();
    }

    /**
     * 和htmlEncode正好相反
     * @param strSrc 要进行转换的字符串
     * @return 转换后的字符串
     * @since  1.0
     */
    public static String htmlDecode(String strSrc) {
        if (strSrc == null)
            return "";
        strSrc = strSrc.replaceAll("&lt;", "<");
        strSrc = strSrc.replaceAll("&gt;", ">");
        strSrc = strSrc.replaceAll("&quot;", "\"");
        strSrc = strSrc.replaceAll("&#039;", "'");
        strSrc = strSrc.replaceAll("&amp;", "&");
        return strSrc;
    }

    /**
     * 在将数据存入数据库前转换
     * @param strVal 要转换的字符串
     * @return 从“ISO8859_1”到“GBK”得到的字符串
     * @since  1.0
     */
    public static String toChinese(String strVal) {
        try {
            if (strVal == null) {
                return "";
            } else {
                strVal = strVal.trim();
                strVal = new String(strVal.getBytes("ISO8859_1"), "GBK");
                return strVal;
            }
        } catch (Exception exp) {
            return "";
        }
    }
    /**
     * 编码转换 从UTF-8到GBK
     * @param strVal
     * @return
     */
    public static String toGBK(String strVal) {
        try {
            if (strVal == null) {
                return "";
            } else {
                strVal = strVal.trim();
                strVal = new String(strVal.getBytes("UTF-8"), "GBK");
                return strVal;
            }
        } catch (Exception exp) {
            return "";
        }
    }

    /**
     * 将数据从数据库中取出后转换   *
     * @param strVal 要转换的字符串
     * @return 从“GBK”到“ISO8859_1”得到的字符串
     * @since  1.0
     */
    public static String toISO(String strVal) {
        try {
            if (strVal == null) {
                return "";
            } else {
                strVal = new String(strVal.getBytes("GBK"), "ISO8859_1");
                return strVal;
            }
        } catch (Exception exp) {
            return "";
        }
    }
    public static String gbk2UTF8(String strVal) {
        try {
            if (strVal == null) {
                return "";
            } else {
                strVal = new String(strVal.getBytes("GBK"), "UTF-8");
                return strVal;
            }
        } catch (Exception exp) {
            return "";
        }
    }
    public static String ISO2UTF8(String strVal) {
       try {
           if (strVal == null) {
               return "";
           } else {
               strVal = new String(strVal.getBytes("ISO-8859-1"), "UTF-8");
               return strVal;
           }
       } catch (Exception exp) {
           return "";
       }
   }
   public static String UTF82ISO(String strVal) {
       try {
           if (strVal == null) {
               return "";
           } else {
               strVal = new String(strVal.getBytes("UTF-8"), "ISO-8859-1");
               return strVal;
           }
       } catch (Exception exp) {
           return "";
       }
   }



    /**
     *显示大文本块处理(将字符集转成ISO)
     *@deprecated
     *@param str 要进行转换的字符串
     *@return 转换成html可以正常显示的字符串
     */
    public static String toISOHtml(String str) {
        return toISO(htmlDecode(null2Blank((str))));
    }

    /**
     *实际处理 return toChineseNoReplace(null2Blank(str));
     *主要应用于老牛的信息发布
     *@param str 要进行处理的字符串
     *@return 转换后的字符串
     *@see fs_com.utils.CTools#toChinese
     *@see fs_com.utils.CTools#null2Blank
     */
    public static String toChineseAndHtmlEncode(String str, int quotes) {
        return htmlEncode(toChinese(str), quotes);
    }

    /**
     *把null值和""值转换成&nbsp;
     *主要应用于页面表格格的显示
     *@param str 要进行处理的字符串
     *@return 转换后的字符串
     */
    public static String str4Table(String str) {
        if (str == null)
            return "&nbsp;";
        else if (str.equals(""))
            return "&nbsp;";
        else
            return str;
    }

    /**
     * String型变量转换成int型变量
     * @param str 要进行转换的字符串
     * @return intVal 如果str不可以转换成int型数据，返回-1表示异常,否则返回转换后的值
     * @since  1.0
     */
    public static int str2Int(String str) {
        int intVal;

        try {
            intVal = Integer.parseInt(str);
        } catch (Exception e) {
            intVal = 0;
        }

        return intVal;
    }

    public static double str2Double(String str) {
        double dVal = 0;

        try {
            dVal = Double.parseDouble(str);
        } catch (Exception e) {
            dVal = 0;
        }

        return dVal;
    }


    public static long str2Long(String str) {
        long longVal = 0;

        try {
            longVal = Long.parseLong(str);
        } catch (Exception e) {
            longVal = 0;
        }

        return longVal;
    }

    public static float stringToFloat(String floatstr) {
        Float floatee;
        floatee = Float.valueOf(floatstr);
        return floatee.floatValue();
    }

    //change the float type to the string type
    public static String floatToString(float value) {
        Float floatee = new Float(value);
        return floatee.toString();
    }

    /**
     *int型变量转换成String型变量
     *@param intVal 要进行转换的整数
     *@return str 如果intVal不可以转换成String型数据，返回空值表示异常,否则返回转换后的值
     */
    /**
     *int型变量转换成String型变量
     *@param intVal 要进行转换的整数
     *@return str 如果intVal不可以转换成String型数据，返回空值表示异常,否则返回转换后的值
     */
    public static String int2Str(int intVal) {
        String str;

        try {
            str = String.valueOf(intVal);
        } catch (Exception e) {
            str = "";
        }

        return str;
    }

    /**
     *long型变量转换成String型变量
     *@param longVal 要进行转换的整数
     *@return str 如果longVal不可以转换成String型数据，返回空值表示异常,否则返回转换后的值
     */

    public static String long2Str(long longVal) {
        String str;

        try {
            str = String.valueOf(longVal);
        } catch (Exception e) {
            str = "";
        }

        return str;
    }

    /**
     *null 处理
     *@param str 要进行转换的字符串
     *@return 如果str为null值，返回空串"",否则返回str
     */
    public static String null2Blank(String str) {
        if (str == null)
            return "";
        else
            return str;
    }

    /**
     *null 处理
     *@param d 要进行转换的日期对像
     *@return 如果d为null值，返回空串"",否则返回d.toString()
     */

    public static String null2Blank(Date d) {
        if (d == null)
            return "";
        else
            return d.toString();
    }

    /**
     *null 处理
     *@param str 要进行转换的字符串
     *@return 如果str为null值，返回空串整数0,否则返回相应的整数
     */
    public static int null2Zero(String str) {
        int intTmp;
        intTmp = str2Int(str);
        if (intTmp == -1)
            return 0;
        else
            return intTmp;
    }
    /**
     * 把null转换为字符串"0"
     * @param str
     * @return
     */
    public static String null2SZero(String str) {
        str = CTool.null2Blank(str);
        if (str.equals(""))
            return "0";
        else
            return str;
    }

    /**
     * sql语句 处理
     * @param sql 要进行处理的sql语句
     * @param dbtype 数据库类型
     * @return 处理后的sql语句
     */
    public static String sql4DB(String sql, String dbtype) {
        if (!dbtype.equalsIgnoreCase("oracle")) {
            sql = CTool.toISO(sql);
        }
        return sql;
    }

    /**
     * 对字符串进行md5加密
     * @param s 要加密的字符串
     * @return md5加密后的字符串
     */
    public final static String MD5(String s) {
        char hexDigits[] = {
                           '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                           'a', 'b', 'c', 'd',
                           'e', 'f'};
        try {
            byte[] strTemp = s.getBytes();
            MessageDigest mdTemp = MessageDigest.getInstance("MD5");
            mdTemp.update(strTemp);
            byte[] md = mdTemp.digest();
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            return null;
        }
    }
    /**
     * 字符串从GBK编码转换为Unicode编码
     * @param text
     * @return
     */
    public static String StringToUnicode(String text) {
        String result = "";
        int input;
        StringReader isr;
        try {
            isr = new StringReader(new String(text.getBytes(), "GBK"));
        } catch (UnsupportedEncodingException e) {
            return "-1";
        }
        try {
            while ((input = isr.read()) != -1) {
                result = result + "&#x" + Integer.toHexString(input) + ";";

            }
        } catch (IOException e) {
            return "-2";
        }
        isr.close();
        return result;

    }
    /**
     * 
     * @param inStr
     * @return
     */
    public static String gb2utf(String inStr) {
        char temChr;
        int ascInt;
        int i;
        String result = new String("");
        if (inStr == null) {
            inStr = "";
        }
        for (i = 0; i < inStr.length(); i++) {
            temChr = inStr.charAt(i);
            ascInt = temChr + 0;
            //System.out.println("1=="+ascInt);
            //System.out.println("1=="+Integer.toBinaryString(ascInt));
            if( Integer.toHexString(ascInt).length() > 2 ) {
                result = result + "&#x" + Integer.toHexString(ascInt) + ";";
            }
            else
            {
                result = result + temChr;
            }

        }
        return result;
    }
    /**
     * This method will encode the String to unicode.
     *
     * @param gbString
     * @return
     */

    //代码:--------------------------------------------------------------------------------
    public static String gbEncoding(final String gbString) {
        char[] utfBytes = gbString.toCharArray();
        String unicodeBytes = "";
        for (int byteIndex = 0; byteIndex < utfBytes.length; byteIndex++) {
            String hexB = Integer.toHexString(utfBytes[byteIndex]);
            if (hexB.length() <= 2) {
                hexB = "00" + hexB;
            }
            unicodeBytes = unicodeBytes + "\\u" + hexB;
        }
        System.out.println("unicodeBytes is: " + unicodeBytes);
        return unicodeBytes;
    }

    /**
     * This method will decode the String to a recognized String
     * in ui.
     * @param dataStr
     * @return
     */
    public static StringBuffer decodeUnicode(final String dataStr) {
        int start = 0;
        int end = 0;
        final StringBuffer buffer = new StringBuffer();
        while (start > -1) {
            end = dataStr.indexOf("\\u", start + 2);
            String charStr = "";
            if (end == -1) {
                charStr = dataStr.substring(start + 2, dataStr.length());
            } else {
                charStr = dataStr.substring(start + 2, end);
            }
            char letter = (char) Integer.parseInt(charStr, 16); // 16进制parse整形字符串。
            buffer.append(new Character(letter).toString());
            start = end;
        }
        return buffer;
    }

}

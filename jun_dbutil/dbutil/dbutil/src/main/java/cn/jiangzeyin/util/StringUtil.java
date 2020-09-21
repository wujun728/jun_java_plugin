package cn.jiangzeyin.util;

import java.util.StringTokenizer;

/**
 * Created by jiangzeyin on 2017/8/14.
 */
public class StringUtil {
    private final static String CONFIG_LOCATION_DELIMITERS = ",; \t\n";

    public static String[] stringToArray(String str) {
        return stringToArray(str, CONFIG_LOCATION_DELIMITERS);
    }

    public static String[] stringToArray(String str, String separator) {
        if ((str == null) || (separator == null)) {
            return null;
        }

        int i = 0;
        StringTokenizer st = new StringTokenizer(str, separator);
        String[] array = new String[st.countTokens()];
        while (st.hasMoreTokens()) {
            array[(i++)] = st.nextToken();
        }
        return array;
    }
    public static String convertNULL(String input) {
        if (input == null)
            return "";
        return input.trim().intern();
    }

    public static String convertNULL(Object input) {
        if (input == null)
            return "";
        return convertNULL(input.toString());
    }
}

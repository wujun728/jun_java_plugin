package io.github.wujun728.db.utils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FieldUtils {


    public static final char UNDERLINE = '_';

    public static String toUnderlineCase(String param) {
        return getUnderlineName(param);
    }
    public static String getUnderlineName(String param) {
        return toSymbolCase(param, UNDERLINE);
        //return StrUtil.toUnderlineCase(param);
    }
    public static String toSymbolCase(CharSequence str, char symbol) {
        if (str == null) {
            return null;
        } else {
            int length = str.length();
            StringBuilder sb = new StringBuilder();
            for(int i = 0; i < length; ++i) {
                char c = str.charAt(i);
                if (Character.isUpperCase(c)) {
                    Character preChar = i > 0 ? str.charAt(i - 1) : null;
                    Character nextChar = i < str.length() - 1 ? str.charAt(i + 1) : null;
                    if (null != preChar) {
                        if (symbol == preChar) {
                            if (null == nextChar || Character.isLowerCase(nextChar)) {
                                c = Character.toLowerCase(c);
                            }
                        } else if (Character.isLowerCase(preChar)) {
                            sb.append(symbol);
                            if (null == nextChar || Character.isLowerCase(nextChar) || isNumber(nextChar)) {
                                c = Character.toLowerCase(c);
                            }
                        } else if (null != nextChar && Character.isLowerCase(nextChar)) {
                            sb.append(symbol);
                            c = Character.toLowerCase(c);
                        }
                    } else if (null == nextChar || Character.isLowerCase(nextChar)) {
                        c = Character.toLowerCase(c);
                    }
                }

                sb.append(c);
            }
            return sb.toString();
        }
    }

    public static boolean isNumber(char ch) {
        return ch >= '0' && ch <= '9';
    }

    public static String getCamelName(String param) {
        return toCamelCase(param, UNDERLINE);
        //return StrUtil.toCamelCase(param);
    }

    public static String toCamelCase(CharSequence name) {
        return toCamelCase(name, UNDERLINE);
    }
    public static String toCamelCase(CharSequence name, char symbol) {
        if (null == name) {
            return null;
        } else {
            String name2 = name.toString();
            if (/*StrUtil.contains(name2, symbol)*/name2.indexOf(symbol)>-1) {
                int length = name2.length();
                StringBuilder sb = new StringBuilder(length);
                boolean upperCase = false;
                for(int i = 0; i < length; ++i) {
                    char c = name2.charAt(i);
                    if (c == symbol) {
                        upperCase = true;
                    } else if (upperCase) {
                        sb.append(Character.toUpperCase(c));
                        upperCase = false;
                    } else {
                        sb.append(Character.toLowerCase(c));
                    }
                }
                return sb.toString();
            } else {
                return name2;
            }
        }
    }




    public static void main(String[] args) {
//        System.out.println(getUnderlineName("AbcDeft"));
//        System.out.println(getCamelName("abc_deft"));
//        System.out.println(fieldNameToColumnName("AbcDeft"));
//        System.out.println(columnNameToFieldName("abc_deft"));
        System.out.println(FieldUtils.toCamelCase("abc_deft"));
        System.out.println(FieldUtils.toUnderlineCase("AbcDeft"));
    }



    public static List<Field> allFields(Class clazz){
        ArrayList<Field> fields = new ArrayList<>();
        fields.addAll(Arrays.asList(clazz.getSuperclass().getDeclaredFields()));
        fields.addAll(Arrays.asList(clazz.getDeclaredFields()));
        return fields;
    }
    
    public static String columnNameToFieldName(String name){
    	return getCamelName(name);
    }
    public static String fieldNameToColumnName(String name){
    	return getUnderlineName(name);
    }

}

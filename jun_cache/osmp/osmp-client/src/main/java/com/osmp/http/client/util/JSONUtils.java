/*   
 * Project: OSMP
 * FileName: JSONUtils.java
 * version: V1.0
 */
package com.osmp.http.client.util;

import java.lang.reflect.Field;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.parser.deserializer.DateDeserializer;

/**
 * json处理
 * @author heyu
 *
 */
public class JSONUtils {
    
    private static Feature[] features = 
    {
            Feature.AllowSingleQuotes
    };
    
    
    public static Map<String , String> jsonString2Map(String jsonStr){
        return JSON.parseObject(jsonStr, Map.class, JSON.DEFAULT_PARSER_FEATURE, features);
    }
    
    /**
     * 
     * @param jsonStr 需转换的标准的json字符串
     * @param clz 转换对象
     * @param classMap 转换对象中包含复杂对象，key对应jsonStr中key,value对应需转换为的复杂对象
     * @return
     */
    public static <T> T jsonString2Bean(String jsonStr,Class<T> clz){
        if(jsonStr == null || "".equals(jsonStr.trim())) return null;
        if(isSimpleType(clz)){
            return toSimpleValue(clz,jsonStr);
        }
        List<Class<?>> classes = new ArrayList<Class<?>>();
        parseClasses(clz, classes);
        if(classes != null && !classes.isEmpty()){
            ParserConfig config = new ParserConfig();
            config.putDeserializer(Date.class, new DateDeserializer());
            for(Class<?> clazz : classes){
                if(!isSimpleType(clazz) && clazz != Map.class)
                    config.putDeserializer(clazz, new CustomJsonDeserializer());
            }   
            T item = JSON.parseObject(jsonStr, clz,config, JSON.DEFAULT_PARSER_FEATURE, features);
            config = null;
            return item;
        }
        return JSON.parseObject(jsonStr, clz, JSON.DEFAULT_PARSER_FEATURE, features);
    }
    
    public static <T> List<T> jsonString2List(String jsonStr,Class<T> clz){
        if(jsonStr == null || "".equals(jsonStr.trim())) return null;
        if(!jsonStr.startsWith("[") && !jsonStr.endsWith("]"))
            jsonStr = "["+jsonStr+"]";
        if(isSimpleType(clz) || Map.class == clz){
            List<T> lst =  JSONArray.parseArray(jsonStr, clz);
            return lst == null ? new ArrayList<T>() : lst;
        }
        List<String> list = JSONArray.parseArray(jsonStr, String.class);
        if(list == null || list.isEmpty()) return new ArrayList<T>();
        List<T> ret = new ArrayList<T>();
        List<Class<?>> classes = new ArrayList<Class<?>>();
        parseClasses(clz, classes);
        ParserConfig config = null;
        if(classes != null && !classes.isEmpty()){
            config = new ParserConfig();
            config.putDeserializer(Date.class, new DateDeserializer());
            for(Class<?> clazz : classes){
                if(!isSimpleType(clazz) && clazz != Map.class)
                    config.putDeserializer(clazz, new CustomJsonDeserializer());
            }
        }
        for(String item : list) {
            T itemObj = JSON.parseObject(item, clz, config, JSON.DEFAULT_PARSER_FEATURE, features);
            if(itemObj != null) ret.add(itemObj);
        }
        config = null;
        return ret;
    }
    
    public static String object2JsonString(Object obj){
        return JSON.toJSONStringWithDateFormat(obj, "yyyy-MM-dd HH:mm:ss");
    }
    
    
    private static <T> T toSimpleValue(Class<T> fieldType,String data){
        if(fieldType == null) return null;
        if(fieldType == String.class){
            return (T)data;
        }
        if(fieldType == int.class || fieldType == Integer.class){
            return (T)Integer.valueOf(data);
        }
        if(fieldType == float.class || fieldType == Float.class){
            return (T)Float.valueOf(data);
        }
        if(fieldType == double.class || fieldType == Double.class){
            return (T)Double.valueOf(data);
        }
        if(fieldType == long.class || fieldType == Long.class){
            return (T)Long.valueOf(data);
        }
        if(fieldType == byte.class || fieldType == Byte.class){
            return (T)Byte.valueOf(data);
        }
        if(fieldType == short.class || fieldType == Short.class){
            return (T)Short.valueOf(data);
        }
        if(fieldType == boolean.class || fieldType == Boolean.class){
            return (T)Boolean.valueOf(data);
        }
        return null;
    }
    
    private static boolean isSimpleType(Class fieldType){
        if(fieldType == String.class){
            return true;
        }
        if(fieldType == int.class || fieldType == Integer.class){
            return true;
        }
        if(fieldType == float.class || fieldType == Float.class){
            return true;
        }
        if(fieldType == double.class || fieldType == Double.class){
            return true;
        }
        if(fieldType == long.class || fieldType == Long.class){
            return true;
        }
        if(fieldType == byte.class || fieldType == Byte.class){
            return true;
        }
        if(fieldType == short.class || fieldType == Short.class){
            return true;
        }
        if(fieldType == boolean.class || fieldType == Boolean.class){
            return true;
        }
        return false;
    }
    
    private static Class getClass(Type type) {     
        if (type instanceof ParameterizedType) { // 处理泛型类型     
            return getGenericClass((ParameterizedType) type);     
        } else if (type instanceof TypeVariable) {     
            return (Class) getClass(((TypeVariable) type).getBounds()[0]); // 处理泛型擦拭对象     
        } else {// class本身也是type，强制转型     
            return (Class) type;     
        }     
    }     
    
    private static Class getGenericClass(ParameterizedType parameterizedType) {     
        Object genericClass = parameterizedType.getActualTypeArguments()[0];     
        if (genericClass instanceof ParameterizedType) { // 处理多级泛型     
            return (Class) ((ParameterizedType) genericClass).getRawType();     
        } else if (genericClass instanceof GenericArrayType) { // 处理数组泛型     
            return (Class) ((GenericArrayType) genericClass).getGenericComponentType();     
        } else if (genericClass instanceof TypeVariable) { // 处理泛型擦拭对象     
            return (Class) getClass(((TypeVariable) genericClass).getBounds()[0]);     
        } else {     
            return (Class) genericClass;     
        }     
    }    
    
    //还有缺陷，解析不了List中带List的泛型
    private static void parseClasses(Class<?> clz , List<Class<?>> lst) {
        if(lst == null){
            return;
        }
        if(clz == null || isSimpleType(clz) 
                || Date.class == clz
                || Map.class.isAssignableFrom(clz) 
                || Collection.class.isAssignableFrom(clz))
            return;
            
        if(lst.contains(clz)) return;
        lst.add(clz);
        
        Field[] fls = clz.getDeclaredFields();
        if(fls != null && fls.length > 0){
            for(Field f : fls){
                Class<?> clazz = f.getType();
                if(isSimpleType(clazz) || Map.class == clazz){
                    continue;
                }
                parseClasses(getClass(f.getGenericType()) , lst);
            }
        }
    }
}


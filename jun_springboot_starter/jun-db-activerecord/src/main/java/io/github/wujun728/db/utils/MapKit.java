// 功能已合并到RecordUtil.java
/*
package io.github.wujun728.db.utils;

import cn.hutool.core.annotation.AnnotationUtil;
import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import javax.persistence.Column;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: RecordUtils
 * @Description: Record相关工具类
 * /
public class MapKit {



    public static <T> List mapToBeans(List<Map<String, Object>> lists, Class<T> clazz) {
        List<T> datas = new ArrayList();
        if (lists != null && lists.size() > 0) {
            lists.forEach(map -> {
                datas.add(mapToBean(map, clazz));
            });
        }
        return datas;
    }

    public static <T> T mapToBean(Map<String, Object> map, Class<T> clazz) {
        return mapToBean(map, clazz, true);
    }

    public static <T> T mapToBean(Map<String, Object> map, Class<T> clazz, boolean isToCamelCase) {
        T obj = null;
        Map m = new HashMap<>();
        map.forEach((k, v) -> {
            if(isToCamelCase){
                m.put(FieldUtils.toCamelCase(String.valueOf(k)), v);
            }else{
                m.put(FieldUtils.toUnderlineCase(String.valueOf(k)), v);
            }
        });
        try {
            //obj = BeanUtil.mapToBean(map, clazz,true, CopyOptions.create());
            obj = JSONObject.parseObject(JSONObject.toJSONString(map), clazz);
            //Map demoMap = JSONObject.parseObject(JSONObject.toJSONString(obj), Map.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return obj;
    }

    public static <T> List<Map<String,Object>> beanToMaps(List<T> lists) {
        return beanToMaps(lists,true);
    }
    private static <T>  List<Map<String,Object>> beanToMaps(List<T> lists, boolean isToCamelCase) {
        List<Map<String,Object>> datas = new ArrayList();
        if (lists != null && lists.size() > 0) {
            lists.forEach(obj -> {
                datas.add(beanToMap(obj, isToCamelCase));
            });
        }
        return datas;
    }
    public static Map beanToMap(Object bean) {
        return beanToMap(bean, true);
    }

    private static Map beanToMap(Object bean, boolean isToCamelCase) {
        Map map = new HashMap<>();
        try {
            Class<?> cls = bean.getClass();
            for (Field field : cls.getDeclaredFields()) {
                field.setAccessible(true);
                Object val = field.get(bean);
                if (val != null) {
                    String columnName = getColumnName(field);
                    if (columnName == null) {
                        continue;
                    }
                    if (isToCamelCase) {
                        map.put(FieldUtils.toCamelCase(columnName), val);
                    } else {
                        map.put(columnName, val);
                    }
                }
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        return map;
    }



    public static void buildLabelNamesAndTypes(ResultSetMetaData rsmd, String[] labelNames, int[] types) throws SQLException {
        for (int i=1; i<labelNames.length; i++) {
            // 备忘：getColumnLabel 获取 sql as 子句指定的名称而非字段真实名称
            labelNames[i] = rsmd.getColumnLabel(i);
            types[i] = rsmd.getColumnType(i);
        }
    }

    public static byte[] handleBlob(Blob blob) throws SQLException {
        if (blob == null)
            return null;

        InputStream is = null;
        try {
            is = blob.getBinaryStream();
            if (is == null)
                return null;
            byte[] data = new byte[(int)blob.length()];		// byte[] data = new byte[is.available()];
            if (data.length == 0)
                return null;
            is.read(data);
            return data;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        finally {
            if (is != null)
                try {is.close();} catch (IOException e) {throw new RuntimeException(e);}
        }
    }

    public static String handleClob(Clob clob) throws SQLException {
        if (clob == null)
            return null;

        Reader reader = null;
        try {
            reader = clob.getCharacterStream();
            if (reader == null)
                return null;
            char[] buffer = new char[(int)clob.length()];
            if (buffer.length == 0)
                return null;
            reader.read(buffer);
            return new String(buffer);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        finally {
            if (reader != null)
                try {reader.close();} catch (IOException e) {throw new RuntimeException(e);}
        }
    }


    public static String getColumnName(Field field) {
        if(Modifier.isTransient(field.getModifiers())){
            return null;
        }
        String DYH = "";// ""`";  //'`';
        String columndName = FieldUtils.getUnderlineName(field.getName());
        String columndNameNew = DYH + columndName + DYH;
        if (AnnotationUtil.hasAnnotation(field, Transient.class)) {
            return null;
        }
        if (AnnotationUtil.hasAnnotation(field, TableField.class)) {
            columndName = AnnotationUtil.getAnnotationValue(field, TableField.class, "value");
            Boolean existFlag = AnnotationUtil.getAnnotationValue(field, TableField.class, "exist");
            if (existFlag == false) {
                return null;
            }
            columndNameNew = DYH + columndName + DYH;
        } else if (AnnotationUtil.hasAnnotation(field, Column.class)) {
            columndName = AnnotationUtil.getAnnotationValue(field, Column.class, "name");
            columndNameNew = DYH + columndName + DYH;
        }
        return columndNameNew;
    }

    public static String getTableName(Object bean) {
        String tableName = "";
        if (bean instanceof Class) {
            tableName = ((Class) bean).getSimpleName();
            tableName = FieldUtils.getUnderlineName(tableName);
            Class clazz = (Class) bean;
            if (AnnotationUtil.hasAnnotation(clazz, Table.class)) {
                tableName = AnnotationUtil.getAnnotationValue(clazz, Table.class, "name");
            } else if (AnnotationUtil.hasAnnotation(clazz, TableName.class)) {
                tableName = AnnotationUtil.getAnnotationValue(clazz, TableName.class, "value");
            }
        } else {
            tableName = FieldUtils.getUnderlineName(bean.getClass().getSimpleName());
            if (AnnotationUtil.hasAnnotation(bean.getClass(), Table.class)) {
                tableName = AnnotationUtil.getAnnotationValue(bean.getClass(), Table.class, "name");
            } else if (AnnotationUtil.hasAnnotation(bean.getClass(), TableName.class)) {
                tableName = AnnotationUtil.getAnnotationValue(bean.getClass(), TableName.class, "value");
            }
        }
        return tableName;
    }

}
*/

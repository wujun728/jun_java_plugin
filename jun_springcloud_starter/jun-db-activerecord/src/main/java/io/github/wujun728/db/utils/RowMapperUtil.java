package io.github.wujun728.db.utils;

import cn.hutool.core.util.ReflectUtil;
import org.springframework.jdbc.core.RowMapper;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class RowMapperUtil {
    public static <T> RowMapper<T> newRowMapper(Class<T> clz){
        Map<String, Field> fieldMap = ReflectUtil.getFieldMap(clz);
        return (rs, rowNum) -> {
            T obj = ReflectUtil.newInstance(clz);
            int columnCount = rs.getMetaData().getColumnCount();
            for(int i=1;i<=columnCount;i++){
                String label = rs.getMetaData().getColumnLabel(i);
                String fieldName = toFieldColumn(label);

                Field field = fieldMap.get(fieldName);
                if(field != null){
                    Class<?> type = field.getType();
                    Object value = rs.getObject(i, field.getType());
                    ReflectUtil.setFieldValue(obj,field,value);
                }
            }
            return obj;
        };
    }
    public static <T> RowMapper<T> newRowMapperObjs(){
        //Map<String, Field> fieldMap = ReflectUtil.getFieldMap(clz);
        return (rs, rowNum) -> {
            int colAmount = rs.getMetaData().getColumnCount();
            if (colAmount > 1) {
                Object[] temp = new Object[colAmount];
                for (int i=0; i<colAmount; i++) {
                    temp[i] = rs.getObject(i + 1);
                }
                return (T) temp;
            }
            return null;
        };
    }
    public static RowMapper<Map<String,Object>> newMapMapper(){
        return (rs, rowNum) -> {
            Map<String,Object> obj = new HashMap<>();
            int columnCount = rs.getMetaData().getColumnCount();
            for(int i=1;i<=columnCount;i++){
                String label = rs.getMetaData().getColumnLabel(i);
                String fieldName = toFieldColumn(label);
                obj.put(fieldName,rs.getObject(i));
            }
            return obj;
        };
    }

    public static String toFieldColumn(String columnName){
        columnName = columnName.toLowerCase();
        StringBuffer name = new StringBuffer();
        char[] array = columnName.toCharArray();
        for(int i=0;i<array.length;i++){
            char c = array[i];
            if(c == '_' && array.length>i+1){
                i++;
                name.append((array[i]+"").toUpperCase());
            }else{
                name.append(array[i]);
            }
        }
        return name.toString();
    }


}

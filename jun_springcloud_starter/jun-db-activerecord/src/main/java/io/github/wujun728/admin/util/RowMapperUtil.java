package io.github.wujun728.admin.util;

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
                String fieldName = StringUtil.toFieldColumn(label);

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
    public static RowMapper<Map<String,Object>> newMapMapper(){
        return (rs, rowNum) -> {
            Map<String,Object> obj = new HashMap<>();
            int columnCount = rs.getMetaData().getColumnCount();
            for(int i=1;i<=columnCount;i++){
                String label = rs.getMetaData().getColumnLabel(i);
                String fieldName = StringUtil.toFieldColumn(label);
                obj.put(fieldName,rs.getObject(i));
            }
            return obj;
        };
    }


}

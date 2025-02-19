package io.github.wujun728.db.orm.core;

import io.github.wujun728.db.orm.mapping.ColumnMap;
import io.github.wujun728.db.orm.mapping.ModelMap;
import io.github.wujun728.db.record.DbException;

import java.lang.reflect.Method;
import java.util.*;

/**
 * Model基类
 */
public class BaseModel {

    protected ModelMap modelMap;

    //实体与表映射
    protected Map<Class<?>, String> TABLE_MAP = new HashMap<>();

    //属性与字段映射
    protected Map<String, String> PROPERTY_MAP = new HashMap<>();

    //数据库字段与属性名映射
    protected Map<String, String> FIELD_MAP = new HashMap<>();

    //实体主键映射
    protected Map<Class<?>, String> PK_MAP = new HashMap<>();

    //数据库字段列表映射
    protected Map<Class<?>, List<String>> FIELDS_MAP = new HashMap<>();

    //getter映射
    protected Map<String, Method> GETTERS_MAP = new HashMap<>();

    //setter映射
    protected Map<String, Method> SETTERS_MAP = new HashMap<>();

    protected Map<Class<?>, Boolean> IS_CACHE_MAP = new HashMap<>();

    public BaseModel() {
        Class<?> modelClass = this.getClass();
        this.modelMap = Register.modelMapping.get(modelClass);
        if(modelMap==null){
            Register.registerClass(modelClass);
            this.modelMap = Register.modelMapping.get(modelClass);
        }
        if(modelMap==null){
            throw new DbException(modelClass.getName() + "未注册，无法使用，请使用Register注册Model");
        }
        List<String> fields = new ArrayList<>();

        TABLE_MAP.put(modelClass, modelMap.getTable());

        IS_CACHE_MAP.put(modelClass, modelMap.isCache());

        PK_MAP.put(modelClass, modelMap.getPrimaryKey());

        for (ColumnMap columnMap : modelMap.getColumnMaps()) {
            PROPERTY_MAP.put(columnMap.getProperty(), columnMap.getField());
            
            FIELD_MAP.put(columnMap.getField(), columnMap.getProperty());

            GETTERS_MAP.put(columnMap.getProperty(), columnMap.getGetter());

            SETTERS_MAP.put(columnMap.getProperty(), columnMap.getSetter());

            fields.add(columnMap.getField());
        }

        PK_MAP.put(modelClass, modelMap.getPrimaryKey());

        FIELDS_MAP.put(modelClass, fields);

    }
}

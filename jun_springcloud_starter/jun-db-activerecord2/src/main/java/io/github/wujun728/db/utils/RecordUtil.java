package io.github.wujun728.db.utils;

import cn.hutool.core.annotation.AnnotationUtil;
import cn.hutool.json.JSONConfig;
import cn.hutool.json.JSONUtil;
import cn.hutool.log.StaticLog;
import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.github.wujun728.db.record.Page;
import io.github.wujun728.db.record.Record;

import javax.persistence.Column;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;

/**
 * @ClassName: RecordUtils
 * @Description: Record相关工具类
 */
public class RecordUtil {


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


    public static List<Map<String, Object>> recordToMaps(List<Record> recordList) {
        return recordToMaps(recordList, false);
    }

    public static List<Map<String, Object>> recordToMaps(List<Record> recordList, Boolean isUnderLine) {
        if (recordList == null || recordList.size() == 0) {
            return null;
        }
        List<Map<String, Object>> list = new ArrayList<>();
        if (recordList.size() == 0) {
            return list;
        }
        for (Record record : recordList) {
            try {
                list.add(RecordUtil.recordToMap(record, isUnderLine));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return list;
    }

    public static Map<String, Object> recordToMap(Record record) {
        return recordToMap(record, false);
    }

    public static Map<String, Object> recordToMap(Record record, Boolean isUnderLine) {
        Map<String, Object> map = new HashMap<String, Object>();
        if (record != null) {
            String[] columns = record.getColumnNames();
            for (String col : columns) {
                String fieldName = FieldUtils.columnNameToFieldName(col);
                if (isUnderLine) {
                    fieldName = col;
                }
                map.put(fieldName, record.get(col));
            }
        }
        return map;
    }

    public static Page pageRecordToPageMap(Page<Record> pageList, Boolean isUnderLine) {
        if (pageList == null) {
            return null;
        }
        Page page = new Page();
        page.setList(recordToMaps(pageList.getList(), isUnderLine));
        page.setPageNumber(pageList.getPageNumber());
        page.setPageSize(pageList.getPageSize());
        page.setTotalPage(pageList.getTotalPage());
        page.setTotalRow(pageList.getTotalRow());
        return page;
    }


    public static <T> List<T> recordToBeans(List<Record> recordList, Class<T> clazz) {
        if (recordList == null || recordList.size() == 0) {
            return null;
        }
        List<T> list = new ArrayList<>();
        if (recordList.size() == 0) {
            return list;
        }
        for (Record record : recordList) {
            try {
                list.add((T) RecordUtil.recordToBean(record, clazz));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return list;
    }

    public static <T> T recordToBean(Record record, Class<T> clazz) {
        return recordToBean(record, clazz, false);
    }

    public static <T> T recordToBean(Record record, Class<T> clazz, boolean isToCamelCase) {
        if (record == null) {
            return null;
        }
        Map maps = recordToMap(record, isToCamelCase);
        return (T) mapToBean(maps, clazz, isToCamelCase);
    }

    public static Record mapping(Map<String, Object> map) {
        return mapToRecord(map);
    }

    public static Record mapToRecord(Map<String, Object> map) {
        return mapToRecord(map, false);
    }

    private static Record mapToRecord(Map<String, Object> map, boolean isToCamelCase) {
        Record record = new Record();
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            String keyOld = entry.getKey();
            if (isToCamelCase) {
                record.set(keyOld, entry.getValue());
            } else {
                String keyNew = FieldUtils.toUnderlineCase(keyOld);
                record.set(keyNew, entry.getValue());
            }
        }
        return record;
    }

    public static List<Record> mapToRecords(List<Map<String, Object>> maps) {
        return toRecords(maps);
    }
    public static List<Record> mappingList(List<Map<String, Object>> maps) {
        return toRecords(maps);
    }

    private static List<Record> toRecords(List<Map<String, Object>> maps) {
        List<Record> records = new ArrayList<>();
        if (null != maps && !maps.isEmpty()) {
            for (Map<String, Object> map : maps) {
                records.add(mapping(map));
            }
            return records;
        } else {
            return null;
        }
    }

    //****************************************************************************************************
    //****************************************************************************************************
    //****************************************************************************************************

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
            }/*else if(AnnotationUtil.hasAnnotation(clazz,Entity.class)){
				tableName = AnnotationUtil.getAnnotationValue(clazz, Entity.class,"table");
			}*/
        } else {
            tableName = FieldUtils.getUnderlineName(bean.getClass().getSimpleName());
            if (AnnotationUtil.hasAnnotation(bean.getClass(), Table.class)) {
                tableName = AnnotationUtil.getAnnotationValue(bean.getClass(), Table.class, "name");
            } else if (AnnotationUtil.hasAnnotation(bean.getClass(), TableName.class)) {
                tableName = AnnotationUtil.getAnnotationValue(bean.getClass(), TableName.class, "value");
            }/*else if(AnnotationUtil.hasAnnotation(bean.getClass(),Entity.class)){
				tableName = AnnotationUtil.getAnnotationValue(bean.getClass(), Entity.class,"table");
			}*/
        }
        return tableName;
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

    public static <T> List<Record> beanToRecords(List<T> lists) {
        return beanToRecords(lists,true);
    }
    private static <T>  List<Record> beanToRecords(List<T> lists, boolean isToCamelCase) {
        List<Record> datas = new ArrayList();
        if (lists != null && lists.size() > 0) {
            lists.forEach(obj -> {
                datas.add(beanToRecord(obj, isToCamelCase));
            });
        }
        return datas;
    }


    public static Record beanToRecord(Object bean) {
        return beanToRecord(bean, false);
    }

    private static Record beanToRecord(Object bean, boolean isToCamelCase) {
        Record record = new Record();
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
                        record.put(FieldUtils.toCamelCase(columnName), val);
                    } else {
                        record.put(columnName, val);
                    }
                }
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        return record;
    }

    private static String getColumnName(Field field) {
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
        }/*else if(AnnotationUtil.hasAnnotation(field, io.github.wujun728.db.orm.annotation.Column.class)){
            columndName = AnnotationUtil.getAnnotationValue(field, io.github.wujun728.db.orm.annotation.Column.class,"name");
            columndNameNew = DYH+columndName+DYH;
        }*/
        return columndNameNew;
    }


}

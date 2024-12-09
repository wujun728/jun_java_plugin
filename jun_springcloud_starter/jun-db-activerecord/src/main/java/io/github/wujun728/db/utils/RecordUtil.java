package io.github.wujun728.db.utils;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import io.github.wujun728.db.Page;
import io.github.wujun728.db.Record;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * @ClassName: RecordUtils
 * @Description: Record相关工具类
 *
 */
public class RecordUtil {


//
//
//
//
//    public static <T> List<Record> beanToRecords(List<T> objs) throws IllegalArgumentException, IllegalAccessException{
//        List<Record> datas = new ArrayList();
//        for(T item :objs){
//            datas.add(beanToRecord(item));
//        }
//        return datas;
//    }
//    public static <T> Record beanToRecord(T obj) throws IllegalArgumentException, IllegalAccessException{
//        if(obj != null){
//            Record record = new Record();
//            Class clazz = obj.getClass();
//            Field[] fields = clazz.getDeclaredFields();
//            for(int i=0; i<fields.length; i++){
//                Field field = fields[i];
//                if(!field.isAccessible())  {
//                    field.setAccessible(true);
//                }
//                String columnName = FieldUtils.fieldNameToColumnName(field.getName());
//                record.set(columnName, field.get(obj));
//            }
//            return record;
//
//        }
//        return null;
//    }
//
//
//
//
    public static <T> List mapToBeans(List<Map<String, Object>> lists, Class<T> clazz){
        List<T> datas = new ArrayList();
        if(lists!=null && lists.size()>0) {
            lists.forEach(item->{
                datas.add(RecordUtil.mapToBean(item,clazz));
            });
        }
        return datas;
    }
    public static <T> T mapToBean(Map<String, Object> item, Class<T> clazz){
        T obj = null;
        Map m = new HashMap<>();
        item.forEach((k,v)->{
            m.put(FieldUtils.columnNameToFieldName(String.valueOf(k)), v);
        });
        try {
            obj = BeanUtil.mapToBean(item, clazz,true, CopyOptions.create());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return obj;
    }

    public static List<Map> recordListToMapList(List<Record> recordList){
        return recordToMaps(recordList,false);
    }
    public static List<Map> recordToMaps(List<Record> recordList,Boolean isUnderLine){
        if (recordList == null || recordList.size() == 0){
            return null;
        }
        List<Map> list = new ArrayList<>();
        for (Record record : recordList){
            try {
                list.add(RecordUtil.recordToMap(record,isUnderLine));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return list;
    }
    public static Map<String, Object> recordToMap(Record record,Boolean isUnderLine) {
        Map<String, Object> map = new HashMap<String, Object>();
        if (record != null) {
            String[] columns = record.getColumnNames();
            for (String col : columns) {
                String fieldName = FieldUtils.columnNameToFieldName(col);
                if(isUnderLine){
                    fieldName = col;
                }
                map.put(fieldName, record.get(col));
            }
        }
        return map;
    }

    public static Page pageRecordToPageMap(Page<Record> pageList, Boolean isUnderLine){
        if (pageList == null || pageList.getList().size() == 0){
            return null;
        }
        List<Map<String, Object>> list = new ArrayList<>();
        List<Record> recordList = pageList.getList();
        for (Record record : recordList){
            try {
                list.add(RecordUtil.recordToMap(record,isUnderLine));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        Page page = new Page();
        page.setList(list);
        page.setPageNumber(pageList.getPageNumber());
        page.setPageSize(pageList.getPageSize());
        page.setTotalPage(pageList.getTotalPage());
        page.setTotalRow(pageList.getTotalRow());
        return page;
    }


    public static <T> List<T> recordToBeanList(List<Record> recordList, Class<T> clazz){
        if (recordList == null || recordList.size() == 0){
            return null;
        }
        List<T> list = new ArrayList<>();
        for (Record record : recordList){
            try {
                list.add((T) RecordUtil.recordToBean(record, clazz));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return list;
    }

    public static <T> T recordToBean(Record record, Class<T> clazz){
        if (record == null){
            return null;
        }
        Map maps = recordToMap(record,false);
        return (T) mapToBean(maps,clazz);
        /*try {
            T vo = clazz.newInstance();
            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                String fieldName = field.getName();
                field.setAccessible(true);
                //根据类型赋值
                String fieldClassName = field.getType().getSimpleName();
                String columnName = FieldUtils.fieldNameToColumnName(fieldName);
                Object obj = record.get(fieldName);
                if (obj == null) {
                    obj = record.get(columnName);
                }
                if (obj != null) {
                    if ("String".equalsIgnoreCase(fieldClassName)) {
                        field.set(vo, obj.toString());
                    }else if ("int".equals(fieldClassName) || "Integer".equals(fieldClassName)) {
                        field.set(vo,Integer.parseInt(obj.toString()));
                    }else if ("long".equals(fieldClassName) || "Long".equals(fieldClassName)) {
                        field.set(vo,Long.parseLong(obj.toString()));
                    }else if ("double".equals(fieldClassName) || "Double".equals(fieldClassName)) {
                        field.set(vo,Double.parseDouble(obj.toString()));
                    }else if ("BigDecimal".equalsIgnoreCase(fieldClassName)){
                        field.set(vo, new BigDecimal(obj.toString()));
                    }else if ("boolean".equals(fieldClassName)){
                        field.set(vo,obj);
                    }else if ("LocalDateTime".equals(fieldClassName) ) {
                        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S");
                        LocalDateTime ldt = LocalDateTime.from(df.parse(obj.toString()));
                        field.set(obj, ldt);
                    }else if("Date".equals(fieldClassName) ){
                        if(obj instanceof Timestamp){
                            Timestamp timestamp = (Timestamp) obj;
                            Date date = new Date(timestamp.getTime());
                            field.set(obj, date);
                        }else if(obj instanceof LocalDateTime){
                            DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S");
                            LocalDateTime ldt = LocalDateTime.from(df.parse(obj.toString()));
                            field.set(obj, ldt);
                        }else{
                            field.set(obj, obj);
                        }
                    } else {
                        field.set(obj, obj);
                    }

                }
            }
            return vo;
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }*/
    }



//    public static <T> T recordToBean(Record record, Class<T> clazz){
//        return RecordUtil.recordToBean(record,clazz);
//    }
//    public static Map<String, Object> recordToMap(Record record) {
//        return RecordUtil.recordToMap(record);
//    }
//    public static <T> List<T> recordToListBean(List<Record> recordList, Class<T> clazz){
//        return RecordUtil.recordToListBean(recordList,clazz);
//    }
//    public static Page pageRecordToPage(Page<Record> pageList){
//        return RecordUtil.pageRecordToPage(pageList);
//    }
//    public static List<Map> recordToMaps(List<Record> recordList){
//        return RecordUtil.recordToMaps(recordList);
//    }

    public static Record mapping(Map<String, Object> map) {
        return toRecord(map);
    }

    private static Record toRecord(Map<String, Object> map) {
        Record record = new Record();
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            record.set(entry.getKey(), entry.getValue());
        }
        return record;
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

}

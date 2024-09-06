package io.github.wujun728.db;

//import com.alibaba.fastjson2.JSONObject;
//import com.google.common.collect.Lists;
//import org.springframework.util.CollectionUtils;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: RecordUtils
 * @Description: Record相关工具类
 *
 */
public class RecordUtil {

    public static <T> List<Record> beanToRecords(List<T> objs) throws IllegalArgumentException, IllegalAccessException{
        List<Record> datas = new ArrayList();
        for(T item :objs){
            datas.add(beanToRecord(item));
        }
        return datas;
    }
    public static <T> Record beanToRecord(T obj) throws IllegalArgumentException, IllegalAccessException{
        if(obj != null){
            Record record = new Record();
            Class clazz = obj.getClass();
            Field[] fields = clazz.getDeclaredFields();
            for(int i=0; i<fields.length; i++){
                Field field = fields[i];
                if(!field.isAccessible())  {
                    field.setAccessible(true);
                }
                String columnName = FieldUtils.fieldNameToColumnName(field.getName());
                record.set(columnName, field.get(obj));
            }
            return record;

        }
        return null;
    }

    public static <T> List<Map> beanToMaps(List<T> objs) throws IllegalArgumentException, IllegalAccessException{
        List<Map> datas = new ArrayList();
        for(T item :objs){
            datas.add(beanToMap(item));
        }
        return datas;
    }
    public static <T> Map beanToMap(T obj) throws IllegalArgumentException, IllegalAccessException{
        if(obj != null){
            Map record = new HashMap();
            Class clazz = obj.getClass();
            Field[] fields = clazz.getDeclaredFields();
            for(int i=0; i<fields.length; i++){
                Field field = fields[i];
                if(!field.isAccessible())  {
                    field.setAccessible(true);
                }
                String columnName = FieldUtils.fieldNameToColumnName(field.getName());
                record.put(columnName, field.get(obj));
            }
            return record;

        }
        return null;
    }

    @Deprecated
	public List convertRecord(List<Record> lists,Class clazz){
		List datas = new ArrayList();
        if(lists!=null && lists.size()>0) {
			lists.forEach(item->{
				Object info = null;
				try {
					//info = BeanMapUtil.columnsMapToBean(item.getColumns(), clazz);
					info = BeanUtil.mapToBean(item.getColumns(),clazz,true,CopyOptions.create());
				} catch (Exception e) {
					e.printStackTrace();
				}
				datas.add(info);
			});

		}
		return datas;
	}
    @Deprecated
	public Object convertRecord(Record record,Class clazz){
		Object info = null;
		try {
            info = BeanUtil.mapToBean(record.getColumns(), clazz,true, CopyOptions.create());
			//info = BeanMapUtil.columnsMapToBean(record.getColumns(), clazz);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return info;
	}

    @SuppressWarnings("unchecked")
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
            //obj = BeanMapUtil.mapToBean(item,clazz);
            obj = BeanUtil.mapToBean(item, clazz,true, CopyOptions.create());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return obj;
    }

//    public static List<Map> recordToMaps(List<Record> recordList){
//        return recordToMaps(recordList,false);
//    }
    public static List<Map<String, Object>> recordToMaps(List<Record> recordList,Boolean isUnderLine){
        if (recordList == null || recordList.size() == 0){
            return null;
        }
        List<Map<String, Object>> list = new ArrayList<>();
        for (Record record : recordList){
            try {
                list.add(RecordUtil.recordToMap(record,isUnderLine));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return list;
    }

    public static Page pageRecordToPage(Page<Record> pageList,Boolean isUnderLine){
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

//    public static Map<String, Object> recordToMap(Record record) {
//        return recordToMap(record,false);
//    }
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

    public static <T> T recordToBean(Record record, Class<T> clazz){
        if (record == null){
            return null;
        }
        try {
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
                    }else if ("BigDecimal".equalsIgnoreCase(fieldClassName)){
                        field.set(vo, new BigDecimal(obj.toString()));
                    }else if ("boolean".equals(fieldClassName)){
                        field.set(vo,obj);
                    }
                }
            }
            return vo;
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
    /**
     * Record数据集合转为实体类List集合
     *
     * @param recordList        Record数据
     * @param clazz             实体类
     * @param <T>               泛型
     * @return                  实体类List集合
     * @throws Exception        抛出异常
     */
    public static <T> List<T> recordToListBean(List<Record> recordList, Class<T> clazz){
        if (recordList == null || recordList.size() == 0){
            return null;
        }
        List<T> list = new ArrayList<>();
        for (Record record : recordList){
            try {
                list.add(RecordUtil.recordToBean(record, clazz));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return list;
    }

}

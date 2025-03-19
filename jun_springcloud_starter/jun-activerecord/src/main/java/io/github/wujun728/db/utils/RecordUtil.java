package io.github.wujun728.db.utils;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.util.StrUtil;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.activerecord.DbKit;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.druid.DruidPlugin;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.sql.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

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


    //*****************************************************************************************************************
    //*****************************************************************************************************************
    //*****************************************************************************************************************

    public static final RecordUtil me = new RecordUtil();

    public List<Record> build(ResultSet rs) throws SQLException {
        return build(rs, null);
    }

    @SuppressWarnings("unchecked")
    public List<Record> build(ResultSet rs, Function<Record, Boolean> func) throws SQLException {
        List<Record> result = new ArrayList<Record>();
        ResultSetMetaData rsmd = rs.getMetaData();
        int columnCount = rsmd.getColumnCount();
        String[] labelNames = new String[columnCount + 1];
        int[] types = new int[columnCount + 1];
        buildLabelNamesAndTypes(rsmd, labelNames, types);
        while (rs.next()) {
            Record record = new Record();
            //record.setColumnsMap(config.containerFactory.getColumnsMap());
            Map<String, Object> columns = record.getColumns();
            for (int i=1; i<=columnCount; i++) {
                Object value;
                if (types[i] < Types.BLOB) {
                    value = rs.getObject(i);
                } else {
                    if (types[i] == Types.CLOB) {
                        value = RecordUtil.me.handleClob(rs.getClob(i));
                    } else if (types[i] == Types.NCLOB) {
                        value = RecordUtil.me.handleClob(rs.getNClob(i));
                    } else if (types[i] == Types.BLOB) {
                        value = RecordUtil.me.handleBlob(rs.getBlob(i));
                    } else {
                        value = rs.getObject(i);
                    }
                }
                columns.put(labelNames[i], value);
            }
            if (func == null) {
                result.add(record);
            } else {
                if ( ! func.apply(record) ) {
                    break ;
                }
            }
        }
        return result;
    }

    public void buildLabelNamesAndTypes(ResultSetMetaData rsmd, String[] labelNames, int[] types) throws SQLException {
        for (int i=1; i<labelNames.length; i++) {
            // 备忘：getColumnLabel 获取 sql as 子句指定的名称而非字段真实名称
            labelNames[i] = rsmd.getColumnLabel(i);
            types[i] = rsmd.getColumnType(i);
        }
    }

    public byte[] handleBlob(Blob blob) throws SQLException {
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

    public String handleClob(Clob clob) throws SQLException {
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

	/* backup before use columnType
	static final List<Record> build(ResultSet rs) throws SQLException {
		List<Record> result = new ArrayList<Record>();
		ResultSetMetaData rsmd = rs.getMetaData();
		int columnCount = rsmd.getColumnCount();
		String[] labelNames = getLabelNames(rsmd, columnCount);
		while (rs.next()) {
			Record record = new Record();
			Map<String, Object> columns = record.getColumns();
			for (int i=1; i<=columnCount; i++) {
				Object value = rs.getObject(i);
				columns.put(labelNames[i], value);
			}
			result.add(record);
		}
		return result;
	}

	private static final String[] getLabelNames(ResultSetMetaData rsmd, int columnCount) throws SQLException {
		String[] result = new String[columnCount + 1];
		for (int i=1; i<=columnCount; i++)
			result[i] = rsmd.getColumnLabel(i);
		return result;
	}
	*/

	/* backup
	static final List<Record> build(ResultSet rs) throws SQLException {
		List<Record> result = new ArrayList<Record>();
		ResultSetMetaData rsmd = rs.getMetaData();
		List<String> labelNames = getLabelNames(rsmd);
		while (rs.next()) {
			Record record = new Record();
			Map<String, Object> columns = record.getColumns();
			for (String lableName : labelNames) {
				Object value = rs.getObject(lableName);
				columns.put(lableName, value);
			}
			result.add(record);
		}
		return result;
	}

	private static final List<String> getLabelNames(ResultSetMetaData rsmd) throws SQLException {
		int columCount = rsmd.getColumnCount();
		List<String> result = new ArrayList<String>();
		for (int i=1; i<=columCount; i++) {
			result.add(rsmd.getColumnLabel(i));
		}
		return result;
	}
	*/

    //*****************************************************************************************************************
    //*****************************************************************************************************************
    //*****************************************************************************************************************

    static volatile Map<String, ActiveRecordPlugin> activeRecordPluginMap = new ConcurrentHashMap<>();

    public static ActiveRecordPlugin init(String configName,String jdbcUrl,String user,String password) {
        return initActiveRecordPlugin(configName, jdbcUrl, user, password);
    }
    public static ActiveRecordPlugin initActiveRecordPlugin(String configName,String jdbcUrl,String user,String password) {
        configName = StrUtil.isEmpty(configName)? DbKit.MAIN_CONFIG_NAME:configName;
        if(DbKit.getConfig(configName) == null){
            DruidPlugin druidPlugin = new DruidPlugin(jdbcUrl, user, password);
            ActiveRecordPlugin arp = new ActiveRecordPlugin(configName,druidPlugin);
            arp.setDevMode(true);
            arp.setShowSql(true);
            // 添加 sql 模板文件，实际开发时将 sql 文件放在 src/main/resources 下
            //arp.addSqlTemplate("com/jfinal/plugin/activerecord/test.sql");
            // 所有映射在生成的 _MappingKit.java 中自动化搞定
            //_MappingKit.mapping(arp);
            // 先启动 druidPlugin，后启动 arp
            druidPlugin.start();
            arp.start();
            activeRecordPluginMap.put(configName,arp);
            return arp;
        }else{
            return activeRecordPluginMap.get(configName);
        }
    }
    public static ActiveRecordPlugin init(DataSource ds) {
        return initActiveRecordPlugin("main", ds);
    }
    public static ActiveRecordPlugin init(String configName,DataSource ds) {
        return initActiveRecordPlugin(configName, ds);
    }
    public static ActiveRecordPlugin initActiveRecordPlugin(String configName,DataSource ds) {
        configName = StrUtil.isEmpty(configName)?DbKit.MAIN_CONFIG_NAME:configName;
        if(DbKit.getConfig(configName) == null){
            ActiveRecordPlugin arp = new ActiveRecordPlugin(configName, ds);
            arp.setDevMode(true);
            arp.setShowSql(true);
            // 添加 sql 模板文件，实际开发时将 sql 文件放在 src/main/resources 下
            //arp.addSqlTemplate("com/jfinal/plugin/activerecord/test.sql");
            // 所有映射在生成的 _MappingKit.java 中自动化搞定
            //_MappingKit.mapping(arp);
            // 先启动 druidPlugin，后启动 arp
            arp.start();
            activeRecordPluginMap.put(configName,arp);
            return arp;
        }else{
            return activeRecordPluginMap.get(configName);
        }
    }

}

package io.github.wujun728.rest.util;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.*;
import cn.hutool.db.meta.Column;
import cn.hutool.db.meta.MetaUtil;
import cn.hutool.db.meta.Table;
import cn.hutool.log.StaticLog;
import com.google.common.collect.Lists;
import io.github.wujun728.common.exception.BusinessException;
import io.github.wujun728.common.utils.IdGenerator15;
import io.github.wujun728.db.record.Record;
//import io.github.wujun728.db.record.interfaces.IRecordHandler;
import io.github.wujun728.db.record.kit.FieldUtils;
import org.springframework.util.CollectionUtils;

import javax.sql.DataSource;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class RestUtil {



    public static void buildRecord(Map<String, Object> parameters, Table table, Record record) {
        Collection<Column> columns = table.getColumns();
        for (Column column : columns) {
            String paramValue = RestUtil.getParamValue(parameters, column.getName());
            paramValue = RestUtil.getId(paramValue);
            checkDataFormat(column, paramValue);
            if (ObjectUtil.isNotEmpty(paramValue)) {//非空值，直接设置
                record.set(column.getName(), (paramValue));
            } else {
                String fieldName = FieldUtils.columnNameToFieldName(column.getName());
                if (ObjectUtil.isNotEmpty(RestUtil.getDefaultValue(fieldName))) {//设置默认值的字段
                    record.set(column.getName(), RestUtil.getDefaultValue(fieldName));
                } else {
                    if(column.isPk()){
                        if(column.isAutoIncrement()){
                            //自增的主键，不自动赋值
                        }else{
                            setPkValue(record, column);
                            StaticLog.warn("参数未传值： " + column.getName());
                        }
                    }else{
                        if (!column.isNullable()){ //非空字段，保存的时候，必填直接返回提示
                            throw new BusinessException("参数[" + column.getName() + "]不能为空！");
                        }
                    }
                }
            }
        }
    }

    public static void checkDataFormat(Column column, String val) {
        if ("DATE".equalsIgnoreCase(column.getTypeName()) ||
                "datetime".equalsIgnoreCase(column.getTypeName()) ||
                "DATE".equalsIgnoreCase(column.getTypeName())) {
            try {
                DateTime date = DateUtil.parse(val);
                //log.info("是日期类型字符串：[{}]", val);
            } catch (Exception e) {
                //log.info("不是日期类型字符串：[{}]", val);
                throw new BusinessException("参数["+ column.getName()+"]日期格式字符串不合法");
            }
        }
    }


    public static void setPkValue(Record record, Column column) {
        if ("VARCHAR".equalsIgnoreCase(column.getTypeName()) ||
                "TEXT".equalsIgnoreCase(column.getTypeName()) ||
                "LONGTEXT".equalsIgnoreCase(column.getTypeName()) ||
                "CLOB".equalsIgnoreCase(column.getTypeName())) {
            String idStr = IdGenerator15.generateIdStr();
            if (column.getSize() > idStr.length()) {
                record.set(column.getName(), idStr);
            } else {
                int start = idStr.length() - Integer.valueOf((int) column.getSize());
                record.set(column.getName(), idStr.substring(start, idStr.length()));
            }
        } else if ("DATE".equalsIgnoreCase(column.getTypeName()) ||
                "datetime".equalsIgnoreCase(column.getTypeName()) ||
                "DATE".equalsIgnoreCase(column.getTypeName())) {
            record.set(column.getName(), DateUtil.now());
        } else if ("bigint".equalsIgnoreCase(column.getTypeName()) ||
                "int".equalsIgnoreCase(column.getTypeName()) ||
                "float".equalsIgnoreCase(column.getTypeName()) ||
                "decimal".equalsIgnoreCase(column.getTypeName()) ||
                "bit".equalsIgnoreCase(column.getTypeName()) ||
                "integer".equalsIgnoreCase(column.getTypeName()) ||
                "tinyint".equalsIgnoreCase(column.getTypeName())) {

            long idStr = IdGenerator15.generateId();
            long currentSeconds = DateUtil.currentSeconds();
            if (column.getSize() >= String.valueOf(idStr).length()) {//十六位，雪花ID，毫秒级别
                record.set(column.getName(), idStr);
            } else if (column.getSize() >= String.valueOf(currentSeconds).length()) { //十位，时间戳，秒级别
                record.set(column.getName(), currentSeconds);
            } else {
                int size = Integer.valueOf((int) column.getSize());
                String numberkey = RandomUtil.randomNumbers(size);  //随机数，字段长度的随机数字，字段不能设置太短
                record.set(column.getName(), numberkey);
            }
        }
    }


    public static String getTablePrimaryKes(Table table) {
        Set<String> pks = table.getPkNames();
        if(CollectionUtils.isEmpty(pks)){
            throw new BusinessException("表"+table.getTableName()+"必须含有主键，无主键无法使用通用删除接口，请使用自定义SQL删除数据；");
        }
        String primaryKey = StrUtil.join(",",pks);
        return primaryKey;
    }

    public static void fillRecord(Record record, String tableName, Boolean isSave) {
        /*List<Class> allSuperclasses = ClassUtil.getAllClassByInterface(IRecordHandler.class);
        for (Class clazz : allSuperclasses) {
            try {
                Object obj = clazz.newInstance();
                String tablename = ReflectUtil.invoke(obj,"tableName");
                if(tablename.equalsIgnoreCase(tablename) || tablename.equalsIgnoreCase("all")){
                    if(isSave){
                        ReflectUtil.invoke(obj,"insertFill",new Object[]{record});
                    }else{
                        ReflectUtil.invoke(obj,"updateFill",new Object[]{record});
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }*/
    }

    public static Record getTableRecord(DataSource ds, String tableName, Map params){
        Record record = new Record();
        Table table = MetaUtil.getTableMeta(ds,tableName);
        table.getColumns().forEach(c->{
            record.set(c.getName(), MapUtil.getStr(params,c.getName()));
        });
        return record;
    }

    public static String getPkNames(DataSource ds, String tableName){
        Table table = MetaUtil.getTableMeta(ds,tableName);
        return String.join(",",table.getPkNames());
    }


    public static Object getDefaultValue(String fieldname){
        if("createTime".equals(fieldname)){
            return DateUtil.now();
        }
        if("updateTime".equals(fieldname)){
            return DateUtil.now();
        }
        if("creator".equals(fieldname)){
            return "admin";
        }
        if("createUser".equals(fieldname)){
            return "admin";
        }
        if("updateUser".equals(fieldname)){
            return "admin";
        }
        return null;
    }

    public static String getId(String val) {
        //printMsg(val);
        if("{_objectId}".equalsIgnoreCase(val)){
            return IdUtil.objectId();
        }
        else if("{_simpleUUID}".equalsIgnoreCase(val)){
            return IdUtil.simpleUUID();
        }
        else if("{_snowflakeId}".equalsIgnoreCase(val)){
            return IdUtil.getSnowflakeNextIdStr();
        }
        else if("{_nanoId}".equalsIgnoreCase(val)){
            return IdUtil.nanoId();
        }else
        if("{_id2}".equalsIgnoreCase(val)){
            return IdUtil.objectId();
        }
        else if("{_id3}".equalsIgnoreCase(val)){
            return IdUtil.simpleUUID();
        }
        else if("{_id1}".equalsIgnoreCase(val)){
            return IdUtil.getSnowflakeNextIdStr();
        }
        else if("{_id4}".equalsIgnoreCase(val)){
            return IdUtil.nanoId();
        }else
        {
            return val;
        }
    }
    public static String getParamValue(Map<String, Object> parameters, String paramName) {
        String val = MapUtil.getStr(parameters,paramName);
        if(ObjectUtil.isEmpty(val)){
            String fieldName = RestUtil.columnNameToFieldName(paramName);
            val = MapUtil.getStr(parameters,fieldName);
        }
        return val;
    }

    public static List getPrimaryKeyArgs(Map<String, Object> parameters, Table table) {
        Set<String> pks = table.getPkNames();
        List args = Lists.newArrayList();
        for(int i = 0; i < pks.size(); i++ ){
            String key = CollectionUtil.get(pks,i);
            String val1 = MapUtil.getStr(parameters,key);
            args.add(val1);
            if(StrUtil.isEmpty(val1)){
                throw new BusinessException("主键"+key+"必须含有入参，非空；(有多列，均使用英文逗号分隔)");
            }
        }
        return args;
    }

    public static Boolean isExistsParamname(Map<String, Object> parameters, String paramName) {
        String fieldName = RestUtil.columnNameToFieldName(paramName);
        for(String key :  parameters.keySet()){
            if(key.contains(paramName) || key.contains(fieldName)){
                return true;
            }
        }
        return false;
    }

    public static String getQueryCondition(Map<String, Object> parameters, Table table) {
        StringBuffer sqlbuilder = new StringBuffer();
        Collection<Column> columns = table.getColumns();
        for(String key : parameters.keySet()){
            if(key.startsWith("header.") || key.startsWith("session.") || key.startsWith("cookies.")|| key.startsWith("root.")){
                continue;
            }
            for (Column column : columns) {
                String fieldName = RestUtil.columnNameToFieldName(column.getName());
                // fielc.like=abc    field=abc    content-type=abc
                if(key.startsWith(column.getName()) || key.startsWith(fieldName) || key.contains(fieldName) || key.contains(column.getName())){
                    if(key.contains(".")){
                        String keys[] = key.split("\\.");
                        String field = keys[0];
                        String ex = keys[1];
                        String val = RestUtil.getParamValue(parameters, key);
                        sqlbuilder.append(" AND" + join(ex,column.getName(),val));
                    }else{
                        if(key.equalsIgnoreCase(fieldName) || key.equalsIgnoreCase(column.getName())){
                            String val = RestUtil.getParamValue(parameters, key);
                            sqlbuilder.append(" AND" + join("eq",column.getName(),val));
                        }
                    }
                }
            }
        }
        return sqlbuilder.toString();
    }

    private static String join(String ex,String columnName,String value){
        StringBuffer sql = new StringBuffer();
        sql.append(" ");
        // 设置查询方式
        if ("eq".equalsIgnoreCase(ex)){
            sql.append(columnName+" = '"+value+"'");
        }else if ("like".equalsIgnoreCase(ex)){
            sql.append(columnName+" like '%"+value+"%'");
        }else if ("between".equalsIgnoreCase(ex)){
//            sql.append((fieldName, ((Object[]) value)[0], ((Object[]) value)[1]);
        }else if ("le".equalsIgnoreCase(ex)){
            sql.append(columnName+" <= "+ value);
        }else if ("lt".equalsIgnoreCase(ex)){
            sql.append(columnName+" < "+ value);
        }else if ("ge".equalsIgnoreCase(ex)){
            sql.append(columnName+" >= "+ value);
        }else if ("gt".equalsIgnoreCase(ex)){
            sql.append(columnName+" >= "+ value);
        }else if ("notEq".equalsIgnoreCase(ex)){
            sql.append(columnName+" not like '%"+ value+"%'");
        }else if ("leftLike".equalsIgnoreCase(ex)){
            sql.append(columnName+" like '%"+ value+"'");
        }else if ("rightLike".equalsIgnoreCase(ex)){
            sql.append(columnName+" like '"+ value+"%'");
        }else if ("allLike".equalsIgnoreCase(ex)){
            sql.append(columnName+" like '%"+ value+"%'");
        }else if ("notLeftLike".equalsIgnoreCase(ex)){
            sql.append(columnName+" not like '%"+ value+"%'");
        }else if ("notRightLike".equalsIgnoreCase(ex)){
            sql.append(columnName+" not like '"+ value+"%'");
        }else if ("notAllLike".equalsIgnoreCase(ex)){
            sql.append(columnName+" not like '%"+ value+"%'");
        }else if ("isNull".equalsIgnoreCase(ex)){
            sql.append(columnName+" is null ");
        }else if ("notNull".equalsIgnoreCase(ex)){
            sql.append(columnName+" is not null ");
        }else if ("in".equalsIgnoreCase(ex)){
            sql.append(columnName+" in ()"+ String.join(",",value.split(","))+" )");
        }else if ("notIn".equalsIgnoreCase(ex)) {
            sql.append(columnName+" not in ( "+ String.join(",",value.split(","))+" )");
        }else {
            sql.append(columnName + " = " + value);
        }
        return sql.toString();
    }

    public static String columnNameToFieldName(String name){
        return underlineToCamel(name);
    }

    public static String underlineToCamel(String name){
        boolean allUpper = true;
        boolean allLower = true;
        for (int i = 0; i < name.length(); i++) {
            char c = name.charAt(i);
            if(Character.isLowerCase(c)){
                allUpper = false;
            }else if (Character.isUpperCase(c)){
                allLower = false;
            }
        }

        if (allUpper){
            name = name.toLowerCase();
            allLower = true;
        }
        if (!allLower){
            return name;
        }

        StringBuilder sb = new StringBuilder(name.length());
        for (int i = 0; i < name.length(); i++) {
            char c = name.charAt(i);
            if ('_' == c) {
                if (++i < name.length()){
                    sb.append(Character.toUpperCase(name.charAt(i)));
                }
            }else {
                sb.append(c);
            }
        }
        return sb.toString();
    }


}

package io.github.wujun728.rest.util;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.db.meta.Column;
import cn.hutool.db.meta.Table;
import com.google.common.collect.Lists;
import io.github.wujun728.common.base.interfaces.IRecordHandler;
import io.github.wujun728.common.exception.BusinessException;
import io.github.wujun728.common.utils.ClassUtil;
import io.github.wujun728.db.record.FieldUtils;
import io.github.wujun728.db.record.Record;
import org.springframework.util.CollectionUtils;

import java.util.*;

public class RestUtil {

    public static String getTablePrimaryKes(Table table) {
        Set<String> pks = table.getPkNames();
        if(CollectionUtils.isEmpty(pks)){
            throw new BusinessException("表"+table.getTableName()+"必须含有主键，无主键无法使用通用删除接口，请使用自定义SQL删除数据；");
        }
        String primaryKey = StrUtil.join(",",pks);
        return primaryKey;
    }

    public static void fillRecord(Record record,String tableName,Boolean isSave) {
        List<Class> allSuperclasses = ClassUtil.getAllClassByInterface(IRecordHandler.class);
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
        }
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
            String fieldName = FieldUtils.columnNameToFieldName(paramName);
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
        String fieldName = FieldUtils.columnNameToFieldName(paramName);
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
                String fieldName = FieldUtils.columnNameToFieldName(column.getName());
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


}

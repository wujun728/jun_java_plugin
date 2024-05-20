package com.jun.plugin.rest.util;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.db.meta.Column;
import cn.hutool.db.meta.Table;
import cn.hutool.log.StaticLog;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.jun.plugin.common.base.interfaces.IRecordHandler;
import com.jun.plugin.common.exception.BusinessException;
import com.jun.plugin.common.util.ClassUtil;
import com.jun.plugin.common.util.StringUtil;
import com.jun.plugin.db.record.FieldUtils;
import com.jun.plugin.db.record.Record;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.stream.Collectors;

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


    @ApiOperation("将Map集合构建为树集合")
    public static void buildTree(@ApiParam("Map集合") Collection<Map> collection, @ApiParam("key属性名称") String key, @ApiParam("parent属性名称") String parent) {
        String childrenKey = "children";
        buildTree(collection, (item) -> {
            return item.get(key);
        }, (item) -> {
            return item.get(parent);
        }, (item) -> {
            return (ArrayList)getValue(item,childrenKey, new ArrayList());
        }, (BiConsumer)null);
    }

    @ApiOperation("获取对象值")
    public static List  getValue(Map map,String key, List defaultValue) {
        List value = (List) map.get(key);
        if (StringUtil.isEmpty(value)) {
            value = defaultValue;
            map.put(key, defaultValue);
        }
        return value;
    }

    @ApiOperation("将集合构建为树集合")
    public static <T, K> void buildTree(@ApiParam("集合") Collection<T> collection, @ApiParam("key值lambda表达式") Function<T, K> keyMapper, @ApiParam("parent值lambda表达式") Function<T, K> parentMapper, @ApiParam("获取子集合lambda表达式") Function<T, Collection<T>> getChildrenMapper, @ApiParam("设置子集合lambda表达式") BiConsumer<T, Collection<T>> setChildrenMapper) {
        Map<K, T> map = toMap(collection, (item) -> {
            Collection<T> children = (Collection)getChildrenMapper.apply(item);
            if (children == null) {
                Collection<T> childrenx = new ArrayList();
                setChildrenMapper.accept(item, childrenx);
            }

            return keyMapper.apply(item);
        });
        Iterator<T> iterator = collection.iterator();
        while(iterator.hasNext()) {
            T t = iterator.next();
            T parentT = map.get(parentMapper.apply(t));
            if (parentT != null) {
                Collection<T> children = (Collection)getChildrenMapper.apply(parentT);
                children.add(t);
                iterator.remove();
            }
        }

    }

    @ApiOperation("将对象集合转换为对象Map")
    public static <T, K> Map<K, T> toMap(@ApiParam("集合") Collection<T> collection, @ApiParam("Map键值lambda表达式") Function<T, K> keyMapper) {
        return toMap(collection, keyMapper, (item) -> {
            return item;
        });
    }

    @ApiOperation("将对象集合转换为对象Map")
    public static <T, K, V> Map<K, V> toMap(@ApiParam("集合") Collection<T> collection, @ApiParam("Map键值lambda表达式") Function<T, K> keyMapper, @ApiParam("新对象lambda表达式") Function<T, V> valueMapper) {
        return (Map)collection.stream().collect(Collectors.toMap(keyMapper, valueMapper));
    }

    @ApiOperation("将对象集合转换为新对象集合")
    public static <T, N> List<N> map(@ApiParam("集合") Collection<T> collection, @ApiParam("新对象lambda表达式") Function<T, N> mapper) {
        return (List)collection.stream().map(mapper).collect(Collectors.toList());
    }

}

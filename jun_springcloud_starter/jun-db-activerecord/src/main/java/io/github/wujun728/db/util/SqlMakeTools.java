package io.github.wujun728.db.util;


import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.sql.Types;
import java.util.*;
import java.util.stream.Collectors;

import static io.github.wujun728.db.dao.EntityDao.*;


/**
 */
public class SqlMakeTools {
    /**
     * 组装SQL
     *
     * @param clazz   类型
     * @param tbName  表名
     * @param sqlFlag sql标识
     * @param <E>     泛型
     * @return String 创建的sql
     */
    public static <E> String makeSql(Class clazz, String tbName, String sqlFlag) {
        StringBuffer sql = new StringBuffer();
        Field[] fields = clazz.getDeclaredFields();
        if (sqlFlag.equals(SQL_INSERT)) {
            sql.append(" INSERT INTO " + tbName);
            sql.append("(");
            for (int i = 0; fields != null && i < fields.length; i++) {
                fields[i].setAccessible(true); // 暴力反射
                String column = EntityTools.getColumnName(fields[i]);//获取属性对应字段名，没有注解默认按照属性名。有Column注解，获取Column的name作为字段名
                sql.append(column).append(",");
            }
            sql = sql.deleteCharAt(sql.length() - 1);
            sql.append(") VALUES (");
            for (int i = 0; fields != null && i < fields.length; i++) {
                sql.append("?,");
            }
            sql = sql.deleteCharAt(sql.length() - 1);
            sql.append(")");
        } else if (sqlFlag.equals(SQL_UPDATE)) {
            String primaryKey = "id";
            sql.append(" UPDATE " + tbName + " SET ");
            for (int i = 0; fields != null && i < fields.length; i++) {
                fields[i].setAccessible(true); // 暴力反射
                String column = EntityTools.getColumnName(fields[i]);//获取属性对应字段名，没有注解默认按照属性名。有Column注解，获取Column的name作为字段名
                if (EntityTools.isPk(clazz, fields[i])) { // id 代表主键
                    primaryKey = column;
                    continue;
                }
                sql.append(column).append("=").append("?,");
            }
            sql = sql.deleteCharAt(sql.length() - 1);
            sql.append(" WHERE " + primaryKey + " = ?");
        } else if (sqlFlag.equals(SQL_DELETE)) {
            String primaryKey = "id";
            for (int i = 0; fields != null && i < fields.length; i++) {
                fields[i].setAccessible(true); // 暴力反射
                String column = EntityTools.getColumnName(fields[i]);//获取属性对应字段名，没有注解默认按照属性名。有Column注解，获取Column的name作为字段名
                if (EntityTools.isPk(clazz, fields[i])) { // id 代表主键
                    primaryKey = column;
                    break;
                }
            }
            sql.append(" DELETE FROM " + tbName + " WHERE " + primaryKey + " = ?");
        }
        return sql.toString();
    }

    /**
     * 设置参数
     *
     * @param entity  实体
     * @param sqlFlag sql标识
     * @param <E>     泛型
     * @return Object[] 参数数组
     */
    public static <E> Object[] setArgs(E entity, String sqlFlag) {
        Class<?> clzz = entity.getClass();
        Field[] fields = clzz.getDeclaredFields();
        if (sqlFlag.equals(SQL_INSERT)) {
            Object[] args = new Object[fields.length];
            for (int i = 0; args != null && i < args.length; i++) {
                try {
                    fields[i].setAccessible(true); // 暴力反射
                    args[i] = fields[i].get(entity);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return args;
        } else if (sqlFlag.equals(SQL_UPDATE)) {
            Object[] args = new Object[fields.length];
            Object primaryValue = new Object();
            int j = 0;
            for (int i = 0; fields != null && i < fields.length; i++) {
                try {
                    fields[i].setAccessible(true); // 暴力反射
                    if (EntityTools.isPk(clzz, fields[i])) { // id 代表主键
                        primaryValue = fields[i].get(entity);
                        continue;
                    }
                    args[j] = fields[i].get(entity);
                    j++;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            args[args.length - 1] = primaryValue;
            return args;
        } else if (sqlFlag.equals(SQL_DELETE)) {
            Object primaryValue = new Object();
            for (int i = 0; fields != null && i < fields.length; i++) {
                try {
                    fields[i].setAccessible(true); // 暴力反射
                    if (EntityTools.isPk(clzz, fields[i])) { // id 代表主键
                        primaryValue = fields[i].get(entity);
                        break;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            Object[] args = new Object[1]; // 长度是1
            try {
                args[0] = primaryValue;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return args;
        }
        return null;

    }

    /**
     * 设置参数类型(缺少的用到了再添加)
     *
     * @param entity  实体
     * @param sqlFlag sql标识
     * @param <E>     泛型
     * @return int[] 参数类型数组
     */
    public static <E> int[] setArgTypes(E entity, String sqlFlag) {
        Field[] fields = entity.getClass().getDeclaredFields();
        if (sqlFlag.equals(SQL_INSERT)) {
            int[] argTypes = new int[fields.length];
            try {
                for (int i = 0; argTypes != null && i < argTypes.length; i++) {
                    argTypes[i] = getTypes(fields[i]);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return argTypes;
        } else if (sqlFlag.equals(SQL_UPDATE)) {
            int[] tempArgTypes = new int[fields.length];
            int[] argTypes = new int[fields.length];
            try {
                for (int i = 0; tempArgTypes != null && i < tempArgTypes.length; i++) {
                    tempArgTypes[i] = getTypes(fields[i]);
                }
                System.arraycopy(tempArgTypes, 1, argTypes, 0, tempArgTypes.length - 1); // 数组拷贝
                argTypes[argTypes.length - 1] = tempArgTypes[0];

            } catch (Exception e) {
                e.printStackTrace();
            }
            return argTypes;

        } else if (sqlFlag.equals(SQL_DELETE)) {
            int[] argTypes = new int[1]; // 长度是1
            try {
                argTypes[0] = getTypes(fields[0]);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return argTypes;
        }
        return null;
    }

    private static int getTypes(Field arg) {
        arg.setAccessible(true); // 暴力反射
        if (String.class.equals(arg.getType())) {
            return Types.VARCHAR;
        } else if (int.class.equals(arg.getType()) || Integer.class.equals(arg.getType())) {
            return Types.INTEGER;
        } else if (double.class.equals(arg.getType()) || Double.class.equals(arg.getType())) {
            return Types.DOUBLE;
        } else if (java.util.Date.class.isAssignableFrom(arg.getType())) {
            return Types.TIMESTAMP;
        } else if (long.class.equals(arg.getType()) || Long.class.equals(arg.getType())) {
            return Types.BIGINT;
        } else if (float.class.equals(arg.getType()) || Float.class.equals(arg.getType())) {
            return Types.FLOAT;
        } else if (boolean.class.equals(arg.getType()) || Boolean.class.equals(arg.getType())) {
            return Types.BOOLEAN;
        } else if (short.class.equals(arg.getType()) || Short.class.equals(arg.getType())) {
            return Types.INTEGER;
        } else if (byte.class.equals(arg.getType()) || Byte.class.equals(arg.getType())) {
            return Types.INTEGER;
        } else if (BigDecimal.class.equals(arg.getType())) {
            return Types.DECIMAL;
        } else {
            return Types.OTHER;
        }
    }



}

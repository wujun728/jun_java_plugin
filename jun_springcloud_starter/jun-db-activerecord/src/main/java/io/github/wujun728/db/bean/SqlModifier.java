//package io.github.wujun728.db.bean;
//
//import io.github.wujun728.db.dao.EntityDao;
//import org.apache.commons.collections.CollectionUtils;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//import java.util.stream.Collectors;
//
///**
// * sql对象编辑器，可用于给sql统一添加查询条件、添加更新字段、添加插入字段等
// *
// * @author zhouning
// */
//public class SqlModifier {
//    private final SQL sql;
//
//    public SqlModifier(SQL sql) {
//        this.sql = sql;
//    }
//
//    public SqlModifier addUpdate(String key, Object val) throws Exception {
//        if (EntityDao.SQL_UPDATE.equals(this.sql.getSqlType())) {
//            sql.set(key, val);
//        }
//        return this;
//    }
//
//    public SqlModifier addInsert(String key, Object val) throws Exception {
//        if (EntityDao.SQL_INSERT.equals(this.sql.getSqlType())
//                || EntityDao.SQL_REPLACE.equals(this.sql.getSqlType())
//                || EntityDao.SQL_INSERTIGNORE.equals(this.sql.getSqlType())) {
//            List<Object[]> insertValues = sql.getInsertValues();
//            if (CollectionUtils.isNotEmpty(insertKeys()) && CollectionUtils.isNotEmpty(insertValues)) {
//                sql.getInsert().getSecond().add(key);
//                List<Object[]> newValues = new ArrayList<>();
//                List<Object[]> values = sql.getInsertValues();
//                for (Object[] arr : values) {
//                    Object[] tmp = new Object[arr.length + 1];
//                    for (int i = 0; i < arr.length; i++) {
//                        tmp[i] = arr[i];
//                    }
//                    tmp[arr.length] = val;
//                    newValues.add(tmp);
//                }
//                sql.setInsertValues(newValues);
//            }
//        }
//        return this;
//    }
//
//    public SqlModifier addSelect(Object field) {
//        if (EntityDao.SQL_SELECT.equals(this.sql.getSqlType())) {
//            sql.getSelectFields().add(field);
//        }
//        return this;
//    }
//
//    public SqlModifier addAnd(Where where) {
//        if (EntityDao.SQL_SELECT.equals(this.sql.getSqlType())) {
//            sql.and(where);
//        }
//        return this;
//    }
//
//    public SqlModifier addOr(Where where) {
//        if (EntityDao.SQL_SELECT.equals(this.sql.getSqlType())) {
//            sql.or(where);
//        }
//        return this;
//    }
//
//    public SqlModifier addAndWhere(Where where) {
//        if (EntityDao.SQL_SELECT.equals(this.sql.getSqlType())) {
//            sql.andWhere(where);
//        }
//        return this;
//    }
//
//    public SqlModifier addOrWhere(Where where) {
//        if (EntityDao.SQL_SELECT.equals(this.sql.getSqlType())) {
//            sql.orWhere(where);
//        }
//        return this;
//    }
//
//    public void changeSqlType(String type) {
//        sql.setSqlType(type);
//    }
//
//    public void changeTableName(String tableName) {
//        if (sql.getSqlType().equals(EntityDao.SQL_CREATE)) {
//            sql.getTableMeta().setName(tableName);
//        } else {
//            sql.getSqlPiepline().getHead().setTbName(tableName);
//        }
//    }
//
//    public List<Object> selectKeys() {
//        List<Object> result = new ArrayList<>();
//        if (sql.getSqlType().equals(EntityDao.SQL_SELECT) && CollectionUtils.isNotEmpty(sql.getSelectFields())) {
//            return Optional.ofNullable(sql.getSqlPiepline().getHead().getSelectFields()).orElseGet(ArrayList::new);
//        }
//        return result;
//    }
//
//    public List<String> updateKeys() {
//        List<String> result = new ArrayList<>();
//        if (sql.getSqlType().equals(EntityDao.SQL_UPDATE) && CollectionUtils.isNotEmpty(sql.getKvs())) {
//            sql.getKvs().forEach(pair -> result.add(pair.getFirst().toString()));
//        }
//        return result;
//    }
//
//    public List<String> insertKeys() {
//        List<String> result = new ArrayList<>();
//        if (EntityDao.SQL_INSERT.equals(this.sql.getSqlType())
//                || EntityDao.SQL_REPLACE.equals(this.sql.getSqlType())
//                || EntityDao.SQL_INSERTIGNORE.equals(this.sql.getSqlType())) {
//            return Optional.ofNullable(sql.getInsert().getSecond()).orElseGet(ArrayList::new);
//        }
//        return result;
//    }
//
//    public String tableName() {
//        if (sql.getSqlType().equals(EntityDao.SQL_CREATE)) {
//            return sql.getTableMeta().getName();
//        } else if (sql.getSqlType().equals(EntityDao.SQL_DROP) || sql.getSqlType().equals(EntityDao.SQL_TRUNCATE)) {
//            return sql.getDrunk().getTables().stream().collect(Collectors.joining(","));
//        } else {
//            return sql.getSqlPiepline().getHead().getTbName();
//        }
//    }
//
//    public String sqlType() {
//        return sql.getSqlType();
//    }
//
//    public String sqlId() {
//        return sql.getId();
//    }
//}

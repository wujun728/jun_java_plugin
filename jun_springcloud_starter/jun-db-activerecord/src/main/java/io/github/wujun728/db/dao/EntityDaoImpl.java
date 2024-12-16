package io.github.wujun728.db.dao;


import io.github.wujun728.db.bean.PageResult;
import io.github.wujun728.db.record.Page;
import io.github.wujun728.db.util.CollectionUtil;
import io.github.wujun728.db.util.EntityTools;
import io.github.wujun728.db.util.SqlMakeTools;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.util.ReflectionUtils;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 支持注解，若实体没有注解，实体类名需要按照驼峰命名，属性与数据库字段一致不区分大小写
 *
 * @author 周宁
 */
public class EntityDaoImpl<T, Id extends Serializable> implements EntityDao<T, Id> {

    private static final int BATCH_PAGE_SIZE = 2000;

    @Autowired
    protected JdbcTemplate jdbcTemplate;


    /**
     * 泛型
     */
    private Class<T> entityClass;

    /**
     * 表名
     */
    private String tableName;
    /**
     * 主键
     */
    private String primaryKey;

    @SuppressWarnings("rawtypes")
    private RowMapper<T> rowMapper;

    @SuppressWarnings("unchecked")
    public EntityDaoImpl() {
        ParameterizedType type = (ParameterizedType) getClass().getGenericSuperclass();
        entityClass = (Class<T>) type.getActualTypeArguments()[0];
        tableName = EntityTools.getTableName(entityClass);
        primaryKey = EntityTools.getPk(entityClass);
        rowMapper = BeanPropertyRowMapper.newInstance(entityClass);

    }

    @Override
    public int save(T t) throws Exception {
        String sql = SqlMakeTools.makeSql(entityClass, tableName, SQL_INSERT);
        Object[] args = SqlMakeTools.setArgs(t, SQL_INSERT);
        int[] argTypes = SqlMakeTools.setArgTypes(t, SQL_INSERT);
        return jdbcTemplate.update(sql, args, argTypes);
    }

    @Override
    public int update(T t) throws Exception {
        String sql = SqlMakeTools.makeSql(entityClass, tableName, SQL_UPDATE);
        Object[] args = SqlMakeTools.setArgs(t, SQL_UPDATE);
        int[] argTypes = SqlMakeTools.setArgTypes(t, SQL_UPDATE);
        return jdbcTemplate.update(sql, args, argTypes);
    }

    @Override
    public void batchSave(List<T> list) throws Exception {
        if (CollectionUtils.isEmpty(list)) {
            return;
        }
        //分页操作
        String sql = SqlMakeTools.makeSql(entityClass, tableName, SQL_INSERT);
        int[] argTypes = SqlMakeTools.setArgTypes(list.get(0), SQL_INSERT);
        Integer j = 0;
        List<Object[]> batchArgs = new ArrayList<Object[]>();
        for (int i = 0; i < list.size(); i++) {
            batchArgs.add(SqlMakeTools.setArgs(list.get(i), SQL_INSERT));
            j++;
            if (j.intValue() == BATCH_PAGE_SIZE) {
                jdbcTemplate.batchUpdate(sql, batchArgs, argTypes);
                batchArgs = new ArrayList<>();
                j = 0;
            }
        }
        jdbcTemplate.batchUpdate(sql, batchArgs, argTypes);
    }

    @Override
    public void saveOrUpdate(T t) throws Exception {
        Field field = ReflectionUtils.findField(entityClass, primaryKey);
        field.setAccessible(true);
        Id id = (Id) ReflectionUtils.getField(field, t);
        if (id == null) {
            throw new InvalidParameterException("entity primary key must be not null");
        }
        if (this.queryOne(id) == null) {
            this.save(t);
        } else {
            this.update(t);
        }
    }

    @Override
    public int saveAll(List<T> list) throws Exception {
        if (CollectionUtils.isEmpty(list)) {
            return 0;
        }
        //分页操作
        String sql = SqlMakeTools.makeSql(entityClass, tableName, SQL_INSERT);
        int[] argTypes = SqlMakeTools.setArgTypes(list.get(0), SQL_INSERT);
        List<Object[]> batchArgs = new ArrayList<Object[]>();
        for (int i = 0; i < list.size(); i++) {
            batchArgs.add(SqlMakeTools.setArgs(list.get(i), SQL_INSERT));
        }
        //将sql分为左右两部分
        int index = sql.indexOf("VALUES");
        index = sql.indexOf("(", index);
        //sql的左侧insert into
        String sqlLeft = sql.substring(0, index);
        //sql的右侧values
        String sqlRight = sql.substring(index);
        //分批次插入
        List<Object[]>[] batchArgsArr = CollectionUtil.slice(batchArgs, BATCH_PAGE_SIZE);
        //影响记录数量
        int resultSize = 0;
        for (List<Object[]> args : batchArgsArr) {
            //本批次的大小
            int batchSize = args.size();
            //插入语句
            StringBuilder insSql = new StringBuilder(sqlLeft);
            //参数
            List<Object> params = new ArrayList<>();
            //字段类型数组
            int[] types = new int[batchSize * argTypes.length];
            for (int i = 0; i < batchSize; i++) {
                for (int j = 0; j < argTypes.length; j++) {
                    types[i * argTypes.length + j] = argTypes[j];
                }
                insSql.append(sqlRight).append(",");
            }
            insSql.setLength(insSql.length() - 1);
            for (Object[] objs : args) {
                for (Object arg : objs) {
                    params.add(arg);
                }
            }
            resultSize = resultSize + jdbcTemplate.update(insSql.toString(), params.toArray(), types);
        }
        return resultSize;
    }

    @Override
    public void batchUpdate(List<T> list) throws Exception {
        if (CollectionUtils.isEmpty(list)) {
            return;
        }
        //分页操作
        String sql = SqlMakeTools.makeSql(entityClass, tableName, SQL_UPDATE);
        int[] argTypes = SqlMakeTools.setArgTypes(list.get(0), SQL_UPDATE);
        Integer j = 0;
        List<Object[]> batchArgs = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            batchArgs.add(SqlMakeTools.setArgs(list.get(i), SQL_UPDATE));
            j++;
            if (j.intValue() == BATCH_PAGE_SIZE) {
                jdbcTemplate.batchUpdate(sql, batchArgs, argTypes);
                batchArgs = new ArrayList<>();
                j = 0;
            }
        }
        jdbcTemplate.batchUpdate(sql, batchArgs, argTypes);
    }


    @SuppressWarnings("unchecked")
    @Override
    public T queryOne(Id id) throws Exception {
        return this.queryOne(id, rowMapper);
    }

    @Override
    public T queryOne(Id id, RowMapper<T> tRowMapper) throws Exception {
        String sql = "SELECT * FROM " + tableName + " WHERE " + primaryKey + " = ?";
        List<T> result = jdbcTemplate.query(sql, tRowMapper, id);
        return DataAccessUtils.singleResult(result);
    }

    @Override
    public int delete(Id id) throws Exception {
        return this.batchDelete(Collections.singletonList(id));
    }

    @Override
    public int batchDelete(List<Id> ids) throws Exception {
        if (CollectionUtils.isNotEmpty(ids)) {
            StringBuilder sql = new StringBuilder();
            List<String> marks = ids.stream().map(s -> "?").collect(Collectors.toList());
            sql.append(" DELETE FROM " + tableName + " WHERE " + primaryKey + " in (");
            sql.append(String.join(",", marks));
            sql.append(")");
            return jdbcTemplate.update(sql.toString(), ids.toArray());
        }
        return 0;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<T> queryAll() throws Exception {
        return this.queryAll(rowMapper);
    }

    @Override
    public List<T> queryAll(RowMapper<T> tRowMapper) throws Exception {
        String sql = "SELECT * FROM " + tableName;
        return jdbcTemplate.query(sql, tRowMapper);
    }

    @Override
    public PageResult<T> pageQuery(Page page) throws Exception {
        return this.pageQueryWithCriteria(page, null,rowMapper);
    }

    @Override
    public PageResult<T> pageQuery(Page page, RowMapper<T> tRowMapper) throws Exception {
        return this.pageQueryWithCriteria(page, null, rowMapper);
    }
    //@TODO
    public PageResult<T> pageQueryWithCriteria(Page page, Page criteria, RowMapper<T> tRowMapper) throws Exception {
        String sql = "SELECT * FROM " + tableName;
        //Pair<String, Object[]> pair = SqlMakeTools.doCriteria(criteria, new StringBuilder(sql));
        sql = null;//pair.getFirst();
        Object[] params = null;//pair.getSecond();
        String pageSql = "SELECT SQL_CALC_FOUND_ROWS * FROM (" + sql + ") temp ";
        if (page != null) {
            pageSql = pageSql + " LIMIT ?,?";
            params = ArrayUtils.add(params, page.getPageNumber());
            params = ArrayUtils.add(params, page.getPageSize());
        }
        List<T> paged = jdbcTemplate.query(pageSql, params, tRowMapper);
        String countSql = "SELECT FOUND_ROWS() ";
        int count = jdbcTemplate.queryForObject(countSql, Integer.class);
        return new PageResult(paged, count);
    }


    @Override
    public void drop() throws Exception {
        String sql = "DROP TABLE IF EXISTS " + tableName;
        jdbcTemplate.execute(sql);
    }

    @Override
    public void truncate() throws Exception {
        String sql = "TRUNCATE TABLE " + tableName;
        jdbcTemplate.execute(sql);
    }


//    public void doBeforeBuild(SQLType sqlType, SQL sql) throws Exception {
//        if (CollectionUtils.isNotEmpty(sqlInterceptors)) {
//            for (SQLInterceptor sqlInterceptor : sqlInterceptors) {
//                sqlInterceptor.beforeBuild(sqlType, sql.getModifier());
//            }
//        }
//    }
//
//    public void doAfterBuild(String sql, Object[] args) throws Exception {
//        if (CollectionUtils.isNotEmpty(sqlInterceptors)) {
//            for (SQLInterceptor sqlInterceptor : sqlInterceptors) {
//                sqlInterceptor.afterBuild(sql, args);
//            }
//        }
//    }

}

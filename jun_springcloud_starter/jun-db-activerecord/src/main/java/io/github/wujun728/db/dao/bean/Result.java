package io.github.wujun728.db.dao.bean;

import io.github.wujun728.db.record.Page;
import org.apache.commons.lang.ArrayUtils;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.ColumnMapRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;

/**
 * @param <E> 结果类型
 * @author 周宁
 */
public class Result<E> {

    private final Class<E> type;
    private final String sql;
    private final Object[] params;
    private final JdbcTemplate jdbcTemplate;
    private BiConsumer<String,Object[]> consumer;

    public Result(Class<E> type, String sql, Object[] params, JdbcTemplate jdbcTemplate,BiConsumer<String,Object[]> consumer) {
        this.type = type;
        this.sql = sql;
        this.params = params;
        this.jdbcTemplate = jdbcTemplate;
        this.consumer = consumer;
    }

    public E queryOne() throws Exception {
        return DataAccessUtils.singleResult(queryList());
    }

    public E queryObject() throws Exception {
        consumer.accept(sql,params);
        return jdbcTemplate.queryForObject(sql, params, type);
    }

    public List<E> queryList() throws Exception {
        consumer.accept(sql,params);
        return jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(type), params);
    }

    public List<E> queryForList()throws Exception{
        consumer.accept(sql,params);
        return jdbcTemplate.queryForList(sql, type, params);
    }

    public List<Map<String, Object>> queryMaps() throws Exception {
        consumer.accept(sql,params);
        return jdbcTemplate.query(sql, params, new ColumnMapRowMapper());
    }

    public PageResult<E> pageQuery(Page page) throws Exception {
        Object pageParams[] = {};
        String pageSql = "SELECT SQL_CALC_FOUND_ROWS * FROM (" + sql + ") temp ";
        pageSql = pageSql + " LIMIT ?,?";
        pageParams = ArrayUtils.addAll(params, new Object[]{page.getPageNumber(), page.getPageSize()});
        consumer.accept(pageSql,pageParams);
        List<E> paged = jdbcTemplate.query(pageSql, pageParams, BeanPropertyRowMapper.newInstance(type));
        String countSql = "SELECT FOUND_ROWS() ";
        consumer.accept(countSql,new Object[]{});
        int count = jdbcTemplate.queryForObject(countSql, Integer.class);
        return new PageResult(paged, count);
    }
}

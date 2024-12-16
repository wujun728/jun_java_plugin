package io.github.wujun728.db.record;

import io.github.wujun728.db.record.dialect.Dialect;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;

public interface IDbPro {

    JdbcTemplate getJdbcTemplate();

    void setJdbcTemplate(JdbcTemplate jdbcTemplate);

    DataSource getDataSource();

    void setDataSource(DataSource dataSource);

    Dialect getDialect();

    void setDialect(Dialect dialect);


    //	************************************************************************************************************************************************
//	Class 111111111111111111  begin   **************************************************************************************************************
//	************************************************************************************************************************************************
    <T> List<T> findBeanList(Class<T> clazz, String sql);

    <T> List findBeanList(Class clazz, String sql, Object... params);

    <T> List findMapList(Class clazz, String sql, Object... params);

    <T> List findRecordList(Class clazz, String sql, Object... params);

    <T> List  findBeanList(Class beanClass, Map<String, Object> params);

    Integer saveBeanBackPrimaryKey(Object bean);

    Integer saveBean(Object bean);

    Integer updateBean(Object bean);

    Integer deleteBean(Object bean);

    /*public Object findById(Class beanClass, Object[] id) {
            SqlContext sqlContext = SqlUtil.getByKey(beanClass, id);
            return findObject(beanClass, sqlContext.getSql(), sqlContext.getParams());
        }*/
    <T> T findBeanById(Class<T> clazz, Object... idValue);

    <T> T findBeanByIds(Class beanClass, String primaryKeys, Object... idValue);

    Object  findBeanBySql(Class clazz, String sql, Object... params);

    <T> Page findBeanPages(Class beanClass, int page, int rows);

    <T> Page findBeanPages(Class beanClass, int page, int rows, Map<String, Object> params);

    /*public <T> List findEntityAll(Class beanClass, String sql, Object[] params) {
            List<Map<String, Object>> list = queryList(SqlUtil.getSelect(beanClass));
            return RecordUtil.mapToBeans(list, beanClass);
        }*/
    List<Map<String, Object>> queryList(Class beanClass, Map<String, Object> params);

    List<Map<String, Object>> queryBeanFieldList(Class beanClass, String field, Object parmas);

    List<Map<String, Object>> queryBeanFieldsList(Class beanClass, String[] fields, Object[] parmas);

    Page queryBeanPage(Class beanClass, int page, int rows);

    Page queryBeanPage(Class beanClass, int page, int rows, Map<String, Object> params);

    List<Map<String, Object>> queryList(String sql, Object[] params);

    List<Map<String, Object>> queryList(String sql);

    int updateSql(String sql);

    String queryForString(String sql, Object[] params);

    Date queryForDate(String sql, Object[] params);

    Page<Map> queryMapPages(String sql, int page, int rows, Object[] params);

    int count(String sql, Object... params);

    int queryForInt(String sql, Object[] params);

    Boolean deleteById(String tableName, Object... idValues);

    Boolean deleteByIds(String tableName, String primaryKey, Object... idValues);

    Boolean deleteBySql(String sql, Object... paras);

    Boolean deleteBySql(String sql);

    Record findById(String tableName, Object id);

    Record findRecordById(String tableName, Object id);

    Record findByIds(String tableName, String primaryKey, Object... idValues);

    List<Record> findByColumnValueRecords(String tableName, String columnNames, Object... columnValues);

    <T> List findByColumnValueBeans(Class clazz, String columnNames, Object... columnValues);

    <T> List findByWhereSqlForBean(Class clazz, String whereSql, Object... columnValues);

    List<Record> find(String sql);

    List<Record> find(String sql, Object... param);

    Page<Record> paginate(Integer pageNumber, Integer limit, String select, String from);

    Page<Record> paginate(Integer pageNumber, Integer limit, String select, String from, Map<String, Object> params);

    boolean save(String tableName, Record record);

    boolean update(String tableName, Record record);

    boolean delete(String tableName, Record record);

    Object executeSqlXml(String sqlXml, Map params) throws SQLException;

    int updateSqlXml(String sqlXml, Map params) throws SQLException;

    List<Map<String, Object>> querySqlXml(String sqlXml, Map params) throws SQLException;
}

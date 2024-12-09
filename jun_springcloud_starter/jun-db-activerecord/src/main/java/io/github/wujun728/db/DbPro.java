package io.github.wujun728.db;

import com.google.common.collect.Maps;
import io.github.wujun728.db.dialect.Dialect;
import io.github.wujun728.db.exception.SqlException;
import io.github.wujun728.db.utils.RecordUtil;
import io.github.wujun728.db.utils.SqlContext;
import io.github.wujun728.db.utils.SqlUtil;
import io.github.wujun728.sql.SqlXmlUtil;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import static io.github.wujun728.db.Db.getPkNames;
import static io.github.wujun728.db.Db.main;


/**
 * Db 操作类，支持SQL模式、Record模式，Bean模式（JPA Bean模式、Mybatis Entity模式）
 */
@SuppressWarnings({"rawtypes", "unchecked"})
public class DbPro {

	private DataSource dataSource = null;
	private JdbcTemplate jdbcTemplate = null;
	private Dialect dialect = null;
	private static final Map<String, DbPro> map = new HashMap<String, DbPro>();
	public static final Map<String, JdbcTemplate> jdbcTemplateMap = new ConcurrentHashMap<>(24);
	public static final Map<String, DataSource> dataSourceMap = new ConcurrentHashMap<>(24);

	public DbPro(String configName) {
		use(configName);
	}

	public static DbPro use(String configName) {
		DbPro result = map.get(configName);
		if (result == null) {
			result = new DbPro();
			result.setJdbcTemplate(jdbcTemplateMap.get(configName));
			result.setDataSource(dataSourceMap.get(configName));
			map.put(configName, result);
		}
		return result;
	}
	
	public static DbPro use() {
		return use(main);
	}

	public DbPro() {
	}


	public JdbcTemplate getJdbcTemplate() {
		return this.jdbcTemplate;
	}

	public  void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public DataSource getDataSource() {
		return this.dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	


	//************************************************************************************************************************************************
	//Record 111111111111111111  begin   **************************************************************************************************************************************
	//************************************************************************************************************************************************



	public List<Map<String, Object>> findMaps(String sql) {
		List<Map<String, Object>> results = jdbcTemplate.queryForList(sql);
		return results;
	}
	public <T> List<T> findEntityList(Class<T> clazz, String sql) {
		List<Record> lists =  find(sql );
		List<T> datas = RecordUtil.recordToListBean(lists,clazz);
		return datas;
	}
//
//	public <T> List<T> findEntityList(Class<T> clazz, String sql, Object... paras) {
//		List<Record> lists =  find(sql,paras);
//		List<T> datas = RecordUtil.recordToListBean(lists,clazz);
//		return datas;
//	}



	/********************************************************************************
	 * 业务层 禁止写sql语句，以下方法仅供子类调用  jdbcTemplate
	 ********************************************************************************/




	/********************************************************************************
	 * 业务层 禁止写sql语句，以下方法仅供子类调用  jdbcTemplate
	 ********************************************************************************/


	public <T> List findBySql(Class clazz, String sql) {
		List<Map<String, Object>> results = jdbcTemplate.queryForList(sql);
		return RecordUtil.mapToBeans(results,clazz);
	}
	public  List<Map<String, Object>> findMaps(String sql, Object... paras) {
		List results = jdbcTemplate.queryForList(sql, paras);
		return results;
	}

	public  List<Map<String, Object>> findMaps2(String sql) {
		List results = jdbcTemplate.queryForList(sql);
		return results;
	}

	public  int updateBySql(String sql) {
		return jdbcTemplate.update(sql);
	}


	public Boolean deleteByIds2(String tableName, String primaryKey, Object... idValues) {
		String[] pKeys = primaryKey.split(",");
		if (pKeys.length != idValues.length)
			throw new IllegalArgumentException("primary key number must equals id value number");
		String sql = dialect.forDbDeleteById(tableName, pKeys);
		int flag = jdbcTemplate.update(sql, idValues);
		return flag > 0;
	}


	public <T> List findObjectList(Class clazz, String sql, Object... params) {
		List datas = findList(sql, params);
		return RecordUtil.mapToBeans(datas, clazz);
	}

	public  Object findObject(Class beanClass, String sql, Object... params) {
		try {
			return jdbcTemplate.queryForObject(sql, BeanPropertyRowMapper.newInstance(beanClass), params);
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	public  List<Map<String, Object>> findList(String sql, Object... params) {
		return jdbcTemplate.queryForList(sql, params);
	}

	public List<Map<String, Object>> findList(String sql) {
		return jdbcTemplate.queryForList(sql);
	}


	public int findForInt(String sql, Object... params) {
		return count(sql,params);
	}
	public String findForString(String sql, Object... params) {
		try {
			return jdbcTemplate.queryForObject(sql, String.class, params);
		} catch (Exception e) {
			throw new SqlException(e, sql);
		}
	}
	public Date findForDate(String sql, Object... params) {
		try {
			return jdbcTemplate.queryForObject(sql, Date.class, params);
		} catch (Exception e) {
			throw new SqlException(e, sql);
		}
	}

	/********************************************************************************
	 * 业务层 禁止写sql语句，以下方法仅供子类调用  SqlUtil
	 ********************************************************************************/


	public Integer saveEntity(Object bean) {
		return updateSqlContext(SqlUtil.getInsert(bean));
	}

	public Integer updateEntity(Object bean) {
		return updateSqlContext(SqlUtil.getUpdate(bean));
	}


	public Integer deleteEntity(Object bean) {
		return updateSqlContext(SqlUtil.getDelete(bean));
	}


	public Object findObjectById(Class beanClass, Object... id) {
		SqlContext sqlContext = SqlUtil.getByKey(beanClass, id);
		return findObject(beanClass, sqlContext.getSql(), sqlContext.getParams());
	}

	public Object findObjectByParams(Class beanClass, String[] fields, Object... parmas) {
		SqlContext sqlContext = SqlUtil.getByParams(beanClass, fields, parmas);
		Object obj =  findObject(beanClass, sqlContext.getSql(), sqlContext.getParams());
		return obj;
	}

	public <T> T findById(Class<T> clazz, Object idValue) {
		String tableName = SqlUtil.getTableName(clazz);
		String primaryKeyStr = getPkNames(tableName);
		Record record =  findByIds(tableName,primaryKeyStr,idValue);
		if(record==null){
			return null;
		}
		T datas = RecordUtil.recordToBean(record,clazz);
		return datas;
	}


	@Deprecated
	public <T> List findListAll(Class beanClass) {
		List<Map<String, Object>>  list =  findList(SqlUtil.getSelect(beanClass));
		return RecordUtil.mapToBeans(list,beanClass);
	}


	public List<Map<String, Object>> findList(Class beanClass, Map<String, Object> params) {
		return findList(SqlUtil.getSelect(beanClass, params));
	}


	public List<Map<String, Object>> findList(Class beanClass, String field, Object parmas) {
		return findList(beanClass, new String[]{field}, parmas);
	}


	public List<Map<String, Object>> findList(Class beanClass, String[] fields, Object... parmas) {
		return findList(SqlUtil.getByParams(beanClass, fields, parmas));
	}


	public  <T> Page findPages(Class beanClass, int page, int rows) {
		return findPages(beanClass,page,rows, Maps.newHashMap());
	}
	public <T> Page findPages(Class beanClass, int page, int rows, Map<String, Object> params) {
		SqlContext sqlContext = SqlUtil.getSelect(beanClass, page, rows, params);
		Page pageVo = new Page();
		List<Map<String, Object>> listData = findList(sqlContext);
		pageVo.setList(RecordUtil.mapToBeans(listData,beanClass));
		int totalRow = findInt(SqlUtil.getCount(sqlContext));
		pageVo.setTotalRow(totalRow);
		pageVo.setPageSize(rows);
		pageVo.setPageNumber(page);
		if(totalRow%rows==0){
			pageVo.setTotalPage(totalRow/rows);
		}else{
			pageVo.setTotalPage(totalRow/rows+1);
		}
		return pageVo;
	}

	public Page findPages(String sql, int page, int rows, Object... params) {
		Page pageVo = new Page();
		pageVo.setList(findList(SqlUtil.getSelect(sql, page, rows), params));
		pageVo.setTotalRow(count(SqlUtil.getCount(sql), params));
		return pageVo;
	}

	/********************************************************************************
	 * 私有方法
	 ********************************************************************************/


	private int updateSqlContext(SqlContext sqlContext) {
		int result;
		String sql = null;
		try {
			sql = sqlContext.getSql();
			result = jdbcTemplate.update(sql, sqlContext.getParams());
		} catch (Exception e) {
			throw new SqlException(e, sql);
		}
		return result;
	}

	private List<Map<String, Object>> findList(SqlContext sqlContext) {
		String sql = null;
		try {
			sql = sqlContext.getSql();
			return jdbcTemplate.queryForList(sql, sqlContext.getParams());
		} catch (Exception e) {
			throw new SqlException(e, sql);
		}
	}

	private  <T> List findList(SqlContext sqlContext, Class clazz) {
		String sql = null;
		try {
			sql = sqlContext.getSql();
			return findList(sql, clazz, sqlContext.getParams());
		} catch (Exception e) {
			throw new SqlException(e, sql);
		}
	}

	/**
	 * 查询
	 *
	 * @param sqlContext sql上下文
	 */
	private int findInt(SqlContext sqlContext) {
		String sql = null;
		try {
			sql = sqlContext.getSql();
			return jdbcTemplate.queryForObject(sql, Integer.class, sqlContext.getParams());
		} catch (Exception e) {
			throw new SqlException(e, sql);
		}
	}


	//************************************************************************************************************************************************
	//Record 111111111111111111  end   SqlXmlUtil ***************************************************************************************************
	//************************************************************************************************************************************************

	public  Object executeSqlXml(String sqlXml, Map params) throws SQLException {
		return SqlXmlUtil.executeSql(dataSource.getConnection(),sqlXml,params,true);
	}




	//************************************************************************************************************************************************
	//Record begin  **************************************************************************************************************************************
	//************************************************************************************************************************************************

	/**
	 * 根据id查找
	 *
	 * @param tableName 表名
	 * @param id        主键
	 * @return Record
	 */
	public Record findById(String tableName, Object... id) {
		String primaryKeyStr = getPkNames(tableName);
		if (primaryKeyStr.contains(",")) {
			return findByIds(tableName, primaryKeyStr, id);
		}
		String sql = "SELECT * FROM " + tableName + " WHERE " + primaryKeyStr + " = ?";
		Map<String, Object> resultMap = null;
		try {
			resultMap = jdbcTemplate.queryForMap(sql, id);
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
		return mapping(resultMap);
	}


	public Record findByIds(String tableName, String primaryKey, Object... idValues) {
		Record record = new Record();
		String[] pKeys = primaryKey.split(",");
		if (pKeys.length != idValues.length)
			throw new IllegalArgumentException("primary key number must equals id value number");

		String sql = dialect.forDbFindById(tableName, pKeys);
		Map<String, Object> resultMap;
		try {
			resultMap = jdbcTemplate.queryForMap(sql, idValues);
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
		return mapping(resultMap);
	}


	public List<Record> findBySql(String sql) {
		return find(sql);

	}
	public List<Record> find(String sql) {
		List<Map<String, Object>> results = jdbcTemplate.queryForList(sql);
		return mappingList(results);
	}


	public List<Record> find(String sql, Object... param) {
		List<Map<String, Object>> results = jdbcTemplate.queryForList(sql, param);
		return mappingList(results);
	}

	public Page<Record> paginate(Integer pageNumber, Integer limit, String select, String from) {
		return paginate(pageNumber,limit,select,from,Maps.newHashMap());
	}
	public Page<Record> paginate(Integer pageNumber, Integer limit, String select, String from,Map<String, Object> params) {
		String sqlStr = select + " " + from;
		SqlContext sqlContext = SqlUtil.getSelect(new StringBuilder(sqlStr), params);
		Page pageVo = new Page();
		List<Map<String, Object>> results = queryList(sqlContext);
		List<Record> records = mappingList(results);
		int totalpage = queryInt(SqlUtil.getCount(sqlContext));
		pageVo.setList(records);
		pageVo.setTotalRow(totalpage);
		pageVo.setPageNumber(pageNumber);
		pageVo.setPageSize(limit);
		pageVo.setTotalPage(totalpage / limit);
		return pageVo;
	}

	public List<Record> findAll(String tableName) {
		String sql = "SELECT * FROM " + tableName + " ";
		List<Map<String, Object>> resultMap = jdbcTemplate.queryForList(sql);
		return mappingList(resultMap);
	}


	/**
	 * 保存record
	 *
	 * @param tableName 表名
	 * @param record    record对象
	 */
	public boolean save(String tableName, Record record) {
		String primaryKey = getPkNames(tableName);
		String[] pKeys = primaryKey.split(",");
		List<Object> paras = new ArrayList<Object>();
		StringBuilder sql = new StringBuilder();
		dialect.forDbSave(tableName, pKeys, record, sql, paras);
		int flag = 0;
		try {
			flag = jdbcTemplate.update(sql.toString(), paras.toArray());
		} catch (DuplicateKeyException e) {
			throw new RuntimeException("主键冲突，保存失败！");
		}catch (DataAccessException e){
			e.printStackTrace();
			throw new RuntimeException(e);
		}catch (Exception e){
			e.printStackTrace();
		}
		return flag > 0;
	}

	public boolean update(String tableName, Record record) {
		String primaryKey = getPkNames(tableName);
		String[] pKeys = primaryKey.split(",");
		Object[] ids = new Object[pKeys.length];
		for (int i=0; i<pKeys.length; i++) {
			ids[i] = record.get(pKeys[i].trim());	// .trim() is important!
			if (ids[i] == null)
				throw new RuntimeException("You can't update record without Primary Key, " + pKeys[i] + " can not be null.");
		}
		StringBuilder sql = new StringBuilder();
		List<Object> paras = new ArrayList<Object>();
		dialect.forDbUpdate(tableName, pKeys, ids, record, sql, paras);
		if (paras.size() <= 1) {	// 参数个数为 1 的情况表明只有主键，也无需更新
			return false;
		}
		int result = jdbcTemplate.update(sql.toString(), paras.toArray());
		if (result >= 1) {
			return true;
		}
		return false;
	}

	public Record mapping(Map<String, Object> map) {
		return toRecord(map);
	}
	private Record toRecord(Map<String, Object> map) {
		Record record = new Record();
		for (Map.Entry<String, Object> entry : map.entrySet()) {
			record.set(entry.getKey(), entry.getValue());
		}
		return record;
	}

	public List<Record> mappingList(List<Map<String, Object>> maps) {
		return toRecords(maps);
	}
	private List<Record> toRecords(List<Map<String, Object>> maps) {
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


	//************************************************************************************************************************************************
	//Record end  **************************************************************************************************************************************
	//************************************************************************************************************************************************


	public  List<Map<String, Object>> query(String sql, Object... paras) {
		List results = jdbcTemplate.queryForList(sql, paras);
		return results;
	}

	public  List<Map<String, Object>> query(String sql) {
		List results = jdbcTemplate.queryForList(sql);
		return results;
	}

	public int update(String sql) {
		return jdbcTemplate.update(sql);
	}


	public Boolean deleteById(String tableName, Object... idValues) {
		String primaryKeyStr = getPkNames(tableName);
		if (primaryKeyStr.contains(",")) {
			return deleteByIds(tableName, primaryKeyStr, idValues);
		}
		String sql = " DELETE FROM " + tableName + " WHERE " + primaryKeyStr + " = ?";
		int flag = jdbcTemplate.update(sql, idValues);
		return flag > 0;
	}

	public Boolean deleteByIds(String tableName, String primaryKey, Object... idValues) {
		String[] pKeys = primaryKey.split(",");
		if (pKeys.length != idValues.length)
			throw new IllegalArgumentException("primary key number must equals id value number");
		String sql = dialect.forDbDeleteById(tableName, pKeys);
		int flag = jdbcTemplate.update(sql, idValues);
		return flag > 0;
	}

	public Boolean deleteBySql(String sql, Object... paras) {
		int flag = jdbcTemplate.update(sql, paras);
		return flag>0;
	}

	public Boolean deleteBySql(String sql) {
		return deleteBySql(sql,null);
	}

	public <T> List queryForBeanList(String sql, Class clazz, Object... params) {
		List datas = queryForList(sql, params);
		return RecordUtil.mapToBeans(datas, clazz);
	}


	public Integer saveBackPrimaryKey(Object bean) {
		save(bean);
		return jdbcTemplate.queryForObject("SELECT last_insert_id() as id", Integer.class);
	}


	public Integer save(Object bean) {
		return update(SqlUtil.getInsert(bean));
	}


	public Integer update(Object bean) {
		return update(SqlUtil.getUpdate(bean));
	}


	public Integer delete(Object bean) {
		return update(SqlUtil.getDelete(bean));
	}


	public Object getById(Class beanClass, Object... id) {
		SqlContext sqlContext = SqlUtil.getByKey(beanClass, id);
		return queryForObject(beanClass, sqlContext.getSql(), sqlContext.getParams());
	}


	public Object getByParams(Class beanClass, String[] fields, Object... parmas) {
		SqlContext sqlContext = SqlUtil.getByParams(beanClass, fields, parmas);
		Object obj =  queryForObject(beanClass, sqlContext.getSql(), sqlContext.getParams());
		return obj;
	}

	public <T> List queryAll(Class beanClass) {
		List<Map<String, Object>>  list =  queryList(SqlUtil.getSelect(beanClass));
		return RecordUtil.mapToBeans(list,beanClass);
	}


	public List<Map<String, Object>> queryList(Class beanClass, Map<String, Object> params) {
		return queryList(SqlUtil.getSelect(beanClass, params));
	}


	public List<Map<String, Object>> queryList(Class beanClass, String field, Object parmas) {
		return queryList(beanClass, new String[]{field}, parmas);
	}


	public List<Map<String, Object>> queryList(Class beanClass, String[] fields, Object... parmas) {
		return queryList(SqlUtil.getByParams(beanClass, fields, parmas));
	}


	public Page queryPage(Class beanClass, int page, int rows) {
		return queryPage(beanClass,page,rows,Maps.newHashMap());
	}
	public Page queryPage(Class beanClass, int page, int rows, Map<String, Object> params) {
		SqlContext sqlContext = SqlUtil.getSelect(beanClass, page, rows, params);
		Page pageVo = new Page();
		List<Map<String, Object>> listData = queryList(sqlContext);
		pageVo.setList(RecordUtil.mapToBeans(listData,beanClass));
		pageVo.setTotalRow(queryInt(SqlUtil.getCount(sqlContext)));
		return pageVo;
	}


	/********************************************************************************
	 * 业务层 禁止写sql语句，以下方法仅供子类调用
	 ********************************************************************************/


	public List<Map<String, Object>> queryForList(String sql, Object... params) {
		return jdbcTemplate.queryForList(sql, params);
	}

	public List<Map<String, Object>> queryForList(String sql) {
		return jdbcTemplate.queryForList(sql);
	}

	/**
	 * 查询
	 * <p>
	 * sql
	 *
	 * @param params 查询参数
	 * @return 返回查询结果
	 */
	public Object queryForObject(Class beanClass, String sql, Object... params) {
		try {
			return jdbcTemplate.queryForObject(sql, BeanPropertyRowMapper.newInstance(beanClass), params);
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	public Page queryForPage(String sql, int page, int rows, Object... params) {
		Page pageVo = new Page();
		pageVo.setList(queryForList(SqlUtil.getSelect(sql, page, rows), params));
		pageVo.setTotalRow(count(SqlUtil.getCount(sql), params));
		return pageVo;
	}

	public int count(String sql, Object... params) {
		try {
			return jdbcTemplate.queryForObject(sql, Integer.class, params);
		} catch (Exception e) {
			throw new SqlException(e, sql);
		}
	}
	public int queryForInt(String sql, Object... params) {
		return count(sql,params);
	}
	public String queryForString(String sql, Object... params) {
		try {
			return jdbcTemplate.queryForObject(sql, String.class, params);
		} catch (Exception e) {
			throw new SqlException(e, sql);
		}
	}
	public Date queryForDate(String sql, Object... params) {
		try {
			return jdbcTemplate.queryForObject(sql, Date.class, params);
		} catch (Exception e) {
			throw new SqlException(e, sql);
		}
	}

	/********************************************************************************
	 * 私有方法
	 ********************************************************************************/


	private int update(SqlContext sqlContext) {
		int result;
		String sql = null;
		try {
			sql = sqlContext.getSql();
			result = jdbcTemplate.update(sql, sqlContext.getParams());
		} catch (Exception e) {
			throw new SqlException(e, sql);
		}
		return result;
	}

	private List<Map<String, Object>> queryList(SqlContext sqlContext) {
		String sql = null;
		try {
			sql = sqlContext.getSql();
			return jdbcTemplate.queryForList(sql, sqlContext.getParams());
		} catch (Exception e) {
			throw new SqlException(e, sql);
		}
	}

	private <T> List queryList(SqlContext sqlContext, Class clazz) {
		String sql = null;
		try {
			sql = sqlContext.getSql();
			return queryForList(sql, clazz, sqlContext.getParams());
		} catch (Exception e) {
			throw new SqlException(e, sql);
		}
	}

	/**
	 * 查询
	 *
	 * @param sqlContext sql上下文
	 */
	private int queryInt(SqlContext sqlContext) {
		String sql = null;
		try {
			sql = sqlContext.getSql();
			return jdbcTemplate.queryForObject(sql, Integer.class, sqlContext.getParams());
		} catch (Exception e) {
			throw new SqlException(e, sql);
		}
	}

}




package org.springrain.frame.dao;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.dao.EmptyResultDataAccessException;
//import org.springframework.jdbc.core.FrameBeanPropertyRowMapper;
import org.springframework.jdbc.core.CallableStatementCreator;
import org.springframework.jdbc.core.ColumnMapRowMapper;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.transaction.NoTransactionException;
import org.springframework.transaction.interceptor.TransactionInterceptor;
import org.springrain.frame.common.BaseLogger;
import org.springrain.frame.common.SessionUser;
import org.springrain.frame.dao.dialect.IDialect;
import org.springrain.frame.entity.AuditLog;
import org.springrain.frame.task.LuceneTask;
import org.springrain.frame.util.ClassUtils;
import org.springrain.frame.util.EntityInfo;
import org.springrain.frame.util.FieldInfo;
import org.springrain.frame.util.Finder;
import org.springrain.frame.util.GlobalStatic;
import org.springrain.frame.util.Page;
import org.springrain.frame.util.RegexValidateUtils;
import org.springrain.frame.util.SecUtils;
import org.springrain.frame.util.ThreadPoolManager;

/**
 * 基础的Dao父类,所有的Dao都必须继承此类,每个数据库都需要一个实现.</br>
 * 
 * 例如 demo数据的实现类是org.springrain.springrain.dao.BasedemoDaoImpl,demo2数据的实现类是org.
 * springrain demo2.dao.Basedemo2DaoImpl</br>
 * 
 * @copyright {@link weicms.net}
 * @author springrain<Auto generate>
 * @version 2013-03-19 11:08:15
 * @see org.springrain.frame.dao.BaseJdbcDaoImpl
 */
public abstract class BaseJdbcDaoImpl extends BaseLogger implements IBaseJdbcDao {
	private static final String frame_jdbc_call_key = "frame_jdbc_call_key";

	/**
	 * 抽象方法.每个数据库的代理Dao都必须实现.在多库情况下,用于区分底层数据库的连接对象,对数据库进行增删改查.</br>
	 * 例如:demo数据库的代理Dao org.springrain.dao.BasedemoDaoImpll
	 * 实现返回的是spring的beanjdbc.</br>
	 * demo2 数据库的代理Dao org.springrain.demo2.dao.Basedemo2DaoImpl 实现返回的是spring的bean
	 * jdbc_demo2.</br>
	 * 
	 * @return
	 */
	public abstract NamedParameterJdbcTemplate getJdbc();

	/**
	 * 抽象方法.每个数据库的代理Dao都必须实现.在多库情况下,用于区分底层数据库的连接对象,调用数据库的函数和存储过程.</br>
	 * 例如:demo 数据库的代理Dao org.springrain.demo1.dao.BasedemoDaoImpl 实现返回的是spring的bean
	 * jdbcCall.</br>
	 * datalog 数据库的代理Dao org.springrain.demo2.dao.Basedemo2DaoImpl
	 * 实现返回的是spring的beanjdbcCall_demo2.</br>
	 * 
	 * @return
	 */
	public abstract SimpleJdbcCall getJdbcCall();

	/**
	 * 读写分离的读数据库
	 * 
	 * @return
	 */
	public NamedParameterJdbcTemplate getReadJdbc() {
		return getJdbc();
	}

	/**
	 * 读写分离的写数据库
	 * 
	 * @return
	 */
	public NamedParameterJdbcTemplate getWriteJdbc() {
		return getJdbc();
	}

	/**
	 * 获取数据库方言,Dao 中注入spring bean.</br>
	 * 例如mysql的实现是 mysqlDialect. oracle的实现是 oracleDialect. 如果使用了sequence
	 * 在entity使用@PKSequence实现自增 详见 org.springrain.frame.dao.dialect.IDialect的实现
	 * 
	 * @return
	 */
	public abstract IDialect getDialect();

	/**
	 * 默认(return null)不记录日志,在多库情况下,用于区分数据库实例的日志记录表,
	 * 
	 * @return
	 */

	public AuditLog getAuditLog() {
		return null;
	}

	/**
	 * 是否打印sql语句,默认false
	 * 
	 * @return
	 */

	public boolean showsql() {
		return false;
	}

	public String getUserName() {
		return SessionUser.getUserName();
	}

	/**
	 * 打印sql
	 * 
	 * @param sql
	 */
	private void logInfoSql(String sql) {
		if (showsql()) {
			System.out.println(sql);
		}
	}

	public BaseJdbcDaoImpl() {
	}

	@Override
	public <T> List<T> queryForList(Finder finder, Class<T> clazz) throws Exception {
		return queryForList(finder, clazz, null);

	}

	@Override
	public <T> List<T> queryForListByProc(Finder finder, Class<T> clazz) throws Exception {
		String procName = finder.getProcName();
		String functionName = finder.getFunName();

		if (StringUtils.isBlank(procName) && StringUtils.isBlank(functionName)) {
			throw new NullPointerException("存储过程和函数不能同时为空!");
		}

		Map params = finder.getParams();
		Map<String, Object> m;
		SimpleJdbcCall simpleJdbcCall;

		if (StringUtils.isNotBlank(procName)) {
			simpleJdbcCall = getJdbcCall().withProcedureName(procName);
		} else {
			simpleJdbcCall = getJdbcCall().withFunctionName(functionName);
		}

		if (params != null) {
			if (ClassUtils.isBaseType(clazz)) {
				m = simpleJdbcCall.returningResultSet(frame_jdbc_call_key, new RowNumberSingleColumnRowMapper(clazz))
						.execute(params);
			} else {
				m = simpleJdbcCall
						.returningResultSet(frame_jdbc_call_key, FrameBeanPropertyRowMapper.newInstance(clazz))
						.execute(params);
			}
		} else {
			if (ClassUtils.isBaseType(clazz)) {
				m = simpleJdbcCall.returningResultSet(frame_jdbc_call_key, new RowNumberSingleColumnRowMapper(clazz))
						.execute();
			} else {
				m = simpleJdbcCall
						.returningResultSet(frame_jdbc_call_key, FrameBeanPropertyRowMapper.newInstance(clazz))
						.execute();
			}
		}
		return (List<T>) m.get(frame_jdbc_call_key);
	}

	/**
	 * 调用数据库存储过程 查询结果是 List
	 * 
	 * @param finder
	 * @return
	 * @throws Exception
	 */

	@Override
	public List<Map<String, Object>> queryForListByProc(Finder finder) throws Exception {
		String procName = finder.getProcName();
		String functionName = finder.getFunName();
		if (StringUtils.isBlank(procName) && StringUtils.isBlank(functionName)) {
			throw new NullPointerException("存储过程和函数不能同时为空!");
		}
		Map params = finder.getParams();
		Map<String, Object> m;
		SimpleJdbcCall simpleJdbcCall;

		if (StringUtils.isNotBlank(procName)) {
			simpleJdbcCall = getJdbcCall().withProcedureName(procName);
		} else {
			simpleJdbcCall = getJdbcCall().withFunctionName(functionName);
		}

		if (params != null) {
			m = simpleJdbcCall.returningResultSet(frame_jdbc_call_key, new ColumnMapRowMapper()).execute(params);
		} else {
			m = simpleJdbcCall.returningResultSet(frame_jdbc_call_key, new ColumnMapRowMapper()).execute();
		}
		return (List<Map<String, Object>>) m.get(frame_jdbc_call_key);

	}

	/**
	 * 调用数据库函数 查询结果是 List
	 * 
	 * @param finder
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<Map<String, Object>> queryForListByFunction(Finder finder) throws Exception {
		return queryForListByProc(finder);
	}

	@Override
	public List<Map<String, Object>> queryForList(Finder finder) throws Exception {
		// 打印sql
		logInfoSql(finder.getSql());
		return getReadJdbc().queryForList(finder.getSql(), finder.getParams());
	}

	@Override
	public Map<String, Object> queryForObject(Finder finder) throws Exception {
		Map<String, Object> map = null;
		try {
			// 打印sql
			logInfoSql(finder.getSql());
			map = getReadJdbc().queryForMap(finder.getSql(), finder.getParams());
		} catch (EmptyResultDataAccessException e) {
			logger.error(e.getMessage(), e);
			map = null;
		}

		return map;
	}

	@Override
	public List<Map<String, Object>> queryForList(Finder finder, Page page) throws Exception {
		String pageSql = getPageSql(page, finder);
		if (pageSql == null) {
            return null;
        }
		finder.setPageSql(pageSql);

		// 打印sql
		logInfoSql(pageSql);

		return getReadJdbc().queryForList(pageSql, finder.getParams());

	}

	@Override
	public Integer update(Finder finder) throws Exception {
		checkMethodName();

		// 打印sql
		logInfoSql(finder.getSql());
		return getWriteJdbc().update(finder.getSql(), finder.getParams());
	}

	@Override
	public <T> List<T> queryForList(Finder finder, Class<T> clazz, Page page) throws Exception {
		String pageSql = getPageSql(page, finder);
		if (pageSql == null) {
			return null;
		}

		finder.setPageSql(pageSql);

		// 打印sql
		logInfoSql(pageSql);

		if (ClassUtils.isBaseType(clazz)) {
			if (getDialect().isRowNumber()) {
				return getReadJdbc().query(pageSql, finder.getParams(), new RowNumberSingleColumnRowMapper(clazz));
			} else {
				return getReadJdbc().queryForList(pageSql, finder.getParams(), clazz);
			}
		} else {
			return getReadJdbc().query(pageSql, finder.getParams(), FrameBeanPropertyRowMapper.newInstance(clazz));
		}
	}

	@Override
	public <T> List<T> findListDataByFinder(Finder finder, Page page, Class<T> clazz, Object queryBean)
			throws Exception {

		if (finder == null) {

			String tableName = Finder.getTableName(queryBean);

			finder = new Finder("SELECT * FROM " + tableName);
			finder.append(" WHERE 1=1 ");

		}

		if (queryBean != null) {
			getFinderWhereByQueryBean(finder, queryBean);
		}

		if (page == null) {
			return this.queryForList(finder, clazz, page);
		}
		int _index = RegexValidateUtils.getOrderByIndex(finder.getSql());
		if (_index > 0) {
			finder.setSql(finder.getSql().substring(0, _index));
		}
		// 根据page的参数 添加 order by
		getFinderOrderBy(finder, page);

		return this.queryForList(finder, clazz, page);
	}

	@Override
	public Finder getFinderOrderBy(Finder finder, Page page) throws Exception {
		if (finder == null || page == null) {
			return finder;
		}
		String sort = page.getSort();
		String order = page.getOrder();
		if (StringUtils.isBlank(order)) {
		    return finder;
		}
		order = order.trim();
		if (order.contains(" ") || order.contains(";") || order.contains(",") || order.contains("'") || order.contains("(") || order.contains(")")) {// 认为是异常的,主要是防止注入
				return null;
		}

		if (RegexValidateUtils.getOrderByIndex(finder.getSql()) < 0) {
				finder.append(" order by ").append(order);
		}
		if (StringUtils.isBlank(sort)) {
			    return finder;
		}
		if ("asc".equalsIgnoreCase(sort) && (finder.getSql().toLowerCase().contains(" asc ") == false)) {
					finder.append(" asc ");
		} else if ("desc".equalsIgnoreCase(sort)&& (finder.getSql().toLowerCase().contains(" desc ") == false)) {
					finder.append(" desc ");
		}
			
		return finder;
	}

	@Override
	public Finder getFinderWhereByQueryBean(Finder finder, Object o) throws Exception {
		EntityInfo entityInfoByClass = ClassUtils.getEntityInfoByClass(o.getClass());
		if (entityInfoByClass == null) {
			return finder;
		}

		List<FieldInfo> fields = entityInfoByClass.getFields();
		if (CollectionUtils.isEmpty(fields)) {
			return finder;
		}

		Object alias_o = ClassUtils.getPropertieValue(GlobalStatic.frameTableAlias, o);
		String alias = null;
		if (alias_o != null) {
			alias = alias_o.toString();
		}

		for (FieldInfo finfo : fields) {
			String wheresql = finfo.getWhereSQL();
			if (StringUtils.isBlank(wheresql)) {
				continue;
			}

			String name = finfo.getFieldName();
			Object value = ClassUtils.getPropertieValue(name, o);
			if (value == null || StringUtils.isBlank(value.toString())) {
				continue;
			}
			if (StringUtils.isNotBlank(alias)) {
				wheresql = alias + "." + wheresql;
			}
			String pname = wheresql.substring(wheresql.indexOf(':') + 1).trim();
			if (wheresql.toLowerCase().contains(" in ") && pname.endsWith(")")) {
				pname = pname.substring(0, pname.length() - 1).trim();
			}

			if (wheresql.toLowerCase().contains(" like ")) {
				boolean qian = pname.trim().startsWith("%");
				boolean hou = pname.trim().endsWith("%");
				wheresql = wheresql.replaceAll("%", "");
				pname = pname.replaceAll("%", "");
				if (qian) {
					value = "%" + value;
				}
				if (hou) {
					value = value + "%";
				}

				finder.append(" and ").append(wheresql).setParam(pname, value);
			} else {
				finder.append(" and ").append(wheresql).setParam(pname, value);
			}
		}

		return finder;

	}

	/**
	 * 根据page 和finder对象,拼装返回分页查询的语句
	 * 
	 * @param page
	 * @param finder
	 * @return
	 * @throws Exception
	 */
	private String getPageSql(Page page, Finder finder) throws Exception {
		String sql = finder.getSql();
		String orderSql = finder.getOrderSql();

		Map<String, Object> paramMap = finder.getParams();

		// 查询sql、统计sql不能为空
		if (StringUtils.isBlank(sql)) {
			return null;
		}
		if (RegexValidateUtils.getOrderByIndex(sql) > -1) {
			if (StringUtils.isBlank(orderSql)) {
				orderSql = sql.substring(RegexValidateUtils.getOrderByIndex(sql));
				sql = sql.substring(0, RegexValidateUtils.getOrderByIndex(sql));
			}
		} else {
			if (page != null && StringUtils.isNotBlank(page.getOrder())) {// 如果page中包含
																			// 排序属性
				String _order = page.getOrder().trim();
				if (_order.contains(" ") || _order.contains(";") || _order.contains(",") || _order.contains("'")
						|| _order.contains("(") || _order.contains(")")) {// 认为是异常的,主要是防止注入
					orderSql = " order by id asc ";
				} else {
					String _sort = page.getSort();
					if (_sort == null) {
						_sort = "";
					}
					_sort = _sort.trim().toLowerCase();

					if ((!"asc".equals(_sort)) && (!"desc".equals(_sort))) {// 如果
																			// 不是
																			// asc
																			// 也不是
																			// desc
						_sort = "";
					}

					orderSql = " order by " + page.getOrder() + " " + _sort;
				}

			} else {
				orderSql = " order by id asc ";
			}
		}

		if (page == null) {
			if (StringUtils.isNotBlank(orderSql)) {
                return sql + " " + orderSql;
            } else {
                return sql;
            }
		}

		// 如果不需要查询总条数
		if (!page.getSelectpagecount()) {
			return getDialect().getPageSql(sql, orderSql, page);
		}

		Integer count;

		if (finder.getCountFinder() == null) {
			String countSql = new String(sql);
			int order_int = RegexValidateUtils.getOrderByIndex(countSql);
			if (order_int > 1) {
				countSql = countSql.substring(0, order_int);
			}

			// 特殊关键字过滤, distinct ,union ,group by
			if (countSql.toLowerCase().indexOf(" distinct ") > -1 || countSql.toLowerCase().indexOf(" union ") > -1
					|| RegexValidateUtils.getGroupByIndex(countSql) > -1) {
				countSql = "SELECT COUNT(*)  frame_row_count FROM (" + countSql
						+ ") temp_frame_noob_table_name WHERE 1=1 ";
			} else {
				int fromIndex = RegexValidateUtils.getFromIndex(countSql);
				if (fromIndex > -1) {
					countSql = "SELECT COUNT(*) " + countSql.substring(fromIndex);
				} else {
					countSql = "SELECT COUNT(*)  frame_row_count FROM (" + countSql
							+ ") temp_frame_noob_table_name WHERE 1=1 ";
				}

			}

			// count = getReadJdbc().queryForInt(countSql, paramMap);
			count = getReadJdbc().queryForObject(countSql, paramMap, Integer.class);
		} else {
			count = queryForObject(finder.getCountFinder(), Integer.class);
		}
		// 记录总行数(区分是否使用占位符)
		if (count == 0) {
			return null;
		} else {
			page.setTotalCount(count);
		}
		return getDialect().getPageSql(sql, orderSql, page);
	}

	@Override
	public <T> T queryForObject(Finder finder, Class<T> clazz) throws Exception {
		// 打印sql
		logInfoSql(finder.getSql());
		T t = null;
		try {
			if (ClassUtils.isBaseType(clazz)) {
				t = (T) getReadJdbc().queryForObject(finder.getSql(), finder.getParams(), clazz);

			} else {
				t = (T) getReadJdbc().queryForObject(finder.getSql(), finder.getParams(),
						FrameBeanPropertyRowMapper.newInstance(clazz));
			}
		} catch (EmptyResultDataAccessException e) {
			logger.error(e.getMessage(), e);
			t = null;
		}

		return t;

	}

	private String wrapsavesql(Object entity, Map paramMap, Boolean isSequence) throws Exception {
		Class clazz = entity.getClass();
		// entity信息
		EntityInfo entityInfo = ClassUtils.getEntityInfoByEntity(entity);
		List<String> fdNames = ClassUtils.getAllDBFields(clazz);
		String id = SecUtils.getUUID();// 主键
		String tableName = entityInfo.getTableName();
		Class<?> returnType = entityInfo.getPkReturnType();
		String pkName = entityInfo.getPkName();
		// 获取 分表的扩展
		String tableExt = entityInfo.getTableSuffix();

		StringBuilder sql = new StringBuilder("INSERT INTO ").append(tableName).append(tableExt).append("(");

		StringBuilder valueSql = new StringBuilder(" VALUES(");

		// 如果是ID,自动生成UUID,处理主键
		Object _getId = ClassUtils.getPKValue(entity); // 主键

		if (_getId != null && StringUtils.isNotBlank(_getId.toString())) {// 如果Id有值
			id = _getId.toString();
		}

		if (StringUtils.isNotBlank(entityInfo.getPksequence())) {// 如果包含主键序列注解
			String _sequence_value = entityInfo.getPksequence();
			sql.append(pkName);
			valueSql.append(_sequence_value);
			if (fdNames.size() > 1) {
				sql.append(",");
				valueSql.append(",");
			} else {
				sql.append(")");
				valueSql.append(")");
			}
		} else if (returnType == String.class) {
			sql.append(pkName);
			valueSql.append(":" + pkName);
			if (fdNames.size() > 1) {
				sql.append(",");
				valueSql.append(",");
			} else {
				sql.append(")");
				valueSql.append(")");
			}
			paramMap.put(pkName, id);
			ClassUtils.setPropertieValue(pkName, entity, id);
		}

		// 排除主键,上面已经处理过了
		fdNames.remove(pkName);

		// 处理非主键字段
		for (int i = 0; i < fdNames.size(); i++) {
			String fdName = fdNames.get(i);// 字段名称
			String mapKey = ":" + fdName;// 占位符
			Object fdValue = ClassUtils.getPropertieValue(fdName, entity);
			paramMap.put(fdName, fdValue);
			sql.append(fdName);
			valueSql.append(mapKey);
			if (fdNames.size() - i - 1 == 0) {// 最后一个字段
				sql.append(")");
				valueSql.append(")");
			} else {
				sql.append(",");
				valueSql.append(",");
			}

		}
		sql.append(valueSql);// sql语句
		return sql.toString();
	}

	/**
	 * 保存一个实体类,不记录日志
	 * 
	 * @param entity
	 * @return
	 * @throws Exception
	 */
	private Object saveNoLog(Object entity) throws Exception {
		// entity信息
		EntityInfo entityInfo = ClassUtils.getEntityInfoByEntity(entity);
		Class<?> returnType = entityInfo.getPkReturnType();

		Map paramMap = new HashMap();
		Boolean isSequence = StringUtils.isNotBlank(entityInfo.getPksequence());
		String sql = wrapsavesql(entity, paramMap, isSequence);
		// 打印sql
		logInfoSql(sql);

		// 增加如果表没有主键的判断
		if (returnType == null) {
			getWriteJdbc().update(sql, paramMap);
			return null;
		}

		else if (returnType == String.class) {
			getWriteJdbc().update(sql, paramMap);
			return ClassUtils.getPKValue(entity).toString();

		} else {
			KeyHolder keyHolder = new GeneratedKeyHolder();
			SqlParameterSource ss = new MapSqlParameterSource(paramMap);
			String _pkName = entityInfo.getPkName();
			if (StringUtils.isNotBlank(_pkName) && isSequence) {
				getWriteJdbc().update(sql, ss, keyHolder, new String[] { _pkName });
			} else {
				getWriteJdbc().update(sql, ss, keyHolder);
			}
			return keyHolder.getKey().longValue();
		}
	}

	@Override
	public Object save(Object entity) throws Exception {
		checkMethodName();

		// 保存到数据库
		Object id = saveNoLog(entity);
		EntityInfo entityInfo = ClassUtils.getEntityInfoByEntity(entity);
		if (entityInfo.getLuceneSearchAnnotation()) {
			// 保存到索引文件
			LuceneTask luceneTask = new LuceneTask(entity, LuceneTask.saveDocument);
			ThreadPoolManager.addThread(luceneTask);
		}

		// 记录日志
		AuditLog auditLog = getAuditLog();
		if (auditLog == null) {
			return id;
		}

		if (entityInfo.isNotLog()) {
			return id;
		}

		String tableExt = entityInfo.getTableSuffix();
		if (StringUtils.isBlank(tableExt)) {
			int year = Calendar.getInstance().get(Calendar.YEAR);
			tableExt = GlobalStatic.tableSuffix + year;
		}
		auditLog.setOperationClass(entity.getClass().getName());
		auditLog.setOperationType(GlobalStatic.dataSave);
		auditLog.setOperatorName(getUserName());
		if (id != null) {// 如果Id有值
			auditLog.setOperationClassId(id.toString());
		}

		auditLog.setOperationTime(new Date());
		auditLog.setCurValue(entity.toString());
		auditLog.setPreValue("无");
		auditLog.setSuffix(tableExt);
		saveNoLog(auditLog);// 保存日志

		return id;
	}

	@Override
	public List<Integer> update(List list) throws Exception {
		return update(list, false);
	}

	@Override
	public List<Integer> update(List list, boolean onlyupdatenotnull) throws Exception {
		checkMethodName();

		if (CollectionUtils.isEmpty(list)) {
			return null;
		}
		List<Integer> updateList = new ArrayList<>();
		Map[] maps = new HashMap[list.size()];
		String sql = null;
		for (int i = 0; i < list.size(); i++) {
			Map paramMap = new HashMap();
			sql = wrapupdatesql(list.get(i), paramMap, onlyupdatenotnull);
			maps[i] = paramMap;
		}
		int[] batchUpdate = getWriteJdbc().batchUpdate(sql, SqlParameterSourceUtils.createBatch(maps));

		if (batchUpdate.length < 1) {
			return updateList;
		}
		for (int i : batchUpdate) {
			updateList.add(i);
		}

		EntityInfo entityInfo = ClassUtils.getEntityInfoByClass(list.get(0).getClass());

		if (entityInfo.getLuceneSearchAnnotation()) {
			// 更新到索引文件
			LuceneTask luceneTask = new LuceneTask(list, LuceneTask.updateDocument);
			ThreadPoolManager.addThread(luceneTask);
		}

		return updateList;
	}

	@Override
	public List<Integer> save(List list) throws Exception {
		checkMethodName();

		if (CollectionUtils.isEmpty(list)) {
			return null;
		}

		List<Integer> updateList = new ArrayList<>();
		Map[] maps = new HashMap[list.size()];
		String sql = null;
		for (int i = 0; i < list.size(); i++) {
			Map paramMap = new HashMap();
			sql = wrapsavesql(list.get(i), paramMap, false);
			maps[i] = paramMap;
		}
		int[] batchUpdate = getWriteJdbc().batchUpdate(sql, SqlParameterSourceUtils.createBatch(maps));

		if (batchUpdate.length < 1) {
			return updateList;
		}
		for (int i : batchUpdate) {
			updateList.add(i);
		}

		EntityInfo entityInfo = ClassUtils.getEntityInfoByClass(list.get(0).getClass());
		if (entityInfo.getLuceneSearchAnnotation()) {
			// 更新到索引文件
			LuceneTask luceneTask = new LuceneTask(list, LuceneTask.saveDocument);
			ThreadPoolManager.addThread(luceneTask);
		}

		return updateList;
	}

	private String wrapupdatesql(Object entity, Map paramMap, boolean onlyupdatenotnull) throws Exception {
		Class clazz = entity.getClass();
		// entity的信息
		EntityInfo entityInfo = ClassUtils.getEntityInfoByEntity(entity);
		List<String> fdNames = ClassUtils.getAllDBFields(clazz);
		String tableName = entityInfo.getTableName();

		String pkName = entityInfo.getPkName();

		// 获取 分表的扩展
		String tableExt = entityInfo.getTableSuffix();
		StringBuilder sql = new StringBuilder("UPDATE ").append(tableName).append(tableExt).append("  SET  ");

		StringBuilder whereSQL = new StringBuilder(" WHERE ").append(pkName).append("=:").append(pkName);
		for (int i = 0; i < fdNames.size(); i++) {
			String fdName = fdNames.get(i);// 字段名称
			Object fdValue = ClassUtils.getPropertieValue(fdName, entity);

			// 只更新不为null的字段
			if (onlyupdatenotnull && fdValue == null) {// 如果只更新不为null的字段,字段值为null
														// 不更新
				continue;
			}

			if (fdName.equals(pkName)) {// 如果是ID
				if (fdValue != null) {// id有值
					paramMap.put(fdName, fdValue);
				}
				continue;
			}
			// 设置字段
			paramMap.put(fdName, fdValue);
			// 添加需要更新的字段
			sql.append(fdName).append("=:").append(fdName).append(",");
		}

		String str = sql.toString();
		if (str.endsWith(",")) {
			str = str.substring(0, str.length() - 1);
		}

		/*
		 * sql.append(whereSQL); return sql.toString();
		 */

		return str + whereSQL;
	}

	@Override
	public Integer update(Object entity) throws Exception {
		return update(entity, false);

	}

	@Override
	public Integer update(Object entity, boolean onlyupdatenotnull) throws Exception {
		checkMethodName();
		Class clazz = entity.getClass();
		// entity的信息
		EntityInfo entityInfo = ClassUtils.getEntityInfoByEntity(entity);
		// 获取 分表的扩展
		String tableExt = entityInfo.getTableSuffix();
		Map paramMap = new HashMap();
		String sql = wrapupdatesql(entity, paramMap, onlyupdatenotnull);
		Object id = ClassUtils.getPKValue(entity);
		// 打印sql
		logInfoSql(sql);

		Object old_entity = null;
		AuditLog auditLog = getAuditLog();
		if (auditLog != null) {
			old_entity = findByID(id, clazz, tableExt);
		}
		// 更新entity
		Integer hang = getWriteJdbc().update(sql, paramMap);

		if (entityInfo.getLuceneSearchAnnotation()) {
			// 更新到索引文件
			LuceneTask luceneTask = new LuceneTask(entity, LuceneTask.updateDocument);
			ThreadPoolManager.addThread(luceneTask);
		}

		if (auditLog == null) {
			return hang;
		}

		if (entityInfo.isNotLog()) {
			return hang;
		}

		auditLog.setOperationClass(entity.getClass().getName());
		auditLog.setOperationType(GlobalStatic.dataUpdate);
		auditLog.setOperatorName(getUserName());
		auditLog.setOperationClassId(id.toString());
		auditLog.setOperationTime(new Date());
		auditLog.setPreValue(old_entity.toString());
		auditLog.setCurValue(entity.toString());

		String audit_tableExt = tableExt;
		if (StringUtils.isBlank(tableExt)) {
			int year = Calendar.getInstance().get(Calendar.YEAR);
			audit_tableExt = GlobalStatic.tableSuffix + year;
		}
		auditLog.setSuffix(audit_tableExt);
		// 保存日志
		saveNoLog(auditLog);

		return hang;

	}

	@Override
	public <T> T findByID(Object id, Class<T> clazz) throws Exception {
		return findByID(id, clazz, null);
	}

	@Override
	public <T> T findByID(Object id, Class<T> clazz, String tableExt) throws Exception {
		EntityInfo entityInfo = ClassUtils.getEntityInfoByClass(clazz);
		String tableName = entityInfo.getTableName();
		String idName = entityInfo.getPkName();
		if (StringUtils.isNotBlank(tableExt)) {
			tableName += tableExt;
		}
		String sql = "SELECT * FROM " + tableName + " WHERE " + idName + "=:id";
		Finder finder = new Finder(sql);
		finder.setParam("id", id);
		// 打印sql
		logInfoSql(finder.getSql());
		return queryForObject(finder, clazz);
	}

	@Override
	public Object saveorupdate(Object entity) throws Exception {
		Object o = ClassUtils.getPKValue(entity);
		if (o == null) {
			return save(entity);
		} else {
			return update(entity);
		}

	}

	@Override
	public void deleteById(Object id, Class clazz) throws Exception {
		checkMethodName();

		if (id == null) {
            return;
        }

		EntityInfo entityInfo = ClassUtils.getEntityInfoByClass(clazz);
		String tableName = entityInfo.getTableName();
		String idName = entityInfo.getPkName();
		String sql = "DELETE FROM " + tableName + " WHERE " + idName + "=:id";
		Finder finder = new Finder(sql);
		finder.setParam("id", id);

		AuditLog auditLog = getAuditLog();
		Object findEntityByID = null;

		if (auditLog != null) {
			findEntityByID = findByID(id, clazz);
			if (findEntityByID == null) {// 数据库不存在记录,直接返回
				return;
			}
		}
		// 打印sql
		logInfoSql(finder.getSql());
		update(finder);

		if (auditLog == null) {
			return;
		}

		if (entityInfo.isNotLog()) {
			return;
		}
		/**
		 * 删除还有个 bug,就是删除分表的数据,日志记录有问题 没有分表
		 */

		int year = Calendar.getInstance().get(Calendar.YEAR);
		String tableExt = GlobalStatic.tableSuffix + year;

		auditLog.setOperationClass(clazz.getName());
		auditLog.setOperationType(GlobalStatic.dataDelete);
		auditLog.setOperatorName(getUserName());
		auditLog.setOperationClassId(id.toString());
		auditLog.setOperationTime(new Date());
		auditLog.setPreValue(findEntityByID.toString());
		auditLog.setSuffix(tableExt);
		auditLog.setCurValue("无");
		// 保存日志
		saveNoLog(auditLog);

		// if(CollectionUtils.isNotEmpty(ClassUtils.getLuceneFields(clazz))){

		if (entityInfo.getLuceneSearchAnnotation()) {
			// 更新到索引文件
			LuceneTask luceneTask = new LuceneTask(id, clazz);
			ThreadPoolManager.addThread(luceneTask);
		}

	}

	@SuppressWarnings({ "rawtypes" })
	@Override
	public void deleteByIds(List ids, Class clazz) throws Exception {
		checkMethodName();

		if (CollectionUtils.isEmpty(ids)) {
            return;
        }

		EntityInfo entityInfo = ClassUtils.getEntityInfoByClass(clazz);
		String tableName = entityInfo.getTableName();
		String idName = entityInfo.getPkName();
		String sql = "Delete FROM " + tableName + " WHERE " + idName + " in (:ids)";
		Finder finder = new Finder(sql);
		finder.setParam("ids", ids);
		update(finder);

		// if(CollectionUtils.isNotEmpty(ClassUtils.getLuceneFields(clazz))){
		if (entityInfo.getLuceneSearchAnnotation()) {
			// 更新到索引文件
			LuceneTask luceneTask = new LuceneTask(ids, clazz);
			ThreadPoolManager.addThread(luceneTask);
		}
	}

	@Override
	public Map<String, Object> queryObjectByFunction(Finder finder) throws Exception {
		return queryObjectByProc(finder);
	}

	@Override
	public Map<String, Object> queryObjectByProc(Finder finder) throws Exception {
		String procName = finder.getProcName();
		String functionName = finder.getFunName();
		if (StringUtils.isBlank(procName) && StringUtils.isBlank(functionName)) {
			throw new NullPointerException("存储过程和函数不能同时为空!");
		}
		Map<String, Object> params = finder.getParams();
		Map<String, Object> m = null;
		SimpleJdbcCall simpleJdbcCall = null;

		if (StringUtils.isNotBlank(procName)) {
			simpleJdbcCall = getJdbcCall().withProcedureName(procName);
		} else {
			simpleJdbcCall = getJdbcCall().withFunctionName(functionName);
		}

		try {
			if (params == null) {
				params = new HashMap<String, Object>();
			}
			m = simpleJdbcCall.execute(params);
		} catch (EmptyResultDataAccessException e) {
			logger.error(e.getMessage(), e);
			m = null;
		}
		return m;
	}

	@Override
	public <T> List<T> queryForListByFunction(Finder finder, Class<T> clazz) throws Exception {
		return queryForListByProc(finder, clazz);
	}

	@Override
	public <T> T queryForObjectByProc(Finder finder, Class<T> clazz) throws Exception {
		T t = null;
		String procName = finder.getProcName();
		if (StringUtils.isEmpty(procName)) {
			return null;
		}
		Map<String, Object> params = finder.getParams();
		try {
			if (params == null) {
				params = new HashMap<String, Object>();
			}
			t = (T) getJdbcCall().withProcedureName(procName).executeObject(clazz, params);

		} catch (EmptyResultDataAccessException e) {
			logger.error(e.getMessage(), e);
			t = null;
		}

		return t;
	}

	@Override
	public <T> T queryForObjectByByFunction(Finder finder, Class<T> clazz) throws Exception {
		String funName = finder.getFunName();
		T t = null;
		if (StringUtils.isEmpty(funName)) {
			return null;
		}
		Map<String, Object> params = finder.getParams();
		try {
			if (params == null) {
				params = new HashMap<String, Object>();
			}
			t = getJdbcCall().withFunctionName(funName).executeFunction(clazz, params);

		} catch (EmptyResultDataAccessException e) {
			logger.error(e.getMessage(), e);
			t = null;
		}
		return t;
	}

	/**
	 * Entity作为查询的query bean,并返回Entity
	 * 
	 * @param entity
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <T> T queryForObject(T entity) throws Exception {
		String tableName = Finder.getTableName(entity);
		Finder finder = new Finder("SELECT * FROM ");
		finder.append(tableName).append("  WHERE 1=1 ");
		getFinderWhereByQueryBean(finder, entity);
		// 打印sql
		logInfoSql(finder.getSql());
		return (T) queryForObject(finder, entity.getClass());

	}

	/**
	 * Entity作为查询的query bean,并返回Entity
	 * 
	 * @param entity
	 * @param page
	 *            分页对象
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <T> List<T> queryForListByEntity(T entity, Page page) throws Exception {
		String tableName = Finder.getTableName(entity);
		Finder finder = new Finder("SELECT * FROM ");
		finder.append(tableName).append("  WHERE 1=1 ");
		getFinderWhereByQueryBean(finder, entity);
		// 打印sql
		logInfoSql(finder.getSql());
		return (List<T>) queryForList(finder, entity.getClass(), page);

	}

	/**
	 * 执行 call 操作,执行存储过程,和数据库函数
	 * 
	 * @param callableStatementCreator
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@Override
	public Object executeCallBack(CallableStatementCreator callableStatementCreator, List<SqlParameter> parameter)
			throws Exception {
		return getWriteJdbc().getJdbcOperations().call(callableStatementCreator, parameter);

	}

	@Override
	public boolean isCheckMethodName() {
		return true;
	}

	/**
	 * 检查方法是否有事务
	 * 
	 * @throws Exception
	 */
	private void checkMethodName() throws NoTransactionException {
		if (isCheckMethodName()) {// 方法是否具有事务
			try {
				TransactionInterceptor.currentTransactionStatus();
			} catch (NoTransactionException e) {
				logger.error(e.getMessage(), e);
				throw new NoTransactionException("save和update方法,请按照事务拦截方法名书写规范!具体参见:applicationContext-tx.xml");
			}

		}
	}

}

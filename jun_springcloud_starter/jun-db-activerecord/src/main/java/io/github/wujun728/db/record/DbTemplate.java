package io.github.wujun728.db.record;

import cn.hutool.core.map.MapUtil;
import io.github.wujun728.common.exception.BusinessException;
import io.github.wujun728.db.record.mapper.BaseRowMapper;
import io.github.wujun728.db.record.mapper.BatchSql;
import io.github.wujun728.db.record.mapper.SqlUtil;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 数据库操作类
 */
@Setter
@Getter
public class DbTemplate {
	public static final Logger logger = LoggerFactory.getLogger(DbTemplate.class);
	private JdbcTemplate jdbcTemplate;
	private TransactionTemplate transactionTemplate;
    public final static int DEFAULT_FETCHSIZE = 32; //默认的fetchsize

    public DbTemplate() {}

    public DbTemplate(DataSource dataSource) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
    	TransactionTemplate transactionTemplate = new TransactionTemplate();
    	DataSourceTransactionManager transactionManager = new DataSourceTransactionManager();
        transactionManager.setDataSource(dataSource);
        transactionTemplate.setTransactionManager(transactionManager);
    	this.jdbcTemplate = jdbcTemplate;
        this.transactionTemplate = transactionTemplate;
    }

	public JdbcTemplate getJdbcTemplate() {
		return this.jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

    /**
     * 返回一条记录
     * @param sql 传入的sql语句
     * @param objects 参数数组
     * @return map对象
     */
	public Map<String, Object> queryForMap(String sql, Object[] objects) {
		Map<String, Object> map = null;
		try {
			if(objects != null && objects.length > 0) {
				map = jdbcTemplate.queryForMap(sql, objects);
			}
			else {
				map = jdbcTemplate.queryForMap(sql);
			}
		} catch (EmptyResultDataAccessException e) {
//			logger.error("查询无记录："+SqlUtil.getSql(sql, objects));
		} catch (Exception e) {
			logger.error(e.getMessage()+"\n"+ SqlUtil.getSql(sql, objects));
			e.printStackTrace();
			throw new BusinessException(e.getMessage());
		}
		if (map == null) {
			map = new HashMap<String, Object>();
		}
		return map;
	}

	/**
	 * 返回一int数值
	 * @param sql 传入的sql语句
	 * @param objects 参数数组
	 * @return -1:数据库异常
	 */
	public int queryForInt(String sql, Object[] objects) {
		int exc = -1;
		try {
			if(objects != null && objects.length > 0) {
				exc = jdbcTemplate.queryForObject(sql, objects, Integer.class);
			}
			else {
				exc = jdbcTemplate.queryForObject(sql, Integer.class);
			}
		} catch (Exception e) {
			exc = -1;
			logger.error(e.getMessage()+"\n"+SqlUtil.getSql(sql, objects));
			e.printStackTrace();
			throw new BusinessException(e.getMessage());
		}
		return exc;
	}

	/**
	 * 返还一个long数值
	 * @param sql 传入的sql语句
	 * @param objects  参数数组
	 * @return -1:数据库异常
	 */
	public long queryForLong(String sql, Object[] objects) {
		long exc = -1;
		try {
			if(objects != null && objects.length > 0) {
				exc = jdbcTemplate.queryForObject(sql, objects, Long.class);
			}
			else {
				exc = jdbcTemplate.queryForObject(sql, Long.class);
			}
		} catch (Exception e) {
			exc = -1;
			logger.error(e.getMessage()+"\n"+SqlUtil.getSql(sql, objects));
			e.printStackTrace();
			throw new BusinessException(e.getMessage());
		}
		return exc;
	}

	/**
     * 返回指定对象
	 * @param sql 传入的sql语句
	 * @param objects  参数数组
	 * @param rowMapper  字段映射，针对实体类，例如 User、Student ...
	 * @return 实体类对象
     */
    public <T> T queryForObject(String sql, Object[] objects, RowMapper<T> rowMapper) {
    	try {
            return jdbcTemplate.queryForObject(sql, objects, rowMapper);
        } catch (EmptyResultDataAccessException e) {
//			logger.error("查询无记录："+SqlUtil.getSql(sql, objects));
            return null;
        } catch (Exception e) {
			logger.error(e.getMessage()+"\n"+SqlUtil.getSql(sql, objects));
			e.printStackTrace();
			throw new BusinessException(e.getMessage());
        	//return null;
		}
    }

	/**
     * 返回指定对象，针对Java Bean，非简单类型（String、Integer、Long），例如 User、Student ...
	 * @param sql 传入的sql语句
	 * @param objects  参数数组
	 * @param clazz  实体类，通过@MapRow映射数据库字段
	 * @return 实体类对象
     */
	public <T> T queryForObject(String sql, Object[] objects, Class<T> clazz) {
    	try {
            return jdbcTemplate.queryForObject(sql, objects, new BaseRowMapper<T>(clazz));
        } catch (EmptyResultDataAccessException e) {
//			logger.error("查询无记录："+SqlUtil.getSql(sql, objects));
            return null;
        } catch (Exception e) {
			logger.error(e.getMessage()+"\n"+SqlUtil.getSql(sql, objects));
        	//return null;
			e.printStackTrace();
			throw new BusinessException(e.getMessage());
		}
    }

	/**
	 * 返回一个字符串
	 * @param sql 传入的sql语句
	 * @param objects  参数数组，可以为null
	 * @return 字符串，异常情况下为空串
	 */
	public String queryForString(String sql, Object[] objects) {
		String str = "";
		try {
			if(objects != null && objects.length > 0) {
				str = jdbcTemplate.queryForObject(sql, objects, String.class);
			}
			else {
				str = jdbcTemplate.queryForObject(sql, String.class);
			}
			return str;
		} catch (EmptyResultDataAccessException e) {
//			logger.error("查询无记录："+SqlUtil.getSql(sql, objects));
            return "";
        } catch (Exception e) {
			logger.error(e.getMessage()+"\n"+SqlUtil.getSql(sql, objects));
			//return "";
			e.printStackTrace();
			throw new BusinessException(e.getMessage());
		}
	}

	public Date queryForDate(String sql, Object[] objects) {
		Date date = null;
		try {
			if(objects != null && objects.length > 0) {
				date = jdbcTemplate.queryForObject(sql, objects, Date.class);
			}
			else {
				date = jdbcTemplate.queryForObject(sql, Date.class);
			}
			return date;
		} catch (EmptyResultDataAccessException e) {
//			logger.error("查询无记录："+SqlUtil.getSql(sql, objects));
            return null;
        } catch (Exception e) {
			logger.error(e.getMessage()+"\n"+SqlUtil.getSql(sql, objects));
			//return null;
			e.printStackTrace();
			throw new BusinessException(e.getMessage());
		}
	}

	/**
	 * 返回数据集
	 * @param sql 传入的sql语句
	 * @param objects  参数数组，可以为null
	 * @param keyName 字段名称
	 * @return 指定字段对应值的字符串list集合
	 */
	public List<String> queryForList(String sql, Object[] objects, String keyName) {
		List<String> resList = new ArrayList<String>();

		List<Map<String, Object>> dataList = this.queryForList(sql, objects, DbTemplate.DEFAULT_FETCHSIZE);
		for(Map<String, Object> map : dataList) {
			resList.add(MapUtil.getStr(map, keyName));
		}

		return resList;
	}

	/**
	 * 返回数据集
	 * @param sql 传入的sql语句
	 * @param objects  参数数组，可以为null
	 * @return map对象的集合
	 */
	public List<Map<String, Object>> queryForList(String sql, Object[] objects) {
		return this.queryForList(sql, objects, DbTemplate.DEFAULT_FETCHSIZE);
	}

	/**
	 * 返回数据集
	 * @param sql 传入的sql语句:
	 * @param objects  参数数组，可以为null
	 * @param fetchSize 一次读取到缓存的记录数
	 * @return map对象的集合
	 */
	public List<Map<String, Object>> queryForList(String sql, Object[] objects, int fetchSize) {
		jdbcTemplate.setFetchSize(fetchSize);

		List<Map<String, Object>> list = null;
		try {
			if(objects != null && objects.length > 0) {
				list = jdbcTemplate.queryForList(sql, objects);
			}
			else {
				list = jdbcTemplate.queryForList(sql);
			}
		} catch (Exception e) {
			logger.error(e.getMessage()+"\n"+SqlUtil.getSql(sql, objects));
			e.printStackTrace();
			throw new BusinessException(e.getMessage()+"\n"+SqlUtil.getSql(sql, objects));
		}
		if (list == null) {
			list = new ArrayList<Map<String, Object>>();
		}
		return list;
	}

	/**
     * 返回指定对象list，例如 List<User> List<Student> ...
	 * @param sql 传入的sql语句:
	 * @param objects  参数数组，可以为null
	 * @param rowMapper  字段映射，针对实体类，例如 User、Student ...
	 * @return 实体类对象list集合
     */
    public <T> List<T> queryForList(String sql, Object[] objects, RowMapper<T> rowMapper) {
    	List<T> list = null;

    	try {
    		jdbcTemplate.setFetchSize(DEFAULT_FETCHSIZE);
			if(objects != null && objects.length > 0) {
				list = jdbcTemplate.query(sql, objects, rowMapper);
			}
			else {
				list = jdbcTemplate.query(sql, rowMapper);
			}
		} catch (Exception e) {
			logger.error(e.getMessage()+"\n"+SqlUtil.getSql(sql, objects));
			e.printStackTrace();
			throw new BusinessException(e.getMessage()+"\n"+SqlUtil.getSql(sql, objects));
		}
		if (list == null) {
			list = new ArrayList<T>();
		}

        return list;
    }

	/**
     * 返回指定对象list，例如 List<User> List<Student> ...
	 * @param sql 传入的sql语句
	 * @param objects  参数数组
	 * @param clazz  实体类，通过@MapRow映射数据库字段
	 * @return 实体类对象list集合
     */
    public <T> List<T> queryForList(String sql, Object[] objects, Class<T> clazz) {
    	List<T> list = null;

    	try {
    		jdbcTemplate.setFetchSize(DEFAULT_FETCHSIZE);
			if(objects != null && objects.length > 0) {
				list = jdbcTemplate.query(sql, objects, new BaseRowMapper<T>(clazz));
			}
			else {
				list = jdbcTemplate.query(sql, new BaseRowMapper<T>(clazz));
			}
		} catch (Exception e) {
			logger.error(e.getMessage()+"\n"+SqlUtil.getSql(sql, objects));
			e.printStackTrace();
			throw new BusinessException(e.getMessage()+"\n"+SqlUtil.getSql(sql, objects));
		}
		if (list == null) {
			list = new ArrayList<T>();
		}

        return list;
    }

	/**
	 * insert,update,delete 操作
	 * @param sql 传入的语句 sql="insert into tables values(?,?)";
	 * @param objects  参数数组
	 * @return 0:失败 1:成功
	 */
	public int execute(String sql, Object[] objects) {
		int exc = 1;
		try {
			if(objects != null && objects.length > 0) {
				jdbcTemplate.update(sql, objects);
			}
			else {
				jdbcTemplate.update(sql);
			}

		} catch (Exception e) {
			exc = 0;
			logger.error(e.getMessage()+"\n"+SqlUtil.getSql(sql, objects));
			e.printStackTrace();
			throw new BusinessException(e.getMessage()+"\n"+SqlUtil.getSql(sql, objects));
		}
		return exc;
	}

	/**
	 * 执行插入操作
	 * @param sql 传入的语句
	 * @param objects  参数数组
	 * @return 返回自动增加的id号
	 */
	public long insert(final String sql, final Object[] objects) {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
            	PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            	if(objects != null && objects.length > 0) {
            		for(int i=1;i<=objects.length;i++) {
            			ps.setObject(i, objects[i-1]);
            		}
            	}
                return ps;
			}
		}, keyHolder);

		return keyHolder.getKey().longValue();
	}

	/**
	 * 批量insert,update,delete操作
	 * @param sql sql语句必须固定
	 * @param objectsList 参数数组，数组长度必须与sql语句中占位符的数量一致
	 * @return 1：成功 0：失败
	 */
	public int batchExecute(String sql, final List<Object[]> objectsList) {
		int exc = 1;
		try {
			jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter(){
				@Override
				public void setValues(PreparedStatement ps, int i) throws SQLException {
					Object[] objects = objectsList.get(i);
					for(int j=0;j<objects.length;j++) {
						ps.setObject(j+1, objects[j]);
					}
				}

				@Override
				public int getBatchSize() {
					return objectsList.size();
				}
			});
		} catch (DataAccessException e) {
			exc = 0;
			logger.error(e.getMessage());
		}
		return exc;
	}

	/**
	 * 事务处理
	 * @param batchSql 批处理sql对象
	 * @return 1：成功 0：失败
	 */
	public int doInTransaction(final BatchSql batchSql) {
		int exc = 1;
		if (batchSql == null) {
			exc = 0;
		}
		try {
			transactionTemplate.execute(new TransactionCallbackWithoutResult() {
				public void doInTransactionWithoutResult(
						TransactionStatus status) {
					List<Map<String, Object>> sqlList = batchSql.getSqlList();
					for (int i = 0; i < sqlList.size(); i++) {
						Map<String, Object> sqlMap = sqlList.get(i);
						String sql = (String) sqlMap.get("sql");
						Object[] objects = (Object[]) sqlMap.get("objects");
						jdbcTemplate.update(sql, objects);
					}
				}
			});
		} catch (Exception e) {
			exc = 0;
			logger.error(e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage());
		}
		return exc;
	}

	/**
	 * 替换查询字段为count(1)
	 * @param sql 传入的sql语句
	 * @return 替换后的sql语句，如：select * from xxxx 替换为select count(1) from xxxx
	 */
	public String replaceSqlCount(String sql){
	    String oldSql = sql;
	    String newSql = "";

		sql = sql.toLowerCase();
		if (sql.contains("group")) {//如果语句最后有group by 不需要替换，嵌套一层
			String groupSql = oldSql.substring(sql.lastIndexOf("group"));
			if (!groupSql.contains(")")) {
				return "select count(1) from (" + sql + ") AA";
			}
		}

		//以小写字母匹配所有的select和from
		Pattern p = Pattern.compile("select|from", Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher(sql);
		int iSelect = 0;
		int iFrom = 0;
		//找到最外面的select对应的的是第几个from
		while(m.find()) {
			if (m.group().toLowerCase().equals("select")) {
				iSelect ++;
			}
			if (m.group().toLowerCase().equals("from")) {
				iFrom ++;
			}
			if (iSelect == iFrom) {
				break;
			}
		}

		int iSearch = 0;
		int fromIndex = 0;
		while(iSearch < iFrom){//找到最外面的select对应的from的位置
			if ((sql.toLowerCase()).indexOf("from")>0) {
				fromIndex = (sql.toLowerCase()).indexOf("from");
				sql = sql.replaceFirst("from", "####");
				iSearch ++;
			}
		}
		newSql = " select count(1) from "+oldSql.substring(fromIndex+4);
		return newSql;
	}

	/**
	 * 替换掉sql语句中最外面的order by
	 * @param sql 传入的sql语句
	 * @return 替换后的sql语句
	 */
	public String replaceSqlOrder(String sql){
		String oldSql = sql;
	    sql = sql.toLowerCase();
		String newSql = "";
		if (sql.contains("order ")) {
			String orderSql = oldSql.substring(sql.lastIndexOf("order "));
			if (orderSql.contains(")")) {
				newSql = oldSql;
			} else {
				newSql = oldSql.substring(0, sql.lastIndexOf("order "));
			}
		} else {
			newSql = oldSql;
		}
        return newSql;
    }

	/**
	 * 数据库分页查询
	 * @param sql 传入的sql语句
	 * @param list 参数集合
	 * @return Map对象的list集合
	 */
	public List<Map<String, Object>> getForList(String sql, List<String> list, int pageSize,int pageNum) {
		//int pageSize = RequestUtil.getIntValue(request, "pageSize");
		//int pageNum = RequestUtil.getIntValue(request, "pageNum");

		//替换sql语句中的order by，并将查询字段替换成count(1)
		List<String> cntSqlList = new ArrayList<String>();
		cntSqlList.addAll(list);
		String orderSql = this.replaceSqlOrder(sql);
		//需要查看替换掉order by之后占位符是否减少
		int cnt = this.getCharCnt(orderSql);
		if (cnt != cntSqlList.size()) {//如果替换order by之后占位符少了，将list从后去除cntSqlList.size()-cnt个参数
			int removeCnt = 0;
			int rCnt = cntSqlList.size()-cnt;//需要剔除的参数个数
			for (int i = cntSqlList.size()-1; i >= 0; i--) {
				cntSqlList.remove(i);
				removeCnt ++;
				if (removeCnt == rCnt) {
					break;
				}
			}
		}
		String cntSql = this.replaceSqlCount(orderSql);
		//需要查看替换成count(1)之后占位符是否减少
		cnt = this.getCharCnt(cntSql);
		if (cnt != cntSqlList.size()) {//如果替换count(1)之后占位符少了，将list从前去除list.size()-cnt个参数
			int removeCnt = 0;
			int rCnt = cntSqlList.size()-cnt;//需要剔除的参数个数
			for (int i = 0; i < cntSqlList.size(); i++) {
				cntSqlList.remove(i);
				removeCnt ++;
				if (removeCnt == rCnt) {
					break;
				}
			}
		}

		int startIndex = (pageNum-1) * pageSize;
		sql += " limit "+startIndex+", "+pageSize;
		return this.queryForList(sql, list.toArray());
	}

	/**
	 * 获取sql语句中占位符的个数
	 * @param sql 传入的sql语句
	 * @return 占位符的个数
	 */
	public int getCharCnt(String sql) {
		int cnt = 0;
		Pattern p = Pattern.compile("\\?", Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher(sql);
		while(m.find()){
			cnt ++;
		}
		return cnt;
	}

	/**
	 * 查询出记录总条数
	 * @param sql 传入的sql语句
	 * @return 记录总条数
	 */
	public long getTotalRows(String sql, List<String> list) {
		sql = this.replaceSqlCount(this.replaceSqlOrder(sql));
		return this.queryForLong(sql, list.toArray());
	}
}
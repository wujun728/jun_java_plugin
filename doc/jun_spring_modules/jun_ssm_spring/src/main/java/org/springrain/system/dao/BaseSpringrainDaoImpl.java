package org.springrain.system.dao;

import javax.annotation.Resource;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;
import org.springrain.frame.dao.BaseJdbcDaoImpl;
import org.springrain.frame.dao.IBaseJdbcDao;
import org.springrain.frame.dao.dialect.IDialect;
import org.springrain.frame.entity.AuditLog;

/**
 * springrain项目的基础Dao,代理demo数据库
 * 
 * @copyright {@link weicms.net}
 * @author springrain<Auto generate>
 * @version 2013-03-19 11:08:15
 * @see org.springrain.system.dao.BaseSpringrainDaoImpl
 */
@Repository("baseSpringrainDao")
public class BaseSpringrainDaoImpl extends BaseJdbcDaoImpl implements IBaseJdbcDao {

	public BaseSpringrainDaoImpl() {
	}

	/**
	 * demo 数据库的jdbc,对应 spring配置的 jdbc bean
	 */
	@Resource
	NamedParameterJdbcTemplate jdbc;
	/**
	 * demo 数据库的jdbcCall,对应 spring配置的 jdbcCall bean
	 */
	@Resource
	public SimpleJdbcCall jdbcCall;
	/**
	 * mysqlDialect 是mysql的方言,springBean的name,可以参考 IDialect的实现
	 */
	@Resource
	public IDialect mysqlDialect;

	@Override
	public IDialect getDialect() {
		return mysqlDialect;
	}

	/**
	 * 实现父类方法,springrain数据库的jdbcCall,对应 spring配置的 jdbcCall bean
	 */
	@Override
	public SimpleJdbcCall getJdbcCall() {
		return this.jdbcCall;
	}

	/**
	 * 实现父类方法,springrain数据库的jdbcCall,对应 spring配置的 jdbc bean
	 */
	@Override
	public NamedParameterJdbcTemplate getJdbc() {
		return jdbc;
	}

	/*
	 * 读写分离时,处理同步延迟问题
	 * 
	 * @Override public NamedParameterJdbcTemplate getJdbc() { try{
	 * TransactionInterceptor.currentTransactionStatus();
	 * }catch(NoTransactionException e){ return this.jdbc; } return getWriteJdbc();
	 * }
	 */

	/**
	 * 实现父类方法,返回记录日志的Entity接口实现,reutrn null 则代表不记录日志
	 */
	@Override
	public AuditLog getAuditLog() {
		return null;
	}

	/**
	 * 是否打印sql语句,默认false
	 * 
	 * @return
	 */
	@Override
	public boolean showsql() {
		return false;
	}

}

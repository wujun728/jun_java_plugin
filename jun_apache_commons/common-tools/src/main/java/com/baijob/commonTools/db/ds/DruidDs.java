package com.baijob.commonTools.db.ds;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.druid.pool.DruidDataSource;
import com.baijob.commonTools.FileUtil;
import com.baijob.commonTools.LangUtil;
import com.baijob.commonTools.Setting;
import com.baijob.commonTools.Exceptions.ConnException;
import com.baijob.commonTools.Exceptions.SettingException;
import com.baijob.commonTools.db.DbUtil;
import com.baijob.commonTools.db.DsSetting;
import com.baijob.commonTools.net.SSHUtil;
import com.baijob.commonTools.net.SocketUtil;

/**
 * 封装Druid数据源
 * 
 * @author Luxiaolei
 * 
 */
public class DruidDs {
	private static Logger logger = LoggerFactory.getLogger(DruidDs.class);
	
	/** 默认的Druid配置文件路径 */
	public final static String DEFAULT_DRUID_CONFIG_PATH = "config/druid.setting";

	/*--------------------------私有变量 start-------------------------------*/
	/** JDBC配置对象 */
	private static Setting dbSetting;
	private static Setting druidSetting;

	/** 数据源池 */
	private static Map<String, DruidDataSource> dsMap;
	/*--------------------------私有变量 end-------------------------------*/

	static {
		init(null, null);
	}

	/**
	 * 初始化数据库连接配置文件
	 * 
	 * @param druid_setting Druid配置文件
	 * @param db_setting 数据库配置文件
	 */
	synchronized public static void init(Setting druid_setting, Setting db_setting) {
		dsMap = new HashMap<String, DruidDataSource>();

		// 初始化Druid配置文件
		druidSetting = druid_setting;
		if (druidSetting == null) {
			try {
				druidSetting = new Setting(DEFAULT_DRUID_CONFIG_PATH, Setting.DEFAULT_CHARSET, true);
			}catch(Exception e) {
				logger.info("Druid setting file {} not found.", DEFAULT_DRUID_CONFIG_PATH);
			}
		}
		// 初始化数据库连接配置文件
		dbSetting = db_setting;
		if (dbSetting == null) {
			try {
				dbSetting = new Setting(DsSetting.DEFAULT_DB_CONFIG_PATH, Setting.DEFAULT_CHARSET, true);
			}catch(Exception e) {
				logger.info("No default DB config file {} found, custom to init it.", DsSetting.DEFAULT_DB_CONFIG_PATH);
			}
		}
	}

	/**
	 * 获得一个数据源
	 * 
	 * @param dsName 数据源名称，若null使用配置文件中的默认连接，此名称在配置文件中定义
	 * @param sshName 跳板机名称
	 * @throws ConnException
	 */
	synchronized public static DataSource getDataSource(String dsName, String sshName) throws ConnException {
		if(dbSetting == null) {
			throw new ConnException("No setting found, please init it!");
		}
		DsSetting dsSetting = null;
		try {
			dsSetting = new DsSetting(dsName, sshName, dbSetting);
		} catch (SettingException e) {
			throw new ConnException("Init datasource setting fail！", e);
		}

		// 如果已经存在已有数据源（连接池）直接返回
		DruidDataSource existedDataSource = dsMap.get(dsSetting.getName());
		if (existedDataSource != null) {
			return existedDataSource;
		}

		// 基本连接信息
		String remoteHost = dsSetting.getString(DsSetting.KEY_DS_HOST);
		int port = dsSetting.getInt(DsSetting.KEY_DS_PORT);
		String dbName = dsSetting.getString(DsSetting.KEY_DS_DB);
		// 验证连接信息的有效性
		if (LangUtil.isEmpty(remoteHost) || !SocketUtil.isValidPort(port) || LangUtil.isEmpty(dbName)) {
			throw new ConnException("Invalid connection info=>host:" + remoteHost + ", port:" + port + ", database:" + dbName + "】");
		}

		DruidDataSource dds = new DruidDataSource();
		if(druidSetting != null) {
			try {
				// 连接池参数注入
				druidSetting.settingToObject(dds);
			} catch (SettingException e) {
				throw new ConnException("Read Druid setting error!", e);
			}
		}
		// 基本连接信息
		dds.setName(dsSetting.getName()); // 数据源名称为连接名称
		dds.setDriverClassName(dsSetting.getJdbcDriver());
		dds.setUsername(dsSetting.getDsUser());
		dds.setPassword(dsSetting.getDsPass());
		if (dsSetting.isEnableSSH()) {
			port = SSHUtil.openAndBindPortToLocal(dsSetting.getSSHConnector(), remoteHost, port);
			remoteHost = SocketUtil.LOCAL_IP;
		}
		String jdbcUrl = DbUtil.buildJdbcUrl(dsSetting.getProtocol(), remoteHost, port, dbName, dsSetting.getJdbcUrlParam());
		dds.setUrl(jdbcUrl);
		logger.info("【{}】{}@{}", dsSetting.getName(), dds.getUsername(), jdbcUrl);

		// 添加到数据源池中，以备下次使用
		dsMap.put(dsSetting.getName(), dds);
		return dds;
	}

	/**
	 * 获得默认数据源（连接池），链接信息来自于配置文件
	 * 
	 * @return 数据源
	 * @throws ConnException
	 */
	synchronized public static DataSource getDataSource() throws ConnException {
		return getDataSource(null, null);
	}

	/**
	 * 获得一个数据库连接池中的连接
	 * 
	 * @param datasource 数据源名称，此名称在配置文件中定义
	 * @param sshName SSH连接名称
	 * @return 连接对象
	 * @throws SQLException
	 * @throws ConnException
	 */
	synchronized public static Connection getConnection(String datasource, String sshName) throws SQLException, ConnException {
		return getDataSource(datasource, sshName).getConnection();
	}

	/**
	 * 获得一个默认连接池中的连接（此默认连接取决于配置文件）
	 * 
	 * @return 连接对象
	 * @throws SQLException
	 * @throws ConnException
	 */
	synchronized public static Connection getConnection() throws SQLException, ConnException {
		return getConnection(null, null);
	}

	/**
	 * 关闭数据源
	 * 
	 * @param dsName 数据源名称
	 * @param sshName SSH名称
	 */
	synchronized public static void closeDs(String dsName, String sshName) {
		DsSetting dsSetting = null;
		try {
			dsSetting = new DsSetting(dsName, sshName, dbSetting);
		} catch (SettingException e) {
			logger.error("Error to init DsSetting.", e);
			return;
		}

		String name = dsSetting.getName();
		DruidDataSource dds = dsMap.get(name);
		if (dds != null) {
			FileUtil.close(dds);
			dsMap.remove(name);
		}
	}

	/**
	 * 关闭默认数据源
	 */
	synchronized public static void closeDs() {
		closeDs(null, null);
	}

	/**
	 * 关闭所有连接池
	 */
	synchronized public static void closeAll() {
		Collection<DruidDataSource> values = dsMap.values();
		for (DruidDataSource dds : values) {
			if (dds != null) {
				dds.close();
			}
		}
		dsMap.clear();
		SSHUtil.closeAll();
	}
}

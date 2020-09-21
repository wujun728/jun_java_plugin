package com.baijob.commonTools.db;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.baijob.commonTools.LangUtil;
import com.baijob.commonTools.Setting;
import com.baijob.commonTools.Exceptions.ConnException;
import com.baijob.commonTools.Exceptions.SettingException;
import com.baijob.commonTools.net.Connector;
import com.baijob.commonTools.net.SSHUtil;

/**
 * 数据库设定，与数据库配置文件对应
 * @author Luxiaolei
 *
 */
public class DsSetting {
	private static Logger logger = LoggerFactory.getLogger(DsSetting.class);
	
	/** 默认的数据库连接配置文件路径 */
	public final static String DEFAULT_DB_CONFIG_PATH = "config/db.setting";
	/** 默认的数据库连接驱动（MySQL） */
	public final static String DEFAULT_DRIVER = "com.mysql.jdbc.Driver";
	/** 默认的JDBC协议 */
	public final static String DEFAULT_PROTOCOL = "jdbc:mysql";

	// 变量名
	public final static String VAR_SSH = "${ssh}";
	public final static String VAR_DS = "${ds}";

	// DB KEYS
	public final static String DRIVER_KEY = "jdbc.driver";
	public final static String PROTOCOL_KEY = "jdbc.protocol";
	public final static String PARAM_KEY = "jdbc.url.param";
	public final static String DATASOURCE_KEY = "ds.default";
	public final static String SSHNAME_KEY = "ssh.default";

	// SSH KEYS
	public final static String KEY_SSH_HOST = "${ssh}.ssh.host";
	public final static String KEY_SSH_PORT = "${ssh}.ssh.port";
	public final static String KEY_SSH_USER = "${ssh}.ssh.user";
	public final static String KEY_SSH_PASS = "${ssh}.ssh.pass";

	// DATASOURCE KEYS
	public final static String KEY_DS_URL = "${ds}.ds.url";
	public final static String KEY_DS_HOST = "${ds}.ds.host";
	public final static String KEY_DS_PORT = "${ds}.ds.port";
	public final static String KEY_DS_DB     = "${ds}.ds.db";
	public final static String KEY_DS_USER = "${ds}.${ssh}.ds.user";
	public final static String KEY_DS_PASS = "${ds}.${ssh}.ds.pass";

	/** 数据源名称 */
	private String dsName;
	/** SSH名称 */
	private String ssh;
	/** 数据库连接配置文件 */
	private Setting dbSetting;

	/**
	 * 自定义数据源和SSH的构造
	 * @param dataSourceName 数据源名称
	 * @param sshTunnel SSH名称
	 * @param dbSetting 数据配置文件
	 * @throws ConnException 
	 */
	public DsSetting(String dataSourceName, String sshTunnel, Setting dbSetting) throws SettingException {
		this.dbSetting = dbSetting;
		
		this.dsName = (dataSourceName == null) ? dbSetting.getString(DATASOURCE_KEY) : dataSourceName;
		if (LangUtil.isEmpty(this.dsName)) {
			throw new SettingException("无法找到默认的连接配置项！");
		}
		
		this.ssh = (sshTunnel == null) ? dbSetting.getString(SSHNAME_KEY) : sshTunnel;
		if (LangUtil.isEmpty(this.ssh)) {
			logger.debug("Not use SSH tunnel.");
			this.ssh = SSHUtil.SSH_NONE;
		}
	}
	
	/**
	 * 使用数据库配置文件中的数据源和SSH的构造
	 * @param dbSetting 数据配置文件
	 */
	public DsSetting(Setting dbSetting) {
		this.dbSetting = dbSetting;
		
		this.dsName = dbSetting.getString(DATASOURCE_KEY);
		this.ssh =dbSetting.getString(SSHNAME_KEY);
	}
	

	/**
	 * 生成数据源相关参数的key
	 * 
	 * @param keyTemplate 模板
	 * @return key名称
	 */
	public String genKey(String keyTemplate) {
		String replacedKey = keyTemplate.replace(DsSetting.VAR_DS, dsName).replace(DsSetting.VAR_SSH, ssh);
		logger.debug("Generate datasource setting key=>{}", replacedKey);
		return replacedKey;
	}

	/**
	 * String类型值
	 * 
	 * @param key 键
	 * @return 对应值
	 */
	public String getString(String key) {
		return dbSetting.getString(genKey(key));
	}

	/**
	 * Int类型值
	 * 
	 * @param key 键
	 * @return 对应值
	 */
	public int getInt(String key) {
		return dbSetting.getInt(genKey(key));
	}

	/**
	 * 获得默认数据源名称
	 * @return 数据源名称
	 */
	public String getName() {
		return getDsName(dsName, ssh);
	}

	/**
	 * 是否开启了SSH功能
	 * @return 是否开启SSH
	 */
	public boolean isEnableSSH() {
		return !SSHUtil.SSH_NONE.equals(ssh);
	}

	/**
	 * 获得SSH主机名
	 * @return SSH主机名
	 */
	public String getSshHost() {
		return getString(KEY_SSH_HOST);
	}

	/**
	 * 获得SSH端口号
	 * @return SSH端口号
	 */
	public int getSshPort() {
		return getInt(KEY_SSH_PORT);
	}

	/**
	 * 获得SSH连接用户名
	 * @return SSH用户名
	 */
	public String getSshUser() {
		return getString(KEY_SSH_USER);
	}

	/**
	 * 获得SSH连接密码
	 * @return SSH密码
	 */
	public String getSshPass() {
		return getString(KEY_SSH_PASS);
	}

	/**
	 * 获得数据库主机名
	 * @return 主机名
	 */
	public String getDsHost() {
		return getString(KEY_DS_HOST);
	}

	/**
	 * 获得数据库端口号
	 * @return 端口
	 */
	public int getDsPort() {
		return getInt(KEY_DS_PORT);
	}

	/**
	 * 获得库名称
	 * @return 数据库名称
	 */
	public String getDsDb() {
		return getString(KEY_DS_DB);
	}

	/**
	 * 获得数据库访问用户名
	 * @return 用户名
	 */
	public String getDsUser() {
		return getString(KEY_DS_USER);
	}

	/**
	 * 获得数据库访问密码
	 * @return 密码
	 */
	public String getDsPass() {
		return getString(KEY_DS_PASS);
	}

	/**
	 * 获得JDBC驱动
	 * @return JDBC驱动类字符串
	 */
	public String getJdbcDriver() {
		String jdbcDriver = dbSetting.getString(DRIVER_KEY);
		return LangUtil.isEmpty(jdbcDriver) ? DEFAULT_DRIVER : jdbcDriver;
	}
	
	/**
	 * 获得协议
	 * @return 协议
	 */
	public String getProtocol() {
		String protocol = dbSetting.getString(PROTOCOL_KEY);
		return LangUtil.isEmpty(protocol) ? DEFAULT_PROTOCOL : protocol;
	}
	
	/**
	 * 获得JDBC连接字符串参数
	 * @return 无参数返回 "" ，否则返回带 ? 的参数
	 */
	public String getJdbcUrlParam(){
		return dbSetting.getString(DsSetting.PARAM_KEY);
	}
	
	/**
	 * 获得SSH连接信息
	 * @return 连接对象
	 */
	public Connector getSSHConnector() {
		return new Connector(getSshHost(), getSshPort(), getSshUser(), getSshPass());
	}
	
	/**
	 * 获得数据源和SSH节后的拼接名称
	 * @param dsName 数据源名称
	 * @param sshName ssh名称
	 * @return 拼接后名称
	 */
	public static String getDsName(String dsName, String sshName){
		return dsName + "." + sshName;
	}
}

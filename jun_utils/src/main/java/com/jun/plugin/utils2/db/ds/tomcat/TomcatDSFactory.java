package com.jun.plugin.utils2.db.ds.tomcat;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.apache.tomcat.jdbc.pool.PoolProperties;

import com.jun.plugin.utils2.db.DbRuntimeException;
import com.jun.plugin.utils2.db.ds.DSFactory;
import com.jun.plugin.utils2.setting.Setting;
import com.jun.plugin.utils2.util.CollectionUtil;
import com.jun.plugin.utils2.util.StrUtil;

/**
 * Tomcat-Jdbc-Pool数据源工厂类
 * @author Looly
 *
 */
public class TomcatDSFactory extends DSFactory {
	
	private Setting setting;
	/** 数据源池 */
	private Map<String, DataSource> dsMap;
	
	public TomcatDSFactory() {
		this(null);
	}
	
	public TomcatDSFactory(Setting setting) {
		super("Tomcat-Jdbc-Pool");
		checkCPExist(DataSource.class);
		if(null == setting){
			setting = new Setting(DEFAULT_DB_SETTING_PATH, true);
		}
		this.setting = setting;
		this.dsMap = new ConcurrentHashMap<>();
	}

	@Override
	public DataSource getDataSource(String group) {
		if (group == null) {
			group = StrUtil.EMPTY;
		}
		
		// 如果已经存在已有数据源（连接池）直接返回
		final DataSource existedDataSource = dsMap.get(group);
		if (existedDataSource != null) {
			return existedDataSource;
		}

		final DataSource ds = createDataSource(group);
		// 添加到数据源池中，以备下次使用
		dsMap.put(group, ds);
		return ds;
	}

	@Override
	public void close(String group) {
		if (group == null) {
			group = StrUtil.EMPTY;
		}

		DataSource ds = dsMap.get(group);
		if (ds != null) {
			ds.close();
			dsMap.remove(group);
		}
	}

	@Override
	public void destroy() {
		if(CollectionUtil.isNotEmpty(dsMap)){
			Collection<DataSource> values = dsMap.values();
			for (DataSource ds : values) {
				if(null != ds){
					ds.close();
				}
			}
			dsMap.clear();
			dsMap = null;
		}
	}

	/**
	 * 创建数据源
	 * @param group 分组
	 * @return Tomcat数据源 {@link DataSource}
	 */
	private DataSource createDataSource(String group){
		if (group == null) {
			group = StrUtil.EMPTY;
		}
		
		Setting config = setting.getSetting(group);
		if(null == config || config.isEmpty()){
			throw new DbRuntimeException("No Tomcat jdbc pool config for group: [{}]", group);
		}
		PoolProperties poolProps = new PoolProperties();
		config.toBean(poolProps);
		final DataSource ds = new DataSource();
		ds.setPoolProperties(poolProps);
		
		return ds;
	}
}

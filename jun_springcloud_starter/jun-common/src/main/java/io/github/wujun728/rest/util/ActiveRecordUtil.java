package io.github.wujun728.rest.util;

import cn.hutool.core.util.StrUtil;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.activerecord.DbKit;
import com.jfinal.plugin.druid.DruidPlugin;

import javax.sql.DataSource;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ActiveRecordUtil {

	static volatile Map<String,ActiveRecordPlugin> activeRecordPluginMap = new ConcurrentHashMap<>();
	
	public static ActiveRecordPlugin initActiveRecordPlugin(String configName,String jdbcUrl,String user,String password) {
		configName = StrUtil.isEmpty(configName)?DbKit.MAIN_CONFIG_NAME:configName;
		if(DbKit.getConfig(configName) == null){
			DruidPlugin druidPlugin = new DruidPlugin(jdbcUrl, user, password);
			ActiveRecordPlugin arp = new ActiveRecordPlugin(configName,druidPlugin);
			arp.setDevMode(true);
			arp.setShowSql(true);
			// 添加 sql 模板文件，实际开发时将 sql 文件放在 src/main/resources 下
			//arp.addSqlTemplate("com/jfinal/plugin/activerecord/test.sql");
			// 所有映射在生成的 _MappingKit.java 中自动化搞定
			//_MappingKit.mapping(arp);
			// 先启动 druidPlugin，后启动 arp
			druidPlugin.start();
			arp.start();
			activeRecordPluginMap.put(configName,arp);
			return arp;
		}else{
			return activeRecordPluginMap.get(configName);
		}
	}
	public static ActiveRecordPlugin initActiveRecordPlugin(String configName,DataSource ds) {
		configName = StrUtil.isEmpty(configName)?DbKit.MAIN_CONFIG_NAME:configName;
		if(DbKit.getConfig(configName) == null){
			ActiveRecordPlugin arp = new ActiveRecordPlugin(configName, ds);
			arp.setDevMode(true);
			arp.setShowSql(true);
			// 添加 sql 模板文件，实际开发时将 sql 文件放在 src/main/resources 下
			//arp.addSqlTemplate("com/jfinal/plugin/activerecord/test.sql");
			// 所有映射在生成的 _MappingKit.java 中自动化搞定
			//_MappingKit.mapping(arp);
			// 先启动 druidPlugin，后启动 arp
			arp.start();
			activeRecordPluginMap.put(configName,arp);
			return arp;
		}else{
			return activeRecordPluginMap.get(configName);
		}
	}

}



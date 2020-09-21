package cn.jiangzeyin.database.config;


import cn.jiangzeyin.system.SystemDbLog;
import cn.jiangzeyin.system.SystemKey;
import cn.jiangzeyin.util.Assert;
import cn.jiangzeyin.util.PropertiesParser;
import cn.jiangzeyin.util.ResourceUtil;
import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.mysql.jdbc.StringUtils;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.*;

/**
 * 数据源配置信息
 * Created by jiangzeyin on 2017/1/6.
 */
public final class DataSourceConfig {
    private DataSourceConfig() {

    }

    private static PropertiesParser systemPropertiesParser;

    public static void init(String propertyPath) throws Exception {
        if (StringUtils.isNullOrEmpty(propertyPath))
            throw new IllegalArgumentException("propertyPath is null ");
        InputStream inputStream = ResourceUtil.getResource(propertyPath);
        systemPropertiesParser = new PropertiesParser(inputStream);
        String[] sourceTags = systemPropertiesParser.getStringArrayProperty(ConfigProperties.PROP_SOURCE_TAG);
        Assert.notNull(sourceTags, "sourceTag is blank");
        if (sourceTags.length < 1) {
            throw new IllegalArgumentException("sourceTag is blank");
        }
        String[] configPaths = systemPropertiesParser.getStringArrayProperty(ConfigProperties.PROP_CONFIG_PATH);
        Assert.notNull(configPaths, "configPath is blank");
        if (configPaths.length < 1) {
            throw new IllegalArgumentException("configPath is blank");
        }
        dataSource(sourceTags, configPaths);
        //
        ModifyUser.initModify(systemPropertiesParser.getPropertyGroup(ConfigProperties.PROP_LAST_MODIFY));
        //
        ModifyUser.initCreate(systemPropertiesParser.getPropertyGroup(ConfigProperties.PROP_CREATE));
        //
        SystemColumn.init(systemPropertiesParser.getPropertyGroup(ConfigProperties.PROP_SYSTEM_COLUMN));
    }

    private static void dataSource(String[] sourceTags, String[] configPaths) throws Exception {
        SystemDbLog.getInstance().info("初始化连接数据库");
        if (configPaths.length == 1) {
            Map<String, DataSource> concurrentHashMap = initConfigPath(sourceTags, configPaths[0]);
            DatabaseContextHolder.init(concurrentHashMap);
        } else {
            List<Map<String, DataSource>> mapList = new ArrayList<>();
            List<String> configList = new ArrayList<>();
            for (String configPath : configPaths) {
                Map<String, DataSource> map = initConfigPath(sourceTags, configPath);
                if (map == null || map.size() < 1)
                    continue;
                mapList.add(map);
                configList.add(configPath);
            }
            DatabaseContextHolder.init(mapList.toArray(new Map[0]), configList.toArray(new String[0]));
        }
    }

    private static Map<String, DataSource> initConfigPath(String[] sourceTags, String configPath) throws Exception {
        SystemDbLog.getInstance().info("load " + configPath);
        PropertiesParser propertiesParser = new PropertiesParser(ResourceUtil.getResource(configPath));
        Map<String, DataSource> hashMap = new HashMap<>();
        String systemKey = systemPropertiesParser.getStringProperty(ConfigProperties.PROP_SYSTEM_KEY);
        SystemKey systemKey1 = null;
        if (systemKey != null) {
            systemKey1 = new SystemKey(systemKey);
        }
        String[] systemKeyColumn = systemPropertiesParser.getStringArrayProperty(ConfigProperties.PROP_SYSTEM_KEY_COLUMN, null);
        if (systemKeyColumn != null && systemKey1 == null) {
            SystemDbLog.getInstance().warn(" use systemKeyColumn moust systemKey");
        }
        for (String tag : sourceTags) {
            Properties properties_tag = propertiesParser.getPropertyGroup(tag, true);
            if (properties_tag.isEmpty()) {
                SystemDbLog.getInstance().warn(tag + "is blank");
                continue;
            }
            String url = properties_tag.getProperty(DruidDataSourceFactory.PROP_URL);
            if (systemKey1 != null && arrayContainValue(systemKeyColumn, DruidDataSourceFactory.PROP_URL))
                url = systemKey1.decrypt(url);
            String ip = url.substring(url.indexOf("://") + 3, url.lastIndexOf("/"));
            String[] ipInfo = ip.split(":");
            int port = Integer.parseInt(ipInfo[1]);
            boolean flag = isConnect(ipInfo[0], port);
            if (!flag) {
                SystemDbLog.getInstance().warn(ip + "not Connect continue");
                continue;
            }
            properties_tag.setProperty(DruidDataSourceFactory.PROP_URL, url);
            String userName = properties_tag.getProperty(DruidDataSourceFactory.PROP_USERNAME);
            if (systemKey1 != null && arrayContainValue(systemKeyColumn, DruidDataSourceFactory.PROP_USERNAME)) {
                userName = systemKey1.decrypt(userName);
                properties_tag.setProperty(DruidDataSourceFactory.PROP_USERNAME, userName);
            }
            String pwd = properties_tag.getProperty(DruidDataSourceFactory.PROP_PASSWORD);
            if (systemKey1 != null && arrayContainValue(systemKeyColumn, DruidDataSourceFactory.PROP_PASSWORD)) {
                pwd = systemKey1.decrypt(pwd);
                properties_tag.setProperty(DruidDataSourceFactory.PROP_PASSWORD, pwd);
            }
            DataSource dataSource = DruidDataSourceFactory.createDataSource(properties_tag);
            hashMap.put(tag, dataSource);
        }
        return hashMap;
    }

    private static boolean arrayContainValue(String[] array, String value) {
        if (array == null)
            return false;
        for (String item : array) {
            if (value.equals(item))
                return true;
        }
        return false;
    }

    private static boolean isConnect(String host, int port) {
        Socket socket = new Socket();
        try {
            socket.connect(new InetSocketAddress(host, port));
        } catch (IOException e) {
            return false;
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return true;
    }
}

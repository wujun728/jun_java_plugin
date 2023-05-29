package com.jun.plugin.resources.db;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;

import com.jun.plugin.resources.Constants;
import com.jun.plugin.resources.KeyConstants;
import com.jun.plugin.resources.config.Config;
import com.jun.plugin.resources.config.GlobalConfig;
import com.jun.plugin.resources.db.datasource.AbstractDataSource;
import com.jun.plugin.resources.utils.StringUtils;

import javax.sql.DataSource;
import java.lang.reflect.Constructor;

/**
 * Created By Hong on 2018/7/30
 **/
public final class JdbcUtils {

    private static final Logger LOG = LoggerFactory.getLogger(JdbcUtils.class);

    /**
     * 配置属性
     **/
    private static Config CONFIG = GlobalConfig.get();

    /**
     * 获取数据连接池
     *
     * @return DataSource
     */
    private static DataSource getDataSource() {
        DataSource dataSource = null;
        try {
            String clazzName = CONFIG.getValue(KeyConstants.DB_DATA_SOURCE);
            if (StringUtils.isEmpty(clazzName)) {
                clazzName = Constants.DB_DEFAULT_DATA_SOURCE;
            }
            Class<?> clazz = Class.forName(clazzName);
            if (clazz != null) {
                //实例化AbstractDataSource
                Constructor<AbstractDataSource> constructor = (Constructor<AbstractDataSource>) clazz
                        .getConstructor(String.class, String.class, String.class, String.class);
                AbstractDataSource abstractDataSource = constructor.newInstance(CONFIG.getValue(KeyConstants.DB_DRIVER_CLASS_NAME), CONFIG.getValue(KeyConstants.DB_URL),
                        CONFIG.getValue(KeyConstants.DB_USERNAME), CONFIG.getValue(KeyConstants.DB_PASSWORD));
                if (abstractDataSource != null) {
                    //获取DataSource
                    dataSource = abstractDataSource.getDataSource();
                }
            }
        } catch (Exception e) {
            if (LOG.isErrorEnabled()) {
                LOG.error("DataSource access failed.", e);
            }
            throw new IllegalArgumentException("DataSource access failed.");
        }
        return dataSource;
    }

    /**
     * 获取JDBC模板
     *
     * @return JdbcTemplate
     */
    public static JdbcTemplate getJdbcTemplate() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        jdbcTemplate.setDataSource(getDataSource());
        return jdbcTemplate;
    }

}

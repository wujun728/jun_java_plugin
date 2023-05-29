package com.jun.plugin.resources.db;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created By Hong on 2018/7/31
 **/
public final class DbUtils {

    private static final Logger LOG = LoggerFactory.getLogger(DbUtils.class);

    private static final JdbcTemplate JDBC_TEMPLATE = JdbcUtils.getJdbcTemplate();

    /**
     * 查询返回集合
     *
     * @param sql sql语句
     * @return 配置集合
     */
    public static List<Map<String, Object>> select(String sql) {
        try {
            return JDBC_TEMPLATE.queryForList(sql);
        } catch (Exception e) {
            if (LOG.isErrorEnabled()) {
                LOG.error("Query Fail.", e);
            }
            return new ArrayList<>();
        }
    }
}

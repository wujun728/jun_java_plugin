package cn.kiiwii.framework.dubbo.provider.dao.impl;

import cn.kiiwii.framework.dubbo.provider.dao.ITestDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * Created by zhong on 2016/11/22.
 */
@Repository(value = "testDAO")
public class TestDAOImpl implements ITestDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Object test() {
        return jdbcTemplate.queryForList("select * from test");
    }
}

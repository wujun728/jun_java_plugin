package cn.kiiwii.framework.dao.impl;

import cn.kiiwii.framework.druid.DynamicDataSource.TargetDataSource;
import cn.kiiwii.framework.dao.ITestDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by zhong on 2016/9/5.
 */
@Repository("testDAO")
public class TestDAOImpl implements ITestDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void testMaster() {
        List<Map<String,Object>> list = this.jdbcTemplate.queryForList("select * from test");
        System.out.println(list);
    }
    @TargetDataSource(name="slave1")
    @Override
    public void testSlave1() {
        List<Map<String,Object>> list = this.jdbcTemplate.queryForList("select * from test");
        System.out.println(list);
    }

    @TargetDataSource(name="slave2")
    @Override
    public void testSlave2() {
        List<Map<String,Object>> list = this.jdbcTemplate.queryForList("select * from test");
        System.out.println(list);
    }
}

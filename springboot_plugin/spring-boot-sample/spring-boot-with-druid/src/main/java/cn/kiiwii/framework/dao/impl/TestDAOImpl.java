package cn.kiiwii.framework.dao.impl;

import cn.kiiwii.framework.dao.ITestDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

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
    public void test() {
        List<Map<String,Object>> list = this.jdbcTemplate.queryForList("select * from test");
        System.out.println(list);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void save() {
        this.jdbcTemplate.execute("INSERT INTO test (`test_name`,`test_num`) VALUES ('zhong','27')");
    }
}

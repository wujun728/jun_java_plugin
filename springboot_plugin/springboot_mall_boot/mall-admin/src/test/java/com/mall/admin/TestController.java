package com.mall.admin;

import com.mall.admin.bean.SpringBeanManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author Wujun
 * @version 1.0
 * @create_at 2019/1/19 19:17
 */
public class TestController {

    private JdbcTemplate jdbcTemplate;


    @GetMapping("lock")
    @Transactional
    public void lock() {
        DataSourceTransactionManager transactionManager = SpringBeanManager.getBean(DataSourceTransactionManager.class);
        TransactionStatus status = null;
        try {
            DefaultTransactionDefinition def = new DefaultTransactionDefinition();
            def.setPropagationBehavior(TransactionDefinition.ISOLATION_REPEATABLE_READ); // 事物隔离级别，开启新事务，这样会比较安全些
            jdbcTemplate.update("update system_role set role_name = 'test' where id = 1");

            Thread.sleep(60000);

            status = transactionManager.getTransaction(def); // 获得事务状态
            transactionManager.commit(status);
        } catch (Exception e) {
            e.printStackTrace();
            transactionManager.rollback(status);
        }
    }
}

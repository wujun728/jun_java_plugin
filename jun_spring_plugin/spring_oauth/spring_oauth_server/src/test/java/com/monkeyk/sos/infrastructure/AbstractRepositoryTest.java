package com.monkeyk.sos.infrastructure;

import com.monkeyk.sos.ContextTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * @author Shengzhao Li
 */
public abstract class AbstractRepositoryTest extends ContextTest {


    @Autowired
    private JdbcTemplate jdbcTemplate;


    public JdbcTemplate jdbcTemplate() {
        return jdbcTemplate;
    }


}
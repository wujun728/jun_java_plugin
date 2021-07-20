package com.jun.plugin.oauth.server.infrastructure;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import com.jun.plugin.oauth.server.ContextTest;

/**
 * @author Wujun
 */
public abstract class AbstractRepositoryTest extends ContextTest {


    @Autowired
    private JdbcTemplate jdbcTemplate;


    public JdbcTemplate jdbcTemplate() {
        return jdbcTemplate;
    }


}
package com.lyx.test;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;

public class PeopleDao0 {

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public void setDataSource(DataSource dataSource) {
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(
                dataSource);
    }

    public int[] batchUpdate(final List<People> peoples) {
        SqlParameterSource[] batch = SqlParameterSourceUtils
                .createBatch(peoples.toArray());
        int[] updateCounts = this.namedParameterJdbcTemplate
                .batchUpdate(
                        "update people set first_name = :firstName, last_name = :lastName where id = :id",
                        batch);
        return updateCounts;
    }

}

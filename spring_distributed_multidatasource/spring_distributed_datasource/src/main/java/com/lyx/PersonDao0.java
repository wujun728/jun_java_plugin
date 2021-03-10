package com.lyx;

import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.stereotype.Repository;

@Repository
public class PersonDao0 {

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(
                dataSource);
    }

    /**
     * @param firstName
     * @return
     */
    public int countOfActorsByFirstName(String firstName) {
        String sql = "select count(*) from people where first_name = :first_name";
        SqlParameterSource namedParameters = new MapSqlParameterSource(
                "first_name", firstName);
        return this.namedParameterJdbcTemplate.queryForObject(sql,
                namedParameters, Integer.class);
    }

    /**
     * namedParameterJdbcTemplate 用的批量操作
     *
     * @param peoples
     * @return
     */
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

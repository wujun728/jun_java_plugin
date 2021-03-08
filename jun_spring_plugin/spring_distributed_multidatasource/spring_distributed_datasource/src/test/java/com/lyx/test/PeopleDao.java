package com.lyx.test;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ParameterizedPreparedStatementSetter;

public class PeopleDao {
    private JdbcTemplate jdbcTemplate;

    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    /**
     * this.jdbcTemplate.batchUpdate
     *
     * @param peoples
     * @return
     */
    public int[] batchUpdate(final List<People> peoples) {
        int[] updateCounts = this.jdbcTemplate.batchUpdate(
                "update people set first_name = ?, "
                        + "last_name = ? where id = ?",
                new BatchPreparedStatementSetter() {
                    public void setValues(PreparedStatement ps, int i)
                            throws SQLException {
                        ps.setString(1, peoples.get(i).getFirstName());
                        ps.setString(2, peoples.get(i).getLastName());
                        ps.setLong(3, peoples.get(i).getId());
                    }

                    public int getBatchSize() {
                        return peoples.size();
                    }
                });
        return updateCounts;
    }

    /**
     * @param peoples
     * @return
     */
    public int[][] batchUpdate(final Collection<People> peoples) {
        int[][] updateCounts = this.jdbcTemplate.batchUpdate(
                "update people set first_name = ?, last_name = ? where id = ?",
                peoples, 100, // int batchSize
                new ParameterizedPreparedStatementSetter<People>() {
                    public void setValues(PreparedStatement ps, People argument)
                            throws SQLException {
                        ps.setString(1, argument.getFirstName());
                        ps.setString(2, argument.getLastName());
                        ps.setLong(3, argument.getId());
                    }
                });
        return updateCounts;
    }

}

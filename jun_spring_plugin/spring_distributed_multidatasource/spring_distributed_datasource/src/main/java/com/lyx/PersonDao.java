package com.lyx;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class PersonDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    /**
     * 查询记录的个数 jdbcTemplate.queryForObject
     *
     * @return
     */
    public long count() {
        long rowCount = this.jdbcTemplate.queryForObject(
                "select count(*) from people", Long.class);
        return rowCount;
    }

    /**
     * 查询同名的人的个数
     *
     * @param name
     * @return
     */
    public long countPeopleByName(String name) {
        long countOfActorsNamedJoe = this.jdbcTemplate.queryForObject(
                "select count(*) from people where first_name = ?", Long.class,
                name);
        return countOfActorsNamedJoe;
    }

    /**
     * 根据Id查询姓名
     *
     * @param id
     * @return
     */
    public String queryNameById(int id) {
        String lastName = this.jdbcTemplate.queryForObject(
                "select last_name from t_actor where person_id = ?",
                new Object[]{id}, String.class);
        return lastName;
    }

    /**
     * 根据Id查找实体
     *
     * @param id
     * @return
     */
    public People findPeopleById(int id) {
        People people = this.jdbcTemplate.queryForObject(
                "select first_name, last_name from people where person_id = ?",
                new Object[]{id}, new RowMapper<People>() {
                    public People mapRow(ResultSet rs, int rowNum)
                            throws SQLException {
                        People p = new People();
                        p.setFirstName(rs.getString("first_name"));
                        p.setLastName(rs.getString("last_name"));
                        return p;
                    }
                });

        return people;
    }

    /**
     * 分页查询people jdbcTemplate.query
     *
     * @param pageNo
     * @param pageSize
     * @return
     */
    public List<People> findPeopleList(int pageNo, int pageSize) {
        int start = (pageNo - 1) * pageSize;
        List<People> peoples = this.jdbcTemplate.query(
                "select first_name, last_name from people limit ?,?",
                new Object[]{start, pageSize}, new RowMapper<People>() {
                    public People mapRow(ResultSet rs, int rowNum)
                            throws SQLException {
                        People p = new People();
                        p.setFirstName(rs.getString("first_name"));
                        p.setLastName(rs.getString("last_name"));
                        return p;
                    }
                });

        return peoples;
    }

    /**
     * 分页查询
     *
     * @param pageNo
     * @param pageSize
     * @return
     */
    public List<People> findPeopleList0(int pageNo, int pageSize) {
        int start = (pageNo - 1) * pageSize;
        List<People> peoples = this.jdbcTemplate.query(
                "select first_name, last_name from people limit ?,?",
                new Object[]{start, pageSize}, new PeopleRowMapper());

        return peoples;
    }

    /**
     * 实现RowMapper
     *
     * @author Wujun
     */
    private static class PeopleRowMapper implements RowMapper<People> {
        public People mapRow(ResultSet rs, int rowNum) throws SQLException {
            // TODO Auto-generated method stub
            People p = new People();
            p.setFirstName(rs.getString("first_name"));
            p.setLastName(rs.getString("last_name"));
            return p;
        }
    }

    /**
     * 插入实体 jdbcTemplate.update
     *
     * @param people
     */
    public void addPeople(People people) {
        this.jdbcTemplate.update(
                "insert into people (first_name, last_name) values (?, ?)",
                people.getFirstName(), people.getLastName());

    }

    /**
     * 更新实体 jdbcTemplate.update
     *
     * @param id
     */
    public void updatePeopleName(int id, String name) {
        this.jdbcTemplate
                .update("update people set last_name = ? where person_id = ?",
                        name, id);
    }

    /**
     * 删除实体 jdbcTemplate.update
     *
     * @param id
     */
    public void deletePeople(int id) {
        this.jdbcTemplate.update("delete from people where person_id = ?", id);
    }

    /**
     * jdbcTemplate.queryForList
     *
     * @return
     */
    public List<Map<String, Object>> getList() {
        return this.jdbcTemplate.queryForList("select * from people");
    }

    /**
     * @param id
     * @return
     */
    public Map<String, Object> getUserConvertToMap(int id) {
        return this.jdbcTemplate.queryForMap("select * from people where id= ");
    }

    /**
     * 返回插入实体的key columnNames an array of column names indicating the columns
     * that should be returned from the inserted row or rows
     *
     * @param people
     * @return
     */
    public int addPeople1(final People people) {
        final String INSERT_SQL = "insert into people (first_name , last_name ) values(?,?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        this.jdbcTemplate.update(new PreparedStatementCreator() {
            public PreparedStatement createPreparedStatement(
                    Connection connection) throws SQLException {
                PreparedStatement ps = connection.prepareStatement(INSERT_SQL,
                        new String[]{"id"});
                ps.setString(1, people.getFirstName());
                ps.setString(2, people.getLastName());
                return ps;
            }
        }, keyHolder);

        return (Integer) keyHolder.getKey();
    }

    /**
     * 执行sql语句 jdbcTemplate.execute
     *
     * @param sql create table mytable (id integer, name varchar(100))
     */
    public void execute(String sql) {
        this.jdbcTemplate.execute(sql);
    }
}

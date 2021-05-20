package messfairy.dao;

import messfairy.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class UsersDao extends AbstractDao<User>{

    @Override
    protected User map(ResultSet rs) throws SQLException {
        User user = new User();
        user.setId(Integer.valueOf(rs.getInt("id")));
        user.setName(rs.getString("name"));
        user.setPassword(rs.getString("password"));
        user.setCreateDate(rs.getDate("create_date"));
        return user;
    }

    @Autowired
    public void setDataSource(DataSource dataSource){
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        this.template = new JdbcTemplate(dataSource);
    }
    public void create(User user) {
        String insertSql = "INSERT INTO USERS(name,  first_name,  last_name,  password, email, create_date) VALUES (?, ?, ?, ?, ?, ?)";
        Object[] params = { user.getName(), user.getFirstName(), user.getLastName(), user.getPassword(), user.getEmail(), new Date()};
        int[] types = { Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.TIMESTAMP };
        int row = this.template.update(insertSql, params, types);
        System.out.println(row + " row inserted.");
    }
    public User byId(int id) throws DataAccessException {
        String sql = "select id, name, password, create_date, email from USERS where id = :id";
        SqlParameterSource namedParameters = new MapSqlParameterSource("id", Integer.valueOf(id));
        return this.jdbcTemplate.queryForObject(sql, namedParameters, mapper);
    }
    public User byName(final String name) throws DataAccessException{
        String sql = "select id, name, password, create_date, email from USERS where name = :name";
        SqlParameterSource namedParameters = new MapSqlParameterSource("name", name);
        return (User)this.jdbcTemplate.queryForObject(sql, namedParameters, mapper);
    }
    public User byLogin(final User user)  throws DataAccessException{
        String sql = "select id, name, password, create_date from USERS where name = :name and password = :password";
        Map<String, String> paramsMap = new HashMap<String, String>();
        paramsMap.put("name", user.getName());
        paramsMap.put("password", user.getPassword());
        SqlParameterSource namedParameters = new MapSqlParameterSource(paramsMap);
        return (User)this.jdbcTemplate.queryForObject(sql, namedParameters, mapper);
    }
    public List<User> queryAll() throws DataAccessException{
        String sql = "select id, name, password, create_date, email from USERS";
        List<User> users = this.jdbcTemplate.query(sql, mapper);
        return users;
    }
}

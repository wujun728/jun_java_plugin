package messfairy.dao;

import messfairy.entity.Comment;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class CommentsDao extends AbstractDao<Comment>{
    @Override
    protected Comment map(final ResultSet rs) throws SQLException {
        Comment comment = new Comment();
        comment.setId(Integer.valueOf(rs.getInt("id")));
        comment.setPid(rs.getInt("pid"));
        comment.setContent(rs.getString("content"));
        comment.setCreateDate(rs.getDate("create_date"));
        return comment;
    }
    public void create(final Comment comment) {
        String insertSql = "INSERT INTO COMMENTS(name,  first_name,  last_name,  password, email, create_date) VALUES (?, ?, ?, ?, ?, ?)";
        Object[] params = { comment.getContent(), new Date()};
        int[] types = { Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.TIMESTAMP };
        int row = this.template.update(insertSql, params, types);
        System.out.println(row + " row inserted.");
    }

    public Comment byId(int id) throws DataAccessException {
        String sql = "select id, pid, content, create_date from COMMENTS where id=:id";
        SqlParameterSource namedParameters = new MapSqlParameterSource("id", Integer.valueOf(id));
        return this.jdbcTemplate.queryForObject(sql, namedParameters, mapper());
    }

    public Comment byName(final String name) throws DataAccessException{
        String sql = "select id, name, password, create_date, email from USERS where name = :name";
        SqlParameterSource namedParameters = new MapSqlParameterSource("name", name);
        return this.jdbcTemplate.queryForObject(sql, namedParameters, mapper());
    }

    public Comment byLogin(final Comment comment)  throws DataAccessException{
        String sql = "select id, name, password, create_date from USERS where name = :name and content = :content";
        Map<String, String> paramsMap = new HashMap<String, String>();
        paramsMap.put("name", comment.getContent());
        paramsMap.put("content", comment.getContent());
        SqlParameterSource namedParameters = new MapSqlParameterSource(paramsMap);
        return this.jdbcTemplate.queryForObject(sql, namedParameters, mapper);
    }

    public List<Comment> queryAll() throws DataAccessException{
        String sql = "select id, name, password, create_date, email from USERS";
        List<Comment> comments = this.jdbcTemplate.query(sql, mapper);
        return comments;
    }
}

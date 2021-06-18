package messfairy.dao;

import messfairy.entity.Post;
import messfairy.entity.User;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Date;
import java.util.List;

@Repository
public class PostsDao extends AbstractDao<Post>{

    public void create(final Post post) throws DataAccessException {
        String insertSql = "INSERT INTO POSTS(title,  content, user_id, create_date) VALUES (?, ?, ?, ?)";
        Object[] params = {post.getTitle(), post.getContent(), post.getUserId(), new Date()};
        int[] types = {Types.VARCHAR, Types.VARCHAR, Types.INTEGER, Types.TIMESTAMP};
        int row = this.template.update(insertSql, params, types);
        System.out.println(row + " row inserted.");
    }

    public Post byId(final int id) throws DataAccessException{
        String sql = "select posts.id, posts.user_id, users.name, posts.title, posts.content, posts.create_date from posts join users on posts.user_id=users.id where posts.id=:id";
//        String sql = "select id, user_id, title, content, create_date from POSTS where id = :id";
        SqlParameterSource namedParameters = new MapSqlParameterSource("id", Integer.valueOf(id));
        Post post = this.jdbcTemplate.queryForObject(sql, namedParameters, mapper);
        return post;
    }

    public List<Post> all() throws DataAccessException{
//        List<Post> posts = jdbcTemplate.query("select id, user_id, title, content from posts", BeanPropertyRowMapper.newInstance(Post.class));
        String sql = "select posts.id, posts.user_id, users.name, posts.title, posts.content, posts.create_date from posts join users on posts.user_id=users.id";
        List<Post> posts = this.jdbcTemplate.query(sql, mapper);
        return posts;
    }

    @Override
    protected Post map(final ResultSet rs) throws SQLException {
        Post post = new Post();
        post.setId(rs.getInt("posts.id"));
        post.setUserId(rs.getInt("posts.user_id"));
        post.setCreateDate(rs.getDate("posts.create_date"));
        post.setTitle(rs.getString("posts.title"));
        post.setContent(rs.getString("posts.content"));
        User author = new User();
        author.setName(rs.getString("users.name"));
        author.setId(rs.getInt("posts.user_id"));
        post.setAuthor(author);
        return post;
    }
}

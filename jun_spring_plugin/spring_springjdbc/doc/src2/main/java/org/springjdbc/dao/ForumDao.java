package org.springjdbc.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.CallableStatementCreator;
import org.springframework.jdbc.core.CallableStatementCreatorFactory;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springjdbc.domain.Forum;

@Repository
public class ForumDao extends BaseDao {

	private static final Logger logger = Logger.getLogger(ForumDao.class);
	
	public void addForum(final Forum forum)
	{
		final String sql = "insert into t_forum(forum_name,forum_desc) values(?,?)";
//		Object[] params = new Object[] {forum.getForumName(),forum.getForumDesc()};
//		jdbcTemplate.update(sql,params);
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(new PreparedStatementCreator() {
			
			@Override
			public PreparedStatement createPreparedStatement(Connection con)
					throws SQLException {
				PreparedStatement ps = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
				ps.setString(1, forum.getForumName());
				ps.setString(2, forum.getForumDesc());
				return ps;
			}
		}, keyHolder);
		forum.setForumId(keyHolder.getKey().intValue());
		logger.debug(keyHolder.getKey().intValue());
	}
	
	public void addForums(final List<Forum> forums)
	{
		final String sql = "insert into t_forum(forum_name,forum_desc) values(?,?)";
		jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				Forum forum = forums.get(i);
				ps.setString(1, forum.getForumName());
				ps.setString(2, forum.getForumDesc());
			}
			
			@Override
			public int getBatchSize() {
				return forums.size();
			}
		});
	}
	
	public Forum getForum(final int forumId)
	{
		String sql = "select forum_name,forum_desc FROM t_forum where forum_id=?";
		final Forum forum = new Forum();
		jdbcTemplate.query(sql, new Object[]{forumId}, new RowCallbackHandler() {
			
			@Override
			public void processRow(ResultSet rs) throws SQLException {
				forum.setForumId(forumId);
				forum.setForumName(rs.getString("forum_name"));
				forum.setForumDesc(rs.getString("forum_desc"));
			}
		});
		return forum;
	}
	
	public List<Forum> getForums(final int forumId,final int told)
	{
		String sql = "select forum_id,forum_name,forum_desc FROM t_forum where forum_id between ? and ?";
		final List<Forum> forums = new ArrayList<Forum>();
		jdbcTemplate.query(sql, new Object[]{forumId,told}, new RowCallbackHandler() {
			
			@Override
			public void processRow(ResultSet rs) throws SQLException {
				Forum forum = new Forum();
				forum.setForumId(rs.getInt(forumId));
				forum.setForumName(rs.getString("forum_name"));
				forum.setForumDesc(rs.getString("forum_desc"));
				forums.add(forum);
			}
		});
		return forums;
	}
	
	public List<Forum> getForums(final int forumId,final int told,boolean isRowMapper)
	{
		String sql = "select forum_id,forum_name,forum_desc FROM t_forum where forum_id between ? and ?";
		return jdbcTemplate.query(sql, new Object[]{forumId,told}, new RowMapper<Forum>() {

			@Override
			public Forum mapRow(ResultSet rs, int rowNum) throws SQLException {
				Forum forum = new Forum();
				forum.setForumId(rs.getInt(forumId));
				forum.setForumName(rs.getString("forum_name"));
				forum.setForumDesc(rs.getString("forum_desc"));
				return forum;
			}
		});
	}
	
	@SuppressWarnings("deprecation")
	public int getForumNum()
	{
		String sql = "select count(*) from t_forum";
		return jdbcTemplate.queryForInt(sql);
	}
	
	public double getReplayRate(int userId)
	{
		String sql = "select topic_replies,topic_views from t_topic where user_id=?";
		return jdbcTemplate.queryForObject(sql, new Object[] {userId}, new RowMapper<Double>() {

			@Override
			public Double mapRow(ResultSet rs, int rowNum) throws SQLException {
				int replies = rs.getInt("topic_replies");
				int views = rs.getInt("topic_views");
				if (views > 0) 
					return new Double((double)replies/views);
				else 
					return new Double(0.0);
			}
		});
	}
	
	public int getUserTopicNum(final int userId)
	{
		String sql = "{call P_GET_TOPIC_NUM(?,?)}";
		Integer num = jdbcTemplate.execute(sql, new CallableStatementCallback<Integer>() {

			@Override
			public Integer doInCallableStatement(CallableStatement cs)
					throws SQLException, DataAccessException {
				cs.setInt(1, userId);
				cs.registerOutParameter(2, Types.INTEGER);
				cs.execute();
				return cs.getInt(2);
			}
		});
		return num;
	}
	
	public int getUserTopicNum2(final int userId)
	{
		String sql = "{call P_GET_TOPIC_NUM(?,?)}";
		CallableStatementCreatorFactory factory = new CallableStatementCreatorFactory(sql);
		factory.addParameter(new SqlParameter("userId", Types.INTEGER));
		factory.addParameter(new SqlOutParameter("topicNum", Types.INTEGER));
		Map<String,Integer> paramsMap = new HashMap<>();
		paramsMap.put("userId", userId);
		CallableStatementCreator csc = factory.newCallableStatementCreator(paramsMap);
		Integer num = jdbcTemplate.execute(csc,new CallableStatementCallback<Integer>() {

			@Override
			public Integer doInCallableStatement(CallableStatement cs)
					throws SQLException, DataAccessException {
					cs.execute();
				return cs.getInt(2);
			}
		});
		return num;
	}
}

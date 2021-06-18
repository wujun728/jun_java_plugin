package jdbc.viewspace;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository("viewSpaceDao")
public class ViewSpaceDaoImpl implements ViewSpaceDao{
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public void createViewSpaceTable(){
	  final String sql="create table t_view_space(spaceid int primary key ,spacename varchar(60),description varchar(60),address varchar(60))";
	  this.jdbcTemplate.execute(sql);
	}
	
	@Override
	public void addViewSpace(ViewSpace viewSpace){
	 // SQl--Args--Action
	final String sql="insert into t_view_space(spaceid,spacename,description,address) values(?,?,?,?)";
	Object[] args=new Object[]{
			viewSpace.getSpaceId(),
			viewSpace.getSpaceName(),
			viewSpace.getDescription(),
			viewSpace.getAddress()
		};
		int[] argTypes=new int[]{Types.INTEGER,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR};		
		this.jdbcTemplate.update(sql, args, argTypes);
	}

	@Override
	public ViewSpace getViewSpaceOne(int spaceId) {
		final String sql="select spaceid,spacename,description,address from t_view_space where spaceid=?";
		final Object[] args=new Object[]{spaceId};
		final ViewSpace viewSpace=new ViewSpace();
		this.jdbcTemplate.query(sql,args,new RowCallbackHandler(){
			@Override
			public void processRow(ResultSet set) throws SQLException {
				viewSpace.setSpaceId(set.getInt("spaceid"));
				viewSpace.setSpaceName(set.getString("spacename"));
				viewSpace.setDescription(set.getString("description"));
				viewSpace.setAddress(set.getString("address"));
			}});
		return viewSpace;
	}

	@Override
	public List<ViewSpace> getViewSpaceList(int startrow, int endrow) {
		final String sql="select * from t_view_space where spaceid between ? and ?";
		final Object[] args=new Object[]{startrow,endrow};
		final int[] argTypes=new int[]{Types.INTEGER,Types.INTEGER};
		final List<ViewSpace> list=new ArrayList<ViewSpace>();
        this.jdbcTemplate.query(sql, args, argTypes,new RowCallbackHandler(){
			@Override
			public void processRow(ResultSet set) throws SQLException {
				ViewSpace viewSpace=new ViewSpace();
				viewSpace.setSpaceId(set.getInt("spaceid"));
				viewSpace.setSpaceName(set.getString("spacename"));
				viewSpace.setDescription(set.getString("description"));
				viewSpace.setAddress(set.getString("address"));
				list.add(viewSpace);
			}});
		return list;
	}

	@Override
	public void addViewSpaceList(final List<ViewSpace> list) {
		final String sql="insert into t_view_space(spaceid,spacename,description,address) values(?,?,?,?) ";
		this.jdbcTemplate.batchUpdate(sql,new BatchPreparedStatementSetter(){
			@Override
			public int getBatchSize() {
				return list.size();
			}
			@Override
			public void setValues(PreparedStatement pstm, int index)
					throws SQLException {
		        ViewSpace vs=list.get(index);
		        pstm.setInt(1,vs.getSpaceId());
		        pstm.setString(2,vs.getSpaceName());
		        pstm.setString(3,vs.getDescription());
		        pstm.setString(4,vs.getAddress());
			}});
	}

	@Override
	public List<ViewSpace> getViewSpaces(final int startrow,final  int endrow) {
		final String sql="select * from t_view_space where spaceid between ? and ?";
		final Object[] args=new Object[]{startrow,endrow};
		final int[] argTypes=new int[]{Types.INTEGER,Types.INTEGER};
		return this.jdbcTemplate.query(sql,args,argTypes,new RowMapper<ViewSpace>(){
			@Override
			public ViewSpace mapRow(ResultSet set, int index)
					throws SQLException {
				ViewSpace viewSpace=new ViewSpace();
				viewSpace.setSpaceId(set.getInt("spaceid"));
				viewSpace.setSpaceName(set.getString("spacename"));
				viewSpace.setDescription(set.getString("description"));
				viewSpace.setAddress(set.getString("address"));
				return viewSpace;
			}});
	}
	@Override
	public void procedure() {
	  final String sql="create procedure p_get_num (IN space_id INT,OUT out_num INT) "+
                       "begin "+
                       "select count(*) into out_num from t_view_space where spaceid=space_id; "+
                       "end";
 	  this.jdbcTemplate.execute(sql);	
	}

	@Override
	public int procedureExe(final int id) {
		String sql="{call p_get_num(?,?)}";
	Integer out=this.jdbcTemplate.execute(sql,new CallableStatementCallback<Integer>(){
			@Override
			public Integer doInCallableStatement(CallableStatement callstmt)
					throws SQLException, DataAccessException {
				callstmt.setInt(1,id);
				callstmt.registerOutParameter(2,Types.INTEGER);
				callstmt.execute();
				return callstmt.getInt(2);
			}});
	 return out;
	}
}

package com.zhm.ssr.dao.impl;

import com.zhm.ssr.dao.UserInfoDao;
import com.zhm.ssr.model.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;


/**
 * Created by zhm on 2015/7/10.
 */
@Repository("userInfoDao")
public class UserInfoDaoImpl implements UserInfoDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    public static final class UserInfoRowMap implements RowMapper<UserInfo> {

        public UserInfo mapRow(ResultSet rs, int i) throws SQLException {
            UserInfo result = new UserInfo();
            result.setEmail(rs.getString("email"));
            result.setId(rs.getInt("id"));
            result.setMobile(rs.getString("mobile"));
            result.setPassword(rs.getString("password"));
            result.setUsername(rs.getString("username"));
            return result;
        }
    }
    public UserInfo findByUserid(String userid) {
        String sql = "select * from user_info where mobile=? or email=?";
        List<UserInfo> results = jdbcTemplate.query(sql,new Object[]{userid,userid},new UserInfoRowMap());
        return results.size()>0?results.get(0):null;
    }

    public long findNumsByCond(String keyword) {
        String sql ="select count(id) from user_info where username LIKE ? or email LIKE ? or mobile LIKE ?";
        return jdbcTemplate.queryForObject(sql,Integer.class,new Object[]{keyword,keyword,keyword});
    }

    public List<UserInfo> findByCond(String keyword, String order, int offset, int limit) {
        String sql ="select * from user_info where username LIKE ? or email LIKE ? or mobile LIKE ? limit ?,?";
        return jdbcTemplate.query(sql,new Object[]{keyword,keyword,keyword,offset,limit},new UserInfoRowMap());
    }

    public void delInfoById(int id) {
        String sql = "delete from user_info where id=? ";
        jdbcTemplate.update(sql,id);
    }
}

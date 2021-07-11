package org.wf.jwtp.provider;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.util.Assert;
import org.wf.jwtp.util.JacksonUtil;
import org.wf.jwtp.util.TokenUtil;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * jdbc存储token的实现
 * Created by wangfan on 2018-12-28 下午 1:00.
 */
public class JdbcTokenStore extends TokenStoreAbstract {
    private final JdbcTemplate jdbcTemplate;
    private RowMapper<Token> rowMapper = new TokenRowMapper();
    // sql
    private static final String UPDATE_FIELDS = "user_id, access_token, refresh_token, expire_time, refresh_token_expire_time, roles, permissions";
    private static final String BASE_SELECT = "select token_id, " + UPDATE_FIELDS + " from oauth_token";
    // 查询用户的某个token
    private static final String SQL_SELECT_BY_TOKEN = BASE_SELECT + " where user_id = ? and access_token = ?";
    // 查询某个用户的全部token
    private static final String SQL_SELECT_BY_USER_ID = BASE_SELECT + " where user_id = ? order by create_time";
    // 插入token
    private static final String SQL_INSERT = "insert into oauth_token (" + UPDATE_FIELDS + ") values (?, ?, ?, ?, ?, ?, ?)";
    // 删除某个用户指定token
    private static final String SQL_DELETE = "delete from oauth_token where user_id = ? and access_token = ?";
    // 删除某个用户全部token
    private static final String SQL_DELETE_BY_USER_ID = "delete from oauth_token where user_id = ?";
    // 修改某个用户的角色
    private static final String SQL_UPDATE_ROLES = "update oauth_token set roles = ? where user_id = ?";
    // 修改某个用户的权限
    private static final String SQL_UPDATE_PERMS = "update oauth_token set permissions = ? where user_id = ?";
    // 查询某个用户的refresh_token
    private static final String SQL_SELECT_REFRESH_TOKEN = BASE_SELECT + " where user_id = ? and refresh_token= ?";
    // 查询tokenKey
    private static final String SQL_SELECT_KEY = "select token_key from oauth_token_key";
    // 插入tokenKey
    private static final String SQL_INSERT_KEY = "insert into oauth_token_key (token_key) values (?)";

    public JdbcTokenStore(DataSource dataSource) {
        Assert.notNull(dataSource, "DataSource required");
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public String getTokenKey() {
        if (tokenKey == null) {
            try {
                tokenKey = jdbcTemplate.queryForObject(SQL_SELECT_KEY, String.class);
            } catch (EmptyResultDataAccessException e) {
                logger.debug("JwtPermission", e.getCause());
            }
            if (tokenKey == null || tokenKey.trim().isEmpty()) {
                tokenKey = TokenUtil.genHexKey();
                jdbcTemplate.update(SQL_INSERT_KEY, tokenKey);
            }
        }
        return tokenKey;
    }

    @Override
    public int storeToken(Token token) {
        List<Object> objects = getFieldsForUpdate(token);
        int rs = jdbcTemplate.update(SQL_INSERT, JacksonUtil.objectListToArray(objects));
        // 限制用户的最大token数量
        if (getMaxToken() != -1) {
            List<Token> userTokens = findTokensByUserId(token.getUserId());
            if (userTokens.size() > getMaxToken()) {
                for (int i = 0; i < userTokens.size() - getMaxToken(); i++) {
                    removeToken(token.getUserId(), userTokens.get(i).getAccessToken());
                }
            }
        }
        return rs;
    }

    @Override
    public Token findToken(String userId, String access_token) {
        try {
            return jdbcTemplate.queryForObject(SQL_SELECT_BY_TOKEN, rowMapper, userId, access_token);
        } catch (EmptyResultDataAccessException e) {
            logger.debug("JwtPermission", e.getCause());
        }
        return null;
    }

    @Override
    public List<Token> findTokensByUserId(String userId) {
        try {
            return jdbcTemplate.query(SQL_SELECT_BY_USER_ID, rowMapper, userId);
        } catch (EmptyResultDataAccessException e) {
            logger.debug("JwtPermission", e.getCause());
        }
        return null;
    }

    @Override
    public Token findRefreshToken(String userId, String refresh_token) {
        try {
            List<Token> list = jdbcTemplate.query(SQL_SELECT_REFRESH_TOKEN, rowMapper, userId, refresh_token);
            if (list.size() > 0) return list.get(0);
        } catch (EmptyResultDataAccessException e) {
            logger.debug("JwtPermission", e.getCause());
        }
        return null;
    }

    @Override
    public int removeToken(String userId, String access_token) {
        return jdbcTemplate.update(SQL_DELETE, userId, access_token);
    }

    @Override
    public int removeTokensByUserId(String userId) {
        return jdbcTemplate.update(SQL_DELETE_BY_USER_ID, userId);
    }

    @Override
    public int updateRolesByUserId(String userId, String[] roles) {
        String rolesJson = JacksonUtil.toJSONString(roles);
        return jdbcTemplate.update(SQL_UPDATE_ROLES, rolesJson, userId);
    }

    @Override
    public int updatePermissionsByUserId(String userId, String[] permissions) {
        String permJson = JacksonUtil.toJSONString(permissions);
        return jdbcTemplate.update(SQL_UPDATE_PERMS, permJson, userId);
    }

    @Override
    public String[] findRolesByUserId(String userId, Token token) {
        // 判断是否自定义查询
        if (getFindRolesSql() == null || getFindRolesSql().trim().isEmpty()) {
            return token.getRoles();
        }
        try {
            List<String> roleList = jdbcTemplate.query(getFindRolesSql(), new RowMapper<String>() {
                @Override
                public String mapRow(ResultSet rs, int rowNum) throws SQLException {
                    return rs.getString(1);
                }
            }, userId);
            return JacksonUtil.stringListToArray(roleList);
        } catch (EmptyResultDataAccessException e) {
            logger.debug("JwtPermission", e.getCause());
        }
        return null;
    }

    @Override
    public String[] findPermissionsByUserId(String userId, Token token) {
        // 判断是否自定义查询
        if (getFindPermissionsSql() == null || getFindPermissionsSql().trim().isEmpty()) {
            return token.getPermissions();
        }
        try {
            List<String> permList = jdbcTemplate.query(getFindPermissionsSql(), new RowMapper<String>() {
                @Override
                public String mapRow(ResultSet rs, int rowNum) throws SQLException {
                    return rs.getString(1);
                }
            }, userId);
            return JacksonUtil.stringListToArray(permList);
        } catch (EmptyResultDataAccessException e) {
            logger.debug("JwtPermission", e.getCause());
        }
        return null;
    }

    /**
     * 插入、修改操作的sql参数
     */
    private List<Object> getFieldsForUpdate(Token token) {
        List<Object> objects = new ArrayList<Object>();
        objects.add(token.getUserId());  // userId
        objects.add(token.getAccessToken());  // token
        objects.add(token.getRefreshToken());  // refresh_token
        objects.add(token.getExpireTime());  // expire_time
        objects.add(token.getRefreshTokenExpireTime());  // refresh_expire_time
        objects.add(JacksonUtil.toJSONString(token.getRoles()));  // roles
        objects.add(JacksonUtil.toJSONString(token.getPermissions()));  // permissions
        return objects;
    }

    /**
     * Token结果集映射
     */
    private static class TokenRowMapper implements RowMapper<Token> {

        @Override
        public Token mapRow(ResultSet rs, int rowNum) throws SQLException {
            int token_id = rs.getInt("token_id");
            String user_id = rs.getString("user_id");
            String access_token = rs.getString("access_token");
            String refresh_token = rs.getString("refresh_token");
            Date expire_time = timestampToDate(rs.getTimestamp("expire_time"));
            Date refresh_token_expire_time = timestampToDate(rs.getTimestamp("refresh_token_expire_time"));
            String roles = rs.getString("roles");
            String permissions = rs.getString("permissions");
            Token token = new Token();
            token.setTokenId(token_id);
            token.setUserId(user_id);
            token.setAccessToken(access_token);
            token.setRefreshToken(refresh_token);
            token.setExpireTime(expire_time);
            token.setRefreshTokenExpireTime(refresh_token_expire_time);
            token.setRoles(JacksonUtil.stringListToArray(JacksonUtil.parseArray(roles, String.class)));
            token.setPermissions(JacksonUtil.stringListToArray(JacksonUtil.parseArray(permissions, String.class)));
            return token;
        }

        private Date timestampToDate(java.sql.Timestamp timestamp) {
            if (timestamp != null) return new Date(timestamp.getTime());
            return null;
        }

    }

}

package org.wf.jwtp.provider;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.Assert;
import org.wf.jwtp.util.JacksonUtil;
import org.wf.jwtp.util.TokenUtil;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * redis存储token的实现
 * Created by wangfan on 2018-12-29 上午 9:10.
 */
public class RedisTokenStore extends TokenStoreAbstract {
    private static final String KEY_TOKEN_KEY = "oauth_token_key";  // tokenKey存储的key
    private static final String KEY_PRE_TOKEN = "oauth_token:";  // token存储的key前缀
    private static final String KEY_PRE_REFRESH_TOKEN = "oauth_refresh_token:";  // refresh_token存储的key前缀
    private static final String KEY_PRE_ROLE = "oauth_role:";  // 角色存储的key前缀
    private static final String KEY_PRE_PERM = "oauth_prem:";  // 权限存储的key前缀
    private final StringRedisTemplate redisTemplate;
    private final Object jdbcTemplate;

    public RedisTokenStore(StringRedisTemplate redisTemplate) {
        this(redisTemplate, null);
    }

    public RedisTokenStore(StringRedisTemplate redisTemplate, DataSource dataSource) {
        Assert.notNull(redisTemplate, "StringRedisTemplate required");
        this.redisTemplate = redisTemplate;
        if (dataSource != null) {
            this.jdbcTemplate = new org.springframework.jdbc.core.JdbcTemplate(dataSource);
        } else {
            this.jdbcTemplate = null;
        }
    }

    @Override
    public String getTokenKey() {
        if (tokenKey == null) {
            tokenKey = redisTemplate.opsForValue().get(KEY_TOKEN_KEY);
            if (tokenKey == null || tokenKey.trim().isEmpty()) {
                tokenKey = TokenUtil.genHexKey();
                redisTemplate.opsForValue().set(KEY_TOKEN_KEY, tokenKey);
            }
        }
        return tokenKey;
    }

    @Override
    public int storeToken(Token token) {
        // 存储access_token
        redisTemplate.opsForList().rightPush(KEY_PRE_TOKEN + token.getUserId(), token.getAccessToken());
        // 存储refresh_token
        if (token.getRefreshToken() != null && findRefreshToken(token.getUserId(), token.getRefreshToken()) == null) {
            redisTemplate.opsForList().rightPush(KEY_PRE_REFRESH_TOKEN + token.getUserId(), token.getRefreshToken());
        }
        // 存储角色
        updateRolesByUserId(token.getUserId(), token.getRoles());
        // 存储权限
        updatePermissionsByUserId(token.getUserId(), token.getPermissions());
        // 限制用户的最大token数量
        if (getMaxToken() != -1) {
            listMaxLimit(KEY_PRE_TOKEN + token.getUserId(), getMaxToken());
            listMaxLimit(KEY_PRE_REFRESH_TOKEN + token.getUserId(), getMaxToken());
        }
        return 1;
    }

    @Override
    public Token findToken(String userId, String access_token) {
        List<Token> tokens = findTokensByUserId(userId);
        if (tokens != null && access_token != null) {
            for (Token token : tokens) {
                if (access_token.equals(token.getAccessToken())) {
                    return token;
                }
            }
        }
        return null;
    }

    @Override
    public List<Token> findTokensByUserId(String userId) {
        if (userId == null || userId.trim().isEmpty()) return null;
        List<Token> tokens = new ArrayList<Token>();
        List<String> accessTokens = redisTemplate.opsForList().range(KEY_PRE_TOKEN + userId, 0, -1);
        if (accessTokens != null && accessTokens.size() > 0) {
            for (String accessToken : accessTokens) {
                Token token = new Token();
                token.setUserId(userId);
                token.setAccessToken(accessToken);
                // 查询权限
                token.setPermissions(JacksonUtil.stringSetToArray(redisTemplate.opsForSet().members(KEY_PRE_PERM + userId)));
                // 查询角色
                token.setRoles(JacksonUtil.stringSetToArray(redisTemplate.opsForSet().members(KEY_PRE_ROLE + userId)));
                tokens.add(token);
            }
        }
        return tokens;
    }

    @Override
    public Token findRefreshToken(String userId, String refresh_token) {
        if (userId != null && !userId.trim().isEmpty() && refresh_token != null) {
            List<String> refreshTokens = redisTemplate.opsForList().range(KEY_PRE_REFRESH_TOKEN + userId, 0, -1);
            if (refreshTokens != null && refreshTokens.size() > 0) {
                for (String refreshToken : refreshTokens) {
                    if (refresh_token.equals(refreshToken)) {
                        Token token = new Token();
                        token.setUserId(userId);
                        token.setRefreshToken(refresh_token);
                        return token;
                    }
                }
            }
        }
        return null;
    }

    @Override
    public int removeToken(String userId, String access_token) {
        redisTemplate.opsForList().remove(KEY_PRE_TOKEN + userId, 0, access_token);
        return 1;
    }

    @Override
    public int removeTokensByUserId(String userId) {
        redisTemplate.delete(KEY_PRE_TOKEN + userId);
        // 删除角色、权限、refresh_token
        redisTemplate.delete(KEY_PRE_ROLE + userId);
        redisTemplate.delete(KEY_PRE_PERM + userId);
        redisTemplate.delete(KEY_PRE_REFRESH_TOKEN + userId);
        return 1;
    }

    @Override
    public int updateRolesByUserId(String userId, String[] roles) {
        String roleKey = KEY_PRE_ROLE + userId;
        redisTemplate.delete(roleKey);
        if (roles != null && roles.length > 0) {
            redisTemplate.opsForSet().add(roleKey, roles);
        }
        return 1;
    }

    @Override
    public int updatePermissionsByUserId(String userId, String[] permissions) {
        String permKey = KEY_PRE_PERM + userId;
        redisTemplate.delete(permKey);
        if (permissions != null && permissions.length > 0) {
            redisTemplate.opsForSet().add(permKey, permissions);
        }
        return 1;
    }

    @Override
    public String[] findRolesByUserId(String userId, Token token) {
        // 判断是否自定义查询
        if (getFindRolesSql() == null || getFindRolesSql().trim().isEmpty()) {
            return token.getRoles();
        }
        if (jdbcTemplate != null) {
            try {
                List<String> roleList = ((org.springframework.jdbc.core.JdbcTemplate) jdbcTemplate).query(getFindRolesSql(), new org.springframework.jdbc.core.RowMapper<String>() {
                    @Override
                    public String mapRow(ResultSet rs, int rowNum) throws SQLException {
                        return rs.getString(1);
                    }
                }, userId);
                return JacksonUtil.stringListToArray(roleList);
            } catch (org.springframework.dao.EmptyResultDataAccessException e) {
                logger.debug("JwtPermission", e.getCause());
            }
        }
        return null;
    }

    @Override
    public String[] findPermissionsByUserId(String userId, Token token) {
        // 判断是否自定义查询
        if (getFindPermissionsSql() == null || getFindPermissionsSql().trim().isEmpty()) {
            return token.getPermissions();
        }
        if (jdbcTemplate != null) {
            try {
                List<String> permList = ((org.springframework.jdbc.core.JdbcTemplate) jdbcTemplate).query(getFindPermissionsSql(), new org.springframework.jdbc.core.RowMapper<String>() {
                    @Override
                    public String mapRow(ResultSet rs, int rowNum) throws SQLException {
                        return rs.getString(1);
                    }
                }, userId);
                return JacksonUtil.stringListToArray(permList);
            } catch (org.springframework.dao.EmptyResultDataAccessException e) {
                logger.debug("JwtPermission", e.getCause());
            }
        }
        return null;
    }

    /**
     * 限制list的最大数量
     */
    private void listMaxLimit(String key, int max) {
        Long userTokenSize = redisTemplate.opsForList().size(key);
        if (userTokenSize != null && userTokenSize > max) {
            for (int i = 0; i < userTokenSize - max; i++) {
                redisTemplate.opsForList().leftPop(key);
            }
        }
    }

}

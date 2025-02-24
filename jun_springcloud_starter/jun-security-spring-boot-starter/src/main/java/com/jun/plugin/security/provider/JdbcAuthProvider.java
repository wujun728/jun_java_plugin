package com.jun.plugin.security.provider;

import org.springframework.util.Assert;
import com.jun.plugin.security.AuthProperties;
import com.jun.plugin.security.util.CommonUtil;
import com.jun.plugin.security.util.TokenGenUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 操作token和会话的接口（通过jdbc实现）
 *了
 * @version 2023-01-06-9:33
 **/
public class JdbcAuthProvider extends AbstractAuthProvider implements AuthProvider {
    final static Logger log = LoggerFactory.getLogger(JdbcAuthProvider.class);

    private final JdbcTemplate jdbcTemplate;

    private final AuthProperties authProperties;

    public JdbcAuthProvider(JdbcTemplate jdbcTemplate, AuthProperties authProperties) {
        this.jdbcTemplate = jdbcTemplate;
        this.authProperties = authProperties;

        // 同时初始化定时任务
        this.initCleanThread();
    }

    @Override
    protected AuthProperties getAuthProperties() {
        return this.authProperties;
    }


    /**
     * 刷新token有效时间
     *
     * @param token 令牌
     * @return true成功，false失败
     */
    @Override
    public boolean refreshToken(String token) {
        Assert.hasText(token, "The token cannot be empty!");
        try {
            String sql = "update " + this.getAuthProperties().getTableName() + " set token_expire_time = ? where token_str = ?";
            int num = jdbcTemplate.update(sql, CommonUtil.currentTimePlusSeconds(this.getAuthProperties().getTimeout()), token);
            return num > 0;
        } catch (Exception e) {
            log.error("JdbcAuthProvider refreshToken failed, Exception: {e}", e);
            return false;
        }
    }

    /**
     * 检查token是否失效
     *
     * @param token 令牌
     * @return true未失效，false已失效
     */
    @Override
    public boolean checkToken(String token) {
        Assert.hasText(token, "The token cannot be empty!");
        try {
            String sql = "select token_expire_time from " + this.getAuthProperties().getTableName() + " where token_str=?";
            List<Map<String, Object>> resultList = jdbcTemplate.queryForList(sql, token);
            if (Objects.nonNull(resultList) && !resultList.isEmpty()) {
                String tokenExpireTime = resultList.get(0).get("token_expire_time").toString();
                return CommonUtil.timeCompare(tokenExpireTime);
            }
            return false;
        } catch (Exception e) {
            log.error("JdbcAuthProvider checkToken failed, Exception: {e}", e);
            return false;
        }
    }

    /**
     * 创建一个新的token
     *
     * @param loginId 会话登录：参数填写要登录的账号id，建议的数据类型：long | int | String， 不可以传入复杂类型，如：User、Admin 等等
     * @return token令牌
     */
    @Override
    public String createToken(Object loginId) {
        Assert.notNull(loginId, "The loginId cannot be null!");
        try {
            String token = TokenGenUtil.genTokenStr(this.getAuthProperties().getTokenStyle());
            String sql = "insert into " + this.getAuthProperties().getTableName() + " (token_str,login_id,token_expire_time) values (?,?,?)";
            int num = jdbcTemplate.update(sql, token, String.valueOf(loginId), CommonUtil.currentTimePlusSeconds(this.getAuthProperties().getTimeout()));
            return num > 0 ? token : null;
        } catch (Exception e) {
            log.error("JdbcAuthProvider createToken failed, Exception: {e}", e);
            return null;
        }
    }

    /**
     * 根据token，获取loginId
     *
     * @param token 令牌
     * @return loginId
     */
    @Override
    public Object getLoginId(String token) {
        Assert.hasText(token, "The token cannot be empty！");
        try {
            String sql = "select login_id from " + this.getAuthProperties().getTableName() + " where token_str=?";
            List<Map<String, Object>> resultList = jdbcTemplate.queryForList(sql, token);
            if (Objects.nonNull(resultList) && !resultList.isEmpty()) {
                return resultList.get(0).get("login_id");
            }
            return null;
        } catch (Exception e) {
            log.error("JdbcAuthProvider getLoginId failed, Exception: {e}", e);
            return null;
        }
    }

    /**
     * 删除token
     *
     * @param token 令牌
     * @return true成功，false失败
     */
    @Override
    public boolean deleteToken(String token) {
        Assert.hasText(token, "The token cannot be empty！");
        try {
            String sql = "delete from " + this.getAuthProperties().getTableName() + " where token_str = ?";
            int num = jdbcTemplate.update(sql, token);
            return num > 0;
        } catch (Exception e) {
            log.error("JdbcAuthProvider deleteToken failed, Exception: {e}", e);
            return false;
        }
    }

    /**
     * 通过loginId删除token
     *
     * @param loginId 会话id
     * @return true成功，false失败
     */
    @Override
    public boolean deleteTokenByLoginId(Object loginId) {
        Assert.notNull(loginId, "The loginId cannot be null！");
        try {
            String sql = "delete from " + this.getAuthProperties().getTableName() + " where login_id = ?";
            int num = jdbcTemplate.update(sql, loginId);
            return num > 0;
        } catch (Exception e) {
            log.error("JdbcAuthProvider deleteTokenByLoginId failed, Exception: {e}", e);
            return false;
        }
    }

    /**
     * 用于定时执行数据清理的线程池
     */
    public ScheduledExecutorService executorService;

    /**
     * 初始化清理任务，每天执行一次
     */
    private void initCleanThread() {
        this.executorService = Executors.newScheduledThreadPool(1);

        // 获取当前时间
        LocalDateTime now = LocalDateTime.now();
        // 获取明天凌晨第一秒的时间，如2023-08-25 00:00:01:000
        LocalDateTime tomorrow = now.plusDays(1).withHour(0).withMinute(0).withSecond(1).withNano(0);
        // 计算初始延迟时间（单位-毫秒）
        long initialDelay = ChronoUnit.MILLIS.between(now, tomorrow);

        this.executorService.scheduleAtFixedRate(() -> {
            log.info("JdbcAuthProvider clean execute at: {}", CommonUtil.getCurrentTime());
            try {
                // 执行清理方法
                this.clean();
            } catch (Exception e2) {
                log.error("JdbcAuthProvider cleanThread Exception: {e2}", e2);
            }
        }, initialDelay/*首次延迟多长时间后执行*/, 24 * 60 * 60 * 1000/*间隔时间*/, TimeUnit.MILLISECONDS);
        log.info("JdbcAuthProvider cleanThread init successful!");
    }


    private void clean() {
        try {
            String sql = "delete from " + this.getAuthProperties().getTableName() + " where token_expire_time < ?";
            String criticalTime = CommonUtil.currentTimeMinusSeconds(24 * 60 * 60);
            int num = jdbcTemplate.update(sql, criticalTime);
            log.info("JdbcAuthProvider clean num: {}", num);
        } catch (Exception e) {
            log.error("JdbcAuthProvider clean failed, Exception: {e}", e);
        }
    }
}

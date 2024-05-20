package com.jun.plugin.security.provider;


import org.springframework.util.Assert;
import com.jun.plugin.security.AuthProperties;
import com.jun.plugin.security.consts.AuthConsts;
import com.jun.plugin.security.util.CommonUtil;
import com.jun.plugin.security.util.TokenGenUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.*;

/**
 * 操作token和会话的接口（通过单机内存Map实现，系统重启后数据会丢失）
 * 部分代码实现参考自 https://gitee.com/dromara/sa-token/blob/dev/sa-token-core/src/main/java/cn/dev33/satoken/dao/SaTokenDaoDefaultImpl.java
 *
 * @version 2023-01-06-9:33
 **/
public class SingleAuthProvider extends AbstractAuthProvider implements AuthProvider {
    final static Logger log = LoggerFactory.getLogger(SingleAuthProvider.class);

    /**
     * 常量，每次清理过期数据间隔的时间 (单位: 秒) ，默认值30秒，设置为-1代表不启动定时清理------这里暂时先写死
     */
    final static int DATA_REFRESH_PERIOD = 30;

    /**
     * 常量，表示系统中不存在这个缓存 (在对不存在的key获取剩余存活时间时返回此值)
     */
    final static long NOT_VALUE_EXPIRE = -1L;

    /**
     * 数据存储集合
     */
    public final static Map<String, Object> dataMap = new ConcurrentHashMap<>();

    /**
     * 数据的过期时间存储集合 (单位: 毫秒) , 记录所有key的到期时间 [注意不是剩余存活时间]
     */
    public final static Map<String, Long> expireMap = new ConcurrentHashMap<>();


    // ------------------------ String 读写操作开始 ------------------------ //

    /**
     * 从缓存中获取数据（String）
     *
     * @param key 键
     * @return 值
     */
    public String get(String key) {
        clearKeyByTimeout(key);
        return (String) dataMap.get(key);
    }

    /**
     * 往缓存中存入数据（String）
     *
     * @param key     键
     * @param value   值
     * @param timeout 有效时间（秒）
     */
    public void set(String key, String value, long timeout) {
        if (timeout == 0) {
            return;
        }
        dataMap.put(key, value);
        expireMap.put(key, (System.currentTimeMillis() + timeout * 1000));
    }

    /**
     * 更新缓存数据（String）
     *
     * @param key   键
     * @param value 值
     */
    public void update(String key, String value) {
        if (getKeyTimeout(key) == SingleAuthProvider.NOT_VALUE_EXPIRE) {
            return;
        }
        dataMap.put(key, value);
    }

    /**
     * 删除缓存
     *
     * @param key 键
     */
    public void delete(String key) {
        dataMap.remove(key);
        expireMap.remove(key);
    }

    /**
     * 获取缓存剩余存活时间 (单位：秒)
     *
     * @param key 键
     * @return 剩余存活时间 (单位：秒)
     */
    public long getTimeout(String key) {
        return getKeyTimeout(key);
    }

    /**
     * 更新缓存剩余存活时间 (单位：秒)
     *
     * @param key     键
     * @param timeout 有效时间（秒）
     */
    public void updateTimeout(String key, long timeout) {
        expireMap.put(key, (System.currentTimeMillis() + timeout * 1000));
    }

    // ------------------------ Object 读写操作开始 ------------------------ //

    /**
     * 从缓存中获取数据（Object）
     *
     * @param key 键
     * @return 值
     */
    public Object getObject(String key) {
        clearKeyByTimeout(key);
        return dataMap.get(key);
    }

    /**
     * 往缓存中存入数据（Object）
     *
     * @param key     键
     * @param object  值
     * @param timeout 有效时间（秒）
     */
    public void setObject(String key, Object object, long timeout) {
        if (timeout == 0) {
            return;
        }
        dataMap.put(key, object);
        expireMap.put(key, (System.currentTimeMillis() + timeout * 1000));
    }

    /**
     * 更新缓存数据（Object）
     *
     * @param key    键
     * @param object 值
     */
    public void updateObject(String key, Object object) {
        if (getKeyTimeout(key) == SingleAuthProvider.NOT_VALUE_EXPIRE) {
            return;
        }
        dataMap.put(key, object);
    }

    /**
     * 删除缓存
     *
     * @param key 键
     */
    public void deleteObject(String key) {
        dataMap.remove(key);
        expireMap.remove(key);
    }

    /**
     * 获取缓存剩余存活时间 (单位：秒)
     *
     * @param key 键
     * @return 剩余存活时间 (单位：秒)
     */
    public long getObjectTimeout(String key) {
        return getKeyTimeout(key);
    }

    /**
     * 更新缓存剩余存活时间 (单位：秒)
     *
     * @param key     键
     * @param timeout 有效时间（秒）
     */
    public void updateObjectTimeout(String key, long timeout) {
        expireMap.put(key, (System.currentTimeMillis() + timeout * 1000));
    }

    // ------------------------ 过期时间相关操作开始 ------------------------ //

    /**
     * 如果指定key已经过期，则立即清除它
     *
     * @param key 键
     */
    private void clearKeyByTimeout(String key) {
        Long expirationTime = expireMap.get(key);
        // 清除条件：如果不为空 && 已经超过过期时间
        if (expirationTime != null && expirationTime < System.currentTimeMillis()) {
            dataMap.remove(key);
            expireMap.remove(key);
        }
    }

    /**
     * 获取指定key的剩余存活时间 (单位：秒)
     *
     * @param key 键
     */
    private long getKeyTimeout(String key) {
        // 先检查是否已经过期
        clearKeyByTimeout(key);
        // 获取过期时间
        Long expire = expireMap.get(key);
        // 如果根本没有这个值，则直接返回NOT_VALUE_EXPIRE
        if (expire == null) {
            return SingleAuthProvider.NOT_VALUE_EXPIRE;
        }
        // 计算剩余时间并返回
        long timeout = (expire - System.currentTimeMillis()) / 1000;
        // 小于零时，视为不存在，返回NOT_VALUE_EXPIRE
        if (timeout < 0) {
            dataMap.remove(key);
            expireMap.remove(key);
            return SingleAuthProvider.NOT_VALUE_EXPIRE;
        }
        return timeout;
    }


    // ------------------------ 定时清理过期数据 ------------------------ //
    /**
     * 线程池核心线程数最大值
     */
    public static final int corePoolSize = 5;

    /**
     * 用于定时执行数据清理的线程池
     */
    public ScheduledExecutorService executorService;

    /**
     * 是否继续执行数据清理的线程标记
     */
    public volatile boolean refreshFlag;

    /**
     * 清理所有已经过期的key
     */
    public void refreshDataMap() {
        for (String key : expireMap.keySet()) {
            clearKeyByTimeout(key);
        }
    }

    /**
     * 初始化定时任务
     */
    public void initRefreshThread() {
        // 如果配置了<=0的值，则不启动定时清理
        if (SingleAuthProvider.DATA_REFRESH_PERIOD <= 0) {
            return;
        }
        // 启动定时刷新
        this.refreshFlag = true;
        this.executorService = Executors.newScheduledThreadPool(corePoolSize);
        this.executorService.scheduleWithFixedDelay(() -> {
            log.info("SingleAuthProvider - refreshSession - at ：{}", CommonUtil.getCurrentTime());
            try {
                // 如果已经被标记为结束
                if (!refreshFlag) {
                    return;
                }
                // 执行清理方法
                refreshDataMap();
            } catch (Exception e2) {
                log.error("SingleAuthProvider - refreshSession - Exception：{e2}", e2);
            }
        }, 10/*首次延迟多长时间后执行*/, DATA_REFRESH_PERIOD/*间隔时间*/, TimeUnit.SECONDS);
        log.info("SingleAuthProvider - refreshThread - init successful!");
    }


    /**
     * 结束定时任务，不再定时清理过期数据
     */
    public void endRefreshThread() {
        this.refreshFlag = false;
    }


    // ------------------------ 实现AuthProvider接口开始 ------------------------ //
    private final AuthProperties authProperties;

    /**
     * 构造函数
     */
    public SingleAuthProvider(AuthProperties authProperties) {
        this.authProperties = authProperties;
        // 同时初始化定时任务
        this.initRefreshThread();
    }

    @Override
    protected AuthProperties getAuthProperties() {
        return this.authProperties;
    }

    /**
     * 刷新token
     *
     * @param token 令牌
     * @return true成功，false失败
     */
    @Override
    public boolean refreshToken(String token) {
        Assert.hasText(token, "The token cannot be empty!");
        try {
            this.updateTimeout(AuthConsts.AUTH_TOKEN_KEY + token, this.getAuthProperties().getTimeout());
            return true;
        } catch (Exception e) {
            log.error("SingleAuthProvider - refreshToken - failed，Exception：{e}", e);
            return false;
        }
    }

    /**
     * 检查token是否失效
     *
     * @param token 令牌
     * @return true有效，false已失效
     */
    @Override
    public boolean checkToken(String token) {
        Assert.hasText(token, "The token cannot be empty!");
        try {
            long timeout = this.getTimeout(AuthConsts.AUTH_TOKEN_KEY + token);
            return timeout > 0;
        } catch (Exception e) {
            log.error("SingleAuthProvider - checkToken - failed，Exception：{e}", e);
            return false;
        }
    }

    /**
     * 创建一个新的token
     *
     * @param loginId 会话登录：参数填写要登录的账号id，建议的数据类型：long | int | String， 不可以传入复杂类型，如：User、Admin 等等
     * @return token
     */
    @Override
    public String createToken(Object loginId) {
        Assert.notNull(loginId, "The loginId cannot be null!");
        try {
            String token = TokenGenUtil.genTokenStr(this.getAuthProperties().getTokenStyle());
            this.set(AuthConsts.AUTH_TOKEN_KEY + token, String.valueOf(loginId), this.getAuthProperties().getTimeout());
            return token;
        } catch (Exception e) {
            log.error("SingleAuthProvider - createToken - failed，Exception：{e}", e);
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
        Assert.hasText(token, "The token cannot be empty!");
        try {
            return this.get(AuthConsts.AUTH_TOKEN_KEY + token);
        } catch (Exception e) {
            log.error("SingleAuthProvider - getLoginId - failed，Exception：{e}", e);
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
        Assert.hasText(token, "The token cannot be empty!");
        try {
            this.delete(AuthConsts.AUTH_TOKEN_KEY + token);
            return true;
        } catch (Exception e) {
            log.error("SingleAuthProvider - deleteToken - failed，Exception：{e}", e);
            return false;
        }
    }


    /**
     * 通过loginId删除token
     *
     * @param loginId 用户id
     * @return true成功，false失败
     */
    @Override
    public boolean deleteTokenByLoginId(Object loginId) {
        Assert.notNull(loginId, "The loginId cannot be null!");
        try {
            for (String key : expireMap.keySet()) {
                if (loginId.equals(dataMap.get(key))) {
                    dataMap.remove(key);
                    expireMap.remove(key);
                }
            }
            return true;
        } catch (Exception e) {
            log.error("SingleAuthProvider - deleteTokenByLoginId - failed，Exception：{e}", e);
            return false;
        }
    }

}

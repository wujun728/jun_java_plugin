package com.jun.plugin.dfs.core.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jun.plugin.dfs.base.ErrorCodeEnum;
import com.jun.plugin.dfs.base.cache.CacheService;
import com.jun.plugin.dfs.core.entity.AppInfoEntity;
import com.jun.plugin.dfs.core.mapper.AppInfoMapper;
import com.jun.plugin.dfs.utils.DateUtils;
import com.jun.plugin.dfs.utils.MD5Utils;

import java.util.List;
import java.util.StringJoiner;

/**
 * 应用服务类
 */
@Slf4j
@Service
public class AppInfoService {

    /**
     * 客户端的发送请求时间与服务器的时间相差超过多少秒是无效的
     */
    private static final int TIMESTAMP_ERROR_SECONDS = 2 * 60 * 60;
    @Autowired
    private AppInfoMapper appInfoMapper;

    /**
     * 将应用信息载入缓存
     */
    public void loadAppInfoToCache() {
        List<AppInfoEntity> appInfoEntityList = appInfoMapper.getAllAppInfo();
        if (appInfoEntityList != null && !appInfoEntityList.isEmpty()) {
            for (AppInfoEntity appInfoEntity : appInfoEntityList) {
                CacheService.APP_INFO_CACHE.put(appInfoEntity.getAppKey(), appInfoEntity);
            }
        } else {
            CacheService.APP_INFO_CACHE.clear();
        }
    }

    /**
     * 获取应用
     *
     * @param appKey
     * @return
     */
    public AppInfoEntity getAppInfo(String appKey) {
        if (appKey == null) {
            return null;
        }
        AppInfoEntity appInfoEntity = CacheService.APP_INFO_CACHE.get(appKey);
        if (appInfoEntity == null) {
            appInfoEntity = appInfoMapper.getAppInfoByAppKey(appKey);
            if (appInfoEntity != null) {
                CacheService.APP_INFO_CACHE.put(appInfoEntity.getAppKey(), appInfoEntity);
            }
        }
        return appInfoEntity;
    }

    /**
     * 应用信息校验
     *
     * @param appKey    应用编码
     * @param timestamp 时间戳
     * @param sign      MD5(appKey + '$' + appSecret + '$' + 时间戳)
     * @return BaseErrorCode
     */
    public ErrorCodeEnum checkAuth(String appKey, String timestamp, String sign) {
        ErrorCodeEnum result = ErrorCodeEnum.OK;
        AppInfoEntity app = getAppInfo(appKey);

        if (app == null) {
            log.warn("app info not found! appKey:{}", appKey);
            return ErrorCodeEnum.APP_NOT_EXIST;
        }

        if (app.getStatus() == null || app.getStatus() != AppInfoEntity.APP_STATUS_OK) {
            log.warn("app stopped! appKey:{}", appKey);
            // 应用已停用
            return ErrorCodeEnum.APP_STOPPED;
        }

        StringJoiner joiner = new StringJoiner("$");
        joiner.add(appKey).add(app.getAppSecret()).add(timestamp);
        String md5Sign = MD5Utils.md5(joiner.toString());
        if (!sign.equalsIgnoreCase(md5Sign)) {
            log.warn("sign check error! appKey:{}, expect:{}, but get:{}", appKey, md5Sign, sign);
            // 签名校验失败
            return ErrorCodeEnum.APP_AUTH_FAILURE;
        }

        int timestampCheck = DateUtils.getSecondsToNow(timestamp);
        if (timestampCheck < 0 || timestampCheck > TIMESTAMP_ERROR_SECONDS) {
            log.warn("timestamp error! appKey:{}, timestamp:{}, timestampCheck:{}", appKey, timestamp, timestampCheck);
            return ErrorCodeEnum.TIMESTAMP_ERROR;
        }
        return result;
    }
}

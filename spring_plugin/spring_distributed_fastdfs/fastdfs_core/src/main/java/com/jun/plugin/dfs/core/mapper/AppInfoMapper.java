package com.jun.plugin.dfs.core.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.jun.plugin.dfs.core.entity.AppInfoEntity;

import java.util.List;

/**
 * 应用mapper
 */
@Mapper
public interface AppInfoMapper {

    /**
     * 查询所有应用信息
     *
     * @return
     */
    List<AppInfoEntity> getAllAppInfo();

    /**
     * 查询指定应用信息
     *
     * @param appKey
     * @return
     */
    AppInfoEntity getAppInfoByAppKey(@Param("appKey") String appKey);
}

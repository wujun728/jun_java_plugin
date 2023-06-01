package com.cosmoplat.provider.mapper;

import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * <p>
 * CommonMapper 接口
 * </p>
 *
 * @author wenbin
 * @since 2020-08-19
 */
public interface CommonMapper {

    /**
     * 根据conditions，是否与某表的某列存在关联
     *
     * @param tableName  表名
     * @param conditions 条件集合
     * @param notId      条件id不等于
     * @return Integer
     */
    Integer isExistJoinCondition(@Param(value = "tableName") String tableName, @Param("conditions") Map<String, Object> conditions, @Param("notId") String notId);
}

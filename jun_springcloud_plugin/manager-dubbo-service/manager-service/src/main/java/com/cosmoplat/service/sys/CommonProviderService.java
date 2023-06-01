package com.cosmoplat.service.sys;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: wenbin
 * @date: 2020/8/31
 * Description: 调用此通用接口： 可以判断某表的哪些字段是否存在某值， 一般校验重复使用
 */
public interface CommonProviderService {


    /**
     * 根据conditions，是否与某表的某列存在关联
     *
     * @param tableName  表名
     * @param conditions 条件集合
     * @param notId      where id ！= 可以为null
     * @return Boolean
     */
    boolean isExistJoin(String tableName, Map<String, Object> conditions, String notId);
}

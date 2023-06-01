package com.cosmoplat.provider.impl;

import com.cosmoplat.provider.mapper.CommonMapper;
import com.cosmoplat.service.sys.CommonProviderService;
import org.apache.dubbo.config.annotation.DubboService;

import javax.annotation.Resource;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: wenbin
 * @date: 2020/8/31
 * Description: No Description
 */
@DubboService
public class CommonProviderServiceImpl implements CommonProviderService {

    @Resource
    CommonMapper commonMapper;

    @Override
    public boolean isExistJoin(String tableName, Map<String, Object> conditions, String notId) {
        return commonMapper.isExistJoinCondition(tableName, conditions, notId) > 0;
    }
}

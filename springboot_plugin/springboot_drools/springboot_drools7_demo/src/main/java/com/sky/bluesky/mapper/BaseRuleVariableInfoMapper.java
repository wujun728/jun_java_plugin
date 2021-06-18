package com.sky.bluesky.mapper;

import com.sky.bluesky.model.BaseRuleVariableInfo;

import java.util.List;

/**
 * 描述：
 * CLASSPATH: com.sky.bluesky.mapper.BaseRuleVariableInfoMapper
 * VERSION:   1.0
 * Created by lihao
 * DATE:      2017/7/20
 */
public interface BaseRuleVariableInfoMapper {

    /**
     * Date 2017/7/20
     * Author lihao [lihao@sinosoft.com]
     *
     * 方法说明: 根据变量类型或数值类型查询变量信息
     * @param baseRuleVariableInfo 参数
     */
    List<BaseRuleVariableInfo> findBaseRuleVariableInfoList(BaseRuleVariableInfo baseRuleVariableInfo);
}

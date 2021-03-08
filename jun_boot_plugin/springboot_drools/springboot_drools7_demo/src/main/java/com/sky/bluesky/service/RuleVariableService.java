package com.sky.bluesky.service;

import com.github.pagehelper.PageInfo;
import com.sky.bluesky.model.BaseRuleVariableInfo;

import java.util.List;

/**
 * 描述：
 * CLASSPATH: com.sky.bluesky.service.RuleVariableService
 * VERSION:   1.0
 * Created by lihao
 * DATE:      2017/7/20
 */
public interface RuleVariableService {

    /**
     * Date 2017/7/20
     * Author lihao [lihao@sinosoft.com]
     *
     * 方法说明: 根据变量类型或数值类型获取变量集合信息
     * @param baseRuleVariableInfo 参数
     * @param pageInfo 分页参数
     */
    List<BaseRuleVariableInfo> findBaseRuleVariableInfoList(BaseRuleVariableInfo baseRuleVariableInfo, PageInfo pageInfo) throws Exception;
}

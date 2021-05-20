package com.sky.bluesky.service;

import com.github.pagehelper.PageInfo;
import com.sky.bluesky.model.BaseRuleActionRuleRelInfo;

/**
 * 描述：
 * CLASSPATH: com.sky.bluesky.service.RuleActionRelService
 * VERSION:   1.0
 * Created by lihao
 * DATE:      2017/7/24
 */
public interface RuleActionRelService {

    /**
     * Date 2017/7/24
     * Author lihao [lihao@sinosoft.com]
     *
     * 方法说明: 获取规则与动作关系集合信息
     * @param baseRuleActionRuleRelInfo 参数
     * @param page 分页参数
     */
    PageInfo<BaseRuleActionRuleRelInfo> findBaseRuleActionRuleRelInfoPage(BaseRuleActionRuleRelInfo baseRuleActionRuleRelInfo,PageInfo page) throws Exception;
}

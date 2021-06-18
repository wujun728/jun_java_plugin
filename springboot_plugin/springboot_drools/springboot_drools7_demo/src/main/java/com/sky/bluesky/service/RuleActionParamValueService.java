package com.sky.bluesky.service;

import com.github.pagehelper.PageInfo;
import com.sky.bluesky.model.BaseRuleActionParamValueInfo;

/**
 * 描述：
 * CLASSPATH: com.sky.bluesky.service.RuleActionParamValueService
 * VERSION:   1.0
 * Created by lihao
 * DATE:      2017/7/24
 */
public interface RuleActionParamValueService {

    /**
     * Date 2017/7/24
     * Author lihao [lihao@sinosoft.com]
     *
     * 方法说明: 根据动作参数或动作与规则关系id获取对应的参数信息
     * @param baseRuleActionParamValueInfo 参数
     * @param page 分页参数
     */
    PageInfo<BaseRuleActionParamValueInfo> findBaseRuleActionParamValueInfoPage(BaseRuleActionParamValueInfo baseRuleActionParamValueInfo,PageInfo page) throws Exception;

    /**
     * Date 2017/7/27
     * Author lihao [lihao@sinosoft.com]
     * <p>
     * 方法说明: 根据参数id获取参数value
     *
     * @param paramId 参数id
     */
    BaseRuleActionParamValueInfo findRuleParamValueByActionParamId(final Long paramId)throws Exception;
}

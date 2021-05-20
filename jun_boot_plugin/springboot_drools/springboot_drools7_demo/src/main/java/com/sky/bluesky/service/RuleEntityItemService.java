package com.sky.bluesky.service;

import com.sky.bluesky.model.BaseRuleEntityItemInfo;

import java.util.List;

/**
 * 描述：
 * CLASSPATH: com.sky.bluesky.service.RuleEntityItemService
 * VERSION:   1.0
 * Created by lihao
 * DATE:      2017/7/20
 */
public interface RuleEntityItemService {

    /**
     * Date 2017/7/20
     * Author lihao [lihao@sinosoft.com]
     * <p>
     * 方法说明: 根据实体id获取规则引擎实体属性信息
     *
     * @param baseRuleEntityItemInfo 参数
     */
    List<BaseRuleEntityItemInfo> findBaseRuleEntityItemInfoList(BaseRuleEntityItemInfo baseRuleEntityItemInfo) throws Exception;

    /**
     * Date 2017/7/26
     * Author lihao [lihao@sinosoft.com]
     * <p>
     * 方法说明: 根据id获取对应的属性信息
     *
     * @param id 属性Id
     */
    BaseRuleEntityItemInfo findBaseRuleEntityItemInfoById(final Long id) throws Exception;
}

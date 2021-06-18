package com.sky.bluesky.service;

import com.github.pagehelper.PageInfo;
import com.sky.bluesky.model.BaseRuleInfo;
import com.sky.bluesky.model.BaseRulePropertyInfo;
import com.sky.bluesky.model.BaseRulePropertyRelInfo;
import com.sky.bluesky.model.BaseRuleSceneInfo;

import java.util.List;

/**
 * 描述：
 * CLASSPATH: com.sky.bluesky.service.RuleInfoService
 * VERSION:   1.0
 * Created by lihao
 * DATE:      2017/7/25
 */
public interface RuleInfoService {

    /**
     * Date 2017/7/25
     * Author lihao [lihao@sinosoft.com]
     *
     * 方法说明: 获取规则集合信息
     * @param baseRuleInfo 参数
     * @param page 分页参数
     */
    PageInfo<BaseRuleInfo> findBaseRuleInfoPage(BaseRuleInfo baseRuleInfo,PageInfo page) throws Exception;

    /**
     * Date 2017/7/25
     * Author lihao [lihao@sinosoft.com]
     *
     * 方法说明: 查询规则属性信息
     * @param baseRulePropertyInfo 参数
     */
    List<BaseRulePropertyInfo> findBaseRulePropertyInfoList(BaseRulePropertyInfo baseRulePropertyInfo) throws Exception;

    /**
     * Date 2017/7/25
     * Author lihao [lihao@sinosoft.com]
     *
     * 方法说明: 根据规则获取已经配置的属性信息
     * @param ruleId 参数
     */
    List<BaseRulePropertyRelInfo> findRulePropertyListByRuleId(final Long ruleId)throws Exception;

    /**
     * Date 2017/7/26
     * Author lihao [lihao@sinosoft.com]
     *
     * 方法说明: 根据场景获取对应的规则规则信息
     * @param baseRuleSceneInfo 参数
     */
    List<BaseRuleInfo> findBaseRuleListByScene(BaseRuleSceneInfo baseRuleSceneInfo)throws Exception;
}

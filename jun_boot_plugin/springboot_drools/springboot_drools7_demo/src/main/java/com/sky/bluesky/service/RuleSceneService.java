package com.sky.bluesky.service;

import com.sky.bluesky.model.BaseRuleSceneInfo;

import java.util.List;

/**
 * 描述：
 * CLASSPATH: com.sky.bluesky.service.RuleSceneService
 * VERSION:   1.0
 * Created by lihao
 * DATE:      2017/7/20
 */
public interface RuleSceneService {
    /**
     * Date 2017/7/20
     * Author lihao [lihao@sinosoft.com]
     *
     * 方法说明: 获取规则引擎场景集合
     * @param sceneInfo 参数
     */
    List<BaseRuleSceneInfo> findBaseRuleSceneInfiList(BaseRuleSceneInfo sceneInfo) throws Exception;
}

package com.sky.bluesky.service.impl;

import com.sky.bluesky.mapper.BaseRuleSceneInfoMapper;
import com.sky.bluesky.model.BaseRuleSceneInfo;
import com.sky.bluesky.service.RuleSceneService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 描述：
 * CLASSPATH: com.sky.bluesky.service.impl.RuleSceneServiceImpl
 * VERSION:   1.0
 * Created by lihao
 * DATE:      2017/7/20
 */
@Service
public class RuleSceneServiceImpl implements RuleSceneService {

    @Resource
    private BaseRuleSceneInfoMapper baseRuleSceneInfoMapper;
    /**
     * Date 2017/7/20
     * Author lihao [lihao@sinosoft.com]
     * <p>
     * 方法说明: 获取规则引擎场景集合
     *
     * @param sceneInfo 参数
     */
    @Override
    public List<BaseRuleSceneInfo> findBaseRuleSceneInfiList(BaseRuleSceneInfo sceneInfo) throws Exception {
        return this.baseRuleSceneInfoMapper.findBaseRuleSceneInfiList(sceneInfo);
    }
}

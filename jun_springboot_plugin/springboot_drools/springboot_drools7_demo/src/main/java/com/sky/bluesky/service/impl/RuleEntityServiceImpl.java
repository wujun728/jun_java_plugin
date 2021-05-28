package com.sky.bluesky.service.impl;

import com.sky.bluesky.mapper.BaseRuleEntityInfoMapper;
import com.sky.bluesky.model.BaseRuleEntityInfo;
import com.sky.bluesky.service.RuleEntityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 描述：
 * CLASSPATH: com.sky.bluesky.service.impl.RuleEntityServiceImpl
 * VERSION:   1.0
 * Created by lihao
 * DATE:      2017/7/20
 */
@Service
public class RuleEntityServiceImpl implements RuleEntityService {
    Logger logger = LoggerFactory.getLogger(RuleEntityServiceImpl.class);

    @Resource
    private BaseRuleEntityInfoMapper baseRuleEntityInfoMapper;


    /**
     * Date 2017/7/20
     * Author lihao [lihao@sinosoft.com]
     * <p>
     * 方法说明: 获取规则引擎实体信息
     *
     * @param baseRuleEntityInfo 参数
     */
    @Override
    public List<BaseRuleEntityInfo> findBaseRuleEntityInfoList(BaseRuleEntityInfo baseRuleEntityInfo) throws Exception {
        return this.baseRuleEntityInfoMapper.findBaseRuleEntityInfoList(baseRuleEntityInfo);
    }

    /**
     * Date 2017/7/26
     * Author lihao [lihao@sinosoft.com]
     * <p>
     * 方法说明: 根据实体id获取实体信息
     *
     * @param id 实体id
     */
    @Override
    public BaseRuleEntityInfo findBaseRuleEntityInfoById(Long id) throws Exception {
        if (null == id) {
            throw new NullPointerException("参数缺失");
        }
        return this.baseRuleEntityInfoMapper.findBaseRuleEntityInfoById(id);
    }
}

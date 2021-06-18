package com.sky.bluesky.service.impl;

import com.sky.bluesky.mapper.BaseRuleEntityItemInfoMapper;
import com.sky.bluesky.model.BaseRuleEntityItemInfo;
import com.sky.bluesky.service.RuleEntityItemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 描述：
 * CLASSPATH: com.sky.bluesky.service.impl.RuleEntityItemServiceImpl
 * VERSION:   1.0
 * Created by lihao
 * DATE:      2017/7/20
 */
@Service
public class RuleEntityItemServiceImpl implements RuleEntityItemService {
    Logger logger = LoggerFactory.getLogger(RuleEntityItemServiceImpl.class);

    @Resource
    private BaseRuleEntityItemInfoMapper baseRuleEntityItemInfoMapper;

    /**
     * Date 2017/7/20
     * Author lihao [lihao@sinosoft.com]
     * <p>
     * 方法说明: 根据实体id获取规则引擎实体属性信息
     *
     * @param baseRuleEntityItemInfo 参数
     */
    @Override
    public List<BaseRuleEntityItemInfo> findBaseRuleEntityItemInfoList(BaseRuleEntityItemInfo baseRuleEntityItemInfo) throws Exception {
        return this.baseRuleEntityItemInfoMapper.findBaseRuleEntityItemInfoList(baseRuleEntityItemInfo);
    }

    /**
     * Date 2017/7/26
     * Author lihao [lihao@sinosoft.com]
     * <p>
     * 方法说明: 根据id获取对应的属性信息
     *
     * @param id 属性Id
     */
    @Override
    public BaseRuleEntityItemInfo findBaseRuleEntityItemInfoById(Long id) throws Exception {
        if (null == id) {
            throw new NullPointerException("参数缺失");
        }
        return this.baseRuleEntityItemInfoMapper.findBaseRuleEntityItemInfoById(id);
    }
}

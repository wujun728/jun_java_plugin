package com.sky.bluesky.service.impl;

import com.github.pagehelper.PageInfo;
import com.sky.bluesky.mapper.BaseRuleActionParamInfoMapper;
import com.sky.bluesky.model.BaseRuleActionParamInfo;
import com.sky.bluesky.service.RuleActionParamService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 描述：
 * CLASSPATH: com.sky.bluesky.service.impl.RuleActionParamServiceImpl
 * VERSION:   1.0
 * Created by lihao
 * DATE:      2017/7/24
 */
@Service
public class RuleActionParamServiceImpl implements RuleActionParamService {

    @Resource
    private BaseRuleActionParamInfoMapper baseRuleActionParamInfoMapper;

    /**
     * Date 2017/7/24
     * Author lihao [lihao@sinosoft.com]
     * <p>
     * 方法说明: 获取动作参数信息
     *
     * @param baseRuleActionParamInfo 参数
     * @param page                    分页参数
     */
    @Override
    public PageInfo<BaseRuleActionParamInfo> findBaseRuleActionParamInfoPage(BaseRuleActionParamInfo baseRuleActionParamInfo, PageInfo page) throws Exception {

        List<BaseRuleActionParamInfo> list = this.baseRuleActionParamInfoMapper.findBaseRuleActionParamInfoList(baseRuleActionParamInfo);

        return new PageInfo<>(list);
    }

    /**
     * Date 2017/7/24
     * Author lihao [lihao@sinosoft.com]
     * <p>
     * 方法说明: 根据动作id获取动作参数信息
     *
     * @param actionId 参数
     */
    @Override
    public List<BaseRuleActionParamInfo> findRuleActionParamByActionId(Long actionId) {
        if (null == actionId) {
            throw new NullPointerException("参数缺失");
        }
        return this.baseRuleActionParamInfoMapper.findRuleActionParamByActionId(actionId);
    }
}

package com.sky.bluesky.mapper;

import com.sky.bluesky.model.BaseRuleEntityItemInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 描述：
 * CLASSPATH: com.sky.bluesky.mapper.BaseRuleEntityInfoMapper
 * VERSION:   1.0
 * Created by lihao
 * DATE:      2017/7/20
 */
@Mapper
public interface BaseRuleEntityItemInfoMapper {
    /**
     * Date 2017/7/20
     * Author lihao [lihao@sinosoft.com]
     *
     * 方法说明: 根据实体id获取规则引擎实体属性信息
     * @param baseRuleEntityItemInfo 参数
     */
    List<BaseRuleEntityItemInfo> findBaseRuleEntityItemInfoList(BaseRuleEntityItemInfo baseRuleEntityItemInfo);

    /**
     * Date 2017/7/26
     * Author lihao [lihao@sinosoft.com]
     * <p>
     * 方法说明: 根据id获取对应的属性信息
     *
     * @param id 属性Id
     */
    BaseRuleEntityItemInfo findBaseRuleEntityItemInfoById(@Param("id") Long id);
}

package com.chentongwei.core.common.biz;

import com.alibaba.fastjson.JSON;
import com.chentongwei.cache.redis.IBasicCache;
import com.chentongwei.common.enums.constant.RedisEnum;
import com.chentongwei.common.creator.ResultCreator;
import com.chentongwei.common.entity.Result;
import com.chentongwei.core.common.entity.vo.RegionVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Wujun
 * @Project tucaole
 * @Description: 省市区业务
 */
@Service
public class RegionBiz {

    @Autowired
    private IBasicCache basicCache;

    /**
     * 获取省市区下拉框（含子集）
     *
     * @return
     */
    public Result list() {
        String locationList = basicCache.get(RedisEnum.getLocationKey());
        List<RegionVO> list = JSON.parseArray(locationList, RegionVO.class);
        return ResultCreator.getSuccess(list);
    }

}

package com.chentongwei.core.common.biz;

import com.alibaba.fastjson.JSON;
import com.chentongwei.cache.redis.IBasicCache;
import com.chentongwei.common.enums.constant.RedisEnum;
import com.chentongwei.core.common.dao.IRegionDAO;
import com.chentongwei.core.common.entity.vo.RegionVO;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Wujun
 * @Project tucaole
 * @Description: 缓存省市区递归层级结构json
 */
@Service
public class InitRegionBiz {
    private static final Logger LOG = LogManager.getLogger("bizLog");

    /**
     * 省份父id
     */
    private static final int PROVINCE_PID = 0;

    @Autowired
    private IRegionDAO regionDAO;
    @Autowired
    private IBasicCache basicCache;

    /**
     * 省市区下拉框
     */
    public void initRegion() {
        String regionStr = basicCache.get(RedisEnum.getLocationKey());
        //因为省市区一般情况不会动，所以如果redis里有的话，就不递归查询了
        if (StringUtils.isEmpty(regionStr)) {
            List<RegionVO> list = regionDAO.listByPid(PROVINCE_PID);
            recursion(list);
            basicCache.set(RedisEnum.getLocationKey(), JSON.toJSONString(list));
            LOG.info(" 缓存省市区完成");
        }
    }

    /**
     * 递归查询省市区
     *
     * @param list：省市区集合
     */
    private void recursion(List<RegionVO> list) {
        for (RegionVO regionVO : list) {
            List<RegionVO> children = regionDAO.listByPid(regionVO.getId());
            regionVO.setChildren(children);
            recursion(children);
        }
    }

}

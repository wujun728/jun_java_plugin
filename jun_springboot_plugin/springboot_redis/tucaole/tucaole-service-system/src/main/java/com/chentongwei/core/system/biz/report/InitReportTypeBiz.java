package com.chentongwei.core.system.biz.report;

import com.alibaba.fastjson.JSON;
import com.chentongwei.cache.redis.IBasicCache;
import com.chentongwei.common.enums.constant.RedisEnum;
import com.chentongwei.core.system.dao.IReportTypeDAO;
import com.chentongwei.core.system.entity.vo.report.ReportTypeListVO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Wujun
 * @Project tucaole
 * @Description: 吐槽了举报类型Biz
 */
@Service
public class InitReportTypeBiz {
    private static final Logger LOG = LogManager.getLogger("bizLog");

    @Autowired
    private IReportTypeDAO reportTypeDAO;
    @Autowired
    private IBasicCache basicCache;

    /**
     * 缓存举报类型列表
     *
     * @return
     */
    public void initReportType() {
        List<ReportTypeListVO> list = reportTypeDAO.list();
        basicCache.set(RedisEnum.getReportTypeKey(), JSON.toJSONString(list));
        LOG.info("缓存举报类型成功");
    }
}

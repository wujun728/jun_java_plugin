package com.chentongwei.core.system.biz.report;

import com.alibaba.fastjson.JSON;
import com.chentongwei.cache.redis.IBasicCache;
import com.chentongwei.common.creator.ResultCreator;
import com.chentongwei.common.entity.Result;
import com.chentongwei.common.enums.constant.RedisEnum;
import com.chentongwei.core.system.entity.vo.report.ReportTypeListVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Wujun
 * @Project tucaole
 * @Description: 举报类型Biz
 **/
@Service
public class ReportTypeBiz {

    @Autowired
    private IBasicCache basicCache;

    /**
     * 举报类型列表
     *
     * @return
     */
    public Result list() {
        String reportType = basicCache.get(RedisEnum.getReportTypeKey());
        List<ReportTypeListVO> reportTypeList = JSON.parseArray(reportType, ReportTypeListVO.class);
        return ResultCreator.getSuccess(reportTypeList);
    }
}

package com.chentongwei.core.system.biz.report;

import com.chentongwei.cache.redis.IBasicCache;
import com.chentongwei.common.creator.ResultCreator;
import com.chentongwei.common.entity.Result;
import com.chentongwei.common.enums.constant.RedisEnum;
import com.chentongwei.common.enums.constant.TimeEnum;
import com.chentongwei.common.exception.BussinessException;
import com.chentongwei.common.util.CommonExceptionUtil;
import com.chentongwei.core.system.dao.IReportDAO;
import com.chentongwei.core.system.entity.io.report.ReportIO;
import com.chentongwei.core.system.enums.msg.ReportMsgEnum;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Wujun
 * @Project tucaole
 * @Description: 吐槽了举报Biz
 */
@Service
public class ReportBiz {
    private static final Logger LOG = LogManager.getLogger("bizLog");

    @Autowired
    private IReportDAO reportDAO;
    @Autowired
    private IBasicCache basicCache;

    /**
     * 保存举报记录
     * @param reportIO：参数
     * @return
     */
    @Transactional
    public Result save(ReportIO reportIO) {
        //检查此用户今天是否举报了此资源
        checkAlreadyReport(reportIO);

        //每个人每天最多举报10次
        checkReportCountMax(reportIO.getUserId());

        //保存
        reportDAO.save(reportIO);
        //redis+1，每个人每天最多举报10次
        basicCache.increment(RedisEnum.getReportCountKey(reportIO.getUserId().toString()), 1);
        LOG.info("举报文章成功");
        return ResultCreator.getSuccess();
    }

    /**
     * 检查此用户今天是否举报了此资源
     *
     * @param reportIO：参数
     */
    private final void checkAlreadyReport(final ReportIO reportIO) {
        Integer existsCount = reportDAO.checkReportCurrentDate(reportIO.getResourceId(), reportIO.getType(), reportIO.getUserId());
        CommonExceptionUtil.notNullCheck(existsCount, ReportMsgEnum.ALREADY_REPORT);
    }

    /**
     * 每个人每天最多举报10次
     *
     * @param userId：用户id
     */
    private final void checkReportCountMax(final Long userId) {
        long reportCount = basicCache.increment(RedisEnum.getReportCountKey(userId.toString()), 0);
        if (reportCount >= TimeEnum.HUNDRED_TIME.value()) {
            LOG.info("每个用户每天最多举报10次【userId={}】", userId);
            throw new BussinessException(ReportMsgEnum.REPORT_COUNT_MAX);
        }
    }
}

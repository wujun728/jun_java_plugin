package com.chentongwei.core.system.biz;

import com.chentongwei.common.creator.ResultCreator;
import com.chentongwei.common.entity.Result;
import com.chentongwei.core.system.dao.ISysLogDAO;
import com.chentongwei.core.system.entity.io.SysLogIO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * @author Wujun
 * @Project tucaole
 * @Description: 系统日志业务接口实现
 */
@Service
public class SysLogBiz {
    private static final Logger LOG = LogManager.getLogger("bizLog");

    @Autowired
    private ISysLogDAO sysLogDAO;

    /**
     * 保存日志
     * @param sysLogIO：参数
     * @return
     */
    public Result save(SysLogIO sysLogIO) {
        sysLogDAO.save(sysLogIO);
        LOG.info("记录日志成功");
        return ResultCreator.getSuccess();
    }
}

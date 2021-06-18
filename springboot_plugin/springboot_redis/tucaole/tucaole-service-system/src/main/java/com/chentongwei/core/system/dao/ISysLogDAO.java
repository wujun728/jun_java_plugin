package com.chentongwei.core.system.dao;

import com.chentongwei.core.system.entity.io.SysLogIO;

/**
 * @author Wujun
 * @Project tucaole
 * @Description: 系统日志DAO
 */
public interface ISysLogDAO {

    /**
     * 保存日志
     *
     * @param sysLogIO：日志信息
     * @return
     */
    void save(SysLogIO sysLogIO);
}

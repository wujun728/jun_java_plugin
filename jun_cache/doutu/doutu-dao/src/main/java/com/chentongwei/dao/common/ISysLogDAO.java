package com.chentongwei.dao.common;

import com.chentongwei.entity.common.io.SysLogIO;
import com.chentongwei.entity.common.vo.SysLogListVO;

import java.util.List;

/**
 * 系统日志DAO
 *
 * @author TongWei.Chen 2017-06-01 20:03:27
 */
public interface ISysLogDAO {

    /**
     * 日志列表
     *
     * @return
     */
    List<SysLogListVO> list();

    /**
     * 保存日志
     *
     * @param sysLogIO：日志信息
     * @return
     */
    boolean save(SysLogIO sysLogIO);
}

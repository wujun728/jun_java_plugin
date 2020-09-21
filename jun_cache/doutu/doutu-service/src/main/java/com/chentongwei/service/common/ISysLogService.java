package com.chentongwei.service.common;

import com.chentongwei.common.entity.Page;
import com.chentongwei.entity.common.io.SysLogIO;
import com.chentongwei.entity.common.vo.SysLogListVO;

import java.util.List;

/**
 * 系统日志业务接口
 *
 * @author TongWei.Chen 2017-06-01 20:52:58
 */
public interface ISysLogService {

    /**
     * 日志列表
     *
     * @param page：分页信息
     * @return
     */
    List<SysLogListVO> list(Page page);

    /**
     * 保存日志
     *
     * @param sysLogIO：日志信息
     * @return
     */
    boolean save(SysLogIO sysLogIO);
}

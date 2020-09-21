package com.chentongwei.service.common.impl;

import com.chentongwei.common.entity.Page;
import com.chentongwei.common.handler.CommonExceptionHandler;
import com.chentongwei.dao.common.ISysLogDAO;
import com.chentongwei.entity.common.io.SysLogIO;
import com.chentongwei.entity.common.vo.SysLogListVO;
import com.chentongwei.service.common.ISysLogService;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 系统日志业务接口实现
 *
 * @author TongWei.Chen 2017-06-01 20:53:55
 */
@Service
public class SysLogServiceImpl implements ISysLogService {

    @Autowired
    private ISysLogDAO sysLogDAO;

    @Override
    public List<SysLogListVO> list(Page page) {
        PageHelper.startPage(page.getPageNum(), page.getPageSize());
        return sysLogDAO.list();
    }

    @Override
    public boolean save(SysLogIO sysLogIO) {
        boolean flag = sysLogDAO.save(sysLogIO);
        CommonExceptionHandler.flagCheck(flag);
        return true;
    }
}

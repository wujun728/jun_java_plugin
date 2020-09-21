package com.chentongwei.service.common.impl;

import com.chentongwei.common.entity.Page;
import com.chentongwei.dao.common.IUpdateLogDAO;
import com.chentongwei.entity.common.io.UpdateLogSaveIO;
import com.chentongwei.entity.common.vo.UpdateLogListVO;
import com.chentongwei.service.common.IUpdateLogService;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 项目更新日志接口实现
 *
 * @author TongWei.Chen 2017-07-11 14:23:45
 */
@Service
public class UpdateLogServiceImpl implements IUpdateLogService {

    @Autowired
    private IUpdateLogDAO updateLogDAO;

    @Override
    public List<UpdateLogListVO> list(Page page) {
        PageHelper.startPage(page.getPageNum(), page.getPageSize());
        return updateLogDAO.list();
    }

    @Override
    public boolean save(UpdateLogSaveIO updateLogSaveIO) {
        return updateLogDAO.save(updateLogSaveIO);
    }
}

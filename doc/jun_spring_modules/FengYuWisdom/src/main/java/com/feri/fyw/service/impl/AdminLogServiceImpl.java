package com.feri.fyw.service.impl;

import com.feri.fyw.dao.AdminLogDao;
import com.feri.fyw.entity.AdminLog;
import com.feri.fyw.service.intf.AdminLogService;
import com.feri.fyw.vo.PageBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @program: FengYuWisdom
 * @description:
 * @author: Feri(邢朋辉)
 * @create: 2021-06-16 09:52
 */
@Service
public class AdminLogServiceImpl implements AdminLogService {
    @Autowired
    private AdminLogDao dao;
    @Override
    public PageBean queryPage(int aid){
        List<AdminLog> list=dao.selectAll(aid);
        return new PageBean(0,"",list.size(),list);
    }
}

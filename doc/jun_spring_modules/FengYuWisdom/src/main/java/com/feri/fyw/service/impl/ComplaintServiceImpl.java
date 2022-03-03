package com.feri.fyw.service.impl;

import com.feri.fyw.config.SystemConfig;
import com.feri.fyw.dao.ComplaintDao;
import com.feri.fyw.entity.Admin;
import com.feri.fyw.entity.Complaint;
import com.feri.fyw.service.intf.ComplaintService;
import com.feri.fyw.util.RUtil;
import com.feri.fyw.vo.PageBean;
import com.feri.fyw.vo.R;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.servlet.http.HttpSession;

/**
 * @program: FengYuWisdom
 * @description:
 * @author: Feri(邢朋辉)
 * @create: 2021-06-17 10:57
 */
@Service
public class ComplaintServiceImpl implements ComplaintService {
    @Autowired
    private ComplaintDao dao;
    @Override
    public R save(Complaint complaint, HttpSession session) {
        Admin amdin=(Admin)session.getAttribute(SystemConfig.CURR_USER);
        complaint.setSid(amdin.getId());
        if(dao.insert(complaint)>0){
            return RUtil.ok();
        }else {
            return RUtil.fail();
        }
    }

    @Override
    public PageBean queryPage(String msg,int page, int limit) {
        PageHelper.startPage(page,limit);
        PageInfo<Complaint> pageInfo=new PageInfo<>(dao.selectAll(msg));
        return new PageBean(0,"",pageInfo.getTotal(),pageInfo.getList());
    }

    @Override
    public R change(Complaint complaint, HttpSession session) {
        complaint.setFlag(2);
        Admin amdin=(Admin)session.getAttribute(SystemConfig.CURR_USER);
        complaint.setAid(amdin.getId());
        if(dao.update(complaint)>0){
            return RUtil.ok();
        }else {
            return RUtil.fail();
        }
    }
}

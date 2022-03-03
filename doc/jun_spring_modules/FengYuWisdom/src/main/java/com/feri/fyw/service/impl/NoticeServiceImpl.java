package com.feri.fyw.service.impl;

import com.feri.fyw.config.SystemConfig;
import com.feri.fyw.dao.ComplaintDao;
import com.feri.fyw.dao.NoticeDao;
import com.feri.fyw.dao.NoticeReadDao;
import com.feri.fyw.entity.Admin;
import com.feri.fyw.entity.Complaint;
import com.feri.fyw.entity.Notice;
import com.feri.fyw.service.intf.ComplaintService;
import com.feri.fyw.service.intf.NoticeService;
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
public class NoticeServiceImpl implements NoticeService {
    @Autowired
    private NoticeDao dao;
    @Autowired
    private NoticeReadDao readDao;
    @Override
    public R save(Notice notice, HttpSession session) {
        Admin admin=(Admin)session.getAttribute(SystemConfig.CURR_USER);
        notice.setAid(admin.getId());
        if(dao.insert(notice)>0){
            return RUtil.ok();
        }else {
            return RUtil.fail();
        }
    }

    @Override
    public PageBean queryPage(int page, int limit) {
        PageHelper.startPage(page,limit);
        PageInfo<Notice> pageInfo=new PageInfo<>(dao.selectAll());
        return new PageBean(0,"",pageInfo.getTotal(),pageInfo.getList());
    }

    @Override
    public R saveRead(int nid, HttpSession session) {
        if(nid>0){
            int id=((Admin)session.getAttribute(SystemConfig.
                    CURR_USER)).getId();
            if(readDao.insert(id,nid)>0){
                return RUtil.ok();
            }
        }
        return RUtil.fail();
    }


}

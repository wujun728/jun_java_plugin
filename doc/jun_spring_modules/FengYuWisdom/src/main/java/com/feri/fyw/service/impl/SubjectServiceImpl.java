package com.feri.fyw.service.impl;

import com.feri.fyw.dao.SubjectDao;
import com.feri.fyw.entity.Student;
import com.feri.fyw.entity.Subject;
import com.feri.fyw.service.intf.SubjectService;
import com.feri.fyw.util.RUtil;
import com.feri.fyw.vo.PageBean;
import com.feri.fyw.vo.R;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @program: FengYuWisdom
 * @description:
 * @author: Feri(邢朋辉)
 * @create: 2021-06-17 10:57
 */
@Service
public class SubjectServiceImpl implements SubjectService {
    @Autowired
    private SubjectDao dao;
    @Override
    public R save(Subject subject) {
        if(dao.insert(subject)>0){
            return RUtil.ok();
        }else {
            return RUtil.fail();
        }
    }

    @Override
    public PageBean queryPage(int page, int limit) {
        PageHelper.startPage(page,limit);
        PageInfo<Subject> pageInfo=new PageInfo<>(dao.selectAll());
        return new PageBean(0,"",pageInfo.getTotal(),pageInfo.getList());
    }

    @Override
    public R change(Subject subject) {
        if(dao.update(subject)>0){
            return RUtil.ok();
        }else {
            return RUtil.fail();
        }
    }
}

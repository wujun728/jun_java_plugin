package com.feri.fyw.service.impl;

import com.feri.fyw.dao.StudentProjectDao;
import com.feri.fyw.entity.StudentProject;
import com.feri.fyw.service.intf.StudentProjectService;
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
public class StudentProjectServiceImpl implements StudentProjectService {
    @Autowired
    private StudentProjectDao dao;
    @Override
    public R save(StudentProject project) {
        if(dao.insert(project)>0){
            return RUtil.ok();
        }else {
            return RUtil.fail();
        }
    }

    @Override
    public PageBean queryPage(int page, int limit) {
        PageHelper.startPage(page,limit);
        PageInfo<StudentProject> pageInfo=new PageInfo<>(dao.selectAll());
        return new PageBean(0,"",pageInfo.getTotal(),pageInfo.getList());
    }
}

package com.feri.fyw.service.impl;

import com.alibaba.excel.EasyExcel;
import com.feri.fyw.dao.StageExamDao;
import com.feri.fyw.entity.StageExam;
import com.feri.fyw.listener.StageExamReadListener;
import com.feri.fyw.service.intf.StageExamService;
import com.feri.fyw.util.RUtil;
import com.feri.fyw.vo.PageBean;
import com.feri.fyw.vo.R;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;

/**
 * @program: FengYuWisdom
 * @description:
 * @author: Feri(邢朋辉)
 * @create: 2021-06-18 10:05
 */
@Service
public class StageExamServiceImpl implements StageExamService {
    @Autowired
    private StageExamDao dao;
    @Override
    public R save(StageExam exam) {
        if(dao.insert(exam)>0){
            return RUtil.ok();
        }else {
            return RUtil.fail();
        }
    }

    @Override
    public R saveBatch(MultipartFile file) {
        if(file.isEmpty()){
            try {
                List<StageExam> list= EasyExcel.read(file.getInputStream(),StageExam.class,new StageExamReadListener()).doReadAllSync();
                if(list!=null &&list.size()>0){
                    if(dao.insertBatch(list)>0){
                        //
                        return RUtil.ok();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return RUtil.fail();
    }

    @Override
    public PageBean queryAll(int page, int limit) {
        PageHelper.startPage(page, limit);
        PageInfo<StageExam> pageInfo=new PageInfo<>(dao.selectAll());
        return new PageBean(0,"",pageInfo.getTotal(),pageInfo.getList());
    }
}

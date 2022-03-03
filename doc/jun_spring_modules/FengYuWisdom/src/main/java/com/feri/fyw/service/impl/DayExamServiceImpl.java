package com.feri.fyw.service.impl;

import com.alibaba.excel.EasyExcel;
import com.feri.fyw.dao.DayExamDao;
import com.feri.fyw.dto.DayExamDto;
import com.feri.fyw.entity.DayExam;
import com.feri.fyw.listener.DayExamReadListener;
import com.feri.fyw.service.intf.DayExamService;
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
public class DayExamServiceImpl implements DayExamService {
    @Autowired
    private DayExamDao dao;
    @Override
    public R save(DayExam exam) {
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
                List<DayExam> list= EasyExcel.read(file.getInputStream(),DayExam.class,new DayExamReadListener()).doReadAllSync();
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
        PageInfo<DayExamDto> pageInfo=new PageInfo<>(dao.selectAll());
        return new PageBean(0,"",pageInfo.getTotal(),pageInfo.getList());
    }
}

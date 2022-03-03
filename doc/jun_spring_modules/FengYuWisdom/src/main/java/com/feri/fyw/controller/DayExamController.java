package com.feri.fyw.controller;

import com.feri.fyw.entity.DayExam;
import com.feri.fyw.service.intf.DayExamService;
import com.feri.fyw.vo.PageBean;
import com.feri.fyw.vo.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

/**
 * @program: FengYuWisdom
 * @description:
 * @author: Feri(邢朋辉)
 * @create: 2021-06-18 10:10
 */
@Controller
public class DayExamController {
    @Autowired
    private DayExamService service;

    //新增
    @PostMapping("/api/dayexam/save.do")
    @ResponseBody
    public R save(DayExam exam){
        return service.save(exam);
    }
    //新增批量
    @PostMapping("/api/dayexam/savebatch.do")
    public R save(MultipartFile file){
        return service.saveBatch(file);
    }
    //查询
    @GetMapping("/api/dayexam/all.do")
    @ResponseBody
    public PageBean all(int page, int limit){
        return service.queryAll(page, limit);
    }
}

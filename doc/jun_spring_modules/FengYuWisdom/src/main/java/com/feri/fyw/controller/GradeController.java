package com.feri.fyw.controller;

import com.feri.fyw.entity.Grade;
import com.feri.fyw.entity.Subject;
import com.feri.fyw.service.intf.GradeService;
import com.feri.fyw.service.intf.SubjectService;
import com.feri.fyw.vo.PageBean;
import com.feri.fyw.vo.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @program: FengYuWisdom
 * @description:
 * @author: Feri(邢朋辉)
 * @create: 2021-06-11 16:42
 */
@Controller
@RequestMapping("/api/grade/")
public class GradeController {
    @Autowired
    private GradeService service;

    //新增
    @RequestMapping("save.do")
    @ResponseBody
    public R save(Grade grade){
        return service.save(grade);
    }
    //查询
    @RequestMapping("all.do")
    @ResponseBody
    public PageBean all(int page,int limit){
        return service.queryPage(page, limit);
    }

}

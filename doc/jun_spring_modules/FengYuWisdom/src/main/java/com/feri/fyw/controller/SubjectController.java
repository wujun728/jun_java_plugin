package com.feri.fyw.controller;

import com.feri.fyw.entity.Student;
import com.feri.fyw.entity.Subject;
import com.feri.fyw.service.intf.StudentService;
import com.feri.fyw.service.intf.SubjectService;
import com.feri.fyw.vo.PageBean;
import com.feri.fyw.vo.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

/**
 * @program: FengYuWisdom
 * @description:
 * @author: Feri(邢朋辉)
 * @create: 2021-06-11 16:42
 */
@Controller
@RequestMapping("/api/subject/")
public class SubjectController {
    @Autowired
    private SubjectService service;

    //新增管理员
    @RequestMapping("save.do")
    @ResponseBody
    public R save(Subject subject){
        return service.save(subject);
    }

    @RequestMapping("all.do")
    @ResponseBody
    public PageBean all(int page,int limit){
        return service.queryPage(page, limit);
    }
    @RequestMapping("update.do")
    @ResponseBody
    public R update(Subject subject){
        return service.change(subject);
    }
}

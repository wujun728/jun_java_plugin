package com.feri.fyw.controller;

import com.feri.fyw.dto.AdminDto;
import com.feri.fyw.entity.Admin;
import com.feri.fyw.entity.Student;
import com.feri.fyw.service.intf.AdminService;
import com.feri.fyw.service.intf.StudentService;
import com.feri.fyw.vo.PageBean;
import com.feri.fyw.vo.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;

/**
 * @program: FengYuWisdom
 * @description:
 * @author: Feri(邢朋辉)
 * @create: 2021-06-11 16:42
 */
@Controller
@RequestMapping("/api/student/")
@CrossOrigin
public class StudentController {
    @Autowired
    private StudentService service;

    //新增管理员
    @RequestMapping("save.do")
    @ResponseBody
    public R save(Student student){
        return service.save(student);
    }

    @RequestMapping("all.do")
    @ResponseBody
    public PageBean all(){
        return service.queryPage();
    }

    @PostMapping("uploadexcel.do")
    public R upload(MultipartFile file){
        return service.saveBatch(file);
    }

    @GetMapping("tjsex.do")
    @ResponseBody
    public R tj(){
        return service.queryTjSex();
    }

    @GetMapping("tjpersons.do")
    @ResponseBody
    public R tjPersons(){
        return service.queryTjPersons();
    }
}

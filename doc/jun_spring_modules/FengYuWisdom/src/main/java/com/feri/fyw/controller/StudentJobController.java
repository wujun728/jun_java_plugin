package com.feri.fyw.controller;

import com.feri.fyw.entity.StudentJob;
import com.feri.fyw.service.intf.StudentJobService;
import com.feri.fyw.vo.PageBean;
import com.feri.fyw.vo.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @program: FengYuWisdom
 * @description:
 * @author: Feri(邢朋辉)
 * @create: 2021-06-18 10:10
 */
@Controller
public class StudentJobController {
    @Autowired
    private StudentJobService service;

    //新增
    @PostMapping("/api/studentjob/save.do")
    @ResponseBody
    public R save(StudentJob studentjob){
        return service.save(studentjob);
    }
    //新增批量
    @PostMapping("/api/studentjob/savebatch.do")
    public R save(MultipartFile file){
        return service.saveBatch(file);
    }
    //查询
    @GetMapping("/api/studentjob/all.do")
    @ResponseBody
    public PageBean all(int page, int limit){
        return service.queryAll(page, limit);
    }

    @GetMapping("/api/studentjob/createtop.do")
    public void download(HttpServletRequest request,HttpServletResponse response) throws Exception {
        //文件下载
        //设置响应头  告知浏览器，要以附件的形式保存内容   filename=浏览器显示的下载文件名
        response.setHeader("content-disposition","attachment;filename="+new SimpleDateFormat("yyyyMMdd").format(new Date())+".jpg");
        //调用业务逻辑层 实现就业榜单的生成
        service.createTop(request,response.getOutputStream());
    }
}

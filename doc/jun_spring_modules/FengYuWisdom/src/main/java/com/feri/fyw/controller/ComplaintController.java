package com.feri.fyw.controller;

import com.feri.fyw.entity.Complaint;
import com.feri.fyw.entity.Subject;
import com.feri.fyw.service.intf.ComplaintService;
import com.feri.fyw.service.intf.SubjectService;
import com.feri.fyw.vo.PageBean;
import com.feri.fyw.vo.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * @program: FengYuWisdom
 * @description:
 * @author: Feri(邢朋辉)
 * @create: 2021-06-11 16:42
 */
@Controller
@RequestMapping("/api/complaint/")
public class ComplaintController {
    @Autowired
    private ComplaintService service;

    //新增
    @RequestMapping("save.do")
    @ResponseBody
    public R save(Complaint complaint,HttpSession session){
        return service.save(complaint,session);
    }
    //分页查询
    @RequestMapping("all.do")
    @ResponseBody
    public PageBean all(String msg,int page,int limit){
        return service.queryPage(msg,page, limit);
    }
    //处理 投诉
    @RequestMapping("update.do")
    @ResponseBody
    public R update(Complaint complaint, HttpSession session){
        return service.change(complaint,session);
    }
}

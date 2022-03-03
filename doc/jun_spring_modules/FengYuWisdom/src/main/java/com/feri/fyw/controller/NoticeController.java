package com.feri.fyw.controller;

import com.feri.fyw.entity.Complaint;
import com.feri.fyw.entity.Notice;
import com.feri.fyw.service.intf.ComplaintService;
import com.feri.fyw.service.intf.NoticeService;
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
@RequestMapping("/api/notice/")
public class NoticeController {
    @Autowired
    private NoticeService service;

    //新增
    @RequestMapping("save.do")
    @ResponseBody
    public R save(Notice notice,HttpSession session){
        return service.save(notice,session);
    }
    //分页查询
    @RequestMapping("all.do")
    @ResponseBody
    public PageBean all(int page,int limit){
        return service.queryPage(page, limit);
    }
    //处理 投诉
    @RequestMapping("saveread.do")
    @ResponseBody
    public R update(int nid, HttpSession session){
        return service.saveRead(nid,session);
    }
}

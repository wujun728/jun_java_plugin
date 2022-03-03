package com.feri.fyw.controller;

import com.feri.fyw.config.SystemConfig;
import com.feri.fyw.entity.Admin;
import com.feri.fyw.service.intf.AdminLogService;
import com.feri.fyw.vo.PageBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpSession;

/**
 * @program: FengYuWisdom
 * @description:
 * @author: Feri(邢朋辉)
 * @create: 2021-06-16 09:54
 */
@RestController
public class AdminLogController {
    @Autowired
    private AdminLogService service;
    //查询管理员日志
    @GetMapping("/api/adminlog/all.do")
    public PageBean all(HttpSession session){
        Admin admin=(Admin)session.getAttribute(SystemConfig.CURR_USER);
        return service.queryPage(admin.getId());
    }
}

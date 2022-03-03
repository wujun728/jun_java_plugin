package com.feri.fyw.service.intf;

import com.feri.fyw.dao.AdminDao;
import com.feri.fyw.dto.AdminDto;
import com.feri.fyw.entity.Admin;
import com.feri.fyw.vo.PageBean;
import com.feri.fyw.vo.R;

import javax.servlet.http.HttpSession;

/**
 * @program: FengYuWisdom
 * @description:
 * @author: Feri(邢朋辉)
 * @create: 2021-06-11 16:36
 */
public interface AdminService {
    //新增
    R save(Admin admin);
    //登陆
    R login(AdminDto dto, HttpSession session);
    //注销
    R loginout(HttpSession session);
    //查询列表
    PageBean queryList();

}

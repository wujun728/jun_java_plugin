package com.feri.fyw.service.impl;

import com.feri.fyw.config.SystemConfig;
import com.feri.fyw.dao.AdminDao;
import com.feri.fyw.dao.AdminLogDao;
import com.feri.fyw.dto.AdminDto;
import com.feri.fyw.entity.Admin;
import com.feri.fyw.entity.AdminLog;
import com.feri.fyw.service.intf.AdminService;
import com.feri.fyw.util.RUtil;
import com.feri.fyw.vo.PageBean;
import com.feri.fyw.vo.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @program: FengYuWisdom
 * @description:
 * @author: Feri(邢朋辉)
 * @create: 2021-06-11 16:38
 */
@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    private AdminDao dao;
    @Autowired
    private AdminLogDao logDao;


    @Override
    public R save(Admin admin) {
        if(admin!=null && admin.getName()!=null){
            //密码 加密
            admin.setPassword(DigestUtils.md5DigestAsHex(admin.getPassword().getBytes()));
            if(dao.insert(admin)>0){
                //新增成功
                return RUtil.ok();
            }
        }
        return RUtil.fail();
    }

    @Override
    public R login(AdminDto dto, HttpSession session) {
        //如果用户在一定的时间内，连续出错多次，应该锁定账号，该如何实现？

        //1.校验
        if(dto!=null && dto.getAccount()!=null){
            //2.校验验证码
            if(dto.getCaptcha().equals(session.getAttribute("code").toString())){
                //3.查询
                Admin admin=dao.selectByName(dto.getAccount());
                //4.校验用户
                if(admin!=null){
                    //5.校验密码
                    if(admin.getPassword().equals(DigestUtils.md5DigestAsHex(dto.getPass().getBytes()))){
                        //6.登陆成功
                        session.setAttribute(SystemConfig.CURR_USER,admin);
                        //7.记录操作日志
                        logDao.insert(new AdminLog(admin.getId(),SystemConfig.ALOG_TYPE_LS,"登陆成功"));

                        return RUtil.ok(admin);
                    }else {
                        logDao.insert(new AdminLog(admin.getId(),SystemConfig.ALOG_TYPE_LE,"密码不正确，登陆失败"));
                    }
                }
            }
        }
        return RUtil.fail();
    }

    @Override
    public R loginout(HttpSession session) {
        Admin admin=(Admin)session.getAttribute(SystemConfig.CURR_USER);
        logDao.insert(new AdminLog(admin.getId(),SystemConfig.ALOG_TYPE_OUT,"账号退出"));
        session.removeAttribute(SystemConfig.CURR_USER);
        return RUtil.ok();
    }

    @Override
    public PageBean queryList() {
        List<Admin> list=dao.selectAll();
        return new PageBean(0,"",list.size(),list);
    }
}

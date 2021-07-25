package com.jun.plugin.demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jun.plugin.demo.entity.User;
import com.jun.plugin.demo.util.ResponseData;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author CDHong
 * @since 2019-06-24
 */
public interface IUserService extends IService<User> {
    ResponseData login(String logName, String logPwd);
    ResponseData findByRelName(String logName);
    ResponseData userPage(Integer pageIndex,Integer pageSize,String search);
}

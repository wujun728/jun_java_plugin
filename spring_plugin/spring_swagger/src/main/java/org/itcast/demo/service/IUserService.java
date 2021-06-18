package org.itcast.demo.service;

import org.itcast.demo.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import org.itcast.demo.util.ResponseData;

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

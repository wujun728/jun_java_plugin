package org.itcast.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.additional.query.impl.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.service.additional.update.impl.LambdaUpdateChainWrapper;
import org.itcast.demo.entity.User;
import org.itcast.demo.mapper.UserMapper;
import org.itcast.demo.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.itcast.demo.util.ConstUtils;
import org.itcast.demo.util.ResponseCode;
import org.itcast.demo.util.ResponseData;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Wujun
 * @since 2019-06-24
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Override
    public ResponseData login(String logName, String logPwd) {
        //new LambdaUpdateChainWrapper<>(baseMapper);
        User user = new LambdaQueryChainWrapper<>(baseMapper).eq(User::getLogName, logName).eq(User::getLogPwd, logPwd).one();
        if(Objects.nonNull(user)){
            if(user.getLogStatus() == ConstUtils.UserLoginStatus.DISABLED){
                return ResponseData.fail("你已被禁用，请联系管理员~");
            }
            return ResponseData.ok("登陆成功~",user);
        }
        return ResponseData.error(ResponseCode.NEED_LOGIN);
    }

    @Override
    public ResponseData findByRelName(String relName) {
        List<User> user = baseMapper.findAll(Wrappers.<User>lambdaQuery().like(User::getRelName, relName));
        if(Objects.nonNull(user) ){
            return ResponseData.ok(user);
        }
        return ResponseData.fail("没有查询到数据~");
    }

    @Override
    public ResponseData userPage(Integer pageIndex, Integer pageSize, String search) {
        Page<User> page = new Page<>(pageIndex, pageSize);
        LambdaQueryWrapper<User> queryWrapper = Wrappers.<User>lambdaQuery().like(User::getLogName, search).or().like(User::getRelName, search);
        IPage<User> userIPage = baseMapper.pageInfo(page, queryWrapper);
        return ResponseData.okPage(userIPage.getTotal(),userIPage.getRecords());
    }
}

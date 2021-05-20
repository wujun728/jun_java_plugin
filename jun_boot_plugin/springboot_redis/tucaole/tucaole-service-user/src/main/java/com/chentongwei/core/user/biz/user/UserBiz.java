package com.chentongwei.core.user.biz.user;

import com.chentongwei.cache.redis.IBasicCache;
import com.chentongwei.common.enums.constant.RedisEnum;
import com.chentongwei.common.enums.msg.ResponseEnum;
import com.chentongwei.common.creator.ResultCreator;
import com.chentongwei.common.email.biz.MailBiz;
import com.chentongwei.common.entity.Result;
import com.chentongwei.common.entity.vo.AddressVO;
import com.chentongwei.common.entity.vo.LocationVO;
import com.chentongwei.common.enums.constant.TimeEnum;
import com.chentongwei.common.exception.BussinessException;
import com.chentongwei.common.util.CommonExceptionUtil;
import com.chentongwei.common.util.DateUtil;
import com.chentongwei.common.util.MD5Util;
import com.chentongwei.common.util.StringUtil;
import com.chentongwei.core.user.dao.IUserDAO;
import com.chentongwei.core.user.entity.io.user.*;
import com.chentongwei.core.user.entity.vo.user.LoginVO;
import com.chentongwei.core.user.entity.vo.user.UserDetailVO;
import com.chentongwei.core.user.entity.vo.user.UserVO;
import com.chentongwei.core.user.enums.msg.UserMsgEnum;
import com.chentongwei.core.user.util.UserExceptionUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.beans.BeanCopier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.util.Date;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @author Wujun
 * @Project tucaole
 * @Description: 用户业务接口实现类
 **/
@Service
public class UserBiz {
    private static final Logger LOG = LogManager.getLogger("bizLog");

    @Autowired
    private IUserDAO userDAO;
    @Autowired
    private MailBiz mailBiz;
    @Autowired
    private UserMailBiz userMailBiz;
    @Autowired
    private IBasicCache basicCache;

    /**
     * 查看详情
     *
     * 为什么写的这么啰嗦？
     * 因为想让JSON标准化
     * 返回如下格式的地址：
     * address: {
         "province": {
             "id": 1,
             "name": "11"
         },
         "city": {
             "id": 2,
             "name": "22"
         },
         "district": {
             "id": 3,
             "name": "33"
         }
     * }
     *
     * 而不是：
     * {
     *   "省id": 1,
         "省": "北京",
         "市id": 11,
         "市": "北京",
         "区id": 111,
         "区": "朝阳区"
     * }
     *
     * @param userId：用户id
     * @return
     */
    public Result detail(Long userId) {
        UserVO userVO = userDAO.getById(userId);
        CommonExceptionUtil.nullCheck(userVO);
        //将基本信息复制进去，alibaba不推荐用BeanUtils，所以用的BeanCopier
        final BeanCopier beanCopier = BeanCopier.create(UserVO.class, UserDetailVO.class, false);

        //返回结果
        UserDetailVO userDetailVO = new UserDetailVO();
        beanCopier.copy(userVO, userDetailVO, null);
        //外层地址对象
        AddressVO address = new AddressVO();

        //省份对象
        LocationVO province = new LocationVO();
        province.id(userVO.getProvinceId()).name(userVO.getProvinceName());
        address.setProvince(province);

        //城市对象
        LocationVO city = new LocationVO();
        city.id(userVO.getCityId()).name(userVO.getCityName());
        address.setCity(city);

        //地区对象
        LocationVO district = new LocationVO();
        district.id(userVO.getDistrictId()).name(userVO.getDistrictName());
        address.setDistrict(district);

        userDetailVO.setAddress(address);
        return ResultCreator.getSuccess(userDetailVO);
    }

    /**
     * QQ注册
     *
     * @param userIO：用户信息
     * @return Result
     */
    @Transactional
    public Result qqRegist(RegistUserIO userIO) {
        UserVO user = userDAO.getByOpenId(userIO.getOpenId());
        if (null == user) {
            userDAO.regist(userIO);
            //TODO 存到Redis  7天
            basicCache.set(RedisEnum.getLoginedUserKey(userIO.getId().toString()), userIO.getToken(), 7, TimeUnit.DAYS);
            LOG.info("【{}】QQ注册成功！", userIO.getId());
            return ResultCreator.getFail(UserMsgEnum.USER_INCOMPLETE, null);
        } else {
            //是否禁用
            UserExceptionUtil.userDisabledCheck(user.getStatus());
        }
        //TODO 存到Redis    7天
        basicCache.set(RedisEnum.getLoginedUserKey(userIO.getId().toString()), userIO.getToken(), 7, TimeUnit.DAYS);
        //检查是否完善过资料
        CommonExceptionUtil.strNullCheck(user.getNickname(), UserMsgEnum.USER_INCOMPLETE);
        LOG.info("【{}】QQ登录成功！", user.getId());
        return ResultCreator.getSuccess();
    }

    /**
     * 注册
     *
     * @param userIO：用户信息
     * @return Result
     */
    @Transactional
    public Result regist(RegistUserIO userIO) {
        //email重复检查
        boolean emailFlag = userDAO.getByEmail(userIO.getEmail());
        CommonExceptionUtil.flagCheck(emailFlag, UserMsgEnum.EMAIL_ALREADY_EXISITS);

        userDAO.regist(userIO);
        //将token和刚注册的用户id缓存到redis，激活用户的时候需要校验   2小时校期
        String token = MD5Util.encode(userIO.getEmail() + userIO.getPassword());
        //发送注册邮件
        userMailBiz.sendRegistMail(userIO, token);
        basicCache.set(RedisEnum.getActiveUserKey(userIO.getId().toString()), token, 2, TimeUnit.HOURS);
        LOG.info("【{}】注册成功！", userIO.getId());
        return ResultCreator.getSuccess();
    }

    /**
     * 登录
     *
     * @param loginIO：登录信息
     * @return Result
     */
    public Result login(LoginIO loginIO) {
        LoginVO user = userDAO.login(loginIO);
        //用户名密码错误
        CommonExceptionUtil.nullCheck(user, UserMsgEnum.EMAIL_OR_PWD_ERROR);
        //是否禁用
        UserExceptionUtil.userDisabledCheck(user.getStatus());
        //是否激活
        UserExceptionUtil.userActiveCheck(user.getIsActive());
        //生成token,加密email+pwd
        String token = MD5Util.encode(loginIO.getEmail() + loginIO.getPassword());
        //存到redis，只要前三步都通过，就存到redis，而不是更新完资料才放redis，只登录没问题就存redis
        basicCache.set(RedisEnum.getLoginedUserKey(user.getId().toString()), token, 7, TimeUnit.DAYS);
        //将token返回给客户端。
        user.setToken(token);
        //检查是否完善过资料
        if (StringUtil.isNull(user.getNickname())) {
            return ResultCreator.getFail(UserMsgEnum.USER_INCOMPLETE, user);
        }
        return ResultCreator.getSuccess(user);
    }

    /**
     * 检查电子邮件是否唯一
     *
     * @param email：电子邮件
     * @return Result
     */
    public Result checkEmailUnique(String email) {
        boolean flag = userDAO.getByEmail(email);
        CommonExceptionUtil.flagCheck(flag, UserMsgEnum.EMAIL_ALREADY_EXISITS);
        return ResultCreator.getSuccess();
    }

    /**
     * 修改用户信息
     *
     * @param userUpdateIO：用户信息
     * @return
     */
    @Transactional
    public Result update(UserUpdateIO userUpdateIO) {
        UserVO userVO = userDAO.getById(userUpdateIO.getUserId());
        CommonExceptionUtil.nullCheck(userVO);
        userUpdateIO.setUserId(userUpdateIO.getUserId());
        userDAO.update(userUpdateIO);
        return ResultCreator.getSuccess();
    }

    /**
     * 更改邮箱
     *
     * @param updateEmailIO：邮箱验证码等参数
     * @return
     */
    @Transactional
    public Result updateEmail(UpdateEmailIO updateEmailIO) {
        /**
         * 为什么不需要验证oldEmail和oldEmailCode？
         * 因为若oldEmail和oldEmailCode不匹配的话都不可能获取到新邮箱的验证码
         * 所以只需要验证新邮箱与新邮箱验证码是否匹配即可
         */
        //从redis取出新邮箱的code
        String newEmailCode = basicCache.get(RedisEnum.getSendTargetEmailKey(updateEmailIO.getNewEmail()));
        //判断code和新邮箱code是否一致
        if (! Objects.equals(newEmailCode, updateEmailIO.getNewEmailCode())) {
            LOG.info("新邮箱验证码不正确");
            throw new BussinessException(ResponseEnum.VERIFICATION_CODE_ERROR);
        }
        // 检查电子邮件是否注册了
        boolean flag = userDAO.getByEmail(updateEmailIO.getNewEmail());
        CommonExceptionUtil.flagCheck(flag, UserMsgEnum.EMAIL_ALREADY_EXISITS);
        //update
        userDAO.updateEmail(updateEmailIO.getUserId(), updateEmailIO.getNewEmail());
        //remove新邮件的code，防止重复修改
        basicCache.deleteKey(RedisEnum.getSendTargetEmailKey(updateEmailIO.getNewEmail()));
        return ResultCreator.getSuccess();
    }

    /**
     * 修改密码
     *
     * @param updatePasswordIO：修改密码
     * @return
     */
    @Transactional
    public Result updatePassword(UpdatePasswordIO updatePasswordIO) {
        //检查两次输入的密码是否一致
        if(! Objects.equals(updatePasswordIO.getNewPassword(), updatePasswordIO.getEnsurePassword())) {
            LOG.info("【两次输入的密码不一致】");
            throw new BussinessException(UserMsgEnum.PWD_AND_ENSUREPWD_NOT_SAMED);
        }
        //根据id查user，判断老密码是否正确
        UserVO userVO = userDAO.getById(updatePasswordIO.getUserId());
        //null ==》用户未登录
        CommonExceptionUtil.nullCheck(userVO, ResponseEnum.USER_NOT_LOGIN);
        //原密码不正确
        if (! Objects.equals(userVO.getPassword(), MD5Util.encode(updatePasswordIO.getOldPassword()))) {
            throw new BussinessException(UserMsgEnum.OLD_PASSWORD_ERROR);
        }
        //修改密码
        userDAO.updatePassword(updatePasswordIO.getUserId(), MD5Util.encode(updatePasswordIO.getNewPassword()));
        return ResultCreator.getSuccess();
    }

    /**
     * 找回密码、修改密码接口
     *
     * @param forgetPasswordIO：参数
     * @return
     */
    @Transactional
    public Result forgetPassword(ForgetPasswordIO forgetPasswordIO) {
        userDAO.updatePasswordByEmail(forgetPasswordIO.getEmail(), MD5Util.encode(forgetPasswordIO.getPassword()));
        //修改成功后，移除redis，防止利用一个验证码肆意修改密码
        basicCache.deleteKey(forgetPasswordIO.getEmail());
        return ResultCreator.getSuccess();
    }

    /**
     * 激活用户
     *
     * @param userId：用户id
     * @return Result
     */
    @Transactional
    public Result activeUser(Long userId) {
        UserVO userVO = userDAO.getById(userId);
        CommonExceptionUtil.nullCheck(userVO, UserMsgEnum.USER_NOT_EXISTS);
        try {
            int hour = DateUtil.hourDiff(userVO.getCreateTime(), new Date());
            //大于2小时就失效
            if(hour > TimeEnum.TWO_HOUR.value()) {
                throw new BussinessException(UserMsgEnum.EMAIL_TIMEOUT_ERROR);
            }
        } catch (ParseException e) {
            LOG.info("【{}】激活失败！", userId);
            e.printStackTrace();
        }
        //检查是否已经激活过
        UserExceptionUtil.alreadyActiveCheck(userVO.getIsActive());
        userDAO.activeUser(userId);
        LOG.info("【{}】激活成功！", userId);
        return ResultCreator.getSuccess();
    }

    /**
     * 删除没有激活的用户（目前在task任务里用的，每天跑一次小程序删除那些大于两小时还没激活的用户。）
     * @return Result
     */
    public Result deleteUnActiveUser() {
        userDAO.deleteUnActiveUser();
        return ResultCreator.getSuccess();
    }

}
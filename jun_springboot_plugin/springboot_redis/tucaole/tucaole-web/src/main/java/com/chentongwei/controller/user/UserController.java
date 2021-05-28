package com.chentongwei.controller.user;

import com.chentongwei.cache.redis.IBasicCache;
import com.chentongwei.common.annotation.CategoryLog;
import com.chentongwei.common.annotation.DescLog;
import com.chentongwei.common.entity.Result;
import com.chentongwei.common.entity.io.BaseGeetestIO;
import com.chentongwei.common.entity.io.TokenIO;
import com.chentongwei.common.enums.constant.RedisEnum;
import com.chentongwei.common.enums.msg.ResponseEnum;
import com.chentongwei.common.exception.BussinessException;
import com.chentongwei.common.util.CommonExceptionUtil;
import com.chentongwei.common.util.IPUtil;
import com.chentongwei.common.util.MD5Util;
import com.chentongwei.common.util.geetest.GeetestConstant;
import com.chentongwei.common.util.geetest.GeetestLib;
import com.chentongwei.core.user.biz.user.UserBiz;
import com.chentongwei.core.user.biz.user.UserMailBiz;
import com.chentongwei.core.user.entity.io.user.*;
import com.chentongwei.core.user.enums.msg.UserMsgEnum;
import com.chentongwei.core.user.enums.status.GeetestStatusEnum;
import com.chentongwei.core.user.enums.status.UserActiveStatusEnum;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Objects;

/**
 * @author Wujun
 * @Project tucaole
 * @Description: 用户控制层接口
 */
@RestController
@RequestMapping("/user/user")
@CategoryLog(menu = "用户模块")
public class UserController {

    /**
     * LOG日志
     */
    private static final Logger LOG = LogManager.getLogger(UserController.class);

    @Autowired
    private GeetestConstant geetestConstant;
    @Autowired
    private UserBiz userBiz;
    @Autowired
    private IBasicCache basicCache;
    @Autowired
    private UserMailBiz userMailBiz;

    /**
     * 查看用户详情
     *
     * @param userId 用户id
     * @return
     */
    @GetMapping("/detail")
    public Result detail(Long userId) {
        //检查用户id是否为null
        CommonExceptionUtil.nullCheck(userId, ResponseEnum.PARAM_ERROR);
        return userBiz.detail(userId);
    }

    /**
     * 注册接口
     *
     * @param request：请求;获取ip用
     * @param userIO：用户信息
     * @return
     */
    @DescLog("注册")
    @PostMapping("/regist")
    public Result regist(HttpServletRequest request, @Valid RegistUserIO userIO) {
        //获取验证码 gtStatus == 0 验证码不正确 -1 验证码失效 1 成功
        int gtStatus = getGtStatus(userIO);
        //检查验证码
        checkGtStatus(gtStatus);
        //检查两次输入的密码是否一致
        if(! Objects.equals(userIO.getPassword(), userIO.getEnsurePassword())) {
            LOG.info("【两次输入的密码不一致】");
            throw new BussinessException(UserMsgEnum.PWD_AND_ENSUREPWD_NOT_SAMED);
        }
        //默认未激活状态
        userIO.setIsActive(UserActiveStatusEnum.USER_IS_NOT_ACTIVE.value());
        //加密密码
        userIO.setPassword(MD5Util.encode(userIO.getPassword()));
        //注册时的IP
        userIO.setIp(IPUtil.getIP(request));
        return userBiz.regist(userIO);
    }

    /**
     * 登录接口
     *
     * @param loginIO：登录信息
     * @return
     */
    @DescLog("登录")
    @PostMapping("/login")
    public Result login(@Valid LoginIO loginIO) {
        //获取验证码 gtStatus == 0 验证码不正确 -1 验证码失效 1 成功
        int gtStatus = getGtStatus(loginIO);
        //检查验证码
        checkGtStatus(gtStatus);
        loginIO.setPassword(MD5Util.encode(loginIO.getPassword()));
        return userBiz.login(loginIO);
    }

    /**
     * 修改个人信息
     *
     * @param userUpdateIO：个人信息
     * @return
     */
    @DescLog("修改个人信息")
    @PostMapping("/update")
    public Result update(@Valid @RequestBody UserUpdateIO userUpdateIO) {
        return userBiz.update(userUpdateIO);
    }

    /**
     * 更改邮箱
     *
     * @param updateEmailIO：邮箱验证码等参数
     * @return
     */
    @PostMapping("/updateEmail")
    public Result updateEmail(@Valid UpdateEmailIO updateEmailIO) {
        return userBiz.updateEmail(updateEmailIO);
    }

    /**
     * 修改密码
     *
     * @param updatePasswordIO：参数
     * @return
     */
    @PostMapping("/updatePassword")
    public Result updatePassword(@Valid UpdatePasswordIO updatePasswordIO) {
        return userBiz.updatePassword(updatePasswordIO);
    }

    /**
     * 激活用户
     *
     * @param userId：用户id
     * @return
     */
    @GetMapping("/activeUser")
    @DescLog("激活用户")
    public Result activeUser(String token, Long userId) {
        //检查用户id和token是否为null
        CommonExceptionUtil.nullCheck(token, ResponseEnum.PARAM_ERROR);
        CommonExceptionUtil.nullCheck(userId, ResponseEnum.PARAM_ERROR);
        //检查token和id是否匹配
        String tokenStr = basicCache.get(RedisEnum.getActiveUserKey(userId.toString()));
        CommonExceptionUtil.strNullCheck(tokenStr, UserMsgEnum.USER_NOT_EXISTS);
        //传来的token与redis里的token是否一致
        if (! Objects.equals(token, tokenStr)) {
            throw new BussinessException(UserMsgEnum.USER_NOT_EXISTS);
        }
        return userBiz.activeUser(userId);
    }

    /**
     * 检查此邮箱是否被占用
     *
     * @param email：电子邮箱
     * @return
     */
    @GetMapping("/checkEmailUnique")
    public Result checkEmailUnique(String email) {
        //检查非空
        CommonExceptionUtil.strNullCheck(email, UserMsgEnum.EMAIL_NULL_ERROR);
        return userBiz.checkEmailUnique(email);
    }

    /**
     * 找回密码接口
     *
     * @param forgetPasswordIO：参数
     * @return
     */
    @PostMapping("forgetPassword")
    public Result forgetPassword(@Valid ForgetPasswordIO forgetPasswordIO) {
        String code = basicCache.get(RedisEnum.getForgetPasswordKey(forgetPasswordIO.getEmail()));
        //Email验证码失效
        CommonExceptionUtil.nullCheck(code, ResponseEnum.VERIFICATION_ALREADY_EXPIRE);
        //Email验证码不正确
        if (! Objects.equals(code, forgetPasswordIO.getCode())) {
            LOG.info("修改密码验证码不正确【{}】", forgetPasswordIO.getEmail());
            throw new BussinessException(ResponseEnum.VERIFICATION_CODE_ERROR);
        }
        return userBiz.forgetPassword(forgetPasswordIO);
    }

    /**
     * 检查此邮箱是否注册了
     *
     * @param email：电子邮箱
     * @return
     */
    @GetMapping("/sendForgetPasswordMail")
    public Result sendForgetPasswordMail(String email) {
        //检查非空
        CommonExceptionUtil.strNullCheck(email, UserMsgEnum.EMAIL_NULL_ERROR);
        return userMailBiz.sendForgetPasswordMail(email);
    }

    /**
     * 更改密码--发送原邮件
     *
     * @param tokenIO：token&userId
     * @return
     */
    @GetMapping("/sendSourceMail")
    public Result sendSourceMail(TokenIO tokenIO) {
        return userMailBiz.sendSourceMail(tokenIO);
    }

    /**
     * 更改密码--发送目标邮件
     *
     * @param sendEmailIO：参数
     * @return
     */
    @GetMapping("/sendTargetMail")
    public Result sendTargetMail(@Valid SendEmailIO sendEmailIO) {
        return userMailBiz.sendTargetMail(sendEmailIO);
    }

    /**
     * 获取验证码状态
     *
     * @param geetestIO：极验证基本信息
     * @return：-1：失效；0：不正确；1：成功
     */
    private int getGtStatus(BaseGeetestIO geetestIO) {
        CommonExceptionUtil.strNullCheck(geetestIO.getToken(), ResponseEnum.TOKEN_IS_NULL);
        //检查验证码
        GeetestLib gtSdk = new GeetestLib(geetestConstant.getId(), geetestConstant.getKey(),
                geetestConstant.getNewfailback());
        //从redis中获取gt-server状态  //0 失败
        String gtServerStatusCode = basicCache.get(gtSdk.gtServerStatusSessionKey + geetestIO.getToken());

        //从Redis中取出验证码信息，若没取到则代表验证码失效
        if(StringUtils.isEmpty(gtServerStatusCode)/* || StringUtils.isEmpty(token)*/) {
            return GeetestStatusEnum.EXPIRED_GEETEST.value();
        }

        //0 失败
        int gtStatus;
        if (GeetestStatusEnum.SUCCESS_GEETEST.value() == Integer.parseInt(gtServerStatusCode)) {
            //gt-server正常，向gt-server进行二次验证
            gtStatus = gtSdk.enhencedValidateRequest(geetestIO.getGeetestChallenge(), geetestIO.getGeetestValidate(), geetestIO.getGeetestSeccode(), geetestIO.getToken());
        } else {
            // gt-server非正常情况下，进行failback模式验证
            gtStatus = gtSdk.failbackValidateRequest(geetestIO.getGeetestChallenge(), geetestIO.getGeetestValidate(), geetestIO.getGeetestSeccode());
        }
        return gtStatus;
    }

    /**
     * 检查验证码返回值
     * @return null:成功
     */
    private void checkGtStatus(int gtStatus) {
        if (gtStatus == GeetestStatusEnum.EXPIRED_GEETEST.value()) {
            LOG.info("【验证码已失效，请刷新页面】");
            throw new BussinessException(ResponseEnum.VERIFICATION_ALREADY_EXPIRE);
        }
        if (gtStatus == GeetestStatusEnum.ERROR_GEETEST.value()) {
            LOG.info("【验证码不正确】");
            throw new BussinessException(ResponseEnum.VERIFICATION_CODE_ERROR);
        }
    }
}
package com.chentongwei.core.user.biz.user;

import com.chentongwei.cache.redis.IBasicCache;
import com.chentongwei.common.enums.constant.RedisEnum;
import com.chentongwei.common.enums.msg.ResponseEnum;
import com.chentongwei.common.creator.ResultCreator;
import com.chentongwei.common.email.biz.MailBiz;
import com.chentongwei.common.email.enums.msg.EmailMsgEnum;
import com.chentongwei.common.email.enums.status.MailContentTypeEnum;
import com.chentongwei.common.email.enums.template.EmailTemplateEnum;
import com.chentongwei.common.entity.Result;
import com.chentongwei.common.entity.io.TokenIO;
import com.chentongwei.common.enums.constant.TimeEnum;
import com.chentongwei.common.exception.BussinessException;
import com.chentongwei.common.util.CommonExceptionUtil;
import com.chentongwei.common.util.StringUtil;
import com.chentongwei.core.user.dao.IUserDAO;
import com.chentongwei.core.user.entity.io.user.RegistUserIO;
import com.chentongwei.core.user.entity.io.user.SendEmailIO;
import com.chentongwei.core.user.entity.vo.user.UserVO;
import com.chentongwei.core.user.enums.msg.UserMsgEnum;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @author Wujun
 * @Project tucaole
 * @Description: 用户模块的emailBiz
 */
@Service
public class UserMailBiz {
    private static final Logger LOG = LogManager.getLogger("bizLog");

    @Autowired
    private IUserDAO userDAO;
    @Autowired
    private MailBiz mailBiz;
    @Autowired
    private IBasicCache basicCache;

    @Value("${active.user.url}")
    private String activeUserUrl;

    /**
     * 发送邮箱进行找回密码
     *
     * @param email：电子邮件
     * @return Result
     */
    public Result sendForgetPasswordMail(String email) {
        // 检查电子邮件是否注册了
        boolean flag = userDAO.getByEmail(email);
        CommonExceptionUtil.flagCheck(! flag, UserMsgEnum.EMAIL_NOT_REGIST);
        //为什么不用get？
        //因为get拿到的是string类型，还需要强制转换成int，并且还要判断非空才能转，所以直接increment，参数0代表不加，也就是返回原值
        long emailCount = basicCache.increment(RedisEnum.getForgetPwdEmailCountKey(email), 0);
        // 每天最多5次邮箱
        if (emailCount >= TimeEnum.FIVE_TIME.value()) {
            LOG.info("找回密码每天最多发送5次【{}】", email);
            throw new BussinessException(UserMsgEnum.COUNT_EXCEED);
        }
        //发送邮件
        String code = sendEmailAndRtnCode(EmailTemplateEnum.FORGET_PASSWORD_TEMPLATE.value(), email);
        //缓存到redis 2小时失效
        basicCache.set(RedisEnum.getForgetPasswordKey(email), code, TimeEnum.TWO_HOUR.value(), TimeUnit.HOURS);
        //每次加1
        basicCache.increment(RedisEnum.getForgetPwdEmailCountKey(email), 1);
        return ResultCreator.getSuccess();
    }

    /**
     * 修改邮箱--原邮箱验证
     *
     * @param tokenIO：参数
     * @return
     */
    public Result sendSourceMail(TokenIO tokenIO) {
        //根据userId查出email
        UserVO userVO = userDAO.getById(tokenIO.getUserId());
        //为什么不用get？
        //因为get拿到的是string类型，还需要强制转换成int，并且还要判断非空才能转，所以直接increment，参数0代表不加，也就是返回原值
        long emailCount = basicCache.increment(RedisEnum.getSendSourceEmailCountKey(userVO.getEmail()), 0);
        // 每天最多更改3次邮箱
        if (emailCount >= TimeEnum.THREE_TIME.value()) {
            LOG.info("更改邮箱-每天最多更改3次【{}】", userVO.getEmail());
            throw new BussinessException(UserMsgEnum.COUNT_EXCEED);
        }
        //发送邮件
        String code = sendEmailAndRtnCode(EmailTemplateEnum.UPDATE_EMAIL_TEMPLATE.value(), userVO.getEmail());
        //缓存到redis 2小时失效
        basicCache.set(RedisEnum.getSendSourceEmailKey(userVO.getEmail()), code, TimeEnum.TWO_HOUR.value(), TimeUnit.HOURS);
        //每次加1
        basicCache.increment(RedisEnum.getSendSourceEmailCountKey(userVO.getEmail()), 1);
        return ResultCreator.getSuccess();
    }

    /**
     * 修改邮箱--新邮箱验证
     *
     * @param sendEmailIO：参数
     * @return
     */
    public Result sendTargetMail(SendEmailIO sendEmailIO) {
        //根据userId查出email
        UserVO userVO = userDAO.getById(sendEmailIO.getUserId());
        //检查邮箱是否发的是原邮箱，而不是新邮箱
        if (Objects.equals(userVO.getEmail(), sendEmailIO.getEmail())) {
            LOG.info("新邮箱【】不能和旧邮箱相同", sendEmailIO.getEmail());
            throw new BussinessException(UserMsgEnum.NEW_EMAIL_SAME_AS_OLD_EMAIL_ERROR);
        }
        //检查原邮件验证码是否正确
        String sourceCode = basicCache.get(RedisEnum.getSendSourceEmailKey(userVO.getEmail()));

        CommonExceptionUtil.strNullCheck(sourceCode, ResponseEnum.VERIFICATION_ALREADY_EXPIRE);
        if (! Objects.equals(sendEmailIO.getCode(), sourceCode)) {
            LOG.info("更改邮箱-原邮箱验证码不正确【{}】", userVO.getEmail());
            throw new BussinessException(ResponseEnum.VERIFICATION_CODE_ERROR);
        }

        // 检查电子邮件是否注册了
        boolean flag = userDAO.getByEmail(sendEmailIO.getEmail());
        CommonExceptionUtil.flagCheck(flag, UserMsgEnum.EMAIL_ALREADY_EXISITS);

        //发送邮件
        String code = sendEmailAndRtnCode(EmailTemplateEnum.UPDATE_EMAIL_TEMPLATE.value(), sendEmailIO.getEmail());

        //缓存到redis 2小时失效
        basicCache.set(RedisEnum.getSendTargetEmailKey(sendEmailIO.getEmail()), code, TimeEnum.TWO_HOUR.value(), TimeUnit.HOURS);
        //移除原邮件的验证码，防止攻击
        basicCache.deleteKey(RedisEnum.getSendSourceEmailKey(userVO.getEmail()));
        return ResultCreator.getSuccess();
    }

    /**
     * 发送验证码到email
     *
     * @param templateName：模板名称
     * @param email：电子邮件
     * @return code：验证码
     */
    private String sendEmailAndRtnCode(final String templateName, final String email) {
        //发送邮件
        //获取随机六位验证码
        String code = StringUtil.getEmailCode();
        Map<String, Object> params = new HashMap<>();
        // 发送六位验证码到邮箱
        params.put("code", code);
        sendEmail(templateName, email, params);
        return code;
    }

    /**
     * 注册时发送邮件
     *
     * @param userIO：注册用户
     */
    public void sendRegistMail(RegistUserIO userIO, String token) {
        //激活用户路径
        String href = String.format(activeUserUrl, token, userIO.getId());
        Map<String, Object> params = new HashMap<>();
        params.put("href", href);
        sendEmail(EmailTemplateEnum.REGIST_TEMPLATE.value(), userIO.getEmail(), params);
    }

    /**
     * 发送邮件
     *
     * @param templateName：模板名称
     * @param email：电子邮件
     * @param params：模板参数
     */
    private void sendEmail(final String templateName, final String email, final Map<String, Object> params) {
        mailBiz
            .title(EmailMsgEnum.EMAIL_TITLE.getValue())
            .to(email)
            .contentType(MailContentTypeEnum.TEMPLATE)
            .send(templateName, params);
    }
}

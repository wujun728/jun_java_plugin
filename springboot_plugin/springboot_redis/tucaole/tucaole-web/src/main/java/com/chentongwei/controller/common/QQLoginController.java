package com.chentongwei.controller.common;

import com.chentongwei.common.creator.ResultCreator;
import com.chentongwei.common.entity.Result;
import com.chentongwei.common.util.IPUtil;
import com.chentongwei.core.user.biz.user.UserBiz;
import com.chentongwei.core.user.entity.io.user.RegistUserIO;
import com.chentongwei.core.user.enums.status.UserActiveStatusEnum;
import com.qq.connect.QQConnectException;
import com.qq.connect.api.OpenID;
import com.qq.connect.javabeans.AccessToken;
import com.qq.connect.oauth.Oauth;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Wujun
 * @Project tucaole
 * @Description: QQ 互联
 **/
@RestController
@RequestMapping("/common/qqLogin")
public class QQLoginController {
    /**
     * LOG
     */
    private static final Logger LOG = LogManager.getLogger(QQLoginController.class);

    @Autowired
    private UserBiz userBiz;

    /**
     * 前端QQ登录请求路径
     * @param request：请求
     * @param response：响应
     * @throws IOException：IO异常
     */
    @GetMapping("")
    public void login(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            response.sendRedirect(new Oauth().getAuthorizeURL(request));
        } catch (QQConnectException e) {
            e.printStackTrace();
        }
    }

    /**
     * 登录之后QQ互联回调地址
     * @param request：请求
     * @throws IOException：IO异常
     */
    @GetMapping("/afterLogin")
    public Result afterLogin(HttpServletRequest request) throws IOException {
        try {
            AccessToken accessTokenObj = (new Oauth()).getAccessTokenByRequest(request);
            String accessToken   = null,
                    openID        = null;
            long tokenExpireIn = 0L;

            if (accessTokenObj.getAccessToken().equals("")) {
//                我们的网站被CSRF攻击了或者用户取消了授权
//                做一些数据统计工作
                LOG.info("没有获取到响应参数");
            } else {
                accessToken = accessTokenObj.getAccessToken();
                tokenExpireIn = accessTokenObj.getExpireIn();

                LOG.info("accessToken=【{}】", accessToken);
                LOG.info("tokenExpireIn=【{}】", tokenExpireIn);

                // 利用获取到的accessToken 去获取当前用的openid
                OpenID openIDObj =  new OpenID(accessToken);
                openID = openIDObj.getUserOpenID();
                LOG.info("openID=【{}】", openID);

                RegistUserIO registUserIO = new RegistUserIO();
                registUserIO.setAccessToken(accessToken);
                registUserIO.setOpenId(openID);
                registUserIO.setIsActive(UserActiveStatusEnum.USER_IS_ACTIVE.value());
                registUserIO.setIp(IPUtil.getIP(request));
                return userBiz.qqRegist(registUserIO);
            }
        } catch (QQConnectException e) {
            e.printStackTrace();
        }
        return ResultCreator.getFail();
    }
}

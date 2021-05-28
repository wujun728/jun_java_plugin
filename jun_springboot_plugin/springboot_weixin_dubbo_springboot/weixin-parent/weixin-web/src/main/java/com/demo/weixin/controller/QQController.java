package com.demo.weixin.controller;

import com.demo.weixin.api.IQQService;
import com.demo.weixin.api.ICacheService;
import com.demo.weixin.exception.WeixinException;
import com.qq.connect.QQConnectException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author Wujun
 * @description QQ服务
 * @date 2017/7/25
 * @since 1.0
 */
@Controller
@RequestMapping("/qq")
public class QQController {

    @Autowired
    private IQQService qqService;

    @Autowired
    private ICacheService cacheService;

    @RequestMapping("/toAuth.html")
    public String toQQAuth(@RequestParam String from) throws QQConnectException, WeixinException {

        // 获取qq授权地址
        String authUrl = qqService.getAuthUrl(from);

        return "redirect:" + authUrl;
    }

    @RequestMapping("/callBack.html")
    public String getAuthUserOpenId(@RequestParam String code, @RequestParam String state) throws QQConnectException {

        if (StringUtils.isAnyBlank(code, state)) {
            throw new IllegalArgumentException("参数缺失");
        }
        // 获取授权成功后的回调地址
        String callBackUrl = cacheService.getAuthCallBackUrl(state);
        if (StringUtils.isBlank(callBackUrl)) {
            throw new IllegalArgumentException("state参数错误");
        }
        // 查询用户的openId
        String queryString = "code=" + code + "&state=" + state;
        String openId = qqService.getQQUserOpenId(queryString, state);

        return "redirect:" + callBackUrl + "?openId=" + openId;
    }

}

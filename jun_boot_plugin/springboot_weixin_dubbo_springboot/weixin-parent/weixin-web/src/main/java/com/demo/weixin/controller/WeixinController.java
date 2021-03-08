package com.demo.weixin.controller;

import com.demo.weixin.api.ICacheService;
import com.demo.weixin.api.IWeixinService;
import com.demo.weixin.enums.ClientType;
import com.demo.weixin.enums.ProjectType;
import com.demo.weixin.enums.WebchatAuthScope;
import com.demo.weixin.exception.WeixinException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;


/**
 * @author Wujun
 * @description 微信公众号服务
 * @date 2017/7/25
 * @since 1.0
 */
@Controller
@RequestMapping("/wx")
public class WeixinController {

    @Autowired
    private ICacheService cacheService;

    @Autowired
    private IWeixinService weixinService;

    /**
     * 获取授权的地址
     *
     * @param projectType 项目类型
     * @param clientType  客户端类型
     * @param scope       微信公众号授权scope {@link com.demo.weixin.enums.WebchatAuthScope}
     * @return
     */
    @RequestMapping("/{projectType}/{clientType}/toAuth.html")
    public String toAuth(@PathVariable String projectType, @PathVariable String clientType, String scope,
                         String from) throws UnsupportedEncodingException, WeixinException {
        if (StringUtils.isBlank(from)) {
            throw new WeixinException(-1, "授权成功后的回调地址不能为空");
        }
        ProjectType projectTypeEnum = ProjectType.findByLowerCaseValue(projectType);
        ClientType clientTypeEnum = ClientType.findByLowerCaseValue(clientType);
        // 获取授权地址
        String authUrl = weixinService.getAuthUrl(projectTypeEnum, clientTypeEnum, from, WebchatAuthScope.getScopeByValue(scope));
        return "redirect:" + authUrl;
    }

    /**
     * 回调(获取用户信息)
     *
     * @param projectType 项目类型
     * @param clientType  客户端类型
     * @param code        微信授权码
     * @return
     */
    @RequestMapping(value = "/{projectType}/{clientType}/callBack.html")
    public String weixinAuthCallBack(@PathVariable String projectType, @PathVariable String clientType, @RequestParam("state") String state,
                                     @RequestParam("code") String code) throws IOException, URISyntaxException, WeixinException {

        ProjectType projectTypeEnum = ProjectType.findByLowerCaseValue(projectType);
        ClientType clientTypeEnum = ClientType.findByLowerCaseValue(clientType);

        String unionId = weixinService.getWeixinUserUnionId(projectTypeEnum, clientTypeEnum, code);
        String callBackUrl = cacheService.getAuthCallBackUrl(state);

        return "redirect:" + callBackUrl + "?unionId=" + unionId;
    }
}

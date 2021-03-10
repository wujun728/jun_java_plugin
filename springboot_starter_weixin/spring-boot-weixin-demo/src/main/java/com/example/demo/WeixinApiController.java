package com.example.demo;

import com.jfinal.weixin.sdk.api.ApiResult;
import com.jfinal.weixin.sdk.api.MenuApi;
import net.dreamlu.weixin.annotation.WxApi;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@WxApi("/weixin/api")
public class WeixinApiController {

    @GetMapping("menu")
    @ResponseBody
    public String getMenu() {
        ApiResult apiResult = MenuApi.getMenu();
        return apiResult.getJson();
    }

}

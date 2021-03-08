package com.example.demo;

import com.jfinal.wxaapp.msg.bean.WxaImageMsg;
import com.jfinal.wxaapp.msg.bean.WxaTextMsg;
import com.jfinal.wxaapp.msg.bean.WxaUserEnterSessionMsg;
import net.dreamlu.weixin.annotation.WxMsgController;
import net.dreamlu.weixin.properties.DreamWeixinProperties;
import net.dreamlu.weixin.spring.DreamWxaMsgController;
import org.springframework.beans.factory.annotation.Autowired;

@WxMsgController("/weixin/wxa")
public class WxaController extends DreamWxaMsgController {

    @Autowired
    private DreamWeixinProperties weixinProperties;

    @Override
    protected void processTextMsg(WxaTextMsg wxaTextMsg) {

    }

    @Override
    protected void processImageMsg(WxaImageMsg wxaImageMsg) {

    }

    @Override
    protected void processUserEnterSessionMsg(WxaUserEnterSessionMsg wxaUserEnterSessionMsg) {

    }
}

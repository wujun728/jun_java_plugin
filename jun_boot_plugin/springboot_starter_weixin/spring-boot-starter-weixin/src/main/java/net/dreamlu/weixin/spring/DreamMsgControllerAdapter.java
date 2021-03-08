/**
 * Copyright (c) 2011-2014, James Zhan 詹波 (jfinal@126.com).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */

package net.dreamlu.weixin.spring;

import com.jfinal.weixin.iot.msg.InEquDataMsg;
import com.jfinal.weixin.iot.msg.InEqubindEvent;
import com.jfinal.weixin.sdk.msg.in.*;
import com.jfinal.weixin.sdk.msg.in.card.*;
import com.jfinal.weixin.sdk.msg.in.event.*;
import com.jfinal.weixin.sdk.msg.in.speech_recognition.InSpeechRecognitionResults;

/**
 * MsgControllerAdapter 对 MsgController 部分方法提供了默认实现，
 * 以便开发者不去关注 MsgController 中不需要处理的抽象方法，节省代码量
 */
public abstract class DreamMsgControllerAdapter extends MsgController {
    //  关注/取消关注事件
    @Override
    protected abstract void processInFollowEvent(InFollowEvent inFollowEvent);

    // 接收文本消息事件
    @Override
    protected abstract void processInTextMsg(InTextMsg inTextMsg);

    // 自定义菜单事件
    @Override
    protected abstract void processInMenuEvent(InMenuEvent inMenuEvent);

    // 接收图片消息事件
    @Override
    protected void processInImageMsg(InImageMsg inImageMsg) {
        renderDefault();
    }

    // 接收语音消息事件
    @Override
    protected void processInVoiceMsg(InVoiceMsg inVoiceMsg) {
        renderDefault();
    }

    // 接收视频消息事件
    @Override
    protected void processInVideoMsg(InVideoMsg inVideoMsg) {
        renderDefault();
    }

    // 接收地理位置消息事件
    @Override
    protected void processInLocationMsg(InLocationMsg inLocationMsg) {
        renderDefault();
    }

    // 接收链接消息事件
    @Override
    protected void processInLinkMsg(InLinkMsg inLinkMsg) {
        renderDefault();
    }

    // 扫描带参数二维码事件
    @Override
    protected void processInQrCodeEvent(InQrCodeEvent inQrCodeEvent) {
        renderDefault();
    }

    // 上报地理位置事件
    @Override
    protected void processInLocationEvent(InLocationEvent inLocationEvent) {
        renderDefault();
    }

    // 接收语音识别结果，与 InVoiceMsg 唯一的不同是多了一个 Recognition 标记
    @Override
    protected void processInSpeechRecognitionResults(InSpeechRecognitionResults inSpeechRecognitionResults) {
        renderDefault();
    }

    // 在模版消息发送任务完成后事件
    @Override
    protected void processInTemplateMsgEvent(InTemplateMsgEvent inTemplateMsgEvent) {
        renderDefault();
    }

    // 群发完成事件
    @Override
    protected void processInMassEvent(InMassEvent inMassEvent) {
        renderDefault();
    }

    // 接收小视频消息
    @Override
    protected void processInShortVideoMsg(InShortVideoMsg inShortVideoMsg) {
        renderDefault();
    }

    // 接客服入会话事件
    @Override
    protected void processInCustomEvent(InCustomEvent inCustomEvent) {
        renderDefault();
    }

    // 用户进入摇一摇界面，在“周边”页卡下摇一摇时事件
    @Override
    protected void processInShakearoundUserShakeEvent(InShakearoundUserShakeEvent inShakearoundUserShakeEvent) {
        renderDefault();
    }

    // 资质认证事件
    @Override
    protected void processInVerifySuccessEvent(InVerifySuccessEvent inVerifySuccessEvent) {
        renderDefault();
    }

    // 资质认证失败事件
    @Override
    protected void processInVerifyFailEvent(InVerifyFailEvent inVerifyFailEvent){
        renderDefault();
    }

    // 门店在审核通过后下发消息事件
    @Override
    protected void processInPoiCheckNotifyEvent(InPoiCheckNotifyEvent inPoiCheckNotifyEvent) {
        renderDefault();
    }

    // WIFI连网后下发消息 by unas at 2016-1-29
    @Override
    protected void processInWifiEvent(InWifiEvent inWifiEvent) {
        renderDefault();
    }

    // 微信会员卡积分变更事件
    @Override
    protected void processInUpdateMemberCardEvent(InUpdateMemberCardEvent msg) {
        renderDefault();
    }

    // 微信会员卡快速买单事件
    @Override
    protected void processInUserPayFromCardEvent(InUserPayFromCardEvent msg) {
        renderDefault();
    }

    // 微信小店订单支付成功接口事件
    @Override
    protected void processInMerChantOrderEvent(InMerChantOrderEvent inMerChantOrderEvent) {
        renderDefault();
    }

    // 没有找到对应的事件消息
    @Override
    protected void processIsNotDefinedEvent(InNotDefinedEvent inNotDefinedEvent) {
        renderDefault();
    }

    // 没有找到对应的消息
    @Override
    protected void processIsNotDefinedMsg(InNotDefinedMsg inNotDefinedMsg) {
        renderDefault();
    }

    @Override
    protected void processInUserGiftingCardEvent(InUserGiftingCardEvent msg) {
        renderDefault();
    }

    @Override
    protected void processInUserGetCardEvent(InUserGetCardEvent msg) {
        renderDefault();
    }

    @Override
    protected void processInUserConsumeCardEvent(InUserConsumeCardEvent msg) {
        renderDefault();
    }

    @Override
    protected void processInCardSkuRemindEvent(InCardSkuRemindEvent msg) {
        renderDefault();
    }

    @Override
    protected void processInCardPayOrderEvent(InCardPayOrderEvent msg) {
        renderDefault();
    }

    @Override
    protected void processInCardPassCheckEvent(InCardPassCheckEvent msg) {
        renderDefault();
    }
    
    
    @Override
    protected void processInUserCardEvent(InUserCardEvent inUserCardEvent) {
        renderDefault();
    }

    /**
     * 处理微信硬件绑定和解绑事件
     * @param msg 处理微信硬件绑定和解绑事件
     */
    @Override
    protected void processInEqubindEvent(InEqubindEvent msg) {
        renderDefault();
    }
    
    /**
     * 处理微信硬件发来数据
     * @param msg 处理微信硬件发来数据
     */
    @Override
    protected void processInEquDataMsg(InEquDataMsg msg) {
        renderDefault();
    }
    
    /**
     * 方便没有使用的api返回“”避免出现，该公众号暂时不能提供服务
     *
     * 可重写该方法
     */
    protected void renderDefault() {
        WebUtils.renderText(response, "");
    }
}

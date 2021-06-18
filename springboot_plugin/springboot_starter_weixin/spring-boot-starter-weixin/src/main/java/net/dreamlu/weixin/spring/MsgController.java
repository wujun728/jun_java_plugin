package net.dreamlu.weixin.spring;

import com.jfinal.kit.HttpKit;
import com.jfinal.kit.StrKit;
import com.jfinal.weixin.iot.msg.InEquDataMsg;
import com.jfinal.weixin.iot.msg.InEqubindEvent;
import com.jfinal.weixin.sdk.api.ApiConfigKit;
import com.jfinal.weixin.sdk.kit.MsgEncryptKit;
import com.jfinal.weixin.sdk.msg.InMsgParser;
import com.jfinal.weixin.sdk.msg.in.*;
import com.jfinal.weixin.sdk.msg.in.card.*;
import com.jfinal.weixin.sdk.msg.in.event.*;
import com.jfinal.weixin.sdk.msg.in.speech_recognition.InSpeechRecognitionResults;
import com.jfinal.weixin.sdk.msg.out.OutMsg;
import com.jfinal.weixin.sdk.msg.out.OutTextMsg;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public abstract class MsgController {
    private static final Log logger = LogFactory.getLog(WebUtils.class);
    // 本次请求 xml 解析后的 InMsg 对象
    protected InMsg msg = null;
    @Autowired
    protected HttpServletRequest request;
    @Autowired
    protected HttpServletResponse response;

    /**
     * weixin 公众号服务器调用唯一入口，即在开发者中心输入的 URL 必须要指向此 action
     * @param imXmlMsg imXmlMsg
     * @return {ResponseEntity}
     */
    @RequestMapping("")
    public ResponseEntity<Void> index(@RequestBody String imXmlMsg) {
        // 开发模式输出微信服务发送过来的  xml 消息
        if (ApiConfigKit.isDevMode()) {
            System.out.println("接收消息:");
            System.out.println(imXmlMsg);
        }
        if (StrKit.isBlank(imXmlMsg)) {
            throw new RuntimeException("请不要在浏览器中请求该连接,调试请查看WIKI:http://git.oschina.net/jfinal/jfinal-weixin/wikis/JFinal-weixin-demo%E5%92%8C%E8%B0%83%E8%AF%95");
        }
        // 是否需要解密消息
        if (ApiConfigKit.getApiConfig().isEncryptMessage()) {
            imXmlMsg = MsgEncryptKit.decrypt(imXmlMsg,
                    request.getParameter("timestamp"),
                    request.getParameter("nonce"),
                    request.getParameter("msg_signature"));
        }

        // 解析消息并根据消息类型分发到相应的处理方法
        msg = InMsgParser.parse(imXmlMsg);

        if (msg instanceof InTextMsg)
            processInTextMsg((InTextMsg) msg);
        else if (msg instanceof InImageMsg)
            processInImageMsg((InImageMsg) msg);
        else if (msg instanceof InSpeechRecognitionResults)  //update by unas at 2016-1-29, 由于继承InVoiceMsg，需要在InVoiceMsg前判断类型
            processInSpeechRecognitionResults((InSpeechRecognitionResults) msg);
        else if (msg instanceof InVoiceMsg)
            processInVoiceMsg((InVoiceMsg) msg);
        else if (msg instanceof InVideoMsg)
            processInVideoMsg((InVideoMsg) msg);
        else if (msg instanceof InShortVideoMsg)   //支持小视频
            processInShortVideoMsg((InShortVideoMsg) msg);
        else if (msg instanceof InLocationMsg)
            processInLocationMsg((InLocationMsg) msg);
        else if (msg instanceof InLinkMsg)
            processInLinkMsg((InLinkMsg) msg);
        else if (msg instanceof InCustomEvent)
            processInCustomEvent((InCustomEvent) msg);
        else if (msg instanceof InFollowEvent)
            processInFollowEvent((InFollowEvent) msg);
        else if (msg instanceof InQrCodeEvent)
            processInQrCodeEvent((InQrCodeEvent) msg);
        else if (msg instanceof InLocationEvent)
            processInLocationEvent((InLocationEvent) msg);
        else if (msg instanceof InMassEvent)
            processInMassEvent((InMassEvent) msg);
        else if (msg instanceof InMenuEvent)
            processInMenuEvent((InMenuEvent) msg);
        else if (msg instanceof InTemplateMsgEvent)
            processInTemplateMsgEvent((InTemplateMsgEvent) msg);
        else if (msg instanceof InShakearoundUserShakeEvent)
            processInShakearoundUserShakeEvent((InShakearoundUserShakeEvent) msg);
        else if (msg instanceof InVerifySuccessEvent)
            processInVerifySuccessEvent((InVerifySuccessEvent) msg);
        else if (msg instanceof InVerifyFailEvent)
            processInVerifyFailEvent((InVerifyFailEvent) msg);
        else if (msg instanceof InPoiCheckNotifyEvent)
            processInPoiCheckNotifyEvent((InPoiCheckNotifyEvent) msg);
        else if (msg instanceof InWifiEvent)
            processInWifiEvent((InWifiEvent) msg);
        else if (msg instanceof InUserCardEvent)
            processInUserCardEvent((InUserCardEvent) msg);
        else if (msg instanceof InUpdateMemberCardEvent)
            processInUpdateMemberCardEvent((InUpdateMemberCardEvent) msg);
        else if (msg instanceof InUserPayFromCardEvent)
            processInUserPayFromCardEvent((InUserPayFromCardEvent) msg);
        else if (msg instanceof InMerChantOrderEvent)
            processInMerChantOrderEvent((InMerChantOrderEvent) msg);
        else if (msg instanceof InCardPassCheckEvent)
            processInCardPassCheckEvent((InCardPassCheckEvent) msg);
        else if (msg instanceof InCardPayOrderEvent)
            processInCardPayOrderEvent((InCardPayOrderEvent) msg);
        else if (msg instanceof InCardSkuRemindEvent)
            processInCardSkuRemindEvent((InCardSkuRemindEvent) msg);
        else if (msg instanceof InUserConsumeCardEvent)
            processInUserConsumeCardEvent((InUserConsumeCardEvent) msg);
        else if (msg instanceof InUserGetCardEvent)
            processInUserGetCardEvent((InUserGetCardEvent) msg);
        else if (msg instanceof InUserGiftingCardEvent)
            processInUserGiftingCardEvent((InUserGiftingCardEvent) msg);
        else if (msg instanceof InEqubindEvent)
            processInEqubindEvent((InEqubindEvent) msg);
        else if (msg instanceof InEquDataMsg)
            processInEquDataMsg((InEquDataMsg) msg);
            //===================微信智能硬件========================//
        else if (msg instanceof InEqubindEvent)
            processInEqubindEvent((InEqubindEvent) msg);
        else if (msg instanceof InEquDataMsg)
            processInEquDataMsg((InEquDataMsg) msg);
            //===================微信智能硬件========================//
        else if (msg instanceof InNotDefinedEvent) {
            logger.error("未能识别的事件类型。 消息 xml 内容为：\n" + imXmlMsg);
            processIsNotDefinedEvent((InNotDefinedEvent) msg);
        } else if (msg instanceof InNotDefinedMsg) {
            logger.error("未能识别的消息类型。 消息 xml 内容为：\n" + imXmlMsg);
            processIsNotDefinedMsg((InNotDefinedMsg) msg);
        }
        return ResponseEntity.ok().build();
    }

    /**
     * 在接收到微信服务器的 InMsg 消息后后响应 OutMsg 消息
     *
     * @param outMsg 输出对象
     */
    public void render(OutMsg outMsg) {
        String outMsgXml = outMsg.toXml();
        // 开发模式向控制台输出即将发送的 OutMsg 消息的 xml 内容
        if (ApiConfigKit.isDevMode()) {
            System.out.println("发送消息:");
            System.out.println(outMsgXml);
            System.out.println("--------------------------------------------------------------------------------\n");
        }

        // 是否需要加密消息
        if (ApiConfigKit.getApiConfig().isEncryptMessage()) {
            outMsgXml = MsgEncryptKit.encrypt(outMsgXml,
                    request.getParameter("timestamp"),
                    request.getParameter("nonce"));
        }
        WebUtils.renderText(response, outMsgXml);
    }

    /**
     * 消息输出
     * @param content 输出的消息
     */
    public void renderOutTextMsg(String content) {
        OutTextMsg outMsg = new OutTextMsg(msg);
        outMsg.setContent(content);
        render(outMsg);
    }

    public String getInMsgXml(HttpServletRequest request) {
        String inMsgXml = HttpKit.readData(request);
        // 是否需要解密消息
        if (ApiConfigKit.getApiConfig().isEncryptMessage()) {
            inMsgXml = MsgEncryptKit.decrypt(inMsgXml,
                    request.getParameter("timestamp"),
                    request.getParameter("nonce"),
                    request.getParameter("msg_signature"));
        }
        if (StrKit.isBlank(inMsgXml)) {
            throw new RuntimeException("请不要在浏览器中请求该连接,调试请查看WIKI:http://git.oschina.net/jfinal/jfinal-weixin/wikis/JFinal-weixin-demo%E5%92%8C%E8%B0%83%E8%AF%95");
        }
        return inMsgXml;
    }

    /**
     * 处理接收到的文本消息
     * @param inTextMsg 处理接收到的文本消息
     */
    protected abstract void processInTextMsg(InTextMsg inTextMsg);

    /**
     * 处理接收到的图片消息
     * @param inImageMsg 处理接收到的图片消息
     */
    protected abstract void processInImageMsg(InImageMsg inImageMsg);

    /**
     * 处理接收到的语音消息
     * @param inVoiceMsg 处理接收到的语音消息
     */
    protected abstract void processInVoiceMsg(InVoiceMsg inVoiceMsg);

    /**
     * 处理接收到的视频消息
     * @param inVideoMsg 处理接收到的视频消息
     */
    protected abstract void processInVideoMsg(InVideoMsg inVideoMsg);

    /**
     * 处理接收到的小视频消息
     * @param inShortVideoMsg 处理接收到的小视频消息
     */
    protected abstract void processInShortVideoMsg(InShortVideoMsg inShortVideoMsg);

    /**
     * 处理接收到的地址位置消息
     * @param inLocationMsg 处理接收到的地址位置消息
     */
    protected abstract void processInLocationMsg(InLocationMsg inLocationMsg);

    /**
     * 处理接收到的链接消息
     * @param inLinkMsg 处理接收到的链接消息
     */
    protected abstract void processInLinkMsg(InLinkMsg inLinkMsg);

    /**
     * 处理接收到的多客服管理事件
     * @param inCustomEvent 处理接收到的多客服管理事件
     */
    protected abstract void processInCustomEvent(InCustomEvent inCustomEvent);

    /**
     * 处理接收到的关注/取消关注事件
     * @param inFollowEvent 处理接收到的关注/取消关注事件
     */
    protected abstract void processInFollowEvent(InFollowEvent inFollowEvent);

    /**
     * 处理接收到的扫描带参数二维码事件
     * @param inQrCodeEvent 处理接收到的扫描带参数二维码事件
     */
    protected abstract void processInQrCodeEvent(InQrCodeEvent inQrCodeEvent);

    /**
     * 处理接收到的上报地理位置事件
     * @param inLocationEvent 处理接收到的上报地理位置事件
     */
    protected abstract void processInLocationEvent(InLocationEvent inLocationEvent);

    /**
     * 处理接收到的群发任务结束时通知事件
     * @param inMassEvent 处理接收到的群发任务结束时通知事件
     */
    protected abstract void processInMassEvent(InMassEvent inMassEvent);

    /**
     * 处理接收到的自定义菜单事件
     * @param inMenuEvent 处理接收到的自定义菜单事件
     */
    protected abstract void processInMenuEvent(InMenuEvent inMenuEvent);

    /**
     * 处理接收到的语音识别结果
     * @param inSpeechRecognitionResults 处理接收到的语音识别结果
     */
    protected abstract void processInSpeechRecognitionResults(InSpeechRecognitionResults inSpeechRecognitionResults);

    /**
     * 处理接收到的模板消息是否送达成功通知事件
     * @param inTemplateMsgEvent 处理接收到的模板消息是否送达成功通知事件
     */
    protected abstract void processInTemplateMsgEvent(InTemplateMsgEvent inTemplateMsgEvent);

    /**
     * 处理微信摇一摇事件
     * @param inShakearoundUserShakeEvent 处理微信摇一摇事件
     */
    protected abstract void processInShakearoundUserShakeEvent(InShakearoundUserShakeEvent inShakearoundUserShakeEvent);

    /**
     * 资质认证成功 || 名称认证成功 || 年审通知 || 认证过期失效通知
     * @param inVerifySuccessEvent 资质认证成功 || 名称认证成功 || 年审通知 || 认证过期失效通知
     */
    protected abstract void processInVerifySuccessEvent(InVerifySuccessEvent inVerifySuccessEvent);

    /**
     * 资质认证失败 || 名称认证失败
     * @param inVerifyFailEvent 资质认证失败 || 名称认证失败
     */
    protected abstract void processInVerifyFailEvent(InVerifyFailEvent inVerifyFailEvent);

    /**
     * 门店在审核事件消息
     * @param inPoiCheckNotifyEvent 门店在审核事件消息
     */
    protected abstract void processInPoiCheckNotifyEvent(InPoiCheckNotifyEvent inPoiCheckNotifyEvent);

    /**
     * WIFI连网后下发消息 by unas at 2016-1-29
     * @param inWifiEvent WIFI连网后下发消息
     */
    protected abstract void processInWifiEvent(InWifiEvent inWifiEvent);

    /**
     * 1. 微信会员卡二维码扫描领取接口
     * 2. 微信会员卡激活接口
     * 3. 卡券删除事件推送
     * 4. 从卡券进入公众号会话事件推送
     * @param inUserCardEvent InUserCardEvent
     */
    protected abstract void processInUserCardEvent(InUserCardEvent inUserCardEvent);

    /**
     * 微信会员卡积分变更
     * @param inUpdateMemberCardEvent 微信会员卡积分变更
     */
    protected abstract void processInUpdateMemberCardEvent(InUpdateMemberCardEvent inUpdateMemberCardEvent);

    /**
     * 微信会员卡快速买单
     * @param inUserPayFromCardEvent 微信会员卡快速买单
     */
    protected abstract void processInUserPayFromCardEvent(InUserPayFromCardEvent inUserPayFromCardEvent);

    /**
     * 微信小店订单支付成功接口消息
     * @param inMerChantOrderEvent 微信小店订单支付成功接口消息
     */
    protected abstract void processInMerChantOrderEvent(InMerChantOrderEvent inMerChantOrderEvent);

    //
    /**
     * 没有找到对应的事件消息
     * @param inNotDefinedEvent 没有对应的事件消息
     */
    protected abstract void processIsNotDefinedEvent(InNotDefinedEvent inNotDefinedEvent);

    /**
     * 没有找到对应的消息
     * @param inNotDefinedMsg 没有对应消息
     */
    protected abstract void processIsNotDefinedMsg(InNotDefinedMsg inNotDefinedMsg);

    /**
     * 卡券转赠事件推送
     * @param msg 卡券转赠事件推送
     */
    protected abstract void processInUserGiftingCardEvent(InUserGiftingCardEvent msg);

    /**
     * 卡券领取事件推送
     * @param msg 卡券领取事件推送
     */
    protected abstract void processInUserGetCardEvent(InUserGetCardEvent msg);

    /**
     * 卡券核销事件推送
     * @param msg 卡券核销事件推送
     */
    protected abstract void processInUserConsumeCardEvent(InUserConsumeCardEvent msg);

    /**
     * 卡券库存报警事件
     * @param msg 卡券库存报警事件
     */
    protected abstract void processInCardSkuRemindEvent(InCardSkuRemindEvent msg);

    /**
     * 券点流水详情事件
     * @param msg 券点流水详情事件
     */
    protected abstract void processInCardPayOrderEvent(InCardPayOrderEvent msg);

    /**
     * 审核事件推送
     * @param msg 审核事件推送
     */
    protected abstract void processInCardPassCheckEvent(InCardPassCheckEvent msg);

    /**
     * 处理微信硬件绑定和解绑事件
     * @param msg 处理微信硬件绑定和解绑事件
     */
    protected abstract void processInEqubindEvent(InEqubindEvent msg) ;

    /**
     * 处理微信硬件发来数据
     * @param msg 处理微信硬件发来数据
     */
    protected abstract void processInEquDataMsg(InEquDataMsg msg);
}

package net.dreamlu.weixin.spring;

import com.jfinal.kit.StrKit;
import com.jfinal.weixin.sdk.api.ApiConfigKit;
import com.jfinal.weixin.sdk.kit.MsgEncryptKit;
import com.jfinal.wxaapp.WxaConfigKit;
import com.jfinal.wxaapp.msg.IMsgParser;
import com.jfinal.wxaapp.msg.bean.WxaImageMsg;
import com.jfinal.wxaapp.msg.bean.WxaMsg;
import com.jfinal.wxaapp.msg.bean.WxaTextMsg;
import com.jfinal.wxaapp.msg.bean.WxaUserEnterSessionMsg;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 小程序消息控制器
 * @author Wujun
 *
 */
public abstract class DreamWxaMsgController {
    private static final Log logger = LogFactory.getLog(DreamWxaMsgController.class);
    private WxaMsg wxaMsg = null;          // 本次请求 xml 解析后的 wxaMsg 对象
    @Autowired
    protected HttpServletRequest request;
    @Autowired
    protected HttpServletResponse response;
    /**
     * 小程序消息
     * @param imXmlMsg imXmlMsg
     */
    @RequestMapping("")
    public void index(@RequestBody String imXmlMsg) {
        // 开发模式输出微信服务发送过来的  xml、json 消息
        if (WxaConfigKit.isDevMode()) {
            System.out.println("接收消息:");
            System.out.println(imXmlMsg);
        }
        if (StrKit.isBlank(imXmlMsg)) {
            throw new RuntimeException("请不要在浏览器中请求该连接,调试请查看WIKI:http://git.oschina.net/jfinal/jfinal-weixin/wikis/JFinal-weixin-demo%E5%92%8C%E8%B0%83%E8%AF%95");
        }
        // 是否需要解密消息
        if (WxaConfigKit.getWxaConfig().isMessageEncrypt()) {
            imXmlMsg = MsgEncryptKit.decrypt(imXmlMsg,
                    request.getParameter("timestamp"),
                    request.getParameter("nonce"),
                    request.getParameter("msg_signature"));
        }

        IMsgParser msgParser = WxaConfigKit.getMsgParser();
        wxaMsg = msgParser.parser(imXmlMsg);
        
        if (wxaMsg instanceof WxaTextMsg) {
            processTextMsg((WxaTextMsg) wxaMsg);
        } else if (wxaMsg instanceof WxaImageMsg) {
            processImageMsg((WxaImageMsg) wxaMsg);
        } else if (wxaMsg instanceof WxaUserEnterSessionMsg) {
            processUserEnterSessionMsg((WxaUserEnterSessionMsg) wxaMsg);
        } else {
            logger.error("未能识别的小程序消息类型。 消息内容为：\n" + imXmlMsg);
        }
        // 直接回复success（推荐方式）
        WebUtils.renderText(response,"success");
    }
    
    /**
     * 处理接收到的文本消息
     * @param textMsg 处理接收到的文本消息
     */
    protected abstract void processTextMsg(WxaTextMsg textMsg);
    
    /**
     * 处理接收到的图片消息
     * @param imageMsg 处理接收到的图片消息
     */
    protected abstract void processImageMsg(WxaImageMsg imageMsg);
    
    /**
     * 处理接收到的进入会话事件
     * @param userEnterSessionMsg 处理接收到的进入会话事件
     */
    protected abstract void processUserEnterSessionMsg(WxaUserEnterSessionMsg userEnterSessionMsg);

}
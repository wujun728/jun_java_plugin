package org.springrain.weixin.sdk.cp.api;

import java.util.Map;

import org.springrain.weixin.sdk.common.exception.WxErrorException;
import org.springrain.weixin.sdk.cp.bean.WxCpXmlMessage;
import org.springrain.weixin.sdk.cp.bean.WxCpXmlOutMessage;

/**
 * 处理微信推送消息的处理器接口
 *
 * @author springrain
 */
public interface WxCpMessageHandler {

  /**
   * @param wxMessage
   * @param context        上下文，如果handler或interceptor之间有信息要传递，可以用这个
   * @param iWxCpService
   * @param sessionManager
   * @return xml格式的消息，如果在异步规则里处理的话，可以返回null
   */
  WxCpXmlOutMessage handle(WxCpXmlMessage wxMessage,
                           Map<String, Object> context,
                           IWxCpService wxCpService) throws WxErrorException;

}

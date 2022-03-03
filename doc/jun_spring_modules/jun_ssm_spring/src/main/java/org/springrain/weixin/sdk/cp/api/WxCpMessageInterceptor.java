package org.springrain.weixin.sdk.cp.api;

import java.util.Map;

import org.springrain.weixin.sdk.common.exception.WxErrorException;
import org.springrain.weixin.sdk.cp.bean.WxCpXmlMessage;

/**
 * 微信消息拦截器，可以用来做验证
 *
 * @author springrain
 */
public interface WxCpMessageInterceptor {

  /**
   * 拦截微信消息
   *
   * @param wxMessage
   * @param context        上下文，如果handler或interceptor之间有信息要传递，可以用这个
   * @param iWxCpService
   * @param sessionManager
   * @return true代表OK，false代表不OK
   */
  boolean intercept(WxCpXmlMessage wxMessage,
                    Map<String, Object> context,
                    IWxCpService wxCpService) throws WxErrorException;

}

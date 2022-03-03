package org.springrain.weixin.sdk.mp.api;

import org.springrain.weixin.sdk.mp.bean.message.WxMpXmlMessage;

/**
 * 消息匹配器，用在消息路由的时候
 */
public interface IWxMpMessageMatcher {

  /**
   * 消息是否匹配某种模式
   * @param message
   */
  boolean match(WxMpXmlMessage message);

}

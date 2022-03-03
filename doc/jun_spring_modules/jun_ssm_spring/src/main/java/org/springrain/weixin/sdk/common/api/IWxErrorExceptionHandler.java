package org.springrain.weixin.sdk.common.api;

import org.springrain.weixin.sdk.common.exception.WxErrorException;


/**
 * WxErrorException处理器
 */
public interface IWxErrorExceptionHandler {

  void handle(WxErrorException e);

}

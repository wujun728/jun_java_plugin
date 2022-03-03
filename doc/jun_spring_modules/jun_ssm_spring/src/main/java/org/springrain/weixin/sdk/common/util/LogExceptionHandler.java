package org.springrain.weixin.sdk.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springrain.weixin.sdk.common.api.IWxErrorExceptionHandler;
import org.springrain.weixin.sdk.common.exception.WxErrorException;


public class LogExceptionHandler implements IWxErrorExceptionHandler {

  private final Logger logger = LoggerFactory.getLogger(getClass());

  @Override
  public void handle(WxErrorException e) {

    this.logger.error("Error happens", e);

  }

}

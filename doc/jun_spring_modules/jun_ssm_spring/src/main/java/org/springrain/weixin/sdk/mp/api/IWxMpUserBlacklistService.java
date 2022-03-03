package org.springrain.weixin.sdk.mp.api;

import java.util.List;

import org.springrain.weixin.sdk.common.api.IWxMpConfig;
import org.springrain.weixin.sdk.common.exception.WxErrorException;
import org.springrain.weixin.sdk.mp.bean.result.WxMpUserBlacklistGetResult;

/**
 * @author springrain
 */
public interface IWxMpUserBlacklistService {
  /**
   * <pre>
   * 获取公众号的黑名单列表
   * 详情请见http://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1471422259_pJMWA&token=&lang=zh_CN
   * </pre>
   */
  WxMpUserBlacklistGetResult getBlacklist(IWxMpConfig wxmpconfig,String nextOpenid) throws WxErrorException;

  /**
   * <pre>
   *   拉黑用户
   *   详情请见http://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1471422259_pJMWA&token=&lang=zh_CN
   * </pre>
   */
  void pushToBlacklist(IWxMpConfig wxmpconfig,List<String> openidList) throws WxErrorException;

  /**
   * <pre>
   *   取消拉黑用户
   *   详情请见http://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1471422259_pJMWA&token=&lang=zh_CN
   * </pre>
   */
  void pullFromBlacklist(IWxMpConfig wxmpconfig,List<String> openidList) throws WxErrorException;
}

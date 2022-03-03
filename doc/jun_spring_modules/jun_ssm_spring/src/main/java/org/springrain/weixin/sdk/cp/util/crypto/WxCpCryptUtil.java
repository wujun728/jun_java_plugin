/**
 * 对公众平台发送给公众账号的消息加解密示例代码.
 *
 * @copyright Copyright (c) 1998-2014 Tencent Inc.
 * <p>
 * 针对org.apache.commons.codec.binary.Base64，
 * 需要导入架包commons-codec-1.9（或commons-codec-1.8等其他版本）
 * 官方下载地址：http://commons.apache.org/proper/commons-codec/download_codec.cgi
 */

// ------------------------------------------------------------------------

/**
 * 针对org.apache.commons.codec.binary.Base64，
 * 需要导入架包commons-codec-1.9（或commons-codec-1.8等其他版本）
 * 官方下载地址：http://commons.apache.org/proper/commons-codec/download_codec.cgi
 */
package org.springrain.weixin.sdk.cp.util.crypto;

import org.apache.commons.codec.binary.Base64;
import org.springrain.weixin.sdk.common.api.IWxCpConfig;
import org.springrain.weixin.sdk.common.util.crypto.WxCryptUtil;

public class WxCpCryptUtil extends WxCryptUtil {

  /**
   * 构造函数
   *
   * @param wxCpConfigStorage
   */
  public WxCpCryptUtil(IWxCpConfig wxcpconfig) {
    /*
     * @param token          公众平台上，开发者设置的token
     * @param encodingAesKey 公众平台上，开发者设置的EncodingAESKey
     * @param appidOrCorpid          公众平台appid
     */
    String encodingAesKey = wxcpconfig.getAesKey();
    String token = wxcpconfig.getToken();
    String corpId = wxcpconfig.getCorpId();

    this.token = token;
    this.appidOrCorpid = corpId;
    this.aesKey = Base64.decodeBase64(encodingAesKey + "=");
  }


}

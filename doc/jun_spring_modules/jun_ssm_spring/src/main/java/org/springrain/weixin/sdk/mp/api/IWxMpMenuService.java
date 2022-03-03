package org.springrain.weixin.sdk.mp.api;

import org.springrain.weixin.sdk.common.api.IWxMpConfig;
import org.springrain.weixin.sdk.common.bean.menu.WxMenu;
import org.springrain.weixin.sdk.common.exception.WxErrorException;
import org.springrain.weixin.sdk.mp.bean.menu.WxMpGetSelfMenuInfoResult;

/**
 * 菜单相关操作接口
 *
 * @author springrain
 */
public interface IWxMpMenuService {

  /**
   * <pre>
   * 自定义菜单创建接口
   * 详情请见: http://mp.weixin.qq.com/wiki/index.php?title=自定义菜单创建接口
   * 如果要创建个性化菜单，请设置matchrule属性
   * 详情请见:http://mp.weixin.qq.com/wiki/0/c48ccd12b69ae023159b4bfaa7c39c20.html
   * </pre>
   */
  void menuCreate(IWxMpConfig wxmpconfig,WxMenu menu) throws WxErrorException;

  /**
   * <pre>
   * 自定义菜单删除接口
   * 详情请见: http://mp.weixin.qq.com/wiki/index.php?title=自定义菜单删除接口
   * </pre>
   */
  void menuDelete(IWxMpConfig wxmpconfig) throws WxErrorException;

  /**
   * <pre>
   * 删除个性化菜单接口
   * 详情请见: http://mp.weixin.qq.com/wiki/0/c48ccd12b69ae023159b4bfaa7c39c20.html
   * </pre>
   *
   * @param menuid
   */
  void menuDelete(IWxMpConfig wxmpconfig,String menuid) throws WxErrorException;

  /**
   * <pre>
   * 自定义菜单查询接口
   * 详情请见: http://mp.weixin.qq.com/wiki/index.php?title=自定义菜单查询接口
   * </pre>
   */
  WxMenu menuGet(IWxMpConfig wxmpconfig) throws WxErrorException;

  /**
   * <pre>
   * 测试个性化菜单匹配结果
   * 详情请见: http://mp.weixin.qq.com/wiki/0/c48ccd12b69ae023159b4bfaa7c39c20.html
   * </pre>
   *
   * @param userid 可以是粉丝的OpenID，也可以是粉丝的微信号。
   */
  WxMenu menuTryMatch(IWxMpConfig wxmpconfig,String userid) throws WxErrorException;

  /**
   * <pre>
   * 获取自定义菜单配置接口
   * 本接口将会提供公众号当前使用的自定义菜单的配置，如果公众号是通过API调用设置的菜单，则返回菜单的开发配置，而如果公众号是在公众平台官网通过网站功能发布菜单，则本接口返回运营者设置的菜单配置。
     请注意：
     1、第三方平台开发者可以通过本接口，在旗下公众号将业务授权给你后，立即通过本接口检测公众号的自定义菜单配置，并通过接口再次给公众号设置好自动回复规则，以提升公众号运营者的业务体验。
     2、本接口与自定义菜单查询接口的不同之处在于，本接口无论公众号的接口是如何设置的，都能查询到接口，而自定义菜单查询接口则仅能查询到使用API设置的菜单配置。
     3、认证/未认证的服务号/订阅号，以及接口测试号，均拥有该接口权限。
     4、从第三方平台的公众号登录授权机制上来说，该接口从属于消息与菜单权限集。
     5、本接口中返回的图片/语音/视频为临时素材（临时素材每次获取都不同，3天内有效，通过素材管理-获取临时素材接口来获取这些素材），本接口返回的图文消息为永久素材素材（通过素材管理-获取永久素材接口来获取这些素材）。
   *  接口调用请求说明:
        http请求方式: GET（请使用https协议）
        https://api.weixin.qq.com/cgi-bin/get_current_selfmenu_info?access_token=ACCESS_TOKEN
   *</pre>
   */
  WxMpGetSelfMenuInfoResult getSelfMenuInfo(IWxMpConfig wxmpconfig) throws WxErrorException;
}

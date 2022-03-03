package org.springrain.weixin.sdk.mp.api;

import java.util.List;

import org.springrain.weixin.sdk.common.api.IWxMpConfig;
import org.springrain.weixin.sdk.common.exception.WxErrorException;
import org.springrain.weixin.sdk.mp.bean.WxMpUserQuery;
import org.springrain.weixin.sdk.mp.bean.result.WxMpUser;
import org.springrain.weixin.sdk.mp.bean.result.WxMpUserList;

/**
 * 用户管理相关操作接口
 *
 * @author springrain
 */
public interface IWxMpUserService {

  /**
   * <pre>
   * 设置用户备注名
   * 详情请见: http://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421140838&token=&lang=zh_CN
   * http请求方式: POST（请使用https协议）
   * 接口地址：https://api.weixin.qq.com/cgi-bin/user/info/updateremark?access_token=ACCESS_TOKEN
   * </pre>
   *
   * @param openid 用户openid
   * @param remark 备注名
   */
  void userUpdateRemark(IWxMpConfig wxmpconfig,String openid, String remark) throws WxErrorException;

  /**
   * <pre>
   * 获取用户基本信息（语言为默认的zh_CN 简体）
   * 详情请见: http://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421140839&token=&lang=zh_CN
   * http请求方式: GET
   * 接口地址：https://api.weixin.qq.com/cgi-bin/user/info?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN
   * </pre>
   *
   * @param openid 用户openid
   */
  WxMpUser userInfo(IWxMpConfig wxmpconfig,String openid) throws WxErrorException;

  /**
   * <pre>
   * 获取用户基本信息
   * 详情请见: http://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421140839&token=&lang=zh_CN
   * http请求方式: GET
   * 接口地址：https://api.weixin.qq.com/cgi-bin/user/info?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN
   * </pre>
   *
   * @param openid 用户openid
   * @param lang 语言，zh_CN 简体(默认)，zh_TW 繁体，en 英语
   */
  WxMpUser userInfo(IWxMpConfig wxmpconfig,String openid, String lang) throws WxErrorException;

  /**
   * <pre>
   * 获取用户基本信息列表
   * 开发者可通过该接口来批量获取用户基本信息。最多支持一次拉取100条。
   * 详情请见: http://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421140839&token=&lang=zh_CN
   * http请求方式: POST
   * 接口地址：https://api.weixin.qq.com/cgi-bin/user/info/batchget?access_token=ACCESS_TOKEN
   * </pre>
   *
   * @param openids 用户openid列表
   */
  List<WxMpUser> userInfoList(IWxMpConfig wxmpconfig,List<String> openids) throws WxErrorException;

  /**
   * <pre>
   * 获取用户基本信息列表
   * 开发者可通过该接口来批量获取用户基本信息。最多支持一次拉取100条。
   * 详情请见: http://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421140839&token=&lang=zh_CN
   * http请求方式: POST
   * 接口地址：https://api.weixin.qq.com/cgi-bin/user/info/batchget?access_token=ACCESS_TOKEN
   * </pre>
   *
   * @param userQuery 详细查询参数
   */
  List<WxMpUser> userInfoList(IWxMpConfig wxmpconfig,WxMpUserQuery userQuery) throws WxErrorException;

  /**
   * <pre>
   * 获取用户列表
   * 公众号可通过本接口来获取帐号的关注者列表，关注者列表由一串OpenID（加密后的微信号，每个用户对每个公众号的OpenID是唯一的）组成。一次拉取调用最多拉取10000个关注者的OpenID，可以通过多次拉取的方式来满足需求。
   * 详情请见: http://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421140840&token=&lang=zh_CN
   * http请求方式: GET（请使用https协议）
   * 接口地址：https://api.weixin.qq.com/cgi-bin/user/get?access_token=ACCESS_TOKEN&next_openid=NEXT_OPENID
   * </pre>
   *
   * @param nextOpenid 可选，第一个拉取的OPENID，null为从头开始拉取
   */
  WxMpUserList userList(IWxMpConfig wxmpconfig,String nextOpenid) throws WxErrorException;
}

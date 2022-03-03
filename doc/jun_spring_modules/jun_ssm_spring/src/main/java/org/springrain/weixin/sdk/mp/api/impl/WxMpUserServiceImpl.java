package org.springrain.weixin.sdk.mp.api.impl;

import java.util.List;

import org.springrain.weixin.sdk.common.api.IWxMpConfig;
import org.springrain.weixin.sdk.common.api.WxConsts;
import org.springrain.weixin.sdk.common.exception.WxErrorException;
import org.springrain.weixin.sdk.mp.api.IWxMpService;
import org.springrain.weixin.sdk.mp.api.IWxMpUserService;
import org.springrain.weixin.sdk.mp.bean.WxMpUserQuery;
import org.springrain.weixin.sdk.mp.bean.result.WxMpUser;
import org.springrain.weixin.sdk.mp.bean.result.WxMpUserList;

import com.google.gson.JsonObject;

/**
 * Created by springrain on 2017/1/8.
 */
public class WxMpUserServiceImpl implements IWxMpUserService {
  private static final String API_URL_PREFIX = WxConsts.mpapiurl+"/cgi-bin/user";
 
  private IWxMpService wxMpService;

  public WxMpUserServiceImpl() {
  }
  public WxMpUserServiceImpl(IWxMpService wxMpService) {
	  this.wxMpService=wxMpService;
  }

  @Override
  public void userUpdateRemark(IWxMpConfig wxmpconfig,String openid, String remark) throws WxErrorException {
    String url = API_URL_PREFIX + "/info/updateremark";
    JsonObject json = new JsonObject();
    json.addProperty("openid", openid);
    json.addProperty("remark", remark);
    this.wxMpService.post(wxmpconfig,url, json.toString());
  }

  @Override
  public WxMpUser userInfo(IWxMpConfig wxmpconfig,String openid) throws WxErrorException {
    return this.userInfo(wxmpconfig,openid, null);
  }

  @Override
  public WxMpUser userInfo(IWxMpConfig wxmpconfig,String openid, String lang) throws WxErrorException {
    String url = API_URL_PREFIX + "/info";
    lang = lang == null ? "zh_CN" : lang;
    String responseContent = this.wxMpService.get(wxmpconfig,url,
        "openid=" + openid + "&lang=" + lang);
    return WxMpUser.fromJson(responseContent);
  }

  @Override
  public WxMpUserList userList(IWxMpConfig wxmpconfig,String next_openid) throws WxErrorException {
    String url = API_URL_PREFIX + "/get";
    String responseContent = this.wxMpService.get(wxmpconfig,url,
        next_openid == null ? null : "next_openid=" + next_openid);
    return WxMpUserList.fromJson(responseContent);
  }

  @Override
  public List<WxMpUser> userInfoList(IWxMpConfig wxmpconfig,List<String> openids)
      throws WxErrorException {
    return this.userInfoList(wxmpconfig,new WxMpUserQuery(openids));
  }

  @Override
  public List<WxMpUser> userInfoList(IWxMpConfig wxmpconfig,WxMpUserQuery userQuery) throws WxErrorException {
    String url = API_URL_PREFIX + "/info/batchget";
    String responseContent = this.wxMpService.post(wxmpconfig,url,
        userQuery.toJsonString());
    return WxMpUser.fromJsonList(responseContent);
  }

}

package org.springrain.weixin.sdk.mp.api.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springrain.weixin.sdk.common.api.IWxMpConfig;
import org.springrain.weixin.sdk.common.api.WxConsts;
import org.springrain.weixin.sdk.common.exception.WxErrorException;
import org.springrain.weixin.sdk.common.util.http.SimplePostRequestExecutor;
import org.springrain.weixin.sdk.mp.api.IWxMpService;
import org.springrain.weixin.sdk.mp.api.IWxMpUserBlacklistService;
import org.springrain.weixin.sdk.mp.bean.result.WxMpUserBlacklistGetResult;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

/**
 * @author springrain
 */

@Service("wxMpUserBlacklistService")
public class WxMpUserBlacklistServiceImpl implements IWxMpUserBlacklistService {
  private static final String API_BLACKLIST_PREFIX = WxConsts.mpapiurl+"/cgi-bin/tags/members";
 
  @Resource
  private IWxMpService wxMpService;

  public WxMpUserBlacklistServiceImpl() {
  }
  
  public WxMpUserBlacklistServiceImpl(IWxMpService wxMpService) {
	  this.wxMpService=wxMpService;
  }

  @Override
  public WxMpUserBlacklistGetResult getBlacklist(IWxMpConfig wxmpconfig,String nextOpenid) throws WxErrorException {
    JsonObject jsonObject = new JsonObject();
    jsonObject.addProperty("begin_openid", nextOpenid);
    String url = API_BLACKLIST_PREFIX + "/getblacklist";
    String responseContent = this.wxMpService.execute(wxmpconfig,new SimplePostRequestExecutor(), url, jsonObject.toString());
    return WxMpUserBlacklistGetResult.fromJson(responseContent);
  }

  @Override
  public void pushToBlacklist(IWxMpConfig wxmpconfig,List<String> openidList) throws WxErrorException {
    Map<String, Object> map = new HashMap<>();
    map.put("openid_list", openidList);
    String url = API_BLACKLIST_PREFIX + "/batchblacklist";
    this.wxMpService.execute(wxmpconfig,new SimplePostRequestExecutor(), url, new Gson().toJson(map));
  }

  @Override
  public void pullFromBlacklist(IWxMpConfig wxmpconfig,List<String> openidList) throws WxErrorException {
    Map<String, Object> map = new HashMap<>();
    map.put("openid_list", openidList);
    String url = API_BLACKLIST_PREFIX + "/batchunblacklist";
    this.wxMpService.execute(wxmpconfig,new SimplePostRequestExecutor(), url, new Gson().toJson(map));
  }
}

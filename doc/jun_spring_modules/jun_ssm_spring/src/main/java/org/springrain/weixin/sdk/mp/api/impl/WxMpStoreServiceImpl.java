package org.springrain.weixin.sdk.mp.api.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springrain.weixin.sdk.common.api.IWxMpConfig;
import org.springrain.weixin.sdk.common.bean.result.WxError;
import org.springrain.weixin.sdk.common.exception.WxErrorException;
import org.springrain.weixin.sdk.common.util.BeanUtils;
import org.springrain.weixin.sdk.mp.api.IWxMpService;
import org.springrain.weixin.sdk.mp.api.IWxMpStoreService;
import org.springrain.weixin.sdk.mp.bean.store.WxMpStoreBaseInfo;
import org.springrain.weixin.sdk.mp.bean.store.WxMpStoreInfo;
import org.springrain.weixin.sdk.mp.bean.store.WxMpStoreListResult;
import org.springrain.weixin.sdk.mp.util.json.WxMpGsonBuilder;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

/**
 *  Created by springrain on 2017/1/26.
 * @author springrain (http://git.oschina.net/chunanyong/springrain)
 *
 */

@Service("wxMpStoreService")
public class WxMpStoreServiceImpl implements IWxMpStoreService {
  private static final String API_BASE_URL = "http://api.weixin.qq.com/cgi-bin/poi";

  
  @Resource
  private IWxMpService wxMpService;

  public WxMpStoreServiceImpl() {
  }
  
  public WxMpStoreServiceImpl(IWxMpService wxMpService) {
	  this.wxMpService=wxMpService;
  }

  @Override
  public void add(IWxMpConfig wxmpconfig,WxMpStoreBaseInfo request) throws WxErrorException {
    BeanUtils.checkRequiredFields(request);

    String url = API_BASE_URL + "/addpoi";
    String response = wxMpService.post(wxmpconfig,url, request.toJson());
    WxError wxError = WxError.fromJson(response);
    if (wxError.getErrorCode() != 0) {
      throw new WxErrorException(wxError);
    }
  }

  @Override
  public WxMpStoreBaseInfo get(IWxMpConfig wxmpconfig,String poiId) throws WxErrorException {
    String url = API_BASE_URL + "/getpoi";
    JsonObject paramObject = new JsonObject();
    paramObject.addProperty("poi_id",poiId);
    String response = wxMpService.post(wxmpconfig,url, paramObject.toString());
    WxError wxError = WxError.fromJson(response);
    if (wxError.getErrorCode() != 0) {
      throw new WxErrorException(wxError);
    }
    return WxMpStoreBaseInfo.fromJson(new JsonParser().parse(response).getAsJsonObject()
        .get("business").getAsJsonObject().get("base_info").toString());
  }

  @Override
  public void delete(IWxMpConfig wxmpconfig,String poiId) throws WxErrorException {
    String url = API_BASE_URL + "/delpoi";
    JsonObject paramObject = new JsonObject();
    paramObject.addProperty("poi_id",poiId);
    String response = wxMpService.post(wxmpconfig,url, paramObject.toString());
    WxError wxError = WxError.fromJson(response);
    if (wxError.getErrorCode() != 0) {
      throw new WxErrorException(wxError);
    }
  }

  @Override
  public WxMpStoreListResult list(IWxMpConfig wxmpconfig,int begin, int limit)
      throws WxErrorException {
    String url = API_BASE_URL + "/getpoilist";
    JsonObject params = new JsonObject();
    params.addProperty("begin", begin);
    params.addProperty("limit", limit);
    String response = wxMpService.post(wxmpconfig,url, params.toString());

    WxError wxError = WxError.fromJson(response);
    if (wxError.getErrorCode() != 0) {
      throw new WxErrorException(wxError);
    }

    return WxMpStoreListResult.fromJson(response);
  }

  @Override
  public List<WxMpStoreInfo> listAll(IWxMpConfig wxmpconfig) throws WxErrorException {
    int limit = 50;
    WxMpStoreListResult list = list(wxmpconfig,0, limit);
    List<WxMpStoreInfo> stores = list.getBusinessList();
    if (list.getTotalCount() > limit) {
      int begin = limit;
      WxMpStoreListResult followingList = list(wxmpconfig,begin, limit);
      while (followingList.getBusinessList().size() > 0) {
        stores.addAll(followingList.getBusinessList());
        begin += limit;
        if (begin >= list.getTotalCount()) {
          break;
        }
        followingList = list(wxmpconfig,begin, limit);
      }
    }

    return stores;
  }

  @Override
  public void update(IWxMpConfig wxmpconfig,WxMpStoreBaseInfo request) throws WxErrorException {
    String url = API_BASE_URL + "/updatepoi";
    String response = wxMpService.post(wxmpconfig,url, request.toJson());
    WxError wxError = WxError.fromJson(response);
    if (wxError.getErrorCode() != 0) {
      throw new WxErrorException(wxError);
    }
  }

  @Override
  public List<String> listCategories(IWxMpConfig wxmpconfig) throws WxErrorException {
    String url = API_BASE_URL + "/getwxcategory";
    String response = wxMpService.get(wxmpconfig,url, null);
    WxError wxError = WxError.fromJson(response);
    if (wxError.getErrorCode() != 0) {
      throw new WxErrorException(wxError);
    }

    return WxMpGsonBuilder.create().fromJson(
        new JsonParser().parse(response).getAsJsonObject().get("category_list"),
        new TypeToken<List<String>>(){}.getType());
  }

}

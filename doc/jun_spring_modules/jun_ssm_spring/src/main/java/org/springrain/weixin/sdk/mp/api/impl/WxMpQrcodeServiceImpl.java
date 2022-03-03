package org.springrain.weixin.sdk.mp.api.impl;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import org.springrain.weixin.sdk.common.api.IWxMpConfig;
import org.springrain.weixin.sdk.common.api.WxConsts;
import org.springrain.weixin.sdk.common.bean.result.WxError;
import org.springrain.weixin.sdk.common.exception.WxErrorException;
import org.springrain.weixin.sdk.common.util.http.SimplePostRequestExecutor;
import org.springrain.weixin.sdk.mp.api.IWxMpQrcodeService;
import org.springrain.weixin.sdk.mp.api.IWxMpService;
import org.springrain.weixin.sdk.mp.bean.result.WxMpQrCodeTicket;
import org.springrain.weixin.sdk.mp.util.http.QrCodeRequestExecutor;

import com.google.gson.JsonObject;

/**
 * Created by springrain on 2017/1/8.
 */

public class WxMpQrcodeServiceImpl implements IWxMpQrcodeService {
  private static final String API_URL_PREFIX = WxConsts.mpapiurl+"/cgi-bin/qrcode";
 
  private IWxMpService wxMpService;

  public WxMpQrcodeServiceImpl() {
  }
  
  public WxMpQrcodeServiceImpl(IWxMpService wxMpService) {
	  this.wxMpService=wxMpService;
  }

  @Override
  public WxMpQrCodeTicket qrCodeCreateTmpTicket(IWxMpConfig wxmpconfig,int scene_id, Integer expire_seconds) throws WxErrorException {
    String url = API_URL_PREFIX + "/create";
    JsonObject json = new JsonObject();
    json.addProperty("action_name", "QR_SCENE");
    if (expire_seconds != null) {
      json.addProperty("expire_seconds", expire_seconds);
    }
    JsonObject actionInfo = new JsonObject();
    JsonObject scene = new JsonObject();
    scene.addProperty("scene_id", scene_id);
    actionInfo.add("scene", scene);
    json.add("action_info", actionInfo);
    String responseContent = wxMpService.execute(wxmpconfig,new SimplePostRequestExecutor(), url, json.toString());
    return WxMpQrCodeTicket.fromJson(responseContent);
  }

  @Override
  public WxMpQrCodeTicket qrCodeCreateLastTicket(IWxMpConfig wxmpconfig,int scene_id) throws WxErrorException {
    String url = API_URL_PREFIX + "/create";
    JsonObject json = new JsonObject();
    json.addProperty("action_name", "QR_LIMIT_SCENE");
    JsonObject actionInfo = new JsonObject();
    JsonObject scene = new JsonObject();
    scene.addProperty("scene_id", scene_id);
    actionInfo.add("scene", scene);
    json.add("action_info", actionInfo);
    String responseContent = wxMpService.execute(wxmpconfig,new SimplePostRequestExecutor(), url, json.toString());
    return WxMpQrCodeTicket.fromJson(responseContent);
  }

  @Override
  public WxMpQrCodeTicket qrCodeCreateLastTicket(IWxMpConfig wxmpconfig,String scene_str) throws WxErrorException {
    String url = API_URL_PREFIX + "/create";
    JsonObject json = new JsonObject();
    json.addProperty("action_name", "QR_LIMIT_STR_SCENE");
    JsonObject actionInfo = new JsonObject();
    JsonObject scene = new JsonObject();
    scene.addProperty("scene_str", scene_str);
    actionInfo.add("scene", scene);
    json.add("action_info", actionInfo);
    String responseContent = wxMpService.execute(wxmpconfig,new SimplePostRequestExecutor(), url, json.toString());
    return WxMpQrCodeTicket.fromJson(responseContent);
  }

  @Override
  public File qrCodePicture(IWxMpConfig wxmpconfig,WxMpQrCodeTicket ticket) throws WxErrorException {
    String url = WxConsts.mpweixinurl+"/cgi-bin/showqrcode";
    return wxMpService.execute(wxmpconfig,new QrCodeRequestExecutor(), url, ticket);
  }

  @Override
  public String qrCodePictureUrl(IWxMpConfig wxmpconfig,String ticket, boolean needShortUrl) throws WxErrorException {
    String url = WxConsts.mpweixinurl+"/cgi-bin/showqrcode?ticket=%s";
    try {
      String resultUrl = String.format(url,
              URLEncoder.encode(ticket, StandardCharsets.UTF_8.name()));
      if (needShortUrl) {
        return wxMpService.shortUrl(wxmpconfig,resultUrl);
      }

      return resultUrl;
    } catch (UnsupportedEncodingException e) {
      WxError error = WxError.newBuilder().setErrorCode(-1)
              .setErrorMsg(e.getMessage()).build();
      throw new WxErrorException(error);
    }
  }

  @Override
  public String qrCodePictureUrl(IWxMpConfig wxmpconfig,String ticket) throws WxErrorException {
    return qrCodePictureUrl(wxmpconfig,ticket, false);
  }

}

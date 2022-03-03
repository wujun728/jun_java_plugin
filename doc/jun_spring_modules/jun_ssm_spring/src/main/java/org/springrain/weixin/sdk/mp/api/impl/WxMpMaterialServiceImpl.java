package org.springrain.weixin.sdk.mp.api.impl;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springrain.weixin.sdk.common.api.IWxMpConfig;
import org.springrain.weixin.sdk.common.api.WxConsts;
import org.springrain.weixin.sdk.common.bean.result.WxError;
import org.springrain.weixin.sdk.common.bean.result.WxMediaUploadResult;
import org.springrain.weixin.sdk.common.exception.WxErrorException;
import org.springrain.weixin.sdk.common.util.fs.FileUtils;
import org.springrain.weixin.sdk.common.util.http.MediaDownloadRequestExecutor;
import org.springrain.weixin.sdk.common.util.http.MediaUploadRequestExecutor;
import org.springrain.weixin.sdk.common.util.json.WxGsonBuilder;
import org.springrain.weixin.sdk.mp.api.IWxMpMaterialService;
import org.springrain.weixin.sdk.mp.api.IWxMpService;
import org.springrain.weixin.sdk.mp.bean.material.WxMediaImgUploadResult;
import org.springrain.weixin.sdk.mp.bean.material.WxMpMaterial;
import org.springrain.weixin.sdk.mp.bean.material.WxMpMaterialArticleUpdate;
import org.springrain.weixin.sdk.mp.bean.material.WxMpMaterialCountResult;
import org.springrain.weixin.sdk.mp.bean.material.WxMpMaterialFileBatchGetResult;
import org.springrain.weixin.sdk.mp.bean.material.WxMpMaterialNews;
import org.springrain.weixin.sdk.mp.bean.material.WxMpMaterialNewsBatchGetResult;
import org.springrain.weixin.sdk.mp.bean.material.WxMpMaterialUploadResult;
import org.springrain.weixin.sdk.mp.bean.material.WxMpMaterialVideoInfoResult;
import org.springrain.weixin.sdk.mp.util.http.MaterialDeleteRequestExecutor;
import org.springrain.weixin.sdk.mp.util.http.MaterialNewsInfoRequestExecutor;
import org.springrain.weixin.sdk.mp.util.http.MaterialUploadRequestExecutor;
import org.springrain.weixin.sdk.mp.util.http.MaterialVideoInfoRequestExecutor;
import org.springrain.weixin.sdk.mp.util.http.MaterialVoiceAndImageDownloadRequestExecutor;
import org.springrain.weixin.sdk.mp.util.http.MediaImgUploadRequestExecutor;
import org.springrain.weixin.sdk.mp.util.json.WxMpGsonBuilder;

/**
 * Created by springrain on 2017/1/8.
 */


public class WxMpMaterialServiceImpl implements IWxMpMaterialService {
	private final   Logger logger = LoggerFactory.getLogger(getClass());
	
	
  private static final String MEDIA_API_URL_PREFIX = WxConsts.mpapiurl+"/cgi-bin/media";
  private static final String MATERIAL_API_URL_PREFIX = WxConsts.mpapiurl+"/cgi-bin/material";
  
  private IWxMpService wxMpService;

  public WxMpMaterialServiceImpl() {
  }
  public WxMpMaterialServiceImpl(IWxMpService wxMpService) {
	  this.wxMpService=wxMpService;
  }

  @Override
  public WxMediaUploadResult mediaUpload(IWxMpConfig wxmpconfig,String mediaType, String fileType, InputStream inputStream) throws WxErrorException {
    try {
      return this.mediaUpload(wxmpconfig,mediaType, FileUtils.createTmpFile(inputStream, UUID.randomUUID().toString(), fileType));
    } catch (IOException e) {
    	logger.error(e.getMessage(),e);
      throw new WxErrorException(WxError.newBuilder().setErrorMsg(e.getMessage()).build());
    }
  }

  @Override
  public WxMediaUploadResult mediaUpload(IWxMpConfig wxmpconfig,String mediaType, File file) throws WxErrorException {
    String url = MEDIA_API_URL_PREFIX + "/upload?type=" + mediaType;
    return this.wxMpService.execute(wxmpconfig,new MediaUploadRequestExecutor(), url, file);
  }

  @Override
  public File mediaDownload(IWxMpConfig wxmpconfig,String media_id) throws WxErrorException {
    String url = MEDIA_API_URL_PREFIX + "/get";
    return this.wxMpService.execute(wxmpconfig,
      new MediaDownloadRequestExecutor(new File(wxmpconfig.getTmpDirFile())),
      url,
      "media_id=" + media_id);
  }

  @Override
  public WxMediaImgUploadResult mediaImgUpload(IWxMpConfig wxmpconfig,File file) throws WxErrorException {
    String url = MEDIA_API_URL_PREFIX + "/uploadimg";
    return this.wxMpService.execute(wxmpconfig,new MediaImgUploadRequestExecutor(), url, file);
  }

  @Override
  public WxMpMaterialUploadResult materialFileUpload(IWxMpConfig wxmpconfig,String mediaType, WxMpMaterial material) throws WxErrorException {
    String url = MATERIAL_API_URL_PREFIX + "/add_material?type=" + mediaType;
    return this.wxMpService.execute(wxmpconfig,new MaterialUploadRequestExecutor(), url, material);
  }

  @Override
  public WxMpMaterialUploadResult materialNewsUpload(IWxMpConfig wxmpconfig,WxMpMaterialNews news) throws WxErrorException {
    if (news == null || news.isEmpty()) {
      throw new IllegalArgumentException("news is empty!");
    }
    String url = MATERIAL_API_URL_PREFIX + "/add_news";
    String responseContent = this.wxMpService.post(wxmpconfig,url, news.toJson());
    return WxMpMaterialUploadResult.fromJson(responseContent);
  }

  @Override
  public InputStream materialImageOrVoiceDownload(IWxMpConfig wxmpconfig,String media_id) throws WxErrorException {
    String url = MATERIAL_API_URL_PREFIX + "/get_material";
    return this.wxMpService.execute(wxmpconfig,new MaterialVoiceAndImageDownloadRequestExecutor(new File(wxmpconfig.getTmpDirFile())), url, media_id);
  }

  @Override
  public WxMpMaterialVideoInfoResult materialVideoInfo(IWxMpConfig wxmpconfig,String media_id) throws WxErrorException {
    String url = MATERIAL_API_URL_PREFIX + "/get_material";
    return this.wxMpService.execute(wxmpconfig,new MaterialVideoInfoRequestExecutor(), url, media_id);
  }

  @Override
  public WxMpMaterialNews materialNewsInfo(IWxMpConfig wxmpconfig,String media_id) throws WxErrorException {
    String url = MATERIAL_API_URL_PREFIX + "/get_material";
    return this.wxMpService.execute(wxmpconfig,new MaterialNewsInfoRequestExecutor(), url, media_id);
  }

  @Override
  public boolean materialNewsUpdate(IWxMpConfig wxmpconfig,WxMpMaterialArticleUpdate wxMpMaterialArticleUpdate) throws WxErrorException {
    String url = MATERIAL_API_URL_PREFIX + "/update_news";
    String responseText = this.wxMpService.post(wxmpconfig,url, wxMpMaterialArticleUpdate.toJson());
    WxError wxError = WxError.fromJson(responseText);
    if (wxError.getErrorCode() == 0) {
      return true;
    } else {
      throw new WxErrorException(wxError);
    }
  }

  @Override
  public boolean materialDelete(IWxMpConfig wxmpconfig,String media_id) throws WxErrorException {
    String url = MATERIAL_API_URL_PREFIX + "/del_material";
    return this.wxMpService.execute(wxmpconfig,new MaterialDeleteRequestExecutor(), url, media_id);
  }

  @Override
  public WxMpMaterialCountResult materialCount(IWxMpConfig wxmpconfig) throws WxErrorException {
    String url = MATERIAL_API_URL_PREFIX + "/get_materialcount";
    String responseText = this.wxMpService.get(wxmpconfig,url, null);
    WxError wxError = WxError.fromJson(responseText);
    if (wxError.getErrorCode() == 0) {
      return WxMpGsonBuilder.create().fromJson(responseText, WxMpMaterialCountResult.class);
    } else {
      throw new WxErrorException(wxError);
    }
  }

  @Override
  public WxMpMaterialNewsBatchGetResult materialNewsBatchGet(IWxMpConfig wxmpconfig,int offset, int count) throws WxErrorException {
    String url = MATERIAL_API_URL_PREFIX + "/batchget_material";
    Map<String, Object> params = new HashMap<>();
    params.put("type", WxConsts.MATERIAL_NEWS);
    params.put("offset", offset);
    params.put("count", count);
    String responseText = this.wxMpService.post(wxmpconfig,url, WxGsonBuilder.create().toJson(params));
    WxError wxError = WxError.fromJson(responseText);
    if (wxError.getErrorCode() == 0) {
      return WxMpGsonBuilder.create().fromJson(responseText, WxMpMaterialNewsBatchGetResult.class);
    } else {
      throw new WxErrorException(wxError);
    }
  }

  @Override
  public WxMpMaterialFileBatchGetResult materialFileBatchGet(IWxMpConfig wxmpconfig,String type, int offset, int count) throws WxErrorException {
    String url = MATERIAL_API_URL_PREFIX + "/batchget_material";
    Map<String, Object> params = new HashMap<>();
    params.put("type", type);
    params.put("offset", offset);
    params.put("count", count);
    String responseText = this.wxMpService.post(wxmpconfig,url, WxGsonBuilder.create().toJson(params));
    WxError wxError = WxError.fromJson(responseText);
    if (wxError.getErrorCode() == 0) {
      return WxMpGsonBuilder.create().fromJson(responseText, WxMpMaterialFileBatchGetResult.class);
    } else {
      throw new WxErrorException(wxError);
    }
  }

}

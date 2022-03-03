package org.springrain.weixin.sdk.xcx.api.impl;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springrain.weixin.sdk.common.api.IWxXcxConfig;
import org.springrain.weixin.sdk.common.api.WxConsts;
import org.springrain.weixin.sdk.common.bean.result.WxError;
import org.springrain.weixin.sdk.common.bean.result.WxMediaUploadResult;
import org.springrain.weixin.sdk.common.exception.WxErrorException;
import org.springrain.weixin.sdk.common.util.fs.FileUtils;
import org.springrain.weixin.sdk.common.util.http.MediaDownloadRequestExecutor;
import org.springrain.weixin.sdk.common.util.http.MediaUploadRequestExecutor;
import org.springrain.weixin.sdk.common.util.json.WxGsonBuilder;
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
import org.springrain.weixin.sdk.xcx.api.IWxXcxMaterialService;
import org.springrain.weixin.sdk.xcx.api.IWxXcxService;

/**
 * Created by springrain on 2017/1/8.
 */


public class WxXcxMaterialServiceImpl implements IWxXcxMaterialService {
	private final   Logger logger = LoggerFactory.getLogger(getClass());
	
	
  private static final String MEDIA_API_URL_PREFIX = WxConsts.mpapiurl+"/cgi-bin/media";
  private static final String MATERIAL_API_URL_PREFIX = WxConsts.mpapiurl+"/cgi-bin/material";
  
  private IWxXcxService wxXcxService;

  public WxXcxMaterialServiceImpl() {
  }
  public WxXcxMaterialServiceImpl(IWxXcxService wxXcxService) {
	  this.wxXcxService=wxXcxService;
  }

  @Override
  public WxMediaUploadResult mediaUpload(IWxXcxConfig wxxcxconfig,String mediaType, String fileType, InputStream inputStream) throws WxErrorException {
    try {
      return this.mediaUpload(wxxcxconfig,mediaType, FileUtils.createTmpFile(inputStream, UUID.randomUUID().toString(), fileType));
    } catch (IOException e) {
    	logger.error(e.getMessage(),e);
      throw new WxErrorException(WxError.newBuilder().setErrorMsg(e.getMessage()).build());
    }
  }
  
  @Override
  public WxMediaUploadResult mediaUpload(IWxXcxConfig wxxcxconfig, File file) throws WxErrorException {
  	return mediaUpload(wxxcxconfig, "image", file);
  }

  @Override
  public WxMediaUploadResult mediaUpload(IWxXcxConfig wxxcxconfig,String mediaType, File file) throws WxErrorException {
    String url = MEDIA_API_URL_PREFIX + "/upload?type=" + mediaType;
    return wxXcxService.execute(wxxcxconfig,new MediaUploadRequestExecutor(), url, file);
  }

  @Override
  public File mediaDownload(IWxXcxConfig wxxcxconfig,String media_id) throws WxErrorException {
    String url = MEDIA_API_URL_PREFIX + "/get";
    return wxXcxService.execute(wxxcxconfig,
      new MediaDownloadRequestExecutor(new File(wxxcxconfig.getTmpDirFile())),
      url,
      "media_id=" + media_id);
  }

  @Override
  public WxMediaImgUploadResult mediaImgUpload(IWxXcxConfig wxxcxconfig,File file) throws WxErrorException {
    String url = MEDIA_API_URL_PREFIX + "/uploadimg";
    return wxXcxService.execute(wxxcxconfig,new MediaImgUploadRequestExecutor(), url, file);
  }

  @Override
  public WxMpMaterialUploadResult materialFileUpload(IWxXcxConfig wxxcxconfig,String mediaType, WxMpMaterial material) throws WxErrorException {
    String url = MATERIAL_API_URL_PREFIX + "/add_material?type=" + mediaType;
    return wxXcxService.execute(wxxcxconfig,new MaterialUploadRequestExecutor(), url, material);
  }

  @Override
  public WxMpMaterialUploadResult materialNewsUpload(IWxXcxConfig wxxcxconfig,WxMpMaterialNews news) throws WxErrorException {
    if (news == null || news.isEmpty()) {
      throw new IllegalArgumentException("news is empty!");
    }
    String url = MATERIAL_API_URL_PREFIX + "/add_news";
    String responseContent = this.wxXcxService.post(wxxcxconfig,url, news.toJson());
    return WxMpMaterialUploadResult.fromJson(responseContent);
  }

  @Override
  public InputStream materialImageOrVoiceDownload(IWxXcxConfig wxxcxconfig,String media_id) throws WxErrorException {
    String url = MATERIAL_API_URL_PREFIX + "/get_material";
    return wxXcxService.execute(wxxcxconfig,new MaterialVoiceAndImageDownloadRequestExecutor(new File(wxxcxconfig.getTmpDirFile())), url, media_id);
  }

  @Override
  public WxMpMaterialVideoInfoResult materialVideoInfo(IWxXcxConfig wxxcxconfig,String media_id) throws WxErrorException {
    String url = MATERIAL_API_URL_PREFIX + "/get_material";
    return wxXcxService.execute(wxxcxconfig,new MaterialVideoInfoRequestExecutor(), url, media_id);
  }

  @Override
  public WxMpMaterialNews materialNewsInfo(IWxXcxConfig wxxcxconfig,String media_id) throws WxErrorException {
    String url = MATERIAL_API_URL_PREFIX + "/get_material";
    return wxXcxService.execute(wxxcxconfig,new MaterialNewsInfoRequestExecutor(), url, media_id);
  }

  @Override
  public boolean materialNewsUpdate(IWxXcxConfig wxxcxconfig,WxMpMaterialArticleUpdate wxMpMaterialArticleUpdate) throws WxErrorException {
    String url = MATERIAL_API_URL_PREFIX + "/update_news";
    String responseText = this.wxXcxService.post(wxxcxconfig,url, wxMpMaterialArticleUpdate.toJson());
    WxError wxError = WxError.fromJson(responseText);
    if (wxError.getErrorCode() == 0) {
      return true;
    } else {
      throw new WxErrorException(wxError);
    }
  }

  @Override
  public boolean materialDelete(IWxXcxConfig wxxcxconfig,String media_id) throws WxErrorException {
    String url = MATERIAL_API_URL_PREFIX + "/del_material";
    return wxXcxService.execute(wxxcxconfig,new MaterialDeleteRequestExecutor(), url, media_id);
  }

  @Override
  public WxMpMaterialCountResult materialCount(IWxXcxConfig wxxcxconfig) throws WxErrorException {
    String url = MATERIAL_API_URL_PREFIX + "/get_materialcount";
    String responseText = this.wxXcxService.get(wxxcxconfig,url, null);
    WxError wxError = WxError.fromJson(responseText);
    if (wxError.getErrorCode() == 0) {
      return WxMpGsonBuilder.create().fromJson(responseText, WxMpMaterialCountResult.class);
    } else {
      throw new WxErrorException(wxError);
    }
  }

  @Override
  public WxMpMaterialNewsBatchGetResult materialNewsBatchGet(IWxXcxConfig wxxcxconfig,int offset, int count) throws WxErrorException {
    String url = MATERIAL_API_URL_PREFIX + "/batchget_material";
    Map<String, Object> params = new HashMap<>();
    params.put("type", WxConsts.MATERIAL_NEWS);
    params.put("offset", offset);
    params.put("count", count);
    String responseText = this.wxXcxService.post(wxxcxconfig,url, WxGsonBuilder.create().toJson(params));
    WxError wxError = WxError.fromJson(responseText);
    if (wxError.getErrorCode() == 0) {
      return WxMpGsonBuilder.create().fromJson(responseText, WxMpMaterialNewsBatchGetResult.class);
    } else {
      throw new WxErrorException(wxError);
    }
  }

  @Override
  public WxMpMaterialFileBatchGetResult materialFileBatchGet(IWxXcxConfig wxxcxconfig,String type, int offset, int count) throws WxErrorException {
    String url = MATERIAL_API_URL_PREFIX + "/batchget_material";
    Map<String, Object> params = new HashMap<>();
    params.put("type", type);
    params.put("offset", offset);
    params.put("count", count);
    String responseText = wxXcxService.post(wxxcxconfig,url, WxGsonBuilder.create().toJson(params));
    WxError wxError = WxError.fromJson(responseText);
    if (wxError.getErrorCode() == 0) {
      return WxMpGsonBuilder.create().fromJson(responseText, WxMpMaterialFileBatchGetResult.class);
    } else {
      throw new WxErrorException(wxError);
    }
  }


}

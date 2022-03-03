package org.springrain.weixin.sdk.mp.api.impl;

import java.text.Format;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.time.FastDateFormat;
import org.springrain.weixin.sdk.common.api.IWxMpConfig;
import org.springrain.weixin.sdk.common.api.WxConsts;
import org.springrain.weixin.sdk.common.exception.WxErrorException;
import org.springrain.weixin.sdk.mp.api.IWxMpDataCubeService;
import org.springrain.weixin.sdk.mp.api.IWxMpService;
import org.springrain.weixin.sdk.mp.bean.datacube.WxDataCubeArticleResult;
import org.springrain.weixin.sdk.mp.bean.datacube.WxDataCubeArticleTotal;
import org.springrain.weixin.sdk.mp.bean.datacube.WxDataCubeInterfaceResult;
import org.springrain.weixin.sdk.mp.bean.datacube.WxDataCubeMsgResult;
import org.springrain.weixin.sdk.mp.bean.datacube.WxDataCubeUserCumulate;
import org.springrain.weixin.sdk.mp.bean.datacube.WxDataCubeUserSummary;

import com.google.gson.JsonObject;

/**
 *  Created by springrain on 2017/1/8.
 * @author springrain (http://git.oschina.net/chunanyong/springrain)
 */
public class WxMpDataCubeServiceImpl implements IWxMpDataCubeService {
  private static final String API_URL_PREFIX = WxConsts.mpapiurl+"/datacube";

  private final Format dateFormat = FastDateFormat.getInstance("yyyy-MM-dd");

  
  //生产环境应该是spring bean
  private IWxMpService wxMpService;
  //private WxMpService wxMpService=new WxMpServiceImpl();

  public WxMpDataCubeServiceImpl() {
  }
  
  public WxMpDataCubeServiceImpl(IWxMpService wxMpService) {
	  this.wxMpService=wxMpService;
  }

  @Override
  public List<WxDataCubeUserSummary> getUserSummary(IWxMpConfig wxmpconfig,Date beginDate, Date endDate) throws WxErrorException {
    String url = API_URL_PREFIX + "/getusersummary";
    JsonObject param = new JsonObject();
    param.addProperty("begin_date", dateFormat.format(beginDate));
    param.addProperty("end_date", dateFormat.format(endDate));
    String responseContent = wxMpService.post(wxmpconfig,url, param.toString());
    return WxDataCubeUserSummary.fromJson(responseContent);
  }

  @Override
  public List<WxDataCubeUserCumulate> getUserCumulate(IWxMpConfig wxmpconfig,Date beginDate, Date endDate) throws WxErrorException {
    String url = API_URL_PREFIX + "/getusercumulate";
    JsonObject param = new JsonObject();
    param.addProperty("begin_date", dateFormat.format(beginDate));
    param.addProperty("end_date", dateFormat.format(endDate));
    String responseContent = wxMpService.post(wxmpconfig,url, param.toString());
    return WxDataCubeUserCumulate.fromJson(responseContent);
  }

  @Override
  public List<WxDataCubeArticleResult> getArticleSummary(IWxMpConfig wxmpconfig,Date beginDate, Date endDate) throws WxErrorException {
    String url = API_URL_PREFIX + "/getarticlesummary";
    JsonObject param = new JsonObject();
    param.addProperty("begin_date", dateFormat.format(beginDate));
    param.addProperty("end_date", dateFormat.format(endDate));
    String responseContent = wxMpService.post(wxmpconfig,url, param.toString());
    return WxDataCubeArticleResult.fromJson(responseContent);
  }

  @Override
  public List<WxDataCubeArticleTotal> getArticleTotal(IWxMpConfig wxmpconfig,Date beginDate, Date endDate) throws WxErrorException {
    String url = API_URL_PREFIX + "/getarticletotal";
    JsonObject param = new JsonObject();
    param.addProperty("begin_date", dateFormat.format(beginDate));
    param.addProperty("end_date", dateFormat.format(endDate));
    String responseContent = wxMpService.post(wxmpconfig,url, param.toString());
    return WxDataCubeArticleTotal.fromJson(responseContent);
  }

  @Override
  public List<WxDataCubeArticleResult> getUserRead(IWxMpConfig wxmpconfig,Date beginDate, Date endDate) throws WxErrorException {
    String url = API_URL_PREFIX + "/getuserread";
    JsonObject param = new JsonObject();
    param.addProperty("begin_date", dateFormat.format(beginDate));
    param.addProperty("end_date", dateFormat.format(endDate));
    String responseContent = wxMpService.post(wxmpconfig,url, param.toString());
    return WxDataCubeArticleResult.fromJson(responseContent);
  }

  @Override
  public List<WxDataCubeArticleResult> getUserReadHour(IWxMpConfig wxmpconfig,Date beginDate, Date endDate) throws WxErrorException {
    String url = API_URL_PREFIX + "/getuserreadhour";
    JsonObject param = new JsonObject();
    param.addProperty("begin_date", dateFormat.format(beginDate));
    param.addProperty("end_date", dateFormat.format(endDate));
    String responseContent = wxMpService.post(wxmpconfig,url, param.toString());
    return WxDataCubeArticleResult.fromJson(responseContent);
  }

  @Override
  public List<WxDataCubeArticleResult> getUserShare(IWxMpConfig wxmpconfig,Date beginDate, Date endDate) throws WxErrorException {
    String url = API_URL_PREFIX + "/getusershare";
    JsonObject param = new JsonObject();
    param.addProperty("begin_date", dateFormat.format(beginDate));
    param.addProperty("end_date", dateFormat.format(endDate));
    String responseContent = wxMpService.post(wxmpconfig,url, param.toString());
    return WxDataCubeArticleResult.fromJson(responseContent);
  }

  @Override
  public List<WxDataCubeArticleResult> getUserShareHour(IWxMpConfig wxmpconfig,Date beginDate, Date endDate) throws WxErrorException {
    String url = API_URL_PREFIX + "/getusersharehour";
    JsonObject param = new JsonObject();
    param.addProperty("begin_date", dateFormat.format(beginDate));
    param.addProperty("end_date", dateFormat.format(endDate));
    String responseContent = wxMpService.post(wxmpconfig,url, param.toString());
    return WxDataCubeArticleResult.fromJson(responseContent);
  }

  @Override
  public List<WxDataCubeMsgResult> getUpstreamMsg(IWxMpConfig wxmpconfig,Date beginDate, Date endDate)
      throws WxErrorException {
    String url = API_URL_PREFIX + "/getupstreammsg";
    JsonObject param = new JsonObject();
    param.addProperty("begin_date", dateFormat.format(beginDate));
    param.addProperty("end_date", dateFormat.format(endDate));
    String responseContent = wxMpService.post(wxmpconfig,url, param.toString());
    return WxDataCubeMsgResult.fromJson(responseContent);
  }

  @Override
  public List<WxDataCubeMsgResult> getUpstreamMsgHour(IWxMpConfig wxmpconfig,Date beginDate,
      Date endDate) throws WxErrorException {
    String url = API_URL_PREFIX + "/getupstreammsghour";
    JsonObject param = new JsonObject();
    param.addProperty("begin_date", dateFormat.format(beginDate));
    param.addProperty("end_date", dateFormat.format(endDate));
    String responseContent = wxMpService.post(wxmpconfig,url, param.toString());
    return WxDataCubeMsgResult.fromJson(responseContent);
  }

  @Override
  public List<WxDataCubeMsgResult> getUpstreamMsgWeek(IWxMpConfig wxmpconfig,Date beginDate,
      Date endDate) throws WxErrorException {
    String url = API_URL_PREFIX + "/getupstreammsgweek";
    JsonObject param = new JsonObject();
    param.addProperty("begin_date", dateFormat.format(beginDate));
    param.addProperty("end_date", dateFormat.format(endDate));
    String responseContent = wxMpService.post(wxmpconfig,url, param.toString());
    return WxDataCubeMsgResult.fromJson(responseContent);
  }

  @Override
  public List<WxDataCubeMsgResult> getUpstreamMsgMonth(IWxMpConfig wxmpconfig,Date beginDate,
      Date endDate) throws WxErrorException {
    String url = API_URL_PREFIX + "/getupstreammsgmonth";
    JsonObject param = new JsonObject();
    param.addProperty("begin_date", dateFormat.format(beginDate));
    param.addProperty("end_date", dateFormat.format(endDate));
    String responseContent = wxMpService.post(wxmpconfig,url, param.toString());
    return WxDataCubeMsgResult.fromJson(responseContent);
  }

  @Override
  public List<WxDataCubeMsgResult> getUpstreamMsgDist(IWxMpConfig wxmpconfig,Date beginDate,
      Date endDate) throws WxErrorException {
    String url = API_URL_PREFIX + "/getupstreammsgdist";
    JsonObject param = new JsonObject();
    param.addProperty("begin_date", dateFormat.format(beginDate));
    param.addProperty("end_date", dateFormat.format(endDate));
    String responseContent = wxMpService.post(wxmpconfig,url, param.toString());
    return WxDataCubeMsgResult.fromJson(responseContent);
  }

  @Override
  public List<WxDataCubeMsgResult> getUpstreamMsgDistWeek(IWxMpConfig wxmpconfig,Date beginDate,
      Date endDate) throws WxErrorException {
    String url = API_URL_PREFIX + "/getupstreammsgdistweek";
    JsonObject param = new JsonObject();
    param.addProperty("begin_date", dateFormat.format(beginDate));
    param.addProperty("end_date", dateFormat.format(endDate));
    String responseContent = wxMpService.post(wxmpconfig,url, param.toString());
    return WxDataCubeMsgResult.fromJson(responseContent);
  }

  @Override
  public List<WxDataCubeMsgResult> getUpstreamMsgDistMonth(IWxMpConfig wxmpconfig,Date beginDate,
      Date endDate) throws WxErrorException {
    String url = API_URL_PREFIX + "/getupstreammsgdistmonth";
    JsonObject param = new JsonObject();
    param.addProperty("begin_date", dateFormat.format(beginDate));
    param.addProperty("end_date", dateFormat.format(endDate));
    String responseContent = wxMpService.post(wxmpconfig,url, param.toString());
    return WxDataCubeMsgResult.fromJson(responseContent);
  }

  @Override
  public List<WxDataCubeInterfaceResult> getInterfaceSummary(IWxMpConfig wxmpconfig,Date beginDate,
      Date endDate) throws WxErrorException {
    String url = API_URL_PREFIX + "/getinterfacesummary";
    JsonObject param = new JsonObject();
    param.addProperty("begin_date", dateFormat.format(beginDate));
    param.addProperty("end_date", dateFormat.format(endDate));
    String responseContent = wxMpService.post(wxmpconfig,url, param.toString());
    return WxDataCubeInterfaceResult.fromJson(responseContent);
  }

  @Override
  public List<WxDataCubeInterfaceResult> getInterfaceSummaryHour(IWxMpConfig wxmpconfig,Date beginDate,
      Date endDate) throws WxErrorException {
    String url = API_URL_PREFIX + "/getinterfacesummaryhour";
    JsonObject param = new JsonObject();
    param.addProperty("begin_date", dateFormat.format(beginDate));
    param.addProperty("end_date", dateFormat.format(endDate));
    String responseContent = wxMpService.post(wxmpconfig,url, param.toString());
    return WxDataCubeInterfaceResult.fromJson(responseContent);
  }
}

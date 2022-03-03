package org.springrain.weixin.sdk.mp.api.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springrain.weixin.sdk.common.api.IWxMpConfig;
import org.springrain.weixin.sdk.common.api.WxConsts;
import org.springrain.weixin.sdk.common.bean.menu.WxMenu;
import org.springrain.weixin.sdk.common.exception.WxErrorException;
import org.springrain.weixin.sdk.mp.api.IWxMpMenuService;
import org.springrain.weixin.sdk.mp.api.IWxMpService;
import org.springrain.weixin.sdk.mp.bean.menu.WxMpGetSelfMenuInfoResult;

/**
 * Created by springrain on 2017/1/8.
 */

public class WxMpMenuServiceImpl implements IWxMpMenuService {
  private static final String API_URL_PREFIX = WxConsts.mpapiurl+"/cgi-bin/menu";
  private final Logger log = LoggerFactory.getLogger(getClass());

  private IWxMpService wxMpService;

  public WxMpMenuServiceImpl() {
  }
  public WxMpMenuServiceImpl(IWxMpService wxMpService) {
	  this.wxMpService=wxMpService;
  }

  @Override
  public void menuCreate(IWxMpConfig wxmpconfig,WxMenu menu) throws WxErrorException {
    String menuJson = menu.toJson();
    String url = API_URL_PREFIX + "/create";
    if (menu.getMatchRule() != null) {
      url = API_URL_PREFIX + "/addconditional";
    }

    log.debug("开始创建菜单：{}", menuJson);

    String result =wxMpService.post(wxmpconfig,url, menuJson);
    log.debug("创建菜单：{},结果：{}", menuJson, result);
  }

  @Override
  public void menuDelete(IWxMpConfig wxmpconfig) throws WxErrorException {
    String url = API_URL_PREFIX + "/delete";
    String result =wxMpService.get(wxmpconfig,url, null);
    log.debug("删除菜单结果：{}", result);
  }

  @Override
  public void menuDelete(IWxMpConfig wxmpconfig,String menuid) throws WxErrorException {
    String url = API_URL_PREFIX + "/delconditional";
    String result =wxMpService.get(wxmpconfig,url, "menuid=" + menuid);
    log.debug("根据MeunId({})删除菜单结果：{}", menuid, result);
  }

  @Override
  public WxMenu menuGet(IWxMpConfig wxmpconfig) throws WxErrorException {
    String url = API_URL_PREFIX + "/get";
    try {
      String resultContent =wxMpService.get(wxmpconfig,url, null);
      return WxMenu.fromJson(resultContent);
    } catch (WxErrorException e) {
      // 46003 不存在的菜单数据
      if (e.getError().getErrorCode() == 46003) {
        return null;
      }
      throw e;
    }
  }

  @Override
  public WxMenu menuTryMatch(IWxMpConfig wxmpconfig,String userid) throws WxErrorException {
    String url = API_URL_PREFIX + "/trymatch";
    try {
      String resultContent =wxMpService.get(wxmpconfig,url, "user_id=" + userid);
      return WxMenu.fromJson(resultContent);
    } catch (WxErrorException e) {
      // 46003 不存在的菜单数据     46002 不存在的菜单版本
      if (e.getError().getErrorCode() == 46003
        || e.getError().getErrorCode() == 46002) {
        return null;
      }
      throw e;
    }
  }

  @Override
  public WxMpGetSelfMenuInfoResult getSelfMenuInfo(IWxMpConfig wxmpconfig) throws WxErrorException {
    String url = WxConsts.mpapiurl+"/cgi-bin/get_current_selfmenu_info";
    String resultContent =wxMpService.get(wxmpconfig,url, null);
    return WxMpGetSelfMenuInfoResult.fromJson(resultContent);
  }
}

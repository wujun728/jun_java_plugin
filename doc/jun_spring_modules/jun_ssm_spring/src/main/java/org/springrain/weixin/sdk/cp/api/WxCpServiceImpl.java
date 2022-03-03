package org.springrain.weixin.sdk.cp.api;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springrain.frame.util.HttpClientUtils;
import org.springrain.frame.util.JsonUtils;
import org.springrain.weixin.sdk.common.api.IWxCpConfig;
import org.springrain.weixin.sdk.common.api.IWxCpConfigService;
import org.springrain.weixin.sdk.common.api.WxConsts;
import org.springrain.weixin.sdk.common.bean.WxAccessToken;
import org.springrain.weixin.sdk.common.bean.WxJsApiSignature;
import org.springrain.weixin.sdk.common.bean.menu.WxMenu;
import org.springrain.weixin.sdk.common.bean.result.WxError;
import org.springrain.weixin.sdk.common.bean.result.WxMediaUploadResult;
import org.springrain.weixin.sdk.common.exception.WxErrorException;
import org.springrain.weixin.sdk.common.util.RandomUtils;
import org.springrain.weixin.sdk.common.util.crypto.SHA1;
import org.springrain.weixin.sdk.common.util.fs.FileUtils;
import org.springrain.weixin.sdk.common.util.http.MediaDownloadRequestExecutor;
import org.springrain.weixin.sdk.common.util.http.RequestExecutor;
import org.springrain.weixin.sdk.common.util.http.SimpleGetRequestExecutor;
import org.springrain.weixin.sdk.common.util.http.SimplePostRequestExecutor;
import org.springrain.weixin.sdk.common.util.http.URIUtil;
import org.springrain.weixin.sdk.common.util.json.GsonHelper;
import org.springrain.weixin.sdk.common.util.json.WxGsonBuilder;
import org.springrain.weixin.sdk.cp.bean.WxCpDepart;
import org.springrain.weixin.sdk.cp.bean.WxCpMessage;
import org.springrain.weixin.sdk.cp.bean.WxCpTag;
import org.springrain.weixin.sdk.cp.bean.WxCpUser;
import org.springrain.weixin.sdk.cp.util.http.CpMediaUploadRequestExecutor;
import org.springrain.weixin.sdk.cp.util.json.WxCpGsonBuilder;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import com.google.gson.reflect.TypeToken;


public class WxCpServiceImpl implements IWxCpService {

	 private final Logger log = LoggerFactory.getLogger(getClass());

  private IWxCpConfigService wxCpConfigService;
  
  public WxCpServiceImpl(){
	  
  }
  
public WxCpServiceImpl(IWxCpConfigService wxCpConfigService){
	  this.wxCpConfigService=wxCpConfigService;
}
  

  @Override
  public boolean checkSignature(IWxCpConfig wxcpconfig,String msgSignature, String timestamp, String nonce, String data) {
    try {
      return SHA1.gen(wxcpconfig.getToken(), timestamp, nonce, data)
        .equals(msgSignature);
    } catch (Exception e) {
      return false;
    }
  }

  @Override
  public void userAuthenticated(IWxCpConfig wxcpconfig,String userId) throws WxErrorException {
    String url = WxConsts.qyapiurl+"/cgi-bin/user/authsucc?userid=" + userId;
    get(wxcpconfig,url, null);
  }

  @Override
  public String getAccessToken(IWxCpConfig wxcpconfig) throws WxErrorException {
    return getAccessToken(wxcpconfig,false);
  }

  @Override
  public String getAccessToken(IWxCpConfig wxcpconfig,boolean forceRefresh) throws WxErrorException {

    	
    	 if (forceRefresh) {
       	  wxCpConfigService.expireAccessToken(wxcpconfig);
         }

         if (!wxcpconfig.isAccessTokenExpired()) {
       	  return wxcpconfig.getAccessToken();
         }
    	
          String url = WxConsts.qyapiurl+"/cgi-bin/gettoken?"
            + "&corpid=" + wxcpconfig.getCorpId()
            + "&corpsecret=" + wxcpconfig.getCorpSecret();
          try {
            HttpGet httpGet = new HttpGet(url);
            if (wxcpconfig.getHttpProxyHost() != null) {
                RequestConfig config = RequestConfig.custom().setProxy(new HttpHost(wxcpconfig.getHttpProxyHost() , wxcpconfig.getHttpProxyPort() )).build();
                httpGet.setConfig(config);
              }
            String resultContent = HttpClientUtils.sendHttpGet(httpGet);
           
            WxError error = WxError.fromJson(resultContent);
            if (error.getErrorCode() != 0) {
              throw new WxErrorException(error);
            }
            //WxAccessToken accessToken = WxAccessToken.fromJson(resultContent);
            WxAccessToken accessToken= WxGsonBuilder.create().fromJson(resultContent, WxAccessToken.class);
            wxcpconfig.setAccessToken(accessToken.getAccessToken());
            wxcpconfig.setAccessTokenExpiresTime(Long.valueOf(accessToken.getExpiresIn()));
            
            wxCpConfigService.updateAccessToken(wxcpconfig);
            
          } catch (RuntimeException e) {
            throw new RuntimeException(e);
          }
    return wxcpconfig.getAccessToken();
  }

  @Override
  public String getJsApiTicket(IWxCpConfig wxcpconfig) throws WxErrorException {
    return getJsApiTicket(wxcpconfig,false);
  }

  @Override
  public String getJsApiTicket(IWxCpConfig wxcpconfig,boolean forceRefresh) throws WxErrorException {
		  if (forceRefresh) {
	    	  wxCpConfigService.expireJsApiTicket(wxcpconfig);
	      }
	      
	      if (!wxcpconfig.isJsApiTicketExpired()) {
	    	  return wxcpconfig.getJsApiTicket();
	      }
          String url = WxConsts.qyapiurl+"/cgi-bin/get_jsapi_ticket";
          String responseContent = execute(wxcpconfig,new SimpleGetRequestExecutor(), url, null);
          JsonElement tmpJsonElement = new JsonParser().parse(responseContent);
          JsonObject tmpJsonObject = tmpJsonElement.getAsJsonObject();
          String jsapiTicket = tmpJsonObject.get("ticket").getAsString();
          Long expiresInSeconds = tmpJsonObject.get("expires_in").getAsLong();
          
          wxcpconfig.setJsApiTicket(jsapiTicket);
          wxcpconfig.setJsApiTicketExpiresTime(expiresInSeconds);
          wxCpConfigService.updateJsApiTicket(wxcpconfig);
          
          return wxcpconfig.getJsApiTicket();
  }

  @Override
  public WxJsApiSignature createJsApiSignature(IWxCpConfig wxcpconfig,String url) throws WxErrorException {
    long timestamp = System.currentTimeMillis() / 1000;
    String noncestr = RandomUtils.getRandomStr();
    String jsapiTicket = getJsApiTicket(wxcpconfig,false);
    String signature = SHA1.genWithAmple(
      "jsapi_ticket=" + jsapiTicket,
      "noncestr=" + noncestr,
      "timestamp=" + timestamp,
      "url=" + url
    );
    WxJsApiSignature jsapiSignature = new WxJsApiSignature();
    jsapiSignature.setTimestamp(timestamp);
    jsapiSignature.setNoncestr(noncestr);
    jsapiSignature.setUrl(url);
    jsapiSignature.setSignature(signature);

    // Fixed bug
    jsapiSignature.setAppid(wxcpconfig.getCorpId());

    return jsapiSignature;
  }

  @Override
  public void messageSend(IWxCpConfig wxcpconfig,WxCpMessage message) throws WxErrorException {
    String url = WxConsts.qyapiurl+"/cgi-bin/message/send";
    post(wxcpconfig,url, message.toJson());
  }

  @Override
  public void menuCreate(IWxCpConfig wxcpconfig,WxMenu menu) throws WxErrorException {
    menuCreate(wxcpconfig,wxcpconfig.getAgentId(), menu);
  }

  @Override
  public void menuCreate(IWxCpConfig wxcpconfig,Integer agentId, WxMenu menu) throws WxErrorException {
    String url = WxConsts.qyapiurl+"/cgi-bin/menu/create?agentid="
      + wxcpconfig.getAgentId();
    post(wxcpconfig,url, menu.toJson());
  }

  @Override
  public void menuDelete(IWxCpConfig wxcpconfig) throws WxErrorException {
    menuDelete(wxcpconfig,wxcpconfig.getAgentId());
  }

  @Override
  public void menuDelete(IWxCpConfig wxcpconfig,Integer agentId) throws WxErrorException {
    String url = WxConsts.qyapiurl+"/cgi-bin/menu/delete?agentid=" + agentId;
    get(wxcpconfig,url, null);
  }

  @Override
  public WxMenu menuGet(IWxCpConfig wxcpconfig) throws WxErrorException {
    return menuGet(wxcpconfig,wxcpconfig.getAgentId());
  }

  @Override
  public WxMenu menuGet(IWxCpConfig wxcpconfig,Integer agentId) throws WxErrorException {
    String url = WxConsts.qyapiurl+"/cgi-bin/menu/get?agentid=" + agentId;
    try {
      String resultContent = get(wxcpconfig,url, null);
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
  public WxMediaUploadResult mediaUpload(IWxCpConfig wxcpconfig,String mediaType, String fileType, InputStream inputStream)
    throws WxErrorException, IOException {
    return mediaUpload(wxcpconfig,mediaType, FileUtils.createTmpFile(inputStream, UUID.randomUUID().toString(), fileType));
  }

  @Override
  public WxMediaUploadResult mediaUpload(IWxCpConfig wxcpconfig,String mediaType, File file) throws WxErrorException {
    String url = WxConsts.qyapiurl+"/cgi-bin/media/upload?type=" + mediaType;
    return execute(wxcpconfig,new CpMediaUploadRequestExecutor(), url, file);
  }

  @Override
  public File mediaDownload(IWxCpConfig wxcpconfig,String media_id) throws WxErrorException {
    String url = WxConsts.qyapiurl+"/cgi-bin/media/get";
    return execute(wxcpconfig, new MediaDownloadRequestExecutor(
        new File(wxcpconfig.getTmpDirFile())),
      url, "media_id=" + media_id);
  }


  @Override
  public Integer departCreate(IWxCpConfig wxcpconfig,WxCpDepart depart) throws WxErrorException {
    String url = WxConsts.qyapiurl+"/cgi-bin/department/create";
    String responseContent = execute(wxcpconfig,
      new SimplePostRequestExecutor(),
      url,
      depart.toJson());
    JsonElement tmpJsonElement = new JsonParser().parse(responseContent);
    return GsonHelper.getAsInteger(tmpJsonElement.getAsJsonObject().get("id"));
  }

  @Override
  public void departUpdate(IWxCpConfig wxcpconfig,WxCpDepart group) throws WxErrorException {
    String url = WxConsts.qyapiurl+"/cgi-bin/department/update";
    post(wxcpconfig,url, group.toJson());
  }

  @Override
  public void departDelete(IWxCpConfig wxcpconfig,Integer departId) throws WxErrorException {
    String url = WxConsts.qyapiurl+"/cgi-bin/department/delete?id=" + departId;
    get(wxcpconfig,url, null);
  }

  @Override
  public List<WxCpDepart> departGet(IWxCpConfig wxcpconfig) throws WxErrorException {
    String url = WxConsts.qyapiurl+"/cgi-bin/department/list";
    String responseContent = get(wxcpconfig,url, null);
    /*
     * 操蛋的微信API，创建时返回的是 { group : { id : ..., name : ...} }
     * 查询时返回的是 { groups : [ { id : ..., name : ..., count : ... }, ... ] }
     */
    JsonElement tmpJsonElement = new JsonParser().parse(responseContent);
    return WxCpGsonBuilder.INSTANCE.create()
      .fromJson(
        tmpJsonElement.getAsJsonObject().get("department"),
        new TypeToken<List<WxCpDepart>>() {
        }.getType()
      );
  }

  @Override
  public void userCreate(IWxCpConfig wxcpconfig,WxCpUser user) throws WxErrorException {
    String url = WxConsts.qyapiurl+"/cgi-bin/user/create";
    post(wxcpconfig,url, user.toJson());
  }

  @Override
  public void userUpdate(IWxCpConfig wxcpconfig,WxCpUser user) throws WxErrorException {
    String url = WxConsts.qyapiurl+"/cgi-bin/user/update";
    post(wxcpconfig,url, user.toJson());
  }

  @Override
  public void userDelete(IWxCpConfig wxcpconfig,String userid) throws WxErrorException {
    String url = WxConsts.qyapiurl+"/cgi-bin/user/delete?userid=" + userid;
    get(wxcpconfig,url, null);
  }

  @Override
  public void userDelete(IWxCpConfig wxcpconfig,String[] userids) throws WxErrorException {
    String url = WxConsts.qyapiurl+"/cgi-bin/user/batchdelete";
    JsonObject jsonObject = new JsonObject();
    JsonArray jsonArray = new JsonArray();
    for (String userid : userids) {
      jsonArray.add(new JsonPrimitive(userid));
    }
    jsonObject.add("useridlist", jsonArray);
    post(wxcpconfig,url, jsonObject.toString());
  }

  @Override
  public WxCpUser userGet(IWxCpConfig wxcpconfig,String userid) throws WxErrorException {
    String url = WxConsts.qyapiurl+"/cgi-bin/user/get?userid=" + userid;
    String responseContent = get(wxcpconfig,url, null);
    return WxCpUser.fromJson(responseContent);
  }

  @Override
  public List<WxCpUser> userList(IWxCpConfig wxcpconfig,Integer departId, Boolean fetchChild, Integer status) throws WxErrorException {
    String url = WxConsts.qyapiurl+"/cgi-bin/user/list?department_id=" + departId;
    String params = "";
    if (fetchChild != null) {
      params += "&fetch_child=" + (fetchChild ? "1" : "0");
    }
    if (status != null) {
      params += "&status=" + status;
    } else {
      params += "&status=0";
    }

    String responseContent = get(wxcpconfig,url, params);
    JsonElement tmpJsonElement = new JsonParser().parse(responseContent);
    return WxCpGsonBuilder.INSTANCE.create()
      .fromJson(
        tmpJsonElement.getAsJsonObject().get("userlist"),
        new TypeToken<List<WxCpUser>>() {
        }.getType()
      );
  }

  @Override
  public List<WxCpUser> departGetUsers(IWxCpConfig wxcpconfig,Integer departId, Boolean fetchChild, Integer status) throws WxErrorException {
    String url = WxConsts.qyapiurl+"/cgi-bin/user/simplelist?department_id=" + departId;
    String params = "";
    if (fetchChild != null) {
      params += "&fetch_child=" + (fetchChild ? "1" : "0");
    }
    if (status != null) {
      params += "&status=" + status;
    } else {
      params += "&status=0";
    }

    String responseContent = get(wxcpconfig,url, params);
    JsonElement tmpJsonElement = new JsonParser().parse(responseContent);
    return WxCpGsonBuilder.INSTANCE.create()
      .fromJson(
        tmpJsonElement.getAsJsonObject().get("userlist"),
        new TypeToken<List<WxCpUser>>() {
        }.getType()
      );
  }

  @Override
  public String tagCreate(IWxCpConfig wxcpconfig,String tagName) throws WxErrorException {
    String url = WxConsts.qyapiurl+"/cgi-bin/tag/create";
    JsonObject o = new JsonObject();
    o.addProperty("tagname", tagName);
    String responseContent = post(wxcpconfig,url, o.toString());
    JsonElement tmpJsonElement = new JsonParser().parse(responseContent);
    return tmpJsonElement.getAsJsonObject().get("tagid").getAsString();
  }

  @Override
  public void tagUpdate(IWxCpConfig wxcpconfig,String tagId, String tagName) throws WxErrorException {
    String url = WxConsts.qyapiurl+"/cgi-bin/tag/update";
    JsonObject o = new JsonObject();
    o.addProperty("tagid", tagId);
    o.addProperty("tagname", tagName);
    post(wxcpconfig,url, o.toString());
  }

  @Override
  public void tagDelete(IWxCpConfig wxcpconfig,String tagId) throws WxErrorException {
    String url = WxConsts.qyapiurl+"/cgi-bin/tag/delete?tagid=" + tagId;
    get(wxcpconfig,url, null);
  }

  @Override
  public List<WxCpTag> tagGet(IWxCpConfig wxcpconfig) throws WxErrorException {
    String url = WxConsts.qyapiurl+"/cgi-bin/tag/list";
    String responseContent = get(wxcpconfig,url, null);
    JsonElement tmpJsonElement = new JsonParser().parse(responseContent);
    return WxCpGsonBuilder.INSTANCE.create()
      .fromJson(
        tmpJsonElement.getAsJsonObject().get("taglist"),
        new TypeToken<List<WxCpTag>>() {
        }.getType()
      );
  }

  @Override
  public List<WxCpUser> tagGetUsers(IWxCpConfig wxcpconfig,String tagId) throws WxErrorException {
    String url = WxConsts.qyapiurl+"/cgi-bin/tag/get?tagid=" + tagId;
    String responseContent = get(wxcpconfig,url, null);
    JsonElement tmpJsonElement = new JsonParser().parse(responseContent);
    return WxCpGsonBuilder.INSTANCE.create()
      .fromJson(
        tmpJsonElement.getAsJsonObject().get("userlist"),
        new TypeToken<List<WxCpUser>>() {
        }.getType()
      );
  }

  @Override
  public void tagAddUsers(IWxCpConfig wxcpconfig,String tagId, List<String> userIds, List<String> partyIds) throws WxErrorException {
    String url = WxConsts.qyapiurl+"/cgi-bin/tag/addtagusers";
    JsonObject jsonObject = new JsonObject();
    jsonObject.addProperty("tagid", tagId);
    if (userIds != null) {
      JsonArray jsonArray = new JsonArray();
      for (String userId : userIds) {
        jsonArray.add(new JsonPrimitive(userId));
      }
      jsonObject.add("userlist", jsonArray);
    }
    if (partyIds != null) {
      JsonArray jsonArray = new JsonArray();
      for (String userId : partyIds) {
        jsonArray.add(new JsonPrimitive(userId));
      }
      jsonObject.add("partylist", jsonArray);
    }
    post(wxcpconfig,url, jsonObject.toString());
  }

  @Override
  public void tagRemoveUsers(IWxCpConfig wxcpconfig,String tagId, List<String> userIds) throws WxErrorException {
    String url = WxConsts.qyapiurl+"/cgi-bin/tag/deltagusers";
    JsonObject jsonObject = new JsonObject();
    jsonObject.addProperty("tagid", tagId);
    JsonArray jsonArray = new JsonArray();
    for (String userId : userIds) {
      jsonArray.add(new JsonPrimitive(userId));
    }
    jsonObject.add("userlist", jsonArray);
    post(wxcpconfig,url, jsonObject.toString());
  }

  @Override
  public String oauth2buildAuthorizationUrl(IWxCpConfig wxcpconfig,String state) {
    return oauth2buildAuthorizationUrl(wxcpconfig,wxcpconfig.getOauth2redirectUri(),state );
  }

  @Override
  public String oauth2buildAuthorizationUrl(IWxCpConfig wxcpconfig,String redirectUri, String state) {
    String url = WxConsts.mpopenurl+"/connect/oauth2/authorize?";
    url += "appid=" + wxcpconfig.getCorpId();
    url += "&redirect_uri=" + URIUtil.encodeURIComponent(redirectUri);
    url += "&response_type=code";
    url += "&scope=snsapi_base";
    if (state != null) {
      url += "&state=" + state;
    }
    url += "#wechat_redirect";
    return url;
  }

  @Override
  public String[] oauth2getUserInfo(IWxCpConfig wxcpconfig,String code) throws WxErrorException {
    return oauth2getUserInfo(wxcpconfig,wxcpconfig.getAgentId(), code);
  }

  @Override
  public String[] oauth2getUserInfo(IWxCpConfig wxcpconfig,Integer agentId, String code) throws WxErrorException {
    String url = WxConsts.qyapiurl+"/cgi-bin/user/getuserinfo?"
      + "code=" + code
      + "&agentid=" + agentId;
    String responseText = get(wxcpconfig,url, null);
    JsonElement je = new JsonParser().parse(responseText);
    JsonObject jo = je.getAsJsonObject();
    return new String[]{GsonHelper.getString(jo, "UserId"), GsonHelper.getString(jo, "DeviceId")};
  }

  @Override
  public int invite(IWxCpConfig wxcpconfig,String userId, String inviteTips) throws WxErrorException {
    String url = WxConsts.qyapiurl+"/cgi-bin/invite/send";
    JsonObject jsonObject = new JsonObject();
    jsonObject.addProperty("userid", userId);
    if (StringUtils.isNotEmpty(inviteTips)) {
      jsonObject.addProperty("invite_tips", inviteTips);
    }
    String responseContent = post(wxcpconfig,url, jsonObject.toString());
    JsonElement tmpJsonElement = new JsonParser().parse(responseContent);
    return tmpJsonElement.getAsJsonObject().get("type").getAsInt();
  }

  @Override
  public String[] getCallbackIp(IWxCpConfig wxcpconfig) throws WxErrorException {
    String url = WxConsts.qyapiurl+"/cgi-bin/getcallbackip";
    String responseContent = get(wxcpconfig,url, null);
    JsonElement tmpJsonElement = new JsonParser().parse(responseContent);
    JsonArray jsonArray = tmpJsonElement.getAsJsonObject().get("ip_list").getAsJsonArray();
    String[] ips = new String[jsonArray.size()];
    for (int i = 0; i < jsonArray.size(); i++) {
      ips[i] = jsonArray.get(i).getAsString();
    }
    return ips;
  }

  @Override
  public String get(IWxCpConfig wxcpconfig,String url, String queryParam) throws WxErrorException {
    return execute(wxcpconfig,new SimpleGetRequestExecutor(), url, queryParam);
  }

  @Override
  public String post(IWxCpConfig wxcpconfig,String url, String postData) throws WxErrorException {
    return execute(wxcpconfig,new SimplePostRequestExecutor(), url, postData);
  }

  /**
   * 向微信端发送请求，在这里执行的策略是当发生access_token过期时才去刷新，然后重新执行请求，而不是全局定时请求
   */
  @Override
  public <T, E> T execute(IWxCpConfig wxcpconfig,RequestExecutor<T, E> executor, String uri, E data) throws WxErrorException {
	int retrySleepMillis = 1000;
	int maxRetryTimes = 5;
	
	int retryTimes = 0;
	
    do {
      try {
        return executeInternal(wxcpconfig,executor, uri, data);
      } catch (WxErrorException e) {
        WxError error = e.getError();
        /*
         * -1 系统繁忙, 1000ms后重试
         */
        if (error.getErrorCode() == -1) {
          int sleepMillis = retrySleepMillis * (1 << retryTimes);
          try {
            log.debug("微信系统繁忙，{}ms 后重试(第{}次)", sleepMillis,
              retryTimes + 1);
            Thread.sleep(sleepMillis);
          } catch (InterruptedException e1) {
        	Thread.currentThread().interrupt();
            throw new RuntimeException(e1);
          }
        } else {
          throw e;
        }
      }
    } while (++retryTimes < maxRetryTimes);

    throw new RuntimeException("微信服务端异常，超出重试次数");
  }

  protected synchronized <T, E> T executeInternal(IWxCpConfig wxcpconfig,RequestExecutor<T, E> executor, String uri, E data) throws WxErrorException {
    if (uri.contains("access_token=")) {
      throw new IllegalArgumentException("uri参数中不允许有access_token: " + uri);
    }
    String accessToken = getAccessToken(wxcpconfig,false);

    String uriWithAccessToken = uri;
    uriWithAccessToken += uri.indexOf('?') == -1 ? "?access_token=" + accessToken : "&access_token=" + accessToken;

    try {
      return executor.execute(wxcpconfig,
        uriWithAccessToken, data);
    } catch (WxErrorException e) {
      WxError error = e.getError();
      /*
       * 发生以下情况时尝试刷新access_token
       * 40001 获取access_token时AppSecret错误，或者access_token无效
       * 42001 access_token超时
       */
      if (error.getErrorCode() == 42001 || error.getErrorCode() == 40001) {
        // 强制设置wxCpConfigStorage它的access token过期了，这样在下一次请求里就会刷新access token
       // wxcpconfig.expireAccessToken();
        wxCpConfigService.expireAccessToken(wxcpconfig);
        if(wxcpconfig.autoRefreshToken()){
          return execute(wxcpconfig,executor, uri, data);
        }
      }
      if (error.getErrorCode() != 0) {
        throw new WxErrorException(error);
      }
      return null;
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
  
  @Override
  public String syncUser(IWxCpConfig wxcpconfig,String mediaId, Map<String, String> callBack) throws WxErrorException {
  	String url = WxConsts.qyapiurl+"/cgi-bin/batch/syncuser";
  	JsonObject jsonObject = new JsonObject();
  	jsonObject.addProperty("media_id", mediaId);
  	jsonObject.addProperty("callback", JsonUtils.writeValueAsString(callBack));
    return post(wxcpconfig,url, jsonObject.toString());
  }

  @Override
  public String replaceParty(IWxCpConfig wxcpconfig,String mediaId) throws WxErrorException {
    String url = WxConsts.qyapiurl+"/cgi-bin/batch/replaceparty";
    JsonObject jsonObject = new JsonObject();
    jsonObject.addProperty("media_id", mediaId);
    return post(wxcpconfig,url, jsonObject.toString());
  }

  @Override
  public String replaceUser(IWxCpConfig wxcpconfig,String mediaId) throws WxErrorException {
    String url = WxConsts.qyapiurl+"/cgi-bin/batch/replaceuser";
    JsonObject jsonObject = new JsonObject();
    jsonObject.addProperty("media_id", mediaId);
    return post(wxcpconfig,url, jsonObject.toString());
  }

  @Override
  public String getTaskResult(IWxCpConfig wxcpconfig,String joinId) throws WxErrorException {
    String url = WxConsts.qyapiurl+"/cgi-bin/batch/getresult?jobid=" + joinId;
    return get(wxcpconfig,url, null);
  }




}

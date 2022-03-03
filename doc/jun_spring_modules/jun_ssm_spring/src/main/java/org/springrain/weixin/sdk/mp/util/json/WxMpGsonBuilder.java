package org.springrain.weixin.sdk.mp.util.json;

import org.springrain.weixin.sdk.mp.bean.WxMpCard;
import org.springrain.weixin.sdk.mp.bean.WxMpMassNews;
import org.springrain.weixin.sdk.mp.bean.WxMpMassOpenIdsMessage;
import org.springrain.weixin.sdk.mp.bean.WxMpMassPreviewMessage;
import org.springrain.weixin.sdk.mp.bean.WxMpMassTagMessage;
import org.springrain.weixin.sdk.mp.bean.WxMpMassVideo;
import org.springrain.weixin.sdk.mp.bean.datacube.WxDataCubeUserCumulate;
import org.springrain.weixin.sdk.mp.bean.datacube.WxDataCubeUserSummary;
import org.springrain.weixin.sdk.mp.bean.kefu.WxMpKefuMessage;
import org.springrain.weixin.sdk.mp.bean.material.WxMediaImgUploadResult;
import org.springrain.weixin.sdk.mp.bean.material.WxMpMaterialArticleUpdate;
import org.springrain.weixin.sdk.mp.bean.material.WxMpMaterialCountResult;
import org.springrain.weixin.sdk.mp.bean.material.WxMpMaterialFileBatchGetResult;
import org.springrain.weixin.sdk.mp.bean.material.WxMpMaterialNews;
import org.springrain.weixin.sdk.mp.bean.material.WxMpMaterialNewsBatchGetResult;
import org.springrain.weixin.sdk.mp.bean.material.WxMpMaterialUploadResult;
import org.springrain.weixin.sdk.mp.bean.material.WxMpMaterialVideoInfoResult;
import org.springrain.weixin.sdk.mp.bean.result.WxMpCardResult;
import org.springrain.weixin.sdk.mp.bean.result.WxMpMassSendResult;
import org.springrain.weixin.sdk.mp.bean.result.WxMpMassUploadResult;
import org.springrain.weixin.sdk.mp.bean.result.WxMpOAuth2AccessToken;
import org.springrain.weixin.sdk.mp.bean.result.WxMpQrCodeTicket;
import org.springrain.weixin.sdk.mp.bean.result.WxMpSemanticQueryResult;
import org.springrain.weixin.sdk.mp.bean.result.WxMpUser;
import org.springrain.weixin.sdk.mp.bean.result.WxMpUserBlacklistGetResult;
import org.springrain.weixin.sdk.mp.bean.result.WxMpUserList;
import org.springrain.weixin.sdk.mp.bean.template.WxMpTemplateIndustry;
import org.springrain.weixin.sdk.mp.bean.template.WxMpTemplateMessage;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class WxMpGsonBuilder {

  public static final GsonBuilder INSTANCE = new GsonBuilder();

  static {
    INSTANCE.disableHtmlEscaping();
    INSTANCE.registerTypeAdapter(WxMpKefuMessage.class, new WxMpKefuMessageGsonAdapter());
    INSTANCE.registerTypeAdapter(WxMpMassNews.class, new WxMpMassNewsGsonAdapter());
    INSTANCE.registerTypeAdapter(WxMpMassTagMessage.class, new WxMpMassTagMessageGsonAdapter());
    INSTANCE.registerTypeAdapter(WxMpMassOpenIdsMessage.class, new WxMpMassOpenIdsMessageGsonAdapter());
    INSTANCE.registerTypeAdapter(WxMpUser.class, new WxMpUserGsonAdapter());
    INSTANCE.registerTypeAdapter(WxMpUserList.class, new WxUserListGsonAdapter());
    INSTANCE.registerTypeAdapter(WxMpMassVideo.class, new WxMpMassVideoAdapter());
    INSTANCE.registerTypeAdapter(WxMpMassSendResult.class, new WxMpMassSendResultAdapter());
    INSTANCE.registerTypeAdapter(WxMpMassUploadResult.class, new WxMpMassUploadResultAdapter());
    INSTANCE.registerTypeAdapter(WxMpQrCodeTicket.class, new WxQrCodeTicketAdapter());
    INSTANCE.registerTypeAdapter(WxMpTemplateMessage.class, new WxMpTemplateMessageGsonAdapter());
    INSTANCE.registerTypeAdapter(WxMpSemanticQueryResult.class, new WxMpSemanticQueryResultAdapter());
    INSTANCE.registerTypeAdapter(WxMpOAuth2AccessToken.class, new WxMpOAuth2AccessTokenAdapter());
    INSTANCE.registerTypeAdapter(WxDataCubeUserSummary.class, new WxMpUserSummaryGsonAdapter());
    INSTANCE.registerTypeAdapter(WxDataCubeUserCumulate.class, new WxMpUserCumulateGsonAdapter());
    INSTANCE.registerTypeAdapter(WxMpMaterialUploadResult.class, new WxMpMaterialUploadResultAdapter());
    INSTANCE.registerTypeAdapter(WxMpMaterialVideoInfoResult.class, new WxMpMaterialVideoInfoResultAdapter());
    INSTANCE.registerTypeAdapter(WxMpMassNews.WxMpMassNewsArticle.class, new WxMpMassNewsArticleGsonAdapter());
    INSTANCE.registerTypeAdapter(WxMpMaterialArticleUpdate.class, new WxMpMaterialArticleUpdateGsonAdapter());
    INSTANCE.registerTypeAdapter(WxMpMaterialCountResult.class, new WxMpMaterialCountResultAdapter());
    INSTANCE.registerTypeAdapter(WxMpMaterialNews.class, new WxMpMaterialNewsGsonAdapter());
    INSTANCE.registerTypeAdapter(WxMpMaterialNews.WxMpMaterialNewsArticle.class, new WxMpMaterialNewsArticleGsonAdapter());
    INSTANCE.registerTypeAdapter(WxMpMaterialNewsBatchGetResult.class, new WxMpMaterialNewsBatchGetGsonAdapter());
    INSTANCE.registerTypeAdapter(WxMpMaterialNewsBatchGetResult.WxMaterialNewsBatchGetNewsItem.class, new WxMpMaterialNewsBatchGetGsonItemAdapter());
    INSTANCE.registerTypeAdapter(WxMpMaterialFileBatchGetResult.class, new WxMpMaterialFileBatchGetGsonAdapter());
    INSTANCE.registerTypeAdapter(WxMpMaterialFileBatchGetResult.WxMaterialFileBatchGetNewsItem.class, new WxMpMaterialFileBatchGetGsonItemAdapter());
    INSTANCE.registerTypeAdapter(WxMpCardResult.class, new WxMpCardResultGsonAdapter());
    INSTANCE.registerTypeAdapter(WxMpCard.class, new WxMpCardGsonAdapter());
    INSTANCE.registerTypeAdapter(WxMpMassPreviewMessage.class, new WxMpMassPreviewMessageGsonAdapter());
    INSTANCE.registerTypeAdapter(WxMediaImgUploadResult.class, new WxMediaImgUploadResultGsonAdapter());
    INSTANCE.registerTypeAdapter(WxMpTemplateIndustry.class, new WxMpIndustryGsonAdapter());
    INSTANCE.registerTypeAdapter(WxMpUserBlacklistGetResult.class, new WxUserBlacklistGetResultGsonAdapter());
  }

  public static Gson create() {
    return INSTANCE.create();
  }

}

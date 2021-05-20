package com.demo.weixin.entity.message;


import com.demo.weixin.enums.MessageType;
import com.demo.weixin.exception.WeixinException;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Wujun
 * @description 语音消息
 * @date 2017/7/31
 * @since 1.0
 */
public class VoiceMessage extends BasicMessage {
    private String mediaId; // 必填。语音文件id，可以调用上传临时素材接口获取

    @Override
    public Map<String, Object> getParamMap() throws WeixinException {
        if (messageType != MessageType.VOICE || StringUtils.isBlank(mediaId)) {
            throw new WeixinException(-1, "语音消息DTO的消息类型错误，或语音媒体文件id为空");
        }
        Map<String, String> mediaMap = new HashMap<>();
        mediaMap.put("media_id", mediaId);

        Map<String, Object> paramMap = super.getParamMap();
        paramMap.put("voice", mediaMap);
        return paramMap;
    }

    public String getMediaId() {
        return mediaId;
    }

    public void setMediaId(String mediaId) {
        this.mediaId = mediaId;
    }
}

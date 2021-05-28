package com.demo.weixin.entity.message;

import com.demo.weixin.enums.MessageType;
import com.demo.weixin.exception.WeixinException;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Wujun
 * @description 视频消息
 * @date 2017/7/31
 * @since 1.0
 */
public class VideoMessage extends BasicMessage {
    private String mediaId; // 必填。视频媒体文件id，可以调用上传临时素材接口获取
    private String title; // 视频消息的标题，不超过128个字节，超过会自动截断
    private String description; // 视频消息的描述，不超过512个字节，超过会自动截断
    private boolean safe; //  表示是否是保密消息，0表示否，1表示是，默认0

    @Override
    public Map<String, Object> getParamMap() throws WeixinException {
        if (MessageType.VIDEO != messageType || StringUtils.isBlank(mediaId)) {
            throw new WeixinException(-1,"text的消息类型错误，或语音媒体文件id为空");
        }

        Map<String, String> mediaMap = new HashMap<>();
        mediaMap.put("media_id", mediaId);
        if (StringUtils.isNotBlank(title)) {
            mediaMap.put("title", title);
        }
        if (StringUtils.isNotBlank(description)) {
            mediaMap.put("description", description);
        }
        Map<String, Object> paramMap = super.getParamMap();
        paramMap.put("video", mediaMap);
        paramMap.put("safe", safe ? "1" : "0");
        return paramMap;
    }

    public String getMediaId() {
        return mediaId;
    }

    public void setMediaId(String mediaId) {
        this.mediaId = mediaId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isSafe() {
        return safe;
    }

    public void setSafe(boolean safe) {
        this.safe = safe;
    }
}

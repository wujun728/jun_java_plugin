package com.demo.weixin.entity.message;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import com.demo.weixin.enums.MessageType;
import com.demo.weixin.exception.WeixinException;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Wujun
 * @description 图文消息（mpnews）
 * @date 2017/7/31
 * @since 1.0
 */
public class MPNewsMessage extends BasicMessage {
    private List<MPArticlesMessage> articles;
    private boolean safe;

    @Override
    public Map<String, Object> getParamMap() throws WeixinException {
        if (MessageType.MP_NEWS != messageType || CollectionUtils.isEmpty(articles)) {
            throw new WeixinException(-1, "图文消息（mpnews）DTO的消息类型错误，或图文消息为空");
        }

        // 过滤出符合条件的图文消息
        List<MPArticlesMessage> articlesList = articles.stream().filter(article ->
                StringUtils.isNoneBlank(article.getTitle(), article.getThumbMediaId(), article.getContent())
        ).collect(Collectors.toList());

        if (CollectionUtils.isEmpty(articlesList)) {
            throw new IllegalArgumentException("图文消息（mpnews）的标题、消息缩略图的media_id、消息的内容不能为空");
        }

        Map<String, String> mediaMap = new HashMap<>();
        mediaMap.put("articles", JSON.toJSONString(articlesList));

        Map<String, Object> paramMap = super.getParamMap();
        paramMap.put("mpnews", mediaMap);
        paramMap.put("safe", safe ? "1" : "0");
        return paramMap;
    }

    class MPArticlesMessage {
        private String title; // 必填。标题，不超过128个字节，超过会自动截断

        @JSONField(name = "thumb_media_id")
        private String thumbMediaId; // 必填。图文消息缩略图的media_id, 可以在上传多媒体文件接口中获得。此处thumb_media_id即上传接口返回的media_id

        private String author; // 图文消息的作者，不超过64个字节。

        @JSONField(name = "content_source_url")
        private String contSourceUrl; // 图文消息点击“阅读原文”之后的页面链接
        private String content; // 必填。 图文消息的内容，支持html标签，不超过666 K个字节
        private String digest; // 图文消息的描述，不超过512个字节，超过会自动截断

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getThumbMediaId() {
            return thumbMediaId;
        }

        public void setThumbMediaId(String thumbMediaId) {
            this.thumbMediaId = thumbMediaId;
        }

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public String getContSourceUrl() {
            return contSourceUrl;
        }

        public void setContSourceUrl(String contSourceUrl) {
            this.contSourceUrl = contSourceUrl;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getDigest() {
            return digest;
        }

        public void setDigest(String digest) {
            this.digest = digest;
        }
    }

    public List<MPArticlesMessage> getArticles() {
        return articles;
    }

    public void setArticles(List<MPArticlesMessage> articles) {
        this.articles = articles;
    }

    public boolean isSafe() {
        return safe;
    }

    public void setSafe(boolean safe) {
        this.safe = safe;
    }
}

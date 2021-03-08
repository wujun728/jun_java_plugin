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
 * @description 图文消息
 * @date 2017/7/31
 * @since 1.0
 */
public class NewsMessage extends BasicMessage {
    private List<ArticlesMessage> articles; // 必填。图文消息，一个图文消息支持1到8条图文
    private String btnTxt; // 按钮文字，仅在图文数为1条时才生效。 默认为“阅读全文”， 不超过4个文字，超过自动截断。

    @Override
    public Map<String, Object> getParamMap() throws WeixinException {
        if (MessageType.NEWS != messageType || CollectionUtils.isEmpty(articles)) {
            throw new WeixinException(-1,"text的消息类型错误，或图文消息为空");
        }

        // 过滤出符合条件的图文消息
        List<ArticlesMessage> articlesList = articles.stream().filter(article -> StringUtils.isNotBlank(article.getTitle()))
                .collect(Collectors.toList());
        if (CollectionUtils.isEmpty(articlesList)) {
            throw new IllegalArgumentException("图文消息的标题不能为空");
        }

        Map<String, String> mediaMap = new HashMap<>();
        mediaMap.put("articles", JSON.toJSONString(articlesList));

        Map<String, Object> paramMap = super.getParamMap();
        paramMap.put("news", mediaMap);
        return paramMap;
    }

    class ArticlesMessage {

        private String title; // 必填。标题，不超过128个字节，超过会自动截断
        private String description; // 描述，不超过512个字节，超过会自动截断
        private String url; // 点击后跳转的链接
        @JSONField(name = "picurl")
        private String picUrl; // 图文消息的图片链接，支持JPG、PNG格式，较好的效果为大图640320，小图8080。

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

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getPicUrl() {
            return picUrl;
        }

        public void setPicUrl(String picUrl) {
            this.picUrl = picUrl;
        }
    }
    public List<ArticlesMessage> getArticles() {
        return articles;
    }

    public void setArticles(List<ArticlesMessage> articles) {
        this.articles = articles;
    }

    public String getBtnTxt() {
        return btnTxt;
    }

    public void setBtnTxt(String btnTxt) {
        this.btnTxt = btnTxt;
    }
}

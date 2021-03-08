package com.demo.weixin.entity.message;

import com.demo.weixin.enums.MessageType;
import com.demo.weixin.exception.WeixinException;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Wujun
 * @description 文本卡片消息
 * @date 2017/7/31
 * @since 1.0
 */
public class TextCardMessage extends BasicMessage {
    private String title; // 必填。标题，不超过128个字节，超过会自动截断
    private String description; // 必填。 描述(可以是富文本)，不超过512个字节，超过会自动截断
    private String url; // 必填。 点击后跳转的链接。
    private String btnTxt; // 按钮文字。 默认为“详情”， 不超过4个文字，超过自动截断。

    @Override
    public Map<String, Object> getParamMap() throws WeixinException {
        if (MessageType.TEXT_CARD != messageType || StringUtils.isAnyBlank(title, description, url)) {
            throw new WeixinException(-1,"textcard的消息类型错误，或title,description,url为空");
        }
        Map<String, String> textCardMap = new HashMap<>();
        textCardMap.put("title", title);
        textCardMap.put("description", description);
        textCardMap.put("url", url);

        Map<String, Object> paramMap = super.getParamMap();
        paramMap.put("textcard", textCardMap);
        // TODO api不够详细。没有说明 btntxt 参数具体怎么传
        this.addValueFromString("btntxt", btnTxt, paramMap);
        return paramMap;
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getBtnTxt() {
        return btnTxt;
    }

    public void setBtnTxt(String btnTxt) {
        this.btnTxt = btnTxt;
    }
}

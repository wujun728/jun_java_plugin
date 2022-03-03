package org.springrain.weixin.sdk.mp.bean.template;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.springrain.weixin.sdk.mp.util.json.WxMpGsonBuilder;

/**
 * 参考 http://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1433751277&token=&lang=zh_CN 发送模板消息接口部分
 */
public class WxMpTemplateMessage implements Serializable {
  private static final long serialVersionUID = 5063374783759519418L;
  
  private String formId;
  private String toUser;
  private String templateId;
  private String url;
  private String page;
  private List<WxMpTemplateData> data = new ArrayList<>();

  public String getToUser() {
    return this.toUser;
  }

  public void setToUser(String toUser) {
    this.toUser = toUser;
  }

  public String getTemplateId() {
    return this.templateId;
  }

  public void setTemplateId(String templateId) {
    this.templateId = templateId;
  }

  public String getUrl() {
    return this.url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public List<WxMpTemplateData> getData() {
    return this.data;
  }

  public void setData(List<WxMpTemplateData> data) {
    this.data = data;
  }

  public void addWxMpTemplateData(WxMpTemplateData datum) {
    this.data.add(datum);
  }
  
  public String getFormId() {
		return formId;
  }
	
  public void setFormId(String formId) {
		this.formId = formId;
  }
  

	public String getPage() {
		return page;
	}
	
	public void setPage(String page) {
		this.page = page;
	}

public String toJson() {
    return WxMpGsonBuilder.INSTANCE.create().toJson(this);
  }

  public static WxMpTemplateMessageBuilder builder() {
    return new WxMpTemplateMessageBuilder();
  }

  public static class WxMpTemplateMessageBuilder {
    private String toUser;
    private String templateId;
    private String url;
    private String formId;
    private String page;
    
    private List<WxMpTemplateData> data = new ArrayList<>();

    public WxMpTemplateMessageBuilder toUser(String toUser) {
      this.toUser = toUser;
      return this;
    }

    public WxMpTemplateMessageBuilder templateId(String templateId) {
      this.templateId = templateId;
      return this;
    }

    public WxMpTemplateMessageBuilder url(String url) {
      this.url = url;
      return this;
    }

    public WxMpTemplateMessageBuilder data(List<WxMpTemplateData> data) {
      this.data = data;
      return this;
    }
    
    public WxMpTemplateMessageBuilder formId(String formId) {
        this.formId = formId;
        return this;
      }
    
    public WxMpTemplateMessageBuilder page(String page) {
        this.page = page;
        return this;
    }

    public WxMpTemplateMessageBuilder from(WxMpTemplateMessage origin) {
      this.toUser(origin.toUser);
      this.templateId(origin.templateId);
      this.url(origin.url);
      this.data(origin.data);
      this.formId(origin.formId);
      this.page(page);
      return this;
    }

    public WxMpTemplateMessage build() {
      WxMpTemplateMessage m = new WxMpTemplateMessage();
      m.toUser = this.toUser;
      m.templateId = this.templateId;
      m.url = this.url;
      m.data = this.data;
      m.formId = this.formId;
      m.page = this.page;
      return m;
    }
  }

}

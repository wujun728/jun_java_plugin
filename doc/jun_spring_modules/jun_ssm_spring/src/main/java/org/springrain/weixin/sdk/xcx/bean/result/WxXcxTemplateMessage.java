package org.springrain.weixin.sdk.xcx.bean.result;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.springrain.weixin.sdk.mp.util.json.WxMpGsonBuilder;

/**
 * 参考 http://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1433751277&token=&lang=zh_CN 发送模板消息接口部分
 */
public class WxXcxTemplateMessage implements Serializable {
  private static final long serialVersionUID = 5063374783759519418L;
  
  private String form_id;
  private String toUser;
  private String template_id;
  private String url;
  private List<WxXcxTemplateData> data = new ArrayList<>();

  public String getToUser() {
    return this.toUser;
  }

  public void setToUser(String toUser) {
    this.toUser = toUser;
  }

  public String getUrl() {
    return this.url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public List<WxXcxTemplateData> getData() {
    return this.data;
  }

  public void setData(List<WxXcxTemplateData> data) {
    this.data = data;
  }

  public void addWxMpTemplateData(WxXcxTemplateData datum) {
    this.data.add(datum);
  }
  
	public String getForm_id() {
		return form_id;
	}
	
	public void setForm_id(String form_id) {
		this.form_id = form_id;
	}
	
	public String getTemplate_id() {
		return template_id;
	}
	
	public void setTemplate_id(String template_id) {
		this.template_id = template_id;
	}

public String toJson() {
    return WxMpGsonBuilder.INSTANCE.create().toJson(this);
  }

  public static WxMpTemplateMessageBuilder builder() {
    return new WxMpTemplateMessageBuilder();
  }

  public static class WxMpTemplateMessageBuilder {
    private String toUser;
    private String template_id;
    private String url;
    private String form_id;
    private List<WxXcxTemplateData> data = new ArrayList<>();

    public WxMpTemplateMessageBuilder toUser(String toUser) {
      this.toUser = toUser;
      return this;
    }

    public WxMpTemplateMessageBuilder template_id(String templateId) {
      this.template_id = templateId;
      return this;
    }

    public WxMpTemplateMessageBuilder url(String url) {
      this.url = url;
      return this;
    }

    public WxMpTemplateMessageBuilder data(List<WxXcxTemplateData> data) {
      this.data = data;
      return this;
    }
    
    public WxMpTemplateMessageBuilder form_id(String form_id) {
        this.form_id = form_id;
        return this;
      }

    public WxMpTemplateMessageBuilder from(WxXcxTemplateMessage origin) {
      this.toUser(origin.toUser);
      this.template_id(origin.template_id);
      this.url(origin.url);
      this.data(origin.data);
      this.form_id(origin.form_id);
      return this;
    }

    public WxXcxTemplateMessage build() {
      WxXcxTemplateMessage m = new WxXcxTemplateMessage();
      m.toUser = this.toUser;
      m.template_id = this.template_id;
      m.url = this.url;
      m.data = this.data;
      m.form_id = this.form_id;
      return m;
    }
  }

}

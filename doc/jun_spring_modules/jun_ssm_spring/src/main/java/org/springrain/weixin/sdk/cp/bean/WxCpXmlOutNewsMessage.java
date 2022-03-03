package org.springrain.weixin.sdk.cp.bean;

import java.util.ArrayList;
import java.util.List;

import org.springrain.weixin.sdk.common.api.WxConsts;
import org.springrain.weixin.sdk.common.util.xml.XStreamCDataConverter;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamConverter;

@XStreamAlias("xml")
public class WxCpXmlOutNewsMessage extends WxCpXmlOutMessage {
  private static final long serialVersionUID = -5796178637883178826L;

  @XStreamAlias("Articles")
  protected final List<Item> articles = new ArrayList<>();
  @XStreamAlias("ArticleCount")
  protected int articleCount;

  public WxCpXmlOutNewsMessage() {
    this.msgType = WxConsts.XML_MSG_NEWS;
  }

  public int getArticleCount() {
    return this.articleCount;
  }

  public void addArticle(Item item) {
    this.articles.add(item);
    this.articleCount = this.articles.size();
  }

  public List<Item> getArticles() {
    return this.articles;
  }


  @XStreamAlias("item")
  public static class Item {

    @XStreamAlias("Title")
    @XStreamConverter(value = XStreamCDataConverter.class)
    private String Title;

    @XStreamAlias("Description")
    @XStreamConverter(value = XStreamCDataConverter.class)
    private String Description;

    @XStreamAlias("PicUrl")
    @XStreamConverter(value = XStreamCDataConverter.class)
    private String PicUrl;

    @XStreamAlias("Url")
    @XStreamConverter(value = XStreamCDataConverter.class)
    private String Url;

    public String getTitle() {
      return this.Title;
    }

    public void setTitle(String title) {
      this.Title = title;
    }

    public String getDescription() {
      return this.Description;
    }

    public void setDescription(String description) {
      this.Description = description;
    }

    public String getPicUrl() {
      return this.PicUrl;
    }

    public void setPicUrl(String picUrl) {
      this.PicUrl = picUrl;
    }

    public String getUrl() {
      return this.Url;
    }

    public void setUrl(String url) {
      this.Url = url;
    }

  }


}

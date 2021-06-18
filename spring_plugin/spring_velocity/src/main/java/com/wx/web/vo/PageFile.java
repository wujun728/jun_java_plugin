package com.wx.web.vo;

/**
 * @author Wujun
 *
 */
public class PageFile {

    private String url;

    private String title;

    private String keywords;

    private String description;

    public PageFile() {
        super();
    }

    public PageFile(String url, String title, String keywords, String description) {
        super();
        this.url = url;
        this.title = title;
        this.keywords = keywords;
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}

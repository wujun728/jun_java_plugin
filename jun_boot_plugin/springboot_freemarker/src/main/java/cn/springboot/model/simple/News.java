package cn.springboot.model.simple;

import java.util.Date;

import cn.springboot.model.BaseEntity;


/** 
 * @Description 新闻对象
 * @author Wujun
 * @date Mar 16, 2017 3:25:39 PM  
 */
public class News implements BaseEntity<String> {

    private static final long serialVersionUID = 3624947930970250778L;

    private String id;

    private String title;// 新闻标题

    private String description;// 新闻内容

    private String address;// 新闻发生地址

    private Date newsTime;// 新闻发生时间

    private Date createTime;// 新闻发布时间

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getNewsTime() {
        return newsTime;
    }

    public void setNewsTime(Date newsTime) {
        this.newsTime = newsTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

}

package com.jun.plugin.mybatis;

import java.util.Date;

/**
 * 辅助实体类：博客（用于测试类型转换器、复杂foreach等）
 */
public class Blog {
    private Integer id;
    private String blogTitle; // 对应数据库列：blog_title
    private String content;
    private Date publishTime; // 对应数据库列：publish_time（测试日期类型转换）
    private Integer authorId;

    // 无参构造器
    public Blog() {}

    // 有参构造器
    public Blog(String blogTitle, String content, Date publishTime, Integer authorId) {
        this.blogTitle = blogTitle;
        this.content = content;
        this.publishTime = publishTime;
        this.authorId = authorId;
    }

    // getter/setter 方法
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getBlogTitle() { return blogTitle; }
    public void setBlogTitle(String blogTitle) { this.blogTitle = blogTitle; }
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
    public Date getPublishTime() { return publishTime; }
    public void setPublishTime(Date publishTime) { this.publishTime = publishTime; }
    public Integer getAuthorId() { return authorId; }
    public void setAuthorId(Integer authorId) { this.authorId = authorId; }

    // toString() 方法
    @Override
    public String toString() {
        return "Blog{" +
                "id=" + id +
                ", blogTitle='" + blogTitle + '\'' +
                ", content='" + content + '\'' +
                ", publishTime=" + publishTime +
                ", authorId=" + authorId +
                '}';
    }
}

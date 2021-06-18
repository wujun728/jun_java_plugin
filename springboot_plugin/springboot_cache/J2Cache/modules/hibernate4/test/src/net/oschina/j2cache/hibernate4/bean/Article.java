package net.oschina.j2cache.hibernate4.bean;


import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "TEST_CACHE_ARTICLE")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Article implements Serializable {

    @Id
    @Column(name = "ID", nullable = false, insertable = true, updatable = true)
    @GenericGenerator(name="systemUUID",strategy="uuid")
    @GeneratedValue(generator="systemUUID")
    private String id;
    /**
     * 文章标题
     */
    @Column(name = "TITLE")
    private String title;
    /**
     * 摘要
     */
    @Column(name = "SUMMARY")
    private String summary;
    /**
     * 发布标识
     */
    @Column(name = "ISSUE")
    private Boolean issue = false;

    /**
     * 创建人
     */
    @Column(name = "CREATOR", length = 20)
    private String creator;
    /**
     * 创建时间
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(updatable = false, name = "CREATE_TIME")
    private Date createTime;
    /**
     * 最后修改人
     */
    @Column(name = "MODIFIER", length = 20)
    private String modifier;
    /**
     * 最后修改时间
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "MODIFY_TIME")
    private Date modifyTime;

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

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public Boolean getIssue() {
        return issue;
    }

    public void setIssue(Boolean issue) {
        this.issue = issue;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getModifier() {
        return modifier;
    }

    public void setModifier(String modifier) {
        this.modifier = modifier;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    @Override
    public String toString() {
        return "Article[hashCode="+this.hashCode()+"]{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", summary='" + summary + '\'' +
                '}';
    }
}

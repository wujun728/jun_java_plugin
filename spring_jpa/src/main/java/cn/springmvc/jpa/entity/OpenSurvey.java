package cn.springmvc.jpa.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * @author Wujun
 *
 */
@Entity
@Table(name = "d_open_survey")
public class OpenSurvey implements BaseEntity<String> {

    private static final long serialVersionUID = 1538192110129669137L;

    private String id = null;

    // "调查名字")
    private String name = null;

    // 调查详细描述")
    private String description = null;

    // "创建人id")
    private String uid = null;

    // "创建时间")
    private Date createtime = null;

    // "是否分享报告数据")
    private Integer allowreport = null;

    // "分类")
    private Integer type = null;

    // "分类名")
    private String typename = null;

    // "当前调查样本量，默认5000")
    private Integer samplecount = null;

    // "当前已做过调查人数")
    private Integer samplesum = null;

    // "访问人数")
    private Integer viewersum = null;

    // "自定义链接")
    private String surveyurl = null;

    // "页数")
    private Integer pagecount = null;

    // "问题数")
    private Integer questioncount = null;

    // "热门样本数")
    private Integer usersurveycount = null;

    // "是否显示")
    private Integer show = null;

    // "标签")
    private String tag = null;

    @Id
    @Column(name = "f_id", length = 32)
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Column(name = "f_name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "f_description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Column(name = "f_uid")
    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    @Column(name = "f_createtime")
    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    @Column(name = "f_allowreport")
    public Integer getAllowreport() {
        return allowreport;
    }

    public void setAllowreport(Integer allowreport) {
        this.allowreport = allowreport;
    }

    @Column(name = "f_type")
    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    @Column(name = "f_typename")
    public String getTypename() {
        return typename;
    }

    public void setTypename(String typename) {
        this.typename = typename;
    }

    @Column(name = "f_samplecount")
    public Integer getSamplecount() {
        return samplecount;
    }

    public void setSamplecount(Integer samplecount) {
        this.samplecount = samplecount;
    }

    @Column(name = "f_samplesum")
    public Integer getSamplesum() {
        return samplesum;
    }

    public void setSamplesum(Integer samplesum) {
        this.samplesum = samplesum;
    }

    @Column(name = "f_viewersum")
    public Integer getViewersum() {
        return viewersum;
    }

    public void setViewersum(Integer viewersum) {
        this.viewersum = viewersum;
    }

    @Column(name = "f_surveyurl")
    public String getSurveyurl() {
        return surveyurl;
    }

    public void setSurveyurl(String surveyurl) {
        this.surveyurl = surveyurl;
    }

    @Column(name = "f_pagecount")
    public Integer getPagecount() {
        return pagecount;
    }

    public void setPagecount(Integer pagecount) {
        this.pagecount = pagecount;
    }

    @Column(name = "f_questioncount")
    public Integer getQuestioncount() {
        return questioncount;
    }

    public void setQuestioncount(Integer questioncount) {
        this.questioncount = questioncount;
    }

    @Column(name = "f_usersurveycount")
    public Integer getUsersurveycount() {
        return usersurveycount;
    }

    public void setUsersurveycount(Integer usersurveycount) {
        this.usersurveycount = usersurveycount;
    }

    @Column(name = "f_show")
    public Integer getShow() {
        return show;
    }

    public void setShow(Integer show) {
        this.show = show;
    }

    @Column(name = "f_tag")
    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

}

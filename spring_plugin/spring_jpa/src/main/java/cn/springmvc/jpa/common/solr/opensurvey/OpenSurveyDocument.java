package cn.springmvc.jpa.common.solr.opensurvey;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.solr.client.solrj.beans.Field;

import cn.springmvc.jpa.common.utils.fmt.FormatFactory;

/**
 * OpenSurvey 对应 Solr的包装类对象
 * 
 * @author Vincent.wang
 *
 */
public class OpenSurveyDocument implements Serializable {

    private static final long serialVersionUID = -7845854665654413264L;

    @Field
    private String id;

    // 自定义链接
    @Field
    private String surveyurl;

    // 调查名字
    @Field
    private String name;

    // 调查详细描述
    @Field
    private String description;

    // 创建时间
    @Field
    private Date createtime;

    // 分类
    @Field
    private Integer type;

    // 分类名
    @Field
    private String typename;

    // 页数
    @Field
    private Integer pagecount;

    // 问题数
    @Field
    private Integer questioncount;

    // 查看数
    @Field
    private Integer viewersum;

    // 当前已做过调查人数
    @Field
    private Integer samplesum;

    // 是否分享报告数据
    @Field
    private Integer allowreport;

    // 是否显示
    @Field
    private Integer show;

    // 标签ID
    @Field
    private String[] tagid;

    // 标签
    @Field
    private String tag;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSurveyurl() {
        return surveyurl;
    }

    public void setSurveyurl(String surveyurl) {
        this.surveyurl = surveyurl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getTypename() {
        return typename;
    }

    public void setTypename(String typename) {
        this.typename = typename;
    }

    public Integer getPagecount() {
        return pagecount;
    }

    public void setPagecount(Integer pagecount) {
        this.pagecount = pagecount;
    }

    public Integer getQuestioncount() {
        return questioncount;
    }

    public void setQuestioncount(Integer questioncount) {
        this.questioncount = questioncount;
    }

    public Integer getViewersum() {
        return viewersum;
    }

    public void setViewersum(Integer viewersum) {
        this.viewersum = viewersum;
    }

    public Integer getSamplesum() {
        return samplesum;
    }

    public void setSamplesum(Integer samplesum) {
        this.samplesum = samplesum;
    }

    public Integer getShow() {
        return show;
    }

    public void setShow(Integer show) {
        this.show = show;
    }

    public Integer getAllowreport() {
        return allowreport;
    }

    public void setAllowreport(Integer allowreport) {
        this.allowreport = allowreport;
    }

    public String[] getTagid() {
        return tagid;
    }

    public void setTagid(String[] tagid) {
        this.tagid = tagid;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;

        if (StringUtils.isNotBlank(tag)) {
            @SuppressWarnings("unchecked")
            Map<String, String> tags = FormatFactory.jsonToObject(Map.class, tag);
            if (MapUtils.isNotEmpty(tags)) {
                List<String> keys = new ArrayList<String>();
                keys.addAll(tags.keySet());
                if (CollectionUtils.isNotEmpty(keys)) {
                    tagid = (String[]) keys.toArray(new String[keys.size()]);
                }
            }
        }

    }
}

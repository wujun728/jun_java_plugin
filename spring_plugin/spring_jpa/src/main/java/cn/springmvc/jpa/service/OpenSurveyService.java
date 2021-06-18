package cn.springmvc.jpa.service;

import cn.springmvc.jpa.common.solr.opensurvey.OpenSurveyDocument;
import cn.springmvc.jpa.common.utils.Pagination;
import cn.springmvc.jpa.entity.OpenSurvey;

/**
 * @author Wujun
 *
 */
public interface OpenSurveyService {

    /**
     * 将所有公开问卷更新至solr库中
     */
    public void openSurveySynchroAllToSolr();

    /**
     * 保存或更新solr库当中的公开问卷
     * 
     * @param openSurvey
     */
    public void saveOrUpdateOpenSurveyDocument(OpenSurvey openSurvey);

    /**
     * 清空solr库当中所有的公开问卷数据
     */
    public void clearOpenSurveyDocumentAll();

    /**
     * 根据ID查询公开问卷
     * 
     * @param id
     *            公开问卷ID
     * @return
     */
    public OpenSurvey findOpenSurveyById(String id);

    /**
     * 根据参数分页查询条目
     * 
     * @param start
     *            开始
     * @param pageSize
     *            每一页的大小
     * @param param
     *            参数
     * @param value
     *            值
     * @param sort
     *            排序方式
     * @return
     */
    public Pagination<OpenSurveyDocument> pagedFindOpenSurveyDocumentListByProperty(int start, int pageSize, String keywords);

}

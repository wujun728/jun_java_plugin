package cn.springmvc.jpa.common.solr.opensurvey;

import java.util.List;

import org.apache.solr.client.solrj.SolrQuery.SortClause;

import cn.springmvc.jpa.common.solr.BaseSolrRepository;
import cn.springmvc.jpa.common.utils.Pagination;
import cn.springmvc.jpa.entity.OpenSurvey;

/**
 * @author Wujun
 *
 */
public interface OpenSurveySolrRepository extends BaseSolrRepository<OpenSurveyDocument, String> {

    /**
     * 根据条目构建要加入solr的对象
     * 
     * @param openSurvey
     * @return
     */
    public OpenSurveyDocument getOpenSurveyDocument(OpenSurvey openSurvey);

    /**
     * 根据条件查询条目
     * 
     * @param filterStr
     * @param sortClauses
     * @return
     */
    public List<OpenSurveyDocument> findOpenSurveyDocumentListByProperty(String filterStr, List<SortClause> sortClauses);

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
    public Pagination<OpenSurveyDocument> pagedFindOpenSurveyDocumentListByProperty(int start, int pageSize, String filterStr, List<SortClause> sortClauses);

    /**
     * 删除solr服务器上所有的数据
     */
    public void clearAll();
}

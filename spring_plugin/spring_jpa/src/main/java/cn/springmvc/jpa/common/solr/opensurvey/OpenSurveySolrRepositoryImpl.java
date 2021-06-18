package cn.springmvc.jpa.common.solr.opensurvey;

import java.io.IOException;
import java.util.List;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrQuery.SortClause;
import org.apache.solr.client.solrj.SolrServerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import cn.springmvc.jpa.common.solr.BaseSolrRepositoryImpl;
import cn.springmvc.jpa.common.utils.Pagination;
import cn.springmvc.jpa.entity.OpenSurvey;

/**
 * @author Vincent.wang
 *
 */
@Service
public class OpenSurveySolrRepositoryImpl extends BaseSolrRepositoryImpl<OpenSurveyDocument, String> implements OpenSurveySolrRepository {

    private Logger logger = LoggerFactory.getLogger(OpenSurveySolrRepositoryImpl.class);

    @Override
    protected String getId(OpenSurveyDocument doc) {
        return doc.getId();
    }

    @Override
    public OpenSurveyDocument getOpenSurveyDocument(OpenSurvey openSurvey) {
        OpenSurveyDocument doc = new OpenSurveyDocument();
        doc.setId(openSurvey.getId());
        doc.setSurveyurl(openSurvey.getSurveyurl());
        doc.setName(openSurvey.getName());
        doc.setDescription(openSurvey.getDescription());
        doc.setCreatetime(openSurvey.getCreatetime());
        doc.setType(openSurvey.getType());
        doc.setTypename(openSurvey.getTypename());
        doc.setPagecount(openSurvey.getPagecount());
        doc.setQuestioncount(openSurvey.getQuestioncount());
        doc.setViewersum(openSurvey.getViewersum());
        doc.setSamplesum(openSurvey.getSamplesum());
        doc.setAllowreport(openSurvey.getAllowreport());
        doc.setShow(openSurvey.getShow());
        doc.setTag(openSurvey.getTag());
        return doc;
    }

    @Override
    public List<OpenSurveyDocument> findOpenSurveyDocumentListByProperty(String filterStr, List<SortClause> sortClauses) {
        SolrQuery solrQuery = new SolrQuery();
        solrQuery.setQuery(filterStr);
        setSort(solrQuery, sortClauses);
        return super.findByQuery(solrQuery);
    }

    @Override
    public Pagination<OpenSurveyDocument> pagedFindOpenSurveyDocumentListByProperty(int start, int pageSize, String filterStr, List<SortClause> sortClauses) {
        SolrQuery solrQuery = new SolrQuery();
        solrQuery.setQuery(filterStr);
        setSort(solrQuery, sortClauses);
        return super.findByQuery(start, pageSize, solrQuery);
    }

    private void setSort(SolrQuery solrQuery, List<SortClause> sortClauses) {
        for (SortClause sortClause : sortClauses) {
            solrQuery.addSort(sortClause);
        }
    }

    @Override
    public void clearAll() {
        try {
            solrServer.deleteByQuery("*:*");
            solrServer.commit();
            logger.debug("##  delete all OpenSurvey in solr success.");
        } catch (SolrServerException e) {
            logger.error("##  delete all OpenSurvey in solr failed.", e);
            e.printStackTrace();
        } catch (IOException e) {
            logger.error("##  delete all OpenSurvey in solr failed.", e);
            e.printStackTrace();
        }
    }

}

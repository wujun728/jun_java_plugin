package cn.springmvc.jpa.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.solr.client.solrj.SolrQuery.SortClause;
import org.apache.solr.client.solrj.util.ClientUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.springmvc.jpa.common.solr.SolrException;
import cn.springmvc.jpa.common.solr.opensurvey.OpenSurveyDocument;
import cn.springmvc.jpa.common.solr.opensurvey.OpenSurveySolrRepository;
import cn.springmvc.jpa.common.solr.opensurvey.OpenSurveySort;
import cn.springmvc.jpa.common.utils.Pagination;
import cn.springmvc.jpa.entity.OpenSurvey;
import cn.springmvc.jpa.repository.OpenSurveyRepository;
import cn.springmvc.jpa.service.OpenSurveyService;

/**
 * @author Wujun
 *
 */
@Service
public class OpenSurveyServiceImpl implements OpenSurveyService {

    private static final Logger log = LoggerFactory.getLogger(OpenSurveyServiceImpl.class);

    @Autowired
    private OpenSurveySolrRepository openSurveySolrRepository;

    @Autowired
    private OpenSurveyRepository openSurveyRepository;

    @Override
    public void openSurveySynchroAllToSolr() {
        List<OpenSurvey> surveys = openSurveyRepository.findOpenSurveyAll();
        List<OpenSurveyDocument> docs = new ArrayList<OpenSurveyDocument>();

        Iterator<OpenSurvey> iterator = surveys.iterator();
        while (iterator.hasNext()) {
            docs.add(openSurveySolrRepository.getOpenSurveyDocument(iterator.next()));
        }

        // log.warn("## size={}", surveys.size());
        try {
            openSurveySolrRepository.batchSave(docs);
        } catch (SolrException e) {
            log.error("## OpenSurvey Synchro All error , error message={}", e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void saveOrUpdateOpenSurveyDocument(OpenSurvey openSurvey) {
        OpenSurveyDocument doc = openSurveySolrRepository.getOpenSurveyDocument(openSurvey);
        openSurveySolrRepository.saveOrUpdate(doc);
    }

    @Override
    public void clearOpenSurveyDocumentAll() {
        try {
            openSurveySolrRepository.clearAll();
        } catch (SolrException e) {
            log.error("## clear all opensurvey error , error message={}", e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public OpenSurvey findOpenSurveyById(String id) {
        return openSurveyRepository.findOpenSurveyById(id);
    }

    @Override
    public Pagination<OpenSurveyDocument> pagedFindOpenSurveyDocumentListByProperty(int start, int pageSize, String keywords) {
        String filterStr = getQueryString(keywords);
        List<SortClause> sortClauses = new ArrayList<SortClause>();
        sortClauses.add(OpenSurveySort.createtime.desc.getSortClause());
        sortClauses.add(OpenSurveySort.samplesum.desc.getSortClause());
        return openSurveySolrRepository.pagedFindOpenSurveyDocumentListByProperty(start, pageSize, filterStr, sortClauses);
    }

    private String getQueryString(String searchText) {
        StringBuffer stringBuffer = new StringBuffer();
        if (StringUtils.isBlank(searchText)) {
            stringBuffer.append("keyWords:*");
        } else {
            stringBuffer.append("keyWords:" + ClientUtils.escapeQueryChars(searchText));
        }
        return stringBuffer.toString();
    }
}

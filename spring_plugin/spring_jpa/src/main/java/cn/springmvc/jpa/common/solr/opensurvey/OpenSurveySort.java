package cn.springmvc.jpa.common.solr.opensurvey;

import org.apache.solr.client.solrj.SolrQuery.SortClause;

/**
 * @author Vincent.wang
 *
 */
public class OpenSurveySort {

    private static final String CREATETIME = "createtime";
    private static final String SAMPLESUM = "samplesum";

    public enum samplesum {
        desc, asc;
        public SortClause getSortClause() {
            return (this == asc) ? SortClause.asc(SAMPLESUM) : SortClause.desc(SAMPLESUM);
        }
    }

    public enum createtime {
        desc, asc;
        public SortClause getSortClause() {
            return (this == asc) ? SortClause.asc(CREATETIME) : SortClause.desc(CREATETIME);
        }
    }
}

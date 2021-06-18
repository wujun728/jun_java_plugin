package cn.springmvc.jpa.util.solr;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.springmvc.jpa.common.solr.opensurvey.OpenSurveyDocument;
import cn.springmvc.jpa.common.utils.Pagination;
import cn.springmvc.jpa.entity.OpenSurvey;
import cn.springmvc.jpa.service.OpenSurveyService;

/**
 * @author Wujun
 *
 *         production为生产环境，development为测试环境
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/spring/applicationContext.xml", "classpath:/spring/applicationContext-dao.xml", "classpath:/spring/applicationContext-solr.xml" })
@ActiveProfiles("development")
public class OpenSurveyServiceTest {

    private static final Logger log = LoggerFactory.getLogger(OpenSurveyServiceTest.class);

    @Autowired
    private OpenSurveyService OpenSurveyService;

    @Test
    public void synchroAll() {
        try {
            OpenSurveyService.openSurveySynchroAllToSolr();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void clearAll() {
        try {
            OpenSurveyService.clearOpenSurveyDocumentAll();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void saveOrUpdate() {
        try {
            String id = "00c3d1a9-ea7d-41cd-a07f-f5768de42950";
            OpenSurvey survey = OpenSurveyService.findOpenSurveyById(id);
            survey.setDescription("测试更新至solr");
            OpenSurveyService.saveOrUpdateOpenSurveyDocument(survey);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void queryPyPage() {
        try {
            int start = 0;
            int pageSize = 10;
            String keywords = "晕死测试";
            Pagination<OpenSurveyDocument> pagination = OpenSurveyService.pagedFindOpenSurveyDocumentListByProperty(start, pageSize, keywords);
            for (OpenSurveyDocument doc : pagination.getItems()) {
                log.warn("## id={} , name={} , description={} , type={} , typename={} ", doc.getId(), doc.getName(), doc.getDescription(), doc.getType(), doc.getTypename());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

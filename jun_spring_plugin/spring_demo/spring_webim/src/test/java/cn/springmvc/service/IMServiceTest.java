package cn.springmvc.service;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.springmvc.model.Archivemessages;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/spring/applicationContext.xml", "classpath:/spring/applicationContext-dao.xml" })
public class IMServiceTest {

    private static final Logger log = LoggerFactory.getLogger(IMServiceTest.class);

    @Autowired
    private IMService iMService;

    @Test
    public void findArchivemessagesAll() {
        try {
            List<Archivemessages> msgs = iMService.findArchivemessagesAll();
            if (CollectionUtils.isNotEmpty(msgs)) {
                for (Archivemessages msg : msgs) {
                    log.info("# ownerJid={} , withJid={} , direction={} , body={}", msg.getOwnerjid(), msg.getWithjid(), msg.getDirection(), msg.getBody());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}

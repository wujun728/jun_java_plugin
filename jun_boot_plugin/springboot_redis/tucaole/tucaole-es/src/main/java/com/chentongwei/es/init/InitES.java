package com.chentongwei.es.init;

import com.chentongwei.es.factory.ElasticsearchClientFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;

/**
 * @author Wujun
 * @Project tucaole
 * @Description: ES启动类
 */
@RestController
public class InitES {
    private static final Logger LOG = LogManager.getLogger(InitES.class);

    @Autowired
    private ElasticsearchClientFactory esClientFactory;

    /**
     * ES启动类
     */
	@PostConstruct
    public void initES() {
        esClientFactory.client();
        LOG.info("--------------------ES启动成功---------------------");
    }
}

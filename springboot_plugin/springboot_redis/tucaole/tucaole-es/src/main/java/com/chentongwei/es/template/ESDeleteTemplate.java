package com.chentongwei.es.template;

import com.chentongwei.es.factory.ElasticsearchClientFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.elasticsearch.client.transport.TransportClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Wujun
 * @Project tucaole
 * @Description: Elasticsearch删除工具类
 */
@Component
public class ESDeleteTemplate {
    private static final Logger LOG = LogManager.getLogger("bizLog");

    @Autowired
    private ElasticsearchClientFactory clientFactory;

    /**
     * 删除文档
     *
     * @param index：索引
     * @param type：类型
     * @param id：文档id
     */
    public void deleteDocument(final String index, final String type, final String id) {
        getClient().prepareDelete(index, type, id).get();
    }

    /**
     * 删除索引
     *
     * @param index：索引
     */
    public void deleteIndex(final String ... index) {
        getClient().admin().indices().prepareDelete(index).get();
    }

    /**
     * 获取客户端
     *
     * @return
     */
    private TransportClient getClient() {
        return clientFactory.client();
    }

}

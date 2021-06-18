package com.chentongwei.es.template;

import com.alibaba.fastjson.JSON;
import com.chentongwei.es.factory.ElasticsearchClientFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Wujun
 * @Project tucaole
 * @Description: Elasticsearch创建工具类
 */
@Component
public class ESCreateTemplate {
    private static final Logger LOG = LogManager.getLogger("bizLog");

    @Autowired
    private ElasticsearchClientFactory clientFactory;

    /**
     * 新建文档(_id自动生成)
     *
     * @param index：索引
     * @param type：类型
     * @param object：文档
     */
    public void createDocument(final String index, final String type, final Object object) {
        createDocument(index, type, null, object);
    }

    /**
     * 新建文档(_id手动指定)
     *
     * @param index：索引
     * @param type：类型
     * @param object：文档
     * @param id：id
     */
    public void createDocument(final String index, final String type, final String id, final Object object) {
        IndexRequestBuilder indexRequestBuilder = getClient().prepareIndex(index, type);
        if (null != id) {
            indexRequestBuilder.setId(id);
        }
        indexRequestBuilder.setSource(objectToJSON(object), XContentType.JSON).get();
    }

    /**
     * 新建多个文档(_id自动生成)
     *
     * @param index：索引
     * @param type：类型
     * @param list：文档集合
     */
    public void createMultiDocument(final String index, final String type, final List<?> list) {
        list.forEach(object -> {
            createDocument(index, type, object);
        });
    }

    /**
     * 批量创建document
     * 若数据量成千上万，建议用此方法
     *
     * 亲测：36000条document用此方法不到10s中即可完成，而用createMultiDocument大概需要一小时。
     *
     * @param index：索引
     * @param type：类型
     * @param list：document集合
     */
    public void bulkDocument(final String index, final String type, final List<?> list) {
        BulkRequestBuilder bulkRequestBuilder = getClient().prepareBulk();
        list.forEach(object -> {
            IndexRequestBuilder indexRequestBuilder = getClient().prepareIndex(index, type).setSource(objectToJSON(object), XContentType.JSON);
            bulkRequestBuilder.add(indexRequestBuilder);
        });
        bulkRequestBuilder.get();
    }

    /**
     * Object --》 json
     *
     * @param object：对象
     * @return
     */
    private String objectToJSON(Object object) {
        return JSON.toJSONString(object);
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
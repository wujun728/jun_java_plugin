package org.itkk.udf.id.service;

import org.itkk.udf.id.IdWorkerFactory;
import org.itkk.udf.id.IdWorkerProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * IdWorkerService
 */
@Service
public class IdWorkerService {

    /**
     * idWorkerFactory
     */
    @Autowired
    private IdWorkerFactory idWorkerFactory;

    /**
     * idWorkerProperties
     */
    @Autowired
    private IdWorkerProperties idWorkerProperties;

    /**
     * 获得分布式ID
     *
     * @return 分布式ID
     */
    public String nextId() {
        return idWorkerFactory.nextId();
    }

    /**
     * 获得分布式ID
     *
     * @param dwId 数据中心ID | 机器ID ( 0 - 1023 )
     * @return 分布式ID
     */
    public String nextId(Integer dwId) {
        return idWorkerFactory.nextId(dwId);
    }

    /**
     * 获得分布式ID
     *
     * @param datacenterId 数据中心ID ( 0 - 31 )
     * @param workerId     机器ID ( 0 - 31 )
     * @return 分布式ID
     */
    public String nextId(Integer datacenterId, Integer workerId) {
        return idWorkerFactory.nextId(datacenterId, workerId);
    }

    /**
     * 批量获得分布式ID
     *
     * @param count count
     * @return 分布式ID
     */
    public List<String> batchNextId(Integer count) {
        return idWorkerFactory.batchNextId(count);
    }

    /**
     * 批量获得分布式ID
     *
     * @param dwId  数据中心ID | 机器ID ( 0 - 1023 )
     * @param count count
     * @return 分布式ID
     */
    public List<String> batchNextId(Integer dwId, Integer count) {
        return idWorkerFactory.batchNextId(dwId, count);
    }

    /**
     * 批量获得分布式ID
     *
     * @param datacenterId 数据中心ID ( 0 - 31 )
     * @param workerId     机器ID ( 0 - 31 )
     * @param count        count
     * @return 分布式ID
     */
    public List<String> batchNextId(Integer datacenterId, Integer workerId, Integer count) {
        return idWorkerFactory.batchNextId(datacenterId, workerId, count);
    }

}

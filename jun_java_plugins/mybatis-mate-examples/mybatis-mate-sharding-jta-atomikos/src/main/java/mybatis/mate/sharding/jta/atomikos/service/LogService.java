package mybatis.mate.sharding.jta.atomikos.service;

import lombok.AllArgsConstructor;
import mybatis.mate.sharding.jta.atomikos.entity.Log;
import mybatis.mate.sharding.jta.atomikos.mapper.LogMapper;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class LogService {
    private LogMapper logMapper;

    /**
     * 减少库存
     *
     * ！！！执行遇到如下错误！！！
     * 【
     *      Caused by: org.postgresql.util.PSQLException: 错误: 禁用已准备好的事务
     *      建议：将max_prepared_transactions设置为一个非零值
     * 】
     * 原因：默认情况下，PostgreSQL并不开启两阶段提交，可以通过在postgresql.conf文件中
     * 设置 max_prepared_transactions 配置项开启PostgreSQL的两阶段提交。
     */
    public void save(String content) {
        Log log = new Log();
        log.setContent(content);
        logMapper.insert(log);
    }
}

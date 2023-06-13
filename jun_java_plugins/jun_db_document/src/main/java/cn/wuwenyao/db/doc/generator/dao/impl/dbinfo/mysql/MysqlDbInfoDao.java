package cn.wuwenyao.db.doc.generator.dao.impl.dbinfo.mysql;

import cn.wuwenyao.db.doc.generator.dao.impl.dbinfo.AbstractDbInfoDao;
import cn.wuwenyao.db.doc.generator.entity.TableInfo;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;

import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;

import static org.slf4j.LoggerFactory.getLogger;

/***
 * 获取mysql数据库信息
 *
 * @author wwy
 *
 */

public final class MysqlDbInfoDao extends AbstractDbInfoDao {


    private static final Logger logger = getLogger(MysqlDbInfoDao.class);

    @Override
    public String databaseName() {
        String databaseName = jdbcTemplate.queryForObject("select database()", String.class);
        return databaseName;
    }

    private List<TableInfo> queryTableInfo() {
        List<TableInfo> tableInfos = jdbcTemplate.query(
                "select table_name,table_comment from information_schema.tables where table_schema = database()",
                new TableInfoRowMapper());
        return tableInfos;
    }

    @Override
    public List<TableInfo> tableInfoList() {
        //查询表信息
        List<TableInfo> tableInfos = queryTableInfo();
        if (CollectionUtils.isEmpty(tableInfos)) {
            return tableInfos;
        }
        //过滤黑名单
        tableInfos = tableInfos.stream().filter(tableInfo -> {
            return isTableGenerate(tableInfo.getTableName());
        }).collect(Collectors.toList());
        //查询表-列信息
        CountDownLatch countDownLatch = new CountDownLatch(tableInfos.size());
        ExecutorService executor = new ThreadPoolExecutor(10, 10,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>(tableInfos.size()));
        tableInfos.stream().forEach((tableInfo) -> {
            executor.execute(new GetTableInfoTask(tableInfo, countDownLatch, jdbcTemplate));
        });
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            logger.error("countDownLatch error", e);
        }
        executor.shutdown();
        return tableInfos;
    }

}

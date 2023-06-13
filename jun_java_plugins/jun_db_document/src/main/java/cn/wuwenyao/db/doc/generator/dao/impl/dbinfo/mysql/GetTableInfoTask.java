package cn.wuwenyao.db.doc.generator.dao.impl.dbinfo.mysql;

import cn.wuwenyao.db.doc.generator.entity.TableFieldInfo;
import cn.wuwenyao.db.doc.generator.entity.TableInfo;
import cn.wuwenyao.db.doc.generator.entity.TableKeyInfo;
import org.slf4j.Logger;
import org.springframework.jdbc.core.ColumnMapRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

import static org.slf4j.LoggerFactory.getLogger;

/***
 * 任务-获取表信息
 * @author wenyao.wu
 * @date 2019/1/30
 */
public class GetTableInfoTask implements Runnable {

    private static final Logger logger = getLogger(GetTableInfoTask.class);

    private TableInfo tableInfo;

    private CountDownLatch countDownLatch;

    private JdbcTemplate jdbcTemplate;

    public GetTableInfoTask(TableInfo tableInfo, CountDownLatch countDownLatch, JdbcTemplate jdbcTemplate) {
        this.tableInfo = tableInfo;
        this.countDownLatch = countDownLatch;
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public void run() {
        try {
            tableInfo.setFields(queryFields());
            tableInfo.setKeys(queryKeys());
            logger.info("表：{}信息查询完毕", tableInfo.getTableName());
        } catch (Exception e) {
            logger.error("任务-获取表信息-异常", e);
        } finally {
            countDownLatch.countDown();
        }

    }

    /***
     * 查询 fields
     * @return
     */
    private List<TableFieldInfo> queryFields() {
        Object[] params = new Object[]{tableInfo.getTableName()};
        List<TableFieldInfo> fields = jdbcTemplate.query(
                "select COLUMN_NAME, COLUMN_COMMENT,COLUMN_DEFAULT,IS_NULLABLE,COLUMN_TYPE,COLUMN_KEY,EXTRA from information_schema.columns where table_schema =database() and table_name = ?",
                params, new TableFieldInfoRowMapper());
        return fields;
    }

    /***
     * 查询 索引
     * @return
     */
    private List<TableKeyInfo> queryKeys() {
        List<Map<String, Object>> rawKeyInfos = jdbcTemplate.query("show keys from " + tableInfo.getTableName(),
                new ColumnMapRowMapper());

        Map<String, TableKeyInfo> keyMap = new HashMap<>(5);
        for (Map<String, Object> rawKeyInfo : rawKeyInfos) {
            TableKeyInfo tableKeyInfo = keyMap.get(rawKeyInfo.get("Key_name").toString());
            String columnName = rawKeyInfo.get("Column_name").toString();
            if (tableKeyInfo == null) {
                tableKeyInfo = new TableKeyInfo();
                ArrayList<String> columns = new ArrayList<>();
                columns.add(columnName);
                tableKeyInfo.setColumns(columns);
                tableKeyInfo.setIndexComment(rawKeyInfo.get("Index_comment").toString());
                tableKeyInfo.setIndexType(rawKeyInfo.get("Index_type").toString());
                tableKeyInfo.setName(rawKeyInfo.get("Key_name").toString());
                tableKeyInfo.setUnique("0".equals(rawKeyInfo.get("Non_unique").toString()));
            } else {
                tableKeyInfo.getColumns().add(columnName);
            }
            keyMap.put(rawKeyInfo.get("Key_name").toString(), tableKeyInfo);
        }
        //索引信息进行排序
        List<TableKeyInfo> tableKeyInfoList = new ArrayList<>(keyMap.values());
        tableKeyInfoList.sort(null);
        return tableKeyInfoList;
    }

}
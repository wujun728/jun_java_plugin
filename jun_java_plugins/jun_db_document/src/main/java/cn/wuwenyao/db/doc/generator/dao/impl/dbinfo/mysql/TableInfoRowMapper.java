package cn.wuwenyao.db.doc.generator.dao.impl.dbinfo.mysql;

import cn.wuwenyao.db.doc.generator.entity.TableInfo;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/***
 * 表信息RowMapper
 * @author wenyao.wu
 * @date 2019/1/30
 */
public class TableInfoRowMapper implements RowMapper<TableInfo> {

    @Override
    public TableInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
        String tableName = rs.getString(1);
        String remark = rs.getString(2);
        TableInfo tableInfo = new TableInfo();
        tableInfo.setTableRemark(remark);
        tableInfo.setTableName(tableName);
        return tableInfo;
    }

}
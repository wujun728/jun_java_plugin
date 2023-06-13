package cn.wuwenyao.db.doc.generator.dao.impl.dbinfo.mysql;

import cn.wuwenyao.db.doc.generator.entity.TableFieldInfo;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/***
 * 列信息RowMapper
 * @author wenyao.wu
 * @date 2019/1/30
 */
public class TableFieldInfoRowMapper implements RowMapper<TableFieldInfo> {

    @Override
    public TableFieldInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
        TableFieldInfo tableFieldInfo = new TableFieldInfo();
        tableFieldInfo.setDefaultValue(rs.getString("COLUMN_DEFAULT"));
        tableFieldInfo.setExtra(rs.getString("EXTRA"));
        tableFieldInfo.setField(rs.getString("COLUMN_NAME"));
        tableFieldInfo.setKey(rs.getString("COLUMN_KEY"));
        tableFieldInfo.setNullAble(rs.getString("IS_NULLABLE"));
        tableFieldInfo.setType(rs.getString("COLUMN_TYPE"));
        tableFieldInfo.setRemark(rs.getString("COLUMN_COMMENT"));
        if (tableFieldInfo.getDefaultValue() == null) {
            tableFieldInfo.setDefaultValue("无");
        }
        return tableFieldInfo;
    }

}
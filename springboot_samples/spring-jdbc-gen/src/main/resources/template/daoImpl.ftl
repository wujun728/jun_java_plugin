package ${packageBase}.dao.impl;

import ${packageBase}.model.${tableBean.tableNameCapitalized};

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Repository("${tableBean.tableNameNoDash}${classPrefix}DAO")
public class ${tableBean.tableNameCapitalized}${classPrefix}DAOImpl implements ${tableBean.tableNameCapitalized}${classPrefix}DAO {
private Logger log = Logger.getLogger(this.getClass());

@Resource
private JdbcTemplate ${jdbcTemplateName};

@Resource
private NamedParameterJdbcTemplate namedParameter${jdbcTemplateNameCapitalized};

@Override
public ${tableBean.tableNameCapitalized} getByPrimaryKey(Integer id) {
try {
String sql = "select * from ${tableBean.tableName} where id = ?";

List<${tableBean.tableNameCapitalized}> resultList = this.${jdbcTemplateName}.query(sql, new Object[]{id},
new RowMapper<${tableBean.tableNameCapitalized}>() {
@Override
public ${tableBean.tableNameCapitalized} mapRow(ResultSet rs, int rowNum) throws SQLException {
${tableBean.tableNameCapitalized} bean = new ${tableBean.tableNameCapitalized}();
<#list tableBean.columnBeanList as columnBean>
    <#if columnBean.columnType == "Date">
    Timestamp ${columnBean.columnNameNoDash}Timestamp = rs.getTimestamp("${columnBean.columnName}");
    if (null != ${columnBean.columnNameNoDash}Timestamp) {
    bean.set${columnBean.columnNameCapitalized}(new Date(${columnBean.columnNameNoDash}Timestamp.getTime()));
    }
    <#else>
    bean.set${columnBean.columnNameCapitalized}(rs.${columnBean.columnTypeRsGetter}("${columnBean.columnName}"));
    </#if>
</#list>
return bean;
}
});

if (null == resultList || resultList.isEmpty()) {
return null;
} else {
return resultList.get(0);
}
} catch (Exception e) {
log.error(e.getMessage(), e);
throw new RuntimeException(e);
}
}

@Override
public List<${tableBean.tableNameCapitalized}> queryByConditions(String whereSql, Object[] params) {
try {
String sql;
if (StringUtils.isBlank(whereSql)) {
sql = "select * from ${tableBean.tableName} ";
} else {
sql = "select * from ${tableBean.tableName} where " + whereSql;
}

return this.${jdbcTemplateName}.query(sql, params,
new RowMapper<${tableBean.tableNameCapitalized}>() {
@Override
public ${tableBean.tableNameCapitalized} mapRow(ResultSet rs, int rowNum) throws SQLException {
${tableBean.tableNameCapitalized} bean = new ${tableBean.tableNameCapitalized}();
<#list tableBean.columnBeanList as columnBean>
    <#if columnBean.columnType == "Date">
    Timestamp ${columnBean.columnNameNoDash}Timestamp = rs.getTimestamp("${columnBean.columnName}");
    if (null != ${columnBean.columnNameNoDash}Timestamp) {
    bean.set${columnBean.columnNameCapitalized}(new Date(${columnBean.columnNameNoDash}Timestamp.getTime()));
    }
    <#else>
    bean.set${columnBean.columnNameCapitalized}(rs.${columnBean.columnTypeRsGetter}("${columnBean.columnName}"));
    </#if>
</#list>
return bean;
}
});
} catch (Exception e) {
log.error(e.getMessage(), e);
throw new RuntimeException(e);
}
}

@Override
public List<${tableBean.tableNameCapitalized}> page(String whereSql, Object[] params, int startPos, int num) {
try {
String sql;
if (StringUtils.isBlank(whereSql)) {
sql = "select * from ${tableBean.tableName} ";
} else {
sql = "select * from ${tableBean.tableName} where " + whereSql;
}

sql += String.format(" limit %d,%d", startPos, num);

return this.${jdbcTemplateName}.query(sql, params,
new RowMapper<${tableBean.tableNameCapitalized}>() {
@Override
public ${tableBean.tableNameCapitalized} mapRow(ResultSet rs, int rowNum) throws SQLException {
${tableBean.tableNameCapitalized} bean = new ${tableBean.tableNameCapitalized}();
<#list tableBean.columnBeanList as columnBean>
    <#if columnBean.columnType == "Date">
    Timestamp ${columnBean.columnNameNoDash}Timestamp = rs.getTimestamp("${columnBean.columnName}");
    if (null != ${columnBean.columnNameNoDash}Timestamp) {
    bean.set${columnBean.columnNameCapitalized}(new Date(${columnBean.columnNameNoDash}Timestamp.getTime()));
    }
    <#else>
    bean.set${columnBean.columnNameCapitalized}(rs.${columnBean.columnTypeRsGetter}("${columnBean.columnName}"));
    </#if>
</#list>
return bean;
}
});
} catch (Exception e) {
log.error(e.getMessage(), e);
throw new RuntimeException(e);
}
}

@Override
public List<${tableBean.tableNameCapitalized}> selectAll() {
return this.queryByConditions(null, null);
}

@Override
public int count(String whereSql, Object[] params) {
try {
String sql;
if (StringUtils.isBlank(whereSql)) {
sql = "select count(1) numCount from ${tableBean.tableName} ";
} else {
sql = "select count(1) numCount from ${tableBean.tableName} where " + whereSql;
}

return this.${jdbcTemplateName}.queryForObject(sql, params,
new RowMapper
<Integer>() {
    @Override
    public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
    return rs.getInt("numCount");
    }
    });
    } catch (Exception e) {
    log.error(e.getMessage(), e);
    throw new RuntimeException(e);
    }
    }

    @Override
    public int insertSelective(${tableBean.tableNameCapitalized} ${tableBean.tableNameNoDash}) {
    try {
    if (null == ${tableBean.tableNameNoDash}) {
    return 0;
    }

    StringBuilder sql = new StringBuilder("insert into ${tableBean.tableName}(");
    List
    <Object> columns = new ArrayList
        <Object>();
            List
            <Object> values = new ArrayList
                <Object>();

                <#list tableBean.columnBeanList as columnBean>
                    if (null != ${tableBean.tableNameNoDash}.get${columnBean.columnNameCapitalized}()) {
                    columns.add("${columnBean.columnName}");
                    values.add(${tableBean.tableNameNoDash}.get${columnBean.columnNameCapitalized}());
                    }
                </#list>

                    if (columns.isEmpty()) {
                    return 0;
                    }

                    sql.append(StringUtils.join(columns, ',')).append(") values (");

                    List
                    <String> questions = new ArrayList
                        <String>(columns.size());
                            for (Object column : columns) {
                            questions.add("?");
                            }

                            sql.append(StringUtils.join(questions, ',')).append(")");

                            return ${jdbcTemplateName}.update(sql.toString(), values.toArray());
                            } catch (Exception e) {
                            log.error(e.getMessage(), e);
                            throw new RuntimeException(e);
                            }
                            }

                            @Override
                            public int save(${tableBean.tableNameCapitalized} ${tableBean.tableNameNoDash}) {
                            try {
                            if (null == ${tableBean.tableNameNoDash}) {
                            return 0;
                            }

                            StringBuilder sql = new StringBuilder("insert into ${tableBean.tableName}(");
                            List
                            <Object> columns = new ArrayList
                                <Object>();
                                    List
                                    <Object> parameters = new ArrayList
                                        <Object>();

                                        <#list tableBean.columnBeanList as columnBean>
                                            if (null != ${tableBean.tableNameNoDash}
                                            .get${columnBean.columnNameCapitalized}()) {
                                            columns.add("${columnBean.columnName}");
                                            parameters.add(":${columnBean.columnNameNoDash}");
                                            }
                                        </#list>

                                            if (columns.isEmpty()) {
                                            return 0;
                                            }

                                            sql.append(StringUtils.join(columns, ',')).append(") values
                                            (").append(StringUtils.join(parameters, ',')).append(")");

                                            SqlParameterSource ps = new
                                            BeanPropertySqlParameterSource(${tableBean.tableNameNoDash});
                                            KeyHolder keyholder = new GeneratedKeyHolder();
                                            int updateNums = namedParameter${jdbcTemplateNameCapitalized}
                                            .update(sql.toString(), ps, keyholder);
                                            int m = keyholder.getKey().intValue();
                                        ${tableBean.tableNameNoDash}.setId(m);

                                            return updateNums;
                                            } catch (Exception e) {
                                            log.error(e.getMessage(), e);
                                            throw new RuntimeException(e);
                                            }
                                            }

                                            @Override
                                            public int
                                            change(${tableBean.tableNameCapitalized} ${tableBean.tableNameNoDash}) {
                                            try {
                                            if (null == ${tableBean.tableNameNoDash}.getId()) {
                                            throw new RuntimeException("updateByPrimaryKeySelective fail! ID can not be
                                            null");
                                            }

                                            StringBuilder sql = new StringBuilder("UPDATE ${tableBean.tableName} SET ");

                                            List
                                            <String> updateSql = new ArrayList
                                                <String>();
                                                    List
                                                    <Object> params = new ArrayList
                                                        <Object>();
                                                        <#list tableBean.columnBeanList as columnBean>
                                                            if (null != ${tableBean.tableNameNoDash}
                                                            .get${columnBean.columnNameCapitalized}()) {
                                                            updateSql.add("${columnBean.columnName} = ?");
                                                            params.add(${tableBean.tableNameNoDash}
                                                            .get${columnBean.columnNameCapitalized}());
                                                            }
                                                        </#list>

                                                            if (updateSql.isEmpty()) { // all fields is null, nothing to
                                                            update
                                                            return 0;
                                                            }

                                                            sql.append(StringUtils.join(updateSql, ", ")).append(" WHERE
                                                            id = ?");
                                                            params.add(${tableBean.tableNameNoDash}.getId());

                                                            return this.${jdbcTemplateName}.update(sql.toString(),
                                                            params.toArray());
                                                            } catch (Exception e) {
                                                            log.error(e.getMessage(), e);
                                                            throw new RuntimeException(e);
                                                            }
                                                            }

                                                            @Override
                                                            public int removeByPrimaryKey(Integer id) {
                                                            if(null == id) {
                                                            return 0;
                                                            }

                                                            String sql = "delete from ${tableBean.tableName} where id =
                                                            ?";
                                                            return ${jdbcTemplateName}.update(sql, id);
                                                            }

                                                            @Override
                                                            public int removeByConditions(String whereSql, Object[]
                                                            params) {
                                                            if(StringUtils.isBlank(whereSql)) {
                                                            return 0;
                                                            }

                                                            String sql = "DELETE from ${tableBean.tableName} where " +
                                                            whereSql;
                                                            return this.${jdbcTemplateName}.update(sql, params);
                                                            }
                                                            }

package com.jun.plugin.dbtracer;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.ParameterMode;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.mapping.StatementType;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandlerRegistry;

import com.alibaba.druid.util.JdbcConstants;
import com.jun.plugin.dbtracer.db.RecordLogger;
import com.jun.plugin.dbtracer.sql.SQLParseResult;
import com.jun.plugin.dbtracer.sql.SqlParser;

@Intercepts({ @Signature(type = Executor.class, method = "update", args = { MappedStatement.class, Object.class }) })
public class UpdateStatementInterceptor implements Interceptor {

    private SqlParser sqlParser;
//    private ObjectTypeHandler OBJECT_TYPE_HANDLER = new ObjectTypeHandler();

    /**
     * update user set name = 'xxxx' where id = 100--> select name from user
     * where id = 100
     * 
     * update user set name = ? where id = ?
     */
    public Object intercept(Invocation invocation) throws Throwable {
        Executor executor = (Executor) invocation.getTarget();
        Object[] args = invocation.getArgs();

        MappedStatement mpStt = (MappedStatement) args[0];
        // update,delete,insert都调用的是Executor的update()方法
        if (!SqlCommandType.UPDATE.equals(mpStt.getSqlCommandType())) {
            return invocation.proceed();
        }

        Object updateParams = (Object) args[1];

        SqlSource sqlSorce = mpStt.getSqlSource();
        BoundSql boundSql = sqlSorce.getBoundSql(updateParams);
        String sql = boundSql.getSql();
        System.out.println(boundSql.getSql());

        UpdateHistory history = null;
        try {
            ResultSet rsOfQuery = null;
            Connection conn = executor.getTransaction().getConnection();

            /**
             * 将预编译SQL替换成普通SQL
             */
            Map<Integer, String> idx2Value = null;
            if (StatementType.PREPARED.equals(mpStt.getStatementType())) {
                idx2Value = calculateParamIndex2Value(mpStt, updateParams);
                sql = replaceByParameter(sql, idx2Value);
            }

            SQLParseResult parseResult = sqlParser.parseUpdateSQL(sql);
            if (null != parseResult) {
                String querySql = parseResult.getQuerySql();
                Statement st = conn.createStatement();
                rsOfQuery = st.executeQuery(querySql);

                int queryRestCnt = 0;
                Map<String, Object> oldCol2Value = new HashMap<String, Object>();
                while (rsOfQuery.next()) {
                    queryRestCnt++;
                    if (queryRestCnt > 1) {
                        // TODO
                        System.out.println("查询结果多于1行");
                        break;
                    }

                    List<String> updateColumes = parseResult.getUpdateColume();
                    for (String col : updateColumes) {
                        oldCol2Value.put(col, rsOfQuery.getObject(col));
                    }
                }

                history = new UpdateHistory();
                history.setNewCol2Val(mapUpdateColIdx2Name(idx2Value, parseResult.getUpdateColIdx2Name()));
                history.setOriginalCol2Val(oldCol2Value);
                history.setTable(parseResult.getTable());
                history.setWhere(parseResult.getWhere());
                history.setKey(parseResult.getKey());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        Object proceedRet;
        try {
            proceedRet = invocation.proceed();

            // 没有抛出异常，则异步执行写Log操作
            RecordLogger.log(history);
        } catch (Exception ex) {
            throw ex;
        }

        return proceedRet;
    }

    private Map<String, String> mapUpdateColIdx2Name(Map<Integer, String> idx2Value, Map<Integer, String> idx2ColName) {
        Map<String, String> colName2Value = new HashMap<String, String>();
        Set<Entry<Integer, String>> set = idx2ColName.entrySet();
        for (Entry<Integer, String> entry : set) {
            colName2Value.put(entry.getValue(), idx2Value.get(entry.getKey()));
        }
        return colName2Value;
    }

    /**
     * 从Update SQL获取 Select SQL
     * 
     * @param updateSql
     * @return
     */
    // private SQLParseResult parseUpdateSQL(String updateSql) {
    // List<SQLStatement> statementList = SQLUtils.parseStatements(updateSql, dbType);
    // if (statementList.size() > 1) {
    // // TODO
    // System.out.print("不支持同时更新多条记录");
    // return null;
    // }
    //
    // SQLParseResult praseResult = new SQLParseResult();
    // StringBuffer selectSb = new StringBuffer();
    // SQLStatement statement = statementList.get(0);
    // int questionMarkIdx = 0;
    // if (statement instanceof SQLUpdateStatement) {
    // SQLUpdateStatement updateStt = (SQLUpdateStatement) statement;
    //
    // praseResult.table = updateStt.getTableName().toString();
    // SQLExpr whereExpr = updateStt.getWhere();
    // System.out.println(whereExpr.toString());
    // List<SQLUpdateSetItem> updateItems = updateStt.getItems();
    // for (SQLUpdateSetItem updateItem : updateItems) {
    // praseResult.updateColume.add(updateItem.getColumn().toString());
    // praseResult.updateColIdx2Name.put(questionMarkIdx, updateItem.getColumn().toString());
    // questionMarkIdx++;
    // System.out.println("Column:" + updateItem.getColumn() + ",value:" + updateItem.getValue());
    // }
    //
    // selectSb.append("SELECT ");
    // List<String> updateColumeNames = praseResult.updateColume;
    // for (int idx = 0; idx < updateColumeNames.size(); idx++) {
    // selectSb.append(updateColumeNames.get(idx));
    // if (idx < updateColumeNames.size() - 1) {
    // selectSb.append(" , ");
    // }
    // }
    // selectSb.append(" FROM ").append(updateStt.getTableName()).append(" WHERE ").append(whereExpr);
    // System.out.println(selectSb.toString());
    // praseResult.querySql = selectSb.toString();
    // praseResult.where = whereExpr.toString();
    // } else {
    // // TODO
    // return null;
    // }
    //
    // return praseResult;
    // }

    /**
     * 计算预编译SQL的？索引与参数的对应关系 key=索引值，value=参数
     * 
     * @param mpStt
     * @param parameterObject
     * @return
     */
    private Map<Integer, String> calculateParamIndex2Value(MappedStatement mpStt, Object parameterObject) {
        SqlSource sqlSorce = mpStt.getSqlSource();
        BoundSql boundSql = sqlSorce.getBoundSql(parameterObject);
        List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
        Configuration configuration = mpStt.getConfiguration();
        TypeHandlerRegistry typeHandlerRegistry = configuration.getTypeHandlerRegistry();
        Map<Integer, String> name2Val = new HashMap<Integer, String>();
        for (int idx = 0; idx < parameterMappings.size(); idx++) {
            ParameterMapping parameterMapping = parameterMappings.get(idx);
            if (parameterMapping.getMode() != ParameterMode.OUT) {
                Object value;
                String propertyName = parameterMapping.getProperty();
                if (boundSql.hasAdditionalParameter(propertyName)) {
                    value = boundSql.getAdditionalParameter(propertyName);
                } else if (parameterObject == null) {
                    value = null;
                } else if (typeHandlerRegistry.hasTypeHandler(parameterObject.getClass())) {
                    value = parameterObject;
                } else {
                    MetaObject metaObject = configuration.newMetaObject(parameterObject);
                    value = metaObject.getValue(propertyName);
                }

                JdbcType jdbcType = parameterMapping.getJdbcType();
                if (value == null && jdbcType == null) {
                    jdbcType = configuration.getJdbcTypeForNull();
                }

                // TypeHandler typeHandler =
                // resolveTypeHandler(typeHandlerRegistry, value, jdbcType);
                String parsedValue = getSqlFormartedValue(value);

                name2Val.put(idx, parsedValue);
            }
        }

        return name2Val;
    }

    /*
     * @see UnknownTypeHandler.resolveTypeHandler(Object parameter, JdbcType
     * jdbcType)
     */
    // private TypeHandler<? extends Object>
    // resolveTypeHandler(TypeHandlerRegistry typeHandlerRegistry, Object
    // parameter,
    // JdbcType jdbcType) {
    // TypeHandler<? extends Object> handler;
    // if (parameter == null) {
    // handler = OBJECT_TYPE_HANDLER;
    // } else {
    // handler = typeHandlerRegistry.getTypeHandler(parameter.getClass(),
    // jdbcType);
    // if (handler == null || handler instanceof UnknownTypeHandler) {
    // handler = OBJECT_TYPE_HANDLER;
    // }
    // }
    // return handler;
    // }

    /**
     * 将预编译SQL里面的?替换成实际的参数
     * 
     * @param sqlWithQuestionmark
     * @param cloume2Value
     */
    private String replaceByParameter(String sqlWithQuestionmark, final Map<Integer, String> cloume2Value) {
        Set<Entry<Integer, String>> entrySet = cloume2Value.entrySet();
        for (Entry<Integer, String> entry : entrySet) {
            sqlWithQuestionmark = sqlWithQuestionmark.replaceFirst("\\?", entry.getValue());
        }
        System.out.println("sqlWithQuestionmark:" + sqlWithQuestionmark);
        return sqlWithQuestionmark;
    }

    /**
     * 根据标准SQL的格式，格式化入参
     * 
     * @param typeHandler
     * @param value
     * @return
     */
    private String getSqlFormartedValue(Object value) {
        // TODO 需要考虑jdbcType,比例这种场景：javaType是Long,但是数据库的列类型定义 为nvarchar,这种场景，mybatis会做隐式转换?
        if (value == null) {
            return null;
        }

        if (value instanceof Number) {
            return String.valueOf(value);
        }

        if (value instanceof Boolean) {
            return String.valueOf(value);
        }

        if (value instanceof String) {
            return "'" + String.valueOf(value) + "'";
        }

        if (value instanceof java.util.Date) {
            return "'" + Util.parseTime((Date) value) + "'";
        }

        // TODO Blob等形式暂时不支持
        return String.valueOf(value);
    }

    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    public void setProperties(Properties arg0) {
        String dbType = arg0.getProperty("dbType");
        if (dbType == null) {
            dbType = JdbcConstants.MYSQL;
        }
        
        sqlParser = new SqlParser( dbType);
    }
}

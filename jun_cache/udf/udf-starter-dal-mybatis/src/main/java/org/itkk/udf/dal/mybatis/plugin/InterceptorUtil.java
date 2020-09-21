package org.itkk.udf.dal.mybatis.plugin;

import org.apache.ibatis.mapping.*;
import org.itkk.udf.dal.mybatis.plugin.pagequery.BoundSqlSqlSource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 描述 : InterceptorUtil
 *
 * @author wangkang
 */
public class InterceptorUtil {

    /**
     * EMPTY_RESULTMAPPING
     */
    private static final List<ResultMapping> EMPTY_RESULTMAPPING = new ArrayList<>(0);

    /**
     * 描述 : 构造函数
     */
    private InterceptorUtil() {

    }

    /**
     * 新建count查询和分页查询的MappedStatement
     *
     * @param ms      ms
     * @param newMsId newMsId
     * @return MappedStatement
     */
    public static MappedStatement newCountMappedStatement(MappedStatement ms, String newMsId) {
        MappedStatement.Builder builder = new MappedStatement.Builder(ms.getConfiguration(), newMsId, ms.getSqlSource(), ms.getSqlCommandType());
        builder.resource(ms.getResource());
        builder.fetchSize(ms.getFetchSize());
        builder.statementType(ms.getStatementType());
        builder.keyGenerator(ms.getKeyGenerator());
        if (ms.getKeyProperties() != null && ms.getKeyProperties().length != 0) {
            StringBuilder keyProperties = new StringBuilder();
            for (String keyProperty : ms.getKeyProperties()) {
                keyProperties.append(keyProperty).append(",");
            }
            keyProperties.delete(keyProperties.length() - 1, keyProperties.length());
            builder.keyProperty(keyProperties.toString());
        }
        builder.timeout(ms.getTimeout());
        builder.parameterMap(ms.getParameterMap());
        //count查询返回值int
        List<ResultMap> resultMaps = new ArrayList<>();
        ResultMap resultMap = new ResultMap.Builder(ms.getConfiguration(), ms.getId(), Long.class, EMPTY_RESULTMAPPING).build();
        resultMaps.add(resultMap);
        builder.resultMaps(resultMaps);
        builder.resultSetType(ms.getResultSetType());
        builder.cache(ms.getCache());
        builder.flushCacheRequired(ms.isFlushCacheRequired());
        builder.useCache(ms.isUseCache());

        return builder.build();
    }

    /**
     * 复制MappedStatement
     *
     * @param mappedStatement   mappedStatement
     * @param boundSql          boundSql
     * @param sql               sql
     * @param parameterMappings parameterMappings
     * @param parameter         parameter
     * @return 结果
     */
    public static MappedStatement copyFromNewSql(MappedStatement mappedStatement, BoundSql boundSql, String sql,
                                                 List<ParameterMapping> parameterMappings, Object parameter) {
        BoundSql newBoundSql = copyFromBoundSql(mappedStatement, boundSql, sql, parameterMappings, parameter);
        return copyFromMappedStatement(mappedStatement, new BoundSqlSqlSource(newBoundSql));
    }

    /**
     * 复制MappedStatement
     *
     * @param ms           MappedStatement
     * @param newSqlSource newSqlSource
     * @return 结果
     */
    private static MappedStatement copyFromMappedStatement(MappedStatement ms, SqlSource newSqlSource) {
        MappedStatement.Builder builder = new MappedStatement.Builder(ms.getConfiguration(), ms.getId(), newSqlSource, ms.getSqlCommandType());
        builder.resource(ms.getResource());
        builder.fetchSize(ms.getFetchSize());
        builder.statementType(ms.getStatementType());
        builder.keyGenerator(ms.getKeyGenerator());
        String[] keys = ms.getKeyProperties();
        if (keys != null) {
            String keysstr = Arrays.toString(keys);
            keysstr = keysstr.replace("[", "");
            keysstr = keysstr.replace("]", "");
            builder.keyProperty(keysstr);
        }
        builder.timeout(ms.getTimeout());
        builder.parameterMap(ms.getParameterMap());
        builder.resultMaps(ms.getResultMaps());
        builder.cache(ms.getCache());
        builder.databaseId(ms.getDatabaseId());
        builder.flushCacheRequired(ms.isFlushCacheRequired());
        String[] keyColumns = ms.getKeyColumns();
        if (keyColumns != null) {
            String keysstr = Arrays.toString(keyColumns);
            keysstr = keysstr.replace("[", "");
            keysstr = keysstr.replace("]", "");
            builder.keyColumn(keysstr);
        }
        builder.lang(ms.getLang());
        String[] resulSets = ms.getResultSets();
        if (resulSets != null) {
            String keysstr = Arrays.toString(resulSets);
            keysstr = keysstr.replace("[", "");
            keysstr = keysstr.replace("]", "");
            builder.resultSets(keysstr);
        }
        builder.resultSetType(ms.getResultSetType());
        builder.statementType(ms.getStatementType());
        builder.useCache(ms.isUseCache());
        return builder.build();
    }

    /**
     * 复制MappedStatement
     *
     * @param mappedStatement   mappedStatement
     * @param boundSql          boundSql
     * @param sql               sql
     * @param parameterMappings parameterMappings
     * @param parameter         parameter
     * @return 结果
     */
    private static BoundSql copyFromBoundSql(MappedStatement mappedStatement, BoundSql boundSql, String sql,
                                             List<ParameterMapping> parameterMappings, Object parameter) {
        BoundSql newBoundSql = new BoundSql(mappedStatement.getConfiguration(), sql, parameterMappings, parameter);
        for (ParameterMapping mapping : boundSql.getParameterMappings()) {
            String prop = mapping.getProperty();
            if (boundSql.hasAdditionalParameter(prop)) {
                newBoundSql.setAdditionalParameter(prop, boundSql.getAdditionalParameter(prop));
            }
        }
        return newBoundSql;
    }

}

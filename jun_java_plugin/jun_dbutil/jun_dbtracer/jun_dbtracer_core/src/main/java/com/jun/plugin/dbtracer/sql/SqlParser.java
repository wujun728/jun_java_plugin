package com.jun.plugin.dbtracer.sql;

import java.util.List;
import java.util.Map;

import com.alibaba.druid.sql.SQLUtils;
import com.alibaba.druid.sql.ast.SQLExpr;
import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.ast.expr.SQLBinaryOpExpr;
import com.alibaba.druid.sql.ast.expr.SQLBinaryOperator;
import com.alibaba.druid.sql.ast.expr.SQLNumericLiteralExpr;
import com.alibaba.druid.sql.ast.expr.SQLTextLiteralExpr;
import com.alibaba.druid.sql.ast.statement.SQLUpdateSetItem;
import com.alibaba.druid.sql.ast.statement.SQLUpdateStatement;
import com.jun.plugin.dbtracer.Bootstrap;
import com.jun.plugin.dbtracer.sql.NotSupportedSQLException.ExceptionType;
import com.jun.plugin.dbtracer.xml.TableConfiguration;

/**
 * SQL解析模块
 * 
 * @author: guhai
 */
public class SqlParser {

    private String dbType;

    public SqlParser(String dbType) {
        this.dbType = dbType;
    }

    public SQLParseResult parseUpdateSQL(String updateSql) throws NotSupportedSQLException {

        Map<String, TableConfiguration> config = Bootstrap.getTableConfiguration();
        List<SQLStatement> statementList = SQLUtils.parseStatements(updateSql, dbType);
        if (statementList.size() > 1) {
            throw new NotSupportedSQLException(ExceptionType.MULTIPLE_UPDATE_SQL, updateSql);
        }

        SQLParseResult praseResult = new SQLParseResult();
        StringBuffer selectSb = new StringBuffer();
        SQLStatement statement = statementList.get(0);
        int questionMarkIdx = 0;
        if (statement instanceof SQLUpdateStatement) {
            SQLUpdateStatement updateStt = (SQLUpdateStatement) statement;

            praseResult.setTable(updateStt.getTableName().toString());
            SQLExpr whereExpr = updateStt.getWhere();
            System.out.println(whereExpr.toString());
            List<SQLUpdateSetItem> updateItems = updateStt.getItems();
            for (SQLUpdateSetItem updateItem : updateItems) {
                String column = updateItem.getColumn().toString();
                praseResult.getUpdateColume().add(column);
                praseResult.getUpdateColIdx2Name().put(questionMarkIdx, column);
                // praseResult.updateColIdx2Name.put(questionMarkIdx, updateItem.getColumn().toString());
                questionMarkIdx++;
                System.out.println("Column:" + updateItem.getColumn() + ",value:" + updateItem.getValue());
            }

            selectSb.append("SELECT ");
            List<String> updateColumeNames = praseResult.getUpdateColume();
            for (int idx = 0; idx < updateColumeNames.size(); idx++) {
                selectSb.append(updateColumeNames.get(idx));
                if (idx < updateColumeNames.size() - 1) {
                    selectSb.append(" , ");
                }
            }
            selectSb.append(" FROM ").append(updateStt.getTableName()).append(" WHERE ").append(whereExpr);
            System.out.println(selectSb.toString());

            praseResult.setQuerySql(selectSb.toString());
            // praseResult.querySql = selectSb.toString();
            // praseResult.where = whereExpr.toString();
            praseResult.setWhere(whereExpr.toString());

            String tblName = praseResult.getTable();
            String keyName = config.get(tblName).getKey();
            Object key = getKeyValue((SQLBinaryOpExpr) whereExpr, keyName);

            if (key == null) {
                throw new NotSupportedSQLException(ExceptionType.UPDATE_CONDITION_PK_NOT_EXIST, whereExpr.toString());
            }
            praseResult.setKey(String.valueOf(key));
        } else {
            // TODO 支持insert
            return null;
        }

        return praseResult;
    }

    /**
     * 限制：1.目前键值类型只解析数据型和字符型<br>
     * 2.where条件只解析以下两种情况<br>
     * A. where id = 1 <br>
     * B.where id = 1 and version = 10 and ...<br>
     * 
     * @Description: TODO
     * @param whereExpr
     * @param keyName
     * @return
     */
    private static Object getKeyValue(SQLBinaryOpExpr whereExpr, String keyName) throws NotSupportedSQLException {
        if (whereExpr == null) {
            return null;
        }
        if (keyName == null) {
            return null;
        }

        if (SQLBinaryOperator.Equality.equals(whereExpr.getOperator())) {
            // "update user set name = 'xxxx' where id = 1 ";
            if (keyName.equals(whereExpr.getLeft().toString())) {
                SQLExpr rightExpr = whereExpr.getRight();
                if (rightExpr instanceof SQLTextLiteralExpr) {
                    return ((SQLTextLiteralExpr) rightExpr).getText();
                }
                if (rightExpr instanceof SQLNumericLiteralExpr) {
                    return ((SQLNumericLiteralExpr) rightExpr).getNumber();
                }

                throw new NotSupportedSQLException(ExceptionType.PK_NUMBER_OR_STRING_ONLY, whereExpr.toString());
            }
        }

        else if (SQLBinaryOperator.BooleanAnd.equals(whereExpr.getOperator())) {
            // ="update user set name = 'xxxx' where  id = 1  and version=10 and xx=11";
            Object keyValue = getKeyValue((SQLBinaryOpExpr) whereExpr.getLeft(), keyName);
            if (keyValue != null) {
                return keyValue;
            }
            keyValue = getKeyValue((SQLBinaryOpExpr) whereExpr.getRight(), keyName);
            if (keyValue != null) {
                return keyValue;
            }
        } else {
            throw new NotSupportedSQLException(ExceptionType.UPDATE_CONDITION_AND_OR_EQUAL_ONLY, whereExpr.toString());
        }

        return null;
    }
}

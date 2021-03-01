package com.jun.plugin.dbtracer;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.druid.sql.SQLUtils;
import com.alibaba.druid.sql.ast.SQLExpr;
import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.ast.expr.SQLBinaryOpExpr;
import com.alibaba.druid.sql.ast.expr.SQLBinaryOperator;
import com.alibaba.druid.sql.ast.expr.SQLNumericLiteralExpr;
import com.alibaba.druid.sql.ast.expr.SQLTextLiteralExpr;
import com.alibaba.druid.sql.ast.statement.SQLUpdateSetItem;
import com.alibaba.druid.sql.ast.statement.SQLUpdateStatement;
import com.alibaba.druid.util.JdbcConstants;

/**
 * Hello world!
 *
 */
public class App {
    public static void main(String[] args) {

        String key = "id";
        /**
         * update user set name = 'xxxx' where id = ?-->
         * select name from user where id = 100
         */
        String sql = "update user set name = 'xxxx' where version=10 and id = 1  and xx=11";
        List<SQLStatement> statementList = SQLUtils.parseStatements(sql, JdbcConstants.MYSQL);
        if (statementList.size() > 1) {
            System.out.print("不支持同时更新多条记录");
        }
        SQLStatement statement = statementList.get(0);
        if (statement instanceof SQLUpdateStatement) {
            SQLUpdateStatement updateStt = (SQLUpdateStatement) statement;

            System.out.println(updateStt.getTableName());
            SQLExpr whereExpr = updateStt.getWhere();
            /**
             * 解析出key
             */
            System.out.println("key:" + getKeyValue((SQLBinaryOpExpr) whereExpr, key).toString() + "=================");
            System.out.println(whereExpr.toString());

            List<SQLUpdateSetItem> updateItems = updateStt.getItems();
            List<String> updateColumeNames = new ArrayList<String>();
            for (SQLUpdateSetItem updateItem : updateItems) {
                updateColumeNames.add(updateItem.getColumn().toString());
                System.out.println("Column:" + updateItem.getColumn() + ",value:" + updateItem.getValue());
            }

            StringBuffer selectSb = new StringBuffer();
            selectSb.append("SELECT ");
            for (int idx = 0; idx < updateColumeNames.size(); idx++) {
                selectSb.append(updateColumeNames.get(idx));
                if (idx < updateColumeNames.size() - 1) {
                    selectSb.append(" , ");
                }
            }
            selectSb.append(" FROM ").append(updateStt.getTableName()).append(" WHERE ").append(whereExpr);
            System.out.println(selectSb.toString());
        } else {

        }

        System.out.println("Hello World!");
    }

    /**
     * 限制：1.目前键值类型只解析数据型和字符型<br>
     *       2.where条件只解析以下两种情况<br>
     *         A. where id = 1   <br>
     *         B.where id = 1  and version = 10  and ...<br>
     * @Description: TODO 
     * @param whereExpr
     * @param keyName
     * @return
     */
    private static Object getKeyValue(SQLBinaryOpExpr whereExpr, String keyName) {
        if (whereExpr == null) {
            return null;
        }
        if(keyName==null){
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

                throw new RuntimeException("Unsupported key type:" + rightExpr.getClass());
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
        }

        return null;
    }
}

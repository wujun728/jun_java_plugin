package org.itkk.udf.dal.mybatis.plugin.pagequery.parser;

import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.expression.Alias;
import net.sf.jsqlparser.expression.Function;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.select.*;
import org.itkk.udf.core.exception.SystemRuntimeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * 分页查询,去除order by处理,参考开源项目Mybatis_PageHelper
 *
 * @author wangkang
 */
public class SqlParser {

    /**
     * KEEP_ORDERBY
     */
    private static final String KEEP_ORDERBY = "/*keep orderby*/";

    /**
     * <p>
     * Field TABLE_ALIAS: TABLE_ALIAS
     * </p>
     */
    private static final Alias TABLE_ALIAS;

    /**
     * <p>
     * Field log: 日志
     * </p>
     */
    private Logger log = LoggerFactory.getLogger(this.getClass());

    static {
        TABLE_ALIAS = new Alias("table_count");
        TABLE_ALIAS.setUseAs(false);
    }

    /**
     * 获取智能的countSql
     *
     * @param sql  sql
     * @param name 字段名称
     * @return 优化后的sql
     */
    public String getSmartCountSql(String sql, String name) {
        // 校验是否支持该sql
        isSupportedSql(sql);
        // 解析SQL
        Statement stmt;
        //特殊sql不需要去掉order by时，使用注释前缀
        if (sql.indexOf(KEEP_ORDERBY) >= 0) {
            return getSimpleCountSql(sql);
        }
        //处理
        try {
            //解析
            stmt = CCJSqlParserUtil.parse(sql);
            Select select = (Select) stmt;
            SelectBody selectBody = select.getSelectBody();
            // 处理body-去order by
            processSelectBody(selectBody);
            // 处理with-去order by
            processWithItemsList(select.getWithItemsList());
            // 处理为count查询
            sqlToCount(select, name);
            // 返回
            return select.toString();
        } catch (JSQLParserException e) {
            // 无法解析的用一般方法返回count语句
            this.log.warn("无法使用smartCountSql,改用普通simpleCountSql执行:", e);
            return getSimpleCountSql(sql);
        }
    }

    /**
     * 获取普通的Count-sql
     *
     * @param sql 原查询sql
     * @return 返回count查询sql
     */
    private String getSimpleCountSql(String sql) {
        isSupportedSql(sql);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("select count(*) from (");
        stringBuilder.append(sql);
        stringBuilder.append(") tmp_count");
        return stringBuilder.toString();
    }

    /**
     * 将sql转换为count查询
     *
     * @param select 查询对象
     * @param name   字段名称
     */
    private void sqlToCount(Select select, String name) {
        SelectBody selectBody = select.getSelectBody();
        // 是否能简化count查询
        List<SelectItem> countItem = new ArrayList<>();
        countItem.add(new SelectExpressionItem(new Column("count(" + name + ")")));
        if (selectBody instanceof PlainSelect && isSimpleCount((PlainSelect) selectBody)) {
            ((PlainSelect) selectBody).setSelectItems(countItem);
        } else {
            PlainSelect plainSelect = new PlainSelect();
            SubSelect subSelect = new SubSelect();
            subSelect.setSelectBody(selectBody);
            subSelect.setAlias(TABLE_ALIAS);
            plainSelect.setFromItem(subSelect);
            plainSelect.setSelectItems(countItem);
            select.setSelectBody(plainSelect);
        }
    }

    /**
     * 是否可以用简单的count查询方式
     *
     * @param select 查询对象
     * @return 结果
     */
    private boolean isSimpleCount(PlainSelect select) { //NOSONAR
        // 包含group by的时候不可以
        if (select.getGroupByColumnReferences() != null) {
            return false;
        }
        // 包含distinct的时候不可以
        if (select.getDistinct() != null) {
            return false;
        }
        for (SelectItem item : select.getSelectItems()) {
            // select列中包含参数的时候不可以，否则会引起参数个数错误
            if (item.toString().contains("?")) {
                return false;
            }
            // 如果查询列中包含函数，也不可以，函数可能会聚合列
            if (item instanceof SelectExpressionItem && checkSelectItem(item)) {
                return false;
            }
        }
        return true;
    }

    /**
     * <p>
     * Description: 检查查询项
     * </p>
     *
     * @param item 查询项
     * @return 结果
     */
    private static boolean checkSelectItem(SelectItem item) {
        return ((SelectExpressionItem) item).getExpression() instanceof Function;
    }

    /**
     * 处理WithItem
     *
     * @param withItemsList withItemsList
     */
    private void processWithItemsList(List<WithItem> withItemsList) {
        if (withItemsList != null && !withItemsList.isEmpty()) {
            for (WithItem item : withItemsList) {
                processSelectBody(item.getSelectBody());
            }
        }
    }

    /**
     * 处理selectBody去除Order by
     *
     * @param selectBody 查询主体
     */
    private void processSelectBody(SelectBody selectBody) {
        if (selectBody instanceof PlainSelect) {
            processPlainSelect((PlainSelect) selectBody);
        } else if (selectBody instanceof WithItem) {
            WithItem withItem = (WithItem) selectBody;
            if (withItem.getSelectBody() != null) {
                processSelectBody(withItem.getSelectBody());
            }
        } else {
            SetOperationList operationList = (SetOperationList) selectBody;
            if (operationList.getSelects() != null && !operationList.getSelects().isEmpty()) {
                List<SelectBody> plainSelects = operationList.getSelects();
                for (SelectBody plainSelect : plainSelects) {
                    processSelectBody(plainSelect);
                }
            }
            if (!orderByHashParameters(operationList.getOrderByElements())) {
                operationList.setOrderByElements(null);
            }
        }
    }

    /**
     * 处理PlainSelect类型的selectBody
     *
     * @param plainSelect plainSelect
     */
    private void processPlainSelect(PlainSelect plainSelect) {
        if (!orderByHashParameters(plainSelect.getOrderByElements())) {
            plainSelect.setOrderByElements(null);
        }
        if (plainSelect.getFromItem() != null) {
            processFromItem(plainSelect.getFromItem());
        }
        if (plainSelect.getJoins() != null && !plainSelect.getJoins().isEmpty()) {
            List<Join> joins = plainSelect.getJoins();
            for (Join join : joins) {
                if (join.getRightItem() != null) {
                    processFromItem(join.getRightItem());
                }
            }
        }
    }

    /**
     * 处理子查询
     *
     * @param fromItem 子查询
     */
    private void processFromItem(FromItem fromItem) { //NOSONAR
        if (fromItem instanceof SubJoin) {
            SubJoin subJoin = (SubJoin) fromItem;
            if (subJoin.getJoin() != null && subJoin.getJoin().getRightItem() != null) {
                processFromItem(subJoin.getJoin().getRightItem());
            }
            if (subJoin.getLeft() != null) {
                processFromItem(subJoin.getLeft());
            }
        } else if (fromItem instanceof SubSelect) {
            SubSelect subSelect = (SubSelect) fromItem;
            if (subSelect.getSelectBody() != null) {
                processSelectBody(subSelect.getSelectBody());
            }
        } else if (fromItem instanceof ValuesList) {
            // 不用做处理
        } else if (fromItem instanceof LateralSubSelect) {
            LateralSubSelect lateralSubSelect = (LateralSubSelect) fromItem;
            if (lateralSubSelect.getSubSelect() != null) {
                SubSelect subSelect = lateralSubSelect.getSubSelect();
                if (subSelect.getSelectBody() != null) {
                    processSelectBody(subSelect.getSelectBody());
                }
            }
        }
        // Table时不用处理
    }

    /**
     * 判断Orderby是否包含参数，有参数的不能去
     *
     * @param orderByElements orderby 项目
     * @return 结果
     */
    private boolean orderByHashParameters(List<OrderByElement> orderByElements) {
        if (orderByElements == null) {
            return false;
        }
        for (OrderByElement orderByElement : orderByElements) {
            if (orderByElement.toString().contains("?")) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断是否支持分页插件
     *
     * @param sql 语句
     */
    private void isSupportedSql(String sql) {
        if (sql.trim().toUpperCase().endsWith("FOR UPDATE")) {
            throw new SystemRuntimeException("分页插件不支持包含for update的sql");
        }
    }
}

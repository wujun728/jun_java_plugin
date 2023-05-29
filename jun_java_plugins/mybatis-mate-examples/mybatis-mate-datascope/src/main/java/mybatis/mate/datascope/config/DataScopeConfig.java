package mybatis.mate.datascope.config;

import mybatis.mate.datascope.AbstractDataScopeProvider;
import mybatis.mate.datascope.DataColumnProperty;
import mybatis.mate.datascope.DataScopeProperty;
import mybatis.mate.datascope.IDataScopeProvider;
import net.sf.jsqlparser.expression.Parenthesis;
import net.sf.jsqlparser.expression.StringValue;
import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
import net.sf.jsqlparser.expression.operators.relational.*;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.statement.delete.Delete;
import net.sf.jsqlparser.statement.select.PlainSelect;
import org.apache.ibatis.mapping.MappedStatement;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;

@Configuration
public class DataScopeConfig {
    public final static String TEST = "test";
    public final static String TEST_CLASS = "testClass";

    @Bean
    public IDataScopeProvider dataScopeProvider() {
        return new AbstractDataScopeProvider() {

            /**
             * 这里是 Select 查询 Where 条件
             */
            @Override
            public void setWhere(PlainSelect plainSelect, Object[] args, DataScopeProperty dataScopeProperty) {
                // args 中包含 mapper 方法的请求参数，需要使用可以自行获取
                /*
                    // 测试数据权限，最终执行 SQL 语句
                    SELECT u.* FROM user u WHERE (u.department_id IN ('1', '2', '3', '5'))
                    AND u.mobile LIKE '%1533%'
                 */
                if (TEST.equals(dataScopeProperty.getType())) {
                    // 业务 test 类型
                    List<DataColumnProperty> dataColumns = dataScopeProperty.getColumns();
                    for (DataColumnProperty dataColumn : dataColumns) {
                        if ("department_id".equals(dataColumn.getName())) {
                            // 追加部门字段 IN 条件，也可以是 SQL 语句
                            ItemsList itemsList = new ExpressionList(Arrays.asList(
                                    new StringValue("1"),
                                    new StringValue("2"),
                                    new StringValue("3"),
                                    new StringValue("5")
                            ));
                            InExpression inExpression = new InExpression(new Column(dataColumn.getAliasDotName()), itemsList);
                            if (null == plainSelect.getWhere()) {
                                // 不存在 where 条件
                                plainSelect.setWhere(new Parenthesis(inExpression));
                            } else {
                                // 存在 where 条件 and 处理
                                plainSelect.setWhere(new AndExpression(plainSelect.getWhere(), inExpression));
                            }
                        } else if ("mobile".equals(dataColumn.getName())) {
                            // 支持一个自定义条件
                            LikeExpression likeExpression = new LikeExpression();
                            likeExpression.setLeftExpression(new Column(dataColumn.getAliasDotName()));
                            likeExpression.setRightExpression(new StringValue("%1533%"));
                            plainSelect.setWhere(new AndExpression(plainSelect.getWhere(), likeExpression));
                        }
                    }

                    // SQL 解析写法
//                    try {
//                        Expression selectExpression = CCJSqlParserUtil.parseCondExpression("username='333'", true);
//                        plainSelect.setWhere(new AndExpression(plainSelect.getWhere(), selectExpression));
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
                }

                else if (TEST_CLASS.equals(dataScopeProperty.getType())) {
                    System.err.println("----------------使用类注解权限----------------");
                }
            }

            @Override
            public void processInsert(Object[] args, MappedStatement mappedStatement, DataScopeProperty dataScopeProperty) {
                System.err.println("------------------  执行【插入】你可以干点什么");
            }

            @Override
            public void processDelete(Object[] args, MappedStatement mappedStatement, DataScopeProperty dataScopeProperty) {
                /**
                 * 这是删除自定义处理逻辑，插入更新需要限制条件可以参考这里
                 */
                if (TEST_CLASS.equals(dataScopeProperty.getType())) {
                    processStatements(args, mappedStatement, (statement, index) -> {
                        Delete delete = (Delete) statement;
                        List<DataColumnProperty> dataColumns = dataScopeProperty.getColumns();
                        for (DataColumnProperty dataColumn : dataColumns) {
                            if ("department_id".equals(dataColumn.getName())) {
                                EqualsTo equalsTo = new EqualsTo();
                                equalsTo.setLeftExpression(new Column(dataColumn.getAliasDotName()));
                                equalsTo.setRightExpression(new StringValue("1"));
                                delete.setWhere(new AndExpression(delete.getWhere(), equalsTo));
                            }
                        }
                    });
                }
            }

            @Override
            public void processUpdate(Object[] args, MappedStatement mappedStatement, DataScopeProperty dataScopeProperty) {
                System.err.println("------------------  执行【更新】 你可以干点什么");
            }
        };
    }
}

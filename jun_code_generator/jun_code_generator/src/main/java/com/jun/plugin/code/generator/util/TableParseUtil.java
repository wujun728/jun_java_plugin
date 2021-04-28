package com.jun.plugin.code.generator.util;


import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.alibaba.druid.DbType;
import com.alibaba.druid.sql.SQLUtils;
import com.alibaba.druid.sql.ast.SQLExpr;
import com.alibaba.druid.sql.ast.SQLLimit;
import com.alibaba.druid.sql.ast.SQLOrderBy;
import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.ast.expr.SQLBinaryOpExpr;
import com.alibaba.druid.sql.ast.expr.SQLBinaryOperator;
import com.alibaba.druid.sql.ast.expr.SQLInSubQueryExpr;
import com.alibaba.druid.sql.ast.statement.SQLColumnDefinition;
import com.alibaba.druid.sql.ast.statement.SQLExprTableSource;
import com.alibaba.druid.sql.ast.statement.SQLJoinTableSource;
import com.alibaba.druid.sql.ast.statement.SQLSelectGroupByClause;
import com.alibaba.druid.sql.ast.statement.SQLSelectItem;
import com.alibaba.druid.sql.ast.statement.SQLSelectQuery;
import com.alibaba.druid.sql.ast.statement.SQLSelectQueryBlock;
import com.alibaba.druid.sql.ast.statement.SQLSelectStatement;
import com.alibaba.druid.sql.ast.statement.SQLSubqueryTableSource;
import com.alibaba.druid.sql.ast.statement.SQLTableSource;
import com.alibaba.druid.sql.ast.statement.SQLUnionQuery;
import com.alibaba.druid.sql.dialect.mysql.ast.statement.MySqlCreateTableStatement;
import com.alibaba.druid.sql.dialect.mysql.ast.statement.MySqlDeleteStatement;
import com.alibaba.druid.sql.dialect.mysql.ast.statement.MySqlInsertStatement;
import com.alibaba.druid.sql.dialect.mysql.ast.statement.MySqlUpdateStatement;
import com.alibaba.druid.util.JdbcConstants;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jun.plugin.code.generator.model.ClassInfo;
import com.jun.plugin.code.generator.model.FieldInfo;
import com.jun.plugin.code.generator.model.ParamInfo;
import com.jun.plugin.code.meta.util.Column;
import com.jun.plugin.code.meta.util.Table;

/**
 * @author xuxueli 2018-05-02 21:10:45
 */
public class TableParseUtil {

    /**
     * 解析建表SQL生成代码（model-dao-xml）
     *
     * @param tableSql
     * @return
     */
    public static ClassInfo processTableIntoClassInfo(String tableSql) throws IOException {
        if (tableSql==null || tableSql.trim().length()==0) {
            throw new CodeGenerateException("Table structure can not be empty.");
        }
        tableSql = tableSql.trim();

        // table Name
        String tableName = null;
        if (tableSql.contains("TABLE") && tableSql.contains("(")) {
            tableName = tableSql.substring(tableSql.indexOf("TABLE")+5, tableSql.indexOf("("));
        } else if (tableSql.contains("table") && tableSql.contains("(")) {
            tableName = tableSql.substring(tableSql.indexOf("table")+5, tableSql.indexOf("("));
        } else {
            throw new CodeGenerateException("Table structure anomaly.");
        }

        if (tableName.contains("`")) {
            tableName = tableName.substring(tableName.indexOf("`")+1, tableName.lastIndexOf("`"));
        }

        // class Name
        String className = StringUtils.upperCaseFirst(StringUtils.underlineToCamelCase(tableName));
        if (className.contains("_")) {
            className = className.replaceAll("_", "");
        }

        // class Comment
        String classComment = "";
        if (tableSql.contains("COMMENT=")) {
            String classCommentTmp = tableSql.substring(tableSql.lastIndexOf("COMMENT=")+8).trim();
            if (classCommentTmp.contains("'") || classCommentTmp.indexOf("'")!=classCommentTmp.lastIndexOf("'")) {
                classCommentTmp = classCommentTmp.substring(classCommentTmp.indexOf("'")+1, classCommentTmp.lastIndexOf("'"));
            }
            if (classCommentTmp!=null && classCommentTmp.trim().length()>0) {
                classComment = classCommentTmp;
            }
        }

        // field List
        List<FieldInfo> fieldList = new ArrayList<FieldInfo>();

        String fieldListTmp = tableSql.substring(tableSql.indexOf("(")+1, tableSql.lastIndexOf(")"));

        // replave "," by "，" in comment
        Matcher matcher = Pattern.compile("\\ COMMENT '(.*?)\\'").matcher(fieldListTmp);     // "\\{(.*?)\\}"
        while(matcher.find()){

            String commentTmp = matcher.group();
            commentTmp = commentTmp.replaceAll("\\ COMMENT '|\\'", "");      // "\\{|\\}"

            if (commentTmp.contains(",")) {
                String commentTmpFinal = commentTmp.replaceAll(",", "，");
                fieldListTmp = fieldListTmp.replace(commentTmp, commentTmpFinal);
            }
        }

        // remove invalid data
        for (Pattern pattern: Arrays.asList(
                Pattern.compile("[\\s]*PRIMARY KEY .*(\\),|\\))"),      // remove PRIMARY KEY
                Pattern.compile("[\\s]*UNIQUE KEY .*(\\),|\\))"),       // remove UNIQUE KEY
                Pattern.compile("[\\s]*KEY .*(\\),|\\))")               // remove KEY
        )) {
            Matcher patternMatcher = pattern.matcher(fieldListTmp);
            while(patternMatcher.find()){
                fieldListTmp = fieldListTmp.replace(patternMatcher.group(),"");
            }
        }

        String[] fieldLineList = fieldListTmp.split(",");
        if (fieldLineList.length > 0) {
            for (String columnLine :fieldLineList) {
                columnLine = columnLine.trim();		                                        // `userid` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户ID',
                if (columnLine.startsWith("`")){

                    // column Name
                    columnLine = columnLine.substring(1);			                        // userid` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户ID',
                    String columnName = columnLine.substring(0, columnLine.indexOf("`"));	// userid

                    // field Name
                    String fieldName = StringUtils.lowerCaseFirst(StringUtils.underlineToCamelCase(columnName));
                    if (fieldName.contains("_")) {
                        fieldName = fieldName.replaceAll("_", "");
                    }

                    // field class
                    columnLine = columnLine.substring(columnLine.indexOf("`")+1).trim();	// int(11) NOT NULL AUTO_INCREMENT COMMENT '用户ID',
                    String fieldClass = Object.class.getSimpleName();
                    if (columnLine.startsWith("int") || columnLine.startsWith("tinyint") || columnLine.startsWith("smallint")) {
                        fieldClass = Integer.TYPE.getSimpleName();
                    } else if (columnLine.startsWith("bigint")) {
                        fieldClass = Long.TYPE.getSimpleName();
                    } else if (columnLine.startsWith("float")) {
                        fieldClass = Float.TYPE.getSimpleName();
                    } else if (columnLine.startsWith("double")) {
                        fieldClass = Double.TYPE.getSimpleName();
                    } else if (columnLine.startsWith("datetime") || columnLine.startsWith("timestamp")) {
                        fieldClass = Date.class.getSimpleName();
                    } else if (columnLine.startsWith("varchar") || columnLine.startsWith("text") || columnLine.startsWith("char")) {
                        fieldClass = String.class.getSimpleName();
                    } else if (columnLine.startsWith("decimal")) {
                        fieldClass = BigDecimal.class.getSimpleName();
                    }

                    // field comment
                    String fieldComment = "";
                    if (columnLine.contains("COMMENT")) {
                        String commentTmp = fieldComment = columnLine.substring(columnLine.indexOf("COMMENT")+7).trim();	// '用户ID',
                        if (commentTmp.contains("'") || commentTmp.indexOf("'")!=commentTmp.lastIndexOf("'")) {
                            commentTmp = commentTmp.substring(commentTmp.indexOf("'")+1, commentTmp.lastIndexOf("'"));
                        }
                        fieldComment = commentTmp;
                    }

                    FieldInfo fieldInfo = new FieldInfo();
                    fieldInfo.setColumnName(columnName);
                    fieldInfo.setFieldName(fieldName);
                    fieldInfo.setFieldClass(fieldClass);
                    fieldInfo.setFieldComment(fieldComment);

                    fieldList.add(fieldInfo);
                }
            }
        }

        if (fieldList.size() < 1) {
            throw new CodeGenerateException("Table structure anomaly.");
        }

        ClassInfo codeJavaInfo = new ClassInfo();
        codeJavaInfo.setTableName(tableName);
        codeJavaInfo.setClassName(className);
        codeJavaInfo.setClassComment(classComment);
        codeJavaInfo.setFieldList(fieldList);

        return codeJavaInfo;
    }
    
    public static Table processTableIntoTable(String tableSql) throws IOException {
        if (tableSql==null || tableSql.trim().length()==0) {
            throw new CodeGenerateException("Table structure can not be empty.");
        }
        tableSql = tableSql.trim();

        // table Name
        String tableName = null;
        if (tableSql.contains("TABLE") && tableSql.contains("(")) {
            tableName = tableSql.substring(tableSql.indexOf("TABLE")+5, tableSql.indexOf("("));
        } else if (tableSql.contains("table") && tableSql.contains("(")) {
            tableName = tableSql.substring(tableSql.indexOf("table")+5, tableSql.indexOf("("));
        } else {
            throw new CodeGenerateException("Table structure anomaly.");
        }

        if (tableName.contains("`")) {
            tableName = tableName.substring(tableName.indexOf("`")+1, tableName.lastIndexOf("`"));
        }

        // class Name
        String className = StringUtils.upperCaseFirst(StringUtils.underlineToCamelCase(tableName));
        if (className.contains("_")) {
            className = className.replaceAll("_", "");
        }

        // class Comment
        String classComment = "";
        if (tableSql.contains("COMMENT=")) {
            String classCommentTmp = tableSql.substring(tableSql.lastIndexOf("COMMENT=")+8).trim();
            if (classCommentTmp.contains("'") || classCommentTmp.indexOf("'")!=classCommentTmp.lastIndexOf("'")) {
                classCommentTmp = classCommentTmp.substring(classCommentTmp.indexOf("'")+1, classCommentTmp.lastIndexOf("'"));
            }
            if (classCommentTmp!=null && classCommentTmp.trim().length()>0) {
                classComment = classCommentTmp;
            }
        }

        // field List
        List<FieldInfo> fieldList = new ArrayList<FieldInfo>();
        List<Column> columnList = new ArrayList<Column>();

        String fieldListTmp = tableSql.substring(tableSql.indexOf("(")+1, tableSql.lastIndexOf(")"));

        // replave "," by "，" in comment
        Matcher matcher = Pattern.compile("\\ COMMENT '(.*?)\\'").matcher(fieldListTmp);     // "\\{(.*?)\\}"
        while(matcher.find()){

            String commentTmp = matcher.group();
            commentTmp = commentTmp.replaceAll("\\ COMMENT '|\\'", "");      // "\\{|\\}"

            if (commentTmp.contains(",")) {
                String commentTmpFinal = commentTmp.replaceAll(",", "，");
                fieldListTmp = fieldListTmp.replace(commentTmp, commentTmpFinal);
            }
        }

        // remove invalid data
        for (Pattern pattern: Arrays.asList(
                Pattern.compile("[\\s]*PRIMARY KEY .*(\\),|\\))"),      // remove PRIMARY KEY
                Pattern.compile("[\\s]*UNIQUE KEY .*(\\),|\\))"),       // remove UNIQUE KEY
                Pattern.compile("[\\s]*KEY .*(\\),|\\))")               // remove KEY
        )) {
            Matcher patternMatcher = pattern.matcher(fieldListTmp);
            while(patternMatcher.find()){
                fieldListTmp = fieldListTmp.replace(patternMatcher.group(),"");
            }
        }

        String[] fieldLineList = fieldListTmp.split(",");
        if (fieldLineList.length > 0) {
            for (String columnLine :fieldLineList) {
                columnLine = columnLine.trim();		                                        // `userid` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户ID',
                if (columnLine.startsWith("`")){

                    // column Name
                    columnLine = columnLine.substring(1);			                        // userid` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户ID',
                    String columnName = columnLine.substring(0, columnLine.indexOf("`"));	// userid

                    // field Name
                    String fieldName = StringUtils.lowerCaseFirst(StringUtils.underlineToCamelCase(columnName));
                    if (fieldName.contains("_")) {
                        fieldName = fieldName.replaceAll("_", "");
                    }

                    // field class
                    columnLine = columnLine.substring(columnLine.indexOf("`")+1).trim();	// int(11) NOT NULL AUTO_INCREMENT COMMENT '用户ID',
                    String fieldClass = Object.class.getSimpleName();
                    if (columnLine.startsWith("int") || columnLine.startsWith("tinyint") || columnLine.startsWith("smallint")) {
                        fieldClass = Integer.TYPE.getSimpleName();
                    } else if (columnLine.startsWith("bigint")) {
                        fieldClass = Long.TYPE.getSimpleName();
                    } else if (columnLine.startsWith("float")) {
                        fieldClass = Float.TYPE.getSimpleName();
                    } else if (columnLine.startsWith("double")) {
                        fieldClass = Double.TYPE.getSimpleName();
                    } else if (columnLine.startsWith("datetime") || columnLine.startsWith("timestamp")) {
                        fieldClass = Date.class.getSimpleName();
                    } else if (columnLine.startsWith("varchar") || columnLine.startsWith("text") || columnLine.startsWith("char")) {
                        fieldClass = String.class.getSimpleName();
                    } else if (columnLine.startsWith("decimal")) {
                        fieldClass = BigDecimal.class.getSimpleName();
                    }

                    // field comment
                    String fieldComment = "";
                    if (columnLine.contains("COMMENT")) {
                        String commentTmp = fieldComment = columnLine.substring(columnLine.indexOf("COMMENT")+7).trim();	// '用户ID',
                        if (commentTmp.contains("'") || commentTmp.indexOf("'")!=commentTmp.lastIndexOf("'")) {
                            commentTmp = commentTmp.substring(commentTmp.indexOf("'")+1, commentTmp.lastIndexOf("'"));
                        }
                        fieldComment = commentTmp;
                    }

                    FieldInfo fieldInfo = new FieldInfo();
                    fieldInfo.setColumnName(columnName);
                    fieldInfo.setFieldName(fieldName);
                    fieldInfo.setFieldClass(fieldClass);
                    fieldInfo.setFieldComment(fieldComment);
                    fieldList.add(fieldInfo);
                    
                    Column column = new Column();
                    column.setColumnName(columnName);
                    column.setFieldName(fieldName);
                    column.setFieldClass(fieldClass);
                    column.setFieldComment(fieldComment);
                    columnList.add(column);
                }
            }
        }

        if (fieldList.size() < 1) {
            throw new CodeGenerateException("Table structure anomaly.");
        }

        Table codeJavaInfo = new Table();
        codeJavaInfo.setTableName(tableName);
        codeJavaInfo.setClassName(className);
        codeJavaInfo.setClassComment(classComment);
        codeJavaInfo.setFieldList(fieldList);
        codeJavaInfo.setColumnList(columnList);;
        return codeJavaInfo;
    }
    

    public static ClassInfo processTableIntoClassInfoDruid(String sql) throws IOException {
       DbType dbType = JdbcConstants.MYSQL;
       ClassInfo codeJavaInfo = new ClassInfo();
       //格式化输出
       String result = SQLUtils.format(sql, dbType);
       System.out.println(result); // 缺省大写格式
       List<SQLStatement> stmtList = SQLUtils.parseStatements(sql, dbType);
       
       //解析出的独立语句的个数
       System.err.println("size is:" + stmtList.size());
       for (int i = 0; i < stmtList.size(); i++) {
           SQLStatement stmt = stmtList.get(i);
           if(stmt instanceof SQLSelectStatement) {
               if(stmt instanceof MySqlInsertStatement) {
                   System.err.println("carete ------------------------------------------------------------------------------- ");
                   MySqlCreateTableStatement statement= (MySqlCreateTableStatement) stmt;
                   System.out.println("SQL : " + stmt.toString());
                   System.out.println("getTableName : " + statement.getTableName());
                   System.out.println("getComment : " + statement.getComment());
                   System.out.println("getPrimaryKeyNames : " + statement.getPrimaryKeyNames());
                   System.out.println("getColumnNames : " + statement.getColumnNames(false));
                   System.out.println("getColumnComments : " + statement.getColumnComments());
                       
                   // table Name
                   String tableName = statement.getTableName().toString();
                   codeJavaInfo.setTableName(tableName);
                   // class Name
                   String className = StringUtils.upperCaseFirst(StringUtils.underlineToCamelCase(tableName));
                   if (className.contains("_")) {
                       className = className.replaceAll("_", "");
                   }
                   codeJavaInfo.setClassName(className);
                   String classComment = statement.getComment().toString();
                   codeJavaInfo.setClassComment(classComment);
                   codeJavaInfo.setFieldList(getFieldLis(statement.getColumnDefinitions()));
               }
           }
       }
       return codeJavaInfo;
    }
    public static List<FieldInfo> getFieldLis(List<SQLColumnDefinition> fieldLineList) {
        // field List
        List<FieldInfo> fieldList = new ArrayList<FieldInfo>();
        if (fieldLineList.size() > 0) {
            for (SQLColumnDefinition columnLine :fieldLineList) {
                // column Name
                String columnName = columnLine.getColumnName();   // userid

                // field Name
                String fieldName = StringUtils.lowerCaseFirst(StringUtils.underlineToCamelCase(columnName));
                if (fieldName.contains("_")) {
                    fieldName = fieldName.replaceAll("_", "");
                }
                String fieldType = columnLine.getDataType().getName();
                String fieldClass = Object.class.getSimpleName();
                if (fieldType.startsWith("int") || fieldType.startsWith("tinyint") || fieldType.startsWith("smallint")) {
                    fieldClass = Integer.TYPE.getSimpleName();
                } else if (fieldType.startsWith("bigint")) {
                    fieldClass = Long.TYPE.getSimpleName();
                } else if (fieldType.startsWith("float")) {
                    fieldClass = Float.TYPE.getSimpleName();
                } else if (fieldType.startsWith("double")) {
                    fieldClass = Double.TYPE.getSimpleName();
                } else if (fieldType.startsWith("datetime") || fieldType.startsWith("timestamp")) {
                    fieldClass = Date.class.getSimpleName();
                } else if (fieldType.startsWith("varchar") || fieldType.startsWith("text") || fieldType.startsWith("char")) {
                    fieldClass = String.class.getSimpleName();
                } else if (fieldType.startsWith("decimal")) {
                    fieldClass = BigDecimal.class.getSimpleName();
                }
                // field comment
                String fieldComment = columnLine.getComment().toString();
                String fieldLength = columnLine.getDataType().getArguments().get(0).toString();
                boolean isAutoIncrement = columnLine.isAutoIncrement();
                boolean isPrimaryKey = columnLine.isPrimaryKey();

                FieldInfo fieldInfo = new FieldInfo();
                fieldInfo.setColumnName(columnName);
                fieldInfo.setFieldName(fieldName);
                fieldInfo.setFieldClass(fieldClass);
                fieldInfo.setFieldComment(fieldComment);
                fieldInfo.setFieldLength(fieldLength);
                fieldList.add(fieldInfo);
            }
        }

        if (fieldList.size() < 1) {
            throw new CodeGenerateException("Table structure anomaly.");
        }
        return fieldList;
    }
    
 
    
    public static void main(String[] args) {
         String sql1 = "update tabes12321 set name = 'x' where id < 100 limit 10;";
         String sql2 = "insert into T_UPGRADERECORD (ID, CASEID, CASENAME)\n" + 
             "values (12245, 50047319, '楠?--123-base(2)-base');";
         String sql3 = "delete from tablename1 where id = 10;";
         String sql4 = "CREATE TABLE `userinfo` (\n" + 
              "  `user_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户ID',\n" + 
              "  `username` varchar(255) NOT NULL COMMENT '用户名',\n" + 
              "  `addtime` datetime NOT NULL COMMENT '创建时间',\n" + 
              "  PRIMARY KEY (`user_id`)\n" + 
              ") ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户信息';";
 
        String sql = "select user,userid from emp_table;";
        DbType dbType = JdbcConstants.MYSQL;
        sql=sql+sql1+sql2+sql3+sql4;
        //格式化输出
        String result = SQLUtils.format(sql, dbType);
        System.out.println(result); // 缺省大写格式
        List<SQLStatement> stmtList = SQLUtils.parseStatements(sql, dbType);
 
        //解析出的独立语句的个数
        System.err.println("size is:" + stmtList.size());
        for (int i = 0; i < stmtList.size(); i++) {
            SQLStatement stmt = stmtList.get(i);
            if(stmt instanceof SQLSelectStatement) {
                System.err.println("select ---------------------------------------------- ");
                SQLSelectStatement statement= (SQLSelectStatement) stmt;
                SQLSelectQuery sqlSelectQuery= statement.getSelect().getQuery();
                // 非union的查询语句
                if (sqlSelectQuery instanceof SQLSelectQueryBlock) {
                    SQLSelectQueryBlock sqlSelectQueryBlock = (SQLSelectQueryBlock) sqlSelectQuery;
                    // 获取字段列表
                    List<SQLSelectItem> selectItems         = sqlSelectQueryBlock.getSelectList();
                    selectItems.forEach(x -> {
                        System.out.println(" Tables getSelectList : " +x);
                    });
                    // 获取表
                    SQLTableSource table = sqlSelectQueryBlock.getFrom();
                    System.out.println(" Tables : " + table);
                    if (table instanceof SQLExprTableSource) {
                    // join多表
                    } else if (table instanceof SQLJoinTableSource) {
                    // 子查询作为表
                    } else if (table instanceof SQLSubqueryTableSource) {
                    }
                    // 获取where条件
                    SQLExpr where = sqlSelectQueryBlock.getWhere();
                    // 如果是二元表达式
                    if (where instanceof SQLBinaryOpExpr) {
                        SQLBinaryOpExpr   sqlBinaryOpExpr = (SQLBinaryOpExpr) where;
                        SQLExpr           left            = sqlBinaryOpExpr.getLeft();
                        SQLBinaryOperator operator        = sqlBinaryOpExpr.getOperator();
                        SQLExpr           right           = sqlBinaryOpExpr.getRight();
                    // 如果是子查询
                    } else if (where instanceof SQLInSubQueryExpr) {
                        SQLInSubQueryExpr sqlInSubQueryExpr = (SQLInSubQueryExpr) where;
                    }
                    // 获取分组
                    SQLSelectGroupByClause groupBy = sqlSelectQueryBlock.getGroupBy();
                    // 获取排序
                    SQLOrderBy orderBy = sqlSelectQueryBlock.getOrderBy();
                    // 获取分页
                    SQLLimit limit = sqlSelectQueryBlock.getLimit();
                // union的查询语句
                } else if (sqlSelectQuery instanceof SQLUnionQuery) {
                    // union处理---------------------
                }
            }else if(stmt instanceof MySqlCreateTableStatement) {
                System.err.println("carete ------------------------------------------------------------------------------- ");
                MySqlCreateTableStatement statement= (MySqlCreateTableStatement) stmt;
                System.out.println("SQL : " + stmt.toString());
                System.out.println("getTableName : " + statement.getTableName());
                System.out.println("getComment : " + statement.getComment());
                System.out.println("getPrimaryKeyNames : " + statement.getPrimaryKeyNames());
                System.out.println("getColumnNames : " + statement.getColumnNames(false));
                System.out.println("getColumnComments : " + statement.getColumnComments());
                System.out.println("getColumnName : " + statement.getColumnDefinitions().get(0).getColumnName());
                System.out.println("getComment : " + statement.getColumnDefinitions().get(0).getComment());
                System.out.println("isAutoIncrement : " + statement.getColumnDefinitions().get(0).isAutoIncrement());
                System.out.println("isPrimaryKey : " + statement.getColumnDefinitions().get(0).isPrimaryKey());
                System.out.println("getDataType : " + statement.getColumnDefinitions().get(0).getDataType().getName());
                System.out.println("getDataType-getArguments : " + statement.getColumnDefinitions().get(0).getDataType().getArguments());
            }else  if(stmt instanceof MySqlDeleteStatement) {
                System.err.println("delete ------------------------------------------------------------------------------- ");
                MySqlDeleteStatement statement= (MySqlDeleteStatement) stmt;
                System.out.println("SQL : " + stmt.toString());
                System.out.println("getTableName : " + statement.getTableName());
                System.out.println("getWhere : " + statement.getWhere());
                System.out.println("getUsing : " + statement.getUsing());
                System.out.println("getWith : " + statement.getWith());
            }else  if(stmt instanceof MySqlUpdateStatement) {
                MySqlUpdateStatement statement= (MySqlUpdateStatement) stmt;
                System.err.println("update ------------------------------------------------------------------------------- ");
                System.out.println("SQL : " + stmt.toString());
                System.out.println("getTableName : " + statement.getTableName());
                System.out.println("getHints : " + statement.getHints());
                System.out.println("getItems : " + statement.getItems());
                System.out.println("getLimit : " + statement.getLimit());
            }else  if(stmt instanceof MySqlInsertStatement) {
                MySqlInsertStatement statement= (MySqlInsertStatement) stmt;
                System.err.println("update ------------------------------------------------------------------------------- ");
                System.out.println("SQL : " + stmt.toString());
                System.out.println("getTableName : " + statement.getTableName());
                System.out.println("getColumns : " + statement.getColumns());
                System.out.println("getValues : " + statement.getValues());
                System.out.println("getColumnsString : " + statement.getColumnsString());
            }
        }
    }
    

    /**
     * 解析DDL SQL生成类信息
     *
     * @param paramInfo
     * @return
     */
    public static ClassInfo processTableIntoClassInfo(ParamInfo paramInfo)
            throws IOException {
        //process the param
        String tableSql = paramInfo.getTableSql();
        String nameCaseType = MapUtil.getString(paramInfo.getOptions(),"nameCaseType");
        Boolean isPackageType = MapUtil.getBoolean(paramInfo.getOptions(),"isPackageType");

        if (tableSql == null || tableSql.trim().length() == 0) {
            throw new CodeGenerateException("Table structure can not be empty. 表结构不能为空。");
        }
        //deal with special character
        tableSql = tableSql.trim().replaceAll("'", "`").replaceAll("\"", "`").replaceAll("，", ",").toLowerCase();
        //deal with java string copy \n"
        tableSql = tableSql.trim().replaceAll("\\\\n`", "").replaceAll("\\+", "").replaceAll("``", "`").replaceAll("\\\\", "");
        // table Name
        String tableName = null;
        if (tableSql.contains("TABLE") && tableSql.contains("(")) {
            tableName = tableSql.substring(tableSql.indexOf("TABLE") + 5, tableSql.indexOf("("));
        } else if (tableSql.contains("table") && tableSql.contains("(")) {
            tableName = tableSql.substring(tableSql.indexOf("table") + 5, tableSql.indexOf("("));
        } else {
            throw new CodeGenerateException("Table structure incorrect.表结构不正确。");
        }

        //新增处理create table if not exists members情况
        if (tableName.contains("if not exists")) {
            tableName = tableName.replaceAll("if not exists", "");
        }

        if (tableName.contains("`")) {
            tableName = tableName.substring(tableName.indexOf("`") + 1, tableName.lastIndexOf("`"));
        } else {
            //空格开头的，需要替换掉\n\t空格
            tableName = tableName.replaceAll(" ", "").replaceAll("\n", "").replaceAll("\t", "");
        }
        //优化对byeas`.`ct_bd_customerdiscount这种命名的支持
        if (tableName.contains("`.`")) {
            tableName = tableName.substring(tableName.indexOf("`.`") + 3);
        } else if (tableName.contains(".")) {
            //优化对likeu.members这种命名的支持
            tableName = tableName.substring(tableName.indexOf(".") + 1);
        }

        //ignore prefix
        if(tableName!=null && StringUtils.isNotNull(MapUtil.getString(paramInfo.getOptions(),"ignorePrefix"))){
            tableName = tableName.replaceAll(MapUtil.getString(paramInfo.getOptions(),"ignorePrefix"),"");
        }
        // class Name
        String className = StringUtils.upperCaseFirst(StringUtils.underlineToCamelCase(tableName));
        if (className.contains("_")) {
            className = className.replaceAll("_", "");
        }

        // class Comment
        String classComment = null;
        //mysql是comment=,pgsql/oracle是comment on table,
        //2020-05-25 优化表备注的获取逻辑
        if (tableSql.contains("comment=") || tableSql.contains("comment on table")) {
            String classCommentTmp = (tableSql.contains("comment=")) ?
                    tableSql.substring(tableSql.lastIndexOf("comment=") + 8).trim() : tableSql.substring(tableSql.lastIndexOf("comment on table") + 17).trim();
            if (classCommentTmp.contains("`")) {
                classCommentTmp = classCommentTmp.substring(classCommentTmp.indexOf("`") + 1);
                classCommentTmp = classCommentTmp.substring(0, classCommentTmp.indexOf("`"));
                classComment = classCommentTmp;
            } else {
                //非常规的没法分析
                classComment = className;
            }
        } else {
            //修复表备注为空问题
            classComment = tableName;
        }
        //如果备注跟;混在一起，需要替换掉
        classComment = classComment.replaceAll(";", "");
        // field List
        List<FieldInfo> fieldList = new ArrayList<FieldInfo>();

        // 正常( ) 内的一定是字段相关的定义。
        String fieldListTmp = tableSql.substring(tableSql.indexOf("(") + 1, tableSql.lastIndexOf(")"));

        // 匹配 comment，替换备注里的小逗号, 防止不小心被当成切割符号切割
        String commentPattenStr1 = "comment `(.*?)\\`";
        Matcher matcher1 = Pattern.compile(commentPattenStr1).matcher(fieldListTmp);
        while (matcher1.find()) {

            String commentTmp = matcher1.group();
            //2018-9-27 zhengk 不替换，只处理，支持COMMENT评论里面多种注释
            //commentTmp = commentTmp.replaceAll("\\ comment `|\\`", " ");      // "\\{|\\}"

            if (commentTmp.contains(",")) {
                String commentTmpFinal = commentTmp.replaceAll(",", "，");
                fieldListTmp = fieldListTmp.replace(matcher1.group(), commentTmpFinal);
            }
        }
        //2018-10-18 zhengkai 新增支持double(10, 2)等类型中有英文逗号的特殊情况
        String commentPattenStr2 = "\\`(.*?)\\`";
        Matcher matcher2 = Pattern.compile(commentPattenStr2).matcher(fieldListTmp);
        while (matcher2.find()) {
            String commentTmp2 = matcher2.group();
            if (commentTmp2.contains(",")) {
                String commentTmpFinal = commentTmp2.replaceAll(",", "，").replaceAll("\\(", "（").replaceAll("\\)", "）");
                fieldListTmp = fieldListTmp.replace(matcher2.group(), commentTmpFinal);
            }
        }
        //2018-10-18 zhengkai 新增支持double(10, 2)等类型中有英文逗号的特殊情况
        String commentPattenStr3 = "\\((.*?)\\)";
        Matcher matcher3 = Pattern.compile(commentPattenStr3).matcher(fieldListTmp);
        while (matcher3.find()) {
            String commentTmp3 = matcher3.group();
            if (commentTmp3.contains(",")) {
                String commentTmpFinal = commentTmp3.replaceAll(",", "，");
                fieldListTmp = fieldListTmp.replace(matcher3.group(), commentTmpFinal);
            }
        }
        String[] fieldLineList = fieldListTmp.split(",");
        if (fieldLineList.length > 0) {
            int i = 0;
            //i为了解决primary key关键字出现的地方，出现在前3行，一般和id有关
            for (String columnLine : fieldLineList) {
                i++;
                columnLine = columnLine.replaceAll("\n", "").replaceAll("\t", "").trim();
                // `userid` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户ID',
                // 2018-9-18 zhengk 修改为contains，提升匹配率和匹配不按照规矩出牌的语句
                // 2018-11-8 zhengkai 修复tornadoorz反馈的KEY FK_permission_id (permission_id),KEY FK_role_id (role_id)情况
                // 2019-2-22 zhengkai 要在条件中使用复杂的表达式
                // 2019-4-29 zhengkai 优化对普通和特殊storage关键字的判断（感谢@AhHeadFloating的反馈 ）
                // 2020-10-20 zhengkai 优化对fulltext/index关键字的处理（感谢@WEGFan的反馈）
                boolean specialFlag = (!columnLine.contains("key ") && !columnLine.contains("constraint") && !columnLine.contains("using") && !columnLine.contains("unique ")
                        && !(columnLine.contains("primary ") && columnLine.indexOf("storage") + 3 > columnLine.indexOf("("))
                        && !columnLine.contains("fulltext ") && !columnLine.contains("index ")
                        && !columnLine.contains("pctincrease")
                        && !columnLine.contains("buffer_pool") && !columnLine.contains("tablespace")
                        && !(columnLine.contains("primary ") && i > 3));
                if (specialFlag) {
                    //如果是oracle的number(x,x)，可能出现最后分割残留的,x)，这里做排除处理
                    if (columnLine.length() < 5) {
                        continue;
                    }
                    //2018-9-16 zhengkai 支持'符号以及空格的oracle语句// userid` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户ID',
                    String columnName = "";
                    columnLine = columnLine.replaceAll("`", " ").replaceAll("\"", " ").replaceAll("'", "").replaceAll("  ", " ").trim();
                    //如果遇到username varchar(65) default '' not null,这种情况，判断第一个空格是否比第一个引号前
                    columnName = columnLine.substring(0, columnLine.indexOf(" "));
                    // field Name
                    // 2019-09-08 yj 添加是否下划线转换为驼峰的判断
                    String fieldName = null;
                    if (ParamInfo.NAME_CASE_TYPE.CAMEL_CASE.equals(nameCaseType)) {
                        fieldName = StringUtils.lowerCaseFirst(StringUtils.underlineToCamelCase(columnName));
                        if (fieldName.contains("_")) {
                            fieldName = fieldName.replaceAll("_", "");
                        }
                    } else if (ParamInfo.NAME_CASE_TYPE.UNDER_SCORE_CASE.equals(nameCaseType)) {
                        fieldName = StringUtils.lowerCaseFirst(columnName);
                    } else if (ParamInfo.NAME_CASE_TYPE.UPPER_UNDER_SCORE_CASE.equals(nameCaseType)) {
                        fieldName = StringUtils.lowerCaseFirst(columnName.toUpperCase());
                    } else {
                        fieldName = columnName;
                    }

                    // field class
                    columnLine = columnLine.substring(columnLine.indexOf("`") + 1).trim();
                    // int(11) NOT NULL AUTO_INCREMENT COMMENT '用户ID',
                    String fieldClass = Object.class.getSimpleName();
                    //2018-9-16 zhengk 补充char/clob/blob/json等类型，如果类型未知，默认为String
                    //2018-11-22 lshz0088 处理字段类型的时候，不严谨columnLine.contains(" int") 类似这种的，可在前后适当加一些空格之类的加以区分，否则当我的字段包含这些字符的时候，产生类型判断问题。
                    //2020-05-03 MOSHOW.K.ZHENG 优化对所有类型的处理
                    //2020-10-20 zhengkai 新增包装类型的转换选择
                    if (columnLine.contains(" tinyint")) {
                        //20191115 MOSHOW.K.ZHENG 支持对tinyint的特殊处理
                        fieldClass = MapUtil.getString(paramInfo.getOptions(),"tinyintTransType");;
                    } else if (columnLine.contains(" int") || columnLine.contains(" smallint")) {
                        fieldClass = (isPackageType)?Integer.class.getSimpleName():"int";
                    } else if (columnLine.contains(" bigint")) {
                        fieldClass = (isPackageType)?Long.class.getSimpleName():"long";
                    } else if (columnLine.contains(" float")) {
                        fieldClass = (isPackageType)?Float.class.getSimpleName():"float";
                    } else if (columnLine.contains(" double")) {
                        fieldClass = (isPackageType)?Double.class.getSimpleName():"double";
                    } else if (columnLine.contains(" time") || columnLine.contains(" date") || columnLine.contains(" datetime") || columnLine.contains(" timestamp")) {
                        fieldClass =  MapUtil.getString(paramInfo.getOptions(),"timeTransType");
                    } else if (columnLine.contains(" varchar") || columnLine.contains(" text") || columnLine.contains(" char")
                            || columnLine.contains(" clob") || columnLine.contains(" blob") || columnLine.contains(" json")) {
                        fieldClass = String.class.getSimpleName();
                    } else if (columnLine.contains(" decimal") || columnLine.contains(" number")) {
                        //2018-11-22 lshz0088 建议对number类型增加int，long，BigDecimal的区分判断
                        //如果startKh大于等于0，则表示有设置取值范围
                        int startKh = columnLine.indexOf("(");
                        if (startKh >= 0) {
                            int endKh = columnLine.indexOf(")", startKh);
                            String[] fanwei = columnLine.substring(startKh + 1, endKh).split("，");
                            //2019-1-5 zhengk 修复@arthaschan反馈的超出范围错误
                            //System.out.println("fanwei"+ JSON.toJSONString(fanwei));
                            //                            //number(20,6) fanwei["20","6"]
                            //                            //number(0,6) fanwei["0","6"]
                            //                            //number(20,0) fanwei["20","0"]
                            //                            //number(20) fanwei["20"]
                            //如果括号里是1位或者2位且第二位为0，则进行特殊处理。只有有小数位，都设置为BigDecimal。
                            if ((fanwei.length > 1 && "0".equals(fanwei[1])) || fanwei.length == 1) {
                                int length = Integer.parseInt(fanwei[0]);
                                if (fanwei.length > 1) {
                                    length = Integer.valueOf(fanwei[1]);
                                }
                                //数字范围9位及一下用Integer，大的用Long
                                if (length <= 9) {
                                    fieldClass = (isPackageType)?Integer.class.getSimpleName():"int";
                                } else {
                                    fieldClass = (isPackageType)?Long.class.getSimpleName():"long";
                                }
                            } else {
                                //有小数位数一律使用BigDecimal
                                fieldClass = BigDecimal.class.getSimpleName();
                            }
                        } else {
                            fieldClass = BigDecimal.class.getSimpleName();
                        }
                    } else if (columnLine.contains(" boolean")) {
                        //20190910 MOSHOW.K.ZHENG 新增对boolean的处理（感谢@violinxsc的反馈）以及修复tinyint类型字段无法生成boolean类型问题（感谢@hahaYhui的反馈）
                        fieldClass = (isPackageType)?Boolean.class.getSimpleName():"boolean";
                    } else {
                        fieldClass = String.class.getSimpleName();
                    }

                    // field comment，MySQL的一般位于field行，而pgsql和oralce多位于后面。
                    String fieldComment = null;
                    if (tableSql.contains("comment on column") && (tableSql.contains("." + columnName + " is ") || tableSql.contains(".`" + columnName + "` is"))) {
                        //新增对pgsql/oracle的字段备注支持
                        //COMMENT ON COLUMN public.check_info.check_name IS '检查者名称';
                        //2018-11-22 lshz0088 正则表达式的点号前面应该加上两个反斜杠，否则会认为是任意字符
                        //2019-4-29 zhengkai 优化对oracle注释comment on column的支持（@liukex）
                        tableSql = tableSql.replaceAll(".`" + columnName + "` is", "." + columnName + " is");
                        Matcher columnCommentMatcher = Pattern.compile("\\." + columnName + " is `").matcher(tableSql);
                        fieldComment = columnName;
                        while (columnCommentMatcher.find()) {
                            String columnCommentTmp = columnCommentMatcher.group();
                            //System.out.println(columnCommentTmp);
                            fieldComment = tableSql.substring(tableSql.indexOf(columnCommentTmp) + columnCommentTmp.length()).trim();
                            fieldComment = fieldComment.substring(0, fieldComment.indexOf("`")).trim();
                        }
                    } else if (columnLine.contains(" comment")) {
                        //20200518 zhengkai 修复包含comment关键字的问题
                        String commentTmp = columnLine.substring(columnLine.lastIndexOf("comment") + 7).trim();
                        // '用户ID',
                        if (commentTmp.contains("`") || commentTmp.indexOf("`") != commentTmp.lastIndexOf("`")) {
                            commentTmp = commentTmp.substring(commentTmp.indexOf("`") + 1, commentTmp.lastIndexOf("`"));
                        }
                        //解决最后一句是评论，无主键且连着)的问题:album_id int(3) default '1' null comment '相册id：0 代表头像 1代表照片墙')
                        if (commentTmp.contains(")")) {
                            commentTmp = commentTmp.substring(0, commentTmp.lastIndexOf(")") + 1);
                        }
                        fieldComment = commentTmp;
                    } else {
                        //修复comment不存在导致报错的问题
                        fieldComment = columnName;
                    }

                    FieldInfo fieldInfo = new FieldInfo();
                    fieldInfo.setColumnName(columnName);
                    fieldInfo.setFieldName(fieldName);
                    fieldInfo.setFieldClass(fieldClass);
                    fieldInfo.setFieldComment(fieldComment);

                    fieldList.add(fieldInfo);
                }
            }
        }

        if (fieldList.size() < 1) {
            throw new CodeGenerateException("表结构分析失败，请检查语句或者提交issue给我");
        }

        ClassInfo codeJavaInfo = new ClassInfo();
        codeJavaInfo.setTableName(tableName);
        codeJavaInfo.setClassName(className);
        codeJavaInfo.setClassComment(classComment);
        codeJavaInfo.setFieldList(fieldList);

        return codeJavaInfo;
    }

    /**
     * 解析JSON生成类信息
     *
     * @param paramInfo
     * @return
     */
    public static ClassInfo processJsonToClassInfo(ParamInfo paramInfo) {
        ClassInfo codeJavaInfo = new ClassInfo();
        codeJavaInfo.setTableName("JsonDto");
        codeJavaInfo.setClassName("JsonDto");
        codeJavaInfo.setClassComment("JsonDto");

        //support children json if forget to add '{' in front of json
        if (paramInfo.getTableSql().trim().startsWith("\"")) {
            paramInfo.setTableSql("{" + paramInfo.getTableSql());
        }
        if (JSON.isValid(paramInfo.getTableSql())) {
            if (paramInfo.getTableSql().trim().startsWith("{")) {
                JSONObject jsonObject = JSONObject.parseObject(paramInfo.getTableSql().trim());
                //parse FieldList by JSONObject
                codeJavaInfo.setFieldList(processJsonObjectToFieldList(jsonObject));
            } else if (paramInfo.getTableSql().trim().startsWith("[")) {
                JSONArray jsonArray = JSONArray.parseArray(paramInfo.getTableSql().trim());
                //parse FieldList by JSONObject
                codeJavaInfo.setFieldList(processJsonObjectToFieldList(jsonArray.getJSONObject(0)));
            }
        }

        return codeJavaInfo;
    }

    /**
     * parse SQL by regex
     *
     * @param paramInfo
     * @return
     * @author https://github.com/ydq
     */
    public static ClassInfo processTableToClassInfoByRegex(ParamInfo paramInfo) {
        // field List
        List<FieldInfo> fieldList = new ArrayList<FieldInfo>();
        //return classInfo
        ClassInfo codeJavaInfo = new ClassInfo();

        //匹配整个ddl，将ddl分为表名，列sql部分，表注释
        String DDL_PATTEN_STR = "\\s*create\\s+table\\s+(?<tableName>\\S+)[^\\(]*\\((?<columnsSQL>[\\s\\S]+)\\)[^\\)]+?(comment\\s*(=|on\\s+table)\\s*'(?<tableComment>.*?)'\\s*;?)?$";

        Pattern DDL_PATTERN = Pattern.compile(DDL_PATTEN_STR, Pattern.CASE_INSENSITIVE);

        //匹配列sql部分，分别解析每一列的列名 类型 和列注释
        String COL_PATTERN_STR = "\\s*(?<fieldName>\\S+)\\s+(?<fieldType>\\w+)\\s*(?:\\([\\s\\d,]+\\))?((?!comment).)*(comment\\s*'(?<fieldComment>.*?)')?\\s*(,|$)";

        Pattern COL_PATTERN = Pattern.compile(COL_PATTERN_STR, Pattern.CASE_INSENSITIVE);

        Matcher matcher = DDL_PATTERN.matcher(paramInfo.getTableSql().trim());
        if (matcher.find()) {
            String tableName = matcher.group("tableName");
            String tableComment = matcher.group("tableComment");
            codeJavaInfo.setTableName(tableName.replaceAll("'", ""));
            codeJavaInfo.setClassName(tableName.replaceAll("'", ""));
            codeJavaInfo.setClassComment(tableComment.replaceAll("'", ""));
            String columnsSQL = matcher.group("columnsSQL");
            if (columnsSQL != null && columnsSQL.length() > 0) {
                Matcher colMatcher = COL_PATTERN.matcher(columnsSQL);
                while (colMatcher.find()) {
                    String fieldName = colMatcher.group("fieldName");
                    String fieldType = colMatcher.group("fieldType");
                    String fieldComment = colMatcher.group("fieldComment");
                    if (!"key".equalsIgnoreCase(fieldType)) {
                        FieldInfo fieldInfo = new FieldInfo();
                        fieldInfo.setFieldName(fieldName.replaceAll("'", ""));
                        fieldInfo.setColumnName(fieldName.replaceAll("'", ""));
                        fieldInfo.setFieldClass(fieldType.replaceAll("'", ""));
                        fieldInfo.setFieldComment(fieldComment.replaceAll("'", ""));
                        fieldList.add(fieldInfo);
                    }
                }
            }
            codeJavaInfo.setFieldList(fieldList);
        }
        return codeJavaInfo;
    }

    public static List<FieldInfo> processJsonObjectToFieldList(JSONObject jsonObject) {
        // field List
        List<FieldInfo> fieldList = new ArrayList<FieldInfo>();
        jsonObject.keySet().stream().forEach(jsonField -> {
            FieldInfo fieldInfo = new FieldInfo();
            fieldInfo.setFieldName(jsonField);
            fieldInfo.setColumnName(jsonField);
            fieldInfo.setFieldClass(String.class.getSimpleName());
            fieldInfo.setFieldComment("father:" + jsonField);
            fieldList.add(fieldInfo);
            if (jsonObject.get(jsonField) instanceof JSONArray) {
                jsonObject.getJSONArray(jsonField).stream().forEach(arrayObject -> {
                    FieldInfo fieldInfo2 = new FieldInfo();
                    fieldInfo2.setFieldName(arrayObject.toString());
                    fieldInfo2.setColumnName(arrayObject.toString());
                    fieldInfo2.setFieldClass(String.class.getSimpleName());
                    fieldInfo2.setFieldComment("children:" + arrayObject.toString());
                    fieldList.add(fieldInfo2);
                });
            } else if (jsonObject.get(jsonField) instanceof JSONObject) {
                jsonObject.getJSONObject(jsonField).keySet().stream().forEach(arrayObject -> {
                    FieldInfo fieldInfo2 = new FieldInfo();
                    fieldInfo2.setFieldName(arrayObject.toString());
                    fieldInfo2.setColumnName(arrayObject.toString());
                    fieldInfo2.setFieldClass(String.class.getSimpleName());
                    fieldInfo2.setFieldComment("children:" + arrayObject.toString());
                    fieldList.add(fieldInfo2);
                });
            }
        });
        if (fieldList.size() < 1) {
            throw new CodeGenerateException("JSON解析失败");
        }
        return fieldList;
    }

    public static ClassInfo processInsertSqlToClassInfo(ParamInfo paramInfo) {
        // field List
        List<FieldInfo> fieldList = new ArrayList<FieldInfo>();
        //return classInfo
        ClassInfo codeJavaInfo = new ClassInfo();

        //get origin sql
        String fieldSqlStr = paramInfo.getTableSql().toLowerCase().trim();
        fieldSqlStr = fieldSqlStr.replaceAll("  ", " ").replaceAll("\\\\n`", "")
                .replaceAll("\\+", "").replaceAll("``", "`").replaceAll("\\\\", "");
        String valueStr = fieldSqlStr.substring(fieldSqlStr.lastIndexOf("values") + 6).replaceAll(" ", "").replaceAll("\\(", "").replaceAll("\\)", "");
        //get the string between insert into and values
        fieldSqlStr = fieldSqlStr.substring(0, fieldSqlStr.lastIndexOf("values"));

        System.out.println(fieldSqlStr);

        String insertSqlPattenStr = "insert into (?<tableName>.*) \\((?<columnsSQL>.*)\\)";
        //String DDL_PATTEN_STR="\\s*create\\s+table\\s+(?<tableName>\\S+)[^\\(]*\\((?<columnsSQL>[\\s\\S]+)\\)[^\\)]+?(comment\\s*(=|on\\s+table)\\s*'(?<tableComment>.*?)'\\s*;?)?$";

        Matcher matcher1 = Pattern.compile(insertSqlPattenStr).matcher(fieldSqlStr);
        while (matcher1.find()) {

            String tableName = matcher1.group("tableName");
            //System.out.println("tableName:"+tableName);
            codeJavaInfo.setClassName(tableName);
            codeJavaInfo.setTableName(tableName);

            String columnsSQL = matcher1.group("columnsSQL");
            //System.out.println("columnsSQL:"+columnsSQL);

            List<String> valueList = new ArrayList<>();
            //add values as comment
            Arrays.stream(valueStr.split(",")).forEach(column -> {
                valueList.add(column);
            });
            AtomicInteger n = new AtomicInteger(0);
            //add column to fleldList
            Arrays.stream(columnsSQL.replaceAll(" ", "").split(",")).forEach(column -> {
                FieldInfo fieldInfo2 = new FieldInfo();
                fieldInfo2.setFieldName(column);
                fieldInfo2.setColumnName(column);
                fieldInfo2.setFieldClass(String.class.getSimpleName());
                if (n.get() < valueList.size()) {
                    fieldInfo2.setFieldComment(column + " , eg." + valueList.get(n.get()));
                }
                fieldList.add(fieldInfo2);
                n.getAndIncrement();
            });

        }
        if (fieldList.size() < 1) {
            throw new CodeGenerateException("INSERT SQL解析失败");
        }
        codeJavaInfo.setFieldList(fieldList);
        return codeJavaInfo;
    }


}

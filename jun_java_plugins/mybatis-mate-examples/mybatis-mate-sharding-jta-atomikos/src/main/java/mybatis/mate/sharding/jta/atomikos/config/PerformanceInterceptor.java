package mybatis.mate.sharding.jta.atomikos.config;


import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.PluginUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.SystemClock;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.apache.ibatis.session.ResultHandler;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Statement;
import java.util.*;

/**
 * <p>
 * 性能分析拦截器，用于输出每条 SQL 语句及其执行时间
 * </p>
 *
 * @author hubin
 * @Date 2021-10-09
 */
@Slf4j
@Component
@Intercepts({@Signature(type = StatementHandler.class, method = "query", args = {Statement.class, ResultHandler.class}),
        @Signature(type = StatementHandler.class, method = "update", args = {Statement.class}),
        @Signature(type = StatementHandler.class, method = "batch", args = {Statement.class})})
public class PerformanceInterceptor implements Interceptor {
    /**
     * SQL 执行最大时长，超过自动停止运行，有助于发现问题。
     */
    private long maxTime = 0;
    /**
     * SQL 是否格式化
     */
    private boolean format = false;
    /**
     * 是否写入日志文件<br>
     * true 写入日志文件，不阻断程序执行！<br>
     * 超过设定的最大执行时长异常提示！
     */
    private boolean writeInLog = false;

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        Statement statement;
        Object firstArg = invocation.getArgs()[0];
        if (Proxy.isProxyClass(firstArg.getClass())) {
            statement = (Statement) SystemMetaObject.forObject(firstArg).getValue("h.statement");
        } else {
            statement = (Statement) firstArg;
        }
        MetaObject stmtMetaObj = SystemMetaObject.forObject(statement);
        try {
            statement = (Statement) stmtMetaObj.getValue("stmt.statement");
        } catch (Exception e) {
            // do nothing
        }
        if (stmtMetaObj.hasGetter("delegate")) {//Hikari
            try {
                statement = (Statement) stmtMetaObj.getValue("delegate");
            } catch (Exception e) {

            }
        }

        String originalSql = null;
        if (originalSql == null) {
            originalSql = statement.toString();
        }
        originalSql = originalSql.replaceAll("[\\s]+", " ");
        int index = indexOfSqlStart(originalSql);
        if (index > 0) {
            originalSql = originalSql.substring(index);
        }

        // 计算执行 SQL 耗时
        long start = SystemClock.now();
        Object result = invocation.proceed();
        long timing = SystemClock.now() - start;

        // 格式化 SQL 打印执行结果
        Object target = PluginUtils.realTarget(invocation.getTarget());
        MetaObject metaObject = SystemMetaObject.forObject(target);
        MappedStatement ms = (MappedStatement) metaObject.getValue("delegate.mappedStatement");
        StringBuilder formatSql = new StringBuilder();
        formatSql.append(" Time：").append(timing);
        formatSql.append(" ms - ID：").append(ms.getId());
        formatSql.append("\n Execute SQL：").append(sqlFormat(originalSql, format)).append("\n");
        if (this.isWriteInLog()) {
            if (this.getMaxTime() >= 1 && timing > this.getMaxTime()) {
                log.error(formatSql.toString());
            } else {
                log.debug(formatSql.toString());
            }
        } else {
            System.err.println(formatSql);
            if (this.getMaxTime() >= 1 && timing > this.getMaxTime()) {
                throw new RuntimeException(" The SQL execution time is too large, please optimize ! ");
            }
        }
        return result;
    }

    @Override
    public Object plugin(Object target) {
        if (target instanceof StatementHandler) {
            return Plugin.wrap(target, this);
        }
        return target;
    }

    @Override
    public void setProperties(Properties prop) {
        String maxTime = prop.getProperty("maxTime");
        String format = prop.getProperty("format");
        if (StringUtils.isNotEmpty(maxTime)) {
            this.maxTime = Long.parseLong(maxTime);
        }
        if (StringUtils.isNotEmpty(format)) {
            this.format = Boolean.valueOf(format);
        }
    }

    public long getMaxTime() {
        return maxTime;
    }

    public PerformanceInterceptor setMaxTime(long maxTime) {
        this.maxTime = maxTime;
        return this;
    }

    public boolean isFormat() {
        return format;
    }

    public PerformanceInterceptor setFormat(boolean format) {
        this.format = format;
        return this;
    }

    public boolean isWriteInLog() {
        return writeInLog;
    }

    public PerformanceInterceptor setWriteInLog(boolean writeInLog) {
        this.writeInLog = writeInLog;
        return this;
    }

    public Method getMethodRegular(Class<?> clazz, String methodName) {
        if (Object.class.equals(clazz)) {
            return null;
        }
        for (Method method : clazz.getDeclaredMethods()) {
            if (method.getName().equals(methodName)) {
                return method;
            }
        }
        return getMethodRegular(clazz.getSuperclass(), methodName);
    }

    /**
     * 获取sql语句开头部分
     *
     * @param sql
     * @return
     */
    private int indexOfSqlStart(String sql) {
        String upperCaseSql = sql.toUpperCase();
        Set<Integer> set = new HashSet<>();
        set.add(upperCaseSql.indexOf("SELECT "));
        set.add(upperCaseSql.indexOf("UPDATE "));
        set.add(upperCaseSql.indexOf("INSERT "));
        set.add(upperCaseSql.indexOf("DELETE "));
        set.remove(-1);
        if (CollectionUtils.isEmpty(set)) {
            return -1;
        }
        List<Integer> list = new ArrayList<>(set);
        Collections.sort(list, Integer::compareTo);
        return list.get(0);
    }

    private final static SqlFormatter sqlFormatter = new SqlFormatter();

    /**
     * 格式sql
     *
     * @param boundSql
     * @param format
     * @return
     */
    public static String sqlFormat(String boundSql, boolean format) {
        if (format) {
            try {
                return sqlFormatter.format(boundSql);
            } catch (Exception ignored) {
            }
        }
        return boundSql;
    }

    static class SqlFormatter {
        public static final String WHITESPACE = " \n\r\f\t";
        private static final Set<String> BEGIN_CLAUSES = new HashSet<>();
        private static final Set<String> END_CLAUSES = new HashSet<>();
        private static final Set<String> LOGICAL = new HashSet<>();
        private static final Set<String> QUANTIFIERS = new HashSet<>();
        private static final Set<String> DML = new HashSet<>();
        private static final Set<String> MISC = new HashSet<>();
        private static final String INDENT_STRING = "    ";
        private static final String INITIAL = "\n    ";

        static {
            BEGIN_CLAUSES.add("left");
            BEGIN_CLAUSES.add("right");
            BEGIN_CLAUSES.add("inner");
            BEGIN_CLAUSES.add("outer");
            BEGIN_CLAUSES.add("group");
            BEGIN_CLAUSES.add("order");

            END_CLAUSES.add("where");
            END_CLAUSES.add("set");
            END_CLAUSES.add("having");
            END_CLAUSES.add("join");
            END_CLAUSES.add("from");
            END_CLAUSES.add("by");
            END_CLAUSES.add("join");
            END_CLAUSES.add("into");
            END_CLAUSES.add("union");

            LOGICAL.add("and");
            LOGICAL.add("or");
            LOGICAL.add("when");
            LOGICAL.add("else");
            LOGICAL.add("end");

            QUANTIFIERS.add("in");
            QUANTIFIERS.add("all");
            QUANTIFIERS.add("exists");
            QUANTIFIERS.add("some");
            QUANTIFIERS.add("any");

            DML.add("insert");
            DML.add("update");
            DML.add("delete");

            MISC.add("select");
            MISC.add("on");
        }

        public String format(String source) {
            return new FormatProcess(source).perform();
        }

        private static class FormatProcess {

            boolean beginLine = true;
            boolean afterBeginBeforeEnd;
            boolean afterByOrSetOrFromOrSelect;
            boolean afterValues;
            boolean afterOn;
            boolean afterBetween;
            boolean afterInsert;
            int inFunction;
            int parensSinceSelect;
            private LinkedList<Integer> parenCounts = new LinkedList<>();
            private LinkedList<Boolean> afterByOrFromOrSelects = new LinkedList<>();

            int indent = 1;

            StringBuilder result = new StringBuilder();
            StringTokenizer tokens;
            String lastToken;
            String token;
            String lcToken;

            public FormatProcess(String sql) {
                tokens = new StringTokenizer(
                        sql,
                        "()+*/-=<>'`\"[]," + WHITESPACE,
                        true
                );
            }

            public String perform() {

                result.append(INITIAL);

                while (tokens.hasMoreTokens()) {
                    token = tokens.nextToken();
                    lcToken = token.toLowerCase(Locale.ROOT);

                    if ("'".equals(token)) {
                        String t = "";
                        do {
                            try {
                                t = tokens.nextToken();
                            } catch (Exception ignored) {
                            }
                            token += t;
                        }
                        // cannot handle single quotes
                        while (!"'".equals(t) && tokens.hasMoreTokens());
                    } else if ("\"".equals(token)) {
                        String t;
                        do {
                            t = tokens.nextToken();
                            token += t;
                        }
                        while (!"\"".equals(t));
                    }

                    if (afterByOrSetOrFromOrSelect && ",".equals(token)) {
                        commaAfterByOrFromOrSelect();
                    } else if (afterOn && ",".equals(token)) {
                        commaAfterOn();
                    } else if ("(".equals(token)) {
                        openParen();
                    } else if (")".equals(token)) {
                        closeParen();
                    } else if (BEGIN_CLAUSES.contains(lcToken)) {
                        beginNewClause();
                    } else if (END_CLAUSES.contains(lcToken)) {
                        endNewClause();
                    } else if ("select".equals(lcToken)) {
                        select();
                    } else if (DML.contains(lcToken)) {
                        updateOrInsertOrDelete();
                    } else if ("values".equals(lcToken)) {
                        values();
                    } else if ("on".equals(lcToken)) {
                        on();
                    } else if (afterBetween && lcToken.equals("and")) {
                        misc();
                        afterBetween = false;
                    } else if (LOGICAL.contains(lcToken)) {
                        logical();
                    } else if (isWhitespace(token)) {
                        white();
                    } else {
                        misc();
                    }

                    if (!isWhitespace(token)) {
                        lastToken = lcToken;
                    }

                }
                return result.toString();
            }

            private void commaAfterOn() {
                out();
                indent--;
                newline();
                afterOn = false;
                afterByOrSetOrFromOrSelect = true;
            }

            private void commaAfterByOrFromOrSelect() {
                out();
                newline();
            }

            private void logical() {
                if ("end".equals(lcToken)) {
                    indent--;
                }
                newline();
                out();
                beginLine = false;
            }

            private void on() {
                indent++;
                afterOn = true;
                newline();
                out();
                beginLine = false;
            }

            private void misc() {
                out();
                if ("between".equals(lcToken)) {
                    afterBetween = true;
                }
                if (afterInsert) {
                    newline();
                    afterInsert = false;
                } else {
                    beginLine = false;
                    if ("case".equals(lcToken)) {
                        indent++;
                    }
                }
            }

            private void white() {
                if (!beginLine) {
                    result.append(" ");
                }
            }

            private void updateOrInsertOrDelete() {
                out();
                indent++;
                beginLine = false;
                if ("update".equals(lcToken)) {
                    newline();
                }
                if ("insert".equals(lcToken)) {
                    afterInsert = true;
                }
            }

            private void select() {
                out();
                indent++;
                newline();
                parenCounts.addLast(parensSinceSelect);
                afterByOrFromOrSelects.addLast(afterByOrSetOrFromOrSelect);
                parensSinceSelect = 0;
                afterByOrSetOrFromOrSelect = true;
            }

            private void out() {
                result.append(token);
            }

            private void endNewClause() {
                if (!afterBeginBeforeEnd) {
                    indent--;
                    if (afterOn) {
                        indent--;
                        afterOn = false;
                    }
                    newline();
                }
                out();
                if (!"union".equals(lcToken)) {
                    indent++;
                }
                newline();
                afterBeginBeforeEnd = false;
                afterByOrSetOrFromOrSelect = "by".equals(lcToken)
                        || "set".equals(lcToken)
                        || "from".equals(lcToken);
            }

            private void beginNewClause() {
                if (!afterBeginBeforeEnd) {
                    if (afterOn) {
                        indent--;
                        afterOn = false;
                    }
                    indent--;
                    newline();
                }
                out();
                beginLine = false;
                afterBeginBeforeEnd = true;
            }

            private void values() {
                indent--;
                newline();
                out();
                indent++;
                newline();
                afterValues = true;
            }

            private void closeParen() {
                parensSinceSelect--;
                if (parensSinceSelect < 0) {
                    indent--;
                    parensSinceSelect = parenCounts.removeLast();
                    afterByOrSetOrFromOrSelect = afterByOrFromOrSelects.removeLast();
                }
                if (inFunction > 0) {
                    inFunction--;
                    out();
                } else {
                    if (!afterByOrSetOrFromOrSelect) {
                        indent--;
                        newline();
                    }
                    out();
                }
                beginLine = false;
            }

            private void openParen() {
                if (isFunctionName(lastToken) || inFunction > 0) {
                    inFunction++;
                }
                beginLine = false;
                if (inFunction > 0) {
                    out();
                } else {
                    out();
                    if (!afterByOrSetOrFromOrSelect) {
                        indent++;
                        newline();
                        beginLine = true;
                    }
                }
                parensSinceSelect++;
            }

            private static boolean isFunctionName(String tok) {
                final char begin = tok.charAt(0);
                final boolean isIdentifier = Character.isJavaIdentifierStart(begin) || '"' == begin;
                return isIdentifier &&
                        !LOGICAL.contains(tok) &&
                        !END_CLAUSES.contains(tok) &&
                        !QUANTIFIERS.contains(tok) &&
                        !DML.contains(tok) &&
                        !MISC.contains(tok);
            }

            private static boolean isWhitespace(String token) {
                return WHITESPACE.contains(token);
            }

            private void newline() {
                result.append("\n");
                for (int i = 0; i < indent; i++) {
                    result.append(INDENT_STRING);
                }
                beginLine = true;
            }
        }
    }
}

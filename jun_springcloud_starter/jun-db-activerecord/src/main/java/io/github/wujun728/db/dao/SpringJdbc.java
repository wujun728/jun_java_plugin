package io.github.wujun728.db.dao;

import java.math.BigDecimal;
import java.sql.*;
import java.util.*;
import java.util.Date;

import io.github.wujun728.db.Page;
import io.github.wujun728.db.bean.PageResult;
import org.apache.commons.lang.ArrayUtils;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.ConnectionCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterUtils;
import org.springframework.stereotype.Component;

public class SpringJdbc implements ISpringJdbc {

    private JdbcTemplate jdbcTemplate;

    private static final int BATCH_PAGE_SIZE=2000;

    private static final Map<Integer, String> paramSymbol = new HashMap<Integer, String>();

    @Override
    public String insertForId(String sql) throws Exception {
        return this.insertForId(sql, null);
    }

    @Override
    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public NamedParameterJdbcTemplate getNamedParameterJdbcTemplate() {
        return new NamedParameterJdbcTemplate(jdbcTemplate);
    }

    @Override
    public String insertForId(final String sql, final Object[] args) throws Exception {
        String id = jdbcTemplate.execute((ConnectionCallback<String>) con -> {
            PreparedStatement ps = (PreparedStatement) con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);// 传入参数：Statement.RETURN_GENERATED_KEYS
            if (args != null) {
                for (int i = 1; i <= args.length; i++) {
                    Object arg = args[i - 1];
                    setParamater(ps, i, arg);
                }
            }
            ps.executeUpdate();// 执行sql
            ResultSet rs = ps.getGeneratedKeys();// 获取结果
            if (rs.next()) {
                return rs.getString(1);// 取得ID
            } else {
                return null;
            }
        });
        return id;
    }

    @Override
    public int batchInsert(String sql, List<Object[]> batchArgs) throws Exception {
        return this.batchInsert(sql, batchArgs, null, 0);
    }

    @Override
    public int batchInsert(String sql, List<Object[]> batchArgs, int batchPageSize) throws Exception {
        return this.batchInsert(sql, batchArgs, null, batchPageSize);
    }

    @Override
    public int batchInsert(String sql, List<Object[]> batchArgs, int[] types) throws Exception {
        return this.batchInsert(sql, batchArgs, types, 0);
    }

    @Override
    public int batchInsert(String sql, List<Object[]> batchArgs, int[] types, int batchPageSize) throws Exception {
        if (batchPageSize <= 0) {
            batchPageSize = BATCH_PAGE_SIZE;
        }
        sql = sql.trim();
        if (sql.endsWith(";")) {
            sql = sql.substring(0, sql.length() - 1);
        }
        int index = sql.toLowerCase().indexOf("values");
        if (index == -1) {
            throw new RuntimeException("此sql语句不能执行批量插入操作");
        }
        index = sql.indexOf("(", index);// 查找values后第一个左括号位置
        String sqlLeft = sql.substring(0, index);
        String sqlRight = sql.substring(index);
        int batchSize = batchArgs.size();
        int resultSize = 0;
        // 插入完整页的数据
        int wholeLength = batchSize / batchPageSize;
        StringBuffer sqlJoin = new StringBuffer();
        if (wholeLength > 0) {
            sqlJoin.append(sqlLeft);
            sqlJoin.append(sqlRight);
            for (int i = 0; i < batchPageSize - 1; i++) {
                sqlJoin.append(",").append(sqlRight);
            }
            List<Object> params = new ArrayList<Object>(batchPageSize * batchArgs.get(0).length);
            int[] argTypes = null;
            if (types != null) {
                argTypes = new int[batchPageSize * types.length];
                int length = types.length;
                for (int i = 0; i < batchPageSize; i++) {
                    for (int j = 0; j < types.length; j++) {
                        argTypes[i * length + j] = types[j];
                    }
                }
            }
            for (int i = 0; i < wholeLength; i++) {
                params.clear();
                for (int j = 0; j < batchPageSize; j++) {
                    Object[] args = batchArgs.get(i * batchPageSize + j);
                    for (Object arg : args) {
                        params.add(arg);
                    }
                }
                if (argTypes == null) {
                    resultSize += jdbcTemplate.update(sqlJoin.toString(), params.toArray());
                } else {
                    resultSize += jdbcTemplate.update(sqlJoin.toString(), params.toArray(), argTypes);
                }
            }
        }
        // 插入剩余的数据
        int surplusSize = batchSize % batchPageSize;
        if (surplusSize > 0) {
            sqlJoin.setLength(0);
            sqlJoin.append(sqlLeft);
            sqlJoin.append(sqlRight);
            for (int i = 0; i < surplusSize - 1; i++) {
                sqlJoin.append(",").append(sqlRight);
            }
            List<Object> params = new ArrayList<Object>(surplusSize * batchArgs.get(0).length);
            int[] argTypes = null;
            if (types != null) {
                argTypes = new int[surplusSize * types.length];
                int length = types.length;
                for (int i = 0; i < surplusSize; i++) {
                    for (int j = 0; j < types.length; j++) {
                        argTypes[i * length + j] = types[j];
                    }
                }
            }
            for (int j = 0; j < surplusSize; j++) {
                Object[] args = batchArgs.get(wholeLength * batchPageSize + j);
                for (Object arg : args) {
                    params.add(arg);
                }
            }
            if (argTypes == null) {
                resultSize += jdbcTemplate.update(sqlJoin.toString(), params.toArray());
            } else {
                resultSize += jdbcTemplate.update(sqlJoin.toString(), params.toArray(), argTypes);
            }
        }
        return resultSize;
    }

    @Override
    public void batchUpdate(String sql, List<Object[]> batchArgs) throws Exception {
        this.batchUpdate(sql, batchArgs, null, 0);
    }

    @Override
    public void batchUpdate(String sql, List<Object[]> batchArgs, int batchPageSize) throws Exception {
        this.batchUpdate(sql, batchArgs, null, batchPageSize);
    }

    @Override
    public void batchUpdate(String sql, List<Object[]> batchArgs, int[] types, int batchPageSize) throws Exception {
        if (batchPageSize <= 0) {
            batchPageSize = BATCH_PAGE_SIZE;
        }
        sql = sql.trim();
        if (sql.endsWith(";")) {
            sql = sql.substring(0, sql.length() - 1);
        }
        int batchSize = batchArgs.size();
        // 插入完整页的数据
        int wholeLength = batchSize / batchPageSize;
        if (wholeLength > 0) {
            List<Object[]> params = new ArrayList<Object[]>();
            int[] argTypes = null;
            if (types != null) {
                argTypes = new int[batchPageSize * types.length];
                int length = types.length;
                for (int i = 0; i < batchPageSize; i++) {
                    for (int j = 0; j < types.length; j++) {
                        argTypes[i * length + j] = types[j];
                    }
                }
            }
            for (int i = 0; i < wholeLength; i++) {
                params.clear();
                for (int j = 0; j < batchPageSize; j++) {
                    Object[] args = batchArgs.get(i * batchPageSize + j);
                    params.add(args);
                }
                if (argTypes == null) {
                    jdbcTemplate.batchUpdate(sql, params);
                } else {
                    jdbcTemplate.batchUpdate(sql, params, argTypes);
                }
            }
        }
        // 插入剩余的数据
        int surplusSize = batchSize % batchPageSize;
        if (surplusSize > 0) {
            List<Object[]> params = new ArrayList<Object[]>();
            int[] argTypes = null;
            if (types != null) {
                argTypes = new int[surplusSize * types.length];
                int length = types.length;
                for (int i = 0; i < surplusSize; i++) {
                    for (int j = 0; j < types.length; j++) {
                        argTypes[i * length + j] = types[j];
                    }
                }
            }
            for (int j = 0; j < surplusSize; j++) {
                Object[] args = batchArgs.get(wholeLength * batchPageSize + j);
                params.add(args);
            }
            if (argTypes == null) {
                jdbcTemplate.batchUpdate(sql.toString(), params);
            } else {
                jdbcTemplate.update(sql.toString(), params, argTypes);
            }
        }
    }


    @Override
    public void batchUpdate(String sql, List<Object[]> batchArgs, int[] types) throws Exception {
        this.batchUpdate(sql, batchArgs, types, 0);
    }


    @Override
    public int update(String sql, Object[] args) throws Exception {
        Object[] obj = this.changeMessage(sql, args);
        return jdbcTemplate.update((String) obj[0], (Object[]) obj[1], (int[]) obj[2]);
    }

    @Override
    public <T> T query(String sql, ResultSetExtractor<T> rse) throws Exception {
        return jdbcTemplate.query(sql, rse);
    }

    @Override
    public <T> T query(String sql, Object[] args, ResultSetExtractor<T> rse) throws Exception {
        Object[] obj = this.changeMessage(sql, args);
        return jdbcTemplate.query((String) obj[0], (Object[]) obj[1], (int[]) obj[2], rse);
    }

    @Override
    public <T> List<T> query(String sql, RowMapper<T> rowMapper) throws Exception {
        return jdbcTemplate.query(sql, rowMapper);
    }

    @Override
    public <T> List<T> query(String sql, Object[] args, RowMapper<T> rowMapper) throws Exception {
        Object[] obj = this.changeMessage(sql, args);
        return jdbcTemplate.query((String) obj[0], (Object[]) obj[1], (int[]) obj[2], rowMapper);
    }

    @Override
    public <T> List<T> query(String sql, Class<T> elementType) throws Exception {
        return jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(elementType));
    }

    @Override
    public <T> List<T> query(String sql, Object[] args, Class<T> elementType) throws Exception {
        Object[] obj = this.changeMessage(sql, args);
        return jdbcTemplate.query((String) obj[0], (Object[]) obj[1], (int[]) obj[2],
                BeanPropertyRowMapper.newInstance(elementType));
    }

    @Override
    public <T> T queryForObject(String sql, Class<T> requiredType) throws Exception {
        return jdbcTemplate.queryForObject(sql, requiredType);
    }

    @Override
    public <T> T queryForObject(String sql, Object[] args, Class<T> requiredType) throws Exception {
        List<T> results = null;
        if (args != null && args.length > 0) {
            Object[] obj = this.changeMessage(sql, args);
            sql = (String) obj[0];
            args = (Object[]) obj[1];
            int[] argTypes = (int[]) obj[2];
            results = jdbcTemplate.query(sql, args, argTypes,  BeanPropertyRowMapper.newInstance(requiredType));
        } else {
            results = jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(requiredType));
        }
        return DataAccessUtils.singleResult(results);
    }

    @Override
    public <T> List<T> query(String sql, Map<String, Object> paramMap, Class<T> elementType) throws Exception {
        Object[] args = NamedParameterUtils.buildValueArray(sql,paramMap);
        sql = NamedParameterUtils.parseSqlStatementIntoString(sql);
        return this.query(sql,args,elementType);
    }

    @Override
    public List<Map<String, Object>> queryForList(String sql, Object[] args) throws Exception {
        Object[] obj = this.changeMessage(sql, args);
        return jdbcTemplate.queryForList((String) obj[0], (Object[]) obj[1]);
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    public <T> PageResult<T> queryForPageResult(Page page, String sql, Object[] args, Class<T> requiredType) throws Exception {
        Object[] obj = this.changeMessage(sql, args);
        String baseSql = (String) obj[0];
        Object[] params = (Object[]) obj[1];
        String pageSql = "SELECT SQL_CALC_FOUND_ROWS * FROM (" + baseSql + ") temp LIMIT ?,?";
        params = ArrayUtils.add(params, page.getPageSize());
        params = ArrayUtils.add(params, page.getPageSize());
        List<T> paged = jdbcTemplate.query(pageSql, params, BeanPropertyRowMapper.newInstance(requiredType));
        String countSql = "SELECT FOUND_ROWS() ";
        int count = jdbcTemplate.queryForObject(countSql, Integer.class);
        return new PageResult(paged, count);
    }

    @Override
    public <T> PageResult<T> queryForPageResult(Page page, String sql, Map<String, Object> paramMap, Class<T> requiredType) throws Exception {
        Object[] args = NamedParameterUtils.buildValueArray(sql,paramMap);
        sql = NamedParameterUtils.parseSqlStatementIntoString(sql);
        return this.queryForPageResult(page, sql, args, requiredType);
    }

    private Object[] changeMessage(String sql, Object[] args) throws Exception {
        List<Object> params = new ArrayList<Object>();
        List<Integer> types = new ArrayList<Integer>();
        List<Object[]> paramList = new ArrayList<Object[]>();
        for (int i = 0; i < args.length; i++) {
            Object arg = args[i];
            if (arg.getClass().isArray()) {
                Object[] array = (Object[]) arg;
                if (array.length == 0) {
                    throw new RuntimeException("Array param can not be empty!");
                }
                Integer type = null;
                for (Object obj : array) {
                    if (type == null) {
                        type = this.getTypes(obj);
                    }
                    params.add(obj);
                    types.add(type);
                }
                if (array.length > 1) {
                    paramList.add(new Object[]{i, getParamSymbol(array.length)});
                }
            } else if (Collection.class.isAssignableFrom(arg.getClass())) {
                Collection<?> list = (Collection<?>) arg;
                if (list.size() == 0) {
                    throw new RuntimeException("Collection param can not be empty!");
                }
                Integer type = null;
                for (Object obj : list) {
                    if (type == null) {
                        type = this.getTypes(obj);
                    }
                    params.add(obj);
                    types.add(type);
                }
                if (list.size() > 1) {
                    paramList.add(new Object[]{i, getParamSymbol(list.size())});
                }
            } else {
                params.add(arg);
                types.add(this.getTypes(arg));
            }
        }
        // 根据序号替换单个?为多个数组?
        if (!paramList.isEmpty()) {
            StringBuffer sqlb = new StringBuffer();
            String sqls = sql.toString();
            int count = -1;
            int index = -1;
            for (Object[] obj : paramList) {
                int paramIndex = (Integer) obj[0];
                while (true) {
                    count++;
                    index = sqls.indexOf("?", index + 1);
                    if (index == -1) {
                        throw new RuntimeException("Paramater size > '?' size!");
                    }
                    if (paramIndex == count) {
                        sqlb.append(sqls.substring(0, index)).append((String) obj[1]);
                        sqls = sqls.substring(index + 1);
                        index = -1;
                        break;
                    }
                }
            }
            sqlb.append(sqls);
            sql = sqlb.toString();
        }

        int[] tys = new int[types.size()];
        for (int i = 0; i < types.size(); i++) {
            tys[i] = types.get(i);
        }
        return new Object[]{sql, params.toArray(), tys};
    }

    private int getTypes(Object arg) {
        if (String.class.equals(arg.getClass())) {
            return Types.VARCHAR;
        } else if (int.class.equals(arg.getClass()) || Integer.class.equals(arg.getClass())) {
            return Types.INTEGER;
        } else if (double.class.equals(arg.getClass()) || Double.class.equals(arg.getClass())) {
            return Types.DOUBLE;
        } else if (Date.class.isAssignableFrom(arg.getClass())) {
            return Types.TIMESTAMP;
        } else if (long.class.equals(arg.getClass()) || Long.class.equals(arg.getClass())) {
            return Types.BIGINT;
        } else if (float.class.equals(arg.getClass()) || Float.class.equals(arg.getClass())) {
            return Types.FLOAT;
        } else if (boolean.class.equals(arg.getClass()) || Boolean.class.equals(arg.getClass())) {
            return Types.BOOLEAN;
        } else if (short.class.equals(arg.getClass()) || Short.class.equals(arg.getClass())) {
            return Types.INTEGER;
        } else if (byte.class.equals(arg.getClass()) || Byte.class.equals(arg.getClass())) {
            return Types.INTEGER;
        } else if (BigDecimal.class.equals(arg.getClass())) {
            return Types.DECIMAL;
        } else {
            return Types.OTHER;
        }
    }

    private void setParamater(PreparedStatement ps, int i, Object arg) throws SQLException {
        if (String.class.equals(arg.getClass())) {
            ps.setString(i, (String) arg);
        } else if (int.class.equals(arg.getClass()) || Integer.class.equals(arg.getClass())) {
            ps.setInt(i, (Integer) arg);
        } else if (double.class.equals(arg.getClass()) || Double.class.equals(arg.getClass())) {
            ps.setDouble(i, (Double) arg);
        } else if (Date.class.isAssignableFrom(arg.getClass())) {
            ps.setTimestamp(i, new Timestamp(((Date) arg).getTime()));
        } else if (long.class.equals(arg.getClass()) || Long.class.equals(arg.getClass())) {
            ps.setLong(i, (Long) arg);
        } else if (float.class.equals(arg.getClass()) || Float.class.equals(arg.getClass())) {
            ps.setFloat(i, (Float) arg);
        } else if (boolean.class.equals(arg.getClass()) || Boolean.class.equals(arg.getClass())) {
            ps.setBoolean(i, (Boolean) arg);
        } else if (short.class.equals(arg.getClass()) || Short.class.equals(arg.getClass())) {
            ps.setShort(i, (Short) arg);
        } else if (byte.class.equals(arg.getClass()) || Byte.class.equals(arg.getClass())) {
            ps.setByte(i, (Byte) arg);
        } else if (BigDecimal.class.equals(arg.getClass())) {
            ps.setBigDecimal(i, (BigDecimal) arg);
        } else {
            throw new SQLException("参数" + i + "属于未知的参数类型！");
        }
    }

    private String getParamSymbol(int size) {
        String result = paramSymbol.get(size);
        if (result == null) {
            synchronized (paramSymbol) {
                StringBuffer sb = new StringBuffer("?");
                for (int i = 1; i < size; i++) {
                    sb.append(",?");
                }
                result = sb.toString();
                paramSymbol.put(size, result);
            }
        }
        return result;
    }
}

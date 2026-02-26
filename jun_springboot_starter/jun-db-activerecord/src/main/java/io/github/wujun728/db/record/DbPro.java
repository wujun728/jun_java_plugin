package io.github.wujun728.db.record;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.db.meta.MetaUtil;
import cn.hutool.db.meta.Table;
import cn.hutool.log.StaticLog;
import io.github.wujun728.db.record.dialect.*;
import io.github.wujun728.db.record.mapper.BatchSql;
import io.github.wujun728.db.utils.RecordUtil;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import javax.sql.DataSource;
import java.sql.*;
import java.util.Date;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import static io.github.wujun728.db.record.Db.main;

/**
 * DbPro - 核心数据库操作引擎
 * <p>
 * 支持三种操作模式：
 * 1. SQL模式 - 直接执行SQL语句
 * 2. Record模式 - 通过Record对象进行CRUD（JFinal风格）
 * 3. Bean模式 - 通过实体对象进行CRUD（支持JPA/MyBatis-Plus注解）
 * <p>
 * 通过Dialect策略模式支持多种数据库方言
 */
public class DbPro {

    private String configName;
    private DataSource dataSource;
    private JdbcTemplate jdbcTemplate;
    private TransactionTemplate transactionTemplate;
    private Dialect dialect;

    public static final int DEFAULT_FETCHSIZE = 32;
    static final Object[] NULL_PARA_ARRAY = new Object[0];

    // 缓存池
    public volatile static ConcurrentHashMap<String, DbPro> cache = new ConcurrentHashMap<>(32, 0.25F);
    public volatile static ConcurrentHashMap<String, DataSource> dataSourceMap = new ConcurrentHashMap<>(32);
    private static final Map<String, String> TABLE_PK_MAP = new HashMap<>();

    public DbPro() {
    }

    // ==================== 初始化 ====================

    static DbPro init(String configName, DataSource dataSource) {
        return init(configName, dataSource, false);
    }

    static DbPro init(String configName, DataSource dataSource, Boolean force) {
        DbPro dbPro = DbPro.cache.get(configName);
        if (dbPro == null || force) {
            dbPro = new DbPro();
            JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
            TransactionTemplate transactionTemplate = new TransactionTemplate();
            DataSourceTransactionManager transactionManager = new DataSourceTransactionManager();
            transactionManager.setDataSource(dataSource);
            transactionTemplate.setTransactionManager(transactionManager);
            dbPro.configName = configName;
            dbPro.jdbcTemplate = jdbcTemplate;
            dbPro.transactionTemplate = transactionTemplate;
            dbPro.dataSource = dataSource;
            dbPro.dialect = detectDialect(dataSource);
            dataSourceMap.put(configName, dataSource);
            registerTablePrimaryKeys(dataSource);
            DbPro.cache.put(configName, dbPro);
        }
        return dbPro;
    }

    private static Dialect detectDialect(DataSource dataSource) {
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            String dbName = connection.getMetaData().getDatabaseProductName().toLowerCase();
            if (dbName.contains("oracle")) {
                return new OracleDialect();
            } else if (dbName.contains("mysql") || dbName.contains("mariadb")) {
                return new MysqlDialect();
            } else if (dbName.contains("sqlite")) {
                return new Sqlite3Dialect();
            } else if (dbName.contains("postgre")) {
                return new PostgreSqlDialect();
            } else if (dbName.contains("h2")) {
                return new MysqlDialect(); // H2兼容MySQL语法
            } else {
                return new MysqlDialect(); // 默认使用MySQL方言
            }
        } catch (SQLException e) {
            throw new RuntimeException("获取数据库连接失败", e);
        } finally {
            if (connection != null) {
                try { connection.close(); } catch (SQLException e) { /* ignore */ }
            }
        }
    }

    private static void registerTablePrimaryKeys(DataSource dataSource) {
        try {
            List<String> tables = MetaUtil.getTables(dataSource);
            tables.forEach(tableName -> {
                try {
                    Table table = MetaUtil.getTableMeta(dataSource, tableName);
                    String pkStr = String.join(",", table.getPkNames());
                    TABLE_PK_MAP.put(tableName, pkStr);
                } catch (Exception e) {
                    // 跳过无法获取元数据的表
                }
            });
        } catch (Exception e) {
            StaticLog.warn("获取表元数据失败: " + e.getMessage());
        }
    }

    // ==================== Getter ====================

    public DataSource getDataSource() {
        return this.dataSource;
    }

    public JdbcTemplate getJdbcTemplate() {
        return this.jdbcTemplate;
    }

    public Dialect getDialectInstance() {
        return this.dialect;
    }

    public static String getPkNames(String tableName) {
        if (CollectionUtil.isEmpty(DbPro.TABLE_PK_MAP)) {
            DataSource ds = DbPro.dataSourceMap.get(main);
            if (ds != null) {
                DbPro.registerTablePrimaryKeys(ds);
            }
        }
        String primaryKey = DbPro.TABLE_PK_MAP.get(tableName);
        if (StrUtil.isEmpty(primaryKey)) {
            primaryKey = "id";
        }
        return primaryKey;
    }

    // ==================== SQL模式 - 基础操作 ====================

    /**
     * 执行insert/update/delete语句
     */
    public int execute(String sql) {
        return execute(sql, null);
    }

    /**
     * 执行insert/update/delete语句（带参数）
     */
    public int execute(String sql, Object[] objects) {
        StaticLog.info(sql);
        try {
            if (objects != null && objects.length > 0) {
                return jdbcTemplate.update(sql, objects);
            } else {
                return jdbcTemplate.update(sql);
            }
        } catch (Exception e) {
            StaticLog.error(e.getMessage() + "\n" + RecordUtil.formatSql(sql, objects));
            throw new DbException(e.getMessage() + "\n" + RecordUtil.formatSql(sql, objects));
        }
    }

    /**
     * 执行插入并返回自增主键
     */
    public long insert(final String sql, final Object[] objects) {
        StaticLog.info(sql);
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                if (objects != null && objects.length > 0) {
                    for (int i = 1; i <= objects.length; i++) {
                        ps.setObject(i, objects[i - 1]);
                    }
                }
                return ps;
            }
        }, keyHolder);
        // Handle case where multiple keys are returned (e.g., H2 with DEFAULT columns)
        List<Map<String, Object>> keyList = keyHolder.getKeyList();
        if (keyList != null && !keyList.isEmpty()) {
            Map<String, Object> keys = keyList.get(0);
            // Return the first numeric key value
            for (Object val : keys.values()) {
                if (val instanceof Number) {
                    return ((Number) val).longValue();
                }
            }
        }
        return keyHolder.getKey().longValue();
    }

    /**
     * 批量执行SQL
     */
    public int batchExecute(String sql, final List<Object[]> objectsList) {
        StaticLog.info(sql);
        try {
            jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement ps, int i) throws SQLException {
                    Object[] objects = objectsList.get(i);
                    for (int j = 0; j < objects.length; j++) {
                        ps.setObject(j + 1, objects[j]);
                    }
                }

                @Override
                public int getBatchSize() {
                    return objectsList.size();
                }
            });
            return 1;
        } catch (DataAccessException e) {
            StaticLog.error(e.getMessage());
            return 0;
        }
    }

    // ==================== SQL模式 - 查询操作 ====================

    /**
     * 查询返回Map列表
     */
    public List<Map<String, Object>> queryList(String sql) {
        return queryList(sql, null);
    }

    public List<Map<String, Object>> queryList(String sql, Object[] params) {
        StaticLog.info(sql);
        try {
            jdbcTemplate.setFetchSize(DEFAULT_FETCHSIZE);
            if (params != null && params.length > 0) {
                return jdbcTemplate.queryForList(sql, params);
            } else {
                return jdbcTemplate.queryForList(sql);
            }
        } catch (Exception e) {
            StaticLog.error(e.getMessage() + "\n" + RecordUtil.formatSql(sql, params));
            throw new DbException(e.getMessage() + "\n" + RecordUtil.formatSql(sql, params));
        }
    }

    /**
     * 查询返回单条Map
     */
    public Map<String, Object> queryForMap(String sql, Object... objects) {
        StaticLog.info(sql);
        try {
            if (objects != null && objects.length > 0) {
                return jdbcTemplate.queryForMap(sql, objects);
            } else {
                return jdbcTemplate.queryForMap(sql);
            }
        } catch (EmptyResultDataAccessException e) {
            return new HashMap<>();
        } catch (Exception e) {
            StaticLog.error(e.getMessage() + "\n" + RecordUtil.formatSql(sql, objects));
            throw new DbException(e.getMessage());
        }
    }

    /**
     * 查询返回int值
     */
    public int queryForInt(String sql, Object... objects) {
        StaticLog.info(sql);
        try {
            if (objects != null && objects.length > 0) {
                return jdbcTemplate.queryForObject(sql, objects, Integer.class);
            } else {
                return jdbcTemplate.queryForObject(sql, Integer.class);
            }
        } catch (Exception e) {
            StaticLog.error(e.getMessage() + "\n" + RecordUtil.formatSql(sql, objects));
            throw new DbException(e.getMessage());
        }
    }

    /**
     * 查询返回long值
     */
    public long queryForLong(String sql, Object... objects) {
        StaticLog.info(sql);
        try {
            if (objects != null && objects.length > 0) {
                return jdbcTemplate.queryForObject(sql, objects, Long.class);
            } else {
                return jdbcTemplate.queryForObject(sql, Long.class);
            }
        } catch (Exception e) {
            StaticLog.error(e.getMessage() + "\n" + RecordUtil.formatSql(sql, objects));
            throw new DbException(e.getMessage());
        }
    }

    /**
     * 查询返回字符串
     */
    public String queryForString(String sql, Object... objects) {
        StaticLog.info(sql);
        try {
            if (objects != null && objects.length > 0) {
                return jdbcTemplate.queryForObject(sql, objects, String.class);
            } else {
                return jdbcTemplate.queryForObject(sql, String.class);
            }
        } catch (EmptyResultDataAccessException e) {
            return "";
        } catch (Exception e) {
            StaticLog.error(e.getMessage() + "\n" + RecordUtil.formatSql(sql, objects));
            throw new DbException(e.getMessage());
        }
    }

    /**
     * 查询返回日期
     */
    public Date queryForDate(String sql, Object... objects) {
        StaticLog.info(sql);
        try {
            if (objects != null && objects.length > 0) {
                return jdbcTemplate.queryForObject(sql, objects, Date.class);
            } else {
                return jdbcTemplate.queryForObject(sql, Date.class);
            }
        } catch (EmptyResultDataAccessException e) {
            return null;
        } catch (Exception e) {
            StaticLog.error(e.getMessage() + "\n" + RecordUtil.formatSql(sql, objects));
            throw new DbException(e.getMessage());
        }
    }

    /**
     * 查询返回第一条结果
     */
    public <T> T queryFirst(String sql, Object... paras) {
        List<Map<String, Object>> result = queryList(sql, paras);
        if (result != null && !result.isEmpty()) {
            Map<String, Object> first = result.get(0);
            if (first.size() == 1) {
                return (T) first.values().iterator().next();
            }
            return (T) first;
        }
        return null;
    }

    /**
     * 查询记录总数
     */
    public int count(String sql, Object... params) {
        try {
            return queryForInt(sql, params);
        } catch (Exception e) {
            throw new DbException(e, sql);
        }
    }

    // ==================== Record模式 - CRUD ====================

    /**
     * 通过SQL查询返回Record列表
     */
    public List<Record> find(String sql, Object... paras) {
        List<Map<String, Object>> results = queryList(sql, paras);
        return RecordUtil.mapToRecords(results);
    }

    public List<Record> find(String sql) {
        return find(sql, NULL_PARA_ARRAY);
    }

    /**
     * 查询表的所有记录
     */
    public List<Record> findAll(String tableName) {
        String sql = dialect.forFindAll(tableName);
        return find(sql, NULL_PARA_ARRAY);
    }

    /**
     * 查询第一条Record
     */
    public Record findFirst(String sql, Object... paras) {
        List<Record> result = find(sql, paras);
        return result.size() > 0 ? result.get(0) : null;
    }

    public Record findFirst(String sql) {
        return findFirst(sql, NULL_PARA_ARRAY);
    }

    /**
     * 通过主键查询Record（自动获取主键名）
     */
    public Record findById(String tableName, Object idValue) {
        String defaultPrimaryKey = getPkNames(tableName);
        return findByIds(tableName, defaultPrimaryKey, idValue);
    }

    public Record findById(String tableName, String primaryKey, Object idValue) {
        return findByIds(tableName, primaryKey, idValue);
    }

    /**
     * 通过主键查询Record（支持复合主键）
     */
    public Record findByIds(String tableName, String primaryKey, Object... idValues) {
        String[] pKeys = primaryKey.split(",");
        if (pKeys.length != idValues.length) {
            throw new IllegalArgumentException("primary key number must equals id value number");
        }
        String sql = dialect.forDbFindById(tableName, pKeys);
        List<Record> result = find(sql, idValues);
        if (CollectionUtil.isEmpty(result)) {
            return null;
        }
        return result.get(0);
    }

    /**
     * 通过列值查询Record列表
     */
    public List<Record> findByColumnValueRecords(String tableName, String columnNames, Object... columnValues) {
        String[] pKeys = columnNames.split(",");
        if (pKeys.length != columnValues.length) {
            throw new IllegalArgumentException("column name number must equals column value number");
        }
        String sql = dialect.forDbFindById(tableName, pKeys);
        try {
            List<Map<String, Object>> resultMap = queryList(sql, columnValues);
            return RecordUtil.mapToRecords(resultMap);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    /**
     * 保存Record
     */
    public boolean save(String tableName, String primaryKey, Record record) {
        String[] pKeys = primaryKey.split(",");
        List<Object> paras = new ArrayList<>();
        StringBuilder sql = new StringBuilder();
        dialect.forDbSave(tableName, pKeys, record, sql, paras);
        try {
            return execute(sql.toString(), paras.toArray()) > 0;
        } catch (DuplicateKeyException e) {
            throw new RuntimeException("主键冲突，保存失败！");
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean save(String tableName, Record record) {
        String primaryKey = getPkNames(tableName);
        return save(tableName, primaryKey, record);
    }

    /**
     * 更新Record
     */
    public boolean update(String tableName, String primaryKey, Record record) {
        String[] pKeys = primaryKey.split(",");
        Object[] ids = new Object[pKeys.length];
        for (int i = 0; i < pKeys.length; i++) {
            ids[i] = getRecordValue(record, pKeys[i].trim());
            if (ids[i] == null) {
                throw new RuntimeException("You can't update record without Primary Key, " + pKeys[i] + " can not be null.");
            }
        }
        StringBuilder sql = new StringBuilder();
        List<Object> paras = new ArrayList<>();
        dialect.forDbUpdate(tableName, pKeys, ids, record, sql, paras);
        if (paras.size() <= 1) {
            return false; // 只有主键无需更新
        }
        return execute(sql.toString(), paras.toArray()) >= 1;
    }

    public boolean update(String tableName, Record record) {
        String primaryKey = getPkNames(tableName);
        return update(tableName, primaryKey, record);
    }

    /**
     * 删除Record
     */
    public boolean delete(String tableName, String primaryKey, Record record) {
        String[] pKeys = primaryKey.split(",");
        dialect.trimPrimaryKeys(pKeys);
        Object[] idValue = new Object[pKeys.length];
        for (int i = 0; i < pKeys.length; i++) {
            idValue[i] = getRecordValue(record, pKeys[i]);
            if (idValue[i] == null) {
                throw new IllegalArgumentException("The value of primary key \"" + pKeys[i] + "\" can not be null in record object");
            }
        }
        return deleteById(tableName, primaryKey, idValue);
    }

    public boolean delete(String tableName, Record record) {
        String primaryKey = getPkNames(tableName);
        return delete(tableName, primaryKey, record);
    }

    /**
     * 通过主键删除（自动获取主键名）
     */
    public boolean deleteById(String tableName, Object idValue) {
        String primaryKey = getPkNames(tableName);
        return deleteById(tableName, primaryKey, idValue);
    }

    /**
     * 通过主键删除（支持复合主键）
     */
    public boolean deleteById(String tableName, String primaryKey, Object... idValues) {
        String[] pKeys = primaryKey.split(",");
        if (pKeys.length != idValues.length) {
            throw new IllegalArgumentException("primary key number must equals id value number");
        }
        String sql = dialect.forDbDeleteById(tableName, pKeys);
        return execute(sql, idValues) >= 1;
    }

    // ==================== Record模式 - 分页 ====================

    /**
     * JFinal风格分页查询
     */
    public Page<Record> paginate(int pageNumber, int pageSize, String select, String sqlExceptSelect) {
        return doPaginate(pageNumber, pageSize, null, select, sqlExceptSelect, NULL_PARA_ARRAY);
    }

    public Page<Record> paginate(int pageNumber, int pageSize, String select, String sqlExceptSelect, Object... paras) {
        return doPaginate(pageNumber, pageSize, null, select, sqlExceptSelect, paras);
    }

    public Page<Record> paginate(int pageNumber, int pageSize, boolean isGroupBySql, String select, String sqlExceptSelect, Object... paras) {
        return doPaginate(pageNumber, pageSize, isGroupBySql, select, sqlExceptSelect, paras);
    }

    private Page<Record> doPaginate(int pageNumber, int pageSize, Boolean isGroupBySql, String select, String sqlExceptSelect, Object... paras) {
        try {
            String totalRowSql = dialect.forPaginateTotalRow(select, sqlExceptSelect, null);
            StringBuilder findSql = new StringBuilder();
            findSql.append(select).append(' ').append(sqlExceptSelect);
            return doPaginateByFullSql(pageNumber, pageSize, isGroupBySql, totalRowSql, findSql, paras);
        } catch (Exception e) {
            throw new DbException(e);
        }
    }

    /**
     * 完整SQL分页查询
     */
    public Page<Record> paginateByFullSql(int pageNumber, int pageSize, String totalRowSql, String findSql, Object... paras) {
        return doPaginateByFullSql(pageNumber, pageSize, null, totalRowSql, new StringBuilder(findSql), paras);
    }

    public Page<Record> paginateByFullSql(int pageNumber, int pageSize, boolean isGroupBySql, String totalRowSql, String findSql, Object... paras) {
        return doPaginateByFullSql(pageNumber, pageSize, isGroupBySql, totalRowSql, new StringBuilder(findSql), paras);
    }

    private Page<Record> doPaginateByFullSql(int pageNumber, int pageSize, Boolean isGroupBySql, String totalRowSql, StringBuilder findSql, Object... paras) {
        if (pageNumber < 1 || pageSize < 1) {
            throw new DbException("pageNumber and pageSize must more than 0");
        }

        // 查询总数
        List<Map<String, Object>> result = queryList(totalRowSql, paras);
        int size = (result != null) ? result.size() : 0;
        if (isGroupBySql == null) {
            isGroupBySql = size > 1;
        }

        long totalRow;
        if (isGroupBySql) {
            totalRow = size;
        } else {
            if (size > 0) {
                Object firstVal = result.get(0).values().iterator().next();
                totalRow = (firstVal instanceof Number) ? ((Number) firstVal).longValue() : 0;
            } else {
                totalRow = 0;
            }
        }

        if (totalRow == 0) {
            return new Page<>(new ArrayList<>(0), pageNumber, pageSize, 0, 0);
        }

        int totalPage = (int) (totalRow / pageSize);
        if (totalRow % pageSize != 0) {
            totalPage++;
        }

        if (pageNumber > totalPage) {
            return new Page<>(new ArrayList<>(0), pageNumber, pageSize, totalPage, (int) totalRow);
        }

        // 查询分页数据
        String sql = dialect.forPaginate(pageNumber, pageSize, findSql);
        List<Record> list = find(sql, paras);
        return new Page<>(list, pageNumber, pageSize, totalPage, (int) totalRow);
    }

    // ==================== Bean模式 - CRUD ====================

    /**
     * 保存实体Bean（支持JPA @Table/@Column 和 MyBatis-Plus @TableName/@TableField注解）
     */
    public boolean saveBean(Object bean) {
        Record record = RecordUtil.beanToRecord(bean);
        String tableName = RecordUtil.getTableName(bean);
        return save(tableName, record);
    }

    /**
     * 更新实体Bean
     */
    public boolean updateBean(Object bean) {
        Record record = RecordUtil.beanToRecord(bean);
        String tableName = RecordUtil.getTableName(bean);
        return update(tableName, record);
    }

    /**
     * 删除实体Bean
     */
    public boolean deleteBean(Object bean) {
        Record record = RecordUtil.beanToRecord(bean);
        String tableName = RecordUtil.getTableName(bean);
        return delete(tableName, record);
    }

    /**
     * 通过SQL查询Bean列表
     */
    public <T> List<T> findBeanList(Class<T> clazz, String sql) {
        return findBeanList(clazz, sql, (Object[]) null);
    }

    public <T> List<T> findBeanList(Class<T> clazz, String sql, Object... params) {
        List<Map<String, Object>> datas = queryList(sql, params);
        return RecordUtil.mapToBeans(datas, clazz);
    }

    /**
     * 通过主键查询Bean
     */
    public <T> T findBeanById(Class<T> clazz, Object idValue) {
        String tableName = RecordUtil.getTableName(clazz);
        String primaryKeyStr = getPkNames(tableName);
        Record record = findByIds(tableName, primaryKeyStr, idValue);
        if (record == null) {
            return null;
        }
        return RecordUtil.recordToBean(record, clazz);
    }

    public <T> T findBeanById(Class<T> beanClass, String primaryKeys, Object... idValue) {
        String tableName = RecordUtil.getTableName(beanClass);
        String primaryKeyStr = primaryKeys;
        if (primaryKeys == null || StrUtil.isEmpty(primaryKeyStr)) {
            primaryKeyStr = getPkNames(tableName);
        }
        Record record = findByIds(tableName, primaryKeyStr, idValue);
        if (record == null) {
            return null;
        }
        return RecordUtil.recordToBean(record, beanClass);
    }

    /**
     * Bean分页查询
     */
    public <T> Page<T> findBeanPages(Class<T> beanClass, int page, int rows) {
        return findBeanPages(beanClass, page, rows, null);
    }

    @SuppressWarnings("unchecked")
    public <T> Page<T> findBeanPages(Class<T> beanClass, int page, int rows, String sql) {
        String tableName = RecordUtil.getTableName(beanClass);
        if (StrUtil.isEmpty(sql)) {
            sql = "select * from " + tableName;
        }
        String countSql = "select count(*) from (" + sql + ") _t";
        int totalRow = count(countSql);

        Page<T> pageVo = new Page<>();
        pageVo.setTotalRow(totalRow);
        pageVo.setPageSize(rows);
        pageVo.setPageNumber(page);
        pageVo.setTotalPage(totalRow % rows == 0 ? totalRow / rows : totalRow / rows + 1);

        String pageSql = dialect.forPaginate(page, rows, new StringBuilder(sql));
        List<Map<String, Object>> listData = queryList(pageSql);
        pageVo.setList(RecordUtil.mapToBeans(listData, beanClass));
        return pageVo;
    }

    /**
     * Map分页查询
     */
    @SuppressWarnings("unchecked")
    public Page<Map<String, Object>> queryMapPages(String sql, int page, int limit, Object[] params) {
        String countSql = "select count(*) from (" + sql + ") _t";
        int totalRow = count(countSql, params);

        Page<Map<String, Object>> pageVo = new Page<>();
        pageVo.setTotalRow(totalRow);
        pageVo.setPageNumber(page);
        pageVo.setPageSize(limit);
        pageVo.setTotalPage(totalRow % limit == 0 ? totalRow / limit : totalRow / limit + 1);

        String pageSql = dialect.forPaginate(page, limit, new StringBuilder(sql));
        pageVo.setList(queryList(pageSql, params));
        return pageVo;
    }

    // ==================== 事务支持 ====================

    /**
     * 批量SQL事务执行
     */
    public int doInTransaction(final BatchSql batchSql) {
        if (batchSql == null) {
            return 0;
        }
        try {
            transactionTemplate.execute(new TransactionCallbackWithoutResult() {
                public void doInTransactionWithoutResult(TransactionStatus status) {
                    List<Map<String, Object>> sqlList = batchSql.getSqlList();
                    for (Map<String, Object> sqlMap : sqlList) {
                        String sql = (String) sqlMap.get("sql");
                        Object[] objects = (Object[]) sqlMap.get("objects");
                        jdbcTemplate.update(sql, objects);
                    }
                }
            });
            return 1;
        } catch (Exception e) {
            StaticLog.error(e.getMessage());
            throw new DbException(e.getMessage());
        }
    }

    /**
     * IAtom事务执行（JFinal风格）
     */
    public boolean tx(IAtom atom) {
        Object res = transactionTemplate.execute(new TransactionCallback<Object>() {
            @Override
            public Object doInTransaction(TransactionStatus status) {
                try {
                    boolean result = atom.run();
                    if (!result) {
                        status.setRollbackOnly();
                    }
                    return result;
                } catch (SQLException e) {
                    status.setRollbackOnly();
                    throw new RuntimeException(e);
                }
            }
        });
        return Boolean.TRUE.equals(res);
    }

    // ==================== 内部工具方法 ====================

    /**
     * 从Record中获取值，支持大小写不敏感匹配
     * 解决数据库返回大写列名(如H2)与用户指定小写主键名不匹配的问题
     */
    private Object getRecordValue(Record record, String key) {
        Object val = record.get(key);
        if (val != null) {
            return val;
        }
        // 尝试大小写不敏感匹配
        Map<String, Object> columns = record.getColumns();
        for (Map.Entry<String, Object> entry : columns.entrySet()) {
            if (entry.getKey().equalsIgnoreCase(key)) {
                return entry.getValue();
            }
        }
        return null;
    }
}

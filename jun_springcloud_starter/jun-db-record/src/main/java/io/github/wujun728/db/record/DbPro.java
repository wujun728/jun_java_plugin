package io.github.wujun728.db.record;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.db.meta.MetaUtil;
import cn.hutool.db.meta.Table;
import cn.hutool.log.StaticLog;
import com.google.common.collect.Maps;
import io.github.wujun728.db.record.dialect.*;
import io.github.wujun728.db.record.mapper.BaseRowMapper;
import io.github.wujun728.db.record.mapper.BatchSql;
import io.github.wujun728.db.utils.RecordUtil;
import io.github.wujun728.db.utils.SqlContext;
import io.github.wujun728.db.utils.SqlUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import javax.sql.DataSource;
import java.sql.*;
import java.util.Date;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static io.github.wujun728.db.record.Db.main;

/**
 * Db 操作类，支持SQL模式、Record模式，Bean模式（JPA Bean模式、Mybatis Entity模式）
 */
public class DbPro{

    private String configName;
    private DataSource dataSource = null;

    private JdbcTemplate jdbcTemplate;
    private TransactionTemplate transactionTemplate;
    public final static int DEFAULT_FETCHSIZE = 32; //默认的fetchsize
    private Dialect dialect;

    public volatile static ConcurrentHashMap<String, DbPro> cache = new ConcurrentHashMap<>(32, 0.25F);
    public volatile static ConcurrentHashMap<String, DataSource> dataSourceMap = new ConcurrentHashMap<>(32);
    public volatile static ConcurrentHashMap<String, JdbcTemplate> dbTemplateMap = new ConcurrentHashMap<>(32);

    static final Object[] NULL_PARA_ARRAY = new Object[0];
    private static final Map<String, String> TABLE_PK_MAP = new HashMap<>();

    public DbPro() {
    }

    static DbPro init(String configName,DataSource dataSource) {
        DbPro dbPro = DbPro.cache.get(configName);
        if (dbPro == null) {
            dbPro = new DbPro();
            JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
            TransactionTemplate transactionTemplate = new TransactionTemplate();
            DataSourceTransactionManager transactionManager = new DataSourceTransactionManager();
            transactionManager.setDataSource(dataSource);
            transactionTemplate.setTransactionManager(transactionManager);
            dbPro.setConfigName(configName);
            dbPro.setJdbcTemplate(jdbcTemplate);
            dbPro.setTransactionTemplate(transactionTemplate);
            dbPro.setDataSource(dataSource);
            dbPro.setDialect(DbPro.getDialect(dbPro.getDataSource()));
            dataSourceMap.put(configName,dataSource);
            DbPro.registerRecord(dbPro.getDataSource());
            /*if("main".equalsIgnoreCase(configName)){
                Register.initReadWrite(dataSource,dataSource);
                Register.initThreadPool(100, 100, 1000); //初始化线程池 0为使用默认值
            }*/
            DbPro.cache.put(configName, dbPro);
        }
        return dbPro;
    }

    private static void registerRecord(DataSource dataSource) {
        List<String> tables = MetaUtil.getTables(dataSource);
        tables.forEach(tableName -> {
            Table table = MetaUtil.getTableMeta(dataSource, tableName);
            String pkStr = String.join(",", table.getPkNames());
            TABLE_PK_MAP.put(tableName, pkStr);// map不清，只做替换
        });
    }

    public DataSource getDataSource() {
        return this.dataSource;
    }

    private void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }
    private Dialect getDialect() {
        return dialect;
    }

    private void setDialect(Dialect dialect) {
        this.dialect = dialect;
    }

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    private void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private String getConfigName() {
        return configName;
    }

    private void setConfigName(String configName) {
        this.configName = configName;
    }

    private TransactionTemplate getTransactionTemplate() {
        return transactionTemplate;
    }

    private void setTransactionTemplate(TransactionTemplate transactionTemplate) {
        this.transactionTemplate = transactionTemplate;
    }

    private static Dialect getDialect(DataSource dataSource) {
        Connection connection  = null;
        try {
            connection = dataSource.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try {
            String dbName = connection.getMetaData().getDatabaseProductName();
            if (dbName.equalsIgnoreCase("Oracle")) {
                return new OracleDialect();
            } else if (dbName.equalsIgnoreCase("mysql")) {
                return new MysqlDialect();
            } else if (dbName.equalsIgnoreCase("sqlite")) {
                return new Sqlite3Dialect();
            } else if (dbName.equalsIgnoreCase("postgresql")) {
                return new PostgreSqlDialect();
            } else {
                return new AnsiSqlDialect();
            }
        } catch (SQLException e) {
            throw new RuntimeException("获取数据库连接失败");
        }finally {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static String getPkNames(String tableName) {
        if(CollectionUtil.isEmpty(DbPro.TABLE_PK_MAP)){
            DbPro.registerRecord(DbPro.dataSourceMap.get(main));
        }
        String primaryKey = DbPro.TABLE_PK_MAP.get(tableName);
        if(StrUtil.isEmpty(primaryKey)){
            primaryKey = "id";
            throw new RuntimeException("当前操作的表没有主键或表不存在，tableName="+tableName);
        }
        return primaryKey;
    }



    /********************************************************************************
     * 私有方法   66666666666666666666   begin
     ********************************************************************************/

    private int updateSqlContext(SqlContext sqlContext) {
        int result;
        String sql = null;
        try {
            sql = sqlContext.getSql();
            result = execute(sql, sqlContext.getParams());
        } catch (Exception e) {
            throw new DbException(e, sql);
        }
        return result;
    }
    private long insertSqlContext(SqlContext sqlContext) {
        long result;
        String sql = null;
        try {
            sql = sqlContext.getSql();
            result = insert(sql, sqlContext.getParams());
        } catch (Exception e) {
            throw new DbException(e, sql);
        }
        return result;
    }

    public long saveBean(Object bean) {
        return insertSqlContext(SqlUtils.getInsert(bean));
    }

    public Integer updateBean(Object bean) {
        return updateSqlContext(SqlUtils.getUpdate(bean));
    }

    public Integer deleteBean(Object bean) {
        return updateSqlContext(SqlUtils.getDelete(bean));
    }


    public List<Map<String, Object>> queryList(String sql) {
        return queryList(sql,null);
    }
    public List<Map<String, Object>> queryList(String sql, Object[] params) {
        return getJdbcTemplate().queryForList(sql,params);
    }


    /**
     * 返回一条记录
     * @param sql 传入的sql语句
     * @param objects 参数数组
     * @return map对象
     */
    public Map<String, Object> queryForMap(String sql, Object[] objects) {
        StaticLog.info(sql);
        Map<String, Object> map = null;
        try {
            if(objects != null && objects.length > 0) {
                map = jdbcTemplate.queryForMap(sql, objects);
            }
            else {
                map = jdbcTemplate.queryForMap(sql);
            }
        } catch (EmptyResultDataAccessException e) {
//			StaticLog.error("查询无记录："+SqlUtil.getSql(sql, objects));
        } catch (Exception e) {
            StaticLog.error(e.getMessage()+"\n"+ SqlUtil.getSql(sql, objects));
            e.printStackTrace();
            throw new DbException(e.getMessage());
        }
        if (map == null) {
            map = new HashMap<String, Object>();
        }
        return map;
    }

    /**
     * 返回一int数值
     * @param sql 传入的sql语句
     * @param objects 参数数组
     * @return -1:数据库异常
     */
    public int queryForInt(String sql, Object[] objects) {
        StaticLog.info(sql);
        int exc = -1;
        try {
            if(objects != null && objects.length > 0) {
                exc = jdbcTemplate.queryForObject(sql, objects, Integer.class);
            }
            else {
                exc = jdbcTemplate.queryForObject(sql, Integer.class);
            }
        } catch (Exception e) {
            exc = -1;
            StaticLog.error(e.getMessage()+"\n"+SqlUtil.getSql(sql, objects));
            e.printStackTrace();
            throw new DbException(e.getMessage());
        }
        return exc;
    }

    /**
     * 返还一个long数值
     * @param sql 传入的sql语句
     * @param objects  参数数组
     * @return -1:数据库异常
     */
    public long queryForLong(String sql, Object[] objects) {
        StaticLog.info(sql);
        long exc = -1;
        try {
            if(objects != null && objects.length > 0) {
                exc = jdbcTemplate.queryForObject(sql, objects, Long.class);
            }
            else {
                exc = jdbcTemplate.queryForObject(sql, Long.class);
            }
        } catch (Exception e) {
            exc = -1;
            StaticLog.error(e.getMessage()+"\n"+SqlUtil.getSql(sql, objects));
            e.printStackTrace();
            throw new DbException(e.getMessage());
        }
        return exc;
    }

    /**
     * 返回指定对象
     * @param sql 传入的sql语句
     * @param objects  参数数组
     * @param rowMapper  字段映射，针对实体类，例如 User、Student ...
     * @return 实体类对象
     */
    public <T> T queryForObject(String sql, Object[] objects, RowMapper<T> rowMapper) {
        StaticLog.info(sql);
        try {
            return jdbcTemplate.queryForObject(sql, objects, rowMapper);
        } catch (EmptyResultDataAccessException e) {
//			StaticLog.error("查询无记录："+SqlUtil.getSql(sql, objects));
            return null;
        } catch (Exception e) {
            StaticLog.error(e.getMessage()+"\n"+SqlUtil.getSql(sql, objects));
            e.printStackTrace();
            throw new DbException(e.getMessage());
            //return null;
        }
    }

    /**
     * 返回指定对象，针对Java Bean，非简单类型（String、Integer、Long），例如 User、Student ...
     * @param sql 传入的sql语句
     * @param objects  参数数组
     * @param clazz  实体类，通过@MapRow映射数据库字段
     * @return 实体类对象
     */
    public <T> T queryForObject(String sql, Object[] objects, Class<T> clazz) {
        StaticLog.info(sql);
        try {
            return jdbcTemplate.queryForObject(sql, objects, new BaseRowMapper<T>(clazz));
        } catch (EmptyResultDataAccessException e) {
//			StaticLog.error("查询无记录："+SqlUtil.getSql(sql, objects));
            return null;
        } catch (Exception e) {
            StaticLog.error(e.getMessage()+"\n"+SqlUtil.getSql(sql, objects));
            //return null;
            e.printStackTrace();
            throw new DbException(e.getMessage());
        }
    }

    /**
     * 返回一个字符串
     * @param sql 传入的sql语句
     * @param objects  参数数组，可以为null
     * @return 字符串，异常情况下为空串
     */
    public String queryForString(String sql, Object[] objects) {
        StaticLog.info(sql);
        String str = "";
        try {
            if(objects != null && objects.length > 0) {
                str = jdbcTemplate.queryForObject(sql, objects, String.class);
            }
            else {
                str = jdbcTemplate.queryForObject(sql, String.class);
            }
            return str;
        } catch (EmptyResultDataAccessException e) {
//			StaticLog.error("查询无记录："+SqlUtil.getSql(sql, objects));
            return "";
        } catch (Exception e) {
            StaticLog.error(e.getMessage()+"\n"+SqlUtil.getSql(sql, objects));
            //return "";
            e.printStackTrace();
            throw new DbException(e.getMessage());
        }
    }

    public Date queryForDate(String sql, Object[] objects) {
        StaticLog.info(sql);
        Date date = null;
        try {
            if(objects != null && objects.length > 0) {
                date = jdbcTemplate.queryForObject(sql, objects, Date.class);
            }
            else {
                date = jdbcTemplate.queryForObject(sql, Date.class);
            }
            return date;
        } catch (EmptyResultDataAccessException e) {
//			StaticLog.error("查询无记录："+SqlUtil.getSql(sql, objects));
            return null;
        } catch (Exception e) {
            StaticLog.error(e.getMessage()+"\n"+SqlUtil.getSql(sql, objects));
            //return null;
            e.printStackTrace();
            throw new DbException(e.getMessage());
        }
    }

    /**
     * 返回数据集
     * @param sql 传入的sql语句
     * @param objects  参数数组，可以为null
     * @param keyName 字段名称
     * @return 指定字段对应值的字符串list集合
     */
    public List<String> queryForList(String sql, Object[] objects, String keyName) {
        List<String> resList = new ArrayList<String>();
        List<Map<String, Object>> dataList = this.queryForList(sql, objects, DEFAULT_FETCHSIZE);
        for(Map<String, Object> map : dataList) {
            resList.add(MapUtil.getStr(map, keyName));
        }

        return resList;
    }

    /**
     * 返回数据集
     * @param sql 传入的sql语句
     * @param objects  参数数组，可以为null
     * @return map对象的集合
     */
    public List<Map<String, Object>> queryForList(String sql, Object[] objects) {
        return this.queryForList(sql, objects, DEFAULT_FETCHSIZE);
    }

    /**
     * 返回数据集
     * @param sql 传入的sql语句:
     * @param objects  参数数组，可以为null
     * @param fetchSize 一次读取到缓存的记录数
     * @return map对象的集合
     */
    private List<Map<String, Object>> queryForList(String sql, Object[] objects, int fetchSize) {
        StaticLog.info(sql);
        jdbcTemplate.setFetchSize(fetchSize);
        List<Map<String, Object>> list = null;
        try {
            if(objects != null && objects.length > 0) {
                list = jdbcTemplate.queryForList(sql, objects);
            }
            else {
                list = jdbcTemplate.queryForList(sql);
            }
        } catch (Exception e) {
            StaticLog.error(e.getMessage()+"\n"+SqlUtil.getSql(sql, objects));
            e.printStackTrace();
            throw new DbException(e.getMessage()+"\n"+SqlUtil.getSql(sql, objects));
        }
        if (list == null) {
            list = new ArrayList<Map<String, Object>>();
        }
        return list;
    }

    /**
     * 返回指定对象list，例如 List<User> List<Student> ...
     * @param sql 传入的sql语句:
     * @param objects  参数数组，可以为null
     * @param rowMapper  字段映射，针对实体类，例如 User、Student ...
     * @return 实体类对象list集合
     */
    public <T> List<T> queryForList(String sql, Object[] objects, RowMapper<T> rowMapper) {
        StaticLog.info(sql);
        List<T> list = null;
        try {
            jdbcTemplate.setFetchSize(DEFAULT_FETCHSIZE);
            if(objects != null && objects.length > 0) {
                list = jdbcTemplate.query(sql, objects, rowMapper);
            }
            else {
                list = jdbcTemplate.query(sql, rowMapper);
            }
        } catch (Exception e) {
            StaticLog.error(e.getMessage()+"\n"+SqlUtil.getSql(sql, objects));
            e.printStackTrace();
            throw new DbException(e.getMessage()+"\n"+SqlUtil.getSql(sql, objects));
        }
        if (list == null) {
            list = new ArrayList<T>();
        }

        return list;
    }

    /**
     * 返回指定对象list，例如 List<User> List<Student> ...
     * @param sql 传入的sql语句
     * @param objects  参数数组
     * @param clazz  实体类，通过@MapRow映射数据库字段
     * @return 实体类对象list集合
     */
    public <T> List<T> queryForList(String sql, Object[] objects, Class<T> clazz) {
        StaticLog.info(sql);
        List<T> list = null;
        try {
            jdbcTemplate.setFetchSize(DEFAULT_FETCHSIZE);
            if(objects != null && objects.length > 0) {
                list = jdbcTemplate.query(sql, objects, new BaseRowMapper<T>(clazz));
            }
            else {
                list = jdbcTemplate.query(sql, new BaseRowMapper<T>(clazz));
            }
        } catch (Exception e) {
            StaticLog.error(e.getMessage()+"\n"+SqlUtil.getSql(sql, objects));
            e.printStackTrace();
            throw new DbException(e.getMessage()+"\n"+SqlUtil.getSql(sql, objects));
        }
        if (list == null) {
            list = new ArrayList<T>();
        }

        return list;
    }

    public int execute(String sql) {
        return execute(sql,null);
    }
    /**
     * insert,update,delete 操作
     * @param sql 传入的语句 sql="insert into tables values(?,?)";
     * @param objects  参数数组
     * @return 0:失败 1:成功
     */

    public int execute(String sql, Object[] objects) {
        StaticLog.info(sql);
        int exc = 1;
        try {
            if(objects != null && objects.length > 0) {
                jdbcTemplate.update(sql, objects);
            }
            else {
                jdbcTemplate.update(sql);
            }
        } catch (Exception e) {
            exc = 0;
            StaticLog.error(e.getMessage()+"\n"+SqlUtil.getSql(sql, objects));
            e.printStackTrace();
            throw new DbException(e.getMessage()+"\n"+SqlUtil.getSql(sql, objects));
        }
        return exc;
    }

    /**
     * 执行插入操作
     * @param sql 传入的语句
     * @param objects  参数数组
     * @return 返回自动增加的id号
     */
    public long insert(final String sql, final Object[] objects) {
        StaticLog.info(sql);
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                if(objects != null && objects.length > 0) {
                    for(int i=1;i<=objects.length;i++) {
                        ps.setObject(i, objects[i-1]);
                    }
                }
                return ps;
            }
        }, keyHolder);
        return keyHolder.getKey().longValue();
    }

    /**
     * 批量insert,update,delete操作
     * @param sql sql语句必须固定
     * @param objectsList 参数数组，数组长度必须与sql语句中占位符的数量一致
     * @return 1：成功 0：失败
     */
    public int batchExecute(String sql, final List<Object[]> objectsList) {
        StaticLog.info(sql);
        int exc = 1;
        try {
            jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter(){
                @Override
                public void setValues(PreparedStatement ps, int i) throws SQLException {
                    Object[] objects = objectsList.get(i);
                    for(int j=0;j<objects.length;j++) {
                        ps.setObject(j+1, objects[j]);
                    }
                }

                @Override
                public int getBatchSize() {
                    return objectsList.size();
                }
            });
        } catch (DataAccessException e) {
            exc = 0;
            StaticLog.error(e.getMessage());
        }
        return exc;
    }

    /**
     * 事务处理
     * @param batchSql 批处理sql对象
     * @return 1：成功 0：失败
     */
    public int doInTransaction(final BatchSql batchSql) {
        int exc = 1;
        if (batchSql == null) {
            exc = 0;
        }
        try {
            transactionTemplate.execute(new TransactionCallbackWithoutResult() {
                public void doInTransactionWithoutResult(
                        TransactionStatus status) {
                    List<Map<String, Object>> sqlList = batchSql.getSqlList();
                    for (int i = 0; i < sqlList.size(); i++) {
                        Map<String, Object> sqlMap = sqlList.get(i);
                        String sql = (String) sqlMap.get("sql");
                        Object[] objects = (Object[]) sqlMap.get("objects");
                        jdbcTemplate.update(sql, objects);
                    }
                }
            });
        } catch (Exception e) {
            exc = 0;
            StaticLog.error(e.getMessage());
            e.printStackTrace();
            throw new DbException(e.getMessage());
        }
        return exc;
    }

    /**
     * 替换查询字段为count(1)
     * @param sql 传入的sql语句
     * @return 替换后的sql语句，如：select * from xxxx 替换为select count(1) from xxxx
     */
    public String replaceSqlCount(String sql){
        String oldSql = sql;
        String newSql = "";

        sql = sql.toLowerCase();
        if (sql.contains("group")) {//如果语句最后有group by 不需要替换，嵌套一层
            String groupSql = oldSql.substring(sql.lastIndexOf("group"));
            if (!groupSql.contains(")")) {
                return "select count(1) from (" + sql + ") AA";
            }
        }

        //以小写字母匹配所有的select和from
        Pattern p = Pattern.compile("select|from", Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(sql);
        int iSelect = 0;
        int iFrom = 0;
        //找到最外面的select对应的的是第几个from
        while(m.find()) {
            if (m.group().toLowerCase().equals("select")) {
                iSelect ++;
            }
            if (m.group().toLowerCase().equals("from")) {
                iFrom ++;
            }
            if (iSelect == iFrom) {
                break;
            }
        }

        int iSearch = 0;
        int fromIndex = 0;
        while(iSearch < iFrom){//找到最外面的select对应的from的位置
            if ((sql.toLowerCase()).indexOf("from")>0) {
                fromIndex = (sql.toLowerCase()).indexOf("from");
                sql = sql.replaceFirst("from", "####");
                iSearch ++;
            }
        }
        newSql = " select count(1) from "+oldSql.substring(fromIndex+4);
        return newSql;
    }

    /**
     * 替换掉sql语句中最外面的order by
     * @param sql 传入的sql语句
     * @return 替换后的sql语句
     */
    public String replaceSqlOrder(String sql){
        String oldSql = sql;
        sql = sql.toLowerCase();
        String newSql = "";
        if (sql.contains("order ")) {
            String orderSql = oldSql.substring(sql.lastIndexOf("order "));
            if (orderSql.contains(")")) {
                newSql = oldSql;
            } else {
                newSql = oldSql.substring(0, sql.lastIndexOf("order "));
            }
        } else {
            newSql = oldSql;
        }
        return newSql;
    }

    /**
     * 数据库分页查询
     * @param sql 传入的sql语句
     * @param list 参数集合
     * @return Map对象的list集合
     */
    public List<Map<String, Object>> getForList(String sql, List<String> list, int pageSize,int pageNum) {
        //int pageSize = RequestUtil.getIntValue(request, "pageSize");
        //int pageNum = RequestUtil.getIntValue(request, "pageNum");

        //替换sql语句中的order by，并将查询字段替换成count(1)
        List<String> cntSqlList = new ArrayList<String>();
        cntSqlList.addAll(list);
        String orderSql = this.replaceSqlOrder(sql);
        //需要查看替换掉order by之后占位符是否减少
        int cnt = this.getCharCnt(orderSql);
        if (cnt != cntSqlList.size()) {//如果替换order by之后占位符少了，将list从后去除cntSqlList.size()-cnt个参数
            int removeCnt = 0;
            int rCnt = cntSqlList.size()-cnt;//需要剔除的参数个数
            for (int i = cntSqlList.size()-1; i >= 0; i--) {
                cntSqlList.remove(i);
                removeCnt ++;
                if (removeCnt == rCnt) {
                    break;
                }
            }
        }
        String cntSql = this.replaceSqlCount(orderSql);
        //需要查看替换成count(1)之后占位符是否减少
        cnt = this.getCharCnt(cntSql);
        if (cnt != cntSqlList.size()) {//如果替换count(1)之后占位符少了，将list从前去除list.size()-cnt个参数
            int removeCnt = 0;
            int rCnt = cntSqlList.size()-cnt;//需要剔除的参数个数
            for (int i = 0; i < cntSqlList.size(); i++) {
                cntSqlList.remove(i);
                removeCnt ++;
                if (removeCnt == rCnt) {
                    break;
                }
            }
        }

        int startIndex = (pageNum-1) * pageSize;
        sql += " limit "+startIndex+", "+pageSize;
        return this.queryForList(sql, list.toArray());
    }

    /**
     * 获取sql语句中占位符的个数
     * @param sql 传入的sql语句
     * @return 占位符的个数
     */
    public int getCharCnt(String sql) {
        int cnt = 0;
        Pattern p = Pattern.compile("\\?", Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(sql);
        while(m.find()){
            cnt ++;
        }
        return cnt;
    }

    /**
     * 查询出记录总条数
     * @param sql 传入的sql语句
     * @return 记录总条数
     */
    public long getTotalRows(String sql, List<String> list) {
        sql = this.replaceSqlCount(this.replaceSqlOrder(sql));
        return this.queryForLong(sql, list.toArray());
    }


    /************************************************************************************************************************
     * 私有方法  66666666666666666666  end
     ************************************************************************************************************************/


    // Save ***********************************************************************************************************
    // Save ***********************************************************************************************************








    // Query ***********************************************************************************************************
    // Query ***********************************************************************************************************



    public <T> List<T> findBeanList(Class<T> clazz, String sql) {
        return findBeanList(clazz, sql,null);
    }


    public <T> List<T> findBeanList(Class clazz, String sql, Object... params) {
        List datas = queryList(sql, params);
        return RecordUtil.mapToBeans(datas, clazz);
    }




    public <T> T findBeanById(Class<T> clazz, Object idValue) {
        String tableName = SqlUtils.getTableName(clazz);
        String primaryKeyStr = getPkNames(tableName);
        Record record = findById(tableName, primaryKeyStr, idValue);
        if (record == null) {
            return null;
        }
        T datas = (T) RecordUtil.recordToBean(record, clazz);
        return datas;
    }


    public <T> T findBeanById(Class beanClass, String primaryKeys, Object... idValue) {
        String tableName = SqlUtils.getTableName(beanClass);
        String primaryKeyStr = StrUtil.join(",", primaryKeys);
        if (primaryKeys == null || StrUtil.isEmpty(primaryKeyStr)) {
            primaryKeyStr = getPkNames(tableName);
        }
        Record record = findById(tableName, primaryKeyStr, idValue);
        if (record == null) {
            return null;
        }
        T datas = (T) RecordUtil.recordToBean(record, beanClass);
        return datas;
    }


    public <T> Page findBeanPages(Class beanClass, int page, int rows) {
        return findBeanPages(beanClass, page, rows, null);
    }


    public <T> Page findBeanPages(Class beanClass, int page, int rows, String sql) {
        //SqlContext sqlContext = SqlUtil.getSelect(beanClass, page, rows, params);
        Page pageVo = new Page();
        if(StrUtil.isEmpty(sql)){
            sql = SqlUtils.getSelectSQl(beanClass);
        }
        String sql1 = SqlUtils.getSelect(sql, page, rows);
        SqlContext sqlContext = SqlUtils.getSelect(new StringBuilder(sql1), Maps.newHashMap());
        List<Map<String, Object>> listData = queryList(sqlContext.getSql(), sqlContext.getParams());
        pageVo.setList(RecordUtil.mapToBeans(listData, beanClass));
        int totalRow = count(SqlUtils.getCount(sql));
        pageVo.setTotalRow(totalRow);
        pageVo.setPageSize(rows);
        pageVo.setPageNumber(page);
        if (totalRow % rows == 0) {
            pageVo.setTotalPage(totalRow / rows);
        } else {
            pageVo.setTotalPage(totalRow / rows + 1);
        }
        return pageVo;
    }


    public Page queryBeanPage(Class beanClass, int page, int rows) {
        return queryBeanPage(beanClass, page, rows, null);
    }


    public Page queryBeanPage(Class beanClass, int page, int limit, String sql) {
        //SqlContext sqlContext = SqlUtil.getSelect(beanClass, page, rows, params);
        Page pageVo = new Page();
        if(StrUtil.isEmpty(sql)){
            sql = SqlUtils.getSelectSQl(beanClass);
        }
        SqlContext sqlContext = SqlUtils.getSelect(new StringBuilder(SqlUtils.getSelect(sql, page, limit)), Maps.newHashMap());
        List<Map<String, Object>> listData = queryList(sqlContext.getSql(), sqlContext.getParams());
        pageVo.setList(RecordUtil.mapToBeans(listData, beanClass));
        int totalRow = count(SqlUtils.getCount(sql));
        pageVo.setTotalRow(totalRow);
        pageVo.setPageNumber(page);
        pageVo.setPageSize(limit);
        if (totalRow % limit == 0) {
            pageVo.setTotalPage(totalRow / limit);
        } else {
            pageVo.setTotalPage(totalRow / limit + 1);
        }
        return pageVo;
    }


    public Page<Map> queryMapPages(String sql, int page, int limit, Object[] params) {
        Page pageVo = new Page();
        pageVo.setList(queryList(SqlUtils.getSelect(sql, page, limit), params));
        int totalRow = count(SqlUtils.getCount(sql), params);
        pageVo.setTotalRow(totalRow);
        pageVo.setPageNumber(page);
        pageVo.setPageSize(limit);
        if (totalRow % limit == 0) {
            pageVo.setTotalPage(totalRow / limit);
        } else {
            pageVo.setTotalPage(totalRow / limit + 1);
        }
        return pageVo;
    }


    public int count(String sql, Object... params) {
        try {
            return queryForInt(sql, params);
            //return queryInt(sql,params);
        } catch (Exception e) {
            throw new DbException(e, sql);
        }
    }


    public List<Record> findByColumnValueRecords(String tableName, String columnNames, Object... columnValues) {
        String[] pKeys = columnNames.split(",");
        if (pKeys.length != columnValues.length)
            throw new IllegalArgumentException("primary key number must equals id value number");

        String sql = dialect.forDbFindById(tableName, pKeys);
        List<Map<String, Object>> resultMap;
        try {
            resultMap = queryList(sql, columnValues);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
        return RecordUtil.mappingList(resultMap);
    }


    public Page<Record> paginate(Integer pageNumber, Integer limit, String select, String from/*, Map<String, Object> params*/) {
        String sqlStr = select + " " + from;
        //SqlContext sqlContext = SqlUtil.getSelect(new StringBuilder(sqlStr), params);
        Page pageVo = new Page();
        List<Map<String, Object>> results = queryList(SqlUtils.getSelect(sqlStr, pageNumber, limit), null);
//        List<Map<String, Object>> results = queryList(sqlContext);
        List<Record> records = RecordUtil.mappingList(results);
        int totalpage = count(SqlUtils.getCount(sqlStr));
        pageVo.setList(records);
        pageVo.setTotalRow(totalpage);
        pageVo.setPageNumber(pageNumber);
        pageVo.setPageSize(limit);
        pageVo.setTotalPage(totalpage / limit);
        return pageVo;
    }


    //************************************************************************************************************************************************
    //Record end  **************************************************************************************************************************************
    //************************************************************************************************************************************************


    //************************************************************************************************************************************************
    //Mybatis  XML  SQL 111111111111111111  end   SqlXmlUtil *************************************************************************
    //************************************************************************************************************************************************


//    public Object executeSqlXml(String sqlXml, Map params) throws SQLException {
//        return JdbcUtil.executeSql(getDataSource().getConnection(), sqlXml, params, true);
//    }
//
//    public int updateSqlXml(String sqlXml, Map params) throws SQLException {
//        return JdbcUtil.update(getDataSource().getConnection(), sqlXml, params);
//    }
//
//    public List<Map<String, Object>> querySqlXml(String sqlXml, Map params) throws SQLException {
//        return JdbcUtil.query(getDataSource().getConnection(), sqlXml, params);
//    }

    //************************************************************************************************************************************************
    //Mybatis  XML  SQL 111111111111111111  end   SqlXmlUtil *************************************************************************
    //************************************************************************************************************************************************


    private  <T> List<T> query(String sql, Object... paras) {
        //List list = jdbcTemplate.queryForList(sql, paras);
        return (List<T>) getJdbcTemplate().query(sql, new RowMapper<Object[]>() {
            public Object[] mapRow(ResultSet rs, int rowNum) throws SQLException {
                int colAmount = rs.getMetaData().getColumnCount();
                if (colAmount > 1) {
                    Object[] temp = new Object[colAmount];
                    for (int i=0; i<colAmount; i++) {
                        temp[i] = rs.getObject(i + 1);
                    }
                    return temp;
                }else if(colAmount == 1) {
                    return new Object[]{rs.getObject(1)};
                }
                return null;
            }
        });
    }


    /**
     * @see #query(String, Object...)
     * @param sql an SQL statement
     */
    private  <T> List<T> query(String sql) {		// return  List<object[]> or List<object>
        return query(sql, NULL_PARA_ARRAY);
    }

    /**
     * Execute sql query and return the first result. I recommend add "limit 1" in your sql.
     * @param sql an SQL statement that may contain one or more '?' IN parameter placeholders
     * @param paras the parameters of sql
     * @return Object[] if your sql has select more than one column,
     * 			and it return Object if your sql has select only one column.
     */
    public <T> T queryFirst(String sql, Object... paras) {
        List<T> result = query(sql, paras);
        return (result.size() > 0 ? result.get(0) : null);
    }


    /**
     * @see #queryFirst(String, Object...)
     * @param sql an SQL statement
     */
    public <T> T queryFirst(String sql) {
        // return queryFirst(sql, NULL_PARA_ARRAY);
        List<T> result = query(sql, NULL_PARA_ARRAY);
        return (result.size() > 0 ? result.get(0) : null);
    }


    // 26 queryXxx method below -----------------------------------------------
    /**
     * Execute sql query just return one column.
     *  <T> the type of the column that in your sql's select statement
     * @param sql an SQL statement that may contain one or more '?' IN parameter placeholders
     * @param paras the parameters of sql
     * @return <T> T
     */
    /*public <T> T queryColumn(String sql, Object... paras) {
        List<T> result = query(sql, paras);
        if (result.size() > 0) {
            T temp = result.get(0);
            if (temp instanceof Object[] && ((Object[]) temp).length>1){
                throw new DbException("Only ONE COLUMN can be queried.");
            }else if (temp instanceof Object[] && ((Object[]) temp).length == 1){
                return (T) ((Object[]) temp)[0];
            }
            return temp;
        }
        return null;
    }

    public <T> T queryColumn(String sql) {
        return (T)queryColumn(sql, NULL_PARA_ARRAY);
    }

    public String queryStr(String sql, Object... paras) {
        Object s = queryColumn(sql, paras);
        return s != null ? s.toString() : null;
    }

    public String queryStr(String sql) {
        return queryStr(sql, NULL_PARA_ARRAY);
    }

    public Integer queryInt(String sql, Object... paras) {
        Number n = queryNumber(sql, paras);
        return n != null ? n.intValue() : null;
    }

    public Integer queryInt(String sql) {
        return queryInt(sql, NULL_PARA_ARRAY);
    }

    public Long queryLong(String sql, Object... paras) {
        Number n = queryNumber(sql, paras);
        return n != null ? n.longValue() : null;
    }

    public Long queryLong(String sql) {
        return queryLong(sql, NULL_PARA_ARRAY);
    }

    public Double queryDouble(String sql, Object... paras) {
        Number n = queryNumber(sql, paras);
        return n != null ? n.doubleValue() : null;
    }

    public Double queryDouble(String sql) {
        return queryDouble(sql, NULL_PARA_ARRAY);
    }

    public Float queryFloat(String sql, Object... paras) {
        Number n = queryNumber(sql, paras);
        return n != null ? n.floatValue() : null;
    }

    public Float queryFloat(String sql) {
        return queryFloat(sql, NULL_PARA_ARRAY);
    }

    public BigDecimal queryBigDecimal(String sql, Object... paras) {
        Object n = queryColumn(sql, paras);
        if (n instanceof BigDecimal) {
            return (BigDecimal)n;
        } else if (n != null) {
            return new BigDecimal(n.toString());
        } else {
            return null;
        }
    }

    public BigDecimal queryBigDecimal(String sql) {
        return queryBigDecimal(sql, NULL_PARA_ARRAY);
    }

    public BigInteger queryBigInteger(String sql, Object... paras) {
        Object n = queryColumn(sql, paras);
        if (n instanceof BigInteger) {
            return (BigInteger)n;
        } else if (n != null) {
            return new BigInteger(n.toString());
        } else {
            return null;
        }
    }

    public BigInteger queryBigInteger(String sql) {
        return queryBigInteger(sql, NULL_PARA_ARRAY);
    }

    public byte[] queryBytes(String sql, Object... paras) {
        return (byte[])queryColumn(sql, paras);
    }

    public byte[] queryBytes(String sql) {
        return (byte[])queryColumn(sql, NULL_PARA_ARRAY);
    }

    public java.util.Date queryDate(String sql, Object... paras) {
        Object d = queryColumn(sql, paras);
        if (d instanceof Temporal) {
            if (d instanceof LocalDateTime) {
                return TimeKit.toDate((LocalDateTime)d);
            }
            if (d instanceof LocalDate) {
                return TimeKit.toDate((LocalDate)d);
            }
            if (d instanceof LocalTime) {
                return TimeKit.toDate((LocalTime)d);
            }
        }
        return (java.util.Date)d;
    }

    public java.util.Date queryDate(String sql) {
        return queryDate(sql, NULL_PARA_ARRAY);
    }

    public LocalDateTime queryLocalDateTime(String sql, Object... paras) {
        Object d = queryColumn(sql, paras);
        if (d instanceof LocalDateTime) {
            return (LocalDateTime)d;
        }
        if (d instanceof LocalDate) {
            return ((LocalDate)d).atStartOfDay();
        }
        if (d instanceof LocalTime) {
            return LocalDateTime.of(LocalDate.now(), (LocalTime)d);
        }
        if (d instanceof java.util.Date) {
            return TimeKit.toLocalDateTime((java.util.Date)d);
        }
        return (LocalDateTime)d;
    }

    public LocalDateTime queryLocalDateTime(String sql) {
        return queryLocalDateTime(sql, NULL_PARA_ARRAY);
    }

    public java.sql.Time queryTime(String sql, Object... paras) {
        return (java.sql.Time)queryColumn(sql, paras);
    }

    public java.sql.Time queryTime(String sql) {
        return (java.sql.Time)queryColumn(sql, NULL_PARA_ARRAY);
    }

    public java.sql.Timestamp queryTimestamp(String sql, Object... paras) {
        return (java.sql.Timestamp)queryColumn(sql, paras);
    }

    public java.sql.Timestamp queryTimestamp(String sql) {
        return (java.sql.Timestamp)queryColumn(sql, NULL_PARA_ARRAY);
    }

    public Boolean queryBoolean(String sql, Object... paras) {
        return (Boolean)queryColumn(sql, paras);
    }

    public Boolean queryBoolean(String sql) {
        return (Boolean)queryColumn(sql, NULL_PARA_ARRAY);
    }

    public Short queryShort(String sql, Object... paras) {
        Number n = queryNumber(sql, paras);
        return n != null ? n.shortValue() : null;
    }

    public Short queryShort(String sql) {
        return queryShort(sql, NULL_PARA_ARRAY);
    }

    public Byte queryByte(String sql, Object... paras) {
        Number n = queryNumber(sql, paras);
        return n != null ? n.byteValue() : null;
    }

    public Byte queryByte(String sql) {
        return queryByte(sql, NULL_PARA_ARRAY);
    }

    public Number queryNumber(String sql, Object... paras) {
        return (Number)queryColumn(sql, paras);
    }

    public Number queryNumber(String sql) {
        return (Number)queryColumn(sql, NULL_PARA_ARRAY);
    }*/
    // 26 queryXxx method under -----------------------------------------------

    public List<Record> find(String sql, Object... paras)  {
        List<Map<String, Object>> results = queryList(sql,paras);
        return RecordUtil.mappingList(results);
    }

    /**
     * @param sql the sql statement
     */
    public List<Record> find(String sql) {
        return find(sql, NULL_PARA_ARRAY);
    }

    public List<Record> findAll(String tableName) {
        String sql = dialect.forFindAll(tableName);
        return find(sql, NULL_PARA_ARRAY);
    }

    /**
     * Find first record. I recommend add "limit 1" in your sql.
     * @param sql an SQL statement that may contain one or more '?' IN parameter placeholders
     * @param paras the parameters of sql
     * @return the Record object
     */
    public Record findFirst(String sql, Object... paras) {
        List<Record> result = find(sql, paras);
        return result.size() > 0 ? result.get(0) : null;
    }

    /**
     * @see #findFirst(String, Object...)
     * @param sql an SQL statement
     */
    public Record findFirst(String sql) {
        return findFirst(sql, NULL_PARA_ARRAY);
    }

    /**
     * Find record by id with default primary key.
     * <pre>
     * Example:
     * Record user = Db.use().findById("user", 15);
     * </pre>
     * @param tableName the table name of the table
     * @param idValue the id value of the record
     */
    public Record findById(String tableName, Object idValue) {
        String defaultPrimaryKey = getPkNames(tableName);
        return findById(tableName, defaultPrimaryKey, idValue);
    }

    /**
     * Find record by ids.
     * <pre>
     * Example:
     * Record user = Db.use().findByIds("user", "user_id", 123);
     * Record userRole = Db.use().findByIds("user_role", "user_id, role_id", 123, 456);
     * </pre>
     * @param tableName the table name of the table
     * @param primaryKey the primary key of the table, composite primary key is separated by comma character: ","
     * @param idValues the id value of the record, it can be composite id values
     */
    public Record findById(String tableName, String primaryKey, Object... idValues) {
        String[] pKeys = primaryKey.split(",");
        if (pKeys.length != idValues.length)
            throw new IllegalArgumentException("primary key number must equals id value number");

        String sql = dialect.forDbFindById(tableName, pKeys);
        List<Record> result = find(sql, idValues);
        if(CollectionUtil.isEmpty(result)){
            return null;
        }
        return result.size() > 0 ? result.get(0) : null;
    }



    /**
     * Delete record by id with default primary key.
     * <pre>
     * Example:
     * Db.use().deleteById("user", 15);
     * </pre>
     * @param tableName the table name of the table
     * @param idValue the id value of the record
     * @return true if delete succeed otherwise false
     */
    public boolean deleteById(String tableName, Object idValue) {
        String primaryKey = getPkNames(tableName);
        return deleteById(tableName, primaryKey, idValue);
    }

    /**
     * Delete record by ids.
     * <pre>
     * Example:
     * Db.use().deleteByIds("user", "user_id", 15);
     * Db.use().deleteByIds("user_role", "user_id, role_id", 123, 456);
     * </pre>
     * @param tableName the table name of the table
     * @param primaryKey the primary key of the table, composite primary key is separated by comma character: ","
     * @param idValues the id value of the record, it can be composite id values
     * @return true if delete succeed otherwise false
     */
    public boolean deleteById(String tableName, String primaryKey, Object... idValues) {
        String[] pKeys = primaryKey.split(",");
        if (pKeys.length != idValues.length)
            throw new IllegalArgumentException("primary key number must equals id value number");

        String sql = dialect.forDbDeleteById(tableName, pKeys);
        return execute(sql, idValues) >= 1;
    }


    /**
     * Delete record.
     * <pre>
     * Example:
     * boolean succeed = Db.use().delete("user", "id", user);
     * </pre>
     * @param tableName the table name of the table
     * @param primaryKey the primary key of the table, composite primary key is separated by comma character: ","
     * @param record the record
     * @return true if delete succeed otherwise false
     */
    public boolean delete(String tableName, String primaryKey, Record record) {
        String[] pKeys = primaryKey.split(",");
        if (pKeys.length <= 1) {
            Object t = record.get(primaryKey);	// 引入中间变量避免 JDK 8 传参有误
            return deleteById(tableName, primaryKey, t);
        }
        dialect.trimPrimaryKeys(pKeys);
        Object[] idValue = new Object[pKeys.length];
        for (int i=0; i<pKeys.length; i++) {
            idValue[i] = record.get(pKeys[i]);
            if (idValue[i] == null)
                throw new IllegalArgumentException("The value of primary key \"" + pKeys[i] + "\" can not be null in record object");
        }
        return deleteById(tableName, primaryKey, idValue);
    }

    /**
     * <pre>
     * Example:
     * boolean succeed = Db.use().delete("user", user);
     * </pre>
     * @see #delete(String, String, Record)
     */
    public boolean delete(String tableName, Record record) {
        /*String defaultPrimaryKey = dialect.getDefaultPrimaryKey();
        String defaultPrimaryKey = getPkNames(tableName);
        Object t = record.get(defaultPrimaryKey);	// 引入中间变量避免 JDK 8 传参有误
        return deleteByIds(tableName, defaultPrimaryKey, t);*/
        String primaryKey = getPkNames(tableName);
        String[] pKeys = primaryKey.split(",");
        Object[] ids = new Object[pKeys.length];
        for (int i = 0; i < pKeys.length; i++) {
            ids[i] = record.get(pKeys[i].trim());    // .trim() is important!
            if (ids[i] == null)
                throw new RuntimeException("You can't update record without Primary Key, " + pKeys[i] + " can not be null.");
        }
        String deleteSql = dialect.forDbDeleteById(tableName, pKeys);
        int result = execute(deleteSql, ids);
        if (result >= 1) {
            return true;
        }
        return false;
    }


    /**
     * Paginate.
     * @param pageNumber the page number
     * @param pageSize the page size
     * @param select the select part of the sql statement
     * @param sqlExceptSelect the sql statement excluded select part
     * @return the Page object
     */
    public Page<Record> paginate(int pageNumber, int pageSize, String select, String sqlExceptSelect) {
        return doPaginate(pageNumber, pageSize, null, select, sqlExceptSelect, NULL_PARA_ARRAY);
    }

    public Page<Record> paginate(int pageNumber, int pageSize, boolean isGroupBySql, String select, String sqlExceptSelect, Object... paras) {
        return doPaginate(pageNumber, pageSize, isGroupBySql, select, sqlExceptSelect, paras);
    }

    protected Page<Record> doPaginate(int pageNumber, int pageSize, Boolean isGroupBySql, String select, String sqlExceptSelect, Object... paras) {
        try {
            String totalRowSql = dialect.forPaginateTotalRow(select, sqlExceptSelect, null);
            StringBuilder findSql = new StringBuilder();
            findSql.append(select).append(' ').append(sqlExceptSelect);
            return doPaginateByFullSql( pageNumber, pageSize, isGroupBySql, totalRowSql, findSql, paras);
        } catch (Exception e) {
            throw new DbException(e);
        }
    }

    protected Page<Record> doPaginateByFullSql(int pageNumber, int pageSize, Boolean isGroupBySql, String totalRowSql, StringBuilder findSql, Object... paras) {
        if (pageNumber < 1 || pageSize < 1) {
            throw new DbException("pageNumber and pageSize must more than 0");
        }
        /*if (dialect.isTakeOverDbPaginate()) {
            return dialect.takeOverDbPaginate(conn, pageNumber, pageSize, isGroupBySql, totalRowSql, findSql, paras);
        }*/
        List result = query(  totalRowSql, paras);
        int size = result.size();
        if (isGroupBySql == null) {
            isGroupBySql = size > 1;
        }

        long totalRow;
        if (isGroupBySql) {
            totalRow = size;
        } else {
            totalRow = (size > 0) ? ((Number)result.get(0)).longValue() : 0;
        }
        if (totalRow == 0) {
            return new Page<Record>(new ArrayList<Record>(0), pageNumber, pageSize, 0, 0);
        }

        int totalPage = (int) (totalRow / pageSize);
        if (totalRow % pageSize != 0) {
            totalPage++;
        }

        if (pageNumber > totalPage) {
            return new Page<Record>(new ArrayList<Record>(0), pageNumber, pageSize, totalPage, (int)totalRow);
        }

        // --------
        String sql = dialect.forPaginate(pageNumber, pageSize, findSql);
        List<Record> list = find(sql, paras);
        return new Page<Record>(list, pageNumber, pageSize, totalPage, (int)totalRow);
    }

    protected Page<Record> paginate(   int pageNumber, int pageSize, String select, String sqlExceptSelect, Object... paras){
        String totalRowSql = dialect.forPaginateTotalRow(select, sqlExceptSelect, null);
        StringBuilder findSql = new StringBuilder();
        findSql.append(select).append(' ').append(sqlExceptSelect);
        return doPaginateByFullSql(  pageNumber, pageSize, null, totalRowSql, findSql, paras);
    }

    protected Page<Record> doPaginateByFullSql(int pageNumber, int pageSize, Boolean isGroupBySql, String totalRowSql, String findSql, Object... paras) {
        try {
            StringBuilder findSqlBuf = new StringBuilder().append(findSql);
            return doPaginateByFullSql(  pageNumber, pageSize, isGroupBySql, totalRowSql, findSqlBuf, paras);
        } catch (Exception e) {
            throw new DbException(e);
        }
    }

    public Page<Record> paginateByFullSql(int pageNumber, int pageSize, String totalRowSql, String findSql, Object... paras) {
        return doPaginateByFullSql(pageNumber, pageSize, null, totalRowSql, findSql, paras);
    }

    public Page<Record> paginateByFullSql(int pageNumber, int pageSize, boolean isGroupBySql, String totalRowSql, String findSql, Object... paras) {
        return doPaginateByFullSql(pageNumber, pageSize, isGroupBySql, totalRowSql, findSql, paras);
    }

    /**
     * Save record.
     * <pre>
     * Example:
     * Record userRole = new Record().set("user_id", 123).set("role_id", 456);
     * Db.use().save("user_role", "user_id, role_id", userRole);
     * </pre>
     * @param tableName the table name of the table
     * @param primaryKey the primary key of the table, composite primary key is separated by comma character: ","
     * @param record the record will be saved
     */
    public boolean save(String tableName,String primaryKey, Record record) {
        //String primaryKey = getPkNames(tableName);
        String[] pKeys = primaryKey.split(",");
        List<Object> paras = new ArrayList<Object>();
        StringBuilder sql = new StringBuilder();
        dialect.forDbSave(tableName, pKeys, record, sql, paras);
        int flag = 0;
        try {
            flag = execute(sql.toString(), paras.toArray());
        } catch (DuplicateKeyException e) {
            throw new RuntimeException("主键冲突，保存失败！");
        } catch (DataAccessException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag > 0;
    }

    /**
     * @see #save(String, String, Record)
     */
    public boolean save(String tableName, Record record) {
        String primaryKey = getPkNames(tableName);
        return save(tableName,primaryKey , record);
    }


    /**
     * Update Record.
     * <pre>
     * Example:
     * Db.use().update("user_role", "user_id, role_id", record);
     * </pre>
     * @param tableName the table name of the Record save to
     * @param primaryKey the primary key of the table, composite primary key is separated by comma character: ","
     * @param record the Record object
     */
    public boolean update(String tableName,String primaryKey, Record record) {
        //String primaryKey = getPkNames(tableName);
        String[] pKeys = primaryKey.split(",");
        Object[] ids = new Object[pKeys.length];
        for (int i = 0; i < pKeys.length; i++) {
            ids[i] = record.get(pKeys[i].trim());    // .trim() is important!
            if (ids[i] == null)
                throw new RuntimeException("You can't update record without Primary Key, " + pKeys[i] + " can not be null.");
        }
        StringBuilder sql = new StringBuilder();
        List<Object> paras = new ArrayList<Object>();
        dialect.forDbUpdate(tableName, pKeys, ids, record, sql, paras);
        if (paras.size() <= 1) {    // 参数个数为 1 的情况表明只有主键，也无需更新
            return false;
        }
        int result = execute(sql.toString(), paras.toArray());
        if (result >= 1) {
            return true;
        }
        return false;
    }


    /**
     * Update record with default primary key.
     * <pre>
     * Example:
     * Db.use().update("user", record);
     * </pre>
     * @see #update(String, String, Record)
     */
    public boolean update(String tableName, Record record) {
        String primaryKey = getPkNames(tableName);
        return update(tableName, primaryKey , record);
    }




}




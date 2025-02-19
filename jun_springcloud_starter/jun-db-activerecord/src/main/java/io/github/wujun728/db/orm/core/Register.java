package io.github.wujun728.db.orm.core;

import io.github.wujun728.db.orm.mapping.ColumnMap;
import io.github.wujun728.db.orm.mapping.JoinMap;
import io.github.wujun728.db.orm.mapping.ModelMap;
import io.github.wujun728.db.orm.annotation.*;
import io.github.wujun728.db.orm.utils.Scanner;
import io.github.wujun728.db.orm.utils.ThreadUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import javax.sql.DataSource;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Model注册类
 */
@Slf4j
public class Register {


    //Model与其属性 数据库字段对应关系
    static Map<Class<?>, ModelMap> modelMapping = new HashMap<>();

    //表名与主键映射
//    static Map<String, String> TABLE_PK_MAP = new HashMap<>();

    public static JedisPool jedisPool;

    public static boolean isUseCache = false;

    public static int expire = 60 * 60;

    private static JdbcTemplate readTemplate;

    private static JdbcTemplate writeTemplate;

    public static void initReadWrite(DataSource read, DataSource write) {
        Register.readTemplate = new JdbcTemplate(read);
        Register.writeTemplate = new JdbcTemplate(write);
    }

    /**
     * 初始化线程池
     * @param coreSize coreSize
     * @param maxPoolSize maxPoolSize
     * @param queueSize queueSize
     */
    public static void initThreadPool (int coreSize, int maxPoolSize, int queueSize){
        ThreadUtils.init(coreSize, maxPoolSize, queueSize);
    }

    public static void initRedisCache(String host, int port) {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPool = new JedisPool(jedisPoolConfig, host, port);
        isUseCache = true;
    }

    public static void initRedisCache(String host, int port, int expire) {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPool = new JedisPool(jedisPoolConfig, host, port);
        isUseCache = true;
        Register.expire = expire;
    }

    public static void initRedisCache(String host, int port, int expire, int timeout) {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPool = new JedisPool(jedisPoolConfig, host, port, timeout);
        isUseCache = true;
        Register.expire = expire;
    }

    public static void initRedisCache(String host, int port, int timeout, int maxIdle, long maxWaitMillis) {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxIdle(maxIdle);
        jedisPoolConfig.setMaxWaitMillis(maxWaitMillis);
        jedisPool = new JedisPool(jedisPoolConfig, host, port, timeout);
        isUseCache = true;
    }

    /**
     * 注册model
     * @param basePackage 扫描的包
     */
    public static void registerModel(String basePackage) throws IOException {
        //扫描包里类
        List<Class<?>> clazzs = Scanner.getClasses(basePackage);
        for(Class<? > clazz : clazzs) {
            registerClass(clazz);
        };
    }

    public static void registerClass(Class<?> clazz) {
        //检查属性注解
        Entity entity = clazz.getAnnotation(Entity.class);
        if(null != entity){
            String table = entity.table();
            boolean isCache = entity.isCache();
            log.info("正在注册Model " + clazz + " => " + table);
            ModelMap modelMap = new ModelMap();

            //获取对应表名
            modelMap.setTable(table);

            //是否使用缓存
            modelMap.setCache(isCache);

            List<ColumnMap> columnMaps = new ArrayList<>();

            List<JoinMap> joinMaps = new ArrayList<>();

            //获取model属性
            Field[] fields = clazz.getDeclaredFields();

            boolean hasPk = false;

            //遍历属性保存关系
            for(Field field : fields){
                Column column = field.getAnnotation(Column.class);
                if(null == column){
                    continue;
                }

                ColumnMap columnMap = new ColumnMap();

                //数据库字段名
                String fieldName = column.name();

                //属性名
                String propertyName = field.getName();

                //保存属性与数据库字段关系
                columnMap.setField(fieldName);
                columnMap.setProperty(propertyName);

                //添加属性与数据库字段映射
                columnMaps.add(columnMap);

                //扫描主键
                if(field.isAnnotationPresent(PK.class) && !hasPk){
                    modelMap.setPrimaryKey(propertyName);
                    hasPk = true;
                }

                //扫描关联列
                Join join = field.getAnnotation(Join.class);
                if(null != join){
                    JoinMap joinMap = new JoinMap();
                    joinMap.setColumn(fieldName);
                    joinMap.setType(field.getType());
                    joinMap.setPropertyName(propertyName);
                    joinMap.setRefColumn(join.refColumn());
                    joinMaps.add(joinMap);
                }

                //获取getter/setter
                String firstLetter = propertyName.substring(0, 1).toUpperCase();
                String getter = "get" + firstLetter + propertyName.substring(1);
                String setter = "set" + firstLetter + propertyName.substring(1);
                try {
                    Method getterMethod = clazz.getDeclaredMethod(getter);
                    Method setterMethod = clazz.getDeclaredMethod(setter, field.getType());
                    columnMap.setGetter(getterMethod);
                    columnMap.setSetter(setterMethod);
                } catch (NoSuchMethodException e) {
                    log.error(propertyName + "缺少getter方法");
                    e.printStackTrace();
                }
            }
            modelMap.setJoinMaps(joinMaps);
            modelMap.setColumnMaps(columnMaps);
            modelMapping.put(clazz, modelMap);
        }
    }

    /**
     * 注册Redis Model
     * @param basePackage 扫描的包
     */
    public static void registerRedisModel(String basePackage) throws IOException {
        //扫描包里类
        List<Class<?>> clazzs = Scanner.getClasses(basePackage);

        for(Class<? > clazz : clazzs){
            RedisEntity redisEntity = clazz.getAnnotation(RedisEntity.class);
            if(null != redisEntity) {

            }
        }
    }

    /**
     * 注册record
     * dbName 数据库名
     */
    /*public static void registerRecord(String dbName) {
        registerRecord(dbName,null);
    }
    public static void registerRecord(String dbName,DataSource dataSource) {
        JdbcTemplate jdbcTemplate = null;
        if(dataSource==null){
            jdbcTemplate = SpringUtils.getBean(JdbcTemplate.class);
        }else{
            jdbcTemplate = new JdbcTemplate(dataSource);
        }
        setReadTemplate(jdbcTemplate);
        setWriteTemplate(jdbcTemplate);
        List<Map<String, Object>> tablePks = jdbcTemplate.queryForList("SELECT t.TABLE_NAME, c.COLUMN_NAME FROM INFORMATION_SCHEMA.TABLE_CONSTRAINTS AS t, INFORMATION_SCHEMA.KEY_COLUMN_USAGE AS c WHERE t.TABLE_NAME = c.TABLE_NAME  AND t.TABLE_SCHEMA = '" + dbName + "' AND t.CONSTRAINT_TYPE = 'PRIMARY KEY';");

        for(Map<String, Object> tablePk : tablePks) {
            if(tablePk.containsKey("COLUMN_NAME")){
                log.info("正在注册Record " + (String)tablePk.get("TABLE_NAME") + " => " + (String)tablePk.get("COLUMN_NAME"));
                TABLE_PK_MAP.put((String)tablePk.get("TABLE_NAME"), (String)tablePk.get("COLUMN_NAME"));
            }
        }
    }*/

    public static JdbcTemplate getReadTemplate() {
        return readTemplate;
    }

    public static void setReadTemplate(JdbcTemplate readTemplate) {
        Register.readTemplate = readTemplate;
    }

    public static JdbcTemplate getWriteTemplate() {
        return writeTemplate;
    }

    public static void setWriteTemplate(JdbcTemplate writeTemplate) {
        Register.writeTemplate = writeTemplate;
    }
}

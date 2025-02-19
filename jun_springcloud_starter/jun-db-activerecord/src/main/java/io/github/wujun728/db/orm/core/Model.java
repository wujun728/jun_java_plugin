package io.github.wujun728.db.orm.core;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.github.wujun728.db.orm.cache.RedisCache;
import io.github.wujun728.db.orm.handler.DataFailHandler;
import io.github.wujun728.db.orm.handler.DataSuccessHandler;
import io.github.wujun728.db.orm.mapping.JoinMap;
import io.github.wujun728.db.orm.utils.CollectionUtils;
import io.github.wujun728.db.orm.utils.SpringUtils;
import io.github.wujun728.db.orm.utils.ThreadUtils;
import io.github.wujun728.db.record.Page;
import io.github.wujun728.db.orm.result.Scroll;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static javax.xml.bind.JAXBIntrospector.getValue;

/**
 * 数据库操作模型类
 */
public class Model<T> extends BaseModel implements Serializable {

    private static final long serialVersionUID = -1453641993600063553L;

    private final Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

    public static class Direction {
        public final static String ASC = "asc";

        public final static String DESC = "desc";
    }

    private JdbcTemplate readTemplate;

    private JdbcTemplate writeTemplate;

    private String tableName;

    private String pk;

    private RedisCache<T> cache;

    private Map<String, Object> mapData = new HashMap<>();

    private String jsonString;

    private JSONObject jsonObject;

    public Model() {
        //读数据源
        readTemplate = Register.getReadTemplate();

        //写数据源
        writeTemplate = Register.getWriteTemplate();

        if(null == readTemplate || null == writeTemplate) {
            readTemplate = SpringUtils.getBean(JdbcTemplate.class);
            writeTemplate = SpringUtils.getBean(JdbcTemplate.class);
        }


        this.tableName = TABLE_MAP.get(this.getClass());
        this.pk = PK_MAP.get(this.getClass());

        cache = new RedisCache<>();
    }

    public List<T> find(String sql, Object... params) {
        try {
            List<Map<String, Object>> resultMap = readTemplate.queryForList(sql, params);
            return mappingList(resultMap);
        }catch (Exception e) {
            logger.error("结果集转换到Model出错", e);
            return null;
        }
    }

    public T findFirst(String sql, Object... params) {
        if(!sql.toUpperCase().contains("LIMIT")) {
            sql = sql.concat("LIMIT 1");
        }

        try {
            Map<String, Object> resultMap = readTemplate.queryForMap(sql, params);
            return mapping(resultMap);
        }catch (Exception e) {
            return null;
        }

    }

    /**
     * 保存model
     *
     * @return long
     */
    public long save() {
        StringBuilder sqlBuilder = new StringBuilder("INSERT INTO ")
                .append(tableName)
                .append("(");
        List<String> fieldNames = FIELDS_MAP.get(this.getClass());
        List<Object> params = new ArrayList<>();
        StringBuilder values = new StringBuilder("");

        List<String> skipField = new ArrayList<>();
        //检查是否外键
        List<JoinMap> joinMaps = modelMap.getJoinMaps();

        if(null != joinMaps && !joinMaps.isEmpty()) {
            for(JoinMap joinMap : joinMaps) {

                //获取属性值
                Object value = getValue(FIELD_MAP.get(joinMap.getColumn()));

                if(null == value) {
                    continue;
                }
                if(value instanceof Model){
                    Model refModel = (Model)value;
                    params.add(refModel.value(refModel.FIELD_MAP.get(joinMap.getRefColumn())));
                }

                sqlBuilder.append(joinMap.getColumn())
                        .append(",");

                values.append("?")
                        .append(",");



                skipField.add(joinMap.getColumn());
            }
        }

        for (String fieldName : fieldNames) {
            //关联列已经处理, 跳过
            if(skipField.contains(fieldName)) {
                continue;
            }
            sqlBuilder.append(fieldName)
                    .append(",");

            values.append("?")
                    .append(",");

            //获取属性值
            params.add(value(FIELD_MAP.get(fieldName)));

        }
        sqlBuilder.deleteCharAt(sqlBuilder.length() - 1)
                .append(")")
                .append(" VALUES (")
                .append(values).deleteCharAt(sqlBuilder.length() - 1)
                .append(")");
        String sql = sqlBuilder.toString();
        //新增缓存
        if(Register.isUseCache && IS_CACHE_MAP.get(this.getClass())) {
            cache.setCahce(this.pkValue(), this, this.getClass());
        }
        return writeTemplate.update(sql, params.toArray());
    }

    /**
     * 更新model
     *
     * @return long
     */
    public long update() {
        List<String> fieldNames = FIELDS_MAP.get(this.getClass());
        List<Object> params = new ArrayList<>();
        List<String> skipField = new ArrayList<>();

        StringBuilder sqlBuilder = new StringBuilder("UPDATE ")
                .append(tableName)
                .append(" SET ");

        //检查是否外键
        List<JoinMap> joinMaps = modelMap.getJoinMaps();

        if(null != joinMaps && !joinMaps.isEmpty()) {
            for(JoinMap joinMap : joinMaps) {
                sqlBuilder.append(joinMap.getColumn())
                        .append((" = ?,"));

                //获取属性值
                Model refModel = (Model)value(FIELD_MAP.get(joinMap.getColumn()));
                params.add(refModel.value(refModel.FIELD_MAP.get(joinMap.getRefColumn())));
                skipField.add(joinMap.getColumn());
            }
        }

        for (String fieldName : fieldNames) {
            if (pk.equals(FIELD_MAP.get(fieldName))) {
                continue;
            }
            if(skipField.contains(fieldName)) {
                continue;
            }
            sqlBuilder.append(fieldName)
                    .append(" = ?,");
            //获取属性值
            params.add(value(FIELD_MAP.get(fieldName)));
        }
        sqlBuilder.deleteCharAt(sqlBuilder.length() - 1);
        sqlBuilder.append(" WHERE ")
                .append(PROPERTY_MAP.get(pk))
                .append(" = ?");
        params.add(pkValue());
        String sql = sqlBuilder.toString();
        //刷新缓存
        if(Register.isUseCache && IS_CACHE_MAP.get(this.getClass())) {
            cache.setCahce(pkValue(), this, this.getClass());
        }
        return writeTemplate.update(sql, params.toArray());
    }

    public long delete() {
        String sql = "DELETE FROM " + tableName + " WHERE " + PROPERTY_MAP.get(pk) + " = ?";
        //刷新缓存
        if(Register.isUseCache && IS_CACHE_MAP.get(this.getClass())) {
            cache.delCahce(pkValue(), this.getClass());
        }
        return writeTemplate.update(sql, pkValue());
    }

    public long delete(Object id) {
        String sql = "DELETE FROM " + tableName + " WHERE " + PROPERTY_MAP.get(pk) + " = ?";
        //刷新缓存
        if(Register.isUseCache && IS_CACHE_MAP.get(this.getClass())) {
            cache.delCahce(pkValue(), this.getClass());
        }
        return writeTemplate.update(sql, id);
    }

    /**
     * 异步删除数据
     */
    public void asyncDelete() {
        ThreadUtils.execute(new Runnable() {
            @Override
            public void run() {
                delete();
            }
        });
    }

    /**
     * 异步删除数据
     */
    public void asyncDelete(final DataSuccessHandler successHandler) {
        ThreadUtils.execute(new Runnable() {
            @Override
            public void run() {
                long result = delete();
                if(result > 0) {
                    successHandler.handle(result);
                }

            }
        });
    }

    /**
     * 异步删除数据
     */
    public void asyncDelete(final DataSuccessHandler successHandler, final DataFailHandler failHandler) {
        ThreadUtils.execute(new Runnable() {
            @Override
            public void run() {
                long result = delete();
                if(result > 0) {
                    successHandler.handle(result);
                }else {
                    failHandler.handle(result);
                }
            }
        });
    }

    /**
     * 异步保持model
     */
    public void asyncSave() {
        ThreadUtils.execute(new Runnable() {
            @Override
            public void run() {
                save();
            }
        });
    }

    /**
     * 异步保持model
     */
    public void asyncSave(final DataSuccessHandler successHandler) {
        ThreadUtils.execute(new Runnable() {
            @Override
            public void run() {
                long result = save();
                if(result > 0) {
                    successHandler.handle(result);
                }
            }
        });
    }

    /**
     * 异步保持model
     */
    public void asyncSave(final DataSuccessHandler successHandler, final DataFailHandler failHandler) {
        ThreadUtils.execute(new Runnable() {
            @Override
            public void run() {
               long result = save();
                if(result > 0) {
                    successHandler.handle(result);
                }else {
                    failHandler.handle(result);
                }
            }
        });
    }

    /**
     * 异步更新model
     */
    public void asyncUpdate() {
        ThreadUtils.execute(new Runnable() {
            @Override
            public void run() {
                update();
            }
        });
    }

    /**
     * 异步更新model
     */
    public void asyncUpdate(final DataSuccessHandler successHandler) {
        ThreadUtils.execute(new Runnable() {
            @Override
            public void run() {
                long result = update();
                if(result > 0) {
                    successHandler.handle(result);
                }
            }
        });
    }

    /**
     * 异步更新model
     */
    public void asyncUpdate(final DataSuccessHandler successHandler, final DataFailHandler failHandler) {
        ThreadUtils.execute(new Runnable() {
            @Override
            public void run() {
                long result = update();
                if(result > 0) {
                    successHandler.handle(result);
                }else {
                    failHandler.handle(result);
                }
            }
        });
    }

    /**
     * 根据多个属性查询
     *
     * @param params    参数
     * @param orderCol  排序列
     * @param direction 排序方向
     * @param size      返回数量
     * @return 实体列表
     */
    public List<T> findByMap(Map<String, Object> params, String orderCol, String direction, Integer size) {
        List<Object> paramList = new ArrayList<>();
        StringBuilder sqlBuilder = new StringBuilder("SELECT * FROM " + tableName + " Where 1 = 1");
        for (Map.Entry<String, Object> param : params.entrySet()) {
            sqlBuilder.append(" AND ")
                    .append(PROPERTY_MAP.get(param.getKey()))
                    .append(" = ? ");
            paramList.add(param.getValue());
        }

        if(!StringUtils.isEmpty(orderCol) && !StringUtils.isEmpty(direction)) {
            sqlBuilder.append(" ORDER BY ")
                    .append(PROPERTY_MAP.get(orderCol))
                    .append(" ")
                    .append(direction);
        }

        if(null != size) {
            sqlBuilder.append(" LIMIT ? ");
            paramList.add(size);
        }

        List<Map<String, Object>> results = readTemplate.queryForList(sqlBuilder.toString(), paramList.toArray());
        return mappingList(results);
    }

    /**
     * 根据多个属性查询
     * @param params 参数
     * @return 实体列表
     */
    public List<T> findByMap(Map<String, Object> params) {
        List<Object> paramList = new ArrayList<>();
        StringBuilder sqlBuilder = new StringBuilder("SELECT * FROM " + tableName + " Where 1 = 1");
        for (Map.Entry<String, Object> param : params.entrySet()) {
            sqlBuilder.append(" AND ")
                    .append(PROPERTY_MAP.get(param.getKey()))
                    .append(" = ? ");
            paramList.add(param.getValue());
        }
        List<Map<String, Object>> results = readTemplate.queryForList(sqlBuilder.toString(), paramList.toArray());
        return mappingList(results);
    }


    /**
     * 瀑布流分页 (暂时只支持Number类型的列)
     *
     * @param orderColName  排序列名
     * @param orderColValue 排序列值
     * @param direction     方向
     * @param params        参数(以属性名为key)
     * @param scroll      分页参数
     * @return ScrollResult
     */
    public Scroll scroll(String orderColName, Number orderColValue, String direction, Map<String, Object> params, Scroll scroll) {
        String operator = null;
        List<Object> paramList = new ArrayList<>();
        List<T> dataList = new ArrayList<>();
        String dataColName = PROPERTY_MAP.get(orderColName);
        //升序? 降序?
        if (direction.equals(Direction.ASC)) {
            operator = ">";
        } else {
            operator = "<";
        }

        //拼接语句
        StringBuilder sqlBuilder = new StringBuilder("SELECT * FROM " + tableName + " WHERE 1 = 1");
        if(!StringUtils.isEmpty(orderColValue) && orderColValue.intValue() > 0) {
            sqlBuilder.append(" ")
                    .append(dataColName)
                    .append(" ")
                    .append(operator)
                    .append(" ?");
            paramList.add(orderColValue);
        }

        if (null != params && !params.isEmpty()) {
            for (Map.Entry<String, Object> param : params.entrySet()) {
                sqlBuilder.append(" AND ")
                        .append(PROPERTY_MAP.get(param.getKey()))
                        .append(" = ? ");
                paramList.add(param.getValue());
            }
        }
        sqlBuilder.append(" ORDER BY ")
                .append(dataColName)
                .append(" ")
                .append(direction)
                .append(" LIMIT ")
                .append(scroll.getPageSize() + 1);
        List<Map<String, Object>> results = readTemplate.queryForList(sqlBuilder.toString(), paramList.toArray());

        //处理结果
        int i = 0;
        boolean hasMore = false;
        Object lastValue = null;

        if(null != results && !results.isEmpty()) {
            for (Map<String, Object> map : results) {
                i++;
                if (i >= (scroll.getPageSize() + 1)) {
                    hasMore = true;
                    break;
                }
                dataList.add(mapping(map));
                lastValue = map.get(dataColName);

            }
        }

        scroll.setHasMore(hasMore);
        scroll.setData(dataList);
        scroll.setLastValue(lastValue);
        scroll.setPageSize(scroll.getPageSize());
        return scroll;
    }

    public Page<T> pager(Page<T> page, Map<String, Object> params, String orderCol, String direction) {
        List<Object> paramList = new ArrayList<>();
        List<T> dataList = new ArrayList<>();
        int beginIndex = (page.getPageNumber() - 1) * page.getPageSize();

        StringBuilder sqlBuilder = new StringBuilder("SELECT * FROM " + tableName);

        //拼接参数
        if (null != params && !params.isEmpty()) {
            for (Map.Entry<String, Object> param : params.entrySet()) {
                sqlBuilder.append(" AND ")
                    .append(PROPERTY_MAP.get(param.getKey()))
                    .append(" = ? ");
                paramList.add(param.getValue());
            }
        }

        //获取总数量
        Map<String, Object> count = readTemplate.queryForMap(sqlBuilder.toString().replace("*", "COUNT(1) as totalSize "));
        int totalSize = Integer.valueOf(count.get("totalSize").toString());
        int totalPage = totalSize % page.getPageSize() == 0 ? totalSize / page.getPageSize() : totalSize / page.getPageSize() + 1;

        //排序列
        if(!StringUtils.isEmpty(orderCol)) {
            sqlBuilder.append(" ORDER BY ")
                    .append(PROPERTY_MAP.get(orderCol))
                    .append(" ")
                    .append(direction);
        }

        //拼接分页参数
        sqlBuilder.append(" LIMIT ")
                .append(beginIndex)
                .append(", ")
                .append(page.getPageSize());

        List<Map<String, Object>> results = readTemplate.queryForList(sqlBuilder.toString(), paramList.toArray());
        if(!CollectionUtils.isEmpty(results)) {
            for (Map<String, Object> map : results) {
                dataList.add(mapping(map));
            }
        }

        page.setTotalPage(totalPage);
        page.setTotalRow(totalSize);
        page.setList(dataList);
        return page;
    }

    public T findById(Object id) {
        String pkField = PK_MAP.get(this.getClass());
        String pkColumn = PROPERTY_MAP.get(pkField);
        T model = null;
        if(Register.isUseCache && IS_CACHE_MAP.get(this.getClass())) {
           model = cache.getCache(id, this.getClass());
        }

        if(null == model) {
            String sql = "SELECT * FROM " + tableName + " WHERE " + pkColumn + " = ?";
            model = mapping(readTemplate.queryForMap(sql, id));

            //刷新缓存
            if(Register.isUseCache && IS_CACHE_MAP.get(this.getClass())) {
                cache.setCahce(id, model, this.getClass());
            }

        }

        return model;
    }

    /**
     * 获取属性值
     *
     * @param propertyName 属性名
     * @return Object
     */
    public Object value(String propertyName) {
        Method method = GETTERS_MAP.get(propertyName);
        try {
            Object result = method.invoke(this);
            mapData.put(propertyName, result);
            return result;
        } catch (IllegalAccessException | InvocationTargetException e) {
            logger.error("执行" + propertyName + " 的getter失败");
            e.printStackTrace();
        }
        return null;
    }

    public void value(T model, String propertyName, Object value) {
        Method method = SETTERS_MAP.get(propertyName);
        try {
            method.invoke(model, value);
        } catch (InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * list转换成modelList
     *
     * @param mapList 数据库记录
     * @return ist<Model>
     */
    public List<T> mappingList(List<Map<String, Object>> mapList) {
        List<T> models = new ArrayList<>();
        for (Map<String, Object> map : mapList) {
            models.add(mapping(map));
        }
        return models;
    }

    /**
     * map转换成model
     *
     * @param map 数据库记录
     * @return Model
     */
    public T mapping(Map<String, Object> map) {
        T model = null;
        try {
            model = (T)this.getClass().newInstance();

            List<String> joinColumns = new ArrayList<>();

            //检查是否有外键关联
            List<JoinMap> joinMaps = modelMap.getJoinMaps();
            for(JoinMap joinMap : joinMaps) {
                if(null != joinMap) {
                    Model refModel = (Model) joinMap.getType().newInstance();
                    Object columnVal = map.get(joinMap.getColumn());
                    try {
                        value(model, joinMap.getPropertyName(), refModel.findById(columnVal));
                        joinColumns.add(joinMap.getColumn());
                    }catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                //空值字段直接忽略
                if (null == entry.getValue()) {
                    continue;
                }

                //如果是关联属性忽略
                if (joinColumns.contains(entry.getKey())) {
                    continue;
                }

                String field = FIELD_MAP.get(entry.getKey());
                Method setter = SETTERS_MAP.get(field);

                //没有在映射的字段也忽略
                if (StringUtils.isEmpty(field) || null == setter) {
                    continue;
                }
                setter.invoke(model, entry.getValue());
            }

        } catch (InvocationTargetException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return model;
    }

    /**
     * 转换成json字符串
     * @return String
     */
    public String toJsonString() {
        if(StringUtils.isEmpty(jsonString)) {
            jsonString = JSON.toJSONString(this);
        }
        return jsonString;
    }

    /**
     * 转换成Map
     * @return Map
     */
    public Map<String, Object> toMap(){
        return mapData;
    }

    /**
     * 转换成json对象
     * @return JSONObject
     */
    public JSONObject toJsonObject() {
        if(StringUtils.isEmpty(jsonObject)) {
            jsonObject = new JSONObject(mapData);
        }
        return jsonObject;
    }

    /**
     * 获取主键值
     * ps: 经过反射获取值, 当然不需要就不用啦
     * @return Object
     */
    public Object pkValue() {
        return value(pk);
    }
}

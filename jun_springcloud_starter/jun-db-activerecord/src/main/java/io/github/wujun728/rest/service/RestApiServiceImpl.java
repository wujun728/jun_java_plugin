package io.github.wujun728.rest.service;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import io.github.wujun728.common.base.Result;
import io.github.wujun728.db.Db;
import io.github.wujun728.db.Record;
import io.github.wujun728.db.utils.DataSourcePool;
import io.github.wujun728.db.utils.RecordUtil;
import io.github.wujun728.rest.entity.ApiDataSource;
import io.github.wujun728.rest.entity.ApiSql;
import io.github.wujun728.rest.plugin.PluginManager;
import io.github.wujun728.rest.plugin.TransformPlugin;
import io.github.wujun728.sql.SqlXmlUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static io.github.wujun728.db.utils.DataSourcePool.main;


//import static io.github.wujun728.rest.util.DataSourcePool.main;

@Slf4j
@Service
public class RestApiServiceImpl implements IRestApiService {


    @Override
    public Object doSQLProcess(ApiSql apiSql1, Map<String, Object> parameters) throws SQLException {
        try {

            Map<String, Object> params = parameters;
//			if(MapUtil.getStr(params,"pageNumber")!=null && MapUtil.getStr(params,"pageSize")!=null ){
//				Integer size = Convert.convert(Integer.class, params.get("pageSize"));
//				Integer page = Convert.convert(Integer.class, params.get("pageNumber"));
//				params.put("pageSize", size);
//				params.put("pageNumber", size*(page-1));
//			}
            List<ApiSql> sqlList = Arrays.asList(apiSql1);
            if (CollectionUtils.isEmpty(params) && !CollectionUtils.isEmpty(sqlList) && JSON.toJSONString(sqlList).contains("#")) {
                return Result.fail("Request parameter is not exists(请求入参不能为空)!");
            }
            DataSource ds = DataSourcePool.get(apiSql1.getDatasourceId());
            if (ds == null ) {
                ds = DataSourcePool.get(main);
            }
            Connection connection = ds.getConnection();
            // 是否开启事务
            boolean flag = true;//config.getOpenTrans() == 1 ? true : false;
            // 执行sql
            List<Object> dataList = executeSql(connection, sqlList, params, flag);
            // 执行数据转换
            for (int i = 0; i < sqlList.size(); i++) {
                ApiSql apiSql = sqlList.get(i);
                Object data = dataList.get(i);
                // 如果此单条sql是查询类sql，并且配置了数据转换插件
                if (data instanceof Iterable && StringUtils.isNotBlank(apiSql.getTransformPlugin())) {
                    log.info("transform plugin execute");
                    List<JSONObject> sourceData = (List<JSONObject>) (data); // 查询类sql的返回结果才可以这样强制转换，只有查询类sql才可以配置转换插件
                    TransformPlugin transformPlugin = (TransformPlugin) PluginManager.getPlugin(apiSql.getTransformPlugin());
                    Object resData = transformPlugin.transform(sourceData, apiSql.getTransformPluginParams());
                    dataList.set(i, resData);// 重新设置值
                }
            }
            Object res = dataList;
            // 如果只有单条sql,返回结果不是数组格式
            if (dataList.size() == 1) {
                res = dataList.get(0);
            }
            // 设置缓存
            /*if (StringUtils.isNoneBlank(apiSql1.getCachePlugin())) {
                CachePlugin cachePlugin = (CachePlugin) PluginManager.getPlugin(apiSql1.getCachePlugin());
                Api Api = new Api();
                BeanUtil.copyProperties(config,Api, false);
                cachePlugin.set(Api, params, res);
            }*/
            return res;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public ApiDataSource getDatasource(String id) {
        ApiDataSource info = new ApiDataSource();
        Record record= Db.use(main).findById( "api_datasource" , id);
        return RecordUtil.recordToBean(record,ApiDataSource.class);
    }


    public List<Object> executeSql(Connection connection, List<ApiSql> sqlList, Map<String, Object> sqlParam, boolean flag) {
        List<Object> dataList = new ArrayList<>();
        try {
            if (flag)
                connection.setAutoCommit(false);
            else
                connection.setAutoCommit(true);
            for (ApiSql apiSql : sqlList) {
//				SqlMeta sqlMeta = JdbcUtil.getEngine().parse(apiSql.getSqlText(), sqlParam);
//				Object data = JdbcUtil.executeSql(connection, sqlMeta.getSql(), sqlMeta.getJdbcParamValues());
                Object data = SqlXmlUtil.executeSql(connection, apiSql.getSqlText(), sqlParam);
                dataList.add(data);
            }
            if (flag)
                connection.commit();
            return dataList;
        } catch (Exception e) {
            try {
                if (flag)
                    connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            throw new RuntimeException(e);
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

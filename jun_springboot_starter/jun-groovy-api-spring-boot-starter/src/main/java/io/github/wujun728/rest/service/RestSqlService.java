package io.github.wujun728.rest.service;

import cn.hutool.core.map.MapUtil;
import cn.hutool.db.meta.MetaUtil;
import cn.hutool.db.meta.Table;
import cn.hutool.extra.spring.SpringUtil;
import com.alibaba.fastjson2.JSON;
import com.google.common.collect.Maps;
import freemarker.template.TemplateException;
import io.github.wujun728.common.base.Result;
import io.github.wujun728.db.record.Db;
import io.github.wujun728.db.record.Record;
import io.github.wujun728.sql.DataSourcePool;
import io.github.wujun728.db.utils.RecordUtil;
import io.github.wujun728.generator.CodeUtil;
import io.github.wujun728.generator.entity.ClassInfo;
import io.github.wujun728.rest.util.DbUtil;
import io.github.wujun728.sql.entity.ApiDataSource;
import io.github.wujun728.sql.entity.ApiSql;
import io.github.wujun728.sql.utils.JdbcUtil;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;

import static io.github.wujun728.db.record.Db.main;


@Service
public class RestSqlService {

    public void init(String tableName, Map<String, Object> parameters) throws SQLException, TemplateException, IOException {
        /*String url = "jdbc:mysql://localhost:3306/db_qixing_bk?useUnicode=true&characterEncoding=UTF-8&useSSL=true&serverTimezone=UTC&useInformationSchema=true";
        String username = "root";
        String password = "";
        DataSource dataSource = DataSourcePool.init("main",url,username,password);
        Db.init(main,dataSource);*/
        DataSource dataSource = SpringUtil.getBean(DataSource.class);
        String entityName = MapUtil.getStr(parameters,"entityName");
        Boolean isUnderLine = entityName.equals(tableName);
        Table table =  MetaUtil.getTableMeta(dataSource,tableName);
        List<Record> sqlList1 = Db.use(main).find("select * from api_sql ");
        List<ApiSql> sqlList = RecordUtil.recordToBeans(sqlList1,ApiSql.class);// Db.use(main).findBeanList(ApiSql.class,"select * from api_sql ");
        ApiSql apiSql = new ApiSql();
        String sqlType = "insert";
        String path = "/"+entityName+"/"+sqlType;
        String id = entityName+"_"+sqlType;
        apiSql.setId(id);
        apiSql.setPath(path);
        apiSql.setType("gen");
        apiSql.setGroup(entityName);
        apiSql.setText(getSQLText(dataSource,tableName,sqlType));
        //Db.use(main).saveBean(apiSql);
        Db.use(main).save("api_sql",RecordUtil.beanToRecord(apiSql));

        sqlType = "update";
        path = "/"+entityName+"/"+sqlType;
        id = entityName+"_"+sqlType;
        apiSql.setId(id);
        apiSql.setPath(path);
        apiSql.setType("gen");
        apiSql.setText(getSQLText(dataSource,tableName,sqlType));
        //Db.use(main).saveBean(apiSql);
        Db.use(main).save("api_sql",RecordUtil.beanToRecord(apiSql));

        sqlType = "delete";
        path = "/"+entityName+"/"+sqlType;
        id = entityName+"_"+sqlType;
        apiSql.setId(id);
        apiSql.setPath(path);
        apiSql.setType("gen");
        apiSql.setText(getSQLText(dataSource,tableName,sqlType));
        //Db.use(main).saveBean(apiSql);
        Db.use(main).save("api_sql",RecordUtil.beanToRecord(apiSql));

        sqlType = "page";
        path = "/"+entityName+"/"+sqlType;
        id = entityName+"_"+sqlType;
        apiSql.setId(id);
        apiSql.setPath(path);
        apiSql.setType("gen");
        apiSql.setText(getSQLText(dataSource,tableName,sqlType));
        //Db.use(main).saveBean(apiSql);
        Db.use(main).save("api_sql",RecordUtil.beanToRecord(apiSql));

        sqlType = "count";
        path = "/"+entityName+"/"+sqlType;
        id = entityName+"_"+sqlType;
        apiSql.setId(id);
        apiSql.setPath(path);
        apiSql.setType("gen");
        apiSql.setText(getSQLText(dataSource,tableName,sqlType));
        //Db.use(main).saveBean(apiSql);
        Db.use(main).save("api_sql",RecordUtil.beanToRecord(apiSql));

        sqlType = "one";
        path = "/"+entityName+"/"+sqlType;
        id = entityName+"_"+sqlType;
        apiSql.setId(id);
        apiSql.setPath(path);
        apiSql.setType("gen");
        apiSql.setText(getSQLText(dataSource,tableName,sqlType));
        //Db.use(main).saveBean(apiSql);
        Db.use(main).save("api_sql",RecordUtil.beanToRecord(apiSql));
    }

    String getSQLText(DataSource dataSource,String tableName,String sqlType){
        ClassInfo classInfo = CodeUtil.getClassInfo(dataSource,tableName);
        HashMap<String, Object> map = Maps.newHashMap();
        map.put("classInfo",classInfo);
        String sql = "";
        try {
            if("insert".equalsIgnoreCase(sqlType)){
                sql = DbUtil.insertSQL(map);
            }
            if("update".equalsIgnoreCase(sqlType)){
                sql = DbUtil.updateSQL(map);
            }
            if("delete".equalsIgnoreCase(sqlType)){
                sql = DbUtil.deleteSQL(map);
            }
            if("page".equalsIgnoreCase(sqlType)){
                sql = DbUtil.pageListSQL(map);
            }
            if("count".equalsIgnoreCase(sqlType)){
                sql = DbUtil.pageListCountSQL(map);
            }
            if("one".equalsIgnoreCase(sqlType)){
                sql = DbUtil.loadSQL(map);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (TemplateException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return sql;
    }

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
            Object res = dataList;
            // 如果只有单条sql,返回结果不是数组格式
            if (dataList.size() == 1) {
                res = dataList.get(0);
            }
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
                Object data = JdbcUtil.executeSql(connection, apiSql.getText(), sqlParam);
                dataList.add(data);
            }
            if (flag){
                if(!connection.isClosed()){
                    connection.commit();
                }
            }
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
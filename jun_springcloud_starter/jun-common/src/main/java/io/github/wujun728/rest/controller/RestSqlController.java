package io.github.wujun728.rest.controller;

import cn.hutool.db.meta.MetaUtil;
import cn.hutool.db.meta.Table;
import com.google.common.collect.Maps;
import io.github.wujun728.common.Result;
import io.github.wujun728.common.utils.HttpRequestUtil;
import io.github.wujun728.db.record.*;
import io.github.wujun728.rest.entity.ApiSql;
import io.github.wujun728.rest.service.IRestApiService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import static io.github.wujun728.db.DataSourcePool.main;

/**
 * @author Wujun
 * @desc 通用Rest服务接口
 */
@Slf4j
@org.springframework.web.bind.annotation.RestController
@RequestMapping({"${platform.path:}/rest", "${platform.path:}/public/rest"})
//@Api(value = "实体公共增删改查接口")
public class RestSqlController {

    @Resource
    IRestApiService restApiService;

    static AtomicReference<Map<String, Table>> tableCache = new AtomicReference<>();

    static {
        tableCache.set(Maps.newHashMap());
    }


    private static Result check(String tableName) {
        return check(tableName, main);
    }
    private static Result check(String tableName,String ds) {
        Map map = tableCache.get();
        if (!map.containsKey(tableName)) {
            Table table = MetaUtil.getTableMeta(Db.use(ds).getConfig().getDataSource(), tableName);
            map.put(tableName, table);
            tableCache.set(map);
            if (CollectionUtils.isEmpty(table.getColumns())) {
                return Result.fail("实体对应的表不存在！");
            }
        }
        return null;
    }


    @RequestMapping(path = {"/exec/{path}"}, produces = "application/json")
    public Result apiExecute(@PathVariable String path ,HttpServletRequest request, HttpServletResponse response) throws SQLException {
        Map<String, Object> parameters = HttpRequestUtil.getAllParameters(request);
        List<Record> records  = Db.use(main).find(" select * from api_sql ");
        List<ApiSql> apiSqls = RecordUtil.recordToListBean(records, ApiSql.class);
        Map<String, ApiSql> apiSqlMap = apiSqls.stream().collect(Collectors.toMap(i->i.getSqlId(),i->i));
        if(apiSqlMap.containsKey(path)){
            Object obj = restApiService.doSQLProcess(apiSqlMap.get(path), parameters);
            return Result.success(obj);
        }else{
            return Result.error("执行的接口不存在" );
        }
    }



}

package io.github.wujun728.rest.controller;

import cn.hutool.core.map.MapUtil;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import io.github.wujun728.common.Result;
import io.github.wujun728.db.RecordUtil;
import io.github.wujun728.rest.util.HttpRequestUtil;
import io.github.wujun728.rest.entity.ApiSql;
import io.github.wujun728.rest.service.IRestApiService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Wujun
 * @desc 通用Rest服务接口
 */
@Slf4j
@org.springframework.web.bind.annotation.RestController
@RequestMapping({"${platform.path:}/rest", "${platform.path:}/public/rest"})
//@Api(value = "实体公共增删改查接口")
public class RestSqlController {

    private String main = "main";
    @Resource
    IRestApiService restApiService;

    @RequestMapping(path = {"/exec/{path}"}, produces = "application/json")
    public Result apiExecute(@PathVariable String path ,HttpServletRequest request, HttpServletResponse response) throws SQLException {
        Map<String, Object> parameters = HttpRequestUtil.getAllParameters(request);
        main = MapUtil.getStr(parameters, "ds");
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

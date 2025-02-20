package io.github.wujun728.rest.controller;

import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.log.StaticLog;
import com.alibaba.fastjson.JSONObject;
import io.github.wujun728.common.base.Result;
import io.github.wujun728.db.record.Db;
import io.github.wujun728.db.record.Page;
import io.github.wujun728.db.utils.DataSourcePool;
import io.github.wujun728.rest.service.SqlService;
import io.github.wujun728.rest.util.HttpRequestUtil;
import io.github.wujun728.sql.entity.ApiSql;
import io.github.wujun728.sql.utils.JdbcUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @author Wujun
 * @desc 通用Rest Sql服务接口
 */
@Slf4j
@RestController
@RequestMapping({"${platform.path:}/apis"})
//@Api(value = "实体公共增删改查接口")
public class RestSqlController {

    @Resource
    SqlService sqlService;

    private String main = "main";

    @GetMapping(path = {"/{entityName}/init"}, produces = "application/json")
    //@ApiOperation(value = "返回实体数据列表", notes = "page与size同时大于零时返回分页实体数据列表,否则返回全部数据列表;
    public Result init(@PathVariable("entityName") String entityName, HttpServletRequest request) throws Exception {
        Map<String, Object> parameters = HttpRequestUtil.getAllParameters(request);
        main = MapUtil.getStr(parameters, "ds","main");
        String tableName = StrUtil.toUnderlineCase(entityName);
        Boolean isUnderLine = entityName.equals(tableName);
        try {
            String url = request.getRequestURI();
            StaticLog.info("url = "+ url);
            parameters.put("entityName" , entityName);
            parameters.put("tableName" , tableName);
            sqlService.init(tableName,parameters);
            return Result.success("接口初始化成功");
        } catch (Exception e) {
            String message = ExceptionUtils.getMessage(e);
            log.error(message, e);
            return Result.error(message);
        }
    }

    @RequestMapping(path = {"/{entityName}/{action}"}, produces = "application/json")
    //@ApiOperation(value = "返回实体数据列表", notes = "page与size同时大于零时返回分页实体数据列表,否则返回全部数据列表;
    public Result list(@PathVariable("entityName") String entityName,@PathVariable("action") String action, HttpServletRequest request) throws Exception {
        Map<String, Object> parameters = HttpRequestUtil.getAllParameters(request);
        String tableName = StrUtil.toUnderlineCase(entityName);
        Boolean isUnderLine = entityName.equals(tableName);
        try {
            String url = request.getRequestURI();
            StaticLog.info("url = "+ url);
            parameters.put("entityName" , entityName);
            parameters.put("tableName" , tableName);

            String sqlType = action;
            String path = "/"+entityName+"/"+sqlType;
            ApiSql apiSql = Db.use(main).findBeanById(ApiSql.class,"path",path);
            if(ObjectUtil.isNotEmpty(apiSql)){
                if("one".equalsIgnoreCase(action)  || "list".equalsIgnoreCase(action)  || "query".equalsIgnoreCase(action) ){
                    List<Map<String, Object>> datas = JdbcUtil.query(DataSourcePool.getConnection(main),apiSql.getText(),parameters);
                    return Result.success(datas);
                }else if( "page".equalsIgnoreCase(action) ){
                    Integer page = MapUtil.getInt(parameters, "page");
                    if ((page == null || page == 0)  ) {
                        page = 1;
                    }
                    Integer limit = MapUtil.getInt(parameters, "limit");
                    if ( (limit == null || limit == 0)) {
                        limit = 10;
                    }
                    Page<JSONObject> pages = JdbcUtil.executeQueryPage(DataSourcePool.getConnection(main),apiSql.getText(),parameters,page,limit);
                    return Result.success(pages.getList()).put("count", pages.getTotalRow()).put("pageSize", pages.getPageSize()).put("totalPage", pages.getTotalPage()).put("pageNumber", pages.getPageNumber());
                }else if("insert".equalsIgnoreCase(action) || "update".equalsIgnoreCase(action)  || "delete".equalsIgnoreCase(action)){
                    int flag = JdbcUtil.update(DataSourcePool.getConnection(main),apiSql.getText(),parameters);
                    if(flag>0){
                        return Result.success(true);
                    }else{
                        return Result.fail();
                    }
                }if("count".equalsIgnoreCase(action)){
                    Long datas = JdbcUtil.count(DataSourcePool.getConnection(main),apiSql.getText(),parameters);
                    return Result.success(datas);
                }
                else {
                    Object obj = JdbcUtil.executeSql(DataSourcePool.getConnection(main),apiSql.getText(),parameters);
                    return Result.success(obj);
                }
            }else{
                return Result.error("接口["+path+"]不存在");
            }
        } catch (Exception e) {
            String message = ExceptionUtils.getMessage(e);
            log.error(message, e);
            return Result.error(message);
        }
    }


}

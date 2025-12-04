package io.github.wujun728.amis.api;


import cn.hutool.core.map.MapUtil;
import cn.hutool.json.JSONObject;
import io.github.wujun728.common.base.Result;
import io.github.wujun728.db.record.Page;
import io.github.wujun728.rest.util.HttpRequestUtil;
import io.github.wujun728.sql.ApiEngine;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Slf4j
@RequestMapping("/${platform.path:}/apisql/")
@RestController
public class ApiSqlController {

    @Autowired
    ApiEngine engine;

    @RequestMapping("/list")
    public List<JSONObject> getAllStudent() {
        List<JSONObject> jsonObjects = engine.executeQuery("apiSql", "page");
        return jsonObjects;
    }

    @GetMapping(path = {  "/{entityName}/page" }, produces = "application/json")
    //@ApiOperation(value = "返回实体数据列表", notes = "page与size同时大于零时返回分页实体数据列表,否则返回全部数据列表;
    public Result page(@PathVariable("entityName") String entityName, HttpServletRequest request) throws Exception {
        Map<String, Object> parameters = HttpRequestUtil.getAllParameters(request);
        try {
            Integer page = MapUtil.getInt(parameters, "page");
            if ((page == null || page == 0)  ) {
                page = 1;
            }
            Integer limit = MapUtil.getInt(parameters, "limit");
            if ( (limit == null || limit == 0)) {
                limit = 10;
            }
            Page<JSONObject> pages = engine.executeQueryPage("apiSql", "page",parameters,page,limit);
            return Result.success(pages.getList()).put("count", pages.getTotalRow()).put("pageSize", pages.getPageSize()).put("totalPage", pages.getTotalPage()).put("pageNumber", pages.getPageNumber());
        } catch (Exception e) {
            String message = ExceptionUtils.getMessage(e);
            log.error(message, e);
            return Result.error(message);
        }
    }

    @GetMapping(path = {  "/{entityName}/list" }, produces = "application/json")
    //@ApiOperation(value = "返回实体数据列表", notes = "page与size同时大于零时返回分页实体数据列表,否则返回全部数据列表;
    public Result list(@PathVariable("entityName") String entityName, HttpServletRequest request) throws Exception {
        Map<String, Object> parameters = HttpRequestUtil.getAllParameters(request);
        try {
            List<JSONObject> datas = engine.executeQuery("apiSql", "list",parameters);
            return Result.success(datas);
        } catch (Exception e) {
            String message = ExceptionUtils.getMessage(e);
            log.error(message, e);
            return Result.error(message);
        }
    }
    @GetMapping(path = {  "/{entityName}/one" }, produces = "application/json")
    //@ApiOperation(value = "返回实体数据列表", notes = "page与size同时大于零时返回分页实体数据列表,否则返回全部数据列表;
    public Result one(@PathVariable("entityName") String entityName, HttpServletRequest request) throws Exception {
        Map<String, Object> parameters = HttpRequestUtil.getAllParameters(request);
        try {
            JSONObject datas = engine.executeQueryOne("apiSql", "one",parameters);
            return Result.success(datas);
        } catch (Exception e) {
            String message = ExceptionUtils.getMessage(e);
            log.error(message, e);
            return Result.error(message);
        }
    }
    @GetMapping(path = {  "/{entityName}/update","/{entityName}/insert","/{entityName}/delete" }, produces = "application/json")
    public Result update(@PathVariable("entityName") String entityName, HttpServletRequest request) throws Exception {
        Map<String, Object> parameters = HttpRequestUtil.getAllParameters(request);
        try {
            int flag = engine.executeUpdate("apiSql", "update",parameters);
            if(flag>0){
                return Result.success(flag);
            }else{
                return Result.fail("操作失败");
            }
        } catch (Exception e) {
            String message = ExceptionUtils.getMessage(e);
            log.error(message, e);
            return Result.error(message);
        }
    }
    @GetMapping(path = {  "/{entityName}/insert"  }, produces = "application/json")
    public Result insert(@PathVariable("entityName") String entityName, HttpServletRequest request) throws Exception {
        Map<String, Object> parameters = HttpRequestUtil.getAllParameters(request);
        try {
            int flag = engine.executeUpdate("apiSql", "insert",parameters);
            if(flag>0){
                return Result.success(flag);
            }else{
                return Result.fail("操作失败");
            }
        } catch (Exception e) {
            String message = ExceptionUtils.getMessage(e);
            log.error(message, e);
            return Result.error(message);
        }
    }
    @GetMapping(path = {  "/{entityName}/delete" }, produces = "application/json")
    public Result delete(@PathVariable("entityName") String entityName, HttpServletRequest request) throws Exception {
        Map<String, Object> parameters = HttpRequestUtil.getAllParameters(request);
        try {
            int flag = engine.executeUpdate("apiSql", "delete",parameters);
            if(flag>0){
                return Result.success(flag);
            }else{
                return Result.fail("操作失败");
            }
        } catch (Exception e) {
            String message = ExceptionUtils.getMessage(e);
            log.error(message, e);
            return Result.error(message);
        }
    }


}

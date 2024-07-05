package io.github.wujun728.rest.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.lang.tree.TreeNodeConfig;
import cn.hutool.core.lang.tree.TreeUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.db.meta.Column;
import cn.hutool.db.meta.Table;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSONUtil;
import cn.hutool.log.StaticLog;
import com.google.common.collect.Maps;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import io.github.wujun728.common.Result;
import io.github.wujun728.common.exception.BusinessException;
import io.github.wujun728.rest.util.FieldUtils;
import io.github.wujun728.rest.util.HttpRequestUtil;
import io.github.wujun728.rest.util.RecordUtil;
import io.github.wujun728.rest.util.RestUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @author Wujun
 * @desc 通用Rest服务接口
 */
@Slf4j
@org.springframework.web.bind.annotation.RestController
@RequestMapping({"${platform.path:}/rest", "${platform.path:}/public/rest"})
//@Api(value = "实体公共增删改查接口")
public class RestApiController extends BaseController{

    private String main = "main";

    @GetMapping(path = {"/{entityName}/list", "/{entityName}/page","/{entityName}/tree"}, produces = "application/json")
    //@ApiOperation(value = "返回实体数据列表", notes = "page与size同时大于零时返回分页实体数据列表,否则返回全部数据列表;
    public Result list(@PathVariable("entityName") String entityName, HttpServletRequest request) throws Exception {
        Map<String, Object> parameters = HttpRequestUtil.getAllParameters(request);
        main = MapUtil.getStr(parameters, "ds","main");
        String tableName = StrUtil.toUnderlineCase(entityName);
        Boolean isUnderLine = entityName.equals(tableName);
        try {
            String url = request.getRequestURI();
            StaticLog.info("url = "+ url);
            parameters.put("entityName" , entityName);
            parameters.put("tableName" , tableName);
            Table table = getTableMeta(tableName,main);

            StringBuffer sql = new StringBuffer();
            String select = "select *";
            sql.append(select);
            String from = " from " + tableName;
            sql.append(from);
            String where = RestUtil.getQueryCondition(parameters,table);
            if(StrUtil.isNotEmpty(where)){
                from = from + " where 1=1 "+ where;
            }
            Boolean isPage = url.contains("page")||parameters.containsKey("page") ?true:false;
            Boolean isTree = url.contains("tree") ?true:false;
            if (isPage) {
                Integer page = MapUtil.getInt(parameters, "page");
                Integer limit = MapUtil.getInt(parameters, "limit");
                if ((page == null || page == 0) || (limit == null || limit == 0)) {
                    page = 1;
                    limit = 10;
                }
                Page<Record> pages = Db.use(main).paginate(page, limit, select, from);
                List<Map<String, Object>> datas = RecordUtil.recordToMaps(pages.getList(),isUnderLine);
                return Result.success(datas).put("count", pages.getTotalRow()).put("pageSize", pages.getPageSize()).put("totalPage", pages.getTotalPage()).put("pageNumber", pages.getPageNumber());
            } else {
                List<Record> datas1 = Db.use(main).find(sql.toString());
                List<Map<String,Object>> datas = RecordUtil.recordToMaps(datas1,isUnderLine);
                //是否构建树 begin
                if(isTree){
                    String treeId = MapUtil.getStr(parameters, "id") == null ? "id" : MapUtil.getStr(parameters, "id");
                    String treePid = MapUtil.getStr(parameters, "pid") == null ? "pid" : MapUtil.getStr(parameters, "pid");
                    Object rootId = parameters.get("rootId") == null ? 0L : parameters.get("rootId");
                    if (StrUtil.isNotEmpty(treePid)) {
                        List treeList = RestUtil.listToTree(datas,String.valueOf(rootId),treeId,treePid);
                        /*TreeNodeConfig treeNodeConfig = new TreeNodeConfig();
                        treeNodeConfig.setIdKey(treeId);
                        treeNodeConfig.setParentIdKey(treePid);
                        List<Tree<Long>> treeList = TreeUtil.build(datas, Long.valueOf(String.valueOf(rootId)), treeNodeConfig, (map, tree) -> {
                            // 也可以使用 tree.setId(object.getId());等一些默认值
                            tree.setId(Long.valueOf(String.valueOf(map.get(treeId))));
                            tree.setParentId(Long.valueOf(String.valueOf(map.get(treePid))));
                            //tree.putExtra("name", object.getName());
                            map.keySet().stream().forEach(k->{
                                if(!k.equals(treeId) && !k.equals(treePid) ){
                                    tree.putExtra(String.valueOf(k),map.get(k));
                                }
                            });
                        });*/
                        StaticLog.info(JSONUtil.toJsonPrettyStr(treeList));
                        return Result.success(treeList);
                    }
                }
                return Result.success(datas);
                //是否构建树 end
            }
        } catch (Exception e) {
            String message = ExceptionUtils.getMessage(e);
            log.error(message, e);
            return Result.error(message);
        }
    }

    private static List buildTree(List<Map> datas, String treeId, String treePid, Object rootId) {
        TreeNodeConfig treeNodeConfig = new TreeNodeConfig();
        treeNodeConfig.setIdKey(treeId);
        treeNodeConfig.setParentIdKey(treePid);
        //treeNodeConfig.setNameKey("name");
        List<Tree<Object>> treeList = TreeUtil.build(datas, rootId, treeNodeConfig, (map, tree) -> {
            tree.setId(map.get(treeId));
            tree.setParentId(map.get(treePid));
            //tree.setName(map.getName());
            //tree.put("name",map.getName());
            tree.putAll(map);
        });
        System.out.println(JSONUtil.toJsonPrettyStr(treeList));
        return treeList;
    }

    @RequestMapping(path = {"/{entityName}/findOne","/{entityName}/getOne","/{entityName}/record","/{entityName}/row"}, produces = "application/json")
    //@ApiOperation(value = "根据ID返回单个实体数据")
    public Result findOne(@PathVariable("entityName") String entityName, HttpServletRequest request) throws Exception {
        Map<String, Object> parameters = HttpRequestUtil.getAllParameters(request);
        main = MapUtil.getStr(parameters, "ds","main");
        String tableName = StrUtil.toUnderlineCase(entityName);
        Boolean isUnderLine = entityName.equals(tableName);
        try {
            parameters.put("entityName" , entityName);
            parameters.put("tableName" , tableName);
            Table table = getTableMeta(tableName,main);
            String primaryKey = RestUtil.getTablePrimaryKes(table);
            List args = RestUtil.getPrimaryKeyArgs(parameters, table);
            Record record = Db.use(main).findByIds(tableName, primaryKey, args.toArray());
//            Record record = Db.use(main).findById(tableName,primaryKey, (Number) args.get(0));
            if (ObjectUtil.isNotNull(record)) {
                Map data = RecordUtil.recordToMap(record,isUnderLine);
                return Result.success(data);
            } else {
                return Result.fail("无此ID对应的记录！");
            }
        } catch (Exception e) {
            String message = ExceptionUtils.getMessage(e);
            log.error(message, e);
            return Result.error(message);
        }
    }

    @RequestMapping(path = "/{entityName}/delete", produces = "application/json")
    //@ApiOperation(value = "根据id删除实体数据" )
    public Result delete(@PathVariable("entityName") String entityName, HttpServletRequest request) throws Exception {
        Map<String, Object> parameters = HttpRequestUtil.getAllParameters(request);
        main = MapUtil.getStr(parameters, "ds","main");
        String tableName = StrUtil.toUnderlineCase(entityName);
        try {
            parameters.put("entityName" , entityName);
            parameters.put("tableName" , tableName);
            Table table = getTableMeta(tableName,main);
            String primaryKey = RestUtil.getTablePrimaryKes(table);
            List args = RestUtil.getPrimaryKeyArgs(parameters, table);
            //Boolean flag = Db.use(main).tx(() -> Db.use(main).deleteByIds(tableName, primaryKey, args.toArray()));
            Boolean flag =   Db.use(main).deleteByIds(tableName, primaryKey, args.toArray());
//            Boolean flag = Db.use(main).deleteByIds(tableName, primaryKey, args.toArray());
            if (flag) {
                return Result.success("删除成功！",flag);
            } else {
                return Result.fail("删除失败！");
            }
        } catch (Exception e) {
            String message = ExceptionUtils.getMessage(e);
            if (message.contains("Unknown column")) {
                throw new BusinessException("接口必须参数id,可选参数primaryKey，其中primaryKey中的列在数据库不存在");
            }
            if (message.contains("number must equals id value number")) {
                throw new BusinessException("接口必须参数id,可选参数primaryKey，有多列，均使用逗号分隔，当前参数个数与值的个数不一致");
            }
            log.error(message, e);
            return Result.error(message);
        }
    }


    @RequestMapping(path = {"/{entityName}/save","/{entityName}/add"}, produces = "application/json")
    //@ApiOperation(value = "新增实体数据", notes = "{\"name\":\"tom\",\"args\":1}")
    public Result create(@PathVariable("entityName") String entityName, HttpServletRequest request) {
        Map<String, Object> parameters = HttpRequestUtil.getAllParameters(request);
        main = MapUtil.getStr(parameters, "ds","main");
        try {
            //return saveOrUpdate(entityName, parameters, true);
            //Step1,校验表信息，并获取表定义及主键信息
            String tableName = StrUtil.toUnderlineCase(entityName);
            main = MapUtil.getStr(parameters, "ds","main");
            parameters.put("entityName" , entityName);
            parameters.put("tableName" , tableName);
            Table table = getTableMeta(tableName,main);
            //Step2,根据表定义，获取表主键，并根据新增及修改，生成主键或者判断主键数据是否存在
            //Step3,根据表定义，新增必填字段信息校验，并将默认或者内置字段生成默认值
            //String primaryKey = RestUtil.getTablePrimaryKes(table);
            Record record = new Record();
            Collection<Column> columns = table.getColumns();
            for (Column column : columns) {
                String paramValue = RestUtil.getParamValue(parameters, column.getName());
                paramValue = RestUtil.getId(paramValue);
                checkDataFormat(column, paramValue);
                if (ObjectUtil.isNotEmpty(paramValue)) {//非空值，直接设置
                    record.set(column.getName(), (paramValue));
                } else {
                    String fieldName = FieldUtils.columnNameToFieldName(column.getName());
                    if (ObjectUtil.isNotEmpty(RestUtil.getDefaultValue(fieldName))) {//设置默认值的字段
                        record.set(column.getName(), RestUtil.getDefaultValue(fieldName));
                    } else {
                        if(column.isPk()){
                            if(column.isAutoIncrement()){
                                //自增的主键，不自动赋值
                            }else{
                                setPkValue(record, column);
                                StaticLog.warn("参数未传值： " + column.getName());
                            }
                        }else{
                            if (!column.isNullable()){ //非空字段，保存的时候，必填直接返回提示
                                    throw new BusinessException("参数[" + column.getName() + "]不能为空！");
                            }
                        }
                    }
                }
            }
            //Step4，根据表定义拿到全部参数并生成入库的对象，并持久化并返回数据
            Boolean isSucess;
            RestUtil.fillRecord(record,tableName,true);
            Record finalRecord = record;
            isSucess = Db.use(main).save(tableName, finalRecord);
            System.out.println("返回数据为：" + JSONUtil.toJsonStr(isSucess));
            if (isSucess) {
                    return Result.success("保存成功！",isSucess);
            } else {
                return Result.fail("新增失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            if (e.getMessage().contains("Duplicate")) {
                return Result.fail("数据重复，主键冲突，请联系管理员，错误信息：" + e.getMessage());
            }
            if (e.getMessage().contains("Incorrect datetime")) {
                return Result.fail("数据格式有误，日期格式不规范(yyyy-mm-dd)");
            }
            if (e.getMessage().contains("Data too long")) {
                return Result.fail("数据值太长，超出最大长度限制" );
            }
            String message = ExceptionUtils.getMessage(e);
            log.error(message, e);
            return Result.error(message);
        }
    }

    @RequestMapping(path = {"/{entityName}/update","/{entityName}/edit"}, produces = "application/json")
    //@ApiOperation(value = "更新实体数据", notes = "不需要更新的字段不设置或设置为空,{\"name\":\"tom\",\"args\":1}")
    public Result update(@PathVariable("entityName") String entityName, HttpServletRequest request) {
        Map<String, Object> parameters = HttpRequestUtil.getAllParameters(request);
        main = MapUtil.getStr(parameters, "ds","main");
        try {
            //return saveOrUpdate(entityName, parameters, false);
            //Step1,校验表信息，并获取表定义及主键信息
            String tableName = StrUtil.toUnderlineCase(entityName);
            main = MapUtil.getStr(parameters, "ds","main");
            parameters.put("entityName" , entityName);
            parameters.put("tableName" , tableName);
            Table table = getTableMeta(tableName,main);
            //Step2,根据表定义，获取表主键，并根据新增及修改，生成主键或者判断主键数据是否存在
            //Step3,根据表定义，新增必填字段信息校验，并将默认或者内置字段生成默认值
            String primaryKey = RestUtil.getTablePrimaryKes(table);
            String primaryValue = RestUtil.getParamValue(parameters, primaryKey);
            Record record = new Record();
            List args = RestUtil.getPrimaryKeyArgs(parameters, table);
            record = Db.use(main).findByIds(tableName, primaryKey, args.toArray());
            if (ObjectUtil.isNull(record)) {
                return Result.fail("修改失败，无此ID对应的记录！");
            }
            Collection<Column> columns = table.getColumns();
            for (Column column : columns) {
                String paramValue = RestUtil.getParamValue(parameters, column.getName());
                checkDataFormat(column, paramValue);
                if (ObjectUtil.isNotEmpty(paramValue)) {//非空值，直接设置
                    record.set(column.getName(), (paramValue));
                } else {
                    String fieldName = FieldUtils.columnNameToFieldName(column.getName());
                    if (ObjectUtil.isNotEmpty(RestUtil.getDefaultValue(fieldName))) {//设置默认值的字段
                        record.set(column.getName(), RestUtil.getDefaultValue(fieldName));
                    } else {
                        if(column.isPk()){
                            if(column.isAutoIncrement()){
                                //自增的主键，不自动赋值
                            }else{
                                setPkValue(record, column);
                                StaticLog.warn("参数未传值： " + column.getName());
                            }
                        }else{
                            if (!column.isNullable()){ //非空字段，保存的时候，必填直接返回提示
                            }
                        }
                    }
                }
            }
            //Step4，根据表定义拿到全部参数并生成入库的对象，并持久化并返回数据
            Boolean isSucess;
            RestUtil.fillRecord(record,tableName,false);
            Record finalRecord1 = record;
            //isSucess = Db.use(main).tx(() -> Db.use(main).update(tableName, finalRecord1));
            isSucess = Db.use(main).update(tableName, finalRecord1);
            System.out.println("返回数据为：" + JSONUtil.toJsonStr(isSucess));
            if (isSucess) {
                    return Result.success("修改成功！",isSucess);
            } else {
                return Result.fail("修改失败");
            }
        } catch (Exception e1) {
            e1.printStackTrace();
            if (e1.getMessage().contains("Duplicate")) {
                return Result.fail("数据重复，主键冲突：" + e1.getMessage());
            }
            if (e1.getMessage().contains("Incorrect datetime")) {
                return Result.fail("数据格式有误，日期格式不规范(yyyy-mm-dd)：" + e1.getMessage());
            }
            if (e1.getMessage().contains("Data too long")) {
                return Result.fail("数据字段值太长，超出最大长度：" + e1.getMessage());
            }
            String message = ExceptionUtils.getMessage(e1);
            log.error(message, e1);
            return Result.error(message);
        }
    }

    public static void main(String[] args) {
        System.out.println(DateUtil.currentSeconds());
    }
    @RequestMapping(path = {"/module/install"}, produces = "application/json")
    public Result install(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> parameters = HttpRequestUtil.getAllParameters(request);
        main = MapUtil.getStr(parameters, "ds","main");
        return Result.success("安装成功！");
    }
    @RequestMapping(path = {"/module/uninstall"}, produces = "application/json")
    public Result uninstall(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> parameters = HttpRequestUtil.getAllParameters(request);
        main = MapUtil.getStr(parameters, "ds","main");
        return Result.success("卸载成功！");
    }
    @RequestMapping(path = {"/cache/clear"}, produces = "application/json")
    public Result cacheClear(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> parameters = HttpRequestUtil.getAllParameters(request);
        main = MapUtil.getStr(parameters, "ds","main");
        //tableCache.set(new HashMap<>());
        return Result.success("缓存清理成功！");
    }
    @RequestMapping(path = {"/api/test"}, produces = "application/json")
    public Result apiTest(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> parameters = HttpRequestUtil.getAllParameters(request);
        main = MapUtil.getStr(parameters, "ds","main");
        //        String url = "localhost:8888/engine/install";
        String url = new String();
        HttpRequest post = HttpRequest.post(url).header("Content-Type", "multipart/form-data");
        Map<String,Object> map = Maps.newHashMap();
        map.put("aaa",123);
        map.put("byk",45465);
        post.form(map);
        HttpResponse execute = null;
        try {
            execute = post.execute();
        } catch (Exception e) {
            throw new BusinessException("安装部署包失败，调用引擎["+url+"]端口失败"+e.getMessage());
        }
        String jsonStr = execute.body();
        System.out.println("execute = " + jsonStr);
        if(JSONUtil.isJson(jsonStr)){
            Result result = JSONUtil.toBean(jsonStr,Result.class);
            return result;
        }else{
            return Result.error(jsonStr);
        }
    }



}

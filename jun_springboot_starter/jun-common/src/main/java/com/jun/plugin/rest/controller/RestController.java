package com.jun.plugin.rest.controller;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.db.meta.Column;
import cn.hutool.db.meta.MetaUtil;
import cn.hutool.db.meta.Table;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSONUtil;
import cn.hutool.log.StaticLog;
import com.google.common.collect.Maps;
import com.jun.plugin.common.generator.util.TreeUtil;
import com.jun.plugin.common.util.HttpRequestUtil;
//import com.jfinal.plugin.activerecord.Db;
//import com.jfinal.plugin.activerecord.Page;
//import com.jfinal.plugin.activerecord.Record;
import com.jun.plugin.common.Result;
import com.jun.plugin.common.exception.BusinessException;
import com.jun.plugin.db.record.*;
import com.jun.plugin.common.util.IdGenerator;
import com.jun.plugin.rest.util.RestUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

import static com.jun.plugin.db.DataSourcePool.main;

/**
 * @author Wujun
 * @desc 通用Rest服务接口
 */
@Slf4j
@org.springframework.web.bind.annotation.RestController
@RequestMapping({"${platform.path:}/rest", "${platform.path:}/public/rest"})
//@Api(value = "实体公共增删改查接口")
public class RestController {

    static AtomicReference<Map<String, Table>> tableCache = new AtomicReference<>();

    static {
        tableCache.set(Maps.newHashMap());
    }


    private static Result check(String tableName) {
        Map map = tableCache.get();
        if (!map.containsKey(tableName)) {
            Table table = MetaUtil.getTableMeta(Db.use(main).getConfig().getDataSource(), tableName);
            map.put(tableName, table);
            tableCache.set(map);
            if (CollectionUtils.isEmpty(table.getColumns())) {
                return Result.fail("实体对应的表不存在！");
            }
        }
        return null;
    }


    @GetMapping(path = {"/{entityName}/list", "/{entityName}/page","/{entityName}/tree"}, produces = "application/json")
    //@ApiOperation(value = "返回实体数据列表", notes = "page与size同时大于零时返回分页实体数据列表,否则返回全部数据列表;
    public Result list(@PathVariable("entityName") String entityName, HttpServletRequest request) throws Exception {
        try {
            String url = request.getRequestURI();
            StaticLog.info("url = "+ url);
            Map<String, Object> parameters = HttpRequestUtil.getAllParameters(request);
            String tableName = StrUtil.toUnderlineCase(entityName);
            parameters.put("entityName" , entityName);
            parameters.put("tableName" , tableName);
            Result fail = check(tableName);
            if (fail != null) return fail;
            Table table = tableCache.get().get(tableName);
            Boolean isPage = false;
            Integer page = MapUtil.getInt(parameters, "page");
            Integer limit = MapUtil.getInt(parameters, "limit");
            if ((page == null || page == 0) || (limit == null || limit == 0)) {
                page = 1;
                limit = 10;
                isPage = false;
            } else {
                isPage = true;
            }
            StringBuffer sql = new StringBuffer();
            String select = "select *";
            String from = " from " + tableName;
            sql.append(select);
            sql.append(from);
            String where = RestUtil.getQueryCondition(parameters,table);
            if (isPage) {
                if(StrUtil.isNotEmpty(where)){
                    from = from + " where 1=1 "+ where;
                }
                Page<Record> pages = Db.use(main).paginate(page, limit, select, from);
                List<Map> datas = RecordUtil.recordToMaps2(pages.getList());
                return Result.success(datas).put("count", pages.getTotalRow()).put("pageSize", pages.getPageSize()).put("totalPage", pages.getTotalPage()).put("pageNumber", pages.getPageNumber());
            } else {
                if(StrUtil.isNotEmpty(where)){
                    sql.append(" where 1=1 "+ where);
                }
                List<Record> lists = Db.use(main).find(sql.toString());
                List<Map> datas = RecordUtil.recordToMaps(lists);
                String tree = MapUtil.getStr(parameters, "tree");
                if(StrUtil.isNotEmpty(tree) && tree.contains(",")){
                    String id = tree.split(",")[0];
                    String pid = tree.split(",")[1];
                    RestUtil.buildTree(datas,id,pid);
                }
                return Result.success(datas);
            }
        } catch (Exception e) {
            String message = ExceptionUtils.getMessage(e);
            log.error(message, e);
            return Result.error(message);
        }
    }

    @RequestMapping(path = {"/{entityName}/findOne","/{entityName}/getOne","/{entityName}/record","/{entityName}/row"}, produces = "application/json")
    //@ApiOperation(value = "根据ID返回单个实体数据")
    public Result findOne(@PathVariable("entityName") String entityName, HttpServletRequest request) {
        try {
            Map<String, Object> parameters = HttpRequestUtil.getAllParameters(request);
            String tableName = StrUtil.toUnderlineCase(entityName);
            parameters.put("entityName" , entityName);
            parameters.put("tableName" , tableName);
            Result fail = check(tableName);
            if (fail != null) return fail;
            Table table = tableCache.get().get(tableName);
            String primaryKey = RestUtil.getTablePrimaryKes(table);
            List args = RestUtil.getPrimaryKeyArgs(parameters, table);
            Record record = Db.use(main).findByIds(tableName, primaryKey, args.toArray());
//            Record record = Db.use(main).findById(tableName,primaryKey, (Number) args.get(0));
            if (ObjectUtil.isNotNull(record)) {
                Map data = RecordUtil.recordToMap(record);
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
    public Result delete(@PathVariable("entityName") String entityName, HttpServletRequest request) {
        try {
            Map<String, Object> parameters = HttpRequestUtil.getAllParameters(request);
            String tableName = StrUtil.toUnderlineCase(entityName);
            parameters.put("entityName" , entityName);
            parameters.put("tableName" , tableName);
            Result fail = check(tableName);
            if (fail != null) return fail;
            Table table = tableCache.get().get(tableName);
            String primaryKey = RestUtil.getTablePrimaryKes(table);
            List args = RestUtil.getPrimaryKeyArgs(parameters, table);
            Boolean flag = Db.use(main).tx(() -> Db.use(main).deleteByIds(tableName, primaryKey, args.toArray()));
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
        try {
            return saveOrUpdate(entityName, request, true);
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
        try {
            return saveOrUpdate(entityName, request, false);
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

    public Result saveOrUpdate(String entityName, HttpServletRequest request, Boolean isSave) {
        //Step1,校验表信息，并获取表定义及主键信息
        String tableName = StrUtil.toUnderlineCase(entityName);
        Result fail = check(tableName);
        if (fail != null) return fail;
        Map<String, Object> parameters = HttpRequestUtil.getAllParameters(request);
        parameters.put("entityName" , entityName);
        parameters.put("tableName" , tableName);
        Table table = tableCache.get().get(tableName);
        //Step2,根据表定义，获取表主键，并根据新增及修改，生成主键或者判断主键数据是否存在
        //Step3,根据表定义，新增必填字段信息校验，并将默认或者内置字段生成默认值
        Record record = new Record();
        if (!isSave) {
            String primaryKey = RestUtil.getTablePrimaryKes(table);
            List args = RestUtil.getPrimaryKeyArgs(parameters, table);
            record = Db.use(main).findByIds(tableName, primaryKey, args.toArray());
//            record = Db.use(main).findById(tableName, primaryKey, args.toArray());
//            record = Db.use(main).findById(tableName,primaryKey, (Number) args.get(0));
            if (ObjectUtil.isNull(record)) {
                return Result.fail("修改失败，无此ID对应的记录！");
            }
        }
        Collection<Column> columns = table.getColumns();
        for (Column column : columns) {
            String paramValue = RestUtil.getParamValue(parameters, column.getName());
            if (isSave) {
                paramValue = RestUtil.getId(paramValue);
            }
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
                            if (isSave) {
                                throw new BusinessException("参数[" + column.getName() + "]不能为空！");
                            }
                        }
                    }
                }
            }
        }
        //Step4，根据表定义拿到全部参数并生成入库的对象，并持久化并返回数据
        Boolean isSucess;
        if (isSave) {
            RestUtil.fillRecord(record,tableName,true);
            Record finalRecord = record;
            isSucess = Db.use(main).tx(() -> Db.use(main).save(tableName, finalRecord));
        } else {
            RestUtil.fillRecord(record,tableName,false);
            Record finalRecord1 = record;
            isSucess = Db.use(main).tx(() -> Db.use(main).update(tableName, finalRecord1));
        }
        System.out.println("返回数据为：" + JSONUtil.toJsonStr(isSucess));
        if (isSucess) {
            if (isSave) {
                return Result.success("保存成功！",isSucess);
            }else{
                return Result.success("修改成功！",isSucess);
            }
        } else {
            return Result.fail("新增或者修改失败");
        }
    }

    private static void checkDataFormat(Column column, String val) {
        if ("DATE".equalsIgnoreCase(column.getTypeName()) ||
                "datetime".equalsIgnoreCase(column.getTypeName()) ||
                "DATE".equalsIgnoreCase(column.getTypeName())) {
            try {
                DateTime date = DateUtil.parse(val);
                log.info("是日期类型字符串：[{}]", val);
            } catch (Exception e) {
                log.info("不是日期类型字符串：[{}]", val);
                throw new BusinessException("参数["+ column.getName()+"]日期格式字符串不合法");
            }
        }
    }

    private static void setPkValue(Record record, Column column) {
        if ("VARCHAR".equalsIgnoreCase(column.getTypeName()) ||
                "TEXT".equalsIgnoreCase(column.getTypeName()) ||
                "LONGTEXT".equalsIgnoreCase(column.getTypeName()) ||
                "CLOB".equalsIgnoreCase(column.getTypeName())) {
            String idStr = IdGenerator.generateIdStr();
            if (column.getSize() > idStr.length()) {
                record.set(column.getName(), idStr);
            } else {
                int start = idStr.length() - Integer.valueOf((int) column.getSize());
                record.set(column.getName(), idStr.substring(start, idStr.length()));
            }
        } else if ("DATE".equalsIgnoreCase(column.getTypeName()) ||
                "datetime".equalsIgnoreCase(column.getTypeName()) ||
                "DATE".equalsIgnoreCase(column.getTypeName())) {
            record.set(column.getName(), DateUtil.now());
        } else if ("bigint".equalsIgnoreCase(column.getTypeName()) ||
                "int".equalsIgnoreCase(column.getTypeName()) ||
                "float".equalsIgnoreCase(column.getTypeName()) ||
                "decimal".equalsIgnoreCase(column.getTypeName()) ||
                "bit".equalsIgnoreCase(column.getTypeName()) ||
                "integer".equalsIgnoreCase(column.getTypeName()) ||
                "tinyint".equalsIgnoreCase(column.getTypeName())) {

            long idStr = IdGenerator.generateId();
            long currentSeconds = DateUtil.currentSeconds();
            if (column.getSize() >= String.valueOf(idStr).length()) {//十六位，雪花ID，毫秒级别
                record.set(column.getName(), idStr);
            } else if (column.getSize() >= String.valueOf(currentSeconds).length()) { //十位，时间戳，秒级别
                record.set(column.getName(), currentSeconds);
            } else {
                int size = Integer.valueOf((int) column.getSize());
                String numberkey = RandomUtil.randomNumbers(size);  //随机数，字段长度的随机数字，字段不能设置太短
                record.set(column.getName(), numberkey);
            }
        }
    }

    public static void main(String[] args) {
        System.out.println(DateUtil.currentSeconds());
    }
    @RequestMapping(path = {"/module/install"}, produces = "application/json")
    public Result install(HttpServletRequest request, HttpServletResponse response) {
        return Result.success("安装成功！");
    }
    @RequestMapping(path = {"/module/uninstall"}, produces = "application/json")
    public Result uninstall(HttpServletRequest request, HttpServletResponse response) {
        return Result.success("卸载成功！");
    }
    @RequestMapping(path = {"/cache/clear"}, produces = "application/json")
    public Result cacheClear(HttpServletRequest request, HttpServletResponse response) {
        tableCache.set(new HashMap<>());
        return Result.success("缓存清理成功！");
    }
    @RequestMapping(path = {"/api/test"}, produces = "application/json")
    public Result apiTest(HttpServletRequest request, HttpServletResponse response) {
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
        if(JSONUtil.isTypeJSON(jsonStr)){
            Result result = JSONUtil.toBean(jsonStr,Result.class);
            return result;
        }else{
            return Result.error(jsonStr);
        }
    }



}

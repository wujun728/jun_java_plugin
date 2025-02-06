//package io.github.wujun728.rest.controller;
//
//import cn.hutool.core.date.DateUtil;
//import cn.hutool.core.map.MapUtil;
//import cn.hutool.core.util.ObjectUtil;
//import cn.hutool.core.util.StrUtil;
//import cn.hutool.db.meta.Column;
//import cn.hutool.db.meta.MetaUtil;
//import cn.hutool.db.meta.Table;
//import cn.hutool.http.HttpRequest;
//import cn.hutool.http.HttpResponse;
//import cn.hutool.json.JSONUtil;
//import cn.hutool.log.StaticLog;
//import com.alibaba.fastjson2.JSON;
//import com.google.common.collect.Maps;
//import io.github.wujun728.common.base.Result;
//import io.github.wujun728.common.exception.BusinessException;
//import io.github.wujun728.db.record.Db;
//import io.github.wujun728.db.record.Page;
//import io.github.wujun728.db.record.Record;
//import io.github.wujun728.db.utils.DataSourcePool;
//import io.github.wujun728.db.utils.FieldUtils;
//import io.github.wujun728.db.utils.RecordUtil;
//import io.github.wujun728.db.utils.TreeBuildUtil;
//import io.github.wujun728.rest.entity.ApiSql;
//import io.github.wujun728.rest.service.RestApiService;
//import io.github.wujun728.rest.util.HttpRequestUtil;
//import io.github.wujun728.rest.util.RestUtil;
//import io.github.wujun728.sql.SqlMeta;
//import io.github.wujun728.sql.SqlXmlUtil;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.commons.lang3.exception.ExceptionUtils;
//import org.springframework.util.CollectionUtils;
//import org.springframework.web.bind.annotation.*;
//
//import javax.annotation.Resource;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.sql.DataSource;
//import java.io.*;
//import java.sql.SQLException;
//import java.util.Collection;
//import java.util.List;
//import java.util.Map;
//import java.util.stream.Collectors;
//
//import static io.github.wujun728.rest.util.RestUtil.checkDataFormat;
//import static io.github.wujun728.rest.util.RestUtil.setPkValue;
//
///**
// * @author Wujun
// * @desc 通用Rest服务接口
// */
//@Slf4j
//@org.springframework.web.bind.annotation.RestController
//@RequestMapping({"${platform.path:}/rest", "${platform.path:}/public/rest"})
////@Api(value = "实体公共增删改查接口")
//public class RestApiController {
//
//    @Resource
//    RestApiService restApiService;
//
//    private String main = "main";
//
//    @GetMapping(path = {"/{entityName}/list"}, produces = "application/json")
//    //@ApiOperation(value = "返回实体数据列表", notes = "page与size同时大于零时返回分页实体数据列表,否则返回全部数据列表;
//    public Result list(@PathVariable("entityName") String entityName, HttpServletRequest request) throws Exception {
//        Map<String, Object> parameters = HttpRequestUtil.getAllParameters(request);
//        main = MapUtil.getStr(parameters, "ds","main");
//        String tableName = StrUtil.toUnderlineCase(entityName);
//        Boolean isUnderLine = entityName.equals(tableName);
//        try {
//            String url = request.getRequestURI();
//            StaticLog.info("url = "+ url);
//            parameters.put("entityName" , entityName);
//            parameters.put("tableName" , tableName);
//            Table table = getTableMeta(tableName,main);
//            StringBuffer sql = new StringBuffer();
//            String select = "select *";
//            sql.append(select);
//            String from = " from " + tableName;
//            String where = RestUtil.getQueryCondition(parameters,table);
//            if(StrUtil.isNotEmpty(where)){
//                from = from + " where 1=1 "+ where;
//            }
//            sql.append(from);
//            List<Record> datas1 = Db.find(sql.toString());
//            List<Map> datas = RecordUtil.recordToMaps(datas1,isUnderLine);
//            return Result.success(datas);
//        } catch (Exception e) {
//            String message = ExceptionUtils.getMessage(e);
//            log.error(message, e);
//            return Result.error(message);
//        }
//    }
//
//    @GetMapping(path = {  "/{entityName}/page" }, produces = "application/json")
//    //@ApiOperation(value = "返回实体数据列表", notes = "page与size同时大于零时返回分页实体数据列表,否则返回全部数据列表;
//    public Result page(@PathVariable("entityName") String entityName, HttpServletRequest request) throws Exception {
//        Map<String, Object> parameters = HttpRequestUtil.getAllParameters(request);
//        main = MapUtil.getStr(parameters, "ds","main");
//        String tableName = StrUtil.toUnderlineCase(entityName);
//        Boolean isUnderLine = entityName.equals(tableName);
//        try {
//            String url = request.getRequestURI();
//            StaticLog.info("url = "+ url);
//            parameters.put("entityName" , entityName);
//            parameters.put("tableName" , tableName);
//            Table table = getTableMeta(tableName,main);
//            StringBuffer sql = new StringBuffer();
//            String select = "select *";
//            sql.append(select);
//            String from = " from " + tableName;
//            String where = RestUtil.getQueryCondition(parameters,table);
//            if(StrUtil.isNotEmpty(where)){
//                from = from + " where 1=1 "+ where;
//            }
//            sql.append(from);
//            Integer page = MapUtil.getInt(parameters, "page");
//            if ((page == null || page == 0)  ) {
//                page = 1;
//            }
//            Integer limit = MapUtil.getInt(parameters, "limit");
//            if ( (limit == null || limit == 0)) {
//                limit = 10;
//            }
//            Page<Record> pages = Db.paginate(page, limit, select, from);
//            List<Map> datas = RecordUtil.recordToMaps(pages.getList(),isUnderLine);
//            return Result.success(datas).put("count", pages.getTotalRow()).put("pageSize", pages.getPageSize()).put("totalPage", pages.getTotalPage()).put("pageNumber", pages.getPageNumber());
//        } catch (Exception e) {
//            String message = ExceptionUtils.getMessage(e);
//            log.error(message, e);
//            return Result.error(message);
//        }
//    }
//
//
//    @GetMapping(path = {"/{entityName}/tree"}, produces = "application/json")
//    //@ApiOperation(value = "返回实体数据列表", notes = "page与size同时大于零时返回分页实体数据列表,否则返回全部数据列表;
//    public Result tree(@PathVariable("entityName") String entityName, HttpServletRequest request) throws Exception {
//        Map<String, Object> parameters = HttpRequestUtil.getAllParameters(request);
//        main = MapUtil.getStr(parameters, "ds","main");
//        String tableName = StrUtil.toUnderlineCase(entityName);
//        Boolean isUnderLine = entityName.equals(tableName);
//        try {
//            String url = request.getRequestURI();
//            StaticLog.info("url = "+ url);
//            parameters.put("entityName" , entityName);
//            parameters.put("tableName" , tableName);
//            Table table = getTableMeta(tableName,main);
//            StringBuffer sql = new StringBuffer();
//            String select = "select *";
//            sql.append(select);
//            String from = " from " + tableName;
//            sql.append(from);
//            String where = RestUtil.getQueryCondition(parameters,table);
//            if(StrUtil.isNotEmpty(where)){
//                from = from + " where 1=1 "+ where;
//            }
//            Boolean isTree = url.contains("tree") ?true:false;
//            List<Record> datas1 = Db.find(sql.toString());
//            List<Map> datas = RecordUtil.recordToMaps(datas1,isUnderLine);
//            //是否构建树 begin
//            if(isTree){
//                String treeId = MapUtil.getStr(parameters, "id") == null ? "id" : MapUtil.getStr(parameters, "id");
//                String treePid = MapUtil.getStr(parameters, "pid") == null ? "pid" : MapUtil.getStr(parameters, "pid");
//                Object rootId = parameters.get("rootId") == null ? 0L : parameters.get("rootId");
//                if (StrUtil.isNotEmpty(treePid)) {
//                    List treeList = TreeBuildUtil.listToTree(datas,String.valueOf(rootId),treeId,treePid);
//                    StaticLog.info(JSONUtil.toJsonPrettyStr(treeList));
//                    return Result.success(treeList);
//                }
//            }
//            return Result.success(datas);
//            //是否构建树 end
//        } catch (Exception e) {
//            String message = ExceptionUtils.getMessage(e);
//            log.error(message, e);
//            return Result.error(message);
//        }
//    }
//
//    @GetMapping(path = {"/{entityName}/findOne","/{entityName}/getOne","/{entityName}/record","/{entityName}/row"}, produces = "application/json")
//    //@ApiOperation(value = "根据ID返回单个实体数据")
//    public Result findOne(@PathVariable("entityName") String entityName, HttpServletRequest request) throws Exception {
//        Map<String, Object> parameters = HttpRequestUtil.getAllParameters(request);
//        main = MapUtil.getStr(parameters, "ds","main");
//        String tableName = StrUtil.toUnderlineCase(entityName);
//        Boolean isUnderLine = entityName.equals(tableName);
//        try {
//            parameters.put("entityName" , entityName);
//            parameters.put("tableName" , tableName);
//            Table table = getTableMeta(tableName,main);
//            String primaryKey = RestUtil.getTablePrimaryKes(table);
//            List args = RestUtil.getPrimaryKeyArgs(parameters, table);
//            Record record = Db.findByIds(tableName, primaryKey, args.toArray());
////            Record record = Db.findById(tableName,primaryKey, (Number) args.get(0));
//            if (ObjectUtil.isNotNull(record)) {
//                Map data = RecordUtil.recordToMap(record,isUnderLine);
//                return Result.success(data);
//            } else {
//                return Result.fail("无此ID对应的记录！");
//            }
//        } catch (Exception e) {
//            String message = ExceptionUtils.getMessage(e);
//            log.error(message, e);
//            return Result.error(message);
//        }
//    }
//
//    @DeleteMapping(path = "/{entityName}/{ids}", produces = "application/json")
//    //@ApiOperation(value = "根据id删除实体数据" )
//    public Result delete2(@PathVariable("entityName") String entityName,@PathVariable("ids") String ids, HttpServletRequest request) throws Exception {
//        return delete(entityName, ids, request);
//    }
//    @RequestMapping(path = "/{entityName}/delete/{ids}", produces = "application/json")
//    //@ApiOperation(value = "根据id删除实体数据" )
//    public Result delete(@PathVariable("entityName") String entityName,@PathVariable("ids") String ids, HttpServletRequest request) throws Exception {
//        Map<String, Object> parameters = HttpRequestUtil.getAllParameters(request);
//        main = MapUtil.getStr(parameters, "ds","main");
//        String tableName = StrUtil.toUnderlineCase(entityName);
//        try {
//            parameters.put("entityName" , entityName);
//            parameters.put("tableName" , tableName);
//            Table table = getTableMeta(tableName,main);
//            String primaryKey = "id";
//            Object[] argsDelete = new Object[]{} ;
//            if(StrUtil.isNotEmpty(ids)){
//                if(ids.contains(",")){
//                    argsDelete = ids.split(",");
//                }else{
//                    argsDelete = new Object[]{ids};
//                }
//            }else{
//                primaryKey = RestUtil.getTablePrimaryKes(table);
//                List args = RestUtil.getPrimaryKeyArgs(parameters, table);
//                argsDelete = args.toArray();
//            }
//            //Boolean flag = Db.tx(() -> Db.deleteByIds(tableName, primaryKey, args.toArray()));
//            Boolean flag =   Db.deleteById(tableName, primaryKey, argsDelete);
////            Boolean flag = Db.deleteByIds(tableName, primaryKey, args.toArray());
//            if (flag) {
//                return Result.success("删除成功！",flag);
//            } else {
//                return Result.fail("删除失败！");
//            }
//        } catch (Exception e) {
//            String message = ExceptionUtils.getMessage(e);
//            if (message.contains("Unknown column")) {
//                throw new BusinessException("接口必须参数id,可选参数primaryKey，其中primaryKey中的列在数据库不存在");
//            }
//            if (message.contains("number must equals id value number")) {
//                throw new BusinessException("接口必须参数id,可选参数primaryKey，有多列，均使用逗号分隔，当前参数个数与值的个数不一致");
//            }
//            log.error(message, e);
//            return Result.error(message);
//        }
//    }
//
//
//    @PostMapping(path = {"/{entityName}"}, produces = "application/json")
//    //@ApiOperation(value = "新增实体数据", notes = "{\"name\":\"tom\",\"args\":1}")
//    public Result save(@PathVariable("entityName") String entityName, HttpServletRequest request) {
//        return create(entityName, request);
//    }
//    @RequestMapping(path = {"/{entityName}/save","/{entityName}/add"}, produces = "application/json")
//    //@ApiOperation(value = "新增实体数据", notes = "{\"name\":\"tom\",\"args\":1}")
//    public Result create(@PathVariable("entityName") String entityName, HttpServletRequest request) {
//        Map<String, Object> parameters = HttpRequestUtil.getAllParameters(request);
//        main = MapUtil.getStr(parameters, "ds","main");
//        try {
//            //return saveOrUpdate(entityName, parameters, true);
//            //Step1,校验表信息，并获取表定义及主键信息
//            String tableName = StrUtil.toUnderlineCase(entityName);
//            main = MapUtil.getStr(parameters, "ds","main");
//            parameters.put("entityName" , entityName);
//            parameters.put("tableName" , tableName);
//            Table table = getTableMeta(tableName,main);
//            //Step2,根据表定义，获取表主键，并根据新增及修改，生成主键或者判断主键数据是否存在
//            //Step3,根据表定义，新增必填字段信息校验，并将默认或者内置字段生成默认值
//            //String primaryKey = RestUtil.getTablePrimaryKes(table);
//            Record record = new Record();
//            RestUtil.buildRecord(parameters, table, record);
//            //Step4，根据表定义拿到全部参数并生成入库的对象，并持久化并返回数据
//            Boolean isSucess;
//            RestUtil.fillRecord(record,tableName,true);
//            Record finalRecord = record;
//            isSucess = Db.save(tableName, finalRecord);
//            System.out.println("返回数据为：" + JSONUtil.toJsonStr(isSucess));
//            if (isSucess) {
//                    return Result.success("保存成功！",isSucess);
//            } else {
//                return Result.fail("新增失败");
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            if (e.getMessage().contains("Duplicate")) {
//                return Result.fail("数据重复，主键冲突，请联系管理员，错误信息：" + e.getMessage());
//            }
//            if (e.getMessage().contains("Incorrect datetime")) {
//                return Result.fail("数据格式有误，日期格式不规范(yyyy-mm-dd)");
//            }
//            if (e.getMessage().contains("Data too long")) {
//                return Result.fail("数据值太长，超出最大长度限制" );
//            }
//            String message = ExceptionUtils.getMessage(e);
//            log.error(message, e);
//            return Result.error(message);
//        }
//    }
//
//    @PutMapping(path = {"/{entityName}"}, produces = "application/json")
//    public Result edit(@PathVariable("entityName") String entityName, HttpServletRequest request) {
//        return update(entityName, request);
//    }
//    @RequestMapping(path = {"/{entityName}/update","/{entityName}/edit"}, produces = "application/json")
//    //@ApiOperation(value = "更新实体数据", notes = "不需要更新的字段不设置或设置为空,{\"name\":\"tom\",\"args\":1}")
//    public Result update(@PathVariable("entityName") String entityName, HttpServletRequest request) {
//        Map<String, Object> parameters = HttpRequestUtil.getAllParameters(request);
//        main = MapUtil.getStr(parameters, "ds","main");
//        try {
//            //return saveOrUpdate(entityName, parameters, false);
//            //Step1,校验表信息，并获取表定义及主键信息
//            String tableName = StrUtil.toUnderlineCase(entityName);
//            main = MapUtil.getStr(parameters, "ds","main");
//            parameters.put("entityName" , entityName);
//            parameters.put("tableName" , tableName);
//            Table table = getTableMeta(tableName,main);
//            //Step2,根据表定义，获取表主键，并根据新增及修改，生成主键或者判断主键数据是否存在
//            //Step3,根据表定义，新增必填字段信息校验，并将默认或者内置字段生成默认值
//            String primaryKey = RestUtil.getTablePrimaryKes(table);
//            String primaryValue = RestUtil.getParamValue(parameters, primaryKey);
//            Record record = new Record();
//            List args = RestUtil.getPrimaryKeyArgs(parameters, table);
//            record = Db.findByIds(tableName, primaryKey, args.toArray());
//            if (ObjectUtil.isNull(record)) {
//                return Result.fail("修改失败，无此ID对应的记录！");
//            }
//            RestUtil.buildRecord(parameters,table,record);
//            //Step4，根据表定义拿到全部参数并生成入库的对象，并持久化并返回数据
//            Boolean isSucess;
//            RestUtil.fillRecord(record,tableName,false);
//            Record finalRecord1 = record;
//            //isSucess = Db.tx(() -> Db.update(tableName, finalRecord1));
//            isSucess = Db.update(tableName, finalRecord1);
//            System.out.println("返回数据为：" + JSONUtil.toJsonStr(isSucess));
//            if (isSucess) {
//                    return Result.success("修改成功！",isSucess);
//            } else {
//                return Result.fail("修改失败");
//            }
//        } catch (Exception e1) {
//            e1.printStackTrace();
//            if (e1.getMessage().contains("Duplicate")) {
//                return Result.fail("数据重复，主键冲突：" + e1.getMessage());
//            }
//            if (e1.getMessage().contains("Incorrect datetime")) {
//                return Result.fail("数据格式有误，日期格式不规范(yyyy-mm-dd)：" + e1.getMessage());
//            }
//            if (e1.getMessage().contains("Data too long")) {
//                return Result.fail("数据字段值太长，超出最大长度：" + e1.getMessage());
//            }
//            String message = ExceptionUtils.getMessage(e1);
//            log.error(message, e1);
//            return Result.error(message);
//        }
//    }
//
//    public static void main(String[] args) {
//        System.out.println(DateUtil.currentSeconds());
//    }
//    @RequestMapping(path = {"/module/install"}, produces = "application/json")
//    public Result install(HttpServletRequest request, HttpServletResponse response) {
//        Map<String, Object> parameters = HttpRequestUtil.getAllParameters(request);
//        main = MapUtil.getStr(parameters, "ds","main");
//        return Result.success("安装成功！");
//    }
//    @RequestMapping(path = {"/module/uninstall"}, produces = "application/json")
//    public Result uninstall(HttpServletRequest request, HttpServletResponse response) {
//        Map<String, Object> parameters = HttpRequestUtil.getAllParameters(request);
//        main = MapUtil.getStr(parameters, "ds","main");
//        return Result.success("卸载成功！");
//    }
//    @RequestMapping(path = {"/cache/clear"}, produces = "application/json")
//    public Result cacheClear(HttpServletRequest request, HttpServletResponse response) {
//        Map<String, Object> parameters = HttpRequestUtil.getAllParameters(request);
//        main = MapUtil.getStr(parameters, "ds","main");
//        //tableCache.set(new HashMap<>());
//        return Result.success("缓存清理成功！");
//    }
//    @RequestMapping(path = {"/api/test"}, produces = "application/json")
//    public Result apiTest(HttpServletRequest request, HttpServletResponse response) {
//        Map<String, Object> parameters = HttpRequestUtil.getAllParameters(request);
//        main = MapUtil.getStr(parameters, "ds","main");
//        //        String url = "localhost:8888/engine/install";
//        String url = new String();
//        HttpRequest post = HttpRequest.post(url).header("Content-Type", "multipart/form-data");
//        Map<String,Object> map = Maps.newHashMap();
//        map.put("aaa",123);
//        map.put("byk",45465);
//        post.form(map);
//        HttpResponse execute = null;
//        try {
//            execute = post.execute();
//        } catch (Exception e) {
//            throw new BusinessException("安装部署包失败，调用引擎["+url+"]端口失败"+e.getMessage());
//        }
//        String jsonStr = execute.body();
//        System.out.println("execute = " + jsonStr);
//        if(JSONUtil.isJson(jsonStr)){
//            Result result = JSONUtil.toBean(jsonStr,Result.class);
//            return result;
//        }else{
//            return Result.error(jsonStr);
//        }
//    }
//
//
//    public static Table getTableMeta(String tableName,String ds) {
//        //Map map = tableCache.get();
//        //if (!map.containsKey(tableName)) {
//        Table table = MetaUtil.getTableMeta(DataSourcePool.get(ds), tableName);
//        //map.put(tableName, table);
//        //tableCache.set(map);
//        if (CollectionUtils.isEmpty(table.getColumns())) {
//            throw new BusinessException("实体对应的表不存在！");
//        }
//        return table;
//        //}
//    }
//
//
//    public Result saveOrUpdate(String entityName, Map<String, Object> parameters, Boolean isSave) throws Exception {
//        //Step1,校验表信息，并获取表定义及主键信息
//        String tableName = StrUtil.toUnderlineCase(entityName);
//        main = MapUtil.getStr(parameters, "ds","main");
//        parameters.put("entityName" , entityName);
//        parameters.put("tableName" , tableName);
//        Table table = getTableMeta(tableName,main);
//        //Step2,根据表定义，获取表主键，并根据新增及修改，生成主键或者判断主键数据是否存在
//        //Step3,根据表定义，新增必填字段信息校验，并将默认或者内置字段生成默认值
//        String primaryKey = RestUtil.getTablePrimaryKes(table);
//        String primaryValue = RestUtil.getParamValue(parameters, primaryKey);
//        if(StrUtil.isNotEmpty(primaryValue)){
//            isSave = false;
//        }
//        Record record = new Record();
//        if (!isSave) {
//            List args = RestUtil.getPrimaryKeyArgs(parameters, table);
//            record = Db.findByIds(tableName, primaryKey, args.toArray());
//            if (ObjectUtil.isNull(record)) {
//                return Result.fail("修改失败，无此ID对应的记录！");
//            }
//        }
//        Collection<Column> columns = table.getColumns();
//        for (Column column : columns) {
//            String paramValue = RestUtil.getParamValue(parameters, column.getName());
//            if (isSave) {
//                paramValue = RestUtil.getId(paramValue);
//            }
//            checkDataFormat(column, paramValue);
//            if (ObjectUtil.isNotEmpty(paramValue)) {//非空值，直接设置
//                record.set(column.getName(), (paramValue));
//            } else {
//                String fieldName = FieldUtils.columnNameToFieldName(column.getName());
//                if (ObjectUtil.isNotEmpty(RestUtil.getDefaultValue(fieldName))) {//设置默认值的字段
//                    record.set(column.getName(), RestUtil.getDefaultValue(fieldName));
//                } else {
//                    if(column.isPk()){
//                        if(column.isAutoIncrement()){
//                            //自增的主键，不自动赋值
//                        }else{
//                            setPkValue(record, column);
//                            StaticLog.warn("参数未传值： " + column.getName());
//                        }
//                    }else{
//                        if (!column.isNullable()){ //非空字段，保存的时候，必填直接返回提示
//                            if (isSave) {
//                                throw new BusinessException("参数[" + column.getName() + "]不能为空！");
//                            }
//                        }
//                    }
//                }
//            }
//        }
//        //Step4，根据表定义拿到全部参数并生成入库的对象，并持久化并返回数据
//        Boolean isSucess;
//        if (isSave) {
//            RestUtil.fillRecord(record,tableName,true);
//            Record finalRecord = record;
//            //isSucess = Db.tx(() -> Db.save(tableName, finalRecord));
//            isSucess = Db.save(tableName, finalRecord);
//        } else {
//            RestUtil.fillRecord(record,tableName,false);
//            Record finalRecord1 = record;
//            //isSucess = Db.tx(() -> Db.update(tableName, finalRecord1));
//            isSucess = Db.update(tableName, finalRecord1);
//        }
//        System.out.println("返回数据为：" + JSONUtil.toJsonStr(isSucess));
//        if (isSucess) {
//            if (isSave) {
//                return Result.success("保存成功！",isSucess);
//            }else{
//                return Result.success("修改成功！",isSucess);
//            }
//        } else {
//            return Result.fail("新增或者修改失败");
//        }
//    }
//
//
//
//
//    public static void download(HttpServletResponse response, File file) throws IOException {
//        String filename = file.getName();
//        response.setCharacterEncoding("UTF-8");
//        response.setHeader("Content-Disposition", "attachment; filename=\"" + filename + "\"");
//        response.addHeader("Content-Length", "" + file.length());
//        response.setContentType("application/octet-stream");
//        try(InputStream is = new FileInputStream(file);
//            BufferedInputStream bis = new BufferedInputStream(is);
//            BufferedOutputStream bos = new BufferedOutputStream(response.getOutputStream())){
//            byte[] buff = new byte[2048];
//            int len;
//            while ((len = bis.read(buff)) != -1){
//                bos.write(buff, 0, len);
//            }
//            bos.flush();
//        } catch (IOException e){
//            throw e;
//        }
//    }
//
//
//    @RequestMapping(path = {"/run/{path}"}, produces = "application/json")
//    public Result apiExecute(@PathVariable String path ,HttpServletRequest request, HttpServletResponse response) throws SQLException {
//        Map<String, Object> parameters = HttpRequestUtil.getAllParameters(request);
//        main = MapUtil.getStr(parameters, "ds");
////        List<Record> records  = Db.findBySql(ApiSql.class," select * from api_sql ");
////        List<ApiSql> apiSqls = RecordUtil.recordToListBean(records, ApiSql.class);
//        List<ApiSql> apiSqls = Db.findBeanList(ApiSql.class," select * from api_sql ");
//        Db.use().findBeanList(ApiSql.class," select * from api_sql ");
//        Map<String, ApiSql> apiSqlMap = apiSqls.stream().collect(Collectors.toMap(i->i.getPath(), i->i));
//        if(apiSqlMap.containsKey(path)){
//            Object obj = restApiService.doSQLProcess(apiSqlMap.get(path), parameters);
//            return Result.success(obj);
//        }else{
//            return Result.error("执行的接口不存在" );
//        }
//    }
//
//
//
//    public static Object executeSQL(DataSource ds, Map<String, Object> sqlParam, String deleteSQL)
//            throws SQLException {
//        SqlMeta sqlMeta = SqlXmlUtil.getEngine().parse(deleteSQL, sqlParam);
//        Object datas = SqlXmlUtil.executeSql(ds.getConnection(), sqlMeta.getSql(), sqlMeta.getJdbcParamValues());
//        System.err.println(JSON.toJSONString(datas));
//        return datas;
//    }
//
//
//}

package io.github.wujun728.rest.controller;

import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.db.meta.MetaUtil;
import cn.hutool.db.meta.Table;
import cn.hutool.json.JSON;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import cn.hutool.log.StaticLog;
import io.github.wujun728.common.base.Result;
import io.github.wujun728.common.exception.BusinessException;
import io.github.wujun728.db.record.Db;
import io.github.wujun728.db.record.Page;
import io.github.wujun728.db.record.Record;
import io.github.wujun728.db.utils.DataSourcePool;
import io.github.wujun728.db.utils.RecordUtil;
import io.github.wujun728.rest.service.RestApiService;
import io.github.wujun728.rest.util.HttpRequestUtil;
import io.github.wujun728.rest.util.RestUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * @author Wujun
 * @desc 通用Rest服务接口
 */
@Slf4j
@org.springframework.web.bind.annotation.RestController
@RequestMapping({"${platform.path:}/bizrest", "${platform.path:}/public/bizrest"})
//@Api(value = "实体公共增删改查接口")
public class RestApiController {

    @Resource
    RestApiService restApiService;

    private String main = "main";

    @GetMapping(path = {"/{entityName}/list"}, produces = "application/json")
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
            List<Map<String, Object>> datas = restApiService.getList(tableName,parameters);
            return Result.success(datas);
        } catch (Exception e) {
            String message = ExceptionUtils.getMessage(e);
            log.error(message, e);
            return Result.error(message);
        }
    }

    @GetMapping(path = {  "/{entityName}/page" }, produces = "application/json")
    //@ApiOperation(value = "返回实体数据列表", notes = "page与size同时大于零时返回分页实体数据列表,否则返回全部数据列表;
    public Result page(@PathVariable("entityName") String entityName, HttpServletRequest request) throws Exception {
        Map<String, Object> parameters = HttpRequestUtil.getAllParameters(request);
        main = MapUtil.getStr(parameters, "ds","main");
        String tableName = StrUtil.toUnderlineCase(entityName);
        Boolean isUnderLine = entityName.equals(tableName);
        try {
            String url = request.getRequestURI();
            StaticLog.info("url = "+ url);
            parameters.put("entityName" , entityName);
            parameters.put("tableName" , tableName);
            Page<Record> pages = restApiService.getPage(tableName,parameters);
            List<Map<String, Object>> datas = RecordUtil.recordToMaps(pages.getList(),isUnderLine);
            return Result.success(datas).put("count", pages.getTotalRow()).put("pageSize", pages.getPageSize()).put("totalPage", pages.getTotalPage()).put("pageNumber", pages.getPageNumber());
        } catch (Exception e) {
            String message = ExceptionUtils.getMessage(e);
            log.error(message, e);
            return Result.error(message);
        }
    }


    @GetMapping(path = {"/{entityName}/tree"}, produces = "application/json")
    //@ApiOperation(value = "返回实体数据列表", notes = "page与size同时大于零时返回分页实体数据列表,否则返回全部数据列表;
    public Result tree(@PathVariable("entityName") String entityName, HttpServletRequest request) throws Exception {
        Map<String, Object> parameters = HttpRequestUtil.getAllParameters(request);
        main = MapUtil.getStr(parameters, "ds","main");
        String tableName = StrUtil.toUnderlineCase(entityName);
        Boolean isUnderLine = entityName.equals(tableName);
        try {
            String url = request.getRequestURI();
            StaticLog.info("url = "+ url);
            parameters.put("entityName" , entityName);
            parameters.put("tableName" , tableName);
            parameters.put("url" , url);
            List<Map<String, Object>> datas = restApiService.getTree(tableName,parameters);
            return Result.success(datas);
            //是否构建树 end
        } catch (Exception e) {
            String message = ExceptionUtils.getMessage(e);
            log.error(message, e);
            return Result.error(message);
        }
    }

    @GetMapping(path = {"/{entityName}/findOne","/{entityName}/getOne","/{entityName}/record","/{entityName}/row"}, produces = "application/json")
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
            Record record = Db.findById(tableName, primaryKey, args.toArray());
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

    @DeleteMapping(path = "/{entityName}/{ids}", produces = "application/json")
    //@ApiOperation(value = "根据id删除实体数据" )
    public Result delete2(@PathVariable("entityName") String entityName,@PathVariable("ids") String ids, HttpServletRequest request) throws Exception {
        return delete(entityName, ids, request);
    }
    @RequestMapping(path = "/{entityName}/delete/{ids}", produces = "application/json")
    //@ApiOperation(value = "根据id删除实体数据" )
    public Result delete(@PathVariable("entityName") String entityName,@PathVariable("ids") String ids, HttpServletRequest request) throws Exception {
        Map<String, Object> parameters = HttpRequestUtil.getAllParameters(request);
        main = MapUtil.getStr(parameters, "ds","main");
        String tableName = StrUtil.toUnderlineCase(entityName);
        try {
            parameters.put("entityName" , entityName);
            parameters.put("tableName" , tableName);
            Table table = getTableMeta(tableName,main);
            String primaryKey = "id";
            Object[] argsDelete = new Object[]{} ;
            if(StrUtil.isNotEmpty(ids)){
                if(ids.contains(",")){
                    argsDelete = ids.split(",");
                }else{
                    argsDelete = new Object[]{ids};
                }
            }else{
                primaryKey = RestUtil.getTablePrimaryKes(table);
                List args = RestUtil.getPrimaryKeyArgs(parameters, table);
                argsDelete = args.toArray();
            }
            Boolean flag =   Db.deleteById(tableName, primaryKey, argsDelete);
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


    @PostMapping(path = {"/{entityName}"}, produces = "application/json")
    public Result save(@PathVariable("entityName") String entityName, HttpServletRequest request) {
        return create(entityName, request);
    }
    @RequestMapping(path = {"/{entityName}/save","/{entityName}/add"}, produces = "application/json")
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
            RestUtil.buildRecord(parameters, table, record);
            //Step4，根据表定义拿到全部参数并生成入库的对象，并持久化并返回数据
            Boolean isSucess;
            RestUtil.fillRecord(record,tableName,true);
            Record finalRecord = record;
            isSucess = Db.save(tableName, finalRecord);
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

    @PutMapping(path = {"/{entityName}"}, produces = "application/json")
    public Result edit(@PathVariable("entityName") String entityName, HttpServletRequest request) {
        return update(entityName, request);
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
            record = Db.findById(tableName, primaryKey, args.toArray());
            if (ObjectUtil.isNull(record)) {
                return Result.fail("修改失败，无此ID对应的记录！");
            }
            RestUtil.buildRecord(parameters,table,record);
            //Step4，根据表定义拿到全部参数并生成入库的对象，并持久化并返回数据
            Boolean isSucess;
            RestUtil.fillRecord(record,tableName,false);
            Record finalRecord1 = record;
            //isSucess = Db.tx(() -> Db.update(tableName, finalRecord1));
            isSucess = Db.update(tableName, finalRecord1);
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

    /*public static void main(String[] args) {
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
    }*/
    @RequestMapping(path = {"/cache/clear"}, produces = "application/json")
    public Result cacheClear(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> parameters = HttpRequestUtil.getAllParameters(request);
        main = MapUtil.getStr(parameters, "ds","main");
        //tableCache.set(new HashMap<>());
        return Result.success("缓存清理成功！");
    }



    public static Table getTableMeta(String tableName,String ds) {
        //Map map = tableCache.get();
        //if (!map.containsKey(tableName)) {
        Table table = MetaUtil.getTableMeta(DataSourcePool.get(ds), tableName);
        //map.put(tableName, table);
        //tableCache.set(map);
        if (CollectionUtils.isEmpty(table.getColumns())) {
            throw new BusinessException("实体对应的表不存在！");
        }
        return table;
        //}
    }



    /**
     * 接收多层级 JSON 数据并解析
     * @param json Hutool JSON 对象（自动接收前端传递的 JSON 数据）
     * @return 解析后的结果
     */
    @PostMapping("/api/query")
    public String query(@RequestBody JSON json) {
        // ================ 1. 基础多层级取值（核心场景） ================
        // 示例前端传递的 JSON 数据结构：
        // {
        //   "user": {
        //     "basicInfo": {
        //       "name": "张三",
        //       "age": 25,
        //       "address": {
        //         "province": "广东省",
        //         "city": "深圳市"
        //       }
        //     },
        //     "hobbies": ["篮球", "编程", "阅读"]
        //   },
        //   "page": {
        //     "pageNum": 1,
        //     "pageSize": 10
        //   }
        // }

        // 关键步骤：将顶层 JSON 接口强转为 JSONObject（核心修正）
        JSONObject rootObj = json instanceof JSONObject ? (JSONObject) json : new JSONObject();

        // ================ 1. 逐层获取多层级数据 ================
        // 第一层：获取 user 节点（JSONObject）
        JSONObject userObj = rootObj.getJSONObject("user");
        // 第二层：获取 user.basicInfo 节点
        JSONObject basicInfoObj = userObj == null ? new JSONObject() : userObj.getJSONObject("basicInfo");
        // 第三层：获取基础信息字段（带默认值，避免空指针）
        String name = basicInfoObj.getStr("name", "未知");
        Integer age = basicInfoObj.getInt("age", 0);

        // 第四层：获取 user.basicInfo.address 节点
        JSONObject addressObj = basicInfoObj.getJSONObject("address");
        String province = addressObj == null ? "未知" : addressObj.getStr("province", "未知");
        String city = addressObj == null ? "未知" : addressObj.getStr("city", "未知");

        // 方式2：链式取值（简洁，推荐空值时加默认值）
        Integer pageNum = json.getByPath("page.pageNum", Integer.class); // 路径取值，默认值1
        Integer pageSize = json.getByPath("page.pageSize", Integer.class);

        // ================ 2. 集合/数组取值 ================
        JSONArray hobbiesArray = userObj.getJSONArray("hobbies"); // 获取数组
        // 遍历数组
        StringBuilder hobbies = new StringBuilder();
        for (int i = 0; i < hobbiesArray.size(); i++) {
            hobbies.append(hobbiesArray.getStr(i)).append(",");
        }

        // ================ 3. 空值安全处理（避免空指针） ================
        // 取值时指定默认值，防止字段不存在时报错
        String email = basicInfoObj.getStr("email", "未填写"); // 字段不存在时返回"未填写"
        Long phone = basicInfoObj.getLong("phone", 0L); // 字段不存在时返回0L

        // ================ 4. 结果拼接（示例） ================
        return String.format(
                "姓名：%s，年龄：%d，地址：%s-%s，页码：%d，每页条数：%d，爱好：%s，邮箱：%s，手机号：%d",
                name, age, province, city, pageNum, pageSize, hobbies.toString().replaceAll(",$", ""),
                email, phone
        );
    }
}
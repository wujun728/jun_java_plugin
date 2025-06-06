package io.github.wujun728.rest.service;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.db.meta.Column;
import cn.hutool.db.meta.Table;
import cn.hutool.json.JSONUtil;
import cn.hutool.log.StaticLog;
import com.alibaba.fastjson2.JSON;
import io.github.wujun728.common.base.Result;
import io.github.wujun728.common.exception.BusinessException;
import io.github.wujun728.db.record.Db;
import io.github.wujun728.db.record.Page;
import io.github.wujun728.db.record.Record;
import io.github.wujun728.db.utils.FieldUtils;
import io.github.wujun728.db.utils.RecordUtil;
import io.github.wujun728.db.utils.TreeBuildUtil;
import io.github.wujun728.generator.util.MapUtil;
import io.github.wujun728.rest.util.RestUtil;
import io.github.wujun728.sql.SqlMeta;
import io.github.wujun728.sql.utils.JdbcUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.*;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import static io.github.wujun728.db.utils.DataSourcePool.main;
import static io.github.wujun728.rest.controller.RestApiController.getTableMeta;
import static io.github.wujun728.rest.util.RestUtil.checkDataFormat;
import static io.github.wujun728.rest.util.RestUtil.setPkValue;


//import static io.github.wujun728.rest.util.DataSourcePool.main;

@Slf4j
@Service
public class RestApiService {

    public List<Map<String, Object>> getList(String tableName, Map<String, Object> parameters){
        String entityName = MapUtil.getString(parameters,"entityName");
        //String tableName = StrUtil.toUnderlineCase(entityName);
        Boolean isUnderLine = entityName.equals(tableName);
        Table table = getTableMeta(tableName,main);
        StringBuffer sql = new StringBuffer();
        String select = "select *";
        sql.append(select);
        String from = " from " + tableName;
        String where = RestUtil.getQueryCondition(parameters,table);
        if(StrUtil.isNotEmpty(where)){
            from = from + " where 1=1 "+ where;
        }
        sql.append(from);
        List<Record> datas1 = Db.find(sql.toString());
        List<Map<String, Object>> datas = RecordUtil.recordToMaps(datas1,isUnderLine);
        return datas;
    }
    public Page<Record> getPage(String tableName, Map<String, Object> parameters){
        Table table = getTableMeta(tableName,main);
        StringBuffer sql = new StringBuffer();
        String select = "select *";
        sql.append(select);
        String from = " from " + tableName;
        String where = RestUtil.getQueryCondition(parameters,table);
        if(StrUtil.isNotEmpty(where)){
            from = from + " where 1=1 "+ where;
        }
        sql.append(from);
        Integer page = cn.hutool.core.map.MapUtil.getInt(parameters, "page");
        if ((page == null || page == 0)  ) {
            page = 1;
        }
        Integer limit = cn.hutool.core.map.MapUtil.getInt(parameters, "limit");
        if ( (limit == null || limit == 0)) {
            limit = 10;
        }
        Page<Record> datas = Db.paginate(page, limit, select, from);
        return datas;
    }

    public List<Map<String, Object>> getTree(String tableName, Map<String, Object> parameters){
        String entityName = MapUtil.getString(parameters,"entityName");
        String url = MapUtil.getString(parameters,"url");
        //String tableName = StrUtil.toUnderlineCase(entityName);
        Boolean isUnderLine = entityName.equals(tableName);
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
        Boolean isTree = url.contains("tree") ?true:false;
        List<Record> datas1 = Db.find(sql.toString());
        List<Map<String, Object>> datas = RecordUtil.recordToMaps(datas1,isUnderLine);
        //是否构建树 begin
        if(isTree){
            String treeId = cn.hutool.core.map.MapUtil.getStr(parameters, "id") == null ? "id" : cn.hutool.core.map.MapUtil.getStr(parameters, "id");
            String treePid = cn.hutool.core.map.MapUtil.getStr(parameters, "pid") == null ? "pid" : cn.hutool.core.map.MapUtil.getStr(parameters, "pid");
            Object rootId = parameters.get("rootId") == null ? 0L : parameters.get("rootId");
            if (StrUtil.isNotEmpty(treePid)) {
                List<Map<String, Object>> treeList = TreeBuildUtil.listToTree(datas,String.valueOf(rootId),treeId,treePid);
                StaticLog.info(JSONUtil.toJsonPrettyStr(treeList));
                return treeList;
            }
        }
        return datas;
    }


    public static void download(HttpServletResponse response, File file) throws IOException {
        String filename = file.getName();
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Content-Disposition", "attachment; filename=\"" + filename + "\"");
        response.addHeader("Content-Length", "" + file.length());
        response.setContentType("application/octet-stream");
        try(InputStream is = new FileInputStream(file);
            BufferedInputStream bis = new BufferedInputStream(is);
            BufferedOutputStream bos = new BufferedOutputStream(response.getOutputStream())){
            byte[] buff = new byte[2048];
            int len;
            while ((len = bis.read(buff)) != -1){
                bos.write(buff, 0, len);
            }
            bos.flush();
        } catch (IOException e){
            throw e;
        }
    }

    public static Object executeSQL(DataSource ds, Map<String, Object> sqlParam, String deleteSQL)
            throws SQLException {
        SqlMeta sqlMeta = JdbcUtil.getEngine().parse(deleteSQL, sqlParam);
        Object datas = JdbcUtil.executeSql(ds.getConnection(), sqlMeta.getSql(), sqlMeta.getJdbcParamValues());
        System.err.println(JSON.toJSONString(datas));
        return datas;
    }


    public Result saveOrUpdate(String entityName, Map<String, Object> parameters, Boolean isSave) throws Exception {
        //Step1,校验表信息，并获取表定义及主键信息
        String tableName = StrUtil.toUnderlineCase(entityName);
        parameters.put("entityName" , entityName);
        parameters.put("tableName" , tableName);
        Table table = getTableMeta(tableName,main);
        //Step2,根据表定义，获取表主键，并根据新增及修改，生成主键或者判断主键数据是否存在
        //Step3,根据表定义，新增必填字段信息校验，并将默认或者内置字段生成默认值
        String primaryKey = RestUtil.getTablePrimaryKes(table);
        String primaryValue = RestUtil.getParamValue(parameters, primaryKey);
        if(StrUtil.isNotEmpty(primaryValue)){
            isSave = false;
        }
        Record record = new Record();
        if (!isSave) {
            List args = RestUtil.getPrimaryKeyArgs(parameters, table);
            record = Db.findById(tableName, primaryKey, args.toArray());
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
            //isSucess = Db.tx(() -> Db.save(tableName, finalRecord));
            isSucess = Db.save(tableName, finalRecord);
        } else {
            RestUtil.fillRecord(record,tableName,false);
            Record finalRecord1 = record;
            //isSucess = Db.tx(() -> Db.update(tableName, finalRecord1));
            isSucess = Db.update(tableName, finalRecord1);
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

    /*@RequestMapping(path = {"/api/test"}, produces = "application/json")
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
    }*/





}

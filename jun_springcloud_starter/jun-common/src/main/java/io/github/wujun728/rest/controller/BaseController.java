package io.github.wujun728.rest.controller;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.db.meta.Column;
import cn.hutool.db.meta.MetaUtil;
import cn.hutool.db.meta.Table;
import cn.hutool.json.JSONUtil;
import cn.hutool.log.StaticLog;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import io.github.wujun728.common.Result;
import io.github.wujun728.common.exception.BusinessException;
import io.github.wujun728.common.utils.IdGenerator15;
import io.github.wujun728.rest.util.DataSourcePool;
//import io.github.wujun728.db.record.Db;
//import io.github.wujun728.db.record.FieldUtils;
//import io.github.wujun728.db.record.Record;
import io.github.wujun728.rest.util.FieldUtils;
import io.github.wujun728.rest.service.IRestApiService;
import io.github.wujun728.rest.util.RestUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @author Wujun
 * @desc 通用Rest服务接口
 */
@Slf4j
//@Api(value = "实体公共增删改查接口")
public class BaseController {

    private String main = "main";

    @Resource
    IRestApiService rescApiService;

    /*static AtomicReference<Map<String, Table>> tableCache = new AtomicReference<>();  */

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

    public Result saveOrUpdate(String entityName, Map<String, Object> parameters, Boolean isSave) throws Exception {
        //Step1,校验表信息，并获取表定义及主键信息
        String tableName = StrUtil.toUnderlineCase(entityName);
        main = MapUtil.getStr(parameters, "ds","main");
        parameters.put("entityName" , entityName);
        parameters.put("tableName" , tableName);
        Table table = getTableMeta(tableName,main);
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
            //isSucess = Db.use(main).tx(() -> Db.use(main).save(tableName, finalRecord));
            isSucess = Db.use(main).save(tableName, finalRecord);
        } else {
            RestUtil.fillRecord(record,tableName,false);
            Record finalRecord1 = record;
            //isSucess = Db.use(main).tx(() -> Db.use(main).update(tableName, finalRecord1));
            isSucess = Db.use(main).update(tableName, finalRecord1);
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
            String idStr = IdGenerator15.generateIdStr();
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

            long idStr = IdGenerator15.generateId();
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

}

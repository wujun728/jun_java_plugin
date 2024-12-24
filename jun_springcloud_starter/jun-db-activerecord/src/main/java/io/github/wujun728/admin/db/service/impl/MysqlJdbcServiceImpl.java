package io.github.wujun728.admin.db.service.impl;

import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import io.github.wujun728.admin.common.BaseData;
import io.github.wujun728.admin.common.Result;
import io.github.wujun728.admin.common.config.UserSession;
import io.github.wujun728.admin.common.service.LogService;
import io.github.wujun728.admin.db.data.ColumnInfo;
import io.github.wujun728.admin.db.data.TableInfo;
import io.github.wujun728.admin.db.service.JdbcService;
import io.github.wujun728.admin.db.service.TableService;
import io.github.wujun728.admin.db.service.TransactionOption;
import io.github.wujun728.admin.util.StringUtil;
import io.github.wujun728.admin.util.TemplateUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

@Service("jdbcService")
//@ConditionalOnProperty(value="db.type",havingValue = "mysql")
@Slf4j
public class MysqlJdbcServiceImpl extends MysqlJdbcDaoImpl implements JdbcService {
    @Resource
    private TableService tableService;

//    @Resource
//    private LogService logService;

    UserSession getSession(){
        UserSession userSession = new UserSession();
        userSession.setEnterpriseId(1L);
        userSession.setUserId(1L);
        return userSession;
    }

    @Override
    public void insert(BaseData obj) {
        if(obj == null){
            return;
        }
        String clzName = obj.getClass().getSimpleName();
        String tableName = StringUtil.toSqlColumn(clzName);
        Result<TableInfo> tableInfo = tableService.get(tableName);
        if(!tableInfo.isSuccess()){
            throw new RuntimeException("找不到表:"+tableName);
        }
        Map<String, Field> fieldMap = ReflectUtil.getFieldMap(obj.getClass());
        if(fieldMap.containsKey("enterpriseId") && ReflectUtil.getFieldValue(obj,"enterpriseId") == null){
            //有企业id
            ReflectUtil.setFieldValue(obj,fieldMap.get("enterpriseId"), getSession().getEnterpriseId());
        }
        if(fieldMap.containsKey("createUserId") && ReflectUtil.getFieldValue(obj,"createUserId") == null){
            //有创建人id
            ReflectUtil.setFieldValue(obj,fieldMap.get("createUserId"), getSession().getUserId());
        }
        if(fieldMap.containsKey("updateAt") && ReflectUtil.getFieldValue(obj,"updateAt") == null){
            ReflectUtil.setFieldValue(obj,fieldMap.get("updateAt"), new Date());
        }
        if(fieldMap.containsKey("createAt") && ReflectUtil.getFieldValue(obj,"createAt") == null){
            ReflectUtil.setFieldValue(obj,fieldMap.get("createAt"), new Date());
        }
        TableInfo table = tableInfo.getData();
        List<ColumnInfo> columnInfos = table.getColumnInfos();
        List<String> columns = columnInfos.stream().map(ColumnInfo::getColumnName).collect(Collectors.toList());
        List<String> args = columnInfos.stream().map(columnInfo -> "?").collect(Collectors.toList());
        String sql = StrUtil.format("insert into {} ({}) values ({})",table.getTableName(),StringUtil.concatStr(columns,","),StringUtil.concatStr(args,","));
        List<Object> values = new ArrayList<>();

        for(String column:columns){
            Object value = null;
            String fieldName = StringUtil.toFieldColumn(column);
            if(fieldMap.containsKey(fieldName)){
                Field field = fieldMap.get(fieldName);
                value = ReflectUtil.getFieldValue(obj, field);
            }
            values.add(value);
        }

        Long id = this.insert("执行" + tableName + "insert ", sql, values.toArray());
        obj.setId(id);

        log(obj);
    }

    private static void log(BaseData obj) {
        LogService logService = SpringUtil.getBean(LogService.class);
        if (logService != null) {
            logService.log(null, obj);
        }
    }

    @Override
    public void insert(Map<String, Object> obj, String tableName) {
        if(obj == null){
            return;
        }
        Result<TableInfo> tableInfo = tableService.get(tableName);
        if(!tableInfo.isSuccess()){
            throw new RuntimeException("找不到表:"+tableName);
        }
        TableInfo table = tableInfo.getData();
        List<ColumnInfo> columnInfos = table.getColumnInfos();
        if(obj.get("enterpriseId") == null){
            obj.put("enterpriseId",getSession().getEnterpriseId());
        }
        obj.computeIfAbsent("updatedAt", k -> new Date());
        obj.computeIfAbsent("createdAt", k -> new Date());
        // List<String> columns = columnInfos.stream().map(columnInfo -> columnInfo.getColumnName()).collect(Collectors.toList());
        List<String> columns = columnInfos.stream().filter(
                columnInfo -> obj.containsKey(StringUtil.toFieldColumn(columnInfo.getColumnName()))
                              || "updated_at".equals(columnInfo.getColumnName())
                              || "created_at".equals(columnInfo.getColumnName())
                                                          ).map(ColumnInfo::getColumnName).collect(Collectors.toList());
        List<String> args = columns.stream().map(columnInfo -> "?").collect(Collectors.toList());
        String sql = StrUtil.format("insert into {} ({}) values ({})",table.getTableName(),StringUtil.concatStr(columns,","),StringUtil.concatStr(args,","));
        List<Object> values = new ArrayList<>();
        for(String column:columns){
            Object value = null;
            String fieldName = StringUtil.toFieldColumn(column);
            if(obj.containsKey(fieldName)){
                value = obj.get(fieldName);
            }
            values.add(value);
        }

        Long id = this.insert("执行" + tableName + "insert ", sql, values.toArray());
        obj.put("id",id);
        LogService logService = SpringUtil.getBean(LogService.class);
        if(logService!=null){
            logService.log(null,obj,tableName);
        }
    }

    @Override
    public void update(BaseData obj) {
        if(obj == null){
            return;
        }
        if(obj.getId() == null){
            throw new RuntimeException("更新失败,id不能为空");
        }
        String clzName = obj.getClass().getSimpleName();
        String tableName = StringUtil.toSqlColumn(clzName);
        Result<TableInfo> tableInfo = tableService.get(tableName);
        if(!tableInfo.isSuccess()){
            throw new RuntimeException("找不到表:"+tableName);
        }

        TableInfo table = tableInfo.getData();
        List<ColumnInfo> columnInfos = table.getColumnInfos();
        List<String> columns = columnInfos.stream().map(columnInfo -> columnInfo.getColumnName()).collect(Collectors.toList());
        List<String> args = columnInfos.stream().map(columnInfo -> columnInfo.getColumnName()+"=?").collect(Collectors.toList());
        String sql = StrUtil.format("update {} set {} where id = ? ",table.getTableName(),StringUtil.concatStr(args,","),args);
        Map<String, Field> fieldMap = ReflectUtil.getFieldMap(obj.getClass());
        List<Object> values = new ArrayList<>();
        for(String column:columns){
            Object value = null;
            String fieldName = StringUtil.toFieldColumn(column);
            if(fieldMap.containsKey(fieldName)){
                Field field = fieldMap.get(fieldName);
                value = ReflectUtil.getFieldValue(obj, field);
            }
            values.add(value);
        }
        values.add(obj.getId());

        BaseData beforeObj = getById(obj.getClass(), obj.getId());
        this.update(StrUtil.format("更新{},{}",tableName,obj.getId()),sql,values.toArray());
        LogService logService = SpringUtil.getBean(LogService.class);
        if(logService!=null){
            logService.log(beforeObj,obj);
        }

    }

    @Override
    public void update(Map<String, Object> obj, String tableName) {
        if(obj == null){
            return;
        }
        Long id = (Long) obj.get("id");
        if(id == null){
            throw new RuntimeException("更新失败,id不能为空");
        }
        Result<TableInfo> tableInfo = tableService.get(tableName);
        if(!tableInfo.isSuccess()){
            throw new RuntimeException("找不到表:"+tableName);
        }

        TableInfo table = tableInfo.getData();
        List<ColumnInfo> columnInfos = table.getColumnInfos();
        List<String> columns = columnInfos.stream().filter(columnInfo ->
            obj.containsKey(StringUtil.toFieldColumn(columnInfo.getColumnName())) || "updated_at".equals(columnInfo.getColumnName())
        ).map(ColumnInfo::getColumnName).collect(Collectors.toList());
        List<String> args = columns.stream().map(c->c+"=?").collect(Collectors.toList());
        String sql = StrUtil.format("update {} set {} where id = ? ",table.getTableName(),StringUtil.concatStr(args,","),args);
        List<Object> values = new ArrayList<>();
        for(String column:columns){
            String fieldName = StringUtil.toFieldColumn(column);
            if("updatedAt".equals(fieldName)){ //如果有updated_at列， 更新时自动更新时间
                values.add(new Date());
                continue;
            }
            Object value = obj.get(fieldName);
            values.add(value);
        }
        values.add(id);
        Map<String, Object> beforeObj = getById(tableName, id);
        this.update(StrUtil.format("更新{},{}",tableName,id),sql,values.toArray());
        LogService logService = SpringUtil.getBean(LogService.class);
        if(logService!=null){
            logService.log(beforeObj,obj,tableName);
        }
    }

    @Override
    public void saveOrUpdate(BaseData obj) {
        if(obj == null){
            return;
        }
        if(obj.getId() == null){
            this.insert(obj);
        }else{
            this.update(obj);
        }
    }

    @Override
    public void saveOrUpdate(Map<String, Object> obj,String tableName) {
        if(obj == null){
            return;
        }
        if(obj.get("id") == null){
            this.insert(obj,tableName);
        }else{
            this.update(obj,tableName);
        }
    }

    @Override
    @Transactional
    public void bathSaveOrUpdate(List<? extends BaseData> objs) {
        if(objs == null){
            return;
        }
        objs.forEach(obj->{
            this.saveOrUpdate(obj);
        });
    }

    @Override
    @Transactional
    public void bathSaveOrUpdate(List<Map<String, Object>> objs, String tableName) {
        if(objs == null){
            return;
        }
        objs.forEach(obj->{
            this.saveOrUpdate(obj,tableName);
        });
    }

    @Override
    public void delete(BaseData obj) {
        if(obj == null || obj.getId() == null){
            return;
        }
        String tableName = StringUtil.toSqlColumn(obj.getClass().getSimpleName());
        this.delete(obj.getId(),tableName);
    }

    @Override
    public void delete(Long id, Class<? extends BaseData> clz) {
        if(id == null || clz == null){
            return;
        }
        String tableName = StringUtil.toSqlColumn(clz.getSimpleName());
        this.delete(id,tableName);
    }

    @Override
    public void delete(Long id, String tableName) {
        if(id == null || StrUtil.isBlank(tableName)){
            return;
        }
        Map<String, Object> beforeObj = getById(tableName, id);
        super.update("删除",StrUtil.format("delete from {} where id = ? ",tableName),id);
        LogService logService = SpringUtil.getBean(LogService.class);
        if(logService!=null){
            logService.log(beforeObj,null,tableName);
        }
    }

    @Override
    @Transactional
    public void transactionOption(TransactionOption transactionOption) {
        transactionOption.call();
    }
    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void forceSaveOrUpdate(BaseData obj) {
        this.saveOrUpdate(obj);
    }
    @Override
    public boolean isRepeat(String sql, Map<String, Object> params) {
        Object id = params.get("id");
        if(id != null && StringUtils.isNotBlank(id.toString())){
            Long idValue = Long.parseLong(id.toString());
            params.put("id",idValue);
        }else{
            params.put("id",-1L);
        }
        sql = TemplateUtil.getValue(sql,params);
        return !this.find(sql).isEmpty();
    }

    @Override
    public Set<Long> findChildIds(String parentSql, String childSql) {
        List<Long> parentIds = this.findForObject(parentSql, Long.class);
        return this.findChildIds(parentIds,childSql);
    }

    @Override
    public Set<Long> findChildIds(Collection<Long> parentIds, String childSql) {
        Set<Long> allIds = new HashSet<>(parentIds);
        this._findChildIds(allIds,new HashSet<>(parentIds),childSql);
        return allIds;
    }

    private void _findChildIds(Set<Long> allIds,Set<Long> parentIds,String childSql){
        if(parentIds.isEmpty()){
            return;
        }
        String sql = StrUtil.format(childSql,StringUtil.concatStr(parentIds,","));
        List<Long> _ids = this.findForObject(sql, Long.class);
        allIds.addAll(_ids);
        this._findChildIds(allIds,new HashSet<>(_ids),childSql);
    }

    @Override
    public void delete(String sql, Object... args) {
        String pre = "delete from ";
        sql = sql.toLowerCase();
        String end = sql.substring(sql.indexOf(pre) + pre.length());
        String tableName = end.substring(0, end.indexOf(" ")).trim();

        List<Long> list = this.findForObject("select id from " + end,Long.class, args);
        for(Long id:list){
            this.delete(id,tableName);
        }
    }

    @Override
    public boolean ownerEnterprise(String tableName, Long id, Long userEnterpriseId) {
        if(id ==null){
            return true;
        }
        Map<String, Object> byId = getById(tableName, id);
        if (byId.size() > 0 && byId.containsKey("enterpriseId")) {
            Long dataEnterpriseId = (Long) byId.get("enterpriseId");
            if (!dataEnterpriseId.equals(userEnterpriseId)) {
                return false;
            }
        }
        return true;
    }
}

package io.github.wujun728.admin.common.service.impl;

import cn.hutool.cache.Cache;
import cn.hutool.cache.CacheUtil;
import cn.hutool.core.util.StrUtil;
import io.github.wujun728.admin.common.data.LogTable;
import io.github.wujun728.admin.common.service.DbCacheService;
import io.github.wujun728.admin.db.service.JdbcService;
import io.github.wujun728.admin.page.constants.Whether;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

@Service("dbCacheService")
public class DbCacheServiceImpl extends AbstractCacheService<Map<String,Object>> implements DbCacheService {
    private static String SPLIT = ":";

    @Resource
    private JdbcService jdbcService;

    /***
     * 最多3000条数据缓存,使用率少的自动淘汰
     * @return
     */
    @Override
    protected Cache<String, Map<String, Object>> initCache() {
        return CacheUtil.newLFUCache(3000);
    }

    @Override
    protected Map<String, Object> load(String key) {
        String[] arr = key.split(SPLIT);
        String tableName = arr[0].toLowerCase();
        LogTable logTable = jdbcService.findOne("select * from log_table where lower(table_name) = ? ", LogTable.class, tableName);
        if(logTable == null || !Whether.YES.equals(logTable.getOpenCache()) || StringUtils.isBlank(logTable.getCacheKeyFields())){
            return null;
        }
        String sql = StrUtil.format("select * from {} where 1=1 ",tableName);
        String[] fields = logTable.getCacheKeyFields().split(",");
        String[] values = arr[1].split(",");
        for(int i=0;i<fields.length;i++){
            sql += " and " + fields[i] + "= ? ";
        }
        Map<String, Object> obj = jdbcService.findOne(sql, values);
        return obj;
    }

    public String getDataKey(String tableName,String key){
        return StrUtil.format("{}{}{}",tableName.toLowerCase(),SPLIT,key);
    }

    @Override
    public Map<String, Object> getData(String tableName, String key) {
        return super.get(this.getDataKey(tableName,key));
    }
}

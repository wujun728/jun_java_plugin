package io.github.wujun728.online.dao;

import com.baomidou.mybatisplus.core.metadata.IPage;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 在线表单数据访问接口
 * 提供对表单数据的增删改查操作
 */
@Mapper
public interface OnlineFormDao {
    public void delete(@Param(value="tableName") String var1, @Param(value="list") List<Long> var2);

    public Map<String, Object> getById(@Param(value="tableName") String var1, @Param(value="id") Long var2);

    public void save(@Param(value="tableName") String var1, @Param(value="columns") Map<String, Object> var2);

    public List<Map<String, Object>> getList(IPage<?> var1, @Param(value="tableName") String var2, @Param(value="params") Map<String, Object> var3);

    public void update(@Param(value="tableName") String var1, @Param(value="id") Long var2, @Param(value="columns") Map<String, Object> var3);
}


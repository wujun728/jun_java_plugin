package cn.springboot.mapper.key;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import cn.springboot.config.db.table.Key;

@Mapper
public interface KeyMapper {

    /**
     * @return 返回key集合
     */
    public List<Key> getTableValues(List<Key> keys);

    /**
     * @return 返回key集合(只存储表名)
     */
    public List<Key> getTables();

}

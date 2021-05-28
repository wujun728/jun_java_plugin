package cn.springboot.mapper.key;

import java.util.List;

import cn.springboot.config.table.Key;
import org.apache.ibatis.annotations.Mapper;


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

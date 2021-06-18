package cn.springboot.mapper;

import java.util.List;
import java.util.Map;

/** 
 * @Description IBaseMapper
 * @author Wujun
 * @date Mar 16, 2017 5:18:48 PM 
 * @param <S>
 * @param <T> 
 */
public interface BaseMapper<S, T> {

    /**
     * @Title 根据ID查询
     * @param id
     * @return
     */
    T findById(S pk);

    /**
     * @Title 根据对象插入数据
     * @author Wujun
     * @param t
     * @throws Exception
     * @return
     */
    int insert(T t);

    /**
     * @Title 根据PK删除
     * @author Wujun
     * @param pk
     * @throws Exception
     * @return
     */
    int delete(S pk);

    /**
     * @Title 根据用户修改
     * @author Wujun
     * @param t
     * @throws Exception
     * @return
     */
    int update(T t);

    /**
     * @Title 根据Map查找单个
     * @author Wujun
     * @param map
     * @return Map
     * @throws Exception
     */
    Map<String, Object> queryOne(Map<String, Object> map);

    /**
     * @Title 根据Map查找多个
     * @author Wujun
     * @param map
     * @return List<Map>
     * @throws Exception
     */
    List<Map<String, Object>> queryAll(Map<String, Object> map);

    /***
     * @Title 根据条件查询数据
     * @param map
     *            查询条件
     * @return 对象集合
     */
    List<T> findAllByFilter(Map<String, Object> map);

    /**
     * @Title 根据条件查询数据记录数
     * @param map
     *            查询条件
     * @return 记录数
     */
    int findAllByFilterCount(Map<String, Object> map);

}
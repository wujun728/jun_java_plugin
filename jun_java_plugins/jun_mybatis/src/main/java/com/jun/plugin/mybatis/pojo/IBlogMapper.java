package com.jun.plugin.mybatis.pojo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author LB
 * @version 1.0
 * @date 2020/12/22 16:18
 */
public interface IBlogMapper {
    /* 约定
     省略statement 根据约定直接定位出sql语句
     方法名和映射文件中的id值一样
     方法的输入参数和映射文件中parameterType保持一致
     方法的返回值和mapper.xml文件中的resultType类型一致

     调用到该方法就会自动映射到该标签
     namespace的值保证了 接口---xxxMapper之间的对应
     保证了 我们在操作接口中的某一个方法的时候可以匹配到某一个mapper中的sql语句
     mapper接口不需要实现类 因为只需要找到对应的sql就ok
     */
    List<blog>  queryAll();

    int addBlog(blog blog);

    int addBlogWithConverter(blog blog);

    int deleteBlogById(int id);

    int updateBlog(blog blog);

    blog selectBlogById(int id);

    blog selectBlogByIdWithConverter(int id);

    List<blog> selectBlogWithHashMap(HashMap<String,Object> map);

    List<HashMap <String,Object>> ResultByMap();

    List<blog> foreach(Grade grade);

    List<blog> arrayForeach(int[] ids2);

    List<blog> listForeach(List<Integer> ids);

    List<blog> objectForeach(blog[] blogs);
}

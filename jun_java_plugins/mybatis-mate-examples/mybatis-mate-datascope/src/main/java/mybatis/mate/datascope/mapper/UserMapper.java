package mybatis.mate.datascope.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import mybatis.mate.annotation.DataColumn;
import mybatis.mate.annotation.DataScope;
import mybatis.mate.datascope.config.DataScopeConfig;
import mybatis.mate.datascope.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@DataScope(type = DataScopeConfig.TEST_CLASS, value = {
        // 这里 DataScope 作用于整个类，优先级小于方法 DataScope 注解
        @DataColumn(name = "department_id")
})
@Mapper
public interface UserMapper extends BaseMapper<User> {

    // 测试 test 类型数据权限范围，混合分页模式
    @DataScope(type = DataScopeConfig.TEST, value = {
            // 关联表 user 别名 u 指定部门字段权限
            @DataColumn(alias = "u", name = "department_id"),
            // 关联表 user 别名 u 指定手机号字段（自己判断处理）
            @DataColumn(alias = "u", name = "mobile")
    })
    @Select("select u.* from user u")
    List<User> selectTestList(IPage<User> page, Long id, @Param("name") String username);

    @DataScope(type = DataScopeConfig.TEST, value = {
            @DataColumn(alias = "u", name = "department_id"),
            @DataColumn(alias = "u", name = "mobile")
    })
    @Select("select u.* from user u join department d on u.department_id = d.id")
    List<User> selectTestJoin(IPage<User> page, Long id, @Param("name") String username);

    @DataScope(type = DataScopeConfig.TEST, value = {
            @DataColumn(alias = "u", name = "department_id"),
            @DataColumn(alias = "u", name = "mobile")
    })
    List<User> selectDeptNamesByUserIds(@Param("userIds") List<Long> userIds, @Param("username") String username);

    /**
     * 忽略某个方法
     */
    @DataScope(ignore = true)
    List<User> selectTestIgnoreDataScope(@Param("userIds") List<Long> userIds, @Param("username") String username);

    /**
     * 使用类注解
     */
    List<User> selectTestXmlSql(@Param("userIds") List<Long> userIds, @Param("username") String username);
}

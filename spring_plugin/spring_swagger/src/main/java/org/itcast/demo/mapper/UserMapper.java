package org.itcast.demo.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.itcast.demo.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Wujun
 * @since 2019-06-24
 */
public interface UserMapper extends BaseMapper<User> {

    @Select("SELECT id,rel_name,log_pwd,log_name,log_status,sex,birthday,create_time from tbl_user ${ew.customSqlSegment}")
    List<User> findAll(@Param(Constants.WRAPPER) Wrapper<User> wrapper);

    IPage<User> pageInfo(Page<User> page,@Param(Constants.WRAPPER) Wrapper<User> wrapper);

}

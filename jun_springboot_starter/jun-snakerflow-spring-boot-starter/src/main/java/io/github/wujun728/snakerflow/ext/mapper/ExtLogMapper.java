package io.github.wujun728.snakerflow.ext.mapper;

import java.util.List;

import io.github.wujun728.snakerflow.ext.entity.ExtLog;
import io.github.wujun728.snakerflow.ext.vo.LogStatisticsTop10Vo;
import io.github.wujun728.snakerflow.ext.vo.LogStatisticsVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 日志 Mapper 接口
 * </p>
 *
 * 
 * @since 2021-08-16
 */
@Mapper
public interface ExtLogMapper extends BaseMapper<ExtLog> {


    @Select("select DATE_FORMAT(create_time,'%Y-%m-%d') date,count(*) value from ext_log where DATE_SUB(CURDATE(), INTERVAL 7 DAY) <= create_time  group by date ORDER BY create_time ")
    List<LogStatisticsVo> selectStatistics7Day();

    @Select("SELECT\n" +
            "\tw.ip,\n" +
            "\tcity,\n" +
            "\tcount( * ) \n" +
            "\tVALUE\t\n" +
            "FROM\n" +
            "\text_log w \n" +
            "WHERE\n" +
            "\tDATE_SUB( CURDATE( ), INTERVAL 1 day ) <= w.create_time \n" +
            "GROUP BY\n" +
            "\tw.ip \n" +
            "ORDER BY\n" +
            "\t\n" +
            "VALUE\n" +
            "DESC \n" +
            "LIMIT 10")
    List<LogStatisticsTop10Vo> selectStatisticsVisitsTop10IP();

    @Select("SELECT count(DISTINCT ip) from ext_log")
    int selectDistinctIp();
    
    @Update(" update ${tableName}  set  ${columnName}  = #{columnValue}  where id = #{id} ")
	int updateRecordByColumnValue(@Param("tableName") String tableName, @Param("columnName") String columnName,
			@Param("columnValue") String columnValue, @Param("id") String id);

}
